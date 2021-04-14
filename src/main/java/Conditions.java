import java.util.*;

import javafx.scene.paint.Color;

//Each region (spereated based on plating conditions will have a condition object associated with it
//The consition object has the soil type, moisture level, sunlight in the region 
//as well as the x,y coordinates of thee outline of that region
public class Conditions {
	private SoilType soilType;
	private int moistureLevel;
	private int sunlight;
	
	//soil type, moisture level and sunlight range from 0-10
	public Conditions(SoilType soilType, int moistureRange, int sunRange) {
		this.soilType = soilType;
		this.moistureLevel = moistureRange;
		this.sunlight = sunRange;
	}
	
	//getters for attributes
	public SoilType getSoilType() {
		return this.soilType;
	}
	public void setSoilType(SoilType type) {
		this.soilType = type;
	}
	public int getMoistureLevel() {
		return this.moistureLevel;
	}
	public void setMoistureLevel(int moistureLevel) {
		this.moistureLevel = moistureLevel;
	}
	public int getSunlight() {
		return this.sunlight;
	}
	public void setSunlight(int sunlight) {
		this.sunlight = sunlight;
	}
	public Color toColor() {
		return new Color(
				soilType.getValue(), 
				(double) moistureLevel / 10., 
				(double) sunlight / 10., 
				1.0);

	}
}

