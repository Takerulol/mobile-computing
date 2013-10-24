package edu.hsbremen.mobile.viergewinnt.logic;

public interface GameLogic {

	void startGame();
	
	GameState getGameState();
	
	void placeToken(int row) throws IllegalStateException;
	
	Token[][] getGamefield();
	
	Token getWinner();
	
}
