
/**
 * Representative of a specific plant species.
 * @author dharjianto
 *
 */
public class PlantSpecies {
	private String speciesName;
	private String genusName;
	private String commonName;
	private String description;
	private double spreadRadius;
	// in inches
	private int lepsSupported;
	private int cost;
	private boolean isWoody;
	private int lightReq;
	// Required amount of light, on a scale from 0 (no light, <= 10 lux) to 10 (very intensive insolation, >= 100 000 lux)	
	private int soilReq;
	// Required texture of the soil, on a scale from 0 (clay) to 10 (rock)
	private int moistReq;
	// Required relative humidity in the air, on a scale from 0 (<=10%) to 10 (>= 90%)
	private SoilType soil;
	private LightType light;
	private MoistureType moisture;

	// for all "req" attributes, if value is -1, no specific requirements for plant
	
	/**
	 * Creates a blank instance of a PlantSpecies (for testing purposes).
	 */
	public PlantSpecies() {
		this.speciesName = "";
		this.genusName = "";
		this.commonName = "";
	}
	
	/**
	 * Creates an instance of a PlantSpecies.
	 * @param speciesName
	 * @param genusName
	 * @param commonName
	 * @param spreadRadius
	 * @param lepsSupported
	 * @param cost
	 * @param isWoody
	 * @param lightReq
	 * @param soilReq
	 * @param moistReq
	 */
	public PlantSpecies(String speciesName, String genusName, String commonName, double spreadRadius, int lepsSupported, int cost, boolean isWoody, int lightReq, int soilReq, int moistReq) {
		this.speciesName = speciesName;
		this.genusName = genusName;
		this.commonName = commonName;
		this.spreadRadius = spreadRadius;
		this.lepsSupported = lepsSupported;
		this.cost = cost;
		this.isWoody = isWoody;
		this.lightReq = lightReq;
		this.soilReq = soilReq;
		this.moistReq = moistReq;
		this.setDescription();				// setting generic description to display
		this.setRequirements();				// setting basic enum requirements for PlantSpecies
	}
	
	/**
	 * Sets the requirements for the PlantSpecies depending on the enum.
	 */
	public void setRequirements() {
		// setting soil type
		if (this.soilReq >= 0) {
			if (this.soilReq < 4) {
				this.soil = SoilType.CLAY;
			} else if (this.soilReq < 8) {
				this.soil = SoilType.DIRT;
			} else {
				this.soil = SoilType.ROCK;
			}
		} else {
			this.soil = SoilType.ANY;
		}
		
		// setting light type
		if (this.lightReq >= 0) {
			if (this.lightReq < 4) {
				this.light = LightType.DARK;
			} else if (this.soilReq < 8) {
				this.light = LightType.BRIGHT;
			} else {
				this.light = LightType.INTENSE;
			}
		} else {
			this.light = LightType.ANY;
		}
	
		
		// setting moisture type
		if (this.moistReq >= 0) { 
			if (this.moistReq < 4) {
				this.moisture = MoistureType.DRY;
			} else if (this.moistReq < 8) {
				this.moisture = MoistureType.MOIST;
			} else {
				this.moisture = MoistureType.WET;
			}
		} else {
			this.moisture = MoistureType.ANY;
		}
	}
	
	/**
	 * Getter method for the SoilType.
	 * @return SoilType
	 */
	public SoilType getSoilType() {
		return this.soil;
	}
	
	/**
	 * Getter method for the MoistureType
	 * @return MoistureType
	 */
	public MoistureType getMoistureType() {
		return this.moisture;
	}
	
	/**
	 * Getter method for the LightType
	 * @return LightType
	 */
	public LightType getLightType() {
		return this.light;
	}

	/**
	 * Setter method to create the plant description to display.
	 */
	public void setDescription() {
		String plantType = "";
		if (isWoody) {
			plantType = "woody";
		} else {
			plantType = "herbaceous";
		}
		this.description = "Also known as " + this.genusName + "-" + this.speciesName +  ".\n\nThis " +  plantType + " plant attracts a total of " + this.lepsSupported + " leps.\n\nThese cost " + this.cost + " US Dollars.";
	}

	/**
	 * Getter method for the description.
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Setter method to create a custom description for the plant.
	 * @param desc
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	/**
	 * Getter method for the species name.
	 * @return String
	 */
	public String getSpeciesName() {
		return speciesName;
	}

	/**
	 * Setter method for the species name.
	 * @param speciesName
	 */
	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	/**
	 * Getter method for the genus name.
	 * @return String
	 */
	public String getGenusName() {
		return genusName;
	}

	/**
	 * Setter method for the genus name.
	 * @param genusName
	 */
	public void setGenusName(String genusName) {
		this.genusName = genusName;
	}

	/**
	 * Getter method for the common name.
	 * @return String
	 */
	public String getCommonName() {
		return this.commonName;
	}

	/**
	 * Setter method for the common name.
	 * @param commonName
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	/**
	 * Getter method for the spread radius.
	 * @return double
	 */
	public double getSpreadRadius() {
		return spreadRadius;
	}

	/**
	 * Setter method for the spread radius.
	 * @param spreadRadius
	 */
	public void setSpreadRadius(int spreadRadius) {
		this.spreadRadius = spreadRadius;
	}

	/**
	 * Getter method for the number of leps supported.
	 * @return int
	 */
	public int getLepsSupported() {
		return lepsSupported;
	}

	/**
	 * Setter method for the number of leps supported.
	 * @param lepsSupported
	 */
	public void setLepsSupported(int lepsSupported) {
		this.lepsSupported = lepsSupported;
	}

	/**
	 * Getter method for the cost.
	 * @return int
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Setter method for the cost.
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * Getter method for whether the PlantSpecies is woody.
	 * @return boolean
	 */
	public boolean isWoody() {
		return isWoody;
	}

	/**
	 * Setter method for whether the PlantSpecies is woody.
	 * @param isWoody
	 */
	public void setWoody(boolean isWoody) {
		this.isWoody = isWoody;
	}

	/**
	 * toString method to return out the common name of the PlantSpecies.
	 * @return String
	 */
	public String toString() {
		return this.commonName;
	}
}
