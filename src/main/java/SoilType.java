
public enum SoilType {
	CLAY("Clay", 1),
	DIRT("Dirt", 2),
	ROCK("Rock", 3);
	
	private final String name;
	private final int value;
	
	private SoilType(String name, int value) {
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
