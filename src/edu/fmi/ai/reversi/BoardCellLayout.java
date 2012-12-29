package edu.fmi.ai.reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BoardCellLayout extends JPanel {

	private static final long serialVersionUID = -2422603891158269826L;

	private static final String CELL_COLOR = "0xBFB630";

	private static final int BOARDER_THICKNESS = 1;

	public static final int HEIGHT_BOARD_CELL = 70;

	public static final int WIDTH_BOARD_CELL = 70;

	private class CellMouseListener implements MouseListener {

		private final int cellIndex;

		private final BoardCellEventsListener listener;

		public CellMouseListener(final BoardCellEventsListener listener,
				final int cellIndex) {
			this.listener = listener;
			this.cellIndex = cellIndex;
		}

		@Override
		public void mouseClicked(MouseEvent event) {
			listener.onCellSelected(cellIndex);
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			// blank
		}

		@Override
		public void mouseExited(MouseEvent event) {
			// blank
		}

		@Override
		public void mousePressed(MouseEvent event) {
			// blank
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			// blank
		}

	}

	public BoardCellLayout(final BoardCellEventsListener listener,
			final int cellIndex) {
		setSize(WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		setVisible(true);
		addMouseListener(new CellMouseListener(listener, cellIndex));
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.decode(CELL_COLOR));
		graphics.fillRect(BOARDER_THICKNESS, BOARDER_THICKNESS,
				WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		graphics.setColor(Color.CYAN);
		graphics.fillOval(BOARDER_THICKNESS, BOARDER_THICKNESS,
				WIDTH_BOARD_CELL - 1, WIDTH_BOARD_CELL - 5);
	}

}
