package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.RepairKit;
import roborally.model.inventory.item.SurpriseBox;
import roborally.program.conditions.AtItemCondition;

/**
 * A test class for at item conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class AtItemConditionTest {

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
		rob = new Robot(b, new Position(5, 5));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=IllegalStateException.class)
	public void result_robotNull() {
		assertTrue(AtItemCondition.AT_ITEM_CONDITION.result(null));
	}
	
	@Test(expected=IllegalStateException.class)
	public void result_robotTerminated() {
		rob.terminate();
		assertTrue(AtItemCondition.AT_ITEM_CONDITION.result(rob));
	}
	
	@Test
	public void result_validCase_battery() {
		@SuppressWarnings("unused")
		Battery bat = new Battery(b, new Position(5, 5));
		assertTrue(AtItemCondition.AT_ITEM_CONDITION.result(rob));
	}
	
	@Test
	public void result_validCase_repairkit() {
		@SuppressWarnings("unused")
		RepairKit rk = new RepairKit(b, new Position(5, 5));
		assertTrue(AtItemCondition.AT_ITEM_CONDITION.result(rob));
	}
	
	@Test
	public void result_validCase_surprisebox() {
		@SuppressWarnings("unused")
		SurpriseBox sb = new SurpriseBox(b, new Position(5, 5));
		assertTrue(AtItemCondition.AT_ITEM_CONDITION.result(rob));
	}
	
	@Test
	public void result_inValidCase() {
		assertFalse(AtItemCondition.AT_ITEM_CONDITION.result(rob));
	}
	
	@Test
	public void result_invalidCase_robotNotOnBoard() {
		Robot rob2 = new Robot();
		assertFalse(AtItemCondition.AT_ITEM_CONDITION.result(rob2));
	}
	
	@Test
	public void toString_normalCase(){
		assertEquals("at-item", AtItemCondition.AT_ITEM_CONDITION.toString());
	}

}
