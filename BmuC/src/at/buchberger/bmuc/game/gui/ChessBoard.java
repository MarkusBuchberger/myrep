package at.buchberger.bmuc.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import at.buchberger.bmuc.game.gui.controller.ModelChangedListener;
import at.buchberger.bmuc.game.model.Board;

public class ChessBoard extends JPanel implements ModelChangedListener {

	private static final long serialVersionUID = -1723689461576406795L;

	public final static int BORDER_SIZE = 10;
	public final static int FIELD_SIZE = 70;

	private List<ChessField> fields = new ArrayList<ChessField>();

	public ChessBoard() {
		super(new GridLayout(8, 8));
		setPreferredSize(new Dimension(8 * FIELD_SIZE + 2 * BORDER_SIZE, 8 * FIELD_SIZE + 2 * BORDER_SIZE));

		Border border = BorderFactory.createLineBorder(new Color(179, 109, 40), BORDER_SIZE);
		setBorder(border);

		for (int i = 7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				ChessField field = new ChessField(i, j);
				fields.add(field);
				add(field);
			}
		}

	}

	@Override
	public void boardChanged(Board currentBoard) {
		for (ChessField field : fields)
			field.boardChanged(currentBoard);
	}

}
