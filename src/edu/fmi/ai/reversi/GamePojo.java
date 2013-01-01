package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.model.Board;

public class GamePojo {

	public int value;

	public Board state;

	public GamePojo(final int value, final Board state) {
		this.value = value;
		this.state = state;
	}

}
