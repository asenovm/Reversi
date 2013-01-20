package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;

public class MainDiagonalMoveChecker extends BaseDiagonalMoveChecker {

	public MainDiagonalMoveChecker(Board board) {
		super(board);
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
	protected int incrementIndex(final int cellIndex, final boolean isMinusDirection) {
		return isMinusDirection ? getMainTop(cellIndex) : getMainBottom(cellIndex);
	}

	private int getMainBottom(final int cellIndex) {
		return cellIndex + Game.BOARD_COLUMN_COUNT + 1;
	}

	private int getMainTop(final int cellIndex) {
		return cellIndex - Game.BOARD_COLUMN_COUNT - 1;
	}

}
