package at.buchberger.bmuc.game.gui.controller;

import at.buchberger.bmuc.game.model.Board;

public interface ModelChangedListener {
	
	public void boardChanged(Board currentBoard);

}
