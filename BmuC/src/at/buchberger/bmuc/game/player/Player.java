package at.buchberger.bmuc.game.player;

import at.buchberger.bmuc.game.model.Board;

public interface Player {

	public Board choseMove(Board board);
	
	public boolean isHuman();
	
}
