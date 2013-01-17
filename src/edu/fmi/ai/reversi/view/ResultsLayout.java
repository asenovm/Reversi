package edu.fmi.ai.reversi.view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.fmi.ai.reversi.model.Player;

public class ResultsLayout extends JPanel {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = 8379710427718395507L;

	private final ResultsTextView whiteTextView;

	private final ResultsTextView blackTextView;

	public ResultsLayout(final int width, final int height) {
		super(new GridLayout(2, 1));

		setVisible(true);

		final Dimension dimension = new Dimension(width, height);
		setMinimumSize(dimension);
		setPreferredSize(dimension);
		setSize(dimension);

		whiteTextView = new ResultsTextView(Player.WHITE);
		blackTextView = new ResultsTextView(Player.BLACK);

		add(whiteTextView, 0);
		add(blackTextView, 1);
	}

	public void onResultChanged(final int whiteDiscsCount, final int blackDiscsCount) {
		whiteTextView.setDiscCount(whiteDiscsCount);
		blackTextView.setDiscCount(blackDiscsCount);
	}

}
