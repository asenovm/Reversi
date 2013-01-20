package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.fmi.ai.reversi.model.Player;

/**
 * A class to represent the panel for displaying the results of the game.
 * 
 * @author martin
 * 
 */
public class ResultPanel extends JPanel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 8379710427718395507L;

	/**
	 * {@value}
	 */
	private static final int GRID_INDEX_BLACK_LABEL = 1;

	/**
	 * {@value}
	 */
	private static final int GRID_INDEX_WHITE_LABEL = 0;

	/**
	 * {@value}
	 */
	private static final int GRID_COLUMN_COUNT = 2;

	/**
	 * {@value}
	 */
	private static final int GRID_ROW_COUNT = 1;

	/**
	 * {@value}
	 */
	private static final String BACKGROUND_COLOR = "#DDD7CB";

	/**
	 * {@value}
	 */
	private static final int PANEL_HEIGHT = 141;

	/**
	 * {@value}
	 */
	private static final int PANEL_WIDTH = 560;

	private final ResultLabel whiteTextView;

	private final ResultLabel blackTextView;

	private class ChangeResultRunnable implements Runnable {

		private final ResultLabel textView;

		private final int result;

		public ChangeResultRunnable(final ResultLabel textView, final int result) {
			this.textView = textView;
			this.result = result;
		}

		@Override
		public void run() {
			textView.setDiscCount(result);
		}

	}

	/**
	 * Creates a new panel for showing the results of the game
	 */
	public ResultPanel() {
		super(new GridLayout(GRID_ROW_COUNT, GRID_COLUMN_COUNT));

		setBackground(Color.decode(BACKGROUND_COLOR));
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		whiteTextView = new ResultLabel(Player.WHITE);
		blackTextView = new ResultLabel(Player.BLACK);

		add(whiteTextView, GRID_INDEX_WHITE_LABEL);
		add(blackTextView, GRID_INDEX_BLACK_LABEL);
	}

	/**
	 * A callback fired when the game results has changed to let the results
	 * panel know that it should update the GUI
	 * 
	 * @param whiteDiscsCount
	 *            the number of white discs on the board
	 * @param blackDiscsCount
	 *            the number of black discs on the board
	 */
	public void onResultChanged(final int whiteDiscsCount, final int blackDiscsCount) {
		SwingUtilities.invokeLater(new ChangeResultRunnable(whiteTextView, whiteDiscsCount));
		SwingUtilities.invokeLater(new ChangeResultRunnable(blackTextView, blackDiscsCount));
	}

}
