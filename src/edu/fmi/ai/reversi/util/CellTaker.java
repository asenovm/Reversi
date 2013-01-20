package edu.fmi.ai.reversi.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.move.MoveChecker;

public class CellTaker {

	private final MoveChecker checker;

	private final Board board;

	/**
	 * Creates a new cell taker object to be used for taking cells
	 * 
	 * @param checker
	 *            the move checker that is used when computing the cells for
	 *            taking
	 * @param board
	 *            the board whose cells are to be taken
	 */
	public CellTaker(final MoveChecker checker, final Board board) {
		this.checker = checker;
		this.board = board;
	}

	/**
	 * Takes the cell specified and all the other cells that are affected
	 * 
	 * @param cellIndex
	 *            the board index of the cell that is to be taken
	 * @param player
	 *            the player who is taking the cell at the cell index given
	 * @return the collection of all the affected of the taking cells
	 */
	public Collection<Cell> takeCell(final int cellIndex, final Player player) {
		return takeSurroundedCells(board.get(cellIndex), player);
	}

	private Collection<Cell> takeSurroundedCells(final Cell cell, final Player player) {
		final Set<Cell> takenCells = new LinkedHashSet<Cell>();

		takenCells.add(cell);

		for (final Direction direction : Direction.values()) {
			takenCells.addAll(takeCells(direction, cell, player));
		}

		for (final Cell takenCell : takenCells) {
			takenCell.take(player);
		}

		return takenCells;
	}

	private Collection<Cell> takeCells(final Direction direction, final Cell cell,
			final Player player) {
		final int neighbourIndex = checker.getNeighbourIndex(direction, cell, player);
		final int startIndex = Math.min(cell.getIndex(), neighbourIndex);
		final int endIndex = Math.max(cell.getIndex(), neighbourIndex);
		return takeCells(neighbourIndex, startIndex, endIndex, direction.getIncrement(), player);
	}

	private Collection<Cell> takeCells(final int fromIndex, final int toIndex, final int step,
			final Player player) {
		final Collection<Cell> result = new HashSet<Cell>();
		for (int i = fromIndex; i <= toIndex; i += step) {
			result.add(board.get(i));
		}
		return result;
	}

	private Collection<Cell> takeCells(final int neighbourIndex, final int start, final int end,
			final int step, final Player player) {
		return neighbourIndex > 0 ? takeCells(start, end, step, player) : Collections
				.<Cell> emptySet();
	}

}
