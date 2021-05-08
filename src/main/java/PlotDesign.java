import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	ArrayList<ImageView> drawSwitch; 
	ArrayList<ImageView> dimSwitch;
	ToolBar box;
	GridPane grid; //added to contain the TextField
	ToolBar toolbar;
	Polygon poly;
	ImageView plotInstructions;
	ImageView dimInstructions;
	Line dimLine;
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
		Canvas canvas = new Canvas(this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
		//Loading Images
		dimInstructions = new ImageView(new Image(getClass().getResourceAsStream("/dimensions.jpg")));
		dimInstructions.setFitWidth(this.manageView.getScreenWidth());
		dimInstructions.setFitHeight(this.manageView.getScreenHeight());
		plotInstructions = new ImageView(new Image(getClass().getResourceAsStream("/drawPlot.jpg")));
		plotInstructions.setFitWidth(this.manageView.getScreenWidth());
		plotInstructions.setFitHeight(this.manageView.getScreenHeight());
		//Set canvas for drawing: https://www.youtube.com/watch?v=gjZQB6BmyK4
		border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
        gc.setLineWidth(2);
        gc.drawImage(plotInstructions.getImage(), 0, 0);
        
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
	/**
	 * Creates the buttons Freehand and Polygon in the toolbar
	 */
	public void toolbarButtons() {
		//Add editing button and functionality
        toolbar = new ToolBar();
        toolbar.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        toolbar.getItems().add(addNextButton("draw","Drawing"));
        //Make sure the anchors cannot be dragged when freehand is selected
        toolbar.getItems().get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
        	dragAnchor = false;    	
        	toggleAnchorHandler();
        });
        toolbar.getItems().add(addNextButton("polygon","Shape"));
        disableDrawing(toolbar.getItems().get(1));
        //Adding page buttons  (buttons to switch after drawing and buttons to switch after dimensions)
        drawSwitch = new ArrayList<>();
        dimSwitch = new ArrayList<>();
        border.setTop(toolbar);
	}
	/**
	 * Creates the buttons Back in both dimSwitch and drawSwitch lists
	 */
	public void backButtons() {
		//Adding Back buttons
        drawSwitch.add(addNextButton("back", "Start"));
        disableDrawing(drawSwitch.get(0));
        
        //Adding second Back button on dimensions screen
        dimSwitch.add(new ImageView(this.manageView.buttonImages.get("back")));
        setOnMouse(dimSwitch.get(0), "back");
        disableDrawing(dimSwitch.get(0));
        dimSwitch.get(0).setOnMouseClicked((e) -> {
            //Remove the lines on the current screen and polygon points
           	removeLines();
           	controller.restartPolygonBoundary();
           	gc.clearRect(0,0, this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
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
           	controller.drawFreehandPart();
           	
           	//Removing the dimension line
           	if(dimLine != null) {
            	border.getChildren().remove(dimLine);
            	dimLine = null;
            }
           	
           	//Change border design
           	border.getChildren().remove(grid);
           	gc.drawImage(plotInstructions.getImage(), 0, 0);
           	createHBox(drawSwitch);	       
           	
           	e.consume();
        });
	}
	/**
	 * Creates the buttons Undo and Clear in dimSwitch and drawSwitch lists, respectively
	 */
	public void clearButtons() {
        //Adding Clear button
		ImageView clear = addNextButton("clear", "Clear");
        clear.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
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
        	drawSwitch.get(2).setOnMouseClicked(null);
        	poly = new Polygon();
        	anchors = FXCollections.observableArrayList();
        	removeLines();
        	gc.drawImage(plotInstructions.getImage(), 0, 0);
        	e.consume();
        });
        drawSwitch.add(clear);
        
        //Adding Undo button
        ImageView undo = addNextButton("undo", "ClearDim");
        dimSwitch.add(undo);
        dimSwitch.get(1).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
            gc.clearRect(0,0, this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
            if(dimLine != null) {
            	border.getChildren().remove(dimLine);
            	dimLine = null;
            }
           	onSettingDimensions(); 
        	e.consume();
        });
  
	}
	
	/**
	 * Creates the buttons Next and Save in dimSwitch and drawSwitch lists, respectively
	 */
	public void saveButtons() {
		//Adding Save button
        drawSwitch.add(new ImageView(this.manageView.buttonImages.get("next")));
        setOnMouse(drawSwitch.get(2), "next");
        
        //Adding Next button
        dimSwitch.add(new ImageView(this.manageView.buttonImages.get("next")));
        setOnMouse(dimSwitch.get(2), "next");
	}
	
	/**
	 * If the user has drawn a plot on their screen, this method is called when the user hits save
	 * Switches to the dimension setting screen
	 */
	public void validateSave() {
		drawSwitch.get(2).setOnMouseClicked((e) -> {        
			e.consume();
           	toolbar.getItems().get(0).setDisable(true);
           	toolbar.getItems().get(1).setDisable(true);
            	
           	//set the outline of the polygon in model
           	if(border.getChildren().contains(poly)) 
           		controller.enterPolygonBoundary(poly);    
           	
           	//Clear everything on this screen
           	border.getChildren().remove(poly);
           	border.getChildren().removeAll(anchors);
           	gc.clearRect(0,0, this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
          	shapeClicked = true;
           	
           	//Start the next screen for dimensions 
           	onSettingDimensions();      	
           	removeLines();
          	controller.drawPlot();
        });
	}
	/**
	 * Called when user hits "save" on their plot. Allows users to select any linear length and input its dimension.
	 * Saves the inputed value and calls settingLength in controller to calculate length per pixel
	 */
	public void onSettingDimensions() {
        gc.drawImage(dimInstructions.getImage(), 0, 0);
		border.setOnMousePressed(controller.getHandlerforSettingDimension(true));
        border.setOnMouseDragged(controller.getHandlerforSettingDimension(false));
        border.setOnMouseReleased(event -> {
        	border.setOnMousePressed(null);
		    border.setOnMouseDragged(null);
		    border.setOnMouseReleased(null);
		    gc.clearRect(0,0, this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
		    dimLine = new Line(manageView.dimLen.get(0)[0], manageView.dimLen.get(0)[1], manageView.dimLen.get(manageView.dimLen.size()-1)[0], manageView.dimLen.get(manageView.dimLen.size()-1)[1]);
		    dimLine.setStrokeWidth(3);
		    dimLine.setStroke(Color.PALEVIOLETRED);
			border.getChildren().add(dimLine);
			gc.drawImage(dimInstructions.getImage(), 0, 0);
		});	
		createHBox(dimSwitch);
	    
	    //Input value box
	    TextField dimension = new TextField();
	    dimension.setPromptText("Enter dimension (ft)");
	    dimSwitch.get(2).addEventHandler(MouseEvent.MOUSE_CLICKED, (event)-> {
	    	String length = dimension.getText();
	    	try{
	    		controller.settingLength(Double.parseDouble(length));
	    	}
	    	catch(NumberFormatException e){
	    		//not a double
	   			dimension.clear();
	   		}         
	    	controller.switchViews("ConditionScreen");          
	    	
	    	event.consume();
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
	public void createHBox(ArrayList<ImageView> list) {
		if(border.getChildren().contains(box))
			border.getChildren().remove(box);
		box = new ToolBar();
//		box.setSpacing(10);
		box.setPadding(new Insets(20));
//		box.setAlignment(Pos.TOP_CENTER);
		box.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		box.getItems().add(createSpacer());
		box.getItems().addAll(list);
		box.getItems().add(createSpacer());
		border.setBottom(box);
	}
	
	private Node createSpacer() {
		Pane spacer = new Pane();
		HBox.setHgrow(spacer, Priority.SOMETIMES);
		
		return spacer;
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
		n.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
            border.setOnMousePressed(null);
            border.setOnMouseDragged(null);
            border.setOnMouseReleased(null);
            
            e.consume();
        });
	}
	
	/**
	 * Creates the polygon shape when the button Polygon is pressed. Initializes the anchors.
	 */
	public void onShape() {
		//Polygon code adapted from: https://gist.github.com/jpt1122/dc3f1b76f152200718a8

		//Initializes the points clockwise starting from top left corner of box
		double[] x = new double[]{this.manageView.getScreenWidth()/2-100, this.manageView.getScreenWidth()/2+100, this.manageView.getScreenWidth()/2+100, this.manageView.getScreenWidth()/2-100};
	    double[] y = new double[]{this.manageView.getScreenHeight()/2-100, this.manageView.getScreenHeight()/2-100, this.manageView.getScreenHeight()/2+100, this.manageView.getScreenHeight()/2+100};  
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
	
	/**
	 * Toggles the drag handler for the Anchor depending on the boolean dragAnchor. 
	 * dragAnchor is changed to true and false depending if the anchors should be draggable
	 */
	public void toggleAnchorHandler() {
		Iterator<Anchor> itr = anchors.iterator();
    	while(itr.hasNext()) {
    		Anchor a = (Anchor)itr.next();
    		a.setDragAnchor(dragAnchor);
    	}
	}
}
