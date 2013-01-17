package edu.fmi.ai.reversi.listeners;

/**
 * Implementations of this interface are listeners for different kinds of events
 * related to the board
 * 
 * @author martin
 * 
 */
public interface BoardEventsListener {
	/**
	 * A callback fired when the board cell with index <tt>cellIndex</tt> has
	 * been selected
	 * 
	 * @param cellIndex
	 *            the index of the cell that has just been selected
	 */
	void onCellSelected(final int cellIndex);
}
