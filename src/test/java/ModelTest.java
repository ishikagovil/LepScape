import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class ModelTest {
	public int budget = 10;
	public Garden garden = new Garden();
	public Map<String, PlantSpecies> plantDirectory = new HashMap<>();
	public Map<String, Lep> lepDirectory = new HashMap<>();
	public float x = (float) 10.0;
	public float y = (float) 5.0;
	public PlantSpecies plant1 = new PlantSpecies("a", "b", "c", 0, 0, 0, false, 0, 0, 0);
	public PlantSpecies plant2 = new PlantSpecies();
	public int cost = 0;
	
	public Model m = new Model();
	
	@Test
	void testSetEdit() {
		m.setToEdit();
		assertTrue(m.editing());
	}
	
	@Test
	void testEditGardenIndex() {
		m.setEditGardenIndex(5);
		assertEquals(m.getEditGardenIndex(), 5);
	}
	

	@Test
	void testSetGarden() {
		m.setGarden(garden);
		assertEquals(garden, m.getGarden());
	}
	
	@Test
	void testPlantDirectory() {
		m.setPlantDirectory(plantDirectory);
		assertEquals(m.getPlantInfo(), plantDirectory);
	}
	
	@Test
	void testLepDirectory() {
		m.setLepDirectory(lepDirectory);
		assertEquals(m.getLepDirectory(), lepDirectory);
		
		Lep lep = new Lep("a", "b", "c");
		m.getLepDirectory().put("lep", lep);
		ArrayList<PlantSpecies> thrives = new ArrayList<>();
		thrives.add(plant1);
		lep.setThrivesIn(thrives);

		m.setLepDirectory(lepDirectory);

		assertEquals(lep.getThrivesIn().get(0).getGenusName(), "b");
	}
	
	@Test
	void testPlaceAndRemovePlant() {
		m.getPlantInfo().put("plant", plant1);
		m.placePlant(0, 0, "plant", "id", false);
		assertEquals(m.getGarden().getPlants().size(), 1);
		m.removePlant("plant", "id");
		assertEquals(m.deleted.size(), 1);
	}
	
	@Test
	void testUpdateXY() {
		m.getPlantInfo().put("plant2", plant2);
		m.placePlant(0, 0, "plant2", "id2", false);
		m.updateXY("id2");
		assertEquals(m.getGarden().placedPlants.get("id2").getX(), m.getX());
		assertEquals(m.getGarden().placedPlants.get("id2").getY(), m.getY());
	}
	
//	@Test
//	void testFilteredList() {
//		m.getPlantInfo().clear();
//		m.getPlantInfo().put("testPlant", new PlantSpecies("a", "b", "c", 3, 3, 3, false, 10, 10, 10));
//		Conditions c = new Conditions(SoilType.ROCK, MoistureType.WET, LightType.INTENSE);
//		ArrayList<String> list = m.getFilteredList(c);
//		assertEquals(list.size(), 1);
//	}
	
	@Test
	void testBudget() {
		m.setBudget(5);
		assertEquals(m.getBudget(), 5);
	}

//	@Test
//	void testLepCount() {
//		m.getGarden().setNumLeps(30);
//		assertEquals(m.getLepCount(), 30);
//	}

//	@Test
//	void testPlacePlant() {
//		m.placePlant(x, x, null);
//		fail("Not yet implemented");
//	}

	@Test
	void testSetScale() {
		m.setScale(5);
		assertEquals(5, m.getScale());
	}

	@Test
	void testSetTranslate() {
		m.setTranslate(new Vector2(2.1, 4.3));
		assertEquals(2.1, m.getTranslate().getX());
		assertEquals(4.3, m.getTranslate().getY());
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
		Vector2 modelTranslations = m.translateScaledPlot(new Vector2(5,2));
		assertEquals(0, modelTranslations.getX());
		assertEquals(2, modelTranslations.getY());
	}
	
	@Test
	void testCalculateLineDistance() {
		double distance = m.calculateLineDistance(2, 2, 5, 6);
		assertEquals(5, distance);
	}

}
