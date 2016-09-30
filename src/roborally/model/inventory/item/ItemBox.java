package roborally.model.inventory.item;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.Board;
import roborally.board.Position;
import roborally.model.inventory.*;
import roborally.model.weight.*;

/**
 * A class of inventory boxes involving an inventory.
 * 
 * @invar	The inventory of every inventory box must be a valid inventory.
 * 			| hasValidInventory()
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ItemBox extends InventoryModel implements Collector{

	/**
	 * The standard weight for item boxes.
	 */
	public static final Weight STANDARD_ITEMBOX_WEIGHT = new Weight(100, WeightUnit.G);
	
	/**
	 * Initializes this new inventory with default values.
	 * 
	 * @effect	This new inventory box is situated on no board, no position
	 * 			and with the standard weight for item boxes as weight.
	 * 			| this(null, null, STANDARD_ITEMBOX_WEIGHT)
	 */
	@Raw
	public ItemBox(){
		this(null, null, STANDARD_ITEMBOX_WEIGHT);
	}
	
	/**
	 * Initializes this new inventory box with given board and position.
	 * 
	 * @param 	board
	 * 			The board where this new inventory box is situated on.
	 * @param 	position
	 * 			The position where this new inventory box is situated on.
	 * @effect	This new inventory box is situated on the given board,
	 * 			on the given position and with the standard weight for
	 * 			item boxes as weight.
	 * 			| this(board, position, STANDARD_ITEMBOX_WEIGHT)
	 */
	@Raw
	public ItemBox(Board board, Position position)
			throws IllegalArgumentException {
		this(board, position, STANDARD_ITEMBOX_WEIGHT);
	}
	
	/**
	 * Initializes this new inventory box with given board, position and weight.
	 * 
	 * @param 	board
	 * 			The board where this new inventory box is situated on.
	 * @param 	position
	 * 			The position where this new inventory box is situated on.
	 * @param 	weight
	 * 			The new weight for this inventory box.
	 * @effect	This new inventory box is situated on the given board,
	 * 			on the given position and with the given weight as weight.
	 * 			| super(board, position, weight)
	 */
	@Raw
	public ItemBox(Board board, Position position, Weight weight)
			throws IllegalArgumentException {
		super(board, position, weight);
	}
	
	/**
	 * Terminates this item box, its inventory and all its inventory items.
	 */
	@Override
	public void terminate(){
		if(!isTerminated() && !isPickedUp()){
			// order is crucial
			super.terminate();
			getInventory().terminate();
			resetInventory();
		}
	}
	
	/**
	 * Checks if this inventory model is still
	 * useful for a possible owner.
	 * 
	 * @return	If this inventory model doesn't contain
	 * 			inventory items in its inventory, it is not
	 * 			useful anymore.
	 * 			| if(getInventory().getNbOfInventoryItems()==0)
	 * 			| 	then result == false
	 */
	@Override @Raw
	public boolean isUseful(){
		return !isTerminated() && (getInventory().getNbOfInventoryItems()!=0);
	}

	/**
	 * This inventory box is used by the given inventory user.
	 * This inventory box will be terminated if the item
	 * isn't useful anymore after usage.
	 * 
	 * @param	inventoryUser
	 * 			The user of this inventory box.
	 * @effect	This inventory box will be terminated if the item
	 * 			isn't useful anymore after usage.
	 * 			| if(!isUseful())
	 * 			| 	then inventoryUser.getInventory().removeAndTerminateInventoryItem(this)
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser)
			throws IllegalArgumentException {
		if(!canHaveAsUser(inventoryUser))
			throw new IllegalArgumentException("The given inventory user does not possess this item box.");
		if(!isUseful())
			inventoryUser.getInventory().removeAndTerminateInventoryItem(this);
	}
	
	/**
	 * Hits this inventory box because of a fired shot.
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This inventory box could not be shot.");
		terminate();
	}

	/**
	 * Checks if this item box has a valid inventory.
	 * 
	 * @return	If this item box is terminated,
	 * 			its inventory must refer the null reference.
	 * 			| if(isTerminated())
	 * 			| 	then result == (getInventory() == null)
	 * @return	If this item box is not terminated,
	 * 			its inventory may not refer the null reference.
	 * 			| if(!isTerminated())
	 * 			| 	then result == (getInventory() != null)
	 */
	@Override
	public boolean hasValidInventory(){
		if(isTerminated())
			return getInventory() == null;
		else
			return getInventory() != null;
	}
	
	/**
	 * Returns the inventory of this item box.
	 */
	@Basic @Override @Raw
	public Inventory getInventory(){
		return inventory;
	}
	
	/**
	 * Sets the inventory of this item box to the null reference,
	 * if this item box is terminated.
	 * 
	 * @post	Sets the inventory of this item box to the null reference,
	 * 			if this item box is terminated.
	 * 			| if(this.isTerminated())
	 * 			| 	then new.getInventory() == null
	 */
	@Raw
	private void resetInventory(){
		if(this.isTerminated())
			this.inventory = null;
	}
	
	/**
	 * Variable storing the inventory of this item box.
	 */
	private Inventory inventory = new Inventory(this);
}
