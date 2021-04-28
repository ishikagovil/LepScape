import javafx.scene.paint.Color;

//Each region (spereated based on plating conditions will have a condition object associated with it
//The consition object has the soil type, moisture level, sunlight in the region 
//as well as the x,y coordinates of thee outline of that region
public class Conditions {
	private SoilType soilType;
	private MoistureType moistureType;
	private LightType sunlightType;
	
	private double x, y;
	
	//soil type, moisture level and sunlight range from 0-10
	public Conditions(SoilType soilType, MoistureType moistureType, LightType sunlightType) {
		this.soilType = soilType;
		this.moistureType = moistureType;
		this.sunlightType = sunlightType;
	}
	
	//getters for attributes
	public SoilType getSoilType() {
		return this.soilType;
	}
	public void setSoilType(SoilType type) {
		this.soilType = type;
	}
	public MoistureType getMoistureType() {
		return this.moistureType;
	}
	public void setMoistureType(MoistureType moistureType) {
		this.moistureType = moistureType;
	}
	public LightType getSunlightType() {
		return this.sunlightType;
	}
	public void setSunlightType(LightType lightType) {
		this.sunlightType = lightType;
	}
	public double getX() {
		return this.x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return this.y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public Color toColor() {
		return new Color(
			soilType.getValue(), 
			moistureType.getValue(),
			sunlightType.getValue(),
			1.0);

	}
}

