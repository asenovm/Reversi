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
				|| isVerticalMovePermitted(moveCell, forPlayer)
				|| isMainDiagonalMovePermitted(moveCell, forPlayer)
				|| isSecondaryDiagonalMovePermitted(moveCell, forPlayer);
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
			final int currentIndex = moveCell.getIndex() - i
					* Game.BOARD_COLUMN_COUNT;
			final Cell currentCell = board.get(currentIndex);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				topIndex = currentIndex;
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
			final int currentIndex = moveCell.getIndex() + i
					* Game.BOARD_COLUMN_COUNT;
			final Cell currentCell = board.get(currentIndex);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				bottomIndex = currentIndex;
				break;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				break;
			}
		}
		return bottomIndex;
	}

	private boolean isMainDiagonalMovePermitted(final Cell moveCell,
			final Player player) {
		return isMainDiagonalBottomMovePermitted(moveCell, player)
				|| isMainDiagonalTopMovePermitted(moveCell, player);
	}

	private boolean isMainDiagonalTopMovePermitted(final Cell moveCell,
			final Player player) {
		return getDiagonalTopNeighbourIndex(moveCell, player, true) > 0;
	}

	private int getDiagonalTopNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal) {
		return getDiagonalNeighbourIndex(moveCell, player, isMainDiagonal,
				false);
	}

	private boolean isMainDiagonalBottomMovePermitted(final Cell moveCell,
			final Player player) {
		return getDiagonalBottomNeighbourIndex(moveCell, player, true) > 0;
	}

	private int getDiagonalBottomNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal) {
		return getDiagonalNeighbourIndex(moveCell, player, isMainDiagonal, true);
	}

	private int getDiagonalNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal,
			final boolean isBottom) {
		int index = -1;
		int upperBound = getDiagonalUpperBound(moveCell, isBottom);
		int sign = isBottom ? 1 : -1;
		for (int i = 1; i < upperBound; ++i) {
			final int currentIndex = getDiagonalCurrentIndex(moveCell,
					isMainDiagonal, sign, i);
			final Cell currentCell = board.get(currentIndex);
			if (isClosestNeighbour(player, i, currentCell)) {
				index = currentIndex;
				break;
			} else if (isStoppingSearch(player, i, currentCell)) {
				break;
			}
		}
		return index;
	}

	private int getDiagonalCurrentIndex(final Cell moveCell,
			final boolean isMainDiagonal, int sign, int i) {
		return moveCell.getIndex() + i * sign
				* (Game.BOARD_COLUMN_COUNT + (isMainDiagonal ? 1 : -1));
	}

	private int getDiagonalUpperBound(final Cell moveCell,
			final boolean isBottom) {
		return isBottom ? Game.BOARD_ROW_COUNT - moveCell.getY() : moveCell
				.getY();
	}

	private boolean isSecondaryDiagonalBottomMovePermitted(final Cell moveCell,
			final Player player) {
		return getDiagonalBottomNeighbourIndex(moveCell, player, false) > 0;
	}

	private boolean isSecondaryDiagonalTopMovePermitted(final Cell moveCell,
			final Player player) {
		return getDiagonalTopNeighbourIndex(moveCell, player, false) > 0;
	}

	private boolean isSecondaryDiagonalMovePermitted(final Cell moveCell,
			final Player forPlayer) {
		return isSecondaryDiagonalBottomMovePermitted(moveCell, forPlayer)
				|| isSecondaryDiagonalTopMovePermitted(moveCell, forPlayer);
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
