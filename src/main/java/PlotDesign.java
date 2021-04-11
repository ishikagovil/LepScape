import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	ArrayList<Button> drawSwitch; 
	ArrayList<Button> dimSwitch;
	Label label;
	HBox box;
	WritableImage img;
	GridPane grid;


	public PlotDesign(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		//Set canvas for drawing
		border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        //Add editing button and functionality
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(addNextButton("Freehand","Drawing"));
        
        //Adding page buttons  (buttons to switch after drawing and buttons to switch after dimensions)
        drawSwitch = new ArrayList<Button>();
        dimSwitch = new ArrayList<Button>();
        
        //Adding Back buttons
        drawSwitch.add(addNextButton("Back", "Start"));
        disableDrawing(drawSwitch.get(0));
        dimSwitch.add(new Button("Back"));
        setOnMouse(dimSwitch.get(0));
        disableDrawing(dimSwitch.get(0));
        dimSwitch.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	gc.drawImage(img,0,0);
            	border.getChildren().remove(label);
            	border.getChildren().remove(grid);
            	createHBox(drawSwitch);	
            }
        });
        
        //Adding Clear buttons
        drawSwitch.add(addNextButton("Clear", "Clear"));
        Button undo = new Button("Undo");
        addNextButton("Undo", "ClearDim");
        dimSwitch.add(undo);
        dimSwitch.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	gc.drawImage(img,0,0);
            	onLength();
            }
        });
        //Adding Done buttons
        drawSwitch.add(new Button("Save"));
        setOnMouse(drawSwitch.get(2));
        drawSwitch.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	img = canvas.snapshot(null, null);
            	manageView.setImage(img);
            	onLength();
            	onSettingDimensions();
            }
        });
        
        //Adding to border
        createHBox(drawSwitch);	
        border.setTop(toolbar);
	}
	public void onSettingDimensions() {
		createHBox(dimSwitch);
		label = new Label(" Setting Dimensions! \n Draw a line from any two points in your plot and input its dimension");
	    label.setStyle("-fx-font: 18 arial;");
	    border.setLeft(label);
	    
	    //Input value box
	    TextField dimension = new TextField();
	    dimension.setPromptText("Enter dimension (ft)");
	    dimension.setOnKeyReleased(event -> {
	    	  if (event.getCode() == KeyCode.ENTER){
	            controller.settingLength(Double.parseDouble(dimension.getText()));
	            dimension.setPromptText("Enter dimension (ft)");
	            border.setOnMousePressed(null);
	            border.setOnMouseDragged(null);
	            controller.switchViews("ConditionScreen");
	          }
	    });	
	    grid = new GridPane();
	    grid.getChildren().add(dimension);
	    border.setRight(grid);
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
	public void onDrawing() {
		border.setOnMousePressed(controller.getHandlerforDrawing(true));
        border.setOnMouseDragged(controller.getHandlerforDrawing(false));
	}
	public void onLength() {
		border.setOnMousePressed(controller.getHandlerforSettingDimension(true));
        border.setOnMouseDragged(controller.getHandlerforSettingDimension(false));
	}
	public void disableDrawing(Button b) {
		b.addEventHandler(ActionEvent.ACTION, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
        });
	}
}