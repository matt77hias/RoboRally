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

/**
 * A test class for if commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class IfCommandTest {

	private Robot rob;
	private Board b;
	private static IfCommand someIfCommand;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		someIfCommand = new IfCommand(WallCondition.WALL_CONDITION, MoveCommand.MOVE_COMMAND, ShootCommand.SHOOT_COMMAND);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(50, 50);
		rob = new Robot(b, new Position(5, 5), new Accu(new Energy(1000)), Direction.RIGHT);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_normalCase() {
		IfCommand someIfC = new IfCommand(WallCondition.WALL_CONDITION, MoveCommand.MOVE_COMMAND, ShootCommand.SHOOT_COMMAND);
		assertSame(WallCondition.WALL_CONDITION, someIfC.getCondition());
		assertSame(someIfC.getThenCommand(), MoveCommand.MOVE_COMMAND);
		assertSame(someIfC.getElseCommand(), ShootCommand.SHOOT_COMMAND);
	}
	
	@Test
	public void getCommandList_thenCase(){
		IfCommand someIfC = new IfCommand(new NotCondition(WallCondition.WALL_CONDITION), MoveCommand.MOVE_COMMAND, ShootCommand.SHOOT_COMMAND);
		List<Command> list = someIfC.getCommandList(rob);
		assertTrue(list.size() == 1);
		assertTrue(list.get(0) == someIfC.getThenCommand());
	}
	
	@Test
	public void getCommandList_elseCase(){
		IfCommand someIfC = new IfCommand(WallCondition.WALL_CONDITION, MoveCommand.MOVE_COMMAND, ShootCommand.SHOOT_COMMAND);
		List<Command> list = someIfC.getCommandList(rob);
		assertTrue(list.size() == 1);
		assertTrue(list.get(0) == someIfC.getElseCommand());
	}
	
	@Test
	public void getNbSubElements(){
		assertEquals(2, someIfCommand.getNbSubElements());
	}
	
	@Test
	public void getSubElementAt(){
		assertSame(someIfCommand.getThenCommand(), someIfCommand.getSubElementAt(1));
		assertSame(someIfCommand.getElseCommand(), someIfCommand.getSubElementAt(2));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_lowerBoundExceeded(){
		someIfCommand.getSubElementAt(0);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getSubElementAt_upperBoundExceeded(){
		someIfCommand.getSubElementAt(3);
	}
	
	@Test
	public void getElementSymbol(){
		assertEquals("if", someIfCommand.getElementSymbol());
	}

}
