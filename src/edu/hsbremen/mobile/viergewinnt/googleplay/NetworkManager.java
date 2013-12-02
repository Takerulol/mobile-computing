package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.nio.ByteBuffer;
import java.util.Observable;

import android.util.Log;

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
public class NetworkManager {
	
	public interface Listener {
		void onMessageReceived(byte[] data);
	}
	
	GamesClient client;
	String roomId;
	String participantId;
	private Listener listener;
	
	public NetworkManager(GamesClient client, String roomId, String participantId)
	{
		this.client = client;
		this.roomId = roomId;
		this.participantId = participantId;
	}
	
	public void registerListener(Listener listener) {
		Log.d("NETWORK_MANAGER", "listener registered: " + listener);
		this.listener = listener;
	}
	
	public void unregisterListener() {
		this.listener = null;
	}

	/**
	 * Sends a package with the given header to the participant.
	 * @param header
	 * @param payload
	 */
	public void sendPackage(Header header, byte[] payload) {
		byte[] messageData = new byte[payload.length+1];
		messageData[0] = header.getByteValue();
		for(int i = 1;i < messageData.length;i++) {
			messageData[i] = payload[i-1];
		}
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
	public void onRealTimeMessageReceived(RealTimeMessage rtm) {
		
		Log.d("NETWORK_MANAGER", "Message received.");
		
		byte[] message = rtm.getMessageData();
		
		Log.d("NETWORK_MANAGER", "byte length: " + message.length);
		
		//notify observers, that a new message has been received. 
		Log.d("NETWORK_MANAGER", "message: " + message);
		Log.d("NETWORK_MANAGER", "listener: " + listener);
		int i = 1;
		
		listener.onMessageReceived(message);
		
	}
	
	private byte[] intToByte(int value) {
		ByteBuffer b = ByteBuffer.allocate(4);
		b.putInt(value);

		byte[] result = b.array();
		return result;
	}
	
	
}
