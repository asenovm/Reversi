package edu.fmi.ai.reversi.model;

import edu.fmi.ai.reversi.Game;

public class Cell {

	private final int x;

	private final int y;

	private final int index;

	private Player ownedBy;

	public Cell(final int x, final int y) {
		this.x = x;
		this.y = y;
		index = y * Game.BOARD_COLUMN_COUNT + x;
	}

	public Cell(final int index) {
		this.index = index;
		this.x = index % Game.BOARD_COLUMN_COUNT;
		this.y = index / Game.BOARD_ROW_COUNT;
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

	public int getIndex() {
		return index;
	}

	public Player getOwner() {
		return ownedBy;
	}

}
