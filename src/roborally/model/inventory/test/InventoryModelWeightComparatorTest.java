package roborally.model.inventory.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.model.energy.Accu;
import roborally.model.inventory.InventoryModelWeightComparator;
import roborally.model.inventory.item.*;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;

/**
 * A test class for the inventory model weight comparator. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 * 
 */
public class InventoryModelWeightComparatorTest {

	@Test
	public void compare_normal() {
		Weight w1 = new Weight(0.1D,WeightUnit.G);
		Weight w2 = new Weight(1D,WeightUnit.G);
		Weight w3 = new Weight(1000D,WeightUnit.G);
		Weight w4 = new Weight(1D,WeightUnit.KG);
		Weight w5 = new Weight(10000D,WeightUnit.G);
		
		InventoryModel m1 = new Battery(null, null, new Accu(), w1);
		InventoryModel m2 = new Battery(null, null, new Accu(), w2);
		InventoryModel m3 = new Battery(null, null, new Accu(), w3);
		InventoryModel m4 = new Battery(null, null, new Accu(), w4);
		InventoryModel m5 = new Battery(null, null, new Accu(), w5);
		
		InventoryModelWeightComparator wc = new InventoryModelWeightComparator();
		
		assertEquals(wc.compare(m1, m1),Math.signum(0),0.01D);
		assertEquals(wc.compare(m1, m2),Math.signum(-1),0.01D);
		assertEquals(wc.compare(m1, m3),Math.signum(-1),0.01D);
		assertEquals(wc.compare(m1, m4),Math.signum(-1),0.01D);
		assertEquals(wc.compare(m1, m5),Math.signum(-1),0.01D);
		
		assertEquals(wc.compare(m2, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m2, m2),Math.signum(0),0.01D);
		assertEquals(wc.compare(m2, m3),Math.signum(-1),0.01D);
		assertEquals(wc.compare(m2, m4),Math.signum(-1),0.01D);
		assertEquals(wc.compare(m2, m5),Math.signum(-1),0.01D);
		
		assertEquals(wc.compare(m3, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m3, m2),Math.signum(1),0.01D);
		assertEquals(wc.compare(m3, m3),Math.signum(0),0.01D);
		assertEquals(wc.compare(m3, m4),Math.signum(0),0.01D);
		assertEquals(wc.compare(m3, m5),Math.signum(-1),0.01D);
		
		assertEquals(wc.compare(m4, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m4, m2),Math.signum(1),0.01D);
		assertEquals(wc.compare(m4, m3),Math.signum(0),0.01D);
		assertEquals(wc.compare(m4, m4),Math.signum(0),0.01D);
		assertEquals(wc.compare(m4, m5),Math.signum(-1),0.01D);
		
		assertEquals(wc.compare(m5, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m2),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m3),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m4),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m5),Math.signum(0),0.01D);
	}
	
	@Test
	public void compare_collectors(){
		Weight w1 = new Weight(100D,WeightUnit.G);
		InventoryModel m1 = new Battery(null, null, new Accu(), w1);
		InventoryModel m2 = new Battery(null, null, new Accu(), w1);
		InventoryModel m3 = new Battery(null, null, new Accu(), w1);
		ItemBox b1 = new ItemBox(null, null, w1);
		ItemBox b2 = new ItemBox(null, null, w1);
		
		InventoryModelWeightComparator wc = new InventoryModelWeightComparator();
		
		assertEquals(wc.compare(b1, m1),Math.signum(0),0.01D);
		assertEquals(wc.compare(m1, b1),Math.signum(0),0.01D);
		assertEquals(wc.compare(b1, b2),Math.signum(0),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(0),0.01D);
		b1.getInventory().addInventoryItem(m1);
		assertEquals(wc.compare(b1, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m1, b1),Math.signum(-1),0.01D);
		assertEquals(wc.compare(b1, b2),Math.signum(1),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(-1),0.01D);
		b2.getInventory().addInventoryItem(m2);
		assertEquals(wc.compare(b1, b2),Math.signum(0),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(0),0.01D);
		b2.getInventory().addInventoryItem(m3);
		assertEquals(wc.compare(b1, b2),Math.signum(-1),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(1),0.01D);
		b2.terminate();
		assertTrue(b2.isTerminated());		
		assertEquals(wc.compare(b1, b2),Math.signum(1),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(-1),0.01D);
		b1.terminate();
		assertTrue(b1.isTerminated());
		assertEquals(wc.compare(b1, b2),Math.signum(0),0.01D);
		assertEquals(wc.compare(b2, b1),Math.signum(0),0.01D);
	}
	
	@Test (expected = NullPointerException.class)
	public void compare_nullVsNotNull(){
		Weight w1 = new Weight(100D,WeightUnit.G);
		InventoryModel m1 = new Battery(null, null, new Accu(), w1);
		InventoryModelWeightComparator wc = new InventoryModelWeightComparator();
		wc.compare(null, m1);
	}
	
	@Test (expected = NullPointerException.class)
	public void compare_notNullVsNull(){
		Weight w1 = new Weight(100D,WeightUnit.G);
		InventoryModel m1 = new Battery(null, null, new Accu(), w1);
		InventoryModelWeightComparator wc = new InventoryModelWeightComparator();
		wc.compare(m1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void compare_nullVsNull(){
		InventoryModelWeightComparator wc = new InventoryModelWeightComparator();
		wc.compare(null, null);
	}
}
