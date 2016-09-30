package roborally.model.weight;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weight units. Supported weight units: grams, kilograms.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public enum WeightUnit {
	G("g"), KG("kg");
	
	/**
	 * Initializes this weight unit with the given symbol.
	 * 
	 * @param	symbol
	 * 			The symbol for this new weight unit.
	 * @post	The symbol for this new weight unit is equal to the given symbol.
	 * 			| new.getSymbol() == symbol
	 */
	private WeightUnit(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Returns the symbol for this weight unit.
	 */
	@Basic @Immutable
	public String getSymbol(){
		return this.symbol;
	}
	
	/**
	 * Variable storing the symbol for this weight unit.
	 */
	private final String symbol;
	
	/**
	 * Returns the value of one unit of this weight unit 
	 * in one unit of the given other weight unit.
	 * 
	 * @param	other
	 * 			The weight unit to convert to.
	 * @return	The resulting exchange rate is positive.
	 * 			| result.signum == 1
	 * @return	If this weight unit is the same as the other weight unit, 1.0 is returned.
	 * 			| (this == other)
	 * 			| 	then result == 1
	 * @return	The resulting exchange rate is the inverse of the exchange rate
	 * 			from the other weight unit to this weight unit.
	 * 			| result == 1 / other.toWeightUnit(this)
	 * @throws 	IllegalArgumentException
	 * 			The given weight unit is invalid.
	 * 			| !isValidWeightUnit(other)
	 */
	public double toWeightUnit(WeightUnit other) throws IllegalArgumentException{
		if (!isValidWeightUnit(other))
			throw new IllegalArgumentException("The given weight unit is invalid");
		if (exchangeRates[this.ordinal()][other.ordinal()] == 0.0)
			exchangeRates[this.ordinal()][other.ordinal()] = 1 / exchangeRates[other.ordinal()][this.ordinal()];
		return exchangeRates[this.ordinal()][other.ordinal()];
	}
	
	/**
	 * Variable referencing a two-dimensional array registering
	 * exchange rates between weight units. The first level is
	 * indexed by the ordinal number of the weight unit to convert
	 * from; the ordinal number to convert to is used to index
	 * the second level.
	 */
	private static double[][] exchangeRates = new double[2][2];
	
	static {
		exchangeRates[G.ordinal()][G.ordinal()] = 1;
		exchangeRates[KG.ordinal()][G.ordinal()] = 1000;
		exchangeRates[KG.ordinal()][KG.ordinal()] = 1;
	}
	
	/**
	 * Checks if the given weight unit is a valid weight unit.
	 * 
	 * @param 	weightUnit
	 * 			The weight unit that has to be checked.
	 * @return	True if and only if the given weight unit
	 * 			is not referring the null reference.
	 * 			| result == weightUnit != null
	 */
	public static boolean isValidWeightUnit(WeightUnit weightUnit){
		return weightUnit != null;
	}
}
