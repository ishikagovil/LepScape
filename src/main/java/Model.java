import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class Model extends Application {
	public int budget;
	public Garden gardenMap;
	public Map<String, PlantSpecies> plantDirectory;
	private float x;
	private float y;
	public PlantSpecies plant1;
	public PlantSpecies plant2;
	public int cost;
		
	// Methods for the user to draw the garden and put in desired conditions
	// create a default optimized garden based on number of leps
	public void createDefault() {}
	// get the boundaries of the garden
	public void getBoundaries(Garden garden) {}
	// create a new condition for the garden
	public void createNewConditions(Conditions conditon) {
		// new condition for different areas of the garden
	}
	// update the outline whenever the user puts down another point to connect
	public void updateOutlineSection(Garden garden) {
		// not sure what should be the parameter
	}
	// update the outline to the condition that user wants
	public void updateConditions(Garden garden) {
		// update conditions using garden outline
	}
	// find dimension of the garden
	public void findDimensions() {}
	
	// Methods to use in order to place down plants
	//choose 2 plants to compare the number of leps they support
	public void comparePlant(PlantSpecies plant1, PlantSpecies plant2) {
		this.plant1 = plant1;
		this.plant2 = plant2;
		// display the 2 plant info here
	}
	// get the information about the plant to compare
	public int getPlantInfo(PlantSpecies plant) {
		return plant.getLepsSupported();
	}
	// check if plant is okay to be placed by checking with spreadRadius
	public boolean validatePlacement(PlantSpecies plant) {
		return true;
	}
	// place down plants
	public boolean placePlant(PlantSpecies plant) {
		return true;
	}
	// update the cost every time a plant is placed
	public int costUpdate() {
		return cost;
	}
	
	@Override
	public void start(Stage primaryStage) {
		
	}
	public float getX() {
		return this.x;
	}
	public float getY() {
		return this.y;
	}
}