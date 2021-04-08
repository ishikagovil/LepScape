import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantSpeciesTest {
	
	private PlantSpecies ps;
	
	@BeforeEach
	void init() {
		ps = new PlantSpecies();
	}
	

	@Test
	void testPlantSpeciesDefaultConstructor() {
		PlantSpecies ps = new PlantSpecies();
		fail("Not yet implemented");
	}

	@Test
	void testPlantSpeciesParameterConstructor() {
		PlantSpecies ps = new PlantSpecies("Pinaceae","Pinus","Pine","A simple pine.", 23, 5, 20, 3, true);
		fail("Not yet implemented");
	}

	@Test
	void testCompareTo() {
		assertEquals(ps.compareTo(null), 20);
		fail("Not yet implemented");
	}

	@Test
	void testGetSpeciesName() {
		assertEquals(ps.getSpeciesName(), "fail");
	}

	@Test
	void testSetSpeciesName() {
		ps.setSpeciesName(null);
		fail("Not yet implemented");
	}

	@Test
	void testGetGenusName() {
		assertEquals(ps.getGenusName(), "fail");
	}

	@Test
	void testSetGenusName() {
		ps.setGenusName(null);
		fail("Not yet implemented");
	}

	@Test
	void testGetCommonName() {
		assertEquals(ps.getCommonName(), "fail");
	}

	@Test
	void testSetCommonName() {
		ps.setCommonName(null);
		fail("Not yet implemented");
	}

	@Test
	void testGetDescription() {
		assertEquals(ps.getDescription(), "fail");
	}

	@Test
	void testSetDescription() {
		ps.setDescription(null);
		fail("Not yet implemented");
	}

	@Test
	void testGetSpreadRadius() {
		assertEquals(ps.getSpreadRadius(), -1);
	}

	@Test
	void testSetSpreadRadius() {
		ps.setSpreadRadius(0);
		fail("Not yet implemented");
	}

	@Test
	void testGetLepsSupported() {
		assertEquals(ps.getLepsSupported(), -1);
	}

	@Test
	void testSetLepsSupported() {
		ps.setLepsSupported(0);
		fail("Not yet implemented");
	}

	@Test
	void testGetCost() {
		assertEquals(ps.getCost(), -1);
	}

	@Test
	void testSetCost() {
		ps.setCost(0);
		fail("Not yet implemented");
	}

	@Test
	void testGetBloomTime() {
		assertEquals(ps.getBloomTime(), -1);
	}

	@Test
	void testSetBloomTime() {
		ps.setBloomTime(0);
		fail("Not yet implemented");
	}

	@Test
	void testIsWoody() {
		assertEquals(ps.isWoody(), false);
	}

	@Test
	void testSetWoody() {
		ps.setWoody(true);
		fail("Not yet implemented");
	}

}
