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
import roborally.program.commands.TurnCommand;
import roborally.program.parameters.TurnDirectionParameter;

/**
 * A test class for turn commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TurnCommandTest {
	
	private Robot rob;
	private Board b;
	private static TurnCommand cw;
	private static TurnCommand ccw;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cw = new TurnCommand(TurnDirectionParameter.CLOCKWISE);
		ccw = new TurnCommand(TurnDirectionParameter.COUNTERCLOCKWISE);
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
		cw.executeStep(rob);
		assertEquals(new Energy(900), rob.getEnergy());
		assertEquals(Direction.DOWN, rob.getDirection());
		ccw.executeStep(rob);
		assertEquals(new Energy(800), rob.getEnergy());
		assertEquals(Direction.RIGHT, rob.getDirection());
	}
	
	@Test
	public void executeStep_notEnoughEnergy() {
		Robot rob2 = new Robot(b, new Position(10, 10), new Accu(new Energy(50)), Direction.RIGHT);
		cw.executeStep(rob2);
		assertEquals(new Energy(50), rob2.getEnergy());
		assertEquals(Direction.RIGHT, rob.getDirection());
	}

	@Test(expected=IllegalStateException.class)
	public void executeStep_robotNull(){
		cw.executeStep(null);
	}

	@Test(expected=IllegalStateException.class)
	public void executeStep_robotTerminated(){
		rob.terminate();
		cw.executeStep(rob);
	}
}
