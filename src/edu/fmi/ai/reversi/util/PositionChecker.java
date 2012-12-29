package edu.fmi.ai.reversi.util;

import java.util.Map;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class PositionChecker {

	private final Map<Integer, Cell> board;

	public PositionChecker(final Map<Integer, Cell> board) {
		this.board = board;
	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
		return isHorizontalMovePermitted(moveCell, forPlayer)
				|| isVerticalMovePermitted(moveCell, forPlayer);
	}

	private boolean isVerticalMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return isTopMovePermitted(moveCell, forPlayer)
				|| isBottomMovePermitted(moveCell, forPlayer);
	}

	private boolean isHorizontalMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return isLeftMovePermitted(moveCell, forPlayer)
				|| isRightMovePermitted(moveCell, forPlayer);
	}

	private boolean isLeftMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return getLeftNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private boolean isRightMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return getRightNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private int getLeftNeighbourIndex(final Cell moveCell,
			final Player forPlayer) {
		int leftNeighbourIndex = -1;

		for (int i = 1; i <= moveCell.getX(); ++i) {
			final Cell currentCell = board.get(moveCell.getIndex() - i);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				leftNeighbourIndex = moveCell.getIndex() - i;
				break;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				break;
			}
		}

		return leftNeighbourIndex;
	}

	private int getRightNeighbourIndex(final Cell moveCell,
			final Player forPlayer) {
		int rightIndex = -1;
		for (int i = 1; i < Game.BOARD_COLUMN_COUNT - moveCell.getX(); ++i) {
			final Cell currentCell = board.get(moveCell.getIndex() + i);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				rightIndex = moveCell.getIndex() + i;
				break;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				break;
			}
		}
		return rightIndex;
	}

	private boolean isTopMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return getTopNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private boolean isBottomMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return getBottomNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private int getTopNeighbourIndex(final Cell moveCell, final Player forPlayer) {
		int topIndex = -1;
		for (int i = 1; i < moveCell.getY(); ++i) {
			final Cell currentCell = board.get(moveCell.getIndex() - i
					* Game.BOARD_COLUMN_COUNT);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				topIndex = moveCell.getIndex() - i * Game.BOARD_COLUMN_COUNT;
				break;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				break;
			}
		}
		return topIndex;
	}

	private int getBottomNeighbourIndex(final Cell moveCell,
			final Player forPlayer) {
		int bottomIndex = -1;
		for (int i = 1; i < Game.BOARD_ROW_COUNT - moveCell.getY(); ++i) {
			final Cell currentCell = board.get(moveCell.getIndex() + i
					* Game.BOARD_COLUMN_COUNT);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				bottomIndex = moveCell.getIndex() + i * Game.BOARD_COLUMN_COUNT;
				break;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				break;
			}
		}
		return bottomIndex;
	}

	private boolean isStoppingSearch(final Player forPlayer, int i,
			final Cell currentCell) {
		return currentCell.isEmpty()
				|| (currentCell.isOwnedBy(forPlayer) && i == 1);
	}

	private boolean isClosestNeighbour(final Player forPlayer, int i,
			final Cell currentCell) {
		return currentCell.isOwnedBy(forPlayer) && i > 1;
	}

}
