package roborally.model.inventory;

import java.util.Random;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.inventory.item.*;

/**
 * A class that creates the different surprises that could be
 * hidden in surprise boxes.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class SurpriseFactory {

	/**
	 * Generates a random standard inventory model (surprise).
	 * Possibilities: Battery, RepairKit, Bomb, TeleportGate, SurpriseBox
	 * 
	 * @param 	board
	 * 			The board where the random standard inventory model
	 * 			has to be situated on.
	 * @param 	position
	 * 			The position where the random standard inventory model
	 * 			has to be situated on.
	 * @return	The return inventory model is situated on the given board,
	 * 			on the given position.
	 * 			| result.getBoard() == board
	 * 			| && result.getPosition() == position
	 * @return 	The returned inventory model is an instance of one of five
	 * 			possible inventory model extending classes, generated with
	 * 			the standard input values for that class if applicable.
	 * 			| Battery.class.isInstance(result)
	 * 			| || RepairKit.class.isInstance(result)
	 * 			| || Bomb.class.isInstance(result)
	 * 			| || TeleportGate.class.isInstance(result)
	 * 			| || SurpriseBox.class.isInstance(result)
	 * @throws	If the given board doesn't refer the null reference,
	 * 			it must be possible to position the returned inventory model
	 * 			on the given board, on the given position.
	 * 			| if(board != null) then
	 * 			| 	!board.canHaveAsBoardModel(position, generateRandomStandardSurprise(board, position))
	 */
	public static InventoryModel generateRandomStandardSurprise(Board board, Position position){
		Random randomGenerator = new Random();
		int choice = randomGenerator.nextInt(5);
		switch(choice){
			case 0:
				return new Battery(board, position);
			case 1:
				return new RepairKit(board, position);
			case 2:
				return new Bomb(board, position,Bomb.STANDARD_BOMB_IMPACT_SIZE);
			case 3:
				return new TeleportGate(board, position,TeleportGate.STANDARD_TELEPORT_RANGE);
			default:
				return new SurpriseBox(board, position);
		}
	}
}
