package at.buchberger.bmuc.game;

import at.buchberger.bmuc.game.controller.BmucController;
import at.buchberger.bmuc.game.player.ki.Abraham;
import at.buchberger.bmuc.game.player.ki.BBraham;

public class BeatMeUpChess {
	public static void main(String[] args) {
		double sum = 0;
		int games = 0;
		while (Math.abs(sum) < 10) {
			sum += BmucController.playGame(new Abraham(), new BBraham());
			games++;

			System.out.println(sum + " (" + games + ")");
		}
		System.out.println("final: " +sum + " (" + games + ")");
	}
}
