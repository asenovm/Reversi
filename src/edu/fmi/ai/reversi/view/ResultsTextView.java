package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;

import edu.fmi.ai.reversi.model.Player;

public class ResultsTextView extends JTextArea {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -6354085779793155063L;

	/**
	 * {@value}
	 */
	public static final String BACKGROUND_COLOR = "#066f02";

	private static final int START_DISCS_NUMBER = 2;

	private final Player player;

	public ResultsTextView(final Player player) {
		super();
		setEditable(false);

		setBackground(Color.decode(BACKGROUND_COLOR));

		this.player = player;
		setText(player.toString() + " result is: " + START_DISCS_NUMBER);

		final Dimension dimension = new Dimension(100, 20);
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
	}

	public void setDiscCount(final int discCount) {
		setText(player.toString() + " result is:" + discCount);
	}

}
