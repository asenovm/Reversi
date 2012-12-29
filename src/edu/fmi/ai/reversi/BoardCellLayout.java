package edu.fmi.ai.reversi;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import edu.fmi.ai.reversi.BoardLayout.DiscColor;

public class BoardCellLayout extends JPanel {

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
	private static final String CELL_COLOR = "0xBFB630";

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

	private BufferedImage whiteDiscImage;

	private BufferedImage blackDiscImage;

	private DiscColor discColor;

	public BoardCellLayout() {
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
		graphics.setColor(Color.decode(CELL_COLOR));
		graphics.fillRect(BOARDER_THICKNESS, BOARDER_THICKNESS,
				WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
		if (discColor != null) {
			graphics.drawImage(discColor == DiscColor.WHITE ? whiteDiscImage
					: blackDiscImage, BOARDER_THICKNESS, BOARDER_THICKNESS,
					WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL, null);
		}
	}

	public void placeDisc(final DiscColor discColor) {
		this.discColor = discColor;
		paintImmediately(0, 0, WIDTH_BOARD_CELL, HEIGHT_BOARD_CELL);
	}
}
