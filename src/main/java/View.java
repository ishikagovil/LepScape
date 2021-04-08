import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.*;

public class View{
	public Map<String, Image> plantImages;
	public int screenWidth = 1380;
	public int screenHeight = 940;
	public BorderPane border;
	Stage stage;
	Controller controller;
	GraphicsContext gc;
	
	public View(Stage stage, Controller c) {}
	
	public void importImages() {}
	public BorderPane getBorderPane() { 
		return null;
	}
	public Button addButton(double transX, double transY, String text, String next) {
		return null;
	}
	public void changeCursor(boolean hand) {} //Changes cursor to either a hand if true is passed, or pointer if false
}