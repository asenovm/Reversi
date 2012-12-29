package edu.fmi.ai.reversi;

public class Game {

	private final BoardLayout boardLayout;

	private final Board board;

	public Game() {
		boardLayout = new BoardLayout();
		board = new Board();
	}

	public boolean isFinished() {
		return false;
	}

	public void awaitInput() {
		// blank
	}

	public void nextMove() {
		// blank
	}

}
