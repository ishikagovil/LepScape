import java.util.*;


public class Garden implements java.io.Serializable {
	
	/**
	 * Class represents the Garden with its plants, boundaries, and conditions
	 */
	private static final long serialVersionUID = 1L;
	public int numLeps;
	public double cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<double[]> outline;
	public ArrayList<double[]> polygonCorners;
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
	public Garden() {
		this.plants = new ArrayList<PlacedPlant>();
		this.outline = new ArrayList<double[]>();
		this.polygonCorners = new ArrayList<double[]>();
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
	 */
	public int[][] getGardenData(){
		return this.data;
	}
	
	/**
	 * getter for width of garden
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * getter for height of garden
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * getter for number of leps supported
	 * @return 
	 */
	public int getNumLeps() {
		return this.numLeps;
	}
	
	/**
	 * sets the number of leps supported
	 * @param x
	 */
	public void setNumLeps(int x) {
		this.numLeps = x;
	}
	

	public void addNumLeps(int x) {
		this.numLeps += x;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double d) {
		this.cost = d;
	}
	
	public void addCost(int x) {
		this.cost += x;
	}
	
	/**
	 * Returns the list of coordinates of freedrawn piece of boundary set by user
	 * @return ArrayList<double[]> representing list of all free drawn plot's boundary coordinates
	 */
	public ArrayList<double[]> getOutline() {
		return this.outline;
	}
	
	/**
	 * Returns the list of coordinates of the polygon piece of boundary set by user
	 * @return ArrayList<double[]> representing list of all polygon's boundary coordinates
	 */
	public ArrayList<double[]> getPolygonCorners() {
		return this.polygonCorners;
	}
	
	public ArrayList<PlacedPlant> getPlants() {
		return this.plants;
	}
	
	public ArrayList<Conditions> getSections() {
		return this.sections;
	}
	
	public void addSection(Conditions cond) {
		this.sections.add(cond);
	}
	
	public Map<String, Lep> getLeps() {
		return this.leps;
	}
	
	public Set<PlantSpecies> getCompostBin() {
		return this.compostBin;
	}
	
	/**
	 * when plant is placed adds to garden
	 * @param plant the PlacedPlant object that is placed
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
		double[] arr = {x,y};
		this.outline.add(arr);
	}
	/**
	 * Called after user is done moving anchors to set plot boundary shape
	 * @param double x representing x coordinate
	 * @param double y representing y coordinate
	 */
	public void setPolygonCorners(double x, double y) {
		double[] arr = {x,y};
		this.polygonCorners.add(arr);
	}
	/**
	 * Clears all plot boundary coordinates, and called when user clears their plot
	 */
	public void clearOutline() {
		this.outline = new ArrayList<double[]>(); 
		this.polygonCorners = new ArrayList<double[]>(); 
	}
	/**
	 * Finds the extreme values of the plot 
	 * @return ArrayList<double[]> of extreme values clockwise starting from the top
	 * @author Ishika Govil 
	 */
	public ArrayList<double[]> getExtremes() {
		ArrayList<double[]> scaledOutlines = new ArrayList<>();
		ArrayList<double[]> extrema = new ArrayList<>();
		int lowestX = 0; 
		int lowestY = 0; 
		int highestX = 0; 
		int highestY = 0;
		scaledOutlines.addAll(outline);
		scaledOutlines.addAll(polygonCorners);
		Iterator<double[]> itr = scaledOutlines.iterator();
		int idx = 0;
		while(itr.hasNext()) {
			double[] point = (double[])itr.next();
			if(point[0] <  scaledOutlines.get(lowestX)[0] && point[0]!= -1)
				lowestX = idx;
			if(point[0] > scaledOutlines.get(highestX)[0] && point[0]!= -1)
				highestX = idx;
			if(point[1] <  scaledOutlines.get(lowestY)[1] && point[1]!= -1)
				lowestY = idx;
			if(point[1] > scaledOutlines.get(highestY)[1] && point[1]!= -1)
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
