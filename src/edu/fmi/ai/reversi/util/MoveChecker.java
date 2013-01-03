package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class MoveChecker {

	private final HorizontalMoveChecker horizontalMoveChecker;

	private final VerticalMoveChecker verticalMoveChecker;

	private final DiagonalMoveChecker diagonalMoveChecker;

	private final Board board;

	public MoveChecker(final Board board) {
		this.board = board;
		horizontalMoveChecker = new HorizontalMoveChecker(board);
		verticalMoveChecker = new VerticalMoveChecker(board);
		diagonalMoveChecker = new DiagonalMoveChecker(board);

	}

	public boolean isMovePermitted(final int cellIndex, final Player player) {
		final Cell cell = board.get(cellIndex);
		return cell.isEmpty() && isMovePermitted(cell, player);
	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
		return horizontalMoveChecker.isMovePermitted(moveCell, forPlayer)
				|| verticalMoveChecker.isMovePermitted(moveCell, forPlayer)
				|| diagonalMoveChecker.isMovePermitted(moveCell, forPlayer);
	}

	public int getLeftNeighbourIndex(final Cell moveCell, final Player player) {
		return horizontalMoveChecker.getLeftNeighbourIndex(moveCell, player);
	}

	public int getTopNeighbourIndex(final Cell moveCell, final Player player) {
		return verticalMoveChecker.getTopNeighbourIndex(moveCell, player);
	}

	public int getRightNeighbourIndex(final Cell moveCell, final Player player) {
		return horizontalMoveChecker.getRightNeighbourIndex(moveCell, player);
	}

	public int getBottomNeighbourIndex(final Cell moveCell, final Player player) {
		return verticalMoveChecker.getBottomNeighbourIndex(moveCell, player);
	}

	public int getMainDiagonalTopNeighbourIndex(final Cell moveCell, final Player player) {
		return diagonalMoveChecker.getDiagonalTopNeighbourIndex(moveCell, player, true);
	}

	public int getMainDiagonalBottomNeighbourIndex(final Cell moveCell, final Player player) {
		return diagonalMoveChecker.getDiagonalBottomNeighbourIndex(moveCell, player, true);
	}

	public int getSecondaryDiagonalTopNeighbourIndex(final Cell moveCell, final Player player) {
		return diagonalMoveChecker.getDiagonalTopNeighbourIndex(moveCell, player, false);
	}

	public int getSecondaryDiagonalBottomNeighbourIndex(final Cell moveCell, final Player player) {
		return diagonalMoveChecker.getDiagonalBottomNeighbourIndex(moveCell, player, false);
	}

}
