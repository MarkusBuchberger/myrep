package at.buchberger.algorithms.minmax;

import java.util.List;

public abstract class GameState<T> {
	
	private int minMax;
	
	private int preSort;
	
	
	public abstract void internalEvaluation();
	
	public abstract List<T> getFollowingStates();
	
	public abstract List<T> getFollowingStates(boolean onlyCheckExists);
	
	public abstract boolean isFinalMove();
	
	public abstract T getPreviousState();
	
	public String getName() {
		return null;
	}

	public int getMinMax() {
		return minMax;
	}

	public void setMinMax(int minMax) {
		this.minMax = minMax;
	}


	public int getPreSort() {
		return preSort;
	}

	public void setPreSort(int preSort) {
		this.preSort = preSort;
	}
	
	

}
