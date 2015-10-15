/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;

import data.Move;

import model.ConnectFour;
import players.AbstractPlayer;
import players.GuiPlayer;
import players.MCTSPlayer;
import players.Player;
import players.RoulettePlayer_1;
import players.RoulettePlayer_2;
import players.MCTS_Adjusting;
import view.C4View;
import view.SetUpView;
import view.View;

public class Controller {
	private static final Controller me = new Controller();
	private ConnectFour game;
	private Player player1, player2;
	private static boolean running = true;
	private static boolean checkInstance = false;
	private CountDownLatch donSignal;
	private View view = new C4View();
	private SetUpView setUpView;
	private JFrame frame;
	private Game_runner runner;
	private Thread trunner;
	private int hintCounterp1, hintCounterp2;
	private String[] actualNames = { "GuiPlayer", "MiniMaxPlayer_1", "MiniMaxPlayer_2","RoulettePlayer_1", "RoulettePlayer_2", 
			"MCTS_Adjusting", "MCTSPlayer"};
	private String[] names = {"Human Player", "MiniMaxPlayer_1", "MiniMaxPlayer_2", "RoulettePlayer_1", "RoulettePlayer_2",
			"MCTS_Adjusting","MCTSPlayer"};

	private boolean suspended = false;
	private Color player1Colour = Color.RED, player2Colour = Color.YELLOW;

	/**
	 * Constructor creates the view and displays the set up.
	 * 
	 * @throws RuntimeException
	 */
	public Controller() throws RuntimeException {
		hintCounterp1 = 3;
		hintCounterp2 = 3;
		if (checkInstance) {
			throw new RuntimeException(
					"You can only instantiate the controller once");
		} else {
			checkInstance = true;
		}

		frame = new JFrame("Connect Four");
		show_new_game();
	}

	/**
	 * This method adds the set up panel to the frame where the players can be
	 * chosen
	 */

	private void show_new_game() {
		frame.setPreferredSize(new Dimension(680, 680));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUpView = new SetUpView(names);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(setUpView);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocation(600, 200);
		frame.pack();
	}

	/**
	 * This method deals with the administrative tasks of taking the users
	 * chosen options from the setup screen and initialising the required data
	 * structures to match those options. Once the initialisations are done, it
	 * calls the start() method to setup the game Thread.
	 * 
	 * @param p1
	 *            Player 1 name
	 * @param p2
	 *            Player 2 name
	 * @param path_1
	 *            Player 1 path used for Roulette Players
	 * @param path_2
	 *            Player 2 path used for Roulette players
	 */
	public void userChosenOptions(String p1, String p2, String path_1,
			String path_2) {
		Player player2 = null, player1 = null;
		int p1Index = 0 , p2Index = 0;
		
		for(int i=0; i< names.length;i++){
			 if(p1.equals(names[i])){
				 p1Index = i;
			 }			 
		}
		for(int i=0; i< names.length;i++){
			 if(p2.equals(names[i])){
				 p2Index = i;
			 }			 
		}
		try {
			Class<?> c = Class.forName("players." + actualNames[p1Index]);
			Constructor<?> cons = c.getConstructor();
			player1 = (Player) cons.newInstance();

			c = Class.forName("players." + actualNames[p2Index]);
			cons = c.getConstructor();
			player2 = (Player) cons.newInstance();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setPlayer1(player1);
		setPlayer2(player2);

//		System.out.println("Printing Contrller path_1...  " + path_1);
//		System.out.println("Printing Contrller path_2...  " + path_2);

		if ((p1.equals("RoulettePlayer_1")) && (p2.equals("RoulettePlayer_2"))) {
			((RoulettePlayer_1) player1).setPath(path_1);
			((RoulettePlayer_2) player2).setPath(path_2);

		} else if ((p1.equals("RoulettePlayer_2")) && (p2.equals("RoulettePlayer_1"))) {

			((RoulettePlayer_2) player1).setPath(path_1);
			((RoulettePlayer_1) player2).setPath(path_2);

		} else if ((p1.equals("MCTS_Adjusting")) && (p2.equals("RoulettePlayer_1"))) {

			((MCTS_Adjusting) player1).setPath(path_1);
			((RoulettePlayer_1) player2).setPath(path_2);

		}else if ((p1.equals("RoulettePlayer_1")) && (p2.equals("MCTS_Adjusting"))) {
			((RoulettePlayer_1) player1).setPath(path_1);
			((MCTS_Adjusting) player2).setPath(path_2);

		} else if ((p1.equals("MCTS_Adjusting")) && (p2.equals("RoulettePlayer_2"))) {

			((MCTS_Adjusting) player1).setPath(path_1);
			((RoulettePlayer_2) player2).setPath(path_2);

		}else if ((p1.equals("RoulettePlayer_2")) && (p2.equals("MCTS_Adjusting"))) {
			((RoulettePlayer_2) player1).setPath(path_1);
			((MCTS_Adjusting) player2).setPath(path_2);

		} else if ((p1.equals("RoulettePlayer_1")) && (p2.equals("RoulettePlayer_1"))) {
			((RoulettePlayer_1) player1).setPath(path_1);
			((RoulettePlayer_1) player2).setPath(path_2);

		} else if ((p1.equals("RoulettePlayer_2")) && (p2.equals("RoulettePlayer_2"))) {
			((RoulettePlayer_2) player1).setPath(path_1);
			((RoulettePlayer_2) player2).setPath(path_2);

		}  else if ((p1.equals("MCTS_Adjusting")) && (p2.equals("MCTS_Adjusting"))) {
			((MCTS_Adjusting) player1).setPath(path_1);
			((MCTS_Adjusting) player2).setPath(path_2);

		}else if ((p1.equals("RoulettePlayer_1"))) {
			((RoulettePlayer_1) player1).setPath(path_1);

		} else if ((p1.equals("RoulettePlayer_2"))) {
			((RoulettePlayer_2) player1).setPath(path_1);

		} else if ((p2.equals("RoulettePlayer_1"))) {
			((RoulettePlayer_1) player2).setPath(path_2);

		} else if ((p2.equals("RoulettePlayer_2"))) {
			((RoulettePlayer_2) player2).setPath(path_2);
			
		} else if ((p1.equals("MCTS_Adjusting"))) {
			((MCTS_Adjusting) player1).setPath(path_1);

		}else if ((p2.equals("MCTS_Adjusting"))) {
			((MCTS_Adjusting) player2).setPath(path_2);
			
		}

		frame.getContentPane().remove(setUpView);
		frame.getContentPane().add((C4View) view);
		frame.pack();
		start_game();
	}

	/**
	 * This method sets up a Thread to run the game. Threads enables the game to
	 * be reset or set up a new game.
	 */
	private void start_game() {
		running = true;

		game = new ConnectFour();

		donSignal = new CountDownLatch(1);

		runner = new Game_runner(game, view, player1, player2,Controller.getInstance(), donSignal);

		trunner = new Thread(runner);

		trunner.start();

	}

	/**
	 * Once a game has been set up, this is the method that plays the game. Its
	 * responsibilities are get moves from the Players and make those moves.
	 * This loops until the game ends, or the game is interrupted. It also
	 * notifies the view and player of who win the game.
	 */
	public void play() {
		while (!game.isWon() && running) {

			view.update(game);
			if (game.playerID() == 1) {
				view.setStatus("Player 1 turn");
				game.makeMove(player1.getMove(game));
			} else {
				view.setStatus("Player 2 turn");
				game.makeMove(player2.getMove(game));
			}
			view.update(game);

			if (game.isWon()) {
				((C4View) view).notifyOfWin(game.getWinner());
//				System.out.println("Player " + game.getWinner() + " wins.");
				// to notify the players that the game has ended
				if (game.getWinner() == 1) {
					player1.endGame(AbstractPlayer.WON);
					player2.endGame(AbstractPlayer.LOST);
				} else if(game.getWinner() == 2) {
					player1.endGame(AbstractPlayer.LOST);
					player2.endGame(AbstractPlayer.WON);
				} else{
					player1.endGame(AbstractPlayer.DRAW);
					player2.endGame(AbstractPlayer.DRAW);
				}

			}

//			System.out.println("running: " + running + " isWon: "
//					+ game.isWon());
		}
		if (!running) {
			// the game was interrupted
			player1.endGame(AbstractPlayer.INTERRUPTED);
			player2.endGame(AbstractPlayer.INTERRUPTED);

		}
		System.err.println("end of play");
		view.update(game);
	}

	/**
	 * This method is used for the purpose of implementing singleton design
	 * pattern. In this case there can only be one instance of the Controller
	 * which is provided by by this method.
	 * 
	 * @return an instance of the Controller class
	 */
	public static Controller getInstance() {
		return me;
	}

	/**
	 * This method is used to set up a new game. It acknowledges the players
	 * that the game is interrupted and it switches the view to set up panel.
	 */
	public void menuNewGame() {
		hintCounterp1 = 3;
		hintCounterp2 = 3;
		running = false;
		player1.interupt();
		player2.interupt();
		try {
			if(trunner.isAlive() && !suspended){
			System.err.printf("Controller Waiting....\n");
			donSignal.await();
			System.err.printf("Controller Done\n");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		game.newGame();
		show_new_game();
	}

	/**
	 * This method is used to reset the game. It acknowledges the players that
	 * the game is interrupted, and it resets the game and starts a new fresh
	 * game.
	 */
	public void menuRestart() {
		hintCounterp1 = 3;
		hintCounterp2 = 3;
		running = false;
		player1.interupt();
		player2.interupt();

		try {
			if(trunner.isAlive() && !suspended){
			System.err.printf("Controller Waiting....\n");
			donSignal.await();
			System.err.printf("Controller Done\n");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		game.newGame();

		view.removeGameMouseListener(game.getMouseListener());

		player1.reset();
		player2.reset();

		if (player1 instanceof GuiPlayer) {
			((GuiPlayer) player1).interupt();
		}

		if (player2 instanceof GuiPlayer) {
			((GuiPlayer) player2).interupt();
		}
		start_game();
	}

	/**
	 * This method is used to quit the game when the Exit option is chosen.
	 */
	public void menuExit() {
		game.exitGame();
	}

	/**
	 * This method is used to return the game.
	 * 
	 * @return the game
	 */
	public ConnectFour getGame() {
		return game;
	}

	/**
	 * This method is used to set the game.
	 * 
	 * @param game
	 *            sets the game
	 */
	public void setGame(ConnectFour game) {
		this.game = game;
	}

	/**
	 * This method is used to return the player1.
	 * 
	 * @return Player1
	 */
	public Player getPlayer1() {
		return player1;
	}

	/**
	 * This method is used to set the player1.
	 * 
	 * @param player1
	 */
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	/**
	 * This method is used to return the player2.
	 * 
	 * @return player2
	 */
	public Player getPlayer2() {
		return player2;
	}

	/**
	 * This method is used to set the player2.
	 * 
	 * @param player2
	 */
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	/**
	 * This method is used to return the running.
	 * @return running
	 */
	public boolean isRunning() {
		return running;
	}
	/**
	 * This method used to set the running.
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
	/**
	 *  This method is used to return donSignal.
	 * @return donSignal
	 */
	public CountDownLatch getDonSignal() {
		return donSignal;
	}
	/**
	 * This method is used to set the donSignal.
	 * @param donSignal
	 */
	public void setDonSignal(CountDownLatch donSignal) {
		this.donSignal = donSignal;
	}
	/**
	 * This method is used to return the view.
	 * @return view
	 */
	public View getView() {
		return view;
	}
	/**
	 * This method is used to pause the thread when the pause
	 * option is selected. 
	 */
	@SuppressWarnings("deprecation")
	public void pause(){
		trunner.suspend();
		suspended = true;
		view.removeGameMouseListener(game.getMouseListener());
	}
	/**
	 * This method is used to resume the paused thread. 
	 */
	@SuppressWarnings("deprecation")
	public void continueGame(){
		trunner.resume();
		suspended =  false;
		view.addGameMouseListener(game.getMouseListener());
	}
	/**
	 * This method is used to provide hint to the user.
	 * The Monte Carlo Tree search is used to return the 
	 * suggested column to choose. There is also a limit
	 * on the number of times user can use hint, and it is
	 * set to 3. So the user can only use the hint feature 
	 * 3 times.
	 * 
	 * @return the suggested column number
	 */
	public int getHint(){

		if (game.playerID() == 1){
		if (hintCounterp1 > 0 ){
			MCTSPlayer hint = new MCTSPlayer();
			if(running && !game.isWon()){
			Move move = hint.getMove(game);
				hintCounterp1--;		
			
			return move.hashCode();
			}else{
				return -2;
			}
		}
		}
		else{
			if (hintCounterp2 > 0 ){
				MCTSPlayer hint = new MCTSPlayer();
				if(running && !game.isWon()){
				Move move = hint.getMove(game);
					hintCounterp2--;		
				
				return move.hashCode();
				}else{
					return -2;
				}
			}
		}
			return -1;		
		
	}

	public Color getPlayer1Colour() {
		return player1Colour;
	}

	public void setPlayer1Colour(Color player1Colour) {
		this.player1Colour = player1Colour;
	}

	public Color getPlayer2Colour() {
		return player2Colour;
	}

	public void setPlayer2Colour(Color player2Colour) {
		this.player2Colour = player2Colour;
	}

}
