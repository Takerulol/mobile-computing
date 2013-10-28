package edu.hsbremen.mobile.viergewinnt.logic;

public interface GameLogic {

	/**
	 * Starts a new game and sets the gamestate to RUNNING.
	 * If a game is already in progress, it will be reseted.
	 */
	void startGame();
	
	/**
	 * Retrives the current gamestate.
	 * @return The current gamestate.
	 */
	GameState getGameState();
	
	/**
	 * Places a new token in the given row.
	 * Changes automatically between the red and blue token after each turn.
	 * Starts with the red token.
	 * 
	 * Throws an IllegalStateException when the row is already filled up.
	 * @param row The row in which the token should be placed.
	 * @throws IllegalStateException
	 */
	void placeToken(int row) throws IllegalStateException;
	
	/**
	 * Returns an array representing the current gamefield.
	 * Empty slots are filled with Token.None.
	 * @return The current gamefield.
	 */
	Token[][] getGamefield();
	
	/**
	 * Returns the winner after the game finished.
	 * If the game is not finished yet, the function will return Token.None.
	 * @return The winner of the match.
	 */
	Token getWinner();
	
}
