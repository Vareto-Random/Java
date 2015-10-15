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
/*
 * Player1
 */
/**
 * This player uses a variation of Minimax algorithm to pick balance game
 * states. Both Min and Max try to minimise their score, and Evaluation function
 * tries to return a score as close to zero as possible. *
 * 
 */
public class MiniMaxPlayer_1 extends AbstractPlayer {

	private Random rng = new Random();
	private int P1RunningTotal = 0;
	private int P2RunningTotal = 0;
	private int turn = 0;
	private int firstAIRT;
	private int[] last_move;

	/**
	 * This method gets the human player's last move value, or if it is played
	 * against another AI the opponent's last move value and updates the Running
	 * total of that player.
	 * 
	 * @param game
	 *            game state
	 */
	public void updateHumanRunningTotal(Game game) {

		if (playerID == 1) {
			// gets the last move the other player to update the running total
			last_move = ((ConnectFour) game).getP2lastMove();
			P2RunningTotal += evalPosition(game, last_move[0], last_move[1], 2);
//			System.out.println("P2RunningTotal Human Running Total:"
//					+ P2RunningTotal);
		} else {
			last_move = ((ConnectFour) game).getP1lastMove();
			P1RunningTotal += evalPosition(game, last_move[0], last_move[1], 1);
//			System.out.println("P1RunningTotal Human Running Total:"
//					+ P1RunningTotal);
		}
	}

	@Override
	public Move getMove(Game game) {
		playerID = game.playerID();
		firstAIRT = 0;
		/*
		 * if the AI player is the first player and it is the first turn, AI
		 * starts the game by choosing a move randomly
		 */
		if (turn == 0 && game.playerID() == 1) {
			Connect4Move move = new Connect4Move(rng.nextInt(7));
			int x = move.column;
			int y = ((ConnectFour) game).getColumn(x).size();
			firstAIRT = evalPosition(game, x, y, playerID);
			P1RunningTotal += firstAIRT;
//			System.out.println("First AI RT: " + firstAIRT);
			turn++;
			return move;
		}

		List<Move> goodMoves = new LinkedList<Move>();
		List<Move> blockingMoves = new LinkedList<Move>();
		List<Move> safeMoves = new LinkedList<Move>();
		List<Move> dangerousMoves = new LinkedList<Move>();
		/*
		 * There are two nested loops, the first loop makes a move for the AI player
		 * and the next loop makes a move to the human player. This allows to check
		 * all the combination of AI and human player moves, thus allowing to check
		 * whether human player can win in its next move.
		 */
		for (Move myMove : game.getAvailableMoves()) {
			if (interupted) {
				return null;
			}
			game.makeMove(myMove); // AI makes a move
			/*
			 * First checks if there is win for AI, if there is any winning
			 * moves takes it
			 */
			if (game.isWon()) {
				game.undo(); // AI move undone
				return myMove;
			}
			/* makes the human player move */
			for (Move hisMove : game.getAvailableMoves()) {
				if (interupted) {
					return null;
				}
				game.makeMove(hisMove); // Human makes a move
				/* if the human player move is the winning move */
				if (game.isWon()) {
					/*
					 * if both AI and human moves are the same it means that the
					 * AI players move allowed the human player to win therefore
					 * we add them to the dangerous moves, otherwise we add them
					 * to the blocking moves to prevent human player from
					 * winning
					 */
					if (hisMove != myMove) {
						if (!blockingMoves.contains(hisMove))
							blockingMoves.add(hisMove);
					} else {
						if (!dangerousMoves.contains(myMove))
							dangerousMoves.add(myMove);
					}
				}
				game.undo(); // Human move undone
			}
			game.undo(); // AI move undone
		}

		/*
		 * if the blocking move is empty it means the opponent(human) cannot win
		 * in the next turn, therefore all the available moves are safe moves
		 */
		if (blockingMoves.isEmpty()) {
			safeMoves = game.getAvailableMoves();
			/* if there are dangerous moves, we remove them from safe moves */
			for (Move m : dangerousMoves) {
				safeMoves.remove(m);
			}
		} else {
			safeMoves = blockingMoves;
		}
		/*
		 * AI lost the game, because all the available moves are dangerous so it
		 * must choose one of the dangerous moves to complete the game
		 */
		if (safeMoves.isEmpty()) {
			return game.getAvailableMoves().get(0);
		}

		if (game.playerID() == 1) {
			double best = Double.POSITIVE_INFINITY;
			for (Move move : safeMoves) {
				if (interupted) {
					return null;
				}
				game.makeMove(move);
				double result = Math.abs(min(game, Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY, 5));
//				System.out.println(move.toString() + " min ... " + result);
				if (result < best) {
					best = result;
					goodMoves.clear();
					goodMoves.add(move);
				} else if (result == best) {
					goodMoves.add(move);
				}
				game.undo();
			}

		} else {
			double best = Double.POSITIVE_INFINITY;
			for (Move move : safeMoves) {
				if (interupted) {
					return null;
				}
				game.makeMove(move);
				double result = Math.abs(max(game, Double.NEGATIVE_INFINITY,
						Double.POSITIVE_INFINITY, 5));
//				System.out.println(move.toString() + " max... " + result);
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
		Connect4Move actualMove = (Connect4Move) goodMoves.get(rng
				.nextInt(goodMoves.size()));
		/* Update the AI running total */
		int x = actualMove.column;
		int y = ((ConnectFour) game).getColumn(x).size();
		if (playerID == 1) {
			P1RunningTotal += evalPosition(game, x, y, playerID);
//			System.out
//					.println("P1RunningTotal AI runningTotal getting updated: "
//							+ P1RunningTotal + "||" + playerID);

		} else {
			P2RunningTotal += evalPosition(game, x, y, playerID);
//			System.out
//					.println("P2RunningTotal AI runningTotal getting updated: "
//							+ P2RunningTotal + "||" + playerID);
		}
		updateHumanRunningTotal(game);
//		System.out.println("Going " + actualMove);
		turn++;

		if (interupted) {
			return null;
		}
		return actualMove;
	}

	private double max(Game game, double a, double b, int depth) {
		if (interupted) {
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
			// System.out.println("max returning " + eval(game));
			return eval(game);
		}

		for (Move move : moves) {
			if (interupted) {
				return 0;
			}

			int x = ((Connect4Move) move).column;
			int y = ((ConnectFour) game).getColumn(x).size();
			int movevalue = evalPosition(game, x, y, playerID);
			if (game.playerID() == 1) {
				P1RunningTotal = P1RunningTotal + movevalue;
			} else {
				P2RunningTotal = P2RunningTotal + movevalue;
			}
			game.makeMove(move);

			a = Math.max(a, min(game, a, b, depth - 1));
			game.undo();
			if (game.playerID() == 1) {
				P1RunningTotal = P1RunningTotal - movevalue;
			} else {
				P2RunningTotal = P2RunningTotal - movevalue;
			}

			if (b <= a)
				break;
		}
		return a;
	}

	private double min(Game game, double a, double b, int depth) {
		if (interupted) {
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
			// System.out.println("min returning " + eval(game));
			return eval(game);
		}

		for (Move move : moves) {
			if (interupted) {
				return 0;
			}

			int x = ((Connect4Move) move).column;
			int y = ((ConnectFour) game).getColumn(x).size();
			int movevalue = evalPosition(game, x, y, playerID);
			if (game.playerID() == 1) {
				P1RunningTotal = P1RunningTotal + movevalue;
			} else {
				P2RunningTotal = P2RunningTotal + movevalue;
			}
			game.makeMove(move);
			b = Math.min(b, max(game, a, b, depth - 1));
			game.undo();

			if (game.playerID() == 1) {
				P1RunningTotal = P1RunningTotal - movevalue;
			} else {
				P2RunningTotal = P2RunningTotal - movevalue;
			}
			if (b <= a)
				break;
		}
		return b;
	}

	// private int[][] updateValues(Game game, int pID) {
	// int[][] playerUpdatedGridVal = new int[7][6];
	// for (int x = 0; x < playerUpdatedGridVal.length; x++) {
	// for (int y = 0; y < playerUpdatedGridVal[0].length; y++) {
	// playerUpdatedGridVal[x][y] = evalPosition(game, x, y, pID);
	// }
	// }
	// return playerUpdatedGridVal;
	// }
	
	/**
	 * This method is the evaluation function that is gets called
	 * when the depth limit is reached.
	 *  
	 * @param game Game state
	 * @return the difference of the player's running totals
	 */
	private double eval(Game game) {
		double difference = 0;
		if (game.playerID() == 1) {
			difference = (Math.abs(P1RunningTotal - P2RunningTotal));
		} else {
			difference = (Math.abs(P2RunningTotal - P1RunningTotal));
		}

		return difference;
		/****************/
		// int[][] gridP1 = updateValues(game, 1);
		// int[][] gridP2 = updateValues(game, 2);
		// double difference = 0;
		// for (int i = 0; i < gridP1.length; i++) {
		// for (int j = 0; j < gridP2[0].length; j++) {
		// if (game.playerID()==1){
		// difference = Math.abs( gridP1[i][j] - gridP2[i][j]);
		// }else{
		// difference = Math.abs( gridP2[i][j] - gridP1[i][j]);
		// }
		// }
		// }
		// return difference/80;
	}
	/**
	 * Evaluates a value of a position in the board for each 4 in a row 
	 * horizontally, vertically and diagonally
	 * 
	 * @param game Game state
	 * @param x The X position in the grid
	 * @param y The Y position in the grid
	 * @param pID player ID
	 * @return the value of a position
	 */
	public int evalPosition(Game game, int x, int y, int pID) {
		int value = 0;
		int[][] grid = ((ConnectFour) game).getGridAsMatrix();

		// System.out.printf("*************** HORIZONTAL (%02d,%02d)***************\n",
		// x, y);

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
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
					} else {
						free = false;
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
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
			// System.out.printf("Value: %02d\n", value);
		}

		// System.out.printf("*************** VERTICAL (%02d,%02d)***************\n",
		// x, y);
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
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);

					} else {
						free = false;
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);

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
			// System.out.printf("Value: %02d\n", value);
		}

		// System.out.printf("*************** MAIN DIAGONAL (%02d,%02d) %02d***************\n",
		// x, y, value);
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
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
					} else {
						free = false;
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
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
			// System.out.printf("Value: %02d\n", value);
		}

		// System.out.printf("*************** SECOND DIAGONAL (%02d,%02d)***************\n",
		// x, y);
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = (x + i) + j;
				int b = (y - i) - j;
				// System.out.printf("[%02d,%02d] ", a, b);
				if ((a >= 0 && a <= 6) && (b >= 0 && b <= 5)) {
					if (grid[a][b] == pID || grid[a][b] == 0) {
						free = true;
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
					} else {
						free = false;
						// System.out.printf("(%02d,%02d) = %01d -> %b\n", a, b,
						// grid[a][b], free);
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
			// System.out.printf("Value: %02d\n", value);

		}
		return value;
	}

	@Override
	public String getName() {
		return "Idea1";
	}

	@Override
	public void reset() {
		super.reset();
		P1RunningTotal = 0;
		P2RunningTotal = 0;
		turn = 0;
		firstAIRT = 0;
	}

}
