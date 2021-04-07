import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<Button> buttons;
	
	public Start(Stage stage, Controller c) {
		super(stage, c);        
		
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		border.getChildren().add(canvas);
        border.setStyle("-fx-background-color: LAVENDER");

        //Populate button array
		buttons = new ArrayList<Button>();
		buttons.add(addNextButton(screenWidth/2-150, screenHeight/2, "New Garden", "PlotDesign"));
		buttons.add(addNextButton(screenWidth/2-50, screenHeight/2, "Gallery", "Gallery"));
		border.getChildren().addAll(buttons);   
	}
}