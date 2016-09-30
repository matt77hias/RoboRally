package roborally.model.inventory;

import be.kuleuven.cs.som.annotate.*;
import roborally.Terminatable;
import roborally.board.*;

/**
 * An interface that must be implemented by all classes
 * of board models with an inventory.
 * 
 * @invar	The inventory of every collector must be a valid inventory.
 * 			| hasValidInventory()
 * @invar	Every non interface collector must extends the BoardModel class,
 * 			or one of its subclasses.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface Collector extends Terminatable{
	
	/**
	 * Terminates this collector, its inventory and all its inventory items.
	 * 
	 * @post	If this collector model is not terminated before
	 * 			executing this method and becomes terminated
	 * 			after executing this method, its old inventory
	 * 			becomes also terminated and its new inventory
	 * 			refers the null reference.
	 * 			| if(!isTerminated() && new.isTerminated())
	 * 			| 	then getInventory().isTerminated() == true
	 * 			|		 && new.getInventory() == null
	 */
	@Override
	public void terminate();
	
	/**
	 * Checks if this collector has a valid inventory.
	 * 
	 * @return	If this collector is terminated,
	 * 			its inventory must refer the null reference.
	 * 			| if(isTerminated())
	 * 			|  then result == (getInventory() == null)
	 * @return	If this collector is not terminated,
	 * 			its inventory may not refer the null reference.
	 * 			| if(!isTerminated())
	 * 			| 	then result == (getInventory() != null)
	 */
	@Raw
	public boolean hasValidInventory();
	
	/**
	 * Returns the inventory of this collector.
	 */
	@Basic @Raw
	public Inventory getInventory();

	/**
	 * Returns the board where this collector is situated on.
	 */
	@Basic @Raw
	public Board getBoard();
	
	/**
	 * Returns the position of the board where this collector is situated on.
	 */
	@Raw
	public Position getPosition();
}
