import java.util.*;

/**
 * Model part of the MVC design pattern
 *
 */
public class Model implements java.io.Serializable{
	
	public Garden gardenMap;
	public transient Map<String, PlantSpecies> plantDirectory;
	public transient Map<String, Lep> lepDirectory;
	public transient ArrayList<Garden> savedGardens;
	public double lengthPerPixel;
	public double scale;
	public Vector2 translate;
	public double x;
	public double y;
	public String movedPlant;
	public HashSet<String> deleted;
	public boolean editGarden;
	public int editGardenIndex;
	public transient Comparator<PlantSpecies> sort;
	public transient PlantFilter filter;
	public boolean gardenDesign;
	
	/**
	 * @author Ishika Govil, Kimmy Huynh, Arunima Dey
	 */
	public Model() {
		this.savedGardens = new ArrayList<>();
		this.gardenMap = new Garden();
		this.plantDirectory = new HashMap<>();
		this.lepDirectory = new HashMap<>();
		this.lengthPerPixel = -1;
		this.movedPlant = "";
		this.deleted = new HashSet<>();
		this.sort = new SortByLeps();
		this.filter = new SearchFilter("");
		editGarden = false;

	}
	
	/**
	 * Restartes all attributes of model except saved gardens when user starts a new garden
	 */
	public void restart() {
		this.gardenMap = new Garden();
		this.lengthPerPixel = -1;
		this.movedPlant = "";
		this.deleted = new HashSet<>();
		this.sort = new SortByLeps();
		this.filter = new SearchFilter("");
		editGarden = false;
		gardenDesign = false;

	}
	
	/**
	 * Sets editGarden based on if user is editing a saved garden or a new one
	 */
	public void setToEdit() {
		this.editGarden = true;
	}
	
	/**
	 * gets editGarden
	 * @return if user if editing
	 */
	public boolean editing() {
		return this.editGarden;
	}
	
	/**
	 * if garden is being edited sets index to the savedgarden
	 * @param index the index
	 */
	public void setEditGardenIndex(int index) {
		editGardenIndex = index;
	}
	
	/**
	 * gets the index for a garden that is being edited
	 * @return the index
	 */
	public int getEditGardenIndex() {
		return this.editGardenIndex;
	}
	
	/**
	 * gets the garden
	 * @return the garden
	 */
	public Garden getGarden() {
		return this.gardenMap;
	}
	/**
	 * sets the garden
	 * @param garden
	 */
	public void setGarden(Garden garden) {
		this.gardenMap = garden;
	}
	
	/**
	 * Setter method to set the plant directory.
	 * @param plantdir
	 */
	public void setPlantDirectory(Map<String, PlantSpecies> plantdir) {
		this.plantDirectory = plantdir;
	}
	
	/**
	 * Setter method to set the lep directory.
	 * @param lepdir
	 */
	public void setLepDirectory(Map<String, Lep> lepdir) {
		this.lepDirectory = lepdir;
		/*Iterator lepIt = lepdir.entrySet().iterator();
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
			
		}*/
		
	}

	/**
	 * get plant's information
	 * @return the information and the corresponding plantSpecie
	 */
	public Map<String, PlantSpecies> getPlantInfo() {
		return this.plantDirectory;
	}

	/**
	 * Place a plant in the garden. Adds it to placedPlants and the updated cost and leps supported.
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param key the name of plant
	 * @param nodeId the node of corresponding imageView
	 * @param initial false if this is a new plant to be added
	 */
	public void placePlant(double x, double y, String key, String nodeId,boolean initial) {
		System.out.println("adding to Garden");
		System.out.println("the key when adding to garden: "+key);
		PlantSpecies specie = plantDirectory.get(key);
		gardenMap.placedPlants.put(nodeId, new PlacedPlant(x,y,specie));
		if(!initial) {
			gardenMap.addToGarden(new PlacedPlant(x,y,specie));
		}
 		System.out.println("plants: "+gardenMap.placedPlants);
	}
	
	/**
	 * Gets the cost of plants for gallery's saved gardens when placed plants has not been initilized
	 * @param garden the garden for which we need cost
	 * @return the cost
	 */
	public double getCostforGallery(Garden garden) {
		Iterator<PlacedPlant> iter = garden.plants.iterator();
		double cost = 0;
		for(int i = 0; i<garden.plants.size();i++) {
			PlantSpecies plant = plantDirectory.get(garden.plants.get(i).getName());
			cost+=plant.getCost();
		}
//		while(iter.hasNext()) {
////			PlantSpecies plant = plantDirectory.get(iter.next().get);
////			plantDirectory.get(iter.next().getName());
////			cost+=plant.getCost();
//		}
		return cost;
	}
	
	/**
	 * Gets the lep count of plants for gallery's saved gardens when placed plants has not been initilized
	 * @param garden the garden for which we need lep count
	 * @return the lep count
	 */
	public int getLepsforGallery(Garden garden) {
		Iterator<PlacedPlant> iter = garden.plants.iterator();
		int lep = 0;
		for(int i = 0; i<garden.plants.size();i++) {
			PlantSpecies plant = plantDirectory.get(garden.plants.get(i).getName());
			lep+=plant.getLepsSupported();
		}
//		while(iter.hasNext()) {
//			System.out.println(iter.next());
//			PlantSpecies plant = plantDirectory.get(iter.next().getName());
//			lep+=plant.getLepsSupported();
//		}
		return lep;
	}
	
	/**
	 * Checks if a given no garden exists with same title before user can set it as the title of theeir garden
	 * @param newTitle the title
	 * @return the boolean to indicate whether title exits or not
	 */
	public boolean isTitleValid(String newTitle) {
		Iterator<Garden> iter = savedGardens.iterator();
		while(iter.hasNext()) {
			if(iter.next().getTitle().equals(newTitle)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * removes a plant from placedPlants and updates budgte and lep
	 * @param key the name of the plant
	 * @param nodeId the nodeId of corresponding imageView
	 */
	public void removePlant(String key, String nodeId) {
		PlantSpecies specie = plantDirectory.get(key);
		System.out.println("removing: "+key);
		deleted.add(key);
		System.out.println(deleted);
		gardenMap.placedPlants.remove(nodeId);
 		System.out.println("plants: "+gardenMap.placedPlants);
	}
	
	/**
	 * Updated the x and y for placedplants when the a plants is moved in garden
	 * @param nodeId the id for for the imageview that moved
	 */
	public void updateXY(String nodeId) {
 		PlacedPlant plant = gardenMap.placedPlants.get(nodeId);
 		plant.setX(x);
 		plant.setY(y);
 		gardenMap.placedPlants.put(nodeId, plant);
 		System.out.println("plants: "+gardenMap.placedPlants);
 	}
	
	/**
	 * Gets the lep direectory
	 * @return the hashmap of lep name and lep object
	 */
	public Map<String, Lep> getLepDirectory() {
		return this.lepDirectory;
	}
	
	/**
	 * get budget remaining
	 * @return the budget
	 */
	public double getBudget() {
		return gardenMap.getCost();
	}
	/**
	 * sets the budget
	 * @param budget
	 */
	public void setBudget(double budget) {
		gardenMap.setBudget(budget);
	}
	
	/**
	 * sets the x location
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * sets the y location
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * gets the x location
	 * @return x
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * gets the y location
	 * @return y
	 */
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
	public void setTranslate(Vector2 translate) {
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
	public Vector2 getTranslate() {
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
	 * Updates the current sorting used for the plants
	 * @param sort the new sorting type
	 * @return a new list of plants sorted by the sort and filter
	 */
	public ArrayList<String> updateSort(Comparator<PlantSpecies> sort) {
		this.sort = sort;
		return filterAndSortPlants();
	}
	

	public ArrayList<String> updateFilter(PlantFilter filter) {
		this.filter = filter;
		return filterAndSortPlants();
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
	 * @author Ishika Govil
	 */
	public Vector2 translateScaledPlot(Vector2 topLeft) {
		double translateX = topLeft.getX() - this.getGarden().getExtremes().get(3).getX() * scale;
		double translateY = topLeft.getY() - this.getGarden().getExtremes().get(0).getY() * scale;
		Vector2 translate = new Vector2(translateX, translateY);
		this.setTranslate(translate);
		return translate;
	}
	
	/**
	 * Makes the arraylist of new plant names for when user sorts and filters plants
	 * @return the arraylist
	 */
	private ArrayList<String> filterAndSortPlants() {
		ArrayList<PlantSpecies> plants = new ArrayList<>(plantDirectory.values());
		ArrayList<String> names = new ArrayList<>();

		Collections.sort(plants, this.sort);
		plants.forEach((plant) -> {
			if(filter.include(plant)) {
				names.add(plant.getGenusName() + "-" + plant.getSpeciesName());
			}
		});
		
		return names;
	}
	/*
	public ArrayList<Integer> isWoodyData() {
		ArrayList<Integer> plantCount = new ArrayList<Integer>();
		boolean isWoody;
		int woody = 0;
		int herb = 0;
		for (PlacedPlant p : this.gardenMap.getPlants()) {
			isWoody = p.getSpecies().isWoody();
			if (isWoody) {
				woody = woody + 1;
			}
			else {
				herb = herb + 1;
			}
		}
		plantCount.add(woody, herb);
		System.out.println("woody: " + woody);
		System.out.println("Herb: " + herb);
		return plantCount;
	}
	*/
}
