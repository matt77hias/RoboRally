package roborally.model.energy;

import java.text.NumberFormat;

import roborally.Terminatable;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of accu's involving an amount of energy and an energy capacity limit.
 * 
 * @invar	The amount of energy stored in the accu must be a valid amount for any accu.
 * 			| canHaveAsAmountOfEnergy(getAmountOfEnergy())
 * @invar	The capacity limit of energy that can be stored in the accu must be a valid capacity
 * 			for any accu.
 * 			| isValidEnergyCapacityLimit(getEnergyCapacityLimit())
 * @invar	The original capacity limit of energy of every accu
 * 			must be a valid capacity for an accu.
 * 			| isValidEnergyCapacityLimit(getOriginalEnergyCapacityLimit())
 * @invar	If an accu has an owner there exists always an energy model object
 * 			which contains that accu as its accu.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Accu implements Terminatable{
	
	/**
	 * Variable storing the standard accu amount of energy.
	 */
	public static final Energy STANDARD_ACCU_AMOUNT_OF_ENERGY = new Energy(1000);
	
	/**
	 * Variable storing the standard accu energy capacity.
	 */
	public static final Energy STANDARD_ACCU_ENERGY_CAPACITY = new Energy(5000);

	/**
	 * Initializes a new default accu with standard property values.
	 * 
	 * @effect	This accu is initialized with an amount of energy equal to the standard
	 * 			accu amount of energy and an energy capacity limit equal to the standard accu energy capacity.
	 * 			| this(STANDARD_ACCU_AMOUNT_OF_ENERGY, STANDARD_ACCU_ENERGY_CAPACITY)
	 */
	public Accu(){
		this(STANDARD_ACCU_AMOUNT_OF_ENERGY, STANDARD_ACCU_ENERGY_CAPACITY);
	}
	
	/**
	 * Initializes this new accu with given amount of energy and an energy capacity limit of 5000 Ws.
	 * 
	 * @pre		The given amount of energy stored in the new accu must be a valid amount.
	 * 			| isValidAmountOfEnergy(amountOfEnergy,new Energy(5000))	
	 * @param 	amountOfEnergy
	 * 			The amount of energy that has to be stored in this new accu.
	 * @effect	This accu is initialized with the given amount of energy and
	 * 			an energy capacity limit equal to the standard accu energy capacity.
	 * 			| this(amountOfEnergy, STANDARD_ACCU_ENERGY_CAPACITY)
	 */
	public Accu(Energy amountOfEnergy){
		this(amountOfEnergy, STANDARD_ACCU_ENERGY_CAPACITY);
	}
	
	/**
	 * Initializes this new accu with given amount of energy and energy capacity limit.
	 * 
	 * @pre		The given amount of energy stored in the new accu must be a valid amount.
	 * 			| isValidAmountOfEnergy(amountOfEnergy,energyCapacityLimit)
	 * @param	amountOfEnergy
	 * 			The amount of energy that has to be stored in this new accu.
	 * @param 	energyCapacityLimit
	 * 			The energy capacity limit for this new accu.
	 * @post	The new amount of energy stored in this accu is equal 
	 * 			to the given value for the amount of energy.
	 * 			| new.getAmountOfEnergy().equals(amountOfEnergy)
	 * @post	The new capacity limit of energy for this accu 
	 * 			and the new original capacity limit of energy for this accu
	 * 			are equal to the given value for the capacity limit of energy.
	 * 			| new.getEnergyCapacityLimit().equals(energyCapacityLimit)
	 * 			| new.getOriginalEnergyCapacityLimit().equals(energyCapacityLimit)
	 * @post	The new accu is not terminated.
	 * 			| new.isTerminated() == false
	 * @post	The new accu is not owned.
	 * 			| new.hasOwner() == false
	 */
	public Accu(Energy amountOfEnergy, Energy energyCapacityLimit){
		setEnergyCapacityLimit(energyCapacityLimit);
		this.originalEnergyCapacityLimit = energyCapacityLimit;
		setAmountOfEnergy(amountOfEnergy);
		this.isTerminated = false;
		setOwnerState(false);
	}
	
	/**
	 * Terminates this accu.
	 * 
	 * @post	If this accu is not terminated yet and has no owner,
	 * 			it becomes terminated.
	 * 			| if(!isTerminated() && !hasOwner()) then
	 * 			| 	new.isTerminated() == true
	 * @post	If this accu is not terminated yet and has no owner,
	 * 			its new amount of energy is equal to zero.
	 * 			| if(!isTerminated() && !hasOwner()) then
	 * 			| 	new.getAmountOfEnergy().getEnergyAmount() == 0.0
	 */
	@Raw @Override
	public void terminate() {
		if(!isTerminated() && !hasOwner()){
			dischargeAmountOfEnergy(getAmountOfEnergy());
			this.isTerminated = true;
		}
	}
	
	/**
	 * Checks whether this accu is terminated.
	 */
	@Basic @Raw @Override
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether accu is terminated.
	 */
	private boolean isTerminated;
	
	/**
	 * Checks whether the given energy request is a valid amount of energy according
	 * the given capacity request for any accu.
	 * 
	 * @pre		The given capacity request is a valid energy capacity 
	 * 			limit for any accu.
	 * 			| isValidEnergyCapacity(capacityRequest)
	 * @param 	energyRequest		
	 * 			The amount of energy that has to be checked.
	 * @param 	capacityRequest	
	 * 			The energy capacity limit that's used as reference.
	 * @return	True if and only if the given capacity request is a valid energy capacity 
	 * 			limit for any accu and if the energy request is a valid energy and if its
	 * 			amount of energy is zero or positive and less than or equal to the given capacity request.
	 * 			| result == (isValidEnergyCapacityLimit(capacityRequest)) && (Energy.isValidEnergy(energyRequest)) &&
			   	|			(energyRequest.getEnergyAmount() >= 0.0) && (energyRequest.compareTo(capacityRequest) <= 0)
	 */
	public static boolean isValidAmountOfEnergy(Energy energyRequest, Energy capacityRequest){
		return (isValidEnergyCapacityLimit(capacityRequest)) && (Energy.isValidEnergy(energyRequest)) &&
			   (energyRequest.getEnergyAmount() >= 0.0) && (energyRequest.compareTo(capacityRequest) <= 0);
	}
	
	/**
	 * Checks whether this accu can have the given request as its amount of energy.
	 * 
	 * @param 	request	
	 * 			The amount of energy that has to be checked
	 * @return	True if and only if the request is valid and if
	 * 			its amount of energy is zero or positive
	 * 			and less than or equal to the capacity limit of energy of this accu.
	 * 			| result == (Energy.isValidEnergy(request)) && (request.getEnergyAmount() >=0.0) &&
	 * 			|			(request.compareTo(getEnergyCapacityLimit()) <= 0)
	 */
	@Raw
	public boolean canHaveAsAmountOfEnergy(Energy request){
		return (Energy.isValidEnergy(request)) && (request.getEnergyAmount() >=0.0) && (request.compareTo(getEnergyCapacityLimit()) <= 0);
	}
	
	/**
	 * Returns the energy of this accu.
	 */
	@Basic @Raw
	public Energy getAmountOfEnergy(){
		return amountOfEnergy;
	}

	/**
	 * Returns the percentage of energy stored in this accu according to
	 * its energy capacity limit.
	 * 
	 * @return 	The percentage of energy stored in this accu according
	 * 		   	its energy capacity limit.
	 * 		   	| result == (getAmountOfEnergy().getEnergyAmount()/
	 * 			|			(getEnergyCapacityLimit().toEnergyUnit(getAmountOfEnergy().getEnergyUnit()).getEnergyAmount()*100
	 */
	public double getEnergyStoragePercentage(){
		double energy = getAmountOfEnergy().getEnergyAmount();
		double capacity = getEnergyCapacityLimit().toEnergyUnit(getAmountOfEnergy().getEnergyUnit()).getEnergyAmount();
		return energy/capacity * 100;
	}
	
	/**
	 * Converts the given request to a percentage output String.
	 * 
	 * @param 	request
	 * 			The request that has to be converted to a percentage output String.
	 * @return	| result.equals(NumberFormat.getPercentInstance().format(request))
	 */
	public static String getPercentage(double request){
		return NumberFormat.getPercentInstance().format(request);
	}
	
	/**
	 * Sets the amount of energy of this accu to the given amount.
	 * 
	 * @pre  	The given amount must be a valid amount of energy for an accu.
	 * 		 	| isValidAmountOfEnergy(amount, getEnergyCapacityLimit())
	 * @param 	amount
	 * 			The new value for the amount of energy of this accu.
	 * @post 	The value of the given amount is the new value for the amount of energy stored in this accu.
	 * 		 	| new.getAmountOfEnergy() == amount
	 */
	@Raw @Model
	private void setAmountOfEnergy(Energy amount){
		assert(isValidAmountOfEnergy(amount, getEnergyCapacityLimit()));
		amountOfEnergy = amount;
	}
	
	/**
	 * Recharges the amount of energy stored in this accu by the given recharge amount.
	 * 
	 * @pre		The recharge amount must be a valid energy.
	 * 			| Energy.isValidEnergy(rechargeAmount)
	 * @pre		The recharge amount's energy amount has to be zero or positive.
	 * 			| rechargeAmount.getEnergyAmount() >= 0.0
	 * @pre 	The sum of the amount already stored in this robot and the given recharge amount
	 * 			must be a possible amount of energy for this accu.
	 * 			| canHaveAsAmountOfEnergy(getAmountOfEnergy().add(rechargeAmount))
	 * @param 	rechargeAmount	
	 * 			The amount of energy to add to the amount of energy stored in this accu.
	 * @effect 	The amount of energy of this accu is set to the sum of the amount already stored 
	 *         	in this accu and the given recharge amount.
	 *         	| setAmountOfEnergy(getAmountOfEnergy().add(rechargeAmount))
	 */
	@Raw
	public void rechargeAmountOfEnergy(Energy rechargeAmount){
		assert(Energy.isValidEnergy(rechargeAmount));
		assert(rechargeAmount.getEnergyAmount() >= 0.0);
		assert(canHaveAsAmountOfEnergy(getAmountOfEnergy().add(rechargeAmount)));
		setAmountOfEnergy(getAmountOfEnergy().add(rechargeAmount));
	}
	
	/**
	 * Discharges the amount of energy stored in this accu by the given discharge amount.
	 * 
	 * @pre		The discharge amount must be a valid energy.
	 * 			| Energy.isValidEnergy(dischargeAmount)
	 * @pre		The discharge amount's energy amount has to be zero or positive.
	 * 			| dischargeAmount.getEnergyAmount() >= 0.0
	 * @pre 	The given discharge amount must be less than or equal to
	 * 			the amount of energy stored in this accu.
	 * 			| canHaveAsAmountOfEnergy(getAmountOfEnergy().subtract(dischargeAmount))
	 * @param 	dischargeAmount	
	 * 			The amount of energy to extract of the amount of energy stored in this accu.
	 * @effect 	The amount of energy of this accu is set to the difference of the amount already stored 
	 *         	in this accu and the given discharge amount.
	 *         	| setAmountOfEnergy(getAmountOfEnergy().subtract(dischargeAmount))
	 */
	@Raw
	public void dischargeAmountOfEnergy(Energy dischargeAmount){
		assert(Energy.isValidEnergy(dischargeAmount));
		assert(dischargeAmount.getEnergyAmount() >= 0.0);
		assert(canHaveAsAmountOfEnergy(getAmountOfEnergy().subtract(dischargeAmount)));
		setAmountOfEnergy(getAmountOfEnergy().subtract(dischargeAmount));
	}
	
	/**
	 * The energy stored in this accu.
	 */
	private Energy amountOfEnergy;
	
	/**
	 * Checks whether the given request is a valid capacity limit of energy for this accu.
	 * 
	 * @param 	request	
	 * 			The capacity that has to be checked
	 * @return 	True if and only if the request is a valid energy
	 * 			and if the request's energy amount is positive and not zero.
	 * 			| result == (Energy.isValidEnergy(request)) && (request.getEnergyAmount() > 0)
	 */
	public static boolean isValidEnergyCapacityLimit(Energy request){
		return (Energy.isValidEnergy(request)) && (request.getEnergyAmount() > 0);
	}
	
	/**
	 * Returns the capacity limit of energy for this accu.
	 * 	The capacity limit of energy expresses the maximum value of energy which
	 * 	can be stored in the accu. 
	 * 
	 * @return
	 */
	@Basic @Raw
	public Energy getEnergyCapacityLimit(){
		return energyCapacityLimit;
	}
	
	/**
	 * Sets the capacity limit of energy of this accu to the given capacity.
	 * 
	 * @pre  	The given capacity must be a valid capacity limit of energy for this accu.
	 * 		 	| isValidEnergyCapacityLimit(capacity)
	 * @param 	capacity
	 * 			The new value for the capacity limit of energy of this accu.
	 * @post 	The given value capacity is the new value for the capacity limit of energy of this accu.
	 * 		 	| new.getEnergyCapacityLimit() == capacity
	 * @effect	If the amount of energy stored in this accu is greater than the
	 * 			given capacity, the new amount of energy is equal to the given
	 * 			capacity of energy.
	 * 			| if(capacity.compareTo(getAmountOfEnergy()) < 0) then
	 * 			| 	new.getAmountOfEnergy() == capacity
	 */
	@Raw
	public void setEnergyCapacityLimit(Energy capacity){
		assert(isValidEnergyCapacityLimit(capacity));
		if(getAmountOfEnergy() != null && capacity.compareTo(getAmountOfEnergy()) < 0.0D){
			setAmountOfEnergy(capacity);
		}
		energyCapacityLimit = capacity;
	}
	
	/**
	 * The capacity limit of energy indicates how much energy could be stored maximum in this accu.
	 * 
	 * @note	The capacity limit of energy isn't necessary constant in time and possibly differs
	 *  		from accu to accu.
	 */
	private Energy energyCapacityLimit;
	
	/**
	 * Returns the original capacity limit of energy of this accu.
	 * 
	 * @note	The original capacity limit of energy of an accu is
	 * 			equal to its energy capacity limit used in the
	 * 			initialization process of an accu.
	 */
	@Basic @Raw @Immutable
	public Energy getOriginalEnergyCapacityLimit(){
		return originalEnergyCapacityLimit;
	}
	
	/**
	 * The original capacity limit of energy of this accu.
	 */
	private final Energy originalEnergyCapacityLimit;
	
	/**
	 * Checks whether this accu is owned by a board model.
	 * 
	 * @return	True if and only if this accu is owned by a board model.
	 * 			| result == hasOwner
	 */
	@Basic @Raw
	public boolean hasOwner(){
		return hasOwner;
	}
	
	/**
	 * This accu becomes owned by a model.
	 * 
	 * @post	The accu is owned by a model.
	 * 			| new.hasOwner() == true
	 */
	public void own(){
		setOwnerState(true);
	}
	
	/**
	 * Change the owner state of this accu.
	 * 
	 * @param 	request
	 * 			The owner state.
	 * 			True in case of owner, false in case of no owner.
	 * @post	Sets the request to the owner state of this accu.
	 * 			| new.hasOwner = request
	 */
	@Raw
	private void setOwnerState(boolean request){
		hasOwner = request;
	}
	
	/**
	 * Detaches this accu from the given energy model. This means
	 * that this accu is not owned anymore.
	 * 
	 * @param 	energyModel
	 * 			The energy model who owns this accu.
	 * @post	This accu is not owned anymore.
	 * 			This means that this accu could be terminated from now on.
	 * 			| new.hasOwner = false
	 * @throws 	NullPointerException
	 * 			The given energy model refers the null reference.
	 * 			| energyModel == null
	 * @throws 	IllegalArgumentException
	 * 			The given energy model must be terminated
	 * 			and must be the owner of this accu.
	 * 			| !energyModel.isTerminated() || energyModel.getAccu() != this
	 */
	public void detachFromEnergyModel(@Raw EnergyModel energyModel)
			throws NullPointerException, IllegalArgumentException{
		if(energyModel == null)
			throw new NullPointerException("The given energy model refers the null reference.");
		if(!energyModel.isTerminated() || energyModel.getAccu() != this)
			throw new IllegalArgumentException("Detaching is not allowed.");
		setOwnerState(false);
	}
	
	/**
	 * Variable registering whether this accu is owned by
	 * a model.
	 */
	private boolean hasOwner;
	
	/**
	 * Checks if the given accu is a valid accu.
	 * 
	 * @param 	request
	 * 			The accu that has to be checked.
	 * @return	True if and only if the given accu
	 * 			doesn't refer the null reference.
	 * 			| result == (request != null);
	 */
	@Raw
	public static boolean isValidAccu(Accu request){
		return request != null;
	}
	
	/**
	 * Checks if the given accu is a valid transfer accu.
	 * 
	 * @param 	request
	 * 			The accu that has to be checked.
	 * @return	True if and only if the given accu is not terminated.
	 * 			| result == isValidAccu(request) && !request.isTerminated();
	 */
	public static boolean isValidTransferAccu(Accu request){
		return isValidAccu(request) && !request.isTerminated();
	}
	
	/**
	 * The given accu transfers as much energy as possible
	 * to this accu.
	 * 
	 * Transfer: amount of energy -> amount of energy
	 * 
	 * @pre		The given accu must be a valid transfer accu.
	 * 			| isValidTransferAccu(accu)
	 * @param 	accu
	 * 			The supply accu.
	 * @effect	The given accu transfers as much energy as possible
	 * 			to this accu.
	 * 			| if (canHaveAsAmountOfEnergy(getAmountOfEnergy().add(accu.getAmountOfEnergy())))
	 * 			| 	then 	rechargeAmountOfEnergy(accu.getAmountOfEnergy())
	 * 			| 			&& accu.dischargeAmountOfEnergy(accu.getAmountOfEnergy())
	 * 			| 	else	rechargeAmountOfEnergy(getEnergyCapacityLimit().subtract(getAmountOfEnergy()))
	 * 			| 			&& accu.dischargeAmountOfEnergy(getEnergyCapacityLimit().subtract(getAmountOfEnergy()))
	 * @post	If the given accu doesn't contain energy
	 * 			after the transfer the given accu becomes terminated.
	 * 			| if (canHaveAsAmountOfEnergy(getAmountOfEnergy().add(accu.getAmountOfEnergy())))
	 * 			| 	then new.accu.isTerminated() == true
	 */
	public void transferEnergyFrom(Accu accu){
		assert(isValidTransferAccu(accu));
		if (canHaveAsAmountOfEnergy(getAmountOfEnergy().add(accu.getAmountOfEnergy()))){
			rechargeAmountOfEnergy(accu.getAmountOfEnergy());
			accu.dischargeAmountOfEnergy(accu.getAmountOfEnergy());
			
			// If the termination doesn't happen in this method
			// there is no possibility to execute accu-accu operations
			// without energy models that own them.
			
			// imaginary owner drop
			accu.setOwnerState(false);
			accu.terminate();
		}
		else{
			Energy energyDiff = getEnergyCapacityLimit().subtract(getAmountOfEnergy());
			rechargeAmountOfEnergy(energyDiff);
			accu.dischargeAmountOfEnergy(energyDiff);
		}
	}
	
	/**
	 * The given accu transfers as much energy as possible, while taking
	 * the given efficiency factor into account, to this accu in order to
	 * heal this accu by upgrading its energy capacity limit.
	 * 
	 * Transfer: amount of energy -> energy capacity limit
	 * 
	 * @pre		The given accu must be a valid transfer accu.
	 * 			| isValidTransferAccu(accu)
	 * @param 	accu
	 * 			The supply accu.
	 * @param 	efficiencyFactor
	 * 			The efficiency factor for healing.
	 * @effect	This accu's energy capacity limit is upgraded with the amount of energy
	 * 			stocked in the given accu multiplied by the given efficiency factor.
	 * 			This upgrade proceeds only till the new energy capacity limit of this accu
	 * 			is equal to its original energy capacity limit or till the given accu could
	 * 			not provide more energy.
	 * 			| let
	 * 			|	diff = getOriginalEnergyCapacityLimit().subtract(getEnergyCapacityLimit())
	 * 			|	load = accu.getAmountOfEnergy().multiply(efficiencyFactor)
	 * 			| in:	
	 * 			| if(diff.compareTo(Energy.WS_0)>0)
	 * 			|	then if(load.compareTo(diff)<=0)
	 * 			|		then 	setEnergyCapacityLimit(getEnergyCapacityLimit().add(load))
	 * 			|				&& accu.dischargeAmountOfEnergy(load.multiply(1/efficiencyFactor))
	 * 			|		else 	setEnergyCapacityLimit((getOriginalEnergyCapacityLimit()))
	 * 			|				&& accu.dischargeAmountOfEnergy(diff.multiply(1/efficiencyFactor))
	 * @post	If the given accu could supply all its energy to this accu, it becomes terminated.
	 * 			| if(diff.compareTo(Energy.WS_0)>0)
	 * 			|	then if(load.compareTo(diff)<=0)
	 * 			|		then new.accu.isTerminated() == true
	 */
	public void transferHealingFrom(Accu accu, double efficiencyFactor){
		assert(isValidTransferAccu(accu));
		
		Energy diff = getOriginalEnergyCapacityLimit().subtract(getEnergyCapacityLimit());
		Energy load = accu.getAmountOfEnergy().multiply(efficiencyFactor);
		
		if(diff.compareTo(Energy.WS_0)>0){
			if(load.compareTo(diff)<=0){
				setEnergyCapacityLimit(getEnergyCapacityLimit().add(load));
				
				// If the termination doesn't happen in this method
				// there is no possibility to execute accu-accu operations
				// without energy models that own them.
				
				// imaginary owner drop
				accu.setOwnerState(false);
				accu.terminate();
			}
			else{
				setEnergyCapacityLimit((getOriginalEnergyCapacityLimit()));
				accu.dischargeAmountOfEnergy(diff.multiply(1/efficiencyFactor));
			}
		}
	}
}
