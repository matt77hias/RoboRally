package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.program.conditions.*;

/**
 * A test class for binary conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BinaryConditionTest {
	
	private BinaryCondition bc;
	private static Condition sub1, sub2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sub1 = WallCondition.WALL_CONDITION;
		sub2 = AtItemCondition.AT_ITEM_CONDITION;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bc = new AndCondition(sub1, sub2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase() {
		BinaryCondition someBC = new OrCondition(sub1, sub2);
		assertSame(sub1, someBC.getFirstSubCondition());
		assertSame(sub2, someBC.getSecondSubCondition());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_illegalFirst(){
		@SuppressWarnings("unused")
		BinaryCondition someBC = new OrCondition(null, sub2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_illegalSecond(){
		@SuppressWarnings("unused")
		BinaryCondition someBC = new OrCondition(sub1, null);
	}
	
	@Test
	public void getNbSubElements_normalCase(){
		assertEquals(2, bc.getNbSubElements());
	}
	
	@Test
	public void getSubElementAt_normalCase(){
		assertSame(bc.getFirstSubCondition(), bc.getSubElementAt(1));
		assertSame(bc.getSecondSubCondition(), bc.getSubElementAt(2));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_lowerBoundExceeded(){
		bc.getSubElementAt(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_upperBoundExceeded(){
		bc.getSubElementAt(3);
	}

}
