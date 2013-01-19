package edu.fmi.ai.reversi.model;

public enum Player {
	/**
	 * {@value}
	 */
	BLACK(1),
	/**
	 * {@value}
	 */
	WHITE(-1),
	/**
	 * {@value}
	 */
	UNKNOWN(0);

	private final int sign;

	private Player(final int sign) {
		this.sign = sign;
	}

	/**
	 * Returns the sign, associated with the current player
	 * 
	 * @return
	 */
	public int getSign() {
		return sign;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name().toLowerCase();
	}

	/**
	 * Returns the opponent of the <tt>player</tt> that is provided
	 * 
	 * @param player
	 *            the player whose opponent we are looking for
	 * @return the opponent of the <tt>player</tt> given
	 */
	public static Player getOpponent(final Player player) {
		return player == BLACK ? WHITE : BLACK;
	}

}
