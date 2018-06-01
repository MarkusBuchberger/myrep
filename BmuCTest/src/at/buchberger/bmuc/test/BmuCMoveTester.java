package at.buchberger.bmuc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;
import at.buchberger.bmuc.model.piece.PieceType;

public class BmuCMoveTester {

	@Test
	public void whiteRochade1() {
		Board board = new Board();
		board.getPieces()[0][4] = new Piece(PieceColor.WHITE, PieceType.KING);
		board.getPieces()[7][4] = new Piece(PieceColor.BLACK, PieceType.KING);
		board.getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.ROOK);
		board.getPieces()[7][0] = new Piece(PieceColor.BLACK, PieceType.ROOK);
		board.getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		board.getPieces()[0][7] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		board.calculateThreats();
		
		try {
			assertEquals(5 + 7 + 7 + 3 + 2 + 2, board.getMoves(PieceColor.WHITE, Integer.MAX_VALUE).size());
		} catch (AssertionError e) {
			board.printAllToConsole();
			throw e;
		}
	}

	@Test
	public void blackRochade1() {
		Board board = new Board();
		board.getPieces()[0][4] = new Piece(PieceColor.WHITE, PieceType.KING);
		board.getPieces()[7][4] = new Piece(PieceColor.BLACK, PieceType.KING);
		board.getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.ROOK);
		board.getPieces()[7][0] = new Piece(PieceColor.BLACK, PieceType.ROOK);
		board.getPieces()[0][0] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		board.getPieces()[0][7] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		board.calculateThreats();
		try {
			assertEquals(5 + 7 + 7 + 3 + 2 + 2, board.getMoves(PieceColor.BLACK, Integer.MAX_VALUE).size());
		} catch (AssertionError e) {
			board.printAllToConsole();
			throw e;
		}
	}
	
	@Test
	public void check1() {
		Board board = new Board();
		board.getPieces()[0][4] = new Piece(PieceColor.WHITE, PieceType.KING);
		board.getPieces()[7][7] = new Piece(PieceColor.BLACK, PieceType.KING);
		board.getPieces()[7][0] = new Piece(PieceColor.BLACK, PieceType.ROOK);
		board.getPieces()[7][2] = new Piece(PieceColor.WHITE, PieceType.ROOK);
		board.calculateThreats();

		try {
			assertEquals(3, board.getMoves(PieceColor.BLACK, Integer.MAX_VALUE).size());
		} catch (AssertionError e) {
			board.printAllToConsole();
			throw e;
		}
	}
	
	@Test
	public void promotion1() {
		Board board = new Board();
		board.getPieces()[1][0] = new Piece(PieceColor.BLACK, PieceType.PAWN);
		board.calculateThreats();

		try {
			assertEquals(4, board.getMoves(PieceColor.BLACK, Integer.MAX_VALUE).size());
		} catch (AssertionError e) {
			board.printAllToConsole();
			throw e;
		}
	}
	
	@Test
	public void promotion2() {
		Board board = new Board();
		board.getPieces()[1][0] = new Piece(PieceColor.BLACK, PieceType.PAWN);
		board.getPieces()[0][1] = new Piece(PieceColor.WHITE, PieceType.KNIGHT);
		board.calculateThreats();

		try {
			assertEquals(8, board.getMoves(PieceColor.BLACK, Integer.MAX_VALUE).size());
		} catch (AssertionError e) {
			board.printAllToConsole();
			throw e;
		}
	}

}
