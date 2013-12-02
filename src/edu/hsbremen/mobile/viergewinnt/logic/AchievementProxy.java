package edu.hsbremen.mobile.viergewinnt.logic;

/**
 * Represents a proxy for the game logic that implements achievement functionality.
 * @author Thorsten
 *
 */
public class AchievementProxy implements GameLogic {

	private GameLogic logic;
	private AchievementLogic al;
	
	private Token localPlayer = Token.None;
	
	public AchievementProxy(GameLogic logic, AchievementLogic al, Token localPlayer)
	{
		this.logic = logic;
		this.al = al;
		this.localPlayer = localPlayer;
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
		
		//check winner & four wins achievement
		if (getGameState().equals(GameState.FINISHED) && 
				getWinner().equals(localPlayer))
		{
			al.unlockAchievement(Achievement.WINNER);
			al.unlockAchievement(Achievement.FOUR_WINS);
			
			//check six in a row achievement
			if (logic.getGamefieldClass().checkWinner(localPlayer, 6))
			{
				al.unlockAchievement(Achievement.SIX_IN_A_ROW);
			}
			
			//check multikill achievement
			if (logic.getGamefieldClass().checkWinningRows(localPlayer) >= 2)
			{
				al.unlockAchievement(Achievement.MULTIKILL);
			}
		}
		
		//check tie 
		else if (getGameState().equals(GameState.FINISHED) && 
				getWinner().equals(Token.None))
		{
			al.unlockAchievement(Achievement.TIE);
		}
	}

	@Override
	public Token[][] getGamefield() {
		return logic.getGamefield();
	}

	@Override
	public Token getWinner() {
		return logic.getWinner();
	}

	@Override
	public Gamefield getGamefieldClass() {
		return getGamefieldClass();
	}

	@Override
	public void registerListener(Listener listener) {
		this.logic.registerListener(listener);
	}

}
