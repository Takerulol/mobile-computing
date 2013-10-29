package edu.hsbremen.mobile.viergewinnt.logic;

/**
 * Represents a proxy for the game logic that implements achievement functionality.
 * @author Thorsten
 *
 */
public class AchievementProxy implements GameLogic {

	private GameLogic logic;
	private AchievementLogic al;
	
	public AchievementProxy(GameLogic logic, AchievementLogic al)
	{
		this.logic = logic;
		this.al = al;
	}
	
	@Override
	public void startGame() {
		logic.startGame();
	}

	@Override
	public GameState getGameState() {
		return logic.getGameState();
	}

	@Override
	public void placeToken(int row) throws IllegalStateException {
		logic.placeToken(row);
	}

	@Override
	public Token[][] getGamefield() {
		return logic.getGamefield();
	}

	@Override
	public Token getWinner() {
		return logic.getWinner();
	}

}
