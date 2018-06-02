package at.buchberger.bmuc.game.player.ki;

import at.buchberger.algorithms.minmax.SimpleMinMax;
import at.buchberger.bmuc.game.model.Board;
import at.buchberger.bmuc.game.player.Player;

public class Abraham implements Player {

	private SimpleMinMax<Board> minMax = new SimpleMinMax<Board>(new SimpleHeuristic(false), 3, 2000000);
	
	@Override
	public Board choseMove(Board board) {
		return minMax.chooseMove(board);
	}

}
