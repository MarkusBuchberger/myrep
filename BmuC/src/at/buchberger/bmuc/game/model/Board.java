package at.buchberger.bmuc.game.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.buchberger.algorithms.minmax.GameState;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;
import at.buchberger.bmuc.model.piece.PieceType;

public class Board extends GameState<Board> {

	private final Piece[][] pieces;
	private Board lastBoard;
	private Piece lastMoved;

	public final int[][] whiteThreats;

	public final int[][] blackThreats;

	private boolean rochadeWhiteLeftViable = true;
	private boolean rochadeWhiteRightViable = true;

	private boolean rochadeBlackLeftViable = true;
	private boolean rochadeBlackRightViable = true;

	private Piece enPassantVulnerable;
	private FinalBoardState finalBoardState = null;

	private int finalHashCode;

	private List<Board> tempFollowingStates = null;

	private int lastMoveX;
	private int lastMoveY;

	public Board() {
		pieces = new Piece[8][];
		for (int i = 0; i < 8; i++)
			pieces[i] = new Piece[8];

		whiteThreats = new int[8][];
		for (int i = 0; i < 8; i++)
			whiteThreats[i] = new int[8];

		blackThreats = new int[8][];
		for (int i = 0; i < 8; i++)
			blackThreats[i] = new int[8];
	}

	public Board(Board toShallowCopy) {
		this();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				pieces[i][j] = toShallowCopy.pieces[i][j];

		lastBoard = toShallowCopy;
		rochadeWhiteLeftViable = toShallowCopy.rochadeWhiteLeftViable;
		rochadeWhiteRightViable = toShallowCopy.rochadeWhiteRightViable;
		rochadeBlackLeftViable = toShallowCopy.rochadeBlackLeftViable;
		rochadeBlackRightViable = toShallowCopy.rochadeBlackRightViable;
	}

	public void calculateThreats() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (pieces[i][j] != null) {
					if (pieces[i][j].getColor() == PieceColor.WHITE)
						pieces[i][j].addThreats(i, j, this, true);
					else
						pieces[i][j].addThreats(i, j, this, false);
				}
			}
		}
	}

	public Collection<Board> getMoves(PieceColor color) {
		return getMoves(color, -1, -1, false);
	}

	public Collection<Board> getMoves(int x, int y) {
		return getMoves(getActivePlayerColor(), x, y, false);
	}

	public Collection<Board> getMoves(PieceColor color, int onlyX, int onlyY, boolean onlyCheckExists) {
		List<Board> moves = new ArrayList<Board>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = this.getPieces()[i][j];
				if (piece != null && (color == null || piece.getColor() == color)
						&& (onlyX == -1 || onlyY == -1 || (onlyX == i && onlyY == j))) {
					moves.addAll(piece.getMoves(i, j, this));
				}
				if (onlyCheckExists && moves.size() > 0)
					return moves;
			}
		}
		if (!onlyCheckExists) {
			for (Board move : moves) {
				move.internalEvaluation();
			}
		}
		return moves;
	}

	public void printAllToConsole() {
		printBoardToConsole();
		printThreatsToConsole();

		System.out.println("\nWhite moves:");
		for (Board move : getMoves(PieceColor.WHITE)) {
			move.printBoardToConsole();
			move.printThreatsToConsole();
		}

		System.out.println("\nBlack moves:");
		for (Board move : getMoves(PieceColor.BLACK)) {
			move.printBoardToConsole();
			move.printThreatsToConsole();
		}
	}

	public void printBoardToConsole() {
		System.out.println();
		for (int i = 7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				if (j != 0)
					System.out.print("|");
				if (pieces[i][j] != null)
					System.out.print(pieces[i][j].getTextRepresentation());
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	public void printThreatsToConsole() {
		System.out.println();
		System.out.println("White Threats:");
		for (int i = 7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				if (j != 0)
					System.out.print("|");
				if (whiteThreats[i][j] > 0)
					System.out.print(whiteThreats[i][j]);
				else
					System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Black Threats:");
		for (int i = 7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				if (j != 0)
					System.out.print("|");
				if (blackThreats[i][j] > 0)
					System.out.print(blackThreats[i][j]);
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}

	public Piece[][] getPieces() {
		return pieces;
	}

	public Board getLastBoard() {
		return lastBoard;
	}

	public Piece getLastMoved() {
		return lastMoved;
	}

	public void setLastMoved(Piece lastMoved) {
		this.lastMoved = lastMoved;
	}

	public boolean isRochadeWhiteLeftViable() {
		return rochadeWhiteLeftViable;
	}

	public void setRochadeWhiteLeftViable(boolean rochadeWhiteLeftViable) {
		this.rochadeWhiteLeftViable = rochadeWhiteLeftViable;
	}

	public boolean isRochadeWhiteRightViable() {
		return rochadeWhiteRightViable;
	}

	public void setRochadeWhiteRightViable(boolean rochadeWhiteRightViable) {
		this.rochadeWhiteRightViable = rochadeWhiteRightViable;
	}

	public boolean isRochadeBlackLeftViable() {
		return rochadeBlackLeftViable;
	}

	public void setRochadeBlackLeftViable(boolean rochadeBlackLeftViable) {
		this.rochadeBlackLeftViable = rochadeBlackLeftViable;
	}

	public boolean isRochadeBlackRightViable() {
		return rochadeBlackRightViable;
	}

	public void setRochadeBlackRightViable(boolean rochadeBlackRightViable) {
		this.rochadeBlackRightViable = rochadeBlackRightViable;
	}

	public Set<Coordinate> getCoordinates(PieceColor color, PieceType type) {
		Set<Coordinate> coordinates = new HashSet<>();
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (pieces[i][j] != null && pieces[i][j].getColor() == color && pieces[i][j].getType() == type)
					coordinates.add(new Coordinate(i, j));

		return coordinates;
	}

	public int[][] getWhiteThreats() {
		return whiteThreats;
	}

	public int[][] getBlackThreats() {
		return blackThreats;
	}

	public boolean existsFollowingState() {
		return !getFollowingStates(true).isEmpty();
	}

	public List<Board> getFollowingStates() {
		return getFollowingStates(false);
	}

	public List<Board> getFollowingStates(boolean onlyCheckExists) {
		if (tempFollowingStates != null)
			return tempFollowingStates;
		List<Board> states = new ArrayList<Board>(getMoves(getActivePlayerColor(), -1, -1, onlyCheckExists));
		// if (!onlyCheckExists)
		// tempFollowingStates = states;
		return states;
	}

	public PieceColor getActivePlayerColor() {
		return lastMoved == null || lastMoved.getColor() == PieceColor.BLACK ? PieceColor.WHITE : PieceColor.BLACK;
	}

	public Piece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public void setEnPassantVulnerable(Piece enPassantVulnerable) {
		this.enPassantVulnerable = enPassantVulnerable;
	}

	public boolean isKingThreated(PieceColor color) {
		Set<Coordinate> coordinates = this.getCoordinates(color, PieceType.KING);
		boolean kingThreated = false;
		if (!coordinates.isEmpty()) {
			Coordinate activeKing = coordinates.iterator().next();
			kingThreated = color == PieceColor.WHITE ? this.getBlackThreats()[activeKing.getX()][activeKing.getY()] > 0
					: this.getWhiteThreats()[activeKing.getX()][activeKing.getY()] > 0;
		}
		return kingThreated;
	}

	public FinalBoardState getFinalBoardState() {
		return finalBoardState;
	}

	public void setFinalBoardState(FinalBoardState finalBoardState) {
		this.finalBoardState = finalBoardState;
	}

	@Override
	public void internalEvaluation() {
		if (getFollowingStates(true).isEmpty()) {
			if (isKingThreated(getActivePlayerColor()))
				finalBoardState = FinalBoardState.CHESSMATE;
			else
				finalBoardState = FinalBoardState.STALEMATE;

		} else

		{
			int repetitionCount = 1;
			Board last = lastBoard;
			while (last != null) {
				if (last.getFinalHashCode() == finalHashCode && last.equals(this))
					repetitionCount++;
				last = last.getLastBoard();
			}
			if (repetitionCount > 2) {
				finalBoardState = FinalBoardState.REPETITION;
			}
		}
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 instanceof Board) {
			Board b2 = (Board) arg0;
			if (rochadeBlackLeftViable == b2.rochadeBlackLeftViable
					&& rochadeBlackRightViable == b2.rochadeBlackRightViable
					&& rochadeWhiteLeftViable == b2.rochadeWhiteLeftViable
					&& rochadeWhiteRightViable == b2.rochadeWhiteRightViable
					&& enPassantVulnerable == b2.enPassantVulnerable) {
				boolean piecesEqual = true;
				for (int i = 0; i < 8; i++)
					piecesEqual &= Arrays.equals(pieces[i], b2.pieces[i]);
				return piecesEqual;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		int code = 2;
		code = rochadeWhiteLeftViable ? code * 3 : code;
		code = rochadeWhiteRightViable ? code * 5 : code;
		code = rochadeBlackLeftViable ? code * 7 : code;
		code = rochadeBlackRightViable ? code * 11 : code;
		code = enPassantVulnerable != null ? code * 13 : code;
		code = getActivePlayerColor() == PieceColor.WHITE ? code * 27 : code;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (pieces[i][j] != null)
					code += (i + 1) * (j + 1) * (pieces[i][j].getColor().ordinal() + 1)
							* (pieces[i][j].getType().ordinal() + 1);
		return code;
	}

	public int getFinalHashCode() {
		return finalHashCode;
	}

	public void setFinalHashCode(int finalHashCode) {
		this.finalHashCode = finalHashCode;
	}

	@Override
	public boolean isFinalMove() {
		return finalBoardState != null;
	}

	@Override
	public Board getPreviousState() {
		return lastBoard;
	}

	public int getLastMoveX() {
		return lastMoveX;
	}

	public void setLastMoveX(int lastMoveX) {
		this.lastMoveX = lastMoveX;
	}

	public int getLastMoveY() {
		return lastMoveY;
	}

	public void setLastMoveY(int lastMoveY) {
		this.lastMoveY = lastMoveY;
	}

	@Override
	public boolean isKillerMove(Board killerMove) {
		return killerMove.lastMoveX == lastMoveX && killerMove.lastMoveY == lastMoveY
				&& killerMove.lastMoved == lastMoved;
	}
}
