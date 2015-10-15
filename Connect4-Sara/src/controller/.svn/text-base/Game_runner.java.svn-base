
/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package controller;

import java.util.concurrent.CountDownLatch;

import model.ConnectFour;
import players.GuiPlayer;
import players.Player;
import view.View;

public class Game_runner implements Runnable
{
	private  CountDownLatch donSignal;
	ConnectFour game;
	View view;
	Player player1, player2;
	Controller c;
	
	public Game_runner(ConnectFour game, View view, Player player1,
			Player player2, Controller c, CountDownLatch donSignal) {
		super();
		this.game = game;
		this.view = view;
		this.player1 = player1;
		this.player2 = player2;
		this.c = c;
		this.donSignal = donSignal;
	}
	/**
	 * This method adds the mouse listener and calls the
	 * play method from the Controller to play the game. 
	 */
	@Override
	public void run()
	{
		view.addGameMouseListener(game.getMouseListener());
		if(player1 instanceof GuiPlayer) {
			((GuiPlayer) player1).addMouseListener(game.getMouseListener());
		}

		if(player2 instanceof GuiPlayer) {
			((GuiPlayer) player2).addMouseListener(game.getMouseListener());
		}
		c.play();	
		System.err.printf("Waiting....\n");
		donSignal.countDown();
		System.err.printf("Done\n");
	}
}
