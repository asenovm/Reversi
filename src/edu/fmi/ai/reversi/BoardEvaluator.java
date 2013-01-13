package edu.fmi.ai.reversi;

import edu.fmi.ai.reversi.model.Board;
import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class BoardEvaluator {

	private final int[][] locationValues;

	public BoardEvaluator() {
		locationValues = new int[][] { { 50, -1, 5, 2, 2, 5, -1, 50 },
				{ -1, 10, 1, 1, 1, 1, 10, -1 }, { 5, 1, 1, 1, 1, 1, 1, 5 },
				{ 2, 1, 1, 0, 0, 1, 1, 2 }, { 2, 1, 1, 0, 0, 1, 1, 2 }, { 5, 1, 1, 1, 1, 1, 1, 5 },
				{ -1, 10, 1, 1, 1, 1, 10, -1 }, { 50, -1, 5, 2, 2, 5, -1, 50 } };
	}

	public float getLocationValue(final Board board, final Player player) {
		int locationValue = 0;
		int opponentValue = 0;
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final Cell currentCell = board.get(j, i);
				if (currentCell.isOwnedBy(player)) {
					locationValue += player.getSign() * locationValues[i][j];
				} else if (!currentCell.isEmpty()) {
					opponentValue += Player.getOther(player).getSign() * locationValues[i][j];
				}
			}
		}
		final float result = opponentValue == 0 ? locationValue : (float) locationValue
				/ opponentValue;
		return player == Player.WHITE ? result : Math.abs(result);
	}

	public float getMobilityValue(final Board board, final Player player) {
		// dont take this into accoutn as for now
		return 0f;
	}
}
