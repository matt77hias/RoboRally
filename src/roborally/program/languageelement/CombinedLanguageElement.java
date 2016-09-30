package roborally.program.languageelement;

import roborally.Terminatable;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of combined language elements.
 * 
 * @invar 	Every sub element must be valid
 * 			| for every i in 1..getNbSubElements() :
 * 			| 	canHaveAsSubElement(getSubElementAt(i))
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class CombinedLanguageElement<T extends LanguageElement> implements LanguageElement, Terminatable{

	/**
	 * Returns the amount of sub elements this language element contains.
	 */
	@Basic
	public abstract int getNbSubElements();
	
	/**
	 * Terminates this combined language element.
	 * 
	 * @effect	All of it's sub language elements that are terminatable will be terminated.
	 * 			| for each i in 0..getNbSubElements() :
	 * 			| 	if (getSubElementAt(i+1) instanceof Terminatable)
	 * 			| 		((Terminatable)getSubElementAt(i+1)).terminate()
	 * @post 	All of it's sub language elements will be the null reference.
	 * 			| for each i in 0..getNbSubElements() :
	 * 			| 	getSubElementAt(i+1) == null
	 * @post	The terminated flag of this object will be set to true.
	 * 			| new.getTerminated() == true
	 */
	@Override
	public void terminate(){
		for (int i = 0; i < getNbSubElements(); i++){
			if (getSubElementAt(i+1) instanceof Terminatable){
				((Terminatable)getSubElementAt(i+1)).terminate();
			}
			setSubElementAt(i+1, null);
		}
		setTerminated(true);
	}
	
	/**
	 * Sets the terminated status of this object.
	 * 
	 * @param 	terminated
	 * 			The terminated flag to give to this object.
	 * @post 	The terminated flag will be equal to the given terminated flag.
	 * 			| new.isTerminated() == terminated
	 */
	private void setTerminated(boolean terminated){
		this.terminated = terminated;
	}
	
	/**
	 * Returns whether this object is terminated.
	 */
	@Basic @Raw @Override
	public boolean isTerminated(){
		return terminated;
	}
	
	/**
	 * Variable storing the terminated status of this object.
	 */
	private boolean terminated = false;

	/**
	 * Returns whether this combined language element can have the
	 * given amount of sub elements.
	 *
	 * @param  	nbSubElements
	 *         	The number of sub elements that has to be checked.
	 * @return 	False if the given number is not positive.
	 *       	| if (number <= 0)
	 *       	|   then result == false
	 */
	@Raw
	public boolean canHaveAsNbSubElements(int nbSubElements) {
		return nbSubElements > 0;
	}

	/**
	 * Returns the sub element of this combined language element at the given index.
	 *
	 * @param  	index
	 *         	The index of the requested sub element.
	 * @throws 	IndexOutOfBoundsException
	 *         	The given index is not positive or exceeds the
	 *         	number of sub elements for this combined condition.
	 *       	| (index < 1) || (index > getNbSubElements())
	 */
	@Basic
	public abstract T getSubElementAt(int index)
			throws IndexOutOfBoundsException;
	
	/**
	 * Checks whether this combined language element has the given language element
	 * as one of its sub elements.
	 *
	 * @return 	True if and only if the given language element is the same
	 *         	language element as this combined language element, or if the given
	 *         	language element is a sub element of one of the sub elements
	 *         	of this combined language element.
	 *       	| result ==
	 *       	|     	(subElement == this)
	 *       	|  		|| (for some I in 1..getNbSubElements():
	 *       	|         	getSubElementAt(I).hasAsSubElement(condition))
	 */
	@Override
	public boolean hasAsSubElement(LanguageElement subElement) {
		if (subElement == this)
			return true;
		for (int pos = 1; pos <= getNbSubElements(); pos++)
			if (getSubElementAt(pos).hasAsSubElement(subElement))
				return true;
		return false;
	}

	/**
	 * Checks whether this combined language element can have the given
	 * language element as one of its sub elements.
	 *
	 * @param  	subElement
	 *         	The sub element that has to be checked.
	 * @return 	True if and only if the given language element is effective
	 *         	and if that language element does not have this combined
	 *         	language element as a sub element.
	 *       	| result ==
	 *       	|   ((subElement != null)
	 *       	|  	&& (!subElement.hasAsSubElement(this)))
	 */
	public boolean canHaveAsSubElement(T subElement) {
		return (subElement != null) && (!subElement.hasAsSubElement(this));
	}
	
	/**
	 * Sets the sub element for this combined language element at the given
	 * index to the given sub element.
	 * 
	 * @param  	index
	 *         	The index at which the sub condition must be registered.
	 * @param  	subElement
	 *         	The sub element that has to be registered.
	 * @pre    	The given index is positive and does not exceed the
	 *         	number of sub elements for this composed language element.
	 *         	| (index >= 1) && (index <= getNbSubElements())
	 * @pre    	This language element can have the given sub element as one
	 *         	of its sub elements.
	 *         	| canHaveAsSubElement(subElement)
	 * @post   	The sub element at the given index of this combined
	 *         	language element is the same as the given sub element.
	 *         	| new.getSubElementAt(index) == subElement
	 */
	protected abstract void setSubElementAt(int index, T subElement);

	/**
	 * Returns the robot language symbol of this condition.
	 * 
	 * @return 	An effective, non-empty string.
	 *       	| result.length() > 0
	 */
	public abstract String getElementSymbol();
}
