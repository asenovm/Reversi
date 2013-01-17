package edu.fmi.ai.reversi.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.fmi.ai.reversi.model.Player;

public class ResultsLayout extends JPanel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 8379710427718395507L;

	/**
	 * {@value}
	 */
	private static final String BACKGROUND_COLOR = "#066f02";

	private final ResultsTextView whiteTextView;

	private final ResultsTextView blackTextView;

	private class ChangeResultRunnable implements Runnable {

		private final ResultsTextView textView;

		private final int result;

		public ChangeResultRunnable(final ResultsTextView textView, final int result) {
			this.textView = textView;
			this.result = result;
		}

		@Override
		public void run() {
			textView.setDiscCount(result);
		}

	}

	public ResultsLayout(final int width, final int height) {
		super(new GridLayout(1, 2));

		setBackground(Color.decode(BACKGROUND_COLOR));
		setPreferredSize(new Dimension(width, height));

		whiteTextView = new ResultsTextView(Player.WHITE);
		blackTextView = new ResultsTextView(Player.BLACK);

		add(whiteTextView, 0);
		add(blackTextView, 1);
	}

	public void onResultChanged(final int whiteDiscsCount, final int blackDiscsCount) {
		SwingUtilities.invokeLater(new ChangeResultRunnable(whiteTextView, whiteDiscsCount));
		SwingUtilities.invokeLater(new ChangeResultRunnable(blackTextView, blackDiscsCount));
	}

}
