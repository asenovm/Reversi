package edu.fmi.ai.reversi.util;

import java.util.concurrent.Semaphore;

public class TurnSwitcher {

	private final Semaphore mutex;

	public TurnSwitcher() {
		mutex = new Semaphore(0);
	}

	public void startTurn() {
		try {
			mutex.acquire();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public void endTurn() {
		mutex.release();
	}

}
