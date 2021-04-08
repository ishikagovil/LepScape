import java.util.*;

//Each region (spereated based on plating conditions will have a condition object associated with it
//The consition object has the soil type, moisture level, sunlight in the region 
//as well as the x,y coordinates of thee outline of that region
public class Conditions {
	private int soilType;
	private int moistureLevel;
	private int sunlight;
	private ArrayList<float[]> sectionOutline; //keeps track of the x,y coordinates of the outline of a given section with the given conditions
	
	//soil type, moisture level and sunlight range from 0-10
	public Conditions(int soilRange, int moistureRange, int sunRange) {
		this.soilType = soilRange;
		this.moistureLevel = moistureRange;
		this.sunlight = sunRange;
	}
	
	public void setSectionsOutlines(ArrayList<float[]> section) {this.sectionOutline=section;}
	
	//getters for attributes
	public int getSoilType() {return this.soilType;}
	public int getMoistureLevel() {return this.moistureLevel;}
	public int getSunlight() {return this.sunlight;}
	public ArrayList<float[]> getSections() {return this.sectionOutline;}

}

