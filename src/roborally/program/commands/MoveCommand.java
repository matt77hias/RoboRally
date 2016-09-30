package roborally.program.commands;

import roborally.model.Cost;
import roborally.model.dynamicObject.Robot;

/**
 * A class for the move command.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class MoveCommand extends BasicCommand {

	/**
	 * Initializes a new move command.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize move commands from
	 * 			outside this class.
	 */
	private MoveCommand(){	
	}
	
	/**
	 * Constant storing one reference to a move command.
	 * This reference is the only move command that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static MoveCommand MOVE_COMMAND = new MoveCommand();
	
	/**
	 * Executes this move command on the given robot.
	 * 
	 * @param	robot
	 * 			The robot on which the command must be executed on.
	 * @effect	If the robot can do a move translation, the robot will do so.
	 * 			| if(robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.MOVE)))
	 *			|	then robot.move()
	 */
	@Override
	public void executeStep(Robot robot) throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("Given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("Given robot is terminated.");
		if(robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.MOVE))){
			try{
				robot.move();
			} catch (IllegalArgumentException e){
				// no fix available
			}
		}
	}
	
	/**
	 * Returns a string representation of this condition.
	 */
	@Override
	public String toString(){
		return "move";
	}
}
