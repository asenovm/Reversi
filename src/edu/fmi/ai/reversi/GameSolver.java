package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

public class GameSolver {

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

	public static GamePojo getOptimalMove(final Board state) {
		return getOptimalMinMove(state, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static GamePojo getOptimalMinMove(final Board state, int alpha,
			int beta) {
		if (state.isTerminal()) {
			return new GamePojo(state.getValue(), state);
		}

		final Collection<Board> gameStates = state.getNextBoards(Player.WHITE);
		GamePojo result = new GamePojo(Integer.MAX_VALUE, state);
		for (final Board nextState : gameStates) {
			final GamePojo next = getOptimalMaxMove(nextState, alpha, beta);
			if (result.value > next.value) {
				result.value = next.value;
				result.state = nextState;
				beta = next.value;
			}

			if (beta <= alpha) {
				return result;
			}
		}
		return result;
	}

	public static GamePojo getOptimalMaxMove(final Board state, int alpha,
			int beta) {
		if (state.isTerminal()) {
			return new GamePojo(state.getValue(), state);
		}

		final Collection<Board> gameStates = state.getNextBoards(Player.BLACK);
		GamePojo result = new GamePojo(Integer.MIN_VALUE, state);
		for (final Board nextState : gameStates) {
			final GamePojo next = getOptimalMinMove(nextState, alpha, beta);
			if (result.value < next.value) {
				result.value = next.value;
				result.state = nextState;
				alpha = next.value;
			}

			if (beta <= alpha) {
				return result;
			}
		}
		return result;
	}

}
