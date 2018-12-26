package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.AlphaBetaMinMax;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;
import at.buchberger.bmuc.game.player.ki.heuristic.SimpleHeuristic;

public class Abraham implements Player {

	private AlphaBetaMinMax<Board> minMax = new AlphaBetaMinMax<Board>(new SimpleHeuristic(true), 2, true, new SimpleHeuristic(true), false, false);
	
	@Override
	public Board choseMove(Board board) {
		return minMax.chooseMove(board);
	}
	
	@Override
	public boolean isHuman() {
		return false;
	}

}
