package main.java.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

import main.java.conway.domain.IGrid;
import main.java.conway.domain.Cell;
import main.java.conway.domain.ICell;
import main.java.conway.domain.Grid;

public class GridTest {
	private IGrid g;
	private int width;
	private int height;
	
	@Before
	public void setup() {
		width = 10;
		height = 10;
		g = new Grid(width,height);
	}

	@After
	public void down() {
	}
	
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
	
	@Test
	public void testGetHeight() {
		assertEquals(g.getHeight(),height);
	}
	
	@Test
	public void testGetWidth() {
		assertEquals(g.getWidth(),width);
	}
}
