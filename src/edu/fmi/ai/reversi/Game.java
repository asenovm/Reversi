package edu.fmi.ai.reversi;

import java.util.concurrent.Semaphore;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.ModelListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.TurnHelper;
import edu.fmi.ai.reversi.view.BoardLayout;

public class Game implements BoardEventsListener {

	public static final int BOARD_ROW_COUNT = 8;

	public static final int BOARD_COLUMN_COUNT = 8;

	private final BoardLayout boardLayout;

	private final Board board;

	private Player currentPlayer;

	private final TurnHelper turnHelper;

	public Game() {
		boardLayout = new BoardLayout(this);
		board = new Board(BOARD_ROW_COUNT, BOARD_COLUMN_COUNT);
		turnHelper = new TurnHelper();
		currentPlayer = Player.WHITE;
	}

	public boolean isFinished() {
		return false;
	}

	public void awaitInput() {
		currentPlayer = Player.WHITE;
		boardLayout.nextTurn(currentPlayer);
		turnHelper.startTurn();
	}

	public void nextMove() {
		currentPlayer = Player.BLACK;
		// blank as for now
	}

	@Override
	public void onCellSelected(final ModelListener listener, final int cellIndex) {
		if (board.isMovePermitted(cellIndex, currentPlayer)) {
			turnHelper.endTurn();
			board.onCellSelected(cellIndex, currentPlayer);
			listener.onMovePermitted();
		}
	}
}
