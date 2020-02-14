package at.buchberger.bmuc.game.player;

import at.buchberger.bmuc.game.gui.controller.GUIInputListener;
import at.buchberger.bmuc.game.model.Board;

public class Human implements Player, GUIInputListener {

	private Board nextMove = null;

	private Object lock = new Object();

	@Override
	public Board choseMove(Board board) {
		synchronized (lock) {
			try {
				System.out.println("waiting for player move");
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Board temp = nextMove;
		nextMove = null;
		return temp;
	}

	@Override
	public boolean isHuman() {
		return true;
	}

	@Override
	public void moveChosen(Board chosenMove) {
		nextMove = chosenMove;
		synchronized (lock) {
			lock.notify();
		}
	}
}
