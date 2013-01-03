package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;

public class GameSolverParameter {
	public final Board board;

	public int alpha;

	public int beta;

	public int level;

	public static GameSolverParameter increasedLevel(
			final GameSolverParameter other) {
		final GameSolverParameter result = new GameSolverParameter(other);
		++result.level;
		return result;
	}

	public GameSolverParameter(Board board, int alpha, int beta, int level) {
		this.board = board;
		this.alpha = alpha;
		this.beta = beta;
		this.level = level;
	}

	private GameSolverParameter(final GameSolverParameter other) {
		this.board = other.board;
		this.alpha = other.alpha;
		this.beta = other.beta;
		this.level = other.level;
	}

	public Collection<Board> getNextBoards(final Player player) {
		return board.getNextBoards(Player.WHITE);
	}
	
	public int getValue(final Player player){
		return board.getValue(player);
	}

}
