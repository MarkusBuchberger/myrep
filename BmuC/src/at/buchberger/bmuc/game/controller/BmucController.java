package at.buchberger.bmuc.game.controller;

import java.util.Calendar;

import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;
import at.buchberger.bmuc.model.piece.PieceType;

public class BmucController {

	public static double playGame(Player player1, Player player2) {
		Board currentState = new Board();
		initStartingPositions(currentState);

		Player activePlayer = player1;

		int turn = 1;

		while (currentState.getFinalBoardState() == null && !currentState.getFollowingStates(true).isEmpty()) {
			System.out.println("\n turn " + turn + " " + (activePlayer == player1 ? "White:" : "Black"));

			Calendar start = Calendar.getInstance();
			Board nextMove = activePlayer.choseMove(currentState);
			currentState.truncatePaths();
			currentState = nextMove;
			currentState.printBoardToConsole();

			activePlayer = activePlayer == player1 ? player2 : player1;
			if (activePlayer == player1)
				turn++;
			System.out.println("elapsed time: "+(Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis()) + " ms");

		}
		System.out.println(currentState.getFinalBoardState());

		double result = 0;

		switch (currentState.getFinalBoardState()) {
		case CHESSMATE:
			result = activePlayer == player1 ? -1 : 1;
		default:
			break;
		}
		System.out.println(result);

		return result;

	}

	public static void initStartingPositions(Board board) {
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
		// {
		// board.getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		// // board.getPieces()[0][7] = new Piece(PieceColor.WHITE,
		// // PieceType.QUEEN);
		// board.getPieces()[3][6] = new Piece(PieceColor.WHITE, PieceType.KING);
		// board.getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.KING);
		// }

		// {
		// board.getPieces()[7][1] = new Piece(PieceColor.BLACK,
		// PieceType.ROOK);
		// board.getPieces()[6][2] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		// board.getPieces()[6][3] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		// board.getPieces()[6][3] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		// board.getPieces()[6][4] = new Piece(PieceColor.BLACK,
		// PieceType.KING);
		// board.getPieces()[4][2] = new Piece(PieceColor.WHITE,
		// PieceType.PAWN);
		// board.getPieces()[4][4] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		// board.getPieces()[4][6] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		// board.getPieces()[3][2] = new Piece(PieceColor.WHITE,
		// PieceType.QUEEN);
		// board.getPieces()[2][4] = new Piece(PieceColor.WHITE,
		// PieceType.PAWN);
		// board.getPieces()[2][5] = new Piece(PieceColor.WHITE,
		// PieceType.KNIGHT);
		// board.getPieces()[2][7] = new Piece(PieceColor.BLACK,
		// PieceType.PAWN);
		//
		// board.getPieces()[1][1] = new Piece(PieceColor.BLACK,
		// PieceType.QUEEN);
		// board.getPieces()[1][4] = new Piece(PieceColor.WHITE,
		// PieceType.KING);
		//
		// }

		board.internalEvaluation();
		board.calculateThreats();
	}
}
