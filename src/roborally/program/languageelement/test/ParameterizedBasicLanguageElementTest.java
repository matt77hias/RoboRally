package roborally.program.languageelement.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.energy.Energy;
import roborally.program.conditions.EnergyAtleastCondition;
import roborally.program.languageelement.ParameterizedBasicLanguageElement;

/**
 * A test class for parameterized basic language element objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ParameterizedBasicLanguageElementTest {

	private ParameterizedBasicLanguageElement<Energy> ble;
	
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
	public void constructor_normalCase() {
		Energy e = new Energy(7);
		ble = new EnergyAtleastCondition(e);
		assertSame(ble.getParameter(), e);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_illegalArgument() {
		ble = new EnergyAtleastCondition(null);
	}

}
