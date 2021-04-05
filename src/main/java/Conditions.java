import java.util.*;

//Each region (spereated based on plating conditions will have a condition object associated with it
//The consition object has the soil type, moisture level, sunlight in the region 
//as well as the x,y coordinates of thee outline of that region
public class Conditions {
	public int soilType;
	public int moistureLevel;
	public int sunlight;
	public ArrayList<float[]> sectionOutline; //keeps track of the x,y coordinates of the outline of a given section with the given conditions
		
	//setters for the attributes
	//soil type, moisture level and sunlight range from 0-10
	public void setMoistureLevel(int range) {this.moistureLevel=range;} 
	public void setSoilType(int range) {this.soilType=range;}
	public void setSunlight(int range) {this.sunlight=range;}
	public void setSectionsOutlines(ArrayList<float[]> section) {this.sectionOutline=section;}
	
	//getters for attributes
	public int getSoilType() {return this.soilType;}
	public int getMoistureLevel() {return this.moistureLevel;}
	public int getSunlight() {return this.sunlight;}
	public ArrayList<float[]> getSections() {return this.sectionOutline;}

}

