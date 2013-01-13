package edu.fmi.ai.reversi.move;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class MoveChecker {

	private final HorizontalMoveChecker horizontalMoveChecker;

	private final VerticalMoveChecker verticalMoveChecker;

	private final MainDiagonalMoveChecker mainDiagonalMoveChecker;

	private final SecondaryDiagonalMoveChecker secondaryDiagonalMoveChecker;

	private final Board board;

	public MoveChecker(final Board board) {
		this.board = board;
		horizontalMoveChecker = new HorizontalMoveChecker(board);
		verticalMoveChecker = new VerticalMoveChecker(board);
		mainDiagonalMoveChecker = new MainDiagonalMoveChecker(board);
		secondaryDiagonalMoveChecker = new SecondaryDiagonalMoveChecker(board);
	}

	public boolean isMovePermitted(final int cellIndex, final Player player) {
		final Cell cell = board.get(cellIndex);
		return cell.isEmpty() && isMovePermitted(cell, player);
	}

	public boolean isMovePermitted(final Cell cell, final Player player) {
		return horizontalMoveChecker.isMovePermitted(cell, player)
				|| verticalMoveChecker.isMovePermitted(cell, player)
				|| mainDiagonalMoveChecker.isMovePermitted(cell, player)
				|| secondaryDiagonalMoveChecker.isMovePermitted(cell, player);
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

	public int getMainBottomNeighbourIndex(final Cell cell, final Player player) {
		return mainDiagonalMoveChecker.getBottomNeighbourIndex(cell, player);
	}

	public int getMainTopNeighbourIndex(final Cell cell, final Player player) {
		return mainDiagonalMoveChecker.getTopNeighbourIndex(cell, player);
	}

	public int getSecondaryBottomNeighbourIndex(final Cell cell, final Player player) {
		return secondaryDiagonalMoveChecker.getBottomNeighbourIndex(cell, player);
	}

	public int getSecondaryTopNeighbourIndex(final Cell cell, final Player player) {
		return secondaryDiagonalMoveChecker.getTopNeighbourIndex(cell, player);
	}

	public int getStableDiscCount(final Player player) {
		int result = 0;
		for (int i = 0; i < board.size(); ++i) {
			final Cell currentCell = board.get(i);
			if (currentCell.isOwnedBy(player) && isStableCell(currentCell, player)) {
				++result;
			}
		}
		return result;
	}

	private boolean isStableCell(final Cell cell, final Player player) {
		return horizontalMoveChecker.isStable(cell, player)
				&& verticalMoveChecker.isStable(cell, player)
				&& mainDiagonalMoveChecker.isStable(cell, player)
				&& secondaryDiagonalMoveChecker.isStable(cell, player);
	}

}
