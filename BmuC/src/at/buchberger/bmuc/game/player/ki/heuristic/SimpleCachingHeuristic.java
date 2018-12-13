package at.buchberger.bmuc.game.player.ki.heuristic;

import java.util.HashMap;

import at.buchberger.algorithms.minmax.Heuristic;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.model.FinalBoardState;
import at.buchberger.bmuc.model.piece.Piece;
import at.buchberger.bmuc.model.piece.PieceColor;

public class SimpleCachingHeuristic implements Heuristic<Board> {

	private PieceColor activeColor;

	private HashMap<Integer, Integer> cache = new HashMap<Integer, Integer>();

	@Override
	public void setInitialState(Board state) {
		activeColor = state.getActivePlayerColor();
	}

	@Override
	public int evaluateGameState(Board gameState, int searchDepth) {

		if (gameState.getFinalBoardState() == FinalBoardState.STALEMATE)
			return 0;
		if (gameState.getFinalBoardState() == FinalBoardState.REPETITION)
			return -100;

		if (gameState.getFinalBoardState() == FinalBoardState.CHESSMATE) // the sooner mate, the better
			return (gameState.getActivePlayerColor() == activeColor ? -1 : 1) * (searchDepth + 1) * 100000;

		Integer entry = cache.get(gameState.getFinalHashCode());
		if (entry != null)
			return entry.intValue();

		int eval = evalPieces(gameState) * 10;

		// return eval;
		if (activeColor == PieceColor.WHITE)
			eval += arraySum(gameState.getWhiteThreats()) - arraySum(gameState.getBlackThreats());
		eval += arraySum(gameState.getBlackThreats()) - arraySum(gameState.getWhiteThreats());

		cache.put(gameState.getFinalHashCode(), eval);
		return eval;
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

	@Override
	public int getCalmnessThreshold() {
		return 80;
	}

}
