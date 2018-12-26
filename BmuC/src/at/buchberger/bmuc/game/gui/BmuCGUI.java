package at.buchberger.bmuc.game.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import at.buchberger.bmuc.game.gui.controller.BmucGuiController;
import at.buchberger.bmuc.game.player.Human;
import at.buchberger.bmuc.game.player.ki.CBraham;

public class BmuCGUI {

	private static void createAndShowGUI() throws IOException {

		BmucGuiController controller = new BmucGuiController();

		JFrame frame = new JFrame("BmuChess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar greenMenuBar = new JMenuBar();
		greenMenuBar.setOpaque(true);
		greenMenuBar.setBackground(new Color(154, 165, 127));
		greenMenuBar.setPreferredSize(new Dimension(200, 20));

		JPanel contentPane = new JPanel(new GridBagLayout());
		contentPane.setBackground(new Color(248, 213, 131));
		frame.setContentPane(contentPane);

		frame.setJMenuBar(greenMenuBar);

		ChessBoard chessBoard = new ChessBoard();
		controller.addBoardChangedListener(chessBoard);
		contentPane.add(chessBoard);
		chessBoard.addMouseListener(controller);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// controller.startGame(new Human(), new CBraham());
		controller.startGame(new CBraham(), new Human());
		// controller.playGame(new CBraham(), new BBraham());

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

}
