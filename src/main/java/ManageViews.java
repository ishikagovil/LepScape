import java.util.*;

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
//	    views.put("Summary", new Summary(stage,controller,this));
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
	public WritableImage getImage() {
		return img;
	}
	public void setImage(WritableImage img) {
		this.img = img;
	}
	public void redrawImage() {
		this.getGC().drawImage(img, 0, 0);
	}
	public void setView(String key) {
		this.currView = this.views.get(key);
	}
	
	public void onChangeCursor(boolean hand) {
		this.currView.changeCursor(hand);
	}
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
