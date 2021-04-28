import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

class ConditionsTest {
	Conditions c;
	
	@BeforeEach
	void createCondition() {
		c = new Conditions(SoilType.CLAY, MoistureType.MOIST, LightType.INTENSE);
	}
	
	@Test
	void testGetSoilType() {
		assertEquals(c.getSoilType(), SoilType.CLAY);
	}
	@Test
	void testSetSoilType() {
		c.setSoilType(SoilType.DIRT);
		assertEquals(c.getSoilType(), SoilType.DIRT);
	}
	@Test
	void testGetMoistureType() {
		assertEquals(c.getMoistureType(), MoistureType.MOIST);
	}
	@Test
	void testSetMoistureType() {
		c.setMoistureType(MoistureType.ANY);
		assertEquals(c.getMoistureType(), MoistureType.ANY);
	}
	@Test
	void testGetSunlightType() {
		assertEquals(c.getSunlightType(), LightType.INTENSE);
	}
	@Test
	void testSetSunlightType() {
		c.setSunlightType(LightType.ANY);
		assertEquals(c.getSunlightType(), LightType.ANY);
	}
	
	@Test
	void testGetAndSetX() {
		c.setX(5.0);
		assertEquals(c.getX(), 5.0);
	}
	@Test
	void testGetAndSetY() {
		c.setY(12.0);
		assertEquals(c.getY(), 12.0);
	}
	@Test
	void testToColor() {
		Color col = c.toColor();
		assertEquals(col.getRed(), 0.3, 1e-4);
		assertEquals(col.getGreen(), 0.6, 1e-4);
		assertEquals(col.getBlue(), 0.9, 1e-4);
	}
	
	@Test
	void testFromColor() {
		Color col = new Color(0.0, 0.0, 0.9, 1.0);
		Conditions newCond = Conditions.fromColor(col);
		assertEquals(newCond.getSoilType(), SoilType.ANY);
		assertEquals(newCond.getSunlightType(), LightType.INTENSE);
		assertEquals(newCond.getMoistureType(), MoistureType.ANY);
	}

}
