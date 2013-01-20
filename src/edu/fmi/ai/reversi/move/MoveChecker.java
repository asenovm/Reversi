package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.Direction;

public class MoveChecker {

	private final HorizontalMoveChecker horizontalMoveChecker;

	private final VerticalMoveChecker verticalMoveChecker;

	private final MainDiagonalMoveChecker mainDiagonalMoveChecker;

	private final SecondaryDiagonalMoveChecker secondaryDiagonalMoveChecker;

	private final Board board;

	public MoveChecker(final Board board) {
		this.board = board;
		horizontalMoveChecker = new HorizontalMoveChecker(board);
		verticalMoveChecker = new VerticalMoveChecker(board);
		mainDiagonalMoveChecker = new MainDiagonalMoveChecker(board);
		secondaryDiagonalMoveChecker = new SecondaryDiagonalMoveChecker(board);
	}

	public boolean isMovePermitted(final int cellIndex, final Player player) {
		final Cell cell = board.get(cellIndex);
		return cell.isEmpty() && isMovePermitted(cell, player);
	}

	public boolean isMovePermitted(final Cell cell, final Player player) {
		return horizontalMoveChecker.isMovePermitted(cell, player)
				|| verticalMoveChecker.isMovePermitted(cell, player)
				|| mainDiagonalMoveChecker.isMovePermitted(cell, player)
				|| secondaryDiagonalMoveChecker.isMovePermitted(cell, player);
	}

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

}
