package at.buchberger.algorithms.minmax;

import java.util.Collection;

public abstract class GameState<T> {
	
	private int minMax;
	private Collection<T> children;
	
	
	public abstract void internalEvaluation();
	
	public abstract Collection<T> getFollowingStates();
	
	public abstract Collection<T> getFollowingStates(boolean onlyCheckExists);
	
	public abstract boolean isFinalMove();


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
