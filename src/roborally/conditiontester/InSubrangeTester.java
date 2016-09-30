package roborally.conditiontester;

import be.kuleuven.cs.som.annotate.*;
import roborally.board.Position;
import roborally.model.BoardModel;

/**
 * A condition tester that tests if a board model is situated
 * in a subrange area of its board around a certain position and
 * with a certain subrange.
 * 
 * @invar	The position of every in subrange tester must be effective.
 * 			| getPosition() != null
 * @invar	The range of every in subrange tester must be equal
 * 			to or greater than zero.
 * 			| getRange() >= 0
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class InSubrangeTester implements ConditionTester {

	/**
	 * Initializes a new in subrange tester with given position and range.
	 * 
	 * @param 	position
	 * 			The new position for the center of the subrange.
	 * @param 	range
	 * 			The new range for the subrange.
	 * @post	The new range for this in subrange tester
	 * 			is equal to the absolute value of the given range.
	 * 			| new.getRange() == Math.abs(range)
	 * @post	The new position of this in subrange tester
	 * 			is equal to the given position, if this position
	 * 			doesn't refer the null reference. Otherwise the
	 * 			new position is equal to the position with both
	 * 			coordinates equal to zero.
	 * 			| if(position != null)
	 * 			| 	then new.getPosition().equals(position)
	 * 			| else
	 * 			| 	new.getPosition().equals(new Position(0L,0L))
	 * 			
	 */
	public InSubrangeTester(Position position, long range){
		this.range = Math.abs(range);
		if(position == null)
			position = new Position(0L,0L);
		this.position = position;
	}
	
	/**
	 * Returns the range of this in subrange tester.
	 */
	@Basic @Raw @Immutable
	public long getRange(){
		return range;
	}
	
	/**
	 * The range of this in subrange tester.
	 */
	private final long range;
	
	/**
	 * Returns the position of this in subrange tester.
	 */
	@Basic @Raw @Immutable
	public Position getPosition(){
		return position;
	}
	
	/**
	 * The position of this in subrange tester.
	 */
	private final Position position;
	
	/**
	 * Checks whether the given model is situated in the area of its board
	 * around the position of this in subrange tester with the range 
	 * of this in subrange tester as the range of the area.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to undergo the test.
	 * @return 	If the board of this board model refers the null reference,
	 * 			the result is always false. Else the result is equal to
	 * 			the result of the 'has in area' check of the given board model's board.
	 * 			| if(boardModel.getBoard() == null)
	 * 			|	then result == false
	 * 			| else
	 * 			|	result == (boardModel.getBoard().hasInArea(getPosition(), boardModel, range))
	 */
	@Override
	public boolean testCondition(BoardModel boardModel) {
		if(boardModel == null || boardModel.getBoard() == null)
			return false;
		return boardModel.getBoard().hasInArea(getPosition(), boardModel, range);
	}

}
