package roborally.program.conditions;

import roborally.board.Position;
import roborally.model.Direction;
import roborally.model.dynamicObject.Robot;
import roborally.model.staticObject.Wall;

/**
 * A class of wall conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public final class WallCondition extends BasicCondition {
	
	/**
	 * Initializes a wall condition.
	 * 
	 * @note	The constructor is private, which means that it's
	 * 			not possible to initialize wall conditions from
	 * 			outside this class.
	 */
	private WallCondition(){
	}

	/**
	 * Constant storing one reference to a wall condition.
	 * This reference is the only wall condition that could be accessed
	 * from outside this class.
	 * [Design pattern: Singleton]
	 * 
	 * @note	This is used instead of a constructor since at this point
	 * 			It isn't really needed to be able to create multiples of this condition.
	 */
	public final static WallCondition WALL_CONDITION = new WallCondition();
	
	/**
	 * Returns the result of this wall condition.
	 * 
	 * @return	False if the robot is not located on any board.
	 * 			| if (robot.getBoard() == null)
	 *			|	then result == false
	 * @return	Let in all result clauses.
	 * 			| let 
	 * 			| 	Position toRightOfRobotPos = robot.getPosition().getNextPositionInDirection(Direction.turnDirectionClockwise(robot.getDirection()))
	 * 			| in :
	 * @return	Otherwise false if the position to the right of the robot is not occupied by any board model.
	 * 			| if(!robot.getBoard().containsPositionKey(toRightOfRobotPos))
	 * 			|	then result == false
	 * @return	Otherwise true if the position to the right of the robot is occupied by at least one wall.
	 * 			| if(robot.getBoard().getBoardModelsClassAt(toRightOfRobotPos, Wall.class).size() > 0)
	 * 			|	then result == true
	 * @return	In all other cases the result is false.
	 * 			| result == false
	 */
	@Override
	public boolean result(Robot robot) 
			throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if(robot.getBoard() == null)
			return false;
		Direction toRightOfRobotDir = Direction.turnDirectionClockwise(robot.getDirection());
		Position toRightOfRobotPos = robot.getPosition().getNextPositionInDirection(toRightOfRobotDir);
		if(!robot.getBoard().containsPositionKey(toRightOfRobotPos))
			return false;
		if(robot.getBoard().getBoardModelsClassAt(toRightOfRobotPos, Wall.class).size() > 0)
			return true;
		return false;
	}
	
	/**
	 * Returns a string representation of this wall condition.
	 * 
	 * @return	Returns "wall"
	 * 			| result.equals("wall")
	 */
	@Override
	public String toString(){
		return "wall";
	}

}
