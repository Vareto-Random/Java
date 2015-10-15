/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package players;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Connect4Move;
import model.ConnectFour;
import model.Game;
import data.Move;
/**
 * 
 * This Player is standard Minimax player which plays
 * optimally aiming to always defeat the human player.
 * This is mainly implemented to compare against the 
 * interesting players. 
 *
 */
public class MiniMaxPlayer extends AbstractPlayer {

	private Random rng = new Random();

	@Override
	public Move getMove(Game game) {
		updateValues(game, playerID);
//		compareValues(game);
		List<Move> goodMoves = new LinkedList<Move>();
		if (game.playerID() == 1) {
			double best = Double.NEGATIVE_INFINITY;			
			for (Move move : game.getAvailableMoves()) {
				if(interupted) {
					return null;
				}
				game.makeMove(move);
				double result = min(game, Double.NEGATIVE_INFINITY,	Double.POSITIVE_INFINITY, 5);
				System.out.println(move.toString() + " ... " + result);
				if (result > best) {
					best = result;
					goodMoves.clear();
					goodMoves.add(move);
					// bestMove = move;
				} else if (result == best) {
					goodMoves.add(move);
				}
				game.undo();

			}
		} else {
			double best = Double.POSITIVE_INFINITY;
			for (Move move : game.getAvailableMoves()) {
				if(interupted) {
					return null;
				}
				game.makeMove(move);
				double result = max(game, Double.NEGATIVE_INFINITY,	Double.POSITIVE_INFINITY, 5);
				System.out.println(move.toString() + " ... " + result);
				if (result < best) {
					best = result;
					goodMoves.clear();
					goodMoves.add(move);
				} else if (result == best) {
					goodMoves.add(move);
				}
				game.undo();
			}
		}
		if (interupted){
			 return null;
		 }
		return goodMoves.get(rng.nextInt(goodMoves.size()));
	}

//	private void compareValues(Game game) {
//		for (int x = 0; x < updatedgridVal.length; x++) {
//			for (int y = 0; y < updatedgridVal[0].length; y++) {
//				System.out.println(x + ", " + y + " = "
//						+ (updatedgridVal[x][y] == gridValues[x][y]));
//			}
//		}
		
		/**************************************************************************/
//		
//		int[][] grid = ((ConnectFour) game).getViewGrid();
//		for (int y = gridValues[0].length - 1; y >= 0; y--) 
//		{
//			for (int x = 0; x < gridValues.length; x++)
//			{
//				System.out.printf("(%d,%d)[%02d->%02d]%02d ", x, y, gridValues[x][y], updatedgridVal[x][y], grid[x][y]);
//			}
//			System.out.printf("\n");
//		}
//	}

	private double max(Game game, double a, double b, int depth) {
		if(interupted) {
			return 0;
		}
		List<Move> moves = game.getAvailableMoves();

		if (game.isWon()) {
			switch (game.getWinner()) {
			case 1:
				return 1;
			case 2:
				return -1;
			default:
				return 0;
			}
		}
		if (depth == 0) {
//			System.out.println("max returning " + eval(game));
			return eval(game);
		}

		for (Move move : moves) {
			if(interupted) {
				return 0;
			}
			game.makeMove(move);
			a = Math.max(a, min(game, a, b, depth - 1));
			game.undo();
			if (b <= a)
				break;
		}
		return a;
	}

	private double min(Game game, double a, double b, int depth) {
		if(interupted) {
			return 0;
		}
		List<Move> moves = game.getAvailableMoves();

		if (game.isWon()) {
			switch (game.getWinner()) {
			case 1:
				return 1;
			case 2:
				return -1;
			default:
				return 0;
			}
		}
		if (depth == 0) {
//			System.out.println("min returning " + eval(game));
			return eval(game);
		}

		for (Move move : moves) {
			if(interupted) {
				return 0;
			}
			game.makeMove(move);
			b = Math.min(b, max(game, a, b, depth - 1));
			game.undo();
			if (b <= a)
				break;
		}
		return b;
	}
	
	/** 
	 * Updates the board values using evalPosition method
	 */
	private int[][] updateValues(Game game, int pID) {
		int[][] playerUpdatedGridVal = new int[7][6];
		for (int x = 0; x < playerUpdatedGridVal.length; x++) {
			for (int y = 0; y < playerUpdatedGridVal[0].length; y++) {
				if(interupted) {
					return playerUpdatedGridVal;
				}
				playerUpdatedGridVal[x][y] = evalPosition(game, x, y, pID);
			}
		}
		return playerUpdatedGridVal;
	}
	/**
	 * Evaluation function 
	 * the board values that each player has is stored in grid arrays
	 * gridP1 and gridP2 for player1 and player2 respectively,
	 * then the difference of these two arrays are calculated.
	 * the sum of the elements of the result array will be stored
	 * in the difference attribute and will be returned.
	 */
	private double eval(Game game) {
		int [][] gridP1 = updateValues(game, 1);
		int [][] gridP2 = updateValues(game, 2);
		double difference = 0;
		for(int i=0;i< gridP1.length;i++){
			for(int j=0; j< gridP2[0].length;j++){
				difference += gridP1[i][j] - gridP2[i][j];
			}
		}
		return difference/80;
	}
	/**
	 * Evaluates a value of a position in the board for each 4 in a row 
	 * horizontally, vertically and diagonally.
	 */
	private int evalPosition(Game game, int x, int y, int pID) {
		int value = 0;
		int[][] grid = ((ConnectFour) game).getGridAsMatrix();
		
//		System.out.printf("*************** HORIZONTAL (%02d,%02d)***************\n", x, y);

		// Horizontal
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = x + i + j;
				int b = y;
				if (a >= 0 && a <= 6) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
					} else {
						free = false;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
						break;
					}
				} else {
					free = false;
					break;
				}
			}
			if (free) {
				value++;

			}
//			System.out.printf("Value: %02d\n", value);
		}

//		System.out.printf("*************** VERTICAL (%02d,%02d)***************\n", x, y);
		// vertical
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = x;
				int b = y + i + j;
				if (b >= 0 && b <= 5) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);

					} else {
						free = false;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);

						break;
					}
				} else {
					free = false;
					break;
				}

			}
			if (free) {
				value++;

			}
//			System.out.printf("Value: %02d\n", value);
		}

//		System.out.printf("*************** MAIN DIAGONAL (%02d,%02d) %02d***************\n", x, y, value);
		// Diagonal
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = x + i + j;
				int b = y + i + j;
				if ((a >= 0 && a <= 6) && (b >= 0 && b <= 5)) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
					} else {
						free = false;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
						break;
					}

				} else {
					free = false;
					break;
				}
			}
			if (free) {
				value++;

			}
//			System.out.printf("Value: %02d\n", value);
		}
		
//		System.out.printf("*************** SECOND DIAGONAL (%02d,%02d)***************\n", x, y);
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = (x + i) + j;
				int b = (y - i) - j;
//				System.out.printf("[%02d,%02d] ", a, b);
				if ((a >= 0 && a <= 6) && (b >= 0 && b <= 5)) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
					} else {
						free = false;
//						System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b, grid[a][b], free);
						break;
					}

				} else {
					free = false;
					break;
				}
			}
			if (free) {
				value++;

			}
//			System.out.printf("Value: %02d\n", value);
			
		}
		return value;

	}

	@Override
	public String getName() {
		return "MiniMax Player";
	}
	
	@Override
	public void reset(){
		super.reset();
		
	}
	
	
	
}
