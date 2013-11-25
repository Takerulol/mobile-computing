package edu.hsbremen.mobile.viergewinnt;

import com.google.example.games.basegameutils.BaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;

public class MainActivity extends BaseGameActivity 
		implements MatchFragment.Listener, MainMenuFragment.Listener {

	private MainMenuFragment mainMenuFragment;
	private MatchFragment matchFragment;

	final int RC_RESOLVE = 5000, RC_UNUSED = 5001;
	
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
	}

	@Override
	public void onMultiplayerGame() {
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

}
