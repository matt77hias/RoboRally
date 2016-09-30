package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.conditiontester.BasicComparison;
import roborally.model.energy.Energy;
import roborally.model.energy.EnergyUnit;

/**
 * A test class for the basic compare operations
 * between two comparable extending objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BasicComparisonTest {

	@Test
	public void compareDecode(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		
		assertTrue(BasicComparison.E.compareDecode(e1, e1));
		assertFalse(BasicComparison.E.compareDecode(e1, e2));
		assertTrue(BasicComparison.E.compareDecode(e1, e3));
		assertFalse(BasicComparison.E.compareDecode(e2, e1));
		assertTrue(BasicComparison.E.compareDecode(e2, e2));
		assertFalse(BasicComparison.E.compareDecode(e2, e3));
		assertTrue(BasicComparison.E.compareDecode(e3, e1));
		assertFalse(BasicComparison.E.compareDecode(e3, e2));
		assertTrue(BasicComparison.E.compareDecode(e3, e3));
		
		assertFalse(BasicComparison.NE.compareDecode(e1, e1));
		assertTrue(BasicComparison.NE.compareDecode(e1, e2));
		assertFalse(BasicComparison.NE.compareDecode(e1, e3));
		assertTrue(BasicComparison.NE.compareDecode(e2, e1));
		assertFalse(BasicComparison.NE.compareDecode(e2, e2));
		assertTrue(BasicComparison.NE.compareDecode(e2, e3));
		assertFalse(BasicComparison.NE.compareDecode(e3, e1));
		assertTrue(BasicComparison.NE.compareDecode(e3, e2));
		assertFalse(BasicComparison.NE.compareDecode(e3, e3));
		
		assertTrue(BasicComparison.I.compareDecode(e1, e1));
		assertFalse(BasicComparison.I.compareDecode(e1, e2));
		assertFalse(BasicComparison.I.compareDecode(e1, e3));
		assertFalse(BasicComparison.I.compareDecode(e2, e1));
		assertTrue(BasicComparison.I.compareDecode(e2, e2));
		assertFalse(BasicComparison.I.compareDecode(e2, e3));
		assertFalse(BasicComparison.I.compareDecode(e3, e1));
		assertFalse(BasicComparison.I.compareDecode(e3, e2));
		assertTrue(BasicComparison.I.compareDecode(e3, e3));
		
		assertFalse(BasicComparison.NI.compareDecode(e1, e1));
		assertTrue(BasicComparison.NI.compareDecode(e1, e2));
		assertTrue(BasicComparison.NI.compareDecode(e1, e3));
		assertTrue(BasicComparison.NI.compareDecode(e2, e1));
		assertFalse(BasicComparison.NI.compareDecode(e2, e2));
		assertTrue(BasicComparison.NI.compareDecode(e2, e3));
		assertTrue(BasicComparison.NI.compareDecode(e3, e1));
		assertTrue(BasicComparison.NI.compareDecode(e3, e2));
		assertFalse(BasicComparison.NI.compareDecode(e3, e3));
		
		assertFalse(BasicComparison.L.compareDecode(e1, e1));
		assertTrue(BasicComparison.L.compareDecode(e1, e2));
		assertFalse(BasicComparison.L.compareDecode(e1, e3));
		assertFalse(BasicComparison.L.compareDecode(e2, e1));
		assertFalse(BasicComparison.L.compareDecode(e2, e2));
		assertFalse(BasicComparison.L.compareDecode(e2, e3));
		assertFalse(BasicComparison.L.compareDecode(e3, e1));
		assertTrue(BasicComparison.L.compareDecode(e3, e2));
		assertFalse(BasicComparison.L.compareDecode(e3, e3));
		
		assertFalse(BasicComparison.G.compareDecode(e1, e1));
		assertFalse(BasicComparison.G.compareDecode(e1, e2));
		assertFalse(BasicComparison.G.compareDecode(e1, e3));
		assertTrue(BasicComparison.G.compareDecode(e2, e1));
		assertFalse(BasicComparison.G.compareDecode(e2, e2));
		assertTrue(BasicComparison.G.compareDecode(e2, e3));
		assertFalse(BasicComparison.G.compareDecode(e3, e1));
		assertFalse(BasicComparison.G.compareDecode(e3, e2));
		assertFalse(BasicComparison.G.compareDecode(e3, e3));
		
		assertTrue(BasicComparison.LE.compareDecode(e1, e1));
		assertTrue(BasicComparison.LE.compareDecode(e1, e2));
		assertTrue(BasicComparison.LE.compareDecode(e1, e3));
		assertFalse(BasicComparison.LE.compareDecode(e2, e1));
		assertTrue(BasicComparison.LE.compareDecode(e2, e2));
		assertFalse(BasicComparison.LE.compareDecode(e2, e3));
		assertTrue(BasicComparison.LE.compareDecode(e3, e1));
		assertTrue(BasicComparison.LE.compareDecode(e3, e2));
		assertTrue(BasicComparison.LE.compareDecode(e3, e3));
		
		assertTrue(BasicComparison.GE.compareDecode(e1, e1));
		assertFalse(BasicComparison.GE.compareDecode(e1, e2));
		assertTrue(BasicComparison.GE.compareDecode(e1, e3));
		assertTrue(BasicComparison.GE.compareDecode(e2, e1));
		assertTrue(BasicComparison.GE.compareDecode(e2, e2));
		assertTrue(BasicComparison.GE.compareDecode(e2, e3));
		assertTrue(BasicComparison.GE.compareDecode(e3, e1));
		assertFalse(BasicComparison.GE.compareDecode(e3, e2));
		assertTrue(BasicComparison.GE.compareDecode(e3, e3));
	}
	
	@Test
	public void isEqualTo(){		
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertFalse(BasicComparison.isEqualTo(e1, e2));
		assertFalse(BasicComparison.isEqualTo(e2, e1));
		assertTrue(BasicComparison.isEqualTo(e1, e3));
		assertTrue(BasicComparison.isEqualTo(e3, e1));
		assertFalse(BasicComparison.isEqualTo(e2, e3));
		assertFalse(BasicComparison.isEqualTo(e3, e2));
		assertTrue(BasicComparison.isEqualTo(e1, e1));
		assertTrue(BasicComparison.isEqualTo(e2, e2));
		assertTrue(BasicComparison.isEqualTo(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isEqualTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isEqualTo(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isEqualTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isEqualTo(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isEqualTo_bothNull(){
		BasicComparison.isEqualTo(null, null);
	}
	
	@Test
	public void isNotEqualTo(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertTrue(BasicComparison.isNotEqualTo(e1, e2));
		assertTrue(BasicComparison.isNotEqualTo(e2, e1));
		assertFalse(BasicComparison.isNotEqualTo(e1, e3));
		assertFalse(BasicComparison.isNotEqualTo(e3, e1));
		assertTrue(BasicComparison.isNotEqualTo(e2, e3));
		assertTrue(BasicComparison.isNotEqualTo(e3, e2));
		assertFalse(BasicComparison.isNotEqualTo(e1, e1));
		assertFalse(BasicComparison.isNotEqualTo(e2, e2));
		assertFalse(BasicComparison.isNotEqualTo(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isNotEqualTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isNotEqualTo(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isNotEqualTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isNotEqualTo(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isNotEqualTo_bothNull(){
		BasicComparison.isNotEqualTo(null, null);
	}
	
	@Test
	public void isIdenticalTo(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertFalse(BasicComparison.isIdenticalTo(e1, e2));
		assertFalse(BasicComparison.isIdenticalTo(e2, e1));
		assertFalse(BasicComparison.isIdenticalTo(e1, e3));
		assertFalse(BasicComparison.isIdenticalTo(e3, e1));
		assertFalse(BasicComparison.isIdenticalTo(e2, e3));
		assertFalse(BasicComparison.isIdenticalTo(e3, e2));
		assertTrue(BasicComparison.isIdenticalTo(e1, e1));
		assertTrue(BasicComparison.isIdenticalTo(e2, e2));
		assertTrue(BasicComparison.isIdenticalTo(e3, e3));
	}
	
	@Test
	public void isIdenticalTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		assertFalse(BasicComparison.isIdenticalTo(null, e1));
	}
	
	@Test
	public void isIdenticalTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		assertFalse(BasicComparison.isIdenticalTo(e1, null));
	}
	
	@Test
	public void isIdenticalTo_bothNull(){
		assertTrue(BasicComparison.isIdenticalTo(null, null));
	}
	
	@Test
	public void isNotIdenticalTo(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertTrue(BasicComparison.isNotIdenticalTo(e1, e2));
		assertTrue(BasicComparison.isNotIdenticalTo(e2, e1));
		assertTrue(BasicComparison.isNotIdenticalTo(e1, e3));
		assertTrue(BasicComparison.isNotIdenticalTo(e3, e1));
		assertTrue(BasicComparison.isNotIdenticalTo(e2, e3));
		assertTrue(BasicComparison.isNotIdenticalTo(e3, e2));
		assertFalse(BasicComparison.isNotIdenticalTo(e1, e1));
		assertFalse(BasicComparison.isNotIdenticalTo(e2, e2));
		assertFalse(BasicComparison.isNotIdenticalTo(e3, e3));
	}
	
	@Test
	public void isNotIdenticalTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		assertTrue(BasicComparison.isNotIdenticalTo(null, e1));
	}
	
	@Test
	public void isNotIdenticalTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		assertTrue(BasicComparison.isNotIdenticalTo(e1, null));
	}
	
	@Test
	public void isNotIdenticalTo_bothNull(){
		assertFalse(BasicComparison.isNotIdenticalTo(null, null));
	}
	
	@Test
	public void isLessThan(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertTrue(BasicComparison.isLessThan(e1, e2));
		assertFalse(BasicComparison.isLessThan(e2, e1));
		assertFalse(BasicComparison.isLessThan(e1, e3));
		assertFalse(BasicComparison.isLessThan(e3, e1));
		assertFalse(BasicComparison.isLessThan(e2, e3));
		assertTrue(BasicComparison.isLessThan(e3, e2));
		assertFalse(BasicComparison.isLessThan(e1, e1));
		assertFalse(BasicComparison.isLessThan(e2, e2));
		assertFalse(BasicComparison.isLessThan(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThan_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isLessThan(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThan_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isLessThan(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThan_bothNull(){
		BasicComparison.isLessThan(null, null);
	}
	
	@Test
	public void isGreaterThan(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertFalse(BasicComparison.isGreaterThan(e1, e2));
		assertTrue(BasicComparison.isGreaterThan(e2, e1));
		assertFalse(BasicComparison.isGreaterThan(e1, e3));
		assertFalse(BasicComparison.isGreaterThan(e3, e1));
		assertTrue(BasicComparison.isGreaterThan(e2, e3));
		assertFalse(BasicComparison.isGreaterThan(e3, e2));
		assertFalse(BasicComparison.isGreaterThan(e1, e1));
		assertFalse(BasicComparison.isGreaterThan(e2, e2));
		assertFalse(BasicComparison.isGreaterThan(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThan_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isGreaterThan(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThan_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isGreaterThan(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThan_bothNull(){
		BasicComparison.isLessThan(null, null);
	}
	
	@Test
	public void isLessThanOrEqualTo(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertTrue(BasicComparison.isLessThanOrEqualTo(e1, e2));
		assertFalse(BasicComparison.isLessThanOrEqualTo(e2, e1));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e1, e3));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e3, e1));
		assertFalse(BasicComparison.isLessThanOrEqualTo(e2, e3));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e3, e2));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e1, e1));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e2, e2));
		assertTrue(BasicComparison.isLessThanOrEqualTo(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThanOrEqualTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isLessThanOrEqualTo(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThanOrEqualTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isLessThanOrEqualTo(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isLessThanOrEqualTo_bothNull(){
		BasicComparison.isLessThanOrEqualTo(null, null);
	}
	
	@Test
	public void isGreaterThanOrEqualTo(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		Energy e2 = new Energy(10, EnergyUnit.KILOJOULE);
		Energy e3 = new Energy(1000, EnergyUnit.JOULE);
		assertFalse(BasicComparison.isGreaterThanOrEqualTo(e1, e2));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e2, e1));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e1, e3));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e3, e1));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e2, e3));
		assertFalse(BasicComparison.isGreaterThanOrEqualTo(e3, e2));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e1, e1));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e2, e2));
		assertTrue(BasicComparison.isGreaterThanOrEqualTo(e3, e3));
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThanOrEqualTo_firstNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isGreaterThanOrEqualTo(null, e1);
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThanOrEqualTo_secondNull(){
		Energy e1 = new Energy(1, EnergyUnit.KILOJOULE);
		BasicComparison.isGreaterThanOrEqualTo(e1, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void isGreaterThanOrEqualTo_bothNull(){
		BasicComparison.isLessThanOrEqualTo(null, null);
	}
	
	@Test
	public void isValidBasicComparison(){
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.E));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.NE));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.I));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.NI));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.L));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.G));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.LE));
		assertTrue(BasicComparison.isValidBasicComparison(BasicComparison.GE));
		assertFalse(BasicComparison.isValidBasicComparison(null));
	}
	
	@Test
	public void getNbOfBasicComparisons(){
		assertEquals(BasicComparison.getNbOfBasicComparisons(),8);
	}
	
	@Test
	public void getAllBasicComparisons(){
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.E));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.NE));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.I));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.NI));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.L));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.G));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.LE));
		assertTrue(BasicComparison.getAllBasicComparisons().contains(BasicComparison.GE));
		assertEquals(BasicComparison.getAllBasicComparisons().size(),BasicComparison.getNbOfBasicComparisons());
	}
}
