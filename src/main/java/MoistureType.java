
/**
 * Representative of the different moisture levels of the garden.
 * @author Dea Harjianto
 *
 */
public enum MoistureType {
	ANY("Any", 0),
	DRY("Dry", 0.3),
	MOIST("Damp", 0.6),
	WET("Wet", 0.9);

	private static final double epsilon = 0.07;
	
	private final String name;
	private final double value;
	
	/**
	 * A new instance of MoistureType
	 * @param name
	 * @param value
	 */
	private MoistureType(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	/**
	 *  A toString method to return the name of the MoistureType.
	 *  @return String
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Gets the max value associated with the MoistureType and returns it.
	 * @return double
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Depending on what value we are given, it creates the MoistureType associated with it.
	 * @param value
	 * @return MoistureType
	 */
	public static MoistureType fromValue(double value) {
		for(MoistureType type : MoistureType.values()) {
			if(Math.abs(value - type.getValue()) <= epsilon) {
				return type;
			}
		}
		
		return MoistureType.ANY;
	}
	
}