package roborally.conditiontester;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

import roborally.model.BoardModel;

/**
 * A combined tester that could test more than one condition
 * of different condition testers.
 * 
 * @invar	Every combined condition tester must have proper
 * 			condition testers.
 * 			| hasProperConditionTesters()
 * 
 * @note	The combined tester doesn't make use of an optimized
 * 			testing facility at the moment. This means for instance
 * 			that the conditions 10<x and 15<x would both be checked
 * 			instead of the more rigid second condition and that both
 * 			conditions are elements of the condition testers of this
 * 			combined tester.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class CombinedTester implements ConditionTester {

	/**
	 * Initializes a new combined tester.
	 */
	public CombinedTester(){
		
	}
	
	/**
	 * Checks if the given condition tester is a valid condition tester.
	 * 
	 * @param 	request
	 * 			The condition tester that has to be checked.
	 * @return	True if and only if the given request doesn't refer
	 * 			the null reference.
	 * 			| result == (request != null)
	 */
	public static boolean isValidConditionTester(ConditionTester request){
		return (request != null);
	}
	
	/**
	 * Checks if the condition testers of this combined tester
	 * are all valid condition testers.
	 * 
	 * @return	True if and only if the condition testers of this
	 * 			combined tester are all valid condition testers
	 * 			and none of them refers this condition tester.
	 * 			| for each ct in getAllConditionTesters() :
	 * 			|	isValidConditionTester(ct) && (ct != this)
	 */
	@Raw
	public boolean hasProperConditionTesters(){
		for(ConditionTester ct : conditionTesters){
			if(!isValidConditionTester(ct) || ct == this)
				return false;
		}
		return true;
	}
	
	/**
	 * Checks if this combined tester has
	 * the given condition tester as one of its conditions testers.
	 * 
	 * @param 	request
	 * 			The condition tester that has to be checked.
	 * @return	True if and only if this combined tester has
	 * 			the given condition tester as one of its conditions testers.
	 * 			| result == getAllConditionTesters().contains(request)
	 */
	@Raw
	public boolean containsConditionTester(ConditionTester request){
		return conditionTesters.contains(request);
	}
	
	/**
	 * Adds the given condition tester to this combined tester.
	 * 
	 * @pre		It's not allowed to add a condition tester while
	 * 			this combined tester is used in an iteration.
	 * @param 	request
	 * 			The condition tester that has to be added.
	 * @post	This combined tester contains the given condition
	 * 			tester as one of its condition testers.
	 * 			| getAllConditionTesters().contains(request)
	 * @throws 	IllegalArgumentException
	 * 			The given request must be a valid condition tester
	 * 			and may not refer this condition tester.
	 * 			| !isValidConditionTester(request) || request == this
	 */
	public void addConditionTester(ConditionTester request)
			throws IllegalArgumentException{
		if(!isValidConditionTester(request) || request == this)
			throw new IllegalArgumentException("The given request is an invalid condition tester.");
		conditionTesters.add(request);
	}
	
	/**
	 * Removes the given condition tester from the condition testers
	 * of this combined tester.
	 * 
	 * @pre		It's not allowed to remove a condition tester while
	 * 			this combined tester is used in an iteration.
	 * @param 	request
	 * 			The condition tester that has to be removed.
	 * @post	This combined tester doesn't contain the given condition
	 * 			tester as one of its condition testers.
	 * 			| !getAllConditionTesters().contains(request)
	 */
	public void removeConditionTester(ConditionTester request){
		if(containsConditionTester(request))
			conditionTesters.remove(request);
	}
	
	/**
	 * Returns the number of condition testers of this combined tester.
	 * 
	 * @return	Returns the number of condition testers of this combined tester.
	 * 			| result == getAllConditionTesters().size()
	 */
	@Raw
	public int getNbOfConditionTesters(){
		return conditionTesters.size();
	}
	
	/**
	 * Returns all the condition testers of this combined tester.
	 */
	@Basic @Raw
	public Set<ConditionTester> getAllConditionTesters(){
		return Collections.unmodifiableSet(conditionTesters);
	}
	
	/**
	 * Collection containing all the condition testers of this combined tester.
	 */
	private Set<ConditionTester> conditionTesters = new HashSet<ConditionTester>();
	
	/**
	 * Tests if the given board model satisfies all the conditions
	 * of all the condition testers of this combined tester.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to undergo the test.
	 * @return 	True if the given board model satisfies all the conditions
	 * 			of all the condition testers of this combined tester.
	 * 			| result == (for each ct in getAllConditionTesters() :
	 * 			|				ct.testCondition(boardModel))
	 */
	@Override
	public boolean testCondition(BoardModel boardModel){
		if(boardModel == null)
			return false;
		for(ConditionTester ct : conditionTesters){
			if(!ct.testCondition(boardModel))
				return false;
		}
		return true;
	}

}
