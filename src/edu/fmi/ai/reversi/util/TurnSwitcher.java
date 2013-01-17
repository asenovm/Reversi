package edu.fmi.ai.reversi.util;

import java.util.concurrent.Semaphore;

/**
 * A helper class to block the current thread until the end of the turn
 * 
 * @author martin
 * 
 */
public class TurnSwitcher {

	/**
	 * {@value}
	 */
	private static final int MUTEX_INITIAL_LOCKS = 0;

	private final Semaphore mutex;

	/**
	 * Creates a new {@link TurnSwitcher} instance
	 */
	public TurnSwitcher() {
		mutex = new Semaphore(MUTEX_INITIAL_LOCKS);
	}

	/**
	 * Block the current thread until {@link TurnSwitcher#endTurn()} is called.
	 */
	public void startTurn() {
		try {
			mutex.acquire();
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			ex.printStackTrace();
		}
	}

	/**
	 * Unblocks the first first Thread that has called
	 * {@link TurnSwitcher#startTurn()}
	 */
	public void endTurn() {
		mutex.release();
	}

}
