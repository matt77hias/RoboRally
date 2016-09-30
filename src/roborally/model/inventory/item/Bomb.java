package roborally.model.inventory.item;

import java.util.HashSet;
import java.util.Set;

import roborally.board.*;
import roborally.model.BoardModel;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.weight.Weight;
import roborally.model.weight.WeightUnit;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of bombs.
 * 
 * @invar 	The impact size of every bomb must be
 * 			a valid impact size.
 * 			| isValidImpactSize(getImpactSize())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Bomb extends InventoryModel{

	/**
	 * The standard bomb weight.
	 */
	public static final Weight STANDARD_BOMB_WEIGHT = new Weight(100, WeightUnit.G);
	
	/**
	 * The standard impact size for bombs.
	 */
	public static final long STANDARD_BOMB_IMPACT_SIZE = 1L;
	
	/**
	 * Initializes a new bomb.
	 * 
	 * @effect	This new bomb is situated on no board
	 * 			with no position and with the standard
	 * 			impact size for bombs as its impact size.
	 * 			| this(null, null, STANDARD_BOMB_IMPACT_SIZE)
	 */
	@Raw
	public Bomb(){
		this(null, null, STANDARD_BOMB_IMPACT_SIZE);
	}
	
	/**
	 * Initializes a new bomb with given impact size.
	 * 
	 * @param	impactSize
	 * 			The impact size of this new bomb.
	 * @effect	This new bomb is situated on no board
	 * 			with no position and with the given
	 * 			impact size as its impact size.
	 * 			| this(null, null, impactSize)
	 */
	@Raw
	public Bomb(long impactSize){
		this(null, null, impactSize);
	}
	
	/**
	 * Initializes this new bomb with given board, position and impact size.
	 * 
	 * @param 	board
	 * 			The board where this new bomb is situated on.
	 * @param 	position
	 * 			The position of this new bomb on the given board.
	 * @param	impactSize
	 * 			The impact size of this new bomb.
	 * @effect	This new bomb is situated on the given board
	 * 			on the given position, with the standard bomb
	 * 			weight as its weight.
	 * 			| super(board, position, STANDARD_BOMB_WEIGHT)
	 * @effect	The impact size of this new bomb is set to the
	 * 			given impact size.
	 * 			| setImpactSize(impactSize)
	 */
	@Raw
	public Bomb(Board board, Position position, long impactSize) 
			throws IllegalArgumentException{
		super(board, position, STANDARD_BOMB_WEIGHT);
		setImpactSize(impactSize);
	}
	
	/**
	 * Checks if this bomb could be hit.
	 * 
	 * @return	If the given bomb is situated on
	 * 			a board referring the null reference,
	 * 			it can not be hit.
	 * 			| if(getBoard() == null)
	 * 			|	then result == false
	 */
	@Override @Raw
	public boolean canBeHit(){
		return super.canBeHit() && (getBoard() != null);
	}
	
	/**
	 * Hits this bomb because of a fired shot.
	 * 
	 * @effect	Every board model that's located in the impact area
	 * 			of the bomb on the bomb's board is hit.
	 * 			This bomb is terminated afterwards.
	 * 			| let
	 * 			| 	tempBoard = getBoard()
	 * 			|	tempArea = getImpactArea()
	 * 			| in:
	 * 			| tempBoard.removeBoardModel(this)
	 * 			| && for each pos in tempArea :
	 * 			|		if(tempBoard.getBoardModelsAt(pos) != null)
	 * 			| 			then for each model in tempBoard.getBoardModelsAt(pos)
	 * 			|				model.hit()
	 * 			| && new.terminate()
	 * 			| [SEQUENTIAL]
	 * @note	This bomb is first removed from its board in order
	 * 			to avoid never ending cascades of exploding bombs.
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This bomb could not be shot.");
		Board tempBoard = getBoard();
		Set<Position> tempArea = getImpactArea();
		
		tempBoard.removeBoardModel(this);
		for(Position pos : tempArea){
			if(tempBoard.getBoardModelsAt(pos) != null){
				Object[] models = tempBoard.getBoardModelsAt(pos).toArray();
				for(int i=0; i<models.length; i++){
					((BoardModel) models[i]).hit();
				}
			}
		}
		terminate();
	}
	
	/**
	 * Returns the impact area, meaning all the positions that this bomb could affect
	 * while exploding.
	 * 
	 * @return	A collection with all the positions inside the impact area square are returned.
	 * 			The impact square has a size of two times the result of the impact size plus one.
	 * 			| for each i from getPosition().getCoordinate(Dimension.HORIZONTAL)-getImpactSize() to getPosition().getCoordinate(Dimension.HORIZONTAL)+getImpactSize() by 1 :
	 * 			|	for each j from getPosition().getCoordinate(Dimension.VERTICAL)-getImpactSize() to getPosition().getCoordinate(Dimension.VERTICAL)+getImpactSize() by 1 :
	 * 			|		there exists a position in result :
	 * 			|			position.getCoordinate(Dimension.HORIZONTAL) == i && position.getCoordinate(Dimension.VERTICAL) == j
	 * 			| && result.size() == (getImpactSize()+1)*2
	 * @throws 	IllegalStateException
	 * 			This bomb is not effective.
	 * 			| isTerminated() || getPosition() == null
	 */
	public Set<Position> getImpactArea() throws IllegalStateException{
		if(isTerminated() || getPosition() == null)
			throw new IllegalStateException("This bomb is not effective.");
		Set<Position> impactPos = new HashSet<Position>();
		for(long i=getPosition().getCoordinate(Dimension.HORIZONTAL)-getImpactSize(); i<=getPosition().getCoordinate(Dimension.HORIZONTAL)+getImpactSize();i++){
			for(long j=getPosition().getCoordinate(Dimension.VERTICAL)-getImpactSize(); j<=getPosition().getCoordinate(Dimension.VERTICAL)+getImpactSize();j++){
				impactPos.add(new Position(i,j));
			}
		}
		return impactPos;
	}
	
	/**
	 * Checks if the given impact size is a valid impact size
	 * for a bomb.
	 * 
	 * @param 	request
	 * 			The impact size that has to be checked.
	 * @return	True if and only if the impact size is greater
	 * 			or equal to zero and less than or equal to
	 * 			the maximum impact size for bombs.
	 * 			| result == request >= 0 && request <= MAX_IMPACT_SIZE
	 */
	public static boolean isValidImpactSize(long request){
		return request >= 0 && request <= MAX_IMPACT_SIZE;
	}
	
	/**
	 * Gets the impact size of this bomb.
	 */
	@Basic @Raw
	public long getImpactSize(){
		return impactSize;
	}
	
	/**
	 * Sets the given request to the impact size of this bomb.
	 * 
	 * @param 	request
	 * 			The request impact size.
	 * @post	If the given request's absolute value
	 * 			is greater than the maximum impact size
	 * 			for bombs, the impact size of this bomb
	 * 			is set to the maximum impact size.
	 * 			In all other cases the impact size of this bomb
	 * 			is set to the absolute value of the given request.
	 * 			| if(Math.abs(request) > MAX_IMPACT_SIZE)
	 * 			| 	then getImpactSize() == MAX_IMPACT_SIZE
	 * 			| else
	 * 			|	getImpactSize() == Math.abs(request)
	 */
	@Raw
	public void setImpactSize(long request){
		if(Math.abs(request) > MAX_IMPACT_SIZE)
			request = MAX_IMPACT_SIZE;
		impactSize = Math.abs(request);
	}
	
	/**
	 * The maximum impact size of the bomb explosion area for every bomb.
	 */
	public static final long MAX_IMPACT_SIZE = 5;
	
	/**
	 * The impact size of the bomb explosion area for this bomb.
	 */
	public long impactSize;

	/**
	 * Uses this bomb by the given inventory user.
	 * 
	 * @param	inventoryUser
	 * 			The inventory user that wants to use this bomb.
	 * @effect	If the given inventory user its board doesn't refer
	 * 			the null reference, then this bomb is dropped
	 * 			on the position and board of the given inventory user,
	 * 			is hit and afterwards terminated.
	 * 			| if(inventoryUser.getBoard() != null)
	 * 			| 	then inventoryUser.getInventory().removeInventoryItem(this)
	 * 			| 		&& hit()
	 * 			|		&& terminate()
	 * 			| [SEQUENTIAL]
	 */
	@Override
	public void use(@Raw InventoryUser inventoryUser)
			throws IllegalArgumentException{
		if(!canHaveAsUser(inventoryUser))
			throw new IllegalArgumentException("The given inventory user does not possess this bomb.");
		if(inventoryUser.getBoard() != null){
			inventoryUser.getInventory().removeInventoryItem(this);
			hit();
			terminate();
		}
	}
}
