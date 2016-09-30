package roborally.program.commands.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.program.commands.*;
import roborally.program.conditions.*;
import roborally.program.parameters.TurnDirectionParameter;

/**
 * A test class for while commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WhileCommandTest {

	private Robot rob;
	private Board b;
	private static WhileCommand someWhileCommand;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		someWhileCommand = new WhileCommand(new EnergyAtleastCondition(new Energy(500)), new TurnCommand(TurnDirectionParameter.CLOCKWISE));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(50, 50);
		rob = new Robot(b, new Position(5, 5), new Accu(new Energy(500)), Direction.RIGHT);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase() {
		WhileCommand someWC = new WhileCommand(WallCondition.WALL_CONDITION, MoveCommand.MOVE_COMMAND);
		assertSame(WallCondition.WALL_CONDITION, someWC.getCondition());
		assertSame(someWC.getWhileCommand(), MoveCommand.MOVE_COMMAND);
	}
	
	@Test
	public void getCommandList_thenCase(){
		List<Command> list = someWhileCommand.getCommandList(rob);
		assertTrue(list.size() == 1);
		assertTrue(list.get(0) == someWhileCommand.getWhileCommand());
	}
	
	@Test
	public void getNbSubElements(){
		assertEquals(1, someWhileCommand.getNbSubElements());
	}
	
	@Test
	public void getSubElementAt(){
		assertSame(someWhileCommand.getWhileCommand(), someWhileCommand.getSubElementAt(1));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_lowerBoundExceeded(){
		someWhileCommand.getSubElementAt(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_upperBoundExceeded(){
		someWhileCommand.getSubElementAt(2);
	}
	
	@Test
	public void getElementSymbol(){
		assertEquals("while", someWhileCommand.getElementSymbol());
	}
}
