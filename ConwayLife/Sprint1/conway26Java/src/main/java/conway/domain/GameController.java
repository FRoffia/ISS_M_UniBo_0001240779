package main.java.conway.domain;

public interface GameController {
	void reset();//reset della griglia a stato iniziale
	void nextGeneration();//prosegui di una generazione
	void switchCellStatus(int x, int y);//cambia lo stato della cella alle coordinate x,y
	void auto(int n);//procedi automaticamente di n generazioni
}
