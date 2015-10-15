/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package players;
/**
 * This player uses MiniMax algorithm with alpha-beta pruning.
 * The AI sets a range based on the opponent move and prunes on
 * the range and only runs on the moves that are in the allowed range.
 * It blocks the winning opportunities of the opponent and takes
 * any available winning positions.
 * The difference between Idea5 and Idea4 is that the Idea5 doesn't 
 * detect the dangerous moves. (dangerous moves are the moves that 
 * if the AI do them, will allow the opponent to win in the next move).
 * Therefore the AI  sometimes allows the opponent to win the game.
 */
 

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Connect4Move;
import model.ConnectFour;
import model.Game;
import data.Move;

public class Idea5 extends AbstractPlayer {

	private Random rng = new Random();;
	private int P1RunningTotal = 0;
	private int P2RunningTotal = 0;
	private static final int range = 2;
	private int turn = 0;
	private int firstAIRT;


	/**
	 *  This method finds the moves that are in the allowed
	 *  range and returns them. 
	 */
	private List<Move> prunMoves(Game game, List<Move> safeMoves) {
		int min = 0;// minimum value in the range
		int max = 0;// maximum value in the range
		int[][] grid;
		int[] last_move;
		int myscore;
		List<Move> possibleMoves = new LinkedList<Move>();
		playerID = game.playerID();

		if (playerID == 1) {
			/* gets the last move the other player to update the running total */
			last_move = ((ConnectFour) game).getP2lastMove();
			P2RunningTotal += evalPosition(game, last_move[0], last_move[1], 2);
//			System.out.println("lastMove p2:" + "("+last_move[0]+ ","+ last_move[1]+")" + "Running Total:"+ P2RunningTotal);

			grid = updateValues(game, 1);
			min = P2RunningTotal - range;
			max = P2RunningTotal + range;
			myscore = P1RunningTotal;

		} else {
			last_move = ((ConnectFour) game).getP1lastMove();
			P1RunningTotal += evalPosition(game, last_move[0], last_move[1], 1);
//			System.out.println("lastMove p1:" + "("+last_move[0]+ ","+ last_move[1]+")" + "Running Total:"+ P1RunningTotal);
			grid = updateValues(game, 2);
			min = P1RunningTotal - range;
			max = P1RunningTotal + range;
			myscore = P2RunningTotal;
		}

		/*
		 * checks if the move is in the allowed range if it is in
		 * the allowed range, adds it to the possibleMoves list
		 */
		for (Move move : safeMoves) {
			int x = ((Connect4Move) move).column;
			int y = ((ConnectFour) game).getColumn(x).size();
			int NextScore = grid[x][y] + myscore;
			System.out.println("Position of Move = " + move + ":: "
					+ "Next Score = " + NextScore + ", " + "AIScore = " + myscore
					+ " HumanScore = " + P1RunningTotal);
			if (NextScore <= max && NextScore >= min) {
				possibleMoves.add(move);
			}

		}
		/* if there was no move in the allowed range picks the closest move to the range */
		if (possibleMoves.isEmpty()) {
			int i=1;
			boolean searching =  true;
			while(searching){
				for (Move move : safeMoves) {
					int x = ((Connect4Move) move).column;
					int y = ((ConnectFour) game).getColumn(x).size();
					int value= grid[x][y];
					if (value + myscore == min-i){
						possibleMoves.add(move);
						searching = false;
					}else if (value + myscore == max+i){
						possibleMoves.add(move);
						searching = false;
					}

				}
				i++;
			}
		}
		return possibleMoves;
	}

	@Override
	public Move getMove(Game game) {
		 firstAIRT=0;
		/* 
		 * if the AI player is the first player and it is the first turn
		 * it chooses its move randomly
		 */
		if(turn == 0 && game.playerID()==1){
//			System.out.println("first turn");
			Connect4Move move = new Connect4Move(rng.nextInt(7));
			int x = move.column;
			int y = ((ConnectFour) game).getColumn(x).size();
			firstAIRT = evalPosition(game, x, y, playerID);
			P1RunningTotal += firstAIRT;
//			System.out.println("First AI RT: "+ firstAIRT);
			turn++;
			return move;
		}
		List<Move> goodMoves = new LinkedList<Move>();
		List<Move> blockingMoves = new LinkedList<Move>();
		List<Move> safeMoves = new LinkedList<Move>();
//		List<Move> dangerousMoves = new LinkedList<Move>();
		
		/*
		 * There are two nested loops, the first loop makes a move for the AI player
		 * and the next loop makes a move to the human player. This allows to check
		 * all the combination of AI and human player moves, thus allowing to check
		 * whether human player can win in its next move.
		 */
		for (Move myMove : game.getAvailableMoves()) {	
			if(interupted) {
				return null;
			}
			game.makeMove(myMove); // AI makes a move
			/* First checks if there is win for AI, if there is any winning moves take it */
			if (game.isWon()) {
				game.undo(); // AI move undone
				return myMove;
			}
			/* makes the human player move*/			
			for (Move hisMove : game.getAvailableMoves()) {
				if(interupted) {
					return null;
				}
				game.makeMove(hisMove); // Human makes a move
				/* if the human player move is the winning move */
				if (game.isWon()) {
					/* if the opponent move is a winning move we add them to the 
					 * blocking moves to prevent human player from winning 
					 * */
						if(!blockingMoves.contains(hisMove))
							blockingMoves.add(hisMove);
				}
				game.undo(); //Human move undone
			}
			game.undo(); //AI move undone
		}

		/* 
		 * if the blocking move is empty it means the opponent(human)
		 * cannot win in the next turn, therefore all the available
		 * moves are safe moves.
		 */
		if(blockingMoves.isEmpty()){
			safeMoves = game.getAvailableMoves();
		}else{
			safeMoves = blockingMoves;
		}

		/* Run min and max on the safe moves that are in the allowed range */
		if (game.playerID() == 1) {
			double best = Double.NEGATIVE_INFINITY;
			for (Move move : prunMoves(game, safeMoves)) {
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
			for (Move move : prunMoves(game, safeMoves)) {
				if(interupted) {
					return null;
				}
				game.makeMove(move);
				double result = max(game, Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY, 5);
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
		Connect4Move actualMove = (Connect4Move) goodMoves.get(rng.nextInt(goodMoves.size()));
		/* Update the AI running total*/
		int x = actualMove.column;
		int y = ((ConnectFour) game).getColumn(x).size();
		if (playerID == 1) {
			P1RunningTotal += evalPosition(game, x, y, playerID);
//			System.out.println("AI runningTotal getting updated: " + P1RunningTotal);

		} else {
			P2RunningTotal += evalPosition(game, x, y, playerID);
//			System.out.println("AI runningTotal getting updated: " + P2RunningTotal);
		}
//		System.out.println("Going " + actualMove);
		turn++;
		
		if (interupted){
			 return null;
		 }
		return actualMove;
	}

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
		// eval should be a value between +1 and -1

		int[][] gridP1 = updateValues(game, 1);
		int[][] gridP2 = updateValues(game, 2);
		double difference = 0;
		for (int i = 0; i < gridP1.length; i++) {
			for (int j = 0; j < gridP2[0].length; j++) {
				difference += gridP1[i][j] - gridP2[i][j];
			}
		}
		// divided by 80 to scale the value to be between -1 and +1
		return difference / 80;
	}
	/**
	 * Evaluates a value of a position in the board for each 4 in a row 
	 * horizontally, vertically and diagonally.
	 */
	private int evalPosition(Game game, int x, int y, int pID) {
		int value = 0;
		int[][] grid = ((ConnectFour) game).getGridAsMatrix();

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

					} else {
						free = false;
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

		}
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
					} else {
						free = false;
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
		}
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
					} else {
						free = false;
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
		}
		// second Diagonal
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = (x + i) + j;
				int b = (y - i) - j;

				if ((a >= 0 && a <= 6) && (b >= 0 && b <= 5)) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
					} else {
						free = false;
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
		}
		return value;

	}

	@Override
	public String getName() {
		return "Idea5";
	}

	@Override
	public void reset(){
		super.reset();
		P1RunningTotal = 0;
		P2RunningTotal = 0;
		turn = 0;
		firstAIRT= 0;
	}


}
