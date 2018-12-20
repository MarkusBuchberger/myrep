package at.buchberger.minmax.test;

import java.util.ArrayList;
import java.util.List;

import at.buchberger.algorithms.minmax.GameState;

public class TestNode extends GameState<TestNode> {

	private int value;
	private String name;

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
		if (getChildren() == null)
			setChildren(new ArrayList<TestNode>());
		return getChildren();
	}

	@Override
	public List<TestNode> getFollowingStates(boolean onlyCheckExists) {
		if (getChildren() == null)
			setChildren(new ArrayList<TestNode>());
		return getChildren();
	}

	@Override
	public boolean isFinalMove() {
		return getChildren() == null || getChildren().isEmpty();
	}

	public void add(TestNode node) {
		if (getChildren() == null)
			setChildren(new ArrayList<TestNode>());
		getChildren().add(node);
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
	public int getHashCodeForKillerHeuristic() {
		return hashCode();
	}

}
