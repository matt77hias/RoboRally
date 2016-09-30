package roborally.program.commands;

import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.dynamicObject.Robot;
import roborally.program.languageelement.LanguageElement;

/**
 * An interface representing a general command that should be
 * implemented by all command classes.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface Command extends LanguageElement {


	/**
	 * Returns a list of commands that need to go on the stack when this command is encountered in the stack.
	 * 
	 * @param	robot
	 * 			Robot needed to check possible conditions.
	 * @return	An effective list.
	 * 			| result != null
	 */
	public List<Command> getCommandList(Robot robot);
	
	/**
	 * Returns whether this command needs to be removed from the stack after it is encountered.
	 * 
	 * @param	robot
	 * 			Robot needed to check possible conditions.
	 */
	@Raw
	public boolean removeFromStackAfterExecution(Robot robot);
	
}
