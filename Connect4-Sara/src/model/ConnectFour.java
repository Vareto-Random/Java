/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package model;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.swing.JComponent;
import controller.GameMouseListener;
import data.Move;

public class ConnectFour implements Game {

	private boolean isWon;
	private int playerID, winner;
	private Stack<Integer> undoStack;
	private List<Move> availableColumns;
	private List<Integer>[] columns;
	private int nMovesRemaining;
	private GameMouseListener ml;
	private int[] p1lastMove, p2lastMove;//

	/**
	 * The constructor initialise all the fields of the class and finds which
	 * column is selected by creating a new instance of GameMouseListener class
	 * as an anonymous class and overriding mousePressed method that finds which
	 * column is clicked by the user.
	 */
	@SuppressWarnings("unchecked")
	public ConnectFour() {
		isWon = false;
		playerID = 1;
		undoStack = new Stack<Integer>();
		availableColumns = new ArrayList<Move>(7);
		columns = (LinkedList<Integer>[]) new LinkedList[7];
		p1lastMove = new int[2];//
		p2lastMove = new int[2];//
		p1lastMove[0] = -1;
		p1lastMove[1] = -1;
		p2lastMove[0] = -1;
		p2lastMove[1] = -1;
		for (int i = 0; i < 7; i++) {
			availableColumns.add(new Connect4Move(i));
			columns[i] = new LinkedList<Integer>();
		}
		nMovesRemaining = 42;
		ml = new GameMouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
//				System.out.println("C4 mouse pressed");
				if(e.getButton() == MouseEvent.BUTTON1){
				int px = e.getX();
				int width = ((JComponent) (e.getSource())).getWidth() / 7;
				this.move = new Connect4Move(px / width);
//				System.out.println(this.move);
				}
			}
		};

		// view.addGameMouseListener(ml);
	}

	@Override
	public boolean makeMove(Move move) {
		// System.out.println("Move: " + (Connect4Move)move);
		if (availableColumns.contains(move)) {
			int column = ((Connect4Move) move).column;
			/* to find the last move of the other player */
			int x = column;//
			int y = columns[column].size();//
			if (playerID == 1) {//
				p1lastMove[0] = x;//
				p1lastMove[1] = y;//
			} else {//
				p2lastMove[0] = x;//
				p2lastMove[1] = y;//
			}
			// System.out.printf("Move: %1d->(%1d,  %1d)\n", playerID, x, y);

			columns[column].add(playerID);
			if (columns[column].size() == 6) {
				availableColumns.remove(move);
			}
			if (isWin(column)) {
				winner = playerID;
				isWon = true;
			}
			nMovesRemaining--;
			if (nMovesRemaining == 0) {
				isWon = true;
			}
			playerID = (playerID % 2) + 1; // next player
			undoStack.push(column);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method checks to see whether there is any winning line either
	 * vertically, horizontally or diagonally.
	 * 
	 * @param x Column number
	 * @return True for if there is any winning line
	 */
	private boolean isWin(int x) {

		int y = columns[x].size() - 1;

		// Check vertical
		boolean win = true;
		if (y >= 3) {
			for (int i = 1; i < 4; i++) {
				if (columns[x].get(y - i) != playerID) {
					win = false;
					break;
				}
			}
			if (win)
				return win;
		}

		int l = Math.max(0, x - 3);
		int r = Math.min(6, x + 3);

		// Check horizontal
		int count = 0;
		for (int i = l; i <= r; i++) {
			if (columns[i].size() > y && columns[i].get(y) == playerID) {
				count++;
				if (count > 3)
					return true;
			} else {
				count = 0;
			}
		}

		// Check diagonal 1
		count = 0;
		l = x - 3;
		int t = y + 3;
		int b = y - 3;
		List<Pair> pairs1 = new LinkedList<ConnectFour.Pair>();
		List<Pair> pairs2 = new LinkedList<ConnectFour.Pair>();
		for (int i = 0; i < 7; i++) {
			int px1 = l + i;
			int py1 = b + i;
			int py2 = t - i;
			if (px1 > -1 && px1 < 7 && py1 > -1 && py1 < 6) {
				pairs1.add(new Pair(px1, py1));
			}
			if (px1 > -1 && px1 < 7 && py2 > -1 && py2 < 6) {
				pairs2.add(new Pair(px1, py2));
			}
		}
		// System.out.println("x = " + x + ", y = " + y);
		// System.out.println(pairs1);
		// System.out.println(pairs2);
		// System.out.println("x = " + x + ", y = " + y + ", l = " + l +
		// ", r = " + r + ", t = " + t + ", b = " + b + ", d = " + d);
		for (Pair p : pairs1) {
			if (columns[p.x].size() > p.y && columns[p.x].get(p.y) == playerID) {
				count++;
				if (count > 3)
					return true;
			} else {
				count = 0;
			}
		}

		count = 0;

		for (Pair p : pairs2) {
			if (columns[p.x].size() > p.y && columns[p.x].get(p.y) == playerID) {
				count++;
				if (count > 3)
					return true;
			} else {
				count = 0;
			}
		}

		return false;
	}

	@Override
	public int getWinner() {
		return winner;
	}

	@Override
	public boolean isWon() {
		return isWon;
	}

	
	@Override
	public List<Move> getAvailableMoves() {
		return new LinkedList<Move>(availableColumns);
	}

	@Override
	public void undo() {
		int col = undoStack.pop();

		int x = col;//
		int y = columns[col].size();//
		// System.out.printf("UNDOING Move: %1d->(%1d,  %1d)\n",
		// columns[col].get(columns[col].size()-1), x, y-1);

		columns[col].remove(columns[col].size() - 1);
		playerID = (playerID % 2) + 1;
		winner = 0;
		isWon = false;
		nMovesRemaining++;
		Move move = new Connect4Move(col);
		if (!availableColumns.contains(move)) {
			availableColumns.add(move);
		}
		if (!undoStack.isEmpty()) {//
			x = undoStack.peek();//
			y = columns[x].size();//
			// System.out.printf("New Last Move: %1d->(%1d,  %1d)\n",
			// columns[x].get(columns[x].size()-1), x, y-1);
			if (columns[x].get(columns[x].size() - 1) == 1) {
				p1lastMove[0] = x;//
				p1lastMove[1] = y - 1;//
			} else {
				p2lastMove[0] = x;//
				p2lastMove[1] = y - 1;//
			}
		}
	}
	
	@Override
	public int playerID() {
		return playerID;
	}

	/**
	 * This method is used by the evalPosition method in players.
	 * 
	 * @return a two dimension array which represents the grid
	 */
	public int[][] getGridAsMatrix() {
		int[][] grid = new int[7][6];
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < columns[x].size(); y++) {
				try {
					grid[x][y] = columns[x].get(y);
				} catch (Exception e) {
					System.out.println(x + ", " + y + ", " + columns[x].size());
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		return grid;
	}
	/**
	 * This method returns the winning tokens positions in order
	 * to change their colour. 
	 * 
	 * @param x The x position in the grid
	 * @param y The y position in the grid
	 * @param pID player ID
	 * @return the winning tokens positions
	 */
	private List<int[]> findTokensPositionToChangeColour(int x, int y, int pID) {
		List<int[]> positionOfwiningTokens = new ArrayList<int[]>(4);
		int[] tmp;
		int[][] grid = getGridAsMatrix();

		// Horizontal
		for (int i = -3; i < 1; i++)// slides the window
		{
			boolean free = true;
			for (int j = 0; j < 4; j++) {
				int a = x + i + j;
				int b = y;
				if (a >= 0 && a <= 6) {
					if (grid[a][b] == pID) {
						tmp = new int[2];
						tmp[0] = a;
						tmp[1] = b;
						positionOfwiningTokens.add(tmp);
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
			if (!free) {
				positionOfwiningTokens.clear();

			} else {
				return positionOfwiningTokens;
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
					if (grid[a][b] == pID) {
						free = true;
						tmp = new int[2];
						tmp[0] = a;
						tmp[1] = b;
						positionOfwiningTokens.add(tmp);
					} else {
						free = false;
						break;
					}
				} else {
					free = false;
					break;
				}

			}
			if (!free) {
				positionOfwiningTokens.clear();

			} else {
				return positionOfwiningTokens;
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
					if (grid[a][b] == pID) {
						free = true;
						tmp = new int[2];
						tmp[0] = a;
						tmp[1] = b;
						positionOfwiningTokens.add(tmp);

					} else {
						free = false;
						break;
					}

				} else {
					free = false;
					break;
				}
			}
			if (!free) {
				positionOfwiningTokens.clear();

			} else {
				return positionOfwiningTokens;
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
					if (grid[a][b] == pID) {
						free = true;
						tmp = new int[2];
						tmp[0] = a;
						tmp[1] = b;
						positionOfwiningTokens.add(tmp);
					} else {
						free = false;
						break;
					}

				} else {
					free = false;
					break;
				}
			}
			if (!free) {
				positionOfwiningTokens.clear();

			} else {
				return positionOfwiningTokens;
			}
		}
		return positionOfwiningTokens;
	}
	/**
	 * This is used to pass the grid to the view and also to pass the positions
	 * of the winning tokens to the view, so the view can update their
	 * colours depending on whether it is Red player or Yellow payer.
	 * @return  two dimension array which represents the grid
	 */
	public int[][] getViewGrid() {
		int[] lastMove = new int[2];
		int[][] grid = new int[7][6];
		int newc;

		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < columns[x].size(); y++) {
				try {
					grid[x][y] = columns[x].get(y);
				} catch (Exception e) {
					System.out.println(x + ", " + y + ", " + columns[x].size());
					e.printStackTrace();
					System.exit(1);
				}
			}
		}
		if (isWon) {
			if (this.getWinner() == 1) {
				lastMove = getP1lastMove();
				newc = 3;
			} else {
				lastMove = getP2lastMove();
				newc = 4;
			}
			List<int[]> winingTokensPos = findTokensPositionToChangeColour(
					lastMove[0], lastMove[1], this.getWinner());
			for (int i = 0; i < winingTokensPos.size(); i++) {
				int[] tmp = winingTokensPos.get(i);

				// System.out.println("the Winning tokens are: " +
				// winingTokensPos.get(i)[0]+" "+ winingTokensPos.get(i)[1]);
				grid[tmp[0]][tmp[1]] = newc;
			}
		}
		return grid;
	}
	/**
	 * Represents the players moves in the grid with X and O
	 * for player 1 and player 2 respectively. 
	 */
	@Override
	public String toString() {
		int[][] grid = getViewGrid();
		String s = "_____________\n";
		for (int y = 5; y >= 0; y--) {
			for (int x = 0; x < 7; x++) {
				switch (grid[x][y]) {
				case 1:
					s += "x ";
					break;
				case 2:
					s += "o ";
					break;
				default:
					s += "  ";
				}
			}
			s += "\n";
		}
		s += "=============\n";
		return s;
	}

	private class Pair {
		int x, y;

		private Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}
	
	@Override
	public int movesRemaining() {
		int mr = 42;

		for (List<Integer> column : columns) {
			mr -= column.size();
		}

		return mr;
	}

	@Override
	public int getValue() {
		if (winner == 2) {
			return -1;
		} else {
			return winner;
		}
	}

	@Override
	public double[] rewards() {
		if (!isWon) {
			System.err
					.println("You shouldn't call rewards() when the game isn't finished.");
			return new double[] { 0.0, 0.0 };
		} else {
			if (winner == 1) {
				return new double[] { 1.0, 0.0 };
			} else {
				return new double[] { 0.0, 1.0 };
			}
		}
	}
	
	@Override
	public int nPlayers() {
		return 2;
	}

	@Override
	public String getName() {
		return "Connect Four";
	}

	@Override
	public GameMouseListener getMouseListener() {
		return ml;
	}


	public List<Integer> getColumn(int c) {
		return columns[c];
	}

	/**
	 * This method returns the player 1 last move. It is there
	 * to allow AI players to know what move the opponent made.
	 * 
	 * @return an array which contains the player 1 last move. the x position is
	 *         stored in index 0 and y position in index 1.
	 */
	public int[] getP1lastMove() {
		return p1lastMove;
	}

	/**
	 * This method returns the player 2 last move. It is there
	 * to allow AI players to know what move the opponent made
	 * 
	 * @return an array which contains the player 2 last move. the x position is
	 *         stored in index 0 and y position in index 1.
	 */
	public int[] getP2lastMove() {
		return p2lastMove;
	}
	
	/**
	 * used to quit the game.
	 */
	public void exitGame() {
		System.exit(0);
	}

	public void newGame() {

	}

}
