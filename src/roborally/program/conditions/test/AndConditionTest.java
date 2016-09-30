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
import roborally.program.conditions.AndCondition;
import roborally.program.conditions.AtItemCondition;
import roborally.program.conditions.Condition;
import roborally.program.conditions.EnergyAtleastCondition;

/**
 * A test class for and conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class AndConditionTest {
	
	private Board b;
	private Robot rob;
	private AndCondition andConditionTrueTrue, andConditionTrueFalse, andConditionFalseTrue, andConditionFalseFalse;

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
		andConditionTrueTrue = new AndCondition(new EnergyAtleastCondition(Energy.WS_0), new EnergyAtleastCondition(new Energy(5000)));
		andConditionTrueFalse = new AndCondition(new EnergyAtleastCondition(Energy.WS_0), new EnergyAtleastCondition(new Energy(6000)));
		andConditionFalseTrue = new AndCondition(AtItemCondition.AT_ITEM_CONDITION, new EnergyAtleastCondition(new Energy(5000)));
		andConditionFalseFalse = new AndCondition(AtItemCondition.AT_ITEM_CONDITION, new EnergyAtleastCondition(new Energy(6000)));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase(){
		Condition cond1 = new EnergyAtleastCondition(new Energy(5000));
		Condition cond2 = AtItemCondition.AT_ITEM_CONDITION;
		AndCondition someAndCondition = new AndCondition(cond1, cond2);
		assertSame(cond1, someAndCondition.getFirstSubCondition());
		assertSame(cond2, someAndCondition.getSecondSubCondition());
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(andConditionTrueTrue.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		Robot robot = new Robot();
		robot.terminate();
		assertTrue(andConditionTrueTrue.result(robot));
	}
	
	@Test
	public void result_andConditionTrueTrueResultsTrue() {
		assertTrue(andConditionTrueTrue.result(rob));
	}

	@Test
	public void result_andConditionTrueFalseResultsFalse() {
		assertFalse(andConditionTrueFalse.result(rob));
	}

	@Test
	public void result_andConditionFalseTrueResultsFalse() {
		assertFalse(andConditionFalseTrue.result(rob));
	}

	@Test
	public void result_andConditionFalseFalseResultsFalse() {
		assertFalse(andConditionFalseFalse.result(rob));
	}
	
	@Test
	public void getElementSymbol(){
		assertEquals("and", andConditionFalseFalse.getElementSymbol());
	}

}
