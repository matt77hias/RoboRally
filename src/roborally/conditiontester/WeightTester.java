package roborally.conditiontester;

import roborally.model.BoardModel;
import roborally.model.inventory.item.InventoryModel;
import roborally.model.weight.Weight;
import be.kuleuven.cs.som.annotate.*;

/**
 * A condition tester that tests a basic weight comparison
 * for board models that are instance of the inventory model
 * class.
 * 
 * @invar	The weight compare operand of every weight tester
 * 			must be a valid weight.
 * 			| Weight.isValidEnergy(getCompareOperand())
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class WeightTester extends ComparisonTester{

	/**
	 * Initializes a new weight tester with standard values.
	 * 
	 * @effect 	The new basic comparison refers the standard basic comparison
	 * 			for weight testers.
	 * 			The new weight compare operand is equal to the standard weight
	 * 			compare operand for weight testers.
	 * 			| this(STANDARD_BASIC_COMPARISON, STANDARD_COMPARE_OPERAND)
	 */
	public WeightTester(){
		this(STANDARD_BASIC_COMPARISON, STANDARD_COMPARE_OPERAND);
	}
	
	/**
	 * Initializes a new weight tester with given
	 * basic comparison and weight compare operand.
	 * 
	 * @param	basicComparison
	 * 			The basic comparison for this new weight tester
	 * @param 	compareOperand
	 * 			The weight compare operand for this new weight tester.
	 * @effect	Sets the new basic comparison to the given basic comparison
	 * 			for this weight tester.
	 * 			| super(basicComparison)
	 * @post	The new weight compare operand of this new weight tester
	 * 			is equal to the given weight compare operand, if it does not
	 * 			refer the null reference. Otherwise the new weight compare operand
	 * 			is equal to the standard weight compare operand for weight testers.
	 * 			| if(compareOperand != null)
	 * 			|	then new.getCompareOperand().equals(compareOperand)
	 * 			| else
	 * 			| 	new.getCompareOperand().equals(STANDARD_COMPARE_OPERAND)
	 */
	public WeightTester(BasicComparison basicComparison, Weight compareOperand){
		super(basicComparison);
		
		if(compareOperand == null)
			compareOperand = STANDARD_COMPARE_OPERAND;
		this.compareOperand = compareOperand;
	}
	
	/**
	 * The standard weight compare operand for weight testers.
	 */
	public static final Weight STANDARD_COMPARE_OPERAND = new Weight(100);
	
	/**
	 * Returns the weight compare operand of this weight tester.
	 */
	@Basic @Raw @Immutable
	public Weight getCompareOperand(){
		return compareOperand;
	}
	
	/**
	 * The compare operand for the test.
	 */
	private final Weight compareOperand;
	
	/**
	 * Tests if the given board model satisfies the basic comparison
	 * of this weight tester with the compare operand of this weight
	 * tester as second operand in the comparison.
	 * 
	 * @param 	boardModel
	 * 			The board model that has to undergo the test.
	 * @return 	If the given board model is an instance of the inventory model
	 * 			class and if its weight satisfies the basic comparison of this
	 * 			weight tester with the compare operand of this weight tester as
	 * 			second operand in the comparison, the result is true.
	 * 			| result == (InventoryModel.class.isInstance(boardModel))
	 * 			| 			&& getBasicComparison().compareDecode(((InventoryModel)boardModel).getWeight(), getCompareOperand())
	 */
	@Override
	public boolean testCondition(BoardModel boardModel) {
		return (InventoryModel.class.isInstance(boardModel))
				&& getBasicComparison().compareDecode(((InventoryModel)boardModel).getWeight(), getCompareOperand());
	}
}
