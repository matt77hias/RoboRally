package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.inventory.Inventory;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.inventory.item.ItemBox;
import roborally.model.staticObject.Wall;
import roborally.model.weight.Weight;

/**
 * A test class for item box objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ItemBoxTest {

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
		ItemBox temp = new ItemBox();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getWeight(),ItemBox.STANDARD_ITEMBOX_WEIGHT);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
	}
	
	@Test
	public void construct2(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		ItemBox temp = new ItemBox(b,p);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),p);
		assertEquals(temp.getWeight(),ItemBox.STANDARD_ITEMBOX_WEIGHT);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
	}
	
	@Test
	public void construct3(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		Weight w = new Weight(500);
		ItemBox temp = new ItemBox(b,p,w);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),p);
		assertEquals(temp.getWeight(),w);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgument(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		@SuppressWarnings("unused")
		Wall w = new Wall(b,p);
		@SuppressWarnings("unused")
		ItemBox temp = new ItemBox(b,p);
	}
	
	@Test
	public void terminate_pickedUp(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		ItemBox temp = new ItemBox(b,p);
		temp.pickUp();
		assertFalse(temp.isTerminated());
		assertTrue(temp.isPickedUp());
		temp.terminate();
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void terminate(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		ItemBox temp = new ItemBox(b,p);
		Inventory i = temp.getInventory();
		assertFalse(temp.isTerminated());
		assertFalse(i.isTerminated());
		temp.terminate();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getWeight(),ItemBox.STANDARD_ITEMBOX_WEIGHT);
		assertTrue(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
		assertEquals(temp.getInventory(),null);
		assertTrue(i.isTerminated());
	}
	
	@Test
	public void isUseful(){
		ItemBox temp = new ItemBox();
		assertEquals(temp.getInventory().getNbOfInventoryItems(),0);
		assertFalse(temp.isUseful());
		InventoryModel b = new Battery();
		temp.getInventory().addInventoryItem(b);
		assertEquals(temp.getInventory().getNbOfInventoryItems(),1);
		assertTrue(temp.isUseful());
		temp.getInventory().removeInventoryItem(b);
		assertEquals(temp.getInventory().getNbOfInventoryItems(),0);
		assertFalse(temp.isUseful());
		b = new Battery();
		temp.getInventory().addInventoryItem(b);
		assertEquals(temp.getInventory().getNbOfInventoryItems(),1);
		assertTrue(temp.isUseful());
		temp.terminate();
		assertTrue(temp.isTerminated());
		assertFalse(temp.isUseful());
	}
	
	@Test
	public void hit(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		Weight w = new Weight(500);
		ItemBox temp = new ItemBox(b,p,w);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),p);
		assertEquals(temp.getWeight(),w);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
		temp.hit();
		assertTrue(temp.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_pickedUp(){
		ItemBox temp = new ItemBox();
		temp.pickUp();
		temp.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		ItemBox temp = new ItemBox();
		temp.terminate();
		temp.hit();
	}
	
	@Test
	public void use(){
		InventoryUser r = new Robot();
		ItemBox temp = new ItemBox();
		Accu a = new Accu(Robot.STANDARD_ROBOT_ENERGY_CAPACITY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY);
		InventoryModel b = new Battery(a);
		temp.getInventory().addInventoryItem(b);
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertFalse(r.isTerminated());
		assertFalse(temp.isTerminated());
		assertTrue(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(temp.getInventory().hasAsInventoryItem(b));
	}
	
	@Test
	public void use_throughInventory(){
		InventoryUser r = new Robot();
		ItemBox temp = new ItemBox();
		Accu a = new Accu(Robot.STANDARD_ROBOT_ENERGY_CAPACITY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY);
		InventoryModel b = new Battery(a);
		temp.getInventory().addInventoryItem(b);
		r.getInventory().addInventoryItem(temp);
		r.getInventory().useInventoryItem(temp);
		assertFalse(r.isTerminated());
		assertFalse(temp.isTerminated());
		assertTrue(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(temp.getInventory().hasAsInventoryItem(b));
	}
	
	@Test
	public void use_resultsInTermination(){
		InventoryUser r = new Robot();
		ItemBox temp = new ItemBox();
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertFalse(r.isTerminated());
		assertTrue(temp.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void use_invalidUser(){
		InventoryUser r = new Robot();
		ItemBox temp = new ItemBox();
		temp.use(r);
	}
}
