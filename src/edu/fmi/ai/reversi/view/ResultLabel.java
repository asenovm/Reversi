package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import edu.fmi.ai.reversi.model.Player;

public class ResultLabel extends JLabel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -6354085779793155063L;

	/**
	 * {@value}
	 */
	private static final int TEXT_SIZE = 18;

	/**
	 * {@value}
	 */
	private static final String TEXT_COLOR_GREY = "#999999";

	/**
	 * {@value}
	 */
	private static final String TEXT_RESULT = " result is: ";

	/**
	 * {@value}
	 */
	private static final String REGEX_COUNT = "\\d+";

	/**
	 * {@value}
	 */
	private static final int START_DISCS_NUMBER = 2;

	/**
	 * Creates a new result label for the <tt>player</tt> specified
	 * 
	 * @param player
	 *            the player for whom the label is to be created
	 */
	public ResultLabel(final Player player) {
		super(player.toString() + TEXT_RESULT + START_DISCS_NUMBER, SwingConstants.CENTER);
		setForeground(player == Player.WHITE ? Color.WHITE : Color.decode(TEXT_COLOR_GREY));
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, TEXT_SIZE));

	}

	/**
	 * Sets the number of possessed by the player discs to be equal to the one
	 * specified here.
	 * 
	 * @param discCount
	 *            the new number of discs to be displayed
	 */
	public void setDiscCount(final int discCount) {
		final String updatedResult = getText().replaceAll(REGEX_COUNT, Integer.toString(discCount));
		setText(updatedResult);
	}
}
