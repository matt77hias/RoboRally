package roborally.conditiontester;

import be.kuleuven.cs.som.annotate.*;

/**
 * A comparison tester.
 * 
 * @invar	The basic comparison of every comparison tester
 * 			must be a valid basic comparison.
 * 			| BasicComparison.isValidBasicComparison(getBasicComparison())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class ComparisonTester implements ConditionTester{

	/**
	 * Initializes a new comparison tester with given basic comparison.
	 * 
	 * @param	basicComparison
	 * 			The basic comparison for this new comparison tester
	 * @post	The new basic comparison of this comparison tester
	 * 			refers the given basic comparison, if it does not
	 * 			refer the null reference. Otherwise the new basic comparison
	 * 			refers the standard basic comparison for comparison testers.
	 * 			| if(basicComparison != null)
	 * 			|	then new.getBasicComparison() == basicComparison
	 * 			| else
	 * 			| 	new.getBasicComparison() == STANDARD_BASIC_COMPARISON
	 */
	@Raw @Model
	protected ComparisonTester(BasicComparison basicComparison){
		if(basicComparison == null)
			basicComparison = STANDARD_BASIC_COMPARISON;
		this.basicComparison = basicComparison;
	}
	
	/**
	 * The standard basic comparison for comparison testers.
	 */
	public static final BasicComparison STANDARD_BASIC_COMPARISON = BasicComparison.E;
	
	/**
	 * Returns the basic comparison of this comparison tester.
	 */
	@Basic @Raw @Immutable
	public BasicComparison getBasicComparison(){
		return basicComparison;
	}
	
	/**
	 * The basic comparison for the test.
	 */
	private final BasicComparison basicComparison;
}
