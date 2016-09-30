package roborally.program.conditions;

import java.util.List;

import roborally.model.BoardModel;
import roborally.model.Cost;
import roborally.model.dynamicObject.Robot;

/**
 * A class of can hit robot conditions conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class CanHitRobotCondition extends BasicCondition {

	/**
	 * Initializes a can hit robot condition.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize can hit robot conditions from
	 * 			outside this class.
	 */
	private CanHitRobotCondition(){
	}
	
	/**
	 * Constant storing one reference to a can hit robot condition.
	 * This reference is the only can hit robot condition that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static CanHitRobotCondition CAN_HIT_ROBOT_CONDITION = new CanHitRobotCondition();
	
	/**
	 * Returns the result of this can hit robot condition.
	 * 
	 * @return	False if the given robot is unable to shoot.
	 *			| if (!robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
	 *			| 	then result == false
	 * @return	True if one of the board models that is in the target list
	 * 			of the given robot, is also a robot.
	 * 			| if (robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
	 * 			|	then result ==
	 * 			|		for some i in 0..robot.getBoard().getAllTargets(robot.getPosition(), robot.getDirection()).size()-1 :
	 * 			|			Robot.class.isInstance(robot.getBoard().getAllTargets(robot.getPosition(), robot.getDirection()).get(i))
	 */
	@Override
	public boolean result(Robot robot) 
			throws IllegalStateException {
		if (robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if (robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if (!robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.SHOOT)))
			return false;
		List<BoardModel> list = robot.getBoard().getAllTargets(robot.getPosition(), robot.getDirection());
		for (BoardModel bm : list){
			if (Robot.class.isInstance(bm)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns a string representation of this can hit robot condition.
	 * 
	 * @return	Returns "can-hit-robot"
	 * 			| result.equals("can-hit-robot")
	 */
	@Override
	public String toString(){
		return "can-hit-robot";
	}

}
