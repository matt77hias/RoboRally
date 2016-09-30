package roborally.program.commands.test;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Dimension;
import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.program.commands.MoveCommand;

/**
 * A test class for move commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class MoveCommandTest {
	
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
		MoveCommand.MOVE_COMMAND.executeStep(rob);
		assertEquals(new Energy(500), rob.getEnergy());
		assertEquals(6, rob.getPosition().getCoordinate(Dimension.HORIZONTAL));
		assertEquals(5, rob.getPosition().getCoordinate(Dimension.VERTICAL));
	}

	@Test
	public void executeStep_borderMove() {
		Robot rob2 = new Robot(b, new Position(0, 0), new Accu(new Energy(1000)), Direction.UP);
		MoveCommand.MOVE_COMMAND.executeStep(rob2);
		assertEquals(new Energy(1000), rob2.getEnergy());
		assertEquals(0, rob2.getPosition().getCoordinate(Dimension.HORIZONTAL));
		assertEquals(0, rob2.getPosition().getCoordinate(Dimension.VERTICAL));
	}

	@Test
	public void executeStep_notEnoughEnergy() {
		Robot rob2 = new Robot(b, new Position(0, 0), new Accu(new Energy(400)), Direction.UP);
		MoveCommand.MOVE_COMMAND.executeStep(rob);
		assertEquals(new Energy(400), rob2.getEnergy());
		assertEquals(0, rob2.getPosition().getCoordinate(Dimension.HORIZONTAL));
		assertEquals(0, rob2.getPosition().getCoordinate(Dimension.VERTICAL));
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
