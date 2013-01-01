package edu.fmi.ai.reversi.model;

public enum Player {
	BLACK(1), WHITE(-1);

	private final int sign;

	private Player(final int sign) {
		this.sign = sign;
	}

	public int getSign() {
		return sign;
	}
}
