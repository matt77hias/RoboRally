package roborally.facade.test;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Dimension;
import roborally.board.Position;
import roborally.facade.Facade;
import roborally.facade.IFacade;
import roborally.model.BoardModel;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Energy;
import roborally.model.inventory.item.Battery;
import roborally.model.inventory.item.InventoryEnergyModel;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.inventory.item.ItemBox;
import roborally.model.inventory.item.RepairKit;
import roborally.model.inventory.item.SurpriseBox;
import roborally.model.staticObject.Wall;

/**
 * A test class for IFacade objects.
 * 
 * @version Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class IFacadeTest {
	
	IFacade<Board, Robot, Wall, Battery, RepairKit, SurpriseBox> f;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception{
	}

	@Before
	public void setUp() throws Exception{
		f = new Facade();
	}

	@After
	public void tearDown() throws Exception{
	}
	
	@Test
	public void createBoard(){
		Board b1 = f.createBoard(-10L, 10L);
		assertTrue(b1 == null);
		Board b2 = f.createBoard(10L, -10L);
		assertTrue(b2 == null);
		Board b3 = f.createBoard(-10L, -10L);
		assertTrue(b3 == null);
		
		Board b4 = f.createBoard(0L, 1L);
		assertEquals(b4.getSizeAt(Dimension.HORIZONTAL),0L);
		assertEquals(b4.getSizeAt(Dimension.VERTICAL),1L);
		Board b5 = f.createBoard(1L, 0L);
		assertEquals(b5.getSizeAt(Dimension.HORIZONTAL),1L);
		assertEquals(b5.getSizeAt(Dimension.VERTICAL),0L);
		Board b6 = f.createBoard(0L, 0L);
		assertEquals(b6.getSizeAt(Dimension.HORIZONTAL),0L);
		assertEquals(b6.getSizeAt(Dimension.VERTICAL),0L);
		
		Board b7 = f.createBoard(5L, 6L);
		assertEquals(b7.getSizeAt(Dimension.HORIZONTAL),5L);
		assertEquals(b7.getSizeAt(Dimension.VERTICAL),6L);
		
		Board b8 = f.createBoard(Long.MAX_VALUE, 1L);
		assertEquals(b8.getSizeAt(Dimension.HORIZONTAL),Long.MAX_VALUE);
		assertEquals(b8.getSizeAt(Dimension.VERTICAL),1L);
		Board b9 = f.createBoard(1L, Long.MAX_VALUE);
		assertEquals(b9.getSizeAt(Dimension.HORIZONTAL),1L);
		assertEquals(b9.getSizeAt(Dimension.VERTICAL),Long.MAX_VALUE);
		Board b10 = f.createBoard(Long.MAX_VALUE, Long.MAX_VALUE);
		assertEquals(b10.getSizeAt(Dimension.HORIZONTAL),Long.MAX_VALUE);
		assertEquals(b10.getSizeAt(Dimension.VERTICAL),Long.MAX_VALUE);
		
		assertFalse(b4.isTerminated());
		assertFalse(b5.isTerminated());
		assertFalse(b6.isTerminated());
		assertFalse(b7.isTerminated());
		assertFalse(b8.isTerminated());
		assertFalse(b9.isTerminated());
		assertFalse(b10.isTerminated());
	}
	
	@Test
	public void merge(){
		Board b1 = f.createBoard(10L, 10L);
		Board b2 = f.createBoard(20L, 20L);
		
		Wall w1 = f.createWall();
		Wall w2 = f.createWall();
		Wall w3 = f.createWall();
		Wall w4 = f.createWall();
		
		f.putWall(b1, 1L, 2L, w1);
		f.putWall(b2, 1L, 2L, w2);
		f.putWall(b2, 11L, 12L, w3);
		f.putWall(b2, 2L, 2L, w4);
		
		f.merge(b1, b2);
		
		assertFalse(w1.isTerminated());
		assertTrue(w2.isTerminated());
		assertTrue(w3.isTerminated());
		assertFalse(w4.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertEquals(w1.getBoard(), b1);
		assertTrue(w2.getBoard() == null);
		assertTrue(w3.getBoard() == null);
		assertEquals(w4.getBoard(), b1);
		
		f.merge(b1, null);
		
		assertFalse(w1.isTerminated());
		assertTrue(w2.isTerminated());
		assertTrue(w3.isTerminated());
		assertFalse(w4.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertEquals(w1.getBoard(), b1);
		assertTrue(w2.getBoard() == null);
		assertTrue(w3.getBoard() == null);
		assertEquals(w4.getBoard(), b1);
		
		f.merge(b1, b2);
		
		assertFalse(w1.isTerminated());
		assertTrue(w2.isTerminated());
		assertTrue(w3.isTerminated());
		assertFalse(w4.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertEquals(w1.getBoard(), b1);
		assertTrue(w2.getBoard() == null);
		assertTrue(w3.getBoard() == null);
		assertEquals(w4.getBoard(), b1);
		
		f.merge(b2, b1);
		
		assertFalse(w1.isTerminated());
		assertTrue(w2.isTerminated());
		assertTrue(w3.isTerminated());
		assertFalse(w4.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertEquals(w1.getBoard(), b1);
		assertTrue(w2.getBoard() == null);
		assertTrue(w3.getBoard() == null);
		assertEquals(w4.getBoard(), b1);
		
		f.merge(b1, b1);
		
		assertFalse(w1.isTerminated());
		assertTrue(w2.isTerminated());
		assertTrue(w3.isTerminated());
		assertFalse(w4.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(b2.isTerminated());
		
		assertEquals(w1.getBoard(), b1);
		assertTrue(w2.getBoard() == null);
		assertTrue(w3.getBoard() == null);
		assertEquals(w4.getBoard(), b1);
	}
	
	@Test
	public void createBattery(){
		Battery b1 = f.createBattery(-10D, 10);
		assertTrue(b1 == null);
		Battery b2 = f.createBattery(0D, 10);
		assertEquals(b2.getAccu().getAmountOfEnergy().getEnergyAmount(), 0D, 0.01D);
		assertEquals(b2.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(b2.isTerminated());
		assertFalse(b2.isPickedUp());
		Battery b3 = f.createBattery(1000D, -10);
		assertEquals(b3.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(b3.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(b3.isTerminated());
		assertFalse(b3.isPickedUp());
		Battery b4 = f.createBattery(1000D, 0);
		assertEquals(b4.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(b4.getWeight().getWeightAmount(), 0, 0.01D);
		assertFalse(b4.isTerminated());
		assertFalse(b4.isPickedUp());
		Battery b5 = f.createBattery(1000D, 100);
		assertEquals(b5.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(b5.getWeight().getWeightAmount(), 100, 0.01D);
		assertFalse(b5.isTerminated());
		assertFalse(b5.isPickedUp());
		Battery b6 = f.createBattery(5001D, 100);
		assertTrue(b6 == null);
	}
	
	@Test
	public void putBattery(){
		Battery b1 = f.createBattery(1000D, 100);
		assertTrue(b1.getBoard() == null);
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		Board board3 = f.createBoard(10L, 10L);
		
		f.putBattery(board1, 11L, 10L, b1);
		assertTrue(b1.getBoard() == null);
		assertTrue(b1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b1));
		f.putBattery(board1, 10L, 11L, b1);
		assertTrue(b1.getBoard() == null);
		assertTrue(b1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b1));
		f.putBattery(board1, 11L, 11L, b1);
		assertTrue(b1.getBoard() == null);
		assertTrue(b1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b1));
		f.putBattery(board1, 1L, 1L, b1);
		assertEquals(b1.getBoard(), board1);
		assertEquals(b1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(b1));
		f.putBattery(board1, 1L, 2L, b1);
		assertEquals(b1.getBoard(), board1);
		assertEquals(b1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(b1));
		assertFalse(board2.containsBoardModel_allCheck(b1));
		
		Battery b2 = f.createBattery(1000D, 100);
		b2.pickUp();
		f.putBattery(board1, 4L, 4L, b2);
		assertTrue(b2.getBoard() == null);
		assertTrue(b2.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b2));
		
		Battery b3 = f.createBattery(1000D, 100);
		b3.terminate();
		f.putBattery(board1, 4L, 4L, b3);
		assertTrue(b3.getPosition() == null);
		assertTrue(b3.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b3));
		
		Battery b4 = f.createBattery(1000D, 100);
		f.putBattery(board1, 1L, 1L, b4);
		assertEquals(b1.getBoard(), board1);
		assertEquals(b1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(b1));
		assertEquals(b4.getBoard(), board1);
		assertEquals(b4.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(b4));
		
		board1.terminate();
		assertTrue(b1.getBoard() == null);
		assertTrue(b1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b1));
		assertTrue(b4.getBoard() == null);
		assertTrue(b4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b4));
		assertTrue(board1.isTerminated());
		assertTrue(b1.isTerminated());
		assertTrue(b4.isTerminated());
		
		Battery b5 = f.createBattery(1000D, 100);
		f.putBattery(board1, 1L, 1L, b5);
		assertTrue(b5.getBoard() == null);
		assertTrue(b5.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(b5));
		
		Wall w1 = f.createWall();
		f.putWall(board3, 1L, 1L, w1);
		Battery b6 = f.createBattery(1000D, 100);
		f.putBattery(board3, 1L, 1L, b6);
		assertTrue(b6.getBoard() == null);
		assertTrue(b6.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(b6));
		
		Robot r1 = f.createRobot(1, 100D);
		f.putRobot(board3, 2L, 2L, r1);
		Battery b7 = f.createBattery(1000D, 100);
		f.putBattery(board3, 1L, 1L, b7);
		assertTrue(b7.getBoard() == null);
		assertTrue(b7.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(b7));
	}
	
	@Test
	public void getBatteryX(){
		Board board1 = f.createBoard(10L, 10L);
		Battery b1 = f.createBattery(1000D, 100);
		f.putBattery(board1, 10L, 9L, b1);
		assertEquals(f.getBatteryX(b1), 10L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getBatteryX_nullBoard(){
		Battery b1 = f.createBattery(1000D, 100);
		f.getBatteryX(b1);
	}
	
	@Test
	public void getBatteryY(){
		Board board1 = f.createBoard(10L, 10L);
		Battery b1 = f.createBattery(1000D, 100);
		f.putBattery(board1, 10L, 9L, b1);
		assertEquals(f.getBatteryY(b1), 9L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getBatteryY_nullBoard(){
		Battery b1 = f.createBattery(1000D, 100);
		f.getBatteryY(b1);
	}
	
	@Test
	public void createRepairKit(){
		RepairKit k1 = f.createRepairKit(-10D, 10);
		assertTrue(k1 == null);
		RepairKit k2 = f.createRepairKit(0D, 10);
		assertEquals(k2.getAccu().getAmountOfEnergy().getEnergyAmount(), 0D, 0.01D);
		assertEquals(k2.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(k2.isTerminated());
		assertFalse(k2.isPickedUp());
		RepairKit k3 = f.createRepairKit(1000D, -10);
		assertEquals(k3.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(k3.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(k3.isTerminated());
		assertFalse(k3.isPickedUp());
		RepairKit k4 = f.createRepairKit(1000D, 0);
		assertEquals(k4.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(k4.getWeight().getWeightAmount(), 0, 0.01D);
		assertFalse(k4.isTerminated());
		assertFalse(k4.isPickedUp());
		RepairKit k5 = f.createRepairKit(1000D, 100);
		assertEquals(k5.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(k5.getWeight().getWeightAmount(), 100, 0.01D);
		assertFalse(k5.isTerminated());
		assertFalse(k5.isPickedUp());
		RepairKit k6 = f.createRepairKit(5001D, 100);
		assertEquals(k6.getAccu().getAmountOfEnergy().getEnergyAmount(), 5001D, 0.01D);
		assertEquals(k6.getWeight().getWeightAmount(), 100, 0.01D);
		assertFalse(k6.isTerminated());
		assertFalse(k6.isPickedUp());
	}
	
	@Test
	public void putRepairKit(){
		RepairKit k1 = f.createRepairKit(1000D, 100);
		assertTrue(k1.getBoard() == null);
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		Board board3 = f.createBoard(10L, 10L);
		
		f.putRepairKit(board1, 11L, 10L, k1);
		assertTrue(k1.getBoard() == null);
		assertTrue(k1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k1));
		f.putRepairKit(board1, 10L, 11L, k1);
		assertTrue(k1.getBoard() == null);
		assertTrue(k1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k1));
		f.putRepairKit(board1, 11L, 11L, k1);
		assertTrue(k1.getBoard() == null);
		assertTrue(k1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k1));
		f.putRepairKit(board1, 1L, 1L, k1);
		assertEquals(k1.getBoard(), board1);
		assertEquals(k1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(k1));
		f.putRepairKit(board1, 1L, 2L, k1);
		assertEquals(k1.getBoard(), board1);
		assertEquals(k1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(k1));
		assertFalse(board2.containsBoardModel_allCheck(k1));
		
		RepairKit k2 = f.createRepairKit(1000D, 100);
		k2.pickUp();
		f.putRepairKit(board1, 4L, 4L, k2);
		assertTrue(k2.getBoard() == null);
		assertTrue(k2.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k2));
		
		RepairKit k3 = f.createRepairKit(1000D, 100);
		k3.terminate();
		f.putRepairKit(board1, 4L, 4L, k3);
		assertTrue(k3.getPosition() == null);
		assertTrue(k3.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k3));
		
		RepairKit k4 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 1L, 1L, k4);
		assertEquals(k1.getBoard(), board1);
		assertEquals(k1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(k1));
		assertEquals(k4.getBoard(), board1);
		assertEquals(k4.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(k4));
		
		board1.terminate();
		assertTrue(k1.getBoard() == null);
		assertTrue(k1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k1));
		assertTrue(k4.getBoard() == null);
		assertTrue(k4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k4));
		assertTrue(board1.isTerminated());
		assertTrue(k1.isTerminated());
		assertTrue(k4.isTerminated());
		
		RepairKit k5 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 1L, 1L, k5);
		assertTrue(k5.getBoard() == null);
		assertTrue(k5.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k5));
		
		Wall w1 = f.createWall();
		f.putWall(board3, 1L, 1L, w1);
		RepairKit k6 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board3, 1L, 1L, k6);
		assertTrue(k6.getBoard() == null);
		assertTrue(k6.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(k6));
		
		Robot r1 = f.createRobot(1, 100D);
		f.putRobot(board3, 2L, 2L, r1);
		RepairKit k7 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board3, 1L, 1L, k7);
		assertTrue(k7.getBoard() == null);
		assertTrue(k7.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(k7));
	}
	
	@Test
	public void getRepairKitX(){
		Board board1 = f.createBoard(10L, 10L);
		RepairKit k1 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 10L, 9L, k1);
		assertEquals(f.getRepairKitX(k1), 10L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getRepairKitX_nullBoard(){
		RepairKit k1 = f.createRepairKit(1000D, 100);
		f.getRepairKitX(k1);
	}
	
	@Test
	public void getRepairKitY(){
		Board board1 = f.createBoard(10L, 10L);
		RepairKit k1 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 10L, 9L, k1);
		assertEquals(f.getRepairKitY(k1), 9L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getRepairKitY_nullBoard(){
		RepairKit k1 = f.createRepairKit(1000D, 100);
		f.getRepairKitY(k1);
	}
	
	@Test
	public void createSurpriseBox(){
		SurpriseBox s2 = f.createSurpriseBox(10);
		assertEquals(s2.getInventory().getNbOfInventoryItems(), 1);
		assertEquals(s2.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(s2.isTerminated());
		assertFalse(s2.isPickedUp());
		SurpriseBox s3 = f.createSurpriseBox(-10);
		assertEquals(s3.getInventory().getNbOfInventoryItems(), 1);
		assertEquals(s3.getWeight().getWeightAmount(), 10, 0.01D);
		assertFalse(s3.isTerminated());
		assertFalse(s3.isPickedUp());
		SurpriseBox s4 = f.createSurpriseBox(0);
		assertEquals(s4.getInventory().getNbOfInventoryItems(), 1);
		assertEquals(s4.getWeight().getWeightAmount(), 0, 0.01D);
		assertFalse(s4.isTerminated());
		assertFalse(s4.isPickedUp());
		SurpriseBox s5 = f.createSurpriseBox(100);
		assertEquals(s5.getInventory().getNbOfInventoryItems(), 1);
		assertEquals(s5.getWeight().getWeightAmount(), 100, 0.01D);
		assertFalse(s5.isTerminated());
		assertFalse(s5.isPickedUp());
	}
	
	@Test
	public void putSurpriseBox(){
		SurpriseBox s1 = f.createSurpriseBox(100);
		assertTrue(s1.getBoard() == null);
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		Board board3 = f.createBoard(10L, 10L);
		
		f.putSurpriseBox(board1, 11L, 10L, s1);
		assertTrue(s1.getBoard() == null);
		assertTrue(s1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s1));
		f.putSurpriseBox(board1, 10L, 11L, s1);
		assertTrue(s1.getBoard() == null);
		assertTrue(s1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s1));
		f.putSurpriseBox(board1, 11L, 11L, s1);
		assertTrue(s1.getBoard() == null);
		assertTrue(s1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s1));
		f.putSurpriseBox(board1, 1L, 1L, s1);
		assertEquals(s1.getBoard(), board1);
		assertEquals(s1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(s1));
		f.putSurpriseBox(board1, 1L, 2L, s1);
		assertEquals(s1.getBoard(), board1);
		assertEquals(s1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(s1));
		assertFalse(board2.containsBoardModel_allCheck(s1));
		
		SurpriseBox s2 = f.createSurpriseBox(100);
		s2.pickUp();
		f.putSurpriseBox(board1, 4L, 4L, s2);
		assertTrue(s2.getBoard() == null);
		assertTrue(s2.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s2));
		
		SurpriseBox s3 = f.createSurpriseBox(100);
		s3.terminate();
		f.putSurpriseBox(board1, 4L, 4L, s3);
		assertTrue(s3.getPosition() == null);
		assertTrue(s3.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s3));
		
		SurpriseBox s4 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 1L, 1L, s4);
		assertEquals(s1.getBoard(), board1);
		assertEquals(s1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(s1));
		assertEquals(s4.getBoard(), board1);
		assertEquals(s4.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(s4));
		
		board1.terminate();
		assertTrue(s1.getBoard() == null);
		assertTrue(s1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s1));
		assertTrue(s4.getBoard() == null);
		assertTrue(s4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s4));
		assertTrue(board1.isTerminated());
		assertTrue(s1.isTerminated());
		assertTrue(s4.isTerminated());
		
		SurpriseBox s5 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 1L, 1L, s5);
		assertTrue(s5.getBoard() == null);
		assertTrue(s5.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(s5));
		
		Wall w1 = f.createWall();
		f.putWall(board3, 1L, 1L, w1);
		SurpriseBox s6 = f.createSurpriseBox(100);
		f.putSurpriseBox(board3, 1L, 1L, s6);
		assertTrue(s6.getBoard() == null);
		assertTrue(s6.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(s6));
		
		Robot r1 = f.createRobot(1, 100D);
		f.putRobot(board3, 2L, 2L, r1);
		SurpriseBox s7 = f.createSurpriseBox(100);
		f.putSurpriseBox(board3, 1L, 1L, s7);
		assertTrue(s7.getBoard() == null);
		assertTrue(s7.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(s7));
	}
	
	@Test
	public void getSurpriseBoxX(){
		Board board1 = f.createBoard(10L, 10L);
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 10L, 9L, s1);
		assertEquals(f.getSurpriseBoxX(s1), 10L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getSurpriseBoxX_nullBoard(){
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.getSurpriseBoxX(s1);
	}
	
	@Test
	public void getSurpriseBoxY(){
		Board board1 = f.createBoard(10L, 10L);
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 10L, 9L, s1);
		assertEquals(f.getSurpriseBoxY(s1), 9L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getSurpriseBoxY_nullBoard(){
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.getSurpriseBoxY(s1);
	}
	
	@Test
	public void createRobot(){
		Robot r1 = f.createRobot(1, -10D);
		assertTrue(r1 == null);
		Robot r2 = f.createRobot(0, 0D);
		assertEquals(r2.getAccu().getAmountOfEnergy().getEnergyAmount(), 0D, 0.01D);
		assertFalse(r2.isTerminated());
		assertEquals(r2.getDirection(), Direction.UP);
		Robot r3 = f.createRobot(1, 1000D);
		assertEquals(r3.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertFalse(r3.isTerminated());
		assertEquals(r3.getDirection(), Direction.RIGHT);
		Robot r4 = f.createRobot(2, 1000D);
		assertEquals(r4.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertFalse(r4.isTerminated());
		assertEquals(r4.getDirection(), Direction.DOWN);
		Robot r5 = f.createRobot(3, 1000D);
		assertEquals(r5.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertFalse(r5.isTerminated());
		assertEquals(r5.getDirection(), Direction.LEFT);
		Robot r6 = f.createRobot(1, 20001D);
		assertTrue(r6 == null);
	}
	
	@Test
	public void putRobot(){
		Robot r1 = f.createRobot(1, 1000D);
		assertTrue(r1.getBoard() == null);
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		Board board3 = f.createBoard(10L, 10L);
		
		f.putRobot(board1, 11L, 10L, r1);
		assertTrue(r1.getBoard() == null);
		assertTrue(r1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r1));
		f.putRobot(board1, 10L, 11L, r1);
		assertTrue(r1.getBoard() == null);
		assertTrue(r1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r1));
		f.putRobot(board1, 11L, 11L, r1);
		assertTrue(r1.getBoard() == null);
		assertTrue(r1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r1));
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(r1.getBoard(), board1);
		assertEquals(r1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(r1));
		f.putRobot(board1, 1L, 2L, r1);
		assertEquals(r1.getBoard(), board1);
		assertEquals(r1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(r1));
		assertFalse(board2.containsBoardModel_allCheck(r1));
		
		Robot r3 = f.createRobot(1, 1000D);
		r3.terminate();
		f.putRobot(board1, 4L, 4L, r3);
		assertTrue(r3.getPosition() == null);
		assertTrue(r3.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r3));
		
		RepairKit k4 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 2L, 2L, k4);
		Robot r4 = f.createRobot(1, 1000D);
		f.putRobot(board1, 2L, 2L, r4);
		assertEquals(r4.getBoard(), board1);
		assertEquals(r4.getPosition(), new Position(2L, 2L));
		assertTrue(board1.containsBoardModel_positionCheck(r4));
		assertEquals(k4.getBoard(), board1);
		assertEquals(k4.getPosition(), new Position(2L, 2L));
		assertTrue(board1.containsBoardModel_positionCheck(k4));
		
		board1.terminate();
		assertTrue(r1.getBoard() == null);
		assertTrue(r1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r1));
		assertTrue(r4.getBoard() == null);
		assertTrue(r4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r4));
		assertTrue(k4.getBoard() == null);
		assertTrue(k4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k4));
		assertTrue(board1.isTerminated());
		assertTrue(r1.isTerminated());
		assertTrue(r4.isTerminated());
		assertTrue(k4.isTerminated());
		
		Robot r5 = f.createRobot(1, 1000D);
		f.putRobot(board1, 1L, 1L, r5);
		assertTrue(r5.getBoard() == null);
		assertTrue(r5.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(r5));
		
		Wall w1 = f.createWall();
		f.putWall(board3, 1L, 1L, w1);
		Robot r6 = f.createRobot(1, 1000D);
		f.putRobot(board3, 1L, 1L, r6);
		assertTrue(r6.getBoard() == null);
		assertTrue(r6.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(r6));
		
		Robot r7 = f.createRobot(1, 100D);
		f.putRobot(board3, 2L, 2L, r7);
		Robot r8 = f.createRobot(1, 1000D);
		f.putRobot(board3, 1L, 1L, r8);
		assertTrue(r8.getBoard() == null);
		assertTrue(r8.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(r8));
	}
	
	@Test
	public void getRobotX(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 1000D);
		f.putRobot(board1, 10L, 9L, r1);
		assertEquals(f.getRobotX(r1), 10L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getRobotX_nullBoard(){
		Robot r1 = f.createRobot(1, 1000D);
		f.getRobotX(r1);
	}
	
	@Test
	public void getRobotY(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 1000D);
		f.putRobot(board1, 10L, 9L, r1);
		assertEquals(f.getRobotY(r1), 9L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getRobotY_nullBoard(){
		Robot r1 = f.createRobot(1, 1000D);
		f.getRobotY(r1);
	}
	
	@Test
	public void getOrientationI(){
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(r1.getDirection(), Direction.UP);
		Robot r2 = f.createRobot(1, 1000D);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		Robot r3 = f.createRobot(2, 1000D);
		assertEquals(r3.getDirection(), Direction.DOWN);
		Robot r4 = f.createRobot(3, 1000D);
		assertEquals(r4.getDirection(), Direction.LEFT);
	}
	
	@Test
	public void getOrientationII(){
		Robot r1 = f.createRobot(-4, 1000D);
		assertEquals(r1.getDirection(), Direction.UP);
		Robot r2 = f.createRobot(-3, 1000D);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		Robot r3 = f.createRobot(-2, 1000D);
		assertEquals(r3.getDirection(), Direction.DOWN);
		Robot r4 = f.createRobot(-1, 1000D);
		assertEquals(r4.getDirection(), Direction.LEFT);
	}
	
	@Test
	public void getOrientationIII(){
		Robot r1 = f.createRobot(4, 1000D);
		assertEquals(r1.getDirection(), Direction.UP);
		Robot r2 = f.createRobot(5, 1000D);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		Robot r3 = f.createRobot(6, 1000D);
		assertEquals(r3.getDirection(), Direction.DOWN);
		Robot r4 = f.createRobot(7, 1000D);
		assertEquals(r4.getDirection(), Direction.LEFT);
	}
	
	@Test
	public void getEnergy(){
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
	}
	
	@Test
	public void getEnergy_terminated(){
		Robot r1 = f.createRobot(0, 1000D);
		r1.terminate();
		assertEquals(f.getEnergy(r1), 0D, 0.01D);
	}
	
	@Test
	public void move_noBoard(){
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		f.move(r1);
		Robot r2 = f.createRobot(1, 1000D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		f.move(r2);
		Robot r3 = f.createRobot(2, 1000D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		f.move(r3);
		Robot r4 = f.createRobot(3, 1000D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
		f.move(r4);
		
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
	}
	
	@Test
	public void move(){
		Board b1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		f.putRobot(b1, 1L, 1L, r1);
		Robot r2 = f.createRobot(1, 1000D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		f.putRobot(b1, 10L, 10L, r2);
		Robot r3 = f.createRobot(2, 1000D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		assertEquals(r3.getDirection(), Direction.DOWN);
		f.putRobot(b1, 20L, 20L, r3);
		Robot r4 = f.createRobot(3, 1000D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
		assertEquals(r4.getDirection(), Direction.LEFT);
		f.putRobot(b1, 30L, 30L, r4);
		
		f.move(r1);
		f.move(r2);
		f.move(r3);
		f.move(r4);
		
		assertEquals(f.getEnergy(r1), 500D, 0.01D);
		assertEquals(f.getEnergy(r2), 500D, 0.01D);
		assertEquals(f.getEnergy(r3), 500D, 0.01D);
		assertEquals(f.getEnergy(r4), 500D, 0.01D);
		
		assertEquals(r1.getDirection(), Direction.UP);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		assertEquals(r3.getDirection(), Direction.DOWN);
		assertEquals(r4.getDirection(), Direction.LEFT);
		
		assertEquals(r1.getPosition(), new Position(1L, 0L));
		assertEquals(r2.getPosition(), new Position(11L, 10L));
		assertEquals(r3.getPosition(), new Position(20L, 21L));
		assertEquals(r4.getPosition(), new Position(29L, 30L));
	}
	
	@Test
	public void move_terminated(){
		Robot r1 = f.createRobot(0, 1000D);
		r1.terminate();
		f.move(r1);
	}
	
	@Test
	public void move_border(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		f.putRobot(b1, 1L, 0L, r1);
		f.move(r1);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		assertEquals(r1.getPosition(), new Position(1L, 0L));
	}
	
	@Test
	public void move_otherRobotPositioned(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(0, 1000D);
		Robot r2 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		f.putRobot(b1, 1L, 1L, r1);
		f.putRobot(b1, 1L, 0L, r2);
		f.move(r1);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		assertEquals(r1.getPosition(), new Position(1L, 1L));
	}
	
	@Test
	public void move_insufficientEnergy(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(0, 1D);
		f.putRobot(b1, 2L, 2L, r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		f.move(r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
	}
	
	@Test
	public void move_surrounded(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 1000D);
		f.putRobot(b1, 2L, 2L, r1);
		
		f.putRobot(b1, 1L, 2L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 2L, f.createRobot(1, 1000D));
		f.putRobot(b1, 2L, 3L, f.createRobot(1, 1000D));
		f.putRobot(b1, 2L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 1L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 1L, 3L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 3L, f.createRobot(1, 1000D));
		
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		f.move(r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
	}
	
	@Test
	public void turn_noBoard(){
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		f.turn(r1);
		Robot r2 = f.createRobot(1, 1000D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		f.turn(r2);
		Robot r3 = f.createRobot(2, 1000D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		f.turn(r3);
		Robot r4 = f.createRobot(3, 1000D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
		f.turn(r4);
		
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
		
		assertEquals(r1.getDirection(), Direction.UP);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		assertEquals(r3.getDirection(), Direction.DOWN);
		assertEquals(r4.getDirection(), Direction.LEFT);
	}
	
	@Test
	public void turn_insufficientEnergy(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(0, 1D);
		assertEquals(r1.getDirection(), Direction.UP);
		f.putRobot(b1, 2L, 2L, r1);
		f.turn(r1);
		assertEquals(r1.getDirection(), Direction.UP);
	}
	
	@Test
	public void turn_terminated(){
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(r1.getDirection(), Direction.UP);
		r1.terminate();
		f.turn(r1);
		assertEquals(r1.getDirection(), Direction.UP);
	}
	
	@Test
	public void turn(){
		Board b1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(f.getEnergy(r1), 1000D, 0.01D);
		assertEquals(r1.getDirection(), Direction.UP);
		f.putRobot(b1, 1L, 1L, r1);
		Robot r2 = f.createRobot(1, 1000D);
		assertEquals(f.getEnergy(r2), 1000D, 0.01D);
		assertEquals(r2.getDirection(), Direction.RIGHT);
		f.putRobot(b1, 10L, 10L, r2);
		Robot r3 = f.createRobot(2, 1000D);
		assertEquals(f.getEnergy(r3), 1000D, 0.01D);
		assertEquals(r3.getDirection(), Direction.DOWN);
		f.putRobot(b1, 20L, 20L, r3);
		Robot r4 = f.createRobot(3, 1000D);
		assertEquals(f.getEnergy(r4), 1000D, 0.01D);
		assertEquals(r4.getDirection(), Direction.LEFT);
		f.putRobot(b1, 30L, 30L, r4);
		
		f.turn(r1);
		f.turn(r2);
		f.turn(r3);
		f.turn(r4);
		
		assertEquals(f.getEnergy(r1), 900D, 0.01D);
		assertEquals(f.getEnergy(r2), 900D, 0.01D);
		assertEquals(f.getEnergy(r3), 900D, 0.01D);
		assertEquals(f.getEnergy(r4), 900D, 0.01D);
		
		assertEquals(r1.getDirection(), Direction.RIGHT);
		assertEquals(r2.getDirection(), Direction.DOWN);
		assertEquals(r3.getDirection(), Direction.LEFT);
		assertEquals(r4.getDirection(), Direction.UP);
		
		assertEquals(r1.getPosition(), new Position(1L, 1L));
		assertEquals(r2.getPosition(), new Position(10L, 10L));
		assertEquals(r3.getPosition(), new Position(20L, 20L));
		assertEquals(r4.getPosition(), new Position(30L, 30L));
	}
	
	@Test
	public void pickUpBattery(){
		Board board1 = f.createBoard(40L, 40L);
		Board board2 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpBattery(r1, null);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		Battery b1 = f.createBattery(100D, 100);
		f.pickUpBattery(r1, b1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(b1.isPickedUp());
		f.pickUpBattery(r1, b1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(b1.isPickedUp());
		
		Battery b2 = f.createBattery(100D, 100);
		b2.terminate();
		f.pickUpBattery(r1, b2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(b2.isPickedUp());
		
		Battery b3 = f.createBattery(100D, 100);
		b3.pickUp();
		f.pickUpBattery(r1, b3);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(b3.isPickedUp());
		
		Robot r2 = f.createRobot(0, 1000D);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		f.pickUpBattery(r2, b1);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		assertTrue(b1.isPickedUp());
		
		f.putRobot(board1, 1L, 1L, r1);
		
		Battery b4 = f.createBattery(100D, 100);
		f.pickUpBattery(r1, b4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(b4.isPickedUp());
		
		f.putBattery(board1, 1L, 1L, b4);
		f.pickUpBattery(r1, b4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertTrue(b4.isPickedUp());
		assertFalse(board1.containsBoardModel_allCheck(b4));
		
		Battery b5 = f.createBattery(100D, 100);
		f.putBattery(board1, 2L, 1L, b5);
		f.pickUpBattery(r1, b5);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(b5.isPickedUp());
		assertTrue(board1.containsBoardModel_allCheck(b5));
		
		Battery b6 = f.createBattery(100D, 100);
		f.putBattery(board2, 1L, 1L, b6);
		f.pickUpBattery(r1, b6);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(b6.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(b6));
		
		Battery b7 = f.createBattery(100D, 100);
		f.putBattery(board2, 2L, 1L, b7);
		f.pickUpBattery(r1, b7);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(b7.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(b7));
		
		Robot r3 = f.createRobot(0, 1000D);
		r3.terminate();
		Battery b8 = f.createBattery(100D, 100);
		f.pickUpBattery(r3, b8);
		assertFalse(b8.isPickedUp());
	}

	@Test
	public void useBattery(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		
		Battery b1 = f.createBattery(100D, 100);
		f.useBattery(r1, b1);
		assertEquals(r1.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		assertFalse(b1.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpBattery(r1, b1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		f.useBattery(r1, b1);
		assertEquals(r1.getAccu().getAmountOfEnergy().getEnergyAmount(), 1100D, 0.01D);
		assertTrue(b1.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		Battery b2 = f.createBattery(100D, 100);
		f.pickUpBattery(r1, b2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		Robot r2 = f.createRobot(0, 1000D);
		f.useBattery(r2, b2);
		assertEquals(r1.getAccu().getAmountOfEnergy().getEnergyAmount(), 1100D, 0.01D);
		assertEquals(r2.getAccu().getAmountOfEnergy().getEnergyAmount(), 1000D, 0.01D);
		assertEquals(b2.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		assertFalse(b2.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		
		f.useBattery(r1, null);
		
		r1.terminate();
		f.useBattery(r1, b2);
		
		Robot r3 = f.createRobot(0, 19900D);
		Battery b3 = f.createBattery(1000D, 100);
		f.pickUpBattery(r3, b3);
		assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		f.useBattery(r3, b3);
		assertEquals(r3.getAccu().getAmountOfEnergy().getEnergyAmount(), 20000D, 0.01D);
		assertEquals(b3.getAccu().getAmountOfEnergy().getEnergyAmount(), 900D, 0.01D);
		assertFalse(b3.isTerminated());
		assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		
		Robot r4 = f.createRobot(0, 19900D);
		Battery b4 = f.createBattery(1000D, 100);
		f.putRobot(board1, 1L, 1L, r4);
		f.putBattery(board1, 1L, 1L, b4);
		f.pickUpBattery(r4, b4);
		assertEquals(r4.getInventory().getNbOfInventoryItems(),1);
		f.useBattery(r4, b4);
		assertEquals(r4.getAccu().getAmountOfEnergy().getEnergyAmount(), 20000D, 0.01D);
		assertEquals(b4.getAccu().getAmountOfEnergy().getEnergyAmount(), 900D, 0.01D);
		assertFalse(b4.isTerminated());
		assertEquals(r4.getInventory().getNbOfInventoryItems(),1);
	}

	@Test
	public void dropBattery(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		Battery b1 = f.createBattery(100D, 100);
		f.dropBattery(r1, b1);
		assertFalse(b1.isPickedUp());
		assertFalse(b1.isTerminated());
		
		f.pickUpBattery(r1, b1);
		assertTrue(b1.isPickedUp());
		assertFalse(b1.isTerminated());
		
		Robot r2 = f.createRobot(0, 1000D);
		f.dropBattery(r2, b1);
		assertTrue(b1.isPickedUp());
		assertFalse(b1.isTerminated());
		
		f.dropBattery(r1, b1);
		assertFalse(b1.isPickedUp());
		assertTrue(b1.isTerminated());
		
		Robot r3 = f.createRobot(0, 1000D);
		f.putRobot(board1, 1L, 1L, r3);
		Battery b2 = f.createBattery(0D, 100);
		f.putBattery(board1, 1L, 1L, b2);
		f.pickUpBattery(r1, b2);
		f.dropBattery(r1, b2);
		assertFalse(b2.isPickedUp());
		assertFalse(b2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(b2));
		assertEquals(b2.getPosition(), r3.getPosition());
		
		Battery b3 = f.createBattery(100D, 100);
		f.putBattery(board1, 1L, 1L, b3);
		f.pickUpBattery(r1, b3);
		f.dropBattery(r1, b3);
		assertFalse(b3.isPickedUp());
		assertFalse(b3.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(b3));
		
		Robot r4 = f.createRobot(0, 1000D);
		r4.terminate();
		f.dropBattery(r4, f.createBattery(100D, 100));
	}

	@Test
	public void pickUpRepairKit(){
		Board board1 = f.createBoard(40L, 40L);
		Board board2 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpRepairKit(r1, null);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		RepairKit k1 = f.createRepairKit(100D, 100);
		f.pickUpRepairKit(r1, k1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(k1.isPickedUp());
		f.pickUpRepairKit(r1, k1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(k1.isPickedUp());
		
		RepairKit k2 = f.createRepairKit(100D, 100);
		k2.terminate();
		f.pickUpRepairKit(r1, k2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(k2.isPickedUp());
		
		RepairKit k3 = f.createRepairKit(100D, 100);
		k3.pickUp();
		f.pickUpRepairKit(r1, k3);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(k3.isPickedUp());
		
		Robot r2 = f.createRobot(0, 1000D);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		f.pickUpRepairKit(r2, k1);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		assertTrue(k1.isPickedUp());
		
		f.putRobot(board1, 1L, 1L, r1);
		
		RepairKit k4 = f.createRepairKit(100D, 100);
		f.pickUpRepairKit(r1, k4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(k4.isPickedUp());
		
		f.putRepairKit(board1, 1L, 1L, k4);
		f.pickUpRepairKit(r1, k4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertTrue(k4.isPickedUp());
		assertFalse(board1.containsBoardModel_allCheck(k4));
		
		RepairKit k5 = f.createRepairKit(100D, 100);
		f.putRepairKit(board1, 2L, 1L, k5);
		f.pickUpRepairKit(r1, k5);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(k5.isPickedUp());
		assertTrue(board1.containsBoardModel_allCheck(k5));
		
		RepairKit k6 = f.createRepairKit(100D, 100);
		f.putRepairKit(board2, 1L, 1L, k6);
		f.pickUpRepairKit(r1, k6);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(k6.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(k6));
		
		RepairKit k7 = f.createRepairKit(100D, 100);
		f.putRepairKit(board2, 2L, 1L, k7);
		f.pickUpRepairKit(r1, k7);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(k7.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(k7));
		
		Robot r3 = f.createRobot(0, 1000D);
		r3.terminate();
		RepairKit k8 = f.createRepairKit(100D, 100);
		f.pickUpRepairKit(r3, k8);
		assertFalse(k8.isPickedUp());
	}

	@Test
	public void useRepairKit(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		r1.getAccu().setEnergyCapacityLimit(new Energy(10000D));
		
		RepairKit k1 = f.createRepairKit(100D, 100);
		f.useRepairKit(r1, k1);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 10000D, 0.01D);
		assertEquals(k1.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		assertFalse(k1.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpRepairKit(r1, k1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		f.useRepairKit(r1, k1);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 10050D, 0.01D);
		assertTrue(k1.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		RepairKit k2 = f.createRepairKit(100D, 100);
		f.pickUpRepairKit(r1, k2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		Robot r2 = f.createRobot(0, 1000D);
		f.useRepairKit(r2, k2);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 10050D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		assertEquals(k2.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		assertFalse(k2.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		
		f.useRepairKit(r1, null);
		
		r1.terminate();
		f.useRepairKit(r1, k2);
		
		Robot r3 = f.createRobot(0, 100D);
		r3.getAccu().setEnergyCapacityLimit(new Energy(19900D));
		RepairKit k3 = f.createRepairKit(1000D, 100);
		f.pickUpRepairKit(r3, k3);
		assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		f.useRepairKit(r3, k3);
		assertEquals(r3.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		assertEquals(k3.getAccu().getAmountOfEnergy().getEnergyAmount(), 800D, 0.01D);
		assertFalse(k3.isTerminated());
		assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		
		Robot r4 = f.createRobot(0, 100D);
		r4.getAccu().setEnergyCapacityLimit(new Energy(19900D));
		RepairKit k4 = f.createRepairKit(1000D, 100);
		f.putRobot(board1, 1L, 1L, r4);
		f.putRepairKit(board1, 1L, 1L, k4);
		f.pickUpRepairKit(r4, k4);
		assertEquals(r4.getInventory().getNbOfInventoryItems(),1);
		f.useRepairKit(r4, k4);
		assertEquals(r4.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		assertEquals(k4.getAccu().getAmountOfEnergy().getEnergyAmount(), 800D, 0.01D);
		assertFalse(k4.isTerminated());
		assertEquals(r4.getInventory().getNbOfInventoryItems(),1);
	}

	@Test
	public void dropRepairKit(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		RepairKit k1 = f.createRepairKit(100D, 100);
		f.dropRepairKit(r1, k1);
		assertFalse(k1.isPickedUp());
		assertFalse(k1.isTerminated());
		
		f.pickUpRepairKit(r1, k1);
		assertTrue(k1.isPickedUp());
		assertFalse(k1.isTerminated());
		
		Robot r2 = f.createRobot(0, 1000D);
		f.dropRepairKit(r2, k1);
		assertTrue(k1.isPickedUp());
		assertFalse(k1.isTerminated());
		
		f.dropRepairKit(r1, k1);
		assertFalse(k1.isPickedUp());
		assertTrue(k1.isTerminated());
		
		Robot r3 = f.createRobot(0, 1000D);
		f.putRobot(board1, 1L, 1L, r3);
		RepairKit k2 = f.createRepairKit(0D, 100);
		f.putRepairKit(board1, 1L, 1L, k2);
		f.pickUpRepairKit(r1, k2);
		f.dropRepairKit(r1, k2);
		assertFalse(k2.isPickedUp());
		assertFalse(k2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(k2));
		assertEquals(k2.getPosition(), r3.getPosition());
		
		RepairKit k3 = f.createRepairKit(100D, 100);
		f.putRepairKit(board1, 1L, 1L, k3);
		f.pickUpRepairKit(r1, k3);
		f.dropRepairKit(r1, k3);
		assertFalse(k3.isPickedUp());
		assertFalse(k3.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(k3));
		
		Robot r4 = f.createRobot(0, 1000D);
		r4.terminate();
		f.dropRepairKit(r4, f.createRepairKit(100D, 100));
	}
	
	@Test
	public void pickUpSurpriseBox(){
		Board board1 = f.createBoard(40L, 40L);
		Board board2 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpRepairKit(r1, null);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.pickUpSurpriseBox(r1, s1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(s1.isPickedUp());
		f.pickUpSurpriseBox(r1, s1);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(s1.isPickedUp());
		
		SurpriseBox s2 = f.createSurpriseBox(100);
		s2.terminate();
		f.pickUpSurpriseBox(r1, s2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(s2.isPickedUp());
		
		SurpriseBox s3 = f.createSurpriseBox(100);
		s3.pickUp();
		f.pickUpSurpriseBox(r1, s3);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertTrue(s3.isPickedUp());
		
		Robot r2 = f.createRobot(0, 1000D);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		f.pickUpSurpriseBox(r2, s1);
		assertEquals(r2.getInventory().getNbOfInventoryItems(),0);
		assertTrue(s1.isPickedUp());
		
		f.putRobot(board1, 1L, 1L, r1);
		
		SurpriseBox s4 = f.createSurpriseBox(100);
		f.pickUpSurpriseBox(r1, s4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		assertFalse(s4.isPickedUp());
		
		f.putSurpriseBox(board1, 1L, 1L, s4);
		f.pickUpSurpriseBox(r1, s4);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertTrue(s4.isPickedUp());
		assertFalse(board1.containsBoardModel_allCheck(s4));
		
		SurpriseBox s5 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 2L, 1L, s5);
		f.pickUpSurpriseBox(r1, s5);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(s5.isPickedUp());
		assertTrue(board1.containsBoardModel_allCheck(s5));
		
		SurpriseBox s6 = f.createSurpriseBox(100);
		f.putSurpriseBox(board2, 1L, 1L, s6);
		f.pickUpSurpriseBox(r1, s6);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(s6.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(s6));
		
		SurpriseBox s7 = f.createSurpriseBox(100);
		f.putSurpriseBox(board2, 2L, 1L, s7);
		f.pickUpSurpriseBox(r1, s7);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		assertFalse(s7.isPickedUp());
		assertTrue(board2.containsBoardModel_allCheck(s7));
		
		Robot r3 = f.createRobot(0, 1000D);
		r3.terminate();
		SurpriseBox s8 = f.createSurpriseBox(100);
		f.pickUpSurpriseBox(r3, s8);
		assertFalse(s8.isPickedUp());
	}

	@Test
	public void useSurpriseBox(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 100D);
		r1.getAccu().setEnergyCapacityLimit(new Energy(10000D));
		
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.useSurpriseBox(r1, s1);
		assertFalse(s1.isTerminated());
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		assertEquals(r1.getInventory().getNbOfInventoryItems(),0);
		
		f.pickUpSurpriseBox(r1, s1);
		assertFalse(s1.isTerminated());
		assertTrue(r1.getInventory().hasAsInventoryItem(s1));
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		
		f.useSurpriseBox(r1, s1);
		assertTrue(s1.isTerminated());
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		assertEquals(r1.getInventory().getNbOfInventoryItems(),1);
		
		SurpriseBox s2 = f.createSurpriseBox(100);
		f.pickUpSurpriseBox(r1, s2);
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		Robot r2 = f.createRobot(0, 1000D);
		f.useSurpriseBox(r2, s2);
		assertFalse(s2.isTerminated());
		assertEquals(r1.getInventory().getNbOfInventoryItems(),2);
		
		f.useSurpriseBox(r1, null);
		
		r1.terminate();
		f.useSurpriseBox(r1, s2);
		
		Robot r3 = f.createRobot(0, 19990D);
		r3.getAccu().setEnergyCapacityLimit(new Energy(19990D));
		SurpriseBox s3 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 10L, 10L, s3);
		f.putRobot(board1, 10L, 10L, r3);
		f.pickUpSurpriseBox(r3, s3);
		assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		f.useSurpriseBox(r3, s3);
		assertTrue(s3.isTerminated());
		assertFalse(r3.getInventory().hasAsInventoryItem(s3));
		
		if(!r3.getPosition().equals(new Position(10L, 10L)) || r3.getAccu().getEnergyCapacityLimit().compareTo(new Energy(19900D))<0){
			assertEquals(r3.getInventory().getNbOfInventoryItems(),0);
		}
		else{
			assertEquals(r3.getInventory().getNbOfInventoryItems(),1);
		}
	}
	
	@Test
	public void dropSurpriseBox(){
		Board board1 = f.createBoard(40L, 40L);
		Robot r1 = f.createRobot(0, 1000D);
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.dropSurpriseBox(r1, s1);
		assertFalse(s1.isPickedUp());
		assertFalse(s1.isTerminated());
		
		f.pickUpSurpriseBox(r1, s1);
		assertTrue(s1.isPickedUp());
		assertFalse(s1.isTerminated());
		
		Robot r2 = f.createRobot(0, 1000D);
		f.dropSurpriseBox(r2, s1);
		assertTrue(s1.isPickedUp());
		assertFalse(s1.isTerminated());
		
		f.dropSurpriseBox(r1, s1);
		assertFalse(s1.isPickedUp());
		assertTrue(s1.isTerminated());
		
		Robot r3 = f.createRobot(0, 1000D);
		f.putRobot(board1, 1L, 1L, r3);
		SurpriseBox s2 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 1L, 1L, s2);
		f.pickUpSurpriseBox(r1, s2);
		f.dropSurpriseBox(r1, s2);
		assertFalse(s2.isPickedUp());
		assertFalse(s2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(s2));
		assertEquals(s2.getPosition(), r3.getPosition());
		
		SurpriseBox s3 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 1L, 1L, s3);
		f.pickUpSurpriseBox(r1, s3);
		f.dropSurpriseBox(r1, s3);
		assertFalse(s3.isPickedUp());
		assertFalse(s3.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(s3));
		assertEquals(s3.getPosition(), r3.getPosition());
		
		Robot r4 = f.createRobot(0, 1000D);
		r4.terminate();
		f.dropSurpriseBox(r4, f.createSurpriseBox(100));
	}

	@Test
	public void transferItems_noBoard(){
		Robot r1 = f.createRobot(1, 100D);
		Robot r2 = f.createRobot(1, 100D);
		Battery b1 = f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.pickUpBattery(r1, b1);
		f.pickUpRepairKit(r2, k1);
		f.pickUpSurpriseBox(r2, s1);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r1.getInventory().hasAsInventoryItem(b1));
		assertFalse(r1.getInventory().hasAsInventoryItem(k1));
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));

		f.transferItems(r2, r1);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r1.getInventory().hasAsInventoryItem(b1));
		assertTrue(r1.getInventory().hasAsInventoryItem(k1));
		assertTrue(r1.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r2.getInventory().hasAsInventoryItem(b1));
		assertFalse(r2.getInventory().hasAsInventoryItem(k1));
		assertFalse(r2.getInventory().hasAsInventoryItem(s1));
		
		f.transferItems(r1, r2);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r1.getInventory().hasAsInventoryItem(b1));
		assertFalse(r1.getInventory().hasAsInventoryItem(k1));
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		
		Robot r3 = f.createRobot(1, 1000D);
		r3.terminate();
		
		f.transferItems(r2, r3);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		r2.terminate();
		f.transferItems(r2, r1);
		
		Robot r4 = f.createRobot(1, 100D);
		Battery b = f.createBattery(100D, 100);
		RepairKit k = f.createRepairKit(100D, 100);
		SurpriseBox s = f.createSurpriseBox(100);
		f.pickUpBattery(r4, b);
		f.pickUpRepairKit(r4, k);
		f.pickUpSurpriseBox(r4, s);
		
		assertTrue(b.isPickedUp());
		assertTrue(k.isPickedUp());
		assertTrue(s.isPickedUp());
		
		assertTrue(r4.getInventory().hasAsInventoryItem(b));
		assertTrue(r4.getInventory().hasAsInventoryItem(k));
		assertTrue(r4.getInventory().hasAsInventoryItem(s));
		
		f.transferItems(r4, r4);
		
		assertTrue(b.isPickedUp());
		assertTrue(k.isPickedUp());
		assertTrue(s.isPickedUp());
		
		assertTrue(r4.getInventory().hasAsInventoryItem(b));
		assertTrue(r4.getInventory().hasAsInventoryItem(k));
		assertTrue(r4.getInventory().hasAsInventoryItem(s));
	}
	
	@Test
	public void transferItems_withBoard(){
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		
		Robot r1 = f.createRobot(1, 1000D);
		Robot r2 = f.createRobot(1, 1000D);
		Battery b1 = f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.pickUpBattery(r1, b1);
		f.pickUpRepairKit(r2, k1);
		f.pickUpSurpriseBox(r2, s1);
		
		f.putRobot(board1, 1L, 1L, r1);
		f.putRobot(board1, 2L, 1L, r2);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r1.getInventory().hasAsInventoryItem(b1));
		assertFalse(r1.getInventory().hasAsInventoryItem(k1));
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));

		f.transferItems(r2, r1);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r1.getInventory().hasAsInventoryItem(b1));
		assertTrue(r1.getInventory().hasAsInventoryItem(k1));
		assertTrue(r1.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r2.getInventory().hasAsInventoryItem(b1));
		assertFalse(r2.getInventory().hasAsInventoryItem(k1));
		assertFalse(r2.getInventory().hasAsInventoryItem(s1));
		
		f.transferItems(r1, r2);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r1.getInventory().hasAsInventoryItem(b1));
		assertFalse(r1.getInventory().hasAsInventoryItem(k1));
		assertFalse(r1.getInventory().hasAsInventoryItem(s1));
		
		Robot r3 = f.createRobot(1, 1000D);
		f.putRobot(board1, 10L, 1L, r3);
		f.transferItems(r2, r3);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r3.getInventory().hasAsInventoryItem(b1));
		assertFalse(r3.getInventory().hasAsInventoryItem(k1));
		assertFalse(r3.getInventory().hasAsInventoryItem(s1));
		
		Robot r4 = f.createRobot(1, 1000D);
		f.putRobot(board2, 1L, 1L, r4);
		f.transferItems(r2, r4);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r4.getInventory().hasAsInventoryItem(b1));
		assertFalse(r4.getInventory().hasAsInventoryItem(k1));
		assertFalse(r4.getInventory().hasAsInventoryItem(s1));
		
		Robot r5 = f.createRobot(1, 1000D);
		f.putRobot(board2, 10L, 1L, r5);
		f.transferItems(r2, r5);
		
		assertTrue(b1.isPickedUp());
		assertTrue(k1.isPickedUp());
		assertTrue(s1.isPickedUp());
		
		assertTrue(r2.getInventory().hasAsInventoryItem(b1));
		assertTrue(r2.getInventory().hasAsInventoryItem(k1));
		assertTrue(r2.getInventory().hasAsInventoryItem(s1));
		
		assertFalse(r5.getInventory().hasAsInventoryItem(b1));
		assertFalse(r5.getInventory().hasAsInventoryItem(k1));
		assertFalse(r5.getInventory().hasAsInventoryItem(s1));
	}

	@Test
	public void isMinimalCostToReach17Plus(){
		assertEquals(f.isMinimalCostToReach17Plus(), 1);
	}

	@Test
	public void getMinimalCostToReach(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 1000D);
		assertEquals(f.getMinimalCostToReach(r1, 1L, 1L), -1D, 0.01D);
		r1.terminate();
		assertEquals(f.getMinimalCostToReach(r1, 1L, 1L), -1D, 0.01D);
		Robot r2 = f.createRobot(1, 1000D);
		f.putRobot(board1, 1L, 1L, r2);
		assertEquals(f.getMinimalCostToReach(r2, -1L, 1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 1L, -1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, -1L, -1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 11L, 1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 1L, 11L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 11L, 11L), -1D, 0.01D);
		
		assertEquals(f.getMinimalCostToReach(r2, 10L, 10L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 1L, 10L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 10L, 1L), -1D, 0.01D);
		
		assertEquals(f.getMinimalCostToReach(r2, 2L, 1L), 500D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 3L, 1L), 1000D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 1L, 2L), 600D, 0.01D);
		
		Battery b1 = f.createBattery(10D, 1000);
		f.putBattery(board1, 1L, 1L, b1);
		f.pickUpBattery(r2, b1);
		
		assertEquals(f.getMinimalCostToReach(r2, 2L, 1L), 550D, 0.01D);
		
		r2.terminate();
		
		assertEquals(f.getMinimalCostToReach(r2, 2L, 1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 3L, 1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 1L, 2L), -1D, 0.01D);
	}
	
	@Test
	public void getMinimalCostToReach_wallObstacle(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r2 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r2);
		f.putWall(board1, 2L, 1L, f.createWall());
		
		assertEquals(f.getMinimalCostToReach(r2, 2L, 1L), -1D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 3L, 1L), 2300D, 0.01D);
	}
	
	@Test
	public void getMinimalCostToReach_battery(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r2 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r2);
		f.putBattery(board1, 2L, 1L, f.createBattery(1000D, 100));
		
		assertEquals(f.getMinimalCostToReach(r2, 2L, 1L), 500D, 0.01D);
		assertEquals(f.getMinimalCostToReach(r2, 3L, 1L), 1000D, 0.01D);
	}

	@Test
	public void isMoveNextTo18Plus(){
		assertEquals(f.isMoveNextTo18Plus(), 1);
	}

	@Test
	public void moveNextTo(){
		Board b1 = f.createBoard(10L, 10L);
		Board b2 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 20000D);
		f.putRobot(b1, 2L, 2L, r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		
		f.moveNextTo(r1,r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		
		f.moveNextTo(r1,null);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		
		Robot r2 = f.createRobot(1, 20000D);
		f.moveNextTo(r1,r2);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		assertEquals(f.getEnergy(r2),20000D,0.01D);
		
		Robot r3 = f.createRobot(1, 20000D);
		r3.terminate();
		f.moveNextTo(r1,r3);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		
		f.moveNextTo(r3,r1);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		
		Robot r4 = f.createRobot(1, 20000D);
		f.putRobot(b2, 2L, 2L, r4);
		f.moveNextTo(r1,r4);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(r4.getPosition(), new Position(2L, 2L));
		assertEquals(f.getEnergy(r4),20000D,0.01D);
		assertEquals(f.getEnergy(r4),20000D,0.01D);
		
		Robot r5 = f.createRobot(1, 20000D);
		f.putRobot(b2, 2L, 1L, r5);
		f.moveNextTo(r1,r5);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(r5.getPosition(), new Position(2L, 1L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		assertEquals(f.getEnergy(r5),20000D,0.01D);
		
		Robot r6 = f.createRobot(1, 20000D);
		f.putRobot(b1, 2L, 1L, r6);
		f.moveNextTo(r1,r6);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(r6.getPosition(), new Position(2L, 1L));
		assertEquals(f.getEnergy(r1),20000D,0.01D);
		assertEquals(f.getEnergy(r6),20000D,0.01D);
	}
	
	@Test
	public void moveNextTo_surrounded(){
		Board b1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 1000D);
		f.putRobot(b1, 2L, 2L, r1);
		
		f.putRobot(b1, 1L, 2L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 2L, f.createRobot(1, 1000D));
		f.putRobot(b1, 2L, 3L, f.createRobot(1, 1000D));
		f.putRobot(b1, 2L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 1L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 1L, f.createRobot(1, 1000D));
		f.putRobot(b1, 1L, 3L, f.createRobot(1, 1000D));
		f.putRobot(b1, 3L, 3L, f.createRobot(1, 1000D));
		
		Robot r2 = f.createRobot(1, 1000D);
		f.putRobot(b1, 5L, 5L, r2);
		
		f.putRobot(b1, 5L, 4L, f.createRobot(1, 1000D));
		f.putRobot(b1, 5L, 6L, f.createRobot(1, 1000D));
		f.putRobot(b1, 4L, 5L, f.createRobot(1, 1000D));
		f.putRobot(b1, 6L, 5L, f.createRobot(1, 1000D));
		f.putRobot(b1, 4L, 4L, f.createRobot(1, 1000D));
		f.putRobot(b1, 7L, 4L, f.createRobot(1, 1000D));
		f.putRobot(b1, 4L, 7L, f.createRobot(1, 1000D));
		f.putRobot(b1, 7L, 7L, f.createRobot(1, 1000D));
		
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(r2.getPosition(), new Position(5L, 5L));
		f.moveNextTo(r1,r2);
		assertEquals(r1.getPosition(), new Position(2L, 2L));
		assertEquals(r2.getPosition(), new Position(5L, 5L));
		
		assertEquals(f.getEnergy(r1),1000D,0.01D);
		assertEquals(f.getEnergy(r2),1000D,0.01D);
	}

	@Test
	public void shoot(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		Battery b1 = f.createBattery(100D, 100);
		f.putBattery(board1, 1L, 2L, b1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 8000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		
		Battery b2 = f.createBattery(100D, 100);
		f.putBattery(board1, 1L, 1L, b2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 7000D, 0.01D);
		assertEquals(b2.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
	}
	
	@Test
	public void shoot_Robot(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 20000D);
		Robot r2 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		f.putRobot(board1, 2L, 1L, r2);
		assertEquals(f.getEnergy(r1), 20000D, 0.01D);
		assertEquals(f.getEnergy(r2), 10000D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 19000D, 0.01D);
		assertEquals(f.getEnergy(r2), 10000D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 16000D, 0.01D);
		assertFalse(r1.isTerminated());
		assertFalse(r2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(r2));
		
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 18000D, 0.01D);
		assertEquals(f.getEnergy(r2), 10000D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 12000D, 0.01D);
		assertFalse(r1.isTerminated());
		assertFalse(r2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(r2));
		
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 17000D, 0.01D);
		assertEquals(f.getEnergy(r2), 8000D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 8000D, 0.01D);
		assertFalse(r1.isTerminated());
		assertFalse(r2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(r2));
		
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 16000D, 0.01D);
		assertEquals(f.getEnergy(r2), 4000D, 0.01D);
		assertEquals(r2.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 4000D, 0.01D);
		assertFalse(r1.isTerminated());
		assertFalse(r2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(r2));
		
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 15000D, 0.01D);
		assertFalse(r1.isTerminated());
		assertTrue(r2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertFalse(board1.containsBoardModel_allCheck(r2));
	}
	
	@Test
	public void shoot_wallI(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Wall w1 = f.createWall();
		f.putWall(board1, 2L, 1L, w1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(w1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(w1));
	}
	
	@Test
	public void shoot_wallII(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Wall w1 = f.createWall();
		f.putWall(board1, 4L, 1L, w1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(w1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(w1));
	}
	
	@Test
	public void shoot_batteryI(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Battery b1 = f.createBattery(100D, 100);
		f.putBattery(board1, 2L, 1L, b1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 600D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(b1));
	}
	
	@Test
	public void shoot_batteryII(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Battery b1 = f.createBattery(100D, 100);
		f.putBattery(board1, 4L, 1L, b1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 600D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(b1));
	}
	
	@Test
	public void shoot_batteryIII(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Battery b1 = f.createBattery(4800D, 100);
		f.putBattery(board1, 4L, 1L, b1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 5000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(b1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(b1));
	}
	
	@Test
	public void shoot_battery_inRow(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Battery b1 = f.createBattery(100D, 100);
		Battery b2 = f.createBattery(100D, 100);
		f.putBattery(board1, 2L, 1L, b1);
		f.putBattery(board1, 3L, 1L, b2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount(), 600D, 0.01D);
		assertEquals(b2.getAccu().getAmountOfEnergy().getEnergyAmount(), 100D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(b1.isTerminated());
		assertFalse(b2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(b1));
		assertTrue(board1.containsBoardModel_allCheck(b2));
	}
	
	@Test
	public void shoot_battery_multipleTargets(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		Battery b1 = f.createBattery(100D, 100);
		Battery b2 = f.createBattery(100D, 100);
		f.putBattery(board1, 2L, 1L, b1);
		f.putBattery(board1, 2L, 1L, b2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(b1.getAccu().getAmountOfEnergy().getEnergyAmount()+b2.getAccu().getAmountOfEnergy().getEnergyAmount(), 700D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(b1.isTerminated());
		assertFalse(b2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(b1));
		assertTrue(board1.containsBoardModel_allCheck(b2));
	}
	
	@Test
	public void shoot_repairKitI(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		RepairKit k1 = f.createRepairKit(100D, 100);
		f.putRepairKit(board1, 2L, 1L, k1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(k1.getAccu().getAmountOfEnergy().getEnergyAmount(), 600D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(k1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(k1));
	}
	
	@Test
	public void shoot_repairKitII(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		RepairKit k1 = f.createRepairKit(100D, 100);
		f.putRepairKit(board1, 2L, 1L, k1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(k1.getAccu().getAmountOfEnergy().getEnergyAmount(), 600D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(k1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(k1));
	}
	
	@Test
	public void shoot_repairKitIII(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		RepairKit k1 = f.createRepairKit(4800D, 100);
		f.putRepairKit(board1, 2L, 1L, k1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(k1.getAccu().getAmountOfEnergy().getEnergyAmount(), 5300D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(k1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(k1));
	}
	
	@Test
	public void shoot_repairKit_multipleTargets(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		
		RepairKit k1 = f.createRepairKit(100D, 100);
		RepairKit k2 = f.createRepairKit(100D, 100);
		f.putRepairKit(board1, 2L, 1L, k1);
		f.putRepairKit(board1, 2L, 1L, k2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		assertEquals(k1.getAccu().getAmountOfEnergy().getEnergyAmount()+k2.getAccu().getAmountOfEnergy().getEnergyAmount(), 700D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertFalse(k1.isTerminated());
		assertFalse(k2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertTrue(board1.containsBoardModel_allCheck(k1));
		assertTrue(board1.containsBoardModel_allCheck(k2));
	}
	
	@Test
	public void shoot_surpriseBoxKitI(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		
		SurpriseBox s1 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 2L, 1L, s1);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertTrue(s1.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertFalse(board1.containsBoardModel_allCheck(s1));
		
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 16000D, 0.01D);
	}
	
	@Test
	public void shoot_surpriseBox_multipleTargets(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		
		SurpriseBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 2L, 1L, s1);
		f.putSurpriseBox(board1, 2L, 1L, s2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertTrue(s1.isTerminated());
		assertTrue(s2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertFalse(board1.containsBoardModel_allCheck(s1));
		assertFalse(board1.containsBoardModel_allCheck(s2));
		
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 12000D, 0.01D);
	}
	
	@Test
	public void shoot_surpriseBox_inRow(){
		Board board1 = f.createBoard(10L, 10L);
		Robot r1 = f.createRobot(1, 10000D);
		f.putRobot(board1, 1L, 1L, r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 20000D, 0.01D);
		
		SurpriseBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		f.putSurpriseBox(board1, 2L, 1L, s1);
		f.putSurpriseBox(board1, 3L, 1L, s2);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 9000D, 0.01D);
		
		assertFalse(r1.isTerminated());
		assertTrue(s1.isTerminated());
		assertTrue(s2.isTerminated());
		assertTrue(board1.containsBoardModel_allCheck(r1));
		assertFalse(board1.containsBoardModel_allCheck(s1));
		assertFalse(board1.containsBoardModel_allCheck(s2));
		
		assertEquals(r1.getAccu().getEnergyCapacityLimit().getEnergyAmount(), 16000D, 0.01D);
	}
	
	@Test
	public void shoot_nullBoard(){
		Robot r1 = f.createRobot(0, 10000D);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
		f.shoot(r1);
		assertEquals(f.getEnergy(r1), 10000D, 0.01D);
	}
	
	@Test
	public void shoot_terminated(){
		Robot r1 = f.createRobot(0, 10000D);
		r1.terminate();
		f.shoot(r1);
	}
	
	@Test
	public void createWall(){
		@SuppressWarnings("unused")
		Wall w1 = f.createWall();
	}
	
	@Test
	public void putWall(){
		Wall w1 = f.createWall();
		assertTrue(w1.getBoard() == null);
		Board board1 = f.createBoard(10L, 10L);
		Board board2 = f.createBoard(10L, 10L);
		Board board3 = f.createBoard(10L, 10L);
		
		f.putWall(board1, 11L, 10L, w1);
		assertTrue(w1.getBoard() == null);
		assertTrue(w1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w1));
		f.putWall(board1, 10L, 11L, w1);
		assertTrue(w1.getBoard() == null);
		assertTrue(w1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w1));
		f.putWall(board1, 11L, 11L, w1);
		assertTrue(w1.getBoard() == null);
		assertTrue(w1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w1));
		f.putWall(board1, 1L, 1L, w1);
		assertEquals(w1.getBoard(), board1);
		assertEquals(w1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(w1));
		f.putWall(board1, 1L, 2L, w1);
		assertEquals(w1.getBoard(), board1);
		assertEquals(w1.getPosition(), new Position(1L, 1L));
		assertTrue(board1.containsBoardModel_positionCheck(w1));
		assertFalse(board2.containsBoardModel_allCheck(w1));
		
		Wall w3 = f.createWall();
		w3.terminate();
		f.putWall(board1, 4L, 4L, w3);
		assertTrue(w3.getPosition() == null);
		assertTrue(w3.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w3));
		
		RepairKit k4 = f.createRepairKit(1000D, 100);
		f.putRepairKit(board1, 2L, 2L, k4);
		Wall w4 = f.createWall();
		f.putWall(board1, 2L, 2L, w4);
		assertTrue(w4.getBoard() == null);
		assertTrue(w4.getPosition() == null);
		assertFalse(board1.containsBoardModel_positionCheck(w4));
		assertEquals(k4.getBoard(), board1);
		assertEquals(k4.getPosition(), new Position(2L, 2L));
		assertTrue(board1.containsBoardModel_positionCheck(k4));
		
		board1.terminate();
		assertTrue(w1.getBoard() == null);
		assertTrue(w1.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w1));
		assertTrue(k4.getBoard() == null);
		assertTrue(k4.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(k4));
		assertTrue(board1.isTerminated());
		assertTrue(w1.isTerminated());
		assertTrue(k4.isTerminated());
		
		Wall w5 = f.createWall();
		f.putWall(board1, 1L, 1L, w5);
		assertTrue(w5.getBoard() == null);
		assertTrue(w5.getPosition() == null);
		assertFalse(board1.containsBoardModel_allCheck(w5));
		
		Wall w_1 = f.createWall();
		f.putWall(board3, 1L, 1L, w_1);
		Wall w6 = f.createWall();
		f.putWall(board3, 1L, 1L, w6);
		assertTrue(w6.getBoard() == null);
		assertTrue(w6.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(w6));
		
		Robot r7 = f.createRobot(1, 100D);
		f.putRobot(board3, 2L, 2L, r7);
		Wall w8 = f.createWall();
		f.putWall(board3, 1L, 1L, w8);
		assertTrue(w8.getBoard() == null);
		assertTrue(w8.getPosition() == null);
		assertFalse(board3.containsBoardModel_allCheck(w8));
	}
	
	@Test
	public void getWallX(){
		Board board1 = f.createBoard(10L, 10L);
		Wall w1 = f.createWall();
		f.putWall(board1, 10L, 9L, w1);
		assertEquals(f.getWallX(w1), 10L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getWallX_nullBoard(){
		Wall w1 = f.createWall();
		f.getWallX(w1);
	}
	
	@Test
	public void getWallY(){
		Board board1 = f.createBoard(10L, 10L);
		Wall w1 = f.createWall();
		f.putWall(board1, 10L, 9L, w1);
		assertEquals(f.getWallY(w1), 9L);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getWallY_nullBoard(){
		Wall w1 = f.createWall();
		f.getWallY(w1);
	}
	
	@Test
	public void getRobots(){
		Board board1 = f.createBoard(10L,10L);
		Robot r1 = f.createRobot(0, 900D);
		BoardModel r2 = f.createRobot(0, 900D);
		Battery b1 = f.createBattery(100D, 100);
		BoardModel b2 = f.createBattery(100D, 100);
		InventoryModel b3 = f.createBattery(100D, 100);
		InventoryEnergyModel b4 =f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		BoardModel k2 = f.createRepairKit(100D, 100);
		InventoryModel k3 = f.createRepairKit(100D, 100);
		InventoryEnergyModel k4 = f.createRepairKit(100D, 100);
		ItemBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		BoardModel s3 = f.createSurpriseBox(100);
		Wall w1 = f.createWall();
		BoardModel w2 = f.createWall();
		
		f.putRobot(board1, 1L, 2L, r1);
		f.putRobot(board1, 1L, 3L, (Robot) r2);
		f.putBattery(board1, 1L, 3L, b1);
		f.putBattery(board1, 1L, 3L, (Battery) b2);
		f.putBattery(board1, 1L, 4L, (Battery) b3);
		f.putBattery(board1, 1L, 5L, (Battery) b4);
		f.putRepairKit(board1, 1L, 3L, k1);
		f.putRepairKit(board1, 1L, 3L, (RepairKit) k2);
		f.putRepairKit(board1, 1L, 4L, (RepairKit) k3);
		f.putRepairKit(board1, 1L, 6L, (RepairKit) k4);
		f.putSurpriseBox(board1, 1L, 3L, (SurpriseBox)s1);
		f.putSurpriseBox(board1, 1L, 2L, s2);
		f.putSurpriseBox(board1, 1L, 7L, (SurpriseBox) s3);
		f.putWall(board1, 1L, 8L, w1);
		f.putWall(board1, 8L, 3L, (Wall) w2);
		
		assertTrue(f.getRobots(board1).contains(r1));
		assertTrue(f.getRobots(board1).contains(r2));
		assertFalse(f.getRobots(board1).contains(b1));
		assertFalse(f.getRobots(board1).contains(b2));
		assertFalse(f.getRobots(board1).contains(b3));
		assertFalse(f.getRobots(board1).contains(b4));
		assertFalse(f.getRobots(board1).contains(k1));
		assertFalse(f.getRobots(board1).contains(k2));
		assertFalse(f.getRobots(board1).contains(k3));
		assertFalse(f.getRobots(board1).contains(k4));
		assertFalse(f.getRobots(board1).contains(s1));
		assertFalse(f.getRobots(board1).contains(s2));
		assertFalse(f.getRobots(board1).contains(s3));
		assertFalse(f.getRobots(board1).contains(w1));
		assertFalse(f.getRobots(board1).contains(w2));
		assertEquals(f.getRobots(board1).size(), 2);
	}

	@Test
	public void getWalls(){
		Board board1 = f.createBoard(10L,10L);
		Robot r1 = f.createRobot(0, 900D);
		BoardModel r2 = f.createRobot(0, 900D);
		Battery b1 = f.createBattery(100D, 100);
		BoardModel b2 = f.createBattery(100D, 100);
		InventoryModel b3 = f.createBattery(100D, 100);
		InventoryEnergyModel b4 =f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		BoardModel k2 = f.createRepairKit(100D, 100);
		InventoryModel k3 = f.createRepairKit(100D, 100);
		InventoryEnergyModel k4 = f.createRepairKit(100D, 100);
		ItemBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		BoardModel s3 = f.createSurpriseBox(100);
		Wall w1 = f.createWall();
		BoardModel w2 = f.createWall();
		
		f.putRobot(board1, 1L, 2L, r1);
		f.putRobot(board1, 1L, 3L, (Robot) r2);
		f.putBattery(board1, 1L, 3L, b1);
		f.putBattery(board1, 1L, 3L, (Battery) b2);
		f.putBattery(board1, 1L, 4L, (Battery) b3);
		f.putBattery(board1, 1L, 5L, (Battery) b4);
		f.putRepairKit(board1, 1L, 3L, k1);
		f.putRepairKit(board1, 1L, 3L, (RepairKit) k2);
		f.putRepairKit(board1, 1L, 4L, (RepairKit) k3);
		f.putRepairKit(board1, 1L, 6L, (RepairKit) k4);
		f.putSurpriseBox(board1, 1L, 3L, (SurpriseBox)s1);
		f.putSurpriseBox(board1, 1L, 2L, s2);
		f.putSurpriseBox(board1, 1L, 7L, (SurpriseBox) s3);
		f.putWall(board1, 1L, 8L, w1);
		f.putWall(board1, 8L, 3L, (Wall) w2);
		
		assertFalse(f.getWalls(board1).contains(r1));
		assertFalse(f.getWalls(board1).contains(r2));
		assertFalse(f.getWalls(board1).contains(b1));
		assertFalse(f.getWalls(board1).contains(b2));
		assertFalse(f.getWalls(board1).contains(b3));
		assertFalse(f.getWalls(board1).contains(b4));
		assertFalse(f.getWalls(board1).contains(k1));
		assertFalse(f.getWalls(board1).contains(k2));
		assertFalse(f.getWalls(board1).contains(k3));
		assertFalse(f.getWalls(board1).contains(k4));
		assertFalse(f.getWalls(board1).contains(s1));
		assertFalse(f.getWalls(board1).contains(s2));
		assertFalse(f.getWalls(board1).contains(s3));
		assertTrue(f.getWalls(board1).contains(w1));
		assertTrue(f.getWalls(board1).contains(w2));
		assertEquals(f.getWalls(board1).size(), 2);
	}
	
	@Test
	public void getRepairKits(){
		Board board1 = f.createBoard(10L,10L);
		Robot r1 = f.createRobot(0, 900D);
		BoardModel r2 = f.createRobot(0, 900D);
		Battery b1 = f.createBattery(100D, 100);
		BoardModel b2 = f.createBattery(100D, 100);
		InventoryModel b3 = f.createBattery(100D, 100);
		InventoryEnergyModel b4 =f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		BoardModel k2 = f.createRepairKit(100D, 100);
		InventoryModel k3 = f.createRepairKit(100D, 100);
		InventoryEnergyModel k4 = f.createRepairKit(100D, 100);
		ItemBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		BoardModel s3 = f.createSurpriseBox(100);
		Wall w1 = f.createWall();
		BoardModel w2 = f.createWall();
		
		f.putRobot(board1, 1L, 2L, r1);
		f.putRobot(board1, 1L, 3L, (Robot) r2);
		f.putBattery(board1, 1L, 3L, b1);
		f.putBattery(board1, 1L, 3L, (Battery) b2);
		f.putBattery(board1, 1L, 4L, (Battery) b3);
		f.putBattery(board1, 1L, 5L, (Battery) b4);
		f.putRepairKit(board1, 1L, 3L, k1);
		f.putRepairKit(board1, 1L, 3L, (RepairKit) k2);
		f.putRepairKit(board1, 1L, 4L, (RepairKit) k3);
		f.putRepairKit(board1, 1L, 6L, (RepairKit) k4);
		f.putSurpriseBox(board1, 1L, 3L, (SurpriseBox)s1);
		f.putSurpriseBox(board1, 1L, 2L, s2);
		f.putSurpriseBox(board1, 1L, 7L, (SurpriseBox) s3);
		f.putWall(board1, 1L, 8L, w1);
		f.putWall(board1, 8L, 3L, (Wall) w2);
		
		assertFalse(f.getRepairKits(board1).contains(r1));
		assertFalse(f.getRepairKits(board1).contains(r2));
		assertFalse(f.getRepairKits(board1).contains(b1));
		assertFalse(f.getRepairKits(board1).contains(b2));
		assertFalse(f.getRepairKits(board1).contains(b3));
		assertFalse(f.getRepairKits(board1).contains(b4));
		assertTrue(f.getRepairKits(board1).contains(k1));
		assertTrue(f.getRepairKits(board1).contains(k2));
		assertTrue(f.getRepairKits(board1).contains(k3));
		assertTrue(f.getRepairKits(board1).contains(k4));
		assertFalse(f.getRepairKits(board1).contains(s1));
		assertFalse(f.getRepairKits(board1).contains(s2));
		assertFalse(f.getRepairKits(board1).contains(s3));
		assertFalse(f.getRepairKits(board1).contains(w1));
		assertFalse(f.getRepairKits(board1).contains(w2));
		assertEquals(f.getRepairKits(board1).size(), 4);
	}
	
	@Test
	public void getSurpriseBoxes(){
		Board board1 = f.createBoard(10L,10L);
		Robot r1 = f.createRobot(0, 900D);
		BoardModel r2 = f.createRobot(0, 900D);
		Battery b1 = f.createBattery(100D, 100);
		BoardModel b2 = f.createBattery(100D, 100);
		InventoryModel b3 = f.createBattery(100D, 100);
		InventoryEnergyModel b4 =f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		BoardModel k2 = f.createRepairKit(100D, 100);
		InventoryModel k3 = f.createRepairKit(100D, 100);
		InventoryEnergyModel k4 = f.createRepairKit(100D, 100);
		ItemBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		BoardModel s3 = f.createSurpriseBox(100);
		Wall w1 = f.createWall();
		BoardModel w2 = f.createWall();
		
		f.putRobot(board1, 1L, 2L, r1);
		f.putRobot(board1, 1L, 3L, (Robot) r2);
		f.putBattery(board1, 1L, 3L, b1);
		f.putBattery(board1, 1L, 3L, (Battery) b2);
		f.putBattery(board1, 1L, 4L, (Battery) b3);
		f.putBattery(board1, 1L, 5L, (Battery) b4);
		f.putRepairKit(board1, 1L, 3L, k1);
		f.putRepairKit(board1, 1L, 3L, (RepairKit) k2);
		f.putRepairKit(board1, 1L, 4L, (RepairKit) k3);
		f.putRepairKit(board1, 1L, 6L, (RepairKit) k4);
		f.putSurpriseBox(board1, 1L, 3L, (SurpriseBox)s1);
		f.putSurpriseBox(board1, 1L, 2L, s2);
		f.putSurpriseBox(board1, 1L, 7L, (SurpriseBox) s3);
		f.putWall(board1, 1L, 8L, w1);
		f.putWall(board1, 8L, 3L, (Wall) w2);
		
		assertFalse(f.getSurpriseBoxes(board1).contains(r1));
		assertFalse(f.getSurpriseBoxes(board1).contains(r2));
		assertFalse(f.getSurpriseBoxes(board1).contains(b1));
		assertFalse(f.getSurpriseBoxes(board1).contains(b2));
		assertFalse(f.getSurpriseBoxes(board1).contains(b3));
		assertFalse(f.getSurpriseBoxes(board1).contains(b4));
		assertFalse(f.getSurpriseBoxes(board1).contains(k1));
		assertFalse(f.getSurpriseBoxes(board1).contains(k2));
		assertFalse(f.getSurpriseBoxes(board1).contains(k3));
		assertFalse(f.getSurpriseBoxes(board1).contains(k4));
		assertTrue(f.getSurpriseBoxes(board1).contains(s1));
		assertTrue(f.getSurpriseBoxes(board1).contains(s2));
		assertTrue(f.getSurpriseBoxes(board1).contains(s3));
		assertFalse(f.getSurpriseBoxes(board1).contains(w1));
		assertFalse(f.getSurpriseBoxes(board1).contains(w2));
		assertEquals(f.getSurpriseBoxes(board1).size(), 3);
	}
	
	@Test
	public void getBatteries(){
		Board board1 = f.createBoard(10L,10L);
		Robot r1 = f.createRobot(0, 900D);
		BoardModel r2 = f.createRobot(0, 900D);
		Battery b1 = f.createBattery(100D, 100);
		BoardModel b2 = f.createBattery(100D, 100);
		InventoryModel b3 = f.createBattery(100D, 100);
		InventoryEnergyModel b4 =f.createBattery(100D, 100);
		RepairKit k1 = f.createRepairKit(100D, 100);
		BoardModel k2 = f.createRepairKit(100D, 100);
		InventoryModel k3 = f.createRepairKit(100D, 100);
		InventoryEnergyModel k4 = f.createRepairKit(100D, 100);
		ItemBox s1 = f.createSurpriseBox(100);
		SurpriseBox s2 = f.createSurpriseBox(100);
		BoardModel s3 = f.createSurpriseBox(100);
		Wall w1 = f.createWall();
		BoardModel w2 = f.createWall();
		
		f.putRobot(board1, 1L, 2L, r1);
		f.putRobot(board1, 1L, 3L, (Robot) r2);
		f.putBattery(board1, 1L, 3L, b1);
		f.putBattery(board1, 1L, 3L, (Battery) b2);
		f.putBattery(board1, 1L, 4L, (Battery) b3);
		f.putBattery(board1, 1L, 5L, (Battery) b4);
		f.putRepairKit(board1, 1L, 3L, k1);
		f.putRepairKit(board1, 1L, 3L, (RepairKit) k2);
		f.putRepairKit(board1, 1L, 4L, (RepairKit) k3);
		f.putRepairKit(board1, 1L, 6L, (RepairKit) k4);
		f.putSurpriseBox(board1, 1L, 3L, (SurpriseBox)s1);
		f.putSurpriseBox(board1, 1L, 2L, s2);
		f.putSurpriseBox(board1, 1L, 7L, (SurpriseBox) s3);
		f.putWall(board1, 1L, 8L, w1);
		f.putWall(board1, 8L, 3L, (Wall) w2);
		
		assertFalse(f.getBatteries(board1).contains(r1));
		assertFalse(f.getBatteries(board1).contains(r2));
		assertTrue(f.getBatteries(board1).contains(b1));
		assertTrue(f.getBatteries(board1).contains(b2));
		assertTrue(f.getBatteries(board1).contains(b3));
		assertTrue(f.getBatteries(board1).contains(b4));
		assertFalse(f.getBatteries(board1).contains(k1));
		assertFalse(f.getBatteries(board1).contains(k2));
		assertFalse(f.getBatteries(board1).contains(k3));
		assertFalse(f.getBatteries(board1).contains(k4));
		assertFalse(f.getBatteries(board1).contains(s1));
		assertFalse(f.getBatteries(board1).contains(s2));
		assertFalse(f.getBatteries(board1).contains(s3));
		assertFalse(f.getBatteries(board1).contains(w1));
		assertFalse(f.getBatteries(board1).contains(w2));
		assertEquals(f.getBatteries(board1).size(), 4);
	}
	
	@Test
	public void loadProgramFromFile(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 1000D);
		f.putRobot(b, 5L, 5L, r);
		assertEquals(0, f.loadProgramFromFile(r, "src/res/programs/gump.prog"));
		
		r.executeProgramStep();
		r.executeProgramStep();
		assertEquals(new Energy(500D), r.getEnergy());
		
		assertEquals(-1, f.loadProgramFromFile(r, "#doesnotexist"));
		
		assertEquals(-1, f.loadProgramFromFile(r, "src/res/programs/incorrect/staircase_wrongSyntaxI.prog"));
	}
	
	@Test
	public void saveProgramToFile(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 1000D);
		f.putRobot(b, 5L, 5L, r);
		
		assertEquals(0, f.loadProgramFromFile(r, "src/res/programs/gump.prog"));
		assertEquals(0, f.saveProgramToFile(r, "C:/gump.prog"));
		
		r.executeProgramStep();
		assertEquals(new Energy(500D), r.getEnergy());
	}
	
	@Test
	public void saveProgramToFile_invalidPrograms(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 1000D);
		f.putRobot(b, 5L, 5L, r);
		
		assertEquals(-1, f.loadProgramFromFile(r, "src/res/programs/incorrect/staircase_wrongSyntaxI.prog"));
		assertEquals(-1, f.saveProgramToFile(r, "C:/staircase_wrongSyntaxI.prog"));
	}
	
	@Test
	public void prettyPrintProgram(){
		Robot r = f.createRobot(1, 1000D);
		assertEquals(0, f.loadProgramFromFile(r, "src/res/programs/move.prog"));
		Writer writer = new StringWriter();
		f.prettyPrintProgram(r, writer);
		assertEquals("(move)", writer.toString());
	}
	
	@Test
	public void prettyPrintProgram_invalidProgram(){
		Robot r = f.createRobot(1, 1000D);
		assertEquals(-1, f.loadProgramFromFile(r, "src/res/programs/movee.txt"));
		Writer writer = new StringWriter();
		f.prettyPrintProgram(r, writer);
	}
	
	@Test
	public void stepn(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 2000D);
		f.putRobot(b, 5, 5, r);
		assertEquals(0, f.loadProgramFromFile(r, "src/res/programs/gump.prog"));
		f.stepn(r, 100);
		assertEquals(new Energy(500D), r.getEnergy());
	}
	
	@Test
	public void stepn_invalidProgram(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 2000D);
		f.putRobot(b, 5, 5, r);
		assertEquals(-1, f.loadProgramFromFile(r, "src/res/programs/incorrect/staircase_wrongSyntaxI.prog"));
		f.stepn(r, 100);
	}
	
	@Test
	public void stepn_noProgram(){
		Board b = f.createBoard(10L,10L);
		Robot r = f.createRobot(1, 2000D);
		f.putRobot(b, 5, 5, r);
		f.stepn(r, 100);
	}
}
