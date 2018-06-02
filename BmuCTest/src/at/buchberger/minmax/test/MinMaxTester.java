package at.buchberger.minmax.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.buchberger.algorithms.minmax.SimpleMinMax;

public class MinMaxTester {

	@Test
	public void simpleChoose1() {

		TestNode root = new TestNode(4);

		TestNode child1 = new TestNode(-1);
		root.add(child1);

		TestNode child11 = new TestNode(13);
		child1.add(child11);
		TestNode child12 = new TestNode(-12);
		child1.add(child12);
		TestNode child13 = new TestNode(5);
		child1.add(child13);

		TestNode child2 = new TestNode(4);
		root.add(child2);
		TestNode child3 = new TestNode(9);
		root.add(child3);

		SimpleMinMax<TestNode> minMax = new SimpleMinMax<>(new TestHeuristic(), 2, 10);
		assertEquals(9, minMax.chooseMove(root).getMinMax());

	}

	@Test
	public void simpleChoose2() {

		TestNode root = new TestNode(4);

		TestNode child1 = new TestNode(-1);
		root.add(child1);
		TestNode child2 = new TestNode(4);
		root.add(child2);
		TestNode child3 = new TestNode(9);
		root.add(child3);

		SimpleMinMax<TestNode> minMax = new SimpleMinMax<>(new TestHeuristic(), 2, 10);
		assertEquals(9, minMax.chooseMove(root).getMinMax());

	}

	@Test
	public void simpleChoose3() {

		TestNode root = new TestNode(4);

		TestNode child1 = new TestNode(-1);
		root.add(child1);

		TestNode child11 = new TestNode(13);
		child1.add(child11);
		TestNode child12 = new TestNode(-12);
		child1.add(child12);

		TestNode child121 = new TestNode(50);
		child12.add(child121);
		TestNode child122 = new TestNode(-120);
		child12.add(child122);
		TestNode child123 = new TestNode(-17);
		child12.add(child123);

		TestNode child13 = new TestNode(5);
		child1.add(child13);

		TestNode child2 = new TestNode(4);
		root.add(child2);
		TestNode child3 = new TestNode(9);
		root.add(child3);

		SimpleMinMax<TestNode> minMax = new SimpleMinMax<>(new TestHeuristic(), 3, 50);
		assertEquals(9, minMax.chooseMove(root).getMinMax());

	}
	
	@Test
	public void simpleChoose4() {
		
		TestNode root = new TestNode(4);
		
		TestNode child1= new TestNode(-1);
		root.add(child1);
		
		TestNode child11= new TestNode(60);
		child1.add(child11);
		TestNode child12= new TestNode(-12);
		child1.add(child12);
		
		TestNode child121= new TestNode(10);
		child12.add(child121);
		TestNode child122= new TestNode(-120);
		child12.add(child122);
		TestNode child123= new TestNode(-17);
		child12.add(child123);
		
		
		TestNode child13= new TestNode(70);
		child1.add(child13);
		
		TestNode child2= new TestNode(4);
		root.add(child2);
		TestNode child3= new TestNode(9);
		root.add(child3);
		
		
		SimpleMinMax<TestNode> minMax = new SimpleMinMax<>(new TestHeuristic(), 3, 20);
		assertEquals(10, minMax.chooseMove(root).getMinMax());

	}
	
	
	@Test
	public void simpleChoose5() {
		
		TestNode root = new TestNode(4);
		
		TestNode child1= new TestNode(-1);
		root.add(child1);
		
		TestNode child11= new TestNode(60);
		child1.add(child11);
		TestNode child12= new TestNode(-12);
		child1.add(child12);
		
		TestNode child121= new TestNode(12);
		child12.add(child121);
		
		TestNode child1211= new TestNode(20);
		child121.add(child1211);
		TestNode child1212= new TestNode(120);
		child121.add(child1212);
		TestNode child1213= new TestNode(30);
		child121.add(child1213);
		TestNode child1214= new TestNode(40);
		child121.add(child1214);
		
		TestNode child122= new TestNode(-120);
		child12.add(child122);
		TestNode child123= new TestNode(-17);
		child12.add(child123);
		
		
		TestNode child13= new TestNode(70);
		child1.add(child13);
		
		TestNode child2= new TestNode(4);
		root.add(child2);
		TestNode child3= new TestNode(9);
		root.add(child3);
		
		
		SimpleMinMax<TestNode> minMax = new SimpleMinMax<>(new TestHeuristic(), 4, 50);
		assertEquals(20, minMax.chooseMove(root).getMinMax());

	}


}
