package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.fmi.ai.reversi.model.Player;

/**
 * A layout representing a cell on the board
 * 
 * @author martin
 * 
 */
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

		/**
		 * {@value}
		 */
		EMPTY(CELL_COLOR_DEFAULT),
		/**
		 * {@value}
		 */
		CAPTURED(CELL_COLOR_DEFAULT),
		/**
		 * {@value}
		 */
		HIGHLIGHTED(CELL_COLOR_HIGHLIGHTED);

		private final String backgroundColor;

		private CellColor(final String color) {
			this.backgroundColor = color;
		}

		/**
		 * Returns the Color instance corresponding to this enum value
		 * 
		 * @return the Color instance corresponding to this enum value
		 */
		public Color getBackgroundColor() {
			return Color.decode(backgroundColor);
		}
	}

	private BufferedImage whiteDiscImage;

	private BufferedImage blackDiscImage;

	private Player cellOwner;

	private CellColor cellColor;

	/**
	 * Creates a new board cell layout.
	 */
	public BoardCellLayout() {
		cellColor = CellColor.EMPTY;
		setPreferredSize(new Dimension(WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL));
		setVisible(true);
		try {
			whiteDiscImage = ImageIO.read(new File(FILE_PATH_WHITE_DISC));
			blackDiscImage = ImageIO.read(new File(FILE_PATH_BLACK_DISC));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Draws the background of the board cell
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);
		graphics.setColor(cellColor.getBackgroundColor());
		graphics.fillRect(BOARDER_THICKNESS, BOARDER_THICKNESS, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		graphics.drawImage(getCellImage(), BOARDER_THICKNESS, BOARDER_THICKNESS, WIDTH_BOARD_CELL,
				HEIGHT_BOARD_CELL, null);
	}

	/**
	 * Updates the UI so that this board cell looks like being taken from the
	 * <tt>player</tt>specified
	 * 
	 * @param owner
	 *            the owner of the board cell to which this layout corresponds
	 */
	public void take(final Player owner) {
		this.cellOwner = owner;
		cellColor = CellColor.CAPTURED;
		repaint();
	}

	/**
	 * Highlights this cell
	 */
	public void highlight() {
		cellColor = CellColor.HIGHLIGHTED;
		repaint();
	}

	/**
	 * Clears the highlight out of the cell
	 */
	public void clearHighlight() {
		cellColor = CellColor.EMPTY;
		repaint();
	}

	/**
	 * 
	 * Immediately repaints the cell layout
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public void repaint() {
		paintImmediately(0, 0, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
	}

	private Image getCellImage() {
		if (cellOwner == Player.WHITE) {
			return whiteDiscImage;
		} else if (cellOwner == Player.BLACK) {
			return blackDiscImage;
		}
		return null;
	}
}
