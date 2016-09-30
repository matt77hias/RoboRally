package roborally.program.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.kuleuven.cs.som.annotate.Raw;
import roborally.model.Cost;
import roborally.model.dynamicObject.Robot;
import roborally.program.languageelement.ParameterizedBasicLanguageElement;
import roborally.program.parameters.TurnDirectionParameter;

/**
 * A class of turn commands
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class TurnCommand extends ParameterizedBasicLanguageElement<TurnDirectionParameter> implements ExecutableCommand{

	/**
	 * Initializes a new turn command with given turn direction parameter.
	 * 
	 * @param 	turnDirection
	 * 			The direction parameter, which identifies the turn direction.
	 */
	public TurnCommand(TurnDirectionParameter turnDirection)
			throws IllegalArgumentException{
		super(turnDirection);
	}
	
	/**
	 * Executes this turn command on the given robot.
	 * 
	 * @param	robot
	 * 			The robot on which the command must be executed on.
	 * @effect	If the robot can do a turn translation and the parameter of this turn command
	 * 			is clockwise then the given robot will turn in a clockwise direction.
	 * 			| if(robot.canDoEnergyCostMethod(robot.STANDARD_ENERGY_TURN_COST_CORE)
	 * 			|		&& getParameter() == TurnDirectionParameter.Clockwise)
	 *			|	then robot.turnClockwise()
	 * @effect	If the robot can do a turn translation and the parameter of this turn command
	 * 			is counterclockwise then the given robot will turn in a counterclockwise direction.
	 * 			| if (robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.TURN))
	 * 			|		&& getParameter() == TurnDirectionParameter.CounterClockwise)
	 *			|	then robot.turnCounterClockwise()
	 */
	@Override
	public void executeStep(Robot robot) throws IllegalStateException {
		if(robot == null)
			throw new IllegalStateException("The given robot is not effective.");
		if(robot.isTerminated())
			throw new IllegalStateException("The given robot is terminated.");
		if(robot.canDoEnergyCostMethod(robot.getEnergyCostOf(Cost.TURN))){
			if (getParameter() == TurnDirectionParameter.CLOCKWISE)
				robot.turnClockwise();
			else if(getParameter() == TurnDirectionParameter.COUNTERCLOCKWISE)
				robot.turnCounterClockwise();
		}
	}
	
	/**
	 * Returns a string representation of this condition.
	 * 
	 * @return	Returns "turn " plus the parameter.
	 * 			| result.equals("turn " + getParameter().turnDirectionToString())
	 */
	@Override
	public String toString(){
		return "turn " + getParameter().turnDirectionToString();
	}

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
