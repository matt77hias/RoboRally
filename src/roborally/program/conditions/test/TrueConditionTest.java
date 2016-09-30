package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.dynamicObject.Robot;
import roborally.program.conditions.TrueCondition;

/**
 * A test class for true conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TrueConditionTest {

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
	public void result_normalCase() {
		assertTrue(TrueCondition.TRUE_CONDITION.result(new Robot()));
		assertTrue(TrueCondition.TRUE_CONDITION.result(null));
	}
	
	@Test
	public void toString_normalCase(){
		assertEquals("true", TrueCondition.TRUE_CONDITION.toString());
	}

}
