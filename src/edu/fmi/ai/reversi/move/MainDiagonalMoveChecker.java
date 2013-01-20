package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;

public class MainDiagonalMoveChecker extends BaseDiagonalMoveChecker {

	public MainDiagonalMoveChecker(Board board) {
		super(board);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isDiagonalEnd(final int cellIndex) {
		return (cellIndex % 8 == 0 || cellIndex / 8 == 0 || cellIndex % 8 == 7 || cellIndex / 8 == 7)
				&& cellIndex != 56 && cellIndex != 7;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canMoveBottom(final int cellIndex) {
		return !(cellIndex / 8 == 7 || cellIndex % 8 == 7);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean canMoveTop(final int cellIndex) {
		return !(cellIndex / 8 == 0 || cellIndex % 8 == 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int incrementIndex(final int cellIndex, final boolean isNegativeDirection) {
		return isNegativeDirection ? getMainTop(cellIndex) : getMainBottom(cellIndex);
	}

	private int getMainBottom(final int cellIndex) {
		return cellIndex + Game.BOARD_COLUMN_COUNT + 1;
	}

	private int getMainTop(final int cellIndex) {
		return cellIndex - Game.BOARD_COLUMN_COUNT - 1;
	}

}
