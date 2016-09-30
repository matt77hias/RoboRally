package roborally.program.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.dynamicObject.Robot;

/**
 * A class of sequential commands
 * 
 * @invar	The current step number must be a number higher than zero
 * 			| getCurrentStep() > 0
 * @invar	The current step number must be a number lower or equal than
 * 			the amount of sub commands this sequential command has.
 * 			| getCurrentStep() <= getNbSubElements()
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 */
public class SeqCommand extends CombinedCommand {
	/**
	 * Initializes this new sequential command with given sub commands.
	 *
	 * @param  	subCommands
	 *         	The sub commands for this new sequential command.
	 * @post   	The sub commands for this new sequential command are
	 * 			the same commands as the given commands.
	 *       	| for i in 1..getNbSubElements() :
	 *       	|	getSubElementAt(i) == subCommands[i-1]
	 * @throws 	IllegalArgumentException
	 *         	This new sequential command cannot have one of the given
	 *         	commands as its sub command.
	 *       	| for some i in 0..subCommands.length
	 *       	|	!canHaveAsSubElement(subCommands[i])
	 */
	public SeqCommand(Command... subCommands)
			throws IllegalArgumentException{
		this.subCommands = new Command[subCommands.length];
		for (int i=0; i<subCommands.length; i++){
			if (!canHaveAsSubElement(subCommands[i]))
				throw new IllegalArgumentException("One of the given sub commands is invalid for this sequential command.");
			setSubElementAt(i+1, subCommands[i]);
		}
	}

	/**
	 * Returns the number of sub commands involved in this sequential command.
	 */
	@Override
	@Basic
	public int getNbSubElements() {
		return subCommands.length;
	}


	/**
	 * Returns the sub command of this sequential command at the given index.
	 */
	@Override
	@Basic
	public Command getSubElementAt(int index)
			throws IndexOutOfBoundsException {
		if(index > getNbSubElements())
			throw new IndexOutOfBoundsException();
		return subCommands[index-1];
	}

	/**
	 * Sets the sub command for this sequential command at the given
	 * index to the given sub command.
	 */
	@Override
	protected void setSubElementAt(int index, Command subCommand) {
		subCommands[index-1] = subCommand;
	}

	/**
	 * Array storing the sub commands of this sequential command.
	 */
	private Command[] subCommands;
	
	/**
	 * Returns the robot language symbol of this sequential command.
	 * 
	 * @return	Returns "seq".
	 * 			| result.equals("seq")
	 */
	@Override
	public String getElementSymbol() {
		return "seq";
	}
	
	/**
	 * Returns the robot language symbol of this sequential command.
	 * 
	 * @return	Returns the robot language of this sequential command.
	 * 			| result.equals(toRobotLanguage())
	 */
	@Override
	public String toString(){
		return toRobotLanguage();
	}
	
	/**
	 * Returns a list of commands that need to go on the stack when this command is encountered in the stack.
	 * 
	 * @param	robot
	 * 			Robot needed to check possible conditions.
	 * @return	All the sub commands of this sequential command are added in reverse order.
	 * 			| for i in 0..getNbSubElements()-1 : result.get(i) == getSubElementAt(getNbSubElements()-i)
	 * 			| && result.size() == getNbSubElements()
	 */
	@Override
	public List<Command> getCommandList(Robot robot){
		List<Command> list = new ArrayList<Command>(getNbSubElements());
		for (int i = getNbSubElements(); i > 0 ; i--){
			list.add(getSubElementAt(i));
		}
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
