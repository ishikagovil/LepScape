import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	public ArrayList<Button> drawSwitch; 
	public ArrayList<Button> dimSwitch;
	public Button freehand;
	public FlowPane flow;
	public Label label;
	public HBox box;
	public WritableImage img;

	public PlotDesign(Stage stage, Controller c) {
		super(stage, c);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		//Set canvas for drawing
		border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //Add editing button and functionality
        freehand = new Button("Freehand");
        setOnMouse(freehand);
        freehand.setOnAction(controller.getHandlerforClicked("Drawing"));
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(freehand);
        
        //Adding page buttons  (buttons to switch after drawing and buttons to switch after dimensions)
        drawSwitch = new ArrayList<Button>();
        dimSwitch = new ArrayList<Button>();
        
        //Adding Back buttons
        drawSwitch.add(addNextButton("Back", "Start"));
        disableDrawing(0, drawSwitch);
        dimSwitch.add(new Button("Back"));
        setOnMouse(dimSwitch.get(0));
        disableDrawing(0, dimSwitch);
        dimSwitch.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	//gc.restore();
            	gc.drawImage(img,0,0);
            	border.getChildren().remove(label);
            	createHBox(drawSwitch);	
            }
        });
        
        //Adding Clear buttons
        Button clear = new Button("Clear");
        setOnMouse(clear);
        clear.setOnAction(controller.getHandlerforClicked("Clear"));
        clear.addEventHandler(ActionEvent.ACTION, (e)-> {
            gc.clearRect(0, 0,screenWidth, screenHeight);
        });
        drawSwitch.add(clear);
        
        Button undo = new Button("Undo");
        setOnMouse(undo);
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	gc.drawImage(img, 0, 0);
            }
        });
        dimSwitch.add(undo);
        
        //Adding Done buttons
        drawSwitch.add(new Button("Save"));
        setOnMouse(drawSwitch.get(2));
        drawSwitch.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	//gc.save();
            	img = canvas.snapshot(null, null);
            	onSettingDimensions();
            }
        });
        dimSwitch.add(addNextButton("Done", "ConditionScreen"));
        disableDrawing(2, dimSwitch);
        
        //Adding to border
        createHBox(drawSwitch);	
        border.setTop(toolbar);
	}
	public void onDrawing() {
		border.setOnMousePressed(controller.getHandlerforDrawing(true));
        border.setOnMouseDragged(controller.getHandlerforDrawing(false));
	}
	public void onSettingDimensions() {
		createHBox(dimSwitch);
		label = new Label(" Setting Dimensions! \n Draw a line from any two points in your plot and input its dimension");
	    label.setStyle("-fx-font: 18 arial;");
	    border.setLeft(label);
	    //Input value box
	}
	public void createHBox(ArrayList<Button> list) {
		if(border.getChildren().contains(box))
			border.getChildren().remove(box);
		box = new HBox();
		box.setSpacing(10);
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().addAll(list);
		border.setBottom(box);
	}
	public void disableDrawing(int index, ArrayList<Button> list) {
		list.get(index).addEventHandler(ActionEvent.ACTION, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
        });
	}
}