import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	public ArrayList<Button> pageSwitch; //back, next, done, clear
	public Button freehand;
	public TextField search;

	public PlotDesign(Stage stage, Controller c) {
		super(stage, c);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		//Set canvas for drawing
		BorderPane border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);

        //Add editing button and functionality
        freehand = new Button("Freehand");
        freehand.setOnMouseEntered(controller.getHandlerforMouseEntered());
        freehand.setOnMouseExited(controller.getHandlerforMouseExited());
        freehand.setOnAction(controller.getHandlerforClicked("Drawing"));
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(freehand);
              
        
        //Adding page buttons 
        pageSwitch = new ArrayList<Button>();
        pageSwitch.add(addNextButton(screenWidth/2 -175, screenHeight/4*3, "Back", "Start"));
        pageSwitch.add(addNextButton(screenWidth/2-85,screenHeight/4*3, "Clear", "PlotDesign"));
        pageSwitch.add(addNextButton(screenWidth/2, screenHeight/4*3, "Done", "GardenDesign"));
        
        border.getChildren().add(toolbar);
        border.getChildren().addAll(pageSwitch);
	}
	public void onDrawing() {
		border.setOnMousePressed(controller.getHandlerforDrawing(true));
        border.setOnMouseDragged(controller.getHandlerforDrawing(false));
	}
}