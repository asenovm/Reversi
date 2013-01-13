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
		final Player opponent = Player.getOpponent(player);
		for (int i = 0; i < Game.BOARD_ROW_COUNT; ++i) {
			for (int j = 0; j < Game.BOARD_COLUMN_COUNT; ++j) {
				final Cell currentCell = board.get(j, i);
				if (currentCell.isOwnedBy(player)) {
					locationValue += player.getSign() * locationValues[i][j];
				} else if (!currentCell.isOwnedBy(opponent)) {
					opponentValue += opponent.getSign() * locationValues[i][j];
				}
			}
		}
		return locationValue + opponentValue;
	}

	public int getStabilityValue(final Board board, final Player player) {
		return player.getSign() * (board.getStableDiscsCount(player) - board.getStableDiscsCount(Player.getOpponent(player))) * 12;
	}

}
