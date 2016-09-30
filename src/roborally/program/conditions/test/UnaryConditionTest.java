package roborally.program.conditions.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.program.conditions.*;

/**
 * A test class for unary conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class UnaryConditionTest {
	
	private UnaryCondition uc;
	private static Condition sub1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sub1 = WallCondition.WALL_CONDITION;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		uc = new NotCondition(sub1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase() {
		UnaryCondition someUC = new NotCondition(sub1);
		assertSame(sub1, someUC.getSubCondition());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_illegalSub(){
		@SuppressWarnings("unused")
		UnaryCondition someUC = new NotCondition(null);
	}
	
	@Test
	public void getNbSubElements_normalCase(){
		assertEquals(1, uc.getNbSubElements());
	}
	
	@Test
	public void getSubElementAt_normalCase(){
		assertSame(uc.getSubCondition(), uc.getSubElementAt(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_lowerBoundExceeded(){
		uc.getSubElementAt(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_upperBoundExceeded(){
		uc.getSubElementAt(2);
	}

}
