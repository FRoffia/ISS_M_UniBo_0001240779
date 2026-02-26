package main.java.conway.domain;

import java.util.ArrayList;

public class Grid  implements IGrid{
	ArrayList<ArrayList<Cell>> griglia;
	
	public Grid(int size_x, int size_y) {
		
	}

	@Override
	public boolean isCellAlive(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCellStatus(int x, int y, boolean v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Cell> getRow(int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cell> getCol(int x) {
		// TODO Auto-generated method stub
		return null;
	}

}
