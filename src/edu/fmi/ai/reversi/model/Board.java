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
				&& isTrappingLeft(moveCell, cellIndex, forPlayer);
	}

	private boolean isTrappingLeft(final Cell moveCell, final int cellIndex,
			final Player forPlayer) {
		int leftIndex = -1;

		if (moveCell.getX() % Game.BOARD_COLUMN_COUNT < 2) {
			return false;
		}

		for (int i = 2; i <= moveCell.getX(); ++i) {
			final Cell currentCell = board.get(cellIndex - i);
			if (currentCell.isOwnedBy(forPlayer)) {
				leftIndex = cellIndex - i;
				break;
			}
		}

		if (leftIndex == -1) {
			return false;
		}

		for (int i = leftIndex + 1; i < cellIndex; ++i) {
			final Cell currentCell = board.get(cellIndex);
			if (!currentCell.isOwnedBy(forPlayer)) {
				return true;
			}
		}

		return false;
	}
}
