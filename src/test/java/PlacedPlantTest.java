import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlacedPlantTest {
	
	private double epsilon = 0.00000001;
	private float x = (float) 3.5;
	private float y = (float) 4.5;
	private PlantSpecies testSpecies = new PlantSpecies("a", "b", "c", 0, 0, 0, true, 0, 0, 0);
	
	private PlacedPlant p1 = new PlacedPlant();
	//private PlacedPlant p2 = new PlacedPlant((float)3.5, (float)4.5, new PlantSpecies());
	private PlacedPlant p2 = new PlacedPlant(x, y, testSpecies);

	@Test
	void testGetX() {
		assertTrue(Math.abs(3.5 - p2.getX()) < epsilon);
	}

	@Test
	void testSetX() {
		p1.setX((float)15.5);
		assertTrue(Math.abs(15.5 - p1.getX()) < epsilon);
		p2.setX((float)12);
		assertTrue(Math.abs(12 - p2.getX()) < epsilon);
	}

	@Test
	void testGetY() {
		assertTrue(Math.abs(4.5 - p2.getY()) < epsilon);
	}

	@Test
	void testSetY() {
		p1.setY((float)15.5);
		assertTrue(Math.abs(15.5 - p1.getY()) < epsilon);
		p2.setY((float)12);
		assertTrue(Math.abs(12 - p2.getY()) < epsilon);
	}

	@Test
	void testGetSpecies() {
		assertNull(p1.getSpecies());
		assertEquals(p2.getSpecies(), testSpecies);
	}

	@Test
	void testSetSpecies() {
		p1.setSpecies(testSpecies);
		assertEquals(p1.getSpecies(), testSpecies);
	}
	
	@Test
	void testGetName() {
		String name = "b-a";
		assertEquals(name, p2.getName());
	}
	
	@Test 
	void testToString() {
		assertEquals(p1.toString(), p1.getName());
	}
	
	@Test
	void testPdfDescription() {
		String desc = p2.pdfDescription();
		PlantSpecies ps = p2.getSpecies();
		assertTrue(
			desc.contains(ps.getCommonName()) &&
			desc.contains(ps.getGenusName()) &&
			desc.contains(ps.getSpeciesName())
		);
	}

}
