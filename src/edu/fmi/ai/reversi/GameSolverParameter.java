package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

/**
 * A parameter object that is used for making calls to the methods of the
 * {@link GameSolver}
 * 
 * @author martin
 * 
 */
public class GameSolverParameter {
	public final Board board;

	public int alpha;

	public int beta;

	public int level;

	/**
	 * Creates a new {@link GameSolverParameter} that is copy of the one
	 * supplied, but with an increased traversal level.
	 * 
	 * @param nextBoard
	 *            the board that is to be wrapper in this parameter object
	 * @param other
	 *            the parameter object that is to be copied, except for the
	 *            board and the current traversal level
	 * @return a new {@link GameSolverParameter} instance
	 */
	public static GameSolverParameter increasedLevel(final Board nextBoard,
			final GameSolverParameter other) {
		return new GameSolverParameter(nextBoard, other.alpha, other.beta, other.level + 1);
	}

	/**
	 * Creates a new {@link GameSolverParameter} instance, using the parameters
	 * given
	 * 
	 * @param board
	 *            the board that is to be wrapper into this parameter object
	 * @param alpha
	 *            the alpha value that is to be used
	 * @param beta
	 *            the beta value that is to be used
	 * @param level
	 *            the current traversal level
	 */
	public GameSolverParameter(final Board board, final int alpha, final int beta, final int level) {
		this.board = board;
		this.alpha = alpha;
		this.beta = beta;
		this.level = level;
	}

	/**
	 * Returns all the next possible board configurations for the
	 * <tt>player</tt> specified
	 * 
	 * @param player
	 *            the player for which the next board configurations are to be
	 *            retrieved
	 * @return a collection of all the possible next board configurations for
	 *         the <tt>player</tt> given.
	 */
	public Collection<Board> getNextBoards(final Player player) {
		return board.getNextBoards(player);
	}

	/**
	 * Returns the board evaluation for the <tt>player</tt> given.
	 * 
	 * @param player
	 *            the player for which the board that is wrapped into this
	 *            parameter object is to be evaluated
	 * @return the heuristic evaluation of the board for the <tt>player</tt>
	 *         supplied.
	 */
	public int getValue(final Player player) {
		return board.getValue(player);
	}

}
