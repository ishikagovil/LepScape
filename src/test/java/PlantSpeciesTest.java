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
		fail("Not yet implemented");
	}

	@Test
	void testGetCommonName() {
		assertEquals(ps.getCommonName(), "fail");
	}

	@Test
	void testSetCommonName() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDescription() {
		fail("Not yet implemented");
	}

	@Test
	void testSetDescription() {
		fail("Not yet implemented");
	}

	@Test
	void testGetSpreadRadius() {
		fail("Not yet implemented");
	}

	@Test
	void testSetSpreadRadius() {
		fail("Not yet implemented");
	}

	@Test
	void testGetLepsSupported() {
		fail("Not yet implemented");
	}

	@Test
	void testSetLepsSupported() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCost() {
		fail("Not yet implemented");
	}

	@Test
	void testSetCost() {
		fail("Not yet implemented");
	}

	@Test
	void testGetBloomTime() {
		fail("Not yet implemented");
	}

	@Test
	void testSetBloomTime() {
		fail("Not yet implemented");
	}

	@Test
	void testIsWoody() {
		fail("Not yet implemented");
	}

	@Test
	void testSetWoody() {
		fail("Not yet implemented");
	}

}
