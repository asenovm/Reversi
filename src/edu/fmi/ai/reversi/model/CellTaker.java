package edu.fmi.ai.reversi.model;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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

	public Collection<Cell> takeSurroundedCells(final Cell moveCell, final Player owner) {
		final Set<Cell> takenCells = new LinkedHashSet<Cell>();
		takenCells.add(moveCell);
		tryTakeHorizontalCells(moveCell, owner, takenCells);
		tryTakeVerticalCells(moveCell, owner, takenCells);
		takeDiagonalCells(moveCell, owner, takenCells);
		return takenCells;
	}

	private void takeDiagonalCells(final Cell moveCell, final Player owner,
			final Set<Cell> changedCells) {
		takeMainTopCells(moveCell, owner, changedCells);
		takeMainBottomCells(moveCell, owner, changedCells);
		takeSecondaryTopCells(moveCell, owner, changedCells);
		takeSecondaryBottomCells(moveCell, owner, changedCells);
	}

	private void takeSecondaryTopCells(final Cell cell, final Player player,
			final Set<Cell> changedCells) {
		int secondaryTopIndex = checker.getSecondaryTopIndex(cell, player);
		if (secondaryTopIndex > 0) {
			takeCells(changedCells, secondaryTopIndex, cell.getIndex(),
					Game.BOARD_COLUMN_COUNT - 1, player);
		}
	}

	private void takeSecondaryBottomCells(final Cell cell, final Player player,
			final Set<Cell> changedCells) {
		int secondaryBottomIndex = checker.getSecondaryBottomIndex(cell, player);
		if (secondaryBottomIndex > 0) {
			takeCells(changedCells, cell.getIndex(), secondaryBottomIndex,
					Game.BOARD_COLUMN_COUNT - 1, player);
		}
	}

	private void takeMainTopCells(final Cell cell, final Player player, final Set<Cell> changedCells) {
		int mainTopIndex = checker.getMainTopIndex(cell, player);
		if (mainTopIndex > 0) {
			takeCells(changedCells, mainTopIndex, cell.getIndex(), Game.BOARD_COLUMN_COUNT + 1,
					player);
		}
	}

	private void takeMainBottomCells(final Cell cell, final Player player,
			final Set<Cell> changedCells) {
		int mainBottomIndex = checker.getMainBottomIndex(cell, player);
		if (mainBottomIndex > 0) {
			takeCells(changedCells, cell.getIndex(), mainBottomIndex, Game.BOARD_COLUMN_COUNT + 1,
					player);
		}
	}

	private void tryTakeVerticalCells(final Cell moveCell, final Player owner,
			final Set<Cell> changedCells) {
		tryTakeBottomCells(owner, moveCell, changedCells);
		tryTakeTopCells(owner, moveCell, changedCells);
	}

	private void tryTakeHorizontalCells(final Cell moveCell, final Player owner,
			final Set<Cell> changedCells) {
		tryTakeLeftCells(owner, moveCell, changedCells);
		tryTakeRightCells(owner, moveCell, changedCells);
	}

	private void tryTakeTopCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int bottomNeigbhourIndex = checker.getBottomNeighbourIndex(moveCell, owner);
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
		int rightNeighbourIndex = checker.getRightNeighbourIndex(moveCell, owner);
		if (rightNeighbourIndex > 0) {
			takeCells(changedCells, moveCell.getIndex(), rightNeighbourIndex, 1, owner);
		}
	}

	private void tryTakeLeftCells(final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int leftNeighbourindex = checker.getLeftNeighbourIndex(moveCell, owner);
		if (leftNeighbourindex > 0) {
			takeCells(changedCells, leftNeighbourindex, moveCell.getIndex(), 1, owner);
		}
	}

	private void takeCells(final Collection<Cell> changedCells, final int fromIndex,
			final int toIndex, final int step, final Player forPlayer) {
		for (int i = fromIndex; i <= toIndex; i += step) {
			final Cell currentCell = board.get(i);
			if (!changedCells.contains(currentCell)) {
				currentCell.take(forPlayer);
				changedCells.add(currentCell);
			}
		}
	}

	public Collection<Cell> takeCell(final int cellIndex, final Player player) {
		final Cell moveCell = board.get(cellIndex);
		moveCell.take(player);

		final Collection<Cell> result = new LinkedList<Cell>();
		result.add(moveCell);
		result.addAll(takeSurroundedCells(moveCell, player));

		return result;
	}

}
