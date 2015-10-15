/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package players;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import model.Connect4Move;
import model.ConnectFour;
import model.Game;
import data.Move;
/*
 * Player4
 */
/**
 * This player learn from its opponent and adjust its play accordingly. It takes
 * into account of opponent's statistics and based on those statistics chooses
 * its move. This version classifies the moves based on their relative differences
 * between the value of each move.
 * 
 */
public class RoulettePlayer_2 extends AbstractPlayer {

	private int[] HumanstatisticsCounter = new int[7];
	private int[] AIstatisticsCounter = new int[7];
	private String path;
	private int AIWins = 0 , HumanWins = 0 ,  totalgames = 0;
	private double AIWinRatio = 0.0 , HumanWinRatio = 0.0;
	

	public RoulettePlayer_2() {
		path = null;
		reset();

	}

	/**
	 * This method is just to write the AI statistics into a text file.
	 */
	private void writeAIStatistics() {
		double[] AIPercentage = new double[7];
		int total = 0;

		for (int c : AIstatisticsCounter) {
			total += c;

		}
		for (int q = 0; q < AIstatisticsCounter.length; q++) {
			AIPercentage[q] = ((AIstatisticsCounter[q]) / (float) total);
		}

//		for (int k = 0; k < AIPercentage.length; k++) {
//			System.out.println("AI Percentages: " + AIPercentage[k]);
//		}

		String fileName = "AI_Moves.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.write(AIPercentage[0] + ",");
			bufferedWriter.write(AIPercentage[1] + ",");
			bufferedWriter.write(AIPercentage[2] + ",");
			bufferedWriter.write(AIPercentage[3] + ",");
			bufferedWriter.write(AIPercentage[4] + ",");
			bufferedWriter.write(AIPercentage[5] + ",");
			bufferedWriter.write(AIPercentage[6] + "");

			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + fileName + "'");

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Move getMove(Game game) {

		List<Connect4Move> sortedList = new ArrayList<Connect4Move>();
		double[] statistics = new double[game.getAvailableMoves().size()];
		double counter = 0.0;
		int j;
		Random rn = new Random();

		/* gets the values of each square and stores the in the sorted list */
		sortedList = getValues(game);

		/*
		 * save the human statistics which are in percentage on the statistics
		 * array
		 */
		statistics = getHumanStatistics(game);

		/*
		 * creates a random number between 0.0 and 1.0
		 */
		double randomNum = rn.nextDouble();
//		System.out.println("Ranodm Numebr:  " + randomNum);

		/*
		 * Roulette Implementation. The human statistics are saved in the
		 * statistics array so by adding each index of the array to the counter
		 * and comparing it against the random number, AI can found the move
		 * according to the statistics.
		 */
		for (j = 0; j < statistics.length; j++) {
			if (interupted) {
				return null;
			}
			counter += statistics[j];
			if (randomNum <= counter) {
				break;
			}
		}

		AIstatisticsCounter[j]++;
		writeAIStatistics();
		/*
		 * In situations where some of the columns are full the number of moves
		 * are reduced, therefore if the chosen move is greater than the sorted
		 * list size, it returns the last index of the sorted list.
		 */
		if (j >= sortedList.size()) {
			if (interupted) {
				return null;
			}
//			System.out.println("AI move: "+ sortedList.get(sortedList.size() - 1));
			return sortedList.get(sortedList.size() - 1);
		}
//		System.out.println("AI move: " + sortedList.get(j));

		if (interupted) {
			return null;
		}
		return sortedList.get(j);

	}
	/**
	 * This method calculates how many times each move is selected
	 * by the human player and calculates the percentage of it. 
	 * @param game The game state
	 * @return The human player statistics in percentage
	 */
	@SuppressWarnings("unchecked")
	public double[] getHumanStatistics(Game game) {
		int[] humanLastMove;
		int sum = 0;
		playerID = game.playerID();
		List<Connect4Move> sortedList = new ArrayList<Connect4Move>();
		double[] percentages = new double[7];

		if (playerID == 1) {
			humanLastMove = ((ConnectFour) game).getP2lastMove();
		} else {
			humanLastMove = ((ConnectFour) game).getP1lastMove();
		}
		Connect4Move LastMove = new Connect4Move(humanLastMove[0]);

		/*
		 * when the AI player is the first player it means the Human player has
		 * not made any move yet, therefore no move can be undone. Therefore AI
		 * picks a move based on the following predefined values.
		 */
		if (game.movesRemaining() == 42) {
			percentages[0] = 0.70;
			percentages[1] = 0.15;
			percentages[2] = 0.10;
			percentages[3] = 0.02;
			percentages[4] = 0.01;
			percentages[5] = 0.01;
			percentages[6] = 0.01;

			return percentages;
		}
		game.undo();
		//		percentages = new double[7];
		/*
		 * gets the value of the moves which are scaled between
		 * 0.0 and 1.0 with the best move being 1.0 and sorted 
		 * in the descending order.
		 */
		sortedList = getValues(game);

		/* Redo the move */
		game.makeMove(LastMove);

		int i = 0;
		/*
		 * goes through the sorted list and the value of the human player last move 
		 * is compared against set of ranges to find the relative rage and updates the
		 * associated index of the counter array. This finds how many times each move is selected.
		 * 
		 */

		for (Connect4Move m : sortedList) {

			if (m.column == LastMove.column) {
				if (m.getValue() > 0.9) {
					HumanstatisticsCounter[0]++;
					break;

				} else if (m.getValue() > 0.8) {
					HumanstatisticsCounter[1]++;
					break;

				} else if (m.getValue() > 0.7) {
					HumanstatisticsCounter[2]++;
					break;
				} else if (m.getValue() > 0.6) {
					HumanstatisticsCounter[3]++;
					break;

				} else if (m.getValue() > 0.5) {
					HumanstatisticsCounter[4]++;
					break;

				} else if (m.getValue() > 0.3) {
					HumanstatisticsCounter[5]++;
					break;

				} else if (m.getValue() > 0.0) {
					HumanstatisticsCounter[6]++;
					break;
				}

			}

		}
		

		/*
		 * adds all the elements of the Human statistics counter array to
		 * calculate the sum of them.
		 */
		for (int c : HumanstatisticsCounter) {

			sum += c;

		}
		
		/*
		 * calculates the percentage of each selected move
		 */
		for (i = 0; i < HumanstatisticsCounter.length; i++) {
			percentages[i] = ((HumanstatisticsCounter[i]) / (float) sum);
		}
		
		/*
		 * 
		 */
		HumanstatisticsCounter[0] = HumanstatisticsCounter[0] - 85; 
		HumanstatisticsCounter[1] = HumanstatisticsCounter[1] - 5;
		HumanstatisticsCounter[2] = HumanstatisticsCounter[2] - 5;
		HumanstatisticsCounter[3] = HumanstatisticsCounter[3] - 2;
		HumanstatisticsCounter[4] = HumanstatisticsCounter[4] - 1;
		HumanstatisticsCounter[5] = HumanstatisticsCounter[5] - 1;
		HumanstatisticsCounter[6] = HumanstatisticsCounter[6] - 1;
		int statistics_total = 0;
		double[] writePercentages = new double[7];
		for (int c : HumanstatisticsCounter) {

			statistics_total += c;

		}
		for (i = 0; i < HumanstatisticsCounter.length; i++) {
			writePercentages[i] = ((HumanstatisticsCounter[i]) / (float) statistics_total);
		}
												
		/*
		 * This is just to write the human percentages to a text
		 * file.
		 */
		String fileName = "opponent_move.txt";
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(writePercentages[0] + ",");
			bufferedWriter.write(writePercentages[1] + ",");
			bufferedWriter.write(writePercentages[2] + ",");
			bufferedWriter.write(writePercentages[3] + ",");
			bufferedWriter.write(writePercentages[4] + ",");
			bufferedWriter.write(writePercentages[5] + ",");
			bufferedWriter.write(writePercentages[6] + "");
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + fileName + "'");

			e.printStackTrace();
		}
		
		HumanstatisticsCounter[0] = HumanstatisticsCounter[0] + 85; 
		HumanstatisticsCounter[1] = HumanstatisticsCounter[1] + 5;
		HumanstatisticsCounter[2] = HumanstatisticsCounter[2] + 5;
		HumanstatisticsCounter[3] = HumanstatisticsCounter[3] + 2;
		HumanstatisticsCounter[4] = HumanstatisticsCounter[4] + 1;
		HumanstatisticsCounter[5] = HumanstatisticsCounter[5] + 1;
		HumanstatisticsCounter[6] = HumanstatisticsCounter[6] + 1;
		
//		for (int k = 0; k < percentages.length; k++) {
//			System.out.println("Human Percentages: " + percentages[k]);
//		}

		return percentages;
	}

	@Override
	public String getName() {
		return "Roulette_2";
	}
	/**
	 * The Human Statistics Counter is set to predefined values
	 * to start from. This is done to help the AI to make better 
	 * decision on how likely its opponent makes each move.
	 * If the Human Statistics Counter starts from zero, once the 
	 * human player makes a move, the percentage of that move
	 * will be 100 and AI may think that its opponent is making that 
	 * move 100% of the time, which is not true as the human has only made one 
	 * move yet. 
	 * This method is called in the constructor to get called in the beginning 
	 * of the game, and also every time the  game is reset.
	 * 
	 */
	@Override
	public void reset() {
		super.reset();
		for (int i = 0; i < AIstatisticsCounter.length; i++) {

			AIstatisticsCounter[i] = 0;
		}
		HumanstatisticsCounter[0] = 85;
		HumanstatisticsCounter[1] = 5;
		HumanstatisticsCounter[2] = 5;
		HumanstatisticsCounter[3] = 2;
		HumanstatisticsCounter[4] = 1;
		HumanstatisticsCounter[5] = 1;
		HumanstatisticsCounter[6] = 1;

		readStatisticsSerializable(path);

	}
	/**
	 * This method tells the status of the game
	 * to the player once the game is finished. 
	 * If the status is not interrupted, it means 
	 * the player either won or lost or the game 
	 * was draw, it calls the writeHumanstatisticsSearlizable
	 * method to write the opponent statistics.
	 */
	@Override
	public void endGame(int status) {
		totalgames++;
		if (status == AbstractPlayer.WON){
			AIWins++;
		} else if (status == AbstractPlayer.LOST){
			HumanWins++;
		}
		if (status != AbstractPlayer.INTERRUPTED) {
			writeStatisticsSerializable(path);
		}
		
		AIWinRatio = (double) AIWins / totalgames;
		HumanWinRatio = (double) HumanWins / totalgames;
	}
	/**
	 * This is to  write the human player statistics to a file
	 * so the player can load its own file next time when he/she
	 * is playing against the Roulette player.
	 * It also writes the AI win ration and Human Win Ration to
	 * two separate text files for the purpose of producing
	 * result charts.
	 * @param FileName file name
	 */
	private void writeStatisticsSerializable(String FileName) {
		if(path != null){
//			System.out.println("writing.....");
			try {
				OutputStream file = new FileOutputStream(path);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);

				output.writeObject(HumanstatisticsCounter);
				output.writeObject(AIstatisticsCounter);
				output.close();
				file.close();

//				for (int v : HumanstatisticsCounter)
//					System.out.println("written human statistics: " + v);
//				for(int i : AIstatisticsCounter)
//					System.out.println("written AI statistics: " + i);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		/*
		 * Writes the AI win Ratio to a text file to produce a graph
		 */
		String AIFile = "AI_Win_Ratio.txt";
		try {
			FileWriter fileWriter = new FileWriter(AIFile, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			Double tmp = new Double(AIWinRatio);
			bufferedWriter.write(tmp.toString());

			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + AIFile + "'");

			e.printStackTrace();
		}
		/*
		 * Writes the Human Win Ration to a text file to produce a graph
		 */
		String HumanFile = "Human_Win_Ratio.txt";
		
		try {
			FileWriter fW = new FileWriter(HumanFile, true);
			BufferedWriter bW = new BufferedWriter(fW);
			
			Double tmp = new Double(HumanWinRatio);
			bW.write(tmp.toString());
			
			bW.newLine();
			bW.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + HumanFile + "'");

			e.printStackTrace();
		}

	}
	/**
	 * This is to read the human player statistics from a saved 
	 * file.
	 * @param FileName
	 */
	private void readStatisticsSerializable(String FileName) {
		if(path !=null){
			File f = new File(FileName);
			if (f.exists()) {

//				System.out.println("reading....");
				try {
					InputStream file = new FileInputStream(path);
					InputStream buffer = new BufferedInputStream(file);
					ObjectInput input = new ObjectInputStream(buffer);

					int[] recoveredstatistics = (int[]) input.readObject();
					int[] recoveredAIstatistics = (int[]) input.readObject();

					input.close();
					file.close();

//					for (int v : recoveredstatistics){
//						System.out.println("Recovered human statistics: " + v);
//					}
//					
//					for (int i: recoveredAIstatistics){
//						System.out.println("Recovered AI statistics:" + i);
//					}
					HumanstatisticsCounter = recoveredstatistics;
					AIstatisticsCounter = recoveredAIstatistics;
				}

				catch (ClassNotFoundException ex) {
					ex.printStackTrace();

				} catch(ClassCastException ex)
				{
					JOptionPane.showMessageDialog(null, "                                         Invalid file!\n" +
														" Your Data will not be saved. If you want to save your data go\n" +
														" back to the set up screen and create or select a valid file\n ");
					path = null;
														
				}
				catch (IOException ex) {
					ex.printStackTrace();

				}
			}
		}
	}
	/**
	 * This method sets the path received, and calls
	 * the readHumanstatisticsSearlizable method to read
	 * the file.
	 * @param path The path received 
	 */
	public void setPath(String path) {
		this.path = path;
		readStatisticsSerializable(path);
	}

	/***********************************************************
	 * The following is the MCTS used to evaluate the probability
	 *  of winning for each move. 
	 ***********************************************************/

	int nPlayers = 2;
	TreeNode root;
	/**
	 * This method calculates the probability of winning for each move
	 * and stores them in a list. The values are scaled with the best 
	 * move having a value of 1.0 and worst move 0.0. 
	 * The values are sorted in the descending order.
	 * 
	 * @param game The game state
	 * @return list of values for each move
	 */
	public List<Connect4Move> getValues(Game game) {
		root = new TreeNode(null, game);
		long stop = System.currentTimeMillis() + 1000;

		while (System.currentTimeMillis() < stop) {
			root.selectAction();
		}

		List<Connect4Move> val = new LinkedList<Connect4Move>();
		double bestValue = Double.NEGATIVE_INFINITY;
//		System.out.println("Player " + game.playerID());
		for (TreeNode child : root.children) {
			double value = child.rewards[game.playerID() - 1] / child.nVisits;
			val.add(new Connect4Move(child.move.hashCode(), value));
			if (value > bestValue) {
				bestValue = value;
			}
		}

		double ScalingFactor = 1.0 / bestValue;
		Collections.sort(val);

		for (int i = 0; i < val.size(); i++) {
			double tmpVal = val.get(i).getValue() * ScalingFactor;
			val.set(i, new Connect4Move(val.get(i).column, tmpVal));
		}

//		for (int i = 0; i < val.size(); i++) {
//						System.out.println(" columns after scaling: " + val.get(i)
//								+ "  values after scaling: " + val.get(i).getValue());
//		}
//		System.out.println("----------------------------------------------");
		return val;
	}

	private class TreeNode {
		private Game game;
		private Random r = new Random();
		private Move move;
		private double epsilon = 1e-6;

		TreeNode[] children;
		double nVisits;
		double[] rewards;

		private TreeNode(Move move, Game game) {
			this.move = move;
			this.game = game;
			rewards = new double[nPlayers];
		}

		public void selectAction() {
			List<TreeNode> visited = new LinkedList<TreeNode>();
			TreeNode cur = this;
			// visited.add(this);
			while (!cur.isLeaf() && !game.isWon()) {
				cur = cur.select();
				try {
					game.makeMove(cur.move);
				} catch (Exception e) {
					System.out.println(">>" + game);
				}
				visited.add(cur);
			}
			double[] rewards;
			if (game.isWon()) {
				rewards = game.rewards();
			} else {
				cur.expand();
				TreeNode newNode = cur.select();
				game.makeMove(newNode.move);
				visited.add(newNode);
				rewards = rollOut();
			}
			// System.out.println(visited.size());

			for (TreeNode node : visited) {
				game.undo();
				node.updateStats(rewards);
			}
			this.updateStats(rewards);
		}

		public void expand() {
			List<Move> moves = game.getAvailableMoves();
			children = new TreeNode[moves.size()];
			if (moves.size() == 0) {
				System.out.println("Error expanding " + moves.size()
						+ " children.");
			}
			int i = 0;
			for (Move m : moves) {
				children[i] = new TreeNode(m, game);
				i++;
			}
		}

		private TreeNode select() {
			TreeNode selected = children[0];
			double bestValue = Double.NEGATIVE_INFINITY;
			if (children.length == 0) {
				System.out.println("NO children to select.");
			}

			for (TreeNode c : children) {
				double uctValue = c.rewards[game.playerID() - 1]
						/ (c.nVisits + epsilon)
						+ Math.sqrt(Math.log(nVisits + 1)
								/ (c.nVisits + epsilon)) + r.nextDouble()
								* epsilon;
				if (uctValue > bestValue) {
					selected = c;
					bestValue = uctValue;
				}
			}

			return selected;
		}

		public boolean isLeaf() {
			return children == null;
		}

		public double[] rollOut() {
			int count = 0;
			List<Move> moves = game.getAvailableMoves();

			while (!game.isWon()) {
				game.makeMove(moves.get(r.nextInt(moves.size())));
				count++;
				moves = game.getAvailableMoves();
			}

			double[] rewards = game.rewards();

			for (int i = 0; i < count; i++) {
				game.undo();
			}

			return rewards;
		}

		public void updateStats(double[] rewards) {
			nVisits++;
			for (int i = 0; i < rewards.length; i++) {
				this.rewards[i] += rewards[i];
			}
		}

	}

}
