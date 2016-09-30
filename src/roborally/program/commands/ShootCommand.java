package roborally.program.commands;

import roborally.model.Cost;
import roborally.model.dynamicObject.Robot;

/**
 * A class for the shoot command.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class ShootCommand extends BasicCommand {

	/**
	 * Initializes a new shoot command.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize shoot commands from
	 * 			outside this class.
	 */
	private ShootCommand(){
	}
	
	/**
	 * Constant storing one reference to a shoot command.
	 * This reference is the only shoot command that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static ShootCommand SHOOT_COMMAND = new ShootCommand();
	
	/**
	 * Executes this shoot command on the given robot.
	 * 
	 * @param	robot
	 * 			The robot on which the command must be executed on.
	 * @effect	If the robot can shoot, the robot will do so.
	 * 			| if(robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
	 *			|	then robot.shoot()
	 */
	@Override
	public void executeStep(Robot robot) throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if(robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
			robot.shoot();
	}
	
	/**
	 * Returns a string representation of this condition.
	 * 
	 * @return	Returns "shoot".
	 * 			| result.equals("shoot")
	 */
	@Override
	public String toString(){
		return "shoot";
	}
}
