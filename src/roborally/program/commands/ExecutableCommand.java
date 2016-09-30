package roborally.program.commands;

import roborally.model.dynamicObject.Robot;

public interface ExecutableCommand extends Command
{
	/**
	 * Executes a step of this command on the given robot.
	 * 
	 * @param	robot
	 * 			The robot on which this command must be executed.
	 * @throws 	IllegalStateException
	 * 			Robot is not effective.
	 * 			| robot == null
	 * @throws	IllegalStateException
	 * 			Robot is terminated.
	 * 			| robot.isTerminated()
	 */	
	public void executeStep(Robot robot) throws IllegalStateException;
}
