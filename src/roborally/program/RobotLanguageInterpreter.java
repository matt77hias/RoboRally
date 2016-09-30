package roborally.program;

import java.io.*;
import java.util.*;

import roborally.model.energy.Energy;
import roborally.program.commands.*;
import roborally.program.conditions.*;
import roborally.program.languageelement.LanguageElement;
import roborally.program.parameters.TurnDirectionParameter;

/**
 * A class of robot language interpreters.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class RobotLanguageInterpreter {

	/**
	 * Initializes a new robot language interpreter.
	 */
	public RobotLanguageInterpreter(){
	}
	
	public void writeCommandListToFile(String filePath, Command cmd) throws IOException{
		BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
		out.write(writeCommandListToString(cmd));
		out.close();
	}
	
	public String writeCommandListToString(Command cmd){
		return cmd.toRobotLanguage();
	}

	public Command readCommandListFromFile(String filePath) throws IOException, IllegalArgumentException{
        StringBuilder fileData = new StringBuilder();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return readCommandList(fileData.toString());
	}
	
	public Command readCommandList(String input) throws IllegalArgumentException{
		input = input.replace("\n", " ");
		input = input.replace("\r", " ");
		input = input.replace("\t", " ");
		input = input.trim().replaceAll(" +", " ");
		
		LinkedList<String> inputSplit = new LinkedList<String>(Arrays.asList(input.split("\\(")));
		
		inputSplit.removeFirst();
		/*inputSplit.addFirst("seq");
		String temp = inputSplit.getLast() + ")";
		inputSplit.removeLast();
		inputSplit.addLast(temp);*/
		
		return (Command)readElement(inputSplit);
	}
	
	public LanguageElement readElement(LinkedList<String> list) throws IllegalArgumentException{
		recursionLevel++;
		int endBrackets = countEndBrackets(list.getFirst());
		LanguageElement retEl = null;
		
		String elementName = list.getFirst().trim();
		list.removeFirst();
		
		if (elementName.startsWith("while")){
			Condition whileCond = (Condition)readElement(list);
			Command whileCmd = (Command)readElement(list);
			retEl = new WhileCommand(whileCond, whileCmd);
		}
		else if (elementName.startsWith("seq")){
			int oldRec = recursionLevel;
			List<Command> cmdList = new ArrayList<Command>();
			while (oldRec <= recursionLevel){
				cmdList.add((Command)readElement(list));
			}
			Command[] a = new Command[cmdList.size()];
			retEl = new SeqCommand(cmdList.toArray(a));
		}
		else if (elementName.startsWith("if")){
			Condition ifCond = (Condition)readElement(list);
			Command thenCmd = (Command)readElement(list);
			Command elseCmd = (Command)readElement(list);
			retEl = new IfCommand(ifCond, thenCmd, elseCmd);
		}
		else if (elementName.startsWith("move")){
			retEl = MoveCommand.MOVE_COMMAND;
		}
		else if (elementName.startsWith("shoot")){
			retEl = ShootCommand.SHOOT_COMMAND;
		}
		else if (elementName.startsWith("turn")){
			String paramStr = elementName.split(" ")[1].split("\\)")[0];
			TurnDirectionParameter param = TurnDirectionParameter.TurnDirectionFromString(paramStr.toLowerCase());
			
			retEl = new TurnCommand(param);
		}
		else if (elementName.startsWith("pickup-and-use")){
			retEl = PickUpAndUseCommand.PICK_UP_AND_USE_COMMAND;
		}
		else if (elementName.startsWith("and")){
			Condition cond1 = (Condition)readElement(list);
			Condition cond2 = (Condition)readElement(list);
			retEl = new AndCondition(cond1, cond2);
		}
		else if (elementName.startsWith("or")){
			Condition cond1 = (Condition)readElement(list);
			Condition cond2 = (Condition)readElement(list);
			retEl = new OrCondition(cond1, cond2);
		}
		else if (elementName.startsWith("not")){
			Condition cond = (Condition)readElement(list);
			retEl = new NotCondition(cond);
		}
		else if (elementName.startsWith("energy-at-least")){
			String paramStr = elementName.split(" ")[1].split("\\)")[0];
			Double param = Double.parseDouble(paramStr);
			retEl = new EnergyAtleastCondition(new Energy(param));
		}
		else if (elementName.startsWith("at-item")){
			retEl = AtItemCondition.AT_ITEM_CONDITION;
		}
		else if (elementName.startsWith("can-hit-robot")){
			retEl = CanHitRobotCondition.CAN_HIT_ROBOT_CONDITION;
		}
		else if (elementName.startsWith("wall")){
			retEl = WallCondition.WALL_CONDITION;
		}
		else if (elementName.startsWith("true")){
			retEl = TrueCondition.TRUE_CONDITION;
		}
		else{
			throw new IllegalArgumentException(elementName + " element unkown");
		}

		recursionLevel -= endBrackets;
		return retEl;
	}
	
	private int recursionLevel;
	
	private int countEndBrackets(String input){
		input = input.trim().replaceAll("\\s+", "");
		int ind = input.indexOf(")");
		int amtBrackets = (ind == -1)? 0:input.length() - ind;
		return amtBrackets;
	}
}