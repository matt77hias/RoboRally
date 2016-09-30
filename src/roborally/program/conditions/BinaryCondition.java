package roborally.program.conditions;

import roborally.program.languageelement.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of binary conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class BinaryCondition extends CombinedLanguageElement<Condition> implements Condition {

	/**
	 * Initialize this new binary condition with given sub conditions.
	 *
	 * @param  	one
	 *         	The first sub condition for this new binary condition.
	 * @param  	two
	 *         	The second sub condition for this new binary condition.
	 * @post   	The first sub condition of this new binary condition
	 * 			refers the given first sub condition.
	 *       	| new.getLeftSubCondition() == one
	 * @post   	The second sub condition of this new binary condition 
	 * 			refers the given second sub condition.
	 *       	| new.getRightSubCondition() == two
	 * @throws 	IllegalArgumentException
	 *         	This new binary condition cannot have the given first
	 *         	sub condition or the given second sub condition as its
	 *        	sub condition.
	 *      	|     (!canHaveAsSubElement(one))
	 *      	|  || (!canHaveAsSubElement(two))
	 */
	protected BinaryCondition(Condition one, Condition two)
			throws IllegalArgumentException{
		if (!canHaveAsSubElement(one))
			throw new IllegalArgumentException("The first given condition is illegal for this binary condition");
		if (!canHaveAsSubElement(two))
			throw new IllegalArgumentException("The second given condition is illegal for this binary condition");
		setSubElementAt(1, one);
		setSubElementAt(2, two);
	}
	
	/**
	 * Returns the number of sub conditions involved in this binary condition.
	 *
	 * @return 	A binary condition always involves two sub conditions.
	 *       	| result == 2
	 */
	@Override
	@Basic
	public int getNbSubElements() {
		return 2;
	}

	/**
	 * Checks whether this binary condition can have the given
	 * number as its number of sub conditions.
	 *
	 * @return 	True if and only if the given number is two.
	 *       	| result == (number == 2)
	 */
	@Override
	@Raw
	public final boolean canHaveAsNbSubElements(int number) {
		return number == 2;
	}

	/**
	 * Returns the sub condition of this binary expression at the given index.
	 * 
	 * @return 	If the given index is one, the first sub condition of this
	 *         	binary condition; otherwise the second sub condition of
	 *         	this combined condition.
	 *      	| if (index == 1)
	 *       	|   then result == getFirstSubCondition()
	 *       	|   else result == getSecondSubCondition()
	 */
	@Override
	@Raw
	public final Condition getSubElementAt(int index)
			throws IndexOutOfBoundsException {
		if((index != 1) && (index != 2))
			throw new IndexOutOfBoundsException();
		if(index == 1)
			return getFirstSubCondition();
		else
			return getSecondSubCondition();
	}

	/**
	 * Sets the sub condition for this binary condition at the given
	 * index to the given sub condition.
	 */
	@Override
	@Raw
	protected void setSubElementAt(int index, Condition subCondition) {
		if(index == 1)
			this.firstSubCondition = subCondition;
		else
			this.secondSubCondition = subCondition;
	}

	/**
	 * Returns the left sub condition of this binary condition.
	 */
	@Basic
	public Condition getFirstSubCondition() {
		return firstSubCondition;
	}

	/**
	 * Variable referencing the first sub condition of this
	 * binary condition.
	 */
	private Condition firstSubCondition;

	/**
	 * Returns the second operand of this binary condition.
	 */
	@Basic
	public Condition getSecondSubCondition() {
		return secondSubCondition;
	}

	/**
	 * Variable referencing the second sub condition of this
	 * binary condition.
	 */
	private Condition secondSubCondition;
	
	/**
	 * Returns this combined condition in robot language syntax.
	 * 
	 * @return 	The condition symbol of this condition followed by the robot language syntax of its
	 * 			two sub conditions, enclosed in brackets.
	 * 			| result =="(" + getElementSymbol() + " " + getFirstSubCondition().toRobotLanguage()
	 * 			| 	+ " " + getSecondSubCondition().toRobotLanguage() + ")"
	 */
	@Override
	public String toRobotLanguage() {
		return "(" + getElementSymbol() + " " + getFirstSubCondition().toRobotLanguage() + " " + getSecondSubCondition().toRobotLanguage() + ")";
	}

}
