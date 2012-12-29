package edu.fmi.ai.reversi.util;

import java.util.concurrent.Semaphore;

public class TurnHelper {

	private final Semaphore mutex;

	public TurnHelper() {
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
