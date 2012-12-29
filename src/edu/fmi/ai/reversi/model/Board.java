package edu.fmi.ai.reversi.model;

import java.util.HashMap;
import java.util.Map;

import edu.fmi.ai.reversi.Game;

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
		board.get(35).take(Player.BLACK);
		board.get(36).take(Player.WHITE);
	}

	public void onCellSelected(final int cellIndex, final Player owner) {
		board.get(cellIndex).take(owner);
	}

	public boolean isMovePermitted(final int cellIndex, final Player forPlayer) {
		final Cell moveCell = board.get(cellIndex);
		return moveCell.isEmpty()
				&& (isHavingLeftNeighbour(moveCell, cellIndex, forPlayer)
						|| isHavingRightNeigbour(moveCell, cellIndex, forPlayer)
						|| isHavingTopNeighbour(moveCell, cellIndex, forPlayer) || isHavingBottomNeighbour(
							moveCell, cellIndex, forPlayer));
	}

	/*
	 * OXXXXXnewO
	 */
	private boolean isHavingLeftNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		if (moveCell.getX() < 2) {
			return false;
		}

		int leftNeighbourIndex = getCLosestLeftNeighbour(moveCell, cellIndex,
				forPlayer);
		return isHavingHorizontalNeighbour(forPlayer, leftNeighbourIndex,
				cellIndex);
	}

	/*
	 * newOXXXXXXO
	 */
	private boolean isHavingRightNeigbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {

		if (moveCell.getX() > 5) {
			return false;
		}

		int rightIndex = getClosestRightNeighbour(moveCell, cellIndex,
				forPlayer);
		return isHavingHorizontalNeighbour(forPlayer, cellIndex, rightIndex);
	}

	private int getCLosestLeftNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		int leftNeighbourIndex = -1;

		for (int i = 2; i <= moveCell.getX(); ++i) {
			final Cell currentCell = board.get(cellIndex - i);
			if (currentCell.isOwnedBy(forPlayer)) {
				leftNeighbourIndex = cellIndex - i;
				break;
			} else if (currentCell.isEmpty()) {
				break;
			}
		}

		return leftNeighbourIndex;
	}

	private int getClosestRightNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		int rightIndex = -1;
		for (int i = 2; i < Game.BOARD_COLUMN_COUNT - moveCell.getX(); ++i) {
			final Cell currentCell = board.get(cellIndex + i);
			if (currentCell.isOwnedBy(forPlayer)) {
				rightIndex = cellIndex + i;
				break;
			} else if (currentCell.isEmpty()) {
				break;
			}
		}
		return rightIndex;
	}

	/*
	 * O X X newO
	 */
	private boolean isHavingTopNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		if (moveCell.getY() < 2) {
			return false;
		}

		int topIndex = getClosestTopNeighbour(moveCell, cellIndex, forPlayer);

		return isHavingVerticalNeighbour(cellIndex, forPlayer, topIndex);
	}

	/*
	 * newO X X O
	 */
	private boolean isHavingBottomNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		if (moveCell.getY() > 5) {
			return false;
		}

		int bottomIndex = getClosestBottomNeighbour(moveCell, cellIndex,
				forPlayer);

		return isHavingVerticalNeighbour(bottomIndex, forPlayer, cellIndex);
	}

	private int getClosestTopNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		int topIndex = -1;
		for (int i = 2; i < moveCell.getY(); ++i) {
			final Cell currentCell = board.get(cellIndex - i
					* Game.BOARD_COLUMN_COUNT);
			if (currentCell.isOwnedBy(forPlayer)) {
				topIndex = cellIndex - i * Game.BOARD_COLUMN_COUNT;
				break;
			} else if (currentCell.isEmpty()) {
				break;
			}
		}
		return topIndex;
	}

	private int getClosestBottomNeighbour(final Cell moveCell,
			final int cellIndex, final Player forPlayer) {
		int bottomIndex = -1;
		for (int i = 2; i < Game.BOARD_ROW_COUNT - moveCell.getY(); ++i) {
			final Cell currentCell = board.get(cellIndex + i
					* Game.BOARD_COLUMN_COUNT);
			if (currentCell.isOwnedBy(forPlayer)) {
				bottomIndex = cellIndex + i * Game.BOARD_COLUMN_COUNT;
				break;
			} else if (currentCell.isEmpty()) {
				break;
			}
		}
		return bottomIndex;
	}

	private boolean isHavingVerticalNeighbour(final int cellIndex,
			final Player forPlayer, int topIndex) {
		if (topIndex == -1 || cellIndex == -1) {
			return false;
		}

		for (int i = topIndex + Game.BOARD_COLUMN_COUNT; i < cellIndex; i += Game.BOARD_COLUMN_COUNT) {
			final Cell currentCell = board.get(i);
			if (currentCell.isEmpty() || currentCell.isOwnedBy(forPlayer)) {
				return false;
			}
		}

		return true;
	}

	private boolean isHavingHorizontalNeighbour(final Player forPlayer,
			final int cellIndex, int rightIndex) {
		if (rightIndex == -1 || cellIndex == -1) {
			return false;
		}

		for (int i = cellIndex + 1; i < rightIndex; ++i) {
			final Cell currentCell = board.get(i);
			if (currentCell.isEmpty() || currentCell.isOwnedBy(forPlayer)) {
				return false;
			}
		}

		return true;
	}

}
