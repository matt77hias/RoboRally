package roborally.program.languageelement.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.energy.Energy;
import roborally.program.conditions.EnergyAtleastCondition;
import roborally.program.conditions.TrueCondition;
import roborally.program.languageelement.BasicLanguageElement;

/**
 * A test class for basic language element objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BasicLanguageElementTest {
	
	private BasicLanguageElement ble;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ble = new EnergyAtleastCondition(new Energy(5));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void hasAsSubElement_SelfReferenceTrue() {
		assertTrue(ble.hasAsSubElement(ble));
	}

	@Test
	public void hasAsSubElement_NotSelfReferenceFalse() {
		assertFalse(ble.hasAsSubElement(null));
		assertFalse(ble.hasAsSubElement(TrueCondition.TRUE_CONDITION));
	}

	@Test
	public void toString_normalCase() {
		assertEquals("("+ble.toString()+")", ble.toRobotLanguage());
	}

}
