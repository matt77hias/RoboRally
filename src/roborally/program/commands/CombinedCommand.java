package roborally.program.commands;

import roborally.program.languageelement.CombinedLanguageElement;

/**
 * A class of combined commands.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class CombinedCommand extends CombinedLanguageElement<Command> implements Command{

	/**
	 * Returns this sequential command in robot language syntax.
	 */
	@Override
	public String toRobotLanguage(){
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(getElementSymbol());
		sb.append("\n");
		for (int i = 0; i < getNbSubElements(); i++){
			sb.append("\t");
			sb.append(getSubElementAt(i+1).toRobotLanguage());
			sb.append("\n");
		}
		sb.append(")");
		return sb.toString();
	}
}
