package edu.hsbremen.mobile.viergewinnt;

import java.util.ArrayList;

import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig.Builder;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
import com.google.example.games.basegameutils.BaseGameActivity;

import edu.hsbremen.mobile.viergewinnt.googleplay.NetworkManager;
import edu.hsbremen.mobile.viergewinnt.googleplay.RoomManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends BaseGameActivity 
implements View.OnClickListener, OnInvitationReceivedListener {
	
	// request code (can be any number, as long as it's unique)
	final static int RC_INVITATION_INBOX = 10001;
	final static int RC_SELECT_PLAYERS = 10000;
	
	private NetworkManager networkManager;
	private RoomManager roomManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Sign In / Sign Out buttons
		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_out_button).setOnClickListener(this);
		
		//initialize fields
		roomManager = new RoomManager(this,getGamesClient());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startGame(View view) {
		Intent intent = new Intent(this, MatchActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSignInFailed() {
		// Sign in has failed. So show the user the sign-in button.
	    findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	    findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	}

	@Override
	public void onSignInSucceeded() {
		// show sign-out button, hide the sign-in button
	    findViewById(R.id.sign_in_button).setVisibility(View.GONE);
	    findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

	    // Set InvitationListener to accept invitations during gameplay
	    getGamesClient().registerInvitationListener(this);
	    
	    // handle possible invitations
	    if (getInvitationId() != null) {
	        Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
	        roomConfigBuilder.setInvitationIdToAccept(getInvitationId());
	        getGamesClient().joinRoom(roomConfigBuilder.build());

	        // prevent screen from sleeping during handshake
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        // TODO Clear flag after game
	        // TODO go to game screen
	    }

	    // (your code here: update UI, enable functionality that depends on sign in, etc)
		
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.sign_in_button) {
	        // start the asynchronous sign in flow
	        beginUserInitiatedSignIn();
	    }
	    else if (view.getId() == R.id.sign_out_button) {
	        // sign out.
	        signOut();

	        // show sign-in button, hide the sign-out button
	        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
	        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
	    }		
	}
	
	private void startQuickGame() {
	    // automatch criteria to invite 1 random automatch opponent.  
	    // You can also specify more opponents (up to 3). 
	    Bundle am = RoomConfig.createAutoMatchCriteria(1, 1, 0);

	    // build the room config:
	    RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
	    roomConfigBuilder.setAutoMatchCriteria(am);
	    RoomConfig roomConfig = roomConfigBuilder.build();

	    // create room:
	    getGamesClient().createRoom(roomConfig);

	    // prevent screen from sleeping during handshake
	    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	    // go to game screen
	}

	private Builder makeBasicRoomConfigBuilder() {
		networkManager = new NetworkManager(getGamesClient(), "0", "0");
		RoomConfig.Builder rtmConfigBuilder = RoomConfig.builder(roomManager);
        rtmConfigBuilder.setMessageReceivedListener(networkManager);
        rtmConfigBuilder.setRoomStatusUpdateListener(roomManager);
        //rtmConfigBuilder.setRoomStatusUpdateListener(this);
        
        return rtmConfigBuilder;
	}

	
	@Override
	public void onInvitationReceived(Invitation arg0) {
		// TODO Implement to handle invitiations during gameplay
	}

	public void showInvitationBox()
	{
		// launch the intent to show the invitation inbox screen
		Intent intent = getGamesClient().getInvitationInboxIntent();
		startActivityForResult(intent, RC_INVITATION_INBOX);
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
	    if (request == RC_INVITATION_INBOX) {
	        if (response != Activity.RESULT_OK) {
	            // canceled
	            return;
	        }

	        // get the selected invitation
	        Bundle extras = data.getExtras();
	        Invitation invitation =
	            extras.getParcelable(GamesClient.EXTRA_INVITATION);

	        // accept it!
	        RoomConfig roomConfig = makeBasicRoomConfigBuilder()
	                .setInvitationIdToAccept(invitation.getInvitationId())
	                .build();
	        getGamesClient().joinRoom(roomConfig);
	        
	        // prevent screen from sleeping during handshake
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

	        // TODO go to game screen
	    }
	    
	    else if (request == RC_SELECT_PLAYERS) {
	        if (response != Activity.RESULT_OK) {
	            // user canceled
	            return;
	        }

	        // get the invitee list
	        Bundle extras = data.getExtras();
	        final ArrayList<String> invitees =
	            data.getStringArrayListExtra(GamesClient.EXTRA_PLAYERS);

	        // get automatch criteria
	        Bundle autoMatchCriteria = null;
	        int minAutoMatchPlayers =
	            data.getIntExtra(GamesClient.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
	        int maxAutoMatchPlayers =
	            data.getIntExtra(GamesClient.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);

	        if (minAutoMatchPlayers > 0) {
	            autoMatchCriteria =
	                RoomConfig.createAutoMatchCriteria(
	                    minAutoMatchPlayers, maxAutoMatchPlayers, 0);
	        } else {
	            autoMatchCriteria = null;
	        }

	        // create the room and specify a variant if appropriate
	        RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
	        roomConfigBuilder.addPlayersToInvite(invitees);
	        if (autoMatchCriteria != null) {
	            roomConfigBuilder.setAutoMatchCriteria(autoMatchCriteria);
	        }
	        RoomConfig roomConfig = roomConfigBuilder.build();
	        getGamesClient().createRoom(roomConfig);

	        // prevent screen from sleeping during handshake
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    }
	    
	    else if (request == RoomManager.RC_WAITING_ROOM) {
	        if (response == Activity.RESULT_OK) {
	            // (start game)
	        }
	        else if (response == Activity.RESULT_CANCELED) {
	            // Waiting room was dismissed with the back button. The meaning of this
	            // action is up to the game. You may choose to leave the room and cancel the
	            // match, or do something else like minimize the waiting room and
	            // continue to connect in the background.

	            // in this example, we take the simple approach and just leave the room:
	            roomManager.leaveRoom();
	            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        }
	        else if (response == GamesActivityResultCodes.RESULT_LEFT_ROOM) {
	            // player wants to leave the room.
	            roomManager.leaveRoom();
	            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        }
	    }


	}
	
	public void invitePlayers() {
		// launch the player selection screen
		// minimum: 1 other player; maximum: 1 other players
		Intent intent = getGamesClient().getSelectPlayersIntent(1, 1);
		startActivityForResult(intent, RC_SELECT_PLAYERS);

	}


}
