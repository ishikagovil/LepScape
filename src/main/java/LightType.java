
public enum LightType {
	ANY("Any", 0),
	DARK("Dark", 1),
	BRIGHT("Bright", 2),
	INTENSE("Intense", 3);
	
	private final String name;
	private final int value;
	
	private LightType(String name, int value) {
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
