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
import roborally.model.inventory.item.InventoryModel;
import roborally.model.inventory.item.RepairKit;
import roborally.model.staticObject.Wall;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;

/**
 * A test class for repair kit objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class RepairKitTest {

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
		RepairKit temp = new RepairKit();
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getAccu().getAmountOfEnergy().subtract(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getAccu().getEnergyCapacityLimit().subtract(RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getWeight(),RepairKit.STANDARD_REPAIRKIT_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct2(){
		Accu acc = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
		RepairKit temp = new RepairKit(acc);
		assertEquals(temp.getBoard(),null);
		assertEquals(temp.getPosition(),null);
		assertEquals(temp.getAccu(),acc);
		assertEquals(temp.getWeight(),RepairKit.STANDARD_REPAIRKIT_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct3(){
		Board board = new Board(10,11);
		Position position = new Position(7,8);
		RepairKit temp = new RepairKit(board, position);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),position);
		assertEquals(temp.getAccu().getAmountOfEnergy().subtract(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getAccu().getEnergyCapacityLimit().subtract(RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getWeight(),RepairKit.STANDARD_REPAIRKIT_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct4(){
		Board board = new Board(10,11);
		Position position = new Position(7,8);
		Accu acc = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
		RepairKit temp = new RepairKit(board, position, acc);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),position);
		assertEquals(temp.getAccu(),acc);
		assertEquals(temp.getWeight(),RepairKit.STANDARD_REPAIRKIT_WEIGHT);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct5(){
		Board board = new Board(10,11);
		Position position = new Position(7,8);
		Accu a = new Accu();
		Weight w = new Weight(1000D,WeightUnit.G);
		RepairKit temp = new RepairKit(board,position,a,w);
		assertEquals(temp.getBoard(),board);
		assertEquals(temp.getPosition(),position);
		assertEquals(temp.getAccu(),a);
		assertEquals(temp.getWeight(),w);
		assertTrue(InventoryModel.isValidWeight(temp.getWeight()));
		assertFalse(temp.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgumentException(){
		Board board = new Board(10,11);
		Position position = new Position(7,8);
		@SuppressWarnings("unused")
		Wall w = new Wall(board, position);
		@SuppressWarnings("unused")
		RepairKit temp = new RepairKit(board,position);
	}
	
	@Test
	public void canHaveAsAccu(){
		RepairKit kit = new RepairKit();
		
		Accu acc = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
		assertTrue(kit.canHaveAsAccu(acc));
		acc.terminate();
		assertFalse(kit.canHaveAsAccu(acc));
		
		Accu ac = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
		assertTrue(kit.canHaveAsAccu(ac));
		ac.own();
		assertFalse(kit.canHaveAsAccu(ac));
		
		assertFalse(kit.canHaveAsAccu(null));
		
		Accu aover1 = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, new Energy(Long.MAX_VALUE));
		assertTrue(kit.canHaveAsAccu(aover1));
		
		Accu aover2 = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, new Energy(Double.MAX_VALUE));
		assertTrue(kit.canHaveAsAccu(aover2));
		
		Accu accu = kit.getAccu();
		assertTrue(kit.canHaveAsAccu(kit.getAccu()));
		kit.terminate();
		assertTrue(kit.canHaveAsAccu(kit.getAccu()));
		assertTrue(kit.canHaveAsAccu(null));
		assertFalse(kit.canHaveAsAccu(accu));
	}
	
	public void hit(){
		Energy am1 = new Energy(500);
		Energy cap1 = new Energy(1100);
		RepairKit k1 = new RepairKit(new Accu(am1, cap1));
		k1.hit();
		assertEquals(k1.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(k1.getAccu().getEnergyCapacityLimit().compareTo(cap1),0);
		
		Energy am2 = new Energy(500);
		Energy cap2 = new Energy(1000);
		RepairKit k2 = new RepairKit(new Accu(am2, cap2));
		k2.hit();
		assertEquals(k2.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(k2.getAccu().getEnergyCapacityLimit().compareTo(cap2),0);
		
		Energy am3 = new Energy(500);
		Energy cap3 = new Energy(900);
		RepairKit k3 = new RepairKit(new Accu(am3, cap3));
		k3.hit();
		assertEquals(k3.getAccu().getAmountOfEnergy().compareTo(new Energy(1000)),0);
		assertEquals(k3.getAccu().getEnergyCapacityLimit().compareTo(cap3),0);
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		RepairKit im = new RepairKit();
		im.terminate();
		im.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_pickedUp(){
		Collector r = new Robot();
		RepairKit im = new RepairKit();
		r.getInventory().addInventoryItem(im);
		im.hit();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void use_invalidUser(){
		Board board = new Board(10,11);
		Position position = new Position(7,8);
		Accu ac = new Accu(RepairKit.STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
		InventoryUser c = new Robot(board,position,ac,null);
		RepairKit kit = new RepairKit();
		assertFalse(kit.canHaveAsUser(c));
		kit.use(c);
	}
	
	@Test
	public void use_full_validUser_throughInventory(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		((Robot) r).getAccu().setEnergyCapacityLimit(new Energy(4000));
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(((Robot) r).getAccu().getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy rcap = ((Robot) r).getAccu().getEnergyCapacityLimit();
		
		Energy am = new Energy(1000);
		Accu ak = new Accu(am, new Energy(8000));
		RepairKit kit = new RepairKit(ak);
		
		r.getInventory().addInventoryItem(kit);
		r.getInventory().useInventoryItem(kit);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), rcap.add(am.multiply(RepairKit.STANDARD_USE_EFFICIENCY_FACTOR)));
		assertFalse(r.isTerminated());
		assertTrue(ak.isTerminated());
		assertTrue(kit.isTerminated());
		assertFalse(r.getInventory().hasAsInventoryItem(kit));
	}

	@Test
	public void use_partial_validUser_throughInventory(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		((Robot) r).getAccu().setEnergyCapacityLimit(new Energy(4000));
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(((Robot) r).getAccu().getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy diff = ((Robot) r).getAccu().getOriginalEnergyCapacityLimit().subtract(((Robot) r).getAccu().getEnergyCapacityLimit());
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
		RepairKit kit = new RepairKit(ak);
				
		r.getInventory().addInventoryItem(kit);
		r.getInventory().useInventoryItem(kit);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), ((Robot) r).getAccu().getOriginalEnergyCapacityLimit());
		assertFalse(r.isTerminated());
		assertFalse(kit.isTerminated());
		assertFalse(ak.isTerminated());
		assertTrue(r.getInventory().hasAsInventoryItem(kit));
		assertEquals(kit.getAccu().getEnergyCapacityLimit(), kit.getAccu().getOriginalEnergyCapacityLimit());
		assertEquals(kit.getAccu().getAmountOfEnergy(), am.subtract(diff.multiply(1/RepairKit.STANDARD_USE_EFFICIENCY_FACTOR)));
	}
	
	@Test
	public void use_nothing_validUser_throughInventory(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		Energy rcap = new Energy(10000);
		((Robot) r).getAccu().setEnergyCapacityLimit(rcap);
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
		RepairKit kit = new RepairKit(ak);
				
		r.getInventory().addInventoryItem(kit);
		r.getInventory().useInventoryItem(kit);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), rcap);
		assertFalse(r.isTerminated());
		assertFalse(kit.isTerminated());
		assertFalse(ak.isTerminated());
		assertTrue(r.getInventory().hasAsInventoryItem(kit));
		assertEquals(kit.getAccu().getEnergyCapacityLimit(), kit.getAccu().getOriginalEnergyCapacityLimit());
		assertEquals(kit.getAccu().getAmountOfEnergy(), am);
	}
	
	@Test
	public void use_full_validUser(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		((Robot) r).getAccu().setEnergyCapacityLimit(new Energy(4000));
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(((Robot) r).getAccu().getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy rcap = ((Robot) r).getAccu().getEnergyCapacityLimit();
		
		Energy am = new Energy(1000);
		Accu ak = new Accu(am, new Energy(8000));
		RepairKit kit = new RepairKit(ak);
		
		r.getInventory().addInventoryItem(kit);
		kit.use(r);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), rcap.add(am.multiply(RepairKit.STANDARD_USE_EFFICIENCY_FACTOR)));
		assertFalse(r.isTerminated());
		assertTrue(ak.isTerminated());
		assertTrue(kit.isTerminated());
	}

	@Test
	public void use_partial_validUser(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		((Robot) r).getAccu().setEnergyCapacityLimit(new Energy(4000));
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(((Robot) r).getAccu().getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy diff = ((Robot) r).getAccu().getOriginalEnergyCapacityLimit().subtract(((Robot) r).getAccu().getEnergyCapacityLimit());
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
		RepairKit kit = new RepairKit(ak);
				
		r.getInventory().addInventoryItem(kit);
		kit.use(r);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), ((Robot) r).getAccu().getOriginalEnergyCapacityLimit());
		assertFalse(r.isTerminated());
		assertFalse(kit.isTerminated());
		assertFalse(ak.isTerminated());
		assertEquals(kit.getAccu().getEnergyCapacityLimit(), kit.getAccu().getOriginalEnergyCapacityLimit());
		assertEquals(kit.getAccu().getAmountOfEnergy(), am.subtract(diff.multiply(1/RepairKit.STANDARD_USE_EFFICIENCY_FACTOR)));
	}
	
	@Test
	public void use_nothing_validUser(){
		InventoryUser r = new Robot(new Accu(new Energy(1000), new Energy(8000)));
		Energy rcap = new Energy(10000);
		((Robot) r).getAccu().setEnergyCapacityLimit(rcap);
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
		RepairKit kit = new RepairKit(ak);
				
		r.getInventory().addInventoryItem(kit);
		kit.use(r);
		
		assertEquals(((Robot) r).getAccu().getEnergyCapacityLimit(), rcap);
		assertFalse(r.isTerminated());
		assertFalse(kit.isTerminated());
		assertFalse(ak.isTerminated());
		assertEquals(kit.getAccu().getEnergyCapacityLimit(), kit.getAccu().getOriginalEnergyCapacityLimit());
		assertEquals(kit.getAccu().getAmountOfEnergy(), am);
	}
}
