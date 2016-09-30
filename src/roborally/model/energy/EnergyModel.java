package roborally.model.energy;

import be.kuleuven.cs.som.annotate.*;
import roborally.Terminatable;

/**
 * This interface should be implemented by every board model that
 * consumes and could store energy.
 * 
 * @invar	The accu for every energy model must be a valid accu
 * 			for the corresponding energy model.
 * 			| canHaveAsAccu(getAccu)
 * @invar	Every non interface energy model must extends the BoardModel class
 * 			or one of its subclasses.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface EnergyModel extends Terminatable {
	
	/**
	 * Terminates this energy model and its accu.
	 * 
	 * @post	If this energy model is not terminated before
	 * 			executing this method and becomes terminated
	 * 			after executing this method, its old accu
	 * 			becomes also terminated and its new accu
	 * 			refers the null reference.
	 * 			| if(!isTerminated() && new.isTerminated())
	 * 			| 	then getAccu().isTerminated() == true
	 * 			|		 && new.getAccu() == null
	 */
	@Override @Raw
	public void terminate();

	/**
	 * Checks if the given accu is valid for this energy model.
	 * 
	 * @param 	accu
	 * 			The accu that has to be checked.
	 * @return	If this energy model is not terminated,
	 * 			the given accu may not refer the null reference.
	 * 			| if(!this.isTerminated())
	 * 			| 	then if(accu == null)
	 * 			|		then result == false
	 * 			If this energy model is not terminated,
	 * 			the given accu may not be terminated.
	 * 			| if(!this.isTerminated())
	 * 			| 	then if(accu.isTerminated())
	 * 			|		then result == false
	 * 			If this energy model is not terminated,
	 * 			the given accu may not have an owner or if it
	 * 			has an owner, this collector must be the owner.
	 * 			This means that the accu of this collector refers
	 * 			the given accu.
	 * 			| if(!this.isTerminated())
	 * 			|	then if(!(getAccu() == accu) || (!accu.hasOwner())))
	 * 			|		then result == false
	 * @return	If this energy model is terminated,
	 * 			true if and only if the given accu refers the null reference.
	 * 			| if(this.isTerminated())
	 * 			| 	then result == (accu == null)
	 */
	@Raw
	public boolean canHaveAsAccu(Accu accu);
	
	/**
	 * Returns the accu of this energy model.
	 */
	@Basic @Raw
	public Accu getAccu();
}
