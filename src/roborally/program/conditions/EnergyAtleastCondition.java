package roborally.program.conditions;

import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Energy;
import roborally.program.languageelement.ParameterizedBasicLanguageElement;

/**
 * A class representing of at-least-energy conditions
 * 
 * @invar	The energy condition of this energy at least condition must be effective.
 * 			| getEnergyCondition() != null
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 */
public class EnergyAtleastCondition extends ParameterizedBasicLanguageElement<Energy> implements Condition {

	/**
	 * Initializes a new energy at least condition with the given energy at least condition.
	 * 
	 * @param 	energyCondition
	 * 			The new energy condition to use for this new energy at least condition.
	 * @effect  This new energy at least condition is initialized as a
	 *         	parameterized basic language element with
	 *         	the given energy condition as parameter.
	 *      	| super(energyCondition)
	 */
	public EnergyAtleastCondition(Energy energyCondition)
			throws IllegalArgumentException {
		super(energyCondition);
	}

	/**
	 * Returns the result of this energy at least condition.
	 * 
	 * @return	Returns true iff the energy of the given robot is higher
	 * 			than the energy of this energy at least condition.
	 * 			| if (robot.getEnergy().compareTo(getParameter()) >= 0)
	 * 			|	then result == true
	 * @return	Returns true iff the energy of the given robot is lower
	 * 			than the energy of this energy at least condition.
	 * 			| if (robot.getEnergy().compareTo(getParameter()) < 0)
	 * 			|	then result == true
	 */
	@Override
	public boolean result(Robot robot) 
			throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if(robot.getEnergy().compareTo(getParameter()) < 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Returns a string representation of this condition.
	 * 
	 * @return	Returns "energy-at-least " plus the energy parameter.
	 * 			| result.equals("energy-at-least " + getParameter().getEnergyAmount())
	 */
	@Override
	public String toString(){
		return "energy-at-least " + getParameter().getEnergyAmount();
	}
}
