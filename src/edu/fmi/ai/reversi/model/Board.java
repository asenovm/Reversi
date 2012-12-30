package edu.fmi.ai.reversi.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.ModelObserver;
import edu.fmi.ai.reversi.util.MoveChecker;

public class Board {

	private final Set<ModelObserver> observers;

	private final Map<Integer, Cell> board;

	private final MoveChecker checker;

	public Board(final int boardRows, final int boardColumns) {
		board = new HashMap<Integer, Cell>();
		for (int i = 0; i < boardRows; ++i) {
			for (int j = 0; j < boardColumns; ++j) {
				final Cell currentCell = new Cell(j, i);
				board.put(i * boardColumns + j, currentCell);
			}
		}

		board.get(Game.POSITION_CENTER_TOP_LEFT).take(Player.WHITE);
		board.get(Game.POSITION_CENTER_TOP_RIGHT).take(Player.BLACK);
		board.get(Game.POSITION_CENTER_BOTTOM_LEFT).take(Player.BLACK);
		board.get(Game.POSITION_CENTER_BOTTOM_RIGHT).take(Player.WHITE);

		// TODO remove exposure
		checker = new MoveChecker(Collections.unmodifiableMap(board));

		observers = new HashSet<ModelObserver>();
	}

	public void onCellSelected(final int cellIndex, final Player owner) {
		final Cell moveCell = board.get(cellIndex);
		final Set<Cell> changedCells = new HashSet<Cell>();
		moveCell.take(owner);

		tryTakeLeftCells(cellIndex, owner, moveCell, changedCells);
		tryTakeRightCells(cellIndex, owner, moveCell, changedCells);
		tryTakeBottomCells(cellIndex, owner, moveCell, changedCells);
		tryTakeTopCells(cellIndex, owner, moveCell, changedCells);

		tryTakeDiagonalTopCcells(cellIndex, owner, moveCell, changedCells);
		tryTakeDiagonalBottomCells(cellIndex, owner, moveCell, changedCells);

		tryTakeSecondaryDiagonalTopCells(cellIndex, owner, moveCell,
				changedCells);
		tryTakeSecondaryDiagonalBottomCells(cellIndex, owner, moveCell,
				changedCells);

		changedCells.add(moveCell);

		notifyDataSetChanged(changedCells);
	}

	private void tryTakeSecondaryDiagonalBottomCells(final int cellIndex,
			final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int diagonalBottomIndex = checker
				.getSecondaryDiagonalBottomNeighbourIndex(moveCell, owner);
		if (diagonalBottomIndex > 0) {
			takeCells(changedCells, cellIndex, diagonalBottomIndex,
					Game.BOARD_COLUMN_COUNT - 1, owner);
		}
	}

	private void tryTakeSecondaryDiagonalTopCells(final int cellIndex,
			final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int diagonalTopIndex = checker.getSecondaryDiagonalTopNeighbourIndex(
				moveCell, owner);
		if (diagonalTopIndex > 0) {
			takeCells(changedCells, diagonalTopIndex, cellIndex,
					Game.BOARD_COLUMN_COUNT - 1, owner);
		}
	}

	private void tryTakeDiagonalBottomCells(final int cellIndex,
			final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int diagonalBottomIndex = checker.getMainDiagonalBottomNeighbourIndex(
				moveCell, owner);
		if (diagonalBottomIndex > 0) {
			takeCells(changedCells, cellIndex, diagonalBottomIndex,
					Game.BOARD_COLUMN_COUNT + 1, owner);
		}
	}

	private void tryTakeDiagonalTopCcells(final int cellIndex,
			final Player owner, final Cell moveCell,
			final Set<Cell> changedCells) {
		int diagonalTopIndex = checker.getMainDiagonalTopNeighbourIndex(
				moveCell, owner);
		if (diagonalTopIndex > 0) {
			takeCells(changedCells, diagonalTopIndex, cellIndex,
					Game.BOARD_COLUMN_COUNT + 1, owner);
		}
	}

	private void tryTakeTopCells(final int cellIndex, final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int bottomNeigbhourIndex = checker.getBottomNeighbourIndex(moveCell,
				owner);
		if (bottomNeigbhourIndex > 0) {
			takeCells(changedCells, cellIndex, bottomNeigbhourIndex,
					Game.BOARD_COLUMN_COUNT, owner);
		}
	}

	private void tryTakeBottomCells(final int cellIndex, final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int topNeighbourIndex = checker.getTopNeighbourIndex(moveCell, owner);
		if (topNeighbourIndex > 0) {
			takeCells(changedCells, topNeighbourIndex, cellIndex,
					Game.BOARD_COLUMN_COUNT, owner);
		}
	}

	private void tryTakeRightCells(final int cellIndex, final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int rightNeighbourIndex = checker.getRightNeighbourIndex(moveCell,
				owner);
		if (rightNeighbourIndex > 0) {
			takeCells(changedCells, cellIndex, rightNeighbourIndex, 1, owner);
		}
	}

	private void tryTakeLeftCells(final int cellIndex, final Player owner,
			final Cell moveCell, final Set<Cell> changedCells) {
		int leftNeighbourindex = checker.getLeftNeighbourIndex(moveCell, owner);
		if (leftNeighbourindex > 0) {
			takeCells(changedCells, leftNeighbourindex, cellIndex, 1, owner);
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

	public boolean isMovePermitted(final int cellIndex, final Player forPlayer) {
		final Cell moveCell = board.get(cellIndex);
		return moveCell.isEmpty()
				&& checker.isMovePermitted(moveCell, forPlayer);
	}

	public boolean addObserver(final ModelObserver observer) {
		return observers.add(observer);
	}

	public boolean removeObserver(final ModelObserver observer) {
		return observers.remove(observer);
	}

	private void notifyDataSetChanged(final Collection<Cell> changedCells) {
		for (final ModelObserver observer : observers) {
			observer.onModelChanged(changedCells);
		}
	}
}
