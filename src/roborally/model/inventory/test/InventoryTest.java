package roborally.model.inventory.test;

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
import roborally.model.energy.EnergyModel;
import roborally.model.energy.EnergyUnit;
import roborally.model.inventory.Inventory;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.Bomb;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.inventory.item.ItemBox;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;

/**
 * A test class for inventory objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InventoryTest {

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
	public void construct(){
		Robot r = new Robot();
		assertFalse(r.getInventory().isTerminated());
		assertEquals(r.getInventory().getCollector(),r);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_invalidCollector_null(){
		@SuppressWarnings("unused")
		Inventory i = new Inventory(null);
	}
	
	@Test
	public void terminate_collectorNotTerminated(){
		Robot r = new Robot();
		assertFalse(r.getInventory().isTerminated());
		assertEquals(r.getInventory().getCollector(),r);
		Inventory i = r.getInventory();
		r.getInventory().terminate();
		assertFalse(i.isTerminated());
		assertEquals(i.getCollector(),r);
	}
	
	@Test
	public void terminate_collectorTerminated(){
		Robot r = new Robot();
		assertFalse(r.getInventory().isTerminated());
		assertEquals(r.getInventory().getCollector(),r);
		Inventory i = r.getInventory();
		r.terminate();
		assertTrue(i.isTerminated());
		assertTrue(i.getCollector() == null);
		i.terminate();
		assertTrue(i.isTerminated());
		assertTrue(i.getCollector() == null);
	}
	
	@Test
	public void terminate_itemsTerminatedCheck_noBoard(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		Battery b1 = new Battery();
		Battery b2 = new Battery();
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		r.terminate();
		assertTrue(b1.isTerminated());
		assertTrue(b2.isTerminated());
	}
	
	@Test
	public void terminate_itemsTerminatedCheck_withBoard(){
		Board b = new Board(20L,20L);
		Position p = new Position(5L,6L);
		Robot r = new Robot(b,p);
		Inventory i = r.getInventory();
		Battery b1 = new Battery(b,p);
		Battery b2 = new Battery(b,p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		r.terminate();
		assertTrue(b1.isTerminated());
		assertTrue(b2.isTerminated());
	}
	
	@Test
	public void isTerminated(){
		Robot r = new Robot();
		assertFalse(r.getInventory().isTerminated());
		Inventory i = r.getInventory();
		i.terminate();
		assertFalse(i.isTerminated());
		r.terminate();
		assertTrue(i.isTerminated());
		i.terminate();
		assertTrue(i.isTerminated());
	}
	
	@Test
	public void canHaveAsCollector(){
		Robot r = new Robot();
		Robot r2 = new Robot();
		Inventory i = r.getInventory();
		assertTrue(i.canHaveAsCollector(r));
		assertTrue(i.canHaveAsCollector(r2));
		assertFalse(i.canHaveAsCollector(null));
		r2.terminate();
		assertTrue(i.canHaveAsCollector(r));
		assertFalse(i.canHaveAsCollector(r2));
		assertFalse(i.canHaveAsCollector(null));
		r.terminate();
		assertFalse(i.canHaveAsCollector(r));
		assertFalse(i.canHaveAsCollector(r2));
		assertTrue(i.canHaveAsCollector(null));
	}
	
	@Test
	public void getCollector(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		assertEquals(i.getCollector(),r);
	}
	
	@Test
	public void isValidInventoryItem(){
		Battery bat = new Battery();
		assertTrue(Inventory.isValidInventoryItem(bat));
		assertFalse(Inventory.isValidInventoryItem(null));
		bat.pickUp();
		assertFalse(Inventory.isValidInventoryItem(bat));
		assertFalse(Inventory.isValidInventoryItem(null));
		
		Battery batt = new Battery();
		assertTrue(Inventory.isValidInventoryItem(batt));
		assertFalse(Inventory.isValidInventoryItem(null));
		batt.terminate();
		assertFalse(Inventory.isValidInventoryItem(batt));
		assertFalse(Inventory.isValidInventoryItem(null));
	}
	
	@Test
	public void canHaveAsInventoryItem(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		Board board2 = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery();
		b1.pickUp();
		Battery b2 = new Battery();
		b2.terminate();
		Battery b3 = new Battery(null, null);
		Battery b4 = new Battery(board2, p);
		Battery b5 = new Battery(board, p2);
		Battery b6 = new Battery(board, p);
		
		assertFalse(i.canHaveAsInventoryItem(b1));
		assertFalse(i.canHaveAsInventoryItem(b2));
		assertFalse(i.canHaveAsInventoryItem(null));
		assertFalse(i.canHaveAsInventoryItem(b3));
		assertFalse(i.canHaveAsInventoryItem(b4));
		assertFalse(i.canHaveAsInventoryItem(b5));
		assertTrue(i.canHaveAsInventoryItem(b6));
		
		r.getBoard().moveBoardModelTo(r, p2);
		
		assertFalse(i.canHaveAsInventoryItem(b1));
		assertFalse(i.canHaveAsInventoryItem(b2));
		assertFalse(i.canHaveAsInventoryItem(null));
		assertFalse(i.canHaveAsInventoryItem(b3));
		assertFalse(i.canHaveAsInventoryItem(b4));
		assertTrue(i.canHaveAsInventoryItem(b5));
		assertFalse(i.canHaveAsInventoryItem(b6));
		
		r.terminate();
		
		assertFalse(i.canHaveAsInventoryItem(b1));
		assertFalse(i.canHaveAsInventoryItem(b2));
		assertFalse(i.canHaveAsInventoryItem(null));
		assertFalse(i.canHaveAsInventoryItem(b3));
		assertFalse(i.canHaveAsInventoryItem(b4));
		assertFalse(i.canHaveAsInventoryItem(b5));
		assertFalse(i.canHaveAsInventoryItem(b6));
		
		Robot r2 = new Robot();
		Inventory i2 = r2.getInventory();
		assertFalse(i2.canHaveAsInventoryItem(b1));
		assertFalse(i2.canHaveAsInventoryItem(b2));
		assertFalse(i2.canHaveAsInventoryItem(null));
		assertTrue(i2.canHaveAsInventoryItem(b3));
		assertFalse(i2.canHaveAsInventoryItem(b4));
		assertFalse(i2.canHaveAsInventoryItem(b5));
		assertFalse(i2.canHaveAsInventoryItem(b6));
		
	}
	
	@Test
	public void hasProperInventoryItems(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		assertTrue(i.hasProperInventoryItems());
		
		r.getBoard().moveBoardModelTo(r, p2);
		Battery b3 = new Battery(board, p2);
		i.addInventoryItem(b3);
		assertTrue(i.hasProperInventoryItems());
		
		r.terminate();
		assertTrue(i.hasProperInventoryItems());
	}
	
	@Test
	public void hasAsInventoryItem(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		Battery b3 = new Battery(board, p2);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertTrue(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		assertFalse(i.hasAsInventoryItem(null));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		r.getBoard().moveBoardModelTo(r, p2);
		i.addInventoryItem(b3);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertTrue(i.hasAsInventoryItem(b2));
		assertTrue(i.hasAsInventoryItem(b3));
		assertFalse(i.hasAsInventoryItem(null));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		r.terminate();
		assertFalse(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		assertFalse(i.hasAsInventoryItem(null));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test
	public void addInventoryItem_noBoard(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		Battery b1 = new Battery();
		i.addInventoryItem(b1);
		assertTrue(i.hasAsInventoryItem(b1));
	}
	
	@Test
	public void addInventoryItem(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		assertTrue(i.hasAsInventoryItem(b1));
		assertTrue(i.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test
	public void addInventoryItem_inventoryModelCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		ib.addInventoryItem(b1);
		ib.addInventoryItem(b2);
		assertTrue(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.addInventoryItem(b);
		assertFalse(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertTrue(i.hasAsInventoryItem(b));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalStateException.class)
	public void addInventoryItem_pickedUpInventoryModelCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		i.addInventoryItem(b);
		Battery b1 = new Battery(board, p);
		ib.addInventoryItem(b1);
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		assertTrue(ib.hasProperInventoryItems());
		assertTrue(ib.hasProperInventoryItemOrder());
	}
	
	@Test
	public void addInventoryItem_transfer(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItem(b1);
		Robot rr = new Robot(board, p2);
		Inventory ii = rr.getInventory();
		
		ii.addInventoryItem(b1, i);
		
		assertTrue(ii.hasAsInventoryItem(b1));
		assertTrue(ii.hasProperInventoryItems());
		assertTrue(ii.hasProperInventoryItemOrder());
		assertFalse(i.hasAsInventoryItem(b1));
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItem_invalidItem(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b3 = new Battery(board, p2);
		i.addInventoryItem(b3);
	}
	
	@Test (expected = IllegalStateException.class)
	public void addInventoryItem_terminatedCollectorInventory(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItem(b1);
		Robot rr = new Robot(board, p2);
		Inventory ii = rr.getInventory();
		rr.terminate();
		
		ii.addInventoryItem(b1, i);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItem_nullInventory(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		i.addInventoryItem(b1, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItem_terminatedTransferInventory(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItem(b1);
		r.terminate();
		Robot rr = new Robot(board, p2);
		Inventory ii = rr.getInventory();
		
		ii.addInventoryItem(b1, i);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItem_equalTransferAndCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItem(b1);
		Inventory ii = i;
		
		ii.addInventoryItem(b1, i);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItem_notOwnedInventoryModel(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		Battery b1 = new Battery(board, p);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		Robot rr = new Robot(board, p2);
		Inventory ii = rr.getInventory();
		
		ii.addInventoryItem(b1, i);
	}
	
	@Test
	public void addInventoryItemsOfPositionI(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		Battery b3 = new Battery(board, p2);
		i.addInventoryItemsOfPosition();
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertTrue(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test
	public void addInventoryItemsOfPositionII(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItemsOfPosition();
		
		assertEquals(i.getAllInventoryItems().size(),0);
	}
	
	@Test
	public void addInventoryItemsOfPosition_noItems(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		i.addInventoryItemsOfPosition();
		
		assertEquals(i.getAllInventoryItems().size(),0);
	}
	
	@Test (expected = IllegalStateException.class)
	public void addInventoryItemsOfPosition_noBoard(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		i.addInventoryItemsOfPosition();
	}
	
	@Test (expected = IllegalStateException.class)
	public void addInventoryItemsOfPosition_terminated(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		r.terminate();
		
		i.addInventoryItemsOfPosition();
	}
	
	public void addInventoryItemsFrom(){
		Robot rc = new Robot();
		Inventory ic = rc.getInventory();
		Robot rt = new Robot();
		Inventory it = rt.getInventory();
		
		Battery b1 = new Battery();
		Battery b2 = new Battery();
		Battery b3 = new Battery();
		it.addInventoryItem(b1);
		it.addInventoryItem(b2);
		it.addInventoryItem(b3);
		assertTrue(it.hasAsInventoryItem(b1));
		assertTrue(it.hasAsInventoryItem(b2));
		assertTrue(it.hasAsInventoryItem(b3));
		assertFalse(ic.hasAsInventoryItem(b1));
		assertFalse(ic.hasAsInventoryItem(b2));
		assertFalse(ic.hasAsInventoryItem(b3));
		ic.addInventoryItemsFrom(it);
		assertTrue(ic.hasAsInventoryItem(b1));
		assertTrue(ic.hasAsInventoryItem(b2));
		assertTrue(ic.hasAsInventoryItem(b3));
		assertFalse(it.hasAsInventoryItem(b1));
		assertFalse(it.hasAsInventoryItem(b2));
		assertFalse(it.hasAsInventoryItem(b3));
		
		assertFalse(it.isTerminated());
		assertTrue(ic.hasProperInventoryItems());
		assertTrue(ic.hasProperInventoryItemOrder());
		assertTrue(it.hasProperInventoryItems());
		assertTrue(it.hasProperInventoryItemOrder());
	}
	
	public void addInventoryItemsFrom_differentBoardPossible(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot rc = new Robot(board, p);
		Inventory ic = rc.getInventory();
		Robot rt = new Robot();
		Inventory it = rt.getInventory();
		
		Battery b1 = new Battery();
		Battery b2 = new Battery();
		Battery b3 = new Battery();
		it.addInventoryItem(b1);
		it.addInventoryItem(b2);
		it.addInventoryItem(b3);
		assertTrue(it.hasAsInventoryItem(b1));
		assertTrue(it.hasAsInventoryItem(b2));
		assertTrue(it.hasAsInventoryItem(b3));
		assertFalse(ic.hasAsInventoryItem(b1));
		assertFalse(ic.hasAsInventoryItem(b2));
		assertFalse(ic.hasAsInventoryItem(b3));
		ic.addInventoryItemsFrom(it);
		assertTrue(ic.hasAsInventoryItem(b1));
		assertTrue(ic.hasAsInventoryItem(b2));
		assertTrue(ic.hasAsInventoryItem(b3));
		assertFalse(it.hasAsInventoryItem(b1));
		assertFalse(it.hasAsInventoryItem(b2));
		assertFalse(it.hasAsInventoryItem(b3));
		
		assertFalse(it.isTerminated());
		assertTrue(ic.hasProperInventoryItems());
		assertTrue(ic.hasProperInventoryItemOrder());
		assertTrue(it.hasProperInventoryItems());
		assertTrue(it.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalStateException.class)
	public void addInventoryItemsFrom_terminatedCol(){
		Robot rc = new Robot();
		Inventory ic = rc.getInventory();
		Robot rt = new Robot();
		Inventory it = rt.getInventory();
		rc.terminate();
		assertTrue(ic.isTerminated());
		ic.addInventoryItemsFrom(it);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItemsFrom_terminatedTra(){
		Robot rc = new Robot();
		Inventory ic = rc.getInventory();
		Robot rt = new Robot();
		Inventory it = rt.getInventory();
		rt.terminate();
		assertTrue(it.isTerminated());
		ic.addInventoryItemsFrom(it);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItemsFrom_nullTra(){
		Robot rc = new Robot();
		Inventory ic = rc.getInventory();
		ic.addInventoryItemsFrom(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addInventoryItemsFrom_selfReference(){
		Robot rc = new Robot();
		Inventory ic = rc.getInventory();
		ic.addInventoryItemsFrom(ic);
	}
	
	@Test
	public void removeInventoryItem(){
		Position p = new Position(10L,10L);
		Position p2 = new Position(1L,1L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		Battery b3 = new Battery(board, p2);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertTrue(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		r.getBoard().moveBoardModelTo(r, p2);
		i.removeInventoryItem(b2);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.addInventoryItem(b3);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertTrue(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.removeInventoryItem(b3);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.removeInventoryItem(null);
		
		assertTrue(i.hasAsInventoryItem(b1));
		assertFalse(i.hasAsInventoryItem(b2));
		assertFalse(i.hasAsInventoryItem(b3));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test
	public void removeInventoryItem_inventoryModelCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		ib.removeInventoryItem(b1);
		ib.addInventoryItem(b1);
		ib.addInventoryItem(b2);
		assertTrue(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		ib.removeInventoryItem(b1);
		assertFalse(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		assertTrue(ib.hasProperInventoryItems());
		assertTrue(ib.hasProperInventoryItemOrder());
		
		i.addInventoryItem(b);
		assertTrue(i.hasAsInventoryItem(b));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		assertTrue(ib.hasProperInventoryItems());
		assertTrue(ib.hasProperInventoryItemOrder());
		
		i.removeInventoryItem(b);
		assertFalse(i.hasAsInventoryItem(b));
		assertFalse(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		assertTrue(ib.hasProperInventoryItems());
		assertTrue(ib.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalStateException.class)
	public void removeInventoryItem_pickedUpCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		ib.addInventoryItem(b1);
		ib.addInventoryItem(b2);
		ib.removeInventoryItem(b1);
		i.addInventoryItem(b);
		ib.removeInventoryItem(b2);
	}
	
	@Test
	public void removeAndTerminateInventoryItem_inventoryModelCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		ib.removeAndTerminateInventoryItem(b1);
		ib.addInventoryItem(b1);
		ib.addInventoryItem(b2);
		assertTrue(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		ib.removeAndTerminateInventoryItem(b1);
		assertFalse(b.isTerminated());
		assertTrue(b1.isTerminated());
		assertFalse(b2.isTerminated());
		assertFalse(ib.hasAsInventoryItem(b1));
		assertTrue(ib.hasAsInventoryItem(b2));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.addInventoryItem(b);
		assertTrue(i.hasAsInventoryItem(b));
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
		
		i.removeAndTerminateInventoryItem(b);
		assertFalse(i.hasAsInventoryItem(b));
		assertTrue(b.isTerminated());
		assertTrue(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertTrue(i.hasProperInventoryItems());
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalStateException.class)
	public void removeAndTerminateInventoryItem_pickedUpCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		ib.addInventoryItem(b1);
		ib.addInventoryItem(b2);
		ib.removeAndTerminateInventoryItem(b1);
		i.addInventoryItem(b);
		ib.removeAndTerminateInventoryItem(b2);
	}
	
	@Test
	public void getTotalWeightOfInventoryItems(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		
		assertEquals(i.getTotalWeightOfInventoryItems(WeightUnit.G).subtract(Battery.STANDARD_BATTERY_WEIGHT).subtract(Battery.STANDARD_BATTERY_WEIGHT).getWeightAmount(),0.0D,0.01D);
		assertEquals(i.getTotalWeightOfInventoryItems(WeightUnit.KG).subtract(Battery.STANDARD_BATTERY_WEIGHT).subtract(Battery.STANDARD_BATTERY_WEIGHT).getWeightAmount(),0.0D,0.01D);
	}
	
	@Test
	public void getTotalWeightOfInventoryItems_inventoryModelCollectors(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		ItemBox b = new ItemBox(board, p);
		Inventory ib = b.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		Battery b3 = new Battery(board, p);
		Battery b4 = new Battery(board, p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		ib.addInventoryItem(b3);
		ib.addInventoryItem(b4);
		i.addInventoryItem(b);
		
		assertEquals(ib.getTotalWeightOfInventoryItems(WeightUnit.G).subtract(Battery.STANDARD_BATTERY_WEIGHT).subtract(Battery.STANDARD_BATTERY_WEIGHT).getWeightAmount(),0.0D,0.01D);
		assertEquals(ib.getTotalWeightOfInventoryItems(WeightUnit.KG).subtract(Battery.STANDARD_BATTERY_WEIGHT).subtract(Battery.STANDARD_BATTERY_WEIGHT).getWeightAmount(),0.0D,0.01D);
		assertEquals(i.getTotalWeightOfInventoryItems(WeightUnit.G).subtract((Battery.STANDARD_BATTERY_WEIGHT.multiply(4))).subtract(ItemBox.STANDARD_ITEMBOX_WEIGHT).getWeightAmount(),0.0D,0.01D);
		assertEquals(i.getTotalWeightOfInventoryItems(WeightUnit.KG).subtract((Battery.STANDARD_BATTERY_WEIGHT.multiply(4))).subtract(ItemBox.STANDARD_ITEMBOX_WEIGHT).getWeightAmount(),0.0D,0.01D);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getTotalWeightOfInventoryItems_nullWeightUnit(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		i.getTotalWeightOfInventoryItems(null);
	}
	
	@Test
	public void getAllInventoryItems(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		Battery b2 = new Battery(board, p);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		
		assertTrue(i.getAllInventoryItems().contains(b1));
		assertTrue(i.getAllInventoryItems().contains(b2));
		assertEquals(i.getAllInventoryItems().size(), 2);
	}
	
	@Test
	public void sortInventoryItemsByWeight(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		
		Weight w1 = new Weight(0.1D,WeightUnit.G);
		Weight w2 = new Weight(1D,WeightUnit.G);
		Weight w3 = new Weight(1000D,WeightUnit.G);
		Weight w4 = new Weight(1D,WeightUnit.KG);
		Weight w5 = new Weight(10000D,WeightUnit.G);
		
		InventoryModel m1 = new Battery(null, null, new Accu(), w3);
		InventoryModel m2 = new Battery(null, null, new Accu(), w1);
		InventoryModel m3 = new Battery(null, null, new Accu(), w5);
		InventoryModel m4 = new Battery(null, null, new Accu(), w2);
		InventoryModel m5 = new Battery(null, null, new Accu(), w4);
		
		i.addInventoryItem(m1);
		i.addInventoryItem(m2);
		i.addInventoryItem(m3);
		i.addInventoryItem(m4);
		i.addInventoryItem(m5);
		
		assertEquals(i.getAllInventoryItems().get(0), m2);
		assertEquals(i.getAllInventoryItems().get(1), m4);
		assertEquals(i.getAllInventoryItems().get(2), m1);
		assertEquals(i.getAllInventoryItems().get(3), m5);
		assertEquals(i.getAllInventoryItems().get(4), m3);
	}
	
	@Test
	public void getAllInventoryItemsClass_InventoryModel(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		InventoryModel b2 = new Battery(board, p);
		Bomb bomb = new Bomb(board, p, 1);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		i.addInventoryItem(bomb);
		
		assertTrue(i.getAllInventoryItemsClass(InventoryModel.class).contains(b1));
		assertTrue(i.getAllInventoryItemsClass(InventoryModel.class).contains(b2));
		assertTrue(i.getAllInventoryItemsClass(InventoryModel.class).contains(bomb));
		assertEquals(i.getAllInventoryItemsClass(InventoryModel.class).size(), 3);
	}
	
	@Test
	public void getAllInventoryItemsClass_Battery(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery(board, p);
		InventoryModel b2 = new Battery(board, p);
		Bomb bomb = new Bomb(board, p, 1);
		i.addInventoryItem(b1);
		i.addInventoryItem(b2);
		i.addInventoryItem(bomb);
		
		assertTrue(i.getAllInventoryItemsClass(Battery.class).contains(b1));
		assertTrue(i.getAllInventoryItemsClass(Battery.class).contains(b2));
		assertEquals(i.getAllInventoryItemsClass(Battery.class).size(), 2);
	}
	
	@Test
	public void getOptimalEnergyModelOrder(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		
		Energy w0 = new Energy(0.01D,EnergyUnit.WS);
		
		Energy w1 = new Energy(0.1D,EnergyUnit.WS);
		Energy w2 = new Energy(1D,EnergyUnit.WS);
		Energy w3 = new Energy(1D*EnergyUnit.KWH.toEnergyUnit(EnergyUnit.WS),EnergyUnit.WS);
		Energy w4 = new Energy(1D,EnergyUnit.KWH);
		Energy w5 = new Energy(10000000D,EnergyUnit.WS);
		
		EnergyModel m1 = new Battery(new Accu(w0,w3));
		EnergyModel m2 = new Battery(new Accu(w0,w1));
		EnergyModel m3 = new Battery(new Accu(w0,w5));
		EnergyModel m4 = new Battery(new Accu(w0,w2));
		EnergyModel m5 = new Battery(new Accu(w0,w4));
		Bomb bomb = new Bomb();
		
		i.addInventoryItem((InventoryModel)m1);
		i.addInventoryItem((InventoryModel)m2);
		i.addInventoryItem((InventoryModel)m3);
		i.addInventoryItem((InventoryModel)m4);
		i.addInventoryItem((InventoryModel)m5);
		i.addInventoryItem(bomb);
		
		assertEquals(i.getOptimalEnergyModelOrder().get(0), m2);
		assertEquals(i.getOptimalEnergyModelOrder().get(1), m4);
		assertEquals(i.getOptimalEnergyModelOrder().get(2), m1);
		assertEquals(i.getOptimalEnergyModelOrder().get(3), m5);
		assertEquals(i.getOptimalEnergyModelOrder().get(4), m3);
	}
	
	@Test
	public void useInventoryItem_terminatedAfterUse(){
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Robot c = new Robot(ac);
		Inventory i = c.getInventory();
		Accu acc = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		InventoryModel b = new Battery(acc);
		c.getInventory().addInventoryItem(b);
		
		i.useInventoryItem(b);
		assertTrue(b.isTerminated());
		assertFalse(i.hasAsInventoryItem(b));
		assertFalse(c.isTerminated());
		assertFalse(i.isTerminated());
	}
	
	@Test
	public void useInventoryItem_notTerminatedAfterUse(){
		Accu ac = new Accu(Battery.STANDARD_BATTERY_AMOUNT_OF_ENERGY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		Robot c = new Robot(ac);
		Inventory i = c.getInventory();
		Accu acc = new Accu(Battery.STANDARD_BATTERY_ENERGY_CAPACITY, Battery.STANDARD_BATTERY_ENERGY_CAPACITY);
		InventoryModel b = new Battery(acc);
		c.getInventory().addInventoryItem(b);
		
		i.useInventoryItem(b);
		assertFalse(b.isTerminated());
		assertTrue(i.hasAsInventoryItem(b));
		assertFalse(c.isTerminated());
		assertFalse(i.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void useInventoryItem_illegalArgumentException(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		Inventory i = r.getInventory();
		Battery b1 = new Battery(board, p);
		
		i.useInventoryItem(b1);
	}
	
	@Test
	public void useInventoryItem_inventoryModelCollector(){
		Position p = new Position(10L,10L);
		Board board = new Board(20L, 20L);
		
		Robot r = new Robot(board, p);
		ItemBox b = new ItemBox(board,p);
		Inventory ib = b.getInventory();
		Bomb bomb = new Bomb(board,p,1);
		ib.addInventoryItem(bomb);
		ib.useInventoryItem(bomb);
		assertTrue(ib.hasAsInventoryItem(bomb));
		assertEquals(r.getAccu().getEnergyCapacityLimit(),r.getAccu().getOriginalEnergyCapacityLimit());
	}
	
	@Test
	public void getInventoryItemAt(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		assertEquals(i.getInventoryItemAt(1),b1);
		assertEquals(i.getInventoryItemAt(2),b2);
		assertEquals(i.getInventoryItemAt(3),b3);
		assertEquals(i.getInventoryItemAt(4),b4);
		assertEquals(i.getInventoryItemAt(5),b5);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getInventoryItemAt_lowerIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getInventoryItemAt(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getInventoryItemAt_zeroIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getInventoryItemAt(0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getInventoryItemAt_higherIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getInventoryItemAt(i.getNbOfInventoryItems()+1);
	}
	
	@Test
	public void getHeaviestInventoryItemAt(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		
		Weight w1 = new Weight(0.1D,WeightUnit.G);
		Weight w2 = new Weight(1D,WeightUnit.G);
		Weight w3 = new Weight(1000D,WeightUnit.G);
		Weight w4 = new Weight(1D,WeightUnit.KG);
		Weight w5 = new Weight(10000D,WeightUnit.G);
		
		InventoryModel m1 = new Battery(null, null, new Accu(), w3);
		InventoryModel m2 = new Battery(null, null, new Accu(), w1);
		InventoryModel m3 = new Battery(null, null, new Accu(), w5);
		InventoryModel m4 = new Battery(null, null, new Accu(), w2);
		InventoryModel m5 = new Battery(null, null, new Accu(), w4);
		
		i.addInventoryItem(m1);
		i.addInventoryItem(m2);
		i.addInventoryItem(m3);
		i.addInventoryItem(m4);
		i.addInventoryItem(m5);
		
		assertEquals(i.getHeaviestInventoryItemAt(1),m3);
		assertEquals(i.getHeaviestInventoryItemAt(2),m5);
		assertEquals(i.getHeaviestInventoryItemAt(3),m1);
		assertEquals(i.getHeaviestInventoryItemAt(4),m4);
		assertEquals(i.getHeaviestInventoryItemAt(5),m2);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getHeaviestInventoryItemAt_lowerIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getHeaviestInventoryItemAt(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getHeaviestInventoryItemAt_zeroIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getHeaviestInventoryItemAt(0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void getHeaviestInventoryItemAt_higherIndexRejected(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		
		i.getHeaviestInventoryItemAt(i.getNbOfInventoryItems()+1);
	}
	
	@Test
	public void getNbOfInventoryItems(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		assertEquals(i.getNbOfInventoryItems(),0);
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		assertEquals(i.getNbOfInventoryItems(),1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		assertEquals(i.getNbOfInventoryItems(),2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		assertEquals(i.getNbOfInventoryItems(),3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		assertEquals(i.getNbOfInventoryItems(),4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		assertEquals(i.getNbOfInventoryItems(),5);
	}
	
	@Test
	public void getNbOfInventoryItemsClass_inventoryModel(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),0);
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		assertEquals(i.getNbOfInventoryItemsClass(InventoryModel.class),5);
	}
	
	@Test
	public void getNbOfInventoryItemsClass_battery(){
		Robot c = new Robot();
		Inventory i = c.getInventory();
		
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),0);
		InventoryModel b1 = new Battery();
		i.addInventoryItem(b1);
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),1);
		Battery b2 = new Battery();
		i.addInventoryItem(b2);
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),2);
		InventoryModel b3 = new Battery();
		i.addInventoryItem(b3);
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),3);
		Battery b4 = new Battery();
		i.addInventoryItem(b4);
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),4);
		InventoryModel b5 = new Battery();
		i.addInventoryItem(b5);
		assertEquals(i.getNbOfInventoryItemsClass(Battery.class),5);
	}
	
	@Test
	public void hasProperInventoryItemOrder(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		
		Weight w1 = new Weight(0.1D,WeightUnit.G);
		Weight w2 = new Weight(1D,WeightUnit.G);
		Weight w3 = new Weight(1000D,WeightUnit.G);
		Weight w4 = new Weight(1D,WeightUnit.KG);
		Weight w5 = new Weight(10000D,WeightUnit.G);
		
		InventoryModel m1 = new Battery(null, null, new Accu(), w3);
		InventoryModel m2 = new Battery(null, null, new Accu(), w1);
		InventoryModel m3 = new Battery(null, null, new Accu(), w5);
		InventoryModel m4 = new Battery(null, null, new Accu(), w2);
		InventoryModel m5 = new Battery(null, null, new Accu(), w4);
		
		assertTrue(i.hasProperInventoryItemOrder());
		i.addInventoryItem(m1);
		assertTrue(i.hasProperInventoryItemOrder());
		i.addInventoryItem(m2);
		assertTrue(i.hasProperInventoryItemOrder());
		i.addInventoryItem(m3);
		assertTrue(i.hasProperInventoryItemOrder());
		i.addInventoryItem(m4);
		assertTrue(i.hasProperInventoryItemOrder());
		i.addInventoryItem(m5);
		assertTrue(i.hasProperInventoryItemOrder());
		i.removeInventoryItem(m1);
		assertTrue(i.hasProperInventoryItemOrder());
		i.removeInventoryItem(m2);
		assertTrue(i.hasProperInventoryItemOrder());
		i.removeInventoryItem(m3);
		assertTrue(i.hasProperInventoryItemOrder());
		i.removeInventoryItem(m4);
		assertTrue(i.hasProperInventoryItemOrder());
		i.removeInventoryItem(m5);
		assertTrue(i.hasProperInventoryItemOrder());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void duplicateInventoryModelRejected(){
		Robot r = new Robot();
		Inventory i = r.getInventory();
		
		Battery b1 = new Battery();
		i.addInventoryItem(b1);
		i.addInventoryItem(b1);
	}
}
