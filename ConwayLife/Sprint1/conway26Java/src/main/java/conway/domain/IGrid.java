package main.java.conway.domain;

import java.util.ArrayList;
import main.java.conway.domain.ICell;

public interface IGrid{
	boolean isCellAlive(int x, int y);//restituisce lo stato della cella locata alle coordinate x e y
	void setCellStatus(int x, int y, boolean v);//porta la cella locata alle coordinate x,y allo stato indicato
	ArrayList<ICell> getRow(int y);//restituisce la y-esima riga di celle
	ArrayList<ICell> getCol(int x);//restituisce la x-esima riga di celle
	int getWidth();//primitiva, restituisce larghezza della griglia
	int getHeight();//primitiva, restituisce altezza della griglia
	ICell getCell(int x, int y);//primitiva, restituisce la cella alle coordinate x,y
	public void reset();//primitiva, porta tutte le celle allo stato morto
}