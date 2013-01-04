package edu.fmi.ai.reversi.move;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class CellTaker {

	private final MoveChecker checker;

	private final Board board;

	public CellTaker(final MoveChecker checker, final Board board) {
		this.checker = checker;
		this.board = board;
	}

	public Collection<Cell> takeSurroundedCells(final Cell cell, final Player player) {
		final Set<Cell> takenCells = new LinkedHashSet<Cell>();
		takenCells.add(cell);
		takenCells.addAll(takeHorizontalCells(cell, player));
		takenCells.addAll(takeVerticalCells(cell, player));
		takenCells.addAll(takeDiagonalCells(cell, player));
		return takenCells;
	}

	public Collection<Cell> takeCell(final int cellIndex, final Player player) {
		final Cell moveCell = board.get(cellIndex);
		moveCell.take(player);
		return takeSurroundedCells(moveCell, player);
	}

	private Collection<Cell> takeVerticalCells(final Cell cell, final Player player) {
		final Set<Cell> result = new HashSet<Cell>();
		result.addAll(takeBottomCells(cell, player));
		result.addAll(takeTopCells(cell, player));
		return result;
	}

	private Collection<Cell> takeHorizontalCells(final Cell cell, final Player player) {
		final Set<Cell> result = new HashSet<Cell>();
		result.addAll(takeLeftCells(cell, player));
		result.addAll(takeRightCells(cell, player));
		return result;
	}

	private Collection<Cell> takeDiagonalCells(final Cell cell, final Player player) {
		final Set<Cell> result = new HashSet<Cell>();
		result.addAll(takeMainTopCells(cell, player));
		result.addAll(takeMainBottomCells(cell, player));
		result.addAll(takeSecondaryTopCells(cell, player));
		result.addAll(takeSecondaryBottomCells(cell, player));
		return result;
	}

	private Collection<Cell> takeSecondaryTopCells(final Cell cell, final Player player) {
		int secondaryTopIndex = checker.getSecondaryTopNeighbourIndex(cell, player);
		return takeCells(secondaryTopIndex, secondaryTopIndex, cell.getIndex(),
				Game.BOARD_COLUMN_COUNT - 1, player);
	}

	private Collection<Cell> takeSecondaryBottomCells(final Cell cell, final Player player) {
		int secondaryBottomIndex = checker.getSecondaryBottomNeighbourIndex(cell, player);
		return takeCells(secondaryBottomIndex, cell.getIndex(), secondaryBottomIndex,
				Game.BOARD_COLUMN_COUNT - 1, player);
	}

	private Collection<Cell> takeMainTopCells(final Cell cell, final Player player) {
		int mainTopIndex = checker.getMainTopNeighbourIndex(cell, player);
		return takeCells(mainTopIndex, mainTopIndex, cell.getIndex(), Game.BOARD_COLUMN_COUNT + 1,
				player);
	}

	private Collection<Cell> takeMainBottomCells(final Cell cell, final Player player) {
		int mainBottomIndex = checker.getMainBottomNeighbourIndex(cell, player);
		return takeCells(mainBottomIndex, cell.getIndex(), mainBottomIndex,
				Game.BOARD_COLUMN_COUNT + 1, player);
	}

	private Collection<Cell> takeTopCells(final Cell cell, final Player player) {
		int bottomNeigbhourIndex = checker.getBottomNeighbourIndex(cell, player);
		return takeCells(bottomNeigbhourIndex, cell.getIndex(), bottomNeigbhourIndex,
				Game.BOARD_COLUMN_COUNT, player);
	}

	private Collection<Cell> takeBottomCells(final Cell cell, final Player player) {
		int topNeighbourIndex = checker.getTopNeighbourIndex(cell, player);
		return takeCells(topNeighbourIndex, topNeighbourIndex, cell.getIndex(),
				Game.BOARD_COLUMN_COUNT, player);
	}

	private Collection<Cell> takeRightCells(final Cell cell, final Player player) {
		int rightNeighbourIndex = checker.getRightNeighbourIndex(cell, player);
		return takeCells(rightNeighbourIndex, cell.getIndex(), rightNeighbourIndex, 1, player);
	}

	private Collection<Cell> takeLeftCells(final Cell cell, final Player player) {
		int leftNeighbourindex = checker.getLeftNeighbourIndex(cell, player);
		return takeCells(leftNeighbourindex, leftNeighbourindex, cell.getIndex(), 1, player);
	}

	private Collection<Cell> takeCells(final int fromIndex, final int toIndex, final int step,
			final Player player) {
		final Collection<Cell> result = new HashSet<Cell>();
		for (int i = fromIndex; i <= toIndex; i += step) {
			final Cell currentCell = board.get(i);
			currentCell.take(player);
			result.add(currentCell);
		}
		return result;
	}

	private Collection<Cell> takeCells(final int neighbourIndex, final int start, final int end,
			final int step, final Player player) {
		return neighbourIndex > 0 ? takeCells(start, end, step, player) : Collections
				.<Cell> emptySet();
	}

}
