package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class DiagonalMoveChecker extends BaseMoveChecker {

	public DiagonalMoveChecker(final Board board) {
		super(board);
	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
		return isMainDiagonalMovePermitted(moveCell, forPlayer)
				|| isSecondaryDiagonalMovePermitted(moveCell, forPlayer);
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

	public int getDiagonalTopNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal) {
		return getDiagonalNeighbourIndex(moveCell, player, isMainDiagonal,
				false);
	}

	private boolean isMainDiagonalBottomMovePermitted(final Cell moveCell,
			final Player player) {
		return getDiagonalBottomNeighbourIndex(moveCell, player, true) > 0;
	}

	public int getDiagonalBottomNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal) {
		return getDiagonalNeighbourIndex(moveCell, player, isMainDiagonal, true);
	}

	private int getDiagonalNeighbourIndex(final Cell moveCell,
			final Player player, final boolean isMainDiagonal,
			final boolean isBottom) {
		int index = -1;
		int sign = isBottom ? 1 : -1;
		int cellIndex = getStartCellIndex(moveCell, isMainDiagonal, isBottom,
				sign);
		int i = 1;
		while (!isEndDiagonalCell(cellIndex, isMainDiagonal, isBottom)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, i, currentCell)) {
				index = cellIndex;
				break;
			} else if (isStoppingSearch(player, i, currentCell)) {
				break;
			}
			++i;
			cellIndex = getNextDiagonalIndex(cellIndex, isMainDiagonal, sign);

		}
		return index;
	}

	private int getStartCellIndex(final Cell moveCell,
			final boolean isMainDiagonal, final boolean isBottom, int sign) {
		int cellIndex = moveCell.getIndex();
		if (isEndDiagonalCell(cellIndex, isMainDiagonal, isBottom)) {
			return cellIndex;
		}
		return getNextDiagonalIndex(cellIndex, isMainDiagonal, sign);
	}

	private boolean isEndDiagonalCell(final int cellIndex,
			final boolean isMainDiagonal, final boolean isBottom) {
		if (isMainDiagonal && !isBottom) {
			return cellIndex % Game.BOARD_COLUMN_COUNT == 0
					|| cellIndex / Game.BOARD_ROW_COUNT == 0;
		} else if (isMainDiagonal && isBottom) {
			return cellIndex % Game.BOARD_COLUMN_COUNT == 7
					|| cellIndex / Game.BOARD_ROW_COUNT == 7;
		} else if (!isMainDiagonal && !isBottom) {
			return cellIndex % Game.BOARD_COLUMN_COUNT == 7
					|| cellIndex / Game.BOARD_ROW_COUNT == 0;
		} else {
			return cellIndex % 8 == Game.BOARD_COLUMN_COUNT
					|| cellIndex / Game.BOARD_ROW_COUNT == 7;
		}
	}

	private int getNextDiagonalIndex(final int cellIndex,
			final boolean isMainDiagonal, int sign) {
		return cellIndex + sign
				* (Game.BOARD_COLUMN_COUNT + (isMainDiagonal ? 1 : -1));
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

}
