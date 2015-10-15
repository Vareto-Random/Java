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
import controller.GameMouseListener;
import data.Move;
/*
 * Human player
 */
public class GuiPlayer extends AbstractPlayer {

	protected GameMouseListener ml;
	protected Move move = null;
	
	public GuiPlayer() {
		interupted = false;
	}
	
	@Override
	public Move getMove(Game game) {
		interupted = false;
//		System.out.println("GUI Player makeMove() " + game.getClass().getSimpleName());
		ml.setPlayerID(playerID);
		
		/*
		 *  Humans make moves by clicking on the GUI.
		 *  This method waits for the user's choice to
		 *  come through from the GUI then returns it.
		 */
		while(move == null && !interupted) {
			move = ml.getMove();
		}
		
		Move temp = move;
		move = null;
		return temp;
	}
	
	public void interupt() {
		super.interupt();
		ml.interupted = true;
	}

	@Override
	public String getName() {
		return "GUI Player";
	}

	
	public void addMouseListener(GameMouseListener ml) {
//		System.out.println("GUI Player adding mouse listener");
		this.ml = ml;
	}

	@Override
	public void reset() {
		super.reset();
		ml.interupted = false;
		
	}

}
