package edu.fmi.ai.reversi.util;

import edu.fmi.ai.reversi.Game;

public enum Direction {
	/**
	 * {@value}
	 */
	LEFT(1),
	/**
	 * {@value}
	 */
	RIGHT(1),
	/**
	 * {@value}
	 */
	TOP(Game.BOARD_COLUMN_COUNT),
	/**
	 * {@value}
	 */
	BOTTOM(Game.BOARD_COLUMN_COUNT),
	/**
	 * {@value}
	 */
	MAIN_DIAGONAL_TOP(Game.BOARD_COLUMN_COUNT + 1),
	/**
	 * {@value}
	 */
	MAIN_DIAGONAL_BOTTOM(Game.BOARD_COLUMN_COUNT + 1),
	/**
	 * {@value}
	 */
	SECONDARY_DIAGONAL_TOP(Game.BOARD_COLUMN_COUNT - 1),
	/**
	 * {@value}
	 */
	SECONDARY_DIAGONAL_BOTTOM(Game.BOARD_COLUMN_COUNT - 1);

	private final int increment;

	private Direction(final int increment) {
		this.increment = increment;
	}

	/**
	 * Returns the cell number with which a cell index is to be incremented in
	 * order to move in the direction specified
	 * 
	 * @return cell number with which a cell index is to be incremented in order
	 *         to move in the direction specified
	 */
	public int getIncrement() {
		return increment;
	}
}
