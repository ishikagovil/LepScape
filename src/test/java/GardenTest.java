import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	private Garden g1 = new Garden(15, 20);
	private Garden g2 = new Garden();
	
	
	@Test
	void testGetNumLeps() {
		assertEquals(15, g1.getNumLeps());
		assertNull(g2.getNumLeps());
	}

	@Test
	void testSetNumLeps() {
		g1.setNumLeps(200);
		assertEquals(200, g1.getNumLeps());
		g2.setNumLeps(15);
		assertEquals(15, g2.getNumLeps());
	}
	
	@Test
	void testAddNumLeps() {
		g1.addNumLeps(15);
		assertEquals(215, g1.getNumLeps());
		g2.addNumLeps(-10);
		assertEquals(5, g2.getNumLeps());
	}

	@Test
	void testGetCost() {
		assertEquals(20, g1.getCost());
		assertNull(g2.getCost());
	}

	@Test
	void testSetCost() {
		g1.setCost(100);
		assertEquals(100, g1.getCost());
		g2.setCost(30);
		assertEquals(30, g2.getCost());
	}
	
	@Test
	void testAddCost() {
		g1.addCost(250);
		assertEquals(350, g1.getCost());
		g2.addCost(-20);
		assertEquals(10, g2.getCost());
	}

	@Test
	void testGetOutline() {
		assertNull(g1.getOutline().get(0));
		assertNull(g2.getOutline().get(0));
	}

	@Test
	void testGetPlants() {
		assertNull(g1.getPlants().get(0));
		assertNull(g2.getPlants().get(0));
	}

	@Test
	void testGetSections() {
		assertNull(g1.getSections().get(0));
		assertNull(g2.getSections().get(0));
	}

	@Test
	void testGetLeps() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCompostBin() {
		assertTrue(g1.getCompostBin().isEmpty());
		assertTrue(g1.getCompostBin().isEmpty());
	}

}
