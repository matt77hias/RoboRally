package roborally.model.energy.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.model.dynamicObject.Robot;
import roborally.model.energy.*;

/**
 * A test class for accu objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class AccuTest {
	
	Accu amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws;
	Accu amountOfEnergy15000Ws_and_energyCapacityLimit1KWH;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws = new Accu(new Energy(15000D,EnergyUnit.WS),new Energy(20000D,EnergyUnit.WS));
		amountOfEnergy15000Ws_and_energyCapacityLimit1KWH = new Accu(new Energy(15000D,EnergyUnit.WS),new Energy(1D,EnergyUnit.KWH));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void construct1(){
		Accu temp = new Accu();
		assertEquals(temp.getAmountOfEnergy().subtract(Accu.STANDARD_ACCU_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct2(){
		Energy am = new Energy(100D,EnergyUnit.WS);
		Accu temp = new Accu(am);
		assertEquals(temp.getAmountOfEnergy().subtract(am).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct3(){
		Energy am = new Energy(100D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.WS);
		Accu temp = new Accu(am, cap);
		assertEquals(temp.getAmountOfEnergy().subtract(am).getEnergyAmount(),0.0D,0.01D);
		assertEquals(temp.getEnergyCapacityLimit().subtract(cap).getEnergyAmount(),0.0D,0.01D);
		assertFalse(temp.isTerminated());
	}
	
	@Test
	public void construct_withoutClone(){
		Accu a1 = new Accu();
		Accu a2 = new Accu();
		assertEquals(a1.getAmountOfEnergy().subtract(Accu.STANDARD_ACCU_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(a1.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(a2.getAmountOfEnergy().subtract(Accu.STANDARD_ACCU_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(a2.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		a1.dischargeAmountOfEnergy(a1.getAmountOfEnergy());
		assertEquals(a1.getAmountOfEnergy().getEnergyAmount(),0.0D,0.01D);
		assertEquals(a1.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(a2.getAmountOfEnergy().subtract(Accu.STANDARD_ACCU_AMOUNT_OF_ENERGY).getEnergyAmount(),0.0D,0.01D);
		assertEquals(a2.getEnergyCapacityLimit().subtract(Accu.STANDARD_ACCU_ENERGY_CAPACITY).getEnergyAmount(),0.0D,0.01D);
		
	}
	
	@Test
	public void terminate(){
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.terminate();
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().getEnergyAmount(),0.0D,0.01D);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.terminate();
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().getEnergyAmount(),0.0D,0.01D);
	}
	
	@Test
	public void terminate_hasOwner(){
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.own();
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
	}
	
	@Test
	public void isValidAmountOfEnergy_amountAccepted_capacityAccepted_sameUnits(){
		Energy am = new Energy(500D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.WS);
		assertTrue(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_amountAccepted_capacityAccepted_differentUnitsI(){
		Energy am = new Energy(500D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.KWH);
		assertTrue(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_amountAccepted_capacityAccepted_differentUnitsII(){
		Energy am = new Energy(1D,EnergyUnit.KWH);
		Energy cap = new Energy(10000000D,EnergyUnit.WS);
		assertTrue(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_negativeAmountRejected_capacityAccepted_sameUnits(){
		Energy am = new Energy(-500D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.WS);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_negativeAmountRejected_capacityAccepted_differentUnits(){
		Energy am = new Energy(-500D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.KWH);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_positiveAmountRejected_capacityAccepted_sameUnits(){
		Energy am = new Energy(1001D,EnergyUnit.WS);
		Energy cap = new Energy(1000D,EnergyUnit.WS);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_positiveAmountRejected_capacityAccepted_differentUnits(){
		Energy am = new Energy(3600001D,EnergyUnit.WS);
		Energy cap = new Energy(1D,EnergyUnit.KWH);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_negativeAmountRejected_capacityRejected_sameUnits(){
		Energy am = new Energy(-1000D,EnergyUnit.WS);
		Energy cap = new Energy(-500D,EnergyUnit.WS);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_negativeAmountRejected_capacityRejected_differentUnits(){
		Energy am = new Energy(-1000D,EnergyUnit.KWH);
		Energy cap = new Energy(-500D,EnergyUnit.WS);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_positiveAmountRejected_capacityRejected_sameUnits(){
		Energy am = new Energy(1000D,EnergyUnit.WS);
		Energy cap = new Energy(-500D,EnergyUnit.WS);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}	
	
	@Test
	public void isValidAmountOfEnergy_positiveAmountRejected_capacityRejected_differentUnits(){
		Energy am = new Energy(1000D,EnergyUnit.WS);
		Energy cap = new Energy(-500D,EnergyUnit.KWH);
		assertFalse(Accu.isValidAmountOfEnergy(am,cap));
	}
	
	@Test
	public void isValidAmountOfEnergy_nullArguments(){
		Energy am = new Energy(1000D,EnergyUnit.WS);
		Energy cap = new Energy(-500D,EnergyUnit.KWH);
		assertFalse(Accu.isValidAmountOfEnergy(null,cap));
		assertFalse(Accu.isValidAmountOfEnergy(am,null));
		assertFalse(Accu.isValidAmountOfEnergy(null,null));
	}
	
	@Test
	public void canHaveAsAmountOfEnergy_amountAccepted_sameUnits(){
		Energy am = new Energy(10000D,EnergyUnit.WS);
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.canHaveAsAmountOfEnergy(am));
	}	
	
	@Test
	public void canHaveAsAmountOfEnergy_amountAccepted_differentUnits(){
		Energy am = new Energy(10000D,EnergyUnit.WS);
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.canHaveAsAmountOfEnergy(am));
	}	
	
	@Test
	public void canHaveAsAmountOfEnergy_positiveAmountRejected_sameUnits(){
		Energy am = new Energy(21000D,EnergyUnit.WS);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.canHaveAsAmountOfEnergy(am));
	}
	
	@Test
	public void canHaveAsAmountOfEnergy_positiveAmountRejected_differentUnits(){
		Energy am = new Energy(1D,EnergyUnit.KWH);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.canHaveAsAmountOfEnergy(am));
	}
		
	@Test
	public void canHaveAsAmountOfEnergy_negativeAmountRejected_sameUnits(){
		Energy am = new Energy(-5000D,EnergyUnit.WS);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.canHaveAsAmountOfEnergy(am));
	}
	
	@Test
	public void canHaveAsAmountOfEnergy_negativeAmountRejected_differentUnits(){
		Energy am = new Energy(-5000D,EnergyUnit.WS);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.canHaveAsAmountOfEnergy(am));
	}
	
	@Test
	public void canHaveAsAmountOfEnergy_nullArgument(){
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.canHaveAsAmountOfEnergy(null));
	}
	
	@Test
	public void isValidEnergyCapacityLimit_CapacityAccepted(){
		Energy cap = new Energy(20000D,EnergyUnit.WS);
		assertTrue(Accu.isValidEnergyCapacityLimit(cap));
	}
	
	@Test
	public void isValidEnergyCapacityLimit_negativeCapacityRejected(){
		Energy cap = new Energy(-20000D,EnergyUnit.WS);
		assertFalse(Accu.isValidEnergyCapacityLimit(cap));
	}
	
	@Test
	public void isValidEnergyCapacityLimit_maxValueCapacityAccepted(){
		Energy cap = new Energy(Double.MAX_VALUE,EnergyUnit.WS);
		assertTrue(Accu.isValidEnergyCapacityLimit(cap));
	}
	
	@Test
	public void isValidEnergyCapacityLimit_nullArgument(){
		assertFalse(Accu.isValidEnergyCapacityLimit(null));
	}
	
	@Test
	public void getAmountOfEnergy(){
		Energy am = new Energy(15000D,EnergyUnit.WS);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),am);
	}
	
	@Test
	public void getEnergyStoragePercentage(){
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyStoragePercentage(),75.00,0.01D);
		Accu a1 = new Accu(new Energy(3000D,EnergyUnit.WS),new Energy(9000D,EnergyUnit.WS));
		assertEquals(a1.getEnergyStoragePercentage(),33.33,0.01D);
		Accu a2 = new Accu(new Energy(3000D,EnergyUnit.WS),new Energy(3000D,EnergyUnit.WS));
		assertEquals(a2.getEnergyStoragePercentage(),100.00,0.01D);
		Accu a3 = new Accu(new Energy(1800000D,EnergyUnit.WS),new Energy(1D,EnergyUnit.KWH));
		assertEquals(a3.getEnergyStoragePercentage(),50.00,0.01D);
	}
	
	@Test
	public void getPercentage(){
		assertEquals(Accu.getPercentage(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyStoragePercentage()/100),"75%");
		Accu a1 = new Accu(new Energy(3000D,EnergyUnit.WS),new Energy(9000D,EnergyUnit.WS));
		assertEquals(Accu.getPercentage(a1.getEnergyStoragePercentage()/100),"33%");
		Accu a2 = new Accu(new Energy(3000D,EnergyUnit.WS),new Energy(3000D,EnergyUnit.WS));
		assertEquals(Accu.getPercentage(a2.getEnergyStoragePercentage()/100),"100%");
		Accu a3 = new Accu(new Energy(1800000D,EnergyUnit.WS),new Energy(1D,EnergyUnit.KWH));
		assertEquals(Accu.getPercentage(a3.getEnergyStoragePercentage()/100),"50%");
		Accu a4 = new Accu(new Energy(6000D,EnergyUnit.WS),new Energy(9000D,EnergyUnit.WS));
		assertEquals(Accu.getPercentage(a4.getEnergyStoragePercentage()/100),"67%");
	}
	
	@Test
	public void recharge_zeroAmountAccepted_sameUnits(){
		Energy am = new Energy(0D,EnergyUnit.WS);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.rechargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void recharge_zeroAmountAccepted_differentUnits(){
		Energy am = new Energy(0D,EnergyUnit.KWH);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.rechargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void recharge_positiveAmountAccepted_sameUnits(){
		Energy am = new Energy(2000D,EnergyUnit.WS);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().add(am);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.rechargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void recharge_positiveAmountAccepted_differentUnits(){
		Energy am = new Energy(0.5,EnergyUnit.KWH);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.getAmountOfEnergy().add(am);
		amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.rechargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit1KWH.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void recharge_MaxPositiveAmountAccepted(){
		Energy am = new Energy(5000D,EnergyUnit.WS);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().add(am);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.rechargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void discharge_zeroAmountAccepted_sameUnits(){
		Energy am = new Energy(0D,EnergyUnit.WS);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.dischargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void discharge_zeroAmountAccepted_differentUnits(){
		Energy am = new Energy(0D,EnergyUnit.KWH);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.dischargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void discharge_positiveAmountAccepted(){
		Energy am = new Energy(5000D,EnergyUnit.WS);
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().subtract(am);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.dischargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void discharge_MaxPositiveAmountAccepted(){
		Energy am = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		Energy exp = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().subtract(am);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.dischargeAmountOfEnergy(am);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),exp);
	}
	
	@Test
	public void setEnergyCapacityLimit_positiveLargerCapacityAccepted_sameUnits(){
		Energy cap = new Energy(25000D,EnergyUnit.WS);
		Energy am = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.setEnergyCapacityLimit(cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyCapacityLimit(),cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),am);
	}
	
	@Test
	public void setEnergyCapacityLimit_positiveLargerCapacityAccepted_differentUnits(){
		Energy cap = new Energy(1D,EnergyUnit.KWH);
		Energy am = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.setEnergyCapacityLimit(cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyCapacityLimit(),cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),am);
	}
	
	@Test
	public void setEnergyCapacityLimit_positiveLowerCapacityAccepted_sameAmountOfEnergy(){
		Energy cap = new Energy(16000D,EnergyUnit.WS);
		Energy am = amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy();
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.setEnergyCapacityLimit(cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyCapacityLimit(),cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),am);
	}
	
	@Test
	public void setEnergyCapacityLimit_positiveLowerCapacityAccepted_lessAmountOfEnergy(){
		Energy cap = new Energy(10000D,EnergyUnit.WS);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.setEnergyCapacityLimit(cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getEnergyCapacityLimit(),cap);
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy(),cap);
	}
	
	@Test
	public void own(){
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.hasOwner());
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.own();
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.hasOwner());
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.own();
		assertTrue(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.hasOwner());
	}
	
	@Test
	public void transferEnergyFrom_fullTransfer(){
		Energy am = new Energy(1000D,EnergyUnit.WS);
		Energy cap = new Energy(2000D,EnergyUnit.WS);
		Accu temp = new Accu(am, cap);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.transferEnergyFrom(temp);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().getEnergyAmount(),16000D,0.01D);
		assertTrue(temp.isTerminated());
		assertEquals(temp.getAmountOfEnergy().getEnergyAmount(),0.0D,0.01D);
	}
	
	@Test
	public void transferEnergyFrom_partialTransfer(){
		Energy am = new Energy(6000D,EnergyUnit.WS);
		Energy cap = new Energy(8000D,EnergyUnit.WS);
		Accu temp = new Accu(am, cap);
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.transferEnergyFrom(temp);
		assertFalse(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.isTerminated());
		assertEquals(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.getAmountOfEnergy().getEnergyAmount(),20000D,0.01D);
		assertFalse(temp.isTerminated());
		assertEquals(temp.getAmountOfEnergy().getEnergyAmount(),1000D,0.01D);
	}
	
	@Test
	public void isValidTransferAccu(){
		assertTrue(Accu.isValidTransferAccu(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws));
		amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws.terminate();
		assertFalse(Accu.isValidTransferAccu(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws));
	}
	
	@Test
	public void isValidTransferAccu_nullArgument(){
		assertFalse(Accu.isValidTransferAccu(null));
	}
	
	@Test
	public void isValidAccu(){
		assertTrue(Accu.isValidAccu(amountOfEnergy15000Ws_and_energyCapacityLimit20000Ws));
		assertFalse(Accu.isValidAccu(null));
	}
	
	@Test
	public void getOriginalEnergyCapacityLimit(){
		Energy oldCap = new Energy(2000);
		Accu accu = new Accu(new Energy(1000), oldCap);
		assertEquals(accu.getOriginalEnergyCapacityLimit(), oldCap);
		accu.setEnergyCapacityLimit(new Energy(1000));
		assertEquals(accu.getOriginalEnergyCapacityLimit(), oldCap);
	}
	
	@Test
	public void transferHealingFrom_full(){
		Accu r = new Accu(new Energy(1000), new Energy(8000));
		r.setEnergyCapacityLimit(new Energy(4000));
		assertEquals(r.getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(r.getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy rcap = r.getEnergyCapacityLimit();
		
		Energy am = new Energy(1000);
		Accu ak = new Accu(am, new Energy(8000));
		
		double factor = 0.5D;
		
		r.transferHealingFrom(ak, factor);
		
		assertEquals(r.getEnergyCapacityLimit(), rcap.add(am.multiply(factor)));
		assertFalse(r.isTerminated());
		assertTrue(ak.isTerminated());
	}

	@Test
	public void transferHealingFrom_partial(){
		Accu r = new Accu(new Energy(1000), new Energy(8000));
		r.setEnergyCapacityLimit(new Energy(4000));
		assertEquals(r.getEnergyCapacityLimit(), new Energy(4000));
		assertEquals(r.getOriginalEnergyCapacityLimit(), new Energy(8000));
		
		Energy diff = r.getOriginalEnergyCapacityLimit().subtract(r.getEnergyCapacityLimit());
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
		
		double factor = 0.5D;
		
		r.transferHealingFrom(ak, factor);
		
		assertEquals(r.getEnergyCapacityLimit(), r.getOriginalEnergyCapacityLimit());
		assertFalse(r.isTerminated());
		assertFalse(ak.isTerminated());
		assertEquals(ak.getEnergyCapacityLimit(), ak.getOriginalEnergyCapacityLimit());
		assertEquals(ak.getAmountOfEnergy(), am.subtract(diff.multiply(1/factor)));
	}
	
	@Test
	public void transferHealingFrom_nothing(){
		Accu r = new Accu(new Energy(1000), new Energy(8000));
		Energy rcap = new Energy(10000);
		r.setEnergyCapacityLimit(rcap);
		
		Energy am = new Energy(9000);
		Accu ak = new Accu(am, new Energy(9000));
				
		double factor = 0.5D;
		
		r.transferHealingFrom(ak, factor);
		
		assertEquals(r.getEnergyCapacityLimit(), rcap);
		assertFalse(r.isTerminated());
		assertFalse(ak.isTerminated());
		assertEquals(ak.getEnergyCapacityLimit(), ak.getOriginalEnergyCapacityLimit());
		assertEquals(ak.getAmountOfEnergy(), am);
	}
	
	@Test (expected = NullPointerException.class)
	public void detachEnergyModel_nullEnergyModel(){
		Accu a = new Accu();
		a.detachFromEnergyModel(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void detachEnergyModel_notOwningEnergyModel(){
		EnergyModel r = new Robot();
		Accu a = new Accu();
		a.detachFromEnergyModel(r);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void detachEnergyModel_notTerminatedOwningEnergyModel(){
		EnergyModel r = new Robot();
		r.getAccu().detachFromEnergyModel(r);
	}
}
