import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
	GridPane grid; //added to contain the TextField
	ToolBar toolbar;
	Polygon poly;
    boolean shapeClicked = false;
    boolean dragAnchor = false;
    ObservableList<Anchor> anchors;
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
        poly = new Polygon();
        anchors = FXCollections.observableArrayList();
        //Adding first set of buttons to HBox
        createHBox(drawSwitch);	
	}
	public void toolbarButtons() {
		//Add editing button and functionality
        toolbar = new ToolBar();
        toolbar.getItems().add(addNextButton("Freehand","Drawing"));
        //Make sure the anchors cannot be dragged when freehand is selected
        toolbar.getItems().get(0).addEventHandler(ActionEvent.ACTION, (e)-> {
        	dragAnchor = false;    	
        	toggleAnchorHandler();
        });
        toolbar.getItems().add(addNextButton("Polygon","Shape"));
        disableDrawing(toolbar.getItems().get(1));
        //Adding page buttons  (buttons to switch after drawing and buttons to switch after dimensions)
        drawSwitch = new ArrayList<Button>();
        dimSwitch = new ArrayList<Button>();
        border.setTop(toolbar);
	}
	public void backButtons() {
		//Adding Back buttons
        drawSwitch.add(addNextButton("Back", "Start"));
        disableDrawing(drawSwitch.get(0));
        
        //Adding second Back button on dimensions screen
        dimSwitch.add(new Button("Back"));
        dimSwitch.get(0).setPrefSize(buttonWidth, buttonHeight);
        setOnMouse(dimSwitch.get(0));
        disableDrawing(dimSwitch.get(0));
        dimSwitch.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	//Remove the lines on the current screen and polygon points
            	removeLines();
            	controller.restartPolygonBoundary();
            	gc.clearRect(0,0, screenWidth, screenHeight);
            	border.setOnMouseReleased(null);
            	
            	//Enable toolbar buttons
            	toolbar.getItems().get(0).setDisable(false);
            	toolbar.getItems().get(1).setDisable(false);
            	dragAnchor = true;
            	toggleAnchorHandler();
            	
            	//Add back the polygon and freehand plot
            	border.getChildren().add(poly);
            	border.getChildren().addAll(anchors);
            	shapeClicked = poly.getPoints().size() != 0;
            	controller.drawFreehandPart(1);
            	
            	//Change border design
            	border.getChildren().remove(label);
            	border.getChildren().remove(grid);
            	createHBox(drawSwitch);	
            	
            }
        });
	}
	public void clearButtons() {
        //Adding Clear button
        Button clear = addNextButton("Clear", "Clear");
        clear.addEventHandler(ActionEvent.ACTION, (e)-> {
        	shapeClicked = false;
       		border.getChildren().removeAll(anchors);             		
       		Iterator<Node> itr = border.getChildren().iterator();
        	while(itr.hasNext()) {
        		Object child = (Object)itr.next();
        		if(child instanceof Polygon) {
       				border.getChildren().remove(child);
       				break;
       			}
        	}  		
        	drawSwitch.get(2).setOnAction(null);
        	poly = new Polygon();
        	anchors = FXCollections.observableArrayList();
        	removeLines();
        });
        drawSwitch.add(clear);
        
        //Adding Undo button
        Button undo = addNextButton("Undo", "ClearDim");
        dimSwitch.add(undo);
        dimSwitch.get(1).addEventHandler(ActionEvent.ACTION, (event)-> {
            gc.clearRect(0,0, screenWidth, screenHeight);
           	onSettingDimensions(); 
        });
  
	}
	public void saveButtons() {
		//Adding Save button
        drawSwitch.add(new Button("Save"));
        drawSwitch.get(2).setPrefSize(buttonWidth, buttonHeight);
        setOnMouse(drawSwitch.get(2));
        
        //Adding Next button
        dimSwitch.add(new Button("Next"));
        dimSwitch.get(2).setPrefSize(buttonWidth, buttonHeight);
        setOnMouse(dimSwitch.get(2));
	}
	
	public void validateSave() {
		drawSwitch.get(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override 
            public void handle(ActionEvent e) {
            	toolbar.getItems().get(0).setDisable(true);
            	toolbar.getItems().get(1).setDisable(true);
            	
            	//set the outline of the polygon in model
            	if(border.getChildren().contains(poly)) 
            		controller.enterPolygonBoundary(poly);    
            	
            	//Clear everything on this screen
            	border.getChildren().remove(poly);
            	border.getChildren().removeAll(anchors);
            	gc.clearRect(0,0, screenWidth, screenHeight);
            	shapeClicked = true;
            	
            	//Start the next screen for dimensions 
            	onSettingDimensions();      	
            	removeLines();
            	controller.scalePlot();
            }
        });
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
	    dimSwitch.get(2).addEventHandler(ActionEvent.ACTION, (event)-> {
	    	String length = dimension.getText();
	    	try{
	    		controller.settingLength(Double.parseDouble(length));
	    	}
	    	catch(NumberFormatException e){
	    		//not a double
	   			dimension.clear();
	   		}         
	    	controller.switchViews("ConditionScreen");          
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
        border.setOnMouseReleased(controller.getHandlerforDrawBreak());
	}
	
	/**
	 * Disables the drawing handlers when user presses the Button passed
	 * @param Button that disables drawing when pressed
	 */
	public void disableDrawing(Node n) {
		n.addEventHandler(ActionEvent.ACTION, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
            border.setOnMouseReleased(null);
        });
	}
	
	//Polygon code adapted from: https://gist.github.com/jpt1122/dc3f1b76f152200718a8
	public void onShape() {
		//Initializes the points clockwise starting from top left corner of box
		double[] x = new double[]{screenWidth/2-100, screenWidth/2+100, screenWidth/2+100, screenWidth/2-100};
	    double[] y = new double[]{screenHeight/2-100, screenHeight/2-100, screenHeight/2+100, screenHeight/2+100};  
		List<Double> values = new ArrayList<Double>();
        for(int i = 0; i < x.length; i++) {
        	values.add(x[i]);
            values.add(y[i]);
        }
        dragAnchor = true;
        poly.getPoints().addAll(values);
        poly.setStroke(Color.BLACK);
        poly.setFill(Color.TRANSPARENT);
        poly.setStrokeWidth(2);
        border.getChildren().add(poly);
        
        //Create the anchors
		Iterator<Double> itr = poly.getPoints().iterator();
		int count = 0;
		while (itr.hasNext()) {
			double index = (Double)itr.next();
			DoubleProperty xProperty = new SimpleDoubleProperty(index);
			index = (Double)itr.next();
	        DoubleProperty yProperty = new SimpleDoubleProperty(index);
	        anchors.add(new Anchor(Color.PINK, xProperty, yProperty, poly, count, dragAnchor, this, controller));	
	        count+=2;
		}
        border.getChildren().addAll(anchors);
	}
	public void toggleAnchorHandler() {
		Iterator<Anchor> itr = anchors.iterator();
    	while(itr.hasNext()) {
    		Anchor a = (Anchor)itr.next();
    		a.setDragAnchor(dragAnchor);
    	}
	}
}
