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
import roborally.model.energy.Energy;
import roborally.model.inventory.Collector;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.Bomb;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.inventory.item.RepairKit;
import roborally.model.inventory.item.SurpriseBox;
import roborally.model.inventory.item.TeleportGate;
import roborally.model.staticObject.Wall;
import roborally.model.weight.Weight;

/**
 * A test class for surprise box objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class SurpriseBoxTest {

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
		SurpriseBox temp = new SurpriseBox();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getWeight(),SurpriseBox.STANDARD_SURPRISEBOX_WEIGHT);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
		assertEquals(temp.getInventory().getNbOfInventoryItems(),1);
	}
	
	@Test
	public void construct2(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),p);
		assertEquals(temp.getWeight(),SurpriseBox.STANDARD_SURPRISEBOX_WEIGHT);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
		assertEquals(temp.getInventory().getNbOfInventoryItems(),1);
	}
	
	@Test
	public void construct3(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		Weight w = new Weight(500);
		SurpriseBox temp = new SurpriseBox(b,p,w);
		assertEquals(temp.getBoard(),b);
		assertEquals(temp.getPosition(),p);
		assertEquals(temp.getWeight(),w);
		assertFalse(temp.isTerminated());
		assertFalse(temp.isPickedUp());
		assertTrue(temp.hasValidInventory());
		assertEquals(temp.getInventory().getNbOfInventoryItems(),1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgument(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		@SuppressWarnings("unused")
		Wall w = new Wall(b,p);
		@SuppressWarnings("unused")
		SurpriseBox temp = new SurpriseBox(b,p);
	}
	
	
	
	@Test
	public void canBeHit(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		Weight w = new Weight(500);
		SurpriseBox temp = new SurpriseBox(b,p,w);
		assertTrue(temp.canBeHit());
		temp.pickUp();
		assertFalse(temp.canBeHit());
		
		temp = new SurpriseBox(b,p,w);
		assertTrue(temp.canBeHit());
		temp.terminate();
		assertFalse(temp.canBeHit());
		
		temp = new SurpriseBox();
		assertFalse(temp.canBeHit());
	}
	
	@Test
	public void hit(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		Robot r = new Robot(b,p,new Accu(new Energy(3000),new Energy(4000)),null);
		temp.hit();
		assertTrue(temp.isTerminated());
		assertTrue(r.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_notPossible_noBoard(){
		SurpriseBox temp = new SurpriseBox();
		temp.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_notPossible_pickedUp(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		temp.pickUp();
		temp.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_notPossible_terminate(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		temp.terminate();
		temp.hit();
	}
	
	@Test
	public void use_teleportGate(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		InventoryModel original = temp.getInventory().getInventoryItemAt(1);
		temp.getInventory().removeInventoryItem(original);
		original.terminate();
		InventoryModel im = new TeleportGate(b,p,TeleportGate.MAX_TELEPORT_RANGE);
		temp.getInventory().addInventoryItem(im);
		Robot r = new Robot(b,p,new Accu(new Energy(1000),new Energy(5000)),null);
		r.getAccu().setEnergyCapacityLimit(new Energy(4000));
		r.getInventory().addInventoryItem(temp);
		
		temp.use(r);
		assertFalse(temp.isUseful());
		
		assertFalse(r.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(temp.isTerminated());
		assertTrue(im.isTerminated());
		// according to statistics there is a nearly zero chance
		// that the robot is still positioned on its original position
		assertFalse(r.getPosition().equals(p));
	}
	
	@Test
	public void use_bomb(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		InventoryModel original = temp.getInventory().getInventoryItemAt(1);
		temp.getInventory().removeInventoryItem(original);
		original.terminate();
		InventoryModel im = new Bomb(b,p,1);
		temp.getInventory().addInventoryItem(im);
		Robot r = new Robot(b,p,new Accu(new Energy(1000),new Energy(5000)),null);
		r.getAccu().setEnergyCapacityLimit(new Energy(4000));
		r.getInventory().addInventoryItem(temp);
				
		temp.use(r);
		assertTrue(r.isTerminated());
		assertTrue(temp.isTerminated());
		assertTrue(im.isTerminated());
	}
	
	@Test
	public void use_repairKit(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		InventoryModel original = temp.getInventory().getInventoryItemAt(1);
		temp.getInventory().removeInventoryItem(original);
		original.terminate();
		InventoryModel im = new RepairKit(b,p);
		temp.getInventory().addInventoryItem(im);
		Robot r = new Robot(b,p,new Accu(new Energy(1000),new Energy(5000)),null);
		r.getAccu().setEnergyCapacityLimit(new Energy(4000));
		r.getInventory().addInventoryItem(temp);
				
		temp.use(r);
		assertFalse(r.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(r.getInventory().hasAsInventoryItem(im));
		assertTrue(temp.isTerminated());
		assertFalse(im.isTerminated());
		
		assertEquals(r.getAccu().getAmountOfEnergy(),new Energy(1000));
		assertEquals(r.getAccu().getEnergyCapacityLimit(),new Energy(4000));
	}
	
	@Test
	public void use_battery(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		InventoryModel original = temp.getInventory().getInventoryItemAt(1);
		temp.getInventory().removeInventoryItem(original);
		original.terminate();
		InventoryModel im = new Battery(b,p);
		temp.getInventory().addInventoryItem(im);
		Robot r = new Robot(b,p,new Accu(new Energy(1000),new Energy(5000)),null);
		r.getAccu().setEnergyCapacityLimit(new Energy(4000));
		r.getInventory().addInventoryItem(temp);
				
		temp.use(r);
		assertFalse(r.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(r.getInventory().hasAsInventoryItem(im));
		assertTrue(temp.isTerminated());
		assertFalse(im.isTerminated());
		
		assertEquals(r.getAccu().getAmountOfEnergy(),new Energy(1000));
		assertEquals(r.getAccu().getEnergyCapacityLimit(),new Energy(4000));
	}
	
	@Test
	public void use_surpriseBox(){
		Board b = new Board(20L,20L);
		Position p = new Position(4L,5L);
		SurpriseBox temp = new SurpriseBox(b,p);
		InventoryModel original = temp.getInventory().getInventoryItemAt(1);
		temp.getInventory().removeInventoryItem(original);
		original.terminate();
		InventoryModel im = new SurpriseBox(b,p);
		InventoryModel original2 = ((Collector) im).getInventory().getInventoryItemAt(1);
		((Collector) im).getInventory().removeInventoryItem(original2);
		original2.terminate();
		InventoryModel imm = new Battery(b,p);
		((Collector) im).getInventory().addInventoryItem(imm);
		temp.getInventory().addInventoryItem(im);
		Robot r = new Robot(b,p,new Accu(new Energy(1000),new Energy(5000)),null);
		r.getAccu().setEnergyCapacityLimit(new Energy(4000));
		r.getInventory().addInventoryItem(temp);
		
		temp.use(r);
		assertFalse(r.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(temp));
		assertTrue(r.getInventory().hasAsInventoryItem(im));
		assertTrue(temp.isTerminated());
		assertFalse(im.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void use_invalidUser(){
		InventoryUser r = new Robot();
		SurpriseBox temp = new SurpriseBox();
		temp.use(r);
	}
}
