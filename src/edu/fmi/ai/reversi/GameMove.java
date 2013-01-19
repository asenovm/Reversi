package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class GameMove implements Comparable<GameMove> {

	private float value;

	private Board board;

	public GameMove(final float value, final Board state) {
		this.value = value;
		this.board = state;
	}

	public GameMove(final GameSolverParameter parameter, final Player player) {
		this.value = parameter.getValue(player);
		this.board = parameter.board;
	}

	public Collection<Cell> getDifference(final Board other) {
		return board.diff(other);
	}

	public GameMove setBoard(final Board board) {
		this.board = board;
		return this;
	}

	public GameMove setValue(final float value) {
		this.value = value;
		return this;
	}

	public float getValue() {
		return value;
	}

	@Override
	public int compareTo(final GameMove other) {
		return (int) Math.signum(value - other.value);
	}

}
