package edu.hsbremen.mobile.viergewinnt.googleplay;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


import android.content.Context;

import com.google.android.gms.games.GamesClient;

import edu.hsbremen.mobile.viergewinnt.logic.Achievement;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;

public class PlayAchievements implements AchievementLogic {

	private GamesClient client;
	private Context context;
	private List<Achievement> incrementalAchievements;
	
	/**
	 * 
	 * @param client GamesClient instance used to unlock achievements.
	 * @param context Android context used to access the resources. Pass down with getContext() or getBaseContext().
	 */
	public PlayAchievements(GamesClient client, Context context) {
		this.client = client;
		this.context = context;
		incrementalAchievements = initIncrementalAchievements();
	}
	
	/**
	 * Initializes a list containing incremental achievements.
	 * @return
	 */
	private List<Achievement> initIncrementalAchievements()
	{
		List<Achievement> list = new ArrayList<Achievement>();
		list.add(Achievement.FOUR_WINS);
		return list;
	}
	
	
	@Override
	public void unlockAchievement(Achievement achievement) {
		//getGamesClient().unlockAchievement(getString(R.string.achievement_prime));
		if (client.isConnected())
		{
			String id = getAchievementID(achievement);
			
			if (incrementalAchievements.contains(achievement))
			{
				client.incrementAchievement(id, 1);
			}
			else
			{
				client.unlockAchievement(id);
			}
		}
	}

	@Override
	public EnumSet<Achievement> getUnlockedAchievements() {
		// TODO implement functionality
		// Functionality not needed at the moment
		throw new UnsupportedOperationException("Function not implemented.");
	}
	
	/**
	 * Retrieves the achievement id from the resources file.
	 * @param achievement
	 * @return
	 */
	private String getAchievementID(Achievement achievement)
	{
		String id = ""; 
		
		switch (achievement)
		{
		case FOUR_WINS:
			id = context.getString(edu.hsbremen.mobile.viergewinnt.R.string.achievement_four_wins);
			break;
		case MULTIKILL:
			id = context.getString(edu.hsbremen.mobile.viergewinnt.R.string.achievement_multikill);
			break;
		case SIX_IN_A_ROW:
			id = context.getString(edu.hsbremen.mobile.viergewinnt.R.string.achievement_six_in_a_row);
			break;
		case TIE: 
			id = context.getString(edu.hsbremen.mobile.viergewinnt.R.string.achievement_its_a_tie);
			break;
		case WINNER:
			id = context.getString(edu.hsbremen.mobile.viergewinnt.R.string.achievement_winner);
			break;
		}
		
		return id;
	}

}
