package roborally.model.inventory.item;

import roborally.board.*;
import roborally.model.energy.*;
import roborally.model.inventory.*;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of batteries involving an accu.
 * 
 * @invar	The accu of a battery must be a valid accu for that battery.
 * 			| canHaveAsAccu(getAccu())
 * @invar	If a battery is picked up there exists always an inventory object
 * 			which contains that battery as one of its inventory items.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Battery extends InventoryEnergyModel{
	
	/**
	 * Variable storing the standard amount of energy for batteries.
	 */
	public final static Energy STANDARD_BATTERY_AMOUNT_OF_ENERGY = new Energy(1000);
	
	/**
	 * Variable storing the standard energy capacity for batteries.
	 */
	public final static Energy STANDARD_BATTERY_ENERGY_CAPACITY = new Energy(5000);
	
	/**
	 * Variable storing the standard battery weight.
	 * 
	 * @note	The weight unit is explicitly added, because of (assignment) restrictions.
	 */
	public static final Weight STANDARD_BATTERY_WEIGHT = new Weight(100, WeightUnit.G);
	
	/**
	 * Initializes this new battery with standard values for a battery.
	 * 
	 * @effect	This new battery is not yet situated on a board, 
	 * 			has no position, has an accu with an energy amount
	 * 			equal to the standard amount of energy for batteries
	 * 			and an energy capacity limit equal to the standard
	 * 			energy capacity for batteries and has a weight equal to the standard
	 * 			weight for batteries.
	 * 			| this(null, null, new Accu(STANDARD_BATTERY_AMOUNT_OF_ENERGY, STANDARD_BATTERY_ENERGY_CAPACITY), STANDARD_BATTERY_WEIGHT)
	 */
	@Raw
	public Battery()
			throws IllegalArgumentException{
		this(null, null, new Accu(STANDARD_BATTERY_AMOUNT_OF_ENERGY, STANDARD_BATTERY_ENERGY_CAPACITY), STANDARD_BATTERY_WEIGHT);
	}
	
	/**
	 * Initializes this new battery with given accu.
	 * 
	 * @param	accu
	 * 			The accu for this battery.
	 * @effect	This new battery is not yet situated on a board, 
	 * 			has no position, has the given accu as its accu 
	 * 			and has a weight equal to the standard weight for batteries.
	 * 			| this(null, null, accu, STANDARD_BATTERY_WEIGHT)
	 */
	@Raw
	public Battery(Accu accu)
			throws IllegalArgumentException{
		this(null, null, accu, STANDARD_BATTERY_WEIGHT);
	}
	
	/**
	 * Initializes this new battery with given board, position and accu.
	 * 
	 * @param 	board
	 * 			The board where this new battery is situated on.
	 * @param 	position
	 * 			The position of this new battery on the given board.
	 * @effect	This new battery is situated on the given board on the given position,
	 * 			has an accu with an energy amount equal to the standard amount of energy for batteries
	 * 			and an energy capacity limit equal to the standard
	 * 			energy capacity for batteries and has a weight equal to the standard weight for batteries.
	 * 			| this(board, position, new Accu(STANDARD_BATTERY_AMOUNT_OF_ENERGY, STANDARD_BATTERY_ENERGY_CAPACITY), STANDARD_BATTERY_WEIGHT))
	 */
	@Raw
	public Battery(Board board, Position position)
			throws IllegalArgumentException{
		this(board, position, new Accu(STANDARD_BATTERY_AMOUNT_OF_ENERGY, STANDARD_BATTERY_ENERGY_CAPACITY), STANDARD_BATTERY_WEIGHT);
	}
	
	
	/**
	 * Initializes this new battery with given board, position and accu.
	 * 
	 * @param 	board
	 * 			The board where this new battery is situated on.
	 * @param 	position
	 * 			The position of this new battery on the given board.
	 * @param	accu
	 * 			The accu for this battery.
	 * @effect	This new battery is situated on the given board on the given position,
	 * 			has the given accu as its accu and has a weight equal to the standard
	 * 			weight for batteries.
	 * 			| this(board, position, accu, STANDARD_BATTERY_WEIGHT)
	 */
	@Raw
	public Battery(Board board, Position position, Accu accu)
			throws IllegalArgumentException{
		this(board, position, accu, STANDARD_BATTERY_WEIGHT);
	}
	
	/**
	 * Initializes this new battery with given board, position, accu and weight.
	 * 
	 * @param 	board
	 * 			The board where this new battery is situated on.
	 * @param 	position
	 * 			The position of this new battery on the given board.
	 * @param	accu
	 * 			The accu for this battery.
	 * @param	weight
	 * 			The weight for this new battery.
	 * @effect	This new battery is situated on the given board on the given position, 
	 * 			the given accu as its accu and the given weight as its weight.
	 * 			| super(board, position, accu, weight)
	 */
	@Raw
	public Battery(Board board, Position position, Accu accu, Weight weight)
			throws IllegalArgumentException{
		super(board, position, accu, weight);
	}
	
	/**
	 * Uses this item. Transferring energy to the given inventory user,
	 * if this inventory user is an energy model.
	 * 
	 * @param	inventoryUser
	 * 			The inventory user to give energy to.
	 * @effect	Transfers as much energy as possible from this battery to
	 * 			the given inventory user, if this inventory user is an instance
	 * 			of the energy model class.
	 * 			| if(EnergyModel.class.isInstance(inventoryUser))
	 * 			| 	then inventoryUser.getAccu().transferEnergyFrom(getAccu())
	 * @effect	If the accu of this battery becomes terminated
	 * 			after the transfer, this battery becomes also terminated.
	 * 			| if(new.getAccu().isTerminated()) 
	 * 			|	then inventoryUser.getInventory().removeAndTerminateInventoryItem(this)
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser) throws IllegalArgumentException{
		if(!canHaveAsUser(inventoryUser))
				throw new IllegalArgumentException("The given inventory user does not possess this battery.");
		if(EnergyModel.class.isInstance(inventoryUser)){
			((EnergyModel) inventoryUser).getAccu().transferEnergyFrom(getAccu());
			if(getAccu().isTerminated()){
				inventoryUser.getInventory().removeAndTerminateInventoryItem(this);
			}
		}
	}
	
	/**
	 * The accu of a battery is standard recharged with this energy variable when shot. 
	 */
	public static final Energy STANDARD_ENERGY_SHOT_BONUS = new Energy(500);
	
	/**
	 * Hits this battery because of a fired shot.
	 * 
	 * @post	If the accu of this battery could be recharged with the standard energy shot bonus
	 * 			for batteries, it is recharged with the standard energy shot bonus.
	 * 			| if(getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
	 * 			|	then getAccu().rechargeAmountOfEnergy(STANDARD_ENERGY_SHOT_BONUS)
	 * @effect	If the accu of this battery could not be recharged with the standard energy shot bonus
	 * 			for batteries, it's recharged so that its amount of energy is equal to its energy capacity limit.
	 * 			| if(!getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
	 * 			|	then getAccu().rechargeAmountOfEnergy(getAccu().getEnergyCapacityLimit().subtract(getAccu().getAmountOfEnergy()))
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This battery could not be shot.");
		if(getAccu().canHaveAsAmountOfEnergy(getAccu().getAmountOfEnergy().add(STANDARD_ENERGY_SHOT_BONUS)))
			getAccu().rechargeAmountOfEnergy(STANDARD_ENERGY_SHOT_BONUS);
		else
			getAccu().rechargeAmountOfEnergy(getAccu().getEnergyCapacityLimit().subtract(getAccu().getAmountOfEnergy()));
	}
	
	/**
	 * Returns a string representation of this battery.
	 * 
	 * @return	Returns a string representation of this battery.
	 * 			| result.equals("Battery:"+getAccu().getAmountOfEnergy())
	 */
	@Override
	public String toString(){
		return "Battery:"+getAccu().getAmountOfEnergy();
	}
}
