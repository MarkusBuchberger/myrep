package at.buchberger.algorithms.minmax;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AlphaBetaMinMax<T extends GameState<T>> {

	private Heuristic<T> heuristic;
	private boolean useAlphaBetaPruning;
	private int minimumDepth;
	private boolean expandUncalmStates;
	private Heuristic<T> preSortHeuristic;
	private boolean useKillerHeuristic;

	public AlphaBetaMinMax(Heuristic<T> heuristic, int minimumDepth, boolean useAlphaBetaPruning,
			Heuristic<T> preSortHeuristic, boolean expandUncalmStates, boolean useKillerHeuristic) {
		super();
		this.heuristic = heuristic;
		this.minimumDepth = minimumDepth;
		this.useAlphaBetaPruning = useAlphaBetaPruning;
		this.preSortHeuristic = preSortHeuristic;
		this.expandUncalmStates = expandUncalmStates;
		this.useKillerHeuristic = useKillerHeuristic;
	}

	private long moves = 0;
	private int maxDepth = 0;
	private Comparator<T> preSortComparator;

	private HashMap<Integer, T> killerMoves;

	public T chooseMove(T state) {
		Calendar start = Calendar.getInstance();
		heuristic.setInitialState(state);
		killerMoves = new HashMap<Integer, T>();
		moves = 0;
		maxDepth = 0;
		if (preSortHeuristic != null) {
			preSortHeuristic.setInitialState(state);
		}

		if (preSortHeuristic != null) {
			preSortComparator = new Comparator<T>() {
				@Override
				public int compare(T o1, T o2) {
					return o1.getPreSortValue() - o2.getPreSortValue();
				}
			};
		}

		T chosenState = buildMinMaxTree(state, minimumDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

		long millis = Calendar.getInstance().getTimeInMillis() - start.getTimeInMillis();
		System.out.println("MinMax calculated moves: " + moves + " ("
				+ (millis > 0 ? ((moves * 1000 / millis) + " m/s") : "") + ", maxdepth: "
				+ Math.abs(maxDepth - minimumDepth) + ", res: " + chosenState.getMinMax() + ")");
		return chosenState;

	}

	private T buildMinMaxTree(T state, int depth, int alpha, int beta, boolean maxPlayer) {
		moves++;
		maxDepth = Math.min(maxDepth, depth);
		T chosenChild = null;
		if (state.isFinalMove()) {
			state.setMinMax(heuristic.evaluateGameState(state, depth));
		} else {
			if (depth <= 0)
				state.setMinMax(heuristic.evaluateGameState(state, depth));

			boolean expand = depth > 0;
			expand |= expandUncalmStates && moves < 3000000
					&& Math.abs(state.getMinMax()
							- heuristic.evaluateGameState(state.getPreviousState(), depth + 1)) > heuristic
									.getCalmnessThreshold();
			if (expand) {
				List<T> children = state.getFollowingStates();

				if (preSortHeuristic != null && depth > 0) {
					for (T child : children) {
						child.setPreSortValue(preSortHeuristic.evaluateGameState(child, 1));
					}
					Collections.sort(children, preSortComparator);
					if (maxPlayer)
						Collections.reverse(children);
				}

				if (useKillerHeuristic) {
					T killer = killerMoves.get(Integer.valueOf(depth));
					T found = null;
					if (killer != null) {
						for (T child : children) {
							if (child.isKillerMove(killer))
								found = child;
						}
					}
					if(found != null) {
						children.remove(children.indexOf(found));
						children.add(0, found);
					}
				}

				if (maxPlayer) {
					int maxValue = Integer.MIN_VALUE;
					for (T child : children) {
						buildMinMaxTree(child, depth - 1, alpha, beta, false);
						if (child.getMinMax() > maxValue) {
							maxValue = child.getMinMax();
							chosenChild = child;
						}
						alpha = Math.max(alpha, maxValue);
						if (useAlphaBetaPruning && beta <= alpha) {
							if (useKillerHeuristic)
								killerMoves.put(Integer.valueOf(depth), child);
							break; // beta cut
						}
					}
					state.setMinMax(maxValue);
				} else {
					int minValue = Integer.MAX_VALUE;
					for (T child : children) {
						buildMinMaxTree(child, depth - 1, alpha, beta, true);

						if (child.getMinMax() < minValue) {
							minValue = child.getMinMax();
							chosenChild = child;
						}

						beta = Math.min(beta, minValue);
						if (useAlphaBetaPruning && beta < alpha) {
							if (useKillerHeuristic)
								killerMoves.put(Integer.valueOf(depth), child);
							break; // alpha cut
						}
					}
					state.setMinMax(minValue);
				}
			}
		}
		return chosenChild;
	}
}
