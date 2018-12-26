package at.buchberger.minmax.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import at.buchberger.algorithms.minmax.AlphaBetaMinMax;

public class MinMaxTester {



	@Test
	public void alphaBeta1() {

		TestNode root = new TestNode(4);

		TestNode child1 = new TestNode(-1);
		root.add(child1);

		TestNode child11 = new TestNode(60);
		child1.add(child11);
		TestNode child12 = new TestNode(-12);
		child1.add(child12);

		TestNode child121 = new TestNode(12);
		child12.add(child121);

		TestNode child1211 = new TestNode(20);
		child121.add(child1211);
		TestNode child1212 = new TestNode(120);
		child121.add(child1212);
		TestNode child1213 = new TestNode(30);
		child121.add(child1213);
		TestNode child1214 = new TestNode(40);
		child121.add(child1214);

		TestNode child122 = new TestNode(-120);
		child12.add(child122);
		TestNode child123 = new TestNode(-17);
		child12.add(child123);

		TestNode child13 = new TestNode(70);
		child1.add(child13);

		TestNode child2 = new TestNode(4);
		root.add(child2);
		TestNode child3 = new TestNode(9);
		root.add(child3);

		AlphaBetaMinMax<TestNode> minMax = new AlphaBetaMinMax<>(new TestHeuristic(), 4, true, new TestHeuristic(), false, false);
		assertEquals(20, minMax.chooseMove(root).getMinMax());

	}

	@Test
	public void alphaBeta2() {
		TestNode root = new TestNode(0);

		TestNode child1 = new TestNode(0, "child1");
		root.add(child1);

		TestNode child11 = new TestNode(0, "child11");
		child1.add(child11);

		TestNode child111 = new TestNode(0, "child111");
		child11.add(child111);

		TestNode child1111 = new TestNode(5, "child1111");
		child111.add(child1111);

		TestNode child1112 = new TestNode(6, "child1112");
		child111.add(child1112);

		TestNode child112 = new TestNode(0, "child112");
		child11.add(child112);

		TestNode child1121 = new TestNode(7, "child1121");
		child112.add(child1121);

		TestNode child1122 = new TestNode(4, "child1122");
		child112.add(child1122);

		TestNode child1123 = new TestNode(5, "child1123");
		child112.add(child1123);

		TestNode child12 = new TestNode(0, "child12");
		child1.add(child12);

		TestNode child121 = new TestNode(0, "child121");
		child12.add(child121);

		TestNode child1211 = new TestNode(3, "child1211");
		child121.add(child1211);

		TestNode child2 = new TestNode(0, "child2");
		root.add(child2);

		TestNode child21 = new TestNode(0, "child21");
		child2.add(child21);

		TestNode child211 = new TestNode(0, "child211");
		child21.add(child211);

		TestNode child2111 = new TestNode(6, "child2111");
		child211.add(child2111);

		TestNode child212 = new TestNode(0, "child212");
		child21.add(child212);

		TestNode child2121 = new TestNode(6, "child2121");
		child212.add(child2121);

		TestNode child2122 = new TestNode(9, "child2122");
		child212.add(child2122);

		TestNode child22 = new TestNode(0, "child22");
		child2.add(child22);

		TestNode child221 = new TestNode(0, "child221");
		child22.add(child221);

		TestNode child2211 = new TestNode(7, "child2211");
		child22.add(child2211);

		TestNode child3 = new TestNode(0, "child3");
		root.add(child3);

		TestNode child31 = new TestNode(0, "child31");
		child3.add(child31);

		TestNode child311 = new TestNode(0, "child311");
		child31.add(child311);

		TestNode child3111 = new TestNode(5, "child3111");
		child311.add(child3111);

		TestNode child32 = new TestNode(0, "child32");
		child3.add(child32);

		TestNode child321 = new TestNode(0, "child321");
		child32.add(child321);

		TestNode child3211 = new TestNode(9, "child3211");
		child321.add(child3211);

		TestNode child3212 = new TestNode(8, "child3212");
		child321.add(child3212);

		TestNode child322 = new TestNode(0, "child322");
		child32.add(child322);

		TestNode child3221 = new TestNode(6, "child3221");
		child322.add(child3221);

		AlphaBetaMinMax<TestNode> minMax = new AlphaBetaMinMax<>(new TestHeuristic(), 4, true, new TestHeuristic(), false, false);
		assertEquals(6, minMax.chooseMove(root).getMinMax());

	}

	@Test
	public void alphaBeta3() {
		TestNode root = new TestNode(0);
		{
			TestNode child1 = new TestNode(0, "child1");
			root.add(child1);

			TestNode child11 = new TestNode(0, "child11");
			child1.add(child11);

			TestNode child111 = new TestNode(10, "child111");
			child11.add(child111);

			TestNode child112 = new TestNode(-5, "child112");
			child11.add(child112);

			TestNode child113 = new TestNode(3, "child113");
			child11.add(child113);

			TestNode child12 = new TestNode(0, "child12");
			child1.add(child12);

			TestNode child121 = new TestNode(-6, "child121");
			child12.add(child121);

			TestNode child122 = new TestNode(12, "child122");
			child12.add(child122);

			TestNode child123 = new TestNode(10000, "child123");
			child12.add(child123);
		}
		{
			TestNode child2 = new TestNode(0, "child2");
			root.add(child2);

			TestNode child21 = new TestNode(0, "child21");
			child2.add(child21);

			TestNode child211 = new TestNode(10, "child211");
			child21.add(child211);

			TestNode child212 = new TestNode(12, "child212");
			child21.add(child212);

			TestNode child213 = new TestNode(3, "child213");
			child21.add(child213);

			TestNode child22 = new TestNode(0, "child22");
			child2.add(child22);

			TestNode child221 = new TestNode(13, "child221");
			child22.add(child221);

			TestNode child222 = new TestNode(20000, "child222");
			child22.add(child222);

			TestNode child223 = new TestNode(30000, "child223");
			child22.add(child223);
		}
		{
			TestNode child3 = new TestNode(0, "child3");
			root.add(child3);

			TestNode child31 = new TestNode(0, "child31");
			child3.add(child31);

			TestNode child311 = new TestNode(3, "child311");
			child31.add(child311);

			TestNode child312 = new TestNode(2, "child312");
			child31.add(child312);

			TestNode child313 = new TestNode(-4, "child313");
			child31.add(child313);

			TestNode child32 = new TestNode(0, "child32");
			child3.add(child32);

			TestNode child321 = new TestNode(40000, "child321");
			child32.add(child321);

			TestNode child322 = new TestNode(50000, "child322");
			child32.add(child322);

			TestNode child323 = new TestNode(60000, "child323");
			child32.add(child323);
		}

		AlphaBetaMinMax<TestNode> minMax = new AlphaBetaMinMax<>(new TestHeuristic(), 4, true, new TestHeuristic(), false, false);
		assertEquals(12, minMax.chooseMove(root).getMinMax());

	}
	
	@Test
	public void alphaBeta4() {
		TestNode root = new TestNode(0);
		{
			TestNode child1 = new TestNode(0, "child1");
			root.add(child1);

			TestNode child11 = new TestNode(0, "child11");
			child1.add(child11);

			TestNode child111 = new TestNode(4, "child111");
			child11.add(child111);

			TestNode child112 = new TestNode(6, "child112");
			child11.add(child112);


			TestNode child12 = new TestNode(0, "child12");
			child1.add(child12);

			TestNode child121 = new TestNode(7, "child121");
			child12.add(child121);

			TestNode child122 = new TestNode(9, "child122");
			child12.add(child122);

		}
		{
			TestNode child2 = new TestNode(0, "child2");
			root.add(child2);

			TestNode child21 = new TestNode(0, "child21");
			child2.add(child21);

			TestNode child211 = new TestNode(1, "child211");
			child21.add(child211);

			TestNode child212 = new TestNode(2, "child212");
			child21.add(child212);


			TestNode child22 = new TestNode(0, "child22");
			child2.add(child22);

			TestNode child221 = new TestNode(0, "child221");
			child22.add(child221);

			TestNode child222 = new TestNode(1, "child222");
			child22.add(child222);

		}
		{
			TestNode child3 = new TestNode(0, "child3");
			root.add(child3);

			TestNode child31 = new TestNode(0, "child31");
			child3.add(child31);

			TestNode child311 = new TestNode(8, "child311");
			child31.add(child311);

			TestNode child312 = new TestNode(1, "child312");
			child31.add(child312);



			TestNode child32 = new TestNode(0, "child32");
			child3.add(child32);

			TestNode child321 = new TestNode(9, "child321");
			child32.add(child321);

			TestNode child322 = new TestNode(2, "child322");
			child32.add(child322);


		}

		AlphaBetaMinMax<TestNode> minMax = new AlphaBetaMinMax<>(new TestHeuristic(), 4, true, new TestHeuristic(), false, false);
		assertEquals(8, minMax.chooseMove(root).getMinMax());

	}

}
