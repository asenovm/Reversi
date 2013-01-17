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

	/**
	 * {@value}
	 */
	private static final int FRAME_HEIGHT = 700;

	/**
	 * {@value}
	 */
	private static final int FRAME_WIDTH = 558;

	private final BoardLayout boardLayout;

	private final ResultPanel resultsLayout;

	public GameLayout(final BoardEventsListener listener) {
		super();
		setLayout(new BorderLayout(0, 0));

		boardLayout = new BoardLayout(listener);
		resultsLayout = new ResultPanel();

		final Container container = getContentPane();
		container.add(boardLayout, BorderLayout.CENTER);
		container.add(resultsLayout, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		pack();
		setVisible(true);
		setResizable(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void onBoardChanged(Collection<Cell> changedCells) {
		boardLayout.onModelChanged(changedCells);
	}

	@Override
	public void onNextMovesAcquired(Collection<Cell> nextMoves) {
		boardLayout.onNextMovesAcquired(nextMoves);
	}

	@Override
	public void onResultChanged(int whiteDiscs, int blackDiscs) {
		resultsLayout.onResultChanged(whiteDiscs, blackDiscs);
	}

}
