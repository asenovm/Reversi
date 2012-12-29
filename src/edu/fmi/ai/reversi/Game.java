package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.TurnSwitcher;
import edu.fmi.ai.reversi.view.BoardLayout;

public class Game implements BoardEventsListener {

	public static final int BOARD_ROW_COUNT = 8;

	public static final int BOARD_COLUMN_COUNT = 8;

	private final BoardLayout boardLayout;

	private final Board board;

	private Player currentPlayer;

	private final TurnSwitcher turnSwitcher;

	public Game() {
		boardLayout = new BoardLayout(this);
		board = new Board(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT);
		turnSwitcher = new TurnSwitcher();
		currentPlayer = Player.WHITE;
	}

	public boolean isFinished() {
		return false;
	}

	public void awaitInput() {
		currentPlayer = Player.WHITE;
		boardLayout.nextTurn(currentPlayer);
		turnSwitcher.startTurn();
	}

	public void nextMove() {
		currentPlayer = Player.BLACK;
		boardLayout.nextTurn(currentPlayer);
		turnSwitcher.startTurn();
		// blank as for now
	}

	@Override
	public void onCellSelected(final ModelListener listener, final int cellIndex) {
		if (board.isMovePermitted(cellIndex, currentPlayer)) {
			board.onCellSelected(cellIndex, currentPlayer);
			listener.onMovePermitted();
			turnSwitcher.endTurn();
		}
	}
}
