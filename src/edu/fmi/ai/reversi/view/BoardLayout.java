package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelObserver;
import edu.fmi.ai.reversi.model.Cell;

public class BoardLayout extends JFrame implements ModelObserver {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 5834762299789973250L;

	/**
	 * {@value}
	 */
	private static final int RESULTS_PANEL_HEIGHT = 100;

	private final BoardEventsListener eventsListener;

	private final ResultsLayout resultsLayout;

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

	public BoardLayout(final BoardEventsListener listener) throws HeadlessException {
		this(listener, "", null);
	}

	public BoardLayout(GraphicsConfiguration graphicsConfiguration) {
		this(null, "", graphicsConfiguration);
	}

	public BoardLayout(final BoardEventsListener listener, String title,
			GraphicsConfiguration graphicsConfiguration) {
		super(title, graphicsConfiguration);

		eventsListener = listener;

		setLayout(new GridBagLayout());
		setBoardSize();
		populateCells();
		resultsLayout = attachResultsLayout();

		setVisible(true);
		pack();
	}

	private ResultsLayout attachResultsLayout() {
		final ResultsLayout resultsLayout = new ResultsLayout(Game.BOARD_COLUMN_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, RESULTS_PANEL_HEIGHT);
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridwidth = GridBagConstraints.REMAINDER;
		constraints.gridheight = GridBagConstraints.REMAINDER;
		constraints.gridx = 0;
		constraints.gridy = 8;
		getContentPane().add(resultsLayout, constraints, 64);
		return resultsLayout;
	}

	private void setBoardSize() {
		final Dimension boardDimension = getBoardDimension();
		setMinimumSize(boardDimension);
		setSize(boardDimension);
		setResizable(false);
	}

	private void populateCells() {
		final Container container = getContentPane();
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final BoardCellLayout currentCell = new BoardCellLayout();
				final int cellIndex = i * Game.BOARD_COLUMN_COUNT + j;
				final GridBagConstraints constraints = new GridBagConstraints();
				constraints.gridx = j;
				constraints.gridy = i;
				container.add(currentCell, constraints, cellIndex);
				currentCell.addMouseListener(new CellMouseListener(cellIndex));
			}
		}
	}

	private Dimension getBoardDimension() {
		final Dimension boardDimension = new Dimension(Game.BOARD_COLUMN_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, (Game.BOARD_ROW_COUNT + 1)
				* BoardCellLayout.HEIGHT_BOARD_CELL);
		return boardDimension;
	}

	public BoardLayout(String title) throws HeadlessException {
		this(null, title, null);
	}

	@Override
	public void setVisible(final boolean isVisible) {
		super.setVisible(isVisible);
		setBackground(Color.WHITE);
		pack();
	}

	@Override
	public void onModelChanged(Collection<Cell> changedCells, final int whiteDiscsCount,
			final int blackDiscsCount) {
		for (final Cell cell : changedCells) {
			final BoardCellLayout boardCell = getCellAt(cell.getIndex());
			boardCell.take(cell.getOwner());
		}
		resultsLayout.onResultChanged(whiteDiscsCount, blackDiscsCount);

	}

	@Override
	public void onNextMovesAcquired(Collection<Cell> nextMoves) {
		clearCellHighlight();
		for (final Cell cell : nextMoves) {
			final BoardCellLayout boardCell = getCellAt(cell.getIndex());
			boardCell.highlight();
		}
	}

	private void clearCellHighlight() {
		final Container container = getContentPane();
		for (int i = 0; i < container.getComponentCount() - 1; ++i) {
			final BoardCellLayout boardCellLayout = (BoardCellLayout) container.getComponent(i);
			boardCellLayout.clearHighlight();
		}
	}

	private BoardCellLayout getCellAt(final int index) {
		final Container container = getContentPane();
		return (BoardCellLayout) container.getComponent(index);
	}
}
