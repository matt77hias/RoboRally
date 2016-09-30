package roborally.program.conditions;

import roborally.model.dynamicObject.Robot;

/**
 * A class of not conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class NotCondition extends UnaryCondition {
	/**
	 * Initializes this new not condition with given operand.
	 *
	 * @param  	subCondition
	 *         	The new sub condition for this new not condition.
	 * @effect 	This new not condition is initialized as a unary condition
	 *         	with the given sub condition as its sub condition.
	 *       	| super(subCondition)
	 */
	public NotCondition(Condition subCondition) throws IllegalArgumentException {
		super(subCondition);
	}
	
	/**
	 * Returns the result of the evaluation of this not condition.
	 * 
	 * @return	The result is the inverse of the result of the sub condition of this not condition.
	 * 			| result == !getSubCondition().result(robot)
	 */
	@Override
	public boolean result(Robot robot) throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		return !getSubCondition().result(robot);
	}

	/**
	 * Returns the robot language symbol of this not condition.
	 * 
	 * @return	Returns "not".
	 * 			| result.equals("not")
	 */
	public String getElementSymbol(){
		return "not";
	}
}
