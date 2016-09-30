package roborally.model.inventory.item;

import java.util.Random;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.*;
import roborally.model.BoardModel;
import roborally.model.inventory.InventoryUser;
import roborally.model.weight.*;

/**
 * A class of teleport gates.
 * 
 * @invar	The teleport range for every teleport gate
 * 			must be a valid teleport range.
 * 			| isValidTeleportRange(getTeleportRange())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TeleportGate extends InventoryModel{

	/**
	 * The standard teleport gate weight.
	 */
	public static final Weight STANDARD_TELEPORT_GATE_WEIGHT = new Weight(100, WeightUnit.G);
	
	/**
	 * Initializes a new teleport gate.
	 * 
	 * @effect	This new teleport gate is situated on no board
	 * 			with no position and the standard teleport range
	 * 			as teleport range.
	 * 			| this(null, null, STANDARD_TELEPORT_RANGE)
	 */
	@Raw
	public TeleportGate(){
		this(null, null, STANDARD_TELEPORT_RANGE);
	}
	
	/**
	 * Initializes a new teleport gate.
	 * 
	 * @param	teleportRange
	 * 			The teleport range for this new teleport gate.
	 * @effect	This new teleport gate is situated on no board
	 * 			with no position and the given teleport range
	 * 			as teleport range.
	 * 			| this(null, null, teleportRange);
	 */
	@Raw
	public TeleportGate(int teleportRange){
		this(null, null, teleportRange);
	}
	
	/**
	 * Initializes this new teleport gate with given board and position.
	 * 
	 * @param 	board
	 * 			The board where this new teleport gate is situated on.
	 * @param 	position
	 * 			The position of this new teleport gate on the given board.
	 * @param	teleportRange
	 * 			The teleport range for this new teleport gate.
	 * @effect	This new teleport gate is situated on the given board
	 * 			on the given position, with the standard teleport gate
	 * 			weight as its weight.
	 * 			| super(board, position, STANDARD_TELEPORT_GATE_WEIGHT)
	 * @effect	Sets the teleport range of this teleport gate to the given
	 * 			teleport range.
	 * 			| setTeleportRange(teleportRange)
	 */
	@Raw
	public TeleportGate(Board board, Position position, int teleportRange) 
			throws IllegalArgumentException{
		super(board, position, STANDARD_TELEPORT_GATE_WEIGHT);
		setTeleportRange(teleportRange);
	}
	
	/**
	 * Teleportes the given board model to another position on its board.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to be teleportated
	 * @post	A random position is generated in the standard area (the standard
	 * 			teleport range as radius and the position of this teleport gate as
	 * 			center) till the given board model could be added to that random position
	 * 			on its board. If such position could not be found after K iterations (where
	 * 			K is equal to the teleport area), the teleport gate becomes terminated.
	 * 			This operation could only be executed if the board of this
	 * 			teleport gate doesn't refer the null reference, otherwise nothing happens.
	 * 			| if(getBoard() != null)
	 * 			|	then 	new.boardModel.getPosition().getCoordinate(Dimension.HORIZONTAL) >= (getPosition().getCoordinate(Dimension.HORIZONTAL) - getTeleportRange())
	 * 			|			&& new.boardModel.getPosition().getCoordinate(Dimension.HORIZONTAL) <= (getPosition().getCoordinate(Dimension.HORIZONTAL) + getTeleportRange())
	 * 			|		 	&& new.boardModel.getPosition().getCoordinate(Dimension.VERTICAL) >= (getPosition().getCoordinate(Dimension.VERTICAL) - getTeleportRange())
	 * 			|			&& new.boardModel.getPosition().getCoordinate(Dimension.VERTICAL) <= (getPosition().getCoordinate(Dimension.VERTICAL) + getTeleportRange())
	 * 			|			&& boardModel.getBoard().canHaveAsCoordinates(new.boardModel.getPosition().getCoordinates())	
	 * @effect	If no random generated position in K iterations satisfies the previous
	 * 			conditions, this teleport gate becomes terminated.
	 * 			| if(getBoard() != null)
	 * 			|	then 	if(new.boardModel.getPosition().equals(boardModel.getPosition())
	 * 			|				then terminate()
	 * @throws 	NullPointerException
	 * 			The given board model refers the null reference.
	 * 			| boardModel == null
	 * @throws 	IllegalStateException
	 * 			This teleport gate or given board model is ineffective.
	 * 			| isTerminated() || boardModel.isTerminated()
	 * @throws	IllegalArgumentException
	 * 			Teleportation is not possible, because or the given
	 * 			board model and the teleporte gate are located on different boards
	 * 			or the given board model and the teleporte gate are not located on
	 * 			the same position.
	 * 			| (getBoard() != boardModel.getBoard()) ||
	 * 			| (getPosition() == null && getPosition() != boardModel.getPosition()) ||
	 * 			| (getPosition() != null && !getPosition().equals(boardModel.getPosition()))
	 */
	public void teleport(BoardModel boardModel)
			throws NullPointerException, IllegalStateException, IllegalArgumentException{
		if(boardModel == null)
			throw new NullPointerException("The given board model refers the null reference.");
	    if(isTerminated() || boardModel.isTerminated())	
	    	throw new IllegalStateException("This teleport gate or given board model is ineffective.");
	    if(getBoard() != boardModel.getBoard())
			throw new IllegalArgumentException("Teleportation is not possible.");
	    if((getPosition() == null && getPosition() != boardModel.getPosition()) || (getPosition() != null && !getPosition().equals(boardModel.getPosition())))
			throw new IllegalArgumentException("Teleportation is not possible.");
		
		if(getBoard() != null){
			boolean succes = false;
			Position random = new Position();
			int i = 1;
			int K = (getTeleportRange()+1+getTeleportRange())*(getTeleportRange()+1+getTeleportRange());
			while(!succes && i<=K){
				random = generateTeleportPosition();
				if(getBoard().canHaveBoardModelAtNoBindingCheck(random, boardModel)){
					getBoard().moveBoardModelTo(boardModel, random);
					succes = true;
				}
				i++;
			}
			if(!succes)
				terminate();
		}
	}
	
   /**
 	* The standard teleport range for teleport gates.
 	*/
	public static final int STANDARD_TELEPORT_RANGE = 5;
	
	/**
	 * The maximum teleport range for teleport gates.
	 */
	public static final int MAX_TELEPORT_RANGE = 10;
	
	/**
	 * 
	 * @param 	request
	 * 			The request that has to be checked.
	 * @return	True if and only if the given request is positive
	 * 			and less than or equal to the maximum teleport range
	 * 			for teleport gates.
	 * 			| result == (request > 0) && (request <= MAX_TELEPORT_RANGE)
	 */
	public static boolean isValidTeleportRange(int request){
		return (request > 0) && (request <= MAX_TELEPORT_RANGE);
	}
	
	/**
	 * Sets the given request to the teleport range of this
	 * teleport gate.
	 * 
	 * @param 	request
	 * 			The request teleport range.
	 * @post	If the given request's absolute value is greater
	 * 			than the maximum teleport range for teleport gates,
	 * 			the teleport range of this teleport gate is set
	 * 			to the maximum teleport range for teleport gates.
	 * 			If the given request is equal to zero, the teleport
	 * 			range of this teleport gate is set to one.
	 * 			In all other cases the teleport range of this
	 * 			teleport gate is set to the absolute value of the request.
	 * 			| if(Math.abs(request) > MAX_TELEPORT_RANGE)
	 * 			| 	then getImpactSize() == MAX_TELEPORT_RANGE
	 * 			| if(request == 0)
	 * 			|	then getImpactSize() == 1
	 * 			| else
	 * 			|	getTeleportRange() == Math.abs(request)
	 */
	@Raw
	public void setTeleportRange(int request){
		if(Math.abs(request) > MAX_TELEPORT_RANGE)
			request = MAX_TELEPORT_RANGE;
		if(request == 0)
			request = 1;
		teleportRange = Math.abs(request);
	}
	
	/**
	 * Returns the teleport range of this teleport gate.
	 */
	@Basic @Raw
	public int getTeleportRange(){
		return teleportRange;
	}
	
	/**
	 * The teleport range of this teleport gate. The teleport area
	 * is situated around this teleport gate with the given teleport
	 * range as 'radius'.
	 */
	public int teleportRange;
	
	/**
	 * Returns a pseudo random generated position in the area around
	 * this teleport gate with the given teleport range as 'radius'.
	 * 
	 * @return	Returns a random position within the boundaries of this teleport gate.
	 * 			| (result.getPosition().getCoordinate(Dimension.HORIZONTAL) >= (getPosition().getCoordinate(Dimension.HORIZONTAL)-getTeleportRange()))
	 * 			| && (result.getPosition().getCoordinate(Dimension.HORIZONTAL) <= (getPosition().getCoordinate(Dimension.HORIZONTAL)+getTeleportRange()))
	 * 			| && (result.getPosition().getCoordinate(Dimension.VERTICAL) >= (getPosition().getCoordinate(Dimension.VERTICAL)-getTeleportRange()))
	 * 			| && (result.getPosition().getCoordinate(Dimension.VERTICAL) <= (getPosition().getCoordinate(Dimension.VERTICAL)+getTeleportRange()))
	 * @throws 	IllegalStateException
	 * 			This teleport gate is ineffective.
	 * 			| isTerminated() || getPosition() == null
	 */
	public Position generateTeleportPosition()
			throws IllegalStateException{
		if(isTerminated() || getPosition() == null)
			throw new IllegalStateException("This teleport gate is ineffective.");
			
		Random randomGenerator = new Random();
		int signx = (randomGenerator.nextBoolean() == true) ? 1 : -1;
		int signy =	(randomGenerator.nextBoolean() == true) ? 1 : -1;
		long x = getPosition().getCoordinate(Dimension.HORIZONTAL)+signx*randomGenerator.nextInt(getTeleportRange()+1);
		long y = getPosition().getCoordinate(Dimension.VERTICAL)+signy*randomGenerator.nextInt(getTeleportRange()+1);
		
		return new Position(x,y);
	}
	
	/**
	 * Hits this teleport gate because of a fired shot.
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This teleport gate could not be shot.");
	}

	/**
	 * Uses this teleport gate by the given inventory user.
	 * 
	 * @param	inventoryUser
	 * 			The inventory user that wants to use this teleport gate.
	 * @effect	If the given inventory user its board doesn't refer
	 * 			the null reference, then this teleport gate is dropped
	 * 			on the position and board of the given inventory user and
	 * 			it teleports the given inventory user and becomes afterwards
	 * 			terminated.
	 * 			| if(inventoryUser.getBoard() != null)
	 * 			| 	then inventoryUser.getInventory().removeInventoryItem(this)
	 * 			| 		&& teleport(inventoryUser)
	 * 			|		&& terminate()
	 * 			| [SEQUENTIAL]
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser)
			throws IllegalArgumentException{
		if(!canHaveAsUser(inventoryUser))
			throw new IllegalArgumentException("The given inventory user does not possess this teleport gate.");
		if(inventoryUser.getBoard() != null){
			inventoryUser.getInventory().removeInventoryItem(this);
			teleport((BoardModel) inventoryUser);
			terminate();
		}
	}
}
