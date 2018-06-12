package at.buchberger.algorithms.minmax;

public interface Heuristic<T extends GameState<T>> {
	
	public int evaluateGameState(T gameState, int searchDepth);

	public void setInitialState(T state);
	
	public int getCalmnessThreshold();

}
