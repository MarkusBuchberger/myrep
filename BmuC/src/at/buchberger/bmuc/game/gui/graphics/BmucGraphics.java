package at.buchberger.bmuc.game.gui.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import at.buchberger.bmuc.model.piece.Piece;

public class BmucGraphics {

	private static BufferedImage image;

	static {
		try {
			image = ImageIO.read(BmucGraphics.class.getResource("ChessPiecesArray.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Image getPart(int x, int y, int x2, int y2) {
		return image.getSubimage(x, y, x2, y2);
	}

	public static Image getGraphics(Piece piece) {
		int xFactor = 0;
		int yFactor = 0;
		switch (piece.getType()) {
		case BISHOP:
			xFactor = 4;
			break;
		case KING:
			xFactor = 1;
			break;
		case KNIGHT:
			xFactor = 3;
			break;
		case PAWN:
			xFactor = 5;
			break;
		case QUEEN:
			xFactor = 0;
			break;
		case ROOK:
			xFactor = 2;
			break;
		default:
			break;
		}
		switch (piece.getColor()) {
		case BLACK:
			yFactor = 0;
			break;
		case WHITE:
			yFactor = 1;
			break;
		default:
			break;

		}
		return image.getSubimage(xFactor * 60, yFactor * 60,  60,  60);
	}

}
