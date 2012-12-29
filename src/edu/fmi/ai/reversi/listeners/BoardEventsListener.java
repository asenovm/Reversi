package edu.fmi.ai.reversi.listeners;

public interface BoardEventsListener {
	void onCellSelected(final ModelListener listener, final int cellIndex);
}
