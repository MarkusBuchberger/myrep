package at.buchberger.algorithms.minmax;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlphaBetaMinMax<T extends GameState<T>> {

	private Heuristic<T> heuristic;
	private Heuristic<T> preSortHeuristic;
	private boolean useAlphaBetaPruning;
	private int minimumDepth;
	private boolean expandUncalmStates;

	private Comparator<T> preSortComparator;

	public AlphaBetaMinMax(Heuristic<T> heuristic, int minimumDepth, boolean useAlphaBetaPruning,
			Heuristic<T> preSortHeuristic, boolean expandUncalmStates) {
		super();
		this.heuristic = heuristic;
		this.minimumDepth = minimumDepth;
		this.useAlphaBetaPruning = useAlphaBetaPruning;
		this.preSortHeuristic = preSortHeuristic;
		this.expandUncalmStates = expandUncalmStates;
	}

	private int moves = 0;
	private int maxDepth = 0;

	public T chooseMove(T state) {
		heuristic.setInitialState(state);
		moves = 0;
		maxDepth = 0;
		if (preSortHeuristic != null) {
			preSortHeuristic.setInitialState(state);
			preSortComparator = new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					return o1.getPreSort() - o2.getPreSort();
				}
			};
		}

		buildMinMaxTree(state, minimumDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

		System.out.println(moves + " moves to calculate");
		System.out.println(Math.abs(maxDepth - minimumDepth) + " max depth");
		int bestMinMax = Integer.MIN_VALUE;
		T bestMove = null;
		List<T> list = new ArrayList<T>(state.getChildren());

		// Collections.shuffle(list);
		for (T child : list) {
			// System.out.print(child.getMinMax() + " ");
			if (child.getMinMax() >= bestMinMax) {
				bestMove = child;
				bestMinMax = child.getMinMax();
			}
		}
		System.out.println("\nbest result:  " + bestMinMax);
		// System.out.println("eval" + " " + bestMove.getEvaluation());

		// if (bestMove.getChildren() != null) {
		// // System.out.println(bestMove.getChildren().size());
		//
		// for (T child : bestMove.getChildren())
		// System.out.print(child.getMinMax() + " ");
		// }

		return bestMove;

	}

	private void buildMinMaxTree(T state, int depth, int alpha, int beta, boolean maxPlayer) {
		moves++;
		maxDepth = Math.min(maxDepth, depth);
		if (state.isFinalMove()) {
			state.setMinMax(heuristic.evaluateGameState(state, depth));
		} else {
			if (depth <= 0)
				state.setMinMax(heuristic.evaluateGameState(state, depth));

			if (depth > 0 || (expandUncalmStates && moves < 300000
					&& Math.abs(state.getMinMax()
							- heuristic.evaluateGameState(state.getPreviousState(), depth + 1)) > heuristic
									.getCalmnessThreshold())) {
				List<T> children = state.getFollowingStates();
				state.setChildren(new ArrayList<T>());
				Collections.shuffle(children);
				if (preSortHeuristic != null && preSortComparator != null) {
					for (T child : children) {
						child.setPreSort(preSortHeuristic.evaluateGameState(child, 1));
					}
					Collections.sort(children, preSortComparator);
				}

				// System.out.println(state.getName() + " " + alpha + " " + beta);
				if (maxPlayer) {
					int maxValue = Integer.MIN_VALUE;
					for (T child : children) {
						if (depth == minimumDepth)
							state.getChildren().add(child);
						buildMinMaxTree(child, depth - 1, alpha, beta, false);

						maxValue = Math.max(maxValue, child.getMinMax());
						alpha = Math.max(alpha, maxValue);
						if (useAlphaBetaPruning && beta <= alpha) {
							// System.out.println(depth + " " + child.getMinMax() + " betacut after " +
							// child.getName() + " "
							// + beta + " < " + alpha);
							break; // beta cut
						}

					}
					state.setMinMax(maxValue);
				} else {
					int minValue = Integer.MAX_VALUE;
					for (T child : children) {
						if (depth == minimumDepth)
							state.getChildren().add(child);
						buildMinMaxTree(child, depth - 1, alpha, beta, true);
						// state.getChildren().add(child);
						minValue = Math.min(minValue, child.getMinMax());
						beta = Math.min(beta, minValue);
						if (useAlphaBetaPruning && beta < alpha) {
							// System.out.println(depth + " " + child.getMinMax() + " alphacut after " +
							// child.getName() + " "
							// + beta + " < " + alpha);
							break; // alpha cut
						}
					}
					state.setMinMax(minValue);
				}
			}
		}
	}
}
