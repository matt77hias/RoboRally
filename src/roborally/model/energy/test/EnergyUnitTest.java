package roborally.model.energy.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.energy.EnergyUnit;

/**
 * A test class for energy units. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class EnergyUnitTest {

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
	public void getSymbol(){
		assertEquals(EnergyUnit.WS.getSymbol(),"Ws");
		assertEquals(EnergyUnit.KWH.getSymbol(),"kWh");
		assertEquals(EnergyUnit.JOULE.getSymbol(),"J");
		assertEquals(EnergyUnit.KILOJOULE.getSymbol(),"kJ");
	}
	
	@Test
	public void isValidEnergyUnit(){
		assertTrue(EnergyUnit.isValidEnergyUnit(EnergyUnit.WS));
		assertTrue(EnergyUnit.isValidEnergyUnit(EnergyUnit.KWH));
		assertTrue(EnergyUnit.isValidEnergyUnit(EnergyUnit.JOULE));
		assertTrue(EnergyUnit.isValidEnergyUnit(EnergyUnit.KILOJOULE));
		assertFalse(EnergyUnit.isValidEnergyUnit(null));
	}
	
	@Test
	public void toEnergyUnit(){
		assertEquals(EnergyUnit.WS.toEnergyUnit(EnergyUnit.WS),1D,0.01D);
		assertEquals(EnergyUnit.WS.toEnergyUnit(EnergyUnit.KWH),1/3600000D,0.0000001D);
		assertEquals(EnergyUnit.WS.toEnergyUnit(EnergyUnit.JOULE),1D,0.01D);
		assertEquals(EnergyUnit.WS.toEnergyUnit(EnergyUnit.KILOJOULE),1/1000D,0.001D);
		assertEquals(EnergyUnit.KWH.toEnergyUnit(EnergyUnit.WS),3600000D,0.01D);
		assertEquals(EnergyUnit.KWH.toEnergyUnit(EnergyUnit.KWH),1D,0.01D);
		assertEquals(EnergyUnit.KWH.toEnergyUnit(EnergyUnit.JOULE),3600000D,0.01D);
		assertEquals(EnergyUnit.KWH.toEnergyUnit(EnergyUnit.KILOJOULE),3600D,0.01D);
		
		assertEquals(EnergyUnit.JOULE.toEnergyUnit(EnergyUnit.WS),1,0.001D);
		assertEquals(EnergyUnit.JOULE.toEnergyUnit(EnergyUnit.KWH),1/3600000D,0.0000001D);
		assertEquals(EnergyUnit.JOULE.toEnergyUnit(EnergyUnit.JOULE),1D,0.01D);
		assertEquals(EnergyUnit.JOULE.toEnergyUnit(EnergyUnit.KILOJOULE),1/1000D,0.01D);
		assertEquals(EnergyUnit.KILOJOULE.toEnergyUnit(EnergyUnit.WS),1000,0.01D);
		assertEquals(EnergyUnit.KILOJOULE.toEnergyUnit(EnergyUnit.KWH),1/3600D,0.01D);
		assertEquals(EnergyUnit.KILOJOULE.toEnergyUnit(EnergyUnit.JOULE),1000D,0.01D);
		assertEquals(EnergyUnit.KILOJOULE.toEnergyUnit(EnergyUnit.KILOJOULE),1D,0.01D);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void toEnergyUnit_inValidEnergyUnitRejectedI(){
		EnergyUnit.WS.toEnergyUnit(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void toEnergyUnit_inValidEnergyUnitRejectedII(){
		EnergyUnit.KWH.toEnergyUnit(null);
	}
}
