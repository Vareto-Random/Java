/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package players;

/**
 * This abstract class makes Player implementations a bit neater
 * 
 */
public abstract class AbstractPlayer implements Player {

	protected int playerID;
	protected boolean interupted;
	public final static int DRAW = 0;
	public final static int WON = 1;
	public final static int LOST = 2;
	public final static int INTERRUPTED = 3;

	@Override
	public int getPlayerID() {
		return playerID;
	}

	@Override
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	@Override
	public void reset() {
		interupted = false;

	}

	@Override
	public void interupt() {
		interupted = true;
	}

	@Override
	public void endGame(int status) {

	}

}
