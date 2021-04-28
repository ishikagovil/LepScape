
public enum LightType {
	ANY("Any", 0),
	DARK("Dark", 0.3),
	BRIGHT("Bright", 0.6),
	INTENSE("Intense", 0.9);
	
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
	
	
}
