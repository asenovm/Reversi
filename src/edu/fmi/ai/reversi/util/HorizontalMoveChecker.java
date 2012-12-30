package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class HorizontalMoveChecker extends BaseMoveChecker {

	public HorizontalMoveChecker(final Board board) {
		super(board);
	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
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

	public int getLeftNeighbourIndex(final Cell moveCell, final Player forPlayer) {
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

	public int getRightNeighbourIndex(final Cell moveCell,
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

}
