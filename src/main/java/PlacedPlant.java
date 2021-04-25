
public class PlacedPlant implements java.io.Serializable{

	private double x;
	private double y;
	private transient PlantSpecies species;
	private String name;
	
	public PlacedPlant() {
		this.species = null;
	}
	
	public PlacedPlant(double x, double y, PlantSpecies species) {
		this.x = x;
		this.y = y;
		this.species = species;
		this.name = species.getGenusName() + " " + species.getSpeciesName();
	}
	
	public double getX() {
		return this.x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public PlantSpecies getSpecies() {
		return this.species;
	}
	
	public void setSpecies(PlantSpecies species) {
		this.species = species;
	}
	
	public String getName() {
		return this.name;
	}
}
