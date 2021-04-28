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
	/**
	 * Get the soil type for this condition
	 * @return the condition soil type
	 */
	public SoilType getSoilType() {
		return this.soilType;
	}
	/**
	 * Set the SoilType for this condition
	 * @param type the new SoilType
	 */
	public void setSoilType(SoilType type) {
		this.soilType = type;
	}
	/**
	 * Get the condition moisture type
	 * @return the moisture type
	 */
	public MoistureType getMoistureType() {
		return this.moistureType;
	}
	/**
	 * Set the MoistureType for this condition
	 * @param type the new MoistureType
	 */
	public void setMoistureType(MoistureType moistureType) {
		this.moistureType = moistureType;
	}
	/**
	 * Get the light type of this condition
	 * @return the light type
	 */
	public LightType getSunlightType() {
		return this.sunlightType;
	}
	/**
	 * Set the LightType for this condition
	 * @param type the new LightType
	 */
	public void setSunlightType(LightType lightType) {
		this.sunlightType = lightType;
	}
	/**
	 * Get the X of where this condition was placed
	 * @return the X coordinate
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Set the X of where this condition was placed
	 * @param the X coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Get the Y of where this condition was placed
	 * @return the Y coordinate
	 */
	public double getY() {
		return this.y;
	}
	/**
	 * Set the Y of where this condition was placed
	 * @param the Y coordinate
	 */
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

