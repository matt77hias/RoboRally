package roborally.program.conditions;

import roborally.model.dynamicObject.Robot;

/**
 * A class of and conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class AndCondition extends BinaryCondition {

	/**
	 * Initializes this new and condition with given sub conditions.
	 *
	 * @param  	one
	 *         	The first sub condition for this new and condition.
	 * @param  	two
	 *         	The second sub condition for this new and condition.
	 * @effect 	This new and condition is initialized as a binary condition
	 *         	with the given sub conditions as sub conditions.
	 *      	| super(one, two)
	 */
	public AndCondition(Condition one, Condition two)
			throws IllegalArgumentException{
		super(one, two);
	}
	
	/**
	 * Returns the result of the evaluation of this and condition.
	 * 
	 * @return	The result is the logical and-ing of its two sub conditions.
	 * 			| result == getFirstSubCondition().result(robot)
	 * 			|			&& getSecondSubCondition().result(robot)
	 */
	@Override
	public boolean result(Robot robot){
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		return getFirstSubCondition().result(robot) && getSecondSubCondition().result(robot);
	}
	
	/**
	 * Returns the robot language symbol of this and condition.
	 * 
	 * @return	Returns "and".
	 * 			| result.equals("and")
	 */
	@Override
	public String getElementSymbol(){
		return "and";
	}
}
