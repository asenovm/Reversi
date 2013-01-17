package edu.fmi.ai.reversi.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.fmi.ai.reversi.model.Player;

public class ResultsTextView extends JLabel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -6354085779793155063L;

	private static final int START_DISCS_NUMBER = 2;

	private final Player player;

	public ResultsTextView(final Player player) {
		super(player.toString() + " result is: " + START_DISCS_NUMBER, SwingConstants.CENTER);
		this.player = player;
		setBackground(player == Player.WHITE ? Color.WHITE : Color.BLACK);
	}

	public void setDiscCount(final int discCount) {
		setText(player.toString() + " result is:" + discCount);
	}

}
