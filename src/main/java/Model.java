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
	private Conditions currConditions = new Conditions(SoilType.CLAY, 0, 0);
	
	private UserMode mode;
	
	/**
	 * @author Ishika Govil, Kimmy Huynh
	 */
	public Model() {
		this.gardenMap = new Garden();
		this.plantDirectory = new HashMap<>();
		this.lepDirectory = new HashMap<>();
		//initializePlantDirectory();
		this.budget = 0;
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
	
	public void setLepDirectory(Map<String, Lep> lepdir) {
		this.lepDirectory = lepdir;
		Iterator lepIt = lepdir.entrySet().iterator();
		Iterator plantIt = this.plantDirectory.entrySet().iterator();
		System.out.println("created iterators for plant + lep");
		
		while(lepIt.hasNext()) {
			Map.Entry lepEntry = (Map.Entry)lepIt.next();
			Lep lepObj = (Lep)lepEntry.getValue();
			ArrayList<String> genusOfPlants = lepObj.getThrivesInGenus();
			
			Iterator genusPlantsIt = genusOfPlants.iterator();
			while(genusPlantsIt.hasNext()) {
				String genusNameForPlant = (String)genusPlantsIt.next();
				
				while (plantIt.hasNext()) {
					Map.Entry plantEntry = (Map.Entry)plantIt.next();
					PlantSpecies plantObj = (PlantSpecies)plantEntry.getValue();
					String genusOfPlant = plantObj.getGenusName();
					
					if (genusOfPlant.equals(genusNameForPlant)) {
						lepObj.getThrivesIn().add(plantObj);
					}
				}
				
			}
			
			System.out.println(lepObj.getThrivesIn());
			
		}
		
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
	public Map<String, PlantSpecies> getPlantInfo() {
		return this.plantDirectory;
	}
	// check if plant is okay to be placed
	public void validatePlacement() {}
	// place down plants and updates budget and lep count
	public void placePlant(double x, double y, String key) {
		PlantSpecies specie = plantDirectory.get(key);
		gardenMap.addToGarden(new PlacedPlant(x,y,specie));
		this.budget = budget - specie.getCost();
		this.lepCount = lepCount + specie.getLepsSupported();
	}
	
	public void removePlant(double x, double y, String key) {
		PlantSpecies specie = plantDirectory.get(key);
		this.budget = budget + specie.getCost();
		this.lepCount = lepCount - specie.getLepsSupported();
	}
	
	// update the cost every time a plant is placed
	public void costUpdate() {}

	public Map<String, Lep> getLepDirectory() {
		return this.lepDirectory;
	}
	
	public void initializePlantDirectory() {

		//plantDirectory.put("commonMilkweed", new PlantSpecies("Asclepias syriaca","Milkweed","Common Milkweed","Milkweed produces purple or pink flowers\narranged in drooping clusters.",5,7,13,40, false));

		//plantDirectory.put("pine", new PlantSpecies("Pinaceae","Pinus","Pine","A simple pine.", 23, 5, 20, 3, true));
	}
	
	public int getBudget() {
		return this.budget;
	}
	public void setBudget(int budget) {
		this.budget = budget;
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
	public UserMode getMode() {
		return this.mode;
	}
	public void setMode(UserMode mode) {
		this.mode = mode;
	}
	public Conditions getCurrentConditions() {
		return this.currConditions;
	}
}
