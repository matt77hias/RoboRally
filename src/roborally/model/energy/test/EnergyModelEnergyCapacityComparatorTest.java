package roborally.model.energy.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.model.energy.*;
import roborally.model.inventory.item.Battery;

/**
 * A test class for the energy model energy capacity comparator. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 * 
 */
public class EnergyModelEnergyCapacityComparatorTest {

	@Test
	public void test() {
		Energy w0 = new Energy(0.01D,EnergyUnit.WS);
		
		Energy w1 = new Energy(0.1D,EnergyUnit.WS);
		Energy w2 = new Energy(1D,EnergyUnit.WS);
		Energy w3 = new Energy(1D*EnergyUnit.KWH.toEnergyUnit(EnergyUnit.WS),EnergyUnit.WS);
		Energy w4 = new Energy(1D,EnergyUnit.KWH);
		Energy w5 = new Energy(10000000D,EnergyUnit.WS);
		
		EnergyModel m1 = new Battery(new Accu(w0,w1));
		EnergyModel m2 = new Battery(new Accu(w0,w2));
		EnergyModel m3 = new Battery(new Accu(w0,w3));
		EnergyModel m4 = new Battery(new Accu(w0,w4));
		EnergyModel m5 = new Battery(new Accu(w0,w5));
		
		EnergyModelEnergyCapacityComparator wc = new EnergyModelEnergyCapacityComparator();
		
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
		assertEquals(wc.compare(m4, m3),Math.signum(1),0.01D);
		assertEquals(wc.compare(m4, m4),Math.signum(0),0.01D);
		assertEquals(wc.compare(m4, m5),Math.signum(-1),0.01D);
		
		assertEquals(wc.compare(m5, m1),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m2),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m3),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m4),Math.signum(1),0.01D);
		assertEquals(wc.compare(m5, m5),Math.signum(0),0.01D);
	}
}
