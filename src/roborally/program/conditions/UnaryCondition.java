package roborally.program.conditions;

import roborally.program.languageelement.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of unary conditions.
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public abstract class UnaryCondition extends CombinedLanguageElement<Condition> implements Condition {

	/**
	 * Initializes this new unary condition with given sub condition.
	 *
	 * @param  	subCondition
	 *         	The sub condition for this new unary condition.
	 * @post   	The sub condition for this new unary condition is the
	 *         	same as the given sub condition.
	 *       	| new.getSubCondition() == subCondition
	 * @throws 	IllegalArgumentException
	 *         	This new unary condition cannot have the given
	 *         	sub condition as its sub condition.
	 *       	| !canHaveAsOperand(operand)
	 */
	@Model
	protected UnaryCondition(Condition subCondition)
			throws IllegalArgumentException {
		if (!canHaveAsSubElement(subCondition))
			throw new IllegalArgumentException("The given sub condition is invalid for this unary condition.");
		setSubElementAt(1, subCondition);
	}

	/**
	 * Returns the number of sub conditions involved in this unary condition.
	 *
	 * @return 	A unary expression always involves a single sub condition.
	 *       	| result == 1
	 */
	@Override
	@Basic
	public final int getNbSubElements() {
		return 1;
	}

	/**
	 * Checks whether this unary condition can have the given
	 * number as its number of sub conditions.
	 *
	 * @return 	True if and only if the given number is 1.
	 *       	| result == (number == 1)
	 */
	@Override
	@Raw
	public final boolean canHaveAsNbSubElements(int number) {
		return number == 1;
	}

	/**
	 * Returns the sub condition of this unary condition at the given index.
	 * 
	 * @return 	The one and only sub condition of this unary condition.
	 *       	| result == getSubCondition()
	 */
	@Override
	public final Condition getSubElementAt(int index)
			throws IndexOutOfBoundsException {
		if (index != 1)
			throw new IndexOutOfBoundsException();
		return getSubCondition();
	}

	/**
	 * Returns the sub condition of this unary condition.
	 */
	@Basic
	public Condition getSubCondition() {
		return subCondition;
	}

	/**
	 * Sets the sub condition for this unary condition at the given
	 * index to the given sub condition.
	 */
	@Override
	protected void setSubElementAt(int index, Condition subCondition) {
		this.subCondition = subCondition;
	}

	/**
	 * Variable referring the sub condition of this unary condition.
	 */
	private Condition subCondition;
	
	/**
	 * Returns this combined condition in robot language syntax.
	 * 
	 * @return 	The condition symbol of this condition followed by the robot language syntax of its
	 * 			sub condition, enclosed in brackets.
	 * 			| result == "(" + getConditionSymbol() + " " + getSubCondition().toRobotLanguage() + ")"
	 */
	@Override
	public String toRobotLanguage() {
		return "(" + getElementSymbol() + " " + getSubCondition().toRobotLanguage() + ")";
	}
}
