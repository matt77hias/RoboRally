package roborally.program.commands.test;

import static org.junit.Assert.*;

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
import roborally.model.inventory.item.Battery;
import roborally.program.commands.MoveCommand;
import roborally.program.commands.PickUpAndUseCommand;

/**
 * A test class for pick up and move commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class PickUpAndUseCommandTest {
	
	private Robot rob;
	private Board b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(50, 50);
		rob = new Robot(b, new Position(5, 5), new Accu(new Energy(1000)), Direction.RIGHT);
	}

	@Test
	public void executeStep_normalCase() {
		Battery bat = new Battery(b, new Position(5, 5), new Accu(new Energy(33)));
		Battery bat2 = new Battery(b, new Position(5, 5), new Accu(new Energy(17)));
		PickUpAndUseCommand.PICK_UP_AND_USE_COMMAND.executeStep(rob);
		assertEquals(new Energy(1050), rob.getEnergy());
		assertTrue(bat.isTerminated());
		assertTrue(bat2.isTerminated());
	}

	@Test
	public void executeStep_noItems() {
		Battery bat = new Battery(b, new Position(4, 5), new Accu(new Energy(33)));
		Battery bat2 = new Battery(b, new Position(5, 6), new Accu(new Energy(17)));
		PickUpAndUseCommand.PICK_UP_AND_USE_COMMAND.executeStep(rob);
		assertEquals(new Energy(1000), rob.getEnergy());
		assertTrue(!bat.isTerminated());
		assertTrue(!bat2.isTerminated());
	}

	@Test(expected=IllegalStateException.class)
	public void executeStep_robotNull(){
		MoveCommand.MOVE_COMMAND.executeStep(null);
	}

	@Test(expected=IllegalStateException.class)
	public void executeStep_robotTerminated(){
		rob.terminate();
		MoveCommand.MOVE_COMMAND.executeStep(rob);
	}
}
