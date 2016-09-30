package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.*;
import roborally.model.staticObject.Wall;
import roborally.program.conditions.WallCondition;

/**
 * A test class for wall conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WallConditionTest {

	private Robot rob;
	private Board b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(200, 200);
		rob = new Robot(b, new Position(5, 5), new Accu(new Energy(5000)), Direction.RIGHT);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(WallCondition.WALL_CONDITION.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		rob.terminate();
		assertTrue(WallCondition.WALL_CONDITION.result(rob));
	}
	
	@Test
	public void result_validCase() {
		@SuppressWarnings("unused")
		Wall w = new Wall(b, new Position(5L,6L));
		assertTrue(WallCondition.WALL_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase() {
		assertFalse(WallCondition.WALL_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase_robotNotOnBoard() {
		Robot rob2 = new Robot();
		assertFalse(WallCondition.WALL_CONDITION.result(rob2));
	}
	
	@Test
	public void toString_normalCase(){
		assertEquals("wall", WallCondition.WALL_CONDITION.toString());
	}

}
