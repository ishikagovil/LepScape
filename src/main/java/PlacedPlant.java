
public class PlacedPlant {

	private float x;
	private float y;
	private PlantSpecies species;
	
	public PlacedPlant() {
		this.species = null;
	}
	
	public PlacedPlant(int x, int y, PlantSpecies species) {
		this.x = x;
		this.y = y;
		this.species = species;
	}
	
	public float getX() {
		return this.x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public PlantSpecies getSpecies() {
		return this.species;
	}
	
	public void setSpecies(PlantSpecies species) {
		this.species = species;
	}
}
