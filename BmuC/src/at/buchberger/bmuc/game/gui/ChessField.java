package at.buchberger.bmuc.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;

import at.buchberger.bmuc.game.gui.controller.ModelChangedListener;
import at.buchberger.bmuc.game.gui.graphics.BmucGraphics;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.model.piece.Piece;

public class ChessField extends JLabel implements ModelChangedListener {

	private Board currentBoard = null;
	private int x;
	private int y;

	private static final long serialVersionUID = 5064461372660229366L;

	public ChessField(int i, int j) {
		this.x = i;
		this.y = j;
		setBackground((i + j) % 2 == 1 ? new Color(255, 206, 158) : new Color(209, 139, 71));
		setOpaque(true);
		setPreferredSize(new Dimension(ChessBoard.FIELD_SIZE, ChessBoard.FIELD_SIZE));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Piece piece = currentBoard != null ? currentBoard.getPieces()[x][y] : null;
		if (piece != null)
			g.drawImage(BmucGraphics.getGraphics(piece), ChessBoard.BORDER_SIZE / 2, ChessBoard.BORDER_SIZE / 2, null);
		if (currentBoard.getLastMoveX() == x && currentBoard.getLastMoveY() == y) {
			g.setColor(Color.YELLOW);
			g.drawRect(0, 0, ChessBoard.FIELD_SIZE - 1, ChessBoard.FIELD_SIZE - 1);
		}
	}

	@Override
	public void boardChanged(Board currentBoard) {
		this.currentBoard = currentBoard;
		repaint();
	}

}
