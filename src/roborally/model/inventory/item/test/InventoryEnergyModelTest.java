package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.*;
import roborally.model.energy.Accu;
import roborally.model.inventory.item.*;
import roborally.model.staticObject.Wall;
import roborally.model.weight.*;

/**
 * A test class for inventory energy model objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InventoryEnergyModelTest {

	Board board;
	Position position;
	Accu a;
	Weight w;
	InventoryEnergyModel bat;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		board = new Board(10,11);
		position = new Position(7,8);
		a = new Accu();
		w = new Weight(1000D,WeightUnit.G);
		bat = new Battery(board,position,a,w);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct(){
		assertEquals(bat.getBoard(),board);
		assertEquals(bat.getPosition(),position);
		assertEquals(bat.getAccu(),a);
		assertEquals(bat.getWeight(),w);
		assertTrue(InventoryModel.isValidWeight(bat.getWeight()));
		assertFalse(bat.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgumentException(){
		@SuppressWarnings("unused")
		Wall w = new Wall(board,new Position(7,9));
		@SuppressWarnings("unused")
		InventoryEnergyModel bat = new Battery(board,new Position(7,9));
	}
	
	@Test
	public void terminate(){
		assertFalse(bat.isTerminated());
		assertFalse(bat.isPickedUp());
		bat.terminate();
		assertTrue(bat.isTerminated());
		assertTrue(a.isTerminated());
		assertEquals(bat.getAccu(), null);
		
		Accu acc = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Battery b= new Battery(board, position, acc);
		assertFalse(b.isTerminated());
		assertFalse(b.isPickedUp());
		b.pickUp();
		assertFalse(b.isTerminated());
		assertTrue(b.isPickedUp());
		b.terminate();
		assertTrue(b.isPickedUp());
		assertFalse(b.isTerminated());
		assertFalse(acc.isTerminated());
		assertEquals(b.getAccu(), acc);
		b.drop(board, position);
		assertFalse(b.isPickedUp());
		assertFalse(b.isTerminated());
		assertFalse(acc.isTerminated());
		assertEquals(b.getAccu(), acc);
		b.terminate();
		assertFalse(b.isPickedUp());
		assertTrue(b.isTerminated());
		assertTrue(acc.isTerminated());
		assertEquals(b.getAccu(), null);
	}
	
	@Test
	public void canHaveAsAccu(){
		assertFalse(bat.isTerminated());
		assertTrue(bat.canHaveAsAccu(a));
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		assertTrue(bat.canHaveAsAccu(ac));
		assertFalse(bat.canHaveAsAccu(null));
		Accu accu = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		assertFalse(accu.isTerminated());
		assertTrue(bat.canHaveAsAccu(accu));
		accu.terminate();
		assertTrue(accu.isTerminated());
		assertFalse(bat.canHaveAsAccu(accu));
		Accu acc = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		assertFalse(acc.isTerminated());
		assertFalse(acc.hasOwner());
		assertTrue(bat.canHaveAsAccu(acc));
		acc.own();
		assertTrue(acc.hasOwner());
		assertFalse(bat.canHaveAsAccu(acc));
		bat.terminate();
		assertFalse(bat.canHaveAsAccu(ac));
		assertTrue(bat.canHaveAsAccu(null));
	}
	
	@Test
	public void getAccu(){
		assertEquals(bat.getAccu(),a);
	}
	
	@Test
	public void isUseFul(){
		assertFalse(bat.isTerminated());
		assertTrue(bat.isUseful());
		bat.getAccu().dischargeAmountOfEnergy(bat.getAccu().getAmountOfEnergy());
		assertFalse(bat.isUseful());
		bat.terminate();
		assertTrue(bat.isTerminated());
		assertFalse(bat.isUseful());
	}
}
