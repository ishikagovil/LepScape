import java.util.*;

public class Garden {
	
	private int numLeps;
	private int cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<double[]> outline;
	public ArrayList<Conditions> sections;
	public Map<String, Lep> leps;
	public Set<PlantSpecies> compostBin;
	
	/**
	 * @author Ishika Govil, Kimmy Huynh,
	 */
	
	/**
	 * 
	 */
	public Garden() {
		this.plants = new ArrayList<PlacedPlant>();
		this.outline = new ArrayList<double[]>();
		this.sections = new ArrayList<Conditions>();
		this.leps = new HashMap<String, Lep>();
		this.compostBin = new HashSet<PlantSpecies>();
	}
	
	public Garden(int numLeps, int cost) {
		this();
		this.numLeps = numLeps;
		this.cost = cost;
	}
	
	public int getNumLeps() {
		return this.numLeps;
	}
	
	public void setNumLeps(int x) {
		this.numLeps = x;
	}
	
	public void addNumLeps(int x) {
		this.numLeps += x;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int x) {
		this.cost = x;
	}
	
	public void addCost(int x) {
		this.cost += x;
	}
	
	/**
	 * Returns the list of coordinates of a plot's boundary set by user
	 * @return ArrayList<double[]> representing list of all plot boundary coordinates
	 */
	public ArrayList<double[]> getOutline() {
		return this.outline;
	}
	
	public ArrayList<PlacedPlant> getPlants() {
		return this.plants;
	}
	
	public ArrayList<Conditions> getSections() {
		return this.sections;
	}
	
	public Map<String, Lep> getLeps() {
		return this.leps;
	}
	
	public Set<PlantSpecies> getCompostBin() {
		return this.compostBin;
	}
	
	public void addToGarden(PlacedPlant plant) {
		plants.add(plant);
	}
	
	/**
	 * Called when user is drawing to update the boundary outline ArrayList
	 * @param double x representing x coordinate
	 * @param double y representing y coordinate
	 */
	public void updateOutline(double x, double y) { 
		double[] arr = {x,y};
		this.outline.add(arr);
	}

	public void clearOutline() { //called if user clears their drawing
		this.outline = new ArrayList<double[]>(); 
	}

	


}
