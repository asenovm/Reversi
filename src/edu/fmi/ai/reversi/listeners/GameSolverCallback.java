package edu.fmi.ai.reversi.listeners;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Cell;

public interface GameSolverCallback {

	/**
	 * A callback fired when the optimal move for the current iteration has been
	 * received
	 * 
	 * @param optimalMoves
	 *            the changed cells that the optimal move consists of.
	 */
	void onOptimalMoveReceived(final Collection<Cell> optimalMoves);
}
