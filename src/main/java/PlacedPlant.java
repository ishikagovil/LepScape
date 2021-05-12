
/**
 * Representative of a plant that was placed within the garden.
 * @author Dea Harjianto
 *
 */
public class PlacedPlant implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private double x;
	private double y;
	private transient PlantSpecies species;
	private String name;
	
	/**
	 * Creates a blank instance of PlacedPlant (for testing purposes).
	 */
	public PlacedPlant() {
		this.species = null;
	}
	
	/**
	 * Creates an instance of PlacedPlant.
	 * @param x
	 * @param y
	 * @param species
	 */
	public PlacedPlant(double x, double y, PlantSpecies species) {
		this.x = x;
		this.y = y;
		this.species = species;
		this.name = species.getGenusName() + "-" + species.getSpeciesName();
	}
	
	/**
	 * Getter method to return the x-coord of the PlacedPlant.
	 * @return double
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * Setter method to set the x-coord of the PlacedPlant.
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Getter method to get the y-coord of the PlacedPlant.
	 * @return double
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Setter method to set the y-cord of the PlacedPlant.
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Getter method to get the species of the PlacedPlant.
	 * @return PlantSpecies
	 */
	public PlantSpecies getSpecies() {
		return this.species;
	}
	
	/**
	 * Setter method to set the species of the PlacedPlant.
	 * @param species
	 */
	public void setSpecies(PlantSpecies species) {
		this.species = species;
	}
	
	/**
	 * Getter method to get the name of the PlacedPlant.
	 * @return String
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * toString method to return the name.
	 * @return String
	 */
	public String toString() {
 		return this.name;
 	}
	public String pdfDescription() {
		return "Species name: " + species.getSpeciesName() + "\n" + "common name: " + species.getCommonName() + "\n" + "genus name: " + species.getGenusName();
	}
}
