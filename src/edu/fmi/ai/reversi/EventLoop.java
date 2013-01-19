package edu.fmi.ai.reversi;


/**
 * The main class, that is used for starting the application
 * 
 * @author martin
 * 
 */
public class EventLoop {

	/**
	 * Loops until the game finishes with one of the two players winning it.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final Game game = new Game();
		while (!game.isFinished()) {
			game.awaitInput();
			game.nextMove();
		}
		game.showWinner();
	}

}
