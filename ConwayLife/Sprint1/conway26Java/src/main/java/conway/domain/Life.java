package main.java.conway.domain;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Life implements LifeInterface{
	private IGrid currentGrid;
	private IGrid nextGrid;
    
   public static LifeInterface CreateGameRules() {
	   return new Life(5, 5); 
	   // Dimensioni di default, possono essere 
	   //lette da un file di configurazione o passate come parametri
   }

    // Costruttore che accetta una griglia pre-configurata (utile per i test)
    public Life(boolean[][] initialGrid) {
    	this.currentGrid = new Grid(initialGrid[0].length,initialGrid.length);
        this.nextGrid    = new Grid(initialGrid[0].length,initialGrid.length);
        
        for(int i = 0; i < this.currentGrid.getHeight(); i++) {
        	for(int j = 0; j < this.currentGrid.getWidth(); j++) {
        		this.currentGrid.setCellStatus(j,i,initialGrid[i][j]);
        		this.nextGrid.setCellStatus(j,i,false);
        	}
        }
    }

    // Costruttore che crea una griglia vuota di dimensioni specifiche (tutte le celle vengono inizializzate qui a false)
    public Life(int rows, int cols) {
    	currentGrid = new Grid(cols,rows);
    	nextGrid = new Grid(cols,rows);
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < cols; j++) {
    			currentGrid.setCellStatus(i, j, false);
    			nextGrid.setCellStatus(i, j, false);
    		}
    	}
    	
    }

    // Calcola la generazione successiva applicando le 4 regole di Conway
    public void nextGeneration() {
    	// Applichiamo le regole leggendo da currentGrid e scrivendo in nextGrid
        for (int y = 0; y < currentGrid.getHeight(); y++) {
            for (int x = 0; x < currentGrid.getWidth(); x++) {
                int neighbors = countNeighborsLive(y, x);
                boolean isAlive = currentGrid.isCellAlive(x, y);
                //apply rules
                if (isAlive) {
                    nextGrid.setCellStatus(x, y, (neighbors == 2 || neighbors == 3));
                } else {
                    nextGrid.setCellStatus(x, y, (neighbors == 3));
                }
            }
        }

        // --- IL PING-PONG ---
        // Scambiamo i riferimenti: ciò che era 'next' diventa 'current'
        IGrid temp = currentGrid;
        currentGrid      = nextGrid;
        nextGrid         = temp;
        // Nota: non abbiamo creato nuovi oggetti, abbiamo solo spostato i puntatori
    }
    
    protected int countNeighborsLive(int row, int col) {
        int count = 0;
        if (row-1 >= 0) {
        	if( currentGrid.isCellAlive(col, row-1)) count++;
        }
        if (row-1 >= 0 && col-1 >= 0) {
        	if( currentGrid.isCellAlive(col-1, row-1)) count++;
        }
        if (row-1 >= 0 && col+1 < currentGrid.getWidth()) {
        	if( currentGrid.isCellAlive(col+1, row-1)) count++;
        }
        if (col-1 >= 0) {
        	if( currentGrid.isCellAlive(col-1, row)) count++;
         }
        if (col+1 < currentGrid.getWidth()) {
        	if( currentGrid.isCellAlive(col+1,row)) count++;
       }
        if (row+1 < currentGrid.getHeight()) {
        	if( currentGrid.isCellAlive(col, row+1)) count++;
         }
        if (row+1 < currentGrid.getHeight() && col-1 >= 0) {
        	if( currentGrid.isCellAlive(col-1, row+1)) count++;
        }
        if (row+1 < currentGrid.getHeight() && col+1 < currentGrid.getWidth()) {
        	if( currentGrid.isCellAlive(col+1, row+1)) count++;
       }
        //System.out.println("Cell (" + row + "," + col + ") has " + count + " live neighbors.");
        return count;
    }


    // Metodi di utilità per i test
    public boolean getCell(int r, int c) { return currentGrid.isCellAlive(c, r); }
    public void setCell(int r, int c, boolean state) { currentGrid.setCellStatus(c, r, state); }
    public boolean[][] getGrid() {
    	boolean[][] grid = new boolean[currentGrid.getHeight()][currentGrid.getWidth()];
    	for(int i = 0; i < currentGrid.getHeight();i++) {
    		for(int j = 0; j < currentGrid.getWidth();j++) {
    			grid[i][j] = currentGrid.isCellAlive(j, i);
    		}
    	}
    	return grid; 
	}

	@Override
	public boolean isAlive(int row, int col) {
		return currentGrid.isCellAlive(col, row);
	}

	@Override
	public int getRows() {
 		return currentGrid.getHeight();
	}

	@Override
	public int getCols() {
 		return currentGrid.getWidth();
	}
	
	//Versione NAIVE
//	private boolean[][] deepCopy(boolean[][] original) {
//	    if (original == null) return null;
//
//	    boolean[][] result = new boolean[original.length][];
//	    for (int i = 0; i < original.length; i++) {
//	        // Creiamo una nuova riga e copiamo i valori della riga originale
//	        result[i] = original[i].clone(); 
//	        // Nota: clone() su un array di primitivi (boolean) è sicuro 
//	        // perché i primitivi vengono copiati per valore.
//	    }
//	    return result;
//	}
	

	private boolean[][] deepCopyJava8(boolean[][] original) {
	    return Arrays.stream(original)
	                 .map(boolean[]::clone)
	                 .toArray(boolean[][]::new);
	}
	
	public String gridRep( ) {
		String toString = new String();
		for(int i = 0; i < currentGrid.getHeight(); i++) {
			toString += currentGrid.getRow(i).stream() // Stream di boolean[] (le righe)
			        .map(cell -> {
			            // Trasformiamo ogni riga in una stringa di . e O
			            StringBuilder sb = new StringBuilder();
			            sb.append(cell.isAlive() ? "O " : ". ");
			            return sb.toString();
			        })
			        .collect(Collectors.joining()) + "\n"; // Uniamo le righe con un a capo
		}
	    
	    
	    return toString;
	}
}
