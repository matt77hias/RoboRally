package roborally.program.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.dynamicObject.Robot;
import roborally.program.languageelement.BasicLanguageElement;

/**
 * A class of basic commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class BasicCommand extends BasicLanguageElement implements ExecutableCommand {

	/**
	 * Returns a list of commands that need to go on the stack when this command is encountered in the stack.
	 * 
	 * @return  A list containing this command is returned.
	 * 			| if (getCondition().result(robot))
	 * 			| 	then result.contains(this) && result.size() == 1
	 */
	@Override
	public List<Command> getCommandList(Robot robot){
		List<Command> list = new ArrayList<Command>(1);
		list.add(this);
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Returns whether this command needs to be removed from the stack after it is encountered.
	 * 
	 * @Return Returns true
	 */
	@Override @Raw
	public boolean removeFromStackAfterExecution(Robot robot){
		return true;
	}
}
