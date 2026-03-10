package main.java.conway.domain;

public class LifeController implements GameController{
	IOutDev outDev;
	Life game;
	
	public LifeController(IOutDev device) {
		outDev = device;
	}
	
	@Override
	public void reset() {
		game.resetGame();
		update();
	}

	@Override
	public void nextGeneration() {
		game.nextGeneration();
		update();
	}

	@Override
	public void switchCellStatus(int x, int y) {
		game.switchCellState(y, x);// TODO Auto-generated method stub
		update();
	}

	@Override
	public void auto(int n) {
		for(int i = 0; i < n; i++) {
			game.nextGeneration();
			update();
			try {
				wait(800);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void update() {
		outDev.displayGrid((Grid)game.getGrid());
	}

}
