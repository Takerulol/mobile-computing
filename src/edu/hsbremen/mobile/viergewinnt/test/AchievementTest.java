package edu.hsbremen.mobile.viergewinnt.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.hsbremen.mobile.viergewinnt.logic.Achievement;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.GameState;
import edu.hsbremen.mobile.viergewinnt.logic.Token;

public class AchievementTest {

private GameLogic logic;
private AchievementLogic al;
	
	@Before
	public void setup()
	{
		
	}
	
	@Test
	public void winnerTest()
	{
		//o o o
		//x x x x
				
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
				
		assertNotAchievement(Achievement.WINNER);
				
		logic.placeToken(3);
				
		assertAchievement(Achievement.WINNER);
	}
	
	@Test
	public void reverseWinnerTest()
	{
		//this test assures that no achievement for the blue player will be granted.
		
		//o o o o x
		//x x x o x
		
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(4);
		logic.placeToken(3);
		logic.placeToken(4);
		logic.placeToken(3);
		
		assertEquals(GameState.FINISHED, logic.getGameState());
		assertNotAchievement(Achievement.WINNER);
	}
	
	@Test
	public void fourWinsTest()
	{
		for (int i = 0; i < 3; i++)
		{
			winGame();
			logic.startGame();
		}
		
		assertNotAchievement(Achievement.FOUR_WINS);
		
		winGame();
		
		assertAchievement(Achievement.FOUR_WINS);
	}
	
	@Test
	public void multikillTest()
	{
		//       x
		//     x x o
		//   x o x o
		// x o o x o
		
		logic.placeToken(0);
		logic.placeToken(1);
		
		logic.placeToken(1);
		logic.placeToken(2);
		
		logic.placeToken(3);
		logic.placeToken(2);
		
		logic.placeToken(2);
		logic.placeToken(4);
		
		logic.placeToken(3);
		logic.placeToken(4);
		
		logic.placeToken(3);
		logic.placeToken(4);
		
		assertNotAchievement(Achievement.MULTIKILL);
		
		logic.placeToken(3);
		
		assertAchievement(Achievement.MULTIKILL);
	}
	
	@Test
	public void sixTest()
	{
		// o o o   o o
		// x x x x x x
		
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(4);
		logic.placeToken(4);
		logic.placeToken(5);
		logic.placeToken(5);
		
		assertNotAchievement(Achievement.SIX_IN_A_ROW);
		
		logic.placeToken(3);
		
		assertAchievement(Achievement.SIX_IN_A_ROW);
		
	}
	
	@Test
	public void tieTest()
	{
		// o o o x o o x
		// x x x o x x o
		// o o o x o o x
		// x x x o x x o 
		// o o o x o o x
		// x x x o x x o

		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(0);
		
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(1);
		
		logic.placeToken(4);
		logic.placeToken(4);
		logic.placeToken(4);
		logic.placeToken(4);
		logic.placeToken(4);
		logic.placeToken(4);
		
		logic.placeToken(5);
		logic.placeToken(5);
		logic.placeToken(5);
		logic.placeToken(5);
		logic.placeToken(5);
		logic.placeToken(5);
		
		logic.placeToken(2);
		logic.placeToken(3);
		logic.placeToken(3);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(3);
		
		logic.placeToken(3);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(3);
		logic.placeToken(3);
		logic.placeToken(2);
		
		logic.placeToken(6);
		logic.placeToken(6);
		logic.placeToken(6);
		logic.placeToken(6);
		logic.placeToken(6);
		
		assertNotAchievement(Achievement.TIE);
		
		logic.placeToken(6);

		assertEquals(logic.getGameState(), GameState.FINISHED);
		assertEquals(logic.getWinner(), Token.None);
		assertAchievement(Achievement.TIE);
	}
	
	
	private void winGame()
	{
		//o o o
		//x x x x
						
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(3);
	}
	

	private void assertAchievement(Achievement achievement) {
		assertTrue(al.getUnlockedAchievements().contains(achievement));
		
	}

	private void assertNotAchievement(Achievement achievement) {
		assertFalse(al.getUnlockedAchievements().contains(achievement));
		
	}

}
