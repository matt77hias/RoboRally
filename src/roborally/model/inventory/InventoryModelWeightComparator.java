package roborally.model.inventory;

import java.util.Comparator;
import roborally.model.inventory.item.InventoryModel;

/**
 * A comparator for the weight of inventory models. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InventoryModelWeightComparator implements Comparator<InventoryModel>{

	/**
	 * Compares the weights of the two given inventory models for order.
	 * Returns a negative integer, zero, or a positive integer
	 * as the first inventory model's weight is less than, equal to, or
	 * greater than the second inventory model's weight.
	 * 
	 * @param 	o1
	 * 			The first inventory model.
	 * @param 	o2
	 * 			The second inventory model.
	 * @return	If both given inventory models are non terminated instances of the collector,
	 * 			the total weight (inventory model + inventory) of the first inventory model is
	 * 			compared to the total weight (inventory model + inventory) of the second inventory model.
	 * 			| if(!o1.isTerminated() && !o2.isTerminated() && Collector.class.isInstance(o1) && Collector.class.isInstance(o2))
	 * 			| then result == (o1.getWeight().add(o1.getInventory().getTotalWeightOfInventoryItems(o1.getWeight().getWeightUnit())))
	 * 			|				 	.compareTo(o2.getWeight().add(o2.getInventory().getTotalWeightOfInventoryItems(o2.getWeight().getWeightUnit())))
	 * @return	If the first given inventory model is an non terminated instance of the collector class,
	 * 			the total weight (inventory model + inventory) of the first inventory model is compared to
	 * 			the weight of the second inventory model.
	 * 			| if(!o1.isTerminated() && Collector.class.isInstance(o1))
	 * 			| 	then result == (o1.getWeight().add(o1.getInventory().getTotalWeightOfInventoryItems(o1.getWeight().getWeightUnit())))
	 * 			|					.compareTo(o2.getWeight())
	 * @return	If the second given inventory model is an non terminated instance of the collector class,
	 * 			the weight of the first inventory model is compared to
	 * 			the total weight (inventory model + inventory) of the second inventory model.
	 * 			| if(!o2.isTerminated() && Collector.class.isInstance(o2))
	 * 			|	then result == o1.getWeight()
	 * 			|				   	.compareTo(o2.getWeight().add(o2.getInventory().getTotalWeightOfInventoryItems(o2.getWeight().getWeightUnit())))
	 * @return	In all other cases is the weight of the first inventory model compared to the weight
	 * 			of the second inventory model.
	 * 			| result == o1.getWeight().compareTo(o2.getWeight())
	 * @throws	NullpointerException
	 * 			At least one of the given arguments refers
	 * 			the null reference.
	 * 			| (o1 == null || o2 == null)
	 */
	@Override
	public int compare(InventoryModel o1, InventoryModel o2) throws NullPointerException {
		if(o1 == null || o2 == null)
			throw new NullPointerException();
		if(!o1.isTerminated() && !o2.isTerminated() && Collector.class.isInstance(o1) && Collector.class.isInstance(o2))
			return	(o1.getWeight().add(((Collector) o1).getInventory().getTotalWeightOfInventoryItems(o1.getWeight().getWeightUnit()))).compareTo(o2.getWeight().add(((Collector) o2).getInventory().getTotalWeightOfInventoryItems(o2.getWeight().getWeightUnit())));
		if(!o1.isTerminated() && Collector.class.isInstance(o1))
			return (o1.getWeight().add(((Collector) o1).getInventory().getTotalWeightOfInventoryItems(o1.getWeight().getWeightUnit()))).compareTo(o2.getWeight());
		if(!o2.isTerminated() && Collector.class.isInstance(o2))
			return	o1.getWeight().compareTo(o2.getWeight().add(((Collector) o2).getInventory().getTotalWeightOfInventoryItems(o2.getWeight().getWeightUnit())));
		return o1.getWeight().compareTo(o2.getWeight());
	}
}
