import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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
		assertNull(g1.getOutline());
	}

	@Test
	void testGetPlants() {
		assertNull(g1.getPlants());
	}

	@Test
	void testGetSections() {
		assertNull(g1.getSections());
	}

	@Test
	void testGetLeps() {
		assertNull(g1.getLeps());
		fail("Not yet implemented");
	}

	@Test
	void testGetCompostBin() {
		assertTrue(g1.getCompostBin().isEmpty());
		assertTrue(g1.getCompostBin().isEmpty());
	}
	@Test
	void testUpdateOutline() {
		g1.updateOutline(0, 0);
		assertArrayEquals(new double[]{0,0}, g1.outline.get(0));
		g2.updateOutline(5, 5);
		g2.updateOutline(5, 10);
		assertArrayEquals(new double[]{5,10}, g2.outline.get(1));
		
	}
	@Test
	void testClearBoundaries() {
		g1.clearOutline();
		assertEquals(0, g1.getOutline().size());
		assertEquals(0, g1.getPolygonCorners().size());
	}
	
	@Test
	void testGetExtremes() {
		g1.updateOutline(3, 3);
		g1.updateOutline(5, 2);
		g1.updateOutline(1, 9);
		g1.updateOutline(2, 1);
		g1.setPolygonCorners(1, 0);
		g1.setPolygonCorners(4, 1);
		g1.setPolygonCorners(0, 4);
		g1.setPolygonCorners(4, 4);
		ArrayList<double[]> extrema = g1.getExtremes();
		assertArrayEquals(new double[] {1,0}, extrema.get(0));
		assertArrayEquals(new double[] {5,2}, extrema.get(1));
		assertArrayEquals(new double[] {1,9}, extrema.get(2));
		assertArrayEquals(new double[] {0,4}, extrema.get(3));
	}
	
}
