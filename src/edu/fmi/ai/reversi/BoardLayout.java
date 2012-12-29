package edu.fmi.ai.reversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class BoardLayout extends JFrame {

	private static final long serialVersionUID = 5834762299789973250L;

	private static final int BOARD_ROW_COUNT = 8;

	private static final int BOARD_COLUMN_COUNT = 8;

	public BoardLayout() throws HeadlessException {
		this("", null);
	}

	public BoardLayout(GraphicsConfiguration graphicsConfiguration) {
		this("", graphicsConfiguration);
	}

	public BoardLayout(String title, GraphicsConfiguration graphicsConfiguration) {
		super(title, graphicsConfiguration);

		setLayout(new GridLayout(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT));
		final Dimension boardDimension = new Dimension(BOARD_ROW_COUNT
				* BoardCellLayout.WIDTH_BOARD_CELL, BOARD_COLUMN_COUNT
				* BoardCellLayout.HEIGHT_BOARD_CELL);
		setMinimumSize(boardDimension);
		setSize(boardDimension);
		setResizable(false);
		setBackground(Color.WHITE);

		for (int i = 0; i < BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < BOARD_COLUMN_COUNT; ++j) {
				getContentPane().add(new BoardCellLayout(), i + j);
			}
		}

		setVisible(true);
		pack();

	}

	public BoardLayout(String title) throws HeadlessException {
		this(title, null);
	}
}
