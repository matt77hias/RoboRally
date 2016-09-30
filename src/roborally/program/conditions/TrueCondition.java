package roborally.program.conditions;


import roborally.model.dynamicObject.Robot;

/**
 * A class of true conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class TrueCondition extends BasicCondition {
	
	/**
	 * Initializes a true condition.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize true conditions from
	 * 			outside this class.
	 */
	private TrueCondition(){	
	}

	/**
	 * Constant storing one reference to a true condition.
	 * This reference is the only true condition that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static TrueCondition TRUE_CONDITION = new TrueCondition();
	
	/**
	 * Returns the result of this true condition.
	 * 
	 * @return	Returns true.
	 * 			| result == true
	 */
	@Override
	public boolean result(Robot robot) {
		return true;
	}
	
	/**
	 * Returns a string representation of this true condition.
	 * 
	 * @return	Returns "true".
	 * 			| result.equals("true")
	 */
	@Override
	public String toString(){
		return "true";
	}
}
