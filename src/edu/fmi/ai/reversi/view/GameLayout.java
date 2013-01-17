package edu.fmi.ai.reversi.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Collection;

import javax.swing.JFrame;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelObserver;
import edu.fmi.ai.reversi.model.Cell;

public class GameLayout extends JFrame implements ModelObserver {

	/**
	 * {@value}
	 */
	private static final long serialVersionUID = -9058857643235514898L;

	private final BoardLayout boardLayout;

	private final ResultsLayout resultsLayout;

	public GameLayout(final BoardEventsListener listener) {
		super();
		setLayout(new BorderLayout(0, 2));

		boardLayout = new BoardLayout(listener);
		resultsLayout = new ResultsLayout(560, 140);

		final Container container = getContentPane();
		container.add(boardLayout, BorderLayout.CENTER);
		container.add(resultsLayout, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(560, 700));

		pack();
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void onModelChanged(Collection<Cell> changedCells, int whiteDiscsCount,
			int blackDiscsCount) {
		boardLayout.onModelChanged(changedCells, whiteDiscsCount, blackDiscsCount);
		resultsLayout.onResultChanged(whiteDiscsCount, blackDiscsCount);
	}

	@Override
	public void onNextMovesAcquired(Collection<Cell> nextMoves) {
		boardLayout.onNextMovesAcquired(nextMoves);
	}

}
