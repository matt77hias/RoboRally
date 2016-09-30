package roborally.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.Direction;

/**
 * A test class for direction objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class DirectionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void directionFromInt(){
		assertEquals(Direction.UP, Direction.directionFromInt(0));
		assertEquals(Direction.UP, Direction.directionFromInt(4));
		assertEquals(Direction.UP, Direction.directionFromInt(-4));
		assertEquals(Direction.RIGHT, Direction.directionFromInt(1));
		assertEquals(Direction.RIGHT, Direction.directionFromInt(5));
		assertEquals(Direction.RIGHT, Direction.directionFromInt(-3));
		assertEquals(Direction.DOWN, Direction.directionFromInt(2));
		assertEquals(Direction.DOWN, Direction.directionFromInt(6));
		assertEquals(Direction.DOWN, Direction.directionFromInt(-2));
		assertEquals(Direction.LEFT, Direction.directionFromInt(3));
		assertEquals(Direction.LEFT, Direction.directionFromInt(7));
		assertEquals(Direction.LEFT, Direction.directionFromInt(-1));
	}

	@Test
	public void turnDirectionClockwise(){
		assertEquals(Direction.UP, Direction.turnDirectionClockwise(Direction.LEFT));
		assertEquals(Direction.RIGHT, Direction.turnDirectionClockwise(Direction.UP));
		assertEquals(Direction.DOWN, Direction.turnDirectionClockwise(Direction.RIGHT));
		assertEquals(Direction.LEFT, Direction.turnDirectionClockwise(Direction.DOWN));
		assertEquals(null, Direction.turnDirectionClockwise(null));
	}
	
	@Test
	public void turnDirectionCounterClockwise(){
		assertEquals(Direction.UP, Direction.turnDirectionCounterClockwise(Direction.RIGHT));
		assertEquals(Direction.RIGHT, Direction.turnDirectionCounterClockwise(Direction.DOWN));
		assertEquals(Direction.DOWN, Direction.turnDirectionCounterClockwise(Direction.LEFT));
		assertEquals(Direction.LEFT, Direction.turnDirectionCounterClockwise(Direction.UP));
		assertEquals(null, Direction.turnDirectionCounterClockwise(null));
	}
	
	@Test
	public void getDirectionnr(){
		assertEquals(Direction.UP.getDirectionnr(), 0);
		assertEquals(Direction.RIGHT.getDirectionnr(), 1);
		assertEquals(Direction.DOWN.getDirectionnr(), 2);
		assertEquals(Direction.LEFT.getDirectionnr(), 3);
	}
	
	@Test
	public void isValidDirection(){
		assertTrue(Direction.isValidDirection(Direction.UP));
		assertTrue(Direction.isValidDirection(Direction.RIGHT));
		assertTrue(Direction.isValidDirection(Direction.DOWN));
		assertTrue(Direction.isValidDirection(Direction.LEFT));
		assertFalse(Direction.isValidDirection(null));
	}
	
	@Test
	public void getDirectionDimension(){
		assertEquals(Direction.getDirectionDimension(Direction.UP), 2);
		assertEquals(Direction.getDirectionDimension(Direction.RIGHT), 1);
		assertEquals(Direction.getDirectionDimension(Direction.DOWN), 2);
		assertEquals(Direction.getDirectionDimension(Direction.LEFT), 1);
		assertEquals(Direction.getDirectionDimension(null), -1);
	}
	
	@Test
	public void getDirectionOrder(){
		assertEquals(Direction.getDirectionOrder(Direction.UP), -1);
		assertEquals(Direction.getDirectionOrder(Direction.RIGHT), 1);
		assertEquals(Direction.getDirectionOrder(Direction.DOWN), 1);
		assertEquals(Direction.getDirectionOrder(Direction.LEFT), -1);
		assertEquals(Direction.getDirectionOrder(null), 0);
	}
	
	@Test
	public void amountOfTurnsToDirection(){
		assertEquals(Direction.amountOfTurnsToDirection(null,null),-1);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.LEFT,null),-1);
		assertEquals(Direction.amountOfTurnsToDirection(null,Direction.LEFT),-1);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.UP,Direction.UP),0);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.UP,Direction.RIGHT),1);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.UP,Direction.DOWN),2);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.UP,Direction.LEFT),3);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.DOWN,Direction.UP),2);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.DOWN,Direction.RIGHT),3);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.DOWN,Direction.DOWN),0);
		assertEquals(Direction.amountOfTurnsToDirection(Direction.DOWN,Direction.LEFT),1);
	}
	
	@Test
	public void amountOfEfficientTurnsToDirection(){
		assertEquals(Direction.amountOfEfficientTurnsToDirection(null,null),-1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.LEFT,null),-1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(null,Direction.LEFT),-1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.UP,Direction.UP),0);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.UP,Direction.RIGHT),1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.UP,Direction.DOWN),2);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.UP,Direction.LEFT),1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.DOWN,Direction.UP),2);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.DOWN,Direction.RIGHT),1);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.DOWN,Direction.DOWN),0);
		assertEquals(Direction.amountOfEfficientTurnsToDirection(Direction.DOWN,Direction.LEFT),1);
	}
	
	@Test
	public void getAllDirections(){
		assertEquals(Direction.getAllDirections().size(), Direction.getNbOfDirections());
		assertTrue(Direction.getAllDirections().contains(Direction.UP));
		assertTrue(Direction.getAllDirections().contains(Direction.RIGHT));
		assertTrue(Direction.getAllDirections().contains(Direction.DOWN));
		assertTrue(Direction.getAllDirections().contains(Direction.LEFT));
		assertFalse(Direction.getAllDirections().contains(null));
	}
	
	@Test
	public void getNbOfDirections(){
		assertEquals(Direction.getNbOfDirections(), 4);
	}
}
