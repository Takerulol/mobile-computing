package edu.hsbremen.mobile.viergewinnt.logic;

public class GameLogicImpl implements GameLogic {

	private GameState state;
	private Gamefield gamefield;
	protected Token currentToken; 
	private Token winner;
	private Listener listener;
	
	static public final int WIDTH = 7;
	static public final int HEIGHT = 6;
	
	public GameLogicImpl()
	{
		state = GameState.INITIALIZED;
		resetGamefield();
	}
	
	@Override
	public void startGame() {
		resetGamefield();
		state = GameState.RUNNING;
	}
	
	private void resetGamefield()
	{
		gamefield = new Gamefield(WIDTH,HEIGHT);
		currentToken = Token.Red; //red begins
		winner = Token.None;
	}

	@Override
	public GameState getGameState() {
		return state;
	}

	@Override
	public void placeToken(int row) throws IllegalStateException {
		if (state.equals(GameState.RUNNING))
		{
			gamefield.placeToken(row, currentToken);
			checkWinner(currentToken);
			checkFull();
			changeToken();
			this.listener.onGamefieldUpdated();
		}
		else
		{
			throw new IllegalStateException("The game is not running.");
		}
	}
	
	private void checkWinner(Token currentToken)
	{
		if (gamefield.checkWinner(currentToken))
		{
			state = GameState.FINISHED;
			winner = currentToken;
		}
	}
	
	/**
	 * Checks whether or not the gamefield is full.
	 * Changes gamestate to FINISHED if full.
	 */
	private void checkFull()
	{
		if (gamefield.checkFull() 
				&& state.equals(GameState.RUNNING))
		{
			state = GameState.FINISHED;
		}
	}

	@Override
	public Token[][] getGamefield() {
		return gamefield.getField();
	}

	@Override
	public Token getWinner() {
		return winner; 
	}
	
	private void changeToken()
	{
		if (currentToken.equals(Token.Red))
			currentToken = Token.Blue;
		else
			currentToken = Token.Red;
	}

	@Override
	public Gamefield getGamefieldClass() {
		return gamefield;
	}

	@Override
	public void registerListener(Listener listener) {
		this.listener = listener;
		
	}

}
