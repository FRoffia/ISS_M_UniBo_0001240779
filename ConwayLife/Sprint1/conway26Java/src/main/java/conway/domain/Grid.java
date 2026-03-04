package main.java.conway.domain;

import java.util.ArrayList;
import main.java.conway.domain.*;

public class Grid  implements IGrid{
	private int width;
	private int height;
	private ArrayList<ArrayList<ICell>> grid;
	
	public Grid(int size_x, int size_y) {
		grid = new ArrayList<>();//dico che ci sono x colonne da y
		for(int i = 0; i < size_x ; i++) {
			grid.add(new ArrayList<>());
			for(int j = 0; j < size_y; j++) {
				grid.get(i).add(new Cell());
			}
		}
		width = size_x;
		height = size_y;
	}

	@Override
	public boolean isCellAlive(int x, int y) {
		return grid.get(x).get(y).isAlive();
	}

	@Override
	public void setCellStatus(int x, int y, boolean v) {
		grid.get(x).get(y).setStatus(v);
	}

	@Override
	public ArrayList<ICell> getRow(int y) {
		ArrayList<ICell> row = new ArrayList<>();
		for(int i = 0;i < grid.size(); i++) {
			row.add(grid.get(i).get(y));
		}
		return row;
	}

	@Override
	public ArrayList<ICell> getCol(int x) {
		return grid.get(x);
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ICell getCell(int x, int y) {
		return grid.get(x).get(y);
	}

	@Override
	public void reset() {
		for(int i = 0; i < grid.get(0).size(); i++) {
			for(int j = 0; j < grid.size(); j++) {
				grid.get(j).get(i).setStatus(false);
			}
		}
	}

}
