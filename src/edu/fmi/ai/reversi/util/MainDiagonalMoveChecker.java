package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class MainDiagonalMoveChecker extends BaseDiagonalMoveChecker {

	public MainDiagonalMoveChecker(Board board) {
		super(board);
	}

	public int getTopNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false);
	}

	public int getBottomNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true);
	}

	@Override
	public int getNeighbourIndex(final Cell cell, final Player player) {
		return Math.max(getNeighbourIndex(cell, player, true),
				getNeighbourIndex(cell, player, false));
	}

	@Override
	protected boolean isDiagonalEnd(final int cellIndex) {
		return (cellIndex % 8 == 0 || cellIndex / 8 == 0 || cellIndex % 8 == 7 || cellIndex / 8 == 7)
				&& cellIndex != 56 && cellIndex != 7;
	}

	@Override
	protected boolean canMoveBottom(final int cellIndex) {
		return !(cellIndex / 8 == 7 || cellIndex % 8 == 7);
	}

	@Override
	protected boolean canMoveTop(final int cellIndex) {
		return !(cellIndex / 8 == 0 || cellIndex % 8 == 0);
	}

	@Override
	protected int incrementIndex(final int cellIndex, final boolean isBottom) {
		return isBottom ? getMainBottom(cellIndex) : getMainTop(cellIndex);
	}

	private int getMainBottom(final int cellIndex) {
		return cellIndex + Game.BOARD_COLUMN_COUNT + 1;
	}

	private int getMainTop(final int cellIndex) {
		return cellIndex - Game.BOARD_COLUMN_COUNT - 1;
	}

}
