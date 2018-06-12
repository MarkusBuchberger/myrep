package at.buchberger.minmax.test;

import at.buchberger.algorithms.minmax.Heuristic;

public class TestHeuristic implements Heuristic<TestNode> {

	@Override
	public int evaluateGameState(TestNode gameState, int searchDepth) {
		return gameState.getValue();
	}

	@Override
	public void setInitialState(TestNode state) {
	}

	@Override
	public int getCalmnessThreshold() {
		return 0;
	}

}
