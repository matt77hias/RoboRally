package roborally.program.conditions;

import java.util.List;

import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.InventoryModel;

/**
 * A class of at item conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class AtItemCondition extends BasicCondition {

	/**
	 * Initializes an at item condition.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize at item conditions from
	 * 			outside this class.
	 */
	private AtItemCondition(){
	}
	
	/**
	 * Constant storing one reference to an at item condition.
	 * This reference is the only at item condition that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static AtItemCondition AT_ITEM_CONDITION = new AtItemCondition();
	
	/**
	 * Returns the result of this at item condition.
	 * 
	 * @return	False if the robot is not located on any board.
	 * 			| if(robot.getBoard() == null)
	 *			|	then result == false
	 * @return	True if one of the inventory models that are located on the
	 * 			same position as the given robot can be picked up by the given
	 * 			robot.
	 * 			| if(robot.getBoard() != null)
	 * 			|	then result ==
	 * 			|		for some i in 0..robot.getBoard().getBoardModelsClassAt(robot.getPosition(), InventoryModel.class).size()-1 :
	 * 			|			robot.getInventory().canHaveAsInventoryItem(robot.getBoard().getBoardModelsClassAt(robot.getPosition(), InventoryModel.class).get(i))
	 */
	@Override
	public boolean result(Robot robot) 
			throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if(robot.getBoard() == null)
			return false;
		List<InventoryModel> list = robot.getBoard().getBoardModelsClassAt(robot.getPosition(), InventoryModel.class);
		for (InventoryModel im : list){
			if (robot.getInventory().canHaveAsInventoryItem(im))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns a string representation of this condition.
	 * 
	 * @return	Returns "at-item"
	 * 			| result.equals("at-item")
	 */
	@Override
	public String toString(){
		return "at-item";
	}
}
