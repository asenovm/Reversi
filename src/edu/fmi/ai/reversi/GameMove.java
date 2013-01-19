package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class GameMove implements Comparable<GameMove> {

	private int value;

	private Board board;

	/**
	 * Creates a new {@link GameMove} from the values specified
	 * 
	 * @param value
	 *            the value of the board as computed by {@link BoardEvaluator}
	 * @param board
	 *            the board that represents this move
	 */
	public GameMove(final int value, final Board board) {
		this.value = value;
		this.board = board;
	}

	/**
	 * Creates a new {@link GameMove} instance from the values specified
	 * 
	 * @param parameter
	 *            a {@link GameSolverParameter} instance to be used
	 * @param player
	 *            the player that will make this move
	 */
	public GameMove(final GameSolverParameter parameter, final Player player) {
		this.value = parameter.getValue(player);
		this.board = parameter.board;
	}

	/**
	 * Gets the difference between the board in this move and the <tt>other</tt>
	 * board given.
	 * 
	 * @param other
	 *            the other board against which the current move is to be
	 *            compared.
	 * @return a collection, containing all the cells that differ from those in
	 *         the <tt>other</tt> board supplied
	 */
	public Collection<Cell> getDifference(final Board other) {
		return board.getDifference(other);
	}

	/**
	 * Sets the board, associated with this move.
	 * 
	 * @param board
	 *            the board that is to be associated with this move.
	 * @return the current {@link GameMove} instance to allow for method
	 *         chaining
	 */
	public GameMove setBoard(final Board board) {
		this.board = board;
		return this;
	}

	/**
	 * Sets the value, associated with this move
	 * 
	 * @param value
	 *            the value to be associated with this move
	 * @return the current {@link GameMove} instance to allow for method
	 *         chaining
	 */
	public GameMove setValue(final int value) {
		this.value = value;
		return this;
	}

	/**
	 * Returns the value, associated with this move
	 * 
	 * @return the value, associated with this move
	 */
	public int getValue() {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(final GameMove other) {
		if (value == other.value) {
			return 0;
		}
		return value > other.value ? 1 : -1;
	}

}
