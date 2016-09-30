package roborally.model.inventory.item;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.*;
import roborally.model.energy.*;
import roborally.model.inventory.InventoryUser;
import roborally.model.weight.*;

/**
 * A class of repair kits.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class RepairKit extends InventoryEnergyModel{
	
	/**
	 * Variable storing the standard amount of energy for repair kits.
	 */
	public final static Energy STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY = new Energy(1000);
	
	/**
	 * Variable storing the standard energy capacity for repair kits.
	 */
	public final static Energy STANDARD_REPAIRKIT_ENERGY_CAPACITY = new Energy(Double.MAX_VALUE);
	
	/**
	 * Variable storing the standard weight repair kits.
	 * 
	 * @note	The weight unit is explicitly added, because of (assignment) restrictions.
	 */
	public static final Weight STANDARD_REPAIRKIT_WEIGHT = new Weight(100, WeightUnit.G);
	
	/**
	 * Initializes this new repair kit with standard values for a repair kit.
	 * 
	 * @effect	This new repair kit is not yet situated on a board, 
	 * 			has no position, has an accu with an energy amount
	 * 			equal to the standard amount of energy for repair kits
	 * 			and an energy capacity limit equal to the standard
	 * 			energy capacity for repair kits and has a weight equal
	 * 			to the standard weight for repair kits.
	 * 			| this(null, null, new Accu(STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY,
	 * 			|		STANDARD_REPAIRKIT_ENERGY_CAPACITY), STANDARD_REPAIRKIT_WEIGHT)
	 */
	@Raw
	public RepairKit()
			throws IllegalArgumentException{
		this(null, null, new Accu(STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, STANDARD_REPAIRKIT_ENERGY_CAPACITY), STANDARD_REPAIRKIT_WEIGHT);
	}
	
	/**
	 * Initializes this new repair kit with given accu.
	 * 
	 * @param	accu
	 * 			The accu for this new repair kit.
	 * @effect	This new repair kit is not yet situated on a board, 
	 * 			has no position, has the given accu as its accu 
	 * 			and has a weight equal to the standard weight for repair kits.
	 * 			| this(null, null, accu, STANDARD_REPAIRKIT_WEIGHT)
	 */
	@Raw
	public RepairKit(Accu accu)
			throws IllegalArgumentException{
		this(null, null, accu, STANDARD_REPAIRKIT_WEIGHT);
	}
	
	/**
	 * Initializes this new repair kit with given board, position and accu.
	 * 
	 * @param 	board
	 * 			The board where this new repair kit is situated on.
	 * @param 	position
	 * 			The position of this new repair kit on the given board.
	 * @effect	This new repair kit is situated on the given board on the given position,
	 * 			has an accu with an energy amount equal to the standard amount
	 * 			of energy for repair kits and an energy capacity limit equal to the standard
	 * 			energy capacity for repair kits and has a weight equal to the standard weight for repair kits.
	 * 			| this(board, position, new Accu(STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, STANDARD_REPAIRKIT_ENERGY_CAPACITY),
	 * 			|	 STANDARD_REPAIRKIT_WEIGHT)
	 */
	@Raw
	public RepairKit(Board board, Position position)
			throws IllegalArgumentException{
		this(board, position, new Accu(STANDARD_REPAIRKIT_AMOUNT_OF_ENERGY, STANDARD_REPAIRKIT_ENERGY_CAPACITY), STANDARD_REPAIRKIT_WEIGHT);
	}
	
	
	/**
	 * Initializes this new repair kit with given board, position and accu.
	 * 
	 * @pre		The given accu must be a valid initial accu.
	 * 			| isValidInitialAccu(accu)
	 * @param 	board
	 * 			The board where this new repair kit is situated on.
	 * @param 	position
	 * 			The position of this new repair kit on the given board.
	 * @param	accu
	 * 			The accu for this new repair kit.
	 * @effect	This new repair kit is situated on the given board on the given position,
	 * 			has the given accu as its accu and has a weight equal to the standard
	 * 			weight for repair kits.
	 * 			| this(board, position, accu, STANDARD_REPAIRKIT_WEIGHT)
	 */
	@Raw
	public RepairKit(Board board, Position position, Accu accu)
			throws IllegalArgumentException{
		this(board, position, accu, STANDARD_REPAIRKIT_WEIGHT);
	}
	
	/**
	 * Initializes this new repair kit with given board, position, accu and weight.
	 * 
	 * @pre		The given accu must be a valid initial accu.
	 * 			| isValidInitialAccu(accu)
	 * @param 	board
	 * 			The board where this new repair kit is situated on.
	 * @param 	position
	 * 			The position of this new repair kit on the given board.
	 * @param	accu
	 * 			The accu for this new repair kit.
	 * @param	weight
	 * 			The weight for this new repair kit.
	 * @effect	This new repair kit is situated on the given board on the given position, 
	 * 			with the given weight as its weight and the given accu after valid
	 * 			modification as its accu.
	 * 			| super(board, position, convertToValidAccu(accu), weight)
	 */
	@Raw
	public RepairKit(Board board, Position position, Accu accu, Weight weight)
			throws IllegalArgumentException{
		super(board, position, accu, weight);
	}

	/**
	 * Uses this item. Transferring energy of this repair kit's accu
	 * in order to increase the energy capacity limit of the accu
	 * of the given inventory user (if this inventory user is an energy model).
	 * 
	 * @param	inventoryUser
	 * 			The inventory user which accu's energy capacity limit has to increase.
	 * @effect	If the given inventory user is an instance of the energy model class,
	 * 			an healing transfer from the accu of this repair kit to the accu of the
	 * 			inventory user is executed with an efficiency factor equal to the standard
	 * 			use efficiency factor for repair kits.
	 * 			| if(EnergyModel.class.isInstance(inventoryUser))
	 * 			| 	then inventoryUser.getAccu().transferHealingFrom(getAccu(), RepairKit.STANDARD_USE_EFFICIENCY_FACTOR)
	 * @effect	If the accu of this repair kit becomes terminated after the transfer,
	 * 			this repair kit becomes also terminated.
	 * 			| if(new.getAccu().isTerminated()) 
	 * 			|	then inventoryUser.getInventory().removeAndTerminateInventoryItem(this)
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser)
			throws IllegalArgumentException{
		if(!canHaveAsUser(inventoryUser))
			throw new IllegalArgumentException("The given inventory user does not possess this repair kit.");
		if(EnergyModel.class.isInstance(inventoryUser)){
			((EnergyModel) inventoryUser).getAccu().transferHealingFrom(getAccu(), RepairKit.STANDARD_USE_EFFICIENCY_FACTOR);
			if(getAccu().isTerminated()){
				inventoryUser.getInventory().removeAndTerminateInventoryItem(this);
			}
		}
	}
	
	/**
	 * The standard use efficiency factor represents how efficient
	 * the use operation is executed.
	 */
	public static final double STANDARD_USE_EFFICIENCY_FACTOR = 0.5D;
	
	/**
	 * The accu of a repair kit is standard recharged with this energy variable when shot. 
	 */
	public static final Energy STANDARD_ENERGY_SHOT_BONUS = new Energy(500);

	/**
	 * Hits this repair kit because of a fired shot.
	 * 
	 * @post	If the accu of this repair kit could be recharged with the standard energy shot bonus
	 * 			for repair kits, it is recharged with the standard energy shot bonus.
	 * 			| if(getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
	 * 			|	then getAccu().rechargeAmountOfEnergy(STANDARD_ENERGY_SHOT_BONUS)
	 * @effect	If the accu of this repair kit could not be recharged with the standard energy shot bonus
	 * 			for repair kits, it's recharged so that its amount of energy is equal to its energy capacity limit.
	 * 			| if(!getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
	 * 			|	then getAccu().rechargeAmountOfEnergy(getAccu().getEnergyCapacityLimit().subtract(getAccu().getAmountOfEnergy()))
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This repair kit could not be shot.");
		if(getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
			getAccu().rechargeAmountOfEnergy(STANDARD_ENERGY_SHOT_BONUS);
		else
			getAccu().rechargeAmountOfEnergy(getAccu().getEnergyCapacityLimit().subtract(getAccu().getAmountOfEnergy()));
	}
}
