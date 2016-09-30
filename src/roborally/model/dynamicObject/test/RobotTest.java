package roborally.model.dynamicObject.test;

import java.io.IOException;
import java.math.BigInteger;

import roborally.board.*;
import roborally.model.*;
import roborally.model.dynamicObject.Robot;
import roborally.model.staticObject.Wall;
import roborally.model.weight.Weight;
import roborally.model.energy.*;
import roborally.model.inventory.item.*;
import roborally.program.Program;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * A class collecting tests for the class of robots.
 * 
 * @version Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 * 
 * @note 	Sometimes method names contain 'positiveCoordinateRejected', which actually means
 * 			that the given coordinate value is mathematically larger than the corresponding boundary value,
 * 			which doesn't mean that the value is always positive according to the Java implementation.
 *
 */
public class RobotTest {
	Board board100x100y;
	
	Robot amountOfEnergy15000_and_energyCapacityLimit20000;
	Robot position20x20yDirectionUp;
	Robot position20x30yDirectionUp;
	Robot position10x30yDirectionDown;
	Robot position0x0yDirectionUp;
	Robot robotWithInventoryItems;
	Battery bat;
	Battery bat2;
	
	/**
	 * Sets up an immutable test fixture.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/**
	 * Sets up a mutable test fixture.
	 */
	@Before
	public void setUp() throws Exception {
		board100x100y = new Board(100, 100);
		amountOfEnergy15000_and_energyCapacityLimit20000 = new Robot(board100x100y, new Position(7, 7), new Accu(new Energy(15000), new Energy(20000)), Direction.DOWN);
		position20x20yDirectionUp = new Robot(board100x100y, new Position(20L, 20L), new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.UP);
		position20x30yDirectionUp = new Robot(board100x100y, new Position(20L, 30L), new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.UP);
		position10x30yDirectionDown = new Robot(board100x100y, new Position(10L, 30L), new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.DOWN);
		position0x0yDirectionUp = new Robot(board100x100y, new Position(0L, 0L), new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.UP);
		robotWithInventoryItems = new Robot(board100x100y, new Position(10, 10));
		bat = new Battery(board100x100y, new Position(10, 10));
		bat2 = new Battery(board100x100y, new Position(10, 10), new Accu(new Energy(3000)), new Weight(3));
		robotWithInventoryItems.getInventory().addInventoryItem(bat);
		robotWithInventoryItems.getInventory().addInventoryItem(bat2);
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	@Test
	public void construct1_NotNull(){
		Robot rob = new Robot();
		assertTrue(rob.getBoard() == null);
		assertTrue(rob.getPosition() == null);
		assertEquals(rob.getDirection(), Robot.STANDARD_ROBOT_DIRECTION);
		assertEquals(rob.getEnergy(), Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY);
		assertEquals(rob.getEnergyCapacityLimit(), Robot.STANDARD_ROBOT_ENERGY_CAPACITY);
		assertFalse(rob.isTerminated());
	}
	
	@Test
	public void construct2_NotNull(){
		Robot rob = new Robot(new Accu(new Energy(1500), new Energy(8000)));
		assertTrue(rob.getBoard() == null);
		assertTrue(rob.getPosition() == null);
		assertEquals(rob.getDirection(), Robot.STANDARD_ROBOT_DIRECTION);
		assertEquals(rob.getEnergy(), new Energy(1500));
		assertEquals(rob.getEnergyCapacityLimit(), new Energy(8000));
		assertFalse(rob.isTerminated());
	}
	
	@Test
	public void construct3_NotNull(){
		Robot rob = new Robot(board100x100y, new Position(15, 15));
		assertTrue(rob.getBoard() == board100x100y);
		assertEquals(rob.getPosition(), new Position(15, 15));
		assertEquals(rob.getDirection(), Robot.STANDARD_ROBOT_DIRECTION);
		assertEquals(rob.getEnergy(), Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY);
		assertEquals(rob.getEnergyCapacityLimit(), Robot.STANDARD_ROBOT_ENERGY_CAPACITY);
		assertFalse(rob.isTerminated());
	}
	
	@Test
	public void construct4_NotNull(){
		Robot rob = new Robot(board100x100y, new Position(15, 15), new Accu(new Energy(1500), new Energy(8000)), Direction.RIGHT);
		assertTrue(rob.getBoard() == board100x100y);
		assertEquals(rob.getPosition(), new Position(15, 15));
		assertEquals(rob.getDirection(), Direction.RIGHT);
		assertEquals(rob.getEnergy(), new Energy(1500));
		assertEquals(rob.getEnergyCapacityLimit(), new Energy(8000));
		assertFalse(rob.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgumentException(){
		@SuppressWarnings("unused")
		Robot rob = new Robot(board100x100y, new Position(15, 15));
		@SuppressWarnings("unused")
		Robot robb = new Robot(board100x100y, new Position(15, 15));
	}
	
	@Test
	public void terminate_normalCase(){
		robotWithInventoryItems.terminate();
		assertTrue(robotWithInventoryItems.getBoard() == null);
		assertTrue(robotWithInventoryItems.getPosition() == null);
		assertTrue(robotWithInventoryItems.getAccu() == null);
		assertTrue(robotWithInventoryItems.getInventory() == null);
		assertTrue(robotWithInventoryItems.isTerminated());
		assertTrue(bat.isTerminated());
		assertTrue(bat2.isTerminated());
	}
	
	@Test
	public void terminate_alreadyTerminated(){
		robotWithInventoryItems.terminate();
		robotWithInventoryItems.terminate();
		assertTrue(robotWithInventoryItems.getBoard() == null);
		assertTrue(robotWithInventoryItems.getPosition() == null);
		assertTrue(robotWithInventoryItems.getAccu() == null);
		assertTrue(robotWithInventoryItems.getInventory() == null);
		assertTrue(robotWithInventoryItems.isTerminated());
		assertTrue(bat.isTerminated());
		assertTrue(bat2.isTerminated());
	}
	
	@Test
	public void canSharePositionWith(){
		Robot positioned = new Robot();
		
		BoardModel r1 = new Robot();
		BoardModel w1 = new Wall();
		BoardModel b1 = new Battery();
		InventoryModel b2 = new Battery();
		Robot r2 = new Robot();
		Wall w2 = new Wall();
		Battery b3 = new Battery();
		
		assertFalse(positioned.canSharePositionWith(r1));
		assertFalse(positioned.canSharePositionWith(r2));
		assertFalse(positioned.canSharePositionWith(w1));
		assertFalse(positioned.canSharePositionWith(w2));
		assertTrue(positioned.canSharePositionWith(b1));
		assertTrue(positioned.canSharePositionWith(b2));
		assertTrue(positioned.canSharePositionWith(b3));
		assertFalse(positioned.canSharePositionWith(null));
	}
	
	@Test
	public void hasValidInventory_normalCase(){
		assertTrue(robotWithInventoryItems.hasValidInventory());
	}
	
	@Test
	public void hasValidInventory_terminated(){
		robotWithInventoryItems.terminate();
		assertTrue(robotWithInventoryItems.hasValidInventory());
	}
	
	@Test
	public void canHaveAsAccu_accuStandardEnergyValues(){
		assertTrue(robotWithInventoryItems.canHaveAsAccu(new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY)));
		assertFalse(robotWithInventoryItems.canHaveAsAccu(null));
	}
	
	@Test
	public void canHaveAsAccu_terminated(){
		robotWithInventoryItems.terminate();
		assertFalse(robotWithInventoryItems.canHaveAsAccu(new Accu(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY, Robot.STANDARD_ROBOT_ENERGY_CAPACITY)));
		assertTrue(robotWithInventoryItems.canHaveAsAccu(null));
	}
	
	@Test
	public void canHaveAsEnergy_lessThanCapacity(){
		assertTrue(robotWithInventoryItems.canHaveAsEnergy(Robot.STANDARD_ROBOT_AMOUNT_OF_ENERGY));
	}
	
	@Test
	public void canHaveAsEnergy_moreThanCapacity(){
		assertFalse(robotWithInventoryItems.canHaveAsEnergy(new Energy(21000)));
	}
	
	@Test
	public void canHaveAsEnergy_terminatedRobot(){
		robotWithInventoryItems.terminate();
		assertFalse(robotWithInventoryItems.canHaveAsEnergy(new Energy(15000)));
	}
	
	@Test
	public void getAmountOfEnergyInWS_normalCase(){
		assertEquals(15000.0, amountOfEnergy15000_and_energyCapacityLimit20000.getAmountOfEnergyInWS(), 0.01);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getAmountOfEnergyInWS_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.getAmountOfEnergyInWS();
	}
	
	@Test
	public void getEnergy_normalCase(){
		assertEquals(new Energy(15000), amountOfEnergy15000_and_energyCapacityLimit20000.getEnergy());
	}

	@Test(expected = IllegalStateException.class)
	public void getEnergy_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.getEnergy();
	}
	
	@Test
	public void dischargeAmountOfEnergy_normalCase(){
		amountOfEnergy15000_and_energyCapacityLimit20000.dischargeAmountOfEnergy(new Energy(5000));
		assertEquals(new Energy(10000), amountOfEnergy15000_and_energyCapacityLimit20000.getEnergy());
	}
	
	@Test(expected = IllegalStateException.class)
	public void dischargeAmountOfEnergy_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.dischargeAmountOfEnergy(new Energy(5000));
	}
	
	@Test
	public void getEnergyStoragePercentage_normalCase(){
		assertEquals(75.0, amountOfEnergy15000_and_energyCapacityLimit20000.getEnergyStoragePercentage(), 0.01);
	}
	
	@Test(expected = IllegalStateException.class)
	public void getEnergyStoragePercentage_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.getEnergyStoragePercentage();
	}
	
	@Test
	public void rechargeAmountOfEnergy_normalCase(){
		amountOfEnergy15000_and_energyCapacityLimit20000.rechargeAmountOfEnergy(new Energy(5000));
		assertEquals(new Energy(20000), amountOfEnergy15000_and_energyCapacityLimit20000.getEnergy());
	}
	
	@Test(expected = IllegalStateException.class)
	public void rechargeAmountOfEnergy_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.rechargeAmountOfEnergy(new Energy(5000));
	}
	
	@Test
	public void getEnergyCapacityLimit_normalCase(){
		assertEquals(new Energy(20000), amountOfEnergy15000_and_energyCapacityLimit20000.getEnergyCapacityLimit());
	}
	
	@Test(expected = IllegalStateException.class)
	public void getEnergyCapacityLimit_terminated(){
		amountOfEnergy15000_and_energyCapacityLimit20000.terminate();
		amountOfEnergy15000_and_energyCapacityLimit20000.getEnergyCapacityLimit();
	}
	
	@Test
	public void hasProperEnergyCostCores(){
		assertTrue(amountOfEnergy15000_and_energyCapacityLimit20000.hasProperEnergyCostCores());
		assertTrue(position20x20yDirectionUp.hasProperEnergyCostCores());
		assertTrue(position20x30yDirectionUp.hasProperEnergyCostCores());
		assertTrue(position10x30yDirectionDown.hasProperEnergyCostCores());
		assertTrue(position0x0yDirectionUp.hasProperEnergyCostCores());
		assertTrue(robotWithInventoryItems.hasProperEnergyCostCores());
	}
	
	@Test
	public void hasAsEnergyCost(){
		Robot r = new Robot();
		assertTrue(r.hasAsEnergyCost(Cost.MOVE));
		assertTrue(r.hasAsEnergyCost(Cost.TURN));
		assertTrue(r.hasAsEnergyCost(Cost.SHOOT));
		assertFalse(r.hasAsEnergyCost(null));
	}
	
	@Test
	public void getAllEnergyCostCores(){
		Robot r = new Robot();
		assertTrue(r.getAllEnergyCostCores().containsKey(Cost.MOVE));
		assertTrue(r.getAllEnergyCostCores().containsKey(Cost.TURN));
		assertTrue(r.getAllEnergyCostCores().containsKey(Cost.SHOOT));
		assertFalse(r.getAllEnergyCostCores().containsKey(null));
	}
	
	@Test
	public void getEnergyCostOf_presetCheck(){
		Robot r = new Robot();
		assertEquals(r.getEnergyCostOf(Cost.MOVE),Robot.STANDARD_ENERGY_MOVE_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.TURN),Robot.STANDARD_ENERGY_TURN_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),Robot.STANDARD_ENERGY_SHOOT_COST_CORE);
	}
	
	@Test
	public void getEnergyCostOf(){
		Robot r = new Robot();
		Battery b1 = new Battery(null, null, new Accu(), new Weight(500));
		Battery b2 = new Battery(null, null, new Accu(), new Weight(500));
		Battery b3 = new Battery(null, null, new Accu(), new Weight(500));
		Battery b4 = new Battery(null, null, new Accu(), new Weight(500));
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(500));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(500));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
		r.getInventory().addInventoryItem(b1);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(525));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
		r.getInventory().addInventoryItem(b2);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(550));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
		r.getInventory().addInventoryItem(b3);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(575));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
		r.getInventory().addInventoryItem(b4);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(600));
		assertEquals(r.getEnergyCostOf(Cost.TURN),new Energy(100));
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(1000));
	}
	
	@Test
	public void setEnergyCostCore(){
		Robot r = new Robot();
		assertEquals(r.getEnergyCostOf(Cost.MOVE),Robot.STANDARD_ENERGY_MOVE_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.TURN),Robot.STANDARD_ENERGY_TURN_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),Robot.STANDARD_ENERGY_SHOOT_COST_CORE);
		r.setEnergyCostCore(new Energy(50000), Cost.SHOOT);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),Robot.STANDARD_ENERGY_MOVE_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.TURN),Robot.STANDARD_ENERGY_TURN_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(50000));
		r.setEnergyCostCore(new Energy(1000), Cost.MOVE);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(1000));
		assertEquals(r.getEnergyCostOf(Cost.TURN),Robot.STANDARD_ENERGY_TURN_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(50000));
		Battery b = new Battery(null, null, new Accu(new Energy(500)), new Weight(2000));
		r.getInventory().addInventoryItem(b);
		assertEquals(r.getEnergyCostOf(Cost.MOVE),new Energy(1100));
		assertEquals(r.getEnergyCostOf(Cost.TURN),Robot.STANDARD_ENERGY_TURN_COST_CORE);
		assertEquals(r.getEnergyCostOf(Cost.SHOOT),new Energy(50000));
	}
	
	@Test (expected = IllegalStateException.class)
	public void setEnergyCostCore_terminated(){
		Robot r = new Robot();
		r.terminate();
		Energy e = new Energy(50000);
		r.setEnergyCostCore(e, Cost.MOVE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setEnergyCostCore_nullCost(){
		Robot r = new Robot();
		Energy e = new Energy(50000);
		r.setEnergyCostCore(e, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setEnergyCostCore_nullEnergy(){
		Robot r = new Robot();
		r.setEnergyCostCore(null, Cost.MOVE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void setEnergyCostCore_nullCost_nullEnergy(){
		Robot r = new Robot();
		r.setEnergyCostCore(null, null);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getEnergyCostOf_terminated(){
		Robot r = new Robot();
		r.terminate();
		r.getEnergyCostOf(Cost.MOVE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getEnergyCostOf_nullCost(){
		Robot r = new Robot();
		r.getEnergyCostOf(null);
	}
	
	@Test
	public void turnClockwise_normalCase(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnClockwise();
		assertEquals(Direction.turnDirectionClockwise(Direction.UP), position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.TURN)), position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void turnCounterClockwise_normalCase(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnCounterClockwise();
		assertEquals(Direction.turnDirectionCounterClockwise(Direction.UP), position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.TURN)), position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void turnToDirection_oneTurn(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnToDirection(Direction.RIGHT);
		assertEquals(Direction.RIGHT, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.TURN)), position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void turnToDirection_twoTurns(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnToDirection(Direction.DOWN);
		assertEquals(Direction.DOWN, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.TURN).multiply(2)), position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void turnToDirection_oneCCTurn(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnToDirection(Direction.LEFT);
		assertEquals(Direction.LEFT, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.TURN)), position0x0yDirectionUp.getEnergy());
	}

	@Test
	public void turnToDirection_zeroTurns(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnToDirection(Direction.UP);
		assertEquals(Direction.UP, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy, position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void turnToDirection_nullDirection(){
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.turnToDirection(Direction.UP);
		assertEquals(Direction.UP, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy, position0x0yDirectionUp.getEnergy());
		position0x0yDirectionUp.turnToDirection(null);
		assertEquals(Direction.UP, position0x0yDirectionUp.getDirection());
		assertEquals(oldEnergy, position0x0yDirectionUp.getEnergy());
	}
	
	@Test
	public void canDoEnergyCostMethod_normalCase(){
		assertTrue(position0x0yDirectionUp.canDoEnergyCostMethod(position0x0yDirectionUp.getEnergyCostOf(Cost.SHOOT)));
	}
	
	@Test
	public void canDoEnergyCostMethod_invalidEnergyCost(){
		assertFalse(position0x0yDirectionUp.canDoEnergyCostMethod(null));
	}
	
	@Test (expected = IllegalStateException.class)
	public void canDoEnergyCostMethod_terminatedRobot(){
		position0x0yDirectionUp.terminate();
		position0x0yDirectionUp.canDoEnergyCostMethod(position0x0yDirectionUp.getEnergyCostOf(Cost.SHOOT));
	}
	
	@Test
	public void canDoEnergyCostMethod_terminatedRobotII(){
		position0x0yDirectionUp.terminate();
		assertFalse(position0x0yDirectionUp.canDoEnergyCostMethod(Energy.WS_0));
	}
	
	@Test
	public void canDoEnergyCostMethod_noBoardRobot(){
		Robot rob = new Robot();
		assertFalse(rob.canDoEnergyCostMethod(rob.getEnergyCostOf(Cost.SHOOT)));
	}
	
	@Test
	public void move_up(){
		Energy oldEnergy = position20x20yDirectionUp.getEnergy();
		position20x20yDirectionUp.move();
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 19);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 20);
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE)), position20x20yDirectionUp.getEnergy());
		assertEquals(Direction.UP, position20x20yDirectionUp.getDirection());
	}
	
	@Test
	public void move_right(){
		position20x20yDirectionUp.turnClockwise();
		Energy oldEnergy = position20x20yDirectionUp.getEnergy();
		position20x20yDirectionUp.move();
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 20);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 21);
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE)), position20x20yDirectionUp.getEnergy());
		assertEquals(Direction.RIGHT, position20x20yDirectionUp.getDirection());
	}
	
	@Test
	public void move_left(){
		position20x20yDirectionUp.turnCounterClockwise();
		Energy oldEnergy = position20x20yDirectionUp.getEnergy();
		position20x20yDirectionUp.move();
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 20);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 19);
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE)), position20x20yDirectionUp.getEnergy());
		assertEquals(Direction.LEFT, position20x20yDirectionUp.getDirection());
	}
	
	@Test
	public void move_down(){
		position20x20yDirectionUp.turnClockwise();
		position20x20yDirectionUp.turnClockwise();
		Energy oldEnergy = position20x20yDirectionUp.getEnergy();
		position20x20yDirectionUp.move();
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 21);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 20);
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE)), position20x20yDirectionUp.getEnergy());
		assertEquals(Direction.DOWN, position20x20yDirectionUp.getDirection());
	}

	@Test(expected = IllegalArgumentException.class)
	public void move_onBorder(){
		position0x0yDirectionUp.move();
	}

	@Test(expected = IllegalArgumentException.class)
	public void move_onOtherImpassibleUnit(){
		Wall wal = new Wall();
		board100x100y.addBoardModelAt(new Position(20, 19), wal);
		position20x20yDirectionUp.move();
	}
	
	@Test(expected = IllegalStateException.class)
	public void move_terminated(){
		position20x20yDirectionUp.terminate();
		position20x20yDirectionUp.move();
	}
	
	@Test
	public void move_heavier(){
		Board bmove = new Board(20L, 20L);
		
		Robot r1 = new Robot(new Accu(new Energy(2000)));
		Battery b1 = new Battery(null, null, new Accu(), new Weight(1000));
		r1.getInventory().addInventoryItem(b1);
		bmove.addBoardModelAt(new Position(5L,5L), r1);
		r1.move();
		assertEquals(r1.getAccu().getAmountOfEnergy(),new Energy(1450));
		assertEquals(r1.getPosition(),new Position(5L,6L));
		
		Robot r2 = new Robot(new Accu(new Energy(2000)));
		Battery b2 = new Battery(null, null, new Accu(), new Weight(1900));
		r2.getInventory().addInventoryItem(b2);
		bmove.addBoardModelAt(new Position(15L,15L), r2);
		r2.move();
		assertEquals(r2.getAccu().getAmountOfEnergy(),new Energy(1405));
		assertEquals(r2.getPosition(),new Position(15L,16L));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_oneTurnTwoTranslations(){
		Board b = new Board(2000, 1000);
		Robot rob = new Robot(b, new Position(20, 20), new Accu(new Energy(0)), Direction.UP);
		assertEquals(new Energy(1100),rob.getMinimalEnergyRequiredToReachPosition(new Position(21, 19)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_twoTurnsOneTranslation(){
		Board b = new Board(2000, 1000);
		Robot rob = new Robot(b, new Position(20, 20), new Accu(new Energy(0)), Direction.UP);
		assertEquals(new Energy(700),rob.getMinimalEnergyRequiredToReachPosition(new Position(20, 21)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_twoTurnsTwoTranslations(){
		Board b = new Board(2000, 1000);
		Robot rob = new Robot(b, new Position(20, 20), new Accu(new Energy(0)), Direction.UP);
		assertEquals(new Energy(1200),rob.getMinimalEnergyRequiredToReachPosition(new Position(21, 21)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_oneTurnOneTranslationOneTurnOneTranslation(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(0)), Direction.UP);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(9).add(rob.getEnergyCostOf(Cost.TURN).multiply(2)),rob.getMinimalEnergyRequiredToReachPosition(new Position(4, 5)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_oneTurnOneTranslationOneTurnOneTranslation2(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(0)), Direction.LEFT);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(9).add(rob.getEnergyCostOf(Cost.TURN).multiply(2)),rob.getMinimalEnergyRequiredToReachPosition(new Position(4, 5)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_OneTranslationOneTurnOneTranslation(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(0)), Direction.RIGHT);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(9).add(rob.getEnergyCostOf(Cost.TURN).multiply(1)),rob.getMinimalEnergyRequiredToReachPosition(new Position(4, 5)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_OneTranslationOneTurnOneTranslation2(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(0)), Direction.DOWN);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(9).add(rob.getEnergyCostOf(Cost.TURN).multiply(1)),rob.getMinimalEnergyRequiredToReachPosition(new Position(4, 5)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_TwoTurnsOneTranslation(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(2, 2), new Accu(new Energy(0)), Direction.LEFT);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(4).add(rob.getEnergyCostOf(Cost.TURN).multiply(2)),rob.getMinimalEnergyRequiredToReachPosition(new Position(6, 2)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_clearBoard_OneTurnOneTranslation(){
		Board b = new Board(10, 10);
		Robot rob = new Robot(b, new Position(2, 2), new Accu(new Energy(0)), Direction.UP);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(4).add(rob.getEnergyCostOf(Cost.TURN).multiply(1)),rob.getMinimalEnergyRequiredToReachPosition(new Position(6, 2)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getMinimalEnergyRequiredToReachPosition_searchLimitNotReachedPointNotFound(){
		Board b = new Board(9, 7);
		b.addBoardModelAt(new Position(1, 0), new Wall());
		b.addBoardModelAt(new Position(0, 1), new Wall());
		Robot rob = new Robot(b, new Position(0, 0), new Accu(new Energy(0)), Direction.UP);
		rob.getMinimalEnergyRequiredToReachPosition(new Position(3, 3));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_mazeBoard(){
		Board b = new Board(9, 7);
		b.addBoardModelAt(new Position(3, 0), new Wall());
		b.addBoardModelAt(new Position(1, 1), new Wall());
		b.addBoardModelAt(new Position(1, 2), new Wall());
		b.addBoardModelAt(new Position(2, 2), new Wall());
		b.addBoardModelAt(new Position(3, 2), new Wall());
		b.addBoardModelAt(new Position(4, 2), new Wall());
		b.addBoardModelAt(new Position(5, 2), new Wall());
		b.addBoardModelAt(new Position(6, 2), new Wall());
		b.addBoardModelAt(new Position(6, 1), new Wall());
		b.addBoardModelAt(new Position(7, 1), new Wall());
		b.addBoardModelAt(new Position(8, 1), new Wall());
		b.addBoardModelAt(new Position(8, 2), new Wall());
		b.addBoardModelAt(new Position(8, 3), new Wall());
		b.addBoardModelAt(new Position(8, 4), new Wall());
		b.addBoardModelAt(new Position(7, 4), new Wall());
		b.addBoardModelAt(new Position(6, 4), new Wall());
		b.addBoardModelAt(new Position(5, 4), new Wall());
		b.addBoardModelAt(new Position(4, 4), new Wall());
		b.addBoardModelAt(new Position(3, 4), new Wall());
		b.addBoardModelAt(new Position(2, 4), new Wall());
		b.addBoardModelAt(new Position(1, 4), new Wall());
		b.addBoardModelAt(new Position(0, 4), new Wall());
		b.addBoardModelAt(new Position(6, 5), new Wall());
		b.addBoardModelAt(new Position(6, 6), new Wall());
		b.addBoardModelAt(new Position(5, 6), new Wall());
		b.addBoardModelAt(new Position(4, 6), new Wall());
		b.addBoardModelAt(new Position(3, 6), new Wall());
		b.addBoardModelAt(new Position(8, 7), new Wall());
		Robot rob = new Robot(b, new Position(5, 5), new Accu(new Energy(0)), Direction.UP);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(41).add(rob.getEnergyCostOf(Cost.TURN).multiply(14)),rob.getMinimalEnergyRequiredToReachPosition(new Position(7, 2)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_turnOverExtraTranslationsPreference(){
		Board b = new Board(2, 3);
		b.addBoardModelAt(new Position(1, 1), new Wall());
		b.addBoardModelAt(new Position(1, 2), new Wall());
		Robot rob = new Robot(b, new Position(0, 2), new Accu(new Energy(0)), Direction.UP);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(4).add(rob.getEnergyCostOf(Cost.TURN).multiply(4)),rob.getMinimalEnergyRequiredToReachPosition(new Position(2, 2)));
	}
	
	@Test
	public void getMinimalEnergyRequiredToReachPosition_blockedByWall(){
		Board b = new Board(20, 20);
		b.addBoardModelAt(new Position(4, 5), new Wall());
		b.addBoardModelAt(new Position(4, 6), new Wall());
		b.addBoardModelAt(new Position(4, 7), new Wall());
		b.addBoardModelAt(new Position(4, 8), new Wall());
		b.addBoardModelAt(new Position(4, 9), new Wall());
		Robot rob = new Robot(b, new Position(1, 7), new Accu(new Energy(0)), Direction.UP);
		assertEquals(rob.getEnergyCostOf(Cost.MOVE).multiply(11).add(rob.getEnergyCostOf(Cost.TURN).multiply(2)),rob.getMinimalEnergyRequiredToReachPosition(new Position(6, 7)));
		b.removeBoardModel(rob);
		Robot rob2 = new Robot(b, new Position(1, 7), new Accu(new Energy(0)), Direction.RIGHT);
		assertEquals(rob2.getEnergyCostOf(Cost.MOVE).multiply(11).add(rob2.getEnergyCostOf(Cost.TURN).multiply(3)),rob2.getMinimalEnergyRequiredToReachPosition(new Position(6, 7)));
		b.removeBoardModel(rob2);
		Robot rob3 = new Robot(b, new Position(1, 7), new Accu(new Energy(0)), Direction.DOWN);
		assertEquals(rob3.getEnergyCostOf(Cost.MOVE).multiply(11).add(rob3.getEnergyCostOf(Cost.TURN).multiply(2)),rob3.getMinimalEnergyRequiredToReachPosition(new Position(6, 7)));
		b.removeBoardModel(rob3);
		Robot rob4 = new Robot(b, new Position(1, 7), new Accu(new Energy(0)), Direction.RIGHT);
		assertEquals(rob4.getEnergyCostOf(Cost.MOVE).multiply(11).add(rob4.getEnergyCostOf(Cost.TURN).multiply(3)),rob4.getMinimalEnergyRequiredToReachPosition(new Position(6, 7)));
		b.removeBoardModel(rob4);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getMinimalEnergyRequiredToReachPosition_positionNotOnBoard(){
		position0x0yDirectionUp.getMinimalEnergyRequiredToReachPosition(new Position(150, 150));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getMinimalEnergyRequiredToReachPosition_positionBlockedByObstacle(){
		position0x0yDirectionUp.getMinimalEnergyRequiredToReachPosition(new Position(20, 20));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getMinimalEnergyRequiredToReachPosition_negativePosition(){
		position0x0yDirectionUp.getMinimalEnergyRequiredToReachPosition(new Position(-1, -1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getMinimalEnergyRequiredToReachPosition_unreachablePosition(){
		Board b = new Board(2000, 1000);
		b.addBoardModelAt(new Position(1, 0), new Wall());
		b.addBoardModelAt(new Position(0, 1), new Wall());
		Robot rob = new Robot(b, new Position(5, 5), new Accu(new Energy(0)), Direction.UP);
		rob.getMinimalEnergyRequiredToReachPosition(new Position(0, 0));
	}
	
	@Test(expected = IllegalStateException.class)
	public void getMinimalEnergyRequiredToReachPosition_terminated(){
		Board b = new Board(2000, 1000);
		Robot rob = new Robot(b, new Position(5, 5), new Accu(new Energy(0)), Direction.UP);
		rob.terminate();
		rob.getMinimalEnergyRequiredToReachPosition(new Position(0, 0));
	}
	
	@Test
	public void moveNextTo_oneRobotHasEnoughEnergyOtherRobotHasSome(){
		Board b = new Board(2000, 1000);
		Robot position20x20yDirectionUp = new Robot(b, new Position(20, 20), new Accu(new Energy(1100)), Direction.UP);
		Robot position10x30yDirectionDown = new Robot(b, new Position(10, 30), new Accu(new Energy(20000)), Direction.DOWN);
		Energy initenergy = position10x30yDirectionDown.getEnergy();
		position20x20yDirectionUp.moveNextTo(position10x30yDirectionDown);
		assertEquals(position10x30yDirectionDown.getCoordinate(Dimension.HORIZONTAL), 20L);
		assertEquals(position10x30yDirectionDown.getCoordinate(Dimension.VERTICAL), 21L);
		assertEquals(position10x30yDirectionDown.getDirection(), Direction.UP);
		assertEquals(position10x30yDirectionDown.getEnergy(), initenergy.subtract(new Energy(9700)));
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 20L);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 20L);
		assertEquals(position20x20yDirectionUp.getDirection(), Direction.UP);
		assertEquals(position20x20yDirectionUp.getEnergy(), new Energy(1100));
	}
	
	@Test
	public void moveNextTo_bothRobotsHaveSomeEnergy(){
		Board b = new Board(2000, 1000);
		Robot position20x20yDirectionUp = new Robot(b, new Position(20, 20), new Accu(new Energy(1300)), Direction.UP);
		Robot position10x30yDirectionDown = new Robot(b, new Position(10, 30), new Accu(new Energy(600)), Direction.DOWN);
		position20x20yDirectionUp.moveNextTo(position10x30yDirectionDown);
		assertEquals(position10x30yDirectionDown.getCoordinate(Dimension.HORIZONTAL), 11L);
		assertEquals(position10x30yDirectionDown.getCoordinate(Dimension.VERTICAL), 30L);
		assertEquals(position10x30yDirectionDown.getDirection(), Direction.RIGHT);
		assertEquals(position10x30yDirectionDown.getEnergy(), new Energy(0));
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.HORIZONTAL), 18L);
		assertEquals(position20x20yDirectionUp.getCoordinate(Dimension.VERTICAL), 20L);
		assertEquals(position20x20yDirectionUp.getDirection(), Direction.LEFT);
		assertEquals(position20x20yDirectionUp.getEnergy(), new Energy(200));
	}
	
	@Test
	public void moveNextTo_bothRobotsHaveMoreEnergyThanIsRequiredForFirstTranslationButNotEnoughForWholeMovement(){
		Board b = new Board(2000, 1000);
		Robot position20x20yDirectionUp = new Robot(b, new Position(20, 20), new Accu(new Energy(5500)), Direction.UP);
		Robot position10x30yDirectionDown = new Robot(b, new Position(10, 30), new Accu(new Energy(6000)), Direction.DOWN);
		Energy oldEnergy = position10x30yDirectionDown.getEnergy().add(position20x20yDirectionUp.getEnergy());
		position20x20yDirectionUp.moveNextTo(position10x30yDirectionDown);
		assertEquals(BigInteger.valueOf(1)
				, position10x30yDirectionDown.getPosition().getManhattanDistanceSeparation(position20x20yDirectionUp.getPosition()));
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.TURN).multiply(3))
				, position10x30yDirectionDown.getEnergy().add(position20x20yDirectionUp.getEnergy()));
	}
	
	@Test
	public void moveNextTo_oneRobotHasEnoughForFullTranslation_otherRobotHasEnoughForSingleTranslationI(){
		Board b = new Board(2000, 1000);
		Robot position20x20yDirectionUp = new Robot(b, new Position(20, 20), new Accu(new Energy(20000)), Direction.UP);
		Robot position10x30yDirectionDown = new Robot(b, new Position(10, 30), new Accu(new Energy(5000)), Direction.DOWN);
		Energy oldEnergy = position10x30yDirectionDown.getEnergy().add(position20x20yDirectionUp.getEnergy());
		position20x20yDirectionUp.moveNextTo(position10x30yDirectionDown);
		assertEquals(BigInteger.valueOf(1)
				, position10x30yDirectionDown.getPosition().getManhattanDistanceSeparation(position20x20yDirectionUp.getPosition()));
		assertEquals(oldEnergy.subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(position20x20yDirectionUp.getEnergyCostOf(Cost.TURN).multiply(2))
				, position10x30yDirectionDown.getEnergy().add(position20x20yDirectionUp.getEnergy()));
	}
	
	@Test
	public void moveNextTo_oneRobotHasEnoughForFullTranslation_otherRobotHasEnoughForSingleTranslationII(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(20, 20), new Accu(new Energy(20000)), Direction.LEFT);
		Robot rob2 = new Robot(b, new Position(10, 30), new Accu(new Energy(4500)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(0))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_oneRobotHasEnoughForFullTranslation_otherRobotHasEnoughForSingleTranslationIII(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(20, 20), new Accu(new Energy(5000)), Direction.LEFT);
		Robot rob2 = new Robot(b, new Position(10, 30), new Accu(new Energy(20000)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(0))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_oneRobotHasEnoughForFullTranslation_otherRobotHasEnoughForSingleTranslationIV(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(20, 20), new Accu(new Energy(4500)), Direction.LEFT);
		Robot rob2 = new Robot(b, new Position(10, 30), new Accu(new Energy(20000)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(0))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_straightVerticalLine_bothEnoughEnergy(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(20, 20), new Accu(new Energy(20000)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(20, 30), new Accu(new Energy(19000)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(9)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(0))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_straightHorizontalLine_OneRobotEnoughEnergy(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(10, 30), new Accu(new Energy(1100)), Direction.DOWN);
		Robot rob2 = new Robot(b, new Position(20, 30), new Accu(new Energy(20000)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(9)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(1))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}	
	
	@Test
	public void moveNextTo_bothRobotsEnoughEnergyOneAlreadyCorrectDirection(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(10, 30), new Accu(new Energy(20000)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(20, 20), new Accu(new Energy(20000)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(19)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(1))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_lowestEnergyReqStartIsInefficient(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(0, 3), new Accu(new Energy(600)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(8, 0), new Accu(new Energy(1600)), Direction.RIGHT);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(7)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(4)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(2))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_completeTranslationToRobotRowOrColumnIsInefficient(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(0, 3), new Accu(new Energy(1600)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(4, 0), new Accu(new Energy(600)), Direction.RIGHT);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(3)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(4)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(2))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_mazeBoard1(){
		Board b = new Board(2000, 1000);
		b.addBoardModelAt(new Position(1, 1), new Wall());
		b.addBoardModelAt(new Position(2, 1), new Wall());
		b.addBoardModelAt(new Position(3, 1), new Wall());
		b.addBoardModelAt(new Position(4, 1), new Wall());
		b.addBoardModelAt(new Position(5, 1), new Wall());
		b.addBoardModelAt(new Position(3, 2), new Wall());
		b.addBoardModelAt(new Position(3, 3), new Wall());
		b.addBoardModelAt(new Position(3, 4), new Wall());
		b.addBoardModelAt(new Position(0, 5), new Wall());
		b.addBoardModelAt(new Position(1, 5), new Wall());
		b.addBoardModelAt(new Position(2, 5), new Wall());
		b.addBoardModelAt(new Position(3, 5), new Wall());
		b.addBoardModelAt(new Position(4, 5), new Wall());
		b.addBoardModelAt(new Position(5, 5), new Wall());
		b.addBoardModelAt(new Position(6, 5), new Wall());
		Robot rob1 = new Robot(b, new Position(1, 3), new Accu(new Energy(3200)), Direction.LEFT);
		Robot rob2 = new Robot(b, new Position(4, 3), new Accu(new Energy(3300)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(2)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(1)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(2))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_mazeBoard2(){
		Board b = new Board(2000, 1000);
		b.addBoardModelAt(new Position(0, 1), new Wall());
		b.addBoardModelAt(new Position(1, 1), new Wall());
		b.addBoardModelAt(new Position(0, 2), new Wall());
		b.addBoardModelAt(new Position(1, 2), new Wall());
		Robot rob1 = new Robot(b, new Position(0, 0), new Accu(new Energy(1200)), Direction.LEFT);
		Robot rob2 = new Robot(b, new Position(0, 3), new Accu(new Energy(2300)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(6)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(4))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test
	public void moveNextTo_mazeBoard3(){
		Board b = new Board(2000, 1000);
		b.addBoardModelAt(new Position(2, 1), new Wall());
		b.addBoardModelAt(new Position(2, 2), new Wall());
		b.addBoardModelAt(new Position(2, 3), new Wall());
		b.addBoardModelAt(new Position(2, 4), new Wall());
		b.addBoardModelAt(new Position(2, 5), new Wall());
		b.addBoardModelAt(new Position(2, 6), new Wall());
		Robot rob1 = new Robot(b, new Position(0, 2), new Accu(new Energy(3200)), Direction.RIGHT);
		Robot rob2 = new Robot(b, new Position(4, 2), new Accu(new Energy(3300)), Direction.UP);
		Energy oldEnergy = rob2.getEnergy().add(rob1.getEnergy());
		rob1.moveNextTo(rob2);
		assertEquals(BigInteger.valueOf(1)
				, rob2.getPosition().getManhattanDistanceSeparation(rob1.getPosition()));
		assertEquals(oldEnergy.subtract(rob1.getEnergyCostOf(Cost.MOVE).multiply(7)).subtract(rob1.getEnergyCostOf(Cost.TURN).multiply(2))
				, rob2.getEnergy().add(rob1.getEnergy()));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_nullRobot(){
		position0x0yDirectionUp.moveNextTo(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_nullBoardOther(){
		Robot r1 = new Robot();
		position0x0yDirectionUp.moveNextTo(r1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_nullBoardThis(){
		Robot r1 = new Robot();
		r1.moveNextTo(position0x0yDirectionUp);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_nullBoardBoth(){
		Robot r1 = new Robot();
		Robot r2 = new Robot();
		r1.moveNextTo(r2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_sameRobot(){
		Robot r1 = new Robot();
		r1.moveNextTo(r1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_terminatedOther(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(0, 3), new Accu(new Energy(1600)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(4, 0), new Accu(new Energy(600)), Direction.RIGHT);
		rob2.terminate();
		rob1.moveNextTo(rob2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_terminatedThis(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(0, 3), new Accu(new Energy(1600)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(4, 0), new Accu(new Energy(600)), Direction.RIGHT);
		rob1.terminate();
		rob1.moveNextTo(rob2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_terminatedBoth(){
		Board b = new Board(2000, 1000);
		Robot rob1 = new Robot(b, new Position(0, 3), new Accu(new Energy(1600)), Direction.UP);
		Robot rob2 = new Robot(b, new Position(4, 0), new Accu(new Energy(600)), Direction.RIGHT);
		rob1.terminate();
		rob2.terminate();
		rob1.moveNextTo(rob2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void moveNextTo_differentBoard(){
		Board b = new Board(2000, 1000);
		Robot r1 = new Robot(b, new Position(0, 3), new Accu(new Energy(1600)), Direction.UP);
		position0x0yDirectionUp.moveNextTo(r1);
	}
	
	@Test
	public void shoot_noTarget(){
		int amtBoardModels = board100x100y.getNbBoardModels();
		Energy oldEnergy = position0x0yDirectionUp.getEnergy();
		position0x0yDirectionUp.shoot();
		assertEquals(oldEnergy.subtract(position0x0yDirectionUp.getEnergyCostOf(Cost.SHOOT)), position0x0yDirectionUp.getEnergy());
		assertEquals(amtBoardModels, board100x100y.getNbBoardModels());
	}
	
	@Test
	 public void shoot_normalCaseRight(){
		Board board20x20y = new Board(20L, 20L);
		Robot target = new Robot(board20x20y, new Position(15, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.RIGHT);
		Robot shooter = new Robot(board20x20y, new Position(10, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.RIGHT);
		Energy oldEnergy = shooter.getEnergy();
		shooter.shoot();
		assertTrue(target.isTerminated());
		assertEquals(oldEnergy.subtract(shooter.getEnergyCostOf(Cost.SHOOT)), shooter.getEnergy());
	 }
	 
	 @Test
	 public void shoot_normalCaseLeft(){
		Board board20x20y = new Board(20L, 20L);
		Robot target = new Robot(board20x20y, new Position(15, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.RIGHT);
		Robot shooter = new Robot(board20x20y, new Position(20, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.LEFT);
		Energy oldEnergy = shooter.getEnergy();
		shooter.shoot();
		assertTrue(target.isTerminated());
		assertEquals(oldEnergy.subtract(shooter.getEnergyCostOf(Cost.SHOOT)), shooter.getEnergy());
	 }
	 
	 @Test
	 public void shoot_normalCaseUp(){
		Board board20x20y = new Board(20L, 20L);
		Robot target = new Robot(board20x20y, new Position(15, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.RIGHT);
		Robot shooter = new Robot(board20x20y, new Position(15, 15), new Accu(new Energy(3000), new Energy(4000)), Direction.UP);
		Energy oldEnergy = shooter.getEnergy();
		shooter.shoot();
		assertTrue(target.isTerminated());
		assertEquals(oldEnergy.subtract(shooter.getEnergyCostOf(Cost.SHOOT)), shooter.getEnergy());
	 }
	 
	 @Test
	 public void eliminateRandomTarget_normalCaseDown(){
		Board board20x20y = new Board(20L, 20L);
		Robot target = new Robot(board20x20y, new Position(15, 10), new Accu(new Energy(3000), new Energy(4000)), Direction.RIGHT);
		Robot shooter = new Robot(board20x20y, new Position(15, 5), new Accu(new Energy(3000), new Energy(4000)), Direction.DOWN);
		Energy oldEnergy = shooter.getEnergy();
		shooter.shoot();
		assertTrue(target.isTerminated());
		assertEquals(oldEnergy.subtract(shooter.getEnergyCostOf(Cost.SHOOT)), shooter.getEnergy());
	 }
	 
	 @Test
	 public void shoot_multiplePossibleTargets(){
		Board board20x20y = new Board(20L, 20L);
		Battery bat = new Battery();
		board20x20y.addBoardModelAt(new Position(15, 10), bat);
		Battery bat2 = new Battery();
		board20x20y.addBoardModelAt(new Position(15, 10), bat2);
		Battery bat3 = new Battery();
		board20x20y.addBoardModelAt(new Position(15, 10), bat3);
		Energy before = bat.getAccu().getAmountOfEnergy().add(bat2.getAccu().getAmountOfEnergy().add(bat3.getAccu().getAmountOfEnergy()));
		Robot shooter = new Robot(board20x20y, new Position(15, 5), new Accu(new Energy(3000), new Energy(4000)), Direction.DOWN);
		Energy oldEnergy = shooter.getEnergy();
		shooter.shoot();
		Energy after = bat.getAccu().getAmountOfEnergy().add(bat2.getAccu().getAmountOfEnergy().add(bat3.getAccu().getAmountOfEnergy()));
		
		assertEquals(before.add(Battery.STANDARD_ENERGY_SHOT_BONUS), after);
		assertEquals(oldEnergy.subtract(shooter.getEnergyCostOf(Cost.SHOOT)), shooter.getEnergy());
	 }
	 
	 @Test
	 public void inventoryTransferFrom(){
		 Board b = new Board(20L, 20L);
		 Position p1 = new Position(0L,0L);
		 Position p2 = new Position(1L,0L);
		 Robot rc = new Robot(b, p1);
		 Robot rt = new Robot(b, p2);
		 Battery b1 = new Battery(b,p1);
		 Battery b2 = new Battery(b,p2);
		 Battery b3 = new Battery(b,p2);
		 rc.getInventory().addInventoryItem(b1);
		 rt.getInventory().addInventoryItem(b2);
		 rt.getInventory().addInventoryItem(b3);
		 assertTrue(rc.getInventory().hasAsInventoryItem(b1));
		 assertFalse(rc.getInventory().hasAsInventoryItem(b2));
		 assertFalse(rc.getInventory().hasAsInventoryItem(b3));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b1));
		 assertTrue(rt.getInventory().hasAsInventoryItem(b2));
		 assertTrue(rt.getInventory().hasAsInventoryItem(b3));
		 rc.inventoryTransferFrom(rt);
		 assertTrue(rc.getInventory().hasAsInventoryItem(b1));
		 assertTrue(rc.getInventory().hasAsInventoryItem(b2));
		 assertTrue(rc.getInventory().hasAsInventoryItem(b3));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b1));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b2));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b3));;
	 }
	 
	 @Test
	 public void inventoryTransferFrom_nullBoardPossible(){
		 Robot rc = new Robot();
		 Robot rt = new Robot();
		 Battery b1 = new Battery();
		 Battery b2 = new Battery();
		 Battery b3 = new Battery();
		 rc.getInventory().addInventoryItem(b1);
		 rt.getInventory().addInventoryItem(b2);
		 rt.getInventory().addInventoryItem(b3);
		 assertTrue(rc.getInventory().hasAsInventoryItem(b1));
		 assertFalse(rc.getInventory().hasAsInventoryItem(b2));
		 assertFalse(rc.getInventory().hasAsInventoryItem(b3));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b1));
		 assertTrue(rt.getInventory().hasAsInventoryItem(b2));
		 assertTrue(rt.getInventory().hasAsInventoryItem(b3));
		 rc.inventoryTransferFrom(rt);
		 assertTrue(rc.getInventory().hasAsInventoryItem(b1));
		 assertTrue(rc.getInventory().hasAsInventoryItem(b2));
		 assertTrue(rc.getInventory().hasAsInventoryItem(b3));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b1));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b2));
		 assertFalse(rt.getInventory().hasAsInventoryItem(b3));
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_nullTra(){
		 Robot rc = new Robot();
		 rc.inventoryTransferFrom(null);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_terminatedTra(){
		 Robot rc = new Robot();
		 Robot rt = new Robot();
		 rt.terminate();
		 rc.inventoryTransferFrom(rt);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_terminatedCol(){
		 Robot rc = new Robot();
		 Robot rt = new Robot();
		 rc.terminate();
		 rc.inventoryTransferFrom(rt);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_selfReference(){
		 Robot rc = new Robot();
		 rc.inventoryTransferFrom(rc);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_differentBoard(){
		 Board b1 = new Board(20L, 20L);
		 Board b2 = new Board(20L, 20L);
		 Position p1 = new Position(4L,5L);
		 Position p2 = new Position(3L,5L);
		 Robot rc = new Robot(b1, p1);
		 Robot rt = new Robot(b2, p2);
		 rc.inventoryTransferFrom(rt);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_inRangeNotNextTo(){
		 Board b = new Board(20L, 20L);
		 Position p1 = new Position(4L,5L);
		 Position p2 = new Position(3L,4L);
		 Robot rc = new Robot(b, p1);
		 Robot rt = new Robot(b, p2);
		 rc.inventoryTransferFrom(rt);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void inventoryTransferFrom_notNextTo(){
		 Board b = new Board(20L, 20L);
		 Position p1 = new Position(0L,0L);
		 Position p2 = new Position(3L,4L);
		 Robot rc = new Robot(b, p1);
		 Robot rt = new Robot(b, p2);
		 rc.inventoryTransferFrom(rt);
	 }
	 
	 @Test
	 public void executeProgramStep_oneStepProgram() throws IllegalArgumentException, IOException{
		 Board b = new Board(20, 20);
		 Robot rob = new Robot(b, new Position(5, 5));
		 Program program = new Program(rob, "src/res/programs/move.prog");
		 rob.setProgram(program);
		 Position nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
		 assertTrue(rob.getProgramFinished());
		 Position oldpos = rob.getPosition().clone();
		 rob.executeProgramStep();
		 assertEquals(oldpos, rob.getPosition());
	 }
	 
	 @Test
	 public void setProgramFromFile() throws IllegalArgumentException, IOException{
		 Board b = new Board(20, 20);
		 Robot rob = new Robot(b, new Position(5, 5));
		 rob.setProgramFromFile("src/res/programs/move.prog");
		 Position nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
		 assertTrue(rob.getProgramFinished());
		 rob.setProgramFromFile("src/res/programs/move.prog");
		 nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
		 assertTrue(rob.getProgramFinished());
	 }
	 
	 @Test
	 public void writeProgramToFile() throws IOException{
		 Board b = new Board(20, 20);
		 Robot rob = new Robot(b, new Position(5, 5));
		 rob.setProgramFromFile("src/res/programs/move.prog");
		 rob.writeProgramToFile("C:/test.prog");
		 rob.setProgramFromFile("C:/test.prog");
		 Position nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
		 assertTrue(rob.getProgramFinished());
	 }
	 
	 @Test
	 public void setProgram() throws IllegalArgumentException, IOException{
		 Board b = new Board(20, 20);
		 Robot rob = new Robot(b, new Position(5, 5));
		 Program program = new Program(rob, "src/res/programs/move.prog");
		 rob.setProgram(program);
		 assertSame(program, rob.getProgram());
		 Position nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
		 assertTrue(rob.getProgramFinished());
		 rob.setProgram(program);
		 assertFalse(rob.getProgramFinished());
		 nextpos = rob.getPosition().getNextPositionInDirection(rob.getDirection());
		 rob.executeProgramStep();
		 assertEquals(nextpos, rob.getPosition());
	 }
}
