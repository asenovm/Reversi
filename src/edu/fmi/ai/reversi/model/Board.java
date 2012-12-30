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

	private final CellTaker cellTaker;

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
		cellTaker = new CellTaker(checker, Collections.unmodifiableMap(board));

		observers = new HashSet<ModelObserver>();
	}

	public void onCellSelected(final int cellIndex, final Player owner) {
		final Cell moveCell = board.get(cellIndex);
		moveCell.take(owner);

		final Collection<Cell> changedCells = cellTaker.takeSurroundingCells(
				moveCell, owner);
		changedCells.add(moveCell);
		notifyDataSetChanged(changedCells);
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
