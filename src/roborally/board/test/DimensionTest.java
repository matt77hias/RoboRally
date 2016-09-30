package roborally.board.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Dimension;

/**
 * A test class for dimension objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class DimensionTest {

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
	public void getDimensionnr(){
		assertEquals(Dimension.HORIZONTAL.getDimensionnr(), 1);
		assertEquals(Dimension.VERTICAL.getDimensionnr(), 2);
	}
	
	@Test
	public void isValidDimension(){
		assertTrue(Dimension.isValidDimension(Dimension.HORIZONTAL));
		assertTrue(Dimension.isValidDimension(Dimension.VERTICAL));
		assertFalse(Dimension.isValidDimension(null));
	}
	
	@Test
	public void correspondsToExistingDimension(){
		assertTrue(Dimension.correspondsToExistingDimension(1));
		assertTrue(Dimension.correspondsToExistingDimension(2));
		assertFalse(Dimension.correspondsToExistingDimension(0));
	}
	
	@Test
	public void getAllDimensions(){
		assertTrue(Dimension.getAllDimensions().contains(Dimension.HORIZONTAL));
		assertTrue(Dimension.getAllDimensions().contains(Dimension.VERTICAL));
		assertFalse(Dimension.getAllDimensions().contains(null));
		assertEquals(Dimension.getAllDimensions().size(),Dimension.values().length);
	}
	
	@Test
	public void getNbOfDimensions(){
		assertEquals(Dimension.getNbOfDimensions(),Dimension.values().length);
	}
	
	@Test
	public void dimensionFromInt(){
		assertEquals(Dimension.dimensionFromInt(1),Dimension.HORIZONTAL);
		assertEquals(Dimension.dimensionFromInt(2),Dimension.VERTICAL);
		assertEquals(Dimension.dimensionFromInt(0),Dimension.HORIZONTAL);
	}
}
