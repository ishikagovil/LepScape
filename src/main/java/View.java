import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.*;

public abstract class View{
	public Map<String, Image> plantImages;
	public int screenWidth = 1380;
	public int screenHeight = 940;
	public BorderPane border;
	Stage stage;
	Controller controller;
	GraphicsContext gc;
	

	public View(Stage stage, Controller c) { 
		this.stage = stage;
        controller = c;
		this.stage.setTitle("Lepscape");
        importImages();
	}	
	
	public BorderPane getBorderPane() {
		return border;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
	public void setX(double x, Node n) {
		n.setTranslateX(n.getLayoutX() + x);
	};
	public void setY(double y, Node n) {
		n.setTranslateY(n.getLayoutY() + y);
	};
	
	public void importImages() {}
	public void changeCursor(boolean hand) { //Changes cursor to either a hand if true is passed, or pointer if false
		if(hand)
			stage.getScene().setCursor(Cursor.HAND);
		else
			stage.getScene().setCursor(Cursor.DEFAULT);
	} 
	public Button addNextButton(double transX, double transY, String text, String next) {
		Button b = new Button(text);
		b.setTranslateX(transX);
		b.setTranslateY(transY);
		b.setOnMouseEntered(controller.getHandlerforMouseEntered());
		b.setOnMouseExited(controller.getHandlerforMouseExited());
		b.setOnAction(controller.getHandlerforClicked(next));
		return b;
	}
	
}