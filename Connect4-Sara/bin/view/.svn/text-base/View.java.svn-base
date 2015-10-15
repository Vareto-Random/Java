/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package view;

import model.Game;
import controller.GameMouseListener;

public interface View {
	/**
	 * This method updates the board every time a move is done.
	 * @param state The game state
	 */
	void update(Game state);
	/**
	 * This is to add the mouse listener
	 * @param ml mouse listener
	 */
	void addGameMouseListener(GameMouseListener ml);
	/**
	 * This is to remove the mouse listener
	 * @param ml mouse listener
	 */
	void removeGameMouseListener(GameMouseListener ml);
	/**
	 * This method is to set the status bar which displays
	 * which player turn it is.	
	 * @param s The string displayed in the status bar
	 */
	void setStatus(String s);
}
