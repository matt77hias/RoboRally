package roborally.model.weight.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.weight.WeightUnit;

/**
 * A test class for weight units. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WeightUnitTest {

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
		assertEquals(WeightUnit.G.getSymbol(),"g");
		assertEquals(WeightUnit.KG.getSymbol(),"kg");
	}
	
	@Test
	public void isValidWeightUnit(){
		assertTrue(WeightUnit.isValidWeightUnit(WeightUnit.G));
		assertTrue(WeightUnit.isValidWeightUnit(WeightUnit.KG));
		assertFalse(WeightUnit.isValidWeightUnit(null));
	}
	
	@Test
	public void toWeightUnit(){
		assertEquals(WeightUnit.G.toWeightUnit(WeightUnit.G),1D,0.01D);
		assertEquals(WeightUnit.G.toWeightUnit(WeightUnit.KG),0.001D,0.0001D);
		assertEquals(WeightUnit.KG.toWeightUnit(WeightUnit.G),1000D,0.01D);
		assertEquals(WeightUnit.KG.toWeightUnit(WeightUnit.KG),1D,0.01D);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void toWeightUnit_inValidWeightUnitRejectedI(){
		WeightUnit.G.toWeightUnit(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void toWeightUnit_inValidWeightUnitRejectedII(){
		WeightUnit.KG.toWeightUnit(null);
	}
}
