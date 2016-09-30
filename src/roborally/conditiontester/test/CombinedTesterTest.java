package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.conditiontester.CombinedTester;
import roborally.conditiontester.TypeTester;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.Battery;

/**
 * A test class for combined tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class CombinedTesterTest {

	@Test
	public void isValidConditionTester(){
		assertTrue(CombinedTester.isValidConditionTester(new TypeTester(BoardModel.class)));
		assertTrue(CombinedTester.isValidConditionTester(new CombinedTester()));
		assertFalse(CombinedTester.isValidConditionTester(null));
	}
	
	@Test
	public void hasProperConditionTesters(){
		CombinedTester ct = new CombinedTester();
		assertTrue(ct.hasProperConditionTesters());
		TypeTester tt = new TypeTester(BoardModel.class);
		ct.addConditionTester(tt);
		assertTrue(ct.hasProperConditionTesters());
		CombinedTester ct2 = new CombinedTester();
		ct.addConditionTester(ct2);
		assertTrue(ct.hasProperConditionTesters());
		ct.removeConditionTester(tt);
		assertTrue(ct.hasProperConditionTesters());
		ct.removeConditionTester(ct2);
		assertTrue(ct.hasProperConditionTesters());
	}

	@Test
	public void containsConditionTester(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		CombinedTester ct2 = new CombinedTester();
		assertFalse(ct.containsConditionTester(tt));
		assertFalse(ct.containsConditionTester(ct2));
		ct.addConditionTester(tt);
		assertTrue(ct.containsConditionTester(tt));
		assertFalse(ct.containsConditionTester(ct2));
		ct.addConditionTester(ct2);
		assertTrue(ct.containsConditionTester(tt));
		assertTrue(ct.containsConditionTester(ct2));
		ct.removeConditionTester(ct2);
		assertTrue(ct.containsConditionTester(tt));
		assertFalse(ct.containsConditionTester(ct2));
		ct.removeConditionTester(tt);
		assertFalse(ct.containsConditionTester(tt));
		assertFalse(ct.containsConditionTester(ct2));
	}
	
	@Test
	public void addConditionTester(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		CombinedTester ct2 = new CombinedTester();
		ct.addConditionTester(tt);
		ct.addConditionTester(ct2);
		assertTrue(ct.containsConditionTester(tt));
		assertTrue(ct.containsConditionTester(ct2));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addConditionTester_null(){
		CombinedTester ct = new CombinedTester();
		ct.addConditionTester(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addConditionTester_selfReference(){
		CombinedTester ct = new CombinedTester();
		ct.addConditionTester(ct);
	}
	
	@Test
	public void removeConditionTester(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		CombinedTester ct2 = new CombinedTester();
		ct.addConditionTester(tt);
		ct.addConditionTester(ct2);
		assertTrue(ct.containsConditionTester(tt));
		assertTrue(ct.containsConditionTester(ct2));
		ct.removeConditionTester(tt);
		ct.removeConditionTester(ct2);
		assertFalse(ct.containsConditionTester(tt));
		assertFalse(ct.containsConditionTester(ct2));
	}
	
	@Test
	public void removeConditionTester_notOwned(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		ct.addConditionTester(tt);
		ct.removeConditionTester(tt);
		ct.removeConditionTester(null);
		ct.removeConditionTester(ct);
	}
	
	@Test
	public void getNbOfConditionTesters(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		CombinedTester ct2 = new CombinedTester();
		assertEquals(ct.getNbOfConditionTesters(),0);
		ct.addConditionTester(tt);
		assertEquals(ct.getNbOfConditionTesters(),1);
		ct.addConditionTester(ct2);
		assertEquals(ct.getNbOfConditionTesters(),2);
		ct.removeConditionTester(tt);
		assertEquals(ct.getNbOfConditionTesters(),1);
		ct.removeConditionTester(ct2);
		assertEquals(ct.getNbOfConditionTesters(),0);
	}
	
	@Test
	public void getAllConditionTesters(){
		CombinedTester ct = new CombinedTester();
		TypeTester tt = new TypeTester(BoardModel.class);
		CombinedTester ct2 = new CombinedTester();
		ct.addConditionTester(tt);
		ct.addConditionTester(ct2);
		assertEquals(ct.getAllConditionTesters().size(),2);
		assertTrue(ct.getAllConditionTesters().contains(tt));
		assertTrue(ct.getAllConditionTesters().contains(ct2));
	}
	
	@Test
	public void testCondition(){
		CombinedTester ct = new CombinedTester();
		BoardModel r = new Robot();
		assertFalse(ct.testCondition(null));
		assertTrue(ct.testCondition(r));
		ct.addConditionTester(new TypeTester(Robot.class));
		assertFalse(ct.testCondition(null));
		assertTrue(ct.testCondition(r));
		CombinedTester ct2 = new CombinedTester();
		ct.addConditionTester(ct2);
		assertFalse(ct.testCondition(null));
		assertTrue(ct.testCondition(r));
		ct2.addConditionTester(new TypeTester(Battery.class));
		assertFalse(ct.testCondition(null));
		assertFalse(ct.testCondition(r));
	}
}
