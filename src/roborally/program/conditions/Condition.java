package roborally.program.conditions;

import roborally.model.dynamicObject.Robot;
import roborally.program.languageelement.LanguageElement;

/**
 * An interface that should be implemented by all condition classes.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface Condition extends LanguageElement{
	
	/**
	 * Returns the result of the evaluation of this condition.
	 * 
	 * @param	robot
	 * 			The robot on which this evaluation must be done.
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| robot == null
	 * @throws	IllegalStateException
	 * 			Robot is terminated.
	 * 			| robot.isTerminated()
	 */	
	public abstract boolean result(Robot robot)
			throws IllegalStateException;
}
