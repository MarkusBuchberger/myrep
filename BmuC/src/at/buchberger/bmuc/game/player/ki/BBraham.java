package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.AlphaBetaMinMax;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;

public class BBraham implements Player {

	private AlphaBetaMinMax<Board> minMax = new AlphaBetaMinMax<Board>(new SimpleHeuristic(false), 2000000, 3, false);

	@Override
	public Board choseMove(Board board) {
		return minMax.chooseMove(board);
	}

}
