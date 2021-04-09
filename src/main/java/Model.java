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
	public Map<String, Lep> lepDirectory;
		
	// Methods for the user to draw the garden and put in desired conditions
	// create a default shape of garden
	public void createDefault() {}
	// get the boundaries of the garden
	public void getBoundaries() {}
	// create a new condition for the garden
	public void createNewConditions() {}
	// update the outline whenever the user puts down another point to connect
	public void updateOutlineSection() {}
	// update the outline to the condition that user wants
	public void updateConditions() {}
	// find dimension of the garden
	public void findDimensions() {}
	
	// Methods to use in order to place down plants
	//choose 2 plants to compare the number of leps they support
	public void comparePlant() {}
	// get plant's sphere
	public void getPlantInfo() {}
	// check if plant is okay to be placed
	public void validatePlacement() {}
	// place down plants
	public void placePlant() {}
	// update the cost every time a plant is placed
	public void costUpdate() {}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}
}