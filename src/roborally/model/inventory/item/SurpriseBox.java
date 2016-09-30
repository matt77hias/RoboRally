package roborally.model.inventory.item;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.*;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.SurpriseFactory;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;

/**
 * A class of surprise boxes.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class SurpriseBox extends ItemBox {
	
	/**
	 * The standard weight for surprise boxes.
	 */
	public static final Weight STANDARD_SURPRISEBOX_WEIGHT = new Weight(100, WeightUnit.G);

	/**
	 * Initializes this surprise box with default values.
	 * 
	 * @effect	This new surprise box is situated on no board, no position
	 * 			and with the standard weight for surprise boxes as weight.
	 * 			| this(null, null, STANDARD_SURPRISEBOX_WEIGHT)
	 */
	@Raw
	public SurpriseBox()
			throws IllegalArgumentException {
		this(null, null, STANDARD_SURPRISEBOX_WEIGHT);
	}
	
	/**
	 * Initializes this surprise box with board and position.
	 * 
	 * @effect	This new surprise box is situated on the given board,
	 * 			on the given position and with the standard weight for
	 * 			surprise boxes as weight.
	 * 			| this(board, position, STANDARD_SURPRISEBOX_WEIGHT)
	 */
	@Raw
	public SurpriseBox(Board board, Position position)
			throws IllegalArgumentException {
		this(board, position, STANDARD_SURPRISEBOX_WEIGHT);
	}
	
	/**
	 * Initializes this surprise box with given board, position and weight. 
	 * 
	 * @param 	board
	 * 			The board where this new surprise box is situated on.
	 * @param 	position
	 * 			The position where this new surprise box is situated on.
	 * @param	weight
	 * 			The weight for this new surprise box.
	 * @effect	This new surprise box is situated on the given board,
	 * 			on the given position and with the given weight as weight.
	 * 			| super(board, position, weight)
	 * @effect	A random surprise is generated and added to the inventory
	 * 			of this new surprise box.
	 * 			| getInventory().addInventoryItem(SurpriseFactory.generateRandomStandardSurprise(getBoard(), getPosition()))
	 */
	@Raw
	public SurpriseBox(Board board, Position position, Weight weight)
			throws IllegalArgumentException {
		super(board, position, weight);
		getInventory().addInventoryItem(SurpriseFactory.generateRandomStandardSurprise(getBoard(), getPosition()));
	}
	
	/**
	 * This surprise box is used by the given inventory user.
	 * This surprise box will be terminated if the item
	 * isn't useful anymore after usage.
	 * 
	 * @param	inventoryUser
	 * 			The user of this surprise box.
	 * @effect	If an inventory model is stocked in the inventory of this
	 * 			surprise box, the less heaviest stocked inventory model is
	 * 			transfered to the inventory of the given inventory user.
	 * 			Then that inventory user uses that inventory model, if its
	 * 			not an instance of the inventory energy model class or the
	 * 			item box class.
	 * 			| if(getInventory().getNbOfInventoryItems()!=0)
	 * 			| 	then
	 * 			|		let temp = getInventory().getInventoryItemAt(1) in:
	 * 			|		inventoryUser.getInventory().addInventoryItem(temp, getInventory())
	 * 			|		&& if(!InventoryEnergyModel.class.isInstance(temp) && !ItemBox.class.isInstance(temp))
	 * 			|				then inventoryUser.getInventory().useInventoryItem(temp)
	 * @effect	This surprise box will be terminated if the item
	 * 			isn't useful anymore after usage.
	 * 			| if(!isUseful() && !inventoryUser.isTerminated())
	 * 			| 	then inventoryUser.getInventory().removeAndTerminateInventoryItem(this)
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser)
			throws IllegalArgumentException {
		if(!canHaveAsUser(inventoryUser))
			throw new IllegalArgumentException("The given inventory user does not possess this surprise box.");
		
		if(getInventory().getNbOfInventoryItems()!=0){
			InventoryModel temp = getInventory().getInventoryItemAt(1);
			inventoryUser.getInventory().addInventoryItem(temp, getInventory());
			// --> to remove after project
			if(!InventoryEnergyModel.class.isInstance(temp) && !ItemBox.class.isInstance(temp))
				inventoryUser.getInventory().useInventoryItem(temp);
		}
		if(!isUseful() && !inventoryUser.isTerminated())
			inventoryUser.getInventory().removeAndTerminateInventoryItem(this);
	}
	
	/**
	 * Checks if this surprise box could be hit.
	 * 
	 * @return	If the given surprise box is situated on
	 * 			a board referring the null reference,
	 * 			it can not be hit.
	 * 			| if(getBoard() == null)
	 * 			|	then result == false
	 */
	@Override @Raw
	public boolean canBeHit(){
		return super.canBeHit() && (getBoard() != null);
	}
	
	/**
	 * Hits this surprise box because of a fired shot.
	 * 
	 * @effect	This surprise box becomes terminated and is replaced
	 * 			with a bomb with an impact range of one. This bomb
	 * 			immediately explodes.
	 * 			| terminate() &&
	 * 			| (new Bomb(getBoard(), getPosition(),1L)).hit()
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This surprise box could not be shot.");
		
		// cascade effect avoided due termination of this surprise box
		
		Board board = getBoard();
		Position position = getPosition();
		terminate();
		(new Bomb(board, position,1L)).hit();
	}
	
	
}
