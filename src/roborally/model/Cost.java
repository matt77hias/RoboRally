package roborally.model;

import java.util.HashSet;
import java.util.Set;

import be.kuleuven.cs.som.annotate.Immutable;

/**
 * An enumeration containing the different costs for a board model.
 * This contains: MOVE, TURN, SHOOT.
 * 
 * @version Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public enum Cost {
	MOVE, TURN, SHOOT;
	
	/**
     * Checks if the given cost is valid.
     * 
     * @param 	cost
     * 			The cost that has to be checked.
     * @return	True if and only if the given cost
     * 			doesn't refer the null reference.
     * 			| result == (cost != null)
     */
    public static boolean isValidCost(Cost cost){
    	return cost != null;
    }
    
    /**
     * Returns a collection with all the valid costs.
     * 
     * @return	Returns a collection which contains all the valid costs once.
     * 			| result.size() == Cost.values().length &&
     * 			| for each cost in result :
     * 			| 	Cost.isValidCost(cost) == true
     */
    @Immutable
    public static Set<Cost> getAllCosts(){
    	Set<Cost> temp = new HashSet<Cost>();
    	for(int i=0; i <getNbOfCosts(); i++)
    		temp.add(Cost.values()[i]);
    	return temp;
    }
    
    /**
     * Returns the number of different valid costs.
     * 
     * @return	Returns the number of different valid costs.
     * 			| result == Cost.values().length
     */
    @Immutable
    public static int getNbOfCosts(){
    	return Cost.values().length;
    }
}
