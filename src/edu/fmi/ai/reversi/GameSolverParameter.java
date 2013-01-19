package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

public class GameSolverParameter {
	public final Board board;

	public int alpha;

	public int beta;

	public int level;

	public static GameSolverParameter increasedLevel(final Board next,
			final GameSolverParameter other) {
		return new GameSolverParameter(next, other.alpha, other.beta, other.level + 1);
	}

	public GameSolverParameter(final Board board, final int alpha, final int beta, final int level) {
		this.board = board;
		this.alpha = alpha;
		this.beta = beta;
		this.level = level;
	}

	public Collection<Board> getNextBoards(final Player player) {
		return board.getNextBoards(player);
	}

	public int getValue(final Player player) {
		return board.getValue(player);
	}

}
