package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.AlphaBetaMinMax;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;
import at.buchberger.bmuc.game.player.ki.heuristic.SimpleHeuristic;

public class BBraham implements Player {

	private AlphaBetaMinMax<Board> minMax = new AlphaBetaMinMax<Board>(new SimpleHeuristic(false), 4, true, null, true);

	@Override
	public Board choseMove(Board board) {
		return minMax.chooseMove(board);
	}

	@Override
	public boolean isHuman() {
		return false;
	}
}
