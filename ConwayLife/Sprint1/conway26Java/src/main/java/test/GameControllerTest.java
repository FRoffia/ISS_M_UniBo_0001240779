package main.java.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import main.java.conway.domain.IGrid;
import main.java.conway.domain.Cell;
import main.java.conway.domain.GameController;
import main.java.conway.domain.ICell;
import main.java.conway.domain.Grid;

public class GameControllerTest {
	private GameController c;
	
	@Before
	public void setup() {
	}

	@After
	public void down() {
	}
	
	/*
	isCellAlive deve restituire il vero valore di vita della cella puntata dalle coordinate.<br>
	setCellStatus deve modificare correttamente il valore della cella puntata dalle coordinate<br>
	Quindi posso fare un test che:<br>
	1:Settare stato a true con setCellStatus<br>
	2:Controllare stato con isCellAlive e vedere se dà true<br>
	3:Settare stato a false<br>
	4:Controllare stato con isCellAlive e vedere se dà false<br><br>  
	 */
	@Test
	public void testCellAlive() {
		g.setCellStatus(5,5,true);
		assertTrue(g.isCellAlive(5,5));
	}
	
	@Test
	public void testCellDead() {	
		g.setCellStatus(5,5,false);
		assertFalse(g.isCellAlive(5,5));
	}
	
	
	/*
	getRow deve restituire la riga di celle giusta.<br>
	getCol deve restituire la colonna di celle giusta.<br>
	Quindi posso fare un test che:<br>
	1:Settare stato a true con setCellStatus a una riga/colonna sola<br>
	2:Prelevare riga/colonna<br>
	3:Verificare che sia quella giusta controllando che sia tutta vera<br><br>
	*/
	@Test 
	public void testGetRow() {
		for(int i = 0; i < width; i++) {
			g.setCellStatus(i, 5, true);
		}
		ArrayList<ICell> row = g.getRow(5);
		for(int i = 0; i < width; i++) {
			assertTrue(row.get(i).isAlive());
		}
	}
	
	@Test 
	public void testGetCol() {
		for(int i = 0; i < height; i++) {
			g.setCellStatus(5, i, true);
		}
		ArrayList<ICell> col = g.getCol(5);
		for(int i = 0; i < height; i++) {
			assertTrue(col.get(i).isAlive());
		}
	}
	
	/*getWidth e getHeight devono restituire le dimensioni passate al costruttore<br>
	1:Costruisco la griglia con valori predefiniti<br>
	2:Controllo se quelle restituite dai metodi corrispondono<br><br>*/
	@Test
	public void testGetHeight() {
		assertEquals(g.getHeight(),height);
	}
	
	@Test
	public void testGetWidth() {
		assertEquals(g.getWidth(),width);
	}
	
	/*getCell deve restituire la cella giusta. Quindi:
	1:Con setCellStatus porto una cella a vivo
	2:Con getCell la vado a prendre
	3:Controllo che sia vera*/
	@Test
	public void testGetCell() {
		g.setCellStatus(5, 5, true);
		assertTrue(g.getCell(5, 5).isAlive());
	}
	
	/*Per testare reset provo ad impostare tutte le celle a true con la primitiva get Cell, eseguo reset, e controllo che tutte siano 
	state portate a false*/
	@Test
	public void testReset() {
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				g.getCell(j, i).setStatus(true);
			}
		}
		g.reset();
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				assertFalse(g.getCell(j, i).isAlive());
			}
		}
	}
}
