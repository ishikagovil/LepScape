import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.application.Application;

public class Controller extends Application {
	private final boolean DEBUG = true;
	ManageViews view;
	Model model;
	Stage stage;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.model = new Model();
		this.stage = stage;
<<<<<<< HEAD
	    view = new ManageViews(stage,this);
=======
		//Load hashmap
	    initializeViews();
	    //Initialize first screen
	    this.view = this.views.get("GardenDesign");
>>>>>>> feat: drag
	    Scene scene = new Scene(view.getBorderPane(), view.getScreenWidth(), view.getScreenHeight());
	    this.stage.setScene(scene);
	    setTheStage();
	}
<<<<<<< HEAD
	
=======
	public void initializeViews() {
		views = new HashMap<>();
		views.put("Start", new Start(stage, this));
	    views.put("Gallery", new Gallery(stage,this));  
	    views.put("PlotDesign", new PlotDesign(stage, this));
	    views.put("ConditionScreen", new ConditionScreen(stage,this));
	    views.put("Navigation", new Navigation(stage, this));	     
	    views.put("Summary", new Summary(stage,this));
	    views.put("GardenDesign", new GardenDesign(stage,this));
	}
>>>>>>> feat: drag
	public void setTheStage() {
		this.stage.getScene().setRoot(this.view.getBorderPane());
		this.stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
	//Action event handlers
	public EventHandler<ActionEvent> getHandlerforClicked(String next) { //Changes view and calls relevant methods in Model/View based on which page is created
		return (e) -> {
			switchViews(next);
		};
	}
	
	//Mouse event handlers 
	public EventHandler<MouseEvent> getHandlerforMouseEntered() { //Sets cursor to hand  (calls changeCursor with true)
		return (e) -> {view.onChangeCursor(true);};
	}
	public EventHandler<MouseEvent> getHandlerforMouseExited() { //Changes cursor back (calls changeCursor with false)
		return (e) -> { view.onChangeCursor(false);  };
	}
	public EventHandler<MouseEvent> getHandlerforDrag() {
		return (e) -> {  drag(e); };
	}
	public EventHandler<MouseEvent> getHandlerforReleased() {
		return (e) -> { release(e);  };
	}
	public EventHandler<MouseEvent> getHandlerforDrawing(boolean isPressed) {
		return (e) -> {  draw(e, isPressed); };
	}
	public EventHandler<MouseEvent> getHandlerforSettingDimension(boolean isPressed) {
		return (e) -> {  settingDimensionLine(e, isPressed); };
	}
	public ChangeListener<Number> onSliderChanged(String sliderType) { //When user changes the conditions slider, this method which updates Model (based on which slider was changed)
		return null;
	}
	
	public void pressed(MouseEvent event) {
		System.out.println("Clicked");
		
	}
	
	public void drag(MouseEvent event) {
		Node n = (Node)event.getSource();
		if (DEBUG) System.out.println("ic mouse drag ty: " + n.getTranslateY() + ", ey: " + event.getY() );
<<<<<<< HEAD
		
		//model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		//model.setY(model.getY() + event.getY());
		//view.setX( model.getX(),n);
		//view.setY( model.getY(),n);
=======
//		n.setTranslateX(n.getTranslateX() + event.getX());
//		n.setTranslateY(n.getTranslateY() + event.getY());
		model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		model.setY(model.getY() + event.getY());
		view.setX(model.getX(),n);
		view.setY(model.getY(),n);
>>>>>>> feat: drag
	}
	
	//TODO: check if it has left the upperBound of the tilePane so then it can be placed
	//Also check if it has entered compared then do the compare. 
	//TODO: Add String param so a placedPlant can be created if in the garden if in compare then get plant info
	//TODO: Add String param for addImageView so view knows which image to use for making the ImageView
	public void release(MouseEvent event) {
		System.out.println("released");
		Node n = (Node)event.getSource();
		view.setX(n.getLayoutX(),n);
		view.setY(n.getLayoutY(),n);
		view.addImageView(model.getX(),model.getY());
	}
<<<<<<< HEAD
	public void draw(MouseEvent event, boolean isPressed) { // (changeCursor called with false)
		if(isPressed)
			 this.view.getGC().beginPath();
		 this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		 this.view.getGC().stroke();
		 this.model.updateOutlineSection(event.getSceneX(), event.getSceneY());
	}
	public void settingDimensionLine(MouseEvent event, boolean isPressed) { // (changeCursor called with false)
		if(isPressed)
			view.getGC().beginPath();
		 this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		 this.view.getGC().stroke();
		
		//Get pixel information
		double[] arr = {event.getSceneX(),event.getSceneY()};
		 this.view.dimLen.add(arr);
		 this.view.dimPixel = Math.sqrt(Math.pow((view.dimLen.get(view.dimLen.size()-1)[1] - view.dimLen.get(0)[1] ),2) + Math.pow((view.dimLen.get(view.dimLen.size()-1)[0]  - view.dimLen.get(0)[0] ),2) );
	}
	public void settingLength(double length) {
		 this.model.setLengthPerPixel(length/view.dimPixel);
=======
	public void draw(MouseEvent event, boolean isPressed) { 
		if(isPressed)
			view.gc.beginPath();
			view.gc.lineTo(event.getSceneX(), event.getSceneY());
			view.gc.stroke();
			model.updateOutlineSection(event.getSceneX(), event.getSceneY());
>>>>>>> feat: drag
	}
	public void switchViews(String next) {
		 if(next.equals("Clear")) {
			 this.view.getGC().clearRect(0, 0,this.view.getScreenWidth(), this.view.getScreenHeight());
			 this.model.getGarden().outline = new ArrayList<double[]>(); 
		 }
		 else if(next.equals("ClearDim")) {
			 this.view.getGC().drawImage(this.view.img, 0, 0);  
			 this.view.dimLen = new ArrayList<>();
		 }
		 else {
			 this.view.switchViews(next);
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
