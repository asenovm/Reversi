package edu.fmi.ai.reversi.listeners;

public interface BoardEventsListener {
	void onCellSelected(final MoveListener listener, final int cellIndex);
}
