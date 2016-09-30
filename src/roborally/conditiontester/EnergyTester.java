package roborally.conditiontester;

import be.kuleuven.cs.som.annotate.*;
import roborally.model.BoardModel;
import roborally.model.energy.Energy;
import roborally.model.energy.EnergyModel;

/**
 * A condition tester that tests a basic energy comparison
 * for board models that are instance of the energy model
 * class.
 * 
 * @invar	The energy compare operand of every energy tester
 * 			must be a valid energy.
 * 			| Energy.isValidEnergy(getCompareOperand())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class EnergyTester extends ComparisonTester{
	
	/**
	 * Initializes a new energy tester with standard values.
	 * 
	 * @effect 	The new basic comparison refers the standard basic comparison
	 * 			for energy testers.
	 * 			The new energy compare operand is equal to the standard energy
	 * 			compare operand for energy testers.
	 * 			| this(STANDARD_BASIC_COMPARISON, STANDARD_COMPARE_OPERAND)
	 */
	public EnergyTester(){
		this(STANDARD_BASIC_COMPARISON, STANDARD_COMPARE_OPERAND);
	}
	
	/**
	 * Initializes a new energy tester with given
	 * basic comparison and energy compare operand.
	 * 
	 * @param	basicComparison
	 * 			The basic comparison for this new energy tester
	 * @param 	compareOperand
	 * 			The energy compare operand for this new energy tester.
	 * @effect	Sets the new basic comparison to the given basic comparison
	 * 			for this energy tester.
	 * 			| super(basicComparison)
	 * @post	The new energy compare operand of this new energy tester
	 * 			is equal to the given energy compare operand, if it does not
	 * 			refer the null reference. Otherwise the new energy compare operand
	 * 			is equal to the standard energy compare operand for energy testers.
	 * 			| if(compareOperand != null)
	 * 			|	then new.getCompareOperand().equals(compareOperand)
	 * 			| else
	 * 			| 	new.getCompareOperand().equals(STANDARD_COMPARE_OPERAND)
	 */
	public EnergyTester(BasicComparison basicComparison, Energy compareOperand){
		super(basicComparison);
		
		if(compareOperand == null)
			compareOperand = STANDARD_COMPARE_OPERAND;
		this.compareOperand = compareOperand;
	}
	
	/**
	 * The standard energy compare operand for energy testers.
	 */
	public static final Energy STANDARD_COMPARE_OPERAND = new Energy(1000);
	
	/**
	 * Returns the energy compare operand of this energy tester.
	 */
	@Basic @Raw @Immutable
	public Energy getCompareOperand(){
		return compareOperand;
	}
	
	/**
	 * The compare operand for the test.
	 */
	private final Energy compareOperand;
	
	/**
	 * Tests if the given board model satisfies the basic comparison
	 * of this energy tester with the compare operand of this energy
	 * tester as second operand in the comparison.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to undergo the test.
	 * @return 	If the given board model is an instance of the energy model
	 * 			class and if its amount of energy stocked in its accu
	 * 			satisfies the basic comparison of this energy tester with
	 * 			the compare operand of this energy tester as second operand in
	 * 			the comparison, the result is true.
	 * 			| result == (EnergyModel.class.isInstance(boardModel))
	 * 			| 			&& getBasicComparison().compareDecode(boardModel.getAccu().getAmountOfEnergy(), getCompareOperand())
	 */
	@Override
	public boolean testCondition(BoardModel boardModel) {
		return (EnergyModel.class.isInstance(boardModel))
				&& getBasicComparison().compareDecode(((EnergyModel)boardModel).getAccu().getAmountOfEnergy(), getCompareOperand());
	}
}
