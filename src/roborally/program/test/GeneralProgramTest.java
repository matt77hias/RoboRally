package roborally.program.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Dimension;
import roborally.board.Position;
import roborally.model.Cost;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.program.Program;

public class GeneralProgramTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test1() throws IllegalArgumentException, IOException {
		Board b = new Board(2000, 2000);
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(10000), Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.RIGHT);
		Program prog = new Program(rob, "src/res/programs/testprog1.prog");
		prog.start();
		while (!prog.isFinished()){
			double old = rob.getEnergy().getEnergyAmount();
			long oldx = rob.getCoordinate(Dimension.HORIZONTAL);
			prog.executeStep();
			if (oldx == 19)
				return;
			// program must ended here without move
			// every step of the execution, the robot must have moved
			assertEquals(oldx+1, rob.getPosition().getCoordinate(Dimension.HORIZONTAL));
			assertEquals(old - rob.getEnergyCostOf(Cost.MOVE).getEnergyAmount(), rob.getEnergy().getEnergyAmount(), 0.1);
		}
	}
	
	@Test
	public void test2() throws IllegalArgumentException, IOException{
		Board b = new Board(2000, 2000);
		Robot rob = new Robot(b, new Position(50, 50), new Accu(new Energy(10000), Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.RIGHT);
		Program prog = new Program(rob, "src/res/programs/testprog2.prog");
		prog.start();
		while (!prog.isFinished()){
			prog.executeStep();
		}
		assertEquals(new Energy(2900), rob.getEnergy());
	}

	@Test
	public void test3() throws IllegalArgumentException, IOException{
		Board b = new Board(2000, 2000);
		Robot rob = new Robot(b, new Position(0,0), new Accu(new Energy(10000), Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.RIGHT);
		Program prog = new Program(rob, "src/res/programs/testprog3.prog");
		rob.setProgram(prog);
		while (!rob.getProgramFinished()){
			rob.executeProgramStep();
		}
		assertEquals(new Energy(0), rob.getEnergy());
		assertEquals(20, rob.getPosition().getCoordinate(Dimension.HORIZONTAL));
	}
	
}
