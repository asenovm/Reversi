package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.fmi.ai.reversi.model.Player;

public class ResultPanel extends JPanel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 8379710427718395507L;

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

	public ResultPanel() {
		super(new GridLayout(1, 2));

		setBackground(Color.decode(BACKGROUND_COLOR));
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

		whiteTextView = new ResultLabel(Player.WHITE);
		blackTextView = new ResultLabel(Player.BLACK);

		add(whiteTextView, 0);
		add(blackTextView, 1);
	}

	public void onResultChanged(final int whiteDiscsCount, final int blackDiscsCount) {
		SwingUtilities.invokeLater(new ChangeResultRunnable(whiteTextView, whiteDiscsCount));
		SwingUtilities.invokeLater(new ChangeResultRunnable(blackTextView, blackDiscsCount));
	}

}
