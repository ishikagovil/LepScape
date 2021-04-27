public enum MoistureType {
	ANY("Any", 0),
	DRY("Dry", 1),
	MOIST("Moist", 2),
	WET("Wet", 3);
	
	private final String name;
	private final int value;
	
	private MoistureType(String name, int value) {
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