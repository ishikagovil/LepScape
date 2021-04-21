import java.util.*;

import javafx.scene.Node;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ManageViews {
	public double dimPixel; //Used when setting the dimensions of the garden
	public ArrayList<double[]> dimLen; //Used when setting the dimensions of the garden
	public WritableImage img; //sets the image from PlotDesign
	Map<String,View> views;
	View currView;
	Controller controller;
	Stage stage;
	public Map<String, String> plantImages;
	
	/**
	 * @author Ishika Govil
	 */
	
	/**
	 * Constuctor for the ManageViews class, which initializes the HashMap of views and other fields
	 * Sets the first view screen to the Start screen
	 * @param Stage
	 * @param Controller
	 * @author Ishika Govil
	 */
	public ManageViews(Stage stage, Controller c, String fileName) {
		importImages(fileName);
		dimLen = new ArrayList<>();
		this.controller = c;
	    this.stage = stage;
		initializeViews();
	    this.currView = this.getView("Start");
	}

	public void importImages(String fileName) {
		plantImages = new HashMap<>();
		plantImages = CSVtoPlants.readFileForImg(fileName);
	}
	
	/**
	 * Loads all the screens to the HashMap with a key that corresponds to the class
	 * @author Ishika Govil
	 */
	public void initializeViews() {
		views = new HashMap<>();
		views.put("Start", new Start(stage, controller,this));
	    views.put("Gallery", new Gallery(stage,controller,this));  
	    views.put("PlotDesign", new PlotDesign(stage, controller,this));
	    views.put("ConditionScreen", new ConditionScreen(stage,controller,this));
	    views.put("Summary", new Summary(stage,controller,this));
	    views.put("GardenDesign", new GardenDesign(stage,controller,this));
	    views.put("LearnMore", new LearnMore(stage,controller,this));
	    views.put("Lepedia", new Lepedia(stage,controller,this));
	}
	
	/** 
	 * Called in Controller when user wishes to switch views
	 * The next action is determined by the string passed to the function
	 * @param String describing the next action to be shown
	 * @author Ishika Govil 
	 */
	public void switchViews(String next) {
		if(next.equals("Drawing"))
			((PlotDesign) this.currView).onDrawing();
		else if(next.equals("Shape")) {
	        //Only allows for one shape drawn at once
			if(((PlotDesign) this.currView).shapeClicked == false)
				((PlotDesign) this.currView).onShape();
		}
		else
			this.currView = this.getView(next);
	}
	
	/** 
	 * Returns the BorderPane associated with the current View
	 * @return BorderPane 
	 */
	public BorderPane getBorderPane() {
		return this.currView.border;
	}
	/** 
	 * Returns the screen width associated with the current View
	 * @return int 
	 */
	public int getScreenWidth() {
		return this.currView.screenWidth;
	}
	/** 
	 * Returns the screen height associated with the current View
	 * @return int 
	 */
	public int getScreenHeight() {
		return this.currView.screenHeight;
	}
	
	/** 
	 * Returns the GraphicsContext associated with the current View
	 * @return GraphicsContext 
	 */
	public GraphicsContext getGC() {
		return this.currView.gc;
	}
	
	/** 
	 * Returns the view associated with the given key
	 * @param String representing key of the desired View
	 * @return View associated with the key
	 */
	public View getView(String key) {
		return views.get(key);
	}
	
	/** 
	 * Writes the WritableImage field img after user saves their PlotDesign
	 * Useful for sharing the WritableImage between View classes
	 * @param WritableImage  
	 */
	public void setImage(WritableImage img) {
		this.img = img;
	}
	
	/** 
	 * Sets the current View to the View described by the key
	 * @param String representing key of the desired View
	 */
	public void setView(String key) {
		this.currView = this.views.get(key);
	}
	
	public void redrawImage() {
		this.getGC().drawImage(img, 0, 0);
	}

	/** 
	 * Called when mouse enters or exits a button
	 * @param boolean describing if the cursor to be shown is a hand or not 
	 * @author Ishika Govil 
	 */
	public void onChangeCursor(boolean hand) {
		this.currView.changeCursor(hand);
	}
	
	// restart the plot, clear all lines so user can draw a new garden design
	public void restartPlot() {
		initializeViews();
		this.dimLen = new ArrayList<>(); 
		this.currView = this.views.get("PlotDesign");
	}
	
	public void removePlant(Node node) {
		views.get("GardenDesign").removePlant(node);
	}
	
	//methods only used by garden design
	public void setY(double y, Node n){currView.setY(y, n);}
	public void setX(double x, Node n){currView.setX(x, n);}
	public void addImageView(double x, double y, String key) {currView.addImageView(x, y, key);}
//	public void removePlant(Node n) {currView.removePlant(n);}
	public void makeInfoPane(String name, String info) {currView.makeInfoPane(name, info);}
	public void updateBudgetandLep(int cost, int lepCount) {currView.updateBudgetandLep(cost, lepCount);}
	
	public void fillRegion(int startX, int startY, Color fillColor) {
		// Inspired by the flood fill example https://stackoverflow.com/questions/23983465/is-there-a-fill-function-for-arbitrary-shapes-in-javafx
		PixelReader pr = this.img.getPixelReader();
		PixelWriter pw = this.img.getPixelWriter();
		
		Stack<Point2D> fillStack = new Stack<>();
		
		fillStack.push(new Point2D(startX, startY));
		
		while(!fillStack.empty()) {
			Point2D curr = fillStack.pop();

			int x = (int) curr.getX();
			int y = (int) curr.getY();

			if(pr.getColor(x, y).grayscale().getBrightness() < 0.95) continue;
			
			pw.setColor(x, y, fillColor);

			if(x > 0) {
				fillStack.push(new Point2D(x-1, y));
			}
			
			if(x < this.img.getWidth() - 1) {
				fillStack.push(new Point2D(x+1, y));
			}

			if(y > 0) {
				fillStack.push(new Point2D(x, y-1));
			}

			if(y < this.img.getHeight() - 1) {
				fillStack.push(new Point2D(x, y+1));
			}
				
		}
		
	}
}
