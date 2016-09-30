package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.conditiontester.InSubrangeTester;
import roborally.model.dynamicObject.Robot;

/**
 * A test class for in subrange tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InSubrangeTesterTest {

	@Test
	public void construct(){
		InSubrangeTester ist = new InSubrangeTester(new Position(5L,6L), 10L);
		assertEquals(ist.getPosition(),new Position(5L,6L));
		assertEquals(ist.getRange(),10L);
	}
	
	@Test
	public void construct_totalProgrammingCheck(){
		InSubrangeTester ist1 = new InSubrangeTester(new Position(5L,6L), -10L);
		assertEquals(ist1.getPosition(),new Position(5L,6L));
		assertEquals(ist1.getRange(),10L);
		
		InSubrangeTester ist2 = new InSubrangeTester(null, 10L);
		assertEquals(ist2.getPosition(), new Position(0L,0L));
		assertEquals(ist2.getRange(),10L);
		
		InSubrangeTester ist3 = new InSubrangeTester(new Position(-5L,-6L), 10L);
		assertEquals(ist3.getPosition(),new Position(-5L,-6L));
		assertEquals(ist3.getRange(),10L);
	}
	
	@Test
	public void getRange(){
		InSubrangeTester ist = new InSubrangeTester(new Position(5L,6L), 10L);
		assertEquals(ist.getRange(),10L);
	}
	
	@Test
	public void getPosition(){
		InSubrangeTester ist = new InSubrangeTester(new Position(5L,6L), 10L);
		assertEquals(ist.getPosition(),new Position(5L,6L));
	}
	
	@Test
	public void testCondition(){
		InSubrangeTester ist = new InSubrangeTester(new Position(5L,6L), 10L);
		assertFalse(ist.testCondition(null));
		assertFalse(ist.testCondition(new Robot(null, null)));
		assertTrue(ist.testCondition(new Robot(new Board(10L,10L), new Position(5L,6L))));
		assertTrue(ist.testCondition(new Robot(new Board(10L,10L), new Position(10L,6L))));
		assertTrue(ist.testCondition(new Robot(new Board(10L,10L), new Position(0L,0L))));
	}
}
