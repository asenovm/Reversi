package edu.fmi.ai.reversi.listeners;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Cell;

public interface GameSolverCallback {
	void onOptimalMoveReceived(final Collection<Cell> optimalMoves);

}
