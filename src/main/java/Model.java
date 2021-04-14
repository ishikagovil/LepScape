import java.util.*;

public class Model {
	public int budget = 100;
	public Garden gardenMap;
	public Map<String, PlantSpecies> plantDirectory;
	public Map<String, Lep> lepDirectory;

	public double lengthPerPixel;

	public double x;
	public double y;
	public int lepCount;
	
	/**
	 * @author Ishika Govil, Kimmy Huynh
	 */
	public Model() {
		this.gardenMap = new Garden();
		this.plantDirectory = new HashMap<>();
		this.lepDirectory = new HashMap<>();
		initializePlantDirectory();
	}
	// Methods for the user to draw the garden and put in desired conditions
	// create the optimal garden based on leps and conditions provided
	public void createDefault() {}
	// get the boundaries of the garden
	public ArrayList<double[]> getBoundaries() {
		return this.gardenMap.getOutline();
	}
	public Garden getGarden() {
		return this.gardenMap;
	}
	
	public void setPlantDirectory(Map<String, PlantSpecies> plantdir) {
		this.plantDirectory = plantdir;
	}
	// create a new condition for the garden
	public void createNewConditions() {}
	
	/**
	 * Update the outline while user is drawing their plot
	 * @param double x coordinate
	 * @param double y coordinate
	 */
	public void updateOutlineSection(double x, double y) {
		this.gardenMap.updateOutline(x, y);
	}
	// update the outline to the condition that user wants
	public void updateConditions() {}
	// find dimension of the garden
	public void findDimensions() {}
	
	// Methods to use in order to place down plants
	//choose 2 plants to compare the number of leps they support
	public void comparePlant() {}
	// get plant's sphere
	public void getPlantInfo() {}
	// check if plant is okay to be placed
	public void validatePlacement() {}
	// place down plants and updates budget and lep count
	public void placePlant(double x, double y, String key) {
		PlantSpecies specie = plantDirectory.get(key);
		gardenMap.addToGarden(new PlacedPlant(x,y,specie));
		this.budget = budget - specie.getCost();
		this.lepCount = lepCount + specie.getLepsSupported();
	}
	// update the cost every time a plant is placed
	public void costUpdate() {}
	
	public void initializePlantDirectory() {

		plantDirectory.put("commonMilkweed", new PlantSpecies("Asclepias syriaca","Milkweed","Common Milkweed","Milkweed produces purple or pink flowers\narranged in drooping clusters.",5,7,13,40, false));

		plantDirectory.put("pine", new PlantSpecies("Pinaceae","Pinus","Pine","A simple pine.", 23, 5, 20, 3, true));
	}
	
	public int getBudget() {
		return this.budget;
	}
	
	public int getLepCount() {
		return this.lepCount;
	}
	
	/*@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}*/
	
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	
	/**
	 * Returns the length per pixel
	 * @return double representing length (ft) per pixel
	 */
	public double getLengthPerPixel() {
		return this.lengthPerPixel;
	}
	
	/**
	 * Sets the length per pixel from Controller
	 * @param double representing length (ft) per pixel
	 */
	public void setLengthPerPixel(double pix) {
		this.lengthPerPixel = pix;
	}


}
