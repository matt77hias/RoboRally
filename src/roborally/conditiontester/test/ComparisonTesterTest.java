package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.conditiontester.BasicComparison;
import roborally.conditiontester.EnergyTester;
import roborally.conditiontester.WeightTester;
import roborally.model.energy.Energy;
import roborally.model.weight.Weight;

/**
 * A test class for in comparison tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ComparisonTesterTest {

	@Test
	public void construct_totalProgrammingCheck(){
		WeightTester wt1 = new WeightTester(null, new Weight(1000));
		assertEquals(wt1.getBasicComparison(), WeightTester.STANDARD_BASIC_COMPARISON);
		assertEquals(wt1.getCompareOperand(), new Weight(1000));
	}
	
	@Test
	public void getCompareOperand(){
		EnergyTester et = new EnergyTester(BasicComparison.L, new Energy(1000));
		assertEquals(et.getCompareOperand(), new Energy(1000));
		WeightTester wt = new WeightTester(BasicComparison.L, new Weight(1000));
		assertEquals(wt.getCompareOperand(), new Weight(1000));
	}

}
