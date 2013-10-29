package edu.hsbremen.mobile.viergewinnt.test;

import java.util.EnumSet;

import edu.hsbremen.mobile.viergewinnt.logic.Achievement;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;

public class AchievementLogicMock implements AchievementLogic {

	private EnumSet<Achievement> unlocked = EnumSet.noneOf(Achievement.class);
	
	@Override
	public void unlockAchievement(Achievement achievement) {
		unlocked.add(achievement);

	}

	@Override
	public EnumSet<Achievement> getUnlockedAchievements() {
		return unlocked;
	}

}
