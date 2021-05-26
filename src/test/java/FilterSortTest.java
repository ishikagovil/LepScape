import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterSortTest {
	PlantFilter filter;
	Comparator<PlantSpecies> comp;
	PlantSpecies ps = new PlantSpecies("Species", "Genus", "Common", 0, 0, 0, false, 3, 7, 9);
	
	@Test
	void testSearchFilter() {
		filter = new SearchFilter("spe");
		assertTrue(filter.include(ps));
	}
	
	@Test
	void testConditionFilter() {
		Conditions c = new Conditions(SoilType.CLAY, MoistureType.DRY, LightType.DARK);
		filter = new ConditionFilter(c);
		assertFalse(filter.include(ps));

		c = new Conditions(SoilType.ANY, MoistureType.ANY, LightType.ANY);
		filter = new ConditionFilter(c);
		assertTrue(filter.include(ps));
		
		PlantSpecies psAny = new PlantSpecies("", "", "", 0, 0, 0, false, 0, 0, 0);
		c = new Conditions(SoilType.CLAY, MoistureType.DRY, LightType.DARK);
		filter = new ConditionFilter(c);
		assertTrue(filter.include(psAny));
		
	}
	
	@Test
	void testComparators() {
		PlantSpecies ps1 = new PlantSpecies("A", "A", "A", 0, 1, 2, false, 3, 7, 9);
		PlantSpecies ps2 = new PlantSpecies("B", "B", "B", 0, 2, 4, false, 3, 7, 9);
		
		comp = new SortByLeps();
		assertTrue(comp.compare(ps1, ps2) > 0);
		assertEquals("Sort by insects supported", comp.toString());
		comp = new SortByCost();
		assertTrue(comp.compare(ps1, ps2) < 0);
		assertEquals("Sort by cost", comp.toString());
		comp = new SortByName();
		assertEquals("Sort by common name", comp.toString());
		assertTrue(comp.compare(ps1, ps2) < 0);
		
	}
}
