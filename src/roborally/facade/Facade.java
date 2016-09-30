package roborally.facade;

import roborally.board.*;
import roborally.model.*;
import roborally.model.dynamicObject.*;
import roborally.model.energy.*;
import roborally.model.staticObject.*;
import roborally.model.weight.*;
import roborally.model.inventory.item.*;

import java.io.IOException;
import java.io.Writer;
import java.util.*;

/**
 * Temporary class which combines methods of the Robot, Battery, RepairKit, SurpriseBox,
 * Board, Wall classes to implement the IFacede interface.
 * This class contains all the functionality required for part III of the OGP Assignment.
 * 
 * @version Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Facade implements IFacade<Board, Robot, Wall, Battery, RepairKit, SurpriseBox> {
	
	/**
	 * Create a new board with the given <code>width</code> and <code>height</code>. 
	 * 
	 * This method must return <code>null</code> if the given <code>width</code> and <code>height</code> are invalid. 
	 */
	@Override
	public Board createBoard(long width, long height){
		try{
			return new Board(width, height);
		}
		catch(IllegalArgumentException e){
			return null;
		}
	}
	
	/**
	 * Merge <code>board1</code> and <code>board2</code>.
	 * 
	 * @note	NullpointerException could happen if the given board1 refers the null reference.
	 */
	@Override
	public void merge(Board board1, Board board2){
		try{
			board1.mergeBoard(board2);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Create a new battery with initial energy equal to <code>initialEnergy</code> and weight equal to <code>weight</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative weight). 
	 */
	@Override
	public Battery createBattery(double initialEnergy, int weight){
		if(Accu.isValidAmountOfEnergy(new Energy(initialEnergy), Battery.STANDARD_BATTERY_ENERGY_CAPACITY))
			return new Battery(null, null, new Accu(new Energy(initialEnergy), Battery.STANDARD_BATTERY_ENERGY_CAPACITY), new Weight(weight));
		return null;
	}
	
	/**
	 * Put <code>battery</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given battery refers the null reference.
	 */
	@Override
	public void putBattery(Board board, long x, long y, Battery battery){
		try{
			board.addBoardModelAt(new Position(x, y), battery);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Return the x-coordinate of <code>battery</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given battery refers the null reference.
	 */
	@Override
	public long getBatteryX(Battery battery) throws IllegalStateException{
		if(battery.getBoard() == null)
			throw new IllegalStateException("The given battery is not placed on a board.");
		return battery.getCoordinate(Dimension.HORIZONTAL);
	}
	
	/**
	 * Return the y-coordinate of <code>battery</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>battery</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given battery refers the null reference.
	 */
	@Override
	public long getBatteryY(Battery battery) throws IllegalStateException{
		if(battery.getBoard() == null)
			throw new IllegalStateException("The given battery is not placed on a board.");
		return battery.getCoordinate(Dimension.VERTICAL);
	}
	
	/**
	 * Create a new repair kit that repairs <code>repairAmount</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative <code>repairAmount</code>).
	 */
	@Override
	public RepairKit createRepairKit(double repairAmount, int weight){
		if(Accu.isValidAmountOfEnergy(new Energy(repairAmount), RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY)){
			Accu accu = new Accu(new Energy(repairAmount), RepairKit.STANDARD_REPAIRKIT_ENERGY_CAPACITY);
			return new RepairKit(null, null, accu, new Weight(weight));
		}
		else
			return null;
	}

	/**
	 * Put <code>repairKit</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given repair kit refers the null reference.
	 */
	@Override
	public void putRepairKit(Board board, long x, long y, RepairKit repairKit){
		try{
			board.addBoardModelAt(new Position(x, y), repairKit);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Return the x-coordinate of <code>repairKit</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>repairKit</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given repair kit refers the null reference.
	 */
	@Override
	public long getRepairKitX(RepairKit repairKit) throws IllegalStateException {
		if(repairKit.getBoard() == null)
			throw new IllegalStateException("The given repair kit is not placed on a board.");
		return repairKit.getCoordinate(Dimension.HORIZONTAL);
	}

	/**
	 * Return the y-coordinate of <code>repairKit</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>repairKit</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given repair kit refers the null reference.
	 */
	@Override
	public long getRepairKitY(RepairKit repairKit) throws IllegalStateException {
		if(repairKit.getBoard() == null)
			throw new IllegalStateException("The given repair kit is not placed on a board.");
		return repairKit.getCoordinate(Dimension.VERTICAL);
	}

	/**
	 * Create a new surprise box with weighing <code>weight</code>. 
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative <code>weight</code>). 
	 */
	@Override
	public SurpriseBox createSurpriseBox(int weight){
		return new SurpriseBox(null, null, new Weight(weight));
	}

	/**
	 * Put <code>surpriseBox</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given surprise box refers the null reference.
	 */
	@Override
	public void putSurpriseBox(Board board, long x, long y, SurpriseBox surpriseBox){
		try{
			board.addBoardModelAt(new Position(x, y), surpriseBox);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Return the x-coordinate of <code>surpriseBox</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>surpriseBox</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given surprise box refers the null reference.
	 */
	@Override
	public long getSurpriseBoxX(SurpriseBox surpriseBox)
			throws IllegalStateException {
		if(surpriseBox.getBoard() == null)
			throw new IllegalStateException("The given surprise box is not placed on a board.");
		return surpriseBox.getCoordinate(Dimension.HORIZONTAL);
	}

	/**
	 * Return the y-coordinate of <code>surpriseBox</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>surpriseBox</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given surprise box refers the null reference.
	 */
	@Override
	public long getSurpriseBoxY(SurpriseBox surpriseBox)
			throws IllegalStateException {
		if(surpriseBox.getBoard() == null)
			throw new IllegalStateException("The given surprise box is not placed on a board.");
		return surpriseBox.getCoordinate(Dimension.VERTICAL);
	}
	
	/** 
	 * Create a new Robot looking at <code>orientation</code> with <code>energy</code> watt-second.
	 * 
	 * This method must return <code>null</code> if the given parameters are invalid (e.g. negative energy). 
	 *  
	 * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
	 */
	@Override
	public Robot createRobot(int orientation, double initialEnergy){
		if(Accu.isValidAmountOfEnergy(new Energy(initialEnergy), Robot.STANDARD_ROBOT_ENERGY_CAPACITY))
			return new Robot(null, null, new Accu(new Energy(initialEnergy), Robot.STANDARD_ROBOT_ENERGY_CAPACITY), Direction.directionFromInt(orientation));
		return null;
	}
	
	/**
	 * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void putRobot(Board board, long x, long y, Robot robot){
		try{
			board.addBoardModelAt(new Position(x, y), robot);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Return the x-coordinate of <code>robot</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public long getRobotX(Robot robot) throws IllegalStateException{
		if(robot.getBoard() == null)
			throw new IllegalStateException("The given robot is not placed on a board.");
		return robot.getCoordinate(Dimension.HORIZONTAL);
	}
	
	/**
	 * Return the y-coordinate of <code>robot</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>robot</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public long getRobotY(Robot robot) throws IllegalStateException{
		if(robot.getBoard() == null)
			throw new IllegalStateException("The given robot is not placed on a board.");
		return robot.getCoordinate(Dimension.VERTICAL);
	}
	
	/**
	 * Return the orientation (either 0, 1, 2 or 3) of <code>robot</code>. 
	 * 
	 * <p>0, 1, 2, 3 respectively represent up, right, down and left.</p>
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public int getOrientation(Robot robot){
		return robot.getDirection().getDirectionnr();
	}
	
	/**
	 * Return the current energy in watt-second of <code>robot</code>.
	 * 
	 * @note	NullpointerException could happen if the given robot the null reference.
	 */
	@Override
	public double getEnergy(Robot robot){
		try{
			return robot.getAmountOfEnergyInWS();
		}
		catch(IllegalStateException e){
			return 0;
		}
	}
	
	/**
	 * Move <code>robot</code> one step in its current direction if the robot has sufficient energy. Do not modify the state of the robot
	 * if it has insufficient energy.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void move(Robot robot){
		if(!robot.isTerminated() && robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.MOVE))){
			try{
				robot.move();
			}
			catch(IllegalArgumentException e2){
				System.err.println(e2.getMessage());
			}
		}
	}
	
	/**
	 * Turn <code>robot</code> 90 degrees in clockwise direction if the robot has sufficient energy. Do not modify the state of the robot
	 * if it has insufficient energy.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void turn(Robot robot){
		if(!robot.isTerminated() && robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.TURN)))
				robot.turnClockwise();
	}
	
	/**
	 * Make <code>robot</code> pick up <code>battery</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void pickUpBattery(Robot robot, Battery battery){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().addInventoryItem(battery);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Make <code>robot</code> use <code>battery</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void useBattery(Robot robot, Battery battery){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().useInventoryItem(battery);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * Make <code>robot</code> drop <code>battery</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void dropBattery(Robot robot, Battery battery){
		if(!robot.isTerminated()){
			robot.getInventory().removeInventoryItem(battery);
		}
	}

	/**
	 * Make <code>robot</code> pick up <code>repairKit</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void pickUpRepairKit(Robot robot, RepairKit repairKit){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().addInventoryItem(repairKit);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Make <code>robot</code> use <code>repairKit</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void useRepairKit(Robot robot, RepairKit repairKit){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().useInventoryItem(repairKit);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Make <code>robot</code> drop <code>repairKit</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void dropRepairKit(Robot robot, RepairKit repairKit){
		if(!robot.isTerminated()){
			robot.getInventory().removeInventoryItem(repairKit);
		}
	}
	
	/**
	 * Make <code>robot</code> pick up <code>surpriseBox</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void pickUpSurpriseBox(Robot robot, SurpriseBox surpriseBox){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().addInventoryItem(surpriseBox);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Make <code>robot</code> use <code>surpriseBox</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void useSurpriseBox(Robot robot, SurpriseBox surpriseBox){
		if(!robot.isTerminated()){
			try{
				robot.getInventory().useInventoryItem(surpriseBox);
			}
			catch (IllegalArgumentException e){
				System.err.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Make <code>robot</code> drop <code>surpriseBox</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void dropSurpriseBox(Robot robot, SurpriseBox surpriseBox){
		if(!robot.isTerminated()){
			robot.getInventory().removeInventoryItem(surpriseBox);
		}
	}
	
	/**
	 * Transfer all items possessed by <code>from</code> to <code>to</code>. 
	 * 
	 * @note	NullpointerException could happen if the given 'to' robot refers the null reference.
	 */
	@Override
	public void transferItems(Robot from, Robot to){
		try{
			to.inventoryTransferFrom(from);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Return whether your implementation of <code>isMinimalCostToReach</code> takes into account other robots, walls and turning (required to score 17+). The return
	 * value of this method determines the expected return value of <code>isMinimalCostToReach</code> in the test suite.
	 * 
	 * This method must return either 0 or 1.
	 */
	@Override
	public int isMinimalCostToReach17Plus(){
		return 1;
	}
	
	/**
	 * Return the minimal amount of energy required for <code>robot</code> to reach (<code>x</code>, </code>y</code>) taking into account the robot's current load and energy level. Do not take into account
	 * shooting and picking up/using/dropping batteries. 
	 * <p>
	 * The expected return value of this method depends on <code>isMinimalCostToReach17Plus</code>:
	 * <ul>
	 * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>0</code>, then <code>getMinimalCostToReach</code> will only be called if there are no obstacles in the rectangle
	 * covering <code>robot</code> and the given position. Moreover, the result of this method should not include the energy required for turning.</li>
	 * <li>If <code>isMinimalCostToReach17Plus</code> returns <code>1</code>, then <code>getMinimalCostToReach</code> must take into account obstacles (i.e. walls, other robots) and the 
	 * fact that turning consumes energy. This method must return <code>-1</code> if the given position is not reachable given the current amount of energy.</li>
	 * </ul>
	 * </p>
	 * In any case, this method must return <code>-1</code> if <code>robot</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public double getMinimalCostToReach(Robot robot, long x, long y){
		if(robot.getBoard() == null)
			return -1;
		try{
			Energy retVal  = robot.getMinimalEnergyRequiredToReachPosition(new Position(x, y));
			if(retVal.compareTo(robot.getEnergy()) <= 0)
				return retVal.getEnergyAmount();
			else
				return -1;
		}
		catch(IllegalArgumentException e){
			return -1;
		}
	}
	
	/**
	 * Return whether your implementation of <code>moveNextTo</code> takes into account other robots, walls and the fact that turning consumes energy (required to score 18+). The return
	 * value of this method determines the expected effect of <code>moveNextTo</code> in the test suite.
	 * 
	 * This method must return either 0 or 1.
	 */
	@Override
	public int isMoveNextTo18Plus(){
		return 1;
	}
	
	/**
	 * Move <code>robot</code> as close as possible (expressed as the manhattan distance) to <code>other</code> given their current energy and load. If multiple optimal (in distance) solutions
	 * exist, select the solution that requires the least amount of total energy. Both robots can move and turn to end up closer to each other. Do not take into account shooting and picking up/using/dropping
	 * batteries.  
	 * <p>
	 * The expected return value of this method depends on <code>isMoveNextTo18Plus</code>:
	 * <ul>
	 * <li>If <code>isMoveNextTo18Plus</code> returns <code>0</code>, then <code>moveNextTo</code> will only be called if there are no obstacles in the rectangle
	 * covering <code>robot</code> and <code>other</code>. Moreover, your implementation must be optimal only in the number of moves (i.e. ignore the fact that turning consumes energy).</li>
	 * <li>If <code>isMoveNextTo18Plus</code> returns <code>1</code>, then <code>moveNextTo</code> must take into account obstacles (i.e. walls, other robots) and the 
	 * fact that turning consumes energy.</li>
	 * </ul>
	 * </p>
	 * Do not change the state if <code>robot</code> and <code>other</code> are not located on the same board.
	 * 
	 * @note	NullpointerException could happen if the given refers the null reference.
	 */
	@Override
	public void moveNextTo(Robot robot, Robot other){
		try{
			robot.moveNextTo(other);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Make <code>robot</code> shoot in the orientation it is currently facing (if possible).
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void shoot(Robot robot){
		if(!robot.isTerminated() && robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
			robot.shoot();
	}
	
	/**
	 * Create a new wall.
	 */
	@Override
	public Wall createWall(){
		return new Wall();
	}
	
	/**
	 * Put <code>robot</code> at position (<code>x</code>, <code>y</code>) on <code>board</code> (if possible).
	 * 
	 * @note	NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public void putWall(Board board, long x, long y, Wall wall){
		try{
			board.addBoardModelAt(new Position(x, y), wall);
		}
		catch(IllegalArgumentException e){
			System.err.println(e.getMessage());
		}	
	}
	
	/**
	 * Return the x-coordinate of <code>wall</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given wall refers the null reference.
	 */
	@Override
	public long getWallX(Wall wall) throws IllegalStateException{
		if(wall.getBoard() == null)
			throw new IllegalStateException("The given wall is not placed on a board.");
		return wall.getCoordinate(Dimension.HORIZONTAL);
	}
	
	/**
	 * Return the y-coordinate of <code>wall</code>.
	 * 
	 * This method must throw <code>IllegalStateException</code> if <code>wall</code> is not placed on a board.
	 * 
	 * @note	NullpointerException could happen if the given wall refers the null reference.
	 */
	@Override
	public long getWallY(Wall wall) throws IllegalStateException{
		if(wall.getBoard() == null)
			throw new IllegalStateException("The given wall is not placed on a board.");
		return wall.getCoordinate(Dimension.VERTICAL);
	}
	
	/**
	 * Return a set containing all robots on <code>board</code>.
	 * 
	 * @note	NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public Set<Robot> getRobots(Board board){
		return board.getAllBoardModelsClass(Robot.class);
	}
	
	/**
	 * Return a set containing all batteries on <code>board</code>.
	 * 
	 * @note	NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public Set<Battery> getBatteries(Board board){
		return board.getAllBoardModelsClass(Battery.class);
	}
	
	/**
	 * Return a set containing all repair kits on <code>board</code>.
	 * 
	 * @note  NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public Set<RepairKit> getRepairKits(Board board){
		return board.getAllBoardModelsClass(RepairKit.class);
	}
	
	/**
	 * Return a set containing all surprise boxes on <code>board</code>.
	 * 
	 * @note  NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public Set<SurpriseBox> getSurpriseBoxes(Board board){
		return board.getAllBoardModelsClass(SurpriseBox.class);
	}
	
	/**
	 * Return a set containing all walls on <code>board</code>.
	 * 
	 * @note	NullpointerException could happen if the given board refers the null reference.
	 */
	@Override
	public Set<Wall> getWalls(Board board){
		return board.getAllBoardModelsClass(Wall.class);
	}

	/**
	 * Load the program stored at <code>path</code> and assign it to <code>robot</code>.
	 * 
	 * Return <code>0</code> if the operation completed successfully; otherwise, return a negative number.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public int loadProgramFromFile(Robot robot, String path){
		try {
			robot.setProgramFromFile(path);
		} catch (IllegalArgumentException e) {
			return -1;
		} catch (IOException e) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Save the program of <code>robot</code> in a file at <code>path</code>.
	 * 
	 * Return <code>0</code> if the operation completed successfully; otherwise, return a negative number.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public int saveProgramToFile(Robot robot, String path){
		try {
			robot.writeProgramToFile(path);
		} catch (IOException e) {
			return -1;
		}
		catch (NullPointerException e) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Pretty print the program of <code>robot</code> via <code>writer</code>.
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void prettyPrintProgram(Robot robot, Writer writer){
		try {
			if(robot.getProgram() != null)
				writer.write(robot.getProgram().toString());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * Execute <code>n</code> basic steps in the program of <code>robot</code>.
	 * 
	 * <p>For example, consider the program (seq (move) (shoot)). The first step performs a move command,
	 * the second step performs a shoot command and all subsequent steps have no effect.</p> 
	 * 
	 * <p>Note that if n equals 1, then only the move command is executed. The next call to stepn then starts
	 * with the shoot command.</p>
	 * 
	 * @note	NullpointerException could happen if the given robot refers the null reference.
	 */
	@Override
	public void stepn(Robot robot, int n){
		for (int i = 0; i < n; i++){
			robot.executeProgramStep();
		}
	}
}
