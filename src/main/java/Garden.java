import java.util.*;

public class Garden implements java.io.Serializable {
	
	/**
	 * Class represents the Garden with its plants, boundaries, and conditions
	 */
	private static final long serialVersionUID = 1L;
	public int numLeps;
	public double cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<Vector2> outline;
	public ArrayList<Vector2> polygonCorners;
	public ArrayList<Conditions> sections;
	public Map<String, Lep> leps;
	public Set<PlantSpecies> compostBin;
	public ArrayList<String> plant;
//	public transient WritableImage image;
	public int width;
	public int height;
	public int[][] data;
	public int plotWidth;
	public int plotHeight;
	public int[][] plotData;
	public double lengthPerPixel;
	public double scale;
	public transient HashMap<String, PlacedPlant> placedPlants;
//	public transient BufferedImage image;
//	private transient SimpleDoubleProperty costForgallery;

	
	/**
	 * @author Ishika Govil, Kimmy Huynh, Arunima Dey
	 */

	/**
	 * Creates a default garden object
	 */
	public Garden() {
		this.plants = new ArrayList<PlacedPlant>();
		this.outline = new ArrayList<Vector2>();
		this.polygonCorners = new ArrayList<Vector2>();
		this.sections = new ArrayList<Conditions>();
		this.leps = new HashMap<String, Lep>();
		this.compostBin = new HashSet<PlantSpecies>();
		this.plant = new ArrayList<>();
		this.width = 0;
		this.height = 0;
		this.data = new int[width][height];
		this.placedPlants= new HashMap<>();
		System.out.println("outline: "+ outline);
		System.out.println("polygonCorners"+polygonCorners);
//		this.image = new BufferedImage (0,0,0);
	}
	
	/**
	 * Creates a Garden with starting leps and cost
	 * @param numLeps the starting leps
	 * @param cost the starting cost
	 */
	public Garden(int numLeps, int cost) {
		this();
		this.numLeps = numLeps;
		this.cost = cost;
	}
	
	/**
	 * Sets the width, height and data attributes
	 * @param width
	 * @param height
	 * @param data
	 * @author Arunima Dey
	 */
	public void setGardenImageInfo(int width, int height, int[][] data){
		//this.image = image;
		this.width = width;
		this.height = height;
		this.data = data;
		//makeData();
	}
	
	/**
	 * Sets the width, height, and data attributes for plot image
	 * @param width
	 * @param height
	 * @param data
	 * @author Arunima Dey
	 */
	public void setPlotImageInfo(int width, int height, int[][] data){
		//this.image = image;
		this.plotWidth = width;
		this.plotHeight = height;
		this.plotData = data;
		//makeData();
	}
	
	/**
	 * getter for data of garden
	 * @return the data
	 * @author Arunima Dey
	 */
	public int[][] getGardenData(){
		return this.data;
	}
	
	/**
	 * Get the width of this garden
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Get the height of this garden
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Get the number of leps for this garden
	 * @return the number of leps supported
	 */
	public int getNumLeps() {
		return this.numLeps;
	}
	
	/**
	 * Set the number of leps supported
	 * @param x the new amount of leps supported
	 */
	public void setNumLeps(int x) {
		this.numLeps = x;
	}
	
	/**
	 * Add an amount to the number of leps in the garden
	 * @param x the amount of leps to add
	 */
	public void addNumLeps(int x) {
		this.numLeps += x;
	}
	
	/**
	 * Get the cost of the garden
	 * @return the cost
	 */
	public double getCost() {
		return this.cost;
	}
	
	/**
	 * Set the cost of the garden
	 * @param d the new cost
	 */
	public void setCost(double d) {
		this.cost = d;
	}
	
	/**
	 * Add an amount to the cost of this garden
	 * @param x the amount to be added
	 */
	public void addCost(int x) {
		this.cost += x;
	}
	
	/**
	 * Returns the list of coordinates of freedrawn piece of boundary set by user
	 * @return ArrayList<double[]> representing list of all free drawn plot's boundary coordinates
	 */
	public ArrayList<Vector2> getOutline() {
		return this.outline;
	}
	
	/**
	 * Returns the list of coordinates of the polygon piece of boundary set by user
	 * @return ArrayList<double[]> representing list of all polygon's boundary coordinates
	 */
	public ArrayList<Vector2> getPolygonCorners() {
		return this.polygonCorners;
	}
	
	/**
	 * Get the placed plants in this garden
	 * @return the list of placed plants
	 */
	public ArrayList<PlacedPlant> getPlants() {
		return this.plants;
	}
	
	/**
	 * Get the conditions for this garden
	 * @return the conditions list
	 */
	public ArrayList<Conditions> getSections() {
		return this.sections;
	}
	
	/**
	 * Add a section to the conditions for this garden
	 * @param cond the Conditions to be added to this garden
	 */
	public void addSection(Conditions cond) {
		this.sections.add(cond);
	}
	
	/**
	 * Get the leps of this garden
	 * @return a Map of the leps in this garden
	 */
	public Map<String, Lep> getLeps() {
		return this.leps;
	}
	
	/**
	 * Get the plants in the compost bin
	 * @return the compost bin plants
	 */
	public Set<PlantSpecies> getCompostBin() {
		return this.compostBin;
	}
	
	/**
	 * Add a plant to the garden
	 * @param plant the plant to be added
	 */
	public void addToGarden(PlacedPlant plant) {
		System.out.println("adding to garden");
		plants.add(plant);
	}

	/**
	 * Called when user is drawing to update the boundary outline ArrayList
	 * @param double x representing x coordinate
	 * @param double y representing y coordinate
	 */
	public void updateOutline(double x, double y) { 
		Vector2 v = new Vector2(x, y);
		this.outline.add(v);
	}
	/**
	 * Called after user is done moving anchors to set plot boundary shape
	 * @param double x representing x coordinate
	 * @param double y representing y coordinate
	 */
	public void setPolygonCorners(double x, double y) {
		Vector2 v = new Vector2(x, y);
		this.polygonCorners.add(v);
	}
	/**
	 * Clears all plot boundary coordinates, and called when user clears their plot
	 */
	public void clearOutline() {
		this.outline = new ArrayList<Vector2>(); 
		this.polygonCorners = new ArrayList<Vector2>(); 
	}
	/**
	 * Finds the extreme values of the plot 
	 * @return ArrayList<double[]> of extreme values clockwise starting from the top
	 * @author Ishika Govil 
	 */
	public ArrayList<Vector2> getExtremes() {
		System.out.println("outline: "+ outline);
		System.out.println("polygon: "+ polygonCorners);
		ArrayList<Vector2> scaledOutlines = new ArrayList<>();
		ArrayList<Vector2> extrema = new ArrayList<>();
		int lowestX = 0; 
		int lowestY = 0; 
		int highestX = 0; 
		int highestY = 0;
		scaledOutlines.addAll(outline);
		scaledOutlines.addAll(polygonCorners);
		System.out.println("scaled: " +scaledOutlines);
		Iterator<Vector2> itr = scaledOutlines.iterator();
		int idx = 0;
		while(itr.hasNext()) {
			Vector2 point = itr.next();
			if(point.getX() < scaledOutlines.get(lowestX).getX() && point.getX()!= -1)
				lowestX = idx;
			if(point.getX() > scaledOutlines.get(highestX).getX() && point.getX()!= -1)
				highestX = idx;
			if(point.getY() <  scaledOutlines.get(lowestY).getY() && point.getY()!= -1)
				lowestY = idx;
			if(point.getY() > scaledOutlines.get(highestY).getY() && point.getY()!= -1)
				highestY = idx;	
			idx++;
		}
		extrema.add(scaledOutlines.get(lowestY));
		extrema.add(scaledOutlines.get(highestX));
		extrema.add(scaledOutlines.get(highestY));
		extrema.add(scaledOutlines.get(lowestX));
		return extrema;
	}
	


}
