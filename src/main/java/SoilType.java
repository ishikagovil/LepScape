
public enum SoilType {
	HUMUS("Humus", 0.3),
	SAND("Sand", 0.6),
	CLAY("Clay", 0.9);
	
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
