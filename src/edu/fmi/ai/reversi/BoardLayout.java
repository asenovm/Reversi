package edu.fmi.ai.reversi;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class BoardLayout extends JFrame {

	private static final long serialVersionUID = 5834762299789973250L;

	private static final int BOARD_ROW_COUNT = 8;

	private static final int BOARD_COLUMN_COUNT = 8;

	private DiscColor currentDiscColor;

	public static enum DiscColor {
		BLACK, WHITE;
	}

	private class CellMouseListener implements MouseListener {

		private final BoardCellLayout cell;

		public CellMouseListener(final int index) {
			cell = (BoardCellLayout) getContentPane().getComponent(index);
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			System.out.println("on mouse clicked");
			cell.placeDisc(currentDiscColor);
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

	public BoardLayout() throws HeadlessException {
		this("", null);
	}

	public BoardLayout(GraphicsConfiguration graphicsConfiguration) {
		this("", graphicsConfiguration);
	}

	public BoardLayout(String title, GraphicsConfiguration graphicsConfiguration) {
		super(title, graphicsConfiguration);
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
		this(title, null);
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
