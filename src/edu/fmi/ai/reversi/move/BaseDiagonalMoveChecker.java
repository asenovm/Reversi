package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public abstract class BaseDiagonalMoveChecker extends BaseMoveChecker {

	protected BaseDiagonalMoveChecker(Board board) {
		super(board);
	}

	@Override
	public int getNeighbourIndex(final Cell cell, final Player player) {
		return Math.max(getNeighbourIndex(cell, player, true, true),
				getNeighbourIndex(cell, player, false, true));
	}

	public int getTopNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, false, true);
	}

	public int getBottomNeighbourIndex(final Cell cell, final Player player) {
		return getNeighbourIndex(cell, player, true, true);
	}

	public boolean isStableTop(final Cell cell, final Player player) {
		final Player otherPlayer = Player.getOpponent(player);
		return getNeighbourIndex(cell, otherPlayer, false, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, false, false) < 0;
	}

	public boolean isStableBottom(final Cell cell, final Player player) {
		final Player otherPlayer = Player.getOpponent(player);
		return getNeighbourIndex(cell, otherPlayer, true, false) < 0
				&& getNeighbourIndex(cell, Player.UNKNOWN, true, false) < 0;
	}

	public boolean hasStable(final Cell cell, final Player player) {
		return isStableTop(cell, player) || isStableBottom(cell, player);
	}

	@Override
	protected int getNeighbourIndex(final Cell cell, final Player player, final boolean isBottom,
			final boolean isStoppingSearch) {
		int cellIndex = cell.getIndex();
		int currentNeighbour = 1;

		if (!canMove(isBottom, cellIndex)) {
			return -1;
		}

		cellIndex = incrementIndex(cellIndex, isBottom);

		while (!isDiagonalEnd(cellIndex)) {
			final Cell currentCell = board.get(cellIndex);
			if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
				return cellIndex;
			} else if (isStoppingSearch && isStoppingSearch(player, currentNeighbour, currentCell)) {
				return -1;
			}
			cellIndex = incrementIndex(cellIndex, isBottom);
			++currentNeighbour;
		}

		Cell currentCell = board.get(cellIndex);
		if (isClosestNeighbour(player, currentNeighbour, currentCell)) {
			return cellIndex;
		}
		return -1;
	}

	private boolean canMove(final boolean isBottom, int cellIndex) {
		return !((isBottom && !canMoveBottom(cellIndex)) || (!isBottom && !canMoveTop(cellIndex)));
	}

	protected abstract boolean canMoveTop(final int cellIndex);

	protected abstract boolean canMoveBottom(final int cellIndex);

	protected abstract boolean isDiagonalEnd(final int cellIndex);

}
