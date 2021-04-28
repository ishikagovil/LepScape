public enum MoistureType {
	ANY("Any", 0),
	DRY("Dry", 0.3),
	MOIST("Moist", 0.6),
	WET("Wet", 0.9);
	
	private final String name;
	private final double value;
	
	private MoistureType(String name, double value) {
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