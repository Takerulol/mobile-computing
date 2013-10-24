package edu.hsbremen.mobile.viergewinnt.logic;

public class GameLogicImpl implements GameLogic {

	private GameState state;
	private Gamefield gamefield;
	private Token currentToken; 
	private Token winner;
	
	public final int WIDTH = 7;
	public final int HEIGHT = 6;
	
	public GameLogicImpl()
	{
		state = GameState.INITIALIZED;
		gamefield = new Gamefield(WIDTH,HEIGHT);
		currentToken = Token.Red; //red begins
		winner = Token.None;
	}
	
	@Override
	public void startGame() {
		state = GameState.RUNNING;
	}

	@Override
	public GameState getGameState() {
		return state;
	}

	@Override
	public void placeToken(int row) throws IllegalStateException {
		gamefield.placeToken(row, currentToken);
		checkWinner(currentToken);
		changeToken();
	}
	
	private void checkWinner(Token currentToken)
	{
		if (gamefield.checkWinner(currentToken))
		{
			state = GameState.FINISHED;
			winner = currentToken;
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

}
