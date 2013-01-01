package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.JFrame;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelObserver;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class BoardLayout extends JFrame implements ModelObserver {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 5834762299789973250L;

	private final BoardEventsListener eventsListener;

	private class CellMouseListener implements MouseListener {

		private final int cellIndex;

		public CellMouseListener(final int index) {
			cellIndex = index;
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			eventsListener.onCellSelected(cellIndex);
		}

		@Override
		public void mouseEntered(final MouseEvent event) {
			// blank
		}

		@Override
		public void mouseExited(final MouseEvent event) {
			// blank
		}

		@Override
		public void mousePressed(final MouseEvent event) {
			// blank
		}

		@Override
		public void mouseReleased(final MouseEvent event) {
			// blank
		}

	}

	public BoardLayout(final BoardEventsListener listener)
			throws HeadlessException {
		this(listener, "", null);
	}

	public BoardLayout(GraphicsConfiguration graphicsConfiguration) {
		this(null, "", graphicsConfiguration);
	}

	public BoardLayout(final BoardEventsListener listener, String title,
			GraphicsConfiguration graphicsConfiguration) {
		super(title, graphicsConfiguration);

		eventsListener = listener;

		setLayout(new GridLayout(Game.BOARD_ROW_COUNT, Game.BOARD_COLUMN_COUNT));
		setBoardSize();
		populateCells();

		initStartConfiguration();
		setVisible(true);
	}

	private void initStartConfiguration() {
		final Container container = getContentPane();

		final BoardCellLayout topLeft = (BoardCellLayout) container
				.getComponent(Game.POSITION_CENTER_TOP_LEFT);
		final BoardCellLayout topRight = (BoardCellLayout) container
				.getComponent(Game.POSITION_CENTER_TOP_RIGHT);
		final BoardCellLayout bottomLeft = (BoardCellLayout) container
				.getComponent(Game.POSITION_CENTER_BOTTOM_LEFT);
		final BoardCellLayout bottomRight = (BoardCellLayout) container
				.getComponent(Game.POSITION_CENTER_BOTTOM_RIGHT);

		topLeft.take(Player.WHITE);
		topRight.take(Player.BLACK);
		bottomLeft.take(Player.BLACK);
		bottomRight.take(Player.WHITE);
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

				container.add(currentCell, i * Game.BOARD_COLUMN_COUNT + j);
				currentCell.addMouseListener(new CellMouseListener(i
						* Game.BOARD_COLUMN_COUNT + j));
			}
		}
	}

	private Dimension getBoardDimension() {
		final Dimension boardDimension = new Dimension(Game.BOARD_ROW_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, Game.BOARD_COLUMN_COUNT
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
	public void onModelChanged(Collection<Cell> changedCells) {
		for (final Cell cell : changedCells) {
			final BoardCellLayout boardCell = getCellAt(cell.getIndex());
			boardCell.take(cell.getOwner());
		}
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
		for (final Component component : container.getComponents()) {
			final BoardCellLayout boardCellLayout = (BoardCellLayout) component;
			boardCellLayout.clearHighlight();
		}
	}

	private BoardCellLayout getCellAt(final int index) {
		final Container container = getContentPane();
		return (BoardCellLayout) container.getComponent(index);
	}
}
