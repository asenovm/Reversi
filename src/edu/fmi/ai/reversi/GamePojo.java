package edu.fmi.ai.reversi;

import java.util.Collection;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;

//TODO remove this
public class GamePojo {

	public int value;

	public Board state;

	public Collection<Cell> move;

	public GamePojo(final int value, final Board state) {
		this.value = value;
		this.state = state;
	}
	
	public Collection<Cell> diff(final Board other) {
		return state.diff(other);
	}

}
