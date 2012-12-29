package edu.fmi.ai.reversi;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardCellLayout extends JPanel {

	private static final long serialVersionUID = -2422603891158269826L;

	private static final String CELL_COLOR = "0xBFB630";

	private static final int BOARDER_THICKNESS = 1;

	public static final int HEIGHT_BOARD_CELL = 70;

	public static final int WIDTH_BOARD_CELL = 70;

	public BoardCellLayout() {
		setSize(WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		setVisible(true);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(Color.decode(CELL_COLOR));
		graphics.fillRect(BOARDER_THICKNESS, BOARDER_THICKNESS,
				WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
	}
}
