package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class HorizontalMoveChecker extends BaseMoveChecker {

	public HorizontalMoveChecker(final Board board) {
		super(board);
	}

	@Override
	public int getNeighbourIndex(Cell cell, Player player) {
		return Math.max(getLeftNeighbourIndex(cell, player), getRightNeighbourIndex(cell, player));
	}

	public int getLeftNeighbourIndex(final Cell cell, final Player player) {
		for (int i = 1; i <= cell.getX(); ++i) {
			final Cell currentCell = board.get(cell.getIndex() - i);
			if (isClosestNeighbour(player, i, currentCell)) {
				return cell.getIndex() - i;
			} else if (isStoppingSearch(player, i, currentCell)) {
				return -1;
			}
		}
		return -1;
	}

	public int getRightNeighbourIndex(final Cell cell, final Player forPlayer) {
		for (int i = 1; i < Game.BOARD_COLUMN_COUNT - cell.getX(); ++i) {
			final Cell currentCell = board.get(cell.getIndex() + i);
			if (isClosestNeighbour(forPlayer, i, currentCell)) {
				return cell.getIndex() + i;
			} else if (isStoppingSearch(forPlayer, i, currentCell)) {
				return -1;
			}
		}
		return -1;
	}

}
