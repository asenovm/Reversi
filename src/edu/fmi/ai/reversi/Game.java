package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
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

	public static final int BOARD_MAX_INDEX = BOARD_COLUMN_COUNT
			* BOARD_ROW_COUNT - 1;

	private final BoardLayout boardLayout;

	private final Board board;

	private Player currentPlayer;

	private final TurnSwitcher turnSwitcher;

	private final GameSolver gameSolver;

	public Game() {
		boardLayout = new BoardLayout(this);
		board = new Board(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT);
		turnSwitcher = new TurnSwitcher();

		currentPlayer = Player.BLACK;
		board.addObserver(boardLayout);

		gameSolver = new GameSolver();
	}

	public boolean isFinished() {
		return false;
	}

	public void awaitInput() {
		currentPlayer = Player.BLACK;
		board.nextMove(currentPlayer);
		turnSwitcher.startTurn();
	}

	public void nextMove() {
		currentPlayer = Player.WHITE;
		board.nextMove(currentPlayer);
		final GamePojo optimalMove = gameSolver.getOptimalMove(board);
		System.out.println("optimal move value is " + optimalMove.value);
		board.takeCells(optimalMove.move);
	}

	@Override
	public void onCellClicked(final int cellIndex) {
		if (board.isMovePermitted(cellIndex, currentPlayer)) {
			board.takeCell(cellIndex, currentPlayer);
			turnSwitcher.endTurn();
		}
	}
}
