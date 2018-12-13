package at.buchberger.bmuc.model.piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.buchberger.bmuc.game.model.Board;

public class Piece {

	private final PieceColor color;
	private final PieceType type;

	public Piece(PieceColor color, PieceType type) {
		super();
		this.color = color;
		this.type = type;
	}

	public PieceColor getColor() {
		return color;
	}

	public PieceType getType() {
		return type;
	}

	public String getTextRepresentation() {
		return color == PieceColor.WHITE ? type.getTextRepresentation() : type.getTextRepresentation().toLowerCase();
	}

	public List<Board> getMoves(int x, int y, Board board) {
		List<Board> moves = new ArrayList<Board>();

		switch (type) {
		case BISHOP:
			doBishopMoves(x, y, board, moves);
			break;
		case KING:
			moves.add(createMove(x, y, x + 1, y, board));
			moves.add(createMove(x, y, x + 1, y + 1, board));
			moves.add(createMove(x, y, x + 1, y - 1, board));
			moves.add(createMove(x, y, x - 1, y, board));
			moves.add(createMove(x, y, x - 1, y + 1, board));
			moves.add(createMove(x, y, x - 1, y - 1, board));
			moves.add(createMove(x, y, x, y - 1, board));
			moves.add(createMove(x, y, x, y + 1, board));

			// rochades
			if (x == 0 && y == 4 && color == PieceColor.WHITE) {
				if (board.isRochadeWhiteLeftViable() && board.getPieces()[0][1] == null
						&& board.getPieces()[0][2] == null && board.getPieces()[0][3] == null
						&& board.getBlackThreats()[0][4] == 0 && board.getBlackThreats()[0][3] == 0
						&& board.getBlackThreats()[0][2] == 0 && board.getPieces()[0][0] != null
						&& board.getPieces()[0][0].getType() == PieceType.ROOK
						&& board.getPieces()[0][0].getColor() == PieceColor.WHITE) {
					moves.add(createRochadeMove(y, 0, 0, -1, board));
				}

				if (board.isRochadeWhiteRightViable() && board.getPieces()[0][6] == null
						&& board.getPieces()[0][5] == null && board.getBlackThreats()[0][4] == 0
						&& board.getBlackThreats()[0][5] == 0 && board.getBlackThreats()[0][6] == 0
						&& board.getPieces()[0][7] != null && board.getPieces()[0][7].getType() == PieceType.ROOK
						&& board.getPieces()[0][7].getColor() == PieceColor.WHITE) {
					moves.add(createRochadeMove(y, 7, 0, 1, board));
				}
			}
			if (x == 7 && y == 4 && color == PieceColor.BLACK) {
				if (board.isRochadeBlackLeftViable() && board.getPieces()[7][1] == null
						&& board.getPieces()[7][2] == null && board.getPieces()[7][3] == null
						&& board.getWhiteThreats()[7][4] == 0 && board.getWhiteThreats()[7][3] == 0
						&& board.getWhiteThreats()[7][2] == 0 && board.getPieces()[7][0] != null
						&& board.getPieces()[7][0].getType() == PieceType.ROOK
						&& board.getPieces()[7][0].getColor() == PieceColor.BLACK) {
					moves.add(createRochadeMove(y, 0, 7, -1, board));
				}

				if (board.isRochadeBlackRightViable() && board.getPieces()[7][6] == null
						&& board.getPieces()[7][5] == null && board.getWhiteThreats()[7][4] == 0
						&& board.getWhiteThreats()[7][5] == 0 && board.getWhiteThreats()[7][6] == 0
						&& board.getPieces()[7][7] != null && board.getPieces()[7][7].getType() == PieceType.ROOK
						&& board.getPieces()[7][7].getColor() == PieceColor.BLACK) {
					moves.add(createRochadeMove(y, 7, 7, 1, board));
				}
			}
			break;
		case KNIGHT:
			moves.add(createMove(x, y, x + 1, y + 2, board));
			moves.add(createMove(x, y, x + 1, y - 2, board));
			moves.add(createMove(x, y, x - 1, y + 2, board));
			moves.add(createMove(x, y, x - 1, y - 2, board));
			moves.add(createMove(x, y, x + 2, y + 1, board));
			moves.add(createMove(x, y, x + 2, y - 1, board));
			moves.add(createMove(x, y, x - 2, y + 1, board));
			moves.add(createMove(x, y, x - 2, y - 1, board));
			break;
		case PAWN:
			int direction = color == PieceColor.WHITE ? 1 : -1;
			if (x + direction < 8 && x + direction > -1) {
				// one ahead
				Piece aheadPiece = board.getPieces()[x + direction][y];
				if (aheadPiece == null) {
					// promote if last line, else normal movement
					if (color == PieceColor.WHITE && x == 6) {
						moves.addAll(createPromotionMoves(7, y, x, y, board));
					} else if (color == PieceColor.BLACK && x == 1) {
						moves.addAll(createPromotionMoves(0, y, x, y, board));
					} else
						moves.add(createMove(x, y, x + direction, y, board));
				}

				// capture + possible promotion
				if (y - 1 > -1) {
					Piece leftPiece = board.getPieces()[x + direction][y - 1];
					if (leftPiece != null && leftPiece.color != color) {
						if (x + direction == 0 || x + direction == 7)
							moves.addAll(createPromotionMoves(x + direction, y - 1, x, y, board));
						else
							moves.add(createMove(x, y, x + direction, y - 1, board));
					}
				}
				if (y + 1 < 8) {
					Piece rightPiece = board.getPieces()[x + direction][y + 1];
					if (rightPiece != null && rightPiece.color != color) {
						if (x + direction == 0 || x + direction == 7)
							moves.addAll(createPromotionMoves(x + direction, y + 1, x, y, board));
						else
							moves.add(createMove(x, y, x + direction, y + 1, board));
					}
				}

				// en passant // TODO DEBUG, rewrite, test
				if ((x == 4 && color == PieceColor.WHITE) || (x == 3 && color == PieceColor.BLACK)) {
					Piece leftPiece = y - 1 > -1 ? board.getPieces()[x][y - 1] : null;
					if (leftPiece != null && leftPiece == board.getEnPassantVulnerable()) {
						moves.add(createMove(x, y, x + direction, y - 1, board, x, y - 1, null));
					}

					Piece rightPiece = y + 1 < 8 ? board.getPieces()[x][y + 1] : null;
					if (rightPiece != null && rightPiece == board.getEnPassantVulnerable()) {
						moves.add(createMove(x, y, x + direction, y + 1, board, x, y + 1, null));
					}
				}

			}
			// two ahead
			if (x == 1 && color == PieceColor.WHITE || x == 6 && color == PieceColor.BLACK) {
				Piece aheadPiece = board.getPieces()[x + direction][y];
				Piece aheadAheadPiece = board.getPieces()[x + direction * 2][y];
				if (aheadPiece == null && aheadAheadPiece == null) {
					Board move = createMove(x, y, x + direction * 2, y, board);
					if (move != null)
						move.setEnPassantVulnerable(this);
					moves.add(move);
				}
			}

			break;
		case QUEEN:
			doBishopMoves(x, y, board, moves);
			doRookMoves(x, y, board, moves);
			break;
		case ROOK:
			doRookMoves(x, y, board, moves);
			break;
		default:
			break;
		}
		moves.removeAll(Collections.singleton(null));
		return moves;
	}

	public void addThreats(int x, int y, Board board, boolean white) {

		switch (type) {
		case BISHOP:
			addBishopThreats(x, y, board, white);
			break;
		case KING:
			addThreatToArray(x + 1, y, board, white);
			addThreatToArray(x + 1, y + 1, board, white);
			addThreatToArray(x + 1, y - 1,  board, white);
			addThreatToArray(x - 1, y, board, white);
			addThreatToArray(x - 1, y + 1, board, white);
			addThreatToArray(x - 1, y - 1, board, white);
			addThreatToArray(x, y - 1, board, white);
			addThreatToArray(x, y + 1, board, white);
			break;
		case KNIGHT:
			addThreatToArray(x + 1, y + 2, board, white);
			addThreatToArray(x + 1, y - 2, board, white);
			addThreatToArray(x - 1, y + 2, board, white);
			addThreatToArray(x - 1, y - 2, board, white);
			
			addThreatToArray(x + 2, y + 1, board, white);
			addThreatToArray(x + 2, y - 1, board, white);
			addThreatToArray(x - 2, y + 1, board, white);
			addThreatToArray(x - 2, y - 1, board, white);
			
			break;
		case PAWN:
			int direction = color == PieceColor.WHITE ? 1 : -1;
			addThreatToArray(x + direction, y - 1, board, white);
			addThreatToArray(x + direction, y + 1, board, white);
			break;
		case QUEEN:
			addBishopThreats(x, y, board, white);
			addRookThreats(x, y, board, white);
			break;
		case ROOK:
			addRookThreats(x, y, board, white);
			break;
		default:
			break;
		}
	}

	private void addThreatToArray(int x, int y, Board board, boolean white) {
		if (x > -1 && x < 8 && y > -1 && y < 8)
			if (white)
				board.whiteThreats[x][y]++;
			else
				board.blackThreats[x][y]++;
	}

	public Board createMove(int origX, int origY, int x, int y, Board board) {
		return createMove(origX, origY, x, y, board, -1, -1, null);
	}

	private Board createRochadeMove(int kingY, int RookY, int x, int kingDirection, Board board) {
		Board rochadeMove = new Board(board);
		rochadeMove.setLastMoved(this);

		Piece king = rochadeMove.getPieces()[x][kingY];
		rochadeMove.getPieces()[x][kingY] = null;
		rochadeMove.getPieces()[x][kingY + kingDirection * 2] = king;
		rochadeMove.setLastMoveX(x);
		rochadeMove.setLastMoveY(kingY + kingDirection * 2);
		Piece rook = rochadeMove.getPieces()[x][RookY];
		rochadeMove.getPieces()[x][RookY] = null;
		rochadeMove.getPieces()[x][kingY + kingDirection * 2 - kingDirection] = rook;

		if (color == PieceColor.WHITE) {
			rochadeMove.setRochadeWhiteLeftViable(false);
			rochadeMove.setRochadeWhiteRightViable(false);
		} else {
			rochadeMove.setRochadeBlackLeftViable(false);
			rochadeMove.setRochadeBlackRightViable(false);
		}

		return createMove(-1, -1, -1, -1, board, -1, -1, rochadeMove);
	}

	private Collection<Board> createPromotionMoves(int x, int y, int remX, int remY, Board board) {
		Set<Board> promotionMoves = new HashSet<Board>();
		{
			Board promotionMove = new Board(board);
			promotionMove.setLastMoved(this);
			promotionMove.getPieces()[remX][remY] = null;
			Piece piece = new Piece(color, PieceType.BISHOP);
			promotionMove.getPieces()[x][y] = piece;
			promotionMove.setLastMoveX(x);
			promotionMove.setLastMoveY(y);
			promotionMoves.add(createMove(-1, -1, -1, -1, board, -1, -1, promotionMove));
		}

		{
			Board promotionMove = new Board(board);
			promotionMove.setLastMoved(this);
			promotionMove.getPieces()[remX][remY] = null;
			Piece piece = new Piece(color, PieceType.KNIGHT);
			promotionMove.getPieces()[x][y] = piece;
			promotionMove.setLastMoveX(x);
			promotionMove.setLastMoveY(y);
			promotionMoves.add(createMove(-1, -1, -1, -1, board, -1, -1, promotionMove));
		}

		{
			Board promotionMove = new Board(board);
			promotionMove.setLastMoved(this);
			promotionMove.getPieces()[remX][remY] = null;
			Piece piece = new Piece(color, PieceType.ROOK);
			promotionMove.getPieces()[x][y] = piece;
			promotionMove.setLastMoveX(x);
			promotionMove.setLastMoveY(y);
			promotionMoves.add(createMove(-1, -1, -1, -1, board, -1, -1, promotionMove));
		}

		{
			Board promotionMove = new Board(board);
			promotionMove.setLastMoved(this);
			promotionMove.getPieces()[remX][remY] = null;
			Piece piece = new Piece(color, PieceType.QUEEN);
			promotionMove.getPieces()[x][y] = piece;
			promotionMove.setLastMoveX(x);
			promotionMove.setLastMoveY(y);
			promotionMoves.add(createMove(-1, -1, -1, -1, board, -1, -1, promotionMove));
		}
		return promotionMoves;
	}

	private Board createMove(int origX, int origY, int x, int y, Board board, int remX, int remY, Board customMove) {
		Board move = customMove;

		if (move == null && x > -1 && x < 8 && y > -1 && y < 8) {

			Piece targetPiece = board.getPieces()[x][y];
			if ((targetPiece == null || targetPiece.getColor() != this.getColor())) {

				move = new Board(board);
				move.setLastMoved(this);

				move.getPieces()[origX][origY] = null;
				move.getPieces()[x][y] = this;
				move.setLastMoveX(x);
				move.setLastMoveY(y);

				if (remX > -1 && remY > -1)
					move.getPieces()[remX][remY] = null;

				// rochade memory rooks
				if (origX == 0 && origY == 0 && this.getType() == PieceType.ROOK && this.getColor() == PieceColor.WHITE)
					move.setRochadeWhiteLeftViable(false);
				if (x == 0 && y == 0) // could be capture
					move.setRochadeWhiteLeftViable(false);

				if (origX == 0 && origY == 7 && this.getType() == PieceType.ROOK && this.getColor() == PieceColor.WHITE)
					move.setRochadeWhiteRightViable(false);
				if (x == 0 && y == 7) // could be capture
					move.setRochadeWhiteRightViable(false);

				if (origX == 7 && origY == 0 && this.getType() == PieceType.ROOK && this.getColor() == PieceColor.BLACK)
					move.setRochadeBlackLeftViable(false);
				if (x == 7 && y == 0) // could be capture
					move.setRochadeBlackLeftViable(false);

				if (origX == 7 && origY == 7 && this.getType() == PieceType.ROOK && this.getColor() == PieceColor.BLACK)
					move.setRochadeBlackRightViable(false);
				if (x == 7 && y == 7) // could be capture
					move.setRochadeBlackRightViable(false);

				// rochade memory kings

				if (origX == 0 && origY == 4 && this.getType() == PieceType.KING
						&& this.getColor() == PieceColor.WHITE) {
					move.setRochadeWhiteLeftViable(false);
					move.setRochadeWhiteRightViable(false);
				}

				if (origX == 7 && origY == 4 && this.getType() == PieceType.KING
						&& this.getColor() == PieceColor.BLACK) {
					move.setRochadeBlackLeftViable(false);
					move.setRochadeBlackRightViable(false);
				}

			}
		}

		// chess check, is move finally viable?
		if (move != null) {
			move.calculateThreats();
			if (!move.isKingThreated(this.color)) {
				move.setFinalHashCode(move.hashCode());
				return move;
			}
		}
		return null;
	}

	private void doBishopMoves(int x, int y, Board board, List<Board> moves) {
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
	}

	private void addBishopThreats(int x, int y, Board board, boolean white) {
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
	}

	private void doRookMoves(int x, int y, Board board, List<Board> moves) {
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				moves.add(createMove(x, y, xt, yt, board));
			}
		}
	}

	private void addRookThreats(int x, int y, Board board, boolean white) {
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && ++xt > -1 && xt < 8 && yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && --xt > -1 && xt < 8 && yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && xt > -1 && xt < 8 && ++yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
		{
			int xt = x, yt = y;
			boolean collision = false;
			while (!collision && xt > -1 && xt < 8 && --yt > -1 && yt < 8) {
				collision |= board.getPieces()[xt][yt] != null;
				if (white)
					board.whiteThreats[xt][yt]++;
				else
					board.blackThreats[xt][yt]++;
			}
		}
	}

}
