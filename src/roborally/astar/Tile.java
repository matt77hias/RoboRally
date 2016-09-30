package roborally.astar;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.cs.som.annotate.*;

import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.energy.Energy;

/**
 * A class of tiles, involving a direction, an energy and the different node values.
 * 
 * @invar	Each tile value index in the tile value indices of every
 * 			tile must be valid tile value indices.
 * 			| for each tvi in TileValueIndex.values() :
 * 			|	isValidTileValueIndex(getNodeValueAtIndex(tvi))
 * @invar	The direction of every tile must be a valid direction.
 * 			| Direction.isValidDirection(getDirection())
 * @invar	The energy of every tile must be a valid energy.
 * 			| Energy.isValidEnergy(getEnergy())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Tile implements Comparable<Tile>{

	/**
	 * An enumeration containing the different tile value indices:
	 * This contains: F, G, H, T, X, Y, PX, PY
	 * 
	 * @version	Pandora: The Robot Strikes Back
	 * @author 	Matthias Moulin & Ruben Pieters
	 *
	 */
	public enum TileValueIndex{
		F, G, H, T, X, Y, PX, PY;
		
		/**
		 * Checks if the given index is a valid tile value index.
		 * 
		 * @param 	id
		 * 			The tile value index that has to be checked.
		 * @return	True if and only if the given tile value index doesn't refer
		 * 			the null reference.
		 * 			| id != null
		 */
		public static boolean isValidTileValueIndex(TileValueIndex id) {
			return id != null;
		}
	}
	
	/**
	 * Initiates this given tile with all its values set to zero.
	 * 
	 * @post	Every value that this tile contains is equal to zero.
	 * 			| for each TileValueIndex id in TileValueIndex.values() : 
	 * 			| 	new.getNodeValueAtIndex(id).equals(BigInteger.ZERO)
	 */
	public Tile(){
		for (TileValueIndex id : TileValueIndex.values()){
			setNodeValueAtIndex(id, BigInteger.ZERO);
		}
	}
	
	/**
	 * Sets the tile value of this tile at the given index to the given value.
	 * 
	 * @param	id
	 * 			The tile value index key.
	 * @param	value
	 * 			The value that has to be set for the given tile value index.
	 * @post	The node value at the given index is equal to the given value.
	 * 			| new.getNodeValueAtIndex(id).equals(value)
	 * @throws	NullPointerException
	 * 			The value refers the null reference.
	 * 			| value == null
	 * @throws	IllegalArgumentexception
	 * 			The given tile value index is invalid.
	 * 			| !TileValueIndex.isValidTileValueIndex(id)
	 */
	public void setNodeValueAtIndex(TileValueIndex id, BigInteger value)
			throws IllegalArgumentException, NullPointerException{
		if(value == null)
			throw new NullPointerException("The value refers the null reference.");
		if(!TileValueIndex.isValidTileValueIndex(id))
			throw new IllegalArgumentException("The given tile value index is invalid.");
		nodeValues.put(id, value);
	}
	
	/**
	 * Sets the tile value of the tile value indexes G, H and F of this tile.
	 * 
	 * @param 	g
	 * 			The G tile value to set.
	 * @param	h
	 * 			The H tile value to set.
	 * @post	The node value at G is equal to the given g value.
	 * 			| new.getNodeValueAtIndex(id).equals(g)
	 * @post	The node value at H is equal to the given h value.
	 * 			| new.getNodeValueAtIndex(id).equals(h)
	 * @post	The node value at F is equal to the given g value add with the h value.
	 * 			| new.getNodeValueAtIndex(id).equals(g.add(h))
	 * @throws	NullPointerException
	 * 			At least one of the given arguments refers the null reference.
	 * 			| g == null || h == null
	 */
	public void setNodeValueGHF(BigInteger g, BigInteger h)
			throws NullPointerException{
		if(g == null || h == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		setNodeValueAtIndex(TileValueIndex.G, g);
		setNodeValueAtIndex(TileValueIndex.H, h);
		setNodeValueAtIndex(TileValueIndex.F, g.add(h));
	}
	
	/**
	 * Sets the tile value of the tile value indexes X and Y of this tile.
	 * 
	 * @param 	x
	 * 			The X tile value to set.
	 * @param	y
	 * 			The Y tile value to set.
	 * @post	The node value at G is equal to the given x value.
	 * 			| new.getNodeValueAtIndex(id).equals(x)
	 * @post	The node value at H is equal to the given y value.
	 * 			| new.getNodeValueAtIndex(id).equals(y)
	 * @throws	NullPointerException
	 * 			At least one of the given arguments refers the null reference.
	 * 			| x == null || y == null
	 */
	@Raw
	public void setNodeValueXY(BigInteger x, BigInteger y)
			throws NullPointerException{
		if(x == null || y == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		setNodeValueAtIndex(TileValueIndex.X, x);
		setNodeValueAtIndex(TileValueIndex.Y, y);
	}
	
	/**
	 * Returns the position of this tile.
	 * 
	 * @return	The position of this tile created with the X and Y tile index values
	 * 			as coordinates.
	 * 			| result.equals(new Position(getNodeValueAtIndex(TileValueIndex.X).longValue(),
	 * 			| 							 getNodeValueAtIndex(TileValueIndex.Y).longValue()))
	 */
	public Position getPosition(){
		return new Position(getNodeValueAtIndex(TileValueIndex.X).longValue(), getNodeValueAtIndex(TileValueIndex.Y).longValue());
	}
	
	/**
	 * Sets the tile value of the tile value indexes PX and PY of this tile.
	 * 
	 * @param 	px
	 * 			The PX tile value that has to be set.
	 * @param	py
	 * 			The PY tile value that has to be set.
	 * @post	The node value at PX is equal to the given px value.
	 * 			| new.getNodeValueAtIndex(id).equals(px)
	 * @post	The node value at PY is equal to the given py value.
	 * 			| new.getNodeValueAtIndex(id).equals(py)
	 * @throws	NullPointerException
	 * 			At least one of the given arguments refers the null reference.
	 * 			| px == null || py == null
	 */
	@Raw
	public void setNodeValuePXPY(BigInteger px, BigInteger py)
			throws NullPointerException{
		if(px == null || py == null)
			throw new NullPointerException("At least one of the given arguments refers the null reference.");
		setNodeValueAtIndex(TileValueIndex.PX, px);
		setNodeValueAtIndex(TileValueIndex.PY, py);
	}
	
	/**
	 * Returns the position of the parent position of this tile.
	 * 
	 * @return	Returns the position of this tile created with the PX and PY tile index values
	 * 			as coordinates.
	 * 			| result.equals(new Position(getNodeValueAtIndex(TileValueIndex.PX).longValue(),
	 * 			| 							 getNodeValueAtIndex(TileValueIndex.PY).longValue()))
	 */
	public Position getParentPosition(){
		return new Position(getNodeValueAtIndex(TileValueIndex.PX).longValue(), getNodeValueAtIndex(TileValueIndex.PY).longValue());
	}
	
	/**
	 * Sets the tile value of the tile value index T of this tile.
	 * 
	 * @param 	t
	 * 			The T tile value to set.
	 * @post	The node value at T is equal to the given t value.
	 * 			| new.getNodeValueAtIndex(id).equals(t)
	 * @throws	NullPointerException
	 * 			The given argument refers the null reference.
	 * 			| t == null
	 */
	@Raw
	public void setNodeValueT(BigInteger t)
			throws NullPointerException{
		if(t == null)
			throw new NullPointerException("The given argumentrefers the null reference.");
		setNodeValueAtIndex(TileValueIndex.T, t);
	}
	
	/**
	 * Returns the tile value corresponding to the given tile value index.
	 * 
	 * @param 	id
	 * 			The tile value index of which the value has to be returned.
	 * @return	Returns the tile value corresponding to the given tile value index.
	 * 			| result == getNodeValues().get(id)
	 * @throws	IllegalArgumentException
	 * 			The given tile value index is invalid.
	 * 			!TileValueIndex.isValidTileValueIndex(id)
	 */
	@Raw
	public BigInteger getNodeValueAtIndex(TileValueIndex id)
			throws IllegalArgumentException{
		if(!TileValueIndex.isValidTileValueIndex(id))
			throw new IllegalArgumentException("The given tile value index is invalid.");
		return nodeValues.get(id);
	}
	
	/**
	 * Returns the node values of this tile as an unmodifiable collection.
	 */
	@Basic @Raw
	public Map<TileValueIndex, BigInteger> getNodeValues(){
		return Collections.unmodifiableMap(nodeValues);
	}
	
	/**
	 * Map containing the value of a tile as value an its value index as key.
	 */
	private Map<TileValueIndex, BigInteger> nodeValues = new HashMap<TileValueIndex, BigInteger>();
	
	/**
	 * Sets the energy of this tile to the given energy.
	 * 
	 * @param 	energy
	 * 			The energy amount to set.
	 * @post	The energy is equal to the given energy.
	 * 			| new.getEnergy().equals(energy)
	 * @throws	IllegalArgumentException
	 * 			The given energy is invalid.
	 * 			| !Energy.isValidEnergy(energy)
	 */
	@Raw
	public void setEnergy(Energy energy)
			throws IllegalArgumentException{
		if(!Energy.isValidEnergy(energy))
			throw new IllegalArgumentException("The given energy is invalid.");
		this.energy = energy;
	}
	
	/**
	 * Returns the energy of this tile.
	 */
	@Basic @Raw
	public Energy getEnergy(){
		return energy;
	}
	
	/**
	 * Variable storing the energy of this tile.
	 */
	private Energy energy;
	
	/**
	 * Sets the direction of this tile to the given direction.
	 * 
	 * @param 	direction
	 * 			The new direction that has to be to set.
	 * @post	The direction of this tile is equal to the given direction.
	 * 			| new.getDirection() == direction
	 * @throws	IllegalArgumentException
	 * 			The given direction is invalid.
	 * 			| !Direction.isValidDirection(direction)
	 */
	@Raw
	public void setDirection(Direction direction)
			throws IllegalArgumentException{
		if(!Direction.isValidDirection(direction))
			throw new IllegalArgumentException("The given direction is invalid.");
		this.direction = direction;
	}
	
	/**
	 * Returns the direction of this tile.
	 */
	@Basic @Raw
	public Direction getDirection(){
		return direction;
	}
	
	/**
	 * Variable storing the direction of this tile.
	 */
	private Direction direction;

	/**
	 * Compares this tile to the given tile.
	 * 
	 * @return 	If this tile's F value is equal to the other tile's
	 * 			F value then the result is equal to this tile's energy
	 * 			compared to the other tile's energy.
	 * 			| if (getNodeValueAtIndex(TileValueIndex.F).compareTo(o.getNodeValueAtIndex(TileValueIndex.F)) == 0)
	 * 			|	then result == getEnergy().compareTo(o.getEnergy())
	 * @return	If this tile's F value is greater than the other tile's
	 * 			F value then the result is 1.
	 * 			| if (getNodeValueAtIndex(TileValueIndex.F).compareTo(o.getNodeValueAtIndex(TileValueIndex.F)) > 0)
	 *			| 	then result == 1
	 * @return 	In all other cases the return value equals minus one.
	 * 			| result == -1
	 * @throws	NullPointerException
	 * 			The given argument refers the null reference.
	 * 			| o == null
	 */
	@Override
	public int compareTo(Tile o)
			throws NullPointerException{
		if(o == null)
			throw new NullPointerException("The given argument refers the null reference.");
		BigInteger thisF = getNodeValueAtIndex(TileValueIndex.F);
		BigInteger oF = o.getNodeValueAtIndex(TileValueIndex.F);
		if (thisF.compareTo(oF) == 0){
			return getEnergy().compareTo(o.getEnergy());
		}
		else if (thisF.compareTo(oF) > 0)
			return 1;
		else
			return -1;
	}
	
	/**
	 * Returns a string representation of this tile.
	 */
	@Override
	public String toString(){
		return "X:"+getNodeValueAtIndex(TileValueIndex.X)+" Y:"+getNodeValueAtIndex(TileValueIndex.Y)
				+" F:"+getNodeValueAtIndex(TileValueIndex.F)+" E:"+energy.toString()+ " T:"+getNodeValueAtIndex(TileValueIndex.T);
	}
}
