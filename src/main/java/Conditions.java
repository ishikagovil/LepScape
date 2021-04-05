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
	public void setMoistureLevel(int range) {} 
	public void setSoilType(int range) {}
	public void setSunlight(int range) {}
	public void setSectionsOutlines(ArrayList<float[]> sections) {}
	
	//getters for attributes
	public int getSoilType() {}
	public int getMoistureLevel() {}
	public int getSunlight() {}
	public ArrayList<float[]> getSections() {}

}

