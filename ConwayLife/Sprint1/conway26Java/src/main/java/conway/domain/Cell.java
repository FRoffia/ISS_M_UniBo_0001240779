package main.java.conway.domain;

public class Cell implements ICell{
	//definisco la rappresentazione completa di una cella
	private boolean status = false;
	
	@Override
	public boolean isAlive() {
		return status;
	}

	@Override
	public void setStatus(boolean v) {
		status = v;
		
	}

}
