
public enum SoilType {
	ANY("Any", 0),
	CLAY("Sand", 0.3),
	DIRT("Dirt", 0.6),
	ROCK("Rock", 0.9);

	private static final double epsilon = 0.07;
	
	private final String name;
	private final double value;
	
	private SoilType(String name, double value) {
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public double getValue() {
		return value;
	}
	
	public static SoilType fromValue(double value) {
		for(SoilType type : SoilType.values()) {
			if(Math.abs(value - type.getValue()) <= epsilon) {
				return type;
			}
		}
		
		return SoilType.ANY;
	}
	
}
