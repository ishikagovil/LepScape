public class PlantSpecies implements Comparable<PlantSpecies> {
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
	
	public PlantSpecies() {
		this.speciesName = "";
		this.genusName = "";
		this.commonName = "";
	}
	
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
	
	public SoilType getSoilType() {
		return this.soil;
	}
	
	public MoistureType getMoistureType() {
		return this.moisture;
	}
	
	public LightType getLightType() {
		return this.light;
	}

	public void setDescription() {
		String plantType = "";
		if (isWoody) {
			plantType = "woody";
		} else {
			plantType = "herbaceous";
		}
		this.description = "Also known as " + this.commonName +  "\nThis " +  plantType + " plant attracts a total of " + this.lepsSupported + " leps.";
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}
	
	public int compareTo(PlantSpecies other) {
		return 0;
	}
	
	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getGenusName() {
		return genusName;
	}

	public void setGenusName(String genusName) {
		this.genusName = genusName;
	}

	public String getCommonName() {
		return this.commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public double getSpreadRadius() {
		return spreadRadius;
	}

	public void setSpreadRadius(int spreadRadius) {
		this.spreadRadius = spreadRadius;
	}

	public int getLepsSupported() {
		return lepsSupported;
	}

	public void setLepsSupported(int lepsSupported) {
		this.lepsSupported = lepsSupported;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public boolean isWoody() {
		return isWoody;
	}

	public void setWoody(boolean isWoody) {
		this.isWoody = isWoody;
	}

	public void setLightReq(int lightReq) {
		this.lightReq = lightReq;
	}
	
	public int getLightReq() {
		return this.lightReq;
	}

	public void setSoilReq(int soilReq) {
		this.soilReq = soilReq;
	}

	public int getSoilReq() {
		return this.soilReq;
	}

	public void setMoistReq(int moistReq) {
		this.moistReq = moistReq;
	}

	public int getMoistReq() {
		return this.moistReq;
	}

	public String toString() {
		return this.commonName;
	}
}
