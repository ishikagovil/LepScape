import java.util.ArrayList;

//Lep specie
//Each lep object will have a specie name, genus name, common name, 
//a description of the lep, and all the native plants that it can feed on to survive
public class Lep {
	
	private String speciesName;
	private String genusName;
	private String commonName;
	private String description;
	private ArrayList<PlantSpecies> thrivesIn; //all the plants species the lep feeds on
	
	public Lep(String speciesName, String genusName, String commonName) {
		this.speciesName = speciesName;
		this.genusName = genusName;
		this.commonName = commonName;
	}
	
	public void setDescription(String des) {this.description = des;}
	public void setThrivesIn(ArrayList<PlantSpecies> thrives) {this.thrivesIn = thrives;}
	
	public String getSpeciesName() {return this.speciesName;}
	public String getGenusName() {return this.genusName;}
	public String getCommonName() {return this.commonName;}
	public String getDescription() {return this.description;}
	public ArrayList<PlantSpecies> getThrivesIn(){return this.thrivesIn;}
	
}