package edu.hsbremen.mobile.viergewinnt.logic;

public class GameLogicImpl implements GameLogic {

	private GameState state;
	private Gamefield gamefield;
	private Token currentToken; 
	
	public final int WIDTH = 7;
	public final int HEIGHT = 6;
	
	public GameLogicImpl()
	{
		state = GameState.INITIALIZED;
		gamefield = new Gamefield(WIDTH,HEIGHT);
		currentToken = Token.Red; //red begins
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
		changeToken();
	}

	@Override
	public Token[][] getGamefield() {
		return gamefield.getField();
	}

	@Override
	public Token getWinner() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void changeToken()
	{
		if (currentToken.equals(Token.Red))
			currentToken = Token.Blue;
		else
			currentToken = Token.Red;
	}

}
