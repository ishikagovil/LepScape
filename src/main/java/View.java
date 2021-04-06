import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.*;

public class View{
	public Map<String, Image> plantImages;
	public int screenWidth = 1380;
	public int screenHeight = 940;
	GraphicsContext gc;
	Controller controller;
	
	public View(Stage stage, Group root, Controller c) { 
		root.getChildren().clear();
		stage.setTitle("Lepscape");
        importImages();
        controller = c;
	}	
	public void importImages() {}
	public Button addButton(double transX, double transY, String text, EClicked next) {
		Button b = new Button();
		b.setText(text);
		b.setTranslateX(transX);
		b.setTranslateY(transY);
		b.setOnMouseEntered(controller.getHandlerforMouseEntered());
		b.setOnMouseExited(controller.getHandlerforMouseExited());
		b.setOnAction(controller.getHandlerforClicked(next));
		return b;
	}
}