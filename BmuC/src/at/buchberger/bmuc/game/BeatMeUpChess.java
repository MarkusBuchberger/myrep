package at.buchberger.bmuc.game;

import at.buchberger.bmuc.game.controller.BmucController;
import at.buchberger.bmuc.game.player.ki.Abraham;
import at.buchberger.bmuc.game.player.ki.BBraham;

public class BeatMeUpChess {
	public static void main(String[] args) {
		BmucController.playGame(new Abraham(), new BBraham());
	}
}
