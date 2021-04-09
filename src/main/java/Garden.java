import java.util.*;

public class Garden {
	
	private int numLeps;
	private int cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<double[]> outline;
	public ArrayList<Conditions> sections;
	public Map<String, Lep> leps;
	public Set<PlantSpecies> compostBin;
	
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
	
	public void updateOutline(double x, double y) { //called to update the outline when user is drawing
		double[] arr = {x,y};
		this.outline.add(arr);
	}
	public void clearOutline() { //called if user clears their drawing
		this.outline = new ArrayList<double[]>(); 
	}

}
