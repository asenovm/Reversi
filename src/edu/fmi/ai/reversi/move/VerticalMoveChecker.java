package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class VerticalMoveChecker extends BaseLineMoveChecker {

	public VerticalMoveChecker(final Board board) {
		super(board);
	}

	/**
	 * Returns the board index of the top neighbour of the <tt>cell</tt> given
	 * for the <tt>player</tt> specified
	 * 
	 * @param cell
	 *            the cell whose neighbour we are trying to find
	 * @param player
	 *            the player to whom the neighbour cell should belong
	 * @return the board index of the top neighbour of the cell given for the
	 *         player specified
	 */
	public int getTopNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true, true);
	}

	/**
	 * Returns the board index of the bottom neighbour of the <tt>cell</tt>
	 * given for the <tt>player</tt> specified
	 * 
	 * @param cell
	 *            the cell whose neighbour we are trying to find
	 * @param player
	 *            the player to whom the neighbour cell should belong
	 * @return the board index of the bottom neighbour of the cell given for the
	 *         player specified
	 */
	public int getBottomNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false, true);
	}

	/**
	 * Returns whether or not the cell specified is a stable cell for the player
	 * given
	 * 
	 * @param cell
	 *            the cell that is to be checked for being a stable one
	 * @param player
	 *            the player to whom the cell belongs
	 * @return whether or not the <tt>cell</tt> given is a stable cell for the
	 *         <tt>player</tt> specified
	 */
	public boolean isStableCell(final Cell cell, final Player player) {
		return isStableTop(cell, player) || isStableBottom(cell, player);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getEndIndex(Cell startCell, boolean isNegativeDirection) {
		return isNegativeDirection ? startCell.getY() + 1 : Game.BOARD_ROW_COUNT - startCell.getY();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int incrementIndex(int cellIndex, boolean isNegativeDirection) {
		return isNegativeDirection ? cellIndex - Game.BOARD_COLUMN_COUNT : cellIndex
				+ Game.BOARD_COLUMN_COUNT;
	}

	private boolean isStableTop(final Cell cell, final Player player) {
		return isStableCell(cell, player, true);
	}

	private boolean isStableBottom(final Cell cell, final Player player) {
		return isStableCell(cell, player, false);
	}

}
