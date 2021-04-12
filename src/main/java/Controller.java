import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.*;
import javafx.application.Application;

public class Controller extends Application {
	private final boolean DEBUG = true;
	View view;
	Model model;
	Stage stage;
	Map<String,View> views;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.model = new Model();
		this.stage = stage;
		model.initializePlantDirectory();
		//Load hashmap
	    initializeViews();
	    //Initialize first screen
	    this.view = this.views.get("GardenDesign");
	    Scene scene = new Scene(view.getBorderPane(), view.getScreenWidth(), view.getScreenHeight());
	    this.stage.setScene(scene);
	    setTheStage();
	}
	public void initializeViews() {
		views = new HashMap<>();
		views.put("Start", new Start(stage, this));
	    views.put("Gallery", new Gallery(stage,this));  
	    views.put("PlotDesign", new PlotDesign(stage, this));
	    views.put("ConditionScreen", new ConditionScreen(stage,this));
	    views.put("Navigation", new Navigation(stage, this));	     
	    views.put("Summary", new Summary(stage,this));
	    views.put("GardenDesign", new GardenDesign(stage,this));
	    views.put("LearnMore", new LearnMore(stage, this));
	}
	public void setTheStage() {
		this.stage.getScene().setRoot(this.view.getBorderPane());
		this.stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	//Page switching
	public EventHandler<ActionEvent> getHandlerforClicked(String next) { //Changes view and calls relevant methods in Model/View based on which page is created
		return (e) -> {
			switchViews(next);
		};
	}
	
	//Handlers 
	public EventHandler<MouseEvent> getHandlerforMouseEntered() { //Sets cursor to hand  (calls changeCursor with true)
		return (e) -> {view.changeCursor(true);};
	}
	public EventHandler<MouseEvent> getHandlerforMouseExited() { //Changes cursor back (calls changeCursor with false)
		return (e) -> { view.changeCursor(false);  };
	}
	public EventHandler<MouseEvent> getHandlerforDrawing(boolean isPressed) {
		return (e) -> {  draw(e, isPressed); };
	}
	public ChangeListener<Number> onSliderChanged(String sliderType) { //When user changes the conditions slider, this method which updates Model (based on which slider was changed)
		return null;
	}
	
	public EventHandler<MouseEvent> getHandlerforPressed(String key){
		return (e) -> { pressed(e,key); };
	}
	
	public EventHandler<MouseEvent> getHandlerforDrag() {
		return (e) -> {  drag(e); };
	}
	
	public EventHandler<MouseEvent> getHandlerforReleased(String key, Boolean startingInTile) {
		return (e) -> { release(e,key,startingInTile);  };
	}
	
	public EventHandler<MouseEvent> getHandlerForDragReleasedOver(Boolean startedInTile){
		return event -> {draggedOver(event, startedInTile);};
	}
	
	public void draggedOver(MouseEvent event, Boolean startedInTile) {
		Node n = (Node) event.getSource();
		System.out.println("in thr draggedOver method");
		if(!startedInTile) {
			view.removePlant(n);
		}
	}
	
	public void pressed(MouseEvent event, String key) {
		Node n = (Node) event.getSource();
		n.setMouseTransparent(true);
		System.out.println("Clicked");
		String name = model.plantDirectory.get(key).getCommonName();
		String description = model.plantDirectory.get(key).getDescription();
		view.makeInfoPane(name,description);
		event.setDragDetect(true);
		
	}
	
	public void drag(MouseEvent event) {
		Node n = (Node)event.getSource();
//		if (DEBUG) System.out.println("ic mouse drag ty: " + n.getTranslateY() + ", ey: " + event.getY() );
		model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		model.setY(model.getY() + event.getY());
		view.setX(model.getX(),n);
		view.setY(model.getY(),n);
		event.setDragDetect(false);
	}
	
	//TODO: check if it has left the upperBound of the tilePane so then it can be placed
	//Also check if it has entered compared then do the compare. 
	//TODO: Add String param so a placedPlant can be created if in the garden if in compare then get plant info
	//TODO: Add String param for addImageView so view knows which image to use for making the ImageView
	public void release(MouseEvent event, String name, Boolean startingInTile) {
		System.out.println("released");
		Node n = (Node)event.getSource();
		n.setMouseTransparent(false);
		view.setX(n.getLayoutX(),n);
		view.setY(n.getLayoutY(),n);
		view.addImageView(model.getX(),model.getY());
		if(startingInTile) {
			model.placePlant(model.getX(), model.getY(), name);
		}
	}
	
	public void draw(MouseEvent event, boolean isPressed) { 
		if(isPressed)
			view.gc.beginPath();
			view.gc.lineTo(event.getSceneX(), event.getSceneY());
			view.gc.stroke();
			model.updateOutlineSection(event.getSceneX(), event.getSceneY());
	}
	
	public void switchViews(String next) {
		if(next.equals("Drawing"))
			((PlotDesign) this.view).onDrawing();
		else if(next.equals("Clear"))
			this.model.getGarden().clearOutline();
		else {
			this.view = this.views.get(next);
			setTheStage();
		}
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
