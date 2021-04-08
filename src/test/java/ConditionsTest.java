import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.*;

class ConditionsTest {
	Conditions n = new Conditions(5,10,2);
	
	@Test
	void testGetSoil() {
//		fail("Not yet implemented");
		assertEquals(n.getSoilType(),5);
	}
	
	@Test
	void testGetMoisture(){
		assertEquals(10,n.getMoistureLevel());
	}
	
	@Test
	void testGetSun() {
		assertEquals(2,n.getSunlight());
	}
	
	@Test
	void test4() {
		ArrayList<float[]> x = new ArrayList<>();
		float[] n1 = {1,2};
		float[] n2 = {0,1};
		float[] n3 = {7,5};
		x.add(n1);
		x.add(n2);
		x.add(n3);
		n.setSectionsOutlines(x);
		assertEquals(x,n.getSections());
	}

}
