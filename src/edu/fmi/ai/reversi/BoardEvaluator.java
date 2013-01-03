package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class BoardEvaluator {

	private final int[][] boardEvaluations;

	public BoardEvaluator() {
		boardEvaluations = new int[][] { { 99, -8, 8, 6, 6, 8, -8, 99 },
				{ -8, -24, -4, -3, -3, -4, -24, -8 }, { 8, -4, 7, 4, 4, 7, -4, 8 },
				{ 6, -3, 4, 0, 0, 4, -3, 6 }, { 6, -3, 4, 0, 0, 4, -3, 6 },
				{ 8, -4, 7, 4, 4, 7, -4, 8 }, { -8, -24, -4, -3, -3, -4, -24, -8 },
				{ 99, -8, 8, 6, 6, 8, -8, 99 } };
	}

	public int getBoardEvaluation(final Board board, final Player player) {
		int evaluation = 0;
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final Cell currentCell = board.get(j, i);
				if (currentCell.isOwnedBy(player)) {
					evaluation += boardEvaluations[i][j];
				}
			}
		}
		return evaluation;
	}

}
