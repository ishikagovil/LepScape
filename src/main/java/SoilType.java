
public enum SoilType {
	ANY("Any", 0),
	CLAY("Clay", 0.3),
	DIRT("Dirt", 0.6),
	ROCK("Rock", 0.9);
	
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
	
	
}
