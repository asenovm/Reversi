package edu.fmi.ai.reversi.listeners;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Cell;

public interface ModelObserver {
	void onBoardChanged(final Collection<Cell> changedCells);

	void onResultChanged(final int whiteDiscs, final int blackDiscs);

	void onNextMovesAcquired(final Collection<Cell> nextMoves);
}
