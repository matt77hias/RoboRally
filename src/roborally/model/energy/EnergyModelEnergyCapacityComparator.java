package roborally.model.energy;

import java.util.Comparator;

/**
 * A comparator for the energy capacity limit of energy models. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class EnergyModelEnergyCapacityComparator implements Comparator<EnergyModel>{

	/**
	 * Compares the energy capacity limits of the two given energy models for order.
	 * Returns a negative integer, zero, or a positive integer
	 * as the energy capacity limit of the first energy model is less than, equal to, or
	 * greater than the energy capacity limit of the second energy model.
	 * 
	 * @param 	o1
	 * 			The first energy model.
	 * @param 	o2
	 * 			The second energy model.
	 * @return	Returns a positive integer
	 * 			as the energy capacity limit of the first energy model is greater
	 * 			than the energy capacity limit of the second energy model.
	 * 			| if(o1.getAccu().getEnergyCapacityLimit().subtract(o2.getAccu().getEnergyCapacityLimit()).getEnergyAmount() > 0.0)
	 * 			| then Math.signum(result) == 1
	 * @return	Returns a positive integer
	 * 			as the energy capacity limit of the first energy model is less
	 * 			than the energy capacity limit of the energy model.
	 * 			| if(o1.getAccu().getEnergyCapacityLimit().subtract(o2.getAccu().getEnergyCapacityLimit()).getEnergyAmount() < 0.0)
	 * 			| then Math.signum(result) == -1
	 * @return	Returns a positive integer
	 * 			as the energy capacity limit of the first energy model is equal
	 * 			to the energy capacity limit of the second energy model.
	 * 			| if(o1.getAccu().getEnergyCapacityLimit().subtract(o2.getAccu().getEnergyCapacityLimit()).getEnergyAmount() == 0.0)
	 * 			| then Math.signum(result) == 0
	 * @throws	NullpointerException
	 * 			At least one of the given arguments refers
	 * 			the null reference.
	 * 			| (o1 == null || o2 == null)
	 */
	@Override
	public int compare(EnergyModel o1, EnergyModel o2) throws NullPointerException {
		if(o1 == null || o2 == null)
			throw new NullPointerException();
		return o1.getAccu().getEnergyCapacityLimit().compareTo(o2.getAccu().getEnergyCapacityLimit());
	}
}
