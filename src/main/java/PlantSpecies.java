
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
		return null;
	}

	public void setSpeciesName(String speciesName) { }

	public String getGenusName() {
		return null;
	}

	public void setGenusName(String genusName) { }

	public String getCommonName() {
		return null;
	}

	public void setCommonName(String commonName) { }

	public String getDescription() {
		return null;
	}

	public void setDescription(String description) { }

	public float getSpreadRadius() {
		return 0.0f;
	}

	public void setSpreadRadius(float spreadRadius) { }

	public int getLepsSupported() {
		return 0;
	}

	public void setLepsSupported(int lepsSupported) { }

	public int getCost() {
		return 0;
	}

	public void setCost(int cost) { }

	public int getBloomTime() {
		return 0;
	}

	public void setBloomTime(int bloomTime) { }

	public boolean isWoody() {
		return false;
	}

	public void setWoody(boolean isWoody) { }
}
