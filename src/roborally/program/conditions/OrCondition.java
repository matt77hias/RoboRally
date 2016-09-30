package roborally.program.conditions;

import roborally.model.dynamicObject.Robot;

/**
 * A class of or conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class OrCondition extends BinaryCondition {

	/**
	 * Initializes this new or condition with given sub conditions.
	 *
	 * @param  	one
	 *         	The first sub condition for this new or condition.
	 * @param  	two
	 *         	The second sub condition for this new or condition.
	 * @effect 	This new or condition is initialized as a binary condition
	 *         	with the given sub conditions as sub conditions.
	 *      	| super(one, two)
	 */
	public OrCondition(Condition one, Condition two)
			throws IllegalArgumentException{
		super(one, two);
	}
	
	/**
	 * Returns the result of the evaluation of this or condition.
	 * 
	 * @return	The result is the logical or-ing of its two sub conditions.
	 * 			| result == getFirstSubCondition().result(robot)
	 * 			|			|| getSecondSubCondition().result(robot)
	 */
	@Override
	public boolean result(Robot robot){
		if (robot == null)
			throw new IllegalStateException("Given robot is not effective.");
		if (robot.isTerminated())
			throw new IllegalStateException("Given robot is terminated.");
		return getFirstSubCondition().result(robot) || getSecondSubCondition().result(robot);
	}
	
	/**
	 * Returns the robot language symbol of this or condition.
	 * 
	 * @return	Returns "or".
	 * 			| result.equals("or")
	 */
	@Override
	public String getElementSymbol(){
		return "or";
	}
}
