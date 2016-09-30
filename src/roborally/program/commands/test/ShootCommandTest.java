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
import roborally.program.commands.MoveCommand;
import roborally.program.commands.ShootCommand;

/**
 * A test class for move commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ShootCommandTest {
	
	private Robot rob, rob2;
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
		rob2 = new Robot(b, new Position(10, 5), new Accu(new Energy(100)), Direction.RIGHT);
	}

	@Test
	public void executeStep_normalCase() {
		Energy oldE = rob2.getEnergyCapacityLimit();
		ShootCommand.SHOOT_COMMAND.executeStep(rob);
		assertEquals(new Energy(0), rob.getEnergy());
		assertEquals(oldE.subtract(Robot.STANDARD_ENERGY_SHOT_DAMAGE), rob2.getEnergyCapacityLimit());
	}

	@Test
	public void executeStep_notEnoughEnergy() {
		ShootCommand.SHOOT_COMMAND.executeStep(rob2);
		assertEquals(new Energy(100), rob2.getEnergy());
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
