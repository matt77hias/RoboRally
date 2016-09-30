package roborally.model.staticObject;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.*;
import roborally.model.*;

/**
 * A class of walls.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Wall extends BoardModel{
	
	/**
	 * Initializes a new wall.
	 * 
	 * @effect	This new wall is situated on no board
	 * 			with no position.
	 * 			| this(null, null)
	 */
	@Raw
	public Wall(){
		this(null, null);
	}
	
	/**
	 * Initializes this new wall with given board and position.
	 * 
	 * @param 	board
	 * 			The board where this new wall is situated on.
	 * @param 	position
	 * 			The position of this new wall on the given board.
	 * @effect	This new wall is situated on the given board
	 * 			on the given position.
	 * 			| super(board, position)
	 */
	@Raw
	public Wall(Board board, Position position) 
			throws IllegalArgumentException{
		super(board, position);
	}
	
	/**
	 * Terminates this wall.
	 * 
	 * @post	This wall becomes terminated.
	 * 			| new.isTerminated() == true
	 */
	@Override
	public void terminate(){
		super.terminate();
	}
	
	/**
	 * Checks if this wall could share its position with the given board model.
	 * 
	 * @param 	request
	 * 			The board model that has to be checked.
	 * @return	True if and only if this wall could share its position
	 * 		   	with the given board model.
	 * 			A wall could share no position with another board model.
	 * 			| result == false
	 * @note	This means this method doesn't include a board, position
	 * 			or termination check.
	 */
	@Override @Raw
	public boolean canSharePositionWith(BoardModel request){
		return false;
	}
	
	/**
	 * Hits this wall because of a fired shot.
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This wall could not be shot.");
	}
}
