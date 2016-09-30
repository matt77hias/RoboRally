package roborally.program;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import be.kuleuven.cs.som.annotate.*;
import roborally.Terminatable;
import roborally.model.dynamicObject.Robot;
import roborally.program.commands.Command;
import roborally.program.commands.ExecutableCommand;

/**
 * A class of programs.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class Program {
	/**
	 * Initializes a new program with given robot and given file's program.
	 * 
	 * @param	robot
	 * 			The robot for this program.
	 * @param	filePath
	 * 			The file path of the file to read for the program.
	 * @post	The given robot is this new program's robot.
	 * 			| new.getRobot() == robot
	 * @effect	The given file's command list will be loaded into this program.
	 * 			| setCommand(filePath)
	 * @throws 	IOException
	 * 			If the given file can not be read.
	 * @throws 	IllegalArgumentException
	 * 			If the given file's program is not written in correct robot language syntax.
	 * @throws	IllegalArgumentException
	 * 			If the given robot is invalid.
	 * 			| !isValidRobot(robot)
	 */
	public Program(Robot robot, String filePath) throws IOException, IllegalArgumentException{
		if (!isValidRobot(robot))
			throw new IllegalArgumentException("The given robot is invalid for this program");
		this.robotLanguageInterpreter = new RobotLanguageInterpreter();
		setCommand(filePath);
		this.robot = robot;
	}
	
	/**
	 * Starts the execution for this program
	 * 
	 * @effect	if this program is finished add the internal command to the stack.
	 * 			| if (isFinished())
	 * 			| 	then getCommandStack().addFirst(getCommand())
	 */
	public void start(){
		if (isFinished())
			getCommandStack().addFirst(getCommand());
	}
	
	/**
	 * Adds the command list of the given command to the command stack.
	 * 
	 * @param	fromCmd
	 * 			The command to use to add to the command stack.
	 * @effect	Every command from the command list of the given command is added to the stack.
	 * 			| for each Command cmd in fromCmd.getCommandList(getRobot()) :
	 * 			| 	getCommandStack().addFirst(cmd)
	 */
	private void addToCommandStackFromCommand(Command fromCmd){
		List<Command> newCommands = fromCmd.getCommandList(getRobot());
		for (Command cmd : newCommands){
			getCommandStack().addFirst(cmd);
		}
	}
	
	/**
	 * Executes a single step of this program.
	 */
	public void executeStep(){
		if (!isFinished()){
			boolean encounteredBasic = false;
			
			while (!encounteredBasic && !isFinished()){
				Command cmd = getCommandStack().getFirst();
				if (ExecutableCommand.class.isInstance(cmd)){
					((ExecutableCommand)cmd).executeStep(getRobot());
					encounteredBasic = true;
				}
				else{
					addToCommandStackFromCommand(cmd);
				}
				
				if (cmd.removeFromStackAfterExecution(robot)){
					if (cmd == getCommandStack().getFirst())
						getCommandStack().removeFirst();
					else
						getCommandStack().remove(cmd);
				}
			}
		}
	}
	
	/**
	 * Returns whether this program has finished yet.
	 * 
	 * @param	robot
	 * 			The robot for which to check 
	 * @return	Returns whether the internal command stack is empty.
	 * 			| getCommandStack().isEmpty()
	 */
	@Raw
	public boolean isFinished(){
		return getCommandStack().isEmpty();
	}

	/**
	 * Returns the command stack of this program.
	 */
	@Basic @Raw
	private LinkedList<Command> getCommandStack(){
		return commandStack;
	}
	
	/**
	 * Internal command stack to keep track of the current program progress.
	 */
	LinkedList<Command> commandStack = new LinkedList<Command>();
	
	/**
	 * Returns the sequential command of this program.
	 */
	@Basic @Model @Raw
	private Command getCommand(){
		return command;
	}
	
	/**
	 * Sets the command of this program to the program represented in the given file.
	 * 
	 * @param 	filePath
	 * 			The file path of the file to read for the program.
	 * @effect 	First terminates the command of this program.
	 * 			| terminateCommand()
	 * @post	The robot language interpreter of this program will load the command list from
	 * 			the given file into this program.
	 * 			| new.getCommand() == getRobotLanguageInterpreter().readCommandListFromFile(filePath)
	 * @throws 	IOException
	 * 			If the given file could not be read.
	 * @throws 	IllegalArgumentException
	 * 			If the given file's program is not written in correct robot language syntax.
	 */
	public void setCommand(String filePath) throws IOException, IllegalArgumentException{
		terminateCommand();
		this.command = getRobotLanguageInterpreter().readCommandListFromFile(filePath);
	}
	
	/**
	 * Terminates the command of this program.
	 * 
	 * @effect	If the internal command is an instance of Terminatable then terminate the command.
	 * 			| if (getCommand() instanceof Terminatable)
	 * 			| 	then ((Terminatable)getCommand()).terminate()
	 */
	private void terminateCommand(){
		if (getCommand() instanceof Terminatable){
			((Terminatable)getCommand()).terminate();
		}
	}
	
	/**
	 * Variable storing the command representing this program.
	 */
	private Command command;
	
	/**
	 * Returns whether the given robot is a valid robot for any program.
	 * 
	 * @param 	robot
	 * 			The robot to be checked
	 * @return 	Returns false if the given robot is not effective.
	 * 			| result == (robot!=null)
	 */
	public static boolean isValidRobot(Robot robot){
		return robot != null;
	}
	
	/**
	 * Returns the robot of this program.
	 */
	@Basic @Immutable @Raw
	public Robot getRobot(){
		return robot;
	}
	
	/**
	 * Variable storing the robot of this program.
	 */
	private final Robot robot;
	
	/**
	 * Returns the robot language interpreter of this program.
	 */
	@Basic @Immutable @Model @Raw
	private final RobotLanguageInterpreter getRobotLanguageInterpreter(){
		return robotLanguageInterpreter;
	}
	
	/**
	 * Variable storing the robot language interpreter for this program.
	 */
	private final RobotLanguageInterpreter robotLanguageInterpreter;
	
	/**
	 * Writes this program to a file at the given filepath.
	 * 
	 * @param 	filePath
	 * 			The file path to write this program to.
	 * @effect	The language interpreter of this program will write the command of this
	 * 			program to the given file path.
	 * 			| getRobotLanguageInterpreter().writeCommandListToFile(filePath, getCommand())
	 * @throws	IOException
	 * 			If the given file path could not be written to.
	 */
	public void write(String filePath) throws IOException{
		getRobotLanguageInterpreter().writeCommandListToFile(filePath, getCommand());
	}
	
	/**
	 * Returns String representation of this program.
	 * 
	 * @return 	Returns the whole sequential condition of this program
	 * 			in String format.
	 * 			| result.equals(getCommand().toRobotLanguage())
	 */
	@Override
	public String toString(){
		return getCommand().toRobotLanguage();
	}
}
