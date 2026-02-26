package main.java.conway.domain;

import java.util.ArrayList;

public interface IGrid{
	boolean isCellAlive(int x, int y);
	void setCellStatus(int x, int y, boolean v);
	ArrayList<Cell> getRow(int y);
	ArrayList<Cell> getCol(int x);
}