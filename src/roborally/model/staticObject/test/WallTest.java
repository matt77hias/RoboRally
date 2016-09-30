package roborally.model.staticObject.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.*;
import roborally.model.*;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.staticObject.Wall;

/**
 * A test class for wall objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WallTest {

	Board board;
	Position pos;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		board = new Board(10,11);
		pos = new Position(3,4);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct1(){
		Wall temp = new Wall();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertFalse(temp.isTerminated());
		assertTrue(temp.canHaveAsBoard(null));
		assertTrue(temp.canHaveAsPosition(null));
	}
	
	@Test
	public void construct2(){
		Wall temp = new Wall(board, pos);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),pos);
		assertFalse(temp.isTerminated());
		assertTrue(temp.canHaveAsBoard(board));
		assertTrue(temp.canHaveAsPosition(pos));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void contruct_illegalArgumentException(){
		@SuppressWarnings("unused")
		Wall w = new Wall(board,pos);
		@SuppressWarnings("unused")
		Wall ww = new Wall(board,pos);
	}
	
	@Test
	public void terminate(){
		Wall temp = new Wall(board, pos);
		assertFalse(temp.isTerminated());
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),pos);
		temp.terminate();
		assertTrue(temp.isTerminated());
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertTrue(temp.canHaveAsBoard(null));
		assertTrue(temp.canHaveAsPosition(null));
		temp.terminate();
		assertTrue(temp.isTerminated());
		assertTrue(temp.canHaveAsBoard(null));
		assertTrue(temp.canHaveAsPosition(null));
	}
	
	@Test
	public void canSharePositionWith(){
		Wall positioned = new Wall();
		
		BoardModel r1 = new Robot();
		BoardModel w1 = new Wall();
		BoardModel b1 = new Battery();
		InventoryModel b2 = new Battery();
		Robot r2 = new Robot();
		Wall w2 = new Wall();
		Battery b3 = new Battery();
		
		assertFalse(positioned.canSharePositionWith(r1));
		assertFalse(positioned.canSharePositionWith(r2));
		assertFalse(positioned.canSharePositionWith(w1));
		assertFalse(positioned.canSharePositionWith(w2));
		assertFalse(positioned.canSharePositionWith(b1));
		assertFalse(positioned.canSharePositionWith(b2));
		assertFalse(positioned.canSharePositionWith(b3));
		assertFalse(positioned.canSharePositionWith(null));
	}
	
	@Test
	public void hit(){
		Wall w = new Wall();
		assertFalse(w.isTerminated());
		w.hit();
		assertFalse(w.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		Wall w = new Wall();
		w.terminate();
		w.hit();
	}
}
