import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Vector2Test {
	
	Vector2 vec = new Vector2(5, 3);

	@Test
	void testX() {
		vec.setX(3.32);
		assertEquals(vec.getX(), 3.32, 1e-6);
	}
	
	@Test
	void testY() {
		vec.setY(1.43);
		assertEquals(vec.getY(), 1.43, 1e-6);
	}
	
	@Test
	void testEquals() {
		Vector2 v1 = new Vector2(1.32, 4.2);
		Vector2 v2 = new Vector2(1.32, 4.2);
		Vector2 v3 = new Vector2(1.32, 4.3);
		
		assertEquals(v1, v2);
		assertEquals(v2, v1);
		assertNotEquals(v1, vec);
		assertNotEquals(v1, v3);
		assertFalse(v1.equals(null));
		assertFalse(v1.equals(new Object()));
	}

}
