package roborally.conditiontester;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.BoardModel;

/**
 * A condition tester that tests if board models are instances
 * of a certain test class.
 * 
 * @invar	The test class of every type tester must be effective.
 * 			| getTestClass() != null
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
@SuppressWarnings("rawtypes")
public class TypeTester implements ConditionTester{

	/**
	 * Initializes a new type tester with given class
	 * as its test class.
	 * 
	 * @param 	clazz
	 * 			The new test class for this tester.
	 * @post	Sets the given test class to the test class
	 * 			of this new type tester. If the given test
	 * 			class refers the null reference, the new test
	 * 			class of this type tester is set to the board
	 * 			model class.
	 * 			| if(clazz != null)
	 * 			| 	then getTestClass() == clazz
	 * 			| else
	 * 			|	getTestClass() == BoardModel.class
	 */
	public TypeTester(Class clazz){
		if(clazz == null)
			clazz = BoardModel.class;
		this.clazz = clazz;
	}
	
	/**
	 * Returns the test class of this type tester.
	 */
	@Basic @Raw @Immutable
	public Class getTestClass(){
		return clazz;
	}
	
	/**
	 * The test class of this type tester.
	 */
	private final Class clazz;
	
	/**
	 * Tests if the given board model is an instance of the test
	 * class of this type tester.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to undergo the test.
	 * @return 	If the given board model is an instance of the
	 * 			test class of this type tester, the result is true.
	 * 			| result == getTestClass().isInstance(boardModel)
	 */
	@Override
	public boolean testCondition(BoardModel boardModel) {
		return getTestClass().isInstance(boardModel);
	}
}
