package roborally.model.staticObject;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.Board;
import roborally.board.Position;
import roborally.model.BoardModel;
import roborally.model.dynamicObject.Robot;

/**
 * A class of action triggers.
 * 
 * @invar	Every non interface collector must extends the BoardModel class,
 * 			or one of its subclasses.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface ActionTrigger{
	
	/**
	 * Checks if this action trigger could share its position with the given board model.
	 * 
	 * @param 	request
	 * 			The board model that has to be checked.
	 * @return	True if and only if this action trigger could share its position
	 * 		   	with the given board model.
	 * 			An action trigger could only share its position with a robot.
	 * 			| result == Robot.class.isInstance(request)
	 * @note	This means this method doesn't include a board, position
	 * 			or termination check.
	 */
	@Raw
	public boolean canSharePositionWith(BoardModel request);
	
	/**
	 * Triggers off this action trigger.
	 * 
	 * @param 	robot
	 * 			The robot who triggered off this action trigger.
	 * @throws	The board of this action trigger must refer
	 * 			the board of the given robot.
	 * 			| getBoard() != robot.getBoard()
	 * @throws	IllegalStateException
	 * 			This action trigger and the given robot
	 * 			may not be terminated.
	 * 			| isTerminated() || robot.isTerminated()
	 * @throws	IllegalArgumentException
	 * 			The position of this action trigger must be
	 * 			equal to the position of the given robot, or
	 * 			both must refer null reference.
	 * 			| (getPosition() == null && robot.getPosition() != null)
	 * 			| || (getPosition().equals(robot.getPosition()))
	 * @note	Because of the invariant, every action trigger
	 * 			is an instance of the BoardModel class and so
	 * 			every action trigger implements the Terminatable
	 * 			interface. This means that the method 'isTerminated()'
	 * 			is a valid method for an action trigger.
	 */
	public void triggerOff(Robot robot)
			throws IllegalStateException, IllegalArgumentException;
	
	/**
	 * Returns the board where this action trigger is situated on.
	 */
	@Basic @Raw
	public Board getBoard();
	
	/**
	 * Returns the position of the board where this action trigger is situated on.
	 */
	@Basic @Raw
	public Position getPosition();
}
