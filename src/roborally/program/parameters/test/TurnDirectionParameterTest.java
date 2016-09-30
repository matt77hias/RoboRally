package roborally.program.parameters.test;

import static org.junit.Assert.*;

import org.junit.Test;
import roborally.program.parameters.TurnDirectionParameter;

/**
 * A test class for turn direction parameter objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TurnDirectionParameterTest {

	@Test
	public void turnDirectionToString(){
		assertEquals(TurnDirectionParameter.TurnDirectionFromString("clockwise"),TurnDirectionParameter.CLOCKWISE);
		assertEquals(TurnDirectionParameter.TurnDirectionFromString("counterclockwise"),TurnDirectionParameter.COUNTERCLOCKWISE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void turnDirectionToString_invalidInput(){
		TurnDirectionParameter.TurnDirectionFromString("Clockwise");
	}
	
	@Test (expected = NullPointerException.class)
	public void turnDirectionToString_nullInput(){
		TurnDirectionParameter.TurnDirectionFromString(null);
	}
	
	@Test
	public void TurnDirectionFromString(){
		assertEquals(TurnDirectionParameter.CLOCKWISE.turnDirectionToString(),"clockwise");
		assertEquals(TurnDirectionParameter.COUNTERCLOCKWISE.turnDirectionToString(),"counterclockwise");
	}
	
	@Test
	public void isValidTurnDirectionParameter(){
		assertTrue(TurnDirectionParameter.isValidTurnDirectionParameter(TurnDirectionParameter.CLOCKWISE));
		assertTrue(TurnDirectionParameter.isValidTurnDirectionParameter(TurnDirectionParameter.COUNTERCLOCKWISE));
		assertFalse(TurnDirectionParameter.isValidTurnDirectionParameter(null));
	}
	
	@Test
	public void getAllTurnDirectionParameters(){
		assertEquals(TurnDirectionParameter.getAllTurnDirectionParameters().size(), 2);
		assertTrue(TurnDirectionParameter.getAllTurnDirectionParameters().contains(TurnDirectionParameter.CLOCKWISE));
		assertTrue(TurnDirectionParameter.getAllTurnDirectionParameters().contains(TurnDirectionParameter.COUNTERCLOCKWISE));
	}
	
	@Test
	public void getNbOfTurnDirectionParameters(){
		assertEquals(TurnDirectionParameter.getNbOfTurnDirectionParameters(), 2);
	}
}
