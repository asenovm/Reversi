package edu.fmi.ai.reversi.util;

import java.util.Map;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class DiagonalMoveChecker extends BaseMoveChecker {

	public DiagonalMoveChecker(Map<Integer, Cell> board) {
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

}
