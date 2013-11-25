package edu.hsbremen.mobile.viergewinnt.test;

import java.util.EnumSet;

import edu.hsbremen.mobile.viergewinnt.logic.Achievement;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;

public class AchievementLogicMock implements AchievementLogic {

	private EnumSet<Achievement> unlocked = EnumSet.noneOf(Achievement.class);
	private int win_counter = 0;
	
	
	@Override
	public void unlockAchievement(Achievement achievement) {
		if (achievement.equals(Achievement.FOUR_WINS))
		{
			win_counter++;
			if (win_counter >= 4)
			{
				unlocked.add(achievement);
			}
		}
		else
		{
			unlocked.add(achievement);
		}

	}

	@Override
	public EnumSet<Achievement> getUnlockedAchievements() {
		return unlocked;
	}

}
