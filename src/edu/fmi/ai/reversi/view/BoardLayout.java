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

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelListener;
import edu.fmi.ai.reversi.model.Player;

public class BoardLayout extends JFrame {

	private static final long serialVersionUID = 5834762299789973250L;

	private final BoardEventsListener eventsListener;

	private Player currentPlayer;

	private class CellMouseListener implements MouseListener, ModelListener {

		private final BoardCellLayout cell;

		private final int cellIndex;

		public CellMouseListener(final int index) {
			cell = (BoardCellLayout) getContentPane().getComponent(index);
			cellIndex = index;
		}

		@Override
		public void mouseClicked(final MouseEvent event) {
			eventsListener.onCellSelected(this, cellIndex);
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

		@Override
		public void onMovePermitted() {
			cell.placeDisc(currentPlayer);
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
				.getComponent(27);
		final BoardCellLayout topRight = (BoardCellLayout) container
				.getComponent(28);
		final BoardCellLayout bottomLeft = (BoardCellLayout) container
				.getComponent(35);
		final BoardCellLayout bottomRight = (BoardCellLayout) container
				.getComponent(36);

		topLeft.placeDisc(Player.WHITE);
		topRight.placeDisc(Player.BLACK);
		bottomLeft.placeDisc(Player.WHITE);
		bottomRight.placeDisc(Player.BLACK);
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

	public void nextTurn(final Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
