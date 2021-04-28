import java.util.ArrayList;

//Lep specie
//Each lep object will have a specie name, genus name, common name, 
//a description of the lep, and all the native plants that it can feed on to survive
public class Lep {
	
	private String speciesName;
	private String genusName;
	private String commonName;
	private String description;
	private ArrayList<String> thrivesInGenus;
	private ArrayList<PlantSpecies> thrivesIn; //all the plants species the lep feeds on
	
	
	public Lep(String speciesName, String genusName, String commonName) {
		this.speciesName = speciesName;
		this.genusName = genusName;
		this.commonName = commonName;
		thrivesInGenus = new ArrayList<>();
		thrivesIn = new ArrayList<>();
	}
	
	/**
	 * Sets the description for the lep
	 * @param des the description
	 */
	public void setDescription(String des) {this.description = des;}
	/**
	 * Sets the plantSpecies that support the lep
	 * @param thrives the arrayList of species
	 */
	public void setThrivesIn(ArrayList<PlantSpecies> thrives) {this.thrivesIn = thrives;}

	/**
	 * sets the genus of the species lep can feed on
	 * @param thrives arraylist of genus names
	 */
	public void setThrivesInGenus(ArrayList<String> thrives) {this.thrivesInGenus = thrives;}
	public ArrayList<String> getThrivesInGenus() {return this.thrivesInGenus;}
	
	/**
	 * getter for specie name of lep
	 * @return the name
	 */
	public String getSpeciesName() {return this.speciesName;}
	/**
	 * gets the genus name of the lep
	 * @return the name
	 */
	public String getGenusName() {return this.genusName;}
	
	/**
	 * gets the common name of the lep
	 * @return the name
	 */
	public String getCommonName() {return this.commonName;}
	/**
	 * gets a description of the lep
	 * @return the description
	 */
	public String getDescription() {return this.description;}
	/**
	 * gets the plants species lep can feed on
	 * @return the plantSpecies
	 */
	public ArrayList<PlantSpecies> getThrivesIn(){return this.thrivesIn;}
	
}