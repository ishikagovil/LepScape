import javafx.event.EventHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;
import javafx.application.Application;

/**
 * @author Ishika Govil, Arunima Dey, Dea Harjianto, Jinay Jain, Kimmy Huynh
 */
public class Controller extends Application {
	private final boolean DEBUG = true;
	ManageViews view;
	// reading plant information
	String plantFile = "src/main/resources/finalPlantListWithInfo.csv";
	// reading lep information
	String lepFile = "src/main/resources/finalLepList.csv";
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
		System.out.println("setting plant directory");
		this.model.setPlantDirectory(CSVtoPlants.readFile(plantFile));
		System.out.println("setting lep directory");
		this.model.setLepDirectory(CSVtoLeps.readFile(lepFile));
		this.stage = stage;
	    view = new ManageViews(stage,this, plantFile, lepFile);
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
		this.stage.setFullScreen(true);
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
	/** 
	 * Calls anchoring when the anchor is dragged on the polygon
	 * @param EventHandler<MouseEvent>
	 * @param Anchor
	 * @param Polygon
	 * @param boolean dragAnchor describing whether anchor is draggable
	 * @param DoubleProperty x describing x position
	 * @param DoubleProperty y describing y position
	 * @param int index of anchor
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
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
	 * Calls drawBreak when the user lifts the mouse when freedrawing plot
	 * @param EventHandler<MouseEvent>
	 */
	public EventHandler<MouseEvent> getHandlerforDrawBreak() {
		return (e) -> {  drawBreak(); };
	}
	
	/** 
	 * Calls drag when mouse is released
	 * @param String key
	 * @param boolean startingInTile
	 * @return EventHandler<MouseEvent>
	 */
	public EventHandler<MouseEvent> getHandlerforReleased(String key, Boolean startingInTile) {
		return (e) -> { release(e,key,startingInTile);  };
	}
	
	/**
	 * Calls entered when mouse is entered
	 * @param String key
	 * @return EventHandler<MouseDragEvent>
	 */
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
	public EventHandler<MouseEvent> getConditionsClickHandler(Canvas canvas) {
		return (e) -> {
			UserMode mode = this.model.getMode();
			if(mode == UserMode.SETTING_CONDITIONS) {
				fillRegion(canvas, e);
			} else if(mode == UserMode.PARTITIONING) {
				draw(e, true);
			}
		};
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
			PlantSpecies plant = model.plantDirectory.get(name);
			double heightWidth = scalePlantSpread(plant);
			((GardenDesign)view.views.get("GardenDesign")).addImageView(event.getSceneX(),event.getSceneY(), name,heightWidth);
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
	 * The spread of a plant is calculated using the lengthPerPixel and spread of the plant
	 * @param PlantSpecies plant
	 * @return double representing the number of pixels of the radius of the plant
	 */
	public double scalePlantSpread(PlantSpecies plant) {
		double numPixels = plant.getSpreadRadius() / this.model.lengthPerPixel;
		return numPixels;
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
		 this.model.getGarden().updateOutline(event.getSceneX(), event.getSceneY());
		 this.view.validateSave();
	}
	/**
	 * When user lifts their mouse upon drawing the plot boundary, it adds a (-1, -1) point to the outline 
	 */
	public void drawBreak() {
		this.model.getGarden().updateOutline(-1,-1);
	}
	
	/** Sets the new coordinates of the anchor of the relevant anchor of the polygon after user drags it
	 * @param EventHandler<MouseEvent>
	 * @param Anchor
	 * @param Polygon
	 * @param boolean dragAnchor describing whether anchor is draggable
	 * @param DoubleProperty x describing x position
	 * @param DoubleProperty y describing y position
	 * @param int index of anchor
	 * @author Ishika Govil 
	 */
	public void anchoring(MouseEvent event, Anchor anchor, boolean dragAnchor, DoubleProperty x, DoubleProperty y, Polygon poly, int idx) {
		if(dragAnchor) {
    		anchor.setCenterX(event.getX());           
    		anchor.setCenterY(event.getY());    
    		poly.getPoints().set(idx, x.get());
    		poly.getPoints().set(idx + 1, y.get());
    	}
	}
	
	/**
	 * When user hits clear on the polygon boundary, polygonCorners ArrayList in the Garden is reset
	 */
	public void restartPolygonBoundary() {
		this.model.getGarden().polygonCorners = new ArrayList<double[]>();
	}
	
	/**
	 * When the user saves the boundary, if they have drawn a polygon, it saves the corners of the polygon
	 * @param Polygon drawn by user
	 * @author Ishika Govil
	 */
	public void enterPolygonBoundary(Polygon poly) {
		for(int i = 0; i < poly.getPoints().size(); i+= 2) {
			DoubleProperty x = new SimpleDoubleProperty(poly.getPoints().get(i));
            DoubleProperty y = new SimpleDoubleProperty(poly.getPoints().get(i + 1));
			this.model.getGarden().setPolygonCorners(x.get(), y.get());
		}
	}
	
	/**
	 * Iterates over the boundary ArrayLists in Garden to draw plot to screen.
	 * Calls iteratePlot() to translate and scale the plot
	 * @param double scale 
	 * @author Ishika Govil
	 */
	public void drawPlot(double scale) {
		ListIterator<double[]> itr = model.getGarden().outline.listIterator();
		iteratePlot(itr, model.getGarden().outline, false, scale);
		itr = model.getGarden().polygonCorners.listIterator();
		iteratePlot(itr, model.getGarden().polygonCorners, true, scale);
	}
	
	/**
	 * Iterates over just the freehand portion outline ArrayList in Garden
	 * @param double scale
	 * @author Ishika Govil
	 */
	public void drawFreehandPart(double scale) {
		ListIterator<double[]> itr = model.getGarden().outline.listIterator();
		iteratePlot(itr, model.getGarden().outline, false, scale);
	}
	
	public void drawToCanvas(Canvas canvas) {
		ArrayList<double[]> extrema = this.model.getGarden().getExtremes();
		ArrayList<double[]> points = this.model.getGarden().getOutline();
		ArrayList<Conditions> conds = this.model.getGarden().getSections();
		points.addAll(this.model.getGarden().getPolygonCorners());
		
		View.drawOnCanvas(canvas, points, extrema, conds);
	}
	
	/**
	 * Scales and translates all the points onto the screen by calling drawLine in View
	 * @param Iterator itr
	 * @param ArrayList representing which plot boundary list, outline or polygonCorners
	 * @param boolean isPolygon representing if the boundary is a polygon
	 * @param double scale 
	 * @author Ishika Govil
	 */
	public void iteratePlot(ListIterator<double[]> itr, ArrayList<double[]> list, boolean isPolygon, double scale) {
		double[] translate;
		if(scale == 1)
			translate = new double[] {0, 0};
		else
			translate = this.model.translateScaledPlot(this.view.getGardenTopLeft());		
		while(itr.hasNext()) {
			double[] point1 = (double[])itr.next();
			double[] point2;
			if(itr.hasNext())
				point2 = list.get(itr.nextIndex());	
			else if(isPolygon)
				point2 = list.get(0);
			else 
				return;
			if(point2[0] != -1 && point1[0]!= -1)
				this.view.drawLine(point1[0]*scale + translate[0], point1[1]*scale + translate[1], point2[0]*scale + translate[0], point2[1]*scale + translate[1], isPolygon);
		}
	}
	
	/**
	 * Using the extreme x and y coordinates, the pixel lengths of the maximum minus minimum are determined.
	 * Then, these lengths are divided by desired lengths to determine scale.
	 * The minimum is used as the scale and drawPlot is called
	 * @author Ishika Govil
	 */
	public void scalePlot() {
		ArrayList<double[]> extrema = this.model.getGarden().getExtremes();
		double scaleY = this.view.getGardenHeight() / Math.abs(extrema.get(0)[1] -  extrema.get(2)[1]);
		double scaleX = this.view.getGardenWidth() / Math.abs(extrema.get(1)[0] -  extrema.get(3)[0]);
		double scale =  Math.min(scaleX, scaleY);
		this.model.setScale(scale);
		drawPlot(scale);
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
		this.view.dimPixel = this.model.calculateLineDistance(view.dimLen.get(view.dimLen.size()-1)[0], view.dimLen.get(0)[0], view.dimLen.get(view.dimLen.size()-1)[1], view.dimLen.get(0)[1]);
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
	
	public Map<String, PlantSpecies> getPlantInfo() {
		return this.model.getPlantInfo();
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
		 else if(next.equals("ConditionScreen")) {
			 if(this.model.lengthPerPixel!= -1 && this.view.dimPixel != -1) {
				 this.view.switchViews(next);
				 setTheStage();
			 }
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

	private void fillRegion(Canvas canvas, MouseEvent e) {
		ArrayList<double[]> extrema = this.model.getGarden().getExtremes();

		double minX = extrema.get(3)[0];
		double maxX = extrema.get(1)[0];
		double minY = extrema.get(0)[1];
		double maxY = extrema.get(2)[1];

		double scale = View.findScale(minX, maxX, minY, maxY, canvas.getWidth(), canvas.getHeight());
		double newX = (e.getX() / scale) + minX;
		double newY = (e.getY() / scale) + minY;

		Conditions curr = this.model.getCurrentConditions();
		Conditions conditions = new Conditions(curr.getSoilType(), curr.getMoistureLevel(), curr.getSunlight());
		
		conditions.setX(newX);
		conditions.setY(newY);
		
		this.model.getGarden().addSection(conditions);
		this.drawToCanvas(canvas);
	}
	
	
	public double getStartingX() {return model.getX();}
	public double getStartingY() {return model.getY();}

}
