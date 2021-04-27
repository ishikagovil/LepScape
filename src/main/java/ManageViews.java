import java.util.*;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ManageViews {
	public double dimPixel; //Used when setting the dimensions of the garden
	public ArrayList<double[]> dimLen; //Used when setting the dimensions of the garden
	public WritableImage img; //sets the image from PlotDesign
	Map<String,View> views;
	View currView;
	Controller controller;
	Stage stage;
	public Map<String, ImageView> plantImages;
	public Map<String, ImageView> lepImages;
	
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
	public ManageViews(Stage stage, Controller c, String fileName, String fileName2) {
		importImages(fileName, fileName2);
		dimLen = new ArrayList<>();
		dimPixel = -1;
		this.controller = c;
	    this.stage = stage;
		initializeViews();
	    this.currView = this.getView("Start");
	}
	
	public void setPlantImg(Map<String, ImageView> imgs) {
		this.plantImages = imgs;
	}

	public void importImages(String fileName, String fileName2) {
		plantImages = new HashMap<>();
		System.out.println("setting plant images");
		plantImages = CSVtoPlants.readFileForImg(fileName);
		lepImages = new HashMap<>();
		System.out.println("setting lep images");
		lepImages = CSVtoLeps.readFileForImg(fileName2);
	}

	public Map<String, ImageView> getPlantImages() {
		return this.plantImages;
	}
	
	public Map<String, ImageView> getLepImages() {
		return this.lepImages;
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
		if(next.equals("Drawing")) {
			((PlotDesign) this.currView).onDrawing();
		}
		else if(next.equals("Shape")) {
	        //Only allows for one shape drawn at once
			if(((PlotDesign) this.currView).shapeClicked == false) {
				((PlotDesign) this.currView).onShape();
			}
			else {				
				((PlotDesign) this.currView).dragAnchor = true;
				((PlotDesign) this.currView).toggleAnchorHandler();
			}
			((PlotDesign) this.currView).validateSave();
			((PlotDesign) this.currView).shapeClicked = true;
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
	 * Returns the garden width associated with the current View
	 * @return int 
	 */
	public double getGardenWidth() {
		return this.currView.gardenWidth;
	}
	/** 
	 * Returns the garden height associated with the current View
	 * @return int 
	 */
	public double getGardenHeight() {
		return this.currView.gardenHeight;
	}
	
	/** 
	 * Returns the top left corner of the garden view frame
	 * @return int 
	 */
	public double[] getGardenTopLeft() {
		return new double[]{this.currView.gardenTopLeftX,  this.currView.gardenTopLeftY};
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
	public void drawLine(double x1, double y1, double x2, double y2, boolean isPolygon) {
		this.currView.drawLine(x1, y1, x2, y2, isPolygon);
	}
	public void validateSave() {
		if(this.currView instanceof PlotDesign)
			((PlotDesign) this.currView).validateSave();
	}
	
	//methods only used by garden design
	public void setY(double y, Node n){currView.setY(y, n);}
	public void setX(double x, Node n){currView.setX(x, n);}
	public void addImageView(double x, double y, String key, double heightWidth) {
		((GardenDesign) views.get("GardenDesign")).addImageView(x,y,key,heightWidth);
//		currView.addImageView(x, y, key);
	}
//	public void removePlant(Node n) {currView.removePlant(n);}
	public void makeInfoPane(String name, String info) {currView.makeInfoPane(name, info);}
	public void updateBudgetandLep(int cost, int lepCount) {currView.updateBudgetandLep(cost, lepCount);}
}
