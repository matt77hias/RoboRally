package roborally.program.languageelement;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of parameterized basic language elements.
 * 
 * @invar 	The parameter of every parameterized basic language element must be effective.
 * 			| getParameter() != null
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class ParameterizedBasicLanguageElement<T> extends BasicLanguageElement {
	
	/**
	 * Initializes this parameterized basic language element with the given parameter.
	 * 
	 * @param	parameter
	 * 			The parameter to use for this parameterized basic language element.
	 * @post	This parameterized basic language element has the given parameter as its parameter.
	 * 			| getParameter() == parameter
	 * @throws 	IllegalArgumentException
	 * 			The given parameter is not effective.
	 * 			| parameter == null
	 */
	protected ParameterizedBasicLanguageElement(T parameter)
			throws IllegalArgumentException {
		if (parameter == null)
			throw new IllegalArgumentException("The given paremeter must be effective.");
		this.parameter = parameter;
	}	
	
	/**
	 * Returns the parameter of this parameterized basic language element.
	 */
	@Basic @Immutable @Raw
	public T getParameter(){
		return parameter;
	}
	
	/**
	 * Variable storing the parameter for this parameterized basic language element.
	 */
	private final T parameter;
}
