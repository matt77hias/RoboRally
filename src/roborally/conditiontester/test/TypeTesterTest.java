package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.conditiontester.TypeTester;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.EnergyModel;
import roborally.model.inventory.Collector;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.Battery;

/**
 * A test class for type tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TypeTesterTest {

	@Test
	public void construct() {
		TypeTester tt = new TypeTester(Robot.class);
		assertEquals(tt.getTestClass(), Robot.class);
	}
	
	@Test
	public void construct_totalProgrammingCheck() {
		TypeTester tt = new TypeTester(null);
		assertEquals(tt.getTestClass(), BoardModel.class);
	}
	
	@Test
	public void testCondition(){
		BoardModel r = new Robot();
		TypeTester tt1 = new TypeTester(Robot.class);
		TypeTester tt2 = new TypeTester(BoardModel.class);
		TypeTester tt3 = new TypeTester(Collector.class);
		TypeTester tt4 = new TypeTester(InventoryUser.class);
		TypeTester tt5 = new TypeTester(EnergyModel.class);
		TypeTester tt6 = new TypeTester(Object.class);
		TypeTester tt7 = new TypeTester(Battery.class);
		
		assertTrue(tt1.testCondition(r));
		assertTrue(tt2.testCondition(r));
		assertTrue(tt3.testCondition(r));
		assertTrue(tt4.testCondition(r));
		assertTrue(tt5.testCondition(r));
		assertTrue(tt6.testCondition(r));
		assertFalse(tt7.testCondition(r));
		
		assertFalse(tt1.testCondition(null));
		assertFalse(tt2.testCondition(null));
		assertFalse(tt3.testCondition(null));
		assertFalse(tt4.testCondition(null));
		assertFalse(tt5.testCondition(null));
		assertFalse(tt6.testCondition(null));
		assertFalse(tt7.testCondition(null));
	}
}
