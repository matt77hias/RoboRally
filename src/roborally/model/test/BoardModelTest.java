package roborally.model.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Dimension;
import roborally.board.Position;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.Battery;
import roborally.model.staticObject.Wall;

/**
 * A test class for board model objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BoardModelTest {

	BoardModel bm;
	Board b;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bm = new Robot();
		b = new Board(20, 20);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct_nullBoard_nullPosition(){
		BoardModel model = new Wall(null, null);
		assertTrue(model.getBoard() == null);
		assertTrue(model.getPosition() == null);
		assertFalse(model.isTerminated());
		
		assertTrue(model.hasProperBoard());
		assertTrue(model.hasProperPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_notNullBoard_nullPositionRejected(){
		@SuppressWarnings("unused")
		BoardModel model = new Wall(b, null);
	}
	
	@Test
	public void construct_nullBoard_notNullPosition(){
		BoardModel model = new Wall(null, new Position(10,10));
		assertTrue(model.getBoard() == null);
		assertTrue(model.getPosition() == null);
		assertFalse(model.isTerminated());
		
		assertTrue(model.hasProperBoard());
		assertTrue(model.hasProperPosition());
	}
	
	@Test
	public void construct_notNullBoard_notNullPosition(){
		BoardModel model = new Wall(b, new Position(10,10));
		assertEquals(model.getBoard(),b);
		assertEquals(model.getPosition(),new Position(10,10));
		assertFalse(model.isTerminated());
		
		assertTrue(model.hasProperBoard());
		assertTrue(model.hasProperPosition());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_positionAlreadyOccupied(){
		@SuppressWarnings("unused")
		BoardModel model = new Wall(b, new Position(10,10));
		@SuppressWarnings("unused")
		BoardModel model2 = new Wall(b, new Position(10,10));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_terminatedBoardRejected(){
		b.terminate();
		@SuppressWarnings("unused")
		BoardModel model = new Wall(b, new Position(10,10));
	}
	
	@Test
	public void isTerminated(){
		BoardModel model = new Wall(b, new Position(10,10));
		assertFalse(model.isTerminated());
		model.terminate();
		assertTrue(model.isTerminated());
		
		assertTrue(model.hasProperBoard());
		assertTrue(model.hasProperPosition());
	}
	

	@Test
	public void terminate_throughBoardModel() {
		b.addBoardModelAt(new Position(10, 10), bm);
		bm.terminate();
		assertTrue(bm.getBoard() == null);
		assertTrue(bm.getPosition() == null);
		assertTrue(bm.isTerminated());
		bm.terminate();
		assertTrue(bm.getBoard() == null);
		assertTrue(bm.getPosition() == null);
		assertTrue(bm.isTerminated());
	}
	
	@Test
	public void isValidBoard(){
		assertTrue(BoardModel.isValidBoard(null));
		assertTrue(BoardModel.isValidBoard(b));
		b.terminate();
		assertTrue(BoardModel.isValidBoard(null));
		assertFalse(BoardModel.isValidBoard(b));
	}
	
	@Test
	public void canHaveAsBoard(){
		assertFalse(bm.canHaveAsBoard(b));
		b.addBoardModelAt(new Position(11,11), bm);
		assertTrue(bm.canHaveAsBoard(b));
		
		Board bt = new Board();
		BoardModel model = new Wall(b, new Position(10,10));
		assertTrue(model.canHaveAsBoard(b));
		assertFalse(model.canHaveAsBoard(null));
		assertFalse(model.canHaveAsBoard(bt));
		b.removeBoardModel(model);
		assertTrue(model.canHaveAsBoard(null));
		assertFalse(model.canHaveAsBoard(bt));
		assertFalse(model.canHaveAsBoard(b));
		b.terminate();
		assertTrue(model.canHaveAsBoard(null));
		assertFalse(model.canHaveAsBoard(b));
		assertFalse(model.canHaveAsBoard(bt));
		bt.addBoardModelAt(new Position(10,10), model);
		assertTrue(model.canHaveAsBoard(bt));
		model.terminate();
		assertTrue(model.canHaveAsBoard(null));
		assertFalse(model.canHaveAsBoard(bt));
	}
	
	@Test
	public void canHaveAsBoard_pickedUpBattery(){
		Robot robot = new Robot(b, new Position(0,0));
		Battery battery = new Battery(b, new Position(0,0));
		robot.getInventory().addInventoryItem(battery);
		assertTrue(battery.isPickedUp());
		assertTrue(battery.canHaveAsBoard(battery.getBoard()));
		assertEquals(battery.getBoard(), null);
		assertFalse(battery.canHaveAsBoard(b));
	}
	
	@Test
	public void getBoard(){
		b.addBoardModelAt(new Position(10, 10), bm);
		assertEquals(bm.getBoard(),b);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setBoard_invalidBoardRejected(){
		Board bt = new Board();
		BoardModel model = new Wall(b, new Position(10,10));
		model.setBoard(bt);
	}
	
	@Test
	public void canHaveAsPosition(){
		BoardModel model = new Wall(b, new Position(10,10));
		assertTrue(model.canHaveAsPosition(new Position(10,10)));
		assertFalse(model.canHaveAsPosition(new Position(11,11)));
		assertFalse(model.canHaveAsPosition(null));
		b.removeBoardModel(model);
		assertFalse(model.canHaveAsPosition(new Position(10,10)));
		assertFalse(model.canHaveAsPosition(new Position(11,11)));
		assertTrue(model.canHaveAsPosition(null));
	}
	
	@Test
	public void getPosition(){
		BoardModel model = new Wall(b, new Position(10,10));
		assertEquals(model.getPosition(),new Position(10,10));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setPosition(){
		BoardModel model = new Wall(b, new Position(10,10));
		model.setPosition(new Position(11,11));
	}
	
	@Test
	public void getCoordinates_normalCase(){
		b.addBoardModelAt(new Position(10, 15), bm);
		assertEquals(10, bm.getCoordinates()[0]);
		assertEquals(15, bm.getCoordinates()[1]);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getCoordinates_nullPosition(){
		BoardModel model = new Wall();
		model.getCoordinates();
	}
	
	@Test
	public void getCoordinate_normalCase(){
		b.addBoardModelAt(new Position(10, 15), bm);
		assertEquals(10, bm.getCoordinate(Dimension.HORIZONTAL));
		assertEquals(15, bm.getCoordinate(Dimension.VERTICAL));
	}
	
	@Test(expected = IllegalStateException.class)
	public void getCoordinate_nullPositionI(){
		BoardModel model = new Wall();
		model.getCoordinate(Dimension.HORIZONTAL);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getCoordinate_nullPositionII(){
		BoardModel model = new Wall();
		model.getCoordinate(Dimension.VERTICAL);
	}
	
	@Test
	public void moveToPosition_normalCase(){
		b.addBoardModelAt(new Position(10, 10), bm);
		bm.moveToPosition(new Position(15, 15));
		assertEquals(new Position(15, 15), bm.getPosition());
		
		assertTrue(bm.hasProperBoard());
		assertTrue(bm.hasProperPosition());
	}
	
	@Test
	public void moveToPosition_noActionTakePlace(){
		Robot r = new Robot(b, new Position(10, 10));
		assertEquals(r.getPosition(),new Position(10,10));
		
		r.moveToPosition(new Position(15, 15));
		assertEquals(r.getPosition(),new Position(15,15));
		
		assertTrue(r.hasProperBoard());
		assertTrue(r.hasProperPosition());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit(){
		BoardModel r = new Robot();
		r.terminate();
		r.hit();
	}
	
	@Test
	public void hasProperBoard(){
		BoardModel r = new Robot(b, new Position(5L,6L));
		assertTrue(r.hasProperBoard());
		r.getBoard().moveBoardModelTo(r, new Position(1L,2L));
		assertTrue(r.hasProperBoard());
		r.terminate();
		assertTrue(r.hasProperBoard());
		r.terminate();
		assertTrue(r.hasProperBoard());
	}
	
	@Test
	public void hasProperPosition(){
		BoardModel r = new Robot(b, new Position(5L,6L));
		assertTrue(r.hasProperPosition());
		r.getBoard().moveBoardModelTo(r, new Position(1L,2L));
		assertTrue(r.hasProperPosition());
		r.terminate();
		assertTrue(r.hasProperPosition());
		r.terminate();
		assertTrue(r.hasProperPosition());
	}
}
