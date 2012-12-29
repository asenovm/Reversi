package edu.fmi.ai.reversi;

import java.util.concurrent.Semaphore;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.view.BoardLayout;

public class Game implements BoardEventsListener {

	private final BoardLayout boardLayout;

	private final Board board;

	private Semaphore mutex;

	public Game() {
		boardLayout = new BoardLayout(this);
		board = new Board();
		mutex = new Semaphore(0);
	}

	public boolean isFinished() {
		return false;
	}

	public synchronized void awaitInput() {
		boardLayout.nextTurn();
		tryAcquireMutex();

	}

	private void tryAcquireMutex() {
		try {
			mutex.acquire();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void nextMove() {
		// blank as for now
	}

	@Override
	public void onCellSelected(final int cellIndex) {
		mutex.release();
	}

}
