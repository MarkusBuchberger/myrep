package at.buchberger.minmax.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.buchberger.algorithms.minmax.GameState;

public class TestNode extends GameState<TestNode> {

	private int value;
	private boolean finalMove;
	private List<TestNode> children = new ArrayList<TestNode>();

	public TestNode(int value) {
		this.value = value;
	}

	@Override
	public void internalEvaluation() {
	}

	@Override
	public Collection<TestNode> getFollowingStates(int max) {
		return children.size() > max ? children.subList(0, max - 1) : children;
	}

	@Override
	public boolean isFinalMove() {
		return finalMove;
	}

	public void setFinalMove(boolean finalMove) {
		this.finalMove = finalMove;
	}

	public void add(TestNode node) {
		this.children.add(node);
	}
	
	public int getValue() {
		return value;
	}

}
