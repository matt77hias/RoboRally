package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.conditiontester.BasicComparison;
import roborally.conditiontester.EnergyTester;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.model.inventory.item.Battery;
import roborally.model.staticObject.Wall;

/**
 * A test class for in energy tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class EnergyTesterTest {

	@Test
	public void construct1(){
		EnergyTester et = new EnergyTester();
		assertEquals(et.getBasicComparison(), EnergyTester.STANDARD_BASIC_COMPARISON);
		assertEquals(et.getCompareOperand(), EnergyTester.STANDARD_COMPARE_OPERAND);
	}
	
	@Test
	public void construct2(){
		EnergyTester et = new EnergyTester(BasicComparison.L, new Energy(1000));
		assertEquals(et.getBasicComparison(), BasicComparison.L);
		assertEquals(et.getCompareOperand(), new Energy(1000));
	}
	
	@Test
	public void construct2_totalProgrammingCheck(){
		EnergyTester et1 = new EnergyTester(null, new Energy(1000));
		assertEquals(et1.getBasicComparison(), EnergyTester.STANDARD_BASIC_COMPARISON);
		assertEquals(et1.getCompareOperand(), new Energy(1000));
		
		EnergyTester et2 = new EnergyTester(BasicComparison.L, null);
		assertEquals(et2.getBasicComparison(), BasicComparison.L);
		assertEquals(et2.getCompareOperand(), EnergyTester.STANDARD_COMPARE_OPERAND);
		
		EnergyTester et3 = new EnergyTester(BasicComparison.L, new Energy(-1000));
		assertEquals(et3.getBasicComparison(), BasicComparison.L);
		assertEquals(et3.getCompareOperand(), new Energy(-1000));
	}
	
	@Test
	public void getBasicComparison(){
		EnergyTester et = new EnergyTester(BasicComparison.L, new Energy(1000));
		assertEquals(et.getBasicComparison(), BasicComparison.L);
	}
	
	@Test
	public void testCondition(){
		EnergyTester et = new EnergyTester(BasicComparison.L, new Energy(1000));
		assertFalse(et.testCondition(null));
		assertFalse(et.testCondition(new Wall()));
		assertFalse(et.testCondition(new Robot(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(1000), new Energy(2000)), null)));
		assertFalse(et.testCondition(new Robot(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(2000), new Energy(2000)), null)));
		assertTrue(et.testCondition(new Robot(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(900), new Energy(2000)), null)));
		assertTrue(et.testCondition(new Robot(new Accu(new Energy(900), new Energy(2000)))));
		assertTrue(et.testCondition(new Battery(new Accu(new Energy(900), new Energy(2000)))));
	}
}
