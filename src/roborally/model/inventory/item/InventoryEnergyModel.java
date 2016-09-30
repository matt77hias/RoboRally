package roborally.model.inventory.item;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.energy.Accu;
import roborally.model.energy.EnergyModel;
import roborally.model.weight.Weight;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of inventory energy models involving an accu.
 * 
 * @invar	The accu of an inventory energy model must be
 * 			a valid accu for that inventory energy model.
 * 			| canHaveAsAccu(getAccu())
 * @invar	If an inventory energy model is picked up there exists
 * 			always an inventory object which contains that inventory
 * 			energy model as one of its inventory items.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class InventoryEnergyModel extends InventoryModel implements EnergyModel{

	/**
	 * Initializes this new inventory energy model with given board,
	 * position, accu and weight.
	 * 
	 * @param 	board
	 * 			The board where this new inventory energy model is situated on.
	 * @param 	position
	 * 			The position of this new inventory energy model on the given board.
	 * @param	accu
	 * 			The accu for this inventory energy model.
	 * @param	weight
	 * 			The weight for this new inventory energy model.
	 * @effect	This new inventory energy model is situated on
	 * 			the given board on the given position, 
	 * 			The given weight as its weight.
	 * 			| super(board, position, weight)
	 * @post	The new accu of this inventory energy model
	 * 			is equal to the given accu.
	 * 			| new.getAccu() == accu
	 */
	@Raw @Model
	protected InventoryEnergyModel(Board board, Position position, Accu accu, Weight weight)
			throws IllegalArgumentException{
		super(board, position, weight);
		setAccu(accu);
	}
	
	/**
	 * Terminates this inventory energy model and its accu.
	 */
	@Override
	public void terminate(){
		if(!isTerminated() && !isPickedUp()){
			// order is crucial
			super.terminate();
			getAccu().detachFromEnergyModel(this);
			getAccu().terminate();
			setAccu(null);
		}
	}
	
	/**
	 * Checks if the given accu is valid for this inventory energy model.
	 * 
	 * @param 	accu
	 * 			The accu that has to be checked.
	 * @return	In all other case the result is true.
	 * 			| result == true
	 */
	@Override @Raw
	public boolean canHaveAsAccu(Accu accu){
		if(this.isTerminated())
			return accu == null;
		else
			return (accu != null) && (!accu.isTerminated()) && ((getAccu() == accu) || (!accu.hasOwner()));
	}
	
	/**
	 * Sets the accu of this inventory energy model to the given accu.
	 * 
	 * @param 	accu
	 * 			The new accu for this inventory energy model.
	 * @post	The new accu of this inventory energy model is the given accu.	
	 * 			| new.getAccu() == accu
	 * @post	The given accu becomes owned,
	 * 			if it doesn't refer the null reference.
	 * 			| if(accu != null) then
	 * 			| 	new.accu.hasOwner() == true
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
	 * Returns the accu of this inventory energy model.
	 */
	@Basic @Raw @Override
	public Accu getAccu(){
		return accu;
	}
	
	/**
	 * The accu of this inventory energy model.
	 */
	private Accu accu;

	/**
	 * Checks if this inventory energy model is still useful for an owner
	 * as inventory item.
	 * 
	 * @return	This inventory energy model is not useful if the amount of energy
	 * 			stocked in its accu is equal to zero.
	 * 			| if(getAccu().getAmountOfEnergy().getEnergyAmount() != 0)
	 * 			| 	then result == false
	 */
	@Override @Raw
	public boolean isUseful() {
		return (!isTerminated()) && (getAccu().getAmountOfEnergy().getEnergyAmount() != 0);
	}
}
