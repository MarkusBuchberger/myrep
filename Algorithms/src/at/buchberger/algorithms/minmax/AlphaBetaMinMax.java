package at.buchberger.algorithms.minmax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlphaBetaMinMax<T extends GameState<T>> {

	private Heuristic<T> heuristic;
	private int maxMoves;
	private boolean useAlphaBetaPruning;
	private int minimumDepth;

	public AlphaBetaMinMax(Heuristic<T> heuristic, int maxMoves, int minimumDepth, boolean useAlphaBetaPruning) {
		super();
		this.heuristic = heuristic;
		this.maxMoves = maxMoves;
		this.minimumDepth = minimumDepth;
		this.useAlphaBetaPruning = useAlphaBetaPruning;
	}

	private int moves = 0;


	public T chooseMove(T state) {
		heuristic.setInitialState(state);
		moves = 0;

		buildMinMaxTree(state, minimumDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

		System.out.println(moves + " moves to calculate");
		int bestMinMax = Integer.MIN_VALUE;
		T bestMove = null;
		List<T> list = new ArrayList<T>(state.getChildren());
		Collections.shuffle(list);
		for (T child : list) {
			System.out.print(child.getMinMax() + " ");
			if (child.getMinMax() >= bestMinMax) {
				bestMove = child;
				bestMinMax = child.getMinMax();
			}
		}
		System.out.println("\nbest result:  " + bestMinMax);
		// System.out.println("eval" + " " + bestMove.getEvaluation());

		if (bestMove.getChildren() != null) {
			// System.out.println(bestMove.getChildren().size());

			for (T child : bestMove.getChildren())
				System.out.print(child.getMinMax() + " ");
		}

		return bestMove;

	}

	private void buildMinMaxTree(T state, int depth, int alpha, int beta, boolean maxPlayer) {
		moves++;
		if (depth == 0 || state.isFinalMove())
			state.setMinMax(heuristic.evaluateGameState(state, depth));
		else {

			if (maxPlayer) {
				int maxValue = Integer.MIN_VALUE;
				for (T child : state.getFollowingStates()) {
					buildMinMaxTree(child, depth - 1, alpha, beta, false);
					maxValue = Math.max(maxValue, child.getMinMax());
					alpha = Math.max(alpha, maxValue);
					if (useAlphaBetaPruning && beta <= alpha)
						break; // beta cut
				}
				state.setMinMax(maxValue);
			} else {
				int minValue = Integer.MAX_VALUE;
				for (T child : state.getFollowingStates()) {
					buildMinMaxTree(child, depth - 1, alpha, beta, true);
					minValue = Math.min(minValue, child.getMinMax());
					beta = Math.min(beta, minValue);
					if (useAlphaBetaPruning &&beta <= alpha)
						break; // alpha cut
				}
				state.setMinMax(minValue);
			}
		}

	}
}
