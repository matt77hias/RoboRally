package roborally.model.energy;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of energy amounts expressing an amount of energy
 * in units.
 * 
 * @invar	The energy unit of every energy must be a valid energy unit.
 * 			| EnergyUnit.isValidEnergyUnit(getEnergyUnit())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
@Value
public class Energy implements Comparable<Energy> {
	
	/**
	 * Initializes this new energy with given energy and energy unit Ws
	 * 
	 * @param 	energyAmount
	 * 			The energy amount for this new energy.
	 * @effect	This new energy is initialized with the given energy amount 
	 * 			and with the energy unit Ws.
	 * 			| this(energyAmount, EnergyUnit.WS)
	 * @throws 	IllegalArgumentException
	 * 			The given energy unit is invalid.
	 * 			| !EnergyUnit.isValidEnergyUnit(energyUnit)
	 */
	public Energy(double energyAmount)
			throws IllegalArgumentException{
		this(energyAmount, EnergyUnit.WS);
	}
	
	/**
	 * Initializes this new energy with given energy amount and given energy unit.
	 * 
	 * @param 	energyAmount
	 * 			The amount of energy for this new energy.
	 * @param 	energyUnit
	 * 			The energy unit for this new energy.
	 * @post	The energy amount for this new energy is the same as the given energy amount.
	 * 			| new.getEnergy() == energy
	 * @post	The energy unit for this new energy is the same as the given energy unit.
	 * 			| new.getEnergyUnit() == energyUnit
	 * @throws 	IllegalArgumentException
	 * 			The given energy unit is invalid.
	 * 			| !EnergyUnit.isValidEnergyUnit(energyUnit)
	 */
	@Raw
	public Energy(double energyAmount, EnergyUnit energyUnit)
			throws IllegalArgumentException{
		if (!EnergyUnit.isValidEnergyUnit(energyUnit))
			throw new IllegalArgumentException("The given energy unit is invalid.");
		this.energyUnit = energyUnit;
		this.energyAmount = energyAmount;
	}
	
	/**
	 * Checks if the given request is a valid energy.
	 * 
	 * @param 	request
	 * 			The request that has to be checked.
	 * @return	True if and only if the energy is not
	 * 			referring the null reference.
	 * 			| result == (request != null)
	 */
	public static boolean isValidEnergy(Energy request){
		return request != null;
	}
	
	/**
	 * Variable referencing a energy amount of 0.00 Ws
	 * 
	 * @return 	The energy amount WS_0 is equal to an energy amount initialized
	 * 			with double value 0.0 and with energy unit EnergyUnit.WS
	 * 			| WS_0.equals(new Energy(0.0, EnergyUnit.WS))
	 */
	public final static Energy WS_0 = new Energy(0.0, EnergyUnit.WS);
	
	/**
	 * Returns the energy amount.
	 */
	@Basic @Raw @Immutable
	public double getEnergyAmount(){
		return this.energyAmount;
	}
	
	/**
	 * Variable storing the amount of energy.
	 */
	private final double energyAmount;
	
	/**
	 * Returns the energy unit.
	 */
	@Basic @Raw @Immutable
	public EnergyUnit getEnergyUnit(){
		return this.energyUnit;
	}
	
	/**
	 * Returns an energy that has the same value as this energy expressed in the given energy unit.
	 * 
	 * @param	energyUnit
	 * 			The energy unit to convert to.
	 * @return	The resulting energy has the given energy unit as its energy unit
	 * 			| result.getEnergyUnit() == energyUnit
	 * @return	The energy amount of the resulting energy is equal to the energy amount
	 * 			of this energy multiplied with the exchange rate from the energy unit of
	 * 			this energy to the given energy unit.
	 * 			| result.getEnergyAmount() == getEnergyAmount()*getEnergyUnit().toEnergyUnit(energyUnit)
	 * @throws	IllegalArgumentException
	 * 			The given energy unit is invalid.
	 * 			| !EnergyUnit.isValidEnergyUnit(energyUnit)
	 */
	public Energy toEnergyUnit(EnergyUnit energyUnit) throws IllegalArgumentException{
		if (!EnergyUnit.isValidEnergyUnit(energyUnit))
			throw new IllegalArgumentException("The given energy unit is invalid.");
		if (getEnergyUnit() == energyUnit)
			return this;
		double exchangeRate = getEnergyUnit().toEnergyUnit(energyUnit);
		double energyAmountInNewEnergyUnit = getEnergyAmount() * exchangeRate;
		return new Energy(energyAmountInNewEnergyUnit, energyUnit);
	}
	
	/**
	 * Variable storing the unit of this energy value.
	 */
	private final EnergyUnit energyUnit;
	
	/**
	 * Compute the negation of this energy.
	 * 
	 * @return 	The resulting energy has the same energy unit as this energy.
	 * 			| result.getEnergyUnit() == this.getEnergyUnit()
	 * @return	The energy amount of the resulting energy is equal to the 
	 * 			negation of the energy amount of this energy.
	 * 			| result.getEnergyAmount() == -this.getEnergyAmount()
	 */
	public Energy negate(){
		return new Energy(-getEnergyAmount(), getEnergyUnit());
	}
	
	/**
	 * Computes the product of this energy with the given factor
	 * 
	 * @param 	factor
	 * 			The factor to multiply with.
	 * @return 	The resulting energy has the same energy unit as this energy.
	 * 			| result.getEnergyUnit() == this.getEnergyUnit()
	 * @return	The energy amount of the resulting energy is equal to the
	 * 			product of the energy  amount of this energy and the given factor.
	 * 			| result.getEnergyAmount = factor * this.getEnergyAmount();
	 */
	public Energy multiply(double factor){
		return new Energy(getEnergyAmount() * factor, getEnergyUnit());
	}
	
	/**
	 * Computes the sum of this energy and the other given energy.
	 * 
	 * @param	other
	 * 			The other energy to add to this.
	 * @return 	The resulting energy has the same energy unit as this energy.
	 * 			| result.getEnergyUnit() == this.getEnergyUnit()
	 * @return	If the two energies use the same units, the energy amount of
	 * 			the resulting energy is equal to the sum of the energy amounts of both energies.
	 * 			| if (this.getEnergyUnit() == other.getEnergyUnit())
	 *			| 	then result.getEnergyAmount() == this.getEnergyAmount() + other.getEnergyAmount()
	 * @return 	If the two energies use different units, the resulting energy is equal to
	 * 			the sum of this energy and the other energy expressed in the energy unit of this energy.
	 * 			| if (this.getEnergyUnit() != other.getEnergyUnit())
	 * 			|	then result.equals(this.add(other.toEnergyUnit(getEnergyUnit())))
	 * @throws	IllegalArgumentException
	 * 			The other energy is invalid.
	 * 			| !isValidEnergy(other)
	 */
	public Energy add(Energy other) throws IllegalArgumentException{
		if (!isValidEnergy(other))
			throw new IllegalArgumentException("The other energy is invalid.");
		if (getEnergyUnit() == other.getEnergyUnit())
			return new Energy(getEnergyAmount() + other.getEnergyAmount(), getEnergyUnit());
		return add(other.toEnergyUnit(getEnergyUnit()));
	}
	
	/**
	 * Computes the subtraction of the other energy from this energy
	 * 
	 * @param	other
	 * 			The energy to subtract.
	 * @return	The resulting energy is equal to the sum of this energy and the negation of the other energy.
	 * 			| result == this.add(other.negate())
	 * @throws 	IllegalArgumentException
	 * 			The other energy is invalid.
	 * 			| !isValidEnergy(other)
	 */
	public Energy subtract(Energy other) throws IllegalArgumentException{
		if (!isValidEnergy(other))
			throw new IllegalArgumentException("The other energy is invalid");
		if (getEnergyUnit() == other.getEnergyUnit())
			return new Energy(getEnergyAmount() - other.getEnergyAmount(), getEnergyUnit());
		return subtract(other.toEnergyUnit(getEnergyUnit()));
	}
	
	/**
	 * Compares this energy with the other energy.
	 * 
	 * @param	other
	 * 			The other energy to compare with.
	 * @return	The result is equal to the comparison of the energy amount of
	 * 			this energy with the energy amount of the other energy.
	 * 			| result == Double.compare(this.getEnergyAmount(), other.toEnergyUnit(this.getEnergyUnit()).getEnergyAmount())
	 * @throws	ClassCastException
	 * 			The other energy is not effective or this energy and the 
	 * 			given energy uses different energy units.
	 * 			| !isValidEnergy(other)
	 */
	@Override
	public int compareTo(Energy other) throws ClassCastException{
		if (!isValidEnergy(other))
			throw new ClassCastException("The other energy is invalid");
		return Double.compare(this.getEnergyAmount(), other.toEnergyUnit(this.getEnergyUnit()).getEnergyAmount());
	}
	
	/**
	 * Returns the signum of this energy.
	 * 
	 * @return	The signum of the energy amount of this energy.
	 * 			| result == Math.signum(getEnergyAmount())
	 */
	public double signum() {
		return Math.signum(getEnergyAmount());
	}
	
	/**
	 * Checks whether this energy has the same value as the other energy.
	 * 
	 * @param	other
	 * 			The other energy to compare with.
	 * @return	True if and only if this energy is equal to the other energy expressed
	 * 			in the energy unit of this energy.
	 * 			| result == this.equals(other.toEnergyUnit(getEnergyUnit())
	 * @throws 	IllegalArgumentException
	 * 			The other energy is invalid.
	 * 			| !isValidEnergy(other)
	 */
	public boolean hasSameValueAs(Energy other) throws IllegalArgumentException{
		if (!isValidEnergy(other))
			throw new IllegalArgumentException("The other energy is invalid.");
		return this.equals(other.toEnergyUnit(getEnergyUnit()));
	}
	
	/**
	 * Checks whether this energy is equal to the given object.
	 * 
	 * @return	True if and only if the given object is effective,
	 * 			if this energy and the given object belong to the
	 * 			same class, and if this energy and the other object
	 * 			interpreted as an energy have equal energy amounts
	 * 			and equal energy units.
	 * 			| ( (other != null)
	 * 			| && (this.getClass() == other.getClass())
	 * 			| && (this.getEnergyAmount() == ((Energy)other).getEnergyAmount())
	 * 			| && (this.getEnergyUnit() == ((Energy)other).getEnergyUnit()) )
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Energy otherEnergy = (Energy)other;
		return ( getEnergyAmount() == otherEnergy.getEnergyAmount()
				&& getEnergyUnit() == otherEnergy.getEnergyUnit() );
	}
	
	/**
	 * Returns the hash code for this energy.
	 * 
	 * @return	Returns the hash code for this energy.
	 * 			| result == Double.valueOf(getEnergyAmount()).hashCode() + getEnergyUnit().hashCode()
	 */
	@Override
	public int hashCode(){
		return Double.valueOf(getEnergyAmount()).hashCode() + getEnergyUnit().hashCode();
	}
	
	/**
	 * Returns a string representation of this energy.
	 * 
	 * @return	Returns a string representation of this energy.
	 * 			| result.equals(""+getEnergyAmount()+" "+getEnergyUnit());
	 */
	@Override
	public String toString(){
		return ""+getEnergyAmount()+" "+getEnergyUnit();
	}
}
