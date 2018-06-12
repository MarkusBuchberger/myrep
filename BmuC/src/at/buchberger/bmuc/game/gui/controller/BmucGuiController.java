package at.buchberger.bmuc.game.gui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;
import at.buchberger.bmuc.model.piece.PieceType;

public class BmucGuiController {

	private Board currentBoard = null;
	private Player player1;
	private Player player2;

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	private List<BoardChangedListener> boardChangedListeners = new ArrayList<BoardChangedListener>();

	public void startGame(Player player1, Player player2) {
		this.currentBoard = new Board();
		this.setPlayer1(player1);
		this.setPlayer2(player2);
		initStartingPositions(currentBoard);
		tellThoseBloodyListeners();
		
		new BmucSwingGameWorker(this).execute();
	}

	public void playGame(Player player1, Player player2) {

		Player activePlayer = player1;

		int turn = 1;

		while (currentBoard.getFinalBoardState() == null && !currentBoard.getFollowingStates(true).isEmpty()) {
			System.out.println("\n turn " + turn + " " + (activePlayer == player1 ? "White:" : "Black"));

			Calendar start = Calendar.getInstance();
			Board nextMove = activePlayer.choseMove(currentBoard);

			currentBoard.truncatePaths();
			currentBoard = nextMove;
			currentBoard.printBoardToConsole();
			System.out.println(
					"elapsed time: " + (Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + " ms");
			tellThoseBloodyListeners();
			activePlayer = activePlayer == player1 ? player2 : player1;
			if (activePlayer == player1)
				turn++;

		}
		System.out.println(currentBoard.getFinalBoardState());
	}

	private void initStartingPositions(Board board) {
		{
			board.getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK);
			board.getPieces()[0][1] = new Piece(PieceColor.WHITE, PieceType.KNIGHT);
			board.getPieces()[0][2] = new Piece(PieceColor.WHITE, PieceType.BISHOP);
			board.getPieces()[0][3] = new Piece(PieceColor.WHITE, PieceType.QUEEN);
			board.getPieces()[0][4] = new Piece(PieceColor.WHITE, PieceType.KING);
			board.getPieces()[0][5] = new Piece(PieceColor.WHITE, PieceType.BISHOP);
			board.getPieces()[0][6] = new Piece(PieceColor.WHITE, PieceType.KNIGHT);
			board.getPieces()[0][7] = new Piece(PieceColor.WHITE, PieceType.ROOK);

			board.getPieces()[1][0] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][1] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][2] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][3] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][4] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][5] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][6] = new Piece(PieceColor.WHITE, PieceType.PAWN);
			board.getPieces()[1][7] = new Piece(PieceColor.WHITE, PieceType.PAWN);

			board.getPieces()[7][0] = new Piece(PieceColor.BLACK, PieceType.ROOK);
			board.getPieces()[7][1] = new Piece(PieceColor.BLACK, PieceType.KNIGHT);
			board.getPieces()[7][2] = new Piece(PieceColor.BLACK, PieceType.BISHOP);
			board.getPieces()[7][3] = new Piece(PieceColor.BLACK, PieceType.QUEEN);
			board.getPieces()[7][4] = new Piece(PieceColor.BLACK, PieceType.KING);
			board.getPieces()[7][5] = new Piece(PieceColor.BLACK, PieceType.BISHOP);
			board.getPieces()[7][6] = new Piece(PieceColor.BLACK, PieceType.KNIGHT);
			board.getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.ROOK);

			board.getPieces()[6][0] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][1] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][2] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][3] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][4] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][5] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][6] = new Piece(PieceColor.BLACK, PieceType.PAWN);
			board.getPieces()[6][7] = new Piece(PieceColor.BLACK, PieceType.PAWN);
		}

		board.internalEvaluation();
		board.calculateThreats();
	}

	public void tellThoseBloodyListeners() {
		for (BoardChangedListener listener : boardChangedListeners)
			listener.boardChanged(currentBoard);
	}

	public void addBoardChangedListener(BoardChangedListener listener) {
		this.boardChangedListeners.add(listener);
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
}
