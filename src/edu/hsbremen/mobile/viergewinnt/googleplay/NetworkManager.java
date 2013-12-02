package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.nio.ByteBuffer;
import java.util.Observable;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;

/**
 * Sends and receives packages to/from all participants.
 * To receive packages, the class needs to be set as a listener in the RoomConfig.
 * --> RoomConfig.Builder.setMessageReceivedListener() 
 * @author Thorsten
 *
 */
public class NetworkManager extends Observable 
	implements RealTimeMessageReceivedListener {
	
	GamesClient client;
	String roomId;
	String participantId;
	
	public NetworkManager(GamesClient client, String roomId, String participantId)
	{
		this.client = client;
		this.roomId = roomId;
		this.participantId = participantId;
	}
	
	

	/**
	 * Sends a package with the given header to the participant.
	 * @param header
	 * @param payload
	 */
	public void sendPackage(Header header, byte[] payload)
	{
		byte[] messageData = new byte[payload.length + 1];
		ByteBuffer buffer = ByteBuffer.wrap(payload, 1, payload.length);
		buffer.put(0, header.getByteValue());
		
		client.sendReliableRealTimeMessage(null, messageData, roomId, participantId);
	}
	
	/**
	 * The integer value will be casted to a byte value!
	 * Larger numbers need to be converted to a byte array first.
	 * @param header
	 * @param payload
	 */
	public void sendPackage(Header header, int payload)
	{
		byte[] buffer = intToByte(payload);
		sendPackage(header,buffer);
	}

	/**
	 * Message received handler.
	 */
	@Override
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		
		byte[] message = rtm.getMessageData();
		
		//notify observers, that a new message has been received. 
		
		super.setChanged();
		super.notifyObservers(message);
	}
	
	private byte[] intToByte(int value) {
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putInt(value);

		byte[] result = b.array();
		return result;
	}
	
	
}
