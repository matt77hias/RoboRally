package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.*;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.*;
import roborally.model.inventory.Collector;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.*;
import roborally.model.staticObject.Wall;
import roborally.model.weight.*;

/**
 * A test class for inventory model objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InventoryModelTest {

	Position p;
	Board b;
	InventoryModel im10x10y500E5000EL2W;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(20, 20);
		p = new Position(10, 10);
		im10x10y500E5000EL2W = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct(){
		Weight w0 = new Weight(-100D,WeightUnit.KG);
		Weight w1 = new Weight(-100D,WeightUnit.G);
		Weight w2 = new Weight(100D,WeightUnit.G);
		Weight w3 = new Weight(100D,WeightUnit.KG);
		Weight w4 = new Weight(Integer.MAX_VALUE+100D,WeightUnit.G);
		Weight w5 = InventoryModel.MAX_WEIGHT;
		Weight w6 = null;
		
		Battery b0 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w0);
		Battery b1 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w1);
		Battery b2 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w2);
		Battery b3 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w3);
		Battery b4 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w4);
		Battery b5 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w5);
		Battery b6 = new Battery(b, p, new Accu(new Energy(500), new Energy(5000)), w6);
		
		assertFalse(b0.isPickedUp());
		assertFalse(b1.isPickedUp());
		assertFalse(b2.isPickedUp());
		assertFalse(b3.isPickedUp());
		assertFalse(b4.isPickedUp());
		assertFalse(b5.isPickedUp());
		assertFalse(b6.isPickedUp());
		
		assertFalse(b0.isTerminated());
		assertFalse(b1.isTerminated());
		assertFalse(b2.isTerminated());
		assertFalse(b3.isTerminated());
		assertFalse(b4.isTerminated());
		assertFalse(b5.isTerminated());
		assertFalse(b6.isTerminated());
		
		assertEquals(b0.getWeight().subtract(new Weight(100D,WeightUnit.KG)).getWeightAmount(),0.0D,0.01D);
		assertEquals(b1.getWeight().subtract(new Weight(100D,WeightUnit.G)).getWeightAmount(),0.0D,0.01D);
		assertEquals(b2.getWeight().subtract(w2).getWeightAmount(),0.0D,0.01D);
		assertEquals(b3.getWeight().subtract(w3).getWeightAmount(),0.0D,0.01D);
		assertEquals(b4.getWeight().subtract(InventoryModel.MAX_WEIGHT).getWeightAmount(),0.0D,0.01D);
		assertEquals(b5.getWeight().subtract(w5).getWeightAmount(),0.0D,0.01D);
		assertEquals(b6.getWeight().subtract(new Weight(0.0, WeightUnit.G)).getWeightAmount(),0.0D,0.01D);
	}
	
	@After
	public void isValidWeight(){
		Weight w0 = new Weight(-100D,WeightUnit.KG);
		Weight w1 = new Weight(-100D,WeightUnit.G);
		Weight w2 = new Weight(100D,WeightUnit.G);
		Weight w3 = new Weight(100D,WeightUnit.KG);
		Weight w4 = new Weight(Integer.MAX_VALUE+100D,WeightUnit.G);
		Weight w5 = InventoryModel.MAX_WEIGHT;
		Weight w6 = null;
		
		assertFalse(InventoryModel.isValidWeight(w0));
		assertFalse(InventoryModel.isValidWeight(w1));
		assertTrue(InventoryModel.isValidWeight(w2));
		assertTrue(InventoryModel.isValidWeight(w3));
		assertFalse(InventoryModel.isValidWeight(w4));
		assertTrue(InventoryModel.isValidWeight(w5));
		assertFalse(InventoryModel.isValidWeight(w6));
	}
	
	@Test
	public void setWeight_TotalPropgrammingCheck(){
		Weight w00 = new Weight(Integer.MIN_VALUE,WeightUnit.G);
		Weight w0 = new Weight(-Integer.MAX_VALUE,WeightUnit.G);
		Weight w1 = null;
		Weight w2 = new Weight(-1,WeightUnit.G);
		Weight w3 = new Weight(-1,WeightUnit.KG);
		Weight w4 = new Weight(0,WeightUnit.G);
		Weight w5 = new Weight(1,WeightUnit.G);
		Weight w6 = new Weight(1,WeightUnit.KG);
		Weight w7 = new Weight(Integer.MAX_VALUE,WeightUnit.G);
		Weight w8 = new Weight(Integer.MAX_VALUE,WeightUnit.KG);
		
		InventoryModel im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w00);
		assertEquals(im.getWeight(),w7);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w0);
		assertEquals(im.getWeight(),w7);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w1);
		assertEquals(im.getWeight(),w4);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w2);
		assertEquals(im.getWeight(),w5);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w3);
		assertEquals(im.getWeight(),w6);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w4);
		assertEquals(im.getWeight(),w4);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w5);
		assertEquals(im.getWeight(),w5);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w6);
		assertEquals(im.getWeight(),w6);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w7);
		assertEquals(im.getWeight(),w7);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
		im = new Battery(null, null, new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY), w8);
		assertEquals(im.getWeight(),w7);
		assertTrue(InventoryModel.isValidWeight(im.getWeight()));
	}

	@Test
	public void getWeight() {
		assertEquals(im10x10y500E5000EL2W.getWeight().subtract(Battery.STANDARD_BATTERY_WEIGHT).getWeightAmount(),0.0D,0.01D);
	}
	
	@Test
	public void isPickedUp(){
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
	}
	
	@Test
	public void pickUp(){
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		assertEquals(im10x10y500E5000EL2W.getBoard(),b);
		assertEquals(im10x10y500E5000EL2W.getPosition(),p);
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
		assertEquals(im10x10y500E5000EL2W.getBoard(),null);
		assertEquals(im10x10y500E5000EL2W.getPosition(),null);
	}
	
	@Test
	public void pickUp_terminated(){
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.terminate();
		im10x10y500E5000EL2W.pickUp();
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
	}
	
	@Test
	public void drop_allNullArguments(){
		assertFalse(im10x10y500E5000EL2W.isTerminated());
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.drop(null,null);
		assertTrue(im10x10y500E5000EL2W.isTerminated());
	}
	
	@Test
	public void drop_boardNullArgument(){
		assertFalse(im10x10y500E5000EL2W.isTerminated());
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.drop(null,p);
		assertTrue(im10x10y500E5000EL2W.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void drop_positionNullArgumentRejected(){
		assertFalse(im10x10y500E5000EL2W.isTerminated());
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.drop(b,null);
		assertTrue(im10x10y500E5000EL2W.isTerminated());
	}

	@Test
	public void drop_stillUseful(){
		im10x10y500E5000EL2W.pickUp();
		assertTrue(im10x10y500E5000EL2W.isPickedUp());
		im10x10y500E5000EL2W.drop(b, new Position(10, 10));
		assertFalse(im10x10y500E5000EL2W.isPickedUp());
		assertTrue(im10x10y500E5000EL2W.getBoard() == b);
		assertTrue(im10x10y500E5000EL2W.getPosition().equals(new Position(10, 10)));
	}

	@Test
	public void drop_notUseful(){
		assertFalse(im10x10y500E5000EL2W.isTerminated());
		im10x10y500E5000EL2W.pickUp();
		((Battery)im10x10y500E5000EL2W).getAccu().dischargeAmountOfEnergy(((Battery)im10x10y500E5000EL2W).getAccu().getAmountOfEnergy());
		im10x10y500E5000EL2W.drop(b, new Position(10, 10));
		assertTrue(im10x10y500E5000EL2W.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void drop_notPickedUp_otherPosition(){
		Board b = new Board(20L,20L);
		Position p = new Position(2L,3L);
		InventoryModel im = new Battery(b,p);
		im.drop(b, new Position(3L,2L));
	}
	
	@Test (expected = IllegalStateException.class)
	public void drop_notPickedUp_otherBoard(){
		Board b = new Board(20L,20L);
		Position p = new Position(2L,3L);
		InventoryModel im = new Battery(b,p);
		im.drop(new Board(20L,20L), p);
	}
	
	@Test (expected = IllegalStateException.class)
	public void drop_notPickedUp_otherBoard_otherPosition(){
		Board b = new Board(20L,20L);
		Position p = new Position(2L,3L);
		InventoryModel im = new Battery(b,p);
		im.drop(new Board(20L,20L), new Position(3L,2L));
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_pickedUp(){
		Collector r = new Robot();
		InventoryModel im = new Battery();
		r.getInventory().addInventoryItem(im);
		im.hit();
	}
	
	@Test
	public void canSharePositionWith(){
		InventoryEnergyModel positioned = new Battery();
		
		BoardModel r1 = new Robot();
		BoardModel w1 = new Wall();
		BoardModel b1 = new Battery();
		InventoryModel b2 = new Battery();
		Robot r2 = new Robot();
		Wall w2 = new Wall();
		Battery b3 = new Battery();
		
		assertTrue(positioned.canSharePositionWith(r1));
		assertTrue(positioned.canSharePositionWith(r2));
		assertFalse(positioned.canSharePositionWith(w1));
		assertFalse(positioned.canSharePositionWith(w2));
		assertTrue(positioned.canSharePositionWith(b1));
		assertTrue(positioned.canSharePositionWith(b2));
		assertTrue(positioned.canSharePositionWith(b3));
		assertFalse(positioned.canSharePositionWith(null));
	}
	
	@Test
	public void canHaveAsUser(){
		InventoryModel bat = new Battery();
		assertFalse(bat.isPickedUp());
		InventoryUser c = new Robot();
		assertFalse(bat.canHaveAsUser(c));
		c.getInventory().addInventoryItem(bat);
		assertTrue(bat.isPickedUp());
		assertTrue(bat.canHaveAsUser(c));
	}
	
	@Test
	public void canHaveAsUser_nullCollector(){
		InventoryModel bat = new Battery();
		assertFalse(bat.canHaveAsUser(null));
	}
	
	@Test
	public void isUseFul(){
		assertTrue(im10x10y500E5000EL2W.isUseful());
		im10x10y500E5000EL2W.terminate();
		assertFalse(im10x10y500E5000EL2W.isUseful());
	}
}
