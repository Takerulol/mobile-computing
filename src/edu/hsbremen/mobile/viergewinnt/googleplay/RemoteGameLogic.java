package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.nio.ByteBuffer;
import java.util.Observable;
import java.util.Observer;

import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.Token;
import android.util.Log;

import com.google.android.gms.games.GamesClient;

/**
 * This class handles network game play.
 * It sends and recieves messages to/from other players and sets the gamestate accordingly.
 * @author Thorsten
 *
 */
public class RemoteGameLogic extends GameLogicImpl 
	implements Observer {

	Token localPlayer;
	NetworkManager networkManager;
	
	public RemoteGameLogic(Token localPlayer, NetworkManager networkManager)
	{
		super();
		this.localPlayer = localPlayer;
		this.networkManager = networkManager;
		
		//observe network manager for new messages
		networkManager.addObserver(this);
	}
	

	/**
	 * Places a token, if it´s the local players turn.
	 * Sends a message to the remote player.
	 */
	@Override
	public void placeToken(int row) throws IllegalStateException
	{
		//Only the local player is able to set a token by using this method
		if (currentToken.equals(localPlayer))
		{
			super.placeToken(row);
			
			//no exception has been thrown --> token has been placed
			//send message
			
			networkManager.sendPackage(Header.PLACE_TOKEN, row);
		}
	}
	
	/**
	 * Processes a received data package.
	 * @param message Message including the header.
	 */
	private void processMessage(byte[] message)
	{
		Header header = Header.fromValue(message[0]);
		
		switch (header)
		{
		case PLACE_TOKEN:
			handlePlaceToken(message);
			break;
			default:
				throw new IllegalStateException(); 
		}
	}



	/**
	 * Sets a token for the remote player, if it is his turn.
	 */
	private void handlePlaceToken(byte[] message) {
		//the second byte contains the row
		if (!currentToken.equals(localPlayer))
		{
			int row = ByteBuffer.wrap(message, 1, message.length - 1).getInt();
			Log.d("REMOTE_GAME_LOGIC", "Placing remote token in row " + row);
			super.placeToken(row);
		}
		else
		{
			System.out.println("Ignored package, because it´s the local players turn.");
		}
	}


	@Override
	public void update(Observable observable, Object data) {
		// A new message has been received.
		// data contains the byte array including the header
		
		byte[] message = (byte[]) data;
		
		processMessage(message);
	}
	
	
}
