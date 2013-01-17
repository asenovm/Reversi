package edu.fmi.ai.reversi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Collection;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.TurnSwitcher;
import edu.fmi.ai.reversi.view.GameLayout;

public class Game implements BoardEventsListener {

	/**
	 * {@value}
	 */
	public static final int BOARD_ROW_COUNT = 8;

	/**
	 * {@value}
	 */
	public static final int BOARD_COLUMN_COUNT = 8;

	private final GameLayout boardLayout;

	private final Board board;

	private Player currentPlayer;

	private final TurnSwitcher turnSwitcher;

	private final GameSolver gameSolver;

	public Game() {
		boardLayout = new GameLayout(this);
		board = new Board();

		turnSwitcher = new TurnSwitcher();
		currentPlayer = Player.BLACK;

		board.addObserver(boardLayout);

		board.startGame();
		gameSolver = new GameSolver();
	}

	public boolean isFinished() {
		return !board.hasNextMoves(Player.WHITE) && !board.hasNextMoves(Player.BLACK);
	}

	public void awaitInput() {
		currentPlayer = Player.BLACK;
		final Collection<Cell> nextMoves = board.getNextMoves(currentPlayer);
		if (!nextMoves.isEmpty()) {
			board.nextMove(currentPlayer);
			turnSwitcher.startTurn();
		}
	}

	public void nextMove() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new FileOutputStream("dump.txt", true));
			writer.println("**************before********************");
			writer.println(board.toString());
			writer.println("**********************************");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		currentPlayer = Player.WHITE;
		board.nextMove(currentPlayer);
		final GameMoveHelper optimalMove = gameSolver.getOptimalMove(board);
		board.takeCells(optimalMove.move);
		try {
			writer = new PrintWriter(new FileOutputStream("dump.txt", true));
			writer.println("**************after********************");
			writer.println(board.toString());
			writer.println("**********************************");
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCellSelected(final int cellIndex) {
		if (isLegalMove(cellIndex)) {
			board.takeCell(cellIndex, currentPlayer);
			turnSwitcher.endTurn();
		}
	}

	private boolean isLegalMove(final int cellIndex) {
		return board.isMovePermitted(cellIndex, currentPlayer) && currentPlayer == Player.BLACK;
	}
}
