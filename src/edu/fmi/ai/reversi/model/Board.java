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

/**
 * A model class, representing the board
 * 
 * @author martin
 * 
 */
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

	/**
	 * Updates the model so that the cell with the <tt>cellIndex</tt> given,
	 * along with all the other cells that are to be taken as the result of
	 * taking the aforementioned cell are marked as taken by the <tt>player</tt>
	 * .
	 * 
	 * @param cellIndex
	 *            the index of the cell that is to be taken
	 * @param player
	 *            the player for which the cell at <tt>cellIndex</tt> is to be
	 *            taken
	 */
	public void takeCell(final int cellIndex, final Player player) {
		final Collection<Cell> takenCells = cellTaker.takeCell(cellIndex, player);
		notifyBoardChanged(takenCells);
		notifyResultChanged(getDiscCount(Player.WHITE), getDiscCount(Player.BLACK));
	}

	/**
	 * Returns whether or not placing a disc at <tt>cellIndex</tt> for the
	 * <tt>player</tt> provided is legal.
	 * 
	 * @param cellIndex
	 *            the index of the cell at which a disc for the <tt>player</tt>
	 *            is to be placed.
	 * @param player
	 *            the player for which we are checking the move for being legal.
	 * @return whether or not the <tt>player</tt> given can place a disc at the
	 *         cell with <tt>cellIndex</tt>
	 */
	public boolean isMovePermitted(final int cellIndex, final Player player) {
		return checker.isMovePermitted(cellIndex, player);
	}

	/**
	 * Adds the <tt>observer</tt> given to the list of the observers that are to
	 * be notified when something changes.
	 * 
	 * @param observer
	 *            the observers that is to be added
	 * @return whether or not the observer has been successfully added to the
	 *         notification list and will be called when something changes
	 */
	public boolean addObserver(final ModelObserver observer) {
		return observers.add(observer);
	}

	/**
	 * Removes the <tt>observer</tt> given from the notification list of this
	 * board.
	 * 
	 * @param observer
	 *            the <tt>observer</tt> that is to be removed from the
	 *            notification list for this board.
	 * @return whether or not the <tt>observer</tt> specified has been
	 *         successfully removed from the notification list.
	 */
	public boolean removeObserver(final ModelObserver observer) {
		return observers.remove(observer);
	}

	/**
	 * Notifies the model that the now is the turn of the <tt>player</tt>
	 * specified
	 * 
	 * @param player
	 *            the player whose turn it is currently.
	 */
	public void nextMove(final Player player) {
		notifyNextMovesAcquired(getNextMoves(player));
	}

	/**
	 * Returns the {@link Cell} instance associated with the <tt>index</tt>
	 * specified
	 * 
	 * @param cellIndex
	 *            the index, the cell corresponding to which is to be retrieved
	 * @return the {@link Cell} instance, associated with the <tt>index</tt>
	 *         given.
	 */
	public Cell get(final int cellIndex) {
		return board.get(cellIndex);
	}

	/**
	 * Returns the {@link Cell} instance, associated with the <tt>x</tt> and
	 * <tt>y</tt> coordinates given.
	 * 
	 * @param x
	 *            the x coordinate of the cell that is to be retrieved
	 * @param y
	 *            the y coordinate of the cell that is to be retrieved
	 * @return the {@link Cell} instance, that is associated with the <tt>x</tt>
	 *         and <tt>y</tt> coordinates given
	 */
	public Cell get(final int x, final int y) {
		return board.get(y * Game.BOARD_COLUMN_COUNT + x);
	}

	/**
	 * Returns the next possible moves for the <tt>player</tt> given
	 * 
	 * @param player
	 *            the player for whom the next possible moves are to be
	 *            retrieved
	 * @return all the possible next moves for the <tt>player</tt> given
	 */
	public Collection<Cell> getNextMoves(final Player player) {
		final List<Cell> result = new ArrayList<Cell>();
		for (int i = 0; i < size(); ++i) {
			if (isMovePermitted(i, player)) {
				result.add(new Cell(i));
			}
		}
		return result;
	}

	/**
	 * Returns the {@link Board} instance that correspond to the next possible
	 * moves for the <tt>player</tt> given.
	 * 
	 * @param player
	 *            the player for whom the list of possible next boards is to be
	 *            retrieved.
	 * @return a collection of all the possible next moves for the
	 *         <tt>player</tt> given.
	 */
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

	/**
	 * Returns the board's value for the <tt>player</tt> given.
	 * 
	 * @param player
	 *            the player, for whom the board is to be evaluated.
	 * @return the value of the board as computed for the <tt>player</tt> given.
	 */
	public int getValue(final Player player) {
		return evaluator.getLocationValue(this, player) + evaluator.getStabilityValue(this, player)
				+ evaluator.getTurnValue(this, player) + evaluator.getMobilityValue(this, player);
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

	/**
	 * Updates the model so that the owners of the <tt>cells</tt> given coincide
	 * with the ones in the current {@link Board} instance
	 * 
	 * @param cells
	 *            the cells that are to be updated in the board model
	 */
	public void takeCells(final Collection<Cell> cells) {
		for (final Cell cell : cells) {
			board.get(cell.getIndex()).take(cell.getOwner());
		}
		notifyBoardChanged(cells);
		notifyResultChanged(getDiscCount(Player.WHITE), getDiscCount(Player.BLACK));
	}

	/**
	 * Configure the initial state of the model
	 */
	public void startGame() {
		takeCell(POSITION_CENTER_TOP_LEFT, Player.WHITE);
		takeCell(POSITION_CENTER_TOP_RIGHT, Player.BLACK);
		takeCell(POSITION_CENTER_BOTTOM_LEFT, Player.BLACK);
		takeCell(POSITION_CENTER_BOTTOM_RIGHT, Player.WHITE);
	}

	/**
	 * Returns the size of the board as number of cells
	 * 
	 * @return the size of the board as number of cells
	 */
	public int size() {
		return board.size();
	}

	/**
	 * Returns the number of stable discs on the board for the <tt>player</tt>
	 * given
	 * 
	 * @param player
	 *            the player, for whom the number of stable discs is to be
	 *            computed
	 * @return the number of stable discs on the booard for the <tt>player</tt>
	 *         given
	 */
	public int getStableDiscsCount(final Player player) {
		return checker.getStableDiscCount(player);
	}

	/**
	 * Returns whether or not there are possible moves for the <tt>player</tt>
	 * given.
	 * 
	 * @param player
	 *            the player for whom it needs to be checked if there are any
	 *            possible moves.
	 * @return whether or not there are any possible moves for the
	 *         <tt>player</tt> given.
	 */
	public boolean hasNextMoves(final Player player) {
		return !getNextMoves(player).isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Board clone() {
		final Board board = new Board();
		for (final Cell cell : this.board.values()) {
			board.get(cell.getIndex()).take(cell.getOwner());
		}
		return board;
	}

	private void notifyBoardChanged(final Collection<Cell> changedCells) {
		for (final ModelObserver observer : observers) {
			observer.onBoardChanged(changedCells);
		}
	}

	private void notifyNextMovesAcquired(final Collection<Cell> nextMoves) {
		for (final ModelObserver observer : observers) {
			observer.onNextMovesAcquired(nextMoves);
		}
	}

	private void notifyResultChanged(final int whiteDiscs, final int blackDiscs) {
		for (final ModelObserver observer : observers) {
			observer.onResultChanged(whiteDiscs, blackDiscs);
		}
	}

	private int getDiscCount(Player player) {
		int result = 0;
		for (final Cell cell : board.values()) {
			if (cell.isOwnedBy(player)) {
				++result;
			}
		}
		return result;
	}

}
