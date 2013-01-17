package edu.fmi.ai.reversi.listeners;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Cell;

/**
 * Implementations of this interface are observers of the board model
 * 
 * @author martin
 * 
 */
public interface ModelObserver {
	/**
	 * A callback fired when some of the cells on the board has changed its
	 * owner
	 * 
	 * @param changedCells
	 *            the board cells that have changed their owners
	 */
	void onBoardChanged(final Collection<Cell> changedCells);

	/**
	 * A callback fired when the possession of board cells has changed.
	 * 
	 * @param whiteDiscs
	 *            the number of dicss on the board for the white player
	 * @param blackDiscs
	 *            the number of discs on the board for the black player
	 */
	void onResultChanged(final int whiteDiscs, final int blackDiscs);

	/**
	 * A callback fired when the possible next moves for the current player have
	 * been retrieved.
	 * 
	 * @param nextMoves
	 *            all the moves the current player can make on the current move.
	 */
	void onNextMovesAcquired(final Collection<Cell> nextMoves);
}
