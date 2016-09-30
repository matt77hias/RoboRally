package roborally.model.staticObject.test;

import static org.junit.Assert.*;

import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.Battery;
import roborally.model.staticObject.VictoryFlag;
import roborally.model.staticObject.Wall;

/**
 * A test class for victory flag objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class VictoryFlagTest {

	@Test
	public void construct1(){
		VictoryFlag temp = new VictoryFlag();
		assertEquals(temp.getBoard(), null);
		assertTrue(temp.getPosition() == null);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct2(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		VictoryFlag temp = new VictoryFlag(b,p);
		assertEquals(temp.getBoard(), b);
		assertEquals(temp.getPosition(), p);
		assertFalse(temp.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_wallPositioned(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		@SuppressWarnings("unused")
		Wall w = new Wall(b,p);
		@SuppressWarnings("unused")
		VictoryFlag temp = new VictoryFlag(b,p);
	}
	
	@Test
	public void terminate(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		VictoryFlag temp = new VictoryFlag(b,p);
		temp.terminate();
		assertEquals(temp.getBoard(), null);
		assertTrue(temp.getPosition() == null);
		assertTrue(temp.isTerminated());
	}
	
	@Test
	public void canSharePositionWith(){
		VictoryFlag temp = new VictoryFlag();
		assertFalse(temp.canSharePositionWith(new Wall()));
		assertTrue(temp.canSharePositionWith(new Robot()));
		assertFalse(temp.canSharePositionWith(new Battery()));
	}
	
	@Test
	public void canBeHit(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		VictoryFlag temp = new VictoryFlag();
		VictoryFlag temp2 = new VictoryFlag(b,p);
		assertTrue(temp.canBeHit());
		assertTrue(temp2.canBeHit());
		temp.terminate();
		temp2.terminate();
		assertFalse(temp.canBeHit());
		assertFalse(temp2.canBeHit());
	}
	
	@Test
	public void hit(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		VictoryFlag temp = new VictoryFlag();
		VictoryFlag temp2 = new VictoryFlag(b,p);
		temp.hit();
		temp2.hit();
		assertEquals(temp.getBoard(), null);
		assertTrue(temp.getPosition() == null);
		assertFalse(temp.isTerminated());
		assertEquals(temp2.getBoard(), b);
		assertEquals(temp2.getPosition(), p);
		assertFalse(temp2.isTerminated());
	}

	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		Board b = new Board(10L,10L);
		Position p = new Position(1L,1L);
		VictoryFlag temp = new VictoryFlag(b,p);
		temp.terminate();
		temp.hit();
	}
}
