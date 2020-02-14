package at.buchberger.bmuc.game.gui.controller;

import java.util.List;

import javax.swing.SwingWorker;

import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;

public class BmucSwingGameWorker extends SwingWorker<Board, Board> {

	private BmucGuiController controller;
	private Player player1;
	private Player player2;

	public BmucSwingGameWorker(BmucGuiController controller, Player player1, Player player2) {
		super();
		this.controller = controller;
		this.player1 = player1;
		this.player2 = player2;
	}

	@Override
	protected Board doInBackground() throws Exception {
		Player activePlayer = player1;

		int turn = 1;

		Board currentBoard = controller.getCurrentBoard();

		while (currentBoard.getFinalBoardState() == null && !currentBoard.getFollowingStates(true).isEmpty()) {
			System.out
					.println("\n turn " + turn + " " + (activePlayer == player1 ? "White:" : "Black"));
			Board nextMove = activePlayer.choseMove(currentBoard);
			nextMove.internalEvaluation();

			currentBoard = nextMove;
			publish(currentBoard);
			activePlayer = activePlayer == player1 ? player2 : player1;
			if (activePlayer == player1)
				turn++;

		}
		System.out.println(currentBoard.getFinalBoardState());
		return null;
	}

	protected void process(List<Board> chunks) {
		Board mostRecentValue = chunks.get(chunks.size() - 1);
		controller.setCurrentBoard(mostRecentValue);
		controller.firedBoardChanged();
	}

}
