package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Dimension;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.TeleportGate;
import roborally.model.staticObject.Wall;

/**
 * A test class for teleport gate objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TeleportGateTest {

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
	public void construct1(){
		TeleportGate temp = new TeleportGate();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getWeight(),TeleportGate.STANDARD_TELEPORT_GATE_WEIGHT);
		assertEquals(temp.getTeleportRange(), TeleportGate.STANDARD_TELEPORT_RANGE);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct2(){
		TeleportGate temp = new TeleportGate(1);
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getWeight(),TeleportGate.STANDARD_TELEPORT_GATE_WEIGHT);
		assertEquals(temp.getTeleportRange(), 1);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct3(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(10L,10L);
		TeleportGate temp = new TeleportGate(b,pos,1);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),pos);
		assertEquals(temp.getWeight(),TeleportGate.STANDARD_TELEPORT_GATE_WEIGHT);
		assertEquals(temp.getTeleportRange(), 1);
		assertFalse(temp.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgument(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(10L,10L);
		@SuppressWarnings("unused")
		Wall w = new Wall(b,pos);
		@SuppressWarnings("unused")
		TeleportGate temp = new TeleportGate(b,pos,1);
	}
	
	@Test (expected = IllegalStateException.class)
	public void generateTeleportPosition_terminated(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(10L,10L);
		TeleportGate temp = new TeleportGate(b,pos,1);
		temp.terminate();
		temp.generateTeleportPosition();
	}
	
	@Test (expected = IllegalStateException.class)
	public void generateTeleportPosition_noPosition(){
		TeleportGate temp = new TeleportGate();
		temp.generateTeleportPosition();
	}
	
	@Test
	public void generateTeleportPositionI(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		for(int i=0; i<=10; i++){
			Position p = temp.generateTeleportPosition();
			assertTrue(p.getCoordinate(Dimension.HORIZONTAL) >= 0);
			assertTrue(p.getCoordinate(Dimension.HORIZONTAL) <= 2);
			assertTrue(p.getCoordinate(Dimension.VERTICAL) >= 0);
			assertTrue(p.getCoordinate(Dimension.VERTICAL) <= 2);
		}
	}
	
	@Test
	public void generateTeleportPositionII(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(9L,10L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		for(int i=0; i<=10; i++){
			Position p = temp.generateTeleportPosition();
			assertTrue(p.getCoordinate(Dimension.HORIZONTAL) >= 8);
			assertTrue(p.getCoordinate(Dimension.HORIZONTAL) <= 10);
			assertTrue(p.getCoordinate(Dimension.VERTICAL) >= 9);
			assertTrue(p.getCoordinate(Dimension.VERTICAL) <= 11);
		}
	}
	
	@Test
	public void getTeleportRange(){
		TeleportGate temp = new TeleportGate(1);
		assertEquals(temp.getTeleportRange(),1);
	}
	
	@Test
	public void setTeleportRange(){
		TeleportGate temp = new TeleportGate(1);
		assertEquals(temp.getTeleportRange(),1);
		temp.setTeleportRange(100);
		assertEquals(temp.getTeleportRange(),TeleportGate.MAX_TELEPORT_RANGE);
		temp.setTeleportRange(3);
		assertEquals(temp.getTeleportRange(),3);
		temp.setTeleportRange(0);
		assertEquals(temp.getTeleportRange(),1);
	}
	
	@Test
	public void isValidTeleportRange(){
		assertFalse(TeleportGate.isValidTeleportRange(-10));
		assertFalse(TeleportGate.isValidTeleportRange(-5));
		assertFalse(TeleportGate.isValidTeleportRange(-1));
		assertFalse(TeleportGate.isValidTeleportRange(0));
		assertTrue(TeleportGate.isValidTeleportRange(1));
		assertTrue(TeleportGate.isValidTeleportRange(5));
		assertTrue(TeleportGate.isValidTeleportRange(TeleportGate.MAX_TELEPORT_RANGE));
		assertFalse(TeleportGate.isValidTeleportRange(TeleportGate.MAX_TELEPORT_RANGE+1));
	}
	
	@Test
	public void hit(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(10L,10L);
		TeleportGate temp = new TeleportGate(b,pos,1);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),pos);
		assertEquals(temp.getWeight(),TeleportGate.STANDARD_TELEPORT_GATE_WEIGHT);
		assertEquals(temp.getTeleportRange(), 1);
		assertFalse(temp.isTerminated());
		temp.hit();
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),pos);
		assertEquals(temp.getWeight(),TeleportGate.STANDARD_TELEPORT_GATE_WEIGHT);
		assertEquals(temp.getTeleportRange(), 1);
		assertFalse(temp.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		temp.terminate();
		temp.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_pickedUp(){
		TeleportGate temp = new TeleportGate(1);
		InventoryUser r = new Robot();
		r.getInventory().addInventoryItem(temp);
		temp.hit();
	}
	
	@Test
	public void hit_noBoard(){
		TeleportGate temp = new TeleportGate(1);
		temp.hit();
	}
	
	@Test
	public void teleport(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, TeleportGate.MAX_TELEPORT_RANGE);
		Robot r = new Robot(b, pos);
		temp.teleport(r);
		assertFalse(temp.isTerminated());
		// according to statistics there is a possibility that
		// this assert fails
		// P(fail) = (1/(12*12))^(21*21)
		assertFalse(r.getPosition().equals(pos));
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		
		assertFalse(temp.isTerminated());
		assertFalse(r.isTerminated());
	}
	
	@Test
	public void teleport_nullBoard(){
		TeleportGate temp = new TeleportGate(1);
		Robot r = new Robot();
		temp.teleport(r);
		assertTrue(r.getPosition() == null);
	}
	
	@Test (expected = NullPointerException.class)
	public void teleport_nullBoardModel(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		temp.teleport(null);
	}
	
	@Test (expected = IllegalStateException.class)
	public void teleport_terminatedBoardModel(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		Robot r = new Robot();
		r.terminate();
		temp.teleport(r);
	}
	
	@Test (expected = IllegalStateException.class)
	public void teleport_terminatedTeleportGate(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, 1);
		Robot r = new Robot();
		temp.terminate();
		temp.teleport(r);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void teleport_differentBoard(){
		Board b1 = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b1, pos, 1);
		Board b2 = new Board(20L, 20L);
		Robot r = new Robot(b2, pos);
		temp.teleport(r);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void teleport_differentPosition(){
		Board b1 = new Board(20L, 20L);
		Position pos1 = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b1, pos1, 1);
		Position pos2 = new Position(2L,1L);
		Robot r = new Robot(b1, pos2);
		temp.teleport(r);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void teleport_differentBoard_differentPosition(){
		Board b1 = new Board(20L, 20L);
		Position pos1 = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b1, pos1, 1);
		Board b2 = new Board(20L, 20L);
		Position pos2 = new Position(2L,1L);
		Robot r = new Robot(b2, pos2);
		temp.teleport(r);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void teleport_resultsInTermination(){
		Board b = new Board(20L,20L);
		Wall w1 = new Wall(b, new Position(0L,0L));
		Wall w2 = new Wall(b, new Position(0L,1L));
		Wall w3 = new Wall(b, new Position(0L,2L));
		Wall w4 = new Wall(b, new Position(1L,0L));
		Wall w5 = new Wall(b, new Position(1L,2L));
		Wall w6 = new Wall(b, new Position(2L,0L));
		Wall w7 = new Wall(b, new Position(2L,1L));
		Wall w8 = new Wall(b, new Position(2L,2L));
		TeleportGate g = new TeleportGate(b, new Position(1L,1L),1);
		Robot r = new Robot(b, new Position(1L,1L));
		g.teleport(r);
		assertFalse(r.isTerminated());
		assertTrue(g.isTerminated());
		assertEquals(r.getPosition(),new Position(1L,1L));
	}
	
	@Test
	public void use(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, TeleportGate.MAX_TELEPORT_RANGE);
		InventoryUser r = new Robot(b, pos);
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		// according to statistics there is a possibility that
		// this assert fails
		assertFalse(r.getPosition().equals(pos));
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		
		assertTrue(temp.isTerminated());
		assertFalse(r.isTerminated());
	}
	
	@Test
	public void use_throughInventory(){
		Board b = new Board(20L, 20L);
		Position pos = new Position(1L,1L);
		TeleportGate temp = new TeleportGate(b, pos, TeleportGate.MAX_TELEPORT_RANGE);
		InventoryUser r = new Robot(b, pos);
		r.getInventory().addInventoryItem(temp);
		r.getInventory().useInventoryItem(temp);
		// according to statistics there is a possibility that
		// this assert fails
		assertFalse(r.getPosition().equals(pos));
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.HORIZONTAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)>=0);
		assertTrue(r.getPosition().getCoordinate(Dimension.VERTICAL)<=TeleportGate.MAX_TELEPORT_RANGE+1);
		
		assertTrue(temp.isTerminated());
		assertFalse(r.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(temp));
	}
	
	@Test
	public void use_noBoard_nothingHappens(){
		TeleportGate temp = new TeleportGate(TeleportGate.MAX_TELEPORT_RANGE);
		InventoryUser r = new Robot();
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertTrue(r.getPosition() == null);
		assertFalse(temp.isTerminated());
		assertFalse(r.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void use_invalidUser(){
		InventoryUser r = new Robot();
		TeleportGate g = new TeleportGate(TeleportGate.MAX_TELEPORT_RANGE);
		g.use(r);
	}
}
