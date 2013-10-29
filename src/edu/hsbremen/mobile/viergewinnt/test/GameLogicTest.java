package edu.hsbremen.mobile.viergewinnt.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.hsbremen.mobile.viergewinnt.logic.AchievementLogic;
import edu.hsbremen.mobile.viergewinnt.logic.AchievementProxy;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;
import edu.hsbremen.mobile.viergewinnt.logic.GameState;
import edu.hsbremen.mobile.viergewinnt.logic.Token;

public class GameLogicTest {

	private GameLogic logic;
	
	@Before
	public void setup()
	{
		//logic = new GameLogicImpl(); 
		
		AchievementLogic al = new AchievementLogicMock();
		logic = new AchievementProxy(new GameLogicImpl(), al);
		logic.startGame();	
	}
	
	@Test
	public void gameStateTest() {
		GameLogic gamelogic = new GameLogicImpl();
		assertEquals(gamelogic.getGameState(), GameState.INITIALIZED);
		
		gamelogic.startGame();
		assertEquals(gamelogic.getGameState(), GameState.RUNNING);
	}
	
	@Test
	public void placeTokenTest()
	{
		logic.placeToken(1);
		Token token = logic.getGamefield()[1][0];
		assertEquals(token,Token.Red);
	}
	
	@Test
	public void stackTokensTest()
	{
		logic.placeToken(0);
		logic.placeToken(0);
		Token token = logic.getGamefield()[0][1];
		assertEquals(token,Token.Blue);
	}
	
	@Test
	public void rowFullTest()
	{
		for (int i = 0; i < logic.getGamefield()[0].length; i++)
		{
			logic.placeToken(0);
		}
	}
	
	@Test(expected = IllegalStateException.class)
	public void rowFullExceptionTest()
	{
		for (int i = 0; i <= logic.getGamefield()[0].length; i++)
		{
			logic.placeToken(0);
		}
	}
	
	@Test
	public void winnerHorizontalTest()
	{
		//o o o
		//x x x x
		
		logic.placeToken(0);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
		
		assertNoWinner();
		
		logic.placeToken(3);
		
		assertWinner(Token.Red);
	}
	
	@Test
	public void winnerVerticalTest()
	{
		//o
		//o x
		//o x
		//o x x
		
		logic.placeToken(1);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(0);
		logic.placeToken(2);
		
		assertNoWinner();
		
		logic.placeToken(0);
		
		assertWinner(Token.Blue);
	}
	
	@Test
	public void winnerDiagonalTest()
	{
		//      x
		//  o x o
		//  x o x
		//x o x o
		
		logic.placeToken(0);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(1);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(2);
		logic.placeToken(3);
		logic.placeToken(3);
		logic.placeToken(3);
		
		assertNoWinner();
		
		logic.placeToken(3);
		
		assertWinner(Token.Red);
	}
	
	@Test
	public void gamefieldFullTest()
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
		
		assertNoWinner();
		
		logic.placeToken(6);

		assertEquals(logic.getGameState(), GameState.FINISHED);
		assertEquals(logic.getWinner(), Token.None);
		
	}
	
	public void assertNoWinner()
	{
		assertEquals(logic.getWinner(), Token.None);
		assertEquals(logic.getGameState(),GameState.RUNNING);
	}
	
	public void assertWinner(Token winner)
	{
		assertEquals(logic.getWinner(),winner);
		assertEquals(logic.getGameState(),GameState.FINISHED);
	}
}
