package edu.hsbremen.mobile.viergewinnt.logic;

import java.util.EnumSet;

public interface AchievementLogic {

	/**
	 * Sets the given achievement as unlocked.
	 * Increments incrementable achievements.
	 * @param achivement
	 */
	void unlockAchievement(Achievement achivement);
	
	/**
	 * Returns an EnumSet of already unlocked achievements. 
	 * @return Unlocked achievements.
	 */
	EnumSet<Achievement> getUnlockedAchievements();
	
	
	
}
