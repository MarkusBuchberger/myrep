package at.buchberger.bmuc.model.piece;

public enum PieceType {
	KING("K", Integer.MAX_VALUE), QUEEN("Q", 9), ROOK("R", 5), BISHOP("B", 3), KNIGHT("N", 3), PAWN("P", 1);

	private String textRepresentation;
	private int simpleValue;

	private PieceType(String textRepresentation, int simpleValue) {
		this.textRepresentation = textRepresentation;
		this.simpleValue = simpleValue;
	}

	public String getTextRepresentation() {
		return textRepresentation;
	}

	public int getSimpleValue() {
		return simpleValue;
	}
}
