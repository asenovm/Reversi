package edu.fmi.ai.reversi.model;

import edu.fmi.ai.reversi.Game;

/**
 * A model class, representing a board cell
 * 
 * @author martin
 * 
 */
public class Cell {

	private final int x;

	private final int y;

	private final int index;

	private Player owner;

	/**
	 * Creates a new board cell from the parameters given
	 * 
	 * @param index
	 *            the index of the cell on the board
	 * @param owner
	 *            the owner of the cell
	 */
	public Cell(final int index, final Player owner) {
		this.index = index;
		this.x = index % Game.BOARD_COLUMN_COUNT;
		this.y = index / Game.BOARD_ROW_COUNT;
		this.owner = owner;
	}

	/**
	 * Creates a new empty board cell on the position given.
	 * 
	 * @param x
	 *            the x position of the cell
	 * @param y
	 *            the y position of the cell
	 */
	public Cell(final int x, final int y) {
		this.x = x;
		this.y = y;
		index = y * Game.BOARD_COLUMN_COUNT + x;
		owner = Player.UNKNOWN;
	}

	/**
	 * Creates a new empty cell on the index given
	 * 
	 * @param index
	 *            the board index at which the cell is located
	 */
	public Cell(final int index) {
		this(index, Player.UNKNOWN);
	}

	/**
	 * Takes this cell for the <tt>player</tt> specified
	 * 
	 * @param player
	 *            the new owner of the cell
	 */
	public void take(Player player) {
		owner = player;
	}

	/**
	 * Returns whether or not the cell is empty and another player can place a
	 * disc on it
	 * 
	 * @return whether or not the cell is empty and another player can place a
	 *         disc on it
	 */
	public boolean isEmpty() {
		return owner == Player.UNKNOWN;
	}

	/**
	 * Returns whether or not the cell is owner by the <tt>player</tt> specified
	 * 
	 * @param player
	 *            the player who is to be tested for being the owner of the cell
	 * @return whether or not the cell is owned by the <tt>player</tt> specified
	 */
	public boolean isOwnedBy(final Player player) {
		return owner == player;
	}

	/**
	 * Returns the <tt>x</tt> position on the board of the current cell
	 * 
	 * @return the <tt>x</tt> position on the board of the current cell
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns the <tt>y</tt> position on the board of the current cell
	 * 
	 * @return the <tt>y</tt> position on the board of the current cell
	 */
	public int getY() {
		return y;
	}

	/**
	 * Returns the index on the board of the current cell
	 * 
	 * @return the index on the board of the current cell
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the owner of the current cell
	 * 
	 * @return the owner of the current cell
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Returns whether or not the current cell has the same owner as the
	 * <tt>other</tt> cell given.
	 * 
	 * @param other
	 *            the cell whose owner is to be compared with the owner of the
	 *            current cell
	 * @return whether or not the current cell has the same owner as the
	 *         <tt>other</tt> cell given.
	 */
	public boolean hasSameOwner(final Cell other) {
		return owner == other.owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (index != other.index)
			return false;
		return true;
	}

}
