package edu.fmi.ai.reversi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

		checker = new MoveChecker(this);
		cellTaker = new CellTaker(checker, this);

		observers = new HashSet<ModelObserver>();
	}

	public void takeCell(final int cellIndex, final Player owner) {
		final Cell moveCell = board.get(cellIndex);
		moveCell.take(owner);

		final Collection<Cell> changedCells = cellTaker.takeSurroundedCells(
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

	public void nextMove(final Player player) {
		notifyNextMoves(getNextMoves(player));
	}

	public Cell get(final int cellIndex) {
		return board.get(cellIndex);
	}

	@Override
	protected Board clone() {
		final Board board = new Board(Game.BOARD_ROW_COUNT,
				Game.BOARD_COLUMN_COUNT);
		for (final Cell cell : this.board.values()) {
			board.board.get(cell.getIndex()).take(cell.getOwner());
		}
		return board;
	}

	private void notifyDataSetChanged(final Collection<Cell> changedCells) {
		for (final ModelObserver observer : observers) {
			observer.onModelChanged(changedCells);
		}
	}

	private void notifyNextMoves(final Collection<Cell> nextMoves) {
		for (final ModelObserver observer : observers) {
			observer.onNextMovesAcquired(nextMoves);
		}
	}

	public Collection<Cell> getNextMoves(final Player player) {
		final List<Cell> result = new ArrayList<Cell>();
		for (int i = 0; i <= Game.BOARD_MAX_INDEX; ++i) {
			if (isMovePermitted(i, player)) {
				final Board newBoard = clone();
				newBoard.takeCell(i, player);
				result.add(new Cell(i));
			}
		}
		return result;
	}

	public Collection<Board> getNextBoards(final Player player) {
		final List<Board> result = new ArrayList<Board>();
		for (int i = 0; i <= Game.BOARD_MAX_INDEX; ++i) {
			if (isMovePermitted(i, player)) {
				final Board newBoard = clone();
				newBoard.takeCell(i, player);
				result.add(newBoard);
			}
		}
		return result;
	}

	public boolean isTerminal() {
		return false;
	}

	public int getValue() {
		return 0;
	}

}
