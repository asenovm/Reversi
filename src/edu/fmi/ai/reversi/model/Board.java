package edu.fmi.ai.reversi.model;

import java.util.HashMap;
import java.util.Map;

import edu.fmi.ai.reversi.Game;

//TODO merge
public class Board {

	private final Map<Integer, Cell> board;

	public Board(final int boardRows, final int boardColumns) {
		board = new HashMap<Integer, Cell>();
		for (int i = 0; i < boardRows; ++i) {
			for (int j = 0; j < boardColumns; ++j) {
				final Cell currentCell = new Cell(j, i);
				board.put(i * boardColumns + j, currentCell);
			}
		}

		board.get(27).take(Player.WHITE);
		board.get(28).take(Player.BLACK);
		board.get(35).take(Player.WHITE);
		board.get(36).take(Player.BLACK);
	}

	public void onCellSelected(final int cellIndex, final Player owner) {
		board.get(cellIndex).take(owner);
	}

	public boolean isMovePermitted(final int cellIndex, final Player forPlayer) {
		final Cell moveCell = board.get(cellIndex);
		return moveCell.isEmpty() && isHavingNeighbour(moveCell, forPlayer);
	}

	private boolean isHavingNeighbour(final Cell moveCell,
			final Player forPlayer) {
		return isHavingHorizontalNeighbour(moveCell, forPlayer)
				|| isHavingVerticalNeighbour(moveCell, forPlayer);
	}

	private boolean isHavingVerticalNeighbour(final Cell moveCell,
			final Player forPlayer) {
		return isHavingTopNeighbour(moveCell, forPlayer)
				|| isHavingBottomNeighbour(moveCell, forPlayer);
	}

	private boolean isHavingHorizontalNeighbour(final Cell moveCell,
			final Player forPlayer) {
		return isHavingLeftNeighbour(moveCell, forPlayer)
				|| isHavingRightNeigbour(moveCell, forPlayer);
	}

	private boolean isHavingLeftNeighbour(final Cell moveCell,
			final Player forPlayer) {
		return getLeftNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private boolean isHavingRightNeigbour(final Cell moveCell,
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

	private boolean isHavingTopNeighbour(final Cell moveCell,
			final Player forPlayer) {
		return getTopNeighbourIndex(moveCell, forPlayer) > 0;
	}

	private boolean isHavingBottomNeighbour(final Cell moveCell,
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
