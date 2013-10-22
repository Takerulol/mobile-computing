package edu.hsbremen.mobile.viergewinnt.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.hsbremen.mobile.viergewinnt.logic.GameLogic;
import edu.hsbremen.mobile.viergewinnt.logic.GameLogicImpl;

public class GameLogicTest {

	private GameLogic logic;
	
	@Before
	public void Setup()
	{
		logic = new GameLogicImpl(); 
		logic.startGame();		
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
