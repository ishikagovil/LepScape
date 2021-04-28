import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantSpeciesTest {
	PlantSpecies ps, ps2, ps3;

	
	@BeforeEach
	void beforeEach() {
		ps = new PlantSpecies("a", "a", "c", 1, 2, 3, false, -1, -1, -1);
		ps2 = new PlantSpecies("a", "a", "c", 1, 2, 3, true, -1, -1, -1);
	}
	
	@Test
	void testConstructors() {
		ps3 = new PlantSpecies();
		
		for(int i = 0; i < 10; i++) { 
			for(int j = 0; j < 10; j++) { 
				for(int k = 0; k < 10; k++) { 
					ps3 = new PlantSpecies("a", "b", "c", 1, 2, 3, true, i, j, k);
				}
			}
		}
	}
	
	@Test
	void testSoilType() {
		assertEquals(ps.getSoilType(), SoilType.ANY);
	}

	@Test
	void testMoistureType() {
		assertEquals(ps.getMoistureType(), MoistureType.ANY);
	}

	@Test
	void testLightType() {
		assertEquals(ps.getLightType(), LightType.ANY);
	}
	
	@Test
	void testDescription() {
		ps.setDescription();
		assertEquals(ps.getDescription(), "Also known as c\nThis herbaceous plant attracts a total of 2 leps.");
		ps.setDescription("a");
		assertEquals(ps.getDescription(), "a");
	}

	@Test
	void testCommonName() {
		ps.setCommonName("a");
		assertEquals(ps.getCommonName(), "a");
	}

	@Test
	void testSpeciesName() {
		ps.setSpeciesName("a");
		assertEquals(ps.getSpeciesName(), "a");
	}

	@Test
	void testGenusName() {
		ps.setGenusName("a");
		assertEquals(ps.getGenusName(), "a");
	}

	@Test
	void testSpreadRadius() {
		ps.setSpreadRadius(5);
		assertEquals(ps.getSpreadRadius(), 5);
	}
	
	
	@Test 
	void testLeps() {
		ps.setLepsSupported(5);
		assertEquals(ps.getLepsSupported(), 5);
	}
	
	@Test
	void testCost() {
		ps.setCost(30);
		assertEquals(ps.getCost(), 30);
	}
	
	@Test
	void testWoody() {
		ps.setWoody(false);
		assertEquals(ps.isWoody(), false);
	}
	
	@Test
	void testToString() {
		assertEquals(ps.toString(), ps.getCommonName());
	}
}
