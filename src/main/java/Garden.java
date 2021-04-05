import java.util.*;

public class Garden {
	
	private int numLeps;
	private int cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<float[]> outline;
	public ArrayList<Conditions> sections;
	public Map<String, Lep> leps;
	public Set<PlantSpecies> compostBin;
	
	public Garden() {
		this.plants = new ArrayList<PlacedPlant>();
		this.outline = new ArrayList<float[]>();
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
	
	public int getCost() {
		return this.cost;
	}
	
	public void setCost(int x) {
		this.cost = x;
	}
	
	public ArrayList<float[]> getOutline() {
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
	
	

}
