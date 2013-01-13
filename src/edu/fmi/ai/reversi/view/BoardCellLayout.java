package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.fmi.ai.reversi.model.Player;

public class BoardCellLayout extends JPanel {

	/**
	 * {@value}
	 */
	private static final String CELL_COLOR_HIGHLIGHTED = "0x699B00";

	/**
	 * {@value}
	 */
	private static final String CELL_COLOR_DEFAULT = "0x88B32D";

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -2422603891158269826L;

	/**
	 * {@value}
	 */
	private static final String FILE_PATH_BLACK_DISC = "assets/black_disc.png";

	/**
	 * {@value}
	 */
	private static final String FILE_PATH_WHITE_DISC = "assets/white_disc.png";

	/**
	 * {@value}
	 */
	private static final int BOARDER_THICKNESS = 1;

	/**
	 * {@value}
	 */
	public static final int HEIGHT_BOARD_CELL = 70;

	/**
	 * {@value}
	 */
	public static final int WIDTH_BOARD_CELL = 70;

	private static enum CellColor {

		EMPTY(CELL_COLOR_DEFAULT), CAPTURED(CELL_COLOR_DEFAULT), HIGHLIGHTED(CELL_COLOR_HIGHLIGHTED);

		private final String backgroundColor;

		private CellColor(final String color) {
			this.backgroundColor = color;
		}

		public Color getBackgroundColor() {
			return Color.decode(backgroundColor);
		}
	}

	private BufferedImage whiteDiscImage;

	private BufferedImage blackDiscImage;

	private Player cellOwner;

	private CellColor cellColor;

	public BoardCellLayout() {
		cellColor = CellColor.EMPTY;
		setSize(WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		setVisible(true);
		try {
			whiteDiscImage = ImageIO.read(new File(FILE_PATH_WHITE_DISC));
			blackDiscImage = ImageIO.read(new File(FILE_PATH_BLACK_DISC));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(cellColor.getBackgroundColor());
		graphics.fillRect(BOARDER_THICKNESS, BOARDER_THICKNESS, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		if (cellOwner != null) {
			graphics.drawImage(cellOwner == Player.WHITE ? whiteDiscImage : blackDiscImage,
					BOARDER_THICKNESS, BOARDER_THICKNESS, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL, null);
		}
	}

	public void take(final Player cellOwner) {
		this.cellOwner = cellOwner;
		cellColor = CellColor.CAPTURED;
		repaint();
	}

	public void highlight() {
		cellColor = CellColor.HIGHLIGHTED;
		repaint();
	}

	public void clearHighlight() {
		cellColor = CellColor.EMPTY;
		repaint();
	}

	@Override
	public void repaint() {
		paintImmediately(0, 0, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
	}
}
