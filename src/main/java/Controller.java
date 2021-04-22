import javafx.event.EventHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import javafx.application.Application;

/**
 * @author Ishika Govil, Arunima Dey, Dea Harjianto, Jinay Jain, Kimmy Huynh
 */
public class Controller extends Application {
	private final boolean DEBUG = true;
	ManageViews view;
	String fileName1 = "src/main/resources/testdata.csv";
	String fileName2 = "src/main/resources/testleps.csv";
	Model model;
	Stage stage;
	
	/** 
	 * Override for the Application start method. Instantiates all fields
	 * @param Stage
	 * @author Ishika Govil 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.model = new Model();
		this.model.setPlantDirectory(CSVtoPlants.readFile(fileName1));
		this.model.setLepDirectory(CSVtoLeps.readFile(fileName2));
		this.stage = stage;
	    view = new ManageViews(stage,this, fileName1);
	    Scene scene = new Scene(view.getBorderPane(), view.getScreenWidth(), view.getScreenHeight());
	    this.stage.setScene(scene);
	    setTheStage();
	}

	/** 
	 * Sets the border pane to the scene and shows the stage
	 * @author Ishika Govil 
	 */
	public void setTheStage() {
		this.stage.getScene().setRoot(this.view.getBorderPane());
		this.stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

	/** 
	 * Calls switchViews when a button is clicked
	 * @param String describing the next action to be shown
	 * @return EventHandler<ActionEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<ActionEvent> getHandlerforClicked(String next) { 
		return (e) -> { switchViews(next); };
	}
	
	/** 
	 * Calls onChangeCursor in view when mouse enters the button frame
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforMouseEntered() { //Sets cursor to hand  (calls changeCursor with true)
		return (e) -> {view.onChangeCursor(true);};
	}
	
	/** 
	 * Calls onChangeCursor in view when mouse exits the button frame
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforMouseExited() { //Changes cursor back (calls changeCursor with false)
		return (e) -> { view.onChangeCursor(false);  };

	}
	
	public EventHandler<MouseEvent> getHandlerforAnchor(Anchor anchor, boolean dragAnchor, DoubleProperty x, DoubleProperty y, Polygon poly, int idx) {
		return (e) -> { anchoring(e, anchor, dragAnchor, x, y, poly, idx); };
	}
	/**
	 * Calls pressed when mouse is pressed
	 * @param key the plant that is pressed
	 * @return EventHandler<MouseEvent>
	 * @author Arunima Dey
	 */
	public EventHandler<MouseEvent> getHandlerforPressed(String key){
		return (e) -> { pressed(e,key); };
	}
	/** 
	 * Calls drag when mouse is dragged
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforDrag() {
		return (e) -> {  drag(e); };
	}
	
	
	/** 
	 * Calls drag when mouse is dragged, specifically when user is drawing
	 * @param boolean describing whether mouse is pressed for the first time
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforDrawing(boolean isPressed) {
		return (e) -> {  draw(e, isPressed); };
	}
	
	/** 
	 * Calls drag when mouse is released
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforReleased(String key, Boolean startingInTile) {
		return (e) -> { release(e,key,startingInTile);  };
	}
	
	public EventHandler<MouseDragEvent> getHandlerforMouseEntered(String key){
		return (e) -> { entered(e,key); };
	}
	
	/** 
	 * Calls drag when mouse is dragged, specifically when user is setting dimensions
	 * @param boolean describing whether mouse is pressed for the first time
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforSettingDimension(boolean isPressed) {
		return (e) -> {  settingDimensionLine(e, isPressed); };
	}
		
	/**
	 * Creates a handler for setting the user's mode in Model
	 * @param mode the mode to set to after handler is called
	 * @return the associated handler
	 * @author Jinay Jain
	 */
	public EventHandler<ActionEvent> getHandlerforModeSetter(UserMode mode) {
		return (e) -> { 
			this.model.setMode(mode); 
		};
	}
	
	/**
	 * Creates handler for conditions canvas clicked
	 * @return the associated canvas click handler
	 * @author Jinay Jain
	 */
	public EventHandler<MouseEvent> getConditionsClickHandler() {
		return (e) -> {
			UserMode mode = this.model.getMode();
			if(mode == UserMode.SETTING_CONDITIONS) {
				ConditionScreen screen = (ConditionScreen) this.view.getView("ConditionScreen");
				screen.saveImage();
				fillRegion(e);
			} else if(mode == UserMode.PARTITIONING) {
				draw(e, true);
			}
		};
	}
	
	/**
	 * Creates handler for when conditions canvas is dragged
	 * @return the associated drag handler
	 * @author Jinay Jain
	 */
	public EventHandler<MouseEvent> getConditionsDragHandler() {
		return (e) -> { draw(e, false); };
	}
	
	/**
	 * Creates a handler to set the soil type
	 * @param newType the new soil type to set to
	 * @return the associated action handler
	 * @author Jinay Jain
	 */
	public EventHandler<ActionEvent> getConditionsSoilHandler(SoilType newType) {
		return (e) -> { this.model.getCurrentConditions().setSoilType(newType); };
	}
	
	/**
	 * Updates the value of current sunlight/moisture levels when setting garden conditions
	 * @param moistureLevel the new moisture level
	 * @param sunlight the new sunlight level
	 * @author Jinay Jain
	 */
	public void updateConditionSlider(int moistureLevel, int sunlight) {
		this.model.getCurrentConditions().setMoistureLevel(moistureLevel);
		this.model.getCurrentConditions().setSunlight(sunlight);
	}	
	
	/**
	 * Updates the budget based on the String in a TextField
	 * @param budgetString the String with the user's budget input
	 * @author Jinay Jain
	 */
	public void updateBudget(String budgetString) {
		try {
			int newBudget = Integer.parseInt(budgetString);
			this.model.setBudget(newBudget);
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Controls the event when mouse is pressed on a plant imageView
	 * Displays name and information of that plant in garden design screen
	 * @param event the mouse event
	 * @param key the plant that was pressed
	 */
	public void pressed(MouseEvent event, String key) {
		Node n = (Node) event.getSource();
		n.setMouseTransparent(true);
		System.out.println("Clicked");
		if(key!=null) {
			String name = model.plantDirectory.get(key).getCommonName();
			String description = model.plantDirectory.get(key).getDescription();
			view.makeInfoPane(name,description);
		}
		event.setDragDetect(true);
	}
	
	/**
	 * Controls the drag event. Moves the imageView with mouse and gives that x and y to model
	 * @param event
	 * @author Arunima Dey
	 */
	public void drag(MouseEvent event) {
		Node n = (Node)event.getSource();
		if (!DEBUG) {
			System.out.println("ic mouse drag ty: " + n.getTranslateY() + ", ey: " + event.getY() );
			System.out.println("ic mouse drag tx: " + n.getTranslateX() + ", ex: " + event.getX() );
		}
		model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		model.setY(model.getY() + event.getY());
		view.setX(model.getX(),n);
		view.setY(model.getY(),n);
		event.setDragDetect(false);
	}
	
	/**
	 * Controls the release event. When drag starts in tilepane adds a new imageView of the same plant to the center pane 
	 * @param event the mouseevent
	 * @param name the name or key of the plant being released
	 * @param startingInTile boolean to inform if drag started in tile pane
	 * @author Arunima Dey
	 */
	public void release(MouseEvent event, String name, Boolean startingInTile) {
		System.out.println("released");
		Node n = (Node)event.getSource();
		n.setMouseTransparent(false);
		if(startingInTile) {
			view.setX(0,n);
			view.setY(0, n);
//			view.setX(n.getLayoutX(),n);
//			view.setY(n.getLayoutY(),n);
			view.addImageView(event.getSceneX(),event.getSceneY(), name);
		}
		
		if(startingInTile) {
			model.placePlant(model.getX(), model.getY(), name);
			view.updateBudgetandLep(model.getBudget(), model.getLepCount());
		}
	}
	
	public void entered(MouseDragEvent event, String key) {
		System.out.println(key);
		view.removePlant((Node) event.getGestureSource());
		model.removePlant(getStartingX(), getStartingY(), key);
		view.updateBudgetandLep(model.getBudget(), model.getLepCount());
		
	}
	
	/** 
	 * Called when user is drawing. 
	 * Updates the canvas of the relevant view and calls updateOutlineSection in model to pass boundary coordinates
	 * @param MouseEvent
	 * @param Boolean describing whether mouse is pressed for the first time
	 * @author Ishika Govil 
	 */
	public void draw(MouseEvent event, boolean isPressed) { // (changeCursor called with false) -- in beta
		if(isPressed)
			 this.view.getGC().beginPath();
		 this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		 this.view.getGC().stroke();
		 this.model.updateOutlineSection(event.getSceneX(), event.getSceneY());
	}
	
	public void anchoring(MouseEvent event, Anchor anchor, boolean dragAnchor, DoubleProperty x, DoubleProperty y, Polygon poly, int idx) {
		if(dragAnchor) {
    		anchor.setCenterX(event.getX());           
    		anchor.setCenterY(event.getY());    
    		poly.getPoints().set(idx, x.get());
    		poly.getPoints().set(idx + 1, y.get());
    	}
	}
	public void restartPolygonBoundary() {
		this.model.getGarden().polygonCorners = new ArrayList<double[]>();
	}
	
	public void enterPolygonBoundary(Polygon poly) {
		for(int i = 0; i < poly.getPoints().size(); i+= 2) {
			DoubleProperty x = new SimpleDoubleProperty(poly.getPoints().get(i));
            DoubleProperty y = new SimpleDoubleProperty(poly.getPoints().get(i + 1));
			this.model.getGarden().setPolygonCorners(x.get(), y.get());
		}
	}
	
	public void drawPlot() {
		ListIterator<double[]> itr = model.getGarden().outline.listIterator();
		iteratePlot(itr, model.getGarden().outline, false);
		itr = model.getGarden().polygonCorners.listIterator();
		iteratePlot(itr, model.getGarden().polygonCorners, true);
	}
	public void iteratePlot(ListIterator<double[]> itr, ArrayList<double[]> list, boolean isPolygon) {
		while(itr.hasNext()) {
			double[] point1 = (double[])itr.next();
			double[] point2;
			if(itr.hasNext())
				point2 = list.get(itr.nextIndex());
			else if(isPolygon)
				point2 = list.get(0);
			else 
				return;
			if(isPolygon) {
				this.view.drawLine(point1[0], point1[1], point2[0], point2[1], isPolygon);
			}
			if(!isPolygon && Math.abs(point1[0] - point2[0]) < 35 && Math.abs(point1[1] - point2[1]) < 35)
				this.view.drawLine(point1[0], point1[1], point2[0], point2[1], isPolygon);
		}
	}
	/** 
	 * Called when user is drawing. 
	 * Updates the canvas of the relevant view and calculates the number of pixels from the starting and ending point of line
	 * @param MouseEvent
	 * @param Boolean describing whether mouse is pressed for the first time
	 * @author Ishika Govil 
	 */
	public void settingDimensionLine(MouseEvent event, boolean isPressed) { // (changeCursor called with false) -- in beta
		if(isPressed)
			view.getGC().beginPath();
		 this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		 this.view.getGC().stroke();
		
		//Get pixel information
		double[] arr = {event.getSceneX(),event.getSceneY()};
		this.view.dimLen.add(arr);
		this.view.dimPixel = Math.sqrt(Math.pow((view.dimLen.get(view.dimLen.size()-1)[1] - view.dimLen.get(0)[1] ),2) + Math.pow((view.dimLen.get(view.dimLen.size()-1)[0]  - view.dimLen.get(0)[0] ),2) );
	}
	
	/** 
	 * Called after user hits enter after inputting the dimension of their line, in feet.
	 * Sets the length per pixel in model by dividing user inputted value by the number of pixels in the line
	 * @param double representing the user inputted line dimension
	 * @author Ishika Govil 
	 */
	public void settingLength(double length) {
		 this.model.setLengthPerPixel(length/view.dimPixel);

	}
	
	public Map<String, Lep> getLepInfo() {
		return this.model.getLepDirectory();
	}
	
	/** 
	 * Called when a button is pressed in order to determine the next screen
	 * The next action is determined by the string passed to the function
	 * @param String describing the next action to be shown
	 * @author Ishika Govil 
	 */
	public void switchViews(String next) {
		 //Clears the canvas the user was drawing on. Also clears the ArrayList corresponding to the coordinates of the plot boundary
		 if(next.equals("Clear")) {
			 this.view.getGC().clearRect(0, 0,this.view.getScreenWidth(), this.view.getScreenHeight());
			 this.model.getGarden().clearOutline();
		 }
		 //Clears only the lines drawn after setting dimension. Also clears the ArrayList corresponding to the coordinates of the line
		 else if(next.equals("ClearDim")) {
			 this.view.getGC().drawImage(this.view.img, 0, 0);  
			 this.view.dimLen = new ArrayList<>();
		 } 
		 else if(next.equals("Restart")) {
			 this.model.getGarden().outline = new ArrayList<double[]>(); 
			 this.view.restartPlot();
			 setTheStage();
		 }
		 else {
			 this.view.switchViews(next);
			 setTheStage();
		 }
	}
	
	/**
	 * Gets the initial budget set by user to be displayed in garden design screen
	 * @return the budget
	 */
	public int getBudget() {
		return model.getBudget();
	}

	private void fillRegion(MouseEvent e) {
		int x = (int) e.getSceneX();
		int y = (int) e.getSceneY();
		
		Color fillColor = this.model.getCurrentConditions().toColor();
		
		this.view.fillRegion(x, y, fillColor);
		this.view.redrawImage();
	}
	
	//Methods used when user is designing new plot and inputting conditions
	public void onSectioning() {} //Called in drag(), model calls updateOutlineSection and view is updated 
	public void displayConditionsOptions() {} //Called in release() to update the view with conditions (changeCursor called with false)
	
	//Methods used when user is designing their garden
	public void displayValidPlantLocation() {} ///Called in drag(), if it is not validated in Model, it tells the view and colors plant red. Otherwise plant is green
	public void onPlantRelease() {} //placePlant() is called in model and updated cost/leps are returned. view is created to show that plant and updates Basket		
	
	//Methods that provide other feedback when buttons are pressed
	public void downloadGarden() {} //Called in getHandlerforClicked if download is pressed. gets info from model, puts it in a pdf, downloads it to computer
	public void toolClicked(MouseEvent event, Image img) {} //Called in getHandlerforClicked if a tool is clicked. changes cursor to image
	public void getCompostInfo() {} //Called in getHandlerforClicked if compost bin clicked, which gets info about deleted plants and sends to View

	//Helpers
	public ArrayList<float[]> getBoundaries() {return null;} //Gets boundaries of garden and sends to View when rendering the ConditionScreen
	public void getRecommendedPlants() {} //when designGarden is called, this method is also called to initialize the optimal garden (calls createDefault in model)
	public void loadGarden() {} // takes garden information stored in Model and renders GardenDesign 

	
	public double getStartingX() {return model.getX();}
	public double getStartingY() {return model.getY();}

}
