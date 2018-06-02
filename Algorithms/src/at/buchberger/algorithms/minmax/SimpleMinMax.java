package at.buchberger.algorithms.minmax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleMinMax<T extends GameState<T>> {

	private Heuristic<T> heuristic;
	private int searchDepth;
	private int maxMoves;

	public SimpleMinMax(Heuristic<T> heuristic, int searchDepth, int maxMoves) {
		this.heuristic = heuristic;
		this.searchDepth = searchDepth;
		this.maxMoves = maxMoves;
	}

	private int moves = 0;

	public T chooseMove(T state) {
		heuristic.setInitialState(state);
		moves = 0;
		calcMoves(state, searchDepth);
		System.out.println(moves + " moves to calculate");

		int bestMinMax = Integer.MIN_VALUE;
		T bestMove = null;
		List<T> list = new ArrayList<T>(state.getChildren());
		Collections.shuffle(list);
		for (T child : list) {
			calcMinMax(child, searchDepth - 1, true);
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

	private void calcMinMax(T state, int depth, boolean myMove) {
		if (state.isFinalMove())
			state.setChildren(null);
		if (state.getChildren() == null || state.getChildren().isEmpty())
			state.setMinMax(heuristic.evaluateGameState(state, depth));
		else {
			for (T child : state.getChildren())
				calcMinMax(child, depth, !myMove);
			boolean first = true;
			int minMax = 0;
			for (T child : state.getChildren()) {
				if (first) {
					first = false;
					minMax = child.getMinMax();
				} else
					minMax = myMove ? Math.min(child.getMinMax(), minMax) : Math.max(child.getMinMax(), minMax);

			}
			state.setMinMax(minMax);
		}
	}

	private void calcMoves(T state, int searchDepth) {
		if (searchDepth > 0 && moves < maxMoves) {
			state.setChildren(state.getFollowingStates(false));
			for (T child : state.getChildren()) {
				if (moves++ < maxMoves)
					calcMoves(child, searchDepth - 1);
			}
		}
	}

}
