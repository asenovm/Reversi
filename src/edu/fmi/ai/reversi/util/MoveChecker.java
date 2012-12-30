package edu.fmi.ai.reversi.util;

import java.util.Map;

import edu.fmi.ai.reversi.model.Cell;
import edu.fmi.ai.reversi.model.Player;

public class MoveChecker {

	private final BaseMoveChecker horizontalPositionChecker;

	private final BaseMoveChecker verticalPositionChecker;

	private final BaseMoveChecker diagonalPositionChecker;

	public MoveChecker(final Map<Integer, Cell> board) {
		horizontalPositionChecker = new HorizontalMoveChecker(board);
		verticalPositionChecker = new VerticalMoveChecker(board);
		diagonalPositionChecker = new DiagonalMoveChecker(board);

	}

	public boolean isMovePermitted(final Cell moveCell, final Player forPlayer) {
		return horizontalPositionChecker.isMovePermitted(moveCell, forPlayer)
				|| verticalPositionChecker.isMovePermitted(moveCell, forPlayer)
				|| diagonalPositionChecker.isMovePermitted(moveCell, forPlayer);
	}

}
