package at.buchberger.algorithms.minmax;

import java.util.Collection;

public abstract class GameState<T> {
	
	private int evaluation;
	private int minMax;
	private Collection<T> children;
	
	
	public abstract void internalEvaluation();
	
	public abstract Collection<T> getFollowingStates(int max);
	
	public abstract boolean isFinalMove();

	public int getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}

	public Collection<T> getChildren() {
		return children;
	}

	public void setChildren(Collection<T> children) {
		this.children = children;
	}

	public int getMinMax() {
		return minMax;
	}

	public void setMinMax(int minMax) {
		this.minMax = minMax;
	}

	public void truncatePaths() {
		children = null;
	}
	

}
