package roborally.model.weight;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of weight amounts expressing an amount of weight
 * in units.
 *
 * @invar	The weight unit of every weight must be a valid weight unit.
 * 			| WeightUnit.isValidWeightUnit(getWeightUnit())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
@Value
public class Weight implements Comparable<Weight> {
	
	/**
	 * Initializes this new weight with given weight and weight unit G.
	 * 
	 * @param 	weightAmount
	 * 			The weight amount for this new weight.
	 * @effect	This new weight is initialized with the given weight amount 
	 * 			and with the weight unit G.
	 * 			| this(weightAmount, WeightUnit.G)
	 * @throws 	IllegalArgumentException
	 * 			The given weight unit is invalid.
	 * 			| !WeightUnit.isValidWeightUnit(weightUnit)
	 */
	public Weight(double weightAmount)
			throws IllegalArgumentException{
		this(weightAmount, WeightUnit.G);
	}
	
	/**
	 * Initializes this new weight with given weight amount and given weight unit.
	 * 
	 * @param 	weightAmount
	 * 			The amount of weight for this new weight.
	 * @param 	weightUnit
	 * 			The weight unit for this new weight.
	 * @post	The weight amount for this new weight is the same as the given weight amount.
	 * 			| new.getWeight() == weight
	 * @post	The weight unit for this new weight is the same as the given weight unit.
	 * 			| new.getWeightUnit() == weightUnit
	 * @throws 	IllegalArgumentException
	 * 			The given weight unit is invalid.
	 * 			| !WeightUnit.isValidWeightUnit(weightUnit)
	 */
	@Raw
	public Weight(double weightAmount, WeightUnit weightUnit)
			throws IllegalArgumentException{
		if (!WeightUnit.isValidWeightUnit(weightUnit))
			throw new IllegalArgumentException("The given weight unit is invalid.");
		this.weightUnit = weightUnit;
		this.weightAmount = weightAmount;
	}
	
	/**
	 * Checks if the given request is a valid weight.
	 * 
	 * @param 	request
	 * 			The request that has to be checked.
	 * @return	True if and only if the weight is not
	 * 			referring the null reference.
	 * 			| result == request != null
	 */
	public static boolean isValidWeight(Weight request){
		return request != null;
	}
	
	/**
	 * Variable referencing a weight amount of 0.0 g
	 * 
	 * @return 	The weight amount g_0 is equal to an weight amount initialized
	 * 			with double value 0.0 and with weight unit WeightUnit.G
	 * 			| g_0.equals(new Weight(0.0, WeightUnit.G))
	 */
	public final static Weight g_0 = new Weight(0.0, WeightUnit.G);
	
	/**
	 * Returns the weight amount.
	 */
	@Basic @Raw @Immutable
	public double getWeightAmount(){
		return this.weightAmount;
	}
	
	/**
	 * Variable storing the amount of weight.
	 */
	private final double weightAmount;
	
	/**
	 * Returns the weight unit.
	 */
	@Basic @Raw @Immutable
	public WeightUnit getWeightUnit(){
		return this.weightUnit;
	}
	
	/**
	 * Returns a weight that has the same value as this weight expressed in the given weight unit.
	 * 
	 * @param	weightUnit
	 * 			The weight unit to convert to.
	 * @return	The resulting weight has the given weight unit as its weight unit
	 * 			| result.getWeightUnit() == weightUnit
	 * @return	The weight amount of the resulting weight is equal to the weight amount
	 * 			of this weight multiplied with the exchange rate from the weight unit of
	 * 			this weight to the given weight unit.
	 * 			| result.getWeightAmount() == getWeightAmount()*getWeightUnit().toWeightUnit(weightUnit)
	 * @throws	IllegalArgumentException
	 * 			The given weight unit is invalid.
	 * 			| !WeightUnit.isValidWeightUnit(weightUnit)
	 */
	public Weight toWeightUnit(WeightUnit weightUnit) throws IllegalArgumentException{
		if (!WeightUnit.isValidWeightUnit(weightUnit))
			throw new IllegalArgumentException("The given weight unit is invalid.");
		if (getWeightUnit() == weightUnit)
			return this;
		double exchangeRate = getWeightUnit().toWeightUnit(weightUnit);
		double weightAmountInNewWeightUnit = getWeightAmount() * exchangeRate;
		return new Weight(weightAmountInNewWeightUnit, weightUnit);
	}
	
	/**
	 * Variable storing the unit of this weight value.
	 */
	private final WeightUnit weightUnit;
	
	//
	
	/**
	 * Computes the negation of this weight.
	 * 
	 * @return 	The resulting weight has the same weight unit as this weight.
	 * 			| result.getWeightUnit() == this.getWeightUnit()
	 * @return	The weight amount of the resulting weight is equal to the 
	 * 			negation of the weight amount of this weight.
	 * 			| result.getWeightAmount() == -this.getWeightAmount()
	 */
	public Weight negate(){
		return new Weight(-getWeightAmount(), getWeightUnit());
	}
	
	/**
	 * Computes the product of this weight with the given factor
	 * 
	 * @param 	factor
	 * 			The factor to multiply with.
	 * @return 	The resulting weight has the same weight unit as this weight.
	 * 			| result.getWeightUnit() == this.getWeightUnit()
	 * @return	The weight amount of the resulting weight is equal to the
	 * 			product of the weight amount of this weight and the given factor.
	 * 			| result.getWeightAmount = factor * this.getWeightAmount();
	 */
	public Weight multiply(double factor){
		return new Weight(getWeightAmount() * factor, getWeightUnit());
	}
	
	/**
	 * Computes the sum of this weight and the other given weight.
	 * 
	 * @param	other
	 * 			The other weight to add to this.
	 * @return 	The resulting weight has the same weight unit as this weight.
	 * 			| result.getWeightUnit() == this.getWeightUnit()
	 * @return	If the two weights use the same units, the weight amount of
	 * 			the resulting weight is equal to the sum of the weight amounts of both weights.
	 * 			| if (this.getWeightUnit() == other.getWeightUnit())
	 *			| 	then result.getWeightAmount() == this.getWeightAmount() + other.getWeightAmount()
	 * @return 	If the two weights use different units, the resulting weight is equal to
	 * 			the sum of this weight and the other weight expressed in the weight unit of this weight.
	 * 			| if (this.getWeightUnit() != other.getWeightUnit())
	 * 			|	then result.equals(this.add(other.toWeightUnit(getWeightUnit())))
	 * @throws	IllegalArgumentException
	 * 			The other weight is invalid.
	 * 			| !isValidWeight(other)
	 */
	public Weight add(Weight other) throws IllegalArgumentException{
		if (!isValidWeight(other))
			throw new IllegalArgumentException("The other weight is invalid.");
		if (getWeightUnit() == other.getWeightUnit())
			return new Weight(getWeightAmount() + other.getWeightAmount(), getWeightUnit());
		return add(other.toWeightUnit(getWeightUnit()));
	}
	
	/**
	 * Computes the subtraction of the other weight from this weight.
	 * 
	 * @param	other
	 * 			The weight to subtract.
	 * @return	The resulting weight is equal to the sum of this weight and the negation of the other weight.
	 * 			| result == this.add(other.negate())
	 * @throws 	IllegalArgumentException
	 * 			The other weight is invalid.
	 * 			| !isValidWeight(other)
	 */
	public Weight subtract(Weight other) throws IllegalArgumentException{
		if (!isValidWeight(other))
			throw new IllegalArgumentException("The other weight is invalid");
		if (getWeightUnit() == other.getWeightUnit())
			return new Weight(getWeightAmount() - other.getWeightAmount(), getWeightUnit());
		return subtract(other.toWeightUnit(getWeightUnit()));
	}
	
	/**
	 * Compares this weight with the other weight.
	 * 
	 * @param	other
	 * 			The other weight to compare with.
	 * @return	The result is equal to the comparison of the weight amount of
	 * 			this weight with the weight amount of the other weight.
	 * 			| result == Double.compare(this.getWeightAmount(), other.toWeightUnit(this.getWeightUnit()).getWeightAmount())
	 * @throws	ClassCastException
	 * 			The other weight is invalid.
	 * 			| !isValidWeight(other)
	 */
	@Override
	public int compareTo(Weight other) throws ClassCastException{
		if (!isValidWeight(other))
			throw new ClassCastException("The other weight is invalid");
		return Double.compare(this.getWeightAmount(), other.toWeightUnit(this.getWeightUnit()).getWeightAmount());
	}
	
	/**
	 * Returns the signum of this weight.
	 * 
	 * @return	The signum of the weight amount of this weight.
	 * 			| result == Math.signum(getWeightAmount())
	 */
	public double signum() {
		return Math.signum(getWeightAmount());
	}
	
	/**
	 * Checks whether this weight has the same value as the other weight.
	 * 
	 * @param	other
	 * 			The other weight to compare with.
	 * @return	True if and only if this weight is equal to the other weight expressed
	 * 			in the weight unit of this weight.
	 * 			| result == this.equals(other.toWeightUnit(getWeightUnit())
	 * @throws	IllegalArgumentException
	 * 			The other weight is invalid.
	 * 			| !isValidWeight(other)
	 */
	public boolean hasSameValueAs(Weight other) throws IllegalArgumentException{
		if (!isValidWeight(other))
			throw new IllegalArgumentException("The other weight is invalid.");
		return this.equals(other.toWeightUnit(getWeightUnit()));
	}
	
	/**
	 * Checks whether this weight is equal to the given object.
	 * 
	 * @return	True if and only if the given object is effective,
	 * 			if this weight and the given object belong to the
	 * 			same class, and if this weight and the other object
	 * 			interpreted as a weight have equal weight amounts
	 * 			and equal weight units.
	 * 			| ( (other != null)
	 * 			| && (this.getClass() == other.getClass())
	 * 			| && (this.getWeightAmount() == other.getWeightAmount())
	 * 			| && (this.getWeightUnit() == other.getWeightUnit()) )
	 */
	@Override
	public boolean equals(Object other){
		if (other == null)
			return false;
		if (this.getClass() != other.getClass())
			return false;
		Weight otherWeight = (Weight)other;
		return ( getWeightAmount() == otherWeight.getWeightAmount()
				&& getWeightUnit() == otherWeight.getWeightUnit() );
	}
	
	/**
	 * Returns the hash code for this weight.
	 * 
	 * @return	Returns the hash code for this weight.
	 * 			| result == Double.valueOf(getWeightAmount()).hashCode() + getWeightUnit().hashCode()
	 */
	@Override
	public int hashCode(){
		return Double.valueOf(getWeightAmount()).hashCode() + getWeightUnit().hashCode();
	}
}
