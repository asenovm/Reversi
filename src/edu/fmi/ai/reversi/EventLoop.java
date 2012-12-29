package edu.fmi.ai.reversi;

public class EventLoop {

	public static void main(String[] args) {
		final Game game = new Game();
		while (!game.isFinished()) {
			game.awaitInput();
			game.nextMove();
		}
	}

}
