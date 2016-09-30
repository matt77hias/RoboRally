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
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.program.conditions.Condition;
import roborally.program.conditions.EnergyAtleastCondition;
import roborally.program.conditions.NotCondition;
import roborally.program.conditions.WallCondition;

/**
 * A test class for not conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class NotConditionTest {
	
	private Board b;
	private Robot rob;
	private NotCondition notConditionTrue, notConditionFalse;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(20, 20);
		rob = new Robot(b, new Position(10, 10), new Accu(new Energy(5000)), Direction.UP);
		notConditionTrue = new NotCondition(new EnergyAtleastCondition(Energy.WS_0));
		notConditionFalse = new NotCondition(WallCondition.WALL_CONDITION);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase(){
		Condition cond1 = new EnergyAtleastCondition(new Energy(5000));
		NotCondition someNotCondition = new NotCondition(cond1);
		assertSame(cond1, someNotCondition.getSubCondition());
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(notConditionTrue.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		Robot robot = new Robot();
		robot.terminate();
		assertTrue(notConditionTrue.result(robot));
	}
	
	@Test
	public void result_andConditionTrueResultsFalse() {
		assertFalse(notConditionTrue.result(rob));
	}

	@Test
	public void result_andConditionFalseResultsTrue() {
		assertTrue(notConditionFalse.result(rob));
	}
	
	@Test
	public void getElementSymbol(){
		assertEquals("not", notConditionTrue.getElementSymbol());
	}

}
