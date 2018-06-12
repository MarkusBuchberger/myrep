package at.buchberger.bmuc.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import at.buchberger.bmuc.game.gui.controller.BoardChangedListener;
import at.buchberger.bmuc.game.model.Board;

public class ChessBoard extends JPanel implements BoardChangedListener {

	private static final long serialVersionUID = -1723689461576406795L;

	private List<ChessField> fields = new ArrayList<ChessField>();
	
	public ChessBoard() {
		super(new GridLayout(8, 8));
		setPreferredSize(new Dimension(580, 580));

		Border border = BorderFactory.createLineBorder(new Color(179, 109, 40), 10);
		setBorder(border);

		for (int i = 7; i > -1; i--) {
			for (int j = 0; j < 8; j++) {
				ChessField field = new ChessField(i, j);
				fields.add(field);
				add(field);
			}
		}

		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				System.out.println(e.getX() + " " + e.getY());
				System.out.println(e.getSource());
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				System.out.println(e.getX() + " " + e.getY());
				System.out.println(e.getSource());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mousePressed(e);
				System.out.println("pressed " + e.getX() + " " + e.getY());
				System.out.println("pressed " + e.getSource());

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseReleased(e);
				System.out.println("released " + e.getX() + " " + e.getY());
				System.out.println("released " + e.getSource());
			}
		};

		addMouseListener(adapter);
	}

	@Override
	public void boardChanged(Board currentBoard) {
		for(ChessField field: fields)
			field.boardChanged(currentBoard);
	}

}
