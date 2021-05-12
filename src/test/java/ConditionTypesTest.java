import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ConditionTypesTest {

	SoilType st = SoilType.ROCK;
	MoistureType mt = MoistureType.MOIST;
	LightType lt = LightType.INTENSE;
	
	@Test
	void testFromValue() {
		for(MoistureType t : MoistureType.values()) {
			assertEquals(MoistureType.fromValue(t.getValue()), t);
		}
		for(SoilType t : SoilType.values()) {
			assertEquals(SoilType.fromValue(t.getValue()), t);
		}
		for(LightType t : LightType.values()) {
			assertEquals(LightType.fromValue(t.getValue()), t);
		}
		
		assertEquals(MoistureType.fromValue(20), MoistureType.ANY);
		assertEquals(SoilType.fromValue(20), SoilType.ANY);
		assertEquals(LightType.fromValue(20), LightType.ANY);
	}
	
	@Test 
	void testToString() {
		assertEquals("Clay", st.toString());
		assertEquals("Damp", mt.toString());
		assertEquals("Full Sun", lt.toString());
	}
}
