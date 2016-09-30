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
import roborally.model.inventory.item.Battery;
import roborally.program.conditions.CanHitRobotCondition;

/**
 * A test class for can hit robot conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class CanHitRobotConditionTest {

	private Robot rob;
	private Board b;
	
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		rob.terminate();
		assertTrue(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob));
	}
	
	@Test
	public void result_validCase_robotplusitems() {
		@SuppressWarnings("unused")
		Battery bat = new Battery(b, new Position(10, 5));
		@SuppressWarnings("unused")
		Battery bat2 = new Battery(b, new Position(10, 5));
		@SuppressWarnings("unused")
		Robot rob2 = new Robot(b, new Position(10, 5));
		assertTrue(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase_pathBlocked() {
		@SuppressWarnings("unused")
		Battery bat = new Battery(b, new Position(9, 5));
		@SuppressWarnings("unused")
		Battery bat2 = new Battery(b, new Position(10, 5));
		@SuppressWarnings("unused")
		Robot rob2 = new Robot(b, new Position(10, 5));
		assertFalse(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase_noTarget() {
		@SuppressWarnings("unused")
		Robot rob2 = new Robot(b, new Position(4, 5));
		assertFalse(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase_robotNotOnBoard() {
		Robot rob2 = new Robot();
		assertFalse(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob2));
	}
	
	@Test
	public void result_invalidCase_robotNotEnoughEnergy() {
		Robot rob2 = new Robot(b, new Position(20, 20), new Accu(Energy.WS_0), Direction.UP);
		assertFalse(CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.result(rob2));
	}
	
	@Test
	public void toString_normalCase(){
		assertEquals("can-hit-robot", CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION.toString());
	}
}
