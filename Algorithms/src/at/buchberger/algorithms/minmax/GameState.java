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
	
	public abstract boolean isKillerMove(T killerMove);
	
	public String getName() {
		return null;
	}

	public int getMinMax() {
		return minMax;
	}

	public void setMinMax(int minMax) {
		this.minMax = minMax;
	}


	public int getPreSortValue() {
		return preSort;
	}

	public void setPreSortValue(int preSortValue) {
		this.preSort = preSortValue;
	}
	
	

}
