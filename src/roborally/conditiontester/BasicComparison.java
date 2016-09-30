package roborally.conditiontester;

import java.util.HashSet;
import java.util.Set;
import be.kuleuven.cs.som.annotate.*;

/**
 * A enumeration with all the basic compare operations
 * between two comparable extending objects. The basic
 * compare operations are:
 * 		E (is equal to),
 * 		NE (is not equal to),
 * 		I (is identical to),
 * 		NI (is not identical to)
 * 		L (is less than),
 * 		G (is greater than),
 * 		LE (is less than or equal to),
 * 		GE (is greater than or equal to)
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public enum BasicComparison{
	E, NE, I, NI, L, G, LE, GE;
	
	/**
	 * Executes the corresponding basic comparison operation
	 * associated with this basic comparison.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	If this basic comparison refers E,
	 * 			the 'is equals to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == E)
	 * 			|	 then result == isEqualTo(o1,o2)
	 * @return	If this basic comparison refers NE,
	 * 			the 'is not equals to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == NE)
	 * 			| 	then result == isNotEqualTo(o1,o2)
	 * @return	If this basic comparison refers I,
	 * 			the 'is identical to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == I)
	 * 			| 	then result == isIdenticalTo(o1,o2)
	 * @return	If this basic comparison refers NI,
	 * 			the 'is not identical to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == NI)
	 * 			| 	then result == isNotIdenticalTo(o1,o2)
	 * @return	If this basic comparison refers L,
	 * 			the 'is less then' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == L)
	 * 			| 	then result == isLessThan(o1,o2)
	 * @return	If this basic comparison refers G,
	 * 			the 'is greater than' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == G)
	 * 			| 	then result == isGreaterThan(o1,o2)
	 * @return	If this basic comparison refers LE,
	 * 			the 'is less than or equals to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == LE)
	 * 			| 	then result == isLessThanOrEqualTo(o1,o2)
	 * @return	If this basic comparison refers GE,
	 * 			the 'is greater than or equals to' comparison is executed and
	 * 			the result is returned.
	 * 			| if(this == GE)
	 * 			| 	then result == isGreaterThanOrEqualTo(o1,o2)
	 * @return	In all other cases the result is false.
	 * 			| result == false
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			If this basic comparison is not referring I or NI.
	 * 			| if(this != I && this != NI)
	 * 			| 	then o1 == null || o2 == null
	 */
	public <T extends Comparable<T>> boolean compareDecode(T o1, T o2){
		switch(this){
			case E:
				return isEqualTo(o1,o2);
			case NE:
				return isNotEqualTo(o1,o2);
			case I:
				return isIdenticalTo(o1,o2);
			case NI:
				return isNotIdenticalTo(o1,o2);
			case L:
				return isLessThan(o1,o2);
			case G:
				return isGreaterThan(o1,o2);
			case LE:
				return isLessThanOrEqualTo(o1,o2);
			case GE:
				return isGreaterThanOrEqualTo(o1,o2);
			default:
				return false;
		}
	}
	
	/**
	 * Checks if the first given operand is equal to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is equal to the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) == 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isEqualTo(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) == 0;
	}
	
	/**
	 * Checks if the first given operand is not equal to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is not equal to the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) != 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isNotEqualTo(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) != 0;
	}
	
	/**
	 * Checks if the first given operand refers to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			refers to the second given operand.
	 * 			| result == (o1==o2)
	 */
	public static <T extends Comparable<T>> boolean isIdenticalTo(T o1, T o2){
		return o1==o2;
	}
	
	/**
	 * Checks if the first given operand refers not to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			refers not to the second given operand.
	 * 			| result == (o1!=o2)
	 */
	public static <T extends Comparable<T>> boolean isNotIdenticalTo(T o1, T o2){
		return o1!=o2;
	}
	
	/**
	 * Checks if the first given operand is less than
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is less than the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) == 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isLessThan(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) < 0;
	}
	
	/**
	 * Checks if the first given operand is greater than
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is greater than to the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) == 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isGreaterThan(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) > 0;
	}
	
	/**
	 * Checks if the first given operand is less than or equal to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is less than or equal to the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) == 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isLessThanOrEqualTo(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) <= 0;
	}
	
	/**
	 * Checks if the first given operand is greater than or equal to
	 * the second given operand.
	 * 
	 * @param 	o1
	 * 			The first operand.
	 * @param 	o2
	 * 			The second operand.
	 * @return	True if and only if the first given operand
	 * 			is greater than or equal to the second given operand.
	 * 			| result == (Math.signum(o1.compareTo(o2)) == 0)
	 * @throws 	NullPointerException
	 * 			None of the given arguments may refer the null reference.
	 * 			| o1 == null || o2 == null
	 */
	public static <T extends Comparable<T>> boolean isGreaterThanOrEqualTo(T o1, T o2)
			throws NullPointerException{
		if(o1 == null || o2 == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		return Math.signum(o1.compareTo(o2)) >= 0;
	}
	
	/**
     * Checks if the given basic comparison is valid.
     * 
     * @param 	basicComparison
     * 			The basic comparison that has to be checked.
     * @return	True if and only if the given basic comparison
     * 			doesn't refer the null reference.
     * 			| result == (basicComparison != null)
     */
    public static boolean isValidBasicComparison(BasicComparison basicComparison){
    	return basicComparison != null;
    }
    
    /**
     * Returns a collection with all the valid basic comparisons.
     * 
     * @return	Returns a collection which contains all the valid basic
     * 			comparisons once.
     * 			| result.size() == BasicComparison.values().length &&
     * 			| for each basicComparison in result :
     * 			| 	BasicComparison.isValidBasicComparison(basicComparison) == true
     */
    @Immutable
    public static Set<BasicComparison> getAllBasicComparisons(){
    	Set<BasicComparison> temp = new HashSet<BasicComparison>();
    	for(int i=0; i <getNbOfBasicComparisons(); i++)
    		temp.add(BasicComparison.values()[i]);
    	return temp;
    }
    
    /**
     * Returns the number of different valid basic comparisons.
     * 
     * @return	Returns the number of different valid basic comparisons.
     * 			| result == BasicComparison.values().length
     */
    @Immutable
    public static int getNbOfBasicComparisons(){
    	return BasicComparison.values().length;
    }
}
