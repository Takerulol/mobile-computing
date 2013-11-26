package edu.hsbremen.mobile.viergewinnt.googleplay;

import android.view.WindowManager;

import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig.Builder;
import com.google.example.games.basegameutils.BaseGameActivity;

/**
 * This class handles invitations.
 * @author Thorsten
 *
 */
public class InvitationManager implements OnInvitationReceivedListener {

	private BaseGameActivity activity;
	private GamesClient gamesClient;
	private RoomManager roomManager;

	public InvitationManager(BaseGameActivity activity, GamesClient client, RoomManager roomManager)
	{
		this.activity = activity;
		this.gamesClient = client;
		this.gamesClient.registerInvitationListener(this);
		
		this.roomManager = roomManager;
	}
	
	@Override
	public void onInvitationReceived(Invitation arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Handles the invitation. If the invitationId is not null, 
	 * the invitation will be accepted. After that the method will 
	 * join the game room.
	 */
	public void handleInvitation(String InvitationId) {
		if (InvitationId != null)
		{
			this.roomManager.acceptInvitation(InvitationId);
		}
	}

	
}
