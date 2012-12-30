package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.TurnSwitcher;
import edu.fmi.ai.reversi.view.BoardLayout;

public class Game implements BoardEventsListener {

	/**
	 * {@value}
	 */
	public static final int POSITION_CENTER_TOP_LEFT = 27;

	/**
	 * {@value}
	 */
	public static final int POSITION_CENTER_TOP_RIGHT = 28;

	/**
	 * {@value}
	 */
	public static final int POSITION_CENTER_BOTTOM_LEFT = 35;

	/**
	 * {@value}
	 */
	public static final int POSITION_CENTER_BOTTOM_RIGHT = 36;

	/**
	 * {@value}
	 */
	public static final int BOARD_ROW_COUNT = 8;

	/**
	 * {@value}
	 */
	public static final int BOARD_COLUMN_COUNT = 8;

	private final BoardLayout boardLayout;

	private final Board board;

	private Player currentPlayer;

	private final TurnSwitcher turnSwitcher;

	public Game() {
		boardLayout = new BoardLayout(this);
		board = new Board(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT);
		turnSwitcher = new TurnSwitcher();
		currentPlayer = Player.BLACK;

		board.addObserver(boardLayout);
	}

	public boolean isFinished() {
		return false;
	}

	public void awaitInput() {
		currentPlayer = Player.BLACK;
		turnSwitcher.startTurn();
	}

	public void nextMove() {
		currentPlayer = Player.WHITE;
		turnSwitcher.startTurn();
	}

	@Override
	public void onCellSelected(final int cellIndex) {
		if (board.isMovePermitted(cellIndex, currentPlayer)) {
			board.onCellSelected(cellIndex, currentPlayer);
			turnSwitcher.endTurn();
		}
	}
}
