package edu.hsbremen.mobile.viergewinnt.logic;

public interface GameLogic {

	void startGame();
	
	GameState getGameState();
	
	void placeToken(int row);
	
	Token[][] getGamefield();
	
	Token getWinner();
	
}
