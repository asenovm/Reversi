package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class HorizontalMoveChecker extends BaseLineMoveChecker {

	public HorizontalMoveChecker(final Board board) {
		super(board);
	}
	

	public int getLeftNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true, true);
	}

	public int getRightNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false, true);
	}

	public boolean isStable(final Cell cell, final Player player) {
		return isStableLeft(cell, player) || isStableRight(cell, player);
	}

	public boolean isStableLeft(final Cell cell, final Player player) {
		return isStable(cell, player, true);
	}

	public boolean isStableRight(final Cell cell, final Player player) {
		return isStable(cell, player, false);
	}

	@Override
	protected int incrementIndex(int cellIndex, boolean isMinusDirection) {
		return isMinusDirection ? cellIndex - 1 : cellIndex + 1;
	}

	@Override
	protected int getEndIndex(final Cell startCell, boolean isMinusDirection) {
		return isMinusDirection ? startCell.getX() : Game.BOARD_COLUMN_COUNT - startCell.getX();
	}

}
