import java.util.*;

public class Model implements java.io.Serializable{
	
	public Garden gardenMap;
	public Map<String, PlantSpecies> plantDirectory;
	public Map<String, Lep> lepDirectory;
	public ArrayList<Garden> savedGardens;
	public double lengthPerPixel;
	public double scale;
	public Vector2 translate;
	public double x;
	public double y;
	public int lepCount;
	public String movedPlant;
	public double initialX;
	public double initialY;
	public HashSet<String> deleted;
	public Boolean editGarden;
	public int editGardenIndex;
	
	/**
	 * @author Ishika Govil, Kimmy Huynh
	 */
	public Model() {
		this.savedGardens = new ArrayList<>();
		this.gardenMap = new Garden();
		this.plantDirectory = new HashMap<>();
		this.lepDirectory = new HashMap<>();
		this.lengthPerPixel = -1;
		this.movedPlant = "";
		this.deleted = new HashSet<>();
		editGarden = false;

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
	 */
	public void placePlant(double x, double y, String key, String nodeId) {
		System.out.println("adding to Garden");
		PlantSpecies specie = plantDirectory.get(key);
		gardenMap.plants.add(new PlacedPlant(x,y,specie));
		gardenMap.placedPlants.put(nodeId, new PlacedPlant(x,y,specie));
 		System.out.println("plants: "+gardenMap.placedPlants);
//		gardenMap.addToGarden(new PlacedPlant(x,y,specie));
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
	
	public ArrayList<String> getFilteredList(Conditions cond) {
		ArrayList<String> names = new ArrayList<>();
		plantDirectory.forEach((name, plant) -> {
			boolean matchMoist = plant.getMoistureType() == MoistureType.ANY 
					|| cond.getMoistureType() == MoistureType.ANY 
					|| cond.getMoistureType() == plant.getMoistureType();
			boolean matchDirt = plant.getSoilType() == SoilType.ANY 
					|| cond.getSoilType() == SoilType.ANY 
					|| cond.getSoilType() == plant.getSoilType();
			boolean matchSun = plant.getLightType() == LightType.ANY 
					|| cond.getSunlightType() == LightType.ANY 
					|| cond.getSunlightType() == plant.getLightType();
			
			if(matchMoist && matchDirt && matchSun) {
				names.add(name);
			}
		});
		
		return names;
	}
}
