
/**
 * Representative of the different soil types of the garden.
 * @author dharjianto
 *
 */
public enum SoilType {
	ANY("Any", 0),
	CLAY("Sand", 0.3),
	DIRT("Dirt", 0.6),
	ROCK("Rock", 0.9);

	private static final double epsilon = 0.07;
	
	private final String name;
	private final double value;
	
	/**
	 * A new instance of SoilType.
	 * @param name
	 * @param value
	 */
	private SoilType(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 *  A toString method to return the name of the SoilType.
	 *  @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Gets the max value associated with the soilType and returns it.
	 * @return double
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Depending on what value we are given, it creates the SoilType associated with it.
	 * @param value
	 * @return SoilType
	 */
	public static SoilType fromValue(double value) {
		for(SoilType type : SoilType.values()) {
			if(Math.abs(value - type.getValue()) <= epsilon) {
				return type;
			}
		}
		
		return SoilType.ANY;
	}
	
}
