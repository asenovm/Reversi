package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;

public class SecondaryDiagonalMoveChecker extends BaseDiagonalMoveChecker {

	protected SecondaryDiagonalMoveChecker(Board board) {
		super(board);
	}

	@Override
	protected int incrementIndex(final int cellIndex, final boolean isNegativeDirection) {
		return isNegativeDirection ? getSecondaryTop(cellIndex) : getSecondaryBottom(cellIndex);
	}

	private int getSecondaryBottom(final int cellIndex) {
		return cellIndex + Game.BOARD_COLUMN_COUNT - 1;
	}

	private int getSecondaryTop(final int cellIndex) {
		return cellIndex - Game.BOARD_COLUMN_COUNT + 1;
	}

	@Override
	protected boolean isDiagonalEnd(final int cellIndex) {
		return (cellIndex % 8 == 0 || cellIndex / 8 == 0 || cellIndex % 8 == 7 || cellIndex / 8 == 7)
				&& cellIndex != 0 && cellIndex != 63;
	}

	@Override
	protected boolean canMoveBottom(final int cellIndex) {
		return !(cellIndex % 8 == 0 || cellIndex / 8 == 7);
	}

	@Override
	protected boolean canMoveTop(final int cellIndex) {
		return !(cellIndex / 8 == 0 || cellIndex % 8 == 7);
	}
}
