package edu.fmi.ai.reversi.util;

import java.util.Map;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class VerticalMoveChecker extends BaseMoveChecker {

	public VerticalMoveChecker(Map<Integer, Cell> board) {
		super(board);
	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
		return isTopMovePermitted(moveCell, forPlayer)
				|| isBottomMovePermitted(moveCell, forPlayer);
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

}
