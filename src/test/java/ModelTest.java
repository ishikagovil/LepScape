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

//	@Test
//	void testPlacePlant() {
//		m.placePlant(x, x, null);
//		fail("Not yet implemented");
//	}

	@Test
	void testCostUpdate() {
		m.costUpdate();
		fail("Not yet implemented");
	}
	@Test
	void testSetScale() {
		m.setScale(5);
		assertEquals(5, m.getScale());
	}

	@Test
	void testSetTranslate() {
		m.setTranslate(new double[]{2.1, 4.3});
		assertEquals(2.1, m.getTranslate()[0]);
		assertEquals(4.3, m.getTranslate()[1]);
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
	@Test
	void testTranslateScaledPlot() {
		m.getGarden().setPolygonCorners(2, 0);
		m.getGarden().setPolygonCorners(4, 1);
		m.getGarden().setPolygonCorners(1, 4);
		m.getGarden().setPolygonCorners(4, 4);
		m.setScale(5);
		double[] modelTranslations = m.translateScaledPlot(new double[] {5,2});
		assertEquals(0, modelTranslations[0]);
		assertEquals(2, modelTranslations[1]);
	}
	
	@Test
	void testCalculateLineDistance() {
		double distance = m.calculateLineDistance(2, 2, 5, 6);
		assertEquals(5, distance);
	}

}
