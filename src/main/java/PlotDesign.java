import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
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
		border = new BorderPane();
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
        HBox box = new HBox();
        box.setSpacing(10);
        pageSwitch = new ArrayList<Button>();
        pageSwitch.add(addButton("Back", "Start"));
        
        //Adding Clear button
        Button clear = new Button("Clear");
        clear.setOnMouseEntered(controller.getHandlerforMouseEntered());
        clear.setOnMouseExited(controller.getHandlerforMouseExited());
        clear.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
            	gc.clearRect(0, 0,screenWidth, screenHeight);
            }
        });
        pageSwitch.add(clear);
        
        //Adding Done button
        
        pageSwitch.add(addButton("Done", "GardenDesign"));
        
        border.setTop(toolbar);
        box.getChildren().addAll(pageSwitch);
        box.setAlignment(Pos.TOP_CENTER);
        border.setBottom(box);
	}
	public void onDrawing() {
		border.setOnMousePressed(controller.getHandlerforDrawing(true));
        border.setOnMouseDragged(controller.getHandlerforDrawing(false));
	}
	public Button addButton(String text, String next) {
		Button b = new Button(text);
        b.setOnMouseEntered(controller.getHandlerforMouseEntered());
        b.setOnMouseExited(controller.getHandlerforMouseExited());
        b.setOnAction(controller.getHandlerforClicked(next));
        b.addEventHandler(ActionEvent.ACTION, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
        });
        return b;
	}
}