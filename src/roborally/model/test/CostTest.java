package roborally.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.Cost;

/**
 * A test class for cost objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class CostTest {

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
	public void isValidCost(){
		assertTrue(Cost.isValidCost(Cost.MOVE));
		assertTrue(Cost.isValidCost(Cost.TURN));
		assertTrue(Cost.isValidCost(Cost.SHOOT));
		assertFalse(Cost.isValidCost(null));
	}
	
	@Test
	public void getAllCosts(){
		assertEquals(Cost.getAllCosts().size(),3);
		assertTrue(Cost.getAllCosts().contains(Cost.MOVE));
		assertTrue(Cost.getAllCosts().contains(Cost.TURN));
		assertTrue(Cost.getAllCosts().contains(Cost.SHOOT));
		assertFalse(Cost.getAllCosts().contains(null));
	}

	@Test
	public void getNbOfCosts(){
		assertEquals(Cost.getNbOfCosts(),3);
	}
}