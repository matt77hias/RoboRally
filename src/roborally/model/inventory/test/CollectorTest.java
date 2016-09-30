package roborally.model.inventory.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.Collector;
import roborally.model.inventory.Inventory;
import roborally.model.inventory.item.ItemBox;
import roborally.model.inventory.item.SurpriseBox;

/**
 * A test class for the collector objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 * 
 */
public class CollectorTest {

	@Test
	public void terminate() {
		Collector r = new Robot();
		Collector ib = new ItemBox();
		Collector sb = new SurpriseBox();
		
		assertFalse(r.isTerminated());
		assertFalse(ib.isTerminated());
		assertFalse(sb.isTerminated());
		
		Inventory ir = r.getInventory();
		Inventory iib = ib.getInventory();
		Inventory isb = sb.getInventory();
		
		r.terminate();
		ib.terminate();
		sb.terminate();
		
		assertTrue(r.isTerminated());
		assertTrue(ib.isTerminated());
		assertTrue(sb.isTerminated());
		
		assertTrue(r.getInventory() == null);
		assertTrue(ib.getInventory() == null);
		assertTrue(sb.getInventory() == null);
		
		assertTrue(ir.isTerminated());
		assertTrue(iib.isTerminated());
		assertTrue(isb.isTerminated());
	}
	
	@Test
	public void hasValidInventory() {
		Collector r = new Robot();
		Collector ib = new ItemBox();
		Collector sb = new SurpriseBox();
		
		assertFalse(r.isTerminated());
		assertFalse(ib.isTerminated());
		assertFalse(sb.isTerminated());
		
		assertFalse(r.getInventory() == null);
		assertFalse(ib.getInventory() == null);
		assertFalse(sb.getInventory() == null);
		
		assertTrue(r.hasValidInventory());
		assertTrue(ib.hasValidInventory());
		assertTrue(sb.hasValidInventory());
		
		r.terminate();
		ib.terminate();
		sb.terminate();
		
		assertTrue(r.isTerminated());
		assertTrue(ib.isTerminated());
		assertTrue(sb.isTerminated());
		
		assertTrue(r.getInventory() == null);
		assertTrue(ib.getInventory() == null);
		assertTrue(sb.getInventory() == null);
		
		assertTrue(r.hasValidInventory());
		assertTrue(ib.hasValidInventory());
		assertTrue(sb.hasValidInventory());
	}
}
