package roborally.program.test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.program.RobotLanguageInterpreter;
import roborally.program.commands.*;

/**
 * A test class for robot language interpreter objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class RobotLanguageInterpreterTest {

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
	public void readCommandList_normalCase1() {
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String input = "(seq (move) (turn Clockwise) (if (energy-at-least 500) (shoot) (move)))";
		SeqCommand cmd = (SeqCommand)rli.readCommandList(input);
		assertEquals(3, cmd.getNbSubElements());
		assertTrue(cmd.getSubElementAt(1) instanceof MoveCommand);
		assertTrue(cmd.getSubElementAt(2) instanceof TurnCommand);
		assertTrue(cmd.getSubElementAt(3) instanceof IfCommand);
	}
	
	@Test
	public void readCommandList_normalCase2() {
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String input = "(while (and (at-item) (can-hit-robot) ) (if (energy-at-least 1500.0) (seq (move) (while (wall) (seq (seq (turn counterclockwise) (shoot) ) (move) ) ) (pickup-and-use) ) (move) ) )";
		WhileCommand cmd = (WhileCommand)rli.readCommandList(input);
		assertEquals(1, cmd.getNbSubElements());
	}
	
	@Test
	public void readCommandListFromFile_normalCase1() throws IOException{
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String filepath = "src/res/programs/gump.prog";
		Command cmd = rli.readCommandListFromFile(filepath);
		assertTrue(cmd instanceof WhileCommand);
	}
	
	@Test
	public void readCommandListFromFile_normalCase2() throws IOException{
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String filepath = "src/res/programs/cowboy.prog";
		Command cmd = rli.readCommandListFromFile(filepath);
		assertTrue(cmd instanceof WhileCommand);
	}

	@Test
	public void writeCommandListToString_normalCase1(){
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String input = "(while (and (at-item) (can-hit-robot) ) (if (energy-at-least 1500.0) (seq (move) (while (wall) (seq (seq (turn counterclockwise) (shoot) ) (move) ) ) (pickup-and-use) ) (move) ) )";
		Command cmd = rli.readCommandList(input);
		String output = rli.writeCommandListToString(cmd);
		assertEquals(output.replaceAll("\\s+", ""), input.replaceAll("\\s+", ""));
	}
	
	@Test
	public void writeCommandListToFile_normalCase1() throws IOException{
		RobotLanguageInterpreter rli = new RobotLanguageInterpreter();
		String input = "(while (and (at-item) (can-hit-robot) ) (if (energy-at-least 1500.0) (seq (move) (while (wall) (seq (seq (turn counterclockwise) (shoot) ) (move) ) ) (pickup-and-use) ) (move) ) )";
		Command cmd = rli.readCommandList(input);
		rli.writeCommandListToFile("C:\\file2.prog", cmd);
	}
}
