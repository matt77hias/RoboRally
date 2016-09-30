package roborally.model.inventory.item.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import roborally.board.Board;
import roborally.board.Position;
import roborally.model.dynamicObject.Robot;
import roborally.model.energy.Accu;
import roborally.model.energy.Energy;
import roborally.model.inventory.Collector;
import roborally.model.inventory.InventoryUser;
import roborally.model.inventory.item.Bomb;
import roborally.model.staticObject.Wall;

/**
 * A test class for bomb objects. 
 * 
 * @version	Pandora: A New Hope
 * @author 	Matthias Moulin & Ruben Pieters
 *
 */
public class BombTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void construct1(){
		Bomb temp = new Bomb();
		assertEquals(temp.getBoard(), null);
		assertEquals(temp.getPosition(), null);
		assertEquals(temp.getWeight(), Bomb.STANDARD_BOMB_WEIGHT);
		assertEquals(temp.getImpactSize(), Bomb.STANDARD_BOMB_IMPACT_SIZE);
	}
	
	@Test
	public void construct2(){
		Bomb temp = new Bomb(2L);
		assertEquals(temp.getBoard(), null);
		assertEquals(temp.getPosition(), null);
		assertEquals(temp.getWeight(), Bomb.STANDARD_BOMB_WEIGHT);
		assertEquals(temp.getImpactSize(), 2L);
	}
	
	@Test
	public void construct3(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(5L,5L);
		Bomb temp = new Bomb(btemp, pos, 2L);
		assertEquals(temp.getBoard(), btemp);
		assertEquals(temp.getPosition(), pos);
		assertEquals(temp.getWeight(), Bomb.STANDARD_BOMB_WEIGHT);
		assertEquals(temp.getImpactSize(), 2L);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgument(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(5L,5L);
		@SuppressWarnings("unused")
		Wall w = new Wall(btemp,pos);
		@SuppressWarnings("unused")
		Bomb temp = new Bomb(btemp, pos, 2L);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void construct_illegalArgumentII(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(-5L,-5L);
		@SuppressWarnings("unused")
		Bomb temp = new Bomb(btemp, pos, 2L);
	}
	
	@Test
	public void setImpactSize(){
		Bomb temp = new Bomb(-100L);
		assertEquals(temp.getImpactSize(), Bomb.MAX_IMPACT_SIZE);
		temp = new Bomb(-Bomb.MAX_IMPACT_SIZE);
		assertEquals(temp.getImpactSize(), Bomb.MAX_IMPACT_SIZE);
		temp = new Bomb(-1L);
		assertEquals(temp.getImpactSize(), 1L);
		temp = new Bomb(0L);
		assertEquals(temp.getImpactSize(), 0L);
		temp = new Bomb(1L);
		assertEquals(temp.getImpactSize(), 1L);
		temp = new Bomb(100L);
		assertEquals(temp.getImpactSize(), Bomb.MAX_IMPACT_SIZE);
		temp = new Bomb(Bomb.MAX_IMPACT_SIZE);
		assertEquals(temp.getImpactSize(), Bomb.MAX_IMPACT_SIZE);
	}
	
	@Test
	public void isValidImpactSize(){
		Bomb temp = new Bomb(-100L);
		assertFalse(Bomb.isValidImpactSize(-100L));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(-Bomb.MAX_IMPACT_SIZE);
		assertFalse(Bomb.isValidImpactSize(-Bomb.MAX_IMPACT_SIZE));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(-1L);
		assertFalse(Bomb.isValidImpactSize(-1L));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(0L);
		assertTrue(Bomb.isValidImpactSize(0L));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(1L);
		assertTrue(Bomb.isValidImpactSize(1L));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(100L);
		assertFalse(Bomb.isValidImpactSize(100L));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
		temp = new Bomb(Bomb.MAX_IMPACT_SIZE);
		assertTrue(Bomb.isValidImpactSize(Bomb.MAX_IMPACT_SIZE));
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
	}
	
	@Test
	public void getImpactSize(){
		Bomb temp = new Bomb(1L);
		assertTrue(Bomb.isValidImpactSize(temp.getImpactSize()));
	}
	
	@Test
	public void getImpactArea(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		
		assertTrue(temp.getImpactArea().contains(new Position(1L,1L)));
		assertEquals(temp.getImpactArea().size(),1);
		
		btemp = new Board(20L,20L);
		pos = new Position(1L,1L);
		temp = new Bomb(btemp, pos, 1L);
		assertTrue(temp.getImpactArea().contains(new Position(0L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(0L,1L)));
		assertTrue(temp.getImpactArea().contains(new Position(0L,2L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,1L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,2L)));
		assertTrue(temp.getImpactArea().contains(new Position(2L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(2L,1L)));
		assertTrue(temp.getImpactArea().contains(new Position(2L,2L)));
		assertEquals(temp.getImpactArea().size(),9);
		
		btemp = new Board(20L,20L);
		pos = new Position(0L,0L);
		temp = new Bomb(btemp, pos, 1L);
		assertTrue(temp.getImpactArea().contains(new Position(-1L,-1L)));
		assertTrue(temp.getImpactArea().contains(new Position(-1L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(-1L,1L)));
		assertTrue(temp.getImpactArea().contains(new Position(0L,-1L)));
		assertTrue(temp.getImpactArea().contains(new Position(0L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(0L,1L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,-1L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,0L)));
		assertTrue(temp.getImpactArea().contains(new Position(1L,1L)));
		assertEquals(temp.getImpactArea().size(),9);
	}
	
	@Test (expected = IllegalStateException.class)
	public void getImpactArea_noPosition(){
		Bomb temp = new Bomb(0L);
		temp.getImpactArea();
	}
	
	@Test (expected = IllegalStateException.class)
	public void getImpactArea_terminated(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		temp.terminate();
		temp.getImpactArea();
	}
	
	@Test
	public void canBeHit(){
		Bomb temp = new Bomb(0L);
		assertFalse(temp.canBeHit());
		temp = new Bomb(0L);
		temp.pickUp();
		assertFalse(temp.canBeHit());
		
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Collector r = new Robot(btemp, pos);
		temp = new Bomb(btemp, pos, 0L);
		assertTrue(temp.canBeHit());
		r.getInventory().addInventoryItem(temp);
		assertFalse(temp.canBeHit());
		temp.terminate();
		assertFalse(temp.canBeHit());
	}
	
	@Test
	public void hit(){
		Board btemp = new Board(20L,20L);
		Bomb bomb = new Bomb(btemp, new Position(1L,1L), 1L);
		Robot r1 = new Robot(btemp, new Position(0L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r2 = new Robot(btemp, new Position(0L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r3 = new Robot(btemp, new Position(0L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r4 = new Robot(btemp, new Position(1L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r5 = new Robot(btemp, new Position(1L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r6 = new Robot(btemp, new Position(1L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r7 = new Robot(btemp, new Position(2L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r8 = new Robot(btemp, new Position(2L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r9 = new Robot(btemp, new Position(2L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r10 = new Robot(btemp, new Position(3L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r11 = new Robot(btemp, new Position(1L, 3L), new Accu(new Energy(1000), new Energy(4000)), null);
		bomb.hit();
		assertTrue(bomb.isTerminated());
		assertTrue(r1.isTerminated());
		assertTrue(r2.isTerminated());
		assertTrue(r3.isTerminated());
		assertTrue(r4.isTerminated());
		assertTrue(r5.isTerminated());
		assertTrue(r6.isTerminated());
		assertTrue(r7.isTerminated());
		assertTrue(r8.isTerminated());
		assertTrue(r9.isTerminated());
		assertFalse(r10.isTerminated());
		assertFalse(r11.isTerminated());
	}
	
	@Test
	public void hit_impactAreaPartialOutOfBoard(){
		Board btemp = new Board(20L,20L);
		Bomb bomb = new Bomb(btemp, new Position(0L,0L), 1L);
		Robot r1 = new Robot(btemp, new Position(0L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r2 = new Robot(btemp, new Position(0L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r3 = new Robot(btemp, new Position(0L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r4 = new Robot(btemp, new Position(1L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r5 = new Robot(btemp, new Position(1L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r6 = new Robot(btemp, new Position(1L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r7 = new Robot(btemp, new Position(2L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r8 = new Robot(btemp, new Position(2L, 1L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r9 = new Robot(btemp, new Position(2L, 2L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r10 = new Robot(btemp, new Position(3L, 0L), new Accu(new Energy(1000), new Energy(4000)), null);
		Robot r11 = new Robot(btemp, new Position(1L, 3L), new Accu(new Energy(1000), new Energy(4000)), null);
		bomb.hit();
		assertTrue(bomb.isTerminated());
		assertTrue(r1.isTerminated());
		assertTrue(r2.isTerminated());
		assertFalse(r3.isTerminated());
		assertTrue(r4.isTerminated());
		assertTrue(r5.isTerminated());
		assertFalse(r6.isTerminated());
		assertFalse(r7.isTerminated());
		assertFalse(r8.isTerminated());
		assertFalse(r9.isTerminated());
		assertFalse(r10.isTerminated());
		assertFalse(r11.isTerminated());
	}
	
	@Test
	public void hit_bombCascade(){
		Board btemp = new Board(20L,20L);
		Bomb bomb = new Bomb(btemp, new Position(0L,1L), 1L);
		Bomb bomb2 = new Bomb(btemp, new Position(0L,0L), 1L);
		bomb.hit();
		assertTrue(bomb.isTerminated());
		assertTrue(bomb2.isTerminated());
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_terminated(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		temp.terminate();
		temp.hit();
	}
	
	@Test (expected = IllegalStateException.class)
	public void hit_noBoard(){
		Bomb temp = new Bomb(0L);
		temp.hit();
	}
	
	@Test
	public void use_suicide(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		InventoryUser r = new Robot(btemp, pos, new Accu(new Energy(1000), new Energy(4000)), null);
		Robot rr = new Robot(btemp, new Position(0L,1L), new Accu(new Energy(1000), new Energy(4000)), null);
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertTrue(temp.isTerminated());
		assertTrue(r.isTerminated());
		assertFalse(rr.isTerminated());
	}
	
	@Test
	public void use_murderAndSuicide(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 1L);
		InventoryUser r = new Robot(btemp, pos, new Accu(new Energy(1000), new Energy(4000)), null);
		Robot rr = new Robot(btemp, new Position(0L,1L), new Accu(new Energy(1000), new Energy(4000)), null);
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertTrue(temp.isTerminated());
		assertTrue(r.isTerminated());
		assertTrue(rr.isTerminated());
	}
	
	@Test
	public void use_suicide_throughInventory(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		InventoryUser r = new Robot(btemp, pos, new Accu(new Energy(1000), new Energy(4000)), null);
		Robot rr = new Robot(btemp, new Position(0L,1L), new Accu(new Energy(1000), new Energy(4000)), null);
		r.getInventory().addInventoryItem(temp);
		r.getInventory().useInventoryItem(temp);
		assertTrue(temp.isTerminated());
		assertTrue(r.isTerminated());
		assertFalse(rr.isTerminated());
	}
	
	@Test
	public void use_murderAndSuicide_throughInventory(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 1L);
		InventoryUser r = new Robot(btemp, pos, new Accu(new Energy(1000), new Energy(4000)), null);
		Robot rr = new Robot(btemp, new Position(0L,1L), new Accu(new Energy(1000), new Energy(4000)), null);
		r.getInventory().addInventoryItem(temp);
		r.getInventory().useInventoryItem(temp);
		assertTrue(temp.isTerminated());
		assertTrue(r.isTerminated());
		assertTrue(rr.isTerminated());
	}
	
	@Test
	public void use_noBoard(){
		Bomb temp = new Bomb(0L);
		InventoryUser r = new Robot();
		r.getInventory().addInventoryItem(temp);
		temp.use(r);
		assertFalse(temp.isTerminated());
		assertFalse(r.isTerminated());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void use_nullCollector(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		temp.use(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void use_invalidCollector(){
		Board btemp = new Board(20L,20L);
		Position pos = new Position(1L,1L);
		Bomb temp = new Bomb(btemp, pos, 0L);
		InventoryUser r = new Robot();
		temp.use(r);
	}

}
