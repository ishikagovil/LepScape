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
	void testCreateDefault() {
		m.createDefault();
		fail("Not yet implemented");
	}

	@Test
	void testGetBoundaries() {
		assertNull(m.getBoundaries());
		fail("Not yet implemented");
	}

	@Test
	void testCreateNewConditions() {
		m.createNewConditions();
		fail("Not yet implemented");
	}

	@Test
	void testUpdateOutlineSection() {
		m.updateOutlineSection();
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
		m.placePlant();
		fail("Not yet implemented");
	}

	@Test
	void testCostUpdate() {
		m.costUpdate();
		fail("Not yet implemented");
	}

	/*@Test
	void testStartStage() {
		m.start(null);
		fail("Not yet implemented");
	}*/

	@Test
	void testGetX() {
		m.getX();
		fail("Not yet implemented");
	}

	@Test
	void testGetY() {
		m.getY();
		fail("Not yet implemented");
	}

}
