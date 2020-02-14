package at.buchberger.bmuc.game.gui.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import at.buchberger.bmuc.game.gui.ChessBoard;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;
import at.buchberger.bmuc.model.piece.PieceType;

public class BmucGuiController extends MouseAdapter {

	private Board currentBoard = null;

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	private List<ModelChangedListener> boardChangedListeners = new ArrayList<ModelChangedListener>();

	private List<GUIInputListener> guiInputListeners = new ArrayList<>();

	public void startGame(Player player1, Player player2) {
		guiInputListeners.clear();
		this.currentBoard = new Board();
		initStartingPositions(currentBoard);
		firedBoardChanged();

		if (player1 instanceof GUIInputListener)
			guiInputListeners.add((GUIInputListener) player1);
		if (player2 instanceof GUIInputListener)
			guiInputListeners.add((GUIInputListener) player2);

		new BmucSwingGameWorker(this, player1, player2).execute();
	}

	private int currentPressedFieldX = -1;
	private int currentPressedFieldY = -1;

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		currentPressedFieldX = calcField(e.getY(), false);
		currentPressedFieldY = calcField(e.getX(), true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		int releasedFieldX = calcField(e.getY(), false);
		int releasedFieldY = calcField(e.getX(), true);

		if (currentPressedFieldX != -1 && currentPressedFieldY != -1 && releasedFieldX != -1 && releasedFieldY != -1) {
			Board chosenMove = null;
			for (Board nextMove : currentBoard.getMoves(currentPressedFieldX, currentPressedFieldY)) {
				// TODO: Multiple moves possible in case of promotion!!! let player chose or
				// take queen?
				if (nextMove.getLastMoveX() == releasedFieldX && nextMove.getLastMoveY() == releasedFieldY)
					chosenMove = nextMove;
			}

			if (chosenMove != null)
				for (GUIInputListener listener : guiInputListeners)
					listener.moveChosen(chosenMove);
		}
		// reset
		currentPressedFieldX = -1;
		currentPressedFieldY = -1;

	}

	private int calcField(int pixelVal, boolean yValue) {
		int withoutBorder = pixelVal - ChessBoard.BORDER_SIZE;

		int field = withoutBorder / ChessBoard.FIELD_SIZE;
		if (field > -1 && field < 8) {
			return yValue ? field : 7 - field;
		}
		return -1;
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

	public void firedBoardChanged() {
		for (ModelChangedListener listener : boardChangedListeners)
			listener.boardChanged(currentBoard);
	}

	public void addBoardChangedListener(ModelChangedListener listener) {
		this.boardChangedListeners.add(listener);
	}

	public void addGuiInputListener(GUIInputListener listener) {
		guiInputListeners.add(listener);
	}

	public void removeAllGuiInputListeners() {
		guiInputListeners.clear();
	}

}
