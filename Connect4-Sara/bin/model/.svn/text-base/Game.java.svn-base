/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package model;

import java.util.List;

import controller.GameMouseListener;
import data.Move;

public interface Game {
	/**
	 * Players return a move that the Player wishes to make and that is passed
	 * to makeMove method to be made. How that is achieved is up to the
	 * implementations.
	 * 
	 * @param move to be made
	 * @return true if the move is available to make otherwise returns false
	 */
	boolean makeMove(Move move);

	/**
	 * 
	 * @return the winner
	 */
	int getWinner();

	/**
	 * 
	 * @return true if the game is finished otherwise returns false
	 */
	boolean isWon();

	/**
	 * 
	 * @return a list of Available moves
	 */
	List<Move> getAvailableMoves();

	/**
	 * Undoes the last move by removing it from the list of moves that are
	 * already made, and changes back all the associated fields to the state
	 * they were before the move had been made.
	 * 
	 */
	void undo();

	/**
	 * 
	 * @return the player ID
	 */
	int playerID();

	/**
	 * 
	 * @return the number of moves remaining
	 */
	int movesRemaining();

	/**
	 * The current value of the game. This will be from the point of view of
	 * player 1, so a value of -1 means player 2 is won and a value of 1 means
	 * player 1 is won.
	 * 
	 * @return the value of the game to the player 1
	 */
	int getValue();

	/**
	 * This is used to return the reward for the winner.
	 * if the player 1 is the winner the index 0 will be 
	 * set to 1 otherwise the index 1 will be set to 1.
	 * 
	 * @return an array of double
	 */
	double[] rewards();

	/**
	 * 
	 * @return the number of players
	 */
	int nPlayers();

	/**
	 * 
	 * @return the mouse listener
	 */
	GameMouseListener getMouseListener();

	/**
	 * 
	 * @return the name of the game.
	 */
	String getName();
}
