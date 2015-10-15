/**
 * Connect-Four
 * Submitted for the Degree of B.Sc. in Computer Science, 2013/2014
 * University of Strathclyde
 * Department of Computer and Information Sciences
 * @author Sara Torabi
 *
 */
package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import data.Move;

public abstract class GameMouseListener implements MouseListener {

	protected Move move = null;
	protected int playerID = 0;
	public boolean interupted = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	public Move getMove() {
//		interupted = false;
//		System.out.println("GML getMove()");
		while (move == null && !this.interupted ) {
//			System.out.print("." + this.interupted);
//			System.out.println("ThreadID: " + Thread.currentThread());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {}
//			System.out.println(">>>>> " + move);
		}
//		System.out.println("GML getMove() Done " + move);
		Move temp = move;
		move = null;
		playerID = 0;
		return temp;
	}

}