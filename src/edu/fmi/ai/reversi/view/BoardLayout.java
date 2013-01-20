package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

/**
 * A layout to represent the game board
 * 
 * @author martin
 * 
 */
public class BoardLayout extends JPanel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 5834762299789973250L;

	/**
	 * {@value}
	 */
	private static final String BACKGROUND_COLOR = "#424623";

	private final BoardEventsListener eventsListener;

	private final Runnable clearHighlightRunnable;

	private class CellMouseListener extends MouseAdapter {

		private final int cellIndex;

		public CellMouseListener(final int index) {
			cellIndex = index;
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			eventsListener.onCellSelected(cellIndex);
		}

	}

	private class ClearHighlightRunnable implements Runnable {

		@Override
		public void run() {
			clearCellHighlight();
		}
	}

	private class TakeCellRunnable implements Runnable {

		private final BoardCellLayout cellLayout;

		private final Player player;

		public TakeCellRunnable(final BoardCellLayout cellLayout, final Player player) {
			this.cellLayout = cellLayout;
			this.player = player;
		}

		@Override
		public void run() {
			cellLayout.take(player);
		}
	}

	private class HighlightCellRunnable implements Runnable {

		private final BoardCellLayout cellLayout;

		public HighlightCellRunnable(final BoardCellLayout cellLayout) {
			this.cellLayout = cellLayout;
		}

		@Override
		public void run() {
			cellLayout.highlight();
		}
	}

	/**
	 * Creates a new board layout that will notify the <tt>listener</tt>
	 * specified when specific events occur.
	 * 
	 * @param listener
	 *            the listener that will be notified when board events occur
	 */
	public BoardLayout(final BoardEventsListener listener) {
		super(new GridLayout(Game.BOARD_ROW_COUNT, Game.BOARD_COLUMN_COUNT));

		eventsListener = listener;
		clearHighlightRunnable = new ClearHighlightRunnable();

		setVisible(true);
		setPreferredSize(getBoardDimension());

		populateCells();

		setBackground(Color.decode(BACKGROUND_COLOR));
	}

	/**
	 * A callback fired when the board model has changed
	 * 
	 * @param changedCells
	 *            all the changed cells
	 */
	public void onModelChanged(final Collection<Cell> changedCells) {
		for (final Cell cell : changedCells) {
			SwingUtilities.invokeLater(new TakeCellRunnable(getCellAt(cell.getIndex()), cell
					.getOwner()));
		}
	}

	/**
	 * A callback fired when the next possible moves have been received.
	 * 
	 * @param nextMoves
	 *            all the possible moves for the next iteration
	 */
	public void onNextMovesAcquired(final Collection<Cell> nextMoves) {
		SwingUtilities.invokeLater(clearHighlightRunnable);
		for (final Cell cell : nextMoves) {
			SwingUtilities.invokeLater(new HighlightCellRunnable(getCellAt(cell.getIndex())));
		}
	}

	private void clearCellHighlight() {
		for (int i = 0; i < getComponentCount(); ++i) {
			final BoardCellLayout boardCellLayout = (BoardCellLayout) getComponent(i);
			boardCellLayout.clearHighlight();
		}
	}

	private BoardCellLayout getCellAt(final int index) {
		return (BoardCellLayout) getComponent(index);
	}

	private Dimension getBoardDimension() {
		final Dimension boardDimension = new Dimension(Game.BOARD_COLUMN_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, Game.BOARD_ROW_COUNT
				* BoardCellLayout.HEIGHT_BOARD_CELL);
		return boardDimension;
	}

	private void populateCells() {
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final BoardCellLayout currentCell = new BoardCellLayout();
				final int cellIndex = i * Game.BOARD_COLUMN_COUNT + j;
				add(currentCell, cellIndex);
				currentCell.addMouseListener(new CellMouseListener(cellIndex));
			}
		}
	}
}
