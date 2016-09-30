package roborally.program.languageelement;

/**
 * A class for basic language elements.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class BasicLanguageElement implements LanguageElement {
	
	/**
	 * Returns whether this condition has the given language element
	 * as one of its sub elements.
	 * 
	 * @param 	subElement
	 * 			The language element that has to be checked.
	 * @return	True if and only if the given basic language element is the same
	 * 			as this basic language element.
	 * 			| result == (subElement == this)
	 */
	@Override
	public boolean hasAsSubElement(LanguageElement subElement) {
		return subElement == this;
	}
	
	/**
	 * Returns this basic condition in robot language syntax.
	 * 
	 * @return	The textual representation of this basic language element.
	 *        	| result.equals(this.toString())
	 */
	@Override
	public String toRobotLanguage(){
		return "(" + toString() + ")";
	}
}
