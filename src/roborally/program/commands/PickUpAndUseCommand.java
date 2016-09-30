package roborally.program.commands;

import java.util.List;

import roborally.model.dynamicObject.Robot;
import roborally.model.inventory.item.InventoryModel;

/**
 * A class for the pick up and use command.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class PickUpAndUseCommand extends BasicCommand {
	
	/**
	 * Initializes a new pick up and use command.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize pick up and use commands from
	 * 			outside this class.
	 */
	private PickUpAndUseCommand(){
	}
	
	/**
	 * Constant storing one reference to a pick up and use command.
	 * This reference is the only pick up and use command that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static PickUpAndUseCommand PICK_UP_AND_USE_COMMAND = new PickUpAndUseCommand();
	
	/**
	 * Executes this pick up and use command on the given robot.
	 * 
	 * @param	robot
	 * 			The robot on which the command must be executed on.
	 * @effect	For every inventory model that is located on the same position as the given
	 * 			robot, the given robot will, if it is both able to pick it up and use it,
	 * 			pick up the item and use it.
	 * 			| if(robot.getBoard() != null) then
	 * 			| let
	 * 			|	possibleItemList = robot.getBoard().getBoardModelsClassAt(robot.getPosition(), InventoryModel.class)
	 * 			| in :
	 * 			| for i in 0..possibleItemList.size()-1 :
	 *			|	if(robot.getInventory().canHaveAsInventoryItem(possibleItemList.get(i)))
	 *			|		then robot.getInventory().addInventoryItem(possibleItemList.get(i))
	 *			|			 && robot.getInventory().useInventoryItem(possibleItemList.get(i))
	 *			| [SEQUENTIAL]
	 */
	@Override
	public void executeStep(Robot robot) throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("Given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("Given robot is terminated.");
		if(robot.getBoard() != null){
			List<InventoryModel> possibleItemList = robot.getBoard().getBoardModelsClassAt(robot.getPosition(), InventoryModel.class);
			for (InventoryModel im : possibleItemList){
				if(robot.getInventory().canHaveAsInventoryItem(im)){
					robot.getInventory().addInventoryItem(im);
					robot.getInventory().useInventoryItem(im);
				}
			}
		}
	}
	
	/**
	 * Returns a string representation of this condition.
	 * 
	 * @return	Returns "pickup-and-use".
	 * 			| result.equals("pickup-and-use")
	 */
	@Override
	public String toString(){
		return "pickup-and-use";
	}
}
