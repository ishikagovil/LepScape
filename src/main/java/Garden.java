import javafx.scene.image.Image;
import java.awt.image.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.imageio.ImageIO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;

public class Garden implements Serializable {
	
	private int numLeps;
	private int cost;
	public ArrayList<PlacedPlant> plants;
	public ArrayList<double[]> outline;
	public ArrayList<double[]> polygonCorners;
	public ArrayList<Conditions> sections;
	public Map<String, Lep> leps;
	public Set<PlantSpecies> compostBin;
	private transient SimpleObjectProperty<Canvas> canvas;
//	private transient Canvas canvas;
	private transient Pane pane;
	public ArrayList<String> plant;
	public transient WritableImage image;
//	public transient BufferedImage image;
//	private transient SimpleDoubleProperty costForgallery;

	
	/**
	 * @author Ishika Govil, Kimmy Huynh,
	 */
	
	public Garden() {
		this.plants = new ArrayList<PlacedPlant>();
		this.outline = new ArrayList<double[]>();
		this.polygonCorners = new ArrayList<double[]>();
		this.sections = new ArrayList<Conditions>();
		this.leps = new HashMap<String, Lep>();
		this.compostBin = new HashSet<PlantSpecies>();
		this.plant = new ArrayList<>();
//		this.image = new BufferedImage (0,0,0);
	}
	
	public Garden(int numLeps, int cost) {
		this();
		this.numLeps = numLeps;
		this.cost = cost;
	}
	
//	public Garden(Canvas canvas, int cost, int lepCount, Pane pane) {
////		this.canvas = new SimpleObjectProperty<Canvas>(canvas);
////		this.canvas2 = canvas;
//		this.numLeps = lepCount;
//		this.cost = cost;
//		this.pane = pane;
//	}
	
	
	
//	public SimpleObjectProperty<Canvas> getCanvas() {
//		return canvas;
//	}
	public Pane getPane() {
		return this.pane;
	}
	
//	public Canvas getCanvas() {
//		return canvas;
//	}
	
	private void writeObject(ObjectOutputStream s) throws IOException{
		s.defaultWriteObject();
//		s.writeObject(pane);
//		s.writeObject(canvas);
//		s.writeObject(new SerializableRenderedImage(image));
//		image = javafx.embed.swing.SwingFXUtils.fromFXImage(image, null);
//		BufferedImage fromFXImage = SwingFXUtils.fromFXImage(image, null);
		
		//BufferedImage im = SwingFXUtils.fromFXImage(this.image, null);
		//ImageIO.write(im, "jpg", s);
		s.writeObject(plants);
		s.write(numLeps);
		s.write(cost);
	}
	
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException{
		s.defaultReadObject();
//		this.canvas = (SimpleObjectProperty<Canvas>) s.readObject();
		this.numLeps = s.read();
		this.cost = s.read();
//		image = ImageIO.read(s);

	}
	
	public int getNumLeps() {
		return this.numLeps;
	}
	
	public void setNumLeps(int x) {
		this.numLeps = x;
	}
	
	public void addNumLeps(int x) {
		this.numLeps += x;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(int x) {
		this.cost = x;
	}
	
	public void addCost(int x) {
		this.cost += x;
//		this.plants = (ArrayList<PlacedPlant>) s.readObject();
//		this.pane = (Pane) s.readObject();
//		this.canvas = new SimpleObjectProperty<>((Canvas)s.readObject());
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
	
	public Map<String, Lep> getLeps() {
		return this.leps;
	}
	
	public Set<PlantSpecies> getCompostBin() {
		return this.compostBin;
	}
	
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
