package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

//TODO remove this
public class GameMoveHelper {

	public int value;

	public Board state;

	public Collection<Cell> move;

	public GameMoveHelper(final int value, final Board state) {
		this.value = value;
		this.state = state;
	}

	public GameMoveHelper(final GameSolverParameter parameter, final Player player) {
		this.value = parameter.getValue(player);
		this.state = parameter.board;
	}

	public Collection<Cell> diff(final Board other) {
		return state.diff(other);
	}

}