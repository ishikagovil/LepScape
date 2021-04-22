import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ModelTest {
	public int budget = 10;
	public Garden garden = new Garden();
	public Map<String, PlantSpecies> plantDirectory ;
	public float x = (float) 10.0;
	public float y = (float) 5.0;
	public PlantSpecies plant1 = new PlantSpecies();
	public PlantSpecies plant2 = new PlantSpecies();
	public int cost = 0;
	
	public Model m = new Model();

	@Test
	void testSetGarden() {
		m.setGarden(garden);
		assertEquals(garden, m.getGarden());
	}
	@Test
	void testCreateNewConditions() {
		m.createNewConditions();
		fail("Not yet implemented");
	}

	@Test
	void testUpdateConditions() {
		m.updateConditions();
		fail("Not yet implemented");
	}

	@Test
	void testFindDimensions() {
		m.findDimensions();
		fail("Not yet implemented");
	}

	@Test
	void testComparePlant() {
		m.comparePlant();
		fail("Not yet implemented");
	}

	@Test
	void testGetPlantInfo() {
		m.getPlantInfo();
		fail("Not yet implemented");
	}

	@Test
	void testValidatePlacement() {
		m.validatePlacement();
		fail("Not yet implemented");
	}

	@Test
	void testPlacePlant() {
		m.placePlant(x, x, null);
		fail("Not yet implemented");
	}

	@Test
	void testCostUpdate() {
		m.costUpdate();
		fail("Not yet implemented");
	}

	@Test
	void testSetX() {
		m.setX(5);
		assertEquals(5, m.getX());
	}

	@Test
	void testSetY() {
		m.setY(5.5);
		assertEquals(5.5, m.getY());
	}
	@Test
	void testSetLengthPerPixel() {
		m.setLengthPerPixel(20.5);
		assertEquals(20.5, m.getLengthPerPixel());
	}
	

}
