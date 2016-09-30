package roborally.conditiontester;

import roborally.model.BoardModel;

/**
 * An interface that has to be implemented by all classes
 * that must have the authority to test a certain (true/false) condition.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface ConditionTester {

	/**
	 * Tests if the given board model satisfies the condition.
	 * 
	 * @param 	boardModel
	 * 			The board model to test on the condition.
	 * @return	False if the given board model is not effective.
	 * 			| if (boardModel == null)
	 * 			| 	then result == false
	 */
	public abstract boolean testCondition(BoardModel boardModel);
}
