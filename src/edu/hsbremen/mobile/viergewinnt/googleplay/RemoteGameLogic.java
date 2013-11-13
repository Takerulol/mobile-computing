package edu.hsbremen.mobile.viergewinnt.googleplay;

import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.Token;
import com.google.android.gms.games.GamesClient;

/**
 * This class handles network game play.
 * It sends and recieves messages to/from other players and sets the gamestate accordingly.
 * @author Thorsten
 *
 */
public class RemoteGameLogic extends GameLogicImpl {

	Token localPlayer;
	
	public RemoteGameLogic(Token localPlayer)
	{
		super();
		this.localPlayer = localPlayer;
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
			
			GamesClient client;
			
			
			
		}
	}
	
	
}
