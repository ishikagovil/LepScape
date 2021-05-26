import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GardenTest {
	
	private Garden g1;
	private Garden g2;
	private PlantSpecies testSpecies;
	private PlacedPlant testPlant;
	private Conditions c;
	
	
	@BeforeEach
	void beforeEach() {
		g1 = new Garden(20);
		g2 = new Garden();
		testSpecies = new PlantSpecies("a", "b", "c", 0, 3, 6, true, 0, 0, 0);
		testPlant = new PlacedPlant(0, 0, testSpecies);
		c = new Conditions(SoilType.ANY, MoistureType.ANY, LightType.ANY);
	}
	
	@Test
	void testGetLepCount() {
		g1.placedPlants.put("test", testPlant);
		assertEquals(3, g1.getLepCount());
	}

	@Test
	void testGetCost() {
		g1.placedPlants.put("test", testPlant);
		assertEquals(g1.getCost(), 6);
	}

	@Test
	void testGetOutline() {
		assertTrue(g1.getOutline().isEmpty());
	}

	@Test
	void testGetPlants() {
		assertEquals(0, g1.getPlants().size());
	}

	@Test
	void testGetSections() {
		assertEquals(g1.getSections().size(), 0);
	}

	@Test
	void testGetLeps() {
		assertEquals(0, g1.getLeps().size());
	}

	@Test
	void testGetCompostBin() {
		assertTrue(g1.getCompostBin().isEmpty());
		assertTrue(g2.getCompostBin().isEmpty());
	}
	@Test
	void testUpdateOutline() {
		g1.updateOutline(0, 0);
		assertEquals(new Vector2(0,0), g1.outline.get(0));
		g2.updateOutline(5, 5);
		g2.updateOutline(5, 10);
		assertEquals(new Vector2(5, 10), g2.outline.get(1));
		
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
		g1.updateOutline(-1, -1);
		g1.setPolygonCorners(1, 0);
		g1.setPolygonCorners(4, 1);
		g1.setPolygonCorners(0, 4);
		g1.updateOutline(-1, -1);
		g1.setPolygonCorners(4, 4);
		g1.updateOutline(-1, -1);
		ArrayList<Vector2> extrema = g1.getExtremes();
		assertEquals(new Vector2(1,0), extrema.get(0));
		assertEquals(new Vector2(5,2), extrema.get(1));
		assertEquals(new Vector2(1,9), extrema.get(2));
		assertEquals(new Vector2(0,4), extrema.get(3));
	}
	
	@Test
	void testAddSection() {
		g1.addSection(c);
		ArrayList<Conditions> temp = new ArrayList<>();
		temp.add(c);
		assertEquals(g1.getSections(), temp);
	}
	
	@Test
	void testAddToGarden() {
		g1.addToGarden(testPlant);
		assertEquals(g1.getPlants().get(0), testPlant);
	}
	
	@Test
	void testBudget() {
		g1.setBudget(300.32);
		assertEquals(300.32, g1.getBudget(), 1e-8);
	}
	
	@Test
	void testTitle() {
		String name = "test garden";
		g1.setTitle(name);
		assertEquals(name, g1.getTitle());
	}
}
