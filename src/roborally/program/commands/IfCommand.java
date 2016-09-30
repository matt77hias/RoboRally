package roborally.program.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.dynamicObject.Robot;
import roborally.program.conditions.Condition;

/**
 * A class of if commands
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class IfCommand extends ConditionalCommand {
	/**
	 * Initializes this new if command with given condition,
	 * then and else commands.
	 *
	 * @param  	condition
	 *         	The condition for this new if command.
	 * @param	thenCommand
	 * 			The then sub command for this new if command.
	 * @param 	elseCommand
	 * 			The else sub command for this new if command.
	 * @pre 	The given condition is effective.
	 * 			| condition != null
	 * @pre 	The then command is effective.
	 * 			| thenCommand != null
	 * @pre 	The else command is effective.
	 * 			| elseCommand != null
	 * @effect  Initializes this new if command as a new conditional
	 * 			command with the same parameters.
	 *       	| super(condition, thenCommand, elseCommand)
	 */
	public IfCommand(Condition condition, Command thenCommand, Command elseCommand){
		super(condition, thenCommand, elseCommand);
	}
	
	/**
	 * Returns the number of sub commands involved in this sequential command.
	 * 
	 * @return	Returns two.
	 * 			| result == 2
	 */
	@Override
	public int getNbSubElements() {
		return 2;
	}

	/**
	 * Returns the sub command at the given index.
	 * 
	 * @return 	If the index equals 1 the then command
	 * 			of this if command is returned.
	 * 			| if (index == 1)
	 * 			| 	then result == getThenCommand()
	 * @return 	If the index equals 2 the else command
	 * 			of this if command is returned.
	 * 			| if (index == 2)
	 * 			| 	then result == getElseCommand()
	 */
	@Override
	public Command getSubElementAt(int index) throws IndexOutOfBoundsException {
		if(index == 1)
			return getThenCommand();
		else if(index == 2)
			return getElseCommand();
		throw new IndexOutOfBoundsException("Given index: " + index + " is out of range");
	}
	
	
	/**
	 * Returns the then subcommand of this if command.
	 */
	@Basic
	public Command getThenCommand(){
		return thenCommand;
	}
	
	/**
	 * Returns the else subcommand of this if command.
	 */
	@Basic
	public Command getElseCommand(){
		return elseCommand;
	}

	/**
	 * Sets the sub command of this if command to
	 * the given sub command at the given index.
	 * 
	 * @param 	index
	 * 			The index to set the sub command at.
	 * @param 	subElement
	 * 			The sub command to set.
	 * @post	If the index is 1 or 2, the sub command at the given index
	 * 			is the given sub command.
	 * 			| if (index == 1 || index == 2)
	 * 			|	then new.getSubElementAt(index) == subElement
	 */
	@Override
	protected void setSubElementAt(int index, Command subElement) {
		if(index == 1)
			thenCommand = subElement;
		else if(index == 2)
			elseCommand = subElement;
	}
	
	/**
	 * Variable storing the then sub command of this if command.
	 */
	private Command thenCommand;
	/**
	 * Variable storing the else sub command of this if command.
	 */
	private Command elseCommand;

	/**
	 * Returns the robot language symbol of this if command.
	 * 
	 * @return	Returns "if".
	 * 			| result.equals("if")
	 */
	@Override
	public String getElementSymbol() {
		return "if";
	}
	
	/**
	 * Returns a list of commands that need to go on the stack when this command is encountered in the stack.
	 * 
	 * @return 	If the condition of this if command applied on the robot is true, a list containing the then command is returned.
	 * 			| if (getCondition().result(robot))
	 * 			| 	then result.contains(getThenCommand()) && result.size() == 1
	 * @return 	Else a list containing the else command is returned.
	 * 			| else then result.contains(getElseCommand()) && result.size() == 1
	 */
	@Override
	public List<Command> getCommandList(Robot robot){
		List<Command> list = new ArrayList<Command>(1);
		if (getCondition().result(robot))
			list.add(getThenCommand());
		else
			list.add(getElseCommand());
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
