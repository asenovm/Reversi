package edu.fmi.ai.reversi.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.fmi.ai.reversi.BoardEvaluator;
import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.ModelObserver;
import edu.fmi.ai.reversi.move.CellTaker;
import edu.fmi.ai.reversi.move.MoveChecker;

public class Board {

	/**
	 * {@value}
	 */
	private static final int POSITION_CENTER_TOP_LEFT = 27;

	/**
	 * {@value}
	 */
	private static final int POSITION_CENTER_TOP_RIGHT = 28;

	/**
	 * {@value}
	 */
	private static final int POSITION_CENTER_BOTTOM_LEFT = 35;

	/**
	 * {@value}
	 */
	private static final int POSITION_CENTER_BOTTOM_RIGHT = 36;

	private final Set<ModelObserver> observers;

	private final Map<Integer, Cell> board;

	private final MoveChecker checker;

	private final CellTaker cellTaker;

	private final BoardEvaluator evaluator;

	public Board() {
		board = new LinkedHashMap<Integer, Cell>();
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final Cell currentCell = new Cell(j, i);
				board.put(currentCell.getIndex(), currentCell);
			}
		}

		checker = new MoveChecker(this);
		cellTaker = new CellTaker(checker, this);
		evaluator = new BoardEvaluator();

		observers = new HashSet<ModelObserver>();
	}

	public void takeCell(final int cellIndex, final Player owner) {
		final Collection<Cell> takenCells = cellTaker.takeCell(cellIndex, owner);
		notifyModelChanged(takenCells);
	}

	public boolean isMovePermitted(final int cellIndex, final Player player) {
		return checker.isMovePermitted(cellIndex, player);
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

	public Cell get(final int x, final int y) {
		return board.get(y * Game.BOARD_COLUMN_COUNT + x);
	}

	@Override
	public Board clone() {
		final Board board = new Board();
		for (final Cell cell : this.board.values()) {
			board.get(cell.getIndex()).take(cell.getOwner());
		}
		return board;
	}

	private void notifyModelChanged(final Collection<Cell> changedCells) {
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
		for (int i = 0; i < size(); ++i) {
			if (isMovePermitted(i, player)) {
				result.add(new Cell(i));
			}
		}
		return result;
	}

	public Collection<Board> getNextBoards(final Player player) {
		final List<Board> result = new ArrayList<Board>();
		for (int i = 0; i < size(); ++i) {
			if (isMovePermitted(i, player)) {
				final Board newBoard = clone();
				newBoard.takeCell(i, player);
				result.add(newBoard);
			}
		}
		return result;
	}

	public float getValue(final Player player) {
		return evaluator.getLocationValue(this, player) + evaluator.getStabilityValue(this, player);
	}

	public Collection<Cell> diff(final Board other) {
		final List<Cell> diff = new ArrayList<Cell>();
		for (final Map.Entry<Integer, Cell> cell : board.entrySet()) {
			if (cell.getValue().getOwner() != other.get(cell.getKey()).getOwner()) {
				diff.add(cell.getValue());
			}
		}
		return diff;
	}

	public void takeCells(final Collection<Cell> cells) {
		for (final Cell cell : cells) {
			board.get(cell.getIndex()).take(cell.getOwner());
		}
		notifyModelChanged(cells);
	}

	public void startGame() {
		takeCell(POSITION_CENTER_TOP_LEFT, Player.WHITE);
		takeCell(POSITION_CENTER_TOP_RIGHT, Player.BLACK);
		takeCell(POSITION_CENTER_BOTTOM_LEFT, Player.BLACK);
		takeCell(POSITION_CENTER_BOTTOM_RIGHT, Player.WHITE);
	}

	public int size() {
		return board.size();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		for (final Cell cell : board.values()) {
			if (cell.getIndex() % 8 == 0) {
				builder.append("\n");
			}
			builder.append(" ").append(cell.getIndex()).append(",").append(cell.getOwner())
					.append(" ");
		}
		return builder.toString();
	}

	public int getStableDiscsCount(final Player player) {
		return checker.getStableDiscCount(player);
	}
}
