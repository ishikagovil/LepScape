
/**
 * Representative of the different light levels of the garden.
 * @author Dea Harjianto
 *
 */
public enum LightType {
	ANY("Any", 0),
	DARK("Full Shade", 0.3),
	BRIGHT("Partial Shade", 0.6),
	INTENSE("Full Sun", 0.9);
	
	private static final double epsilon = 0.07;
	
	private final String name;
	private final double value;
	
	/**
	 * A new instance of a LightType.
	 * @param name
	 * @param value
	 */
	private LightType(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 * A toString method to return the name of the LightType.
	 * @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Gets the max value associated with the LightType and returns it.
	 * @return
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Depending on what value we are given, it creates the LightType associated with it.
	 * @param value
	 * @return LightType
	 */
	public static LightType fromValue(double value) {
		for(LightType type : LightType.values()) {
			if(Math.abs(value - type.getValue()) <= epsilon) {
				return type;
			}
		}
		
		return LightType.ANY;
	}
	
	
}
