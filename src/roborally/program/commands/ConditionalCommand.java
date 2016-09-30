package roborally.program.commands;

import be.kuleuven.cs.som.annotate.*;
import roborally.program.conditions.Condition;

/**
 * A class of conditional commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class ConditionalCommand extends CombinedCommand{
	
	/**
	 * Initializes this new conditional command with the given condition and list of commands.
	 * 
	 * @pre 	The given condition is effective.
	 * 			| condition != null
	 * @pre 	All of the given commands are effective
	 * 			| for i in 0..commands.length-1 :
	 * 			| 	commands[i] != null
	 * @param 	condition
	 * 			The new condition for this conditional command.
	 * @param 	commands
	 * 			The new 'list' of commands for this conditional command.
	 * @effect	Sets the new sub commands to the given commands
	 * 			for this new conditional command.
	 *			| for i in 0..commands.length-1 :
	 *			| 	setSubElementAt(i+1, commands[i])
	 * @effect	Sets the new condition to the given condition
	 * 			for this new conditional command.
	 * 			| setCondition(condition)
	 */
	protected ConditionalCommand(Condition condition, Command... commands){
		for(int i=0; i<commands.length; i++){
			setSubElementAt(i+1, commands[i]);
		}
		setCondition(condition);
	}
	
	/**
	 * Returns the condition of this conditional command.
	 */
	@Basic
	public Condition getCondition(){
		return condition;
	}
	
	/**
	 * Sets the condition of this conditional command to the given condition.
	 * 
	 * @param 	condition
	 * 			The condition that has to be set.
	 * @post	The condition of this conditional command is equal to the given condition.
	 * 			| new.getCondition() == condition
	 */
	private void setCondition(Condition condition){
		this.condition = condition;
	}
	
	/**
	 * Variable storing the condition of this conditional command.
	 */
	private Condition condition;
	
	/**
	 * Returns this sequential command in robot language syntax.
	 */
	@Override
	public String toRobotLanguage() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(getElementSymbol());
		sb.append("\n");
		sb.append("\t");
		sb.append(getCondition().toRobotLanguage());
		sb.append("\n");
		for(int i=0; i<getNbSubElements(); i++){
			sb.append("\t");
			sb.append(getSubElementAt(i+1).toRobotLanguage());
			sb.append("\n");
		}
		sb.append(")");
		return sb.toString();
	}

}
