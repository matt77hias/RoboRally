package roborally.model.staticObject;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.Board;
import roborally.board.Position;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;

/**
 * A class of victory flags.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class VictoryFlag extends BoardModel implements ActionTrigger{
	
	/**
	 * Initializes a new victory flag.
	 * 
	 * @effect	This new victory flag is situated on no board
	 * 			with no position.
	 * 			| this(null, null)
	 */
	@Raw
	public VictoryFlag(){
		this(null, null);
	}
	
	/**
	 * Initializes this new victory flag with given board and position.
	 * 
	 * @param 	board
	 * 			The board where this new victory flag is situated on.
	 * @param 	position
	 * 			The position of this new victory flag on the given board.
	 * @effect	This new victory flag is situated on the given board
	 * 			on the given position.
	 * 			| super(board, position)
	 */
	@Raw
	public VictoryFlag(Board board, Position position) 
			throws IllegalArgumentException{
		super(board, position);
	}
	
	/**
	 * Terminates this victory flag.
	 * 
	 * @post	This victory flag becomes terminated.
	 * 			| new.isTerminated() == true
	 */
	@Override
	public void terminate(){
		super.terminate();
	}
	
	/**
	 * Checks if this victory flag could share its position with the given board model.
	 * 
	 * @param 	request
	 * 			The board model that has to be checked.
	 * @return	True if and only if this victory flag could share its position
	 * 		   	with the given board model.
	 * 			A victory flag could only share its position with a robot.
	 * 			| result == Robot.class.isInstance(request)
	 * @note	This means this method doesn't include a board, position
	 * 			or termination check.
	 */
	@Override @Raw
	public boolean canSharePositionWith(BoardModel request){
		if(Robot.class.isInstance(request))
			return true;
		return false;
	}
	
	/**
	 * Hits this victory flag because of a fired shot.
	 */
	@Override
	public void hit() throws IllegalStateException{
		if(!canBeHit())
			throw new IllegalStateException("This victory flag could not be shot.");
	}
	
	/**
	 * Triggers off this victory flag.
	 * 
	 * @param 	robot
	 * 			The robot who triggered off this action trigger.
	 */
	public void triggerOff(Robot robot)
			throws IllegalStateException, IllegalArgumentException{
		if(isTerminated() || robot.isTerminated())
			throw new IllegalStateException("This victory flag and the given robot may not be terminated.");
		if(getBoard() != robot.getBoard())
			throw new IllegalArgumentException("This victory flay and the given robot must be situated on the same board.");
		if((getPosition() == null && robot.getPosition() != null) || (getPosition().equals(robot.getPosition())))
			throw new IllegalArgumentException("This victory flay and the given robot must be situated on the same position.");
	}
}
