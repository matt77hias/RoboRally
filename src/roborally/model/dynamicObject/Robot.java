package roborally.model.dynamicObject;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roborally.astar.Astar;
import roborally.board.*;
import roborally.model.*;
import roborally.model.inventory.*;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.staticObject.ActionTrigger;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;
import roborally.model.energy.*;
import roborally.program.Program;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of robots involving an accu, direction, energy cost constants
 * and some facilities for turning, moving, shooting.
 * 
 * @invar	The inventory of every robot must be a valid inventory.
 * 			| hasValidInventory()
 * @invar	This robot has a valid accu
 * 			| canHaveAsAccu(getAccu())
 * @invar	This robot has a valid direction
 * 			| Direction.isValidDirection(getDirection())
 * @invar	The energy costs of every robot must be
 * 			proper energy costs.
 * 			| hasProperEnergyCostCores()
 * @invar	The program of every robot must be written
 * 			in correct language (syntax).
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Robot extends BoardModel implements EnergyModel, InventoryUser{
	
	/**
	 * Variable storing the standard amount of energy for robots.
	 */
	public final static Energy STANDARD_ROBOT_AMOUNT_OF_ENERGY = new Energy(5000);
	
	/**
	 * Variable storing the standard energy capacity for robots.
	 */
	public final static Energy STANDARD_ROBOT_ENERGY_CAPACITY = new Energy(20000);
	
	/**
	 * Variable storing the standard direction for robots.
	 */
	public final static Direction STANDARD_ROBOT_DIRECTION = Direction.DOWN;
	
	/**
	 * Initializes this new robot with standard values.
	 * 
	 * @effect	This new robot is situated on no board
	 * 			with no position, has an accu with an amount energy
	 * 			equal to the standard amount of energy and an energy capacity
	 * 			limit equal to the standard energy capacity for robots,
	 * 			and the direction refers the standard direction for robots.
	 * 			| this(null, null, new Accu(STANDARD_ROBOT_AMOUNT_OF_ENERGY, STANDARD_ROBOT_ENERGY_CAPACITY), STANDARD_ROBOT_DIRECTION)
	 */
	@Raw
	public Robot()
			throws IllegalArgumentException{
		this(null, null, new Accu(STANDARD_ROBOT_AMOUNT_OF_ENERGY, STANDARD_ROBOT_ENERGY_CAPACITY), STANDARD_ROBOT_DIRECTION);
	}
	
	/**
	 * Initializes this new robot with the given accu.
	 * 
	 * @param	accu
	 * 			The accu for this robot.
	 * @effect	This new robot is situated on no board
	 * 			with no position, has the given accu as its accu
	 * 			and the direction refers the standard direction for robots.
	 * 			| this(null, null, Accu, STANDARD_ROBOT_DIRECTION)
	 */
	@Raw
	public Robot(Accu accu)
			throws IllegalArgumentException{
		this(null, null, accu, STANDARD_ROBOT_DIRECTION);
	}
	
	/**
	 * Initializes this new robot with board and position.
	 * 
	 * @effect	This new robot is situated on the given position
	 * 			on the given board, has an accu with an amount energy
	 * 			equal to the standard amount of energy and an energy capacity
	 * 			limit equal to the standard energy capacity for robots,
	 * 			and the direction refers the standard direction for robots.
	 * 			| this(board, position, new Accu(STANDARD_ROBOT_AMOUNT_OF_ENERGY, STANDARD_ROBOT_ENERGY_CAPACITY), STANDARD_ROBOT_DIRECTION)
	 */
	@Raw
	public Robot(Board board, Position position)
			throws IllegalArgumentException{
		this(board, position, new Accu(STANDARD_ROBOT_AMOUNT_OF_ENERGY, STANDARD_ROBOT_ENERGY_CAPACITY), STANDARD_ROBOT_DIRECTION);
	}
	
	/**
	 * Initializes this new robot with given board, position, accu and orientation.
	 * 
	 * @param 	board
	 * 			The board where this new robot is situated on.
	 * @param 	position
	 * 			The position of the position of this new robot on the given board.
	 * @param	accu
	 * 			The accu for this robot.
	 * @param	direction
	 * 			The initial direction of this new robot that has to be set.
	 * @effect	This new robot is situated on the given board
	 * 			on the given position.
	 * 			| super(board, position)
	 * @effect	Sets the new direction of this robot to the given direction.
	 * 			| setDirection(direction)
	 * @post	The new accu of this robot is equal to the given accu.
	 * 			| new.getAccu() == accu
	 * @effect	The standard energy costs for robots are set
	 * 			as the energy costs for this new robot.
	 * 			| presetEnergyCostCores()
	 * @throws	IllegalArgumentException
	 * 			The given accu is invalid.
	 * 			| !canHaveAsAccu(accu)
	 */
	@Raw
	public Robot(Board board, Position position, Accu accu, Direction direction) 
				throws IllegalArgumentException{
		super(board, position);
		setDirection(direction);
		setAccu(accu);
		presetEnergyCostCores();
	}
	
	/**
	 * Terminates this robot, its accu, inventory and all its inventory items.
	 * 
	 * @post	This robot becomes terminated.
	 * 			| new.isTerminated() == true
	 */
	@Override
	public void terminate(){
		if(!isTerminated()){
			// Order is crucial.
			super.terminate();
			getAccu().detachFromEnergyModel(this);
			//NullPointerException: 	avoided due to invariant
			getAccu().terminate();
			//NullPointerException: 	avoided due to invariant
			getInventory().terminate();
			
			resetInventory();
			//IllegalArgumentException: post condition of super.terminate() states that isTerminated() will be true so canHaveAsAccu(null) is true
			setAccu(null);
		}
	}
	
	/**
	 * Checks if this robot could share its position with the given board model.
	 * 
	 * @param 	request
	 * 			The board model that has to be checked.
	 * @return	True if and only if this robot could share its position
	 * 		   	with the given board model.
	 * 			A robot could only share a position with an inventory model
	 * 			or an action trigger.
	 * 			| result == InventoryModel.class.isInstance(request)
	 * 			|			|| ActionTrigger.class.isInstance(request)
	 * @note	This means this method doesn't include a board, position
	 * 			or termination check.
	 */
	@Override @Raw
	public boolean canSharePositionWith(BoardModel request){
		if(InventoryModel.class.isInstance(request))
			return true;
		if(ActionTrigger.class.isInstance(request))
			return true;
		return false;
	}
	
	/**
	 * Checks if this robot has a valid inventory.
	 * 
	 * @return	If this robot is terminated,
	 * 			its inventory must refer the null reference.
	 * 			| if(isTerminated())
	 * 			| 	then result == (getInventory() == null)
	 * @return	If this robot is not terminated,
	 * 			its inventory may not refer the null reference.
	 * 			| if(!isTerminated())
	 * 			| 	then result == (getInventory() != null)
	 */
	@Override @Raw
	public boolean hasValidInventory(){
		if(isTerminated())
			return getInventory() == null;
		else
			return getInventory() != null;
	}
	
	/**
	 * Transfers all the inventory model of the given 'transfer' robot
	 * to this 'collector' robot. 
	 * 
	 * @param 	robot
	 * 			The transfer robot.
	 * @throws	IllegalArgumentException
	 * 			The given robot may not refer the null reference.
	 * 			The given robot may not be terminated.
	 * 			This robot may not be terminated.
	 * 			The given robot may not refer this robot.
	 * 			| (robot == null || robot.isTerminated() || isTerminated() || this == robot)
	 * @throws	IllegalArgumentException
	 * 			If the position of this robot refers the null reference,
	 * 			the position of the given robot must also refer the null reference.
	 * 			If the board of this robot doesn't refer the null reference,
	 * 			the given robot must be located next to the position of this robot.
	 * 			| (getPosition() == null && robot.getPosition() != null)
	 * 			|	|| (getBoard() != null && !getBoard().isSituatedNextTo(getPosition(), robot))
	 */
	public void inventoryTransferFrom(Robot robot) throws IllegalArgumentException{
		if(robot == null || robot.isTerminated() || isTerminated() || this == robot)
			throw new IllegalArgumentException("Transfer could not be executed.");
		if((getPosition() == null && robot.getPosition() != null) || (getBoard() != null && !getBoard().isSituatedNextTo(getPosition(), robot)))
			throw new IllegalArgumentException("Transfer could not be executed, because both robots are not located next to each other.");
		getInventory().addInventoryItemsFrom(robot.getInventory());
	}
	
	/**
	 * Returns the inventory of this robot.
	 */
	@Basic @Raw @Override
	public Inventory getInventory(){
		return inventory;
	}
	
	/**
	 * Sets the inventory of this robot to the null reference,
	 * if this robot is terminated.
	 * 
	 * @post	Sets the inventory of this robot to the null reference,
	 * 			if this robot is terminated.
	 * 			| if(this.isTerminated()) then
	 * 			| new.getInventory() == null
	 */
	@Raw
	private void resetInventory(){
		if(this.isTerminated())
			this.inventory = null;
	}
	
	/**
	 * Variable storing the inventory of this robot.
	 */
	private Inventory inventory = new Inventory(this);
	
	/**
	 * Checks if the given accu is valid for this robot.
	 * 
	 * @param 	accu
	 * 			The accu that has to be checked.
	 * @return	If this robot is not terminated,
	 * 			true if and only if the given accu doesn't refer
	 * 			the null reference, is not terminated and
	 * 			not owned or the given accu refers the robots accu
	 * 			instead of this last condition.
	 * 			| if(!this.isTerminated()) then
	 * 			| 	then result == (accu != null) && (!accu.isTerminated())
	 * 			|				&& ((getAccu() == accu) || (!accu.hasOwner()))
	 * @return	If the robot is terminated,
	 * 			true if and only if the given accu refers the null reference.
	 * 			| if(this.isTerminated()) then
	 * 			| 	result == (accu == null)
	 */
	@Override @Raw
	public boolean canHaveAsAccu(Accu accu){
		if(this.isTerminated())
			return accu == null;
		else
			return (accu != null) && (!accu.isTerminated()) && ((getAccu() == accu) || (!accu.hasOwner()));
	}
	
	/**
	 * Returns whether this robot's accu can have the given amount of energy as its energy.
	 * 
	 * @param 	energy
	 * 			The energy to check.
	 * @return	True if and only if this robot is not terminated
	 * 			and if its accu can have the given energy as its amount of energy.
	 * 			| result == !isTerminated() && getAccu().canHaveAsAmountOfEnergy(energy)
	 */
	@Raw
	public boolean canHaveAsEnergy(Energy energy){
		//NullpointerException: avoided due to invariant: if(!isTerminated()) then getAccu() != null
		return !isTerminated() && getAccu().canHaveAsAmountOfEnergy(energy);
	}
	
	/**
	 * Returns the accu of this robot.
	 */
	@Basic @Raw @Override
	public Accu getAccu(){
		return accu;
	}
	
	/**
	 * Sets the accu of this robot to the given accu.
	 * 
	 * @param 	accu
	 * 			The new accu for this robot.
	 * @post	The new accu of this robot is the given accu.	
	 * 			| new.getAccu() == accu
	 * @post	The given accu becomes owned,
	 * 			if it doesn't refer the null reference.
	 * 			| if(accu != null) then
	 * 			| new.accu.hasOwner() == true
	 * @throws	IllegalArgumentException
	 * 			The given accu is invalid.
	 * 			| !canHaveAsAccu(accu)
	 */
	@Raw @Model
	private void setAccu(Accu accu) throws IllegalArgumentException{
		if(!canHaveAsAccu(accu))
			throw new IllegalArgumentException("The given accu is invalid.");
		this.accu = accu;
		if(accu != null)
			accu.own();
	}
	
	/**
	 * Returns the amount of energy contained in this robot's accu expressed in WS.
	 * 
	 * @return	Returns the amount of energy stored in this robot's accu
	 * 			expressed in WS.
	 * 			| result == getAccu().getAmountOfEnergy().toEnergyUnit(EnergyUnit.WS).getEnergyAmount()
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 */
	public double getAmountOfEnergyInWS()
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if !isTerminated() then getAccu() != null
		return getAccu().getAmountOfEnergy().toEnergyUnit(EnergyUnit.WS).getEnergyAmount();
	}
	
	/**
	 * Returns the energy contained in this robot.
	 * 
	 * @return	Returns the energy stored in this robot's accu.
	 * 			| result == getAccu().getAmountOfEnergy()
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 */
	public Energy getEnergy()
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if(!isTerminated()) then getAccu() != null
		return getAccu().getAmountOfEnergy();
	}
	
	/**
	 * Discharges the given amount of energy from this robot's accu.
	 * 
	 * @pre		The discharge amount must be a valid energy.
	 * 			| Energy.isValidEnergy(dischargeAmount)
	 * @pre		The discharge amount's energy amount has to be zero or positive.
	 * 			| dischargeAmount.getEnergyAmount() >= 0
	 * @pre 	The given discharge amount must be less than or equal to the amount of
	 * 			energy stored in this accu.
	 * 			| canHaveAsEnergy(getEnergy().subtract(energy))
	 * @param 	energy
	 * 			The amount of energy to discharge.
	 * @effect	Has the same effect as discharging this robot's accu with the given amount.
	 * 			| getAccu().dischargeAmountOfEnergy(energy)
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 */
	public void dischargeAmountOfEnergy(Energy energy)
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if(!isTerminated()) then getAccu() != null
		getAccu().dischargeAmountOfEnergy(energy);
	}
	
	/**
	 * Returns the percentage of energy stored in this robot's accu
	 * according to its energy capacity limit.
	 * 
	 * @return 	The percentage of energy stored in this robot's accu
	 * 			according its energy capacity limit.
	 * 		   	| result == getAccu().getEnergyStoragePercentage()
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 * @note	The result is rounded at four significant digits; two before
	 * 			and two after the decimal point.
	 */
	public double getEnergyStoragePercentage()
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if(!isTerminated()) then getAccu() != null
		return getAccu().getEnergyStoragePercentage();
	}
	
	/**
	 * Recharges this robot's accu with the given amount of energy.
	 * 
	 * @pre		The recharge amount must be a valid energy.
	 * 			| Energy.isValidEnergy(rechargeAmount)
	 * @pre		The recharge amount's energy amount has to be zero or positive.
	 * 			| energy.getEnergyAmount() >= 0
	 * @pre 	The sum of the amount already stored in this robot and the given recharge amount
	 * 			must be a possible amount of energy for this accu.
	 * 			| canHaveAsEnergy(getEnergy().add(energy))
	 * @param 	energy
	 * 			The amount of energy to recharge.
	 * @effect	Has the same effect as recharging this robot's accu with the given amount.
	 * 			| getAccu().rechargeAmountOfEnergy(energy)
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 */
	public void rechargeAmountOfEnergy(Energy energy)
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if !isTerminated() then getAccu() != null
		getAccu().rechargeAmountOfEnergy(energy);
	}
	
	/**
	 * Returns the energy capacity limit from this robot's accu.
	 * 
	 * @return	Returns the energy capacity limit from this robot's accu.
	 * 			| getAccu().getEnergyCapacityLimit()
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| isTerminated()
	 */
	public Energy getEnergyCapacityLimit()
			throws IllegalStateException{
		if (isTerminated())
			throw new IllegalStateException("Robot is not effective");
		//NullpointerException: avoided due to invariant: if(!isTerminated()) then getAccu() != null
		return getAccu().getEnergyCapacityLimit();
	}
	
	/**
	 * The accu of this robot.
	 */
	private Accu accu;
	
	/**
	 * The standard energy cost core required to turn 90 degrees
	 * clockwise or counterclockwise for every robot.
	 */
	public static final Energy STANDARD_ENERGY_TURN_COST_CORE = new Energy(100);
	
	/**
	 * The standard energy cost core required to move one step in facing direction
	 * for every robot.
	 */
	public static final Energy STANDARD_ENERGY_MOVE_COST_CORE = new Energy(500);
	
	/**
	 * The standard energy cost core required to shoot once in facing direction
	 * for every robot.
	 */
	public static final Energy STANDARD_ENERGY_SHOOT_COST_CORE = new Energy(1000);
	
	/**
	 * The energy cost surplus required to move one step
	 * in facing direction for every robot based on the weight of
	 * its inventory and the move weight surplus factor.
	 */
	private static final Energy ENERGY_MOVE_WEIGHT_SURPLUS_COST = new Energy(50);
	
	/**
	 * The result of the total weight of a robot's inventory divided
	 * by this move weight surplus factor, multiplied by
	 * the energy move weight surplus cost is the additional cost
	 * that a robot has to pay for a single move in facing direction.
	 */
	private static final Weight MOVE_WEIGHT_SURPLUS_FACTOR = new Weight(1,WeightUnit.KG);
	
	/**
	 * Checks if this robot has valid energy cost cores.
	 * 
	 * @return	True if and only if every cost and corresponding
	 * 			energy cost core of this robot's energy cost cores,
	 * 			is respectively a valid cost and a valid energy.
	 * 			| for each cost in getAllEnergyCostCores().keySet() :
	 * 			|	Cost.isValidCost(cost) &&
	 * 			|	Energy.isValidEnergy(getAllEnergyCostCores().get(cost))
	 */
	@Raw
	public boolean hasProperEnergyCostCores(){
		for(Cost cost : energyCostCores.keySet()){
			if(!Cost.isValidCost(cost))
				return false;
			if(!Energy.isValidEnergy(energyCostCores.get(cost)))
				return false;
		}
		return true;
	}
	
	/**
	 * Presets the energy cost cores for this robot.
	 * 
	 * @post	This robot's energy move cost core is set to the standard energy move cost core for robots.
	 * 			This robot's energy turn cost core is set to the standard energy turn cost core for robots.
	 * 			This robot's energy shoot cost core is set to the standard energy shoot cost core for robots.
	 * 			| getAllEnergyCostCores().get(Cost.MOVE).equals(STANDARD_ENERGY_MOVE_COST_CORE)
	 * 			| getAllEnergyCostCores().get(Cost.TURN).equals(STANDARD_ENERGY_TURN_COST_CORE)
	 * 			| getAllEnergyCostCores().get(Cost.SHOOT).equals(STANDARD_ENERGY_SHOOT_COST_CORE)
	 */
	@Model
	private void presetEnergyCostCores(){
		energyCostCores.put(Cost.MOVE,STANDARD_ENERGY_MOVE_COST_CORE);
		energyCostCores.put(Cost.TURN,STANDARD_ENERGY_TURN_COST_CORE);
		energyCostCores.put(Cost.SHOOT,STANDARD_ENERGY_SHOOT_COST_CORE);
	}
	
	/**
	 * Checks if this robot has the given cost as one of its energy costs.
	 * 
	 * @param 	cost
	 * 			The cost that has to be checked.
	 * @return	True if and only if the given cost doensn't refer the null
	 * 			reference and if this robot's energy cost cores has an energy cost
	 * 			core corresponding the given cost.
	 * 			| result == (cost != null) && (getAllEnergyCostCores().containsKey(cost))
	 */
	@Raw
	public boolean hasAsEnergyCost(Cost cost){
		return (cost != null) && (energyCostCores.containsKey(cost));
	}
	
	/**
	 * Returns the energy cost of this robot corresponding to the given cost.
	 * 
	 * @param	cost
	 * 			The cost for which the energy cost has to be returned.
	 * @return	If this robot doesn't contain an energy cost for the given
	 * 			cost, a zero Watt energy cost is returned.
	 * 			| if(!Cost.isValidCost(cost))
	 * 			| 	then result.equals(Energy.WS_0)
	 * @return	If this robot contains an energy cost for the given
	 * 			cost and if the given cost refers the move cost,
	 * 			than the result is equal to the energy cost core for
	 * 			moving for this robot added with the result of the energy
	 * 			weight surplus cost multiplied by the amount of times
	 * 			that the move weight surplus factor could fit into the total
	 * 			weight of the inventory of this robot
	 * 			(rounded to the first smaller or equal integer).
	 * 			| if(Cost.isValidCost(cost) && Cost.MOVE == cost)
	 * 			| let
	 * 			|	total = getInventory().getTotalWeightOfInventoryItems(MOVE_WEIGHT_SURPLUS_FACTOR.getWeightUnit()).getWeightAmount()
	 * 			|	factor_move = (total/MOVE_WEIGHT_SURPLUS_FACTOR.getWeightAmount())
	 *			|	surplus = ENERGY_MOVE_WEIGHT_SURPLUS_COST.multiply(factor_move)
	 *			| in:
	 * 			|	 then result == getAllEnergyCostCores.get(cost).add(surplus)
	 * @return	If this robot contains an energy cost for the given
	 * 			cost, this energy cost is returned.
	 * 			| if(Cost.isValidCost(cost))
	 * 			|	 then result == getAllEnergyCostCores().get(cost)
	 * @throws	IllegalStateException
	 * 			This robot is terminated.
	 * 			| isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The given cost is invalid.
	 * 			| !Cost.isValidCost(cost)
	 * @note	The result is the energy cost. This result is not always equal
	 * 			to the energy cost core, because the energy cost core is independent
	 * 			of all aspects related to a robot.
	 * 			For example: The energy move cost of a robot depends first on the
	 * 			corresponding energy move cost core, but also depends on the total
	 * 			weight of that robot's inventory. The energy move cost core on the other
	 * 			hand depends not on the weight of that robot's inventory or on another
	 * 			characteristic of that robot.
	 */
	public Energy getEnergyCostOf(Cost cost)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This robot is not effective");
		if(!Cost.isValidCost(cost))
			throw new IllegalArgumentException("The given cost is invalid.");
		if(!hasAsEnergyCost(cost))
			return Energy.WS_0;
		
		double total = getInventory().getTotalWeightOfInventoryItems(MOVE_WEIGHT_SURPLUS_FACTOR.getWeightUnit()).getWeightAmount();
		
		if(cost == Cost.MOVE){
			double factor_move = (total/MOVE_WEIGHT_SURPLUS_FACTOR.getWeightAmount());
			Energy surplus = ENERGY_MOVE_WEIGHT_SURPLUS_COST.multiply(factor_move);
			return energyCostCores.get(cost).add(surplus);
		}
		return energyCostCores.get(cost);
	}
	
	/**
	 * Sets the given energy cost core of this robot
	 * to the given energy value.
	
	 * @pre		The given energy, its amount of energy could
	 * 			is positive or zero.
	 * 			| energy.compareTo(Energy.WS_0)>=0
	 * @param	energy
	 * 			The new energy cost core corresponding
	 * 			to the given cost for this robot.
	 * @param	cost
	 * 			The corresponding cost.
	 * @throws	IllegalStateException
	 * 			This robot is terminated.
	 * 			| isTerminated()
	 * @throws	IllegalArgumentException
	 * 			At least one of the given arguments is invalid.
	 * 			| (!Cost.isValidCost(cost) || !Energy.isValidEnergy(energy))
	 */
	public void setEnergyCostCore(Energy energy, Cost cost)
			throws IllegalStateException, IllegalArgumentException{
		if (isTerminated())
			throw new IllegalStateException("This robot is not effective");
		if(!Cost.isValidCost(cost) || !Energy.isValidEnergy(energy))
			throw new IllegalArgumentException("At least one of the given arguments is invalid.");
		assert(energy.compareTo(Energy.WS_0)>=0);
		energyCostCores.put(cost, energy);
	}
	
	/**
	 * Returns all the energy cost cores of this robot.
	 * An energy cost core is the skeleton of the effective corresponding
	 * energy cost. This core is independent of all aspects related to a robot.
	 */
	@Basic
	public Map<Cost, Energy> getAllEnergyCostCores(){
		return Collections.unmodifiableMap(energyCostCores);
	}
	
	/**
	 * Map collection containing all the energy cost cores of this robot,
	 * with the cost as key and the corresponding energy as value.
	 * An energy cost core is the skeleton of the effective corresponding
	 * energy cost. This core is independent of all aspects related to a robot.
	 */
	private Map<Cost, Energy> energyCostCores = new HashMap<Cost, Energy>();
	
	/**
	 * Shoot once in facing direction.
	 * This shot includes the required energy payments.
	 * 
	 * @pre		The robot must be able to pay the required energy cost for shooting.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.SHOOT))
	 * @effect	This robot is discharged with the energy cost required for shooting
	 * 			and a random board model if possible is eliminated from its board
	 * 			This target model must be situated the close as possible in facing direction
	 * 			of this robot.
	 * 			| getAccu().dischargeAmountOfEnergy(getEnergyCostOf(Cost.SHOOT))
	 * 			| && let
	 * 			|		target = getBoard().getRandomTarget(getPosition(), getDirection())
	 * 			|	 in:
	 * 			|		if(target != null) then target.hit()
	 */
	public void shoot(){
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.SHOOT)));
		// IllegalStateException: avoided due to precondition
		// precondition 1: 	getEnergyCostOf(Cost.SHOOT) is a valid variable for this precondition
		// precondition 2: 	the precondition of this method states that canHaveAsEnergy(getEnergy().subtract(ENERGY_SHOOT_COST))
		dischargeAmountOfEnergy(getEnergyCostOf(Cost.SHOOT));
		// NullPointerException: 		avoided due to precondition
		// IllegalArgumentException: 	direction is always valid due to invariant
		// IllegalArgumentException: 	position is always valid due to invariants of Board and BoardModel
		BoardModel target = getBoard().getRandomTarget(getPosition(), getDirection());
		if(target != null)
			target.hit();
	}
	
	/**
	 * Returns the direction of this robot.
	 */
	@Basic @Raw
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * Sets the direction of this robot to the given direction.
	 * 
	 * @param 	direction
	 * 			The direction that has to be set.
	 * @post	The new direction of this robot is equal
	 * 			to the given direction. If the given direction
	 * 			is invalid, it is set to the standard direction for robots.
	 * 			| if(Direction.isValidDirection(direction))
	 * 			|	then direction = STANDARD_ROBOT_DIRECTION
	 * 			| new.getDirection() == direction
	 */
	@Raw @Model
	private void setDirection(Direction direction){
		if(!Direction.isValidDirection(direction))
			direction = STANDARD_ROBOT_DIRECTION;
		this.direction = direction;
	}
	
	/**
	 * Turns this robot 90 degrees in a clockwise direction.
	 * This rotation includes the required energy payments.
	 * 
	 * @pre		The robot must be able to pay the energy cost for turning.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN))
	 * @effect	The robot's accu is discharged with the energy turn cost
	 * 			and its direction is turned one clockwise turn.
	 * 			| getAccu().dischargeAmountOfEnergy(getEnergyCostOf(Cost.TURN)) &&
	 * 			| setDirection(Direction.turnDirectionClockwise(getDirection()))
	 */
	public void turnClockwise(){
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN)));
		//IllegalStateException: 	avoided due to precondition
		dischargeAmountOfEnergy(getEnergyCostOf(Cost.TURN));
		//IllegalArgumentException: avoided due to invariant
		setDirection(Direction.turnDirectionClockwise(getDirection()));
	}
	
	/**
	 * Turns this robot 90 degrees in a counterclockwise direction.
	 * This rotation includes the required energy payments.
	 * 
	 * @pre		The robot must be able to pay the energy cost for turning.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN))
	 * @effect	The robot's accu is discharged with the energy turn cost
	 * 			and its direction is turned one counterclockwise turn.
	 * 			| getAccu().dischargeAmountOfEnergy(getEnergyCostOf(Cost.TURN)) &&
	 * 			| setDirection(Direction.turnDirectionCounterClockwise(getDirection()))
	 */
	public void turnCounterClockwise(){
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN)));
		//IllegalStateException: 	avoided due to precondition
		dischargeAmountOfEnergy(getEnergyCostOf(Cost.TURN));
		//IllegalArgumentException: avoided due to invariant
		setDirection(Direction.turnDirectionCounterClockwise(getDirection()));
	}
	
	/**
	 * Turns this robot efficiently to the given direction.
	 * This means turning by the least possible number of turns required.
	 * 
	 * @pre		The robot must be able to perform this energy cost method.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN).multiply(Direction
	 * 			|	.amountOfEfficientTurnsToDirection(getDirection(), direction)))	
	 * @param	direction
	 * 			The direction that has to be reached by this robot.
	 * @effect	According to the least amount of turns required for
	 * 			this robot to reach the given direction, this robot
	 * 			turns clockwise or counterclockwise to the given direction.
	 * 			| if(Direction.amountOfTurnsToDirection(getDirection(), direction) == 1)
	 * 			| 	then turnClockwise()
	 * 			| if(Direction.amountOfTurnsToDirection(getDirection(), direction) == 2)
	 * 			| 	then turnClockwise() && turnClockwise()
	 * 			|	[SEQUENTIAL]
	 * 			| if(Direction.amountOfTurnsToDirection(getDirection(), direction) == 3)
	 * 			| 	then turnCounterClockwise()
	 */
	public void turnToDirection(Direction direction){
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.TURN).multiply(Direction.amountOfEfficientTurnsToDirection(getDirection(), direction))));
		switch (Direction.amountOfTurnsToDirection(getDirection(), direction)){
			case 1:
				// precondition: robot can do at least one turn as by this method's precondition
				turnClockwise();
				break;
			case 2:
				// precondition: robot can do at least two turns as by this method's precondition
				turnClockwise();
				turnClockwise();
				break;
			case 3:
				// precondition: robot can do at least one turn as by this method's precondition
				turnCounterClockwise();
			break;
		}
	}
	
	/**
	 * The direction of this robot.
	 */
	private Direction direction;
	
	/**
	 * Checks whether this robot is in a valid state
	 * and is able to pay the given energy cost
	 * in order to move, turn or shoot.
	 * 
	 * @param 	energyCost
	 * 			The energy cost of the action that has to be performed by this robot.
	 * @return	If the given energy cost is invalid, the result is always false.
	 * 			| if(!Energy.isValidEnergy(energyCost)) then
	 * 			| result == false
	 * @return	Returns false if this robot is terminated.
	 * 			| if(isTerminated()) then
	 * 			| result == false
	 * @return	Returns false if this robot is not located on any board.
	 * 			| if (getBoard() == null) then
	 * 			| result == false
	 * @return 	In all other cases, returns true if this robot
	 * 			has the energy to pay the given energy cost.
	 * 			| result == (canHaveAsEnergy(getEnergy().subtract(energyCost)))
	 */
	public boolean canDoEnergyCostMethod(Energy energyCost){
		if(!Energy.isValidEnergy(energyCost))
			return false;
		if(isTerminated())
			return false;
		if(getBoard() == null)
			return false;
		// IllegalStateException (getEnergy): 		avoided because method has already returned false if robot is terminated
		// IllegalArgumentException (subtract):		avoided because method has already returned false if energyCost is invalid
		return (canHaveAsEnergy(getEnergy().subtract(energyCost)));
	}
	
	/**
	 *  Returns the first direction that has to be turned to by this robot
	 *  in order to move efficiently to the given position.
	 *  
	 *  @param 	position
	 *  		The position for the calculation
	 *  @return	Let the following variables in all return clauses.
	 *  		| let
	 *  		| 	diffX = position[0] - getCoordinate(1)
	 *			| 	diffY = position[1] - getCoordinate(2)
	 *  		| in:
	 *  @return			
	 *  		| if (diffX == 0 && diffY == 0)
	 *  		| then result == getOrientationValue()
	 *  
	 *  @return
	 *  		| if (diffX == 0) then
	 *  		| if (diffY > 0)
	 *  		| 	then result == 0
	 *  		| else result == 2
	 *  @return
	 *  		| if(diffY == 0) then
	 *  		| if(diffX > 0)
	 *  		| 	then result == 1
	 *  		| else result == 3
	 *  @return 
	 *  		| if(diffX > 0 && diffY < 0) then
	 *  		| if(getOrientationValue() == 0 || getOrientationValue() == 3)
	 *  		| 	then result == 0
	 *  		| else result == 1
	 *  @return
	 *  		| if(diffX < 0 && diffY < 0) then
	 *  		| if(getOrientationValue() == 0 || getOrientationValue() == 1)
	 *  		| 	then result == 0
	 *  		| else result == 3
	 *  @return
	 *  		| if(diffX < 0 && diffY > 0) then
	 *  		| if(getOrientationValue() == 0 || getOrientationValue() == 3)
	 *  		| 	then result == 3
	 *  		| else result == 2
	 *  @return
	 *  		| if(diffX > 0 && diffY > 0) then
	 *  		| if(getOrientationValue() == 0 || getOrientationValue() == 1)
	 *  		| 	then result == 1
	 *  		| else result == 2
	 *  @throws	IllegalArgumentException
	 *  		The given position refers the null reference.
	 *  		| position == null
	 */
	@Model
	private Direction firstDirectionToReachPosition(Position position)
			throws IllegalArgumentException{
		if(position == null)
			throw new IllegalArgumentException("The given position is not valid.");
		long diffX = position.getCoordinate(Dimension.dimensionFromInt(1)) - getCoordinate(Dimension.dimensionFromInt(1));
		long diffY = position.getCoordinate(Dimension.dimensionFromInt(2)) - getCoordinate(Dimension.dimensionFromInt(2));
		
		boolean A = (diffX <= 0);
		boolean B = (diffY <= 0);
		boolean C = (1 == getDirection().getDirectionnr() / 2);
		boolean D = (1 == getDirection().getDirectionnr() % 2);
		
		if (diffX == 0 && diffY == 0){
			return getDirection();
		}
		if (diffX == 0){
			return Direction.directionFromInt(2 * (B? 0 : 1));
		}
		else if (diffY == 0){
			return Direction.directionFromInt(2 * (A? 1 : 0) + 1);
		}
		
		int X = ((!B && C) || (A && C) || (A && !B)) ? 1 : 0;
		int Y = ((!B && !C && !D) || (B && C && !D) || (!A && !C && D) || (!A && !B && !C) || (A && C && D) || (A && B && C)) ? 1 : 0;
		return Direction.directionFromInt(2*X + Y);
	}
	
	/**
	 * Turns this robot efficiently to the given direction
	 * and then moves one unit forward in that direction.
	 * This translation and rotation include the required energy payments.
	 * 
	 * @pre		The robot must be able to perform this energy cost method.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.MOVE)
	 * 			|	.add(getEnergyCostOf(Cost.TURN).multiply(Direction.amountOfEfficientTurnsToDirection(getDirection(), direction))))
	 * @param	direction
	 * 			The direction to turn to and move one unit forward in.
	 * @post	The direction of this robot has changed to the given direction.
	 * 			| (new this).getDirection() == direction
	 * @post	If the given direction is up or down the x coordinate has not changed.
	 * 			| if (direction == Direction.UP || direction == Direction.DOWN)
	 * 			| 	then (new this).getCoordinate(1) == (this).getCoordinate(1)
	 * @post	If the given direction is left or right the y coordinate has not changed.
	 * 			| if (direction == Direction.LEFT || direction == Direction.RIGHT)
	 * 			| 	then (new this).getCoordinate(2) == (this).getCoordinate(2)
	 * @post	If the given direction is left the x coordinate has been subtracted by one.
	 * 			| if (direction == Direction.LEFT)
	 * 			| 	then (new this).getCoordinate(1) = (this).getCoordinate(1) - 1;
	 * @post	If the given direction is right the x coordinate has been added with one.
	 * 			| if (direction == Direction.RIGHT)
	 * 			| 	then (new this).getCoordinate(1) = (this).getCoordinate(1) + 1;
	 * @post	If the given direction is up the y coordinate has been subtracted by one.
	 * 			| if (direction == Direction.UP)
	 * 			| 	then (new this).getCoordinate(2) = (this).getCoordinate(2) - 1;
	 * @post	If the given direction is down the y coordinate has been added with one.
	 * 			| if (direction == Direction.DOWN)
	 * 			| 	then (new this).getCoordinate(2) = (this).getCoordinate(2) + 1;
	 * @post 	This robots energy has been subtracted by the translation cost and by the rotation cost.
	 * 			| (new this).getEnergy.equals(getEnergy().subtract(getEnergyCostOf(Cost.MOVE))
	 * 			|	.subtract(getEnergyCostOf(Cost.TURN).multiply(Direction.amountOfEfficientTurnsToDirection(getDirection(), direction))))
	 * @throws	IllegalStateException
	 * 			This robot is not effective.
	 * 			| isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The given direction is invalid.
	 * 			| !Direction.isValidDirection(direction)
	 * @throws	IllegalArgumentException
	 * 			The robot's new coordinates are invalid for its board.
	 * 			| !getBoard().canHaveBoardModelAtNoBindingCheck((new this).getPosition(), this)
	 */
	@Model
	private void moveDirection(Direction direction)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This robot is not effective.");
		if(!Direction.isValidDirection(direction))
			throw new IllegalArgumentException("The given direction is invalid.");
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.MOVE).add(getEnergyCostOf(Cost.TURN).multiply(Direction.amountOfEfficientTurnsToDirection(getDirection(), direction)))));
		turnToDirection(direction);
		// IllegalArgumentException: 	rethrow
		super.moveToPosition(getPosition().getNextPositionInDirection(getDirection()));
		// IllegalStateException: 		avoided due to precondition
		dischargeAmountOfEnergy(getEnergyCostOf(Cost.MOVE));
	}
	
	/**
	 * Moves this robot one unit forward in the current direction of this robot.
	 * This translation includes the required energy payments.
	 * 
	 * @pre		The robot must be able to pay the energy cost for moving.
	 * 			| canDoEnergyCostMethod(getEnergyCostOf(Cost.MOVE))
	 * @post	The direction of this robot has not changed.
	 * 			| (new this).getDirection() == this.getDirection()
	 * @post	If this robots direction is up or down the x coordinate has not changed.
	 * 			| if (this.getDirection() == Direction.UP || this.getDirection() == Direction.DOWN)
	 * 			| 	then (new this).getCoordinate(Dimension.HORIZONTAL) == (this).getCoordinate(Dimension.HORIZONTAL)
	 * @post	If this robots direction is left or right the y coordinate has not changed.
	 * 			| if (this.getDirection() == Direction.LEFT || this.getDirection() == Direction.RIGHT)
	 * 			| 	then (new this).getCoordinate(Dimension.VERTICAL) == (this).getCoordinate(Dimension.VERTICAL)
	 * @post	If this robots direction is left the x coordinate has been subtracted by one.
	 * 			| if (this.getDirection() == Direction.LEFT)
	 * 			| 	then (new this).getCoordinate(Dimension.HORIZONTAL) = (this).getCoordinate(Dimension.HORIZONTAL) - 1;
	 * @post	If this robots direction is right the x coordinate has been added with one.
	 * 			| if (this.getDirection() == Direction.RIGHT)
	 * 			| 	then (new this).getCoordinate(Dimension.HORIZONTAL) = (this).getCoordinate(Dimension.HORIZONTAL) + 1;
	 * @post	If this robots direction is up the y coordinate has been subtracted by one.
	 * 			| if (this.getDirection() == Direction.UP)
	 * 			| 	then (new this).getCoordinate(Dimension.VERTICAL) = (this).getCoordinate(Dimension.VERTICAL) - 1;
	 * @post	If this robots direction is down the y coordinate has been added with one.
	 * 			| if (this.getDirection() == Direction.DOWN)
	 * 			| 	then (new this).getCoordinate(Dimension.VERTICAL) = (this).getCoordinate(Dimension.VERTICAL) + 1;
	 * @post 	This robots energy has been subtracted by the translation cost.
	 * 			| (new this).getEnergy.equals(getEnergy().subtract(getEnergyCostOf(Cost.MOVE)))
	 * @throws	IllegalStateException
	 * 			This robot is not effective.
	 * 			| isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The robot's new coordinates are invalid for its board.
	 * 			| !getBoard().canHaveBoardModelAtNoBindingCheck((new this).getPosition(), this)
	 */
	public void move() throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This robot is not effective.");
		assert(canDoEnergyCostMethod(getEnergyCostOf(Cost.MOVE)));
		//precondition: 	robot will never turn so due to this method's precondition always is able to fulfill this precondition
		moveDirection(getDirection());
	}
	
	/**
	 * Returns the minimal amount of energy required to reach a certain position with this robot
	 * without performing any inefficient moves or turns.
	 * 
	 * @param 	coordinates
	 * 			The coordinates corresponding to the position that this robot
	 * 			must reach.
	 * @return 	Returns the minimal amount of energy required to reach a certain position
	 * 			with this robot without performing any inefficient moves or turns.
	 * 			| result.equals(Astar.getEnergyRequiredForAstarRoute(this, position))
	 * @throws	IllegalStateException
	 * 			This robot is not effective.
	 * 			| isTerminated()
	 * @throws	IllegalArgumentException
	 * 			This robot cannot be located on the given position.
	 * 			| !getBoard().canHaveBoardModelAtNoBindingCheck(position, this)
	 * @throws	IllegalArgumentException
	 * 			The given position is unreachable or too complex to reach.
	 */
	public Energy getMinimalEnergyRequiredToReachPosition(Position position)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This robot is not effective.");
		//NullPointerException: 	avoided due to invariant
		if (!getBoard().canHaveBoardModelAtNoBindingCheck(position, this))
			throw new IllegalArgumentException("Robot can not be located on the given position.");
		return Astar.getEnergyRequiredForAstarRoute(this, position);
	}
	
	/**
	 * Moves this robot and the given robot as close as possible to each other
	 * with the least total amount of energy consumed.
	 * 
	 * @param 	other
	 * 			The other robot to move next to.
	 * @post	Each robot will be moved to a position that they are able to reach.
	 * 			| getBoard().canHaveBoardModelAtNoBindingCheck((new this).getPosition()), this) &&
	 * 			| getMinimalEnergyRequiredToReachPosition((new this).getPosition()).compareTo(getEnergy()) <= 0 &&
	 * 			| getBoard().canHaveBoardModelAtNoBindingCheck((new other).getPosition()), other) &&
	 * 			| getMinimalEnergyRequiredToReachPosition((new other).getPosition()).compareTo(other.getEnergy()) <= 0
	 * @post	Each robot will be moved to a position if possible that has the lowest Manhattan distance separation
	 * 			between the new position of both robots, with the lowest total energy requirement as possible to get there.
	 * 			If multiple of these positions are possible, there is no guarantee which one will be chosen.
	 * 			| for i,m in [0..getBoard().getSizeAtAt(Dimension.HORIZONTAL)] and with i != m : 
	 * 			| 	for j,n in [0..getBoard().getSizeAtAt(Dimension.VERTICAL)] and with j != n : 
	 * 			|		if((getBoard().canHaveBoardModelAtNoBindingCheck(new Position(i, j), this) &&
	 * 			|		   (getBoard().canHaveBoardModelAtNoBindingCheck(new Position(m, n), other))
	 * 			|       then
	 * 			|			if((new other).getPosition().getManhattanDistanceSeperation((new this).getPosition())
	 * 			|			   .compareTo((new Position(m, n)).getManhattanDistanceSeperation(new Position(i, j))) <= 0)
	 * 			|			then
	 * 			|				this.getMinimalEnergyRequiredToReachPosition((new this).getPosition())
	 * 			|				.add(other.getMinimalEnergyRequiredToReachPosition((new other).getPosition()))
	 * 			|				.compareTo(this.getMinimalEnergyRequiredToReachPosition(new Position(i, j))
	 * 			|				.add(other.getMinimalEnergyRequiredToReachPosition(new Position(m, n)))) <= 0)
	 * @throws	IllegalArgumentException
	 * 			The given other robot refers the null reference.
	 * 			The given other robot's board refers the null reference.
	 * 			This robot's board refers the null reference.
	 * 			The given other robot refers this robot.
	 * 			| other == null || other.getBoard() == null || this.getBoard() == null || other == this
	 * @throws	IllegalArgumentException
	 * 			The robots are not located on the same board.
	 * 			| getBoard() != other.getBoard()
	 */
	public void moveNextTo(Robot other)
			throws IllegalArgumentException, IllegalStateException{
		if(other == null || other.getBoard() == null || this.getBoard() == null || other == this)
			throw new IllegalArgumentException("One of the operating robots or their boards are invalid for this operation.");
		if (getBoard() != other.getBoard())
			throw new IllegalArgumentException("Robots are not located on the same board.");
		List<List<Position>> posLists = Astar.generateOptimalPositionLists(this, other);
		moveAccordingToPositionList(posLists.get(0));
		other.moveAccordingToPositionList(posLists.get(1));
	}
	
	/**
	 * Moves this robot according to the given position list.
	 * 
	 * @param 	posList
	 * 			The position list that the robot must follow.
	 * @pre		The Manhattan distance of the first position in the list must be
	 * 			either 0 or 1 with this robot's position
	 * 			| posList.get(0).getManhattanDistanceSeperation(getPosition()) == 0 ||
	 * 			| posList.get(0).getManhattanDistanceSeperation(getPosition()) == 1
	 * @pre		Every consecutive position in this list must have a Manhattan distance seperation
	 * 			of one with the previous position in the list.
	 * 			| for each i in [1..posList.size()-1] :
	 * 			| 	posList.get(i).getManhattanDistanceSeperation(posList.get(i-1)) == 1
	 * @pre		Every position in the list must be a valid position
	 * 			| for each pos in posList :
	 * 			| 	getBoard().canHaveBoardModelAtNoBindingCheck(pos, this)
	 * @effect	For every position starting with the position that has a Manhattan distance seperation 
	 * 			of one, the robot will do a rotation if needed and one translation.
	 * 			| for each pos in posList : 
	 * 			| 	moveDirection(firstDirectionToReachPosition(pos))
	 * @throws	IllegalArgumentException
	 * 			The given collection refers the null reference.
	 * 			| posList == null
	 */
	@Model
	private void moveAccordingToPositionList(List<Position> posList)
			throws IllegalArgumentException{
		if(posList == null)
			throw new IllegalArgumentException("The given list is invalid.");
		for(Position pos : posList){
			if (pos.equals(getPosition()))
				continue;
			moveDirection(firstDirectionToReachPosition(pos));
		}
	}
	
	/**
	 * The robot's accu is standard discharged with this energy variable when shot. 
	 */
	public static final Energy STANDARD_ENERGY_SHOT_DAMAGE = new Energy(4000);
	
	/**
	 * Hits this robot because of a fired shot.
	 * 
	 * @effect	If this robot's accu does not contain an energy capacity limit
	 * 			greater than the standard energy shot damage, this robot becomes terminated.
	 * 			| if(getAccu().getEnergyCapacityLimit().compareTo(STANDARD_ENERGY_SHOT_DAMAGE) <= 0)
	 * 			| 	then terminate()
	 * @effect	If this robot's accu contains an energy capacity limit greater than
	 * 			the standard energy shot damage, this accu's energy capacity limit
	 * 			is set to the difference of its old energy capacity limit and the
	 * 			standard energy shot damage for robots.
	 * 			| if(getAccu().getAmountOfEnergy().compareTo(STANDARD_ENERGY_SHOT_DAMAGE) > 0)
	 * 			| 	then getAccu().setEnergyCapacityLimit(getAccu().getEnergyCapacityLimit().subtract(STANDARD_ENERGY_SHOT_DAMAGE))
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This robot could not be shot.");
		if(getAccu().getEnergyCapacityLimit().compareTo(STANDARD_ENERGY_SHOT_DAMAGE) <= 0)
			terminate();
		else
			getAccu().setEnergyCapacityLimit(getAccu().getEnergyCapacityLimit().subtract(STANDARD_ENERGY_SHOT_DAMAGE));
	}
	
	/**
	 * Executes a step of the program of this robot, if this robot has a program.
	 * 
	 * @effect	If this robot's program is effective and the program of this robot has
	 * 			not been finished once, first start this robot's program if it hasn't
	 * 			been started yet.
	 * 			| if(getProgram() != null && !getProgramFinished())
	 * 			| 	then if (getProgram().isFinished()) then getProgram().start()
	 * @effect 	Then execute a step of the program
	 * 			| if(getProgram() != null && !getProgramFinished())
	 * 			| 	then getProgram().executeStep()
	 * @effect	Then if the program is finished, set the finished program flag of this robot to true.
	 * 			| if(getProgram() != null && !getProgramFinished())
	 * 			|	then if(getProgram().isFinished()) then setProgramFinished(true)
	 * 			| [all clauses SEQENTIAL]
	 */
	public void executeProgramStep(){
		if(getProgram() != null && !getProgramFinished()){
			if(getProgram().isFinished())
				getProgram().start();
			getProgram().executeStep();
			if (getProgram().isFinished())
				setProgramFinished(true);
		}
	}
	
	/**
	 * Sets this robot's program to a program, read from the given file path.
	 * 
	 * @param 	filePath
	 * 			The file path to read the program from.
	 * @effect	If this robot didn't have a program yet,
	 * 			this robot's program is given a new program
	 * 			according to the given file path.
	 * 			| if (getProgram == null)
	 * 			| 	then setProgram(new Program(this, filePath))
	 * @effect	Else this robot's program command list is set
	 * 			to the command list, included in the given file path.
	 * 			| if (getProgram != null)
	 * 			| 	then getProgram().setCommand(filePath)
	 * @effect 	Sets the program finished flag to false.
	 * 			| setProgramFinished(false)
	 * @throws 	IOException
	 * 			If the given file could not be read.
	 * @throws 	IllegalArgumentException
	 * 			If the given file's program is not written
	 * 			in correct robot language syntax.
	 *
	 * @note	We assume that the exact specification of the throw declarations
	 * 			would be given by the effect clauses.
	 */
	public void setProgramFromFile(String filePath)
			throws IllegalArgumentException, IOException{
		if(getProgram() == null)
			setProgram(new Program(this, filePath));
		else
			getProgram().setCommand(filePath);
		setProgramFinished(false);
	}
	
	/**
	 * Writes this robot's program to the given filepath.
	 * 
	 * @param 	filePath
	 * 			The filepath to write to.
	 * @effect	Writes this robot's program to the given filepath.
	 * 			| getProgram().write(filePath)
	 * @throws	IOException
	 * 			If the given filepath could not be written to.
	 * 
	 * @note	We assume that the exact specification of the throw declarations
	 * 			would be given by the effect clauses.
	 */
	public void writeProgramToFile(String filePath)
			throws IOException{
		getProgram().write(filePath);
	}
	
	/**
	 * Returns the program of this robot
	 */
	@Basic @Raw
	public Program getProgram(){
		return program;
	}
	
	/**
	 * Sets the program of this robot.
	 * 
	 * @param 	program
	 * 			The program that has to be set.
	 * @post	The program of this robot is set
	 * 			to the given program.
	 * 			| new.getProgram() = program
	 * @effect 	Sets the program finished flag to false.
	 * 			| setProgramFinished(false)
	 */
	@Raw
	public void setProgram(Program program){
		this.program = program;
		setProgramFinished(false);
	}
	
	/**
	 * Variable storing the program of this robot.
	 */
	private Program program;
	
	/**
	 * Sets the program finished status to the given boolean.
	 * 
	 * @param 	programFinished
	 * 			Flag to set the program finished status.
	 * @post 	The program finished status is equal to the given program finished boolean.
	 * 			| new.getProgramFinished() == programFinished
	 */
	@Raw @Model
	private void setProgramFinished(boolean programFinished){
		this.programFinished = programFinished;
	}
	
	/**
	 * Returns whether the program of this robot has finished.
	 */
	@Basic @Raw
	public boolean getProgramFinished(){
		return programFinished;
	}
	
	/**
	 * Variable storing whether the program of this robot has finished executing.
	 */
	private boolean programFinished;
	
	/**
	 * Returns a string representation of this robot.
	 * 
	 * @return	Returns a string representation of this robot.
	 * 			| result.equals("Energy:"+getEnergy().toString()+" Inventory: "+getInventory().toString())
	 */
	@Override
	public String toString(){
		return "Energy:"+getEnergy().toString()+" Inventory: "+getInventory().toString();
	}
}
