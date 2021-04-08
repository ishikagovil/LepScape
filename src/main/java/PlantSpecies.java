
public class PlantSpecies implements Comparable<PlantSpecies> {
	private String speciesName;
	private String genusName;
	private String commonName;
	private String description;
	private float spreadRadius;
	private int lepsSupported;
	private int cost;
	private int bloomTime;
	private boolean isWoody;
	
	public PlantSpecies() {
		this.speciesName = "";
		this.genusName = "";
		this.commonName = "";
		this.description = "";
	}
	
	public PlantSpecies(String speciesName, String genusName, String commonName, String description, float spreadRadius, int lepsSupported, int cost, int bloomTime, boolean isWoody) {
		this.speciesName = speciesName;
		this.genusName = genusName;
		this.commonName = commonName;
		this.description = description;
		this.spreadRadius = spreadRadius;
		this.lepsSupported = lepsSupported;
		this.cost = cost;
		this.bloomTime = bloomTime;
		this.isWoody = isWoody;
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
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getSpreadRadius() {
		return spreadRadius;
	}

	public void setSpreadRadius(float spreadRadius) {
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

	public int getBloomTime() {
		return bloomTime;
	}

	public void setBloomTime(int bloomTime) {
		this.bloomTime = bloomTime;
	}

	public boolean isWoody() {
		return isWoody;
	}

	public void setWoody(boolean isWoody) {
		this.isWoody = isWoody;
	}

}
