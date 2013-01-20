package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseDiagonalMoveChecker extends VerticalMoveChecker {

	protected BaseDiagonalMoveChecker(Board board) {
		super(board);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getNeighbourIndex(final Cell cell, final Player player,
			final boolean isNegativeDirection, final boolean isStoppingSearch) {
		int cellIndex = cell.getIndex();
		int iteration = 1;

		if (!canMove(isNegativeDirection, cellIndex)) {
			return -1;
		}

		cellIndex = incrementIndex(cellIndex, isNegativeDirection);

		while (!isDiagonalEnd(cellIndex)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, iteration, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch && isStoppingSearch(player, iteration, currentCell)) {
				return -1;
			}
			cellIndex = incrementIndex(cellIndex, isNegativeDirection);
			++iteration;
		}

		Cell currentCell = board.get(cellIndex);
		return isClosestNeighbour(player, iteration, currentCell) ? cellIndex : -1;
	}

	private boolean canMove(final boolean isNegativeDirection, int cellIndex) {
		return !((!isNegativeDirection && !canMoveBottom(cellIndex)) || (isNegativeDirection && !canMoveTop(cellIndex)));
	}

	/**
	 * Returns whether or not we can continue looking for a neighbour up from
	 * the cell with the given index
	 * 
	 * @param cellIndex
	 *            the index of the cell that we are checking
	 * @return whether or not we can continue looking for a neighbour up from
	 *         the cell with the given index
	 */
	protected abstract boolean canMoveTop(final int cellIndex);

	/**
	 * Returns whether or not we can continue looking for a neighbour down from
	 * the cell with the given index
	 * 
	 * @param cellIndex
	 *            the index of the cell that we are checking
	 * @return whether
	 */
	protected abstract boolean canMoveBottom(final int cellIndex);

	/**
	 * Returns whether or not the cell at the <tt>cellIndex</tt> given is an end
	 * of a diagonal
	 * 
	 * @param cellIndex
	 *            the index of the cell that is to be checked for being an end
	 *            to a diagonal
	 * @return whether or not the cell at the <tt>cellIndex</tt> given is an end
	 *         to a diagonal
	 */
	protected abstract boolean isDiagonalEnd(final int cellIndex);

}
