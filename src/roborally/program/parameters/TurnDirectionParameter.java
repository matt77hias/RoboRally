package roborally.program.parameters;

import java.util.HashSet;
import java.util.Set;
import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration class for the turn direction parameters.
 * This contains: CLOCKWISE, COUNTERCLOCKWISE
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public enum TurnDirectionParameter {
	CLOCKWISE("clockwise"), COUNTERCLOCKWISE("counterclockwise");
	
	/**
	 * Initializes a new turn direction parameter with given
	 * String value.
	 * 
	 * @param 	stringVal
	 * 			The String value for this new turn direction parameter.
	 * @post	The turn direction String representation of
	 * 			this new turn direction parameter is equal to
	 * 			the given String value.
	 * 			| turnDirectionToString().equals(stringVal)
	 */
    private TurnDirectionParameter(String stringVal){
        this.stringVal = stringVal;
	}
    
    /**
     * Returns the turn direction parameter that corresponds to the given
     * string request.
     * 
     * @param 	stringRequest
     * 			The request in order to obtain the corresponding
     * 			turn direction parameter.
     * @return	If the string request is "clockwise",
     * 			the clockwise turn direction parameter is returned.
     * 			| if(stringRequest.equals("clockwise"))
     * 			| 	then result == CLOCKWISE
     * @return	If the string request is "counterclockwise",
     * 			the counterclockwise turn direction parameter is returned.
     * 			| if(stringRequest.equals("counterclockwise"))
     * 			| 	then result == COUNTERCLOCKWISE
     * @throws	NullPointerException
     * 			The given request may not refer the null reference.
     * 			| stringRequest == null
     * @throws	IllegalArgumentException
     * 			The given string request doesn't correspond to an
     * 			existing valid turn direction parameter.
     * 			| for each tdp in getAllTurnDirectionParameters():
     * 			|	!i.turnDirectionToString().equals(stringRequest)
     */
    public static TurnDirectionParameter TurnDirectionFromString(String stringRequest)
    		throws NullPointerException, IllegalArgumentException{
    	if(stringRequest == null)
    		throw new NullPointerException("The given request refers the null reference.");
    	
    	if(stringRequest.equals("clockwise"))
    		return CLOCKWISE;
    	else if(stringRequest.equals("counterclockwise"))
    		return COUNTERCLOCKWISE;
    	throw new IllegalArgumentException("Given turn direction parameter unkown: " + stringRequest);
    }
    
    /**
     * Returns the String representation of this turn direction parameter.
     */
    @Basic
    public String turnDirectionToString(){
    	return this.stringVal;
    }
	
    /**
     * Variable with the String representation of this turn direction parameter.
     */
	private String stringVal;
	
	/**
     * Checks if the given turn direction parameter is valid.
     * 
     * @param 	turnDirectionParameter
     * 			The turn direction parameter that has to be checked.
     * @return	True if and only if the given turn direction parameter
     * 			doesn't refer the null reference.
     * 			| result == (turnDirectionParameter != null)
     */
    public static boolean isValidTurnDirectionParameter(TurnDirectionParameter turnDirectionParameter){
    	return turnDirectionParameter != null;
    }
    
    /**
     * Returns a collection with all the valid turn direction parameters.
     * 
     * @return	Returns a collection which contains all the valid
     * 			turn direction parameters once.
     * 			| result.size() == TurnDirectionParameter.values().length &&
     * 			| for each tdp in result :
     * 			| 	TurnDirectionParameter.isValidTurnDirectionParameter(tdp) == true
     */
    @Immutable
    public static Set<TurnDirectionParameter> getAllTurnDirectionParameters(){
    	Set<TurnDirectionParameter> temp = new HashSet<TurnDirectionParameter>();
    	for(int i=0; i<getNbOfTurnDirectionParameters(); i++)
    		temp.add(TurnDirectionParameter.values()[i]);
    	return temp;
    }
    
    /**
     * Returns the number of different valid turn direction parameters.
     * 
     * @return	Returns the number of different valid turn
     * 			direction parameters.
     * 			| result == TurnDirectionParameter.values().length
     */
    @Immutable
    public static int getNbOfTurnDirectionParameters(){
    	return TurnDirectionParameter.values().length;
    }
}
