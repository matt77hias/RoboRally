package roborally.board.test;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.conditiontester.BasicComparison;
import roborally.conditiontester.EnergyTester;

import static roborally.board.Board.*;

import roborally.board.*;
import roborally.model.*;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.model.inventory.item.*;
import roborally.model.staticObject.Wall;

/**
 * A test class for board objects.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BoardTest {

	Board board20x20y;
	Board board19x20y;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		board20x20y = new Board(20L, 20L);
		board19x20y = new Board(19L, 20L);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct1(){
		Board temp = new Board();
		assertEquals(Board.STANDARD_BOARD_HORIZONTAL_SIZE, temp.getSizeAt(Dimension.HORIZONTAL));
		assertEquals(Board.STANDARD_BOARD_VERTICAL_SIZE, temp.getSizeAt(Dimension.VERTICAL));
		assertFalse(temp.isTerminated());
	}

	@Test
	public void construct2() {
		Board temp = new Board(19L, 20L);
		assertEquals(19L, temp.getSizeAt(Dimension.HORIZONTAL));
		assertEquals(20L, temp.getSizeAt(Dimension.VERTICAL));
		assertFalse(temp.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalSizeRejectedI() {
		@SuppressWarnings("unused")
		Board temp = new Board(19L, Board.getBoundaryAt(Dimension.VERTICAL)+1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalSizeRejectedII() {
		@SuppressWarnings("unused")
		Board temp = new Board(Board.getBoundaryAt(Dimension.HORIZONTAL)+1, 20L);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalSizeRejectedIII() {
		@SuppressWarnings("unused")
		Board temp = new Board(-1L, 2L);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalSizeRejectedIV() {
		@SuppressWarnings("unused")
		Board temp = new Board(4L, -1L);
	}
	
	@Test
	public void getNbDimensions(){
		assertEquals(Board.getBoundaries().length,Board.getNbDimensions());
	}
	
	@Test
	public void getBoundaryAt(){
		assertEquals(Board.getBoundaryAt(Dimension.HORIZONTAL),Board.getBoundaries()[Dimension.HORIZONTAL.getDimensionnr()-1]);
		assertEquals(Board.getBoundaryAt(Dimension.VERTICAL),Board.getBoundaries()[Dimension.VERTICAL.getDimensionnr()-1]);
	}
	
	@Test (expected = IllegalDimensionException.class)
	public void getBoundaryAt_illegalDimensionRejected(){
		Board.getBoundaryAt(null);
	}
	
	@Test
	public void getBoundaries(){
		assertEquals(Board.getBoundaries()[Dimension.HORIZONTAL.getDimensionnr()-1],Board.getBoundaryAt(Dimension.HORIZONTAL));
		assertEquals(Board.getBoundaries()[Dimension.VERTICAL.getDimensionnr()-1],Board.getBoundaryAt(Dimension.VERTICAL));
	}
	
	@Test
	public void isValidSizeAt_normalValuesAccepted(){
		assertTrue(isValidSizeAt(Dimension.HORIZONTAL, 20L));
		assertTrue(isValidSizeAt(Dimension.VERTICAL, 20L));
		assertTrue(isValidSizeAt(Dimension.HORIZONTAL, 1L));
		assertTrue(isValidSizeAt(Dimension.VERTICAL, 1L));
		assertTrue(isValidSizeAt(Dimension.HORIZONTAL, 0L));
		assertTrue(isValidSizeAt(Dimension.VERTICAL, 0L));
	}
	
	@Test
	public void isValidSizeAt_negativeValuesRejected(){
		assertFalse(isValidSizeAt(Dimension.HORIZONTAL, -1L));
		assertFalse(isValidSizeAt(Dimension.HORIZONTAL, -10L));
		assertFalse(isValidSizeAt(Dimension.VERTICAL, -1L));
		assertFalse(isValidSizeAt(Dimension.VERTICAL, -10L));
	}
	
	@Test
	public void isValidSizeAt_largerValuesRejected(){
		assertFalse(isValidSizeAt(Dimension.HORIZONTAL, Board.getBoundaryAt(Dimension.HORIZONTAL)+1L));
		assertFalse(isValidSizeAt(Dimension.VERTICAL, Board.getBoundaryAt(Dimension.VERTICAL)+1L));
	}
	
	@Test
	public void isValidSizeAt_illegalDimensionRejected(){
		assertFalse(isValidSizeAt(null, 10L));
		assertFalse(isValidSizeAt(null, 15L));
	}
	
	@Test
	public void getSizeAt(){
		assertEquals(board19x20y.getSizeAt(Dimension.HORIZONTAL),19L);
		assertEquals(board19x20y.getSizeAt(Dimension.VERTICAL),20L);
	}
	
	@Test (expected = IllegalDimensionException.class)
	public void getSizeAt_illegalDimensionRejected(){
		board19x20y.getSizeAt(null);
	}
	
	@Test
	public void getSize(){
		assertEquals(board19x20y.getSize()[0],19L);
		assertEquals(board19x20y.getSize()[1],20L);
	}
	
	@Test
	public void canHaveAsCoordinates_zeroValuesAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinates(new long[]{0, 0}));
	}
	
	@Test
	public void canHaveAsCoordinates_normalValuesAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinates(new long[]{11L, 10L}));
	}
	
	@Test
	public void canHaveAsCoordinates_sizeValuesAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinates(new long[]{board19x20y.getSizeAt(Dimension.HORIZONTAL), 10L}));
		assertTrue(board19x20y.canHaveAsCoordinates(new long[]{10L, board19x20y.getSizeAt(Dimension.VERTICAL)}));
		assertTrue(board19x20y.canHaveAsCoordinates(new long[]{board19x20y.getSizeAt(Dimension.HORIZONTAL), board19x20y.getSizeAt(Dimension.VERTICAL)}));
	}
	
	@Test
	public void canHaveAsCoordinates_dimensionDoesntMatchRejected(){
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{10L, 10L, 10L}));
	}
	
	@Test
	public void canHaveAsCoordinates_negativeValuesRejected(){
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{-10L, 10L}));
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{-10L, -10L}));
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{10L, -10L}));
	}
	
	@Test
	public void canHaveAsCoordinates_largerValuesRejected(){
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{board19x20y.getSizeAt(Dimension.HORIZONTAL)+1L, 1L}));
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{board19x20y.getSizeAt(Dimension.HORIZONTAL)+1L, board19x20y.getSizeAt(Dimension.VERTICAL)+1L}));
		assertFalse(board19x20y.canHaveAsCoordinates(new long[]{1L, board19x20y.getSizeAt(Dimension.VERTICAL)+1L}));
	}
	
	@Test
	public void canHaveAsCoordinate_normalValueAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinate(10L, Dimension.HORIZONTAL));
		assertTrue(board19x20y.canHaveAsCoordinate(9L, Dimension.VERTICAL));
	}
	
	@Test
	public void canHaveAsCoordinate_zeroValueAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinate(0L, Dimension.HORIZONTAL));
		assertTrue(board19x20y.canHaveAsCoordinate(0L, Dimension.VERTICAL));
	}
	
	@Test
	public void canHaveAsCoordinate_sizeValueAccepted(){
		assertTrue(board19x20y.canHaveAsCoordinate(board19x20y.getSizeAt(Dimension.HORIZONTAL), Dimension.HORIZONTAL));
		assertTrue(board19x20y.canHaveAsCoordinate(board19x20y.getSizeAt(Dimension.VERTICAL), Dimension.VERTICAL));
	}
	
	@Test
	public void canHaveAsCoordinate_illegalDimensionRejected(){
		assertFalse(board20x20y.canHaveAsCoordinate(10L, null));
	}
	
	@Test
	public void canHaveAsCoordinate_negativeCoordinateRejected(){
		assertFalse(board20x20y.canHaveAsCoordinate(-10L, Dimension.HORIZONTAL));
		assertFalse(board20x20y.canHaveAsCoordinate(-10L, Dimension.VERTICAL));
	}
	
	@Test
	public void canHaveAsCoordinate_largerPositiveCoordinateRejected(){
		assertFalse(board19x20y.canHaveAsCoordinate(board19x20y.getSizeAt(Dimension.HORIZONTAL)+1L, Dimension.HORIZONTAL));
		assertFalse(board19x20y.canHaveAsCoordinate(board19x20y.getSizeAt(Dimension.VERTICAL)+1L, Dimension.VERTICAL));
	}

	@Test
	public void terminate_notTerminatedYet(){
		board20x20y.terminate();
		assertTrue(board20x20y.isTerminated());
		board20x20y.terminate();
		assertTrue(board20x20y.isTerminated());
	}
	
	@Test
	public void terminate_noBoardModelsAnymore(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertEquals(board20x20y.getNbBoardModels(),3);
		board20x20y.terminate();
		assertTrue(board20x20y.isTerminated());
		assertEquals(board20x20y.getNbBoardModels(),0);
		assertEquals(board20x20y.getAllBoardModels().values().size(),0);
	}
	
	@Test
	public void terminate_boardModelsAlsoTerminated(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		board20x20y.terminate();
		assertTrue(board20x20y.isTerminated());
		assertTrue(bat.isTerminated());
		assertTrue(bat2.isTerminated());
		assertTrue(rob.isTerminated());
	}
	
	@Test
	public void containsPositionKey(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		assertFalse(board20x20y.containsPositionKey(new Position(5, 5)));
		assertFalse(board20x20y.containsPositionKey(new Position(10, 10)));
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		assertTrue(board20x20y.containsPositionKey(new Position(5, 5)));
		assertFalse(board20x20y.containsPositionKey(new Position(10, 10)));
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		assertTrue(board20x20y.containsPositionKey(new Position(5, 5)));
		assertFalse(board20x20y.containsPositionKey(new Position(10, 10)));
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertTrue(board20x20y.containsPositionKey(new Position(5, 5)));
		assertTrue(board20x20y.containsPositionKey(new Position(10, 10)));
	}
	
	@Test
	public void containsBoardModel_allCheck(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		Robot r = new Robot();
		Robot r2 = new Robot(board19x20y, new Position(5, 5));
		assertTrue(board19x20y.containsBoardModel_allCheck(r2));
		assertFalse(board20x20y.containsBoardModel_allCheck(r2));
		assertFalse(board20x20y.containsBoardModel_allCheck(rob));
		assertFalse(board20x20y.containsBoardModel_allCheck(bat));
		assertFalse(board20x20y.containsBoardModel_allCheck(bat2));
		assertFalse(board20x20y.containsBoardModel_allCheck(r));
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertTrue(board20x20y.containsBoardModel_allCheck(rob));
		assertTrue(board20x20y.containsBoardModel_allCheck(bat));
		assertTrue(board20x20y.containsBoardModel_allCheck(bat2));
		assertFalse(board20x20y.containsBoardModel_allCheck(r));
	}
	
	@Test
	public void containsBoardModel_positionCheck(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		Robot r = new Robot();
		Robot r2 = new Robot(board19x20y, new Position(5, 5));
		assertTrue(board19x20y.containsBoardModel_allCheck(r2));
		assertFalse(board20x20y.containsBoardModel_allCheck(r2));
		assertFalse(board20x20y.containsBoardModel_positionCheck(rob));
		assertFalse(board20x20y.containsBoardModel_positionCheck(bat));
		assertFalse(board20x20y.containsBoardModel_positionCheck(bat2));
		assertFalse(board20x20y.containsBoardModel_positionCheck(r));
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertTrue(board20x20y.containsBoardModel_positionCheck(rob));
		assertTrue(board20x20y.containsBoardModel_positionCheck(bat));
		assertTrue(board20x20y.containsBoardModel_positionCheck(bat2));
		assertFalse(board20x20y.containsBoardModel_positionCheck(r));
	}
	
	@Test
	public void containsBoardModel(){
		Robot rob = new Robot();
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		Robot r = new Robot();
		assertFalse(board20x20y.containsBoardModel(rob,new Position(5, 5)));
		assertFalse(board20x20y.containsBoardModel(bat,new Position(5, 5)));
		assertFalse(board20x20y.containsBoardModel(bat2,new Position(10, 10)));
		assertFalse(board20x20y.containsBoardModel(r,null));
		board20x20y.addBoardModelAt(new Position(5, 5), bat);
		board20x20y.addBoardModelAt(new Position(5, 5), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertFalse(board20x20y.containsBoardModel(rob,new Position(5, 5)));
		assertTrue(board20x20y.containsBoardModel(bat,new Position(5, 5)));
		assertFalse(board20x20y.containsBoardModel(bat2,new Position(10, 10)));
		assertFalse(board20x20y.containsBoardModel(r,null));
		assertTrue(board20x20y.containsBoardModel(rob,new Position(10, 10)));
		assertFalse(board20x20y.containsBoardModel(bat,new Position(10, 10)));
	}
	
	@Test
	public void canHaveBoardModelAt_situatedOnOtherBoardRejected(){
		Battery bat = new Battery(board20x20y, new Position(new long[]{10, 10}));
		assertFalse(board19x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_boardTerminatedRejected(){
		Battery bat = new Battery();
		board20x20y.terminate();
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_nullBoardModelRejected(){
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), null));
	}
	
	@Test
	public void canHaveBoardModelAt_oneBatteryNormalPositionAccepted(){
		Battery bat = new Battery();
		assertTrue(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_batteryPickedUpRejected(){
		Battery bat = new Battery();
		bat.pickUp();
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_oneBatteryAbnormalPositionRejected(){
		Battery bat = new Battery();
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{30, 30}), bat));
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{-1, -1}), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_nullPositionRejected(){
		Battery bat = new Battery();
		assertFalse(board20x20y.canHaveBoardModelAt(null, bat));
	}
	
	@Test
	public void canHaveBoardModelAt_oneBatteryTerminatedRejected(){
		Battery bat = new Battery();
		bat.terminate();
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_oneBatteryRejectedWhenBoardTerminated(){
		Battery bat = new Battery();
		board20x20y.terminate();
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_twoBatteriesSamePositionAccepted(){
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(new Position(new long[]{10, 10}), bat);
		assertTrue(board20x20y.canHaveBoardModelAt(new Position(10, 10), bat2));
	}
	
	@Test public void canHaveBoardModelAt_twoRobotsSamePositionRejected(){
		Robot rob = new Robot();
		Robot rob2 = new Robot();
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), rob2));
	}
	
	@Test public void canHaveBoardModelAt_twoWallsSamePositionRejected(){
		Wall wal = new Wall();
		Wall wal2 = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), wal);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), wal2));
	}
	
	@Test
	public void canHaveBoardModelAt_BatteryRobotSamePositionAccepted(){
		Battery bat = new Battery();
		Robot rob = new Robot();
		board20x20y.addBoardModelAt(new Position(10, 10), bat);
		assertTrue(board20x20y.canHaveBoardModelAt(new Position(10, 10), rob));
	}
	
	@Test
	public void canHaveBoardModelAt_BatteryWallSamePositionRejected(){
		Battery bat = new Battery();
		Wall wal = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), bat);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), wal));
	}
	
	@Test
	public void canHaveBoardModelAt_BatteryWallSamePositionRejected2(){
		Battery bat = new Battery();
		Wall wal = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), wal);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(10, 10), bat));
	}
	
	@Test
	public void canHaveBoardModelAt_RobotWallSamePositionRejected(){
		Robot rob = new Robot();
		Wall wal = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), rob);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), wal));
	}
	
	@Test
	public void canHaveBoardModelAt_RobotWallSamePositionRejected2(){
		Robot rob = new Robot();
		Wall wal = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), wal);
		assertFalse(board20x20y.canHaveBoardModelAt(new Position(new long[]{10, 10}), rob));
	}
	
	
	
	@Test
	public void hasProperBoardModels_addedSeveralBoardModels(){
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		Battery bat3 = new Battery();
		Battery bat4 = new Battery();
		Robot rob = new Robot();
		Robot rob2 = new Robot();
		Wall wal = new Wall();
		Wall wal2 = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), bat);
		board20x20y.addBoardModelAt(new Position(10, 10), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), bat3);
		board20x20y.addBoardModelAt(new Position(15, 15), bat4);
		board20x20y.addBoardModelAt(new Position(15, 15), rob);
		board20x20y.addBoardModelAt(new Position(16, 15), rob2);
		board20x20y.addBoardModelAt(new Position(16, 16), wal);
		board20x20y.addBoardModelAt(new Position(17, 15), wal2);
		assertTrue(board20x20y.hasProperBoardModels());
	}
	
	@Test
	public void addBoardModelAt_BatteriesAdded(){
		Position pos = new Position(10, 10);
		Battery bat = new Battery();
		board20x20y.addBoardModelAt(pos, bat);
		assertTrue(board20x20y.getBoardModelsAt(pos).contains(bat));
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(pos, bat2);
		assertTrue(board20x20y.getBoardModelsAt(pos).contains(bat));
		assertTrue(board20x20y.getBoardModelsAt(pos).contains(bat2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addBoardModelAt_TwiceToSameBoardRejected(){
		Position pos = new Position(10, 10);
		Robot rob = new Robot();
		board20x20y.addBoardModelAt(pos, rob);
		board20x20y.addBoardModelAt(pos, rob);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addBoardModelAt_TermiantedRobotRejected(){
		Position pos = new Position(10, 10);
		Robot rob = new Robot();
		rob.terminate();
		board20x20y.addBoardModelAt(pos, rob);
	}
	
	 @Test
	 public void getAllBoardModels_BatteriesAdded(){
		Position pos = new Position(10, 10);
		Battery bat = new Battery();
		board20x20y.addBoardModelAt(pos, bat);
		assertTrue(board20x20y.getAllBoardModels().get(pos).contains(bat));
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(pos, bat2);
		assertTrue(board20x20y.getAllBoardModels().get(pos).contains(bat));
		assertTrue(board20x20y.getAllBoardModels().get(pos).contains(bat2));
	 }
	 
	 @Test
	 public void getNbBoardModels_addedSeveralBoardModels(){
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		Battery bat3 = new Battery();
		Battery bat4 = new Battery();
		Robot rob = new Robot();
		Robot rob2 = new Robot();
		Wall wal = new Wall();
		Wall wal2 = new Wall();
		board20x20y.addBoardModelAt(new Position(10, 10), bat);
		board20x20y.addBoardModelAt(new Position(10, 10), bat2);
		board20x20y.addBoardModelAt(new Position(10, 10), bat3);
		board20x20y.addBoardModelAt(new Position(15, 15), bat4);
		board20x20y.addBoardModelAt(new Position(15, 15), rob);
		board20x20y.addBoardModelAt(new Position(16, 15), rob2);
		board20x20y.addBoardModelAt(new Position(16, 16), wal);
		board20x20y.addBoardModelAt(new Position(17, 15), wal2);
		assertEquals(8, board20x20y.getNbBoardModels());
	 }
	
	 @Test
	 public void removeBoardModel_BatteriesAddedThenRemoved(){
		Position pos = new Position(10, 10);
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(pos, bat);
		board20x20y.addBoardModelAt(pos, bat2);
		board20x20y.removeBoardModel(bat2);
		assertTrue(board20x20y.getBoardModelsAt(pos).contains(bat));
		assertFalse(board20x20y.getBoardModelsAt(pos).contains(bat2));
		board20x20y.removeBoardModel(bat);
		assertTrue(board20x20y.getBoardModelsAt(pos) == null);
	 }
	
	 @Test
	 public void removeBoardModel_removeNonExistantModel(){
		Position pos = new Position(10, 10);
		Battery bat = new Battery();
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(pos, bat);
		board20x20y.removeBoardModel(bat2);
		assertTrue(board20x20y.getBoardModelsAt(pos).contains(bat));
	 }
	 
	 @Test
	 public void mergeBoard_normalCase(){
		Battery bat = new Battery();
		Robot rob = new Robot();
		Wall wal = new Wall();
		Board board = new Board(20, 20);
		board.addBoardModelAt(new Position(10, 10), wal);
		board.addBoardModelAt(new Position(15, 15), rob);
		board20x20y.addBoardModelAt(new Position(10, 10), bat);
		board20x20y.mergeBoard(board);
		assertTrue(board20x20y.getBoardModelsAt(new Position(10, 10)).contains(bat));
		assertFalse(board20x20y.getBoardModelsAt(new Position(10, 10)).contains(wal));
		assertTrue(wal.isTerminated());
		assertTrue(board20x20y.getBoardModelsAt(new Position(15, 15)).contains(rob));
		assertTrue(board.isTerminated());
		assertFalse(board20x20y.isTerminated());
	 }
	 
	 @Test
	 public void getNbOfBoardModels(){
		 assertEquals(board20x20y.getNbBoardModels(),0);
		 Battery bat = new Battery();
		 board20x20y.addBoardModelAt(new Position(15, 10), bat);
		 assertEquals(board20x20y.getNbBoardModels(),1);
		 Battery batt = new Battery();
		 board20x20y.addBoardModelAt(new Position(15, 10), batt);
		 assertEquals(board20x20y.getNbBoardModels(),2);
		 board20x20y.terminate();
		 assertEquals(board20x20y.getNbBoardModels(),0);
	 }
	 
	 @Test
	 public void getNbOfBoardModelsAt(){
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(15, 10)),0);
		 Battery bat = new Battery();
		 board20x20y.addBoardModelAt(new Position(15, 10), bat);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(15, 10)),1);
		 Battery batt = new Battery();
		 board20x20y.addBoardModelAt(new Position(15, 10), batt);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(15, 10)),2);
		 board20x20y.terminate();
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(15, 10)),0);
	 }
	 
	 @Test
	 public void getNbOfBoardModelsAt_illegalPosition(){
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(25, 10)),0);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(5, 25)),0);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(25, 25)),0);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(-25, -25)),0);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(-15, 10)),0);
		 assertEquals(board20x20y.getNbBoardModelsAt(new Position(15, -10)),0);
	 }
	 
	 @Test
	 public void getNbOfBoardModelsAt_nullPosition(){
		 assertEquals(board20x20y.getNbBoardModelsAt(null),0);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void mergeBoard_nullBoard(){
		 board20x20y.mergeBoard(null);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void mergeBoard_selfReference(){
		 board20x20y.mergeBoard(board20x20y);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void mergeBoard_terminatedBoard(){
		 Board board = new Board(20, 20);
		 board.terminate();
		 board20x20y.mergeBoard(board);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void mergeBoard_thisBoardTerminated(){
		 Board board = new Board(20, 20);
		 board20x20y.terminate();
		 board20x20y.mergeBoard(board);
	 }
	 
	 @SuppressWarnings("unused")
	@Test
	 public void getRandomTarget(){
		 Board b = new Board(20L,20L);
		 BoardModel b1 = new Battery(b, new Position(1L,1L));
		 BoardModel b2 = new Battery(b, new Position(1L,1L));
		 BoardModel b3 = new Battery(b, new Position(1L,2L));
		 BoardModel b4 = new Battery(b, new Position(1L,2L));
		 BoardModel b5 = new Battery(b, new Position(1L,3L));
		 BoardModel b6 = new Battery(b, new Position(1L,3L));
		 BoardModel b7 = new Battery(b, new Position(2L,1L));
		 BoardModel b8 = new Battery(b, new Position(2L,1L));
		 BoardModel b9 = new Battery(b, new Position(2L,2L));
		 BoardModel b10 = new Battery(b, new Position(2L,2L));
		 BoardModel b11 = new Battery(b, new Position(2L,3L));
		 BoardModel b12 = new Battery(b, new Position(2L,3L));
		 BoardModel b13 = new Battery(b, new Position(3L,1L));
		 BoardModel b14 = new Battery(b, new Position(3L,1L));
		 BoardModel b15 = new Battery(b, new Position(3L,2L));
		 BoardModel b16 = new Battery(b, new Position(3L,2L));
		 BoardModel b17 = new Battery(b, new Position(3L,3L));
		 BoardModel b18 = new Battery(b, new Position(3L,3L));
		 BoardModel b19 = new Battery(b, new Position(0L,2L));
		 BoardModel b20 = new Battery(b, new Position(0L,2L));
		 BoardModel b21 = new Battery(b, new Position(2L,0L));
		 BoardModel b22 = new Battery(b, new Position(2L,0L));
		 BoardModel b23 = new Battery(b, new Position(2L,4L));
		 BoardModel b24 = new Battery(b, new Position(2L,4L));
		 BoardModel b25 = new Battery(b, new Position(4L,2L));
		 BoardModel b26 = new Battery(b, new Position(4L,2L));
		 
		 BoardModel down = b.getRandomTarget(new Position(2L,2L), Direction.DOWN);
		 BoardModel up = b.getRandomTarget(new Position(2L,2L), Direction.UP);
		 BoardModel left = b.getRandomTarget(new Position(2L,2L), Direction.LEFT);
		 BoardModel right = b.getRandomTarget(new Position(2L,2L), Direction.RIGHT);
		 BoardModel n = b.getRandomTarget(new Position(2L,4L), Direction.RIGHT);
		 
		 assertTrue(down == b11 || down == b12);
		 assertTrue(up == b7 || up == b8);
		 assertTrue(left == b3 || left == b4);
		 assertTrue(right == b15 || right == b16);
		 assertTrue(n == null);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void getRandomTarget_nullPositionRejected(){
		 board20x20y.getRandomTarget(null, Direction.DOWN);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void getRandomTarget_nullDirectionRejected(){
		 board20x20y.getRandomTarget(new Position(15, 5), null);
	 }
	 
	 @Test
	 public void moveBoardModelTo_normalCase(){
		 Robot rob = new Robot();
		 board20x20y.addBoardModelAt(new Position(10, 10), rob);
		 board20x20y.moveBoardModelTo(rob, new Position(15, 15));
		 assertTrue(board20x20y.getBoardModelsAt(new Position(10, 10)) == null);
		 assertEquals(rob.getPosition(), new Position(15, 15));
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void moveBoardModelTo_movingRobotToWallPosition(){
		 Robot rob = new Robot();
		 board20x20y.addBoardModelAt(new Position(10, 10), rob);
		 Wall wal = new Wall();
		 board20x20y.addBoardModelAt(new Position(15, 15), wal);
		 board20x20y.moveBoardModelTo(rob, new Position(15, 15));
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void moveBoardModelTo_illegalBoardModelRejected(){
		 Robot rob = new Robot();
		 board20x20y.moveBoardModelTo(rob, new Position(10, 10));
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void moveBoardModelTo_boardModelLocatedOnOtherBoardRejected(){
		 Robot rob = new Robot();
		 board19x20y.addBoardModelAt(new Position(10, 10), rob);
		 board20x20y.moveBoardModelTo(rob, new Position(10, 10));
	 }
	 
	 @Test (expected = NullPointerException.class)
	 public void moveBoardModelTo_nullPositionRejected(){
		 Robot rob = new Robot();
		 board20x20y.addBoardModelAt(new Position(10, 10), rob);
		 board20x20y.moveBoardModelTo(rob, null);
	 }
	 
	 @Test (expected = NullPointerException.class)
	 public void moveBoardModelTo_nullBoardModelRejected(){
		 Robot rob = new Robot();
		 board20x20y.addBoardModelAt(new Position(10, 10), rob);
		 board20x20y.moveBoardModelTo(null, new Position(15, 15));
	 }
	 
	 @Test
	 public void getBoardModelsClassAt_inventoryModels(){
		 Battery bat = new Battery();
		 Battery bat2 = new Battery();
		 Battery bat3 = new Battery();
		 Robot rob = new Robot();
		 board20x20y.addBoardModelAt(new Position(10, 10), bat);
		 board20x20y.addBoardModelAt(new Position(10, 10), bat2);
		 board20x20y.addBoardModelAt(new Position(15, 10), bat3);
		 board20x20y.addBoardModelAt(new Position(10, 10), rob);
		 List<InventoryModel> list = board20x20y.getBoardModelsClassAt(new Position(10, 10), InventoryModel.class);
		 assertTrue(list.contains(bat));
		 assertTrue(list.contains(bat2));
		 assertFalse(list.contains(bat3));
		 assertFalse(list.contains(rob));
	 }
	 
	 @Test
	 public void getBoardModelsClassAt_inventoryModelsNullReturned(){
		 List<InventoryModel> list = board20x20y.getBoardModelsClassAt(new Position(10, 10), InventoryModel.class);
		 assertTrue(list == null);
	 }
	 
	 @Test
	 public void getAllBoardModelsClass_BoardModel(){
		 BoardModel r1 = new Robot();
		 BoardModel b1 = new Battery();
		 BoardModel w1 = new Wall();
		 Robot r2 = new Robot();
		 Battery b2 = new Battery();
		 Wall w2 = new Wall();
		 Robot r3 = new Robot();
		 Battery b3 = new Battery();
		 Wall w3 = new Wall();
		 InventoryModel b4 = new Battery();
		 
		 board19x20y.addBoardModelAt(new Position(3, 3), r1);
		 board19x20y.addBoardModelAt(new Position(8, 8), r2);
		 board19x20y.addBoardModelAt(new Position(5, 5), r3);
		 board19x20y.addBoardModelAt(new Position(6, 6), b1);
		 board19x20y.addBoardModelAt(new Position(7, 7), b2);
		 board19x20y.addBoardModelAt(new Position(8, 8), b3);
		 board19x20y.addBoardModelAt(new Position(7, 7), b4);
		 board19x20y.addBoardModelAt(new Position(2, 2), w1);
		 board19x20y.addBoardModelAt(new Position(1, 1), w2);
		 board19x20y.addBoardModelAt(new Position(0, 0), w3);
		 assertEquals(board19x20y.getNbBoardModels(),10);
		 
		 Set<BoardModel> temp = board19x20y.getAllBoardModelsClass(BoardModel.class);
		 assertTrue(temp.contains(r1));
		 assertTrue(temp.contains(r2));
		 assertTrue(temp.contains(r3));
		 assertTrue(temp.contains(b1));
		 assertTrue(temp.contains(b2));
		 assertTrue(temp.contains(b3));
		 assertTrue(temp.contains(b4));
		 assertTrue(temp.contains(w1));
		 assertTrue(temp.contains(w2));
		 assertTrue(temp.contains(w3)); 
	 }
	 
	 @Test
	 public void getAllBoardModelsClass_InventoryModel(){
		 BoardModel r1 = new Robot();
		 BoardModel b1 = new Battery();
		 BoardModel w1 = new Wall();
		 Robot r2 = new Robot();
		 Battery b2 = new Battery();
		 Wall w2 = new Wall();
		 Robot r3 = new Robot();
		 Battery b3 = new Battery();
		 Wall w3 = new Wall();
		 InventoryModel b4 = new Battery();
		 
		 board19x20y.addBoardModelAt(new Position(3, 3), r1);
		 board19x20y.addBoardModelAt(new Position(8, 8), r2);
		 board19x20y.addBoardModelAt(new Position(5, 5), r3);
		 board19x20y.addBoardModelAt(new Position(6, 6), b1);
		 board19x20y.addBoardModelAt(new Position(7, 7), b2);
		 board19x20y.addBoardModelAt(new Position(8, 8), b3);
		 board19x20y.addBoardModelAt(new Position(7, 7), b4);
		 board19x20y.addBoardModelAt(new Position(2, 2), w1);
		 board19x20y.addBoardModelAt(new Position(1, 1), w2);
		 board19x20y.addBoardModelAt(new Position(0, 0), w3);
		 assertEquals(board19x20y.getNbBoardModels(),10);
		 
		 Set<InventoryModel> temp = board19x20y.getAllBoardModelsClass(InventoryModel.class);
		 assertFalse(temp.contains(r1));
		 assertFalse(temp.contains(r2));
		 assertFalse(temp.contains(r3));
		 assertTrue(temp.contains(b1));
		 assertTrue(temp.contains(b2));
		 assertTrue(temp.contains(b3));
		 assertTrue(temp.contains(b4));
		 assertFalse(temp.contains(w1));
		 assertFalse(temp.contains(w2));
		 assertFalse(temp.contains(w3)); 
	 }
	 
	 @Test
	 public void getAllBoardModelsClass_Robot(){
		 BoardModel r1 = new Robot();
		 BoardModel b1 = new Battery();
		 BoardModel w1 = new Wall();
		 Robot r2 = new Robot();
		 Battery b2 = new Battery();
		 Wall w2 = new Wall();
		 Robot r3 = new Robot();
		 Battery b3 = new Battery();
		 Wall w3 = new Wall();
		 InventoryModel b4 = new Battery();
		 
		 board19x20y.addBoardModelAt(new Position(3, 3), r1);
		 board19x20y.addBoardModelAt(new Position(8, 8), r2);
		 board19x20y.addBoardModelAt(new Position(5, 5), r3);
		 board19x20y.addBoardModelAt(new Position(6, 6), b1);
		 board19x20y.addBoardModelAt(new Position(7, 7), b2);
		 board19x20y.addBoardModelAt(new Position(8, 8), b3);
		 board19x20y.addBoardModelAt(new Position(7, 7), b4);
		 board19x20y.addBoardModelAt(new Position(2, 2), w1);
		 board19x20y.addBoardModelAt(new Position(1, 1), w2);
		 board19x20y.addBoardModelAt(new Position(0, 0), w3);
		 assertEquals(board19x20y.getNbBoardModels(),10);
		 
		 Set<Robot> temp = board19x20y.getAllBoardModelsClass(Robot.class);
		 assertTrue(temp.contains(r1));
		 assertTrue(temp.contains(r2));
		 assertTrue(temp.contains(r3));
		 assertFalse(temp.contains(b1));
		 assertFalse(temp.contains(b2));
		 assertFalse(temp.contains(b3));
		 assertFalse(temp.contains(b4));
		 assertFalse(temp.contains(w1));
		 assertFalse(temp.contains(w2));
		 assertFalse(temp.contains(w3));
	 }
	 
	 @Test
	 public void getAllBoardModelsClass_Wall(){
		 BoardModel r1 = new Robot();
		 BoardModel b1 = new Battery();
		 BoardModel w1 = new Wall();
		 Robot r2 = new Robot();
		 Battery b2 = new Battery();
		 Wall w2 = new Wall();
		 Robot r3 = new Robot();
		 Battery b3 = new Battery();
		 Wall w3 = new Wall();
		 InventoryModel b4 = new Battery();
		 
		 board19x20y.addBoardModelAt(new Position(3, 3), r1);
		 board19x20y.addBoardModelAt(new Position(8, 8), r2);
		 board19x20y.addBoardModelAt(new Position(5, 5), r3);
		 board19x20y.addBoardModelAt(new Position(6, 6), b1);
		 board19x20y.addBoardModelAt(new Position(7, 7), b2);
		 board19x20y.addBoardModelAt(new Position(8, 8), b3);
		 board19x20y.addBoardModelAt(new Position(7, 7), b4);
		 board19x20y.addBoardModelAt(new Position(2, 2), w1);
		 board19x20y.addBoardModelAt(new Position(1, 1), w2);
		 board19x20y.addBoardModelAt(new Position(0, 0), w3);
		 assertEquals(board19x20y.getNbBoardModels(),10);
		 
		 Set<Wall> temp = board19x20y.getAllBoardModelsClass(Wall.class);
		 assertFalse(temp.contains(r1));
		 assertFalse(temp.contains(r2));
		 assertFalse(temp.contains(r3));
		 assertFalse(temp.contains(b1));
		 assertFalse(temp.contains(b2));
		 assertFalse(temp.contains(b3));
		 assertFalse(temp.contains(b4));
		 assertTrue(temp.contains(w1));
		 assertTrue(temp.contains(w2));
		 assertTrue(temp.contains(w3));
	 }
	 
	 @Test
	 public void getAllBoardModelsClass_Battery(){
		 BoardModel r1 = new Robot();
		 BoardModel b1 = new Battery();
		 BoardModel w1 = new Wall();
		 Robot r2 = new Robot();
		 Battery b2 = new Battery();
		 Wall w2 = new Wall();
		 Robot r3 = new Robot();
		 Battery b3 = new Battery();
		 Wall w3 = new Wall();
		 InventoryModel b4 = new Battery();
		 
		 board19x20y.addBoardModelAt(new Position(3, 3), r1);
		 board19x20y.addBoardModelAt(new Position(8, 8), r2);
		 board19x20y.addBoardModelAt(new Position(5, 5), r3);
		 board19x20y.addBoardModelAt(new Position(6, 6), b1);
		 board19x20y.addBoardModelAt(new Position(7, 7), b2);
		 board19x20y.addBoardModelAt(new Position(8, 8), b3);
		 board19x20y.addBoardModelAt(new Position(7, 7), b4);
		 board19x20y.addBoardModelAt(new Position(2, 2), w1);
		 board19x20y.addBoardModelAt(new Position(1, 1), w2);
		 board19x20y.addBoardModelAt(new Position(0, 0), w3);
		 assertEquals(board19x20y.getNbBoardModels(),10);
		 
		 Set<Battery> temp = board19x20y.getAllBoardModelsClass(Battery.class);
		 assertFalse(temp.contains(r1));
		 assertFalse(temp.contains(r2));
		 assertFalse(temp.contains(r3));
		 assertTrue(temp.contains(b1));
		 assertTrue(temp.contains(b2));
		 assertTrue(temp.contains(b3));
		 assertTrue(temp.contains(b4));
		 assertFalse(temp.contains(w1));
		 assertFalse(temp.contains(w2));
		 assertFalse(temp.contains(w3));
	 }
	 
	 @SuppressWarnings("unused")
	@Test
	 public void getAllTargets(){
		 Board b = new Board(20L,20L);
		 BoardModel b1 = new Battery(b, new Position(1L,1L));
		 BoardModel b2 = new Battery(b, new Position(1L,1L));
		 BoardModel b3 = new Battery(b, new Position(1L,2L));
		 BoardModel b4 = new Battery(b, new Position(1L,2L));
		 BoardModel b5 = new Battery(b, new Position(1L,3L));
		 BoardModel b6 = new Battery(b, new Position(1L,3L));
		 BoardModel b7 = new Battery(b, new Position(2L,1L));
		 BoardModel b8 = new Battery(b, new Position(2L,1L));
		 BoardModel b9 = new Battery(b, new Position(2L,2L));
		 BoardModel b10 = new Battery(b, new Position(2L,2L));
		 BoardModel b11 = new Battery(b, new Position(2L,3L));
		 BoardModel b12 = new Battery(b, new Position(2L,3L));
		 BoardModel b13 = new Battery(b, new Position(3L,1L));
		 BoardModel b14 = new Battery(b, new Position(3L,1L));
		 BoardModel b15 = new Battery(b, new Position(3L,2L));
		 BoardModel b16 = new Battery(b, new Position(3L,2L));
		 BoardModel b17 = new Battery(b, new Position(3L,3L));
		 BoardModel b18 = new Battery(b, new Position(3L,3L));
		 BoardModel b19 = new Battery(b, new Position(0L,2L));
		 BoardModel b20 = new Battery(b, new Position(0L,2L));
		 BoardModel b21 = new Battery(b, new Position(2L,0L));
		 BoardModel b22 = new Battery(b, new Position(2L,0L));
		 BoardModel b23 = new Battery(b, new Position(2L,4L));
		 BoardModel b24 = new Battery(b, new Position(2L,4L));
		 BoardModel b25 = new Battery(b, new Position(4L,2L));
		 BoardModel b26 = new Battery(b, new Position(4L,2L));
		 
		 assertEquals(b.getAllTargets(new Position(2L,2L), Direction.DOWN).size(),2);
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.DOWN).contains(b11));
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.DOWN).contains(b12));
		 assertEquals(b.getAllTargets(new Position(2L,2L), Direction.UP).size(),2);
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.UP).contains(b7));
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.UP).contains(b8));
		 assertEquals(b.getAllTargets(new Position(2L,2L), Direction.LEFT).size(),2);
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.LEFT).contains(b3));
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.LEFT).contains(b4));
		 assertEquals(b.getAllTargets(new Position(2L,2L), Direction.RIGHT).size(),2);
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.RIGHT).contains(b15));
		 assertTrue(b.getAllTargets(new Position(2L,2L), Direction.RIGHT).contains(b16));
		 
		 assertEquals(b.getAllTargets(new Position(2L,4L), Direction.RIGHT).size(),0); 
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void getAllTargets_nullPosition(){
		Board b = new Board(20L,20L);
		b.getAllTargets(null, Direction.DOWN);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void getAllTargets_illegalPosition(){
		Board b = new Board(20L,20L);
		b.getAllTargets(new Position(21L,2L), null);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void getAllTargets_invalidDirection(){
		Board b = new Board(20L,20L);
		b.getAllTargets(new Position(1L,2L), null);
	 }
	 
	 @Test
	 public void isSituatedNextTo(){
		 Board b = new Board(20L,20L);
		 Board b2 = new Board(20L,20L);
		 Robot r1 = new Robot(b,new Position(0L,1L));
		 Robot r2 = new Robot(b,new Position(1L,0L));
		 Robot r3 = new Robot(b,new Position(2L,1L));
		 Robot r4 = new Robot(b,new Position(1L,2L));
		 Robot r5 = new Robot(b,new Position(1L,1L));
		 Robot r6 = new Robot(b,new Position(1L,3L));
		 Robot r7 = new Robot(b,new Position(0L,0L));
		 Robot r8 = new Robot(b2,new Position(1L,2L));
		 Robot r9 = new Robot(b,new Position(2L,2L));
		 Position p = new Position(1L,1L);
		 assertTrue(b.isSituatedNextTo(p, r1));
		 assertTrue(b.isSituatedNextTo(p, r2));
		 assertTrue(b.isSituatedNextTo(p, r3));
		 assertTrue(b.isSituatedNextTo(p, r4));
		 assertFalse(b.isSituatedNextTo(p, r5));
		 assertFalse(b.isSituatedNextTo(p, r6));
		 assertFalse(b.isSituatedNextTo(p, r7));
		 assertFalse(b.isSituatedNextTo(p, r8));
		 assertFalse(b.isSituatedNextTo(p, r9));
		 assertFalse(b.isSituatedNextTo(p, null));
		 assertFalse(b.isSituatedNextTo(null, r1));
		 assertFalse(b.isSituatedNextTo(null, null));
		 assertFalse(b.isSituatedNextTo(new Position(21L,21L), r1));
		 assertFalse(b.isSituatedNextTo(new Position(-1L,-1L), r1));
	 }
	 
	 @Test
	 public void hasInArea(){
		 Board b = new Board(20L,20L);
		 Board b2 = new Board(20L,20L);
		 Robot r1 = new Robot(b,new Position(0L,1L));
		 Robot r2 = new Robot(b,new Position(1L,0L));
		 Robot r3 = new Robot(b,new Position(2L,1L));
		 Robot r4 = new Robot(b,new Position(1L,2L));
		 Robot r5 = new Robot(b,new Position(1L,1L));
		 Robot r6 = new Robot(b,new Position(1L,3L));
		 Robot r7 = new Robot(b,new Position(0L,0L));
		 Robot r8 = new Robot(b2,new Position(1L,2L));
		 Robot r9 = new Robot(b,new Position(2L,2L));
		 Robot r10 = new Robot(b,new Position(3L,2L));
		 Position p = new Position(1L,1L);
		 assertTrue(b.hasInArea(p, r1, 1L));
		 assertTrue(b.hasInArea(p, r2, 1L));
		 assertTrue(b.hasInArea(p, r3, 1L));
		 assertTrue(b.hasInArea(p, r4, 1L));
		 assertTrue(b.hasInArea(p, r5, 1L));
		 assertFalse(b.hasInArea(p, r6, 1L));
		 assertTrue(b.hasInArea(p, r7, 1L));
		 assertFalse(b.hasInArea(p, r8, 1L));
		 assertTrue(b.hasInArea(p, r9, 1L));
		 assertFalse(b.hasInArea(p, null, 1L));
		 assertFalse(b.hasInArea(p, r10, 1L));
		 assertFalse(b.hasInArea(null, r1, 1L));
		 assertFalse(b.hasInArea(null, null, 1L));
		 assertFalse(b.hasInArea(new Position(21L,21L), r1, 1L));
		 assertFalse(b.hasInArea(new Position(-1L,-1L), r1, 1L));
	 }
	 
	 @Test
	 public void hasInAreaII(){
		 Board b = new Board(20L,20L);
		 Board b2 = new Board(20L,20L);
		 Robot r1 = new Robot(b,new Position(0L,1L));
		 Robot r2 = new Robot(b,new Position(1L,0L));
		 Robot r3 = new Robot(b,new Position(2L,1L));
		 Robot r4 = new Robot(b,new Position(1L,2L));
		 Robot r5 = new Robot(b,new Position(1L,1L));
		 Robot r6 = new Robot(b,new Position(1L,3L));
		 Robot r7 = new Robot(b,new Position(0L,0L));
		 Robot r8 = new Robot(b2,new Position(1L,2L));
		 Robot r9 = new Robot(b,new Position(2L,2L));
		 Robot r10 = new Robot(b,new Position(3L,2L));
		 Position p = new Position(2L,2L);
		 assertFalse(b.hasInArea(p, r1, 1L));
		 assertFalse(b.hasInArea(p, r2, 1L));
		 assertTrue(b.hasInArea(p, r3, 1L));
		 assertTrue(b.hasInArea(p, r4, 1L));
		 assertTrue(b.hasInArea(p, r5, 1L));
		 assertTrue(b.hasInArea(p, r6, 1L));
		 assertFalse(b.hasInArea(p, r7, 1L));
		 assertFalse(b.hasInArea(p, r8, 1L));
		 assertTrue(b.hasInArea(p, r9, 1L));
		 assertFalse(b.hasInArea(p, null, 1L));
		 assertTrue(b.hasInArea(p, r10, 1L));
		 assertFalse(b.hasInArea(null, r1, 1L));
		 assertFalse(b.hasInArea(null, null, 1L));
		 assertFalse(b.hasInArea(new Position(21L,21L), r1, 1L));
		 assertFalse(b.hasInArea(new Position(-1L,-1L), r1, 1L));
	 }
	 
	 @Test
	 public void iterator_next_normalCase(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Battery b1 = new Battery(b,new Position(2L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b2 = new Battery(b,new Position(1L,2L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Battery b3 = new Battery(b,new Position(0L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b4 = new Battery(b,new Position(0L,1L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 int count = 0;
		 Iterator<BoardModel> ci = b.iterator();
		 while (ci.hasNext()){
			count++;
			@SuppressWarnings("unused")
			BoardModel bm = ci.next();
			//System.out.println(bm);
		 }
		 assertEquals(8, count);
	 }
	 
	 @Test (expected = NoSuchElementException.class)
	 public void iterator_next_tillThrow(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Battery b1 = new Battery(b,new Position(2L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b2 = new Battery(b,new Position(1L,2L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Battery b3 = new Battery(b,new Position(0L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b4 = new Battery(b,new Position(0L,1L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 Iterator<BoardModel> ci = b.iterator();
		 for(int i=1; i<=9; i++){
				@SuppressWarnings("unused")
				BoardModel bm = ci.next();
				//System.out.println(bm);
			 }
	 }
	 
	 @Test (expected = UnsupportedOperationException.class)
	 public void iterator_remove_normalCase(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 Iterator<BoardModel> ci = b.iterator();
		 ci.remove();
	 }
	 
	 @Test
	 public void conditionIterator_next_normalCase(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Battery b1 = new Battery(b,new Position(2L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b2 = new Battery(b,new Position(1L,2L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Battery b3 = new Battery(b,new Position(0L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b4 = new Battery(b,new Position(0L,1L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 int count = 0;
		 Iterator<BoardModel> ci = b.conditionIterator(new EnergyTester(BasicComparison.GE, new Energy(1000)));
		 while (ci.hasNext()){
			count++;
			@SuppressWarnings("unused")
			BoardModel bm = ci.next();
			//System.out.println(bm);
		 }
		 assertEquals(3, count);
	 }
	 
	 @Test (expected = NoSuchElementException.class)
	 public void conditionIterator_next_tillThrow(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Battery b1 = new Battery(b,new Position(2L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b2 = new Battery(b,new Position(1L,2L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Battery b3 = new Battery(b,new Position(0L,1L), new Accu(new Energy(800)));
		 @SuppressWarnings("unused")
		 Battery b4 = new Battery(b,new Position(0L,1L), new Accu(new Energy(1000)));
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 Iterator<BoardModel> ci = b.conditionIterator(new EnergyTester(BasicComparison.GE, new Energy(1000)));
		 for(int i=1; i<=4; i++){
			@SuppressWarnings("unused")
			BoardModel bm = ci.next();
			//System.out.println(bm);
		 }
	 }	 
	 
	 @Test (expected = UnsupportedOperationException.class)
	 public void conditionIterator_remove_normalCase(){
		 Board b = new Board();
		 @SuppressWarnings("unused")
		 Robot r1 = new Robot(b,new Position(0L,1L), new Accu(new Energy(400)), Direction.UP);
		 @SuppressWarnings("unused")
		 Robot r2 = new Robot(b,new Position(1L,0L), new Accu(new Energy(1500)), Direction.UP);
		 @SuppressWarnings("unused")
		 Wall w1 = new Wall(b,new Position(1L,1L));
		 @SuppressWarnings("unused")
		 Wall w2 = new Wall(b,new Position(1L,3L));
		 
		 Iterator<BoardModel> ci = b.conditionIterator(new EnergyTester(BasicComparison.GE, new Energy(1000)));
		 ci.remove();
	 }
}
