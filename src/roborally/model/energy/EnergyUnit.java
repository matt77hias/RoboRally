package roborally.model.energy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of energy units. Supported energy units: Watt/seconds, kiloWatt/Hours, Joule, kiloJoule.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public enum EnergyUnit {
	WS("Ws"), KWH("kWh"), JOULE("J"), KILOJOULE("kJ");
	
	/**
	 * Initializes this energy unit with the given symbol.
	 * 
	 * @param	symbol
	 * 			The symbol for this new energy unit.
	 * @post	The symbol for this new energy unit is equal to the given symbol.
	 * 			| new.getSymbol() == symbol
	 */
	private EnergyUnit(String symbol){
		this.symbol = symbol;
	}
	
	/**
	 * Returns the symbol for this energy unit.
	 */
	@Basic @Immutable
	public String getSymbol(){
		return this.symbol;
	}
	
	/**
	 * Variable storing the symbol for this energy unit.
	 */
	private final String symbol;
	
	/**
	 * Returns the value of one unit of this energy unit 
	 * in one unit of the given other energy unit.
	 * 
	 * @param	other
	 * 			The energy unit to convert to.
	 * @return	The resulting exchange rate is positive.
	 * 			| result.signum == 1
	 * @return	If this energy unit is the same as the other energy unit, 1.0 is returned.
	 * 			| (this == other)
	 * 			| 	then result == 1
	 * @return	The resulting exchange rate is the inverse of the exchange rate
	 * 			from the other energy unit to this energy unit.
	 * 			| result == 1 / other.toEnergyUnit(this)
	 * @throws 	IllegalArgumentException
	 * 			The given energy unit is invalid.
	 * 			| !isValidEnergyUnit(other)
	 */
	public double toEnergyUnit(EnergyUnit other) throws IllegalArgumentException{
		if (!isValidEnergyUnit(other)){
			throw new IllegalArgumentException("The given energy unit is invalid");
		}
		if (exchangeRates[this.ordinal()][other.ordinal()] == 0.0){
			exchangeRates[this.ordinal()][other.ordinal()] = 1 / exchangeRates[other.ordinal()][this.ordinal()];
		}
		return exchangeRates[this.ordinal()][other.ordinal()];
	}
	
	/**
	 * Variable referencing a two-dimensional array registering
	 * exchange rates between energy units. The first level is
	 * indexed by the ordinal number of the energy unit to convert
	 * from; the ordinal number to convert to is used to index
	 * the second level.
	 */
	private static double[][] exchangeRates = new double[4][4];
	
	static {
		exchangeRates[WS.ordinal()][WS.ordinal()] = 1D;
		exchangeRates[WS.ordinal()][JOULE.ordinal()] = 1D;
		exchangeRates[KWH.ordinal()][WS.ordinal()] = 3600000D;
		exchangeRates[KWH.ordinal()][KWH.ordinal()] = 1D;
		exchangeRates[KWH.ordinal()][JOULE.ordinal()] = 3600000D;
		exchangeRates[KWH.ordinal()][KILOJOULE.ordinal()] = 3600D;
		exchangeRates[JOULE.ordinal()][JOULE.ordinal()] = 1D;
		exchangeRates[KILOJOULE.ordinal()][JOULE.ordinal()] = 1000D;
		exchangeRates[KILOJOULE.ordinal()][WS.ordinal()] = 1000D;
		exchangeRates[KILOJOULE.ordinal()][KILOJOULE.ordinal()] = 1D;
	}
	
	/**
	 * Checks if the given energy unit is a valid energy unit.
	 * 
	 * @param 	energyUnit
	 * 			The energy unit that has to be checked.
	 * @return	True if and only if the given energy unit
	 * 			is not referring the null reference.
	 * 			| result == energyUnit != null
	 */
	public static boolean isValidEnergyUnit(EnergyUnit energyUnit){
		return energyUnit != null;
	}
}
