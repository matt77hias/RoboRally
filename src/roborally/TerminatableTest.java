package roborally;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.inventory.Inventory;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.Bomb;
import roborally.model.inventory.item.ItemBox;
import roborally.model.inventory.item.RepairKit;
import roborally.model.inventory.item.SurpriseBox;
import roborally.model.inventory.item.TeleportGate;

/**
 * A test class for terminatable objects.
 * 
 * @version Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TerminatableTest {

	@Test
	public void terminateI() {
		Board board1 = new Board();
		
		Accu ar= new Accu();
		Accu ab= new Accu();
		Accu ak= new Accu();
		
		Robot r = new Robot(board1, new Position(1L, 1L),ar, null);
		Battery b = new Battery(board1, new Position(1L, 1L),ab);
		RepairKit k = new RepairKit(board1, new Position(2L, 1L),ak);
		ItemBox ib = new ItemBox(board1, new Position(3L, 1L));
		SurpriseBox sb = new SurpriseBox(board1, new Position(4L, 1L));
		Bomb bo = new Bomb(board1, new Position(3L, 1L), 1);
		TeleportGate tg = new TeleportGate(board1, new Position(5L, 1L), 1);
		
		Inventory ir = r.getInventory();
		Inventory iib = ib.getInventory();
		Inventory isb = sb.getInventory();
		
		ir.addInventoryItem(b);
		iib.addInventoryItem(bo);
		
		r.terminate();
		b.terminate();
		k.terminate();
		ib.terminate();
		sb.terminate();
		bo.terminate();
		tg.terminate();
		
		assertTrue(r.getBoard() == null);
		assertTrue(r.getPosition() == null);
		assertTrue(r.getInventory() == null);
		assertTrue(r.getAccu() == null);
		
		assertTrue(b.getBoard() == null);
		assertTrue(b.getPosition() == null);
		assertTrue(b.getAccu() == null);
		
		assertTrue(k.getBoard() == null);
		assertTrue(k.getPosition() == null);
		assertTrue(k.getAccu() == null);
		
		assertTrue(ib.getBoard() == null);
		assertTrue(ib.getPosition() == null);
		assertTrue(ib.getInventory() == null);
		
		assertTrue(sb.getBoard() == null);
		assertTrue(sb.getPosition() == null);
		assertTrue(sb.getInventory() == null);
		
		assertTrue(bo.getBoard() == null);
		assertTrue(bo.getPosition() == null);
		
		assertTrue(tg.getBoard() == null);
		assertTrue(tg.getPosition() == null);
		
		assertEquals(ir.getNbOfInventoryItems(),0);
		assertEquals(iib.getNbOfInventoryItems(),0);
		assertEquals(isb.getNbOfInventoryItems(),0);
		
		assertEquals(board1.getNbBoardModels(),0);
	}
	
	@Test
	public void terminateII() {
		Board board1 = new Board();
		
		Accu ar= new Accu();
		Accu ab= new Accu();
		Accu ak= new Accu();
		
		Robot r = new Robot(board1, new Position(1L, 1L),ar, null);
		Battery b = new Battery(board1, new Position(1L, 1L),ab);
		RepairKit k = new RepairKit(board1, new Position(2L, 1L),ak);
		ItemBox ib = new ItemBox(board1, new Position(3L, 1L));
		SurpriseBox sb = new SurpriseBox(board1, new Position(4L, 1L));
		Bomb bo = new Bomb(board1, new Position(3L, 1L), 1);
		TeleportGate tg = new TeleportGate(board1, new Position(5L, 1L), 1);
		
		board1.terminate();
		
		assertEquals(board1.getNbBoardModels(),0);
		
		assertTrue(r.getBoard() == null);
		assertTrue(b.getBoard() == null);
		assertTrue(k.getBoard() == null);
		assertTrue(ib.getBoard() == null);
		assertTrue(sb.getBoard() == null);
		assertTrue(bo.getBoard() == null);
		assertTrue(tg.getBoard() == null);
	}
}
