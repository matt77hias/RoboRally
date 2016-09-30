package roborally.program.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.dynamicObject.Robot;
import roborally.program.conditions.Condition;

/**
 * A class of while commands
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WhileCommand extends ConditionalCommand {
	/**
	 * Initializes this new while command with given condition and while command.
	 *
	 * @param  	condition
	 *         	The condition for this new while command.
	 * @param	whileCommand
	 * 			The while sub command for this while command.
	 * @effect  Initializes this new while command as a new conditional
	 * 			command with the same parameters.
	 *       	| super(condition, whileCommand)
	 */
	public WhileCommand(Condition condition, Command whileCommand){
		super(condition, whileCommand);
	}
	
	/**
	 * Returns the number of sub commands involved in this sequential command.
	 * 
	 * @return	Returns always one.
	 * 			| result == 1
	 */
	@Override
	public int getNbSubElements() {
		return 1;
	}

	/**
	 * Returns the sub command at the given index.
	 * 
	 * @return 	If the index equals one then the command is returned.
	 * 			| if (index == 1)
	 * 			| 	then result == getWhileCommand()
	 */
	@Override
	public Command getSubElementAt(int index) throws IndexOutOfBoundsException {
		if (index == 1)
			return getWhileCommand();
		throw new IndexOutOfBoundsException("Given index: " + index + " is out of range");
	}
	
	/**
	 * Returns the while sub command.
	 */
	@Basic
	public Command getWhileCommand(){
		return whileCommand;
	}

	/**
	 * Sets the sub command of this while command to the given sub command at the given index.
	 * 
	 * @param 	index
	 * 			The index to set the sub command at.
	 * @param 	subElement
	 * 			The sub command to set.
	 * @post	If the index is one, the sub command at the given index is the given sub command.
	 * 			| if (index == 1)
	 * 			|	then new.getSubElementAt(index) == subElement
	 */
	@Override
	protected void setSubElementAt(int index, Command subElement) {
		if (index == 1)
			whileCommand = subElement;
	}
	
	/**
	 * Variable storing the while subcommand of this while command.
	 */
	private Command whileCommand;

	/**
	 * Returns the robot language symbol of this if command.
	 * 
	 * @return	Returns "while".
	 * 			| result.equals("while")
	 */
	@Override
	public String getElementSymbol() {
		return "while";
	}
	
	/**
	 * Returns a list of commands that need to go on the stack when this command is encountered in the stack.
	 * 
	 * @return 	If the condition of this if command applied on the robot is true, a list containing the while command is returned.
	 * 			| if (getCondition().result(robot))
	 * 			| 	then result.contains(getWhileCommand()) && result.size() == 1
	 * @return	Else an empty list is returned.
	 * 			| else then result.isEmpty()
	 */
	@Override
	public List<Command> getCommandList(Robot robot){
		List<Command> list = new ArrayList<Command>(1);
		if (getCondition().result(robot))
			list.add(getWhileCommand());
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Returns whether this command needs to be removed from the stack after it is encountered.
	 * 
	 * @Return 	Returns the inverse of the result of the condition of this while command executed on the robot.
	 * 			| result == !getCondition().result(robot)
	 */
	@Override @Raw
	public boolean removeFromStackAfterExecution(Robot robot){
		return !getCondition().result(robot);
	}
}
