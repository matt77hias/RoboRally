package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.*;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.model.inventory.item.*;
import roborally.model.inventory.Collector;
import roborally.model.inventory.InventoryUser;
import roborally.model.staticObject.Wall;
import roborally.model.weight.*;

/**
 * A test class for battery objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BatteryTest {
	Board board;
	Position position;
	Accu a;
	Weight w;
	Battery bat;

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
	public void construct1(){
		Battery temp = new Battery();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getAccu().getAmountOfEnergy().subtract(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getAccu().getEnergyCapacityLimit().subtract(Battery.STANDARD_BATTERY_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getWeight(),Battery.STANDARD_BATTERY_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct2(){
		Accu acc = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Battery temp = new Battery(acc);
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getAccu(),acc);
		assertEquals(temp.getWeight(),Battery.STANDARD_BATTERY_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct3(){
		Battery temp = new Battery(board, position);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),position);
		assertEquals(temp.getAccu().getAmountOfEnergy().subtract(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getAccu().getEnergyCapacityLimit().subtract(Battery.STANDARD_BATTERY_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getWeight(),Battery.STANDARD_BATTERY_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct4(){
		Accu acc = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Battery temp = new Battery(board, position, acc);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),position);
		assertEquals(temp.getAccu(),acc);
		assertEquals(temp.getWeight(),Battery.STANDARD_BATTERY_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct5(){
		assertEquals(bat.getBoard(),board);
		assertEquals(bat.getPosition(),position);
		assertEquals(bat.getAccu(),a);
		assertEquals(bat.getWeight(),w);
		assertTrue(InventoryModel.isValidWeight(bat.getWeight()));
		assertFalse(bat.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void contruct_illegalArgumentException(){
		@SuppressWarnings("unused")
		Wall w = new Wall(board,new Position(7,9));
		@SuppressWarnings("unused")
		Battery bat = new Battery(board,new Position(7,9));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void use_invalidUser(){
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		InventoryUser c = new Robot(board,position,ac,null);
		assertFalse(bat.canHaveAsUser(c));
		bat.use(c);
	}
	
	@Test
	public void use_full_validUser(){
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		InventoryUser c = new Robot(board,position,ac,null);
		
		c.getInventory().addInventoryItem(bat);
		Accu btemp = bat.getAccu();
		bat.use(c);
		
		Robot r = (Robot) c;
		assertEquals(r.getAccu().getAmountOfEnergy().getEnergyAmount(), Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY.add(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY).getEnergyAmount(),0.01D);
		assertEquals(btemp.getAmountOfEnergy().getEnergyAmount(),0.0D,0.01D);
		assertTrue(btemp.isTerminated());
		assertTrue(bat.isTerminated());
		assertFalse(c.isTerminated());
	}

	@Test
	public void use_partial_validUser(){
		Accu acc = new Accu(Battery.STANDARD_BATTERY_ENERGY_CAPACITY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Battery batt = new Battery(board,position,acc, null);
		
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		InventoryUser c = new Robot(board,position,ac,null);
		
		c.getInventory().addInventoryItem(batt);
		batt.use(c);
		Robot r = (Robot) c;
		assertEquals(r.getAccu().getAmountOfEnergy().getEnergyAmount(), Battery.STANDARD_BATTERY_ENERGY_CAPACITY.getEnergyAmount() ,0.01D);
		assertEquals(acc.getAmountOfEnergy().getEnergyAmount(),Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY.getEnergyAmount(),0.01D);
		assertFalse(acc.isTerminated());
		assertFalse(batt.isTerminated());
		assertFalse(c.isTerminated());
	}
	
	public void hit(){
		Energy am1 = new Energy(500);
		Energy cap1 = new Energy(1100);
		Battery b1 = new Battery(new Accu(am1, cap1));
		b1.hit();
		assertEquals(b1.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(b1.getAccu().getEnergyCapacityLimit().compareTo(cap1),0);
		
		Energy am2 = new Energy(500);
		Energy cap2 = new Energy(1000);
		Battery b2 = new Battery(new Accu(am2, cap2));
		b2.hit();
		assertEquals(b2.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(b2.getAccu().getEnergyCapacityLimit().compareTo(cap2),0);
		
		Energy am3 = new Energy(500);
		Energy cap3 = new Energy(900);
		Battery b3 = new Battery(new Accu(am3, cap3));
		b3.hit();
		assertEquals(b3.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(b3.getAccu().getEnergyCapacityLimit().compareTo(cap3),0);
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		Battery im = new Battery();
		im.terminate();
		im.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_pickedUp(){
		Collector r = new Robot();
		Battery im = new Battery();
		r.getInventory().addInventoryItem(im);
		im.hit();
	}
}