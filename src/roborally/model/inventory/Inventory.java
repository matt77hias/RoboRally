package roborally.model.inventory;

import java.util.*;

import be.kuleuven.cs.som.annotate.*;
import roborally.Terminatable;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;
import roborally.model.energy.EnergyModel;
import roborally.model.energy.EnergyModelEnergyCapacityComparator;
import roborally.model.inventory.item.*;
import roborally.model.inventory.Collector;

/**
 * A class of inventories involving all the aspects related
 * to the inventory management.
 * 
 * @invar	Every inventory must have proper inventory items.
 * 			| hasProperInventoryItems()
 * @invar	The inventory items of every inventory are sorted
 * 			by weight in ascending order.
 * 			| hasProperInventoryItemOrder()
 * @invar	The collector of every inventory must be a valid collector.
 * 			| canHaveAsCollector(getCollector)
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Inventory implements Terminatable{

	/**
	 * Initializes a new inventory with given collector.
	 * 
	 * @param 	collector
	 * 			The new collector for this new inventory.
	 * @post	This new inventory is not terminated.
	 * 			| new.isTerminated == false
	 * @effect	Sets the collector of this new inventory
	 * 			to the given collector.
	 * 			| setCollector(collector)
	 */
	@Raw
	public Inventory(Collector collector)
			throws IllegalArgumentException{
		this.isTerminated = false;
		setCollector(collector);
	}
	
	/**
	 * Terminates this inventory and all its inventory items.
	 * 
	 * @effect	If this inventory is not yet terminated
	 * 			and its collector is terminated,
	 * 			all its inventory items become terminated.
	 * 			| if(!isTerminated() && getCollector().isTerminated())
	 * 			| 	then for each item in getAllInventoryItems()  :
	 * 			| 		removeAndTerminateInventoryItem(item)
	 * @post	If this inventory is not yet terminated
	 * 			and its collector is terminated or referring the null reference,
	 * 			this inventory becomes terminated.
	 * 			| if(!isTerminated() && (getCollector() == null || getCollector().isTerminated()))
	 * 			| 	then new.isTerminated() == true
	 * @post	If this inventory is not yet terminated
	 * 			and its collector is terminated or referring the null reference,
	 * 			the new collector of this inventory refers the null reference.
	 * 			| if(!isTerminated() && (getCollector() == null || getCollector().isTerminated()))
	 * 			| 	then new.getCollector() == null	
	 */
	@Override
	public void terminate() {
		if(!isTerminated() && getCollector().isTerminated()){
			while(inventoryItems.size() != 0){
				removeAndTerminateInventoryItem(inventoryItems.get(0));
			}
			this.isTerminated = true;
			setCollector(null);
		}
	}
	
	/**
	 * Checks whether this inventory is terminated.
	 */
	@Basic @Raw @Override
	public boolean isTerminated(){
		return this.isTerminated;
	}
	
	/**
	 * Variable registering whether this inventory is terminated.
	 */
	private boolean isTerminated;
	
	/**
	 * Checks if the given collector is a valid collector.
	 * 
	 * @param 	request
	 * 			The collector that has to be checked.
	 * @return	If this inventory is terminated,
	 * 			the request must refer the null reference.
	 * 			| if(isTerminated()) then
	 * 			| 	result == (request == null)
	 * @return	If this inventory is not terminated,
	 * 			the collector is valid if it doesn't
	 * 			refer the null reference and if it's not terminated.
	 * 			| if(!isTerminated()) then
	 * 			| 	result == (request != null) && (!request.isTerminated())
	 */
	@Raw
	public boolean canHaveAsCollector(Collector request){
		if(isTerminated())
			return request == null;
		else
			return (request != null) && (!request.isTerminated());
	}
	
	/**
	 * Returns the collector of this inventory.
	 */
	@Basic @Raw
	public Collector getCollector(){
		return this.collector;
	}
	
	/**
	 * Sets the collector of this inventory to the given collector.
	 * 
	 * @param 	request
	 * 			The new collector for this inventory.
	 * @post	If this inventory can have the given request as collector,
	 * 			its collector is set to the given request.
	 * 			| if(canHaveAsCollector(request)) then
	 * 			| 	new.getCollector() == request
	 * @throws	IllegalArgumentException
	 * 			The given request in invalid.
	 * 			| !canHaveAsCollector(request)
	 */
	@Raw @Model
	private void setCollector(Collector request){
		if(!canHaveAsCollector(request))
			throw new IllegalArgumentException("The given collector is in invalid.");
		this.collector = request;
	}
	
	/**
	 * Variable storing the collector of this inventory.
	 */
	private Collector collector;
	
	/**
	 * Checks whether the given inventory model is a valid inventory item.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model that has to be checked.
	 * @return	True if and only if the given inventory model does not refer
	 * 			the null reference, is not terminated and not picked up already.
	 * 			| result == (inventoryModel != null && !inventoryModel.isTerminated() && !inventoryModel.isPickedUp())
	 */
	public static boolean isValidInventoryItem(InventoryModel inventoryModel){
		// Because of the pick up operation on an inventory model
		// every inventory model could only be added once. 
		return inventoryModel != null && !inventoryModel.isTerminated() && !inventoryModel.isPickedUp();
	}
	
	/**
	 * Returns whether the collector of this inventory can have
	 * the given inventory model as one of its possessions.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model that has to be checked to check
	 * @return	Always returns false if the collector of this inventory is terminated.
	 * 			| if (isTerminated()) then
	 * 			| result == false
	 * @return 	Always returns false if the given inventory model is not a valid inventory model.
	 * 			| if (!isValidInventoryItem(inventoryModel)) then 
	 * 			| result == false
	 * @return 	If the collector of this inventory is not located on a board then returns true
	 * 			if and only if the given inventory model is also not located on a board.
	 * 			| if (getCollector().getBoard() == null) then 
	 * 			| result == (inventoryModel.getBoard() == null)
	 * @return 	If the collector of this inventory has a board then returns true
	 * 			if and only if the collector of this inventory and the given inventory model
	 * 			are located on the same board and have the same position
	 * 			| if (getCollector().getBoard() != null) then
	 * 			| result == (getCollector().getBoard() == inventoryModel.getBoard()
	 * 			|			&& getCollector().getPosition().equals(inventoryModel.getPosition()))
	 */
	@Raw
	public boolean canHaveAsInventoryItem(InventoryModel inventoryModel){
		if(isTerminated())
			return false;
		if(!isValidInventoryItem(inventoryModel))
			return false;
		// This makes it easy to create Collectors with non empty inventory
		// before adding to a board.
		if(getCollector().getBoard() == null)
			return (inventoryModel.getBoard() == null);
		else
			return (getCollector().getBoard() == inventoryModel.getBoard() && getCollector().getPosition().equals(inventoryModel.getPosition()));
	}
	
	/**
	 * Checks if this inventory has proper inventory items.
	 * 
	 * @return	If this inventory is terminated, its inventory
	 * 			items collection must refer the null reference.
	 * 			| if(isTerminated()) then
	 * 			| result == (getAllInventoryItems().size() == 0);
	 * @return	If this inventory is not terminated,
	 * 			it must have proper inventory items.
	 * 			| if(!isTerminated()) then
	 * 			| for each model in inventoryItems :
	 * 			| 	if(!model.isPickedUp() || model.isTerminated()) then
	 * 			| 	result == false
	 * @return	In all other cases the return value is only true
	 * 			if no duplicate inventory items exist in this inventory.
	 * 			| result == hasNoDuplicateInventoryItems()
	 */
	@Raw
	public boolean hasProperInventoryItems(){
		if(isTerminated())
			return inventoryItems.size() == 0;
		else
			for(InventoryModel model : inventoryItems)
				if(!model.isPickedUp() || model.isTerminated())
					return false;
		return hasNoDuplicateInventoryItems();
	}
	
	/**
	 * Checks if this inventory doesn't contain duplicate inventory models.
	 * 
	 * @return 	True if and only if there exist no duplicate inventory models
	 * 			in this inventory.
	 * 			| result == 
	 * 			|	for each i in [1..getNbOfInventoryItems()] and
	 * 			| 	for each j in [i+1..getNbOfInventoryItems()] :
	 * 			| 		getInventoryItemAt(i) != getInventoryItemAt(j)
	 * @note	Duplicate inventory models could impossible exist
	 * 			if an add operation includes a pick up operation which
	 * 			changes correctly the pick up state of the added inventory model.
	 */
	@Model @Raw
	private boolean hasNoDuplicateInventoryItems(){
		Set<InventoryModel> noDuplicates = new HashSet<InventoryModel>(inventoryItems);
		return noDuplicates.size() == inventoryItems.size();
	}
	
	/**
	 * Returns whether this inventory possess the given inventory model.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model to check.
	 * @return	Returns true if and only if this inventory possesses
	 * 			the given inventory model.
	 * 			| result == (getAllInventoryItems().contains(inventoryModel))
	 */
	@Raw
	public boolean hasAsInventoryItem(InventoryModel inventoryModel){
		return (inventoryItems.contains(inventoryModel));
	}
	
	/**
	 * Adds the given inventory model to this inventory.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model that have to be add to this inventory.
	 * @effect	The given inventory model is picked up, if it can
	 * 			have the given inventory model as an inventory item.
	 * 			| if(canHaveAsInventoryItem(inventoryModel)) then
	 * 			| 	inventoryModel.pickUp()
	 * @post	The given inventory model is added to this inventory,
	 * 			if it can have the given inventory model as an inventory item.
	 * 			| if(canHaveAsInventoryItem(inventoryModel)) then
	 * 			| 	new.getAllInventoryItems().contains(inventoryModel)
	 * @post	After an executed add operation the inventory items
	 * 			of this inventory become sorted again.
	 * 			| new.hasProperInventoryItemsOrder() == true
	 * @throws	IllegalStateException
	 * 			The collector of this inventory is picked up.
	 * 			| if(InventoryModel.class.isInstance(getCollector()))
	 * 			| 	then getCollector().isPickedUp()
	 * @throws	IllegalArgumentException
	 * 			The given inventory model is invalid.
	 * 			| !canHaveAsInventoryItem(inventoryModel)
	 */
	public void addInventoryItem(InventoryModel inventoryModel)
			throws IllegalStateException, IllegalArgumentException{
		if(InventoryModel.class.isInstance(getCollector()))
			if(((InventoryModel) getCollector()).isPickedUp())
				throw new IllegalStateException("The collector of this inventory is picked up.");
		if(!canHaveAsInventoryItem(inventoryModel))
			throw new IllegalArgumentException("The given inventory model could not be added to this inventory.");
		inventoryModel.pickUp();
		inventoryItems.add(inventoryModel);
		sortInventoryItemsByWeight();
	}
	
	/**
	 * Transfers the given inventory model from the given 'transfer' inventory
	 * to this 'collector' inventory. 
	 * 
	 * @param 	inventoryModel
	 * 			The inventory that has to be transferred from the 'transfer' inventory.
	 * @param 	inventory
	 * 			The transfer inventory.
	 * @post	The given inventory model becomes part of this inventory
	 * 			and is not owned anymore by the given inventory.
	 * 			| !new.inventory.hasAsInventoryItem(inventoryModel)
	 * 			| && (new this).hasAsInventoryItem(inventoryModel)
	 * @post	After the executed add operation the inventory items of
	 * 			this inventory become sorted again.
	 * 			| new.hasProperInventoryItemsOrder() == true
	 * @throws	IllegalArgumentException
	 * 			This inventory may not be terminated.
	 * 			| isTerminated()
	 * @throws 	IllegalArgumentException
	 * 			The given inventory may not refer the null reference.
	 * 			The given inventory may not be terminated.
	 * 			| inventory == null || inventory.isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The given inventory may not refer this inventory.
	 * 			| inventory == this
	 * @throws	IllegalArgumentException
	 * 			The given inventory model must be part of the given inventory.
	 * 			| !inventory.hasAsInventoryItem(inventoryModel)
	 */
	public void addInventoryItem(InventoryModel inventoryModel, Inventory inventory)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This inventory is terminated.");
		if(inventory == null || inventory.isTerminated())
			throw new IllegalArgumentException("Transfer could not start.");
		if(inventory == this)
			throw new IllegalArgumentException("The transfer inventory refers this inventory.");
		if(!inventory.hasAsInventoryItem(inventoryModel))
			throw new IllegalArgumentException("The given inventory model could not be transferred.");
		
		inventoryItems.add(inventoryModel);
		inventory.inventoryItems.remove(inventoryModel);
		sortInventoryItemsByWeight();
	}
	
	/**
	 * Transfers all the inventory model of the given 'transfer' inventory
	 * to this 'collector' inventory. 
	 * 
	 * @param 	inventory
	 * 			The transfer inventory.
	 * @post	All the inventory models of the given inventory
	 * 			becomes part of this inventory
	 * 			and are not owned anymore by the given inventory.
	 * 			| for each inventoryModel in inventory.getAllInventoryItems() :
	 * 			| 	!new.inventory.hasAsInventoryItem(inventoryModel)
	 * 			| 	&& (new this).hasAsInventoryItem(inventoryModel)
	 * @post	After all the executed add operations the inventory items
	 * 			of this inventory become sorted again.
	 * 			| new.hasProperInventoryItemsOrder() == true
	 * @throws	IllegalArgumentException
	 * 			This inventory may not be terminated.
	 * 			| isTerminated()
	 * @throws 	IllegalArgumentException
	 * 			The given inventory may not refer the null reference.
	 * 			The given inventory may not be terminated.
	 * 			| inventory == null || inventory.isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The given inventory may not refer this inventory.
	 * 			| inventory == this
	 */
	public void addInventoryItemsFrom(Inventory inventory)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated())
			throw new IllegalStateException("This inventory is terminated.");
		if(inventory == null || inventory.isTerminated())
			throw new IllegalArgumentException("Transfer could not start.");
		if(inventory == this)
			throw new IllegalArgumentException("The transfer inventory refers this inventory.");
		
		while(inventory.getNbOfInventoryItems() > 0){
			InventoryModel item = inventory.getInventoryItemAt(1);
			inventoryItems.add(item);
			inventory.inventoryItems.remove(item);
		}
		sortInventoryItemsByWeight();
	}
	
	/**
	 * Adds all the inventory models, situated on the position and board of 
	 * the collector of this inventory, to this inventory.
	 * 
	 * @effect	Adds all the inventory models, situated on the position and board of 
	 * 			the collector of this inventory, to this inventory.
	 * 			| if(getCollector().getBoard().getBoardModelsClassAt(getCollector().getPosition(), InventoryModel.class).size() > 0) then
	 * 			| 	for each model in getCollector().getBoard().getBoardModelsClassAt(getCollector().getPosition(), InventoryModel.class):
	 * 			| 		model.pickUp() && inventoryItems.add(model)
	 * @post	After all the executed add operations the inventory items
	 * 			of this inventory become sorted again. (If add operations
	 * 			are not executed during this method no resort method is 
	 * 			needed or executed.)
	 * 			| new.hasProperInventoryItemsOrder() == true
	 * @throws	IllegalStateException
	 * 			This inventory is terminated.
	 * 			| isTerminated
	 * @throws	IllegalStateException
	 * 			The collector of this inventory is not located on a board.
	 * 			| getCollector().getBoard() == null
	 * @note	This method sorts the inventory items of this inventory just once.
	 * 			(Even if more inventory items are added.)
	 */
	public void addInventoryItemsOfPosition()
			throws IllegalStateException{
		if(isTerminated())
			throw new IllegalStateException("This inventory is terminated.");
		if(getCollector().getBoard() == null)
			throw new IllegalStateException("The collector of this inventory is not located on a board.");
		
		List<InventoryModel> inventoryModelList = getCollector().getBoard().getBoardModelsClassAt(getCollector().getPosition(), InventoryModel.class);
		if (inventoryModelList.size() > 0){			
			for(InventoryModel model : inventoryModelList){
				model.pickUp();
				inventoryItems.add(model);
			}
			sortInventoryItemsByWeight();
		}
	}
	
	/**
	 * Removes the given inventory model from this inventory 
	 * and drops it on the position on the board of the collector
	 * of this inventory if it's still useful.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model that has to be removed from this inventory.
	 * @post	The given inventory model is removed from this inventory.
	 * 			| !new.getAllInventoryItems().contains(inventoryModel)
	 * @effect	If given the inventory model is part of this inventory,
	 * 			the given inventory model is dropped on the board and position
	 * 			of the collector of this inventory.
	 * 			| if(getAllInventoryItems().contains(inventoryModel))
	 * 			| 	then inventoryModel.drop(getCollector().getBoard(), getCollector().getPosition())
	 * @throws	IllegalStateException
	 * 			The collector of this inventory is picked up.
	 * 			| if(InventoryModel.class.isInstance(getCollector()))
	 * 			| 	then getCollector().isPickedUp()
	 */
	public void removeInventoryItem(InventoryModel inventoryModel)
			throws IllegalStateException{
		if(InventoryModel.class.isInstance(getCollector()))
			if(((InventoryModel) getCollector()).isPickedUp())
				throw new IllegalStateException("The collector of this inventory is picked up.");
		
		if(hasAsInventoryItem(inventoryModel)){
			inventoryItems.remove(inventoryModel);
			inventoryModel.drop(getCollector().getBoard(), getCollector().getPosition());
		}
	}
	
	/**
	 * Removes and terminates the given inventory model from this inventory.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model that has to be removed and terminated from this inventory.
	 * @post	The given inventory model is removed from this inventory.
	 * 			| !new.getAllInventoryItems().contains(inventoryModel)
	 * @effect	The given inventory model is dropped on null reference board
	 * 			and null reference position, which results in termination.
	 * 			| if(getAllInventoryItems().contains(inventoryModel)) then
	 * 			| inventoryModel.drop(null, null)
	 * @throws	IllegalStateException
	 * 			The collector of this inventory is picked up.
	 * 			| if(InventoryModel.class.isInstance(getCollector()))
	 * 			| 	then getCollector().isPickedUp()
	 */
	public void removeAndTerminateInventoryItem(InventoryModel inventoryModel)
			throws IllegalStateException{
		if(InventoryModel.class.isInstance(getCollector()))
			if(((InventoryModel) getCollector()).isPickedUp())
				throw new IllegalStateException("The collector of this inventory is picked up.");
		
		if(hasAsInventoryItem(inventoryModel)){
			inventoryItems.remove(inventoryModel);
			inventoryModel.drop(null, null);
		}
	}
	
	/**
	 * Let the collector of this inventory uses the given inventory model.
	 * 
	 * @param 	inventoryModel
	 * 			The inventory model to use
	 * @effect	The collector of this inventory uses the given inventory model,
	 * 			if its an instance of the inventory user class;
	 * 			then if the inventory model has been terminated
	 * 			the item will be removed from this inventory.
	 * 			| if(InventoryUser.class.isInstance(getCollector()))
	 * 			| 	then (inventoryModel.use(getCollector()) &&
	 * 			| 	if (new.inventoryModel.isTerminated())
	 * 			|		then removeAndTerminateInventoryItem(new.inventoryModel))
	 * @throws	IllegalArgumentException
	 * 			This inventory doesn't contain the given inventory model.
	 * 			| !hasAsInventoryModel(inventoryModel)
	 */
	public void useInventoryItem(InventoryModel inventoryModel) throws IllegalArgumentException{
		if (!hasAsInventoryItem(inventoryModel))
			throw new IllegalArgumentException("This inventory doesn't contain the given inventory model.");
		if(InventoryUser.class.isInstance(getCollector())){
			inventoryModel.use((InventoryUser) getCollector());
			if(inventoryModel.isTerminated())
				removeAndTerminateInventoryItem(inventoryModel);
		}
	}
	
	/**
	 * Returns the inventory model at the given index in this inventory.
	 * 
	 * @param 	index
	 * 			The index of the inventory model that has to be returned.
	 * @return	Returns the inventory model at the given index.
	 * 			| getAllInventoryItems().get(index)
	 * @throws	IndexOutOfBoundsException
	 * 			The given index doesn't correspond with an existing index.
	 * 			| (index<=0 || index>getNbOfInventoryItems())
	 * @note	This method operation is executed in constant time.
	 * @note	An existing index is situated in [1..getNbOfInventoryItems()]
	 */
	public InventoryModel getInventoryItemAt(int index)
			throws IndexOutOfBoundsException{
		if(index<=0 || index>getNbOfInventoryItems())
			throw new IndexOutOfBoundsException("Rejected index: " + index);
		return inventoryItems.get(index-1);
	}
	
	/**
	 * Returns the ith heaviest inventory model in this inventory.
	 * 
	 * @param 	i
	 * 			The number of heaviest inventory model that has to be returned.
	 * @return	Returns the ith heaviest inventory model. Because the inventory models
	 * 			of this inventory are sorted by weight in ascending order. The i
	 * 			heaviest board model is situated at index getNbOfInventoryItems()-index.
	 * 			| getAllInventoryItems().get(getNbOfInventoryItems()-index)
	 * @throws	IndexOutOfBoundsException
	 * 			(getNbOfInventoryItems()-index) doesn't correspond with an existing index.
	 * 			| getNbOfInventoryItems()<i || i<=0
	 * @note	This method operation is executed in constant time.
	 */
	public InventoryModel getHeaviestInventoryItemAt(int i)
			throws IndexOutOfBoundsException{
		if(getNbOfInventoryItems()<i || i<=0){
			int t = getNbOfInventoryItems()-i;
			throw new IndexOutOfBoundsException("Rejected index: " + t);
		}
		return inventoryItems.get(getNbOfInventoryItems()-i);
	}
	
	/**
	 * Returns the total weight of this inventory.
	 * 
	 * @param 	unit
	 * 			The weight unit for the return value.
	 * @return	Returns the total weight of this inventory
	 * 			as a weight of the given weight unit.
	 * 			If one of the inventory items is an instance
	 * 			of the collector class, its own weight plus
	 * 			the total weight of the inventory of that collector
	 * 			is taken into account.
	 * 			| temp = new Weight(0.0, unit)
	 * 			| for each model in getAllInventoryItems() :
	 * 			|	let
	 * 			|		w = model.getWeight()
	 * 			|		if(Collector.class.isInstance(model))
	 * 			|			then w = w.add(model.getInventory().getTotalWeightOfInventoryItems(w.getWeightUnit()))
	 * 			|	in :
	 * 			|	temp = temp.add(w)
	 * 			| result.equals(new.temp)
	 * 			| && result.getWeightUnit() == unit
	 * @throws	IllegalArgumentException
	 * 			The given weight unit is not valid.
	 * 			| !WeightUnit.isValidWeightUnit(unit)
	 */
	public Weight getTotalWeightOfInventoryItems(WeightUnit unit){
		if(!WeightUnit.isValidWeightUnit(unit))
			throw new IllegalArgumentException("The given weight unit is not valid.");
		Weight temp = new Weight(0.0, unit);
		for(InventoryModel model : inventoryItems){
			Weight w = model.getWeight();
			if(Collector.class.isInstance(model))
				w = w.add(((Collector) model).getInventory().getTotalWeightOfInventoryItems(w.getWeightUnit()));
			temp = temp.add(w);
		}
		return temp;
	}
	
	/**
	 * Checks if the inventory models of this inventory are sorted
	 * by weight in ascending order.
	 * 
	 * @return	True if and only if the inventory model collection of this
	 * 			inventory is sorted by weight in ascending order.
	 * 			| for each i in [0..getNbOfInventoryItems()-1] and
	 * 			| for each j in [i+1..getNbOfInventoryItems()-1] :
	 * 			|	if(getAllInventoryItems().get(i).getWeight().compareTo(getAllInventoryItems().get(j).getWeight()) > 0)
	 * 			|		then result == false
	 * 			| else result == true
	 */
	public boolean hasProperInventoryItemOrder(){
		for(int i=0; i<getNbOfInventoryItems(); i++){
			for(int j=i+1; j<getNbOfInventoryItems(); j++){
				if(inventoryItems.get(i).getWeight().compareTo(inventoryItems.get(j).getWeight()) > 0)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Sorts the inventory models of this inventory by weight. (ascending order)
	 * 
	 * @effect	Sorts the inventory models of this inventory by weight.
	 * 			| Collections.sort(getAllInventoryItems(), new InventoryModelWeightComparator())
	 */
	@Model
	private void sortInventoryItemsByWeight(){
		Collections.sort(inventoryItems, new InventoryModelWeightComparator());
	}
	
	/**
	 * Returns an unmodifiable view of all the inventory models of this inventory.
	 */
	@Basic @Raw
	public List<InventoryModel> getAllInventoryItems(){
		return Collections.unmodifiableList(inventoryItems);
	}
	
	/**
	 * Returns the amount of inventory models stored in this accu.
	 * 
	 * @return	Returns the amount of inventory items stored in this accu.
	 * 			| getAllInventoryItems().size()
	 */
	@Raw
	public int getNbOfInventoryItems(){
		return inventoryItems.size();
	}
	
	/**
	 * Returns the amount of inventory models of the given class stocked in this accu.
	 * 
	 * @param 	inventoryModelType
	 * 			The class of inventory model types.
	 * @return	Returns the amount of inventory items stored in this accu.
	 * 			| getAllInventoryItemsClass(inventoryModelType).size()
	 */
	@Raw
	public <T extends InventoryModel> int getNbOfInventoryItemsClass(Class<T> inventoryModelType){
		return getAllInventoryItemsClass(inventoryModelType).size();
	}
	
	/**
	 * Returns all the inventory models of the given class that are stocked in this inventory.
	 * 
	 * @param 	inventoryModelType
	 * 			The class of inventory model types that must be returned.
	 * @return	The result contains every inventory model that is of the given
	 * 			subclass, that are stocked in this inventory
	 * 			| for each T extends InventoryModel model in getAllInventoryItems() :
	 * 			| result.contains(model)
	 * @return	The result only contains inventory models of the given subclass
	 * 			| for each T extends InventoryModel model in result :
	 * 			| inventoryModelType.isInstance(model)
	 */
	@Raw
	public <T extends InventoryModel> Set<T> getAllInventoryItemsClass(Class<T> inventoryModelType){
		Set<T> temp = new HashSet<T>();
		for (InventoryModel im : inventoryItems){
			if (inventoryModelType.isInstance(im))
				temp.add(inventoryModelType.cast(im));
		}
		return Collections.unmodifiableSet(temp);
	}
	
	/**
	 * Returns all the energy models of this inventory sorted by energy capacity. (ascending order)
	 * 
	 * @return 	Returns all the energy models of this inventory
	 * 			| for each em in result : hasAsInventoryItem(em) == true &&
	 * 			| there exists no im in getAllInventoryItems for which
	 * 			| EnergyModel.class.isInstance(im) && !result.contains(im)
	 * 			sorted by energy capacity. (ascending order)
	 * 			| for each i in [0..result.size()-1] and
	 * 			| for each j in [i..result.size()-1] :
	 * 			| result.get(i).getAccu().getEnergyCapacityLimit()
	 * 			|		.subtract(get(j).getAccu().getEnergyCapacityLimit()).getEnergyAmount() <= 0.0
	 */
	public List<EnergyModel> getOptimalEnergyModelOrder(){
		List<EnergyModel> temp = new ArrayList<EnergyModel>();
		for (InventoryModel im : inventoryItems){
			if (EnergyModel.class.isInstance(im))
				temp.add(EnergyModel.class.cast(im));
		}
		Collections.sort(temp,new EnergyModelEnergyCapacityComparator());
		return Collections.unmodifiableList(temp);
	}
	
	/**
	 * Set collection storing all inventory models stocked in this inventory.
	 */
	private List<InventoryModel> inventoryItems = new ArrayList<InventoryModel>();
	
	/**
	 * Returns a string representation of this inventory.
	 */
	@Override
	public String toString(){
		if (inventoryItems.size() == 0)
			return "<empty>";
		else{
			StringBuilder sb = new StringBuilder();
			for (InventoryModel im : inventoryItems)
				sb.append(im.toString()+"|");
			return sb.toString();
		}
	}
}
