package roborally.board.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Dimension;
import roborally.board.IllegalDimensionException;
import roborally.board.Position;
import roborally.model.Direction;

/**
 * A class collecting tests for the class of positions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class PositionTest {
	
	Position pos19x20y;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		pos19x20y = new Position(new long[]{19L,20L});
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct1(){
		Position pos = new Position();
		assertEquals(Position.STANDARD_POSITION_X_COORDINATE, pos.getCoordinates()[0]);
		assertEquals(Position.STANDARD_POSITION_Y_COORDINATE, pos.getCoordinates()[1]);
	}
	@Test
	public void construct2() {
		Position pos = new Position(10L, 20L);
		assertEquals(10L, pos.getCoordinates()[0]);
		assertEquals(20L, pos.getCoordinates()[1]);
	}

	@Test
	public void construct3() {
		Position pos = new Position(new long[]{10L, 20L});
		assertEquals(10L, pos.getCoordinates()[0]);
		assertEquals(20L, pos.getCoordinates()[1]);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructor_illegalLength() {
		@SuppressWarnings("unused")
		Position pos = new Position(new long[]{10L, 20L, 30L});
	}
	
	@Test
	public void areValidCoordinates_normalValuesAccepted(){
		assertTrue(Position.areValidCoordinates(new long[]{10L, 20L}));
	}
	
	@Test
	public void areValidCoordinates_illegalDimensionRejected(){
		assertFalse(Position.areValidCoordinates(new long[]{10L, 20L, 30L}));
	}
	
	@Test
	public void getCoordinates(){
		assertEquals(19L, pos19x20y.getCoordinates()[0]);
		assertEquals(20L, pos19x20y.getCoordinates()[1]);
	}
	
	@Test
	public void getCoordinate(){
		assertEquals(19L, pos19x20y.getCoordinate(Dimension.HORIZONTAL));
		assertEquals(20L, pos19x20y.getCoordinate(Dimension.VERTICAL));
	}
	
	@Test(expected = IllegalDimensionException.class)
	public void getCoordinate_illegalDimensionRejected(){
		pos19x20y.getCoordinate(null);
	}
	
	@Test
	public void equals_verification(){
		assertTrue(pos19x20y.equals(pos19x20y));
		Position pos20x20y = new Position(new long[]{20L, 20L});
		assertFalse(pos20x20y.equals(pos19x20y));
		assertFalse(pos19x20y.equals(pos20x20y));
		pos19x20y = new Position(new long[]{20L, 20L});
		assertTrue(pos20x20y.equals(pos19x20y));
		assertTrue(pos19x20y.equals(pos20x20y));
		
		roborally.model.inventory.item.Battery b = new roborally.model.inventory.item.Battery();
		assertFalse(pos20x20y.equals(b));
		assertFalse(pos20x20y.equals(null));
	}
	
	@Test
	public void clone_singleCase(){
		assertEquals(pos19x20y, pos19x20y.clone());
		assertTrue(pos19x20y.equals(pos19x20y.clone()));
	}
	
	@Test
	public void getManhattanDistanceSeparation_normalCases(){
		Position p1 = new Position(10,10);
		Position p2 = new Position(20,20);
		Position p3 = new Position(10,20);
		assertEquals(p1.getManhattanDistanceSeparation(p1),BigInteger.valueOf(0L));
		assertEquals(p1.getManhattanDistanceSeparation(p2),BigInteger.valueOf(20L));
		assertEquals(p1.getManhattanDistanceSeparation(p3),BigInteger.valueOf(10L));
		assertEquals(p2.getManhattanDistanceSeparation(p1),BigInteger.valueOf(20L));
		assertEquals(p2.getManhattanDistanceSeparation(p2),BigInteger.valueOf(0L));
		assertEquals(p2.getManhattanDistanceSeparation(p3),BigInteger.valueOf(10L));
		assertEquals(p3.getManhattanDistanceSeparation(p1),BigInteger.valueOf(10L));
		assertEquals(p3.getManhattanDistanceSeparation(p2),BigInteger.valueOf(10L));
		assertEquals(p3.getManhattanDistanceSeparation(p3),BigInteger.valueOf(0L));
	}
	
	@Test
	public void getManhattanDistanceSeparation_moreThanLongMax(){
		Position p1 = new Position(0,0);
		Position p2 = new Position(Long.MAX_VALUE,Long.MAX_VALUE);
		Position p3 = new Position(Long.MAX_VALUE,0);
		assertEquals(p1.getManhattanDistanceSeparation(p1),BigInteger.valueOf(0L));
		assertEquals(p1.getManhattanDistanceSeparation(p2),BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)));
		assertEquals(p1.getManhattanDistanceSeparation(p3),BigInteger.valueOf(Long.MAX_VALUE));
		assertEquals(p2.getManhattanDistanceSeparation(p1),BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.valueOf(Long.MAX_VALUE)));
		assertEquals(p2.getManhattanDistanceSeparation(p2),BigInteger.valueOf(0L));
		assertEquals(p2.getManhattanDistanceSeparation(p3),BigInteger.valueOf(Long.MAX_VALUE));
		assertEquals(p3.getManhattanDistanceSeparation(p1),BigInteger.valueOf(Long.MAX_VALUE));
		assertEquals(p3.getManhattanDistanceSeparation(p2),BigInteger.valueOf(Long.MAX_VALUE));
		assertEquals(p3.getManhattanDistanceSeparation(p3),BigInteger.valueOf(0L));
	}
	
	@Test
	public void hashCode_verification(){
		Position pos20x20y = new Position(new long[]{20L, 20L});
		pos19x20y = new Position(new long[]{20L, 20L});
		assertEquals(pos20x20y.hashCode(),pos19x20y.hashCode());
	}
	
	@Test
	public void getNextPositionInDirection(){
		Position p = new Position(1L,1L);
		assertEquals(p.getNextPositionInDirection(Direction.DOWN), new Position(1L, 2L));
		assertEquals(p.getNextPositionInDirection(Direction.UP), new Position(1L, 0L));
		assertEquals(p.getNextPositionInDirection(Direction.LEFT), new Position(0L, 1L));
		assertEquals(p.getNextPositionInDirection(Direction.RIGHT), new Position(2L, 1L));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getNextPositionInDirection_invalidDirection(){
		Position p = new Position(1L,1L);
		p.getNextPositionInDirection(null);
	}
}
