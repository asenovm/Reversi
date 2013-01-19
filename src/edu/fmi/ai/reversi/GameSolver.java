package edu.fmi.ai.reversi;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.fmi.ai.reversi.listeners.GameSolverCallback;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

public class GameSolver {

	/**
	 * {@value}
	 */
	private static final int MAX_LEVEL_SEARCH_DEPTH = 3;

	private final ExecutorService executor;

	private class GameSolverRunnable implements Runnable {

		private final Board board;

		private final GameSolverCallback callback;

		public GameSolverRunnable(final Board board, final GameSolverCallback callback) {
			this.board = board;
			this.callback = callback;
		}

		@Override
		public void run() {
			final GameMove result = getOptimalMinMove(new GameSolverParameter(board,
					Integer.MIN_VALUE, Integer.MAX_VALUE, 0));
			callback.onOptimalMoveReceived(result.getDifference(board));
		}

	}

	public GameSolver() {
		executor = Executors.newSingleThreadExecutor();
	}

	public void getOptimalMove(final Board state, final GameSolverCallback callback) {
		executor.execute(new GameSolverRunnable(state, callback));
	}

	public GameMove getOptimalMinMove(final GameSolverParameter parameter) {
		if (parameter.level == MAX_LEVEL_SEARCH_DEPTH) {
			return new GameMove(parameter, Player.WHITE);
		}

		final Collection<Board> gameStates = parameter.getNextBoards(Player.WHITE);
		GameMove nextMove = new GameMove(Integer.MAX_VALUE, parameter.board);

		for (final Board nextState : gameStates) {
			final GameSolverParameter nextParameter = getNextLevelParameter(parameter, nextState);
			final GameMove optimalMove = getOptimalMaxMove(nextParameter);
			tryUpdateMinResult(parameter, nextMove, nextState, optimalMove);
			if (parameter.beta <= parameter.alpha) {
				return nextMove;
			}
		}

		return nextMove;
	}

	public GameMove getOptimalMaxMove(final GameSolverParameter parameter) {
		if (parameter.level == MAX_LEVEL_SEARCH_DEPTH) {
			return new GameMove(parameter, Player.BLACK);
		}

		final Collection<Board> gameStates = parameter.getNextBoards(Player.BLACK);
		GameMove nextMove = new GameMove(Integer.MIN_VALUE, parameter.board);

		for (final Board nextState : gameStates) {
			final GameSolverParameter nextParameter = getNextLevelParameter(parameter, nextState);
			final GameMove optimalMove = getOptimalMinMove(nextParameter);
			tryUpdateMaxResult(parameter, nextMove, nextState, optimalMove);
			if (parameter.beta <= parameter.alpha) {
				return nextMove;
			}
		}
		return nextMove;
	}

	private void tryUpdateMaxResult(final GameSolverParameter parameter, GameMove nextMove,
			final Board nextState, final GameMove optimalMove) {
		if (nextMove.compareTo(optimalMove) <= 0) {
			final int nextValue = optimalMove.getValue();
			nextMove.setBoard(nextState).setValue(nextValue);
			parameter.alpha = nextValue;
		}
	}

	private void tryUpdateMinResult(final GameSolverParameter parameter, GameMove result,
			final Board nextState, final GameMove next) {
		if (result.compareTo(next) >= 0) {
			final int nextValue = next.getValue();
			result.setBoard(nextState).setValue(nextValue);
			parameter.beta = nextValue;
		}
	}

	private GameSolverParameter getNextLevelParameter(final GameSolverParameter parameter,
			final Board nextState) {
		return GameSolverParameter.increasedLevel(nextState, parameter);
	}

}
