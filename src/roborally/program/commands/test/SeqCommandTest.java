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

/**
 * A test class for sequential commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class SeqCommandTest {

	private static SeqCommand sc;
	private Robot rob;
	private Board b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sc = new SeqCommand(MoveCommand.MOVE_COMMAND, ShootCommand.SHOOT_COMMAND);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		b = new Board(200, 200);
		rob = new Robot(b, new Position(50, 50), new Accu(new Energy(5000)), Direction.RIGHT);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getCommandList_normalCase() {
		SeqCommand seq = new SeqCommand(ShootCommand.SHOOT_COMMAND, MoveCommand.MOVE_COMMAND, PickUpAndUseCommand.PICK_UP_AND_USE_COMMAND);
		List<Command> list = seq.getCommandList(rob);
		assertTrue(list.size() == 3);
		for (int i = 0; i < seq.getNbSubElements(); i++){
			assertEquals(list.get(i) ,seq.getSubElementAt(seq.getNbSubElements()-i));
		}
	}
	
	@Test
	public void getNbSubElements(){
		assertEquals(2, sc.getNbSubElements());
	}
}
