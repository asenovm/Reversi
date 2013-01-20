package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class HorizontalMoveChecker extends BaseLineMoveChecker {

	public HorizontalMoveChecker(final Board board) {
		super(board);
	}

	/**
	 * Returns the board index of the left neighbour of the <tt>cell</tt>
	 * specified
	 * 
	 * @param cell
	 *            the cell whose left neighbour is to be found
	 * @param player
	 *            the player for whom we are searching for a neighbour cell
	 * @return the index of the left neighbour of the <tt>cell</tt> given
	 */
	public int getLeftNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true, true);
	}

	/**
	 * Returns the board index of the right neighbour of the <tt>cell</tt>
	 * specified
	 * 
	 * @param cell
	 *            the cell whose right neighbour is to be found
	 * @param player
	 *            the player to whom the neighbour cell should belong
	 * @return the index of the right neighbour of the <tt>cell</tt> given
	 */
	public int getRightNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false, true);
	}

	/**
	 * Returns whether or not the <tt>cell</tt> given is stable for the
	 * <tt>player</tt> specified
	 * 
	 * @param cell
	 *            the cell that is to be checked for being a stable one
	 * @param player
	 *            the player to whom the cell belongs
	 * @return whether or not the <tt>cell</tt> given is a stable cell for the
	 *         player
	 */
	public boolean isStableCell(final Cell cell, final Player player) {
		return isStableLeft(cell, player) || isStableRight(cell, player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int incrementIndex(int cellIndex, boolean isNegativeDirection) {
		return isNegativeDirection ? cellIndex - 1 : cellIndex + 1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getEndIndex(final Cell startCell, boolean isNegativeDirection) {
		return isNegativeDirection ? startCell.getX() + 1 : Game.BOARD_COLUMN_COUNT
				- startCell.getX();
	}

	private boolean isStableLeft(final Cell cell, final Player player) {
		return isStableCell(cell, player, true);
	}

	private boolean isStableRight(final Cell cell, final Player player) {
		return isStableCell(cell, player, false);
	}

}
