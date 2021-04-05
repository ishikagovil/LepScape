import java.util.ArrayList;

//Lep specie
//Each lep object will have a specie name, genus name, common name, 
//a description of the lep, and all the native plants that it can feed on to survive
public class Lep {
	
	public String speciesName;
	public String genusName;
	public String commonName;
	public String description;
	public ArrayList<PlantSpecies> thrivesIn; //all the plants species the lep feeds on
	
	public Lep(String speciesName, String genusName, String commonName, String description, ArrayList<PlantSpecies> thrivesIn) {}
	
	public String getSpeciesName() {return this.speciesName;}
	public String getGenusName() {return this.genusName;}
	public String getCommonName() {return this.commonName;}
	public String getDescription() {return this.description;}
	public ArrayList<PlantSpecies> getThrivesIn(){return this.getThrivesIn();}
	
}