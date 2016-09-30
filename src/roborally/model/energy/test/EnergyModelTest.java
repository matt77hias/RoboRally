package roborally.model.energy.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.EnergyModel;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.RepairKit;

/**
 * A test class for the energy model objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 * 
 */
public class EnergyModelTest {

	@Test
	public void terminate() {
		EnergyModel r = new Robot();
		EnergyModel k = new RepairKit();
		EnergyModel b = new Battery();
		
		assertFalse(r.isTerminated());
		assertFalse(k.isTerminated());
		assertFalse(b.isTerminated());
		
		Accu ar = r.getAccu();
		Accu ak = k.getAccu();
		Accu ab = k.getAccu();
		
		r.terminate();
		b.terminate();
		k.terminate();
		
		assertTrue(r.isTerminated());
		assertTrue(b.isTerminated());
		assertTrue(k.isTerminated());
		
		assertTrue(r.getAccu() == null);
		assertTrue(k.getAccu() == null);
		assertTrue(b.getAccu() == null);
		
		assertTrue(ar.isTerminated());
		assertTrue(ak.isTerminated());
		assertTrue(ab.isTerminated());
	}
	
	@Test
	public void canHaveAsAccu() {
		EnergyModel r = new Robot();
		EnergyModel k = new RepairKit();
		EnergyModel b = new Battery();
		
		assertFalse(r.isTerminated());
		assertFalse(k.isTerminated());
		assertFalse(b.isTerminated());
		
		Accu ar = r.getAccu();
		Accu ak = k.getAccu();
		Accu ab = b.getAccu();
		
		Accu t = new Accu();
		t.terminate();
		
		assertFalse(r.getAccu() == null);
		assertFalse(k.getAccu() == null);
		assertFalse(b.getAccu() == null);
		
		assertTrue(r.canHaveAsAccu(ar));
		assertTrue(k.canHaveAsAccu(ak));
		assertTrue(b.canHaveAsAccu(ab));
		
		assertFalse(r.canHaveAsAccu(null));
		assertFalse(k.canHaveAsAccu(null));
		assertFalse(b.canHaveAsAccu(null));
		
		assertFalse(r.canHaveAsAccu(ak));
		assertFalse(k.canHaveAsAccu(ab));
		assertFalse(b.canHaveAsAccu(ar));
		
		assertFalse(r.canHaveAsAccu(t));
		assertFalse(k.canHaveAsAccu(t));
		assertFalse(b.canHaveAsAccu(t));
		
		r.terminate();
		b.terminate();
		k.terminate();
		
		assertTrue(r.isTerminated());
		assertTrue(b.isTerminated());
		assertTrue(k.isTerminated());
		
		assertTrue(r.getAccu() == null);
		assertTrue(k.getAccu() == null);
		assertTrue(b.getAccu() == null);
		
		assertTrue(r.canHaveAsAccu(null));
		assertTrue(k.canHaveAsAccu(null));
		assertTrue(b.canHaveAsAccu(null));
		
		assertFalse(r.canHaveAsAccu(ar));
		assertFalse(k.canHaveAsAccu(ak));
		assertFalse(b.canHaveAsAccu(ab));
		
		assertFalse(r.canHaveAsAccu(ak));
		assertFalse(k.canHaveAsAccu(ab));
		assertFalse(b.canHaveAsAccu(ar));
		
		assertFalse(r.canHaveAsAccu(t));
		assertFalse(k.canHaveAsAccu(t));
		assertFalse(b.canHaveAsAccu(t));
	}
}
