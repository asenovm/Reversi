package edu.fmi.ai.reversi.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import edu.fmi.ai.reversi.Game;
import edu.fmi.ai.reversi.util.MoveChecker;

public class Board {

	private final Map<Integer, Cell> board;

	private final MoveChecker checker;

	public Board(final int boardRows, final int boardColumns) {
		board = new HashMap<Integer, Cell>();
		for (int i = 0; i < boardRows; ++i) {
			for (int j = 0; j < boardColumns; ++j) {
				final Cell currentCell = new Cell(j, i);
				board.put(i * boardColumns + j, currentCell);
			}
		}

		board.get(Game.POSITION_CENTER_TOP_LEFT).take(Player.WHITE);
		board.get(Game.POSITION_CENTER_TOP_RIGHT).take(Player.BLACK);
		board.get(Game.POSITION_CENTER_BOTTOM_LEFT).take(Player.WHITE);
		board.get(Game.POSITION_CENTER_BOTTOM_RIGHT).take(Player.BLACK);

		// TODO remove exposure
		checker = new MoveChecker(Collections.unmodifiableMap(board));
	}

	public void onCellSelected(final int cellIndex, final Player owner) {
		board.get(cellIndex).take(owner);
	}

	public boolean isMovePermitted(final int cellIndex, final Player forPlayer) {
		final Cell moveCell = board.get(cellIndex);
		return moveCell.isEmpty()
				&& checker.isMovePermitted(moveCell, forPlayer);
	}

}
