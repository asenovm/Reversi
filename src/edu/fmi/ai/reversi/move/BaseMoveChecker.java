package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseMoveChecker {

	protected Board board;

	protected BaseMoveChecker(final Board board) {
		this.board = board;
	}

	protected boolean isStoppingSearch(final Player player, int currentNeighbour,
			final Cell currentCell) {
		return currentCell.isEmpty() || (currentCell.isOwnedBy(player) && currentNeighbour == 1);
	}

	protected boolean isClosestNeighbour(final Player player, int currentNeighbour,
			final Cell currentCell) {
		return currentCell.isOwnedBy(player) && (currentNeighbour > 1 || player == Player.UNKNOWN);
	}

	public boolean isMovePermitted(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player) >= 0;
	}

	protected boolean isStableCell(final Cell cell, final Player player,
			final boolean isNegativeDirection) {
		final Player otherPlayer = Player.getOpponent(player);
		return isHavingSameColorNeighbours(cell, isNegativeDirection, otherPlayer) || isLineFull(cell);
	}

	private boolean isLineFull(final Cell cell) {
		return getNeighbourIndex(cell, Player.UNKNOWN, true, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, false, false) < 0;
	}

	private boolean isHavingSameColorNeighbours(final Cell cell, final boolean isNegativeDirection,
			final Player otherPlayer) {
		return getNeighbourIndex(cell, otherPlayer, isNegativeDirection, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, isNegativeDirection, false) < 0;
	}

	protected int getNeighbourIndex(Cell cell, Player player) {
		return Math.max(getNeighbourIndex(cell, player, false, true),
				getNeighbourIndex(cell, player, true, true));
	}

	protected abstract int getNeighbourIndex(final Cell cell, final Player player,
			final boolean isNegativeDirection, final boolean isStoppingSearch);

	protected abstract int incrementIndex(final int cellIndex, final boolean isNegativeDirection);

}
