package edu.fmi.ai.reversi.model;

public class Cell {

	private final int x;

	private final int y;

	private Player ownedBy;

	public Cell(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void take(Player player) {
		ownedBy = player;
	}

	public boolean isEmpty() {
		return ownedBy == null;
	}

	public boolean isOwnedBy(final Player player) {
		return ownedBy == player;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
