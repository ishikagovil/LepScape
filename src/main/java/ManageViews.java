import java.util.*;
import javafx.scene.canvas.GraphicsContext;
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
	
	public ManageViews(Stage stage, Controller c) {
		dimLen = new ArrayList<>();
		this.controller = c;
	    this.stage = stage;
		initializeViews();
	    this.currView = this.getView("Start");
	}
	
	public void initializeViews() {
		views = new HashMap<>();
		views.put("Start", new Start(stage, controller,this));
	    views.put("Gallery", new Gallery(stage,controller,this));  
	    views.put("PlotDesign", new PlotDesign(stage, controller,this));
	    views.put("ConditionScreen", new ConditionScreen(stage,controller,this));
	    views.put("Summary", new Summary(stage,controller,this));
	}
	public void switchViews(String next) {
		if(next.equals("Drawing"))
			((PlotDesign) this.currView).onDrawing();
		else
			this.currView = this.getView(next);
	}
	
	public BorderPane getBorderPane() {
		return this.currView.border;
	}
	public int getScreenWidth() {
		return this.currView.screenWidth;
	}
	public int getScreenHeight() {
		return this.currView.screenHeight;
	}
	public GraphicsContext getGC() {
		return this.currView.gc;
	}
	public View getView(String key) {
		return views.get(key);
	}
	public void setImage(WritableImage img) {
		this.img = img;
	}
	public void setView(String key) {
		this.currView = this.views.get(key);
	}
	
	public void onChangeCursor(boolean hand) {
		this.currView.changeCursor(hand);
	}
}
