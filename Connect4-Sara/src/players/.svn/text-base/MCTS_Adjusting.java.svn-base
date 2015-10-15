/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package players; 

import model.Game; 

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
import java.util.LinkedList; 
import java.util.List; 
import java.util.Random; 

import javax.swing.JOptionPane;

import data.Move; 
/*
 * Player5
 */
public class MCTS_Adjusting extends AbstractPlayer { 

	int nPlayers; 
	TreeNode root; 
	private double target = 0.75; 
	private final double targetWinRatio = 0.6; 
	private int totalgames = 100; 
	private int wins = 60; 
	private String path;
	private int humanWins = 60;
	private double HumanRatio = 0;
	private double ratio = 0;


	public MCTS_Adjusting () { 
		this.nPlayers = 2; 
		path = null;
	} 

	@Override 
	public Move getMove(Game game) { 
		List<Move> moves = game.getAvailableMoves(); 
		if(moves.size() == 1) { 
			try { 
				Thread.sleep(500); 
			} catch (InterruptedException e) { 
				// TODO Auto-generated catch block 
				e.printStackTrace(); 
			} 
			return moves.get(0); 
		} 
		root = new TreeNode(null, game); 
		long stop = System.currentTimeMillis() + 1000; 

		while(System.currentTimeMillis() < stop) { 
			root.selectAction(); 
		} 



		double distance = Double.POSITIVE_INFINITY; 
		Move bestMove = null; 
//		System.out.println("Player " + game.playerID()); 
		for (TreeNode child : root.children) { 
			double value = child.rewards[game.playerID() - 1] / child.nVisits; 
//			System.out.println(child.move + " : " + value + " (" + child.nVisits + ")"); 
			if (Math.abs(value - target) < distance) { 
				distance = Math.abs(value - target); 
				bestMove = child.move; 
			} 
		} 
//		System.out.println("----------------------------------------------"); 
		return bestMove; 
	} 

	public void endGame(int status) {
		totalgames++;
		if (status == AbstractPlayer.WON) {
			wins++;
		} else if (status == AbstractPlayer.LOST) {
			humanWins++;
			
		}
		HumanRatio = (double) humanWins / totalgames;
		ratio = (double) wins / totalgames;
		
		if (ratio > targetWinRatio) {
			target -= 0.05;
		} else if (ratio < targetWinRatio) {
			target += 0.05;
		}

		if (status != AbstractPlayer.INTERRUPTED) {
			writeStatistics(path);
		}
	}

	//    public void endGame(boolean iWon) { 
	//        totalgames++; 
	//        if(iWon) { 
	//            wins++; 
	//        } 
	//        double ratio = (double)wins / totalgames; 
	//        if(ratio > targetWinRatio) { 
	//            target -= 0.05; 
	//        } else if (ratio < targetWinRatio) { 
	//            target += 0.05; 
	//        } 
	//    } 

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
				rewards = rollOut(newNode); 
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
				System.out.println("Error expanding " + moves.size() + " children."); 
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
				double uctValue = c.rewards[game.playerID()-1] 
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

		public double[] rollOut(TreeNode tn) { 

			//Do the rollout in the game state 
			int count = 0; 
			List<Move> moves = game.getAvailableMoves(); 

			while(!game.isWon()) { 
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
			for(int i = 0 ; i < rewards.length ; i++) { 
				this.rewards[i] += rewards[i]; 
			} 
		} 

	} 

	@Override 
	public String getName() { 
		return "MCTS Player"; 
	}

	@Override
	public void reset(){
		super.reset();
		readStatistics(path);

	}

	/**
	 * This method sets the path received, and calls
	 * the readHumanstatisticsSearlizable method to read
	 * the file.
	 * @param path The path received 
	 */
	public void setPath(String path) {
		this.path = path;
		readStatistics(path);
	}

	private void writeStatistics(String FileName) {
		if(path != null){
//			System.out.println("writing.....");
			try {
				OutputStream file = new FileOutputStream(path);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);

				output.writeObject(target);
				output.writeObject(totalgames);
				output.writeObject(wins);
				output.close();
				file.close();

//				System.out.println("written target " + target);
//				System.out.println("written total games " + totalgames);
//				System.out.println("written wins " + wins);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		String AIFile = "AI.txt";
		try {
			FileWriter fileWriter = new FileWriter(AIFile, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			Double tmp = new Double(ratio);
			bufferedWriter.write(tmp.toString() + ",");
			Double tmp2 = new Double(target);
			bufferedWriter.write(tmp2.toString());


			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + AIFile + "'");

			e.printStackTrace();
		}
		
		String HumanFile = "Human.txt";
		
		try {
			FileWriter fW = new FileWriter(HumanFile, true);
			BufferedWriter bW = new BufferedWriter(fW);
			
			Double tmp = new Double(HumanRatio);
			bW.write(tmp.toString());
			
			bW.newLine();
			bW.close();
		} catch (IOException e) {
			System.out.println("Error writing to file '" + HumanFile + "'");

			e.printStackTrace();
		}
	}

	private void readStatistics(String FileName) {
		if(path !=null){
			File f = new File(FileName);
			if (f.exists()) {
//				System.out.println("reading....");
				try {
					InputStream file = new FileInputStream(path);
					InputStream buffer = new BufferedInputStream(file);
					ObjectInput input = new ObjectInputStream(buffer);

					Double recoveredTarget =  (Double) input.readObject();
					Integer recoveredTotalGames = (Integer) input.readObject();
					Integer recoveredWins = (Integer) input.readObject();
					input.close();
					file.close();

//					System.out.println("Recovered Target " + recoveredTarget);
//					System.out.println("Recovered Total Games " + recoveredTotalGames);
//					System.out.println("Recovered wins " + recoveredWins);
					target = recoveredTarget;
					totalgames = recoveredTotalGames;
					wins = recoveredWins;

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

} 
