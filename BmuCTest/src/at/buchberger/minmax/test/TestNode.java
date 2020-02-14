package at.buchberger.minmax.test;

import java.util.ArrayList;
import java.util.List;

import at.buchberger.algorithms.minmax.GameState;

public class TestNode extends GameState<TestNode> {

	private int value;
	private String name;

	private ArrayList<TestNode> children = new ArrayList<TestNode>();

	public TestNode(int value) {
		this.value = value;
	}

	public TestNode(int value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public void internalEvaluation() {
	}

	@Override
	public List<TestNode> getFollowingStates() {
		return children;
	}

	@Override
	public List<TestNode> getFollowingStates(boolean onlyCheckExists) {
		return children;
	}

	@Override
	public boolean isFinalMove() {
		return children.isEmpty();
	}

	public void add(TestNode node) {
		children.add(node);
	}

	public int getValue() {
		return value;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public TestNode getPreviousState() {
		return null;
	}
	
	@Override
	public boolean isKillerMove(TestNode killerMove) {
		return killerMove.value == value;
	}

}
