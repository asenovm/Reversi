package edu.fmi.ai.reversi;

import java.util.Collection;

import javax.swing.JOptionPane;

import edu.fmi.ai.reversi.listeners.BoardEventsListener;
import edu.fmi.ai.reversi.listeners.GameSolverCallback;
import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;
import edu.fmi.ai.reversi.util.TurnSwitcher;
import edu.fmi.ai.reversi.view.GameLayout;

public class Game implements BoardEventsListener, GameSolverCallback {

	/**
	 * {@value}
	 */
	private static final String TEXT_WINNER_DISCS = " discs";

	/**
	 * {@value}
	 */
	private static final String TEXT_WINNER_DISCS_COUNT = " with ";

	/**
	 * {@value}
	 */
	private static final String TEXT_WINNER = "Winner is ";

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

	/**
	 * Creates a new Game instance.
	 */
	public Game() {
		boardLayout = new GameLayout(this);
		board = new Board();

		turnSwitcher = new TurnSwitcher();
		currentPlayer = Player.BLACK;

		board.addObserver(boardLayout);

		board.startGame();
		gameSolver = new GameSolver();
	}

	/**
	 * Returns whether or not the game has reached its terminal state and no
	 * further moves can be made.
	 * 
	 * @return whether or not the game has reached its terminal state and no
	 *         further moves can be made.
	 */
	public boolean isFinished() {
		return !board.hasNextMoves(Player.WHITE) && !board.hasNextMoves(Player.BLACK);
	}

	/**
	 * Awaits player input and updates the model and the UI appropriately
	 * afterwards.
	 */
	public void awaitInput() {
		currentPlayer = Player.BLACK;
		final Collection<Cell> nextMoves = board.getNextMoves(currentPlayer);
		if (!nextMoves.isEmpty()) {
			board.nextMove(currentPlayer);
			turnSwitcher.startTurn();
		}
	}

	/**
	 * Makes the next move on the behalf of the AI
	 */
	public void nextMove() {
		currentPlayer = Player.WHITE;
		board.nextMove(currentPlayer);
		gameSolver.getOptimalMove(board, this);
		turnSwitcher.startTurn();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCellSelected(final int cellIndex) {
		if (isLegalMove(cellIndex)) {
			board.takeCell(cellIndex, currentPlayer);
			turnSwitcher.endTurn();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void onOptimalMoveReceived(Collection<Cell> optimalMove) {
		board.takeCells(optimalMove);
		turnSwitcher.endTurn();
	}

	/**
	 * Shows a dialog displaying game winner
	 */
	public void showWinner() {
		final int whiteDiscs = board.getDiscCount(Player.WHITE);
		final int blackDiscs = board.getDiscCount(Player.BLACK);
		final String winner = whiteDiscs > blackDiscs ? Player.WHITE.name() : Player.BLACK.name();
		int winnerDiscs = whiteDiscs > blackDiscs ? whiteDiscs : blackDiscs;
		JOptionPane.showMessageDialog(boardLayout, TEXT_WINNER + winner + TEXT_WINNER_DISCS_COUNT
				+ winnerDiscs + TEXT_WINNER_DISCS);
	}

	private boolean isLegalMove(final int cellIndex) {
		return board.isMovePermitted(cellIndex, currentPlayer) && currentPlayer == Player.BLACK;
	}

}
