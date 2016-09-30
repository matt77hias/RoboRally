package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.*;
import roborally.program.conditions.EnergyAtleastCondition;

/**
 * A test class for energy at least conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class EnergyAtleastConditionTest {

	private Robot rob;
	private Board b;
	private EnergyAtleastCondition eac;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(200, 200);
		rob = new Robot(b, new Position(5, 5), new Accu(new Energy(5000)), Direction.RIGHT);
		eac = new EnergyAtleastCondition(new Energy(500));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(eac.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		rob.terminate();
		assertTrue(eac.result(rob));
	}
	
	@Test
	public void result_validCase_higherThan() {
		assertTrue(eac.result(rob));
	}
	
	@Test
	public void result_validCase_equals() {
		@SuppressWarnings("unused")
		Robot rob2 = new Robot(b, new Position(10, 5), new Accu(new Energy(500)), Direction.DOWN);
		assertTrue(eac.result(rob));
	}
	
	@Test
	public void result_invalidCase_lowerThan() {
		Robot rob2 = new Robot(b, new Position(10, 5), new Accu(new Energy(400)), Direction.DOWN);
		assertFalse(eac.result(rob2));
	}
	
	@Test
	public void toString_normalCase(){
		assertEquals("energy-at-least " + eac.getParameter().getEnergyAmount(), eac.toString());
	}

}
