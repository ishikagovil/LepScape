
public enum LightType {
	ANY("Any", 0),
	DARK("Shade", 0.3),
	BRIGHT("Partial Shade", 0.6),
	INTENSE("No Shade", 0.9);
	
	private static final double epsilon = 0.07;
	
	private final String name;
	private final double value;
	
	private LightType(String name, double value) {
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
	
	public static LightType fromValue(double value) {
		for(LightType type : LightType.values()) {
			if(Math.abs(value - type.getValue()) <= epsilon) {
				return type;
			}
		}
		
		return LightType.ANY;
	}
	
	
}
