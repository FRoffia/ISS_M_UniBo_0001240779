package main.java.test;

import static org.junit.Assert.*;

import org.junit.*;

import main.java.conway.domain.ICell;
import main.java.conway.domain.Cell;

public class CellTest {
	private ICell c; //simbolo c che spazierà nel dominio delle ICell
	
	@Before
	public void setup() {
		c = new Cell();
	}

	@After
	public void down() {
	}
	
	@Test
	public void testCellAlive() {
		c.setStatus(true);
		assertTrue(c.isAlive());
	}
	
	@Test
	public void testCellDead() {	
		c.setStatus(false);
		assertFalse(c.isAlive());
	}
}
