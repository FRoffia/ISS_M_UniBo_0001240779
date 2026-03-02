package main.java.conway.domain;

import java.util.ArrayList;

public interface IGrid{
	boolean isCellAlive(int x, int y);
	void setCellStatus(int x, int y, boolean v);
	ArrayList<ICell> getRow(int y);
	ArrayList<ICell> getCol(int x);
	int getWidth();
	int getHeight();
}