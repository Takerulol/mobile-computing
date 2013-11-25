package edu.hsbremen.mobile.viergewinnt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class MainMenuFragment extends Fragment implements OnClickListener {

	public interface Listener {
		public void onMultiplayerGame();
		public void onShowAchievements();
		public void onShowHighscores();
		public void onSignInButton();
		public void onSignOutButton();
		public void onError(String msg);
	}
	
	private Listener listener;
	private boolean showSignIn = true;
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//menu layout auf gelieferten view setzen
		View v = inflater.inflate(R.layout.fragment_mainmenu, container,false);
		
		//Buttons ids, um allen Buttons einen listener zuzuteilen
		final int[] CLICKABLES = new int[] {
			R.id.start_button, R.id.achievement_button,
			R.id.leaderboard_button, R.id.sign_in_button,
			R.id.sign_out_button
		};
		for(int i : CLICKABLES) {
			v.findViewById(i).setOnClickListener(this);
		}
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		//updaten, dass nur sign in oder out sichtbar ist
		updateUi();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_button:
			this.listener.onMultiplayerGame();
			break;

		case R.id.leaderboard_button:
			this.listener.onShowHighscores();
			break;
			
		case R.id.achievement_button:
			this.listener.onShowAchievements();
			break;

		case R.id.sign_in_button:
			this.listener.onSignInButton();
			break;
			
		case R.id.sign_out_button:
			this.listener.onSignOutButton();
			break;
			
		default:
			this.listener.onError("invalid action");
			break;
		}
	}
	
    public void setShowSignInButton(boolean showSignIn) {
        this.showSignIn = showSignIn;
        updateUi();
    }
    
    private void updateUi() {
        if (getActivity() == null) return;
//        TextView tv = (TextView) getActivity().findViewById(R.id.hello);
//        if (tv != null) tv.setText(mGreeting);

        getActivity().findViewById(R.id.sign_in_bar).setVisibility(showSignIn ?
                View.VISIBLE : View.GONE);
        getActivity().findViewById(R.id.sign_out_bar).setVisibility(showSignIn ?
                View.GONE : View.VISIBLE);
    }

}
