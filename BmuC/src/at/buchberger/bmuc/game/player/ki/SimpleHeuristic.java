package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.Heuristic;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.model.FinalBoardState;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;

public class SimpleHeuristic implements Heuristic<Board> {

	private PieceColor activeColor;

	@Override
	public void setInitialState(Board state) {
		activeColor = state.getActivePlayerColor();
	}

	@Override
	public int evaluateGameState(Board gameState, int searchDepth) {
		gameState.internalEvaluation();

		if (gameState.getFinalBoardState() == FinalBoardState.STALEMATE)
			return 0;
		if (gameState.getFinalBoardState() == FinalBoardState.REPETITION)
			return 0;

		if (gameState.getFinalBoardState() == FinalBoardState.CHESSMATE)
			return (searchDepth + 1) * 100000; // the sooner mate, the better

		int eval = evalPieces(gameState) * 10;

		// return eval;
		if (activeColor == PieceColor.WHITE)
			return eval + arraySum(gameState.getWhiteThreats()) - arraySum(gameState.getBlackThreats());
		return eval + arraySum(gameState.getBlackThreats()) - arraySum(gameState.getWhiteThreats());
	}

	private int arraySum(int[][] array) {
		int sum = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				sum += array[i][j];
		return sum;
	}

	private int evalPieces(Board gameState) {
		Piece[][] pieces = gameState.getPieces();
		int sum = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (pieces[i][j] != null)
					sum += pieces[i][j].getType().getSimpleValue() * (pieces[i][j].getColor() == activeColor ? 1 : -1);
		return sum;
	}

}
