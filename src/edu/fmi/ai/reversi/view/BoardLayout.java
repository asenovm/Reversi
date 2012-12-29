package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;

public class BoardLayout extends JFrame {

	private static final long serialVersionUID = 5834762299789973250L;

	private static final int BOARD_ROW_COUNT = 8;

	private static final int BOARD_COLUMN_COUNT = 8;

	private DiscColor currentDiscColor;

	private final BoardEventsListener eventsListener;

	public static enum DiscColor {
		BLACK, WHITE;
	}

	private class CellMouseListener implements MouseListener {

		private final BoardCellLayout cell;

		private final int cellIndex;

		public CellMouseListener(final int index) {
			cell = (BoardCellLayout) getContentPane().getComponent(index);
			cellIndex = index;
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			cell.placeDisc(currentDiscColor);
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

		setLayout(new GridLayout(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT));
		setBoardSize();
		populateCells();
		setVisible(true);

		currentDiscColor = DiscColor.WHITE;
	}

	private void setBoardSize() {
		final Dimension boardDimension = getBoardDimension();
		setMinimumSize(boardDimension);
		setSize(boardDimension);
		setResizable(false);
	}

	private void populateCells() {
		final Container container = getContentPane();
		for (int i = 0; i < BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < BOARD_COLUMN_COUNT; ++j) {
				final BoardCellLayout currentCell = new BoardCellLayout();

				container.add(currentCell, i + j);
				currentCell.addMouseListener(new CellMouseListener(i + j));
			}
		}
	}

	private Dimension getBoardDimension() {
		final Dimension boardDimension = new Dimension(BOARD_ROW_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, BOARD_COLUMN_COUNT
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

	public void nextTurn() {
		currentDiscColor = currentDiscColor == DiscColor.WHITE ? DiscColor.BLACK
				: DiscColor.WHITE;
	}

}
