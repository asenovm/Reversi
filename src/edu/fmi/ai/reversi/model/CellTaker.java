package edu.fmi.ai.reversi.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.util.MoveChecker;

public class CellTaker {

	private final MoveChecker checker;

	private final Board board;

	public CellTaker(final MoveChecker checker, final Board board) {
		this.checker = checker;
		this.board = board;
	}

	public Collection<Cell> takeSurroundedCells(final Cell moveCell,
			final Player owner) {
		final Set<Cell> takenCells = new HashSet<Cell>();
		tryTakeHorizontalCells(moveCell, owner, takenCells);
		tryTakeVerticalCells(moveCell, owner, takenCells);
		tryTakeDiagonalCells(moveCell, owner, takenCells);
		return takenCells;
	}

	private void tryTakeDiagonalCells(final Cell moveCell, final Player owner,
			final Set<Cell> changedCells) {
		tryTakeDiagonalTopCcells(owner, moveCell, changedCells);
		tryTakeDiagonalBottomCells(owner, moveCell, changedCells);
		tryTakeSecondaryDiagonalTopCells(owner, moveCell, changedCells);
		tryTakeSecondaryDiagonalBottomCells(owner, moveCell, changedCells);
	}

	private void tryTakeVerticalCells(final Cell moveCell, final Player owner,
			final Set<Cell> changedCells) {
		tryTakeBottomCells(owner, moveCell, changedCells);
		tryTakeTopCells(owner, moveCell, changedCells);
	}

	private void tryTakeHorizontalCells(final Cell moveCell,
			final Player owner, final Set<Cell> changedCells) {
		tryTakeLeftCells(owner, moveCell, changedCells);
		tryTakeRightCells(owner, moveCell, changedCells);
	}

	private void tryTakeSecondaryDiagonalBottomCells(final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int diagonalBottomIndex = checker
				.getSecondaryDiagonalBottomNeighbourIndex(moveCell, owner);
		if (diagonalBottomIndex > 0) {
			takeCells(changedCells, moveCell.getIndex(), diagonalBottomIndex,
					Game.BOARD_COLUMN_COUNT - 1, owner);
		}
	}

	private void tryTakeSecondaryDiagonalTopCells(final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int diagonalTopIndex = checker.getSecondaryDiagonalTopNeighbourIndex(
				moveCell, owner);
		if (diagonalTopIndex > 0) {
			takeCells(changedCells, diagonalTopIndex, moveCell.getIndex(),
					Game.BOARD_COLUMN_COUNT - 1, owner);
		}
	}

	private void tryTakeDiagonalBottomCells(final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int diagonalBottomIndex = checker.getMainDiagonalBottomNeighbourIndex(
				moveCell, owner);
		if (diagonalBottomIndex > 0) {
			takeCells(changedCells, moveCell.getIndex(), diagonalBottomIndex,
					Game.BOARD_COLUMN_COUNT + 1, owner);
		}
	}

	private void tryTakeDiagonalTopCcells(final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int diagonalTopIndex = checker.getMainDiagonalTopNeighbourIndex(
				moveCell, owner);
		if (diagonalTopIndex > 0) {
			takeCells(changedCells, diagonalTopIndex, moveCell.getIndex(),
					Game.BOARD_COLUMN_COUNT + 1, owner);
		}
	}

	private void tryTakeTopCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int bottomNeigbhourIndex = checker.getBottomNeighbourIndex(moveCell,
				owner);
		if (bottomNeigbhourIndex > 0) {
			takeCells(changedCells, moveCell.getIndex(), bottomNeigbhourIndex,
					Game.BOARD_COLUMN_COUNT, owner);
		}
	}

	private void tryTakeBottomCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int topNeighbourIndex = checker.getTopNeighbourIndex(moveCell, owner);
		if (topNeighbourIndex > 0) {
			takeCells(changedCells, topNeighbourIndex, moveCell.getIndex(),
					Game.BOARD_COLUMN_COUNT, owner);
		}
	}

	private void tryTakeRightCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int rightNeighbourIndex = checker.getRightNeighbourIndex(moveCell,
				owner);
		if (rightNeighbourIndex > 0) {
			takeCells(changedCells, moveCell.getIndex(), rightNeighbourIndex,
					1, owner);
		}
	}

	private void tryTakeLeftCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int leftNeighbourindex = checker.getLeftNeighbourIndex(moveCell, owner);
		if (leftNeighbourindex > 0) {
			takeCells(changedCells, leftNeighbourindex, moveCell.getIndex(), 1,
					owner);
		}
	}

	private void takeCells(final Collection<Cell> changedCells,
			final int fromIndex, final int toIndex, final int step,
			final Player forPlayer) {
		for (int i = fromIndex; i <= toIndex; i += step) {
			final Cell currentCell = board.get(i);
			currentCell.take(forPlayer);
			changedCells.add(currentCell);
		}
	}

}
