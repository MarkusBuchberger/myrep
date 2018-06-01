package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.MinMax;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;

public class Abraham implements Player {

	private MinMax<Board> minMax = new MinMax<Board>(new SimpleHeuristic(), 3, 20000);
	
	@Override
	public Board choseMove(Board board) {
		return minMax.chooseMove(board);
	}

}
