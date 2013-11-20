package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.util.List;

import android.view.WindowManager;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;


/**
 * 
 * @author Thorsten
 *
 * This class implements the RoomUpdateListener and RoomStatusUpdateListener interface.
 * It is used to encapsulate all code required to keep track of a room.
 */
public class RoomManager implements RoomUpdateListener, RoomStatusUpdateListener {

	// are we already playing?
	private boolean mPlaying = false;
	private BaseGameActivity activity;
	private GamesClient gamesClient;

	// at least 2 players required for our game
	final static int MIN_PLAYERS = 2;

	
	public RoomManager(BaseGameActivity activity, GamesClient gamesClient)
	{
		this.activity = activity;
		this.gamesClient = gamesClient;
	}
	
	// returns whether there are enough players to start the game
	boolean shouldStartGame(Room room) {
	    int connectedPlayers = 0;
	    for (Participant p : room.getParticipants()) {
	        if (p.isConnectedToRoom()) ++connectedPlayers;
	    }
	    return connectedPlayers >= MIN_PLAYERS;
	}

	// Returns whether the room is in a state where the game should be cancelled.
	boolean shouldCancelGame(Room room) {
	    // TODO: Your game-specific cancellation logic here. For example, you might decide to 
	    // cancel the game if enough people have declined the invitation or left the room.
	    // You can check a participant's status with Participant.getStatus().
	    // (Also, your UI should have a Cancel button that cancels the game too)
		
		return false;
	}

	@Override
	public void onPeersConnected(Room room, List<String> peers) {
	    if (mPlaying) {
	        // add new player to an ongoing game
	    }
	    else if (shouldStartGame(room)) {
	        // start game!
	    }
	}

	@Override
	public void onPeersDisconnected(Room room, List<String> peers) {
	    if (mPlaying) {
	        // do game-specific handling of this -- remove player's avatar 
	        // from the screen, etc. If not enough players are left for
	        // the game to go on, end the game and leave the room.
	    }
	    else if (shouldCancelGame(room)) {
	        // cancel the game
	        gamesClient.leaveRoom(this, room.getRoomId());
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    }
	}

	@Override
	public void onPeerLeft(Room room, List<String> peers) {
	    // peer left -- see if game should be cancelled
	    if (!mPlaying && shouldCancelGame(room)) {
	        gamesClient.leaveRoom(this, room.getRoomId());
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    }
	}

	@Override
	public void onPeerDeclined(Room room, List<String> peers) {
	    // peer declined invitation -- see if game should be cancelled
	    if (!mPlaying && shouldCancelGame(room)) {
	    	gamesClient.leaveRoom(this, room.getRoomId());
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    }
	}

	
	
	@Override
	public void onConnectedToRoom(Room arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnectedFromRoom(Room arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onP2PConnected(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onP2PDisconnected(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerInvitedToRoom(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPeerJoined(Room arg0, List<String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomAutoMatching(Room arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRoomConnecting(Room arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onLeftRoom(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onRoomCreated(int statusCode, Room room) {
	    if (statusCode != GamesClient.STATUS_OK) {
	        // let screen go to sleep
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        // show error message, return to main screen.
	    }
	}

	@Override
	public void onJoinedRoom(int statusCode, Room room) {
	    if (statusCode != GamesClient.STATUS_OK) {
	        // let screen go to sleep
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        // show error message, return to main screen.
	    }
	}

	@Override
	public void onRoomConnected(int statusCode, Room room) {
	    if (statusCode != GamesClient.STATUS_OK) {
	        // let screen go to sleep
	        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        // show error message, return to main screen.
	    }
	}
}
