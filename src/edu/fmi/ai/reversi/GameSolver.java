package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

public class GameSolver {

	private static final int MAX_LEVEL_SEARCH_DEPTH = 6;

	/**
	 * {@value}
	 */
	public static final int SIZE_BOARD_GAME = 3;

	/**
	 * {@value}
	 */
	public static final int INDEX_BOARD_MIN = 0;

	/**
	 * {@value}
	 */
	public static final int MOVE_MIN_PLAYER = -1;

	/**
	 * {@value}
	 */
	public static final int MOVE_MAX_PLAYER = 1;

	/**
	 * {@value}
	 */
	public static final int MOVE_EMPTY_BOARD = 0;

	public GameMoveHelper getOptimalMove(final Board state) {
		final GameMoveHelper result = getOptimalMinMove(state,
				Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		result.move = result.diff(state);
		return result;
	}

	public GameMoveHelper getOptimalMinMove(final Board state, int alpha,
			int beta, int level) {
		if (level == MAX_LEVEL_SEARCH_DEPTH) {
			return new GameMoveHelper(state.getValue(Player.WHITE), state);
		}

		final Collection<Board> gameStates = state.getNextBoards(Player.WHITE);
		GameMoveHelper result = new GameMoveHelper(Integer.MAX_VALUE, state);

		for (final Board nextState : gameStates) {
			final GameMoveHelper optimalMove = getOptimalMaxMove(nextState,
					alpha, beta, level + 1);
			beta = tryUpdateMinResult(beta, result, nextState, optimalMove);
			if (beta <= alpha) {
				return result;
			}
		}

		return result;
	}

	public GameMoveHelper getOptimalMaxMove(final Board state, int alpha,
			int beta, int level) {
		if (level == MAX_LEVEL_SEARCH_DEPTH) {
			return new GameMoveHelper(state.getValue(Player.BLACK), state);
		}

		final Collection<Board> gameStates = state.getNextBoards(Player.BLACK);
		GameMoveHelper result = new GameMoveHelper(Integer.MIN_VALUE, state);
		for (final Board nextState : gameStates) {
			final GameMoveHelper optimalMove = getOptimalMinMove(nextState,
					alpha, beta, level + 1);

			alpha = tryUpdateMaxResult(alpha, result, nextState, optimalMove);

			if (beta <= alpha) {
				return result;
			}
		}
		return result;
	}

	private int tryUpdateMaxResult(int alpha, GameMoveHelper result,
			final Board nextState, final GameMoveHelper next) {
		if (result.value < next.value) {
			result.value = next.value;
			result.state = nextState;
			alpha = next.value;
		}
		return alpha;
	}

	private int tryUpdateMinResult(int beta, GameMoveHelper result,
			final Board nextState, final GameMoveHelper next) {
		if (result.value > next.value) {
			result.value = next.value;
			result.state = nextState;
			beta = next.value;
		}
		return beta;
	}

}
