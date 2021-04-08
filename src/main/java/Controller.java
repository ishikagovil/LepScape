import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import java.util.ArrayList;
import java.util.*;

import javafx.application.Application;

public class Controller extends Application {
	View view;
	Model model;
	Stage stage;
	Map<String,View> views;
	
	@Override
	public void start(Stage stage) throws Exception {}
	public static void main(String[] args) {
		launch(args);
	}
	//Page switching
	public EventHandler<ActionEvent> getHandlerforClicked(String next) { //Changes view and calls relevant methods in Model/View based on which page is created
		return (e) -> {  };
	}
		
	//Handlers 
	public EventHandler<MouseEvent> getHandlerforMouseEntered() { //Sets cursor to hand  (calls changeCursor with true)
		return (e) -> { };
	}
	public EventHandler<MouseEvent> getHandlerforMouseExited() { //Changes cursor back (calls changeCursor with false)
		return (e) -> {  };
	}
		public EventHandler<MouseEvent> getHandlerforDrag() {
			return (e) -> {  };
		}
		public EventHandler<MouseEvent> getHandlerforReleased() {
			return (e) -> {  };
		}
		public EventHandler<MouseEvent> getHandlerforDrawing(boolean isPressed) {
			return (e) -> {  };
		}
		public ChangeListener<Number> onSliderChanged(String sliderType) { //When user changes the conditions slider, this method which updates Model (based on which slider was changed)
			return null;
		}
		
		public void drag(MouseEvent event) {}
		public void release(MouseEvent event) {}
		public void draw(MouseEvent event, boolean isPressed) {}// (changeCursor called with false)
		
		public void switchViews(String next) {}
		
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