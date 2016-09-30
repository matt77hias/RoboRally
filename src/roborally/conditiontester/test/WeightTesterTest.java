package roborally.conditiontester.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.conditiontester.BasicComparison;
import roborally.conditiontester.WeightTester;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.model.inventory.item.Battery;
import roborally.model.staticObject.Wall;
import roborally.model.weight.Weight;

/**
 * A test class for in weight tester objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WeightTesterTest {

	@Test
	public void construct1(){
		WeightTester wt = new WeightTester();
		assertEquals(wt.getBasicComparison(), WeightTester.STANDARD_BASIC_COMPARISON);
		assertEquals(wt.getCompareOperand(), WeightTester.STANDARD_COMPARE_OPERAND);
	}
	
	@Test
	public void construct2(){
		WeightTester wt = new WeightTester(BasicComparison.L, new Weight(1000));
		assertEquals(wt.getBasicComparison(), BasicComparison.L);
		assertEquals(wt.getCompareOperand(), new Weight(1000));
	}
	
	@Test
	public void construct2_totalProgrammingCheck(){
		WeightTester wt1 = new WeightTester(null, new Weight(1000));
		assertEquals(wt1.getBasicComparison(), WeightTester.STANDARD_BASIC_COMPARISON);
		assertEquals(wt1.getCompareOperand(), new Weight(1000));
		
		WeightTester wt2 = new WeightTester(BasicComparison.L, null);
		assertEquals(wt2.getBasicComparison(), BasicComparison.L);
		assertEquals(wt2.getCompareOperand(), WeightTester.STANDARD_COMPARE_OPERAND);
		
		WeightTester wt3 = new WeightTester(BasicComparison.L, new Weight(-1000));
		assertEquals(wt3.getBasicComparison(), BasicComparison.L);
		assertEquals(wt3.getCompareOperand(), new Weight(-1000));
	}
	
	@Test
	public void getBasicComparison(){
		WeightTester wt = new WeightTester(BasicComparison.L, new Weight(1000));
		assertEquals(wt.getBasicComparison(), BasicComparison.L);
	}
	
	@Test
	public void testCondition(){
		WeightTester wt = new WeightTester(BasicComparison.L, new Weight(1000));
		assertFalse(wt.testCondition(null));
		assertFalse(wt.testCondition(new Wall()));
		
		assertFalse(wt.testCondition(new Battery(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(1000), new Energy(2000)), new Weight(1000))));
		assertFalse(wt.testCondition(new Battery(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(1000), new Energy(2000)), new Weight(2000))));
		assertTrue(wt.testCondition(new Battery(new Board(10L,11L), new Position(4L,5L), new Accu(new Energy(1000), new Energy(2000)), new Weight(900))));
	}
}
