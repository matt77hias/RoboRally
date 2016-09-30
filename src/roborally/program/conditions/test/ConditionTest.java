package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Energy;
import roborally.program.conditions.Condition;
import roborally.program.conditions.EnergyAtleastCondition;

/**
 * A test class for conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ConditionTest {
	
	private Condition someCondition;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		someCondition = new EnergyAtleastCondition(Energy.WS_0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(someCondition.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		Robot robot = new Robot();
		robot.terminate();
		assertTrue(someCondition.result(robot));
	}
}
