package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.Direction;

/**
 * Helper class for checking whether or not a move is allowed
 * 
 * @author martin
 * 
 */
public class MoveChecker {

	private final HorizontalMoveChecker horizontalMoveChecker;

	private final VerticalMoveChecker verticalMoveChecker;

	private final MainDiagonalMoveChecker mainDiagonalMoveChecker;

	private final SecondaryDiagonalMoveChecker secondaryDiagonalMoveChecker;

	private final Board board;

	/**
	 * Creates a new move checker for the board given.
	 * 
	 * @param board
	 *            the board for which the move checker is to be used
	 */
	public MoveChecker(final Board board) {
		this.board = board;
		horizontalMoveChecker = new HorizontalMoveChecker(board);
		verticalMoveChecker = new VerticalMoveChecker(board);
		mainDiagonalMoveChecker = new MainDiagonalMoveChecker(board);
		secondaryDiagonalMoveChecker = new SecondaryDiagonalMoveChecker(board);
	}

	/**
	 * Returns whether or not the <tt>player</tt> specified can place a disc on
	 * the board index given.
	 * 
	 * @param cellIndex
	 *            the board index where the <tt>player</tt> is trying to place a
	 *            new disc
	 * @param player
	 *            the player who is trying to place a new disc on the
	 *            <tt>cellIndex</tt> given
	 * @return whether or not the <tt>player</tt> specified can place a disc on
	 *         the cell index
	 */
	public boolean isMovePermitted(final int cellIndex, final Player player) {
		final Cell cell = board.get(cellIndex);
		return cell.isEmpty() && isMovePermitted(cell, player);
	}

	/**
	 * Returns the board index of the closest neighbour of the <tt>cell</tt>
	 * given in the direction specified.
	 * 
	 * @param direction
	 *            the direction in which the closest neighbour is to be looked
	 *            for
	 * @param cell
	 *            the cell for which we are looking for the closest neighbour
	 * @param player
	 *            the player to whom the closest neighbour should belong
	 * @return the index of the closest neighbour of the <tt>cell</tt> given,
	 *         that belongs to the <tt>player</tt> specified
	 */
	public int getNeighbourIndex(final Direction direction, final Cell cell, final Player player) {
		switch (direction) {
		case TOP:
			return verticalMoveChecker.getTopNeighbourIndex(cell, player);
		case BOTTOM:
			return verticalMoveChecker.getBottomNeighbourIndex(cell, player);
		case LEFT:
			return horizontalMoveChecker.getLeftNeighbourIndex(cell, player);
		case RIGHT:
			return horizontalMoveChecker.getRightNeighbourIndex(cell, player);
		case MAIN_DIAGONAL_BOTTOM:
			return mainDiagonalMoveChecker.getBottomNeighbourIndex(cell, player);
		case MAIN_DIAGONAL_TOP:
			return mainDiagonalMoveChecker.getTopNeighbourIndex(cell, player);
		case SECONDARY_DIAGONAL_BOTTOM:
			return secondaryDiagonalMoveChecker.getBottomNeighbourIndex(cell, player);
		case SECONDARY_DIAGONAL_TOP:
			return secondaryDiagonalMoveChecker.getTopNeighbourIndex(cell, player);
		default:
			return -1;
		}
	}

	/**
	 * Returns the number of stable discs on the board that belong to the
	 * <tt>player</tt> specified
	 * 
	 * @param player
	 *            the player for which the number of stable discs on the board
	 *            is to be counted
	 * @return the number of stable discs on the board that belong to the
	 *         <tt>player</tt> specified
	 */
	public int getStableDiscCount(final Player player) {
		int result = 0;
		for (int i = 0; i < board.size(); ++i) {
			final Cell currentCell = board.get(i);
			if (currentCell.isOwnedBy(player) && isStableCell(currentCell, player)) {
				++result;
			}
		}
		return result;
	}

	private boolean isStableCell(final Cell cell, final Player player) {
		return horizontalMoveChecker.isStableCell(cell, player)
				&& verticalMoveChecker.isStableCell(cell, player)
				&& mainDiagonalMoveChecker.isStableCell(cell, player)
				&& secondaryDiagonalMoveChecker.isStableCell(cell, player);
	}

	private boolean isMovePermitted(final Cell cell, final Player player) {
		return horizontalMoveChecker.isMovePermitted(cell, player)
				|| verticalMoveChecker.isMovePermitted(cell, player)
				|| mainDiagonalMoveChecker.isMovePermitted(cell, player)
				|| secondaryDiagonalMoveChecker.isMovePermitted(cell, player);
	}

}
