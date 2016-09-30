package roborally.model.inventory.item;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.*;
import roborally.model.*;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;
import roborally.model.inventory.*;

/**
 * An abstract super class representing every inventory model that could be
 * picked up by another board model, involving a weight.
 * 
 * @invar	If an inventory model is picked up there exists always an inventory object
 * 			which contains that inventory model as one of its inventory items.
 * @invar	The weight of every inventory model must be a valid weight.
 * 			| isValidWeight(getWeight)
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class InventoryModel extends BoardModel{

	/**
	 * Initializes this new inventory model with given board, position and weight.
	 * 
	 * @param 	board
	 * 			The board where this new inventory model is situated on.
	 * @param 	position
	 * 			The position of this new inventory model on the given board.
	 * @param	weight
	 * 			The weight for this new inventory model.
	 * @effect	This new inventory model is situated on the given board on the given position.
	 * 			| super(board, position)
	 * @post	The new inventory model is not picked up yet.
	 * 			| new.isPickedUp() == false
	 * @post	The new weight of this battery is equal to the valid
	 * 			version of the given weight. If the given weight is already valid;
	 * 			the new weight of this battery is just equal to the given weight.
	 * 			| new.getWeight() == setWeightToValidWeight(weight)
	 */
	@Raw @Model
	protected InventoryModel(Board board, Position position, Weight weight)
			throws IllegalArgumentException {
		super(board, position);
		setPickedUp(false);
		setWeight(weight);
	}
	
	/**
	 * Terminates this inventory model.
	 * 
	 * @post	If this inventory model is not yet terminated
	 * 			and not picked up,
	 * 			this inventory model becomes terminated.
	 * 			| if(!isTerminated() && !isPickedUp())
	 * 			| 	then new.isTerminated() == true
	 */
	@Override
	public void terminate(){
		if(!isTerminated() && !isPickedUp())
			super.terminate();
	}
	
	/**
	 * Checks if this inventory model could share its position
	 * with the given board model.
	 * 
	 * @param 	request
	 * 			The board model that has to be checked.
	 * @return	True if and only if this inventory model could
	 * 			share its position with the given board model.
	 * 			An inventory model could only share a position
	 * 			with an inventory model or a robot.
	 * 			| result == InventoryModel.class.isInstance(request)
	 * 			|			|| Robot.class.isInstance(request)
	 * @note	This means this method doesn't include a board, position
	 * 			or termination check.
	 */
	@Override @Raw
	public boolean canSharePositionWith(BoardModel request){
		if(InventoryModel.class.isInstance(request))
			return true;
		if(roborally.model.dynamicObject.Robot.class.isInstance(request))
			return true;
		return false;
	}
	
	/**
	 * Variable stocking the maximum, supported weight for an inventory model.
	 */
	public static final Weight MAX_WEIGHT = new Weight(Integer.MAX_VALUE, WeightUnit.G);
	
	/**
	 * Checks if the given weight is a valid weight.
	 * 
	 * @param 	request
	 * 			The weight that has to be checked.
	 * @return	The weight must be a valid weight, must be greater than or equal to zero
	 * 			and must be less than the maximum integer value (measured in grams).
	 * 			| Weight.isValidWeight(request) && (0 <= request.getWeightAmount() && request.compareTo(MAX_WEIGHT)<=0
	 */
	public static boolean isValidWeight(Weight request){
		return Weight.isValidWeight(request) && (0 <= request.getWeightAmount() && request.compareTo(MAX_WEIGHT)<=0);
	}
	
	/**
	 * Returns the weight of the inventory model.
	 */
	@Basic @Raw
	public Weight getWeight(){
		return weight;
	}
	
	/**
	 * Sets the given weight request to the weight of this inventory model.
	 * 
	 * @param 	request
	 * 			The weight request for this inventory model.
	 * @post	The weight of this inventory model is set to the given
	 * 			weight request after modification (this only change the
	 * 			given weight request, if it is invalid for an inventory model)
	 * 			This set operation is only executed if this inventory model
	 * 			is not picked up.
	 * 			| if(!isPickedUp())
	 * 			| 	then	(if(isValidWeight(request)
	 * 			| 				then new.getWeight() == request
	 * 			| 			else
	 * 			| 			then new.getWeight() == setWeightToValidWeight(request))
	 */
	@Raw @Model
	protected void setWeight(Weight request){
		if(!isPickedUp())
			this.weight = setWeightToValidWeight(request);
	}
	
	/**
	 * Converts the given weight to a valid weight if it isn't already the case.
	 * Otherwise returns the given weight without any modification.
	 * 
	 * @param 	request
	 * 			The weight that has to be converted to a valid weight.
	 * @return	If the given weight refers the null reference a weight of 0.0 grams is returned.
	 * 			If the given weight has a negative weight amount, the positive
	 * 			variant is returned if it's less than the maximum supported weight.
	 * 			If the given weight is still positive after subtraction of the maximum weight,
	 * 			the maximum weight is returned.
	 * 			If the given weight is a valid weight, the given weight is returned without
	 * 			modification.
	 * 			| if(!isValidWeight(request)) then
	 * 			| 	if(!Weight.isValidWeight(request))
	 * 			|		then request = new Weight(0.0, WeightUnit.G)
	 * 			|	if(request.getWeightAmount()<0)
	 * 			|		then request = new Weight(Math.abs(request.getWeightAmount()), request.getWeightUnit())
	 * 			| 	if(request.compareTo(MAX_WEIGHT) > 0)
	 * 			| 		request = new Weight(MAX_WEIGHT.getWeightAmount(), MAX_WEIGHT.getWeightUnit())
	 * 			|result == new.request
	 */
	@Raw @Model
	private static Weight setWeightToValidWeight(Weight request){
		if(!isValidWeight(request)){
			if(!Weight.isValidWeight(request))
				request = new Weight(0.0, WeightUnit.G);
			if(request.getWeightAmount()<0)
				request = new Weight(Math.abs(request.getWeightAmount()), request.getWeightUnit());	
			if(request.compareTo(MAX_WEIGHT) > 0)
				request = new Weight(MAX_WEIGHT.getWeightAmount(), MAX_WEIGHT.getWeightUnit());
		}
		return request;
	}
	
	/**
	 * The weight of this inventory model.
	 */
	private Weight weight;
	
	/**
	 * Checks if this inventory model is still
	 * useful for a possible owner.
	 * 
	 * @return	If this inventory model is terminated,
	 * 			it's not useful anymore.
	 * 			| if(isTerminated())
	 * 			| 	then result == false
	 */
	@Raw
	public boolean isUseful(){
		return !isTerminated();
	}
	
	/**
	 * Checks whether this inventory model is picked up.
	 */
	@Basic @Raw
	public boolean isPickedUp(){
		return isPickedUp;
	}
	
	/**
	 * Picks up this inventory model.
	 * 
	 * @effect 	This inventory model is picked up and removed from the board
	 * 			where it was situated on before the pick up, if this inventory
	 * 			model is not terminated.
	 * 			| if(!isTerminated()) then
	 * 			| 	setPickedUp(true) &&
	 * 			| 	if(getBoard() != null) then getBoard().removeBoardModel(new.this)
	 */
	@Raw
	public void pickUp(){
		if(!isTerminated()){
			setPickedUp(true);
			if(getBoard() != null)
				getBoard().removeBoardModel(this);
		}
	}
	
	/**
	 * Drops this inventory model on the given board at the given coordinates.
	 * 
	 * @pre		There may not exist an inventory object which contains
	 * 			this inventory model as one of its inventory items.
	 * 			| for each collector : 	Collector.class.isInstance(collector)
	 * 			|						&& !collector.hasAsInventoryItem(this)
	 * @param	board
	 * 			The	board on which this inventory model is dropped.
	 * @param	Position
	 * 			The position at which this inventory model is dropped on
	 * 			the given board.
	 * @effect	This inventory model is dropped, meaning it isn't picked up anymore.
	 * 			If this inventory model is still useful, this inventory model is added 
	 * 			to the given board (if this board doesn't refer the null reference) 
	 * 			at the given position, otherwise the this inventory model is terminated.
	 * 			| setPickedUp(false) &&
	 * 			| if(board != null && this.isUseful()) 
	 * 			| 	then board.addBoardModel(position, this)
	 * 			| 	else this.terminate()
	 * 			| [SEQUENTIAL]
	 * @throws	IllegalStateException
	 * 			This inventory model must be picked up.
	 * 			| !isPickedUp()
	 * @throws	IllegalArgumentException
	 * 			If the given board doesn't refer the null reference
	 * 			and this inventory model could not be placed on the
	 * 			given position on the given board.
	 * 			(Because this position refers the null reference).
	 * 			| (board != null && this.isUseful()) && position == null
	 */
	public void drop(Board board, Position position)
			throws IllegalStateException, IllegalArgumentException{
		if(!isPickedUp())
			throw new IllegalStateException("This inventory model is not picked up.");
		setPickedUp(false);
		if(board != null && this.isUseful())
			board.addBoardModelAt(position, this);
		else
			this.terminate();
	}
	
	/**
	 * Changes the pick up state of this inventory model to the given request value.
	 * 
	 * @param 	request
	 * 			The new value for the pick up state of this inventory model.
	 * @post	Changes the pick up state of this inventory model
	 * 			to the given request value.
	 * 			| new.isPickedUp = request
	 */
	@Model @Raw
	private void setPickedUp(boolean request){
		isPickedUp = request;
	}
	
	/**
	 * Variable registering whether this inventory model is picked up.
	 * 
	 * @note 	A picked up inventory model has no position on any board, but
	 * 			belongs to the inventory of a board model.
	 */
	private boolean isPickedUp;
	
	/**
	 * Checks if the given inventory user is a valid user for this inventory model.
	 * 
	 * @param	inventoryUser
	 * 			The inventory user that has to be checked.
	 * @return	If the given inventory user refers the null reference, the result is always false.
	 * 			If this inventory model is terminated it can have no inventory user as user.
	 * 			If the given inventory user is terminated, it's invalid.
	 * 			If the given inventory user doesn't contain this inventory model,
	 * 			it's not a valid user for this inventory model.
	 * 			| result == (!isTerminated()) && (inventoryUser != null)
	 *			|		&& (!inventoryUser.isTerminated()) && (inventoryUser.getInventory().hasAsInventoryItem(this))
	 */
	@Raw
	public boolean canHaveAsUser(InventoryUser inventoryUser){
		return (!isTerminated()) && (inventoryUser != null)
				&& (!inventoryUser.isTerminated()) && (inventoryUser.getInventory().hasAsInventoryItem(this));
	}
	
	/**
	 * This inventory model is used by the given inventory user.
	 * This inventory model will be terminated if the item isn't useful anymore after usage.
	 * 
	 * @param	inventoryUser
	 * 			The user of this inventory item.
	 * @post	If this inventory model becomes terminated, it is removed
	 * 			from the inventory of the given inventory user.
	 * 			| if(new.isTerminated())
	 * 			| 	then inventoryUser.getInventory().removeInventoryItem(this)
	 * @throws	IllegalArgumentException
	 * 			The given collector does not possess this inventory model.
	 * 			| !canHaveAsUser(inventoryUser)
	 * @note	The @Raw annotation for inventoryUser in the signature
	 * 			is only applicable for inventoryUser.getInventory()
	 */
	public abstract void use(@Raw InventoryUser inventoryUser) throws IllegalArgumentException;
	
	/**
	 * Checks if this inventory model could be hit.
	 * 
	 * @return	If the given board model is terminated,
	 * 			it can not be hit.
	 * 			| if(!isTerminated())
	 * 			|	then result == false
	 * @return	If the given board model is picked up,
	 * 			it can not be hit.
	 * 			| if(!isPickedUp())
	 * 			|	then result == false
	 */
	@Override @Raw
	public boolean canBeHit(){
		return !isTerminated() && !isPickedUp();
	}
}
