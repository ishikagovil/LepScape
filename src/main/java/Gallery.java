import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.*;

public class Gallery extends View{
	public ArrayList<Button> multiview;
	public Button back;
	public Button sort;
	public TextField search;

	public Gallery(Stage stage, Controller c) {
		super(stage, c);
//		Canvas canvas = new Canvas(screenWidth, screenHeight);
//        root.getChildren().add(canvas);
//        
//        
//        gc = canvas.getGraphicsContext2D();	
	}
}
