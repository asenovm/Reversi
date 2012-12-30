package edu.fmi.ai.reversi.listeners;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Cell;

public interface ModelObserver {
	void onModelChanged(final Collection<Cell> changedCells);

	void onNextMovesAcquired(final Collection<Cell> nextMoves);
}
