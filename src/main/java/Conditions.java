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
	
	//getters/setters for attributes
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
	/**
	 * Converts the conditions into a standard color on the plot
	 * @return the corresponding color for this condition
	 * @author Jinay Jain
	 */
	public Color toColor() {
		return new Color(
			soilType.getValue(), 
			moistureType.getValue(),
			sunlightType.getValue(),
			1.0);

	}
	
	/**
	 * Creates a Conditions from the color specified (used when user clicks on plot)
	 * @param color the color to interpret
	 * @return a created Conditions object for that color
	 * @author Jinay Jain
	 */
	public static Conditions fromColor(Color color) {
		double red = color.getRed();
		double green = color.getGreen();
		double blue = color.getBlue();
		
		SoilType soil = SoilType.fromValue(red);
		MoistureType moisture = MoistureType.fromValue(green);
		LightType light = LightType.fromValue(blue);
		
		return new Conditions(soil, moisture, light);
	}
}

