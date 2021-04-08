import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class LepTest {
	
	Lep butterfly = new Lep("Danaus plexippus", "Danaus Kluk", "Butterfly");

	@Test
	void testGetSpecie() {
		assertEquals("Danaus plexippus", butterfly.getSpeciesName());
//		fail("Not yet implemented");
	}
	
	@Test
	void testGetGenus() {
		assertEquals("Danaus Kluk", butterfly.getGenusName());
	}
	
	@Test
	void testGetCommon() {
		assertEquals("Butterfly",butterfly.getCommonName());
	}
	
	@Test
	void testSetDescription() {
		String description ="Insects with 6 jointed legs, a pair of antennae, compound eyes, and an exoskeleton. Butterfly's body is covered by tiny sensory hairs. The 4 wings and 6 legs are attached to their thorax";
		butterfly.setDescription(description);
		assertEquals(description, butterfly.getDescription());
	}
	
	@Test
	void testSetThrives() {
		PlantSpecies milkweed = new PlantSpecies();
		PlantSpecies joePye = new PlantSpecies();
		ArrayList<PlantSpecies> x = new ArrayList<>();
		x.add(milkweed);
		x.add(joePye);
		butterfly.setThrivesIn(x);
		assertEquals(x,butterfly.getThrivesIn());
	}

}
