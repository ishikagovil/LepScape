import java.util.*;

public class Model {
	public int budget = 100;
	public Garden gardenMap;
	public Map<String, PlantSpecies> plantDirectory;
	public Map<String, Lep> lepDirectory;

	public double lengthPerPixel;
	public double scale;
	public double[] translate;
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
		this.lengthPerPixel = -1;
	}

	
	public Garden getGarden() {
		return this.gardenMap;
	}
	public void setGarden(Garden garden) {
		this.gardenMap = garden;
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
	 * Sets the scale of boundary
	 * @param double representing the calculated scale of plot
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
	/**
	 * Sets the translate of boundary
	 * @param double array representing the x and y direction translations
	 */
	public void setTranslate(double[] translate) {
		this.translate = translate;
	}
	/**
	 * Returns the scale of boundary
	 * @return double representing the calculated scale of plot
	 */
	public double getScale() {
		return this.scale;
	}
	/**
	 * Sets the translate of boundary
	 * @return double array representing the x and y direction translations
	 */
	public double[] getTranslate() {
		return this.translate;
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
	
	/**
	 * Calculates the distance of a line between the two provided points
	 * @param double x1 representing x coordinate of first point
	 * @param double y1 representing y coordinate of first point
	 * @param double x2 representing x coordinate of second point
	 * @param double y2 representing y coordinate of second point
	 * @return double representing distance of the line
	 */
	public double calculateLineDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x1 -  x2),2) + Math.pow(( y1 - y2 ),2) );
	}
	
	/**
	 * 
	 * @param double[] topLeft representing the topLeft coordinate to be scaled
	 * @return double[] representing translate in the x direction and translate in the y direction
	 */
	public double[] translateScaledPlot(double[] topLeft) {
		double translateX = topLeft[0] - this.getGarden().getExtremes().get(3)[0] * scale;
		double translateY = topLeft[1] - this.getGarden().getExtremes().get(0)[1] * scale;
		double[] translate = new double[]{translateX, translateY};
		this.setTranslate(translate);
		return translate;
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
