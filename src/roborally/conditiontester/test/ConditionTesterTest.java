package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.conditiontester.CombinedTester;
import roborally.conditiontester.ConditionTester;
import roborally.conditiontester.EnergyTester;
import roborally.conditiontester.InSubrangeTester;
import roborally.conditiontester.TypeTester;
import roborally.conditiontester.WeightTester;
import roborally.model.dynamicObject.Robot;

/**
 * A test class for condition tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ConditionTesterTest {

	@Test
	public void testCondition_nullBoardModel() {
		ConditionTester et = new EnergyTester();
		ConditionTester wt = new WeightTester();
		ConditionTester ist = new InSubrangeTester(null, 0L);
		ConditionTester ct = new CombinedTester();
		ConditionTester tt = new TypeTester(Robot.class);
		
		assertFalse(et.testCondition(null));
		assertFalse(wt.testCondition(null));
		assertFalse(ist.testCondition(null));
		assertFalse(ct.testCondition(null));
		assertFalse(tt.testCondition(null));
	}
}