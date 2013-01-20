package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseMoveChecker {

	protected Board board;

	protected BaseMoveChecker(final Board board) {
		this.board = board;
	}

	/**
	 * Whether or not the search for the closest neighbour cell belonging to the
	 * given player should stop
	 * 
	 * @param player
	 *            the player for whom we are searching the closest neighbour
	 *            cell
	 * @param iteration
	 *            the current iteration at which we are looking for a neighbour
	 * @param currentCell
	 *            the current cell that is being inspected for being the closest
	 *            neighbour
	 * @return whether or not the search for the closest neighbour should stop
	 */
	protected boolean isStoppingSearch(final Player player, int iteration, final Cell currentCell) {
		return currentCell.isEmpty() || (currentCell.isOwnedBy(player) && iteration == 1);
	}

	/**
	 * Returns whether or not the cell given is the closest neighbour
	 * 
	 * @param player
	 *            the player for whose cell we are looking for the closest
	 *            neighbour
	 * @param iteration
	 *            the current iteration at which we are looking for a closest
	 *            neigbhour
	 * @param currentCell
	 *            the cell that is being inspected for being the closest
	 *            neighbour
	 * @return whether or not the cell specified is the closest neighbour
	 */
	protected boolean isClosestNeighbour(final Player player, int iteration, final Cell currentCell) {
		return currentCell.isOwnedBy(player) && (iteration > 1 || player == Player.UNKNOWN);
	}

	/**
	 * Return whether or not placing a disc on the <tt>cell</tt> given is legal
	 * for the <tt>player</tt> specified
	 * 
	 * @param cell
	 *            the cell which is to be inspected
	 * @param player
	 *            the player who is trying to place a disc on the <tt>cell</tt>
	 *            given
	 * @return whether or not the <tt>player</tt> given can place a disc on the
	 *         cell
	 */
	public boolean isMovePermitted(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player) >= 0;
	}

	/**
	 * Returns whether or not the cell given is a stable cell for the
	 * <tt>player</tt> specified
	 * 
	 * @param cell
	 *            the cell that is to be inspected for being a stable one
	 * @param player
	 *            the player for whom the cell is inspected for bieng a stable
	 *            one
	 * @param isNegativeDirection
	 *            whether or not the next cell is with lower or higher index
	 *            than the current one
	 * @return whether or not the cell given is a stable cell for the
	 *         <tt>player</tt>
	 */
	protected boolean isStableCell(final Cell cell, final Player player,
			final boolean isNegativeDirection) {
		final Player opponent = Player.getOpponent(player);
		return isHavingSameColorNeighbours(cell, isNegativeDirection, opponent) || isLineFull(cell);
	}

	protected abstract int getNeighbourIndex(final Cell cell, final Player player,
			final boolean isNegativeDirection, final boolean isStoppingSearch);

	protected abstract int incrementIndex(final int cellIndex, final boolean isNegativeDirection);

	private boolean isLineFull(final Cell cell) {
		return getNeighbourIndex(cell, Player.UNKNOWN, true, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, false, false) < 0;
	}

	private boolean isHavingSameColorNeighbours(final Cell cell, final boolean isNegativeDirection,
			final Player opponent) {
		return getNeighbourIndex(cell, opponent, isNegativeDirection, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, isNegativeDirection, false) < 0;
	}

	private int getNeighbourIndex(Cell cell, Player player) {
		return Math.max(getNeighbourIndex(cell, player, false, true),
				getNeighbourIndex(cell, player, true, true));
	}

}
