package edu.hsbremen.mobile.viergewinnt;

import java.util.ArrayList;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.example.games.basegameutils.BaseGameActivity;

import edu.hsbremen.mobile.viergewinnt.googleplay.InvitationManager;
import edu.hsbremen.mobile.viergewinnt.googleplay.NetworkManager;
import edu.hsbremen.mobile.viergewinnt.googleplay.PlayAchievements;
import edu.hsbremen.mobile.viergewinnt.googleplay.RemoteGameLogic;
import edu.hsbremen.mobile.viergewinnt.googleplay.RoomManager;
import edu.hsbremen.mobile.viergewinnt.googleplay.RoomManager.Listener;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementProxy;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.Token;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseGameActivity 
		implements MatchFragment.Listener, MainMenuFragment.Listener, RoomManager.Listener,
    {

	private MainMenuFragment mainMenuFragment;
	private MatchFragment matchFragment;

	final int RC_RESOLVE = 5000, RC_UNUSED = 5001;
	final static int RC_INVITATION_INBOX = 10001, RC_SELECT_PLAYERS = 10000;

	private RoomManager roomManager;
	private InvitationManager invitationManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.mainMenuFragment = new MainMenuFragment();
		this.matchFragment = new MatchFragment();
		
		this.mainMenuFragment.setListener(this);
		this.matchFragment.setListener(this);
		
		getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
				this.mainMenuFragment).commit();
		
//		if(this.getGamesClient().getCurrentPlayer() == null) {
//			this.mainMenuFragment.setShowSignInButton(true);
//		} else {
//			this.mainMenuFragment.setShowSignInButton(false);
//		}
		
	}
	
	private void initializeHelperClasses()
	{
		//google play initializations
		this.roomManager = new RoomManager(this,getGamesClient());
		this.roomManager.addListener(this);
		this.invitationManager = new InvitationManager(this,getGamesClient(),this.roomManager);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public void onSignInFailed() {
		this.mainMenuFragment.setShowSignInButton(true);
	}

	@Override
	public void onSignInSucceeded() {
		this.mainMenuFragment.setShowSignInButton(false);
		
		initializeHelperClasses();
		//handle possible invitations
		this.invitationManager.handleInvitation(getInvitationId());
	}

	@Override
	public void onMultiplayerGame() {
		GameLogic logic = new GameLogicImpl();
		if (isSignedIn())
		{
			//provide achievement support
			AchievementLogic al = new PlayAchievements(getGamesClient(),getBaseContext());
			logic = new AchievementProxy(logic,al);
		}
		
		this.matchFragment.setLogic(logic);
		switchToFragment(this.matchFragment);
		//TODO: implement me further	
	}
	
	void switchToFragment(Fragment newFrag) {
		 getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newFrag)
         .commit();
    }

	@Override
	public void onShowAchievements() {
		if (isSignedIn()) {
            startActivityForResult(getGamesClient().getAchievementsIntent(), RC_UNUSED);
        } else {
        	showAlert(getString(R.string.not_logged_in));
        }
	}

	@Override
	public void onShowHighscores() {
		if (isSignedIn()) {
            startActivityForResult(getGamesClient().getAllLeaderboardsIntent(), RC_UNUSED);
        } else {
            showAlert(getString(R.string.not_logged_in));
        }
	}

	@Override
	public void onSignInButton() {
		beginUserInitiatedSignIn();
	}

	@Override
	public void onSignOutButton() {
		signOut();
		this.mainMenuFragment.setShowSignInButton(true);
//        mMainMenuFragment.setGreeting(getString(R.string.signed_out_greeting));
//        mMainMenuFragment.setShowSignInButton(true);
	}

	@Override
	public void onError(String msg) {
		showAlert(msg);
	}

	@Override
	public void onWinnerFound(String msg) {
		switchToFragment(this.mainMenuFragment);
		showAlert(msg);
	}

	@Override
	public void onShowInvitations() {		
		if (isSignedIn()) {
			Intent intent = getGamesClient().getInvitationInboxIntent();
			this.startActivityForResult(intent, RC_INVITATION_INBOX);	
        } else {
            showAlert(getString(R.string.not_logged_in));
        }
	}

	@Override
	public void onQuickGame() {
		if (isSignedIn()) {
			this.roomManager.startQuickGame();	
        } else {
            showAlert(getString(R.string.not_logged_in));
        }
		
	}

	@Override
	public void onInvitePlayers() {
		if (isSignedIn()) {
			Intent intent = getGamesClient().getSelectPlayersIntent(1, 3);
			startActivityForResult(intent, RC_SELECT_PLAYERS);
        } else {
            showAlert(getString(R.string.not_logged_in));
        }
		
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
	        this.invitationManager.handleInvitation(invitation.getInvitationId());
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

	        this.roomManager.handleAutoMatching(invitees);
	    }

	}

	@Override
	public void onStartMultiplayerGame(boolean firstPlayer) {
		if (isSignedIn()) {
			
			//initialize game logic with achievement support 
			// TODO achievement logic depending on token
			GameLogic logic = new GameLogicImpl();
			AchievementLogic al = new PlayAchievements(getGamesClient(),getBaseContext());
			logic = new AchievementProxy(logic,al);
			
			//Red or Blue token?
			Token localToken = Token.None;
			if (firstPlayer) {
				localToken = Token.Red;
			} else {
				localToken = Token.Blue;
			}
			
			//initialize network manager and remote logic
			NetworkManager manager = new NetworkManager
					(getGamesClient(), roomManager.getRoomId(), roomManager.getParticipantId());
			logic = new RemoteGameLogic(localToken, manager);
			
			//start game
			this.matchFragment.setLogic(logic);
			switchToFragment(this.matchFragment);
		}
		else {
			showAlert(getString(R.string.not_logged_in));
		}
	}
	
}
