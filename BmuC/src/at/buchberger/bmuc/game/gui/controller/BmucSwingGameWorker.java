package at.buchberger.bmuc.game.gui.controller;

import java.util.Calendar;
import java.util.List;

import javax.swing.SwingWorker;

import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;

public class BmucSwingGameWorker extends SwingWorker<Board, Board> {

	private BmucGuiController controller;

	public BmucSwingGameWorker(BmucGuiController controller) {
		super();
		this.controller = controller;
	}

	@Override
	protected Board doInBackground() throws Exception {
		Player activePlayer = controller.getPlayer1();

		int turn = 1;

		Board currentBoard = controller.getCurrentBoard();

		while (currentBoard.getFinalBoardState() == null && !currentBoard.getFollowingStates(true).isEmpty()) {
			System.out
					.println("\n turn " + turn + " " + (activePlayer == controller.getPlayer1() ? "White:" : "Black"));

			Calendar start = Calendar.getInstance();
			Board nextMove = activePlayer.choseMove(currentBoard);

			currentBoard.truncatePaths();
			currentBoard = nextMove;
			currentBoard.printBoardToConsole();
			System.out.println(
					"elapsed time: " + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + " ms");
			publish(currentBoard);
			activePlayer = activePlayer == controller.getPlayer1() ? controller.getPlayer2() : controller.getPlayer1();
			if (activePlayer == controller.getPlayer1())
				turn++;

		}
		System.out.println(currentBoard.getFinalBoardState());
		return null;
	}

	protected void process(List<Board> chunks) {
		Board mostRecentValue = chunks.get(chunks.size() - 1);
		controller.setCurrentBoard(mostRecentValue);
		controller.tellThoseBloodyListeners();
	}

}
