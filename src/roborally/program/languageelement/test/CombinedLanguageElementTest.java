package roborally.program.languageelement.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.program.conditions.*;
import roborally.program.languageelement.CombinedLanguageElement;

/**
 * A test class for combined language element objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class CombinedLanguageElementTest {

	private CombinedLanguageElement<Condition> cle;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cle = new AndCondition(TrueCondition.TRUE_CONDITION, TrueCondition.TRUE_CONDITION);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void canHaveAsNbSubElements_notPositive() {
		assertFalse(cle.canHaveAsNbSubElements(-1));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_lowerBound() {
		cle.getSubElementAt(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_upperBound() {
		cle.getSubElementAt(cle.getNbSubElements()+1);
	}
	
	@Test
	public void hasAsSubElement_selfReference(){
		assertTrue(cle.hasAsSubElement(cle));
	}
	
	@Test
	public void hasAsSubElement_subCommand(){
		assertTrue(cle.hasAsSubElement(TrueCondition.TRUE_CONDITION));
	}
	
	@Test
	public void hasAsSubElement_invalid(){
		assertFalse(cle.hasAsSubElement(WallCondition.WALL_CONDITION));
		assertFalse(cle.hasAsSubElement(null));
	}
	
	@Test
	public void canHaveAsSubElement_allInput(){
		assertFalse(cle.canHaveAsSubElement(null));
		assertTrue(cle.canHaveAsSubElement(WallCondition.WALL_CONDITION));
		NotCondition notc = new NotCondition((AndCondition)cle);
		assertFalse(cle.canHaveAsSubElement(notc));
	}
	
	@Test
	public void getElementSymbol_normalCase(){
		assertTrue(cle.getElementSymbol().length() > 0);
	}
	
	@Test
	public void terminate(){
		cle.terminate();
		for (int i = 0; i < cle.getNbSubElements(); i++){
			assertSame(cle.getSubElementAt(i+1), null);
		}
	}
	
	@Test
	public void terminate_recursion(){
		AndCondition andc = new AndCondition(WallCondition.WALL_CONDITION, WallCondition.WALL_CONDITION);
		AndCondition andc2 = new AndCondition(WallCondition.WALL_CONDITION, WallCondition.WALL_CONDITION);
		OrCondition orc = new OrCondition(andc, andc2);
		orc.terminate();
		assertTrue(andc.isTerminated());
		assertTrue(andc2.isTerminated());
		for (int i = 0; i < andc.getNbSubElements(); i++){
			assertSame(andc.getSubElementAt(i+1), null);
		}
		for (int i = 0; i < andc2.getNbSubElements(); i++){
			assertSame(andc2.getSubElementAt(i+1), null);
		}
		for (int i = 0; i < orc.getNbSubElements(); i++){
			assertSame(orc.getSubElementAt(i+1), null);
		}
	}
}
