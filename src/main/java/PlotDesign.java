import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	ArrayList<Button> drawSwitch; 
	ArrayList<Button> dimSwitch;
	Label label; //instructions to user
	HBox box;
	WritableImage img; 
	GridPane grid; //added to contain the TextField
	Polygon poly;
    ObservableList<Anchor> anchors = FXCollections.observableArrayList();
    boolean shapeClicked = false;
    boolean dragAnchor = false;
	/**
	 * @author Ishika Govil 
	 */
	
	/**
	 * Adds the drawing functionality with the Freedraw button
	 * Creates the screen flow by adding clear, back, save, done buttons
	 * @param Stage
	 * @param Controller
	 * @param ManageViews
	 */
	public PlotDesign(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		//Set canvas for drawing: https://www.youtube.com/watch?v=gjZQB6BmyK4
		border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
        gc.setLineWidth(2);
        //Creating buttons on the screen
        toolbarButtons();        
        backButtons();
        clearButtons();   
        saveButtons();
        //Adding first set of buttons to HBox
        createHBox(drawSwitch);	
	}
	public void toolbarButtons() {
		//Add editing button and functionality
        ToolBar toolbar = new ToolBar();
        toolbar.getItems().add(addNextButton("Freehand","Drawing"));
        Button shape = addNextButton("Polygon","Shape");
        toolbar.getItems().add(shape);
        disableDrawing(shape);
        //Adding page buttons  (buttons to switch after drawing and buttons to switch after dimensions)
        drawSwitch = new ArrayList<Button>();
        dimSwitch = new ArrayList<Button>();
        border.setTop(toolbar);
	}
	public void backButtons() {
		 //Adding Back buttons
        drawSwitch.add(addNextButton("Back", "Start"));
        disableDrawing(drawSwitch.get(0));
        dimSwitch.add(new Button("Back"));
        dimSwitch.get(0).setPrefSize(buttonWidth, buttonHeight);
        setOnMouse(dimSwitch.get(0));
        disableDrawing(dimSwitch.get(0));
        dimSwitch.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	dragAnchor = true;
            	for(Anchor a: anchors) {
            		a.setDragAnchor(dragAnchor);
            	}
            	//If there is no polygon in the borderpane right now, then set shapeClicked to false
            	if(!border.getChildren().contains(poly))          
            		shapeClicked = false;
            	gc.drawImage(img,0,0);
            	border.getChildren().remove(label);
            	border.getChildren().remove(grid);
            	createHBox(drawSwitch);	
            	//Remove the arraylist of points from model by calling method in controller
            	controller.restartPolygonBoundary();
            }
        });
	}
	public void clearButtons() {
        //Adding Clear/Undo buttons
        Button clear = addNextButton("Clear", "Clear");
        clear.addEventHandler(ActionEvent.ACTION, (e)-> {
        	shapeClicked = false;
       		border.getChildren().removeAll(anchors);       
       		for(Object child: border.getChildren()) {
       			if(child instanceof Polygon) {
       				border.getChildren().remove(child);
       				break;
       			}
           	}     		
        });
        drawSwitch.add(clear);
        Button undo = addNextButton("Undo", "ClearDim");
        dimSwitch.add(undo);
        dimSwitch.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	gc.drawImage(img,0,0);
            	onSettingDimensions();
            }
        });
        
	}
	public void saveButtons() {
		//Adding Save button
        drawSwitch.add(new Button("Save"));
        drawSwitch.get(2).setPrefSize(buttonWidth, buttonHeight);
        setOnMouse(drawSwitch.get(2));
        drawSwitch.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	//https://stackoverflow.com/questions/47741406/snapshot-save-canvas-in-model-view-controller-setup
            	img = gc.getCanvas().snapshot(null, null);
            	manageView.setImage(img);
            	dragAnchor = false;
            	for(Anchor a: anchors) {
            		a.setDragAnchor(dragAnchor);
            	}
            	shapeClicked = true;
            	onSettingDimensions();
            	//set the outline of the shape in model
            	controller.enterPolygonBoundary(poly);
            }
        });
        dimSwitch.add(addNextButton("Next", "ConditionScreen"));
	}
	
	/**
	 * Called when user hits "save" on their plot. Allows users to select any linear length and input its dimension.
	 * Saves the inputed value and calls settingLength in controller to calculate length per pixel
	 */
	public void onSettingDimensions() {
		border.setOnMousePressed(controller.getHandlerforSettingDimension(true));
        border.setOnMouseDragged(controller.getHandlerforSettingDimension(false));
        border.setOnMouseReleased(event -> {
        	border.setOnMousePressed(null);
		    border.setOnMouseDragged(null);
		});	
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
	
	/**
	 * Creates an HBox using the ArrayList of Buttons provided
	 * Allows for transition of buttons between two screens (plotting and setting dimensions)
	 * @param ArrayList<Button> representing which buttons to add to the HBox
	 */
	public void createHBox(ArrayList<Button> list) {
		if(border.getChildren().contains(box))
			border.getChildren().remove(box);
		box = new HBox();
		box.setSpacing(10);
		box.setPadding(new Insets(20));
		box.setAlignment(Pos.TOP_CENTER);
		box.getChildren().addAll(list);
		border.setBottom(box);
	}
	
	/**
	 * Calls handlers in controller when user is drawing
	 */
	public void onDrawing() {
		border.setOnMousePressed(controller.getHandlerforDrawing(true));
        border.setOnMouseDragged(controller.getHandlerforDrawing(false));
	}
	
	/**
	 * Disables the drawing handlers when user presses the Button passed
	 * @param Button that disables drawing when pressed
	 */
	public void disableDrawing(Button b) {
		b.addEventHandler(ActionEvent.ACTION, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
        });
	}
	
	//Polygon code adapted from: https://gist.github.com/jpt1122/dc3f1b76f152200718a8
	public void onShape() {
		//Initializes the points clockwise starting from top left corner of box
		double[] x = new double[]{screenWidth/2-100, screenWidth/2+100, screenWidth/2+100, screenWidth/2-100};
	    double[] y = new double[]{screenHeight/2-100, screenHeight/2-100, screenHeight/2+100, screenHeight/2+100};  
		List<Double> values = new ArrayList<Double>();
		anchors = FXCollections.observableArrayList();
		poly = new Polygon();
        for(int i = 0; i < x.length; i++) {
        	values.add(x[i]);
            values.add(y[i]);
        }
        dragAnchor = true;
        poly.getPoints().addAll(values);
        poly.setStroke(Color.BLACK);
        poly.setStrokeWidth(2);
        poly.setStrokeLineCap(StrokeLineCap.ROUND);
        poly.setFill(Color.TRANSPARENT);
        border.getChildren().add(poly);
        
        //Create the anchors
        for (int i = 0; i < poly.getPoints().size(); i += 2) {
        	final int idx = i;
            DoubleProperty xProperty = new SimpleDoubleProperty(poly.getPoints().get(i));
            DoubleProperty yProperty = new SimpleDoubleProperty(poly.getPoints().get(i + 1));
            anchors.add(new Anchor(Color.PINK, xProperty, yProperty, poly, idx, dragAnchor, controller));
        }
        border.getChildren().addAll(anchors);
        
	}
}
