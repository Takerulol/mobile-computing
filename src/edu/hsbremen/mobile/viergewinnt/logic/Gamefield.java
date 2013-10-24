package edu.hsbremen.mobile.viergewinnt.logic;

//Represents the gamefield.
//Is able to place tokens and check for a winner.
/**
 * Represents the gamefield.
 * Is able to place tokens and check for a winner.
 * @author Thorsten
 *
 */
public class Gamefield {
	
	private Token[][] field;
	private int lastMove = 0;
	
	/**
	 * Creates the gamefield.
	 * @param width Width of the gamefield.
	 * @param height Height of the gamefield.
	 */
	public Gamefield(int width, int height) {
		field = new Token[width][height];
		
		//initialize fields with None Tokens.
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				field[x][y] = Token.None;
			}
		}
	}
	
	public Token[][] getField()
	{
		return field;
	}
	
	/***
	 * Places a token into the given row on the gamefield.
	 * @param row
	 * @param token the Token that has to be placed.
	 * @return
	 */
	public void placeToken(int row, Token token) throws IllegalStateException
	{
		int height = getRowHeight(row);
		field[row][height] = token;
	}
	
	/***
	 * Returns the current amount of tokens in a specific row.
	 * @param row
	 * @return
	 */
	private int getRowHeight(int row) throws IllegalStateException
	{
		int height = -1;
		Token currentToken;
		
		do 
		{
			height++;
			
			if (height >= field[row].length)
				throw new IllegalStateException("Row " + row + " is full.");
			
			currentToken = field[row][height];
		}
		while (!currentToken.equals(Token.None));
			
		return height;
	}
	
}
