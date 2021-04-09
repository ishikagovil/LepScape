import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlacedPlantTest {
	
	private double epsilon = 0.00000001;
	private float x = (float) 3.5;
	private float y = (float) 4.5;
	
	private PlacedPlant p1 = new PlacedPlant();
	//private PlacedPlant p2 = new PlacedPlant((float)3.5, (float)4.5, new PlantSpecies());
	private PlacedPlant p2 = new PlacedPlant(x, y, new PlantSpecies());

	@Test
	void testGetX() {
		assertNull(p1.getX());
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
		assertNull(p1.getY());
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
		fail("Not yet implemented");
	}

	@Test
	void testSetSpecies() {
		p1.setSpecies(null);
		fail("Not yet implemented");
	}

}
