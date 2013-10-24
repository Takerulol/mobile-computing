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
	private final int WINNING_NUMBER = 4;
	
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
		
		if (height >= field[row].length)
			throw new IllegalStateException("Row " + row + " is full.");
		
		field[row][height] = token;
		lastMove = row;
	}
	
	/***
	 * Returns the current amount of tokens in a specific row.
	 * @param row
	 * @return
	 */
	private int getRowHeight(int row)
	{
		int height = -1;
		Token currentToken;
		
		do 
		{
			height++;
			
			if (height >= field[row].length)
				break; //row is full
			else
				currentToken = field[row][height];
		}
		while (!currentToken.equals(Token.None));
			
		return height;
	}
	
	
	public boolean checkWinner(Token lastToken)
	{
		int x = lastMove;
		int y = getRowHeight(x) -1;
		
		if (checkVertical(lastToken,x,y) >= WINNING_NUMBER)
			return true;
		else if (checkHorizontal(lastToken,x,y) >= WINNING_NUMBER)
			return true;
		else if (checkDiagonalRight(lastToken,x,y) >= WINNING_NUMBER)
			return true;
		else if (checkDiagonalLeft(lastToken,x,y) >= WINNING_NUMBER)
			return true;
		else
			return false;
	}
	
	private int checkVertical(Token token, int x, int y)
	{
		int amount = 1;
		amount += countTokens(token, x, y, 0, 1); //check up
		amount += countTokens(token, x, y, 0, -1); //check down
		return amount;
	}
	
	private int checkHorizontal(Token token, int x, int y)
	{
		int amount = 1;
		amount += countTokens(token, x, y, 1, 0); //check right
		amount += countTokens(token, x, y, -1, 0); //check left
		return amount;
	}
	
	private int checkDiagonalRight(Token token, int x, int y)
	{
		int amount = 1;
		amount += countTokens(token, x, y, 1, 1); //check NO
		amount += countTokens(token, x, y, -1, -1); //check SW
		return amount;
	}
	
	private int checkDiagonalLeft(Token token, int x, int y)
	{
		int amount = 1;
		amount += countTokens(token, x, y, -1, 1); //check NW
		amount += countTokens(token, x, y, 1, -1); //check SE
		return amount;
	}
	
	/**
	 * Checks the number of tokens recursively.
	 * @param token The token that should be checked.
	 * @param x X-Coordinate of the previously checked field.
	 * @param y Y-Coordinate of the previously checked field.
	 * @param dx Delta X (e.g. -1 for going to the left)
	 * @param dy Delta Y (e.g. 1 for going up)
	 * @return The amount of tokens of the same kind in the given direction in a row.
	 */
	private int countTokens(Token token, int x, int y, 
			int dx, int dy)
	{
		//next coordinates
		x += dx;
		y += dy;
		
		//stop conditions
		if (x >= field.length || y >= field[0].length
				|| x < 0  || y < 0)
			return 0; //out of bounds
		
		Token nextToken = field[x][y];
		
		if (!nextToken.equals(token))
			return 0; //different token
		
		//check the next field
		int amount = 1 + countTokens(token, x, y, dx, dy);
		return amount;
	}
	
}
