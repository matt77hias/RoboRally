package roborally.program.languageelement;

/**
 * An interface that should be implemented by all language element classes.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public interface LanguageElement {
	/**
	 * Returns whether this language element has the given language element
	 * as one of its sub elements.
	 * 
	 * @param 	subElement
	 * 			The language element that has to be checked.
	 * @return	True if the given language element is the same as this language element
	 *       	| if (subElement == this)
	 * 			|   then result == true
	 * @return 	False if the given language element is not effective.
	 *       	| if (subElement == null)
	 *      	|   then result == false
	 */
	public boolean hasAsSubElement(LanguageElement subElement);
	
	/**
	 * Returns this language element in robot language syntax.
	 * 
	 * @return 	The resulting string is non-empty.
	 *       	| result.length() > 0
	 */
	public String toRobotLanguage();
}
