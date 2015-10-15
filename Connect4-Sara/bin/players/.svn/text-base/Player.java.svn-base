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
import data.Move;

public interface Player {
	/**
	 * This method returns the move that player wishes to
	 * make.
	 * @param game The game state
	 * @return The chosen move
	 */
	Move getMove(Game game);
	/**
	 * This is used to return the name of the player.
	 * @return player name
	 */
	String getName();
	/**
	 * 
	 * @return the Player ID
	 */
	int getPlayerID();
	/**
	 * This method is used to set the players ID
	 * @param playerID Player ID (1 or 2)
	 */
	void setPlayerID(int playerID);
	/**
	 * If a game is restarted, any data structures used by
	 * AI players will need to be reset.
	 */
	void reset();
	/**
	 * This method is used to notify any threads being used
	 * by Players that they are being interrupted.
	 */
	void interupt();
	/**
	 * This method tells the status of the game
	 * to the player once the game is finished. 
	 */
	void endGame(int status);
}
