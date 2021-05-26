import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.input.TransferMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Polygon;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

import javax.imageio.ImageIO;

import com.itextpdf.text.BaseColor;

import javafx.application.Application;

/**
 * The controller part of the MVC model
 * @author Ishika Govil, Arunima Dey, Dea Harjianto, Jinay Jain, Kimmy Huynh
 */
public class Controller extends Application {
	ManageViews view;
	// reading plant information
	String plantFile = "src/main/resources/finalPlantListWithInfo.csv";
	// reading lep information
	String lepFile = "src/main/resources/finalLepList.csv";
	Model model;
	Stage stage;
	boolean inMain = false;
	final int XDISPLACE = 200;
	final int YDISPLACE = 50;
	final double THRESHOLD = 0.00001;
	final int XSNAPDISPLACE = 15;
	final int YSNAPDISPLACE = 15;
	
	/** 
	 * Override for the Application start method. Instantiates all fields
	 * @param Stage
	 * @author Ishika Govil 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		this.model = new Model();
		System.out.println("setting plant directory");
		this.model.setPlantDirectory(CSVtoPlants.readFile(plantFile));
		System.out.println("setting lep directory");
		this.model.setLepDirectory(CSVtoLeps.readFile(lepFile));
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	    view = new ManageViews(stage,this, plantFile, lepFile, screenBounds.getWidth(), screenBounds.getHeight());
	    System.out.println(screenBounds);
	    this.stage = stage;
		this.stage.setFullScreen(true);
	    readBack();
	    Scene scene = new Scene(view.getBorderPane(), view.getScreenWidth(), view.getScreenHeight());
	    this.stage.setScene(scene);
	    setTheStage();
	    restoreState();
	    
	}

	/** 
	 * Sets the border pane to the scene and shows the stage
	 * @author Ishika Govil 
	 */
	public void setTheStage() {
		this.stage.getScene().setRoot(this.view.getBorderPane());
		this.stage.show();
	}
	/**
	 * main method to launch the software
	 * @param String[] args
	 */
	public static void main(String[] args) {
		launch(args);
	}


	/** 
	 * Calls switchViews when a button is clicked
	 * @param String describing the next action to be shown
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforClicked(String next) { 
		return (e) -> { switchViews(next); };
	}
	
	/** 
	 * Called when the link to Mt. Cuba is clicked
	 * @param String for the url
	 * @return EventHandler<ActionEvent>
	 */
	public EventHandler<ActionEvent> getHandlerForLinkClicked(String url) {
		return (e) -> getHostServices().showDocument(url);
	}
	
	/** 
	 * Calls onChangeCursor in view when mouse enters the button frame
	 * @param String key representing key for the ImageView
	 * @param ImageView b representing the ImageView being hovered
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforMouseEntered(String key, ImageView b) { //Sets cursor to hand  (calls changeCursor with true)
		return (e) -> {view.onChangeCursor(true, key, b);};
	}
	
	/** 
	 * Calls onChangeCursor in view when mouse exits the button frame
	 * @param String key representing key for the ImageView
	 * @param ImageView b representing the ImageView being hovered
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforMouseExited(String key, ImageView b) { //Changes cursor back (calls changeCursor with false)
		return (e) -> { view.onChangeCursor(false, key, b);  };

	}
	/** 
	 * Calls anchoring when the anchor is dragged on the polygon
	 * @param EventHandler<MouseEvent>
	 * @param Anchor
	 * @param Polygon
	 * @param boolean dragAnchor describing whether anchor is draggable
	 * @param DoubleProperty x describing x position
	 * @param DoubleProperty y describing y position
	 * @param int index of anchor
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforAnchor(Anchor anchor, boolean dragAnchor, DoubleProperty x, DoubleProperty y, Polygon poly, int idx) {
		return (e) -> { anchoring(e, anchor, dragAnchor, x, y, poly, idx); };
	}
	/**
	 * Calls pressed when mouse is pressed
	 * @param key the plant that is pressed
	 * @return EventHandler<MouseEvent>
	 * @author Arunima Dey
	 */
	public EventHandler<MouseEvent> getHandlerforPressed(String key, boolean inMain){
		return (e) -> { pressed(e,key,inMain); };
	}
	/** 
	 * Calls drag when mouse is dragged
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforDrag() {
		return (e) -> {  drag(e); };
	}

	/** 
	 * Calls drag when mouse is dragged, specifically when user is drawing
	 * @param boolean describing whether mouse is pressed for the first time
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforDrawing(boolean isPressed) {
		return (e) -> {  draw(e, isPressed); };
	}
	
	/**
	 * Calls drawBreak when the user lifts the mouse when freedrawing plot
	 * @param EventHandler<MouseEvent>
	 */
	public EventHandler<MouseEvent> getHandlerforDrawBreak() {
		return (e) -> {  drawBreak(); };
	}
	
	/** 
	 * Calls drag when mouse is released
	 * @param String key
	 * @param boolean startingInTile
	 * @return EventHandler<MouseEvent>
	 */
	public EventHandler<MouseEvent> getHandlerforReleased(String key, boolean startingInTile) {
		return (e) -> { release(e,key,startingInTile);  };
	}
	
	/**
	 * Calls entered when mouse is entered
	 * @param String key
	 * @return EventHandler<MouseDragEvent>
	 */
	public EventHandler<MouseDragEvent> getHandlerforMouseEntered(String key){
		return (e) -> { entered(e,key); };
	}
	
	/** 
	 * Calls drag when mouse is dragged, specifically when user is setting dimensions
	 * @param boolean describing whether mouse is pressed for the first time
	 * @return EventHandler<MouseEvent>
	 * @author Ishika Govil 
	 */
	public EventHandler<MouseEvent> getHandlerforSettingDimension(boolean isPressed) {
		return (e) -> {  settingDimensionLine(e, isPressed); };
	}
		
	/**
	 * Creates handler for conditions canvas clicked
	 * @return the associated canvas click handler
	 * @author Jinay Jain
	 */
	public EventHandler<MouseEvent> getConditionsClickHandler(Canvas canvas) {
		return (e) -> { fillRegion(canvas, e); };
	}
	
	/**
	 * Handler for compopst clicked
	 * @return the MouseEvent
	 */
	public EventHandler<MouseEvent> getHandlerForCompostClicked(){
		return (e) -> {showCompost(e);};
	}
	
	/**
	 * When compost is clicked, shows the deleted pane
	 * @param event the mouse event
	 */
	public void showCompost(MouseEvent event) {
		((GardenDesign) view.views.get("GardenDesign")).compostPopUp(model.deleted);
	}
	
	/**
	 * Handler for when save button pressed in summary
	 * @return the event
	 */
	public EventHandler<MouseEvent> getHandlerforSummarySave(){
		return (e) -> {summarySave(e);};
	}
	
	/**
	 * Handler for edit button pressed
	 * @param index index of the garden with that index
	 * @param dialog stage that holds the dit button
	 * @return the action event
	 */
	public EventHandler<MouseEvent> getHandlerforEditSaved(int index, Stage window){
		return (e) -> {editSavedGarden(e,index, window);};
	}
	
	/**
	 * Creates handler for when the user clicks on a section of the garden. Updates the list of plants to match those conditions.
	 * @param canvas the Canvas that was clicked
	 * @return the corresponding handler
	 * @author Jinay Jain
	 */
	public EventHandler<MouseEvent> getHandlerForSectionClick(Canvas canvas) {
		return (e) -> {sectionClicked(e, canvas);};
	}
	
	/**
	 * Creates handler for when user deleted a saved garden
	 * @param index the index of the garden to be deleted
	 * @param window the stage of the garden summary 
	 * @return the handler
	 */
	public EventHandler<MouseEvent> getHandlerforDeleteSaved(int index, Stage window){
		return (e) -> {deleteSavedGarden(e, index, window);};
	}
	
	/**
	 * Clears garden when user clicks the clear button
	 */
	public void clearGarden() {
		Garden garden = model.gardenMap;
		garden.placedPlants.clear();
		this.updateBudgetandLep();
		
	}

	/**
	 * Creates a handler for when the user changes the sort
	 * @return the corresponding handler
	 */
	public EventHandler<ActionEvent> getHandlerForSort() {
		return (e) -> { updateSort(e); };
	}
	
	/**
	 * Updates the current search with the query
	 * @param query the search query inputted
	 */
	public void updateSearch(String query) {
		GardenDesign gd = (GardenDesign) this.view.getView("GardenDesign");
		gd.updateImageList(model.updateFilter(new SearchFilter(query)));
	}
	
	/**
	 * Updates the current sort to use
	 * @param comp the comparator to sort by
	 */
	public void updateSort(ActionEvent e) {
		ComboBox<Comparator<PlantSpecies>> box = (ComboBox<Comparator<PlantSpecies>>) e.getSource();
		GardenDesign gd = (GardenDesign) this.view.getView("GardenDesign");
		gd.updateImageList(model.updateSort(box.getValue()));
	}
	
	/**
	 * Controls mouse event when user clicks on a given section of plot
	 * @param e the mouse event
	 * @param canvas the canvas
	 */
	private void sectionClicked(MouseEvent e, Canvas canvas) {
		int newX = (int) e.getX();
		int newY = (int) e.getY();

		WritableImage img = canvas.snapshot(null, null);
		PixelReader pr = img.getPixelReader();
		Conditions cond = Conditions.fromColor(pr.getColor(newX, newY));
		
		ArrayList<String> filteredNames = model.updateFilter(new ConditionFilter(cond));
		System.out.println(filteredNames);
		GardenDesign gd = (GardenDesign) this.view.getView("GardenDesign");
		gd.updateImageList(filteredNames);
	}
	
	/**
	 * Called when Plant 1's drop down is altered in PlantCompare page and updates info accordingly.
	 * @param plantList
	 * @return EventHandler<ActionEvent>
	 */
	public EventHandler<ActionEvent> getHandlerForPlantCompare(ComboBox<PlantSpecies> plantList) {
		return (e) -> {updateComparePlant(e, plantList, true);};
	}
	
	/**
	 * Called when Plant 2's drop down is altered in PlantCompare page and updates info accordingly.
	 * @param plantList
	 * @return EventHandler<ActionEvent>
	 */
	public EventHandler<ActionEvent> getHandlerForPlantCompare2(ComboBox<PlantSpecies> plantList) {
		return (e) -> {updateComparePlant(e, plantList, false);};
	}
	
	/**
	 * Calls the PlantCompare page to update the information for whichever plant/display is called.
	 * @param event
	 * @param plantList
	 * @param isFirst
	 */
	public void updateComparePlant(ActionEvent event, ComboBox<PlantSpecies> plantList, boolean isFirst) {
		int selectedIndex = plantList.getSelectionModel().getSelectedIndex();
	    PlantSpecies selectedItem = plantList.getSelectionModel().getSelectedItem();
	    
	    ((ComparePlants)view.getView("ComparePlants")).updatePlantInfo(selectedItem, isFirst);

	    System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
	    System.out.println("   ComboBox.getValue(): " + plantList.getValue());
	}
	
	/**
	 * Updates the budget based on the double passed from a TextField
	 * @param budgetString the String with the user's budget input
	 * @author Jinay Jain
	 */
	public void updateBudget(double newBudget) {
		this.model.setBudget(newBudget);
	}
	
	/**
	 * Sets the title of a saved garden
	 * @param title
	 */
	public void setGardenTitle(String title) {
		this.model.gardenMap.setTitle(title);
	}
	
	/**
	 * Controls the event when mouse is pressed on a plant imageView
	 * Displays name and information of that plant in garden design screen
	 * @param event the mouse event
	 * @param key the plant that was pressed
	 */
	public void pressed(MouseEvent event, String key, boolean inMain) {
		Node n = (Node) event.getSource();
		System.out.println("Clicked");
		model.movedPlant = key;
		model.setX(n.getTranslateX());
		model.setY(n.getTranslateY());
		if(key!=null) {
			String name = model.plantDirectory.get(key).getCommonName();
			String sciName = model.plantDirectory.get(key).getGenusName() + "-" + model.plantDirectory.get(key).getSpeciesName();
			String description = model.plantDirectory.get(key).getDescription();
			String info = description;
			((GardenDesign)view.views.get("GardenDesign")).makeInfoPane(sciName, name, info);
		}
		event.setDragDetect(true);
		
		
	}
	
	/**
	 * Controls the drag event. Moves the imageView with mouse and gives that x and y to model
	 * @param event
	 * @author Arunima Dey
	 */
	public void drag(MouseEvent event) {
		Node n = (Node)event.getSource();
		n.toFront();
		model.setX(model.getX() + event.getX()); //event.getX() is the amount of horiz drag
		model.setY(model.getY() + event.getY());
		view.setX(model.getX(),n);
		view.setY(model.getY(),n);
		event.setDragDetect(false);
	}
	
	/**
	 * The event handler for drag over 
	 * @return
	 */
	public EventHandler<DragEvent> getHandlerForDragOver() {
		return (e) -> {setOndragOver(e);};
	} 
	
	/**
	 * Controls the drag over event. When plant needs to be places in the center pane
	 * @param event the Drag event
	 */
	public void setOndragOver(DragEvent event) {
		Node n = (Node) event.getGestureSource();
		view.setX(0,n);
		view.setY(0,n);
		if(event.getGestureSource()!=event.getSource() && event.getDragboard().hasString()) {
			Dragboard db = event.getDragboard();
			String name = db.getString();
			System.out.println("the name of the plant setOnDragOver: "+name);
			System.out.println("in main ready to place");
			if(model.gardenMap.getCost()>=model.gardenMap.getBudget()) {
				System.out.println("budget over: "+ model.gardenMap.getBudget());
				GardenDesign g = (GardenDesign) view.views.get("GardenDesign");
				g.budgetExceededPopup();
				System.out.println("budget should now be changed: "+ model.gardenMap.getBudget());
			}
			else {
				double heightWidth = scalePlantSpread(name);
				String nodeId = ((GardenDesign)view.views.get("GardenDesign")).addImageView(event.getSceneX(),event.getSceneY(), name,heightWidth,false);
				model.placePlant(event.getSceneX(), event.getSceneY(), name, nodeId,false);
				if (nodeId != null) {
					((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(model.gardenMap.getCost(), model.gardenMap.getLepCount(),model.gardenMap.getBudget());
				}
			}
		}
		
	}
	
	/**
	 * Controls the release event. When drag starts in tilepane adds a new imageView of the same plant to the center pane. Also checks for collisions among ImageViews according to spread. 
	 * @param event the mouse event
	 * @param name the name or key of the plant being released
	 * @param startingInTile boolean to inform if drag started in tile pane
	 * @author Arunima Dey, Dea Harjianto
	 */
	public void release(MouseEvent event, String name, boolean startingInTile) {
		Node n = (Node)event.getSource();
		System.out.println("updating PART TWO");
		String id = ((Node) event.getSource()).getId();
		model.updateXY(id);
		ImageView plant = ((ImageView) event.getSource());
		double plantX = plant.getBoundsInParent().getCenterX();
		double plantY = plant.getBoundsInParent().getCenterY();
		double plantRadius = plant.getBoundsInParent().getHeight();
		
		double plantXFixed = plantX - plantRadius/2 + XDISPLACE;
		double plantYFixed = plantY - plantRadius/2 + YDISPLACE;
		
		System.out.println("x: " + plantX + ", y: " + plantY + ", radius: " + plantRadius);
		
		double[] collidingInfo = ((GardenDesign)(view.views.get("GardenDesign"))).validatePlantPlacement(plantXFixed, plantYFixed, plantRadius);
		int collideNumber = (int)collidingInfo[1];
		System.out.println(collidingInfo[0]);
		System.out.println(collideNumber);
		
		if (collideNumber == 0) {
 			// updates the location haha gang gang
			model.updateXY(id);
 			System.out.println("updated and moved PART TWO!");
		} else {
			// make it SNAP BACK
			
			double coordSnap = plantCollideSnap(collidingInfo[4]);
			
			double parentX = collidingInfo[2];
			double parentY = collidingInfo[3];
			//double totalDist = collidingInfo[4];
			
			switch(collideNumber) {
				case 1:
					plantX = parentX - coordSnap;
					plantY = parentY - coordSnap;
					break;
				case 2:
					plantX = parentX + coordSnap;
					plantY = parentY - coordSnap;
					break;
				case 3:
					plantX = parentX + coordSnap;
					plantY = parentY + coordSnap;
					break;
				case 4:
					plantX = parentX - coordSnap;
					plantY = parentY + coordSnap;
					break;
			}
			
			view.setX(plantX - XSNAPDISPLACE,n);
			view.setY(plantY - YSNAPDISPLACE,n);
			System.out.println("cannot move, collided PART TWO!");
		}
	}
	/**
	 * Saves the garden image such that it crops the image at the bounds of the main pane in GardenDesign
	 * @return WritableImage for the entire garden
	 */
	public WritableImage snapshotGarden() {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		WritableImage temp = ((GardenDesign)this.view.currView).main.snapshot(params,null);
		PixelReader reader = temp.getPixelReader();	
		if(this.model.getGarden().placedPlants.size() != 0)
			return new WritableImage(reader, 0, 0, (int)((GardenDesign)this.view.currView).main.getWidth(),(int)((GardenDesign)this.view.currView).main.getHeight() );
		else
			return temp;
				
	}
	
	/**
	 * Updates the budget and lep count for garden design
	 */
	public void updateBudgetandLep() {
		((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(model.gardenMap.getCost(), model.gardenMap.getLepCount(),model.gardenMap.getBudget());
	}
	
	
	/**
	 * Does the math for how far apart should the two centers of ImageViews be to be totally aligned.
	 * @param distance
	 * @return double
	 */
	public double plantCollideSnap(double distance) {
		double coord = Math.sqrt(Math.pow(distance, 2) / 2);
		return coord;
	}
	
	/**
	 * Called when a node is trying to be deleted
	 * when it enters the compost nodee
	 * @param event the drag event
	 * @param key the name of the plant
	 * @author Arunima Dey
	 */
	public void entered(MouseDragEvent event, String key) {
		System.out.println("trying to drag into compost");
		System.out.println(key);
		((GardenDesign) view.views.get("GardenDesign")).removePlant((Node) event.getGestureSource());
 		model.removePlant(model.movedPlant,((Node)event.getGestureSource()).getId());
 		((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(model.gardenMap.getCost(), model.gardenMap.getLepCount(),model.gardenMap.getBudget());
	}
	
	/**
	 * when plant is added back from deleted pane it gets added back in the garden
	 * @param plantName the name of the plant that is added back
	 * @author Arunima Dey
	 */
	public void removeFromDeleted(String plantName, String nodeId, double x, double y) {
		model.deleted.remove(plantName);
		model.placePlant(x, y, plantName, nodeId,false);
		if(model.gardenMap.getCost()>model.gardenMap.getBudget()) {
			GardenDesign g = (GardenDesign) view.views.get("GardenDesign");
			g.budgetExceededPopup();
		}
		((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(model.gardenMap.getCost(), model.gardenMap.getLepCount(),model.gardenMap.getBudget());

	}
	
	/**
	 * The spread of a plant is calculated using the lengthPerPixel and spread of the plant
	 * @param plantKey the name of the plant to get pixel spread for
	 * @return double representing the number of pixels of the radius of the plant
	 * @author Arunima Dey
	 */
	public double scalePlantSpread(String plantKey) {
		System.out.println("plantKey in scalePlantspread");
		PlantSpecies plant = model.plantDirectory.get(plantKey);
		double numPixels = plant.getSpreadRadius() / (this.model.getLengthPerPixel() * this.model.getScale());
		return numPixels;
	}
	
	/**
	 * Saves the state of gardenDesign when user closes application
	 */
	public void saveState() {
		Collection<PlacedPlant> values = model.gardenMap.placedPlants.values();
		model.gardenMap.plants = new ArrayList<PlacedPlant>(values);
		System.out.println("size of plants when saving state"+model.gardenMap.plants.size());
		try {
			new File("src/main/resources/SavedState.ser").delete();
			FileOutputStream fos = new FileOutputStream("src/main/resources/SavedState.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model);
			oos.close();
		}catch(Exception e) {
			System.out.println("Not able to write to saved state");
			e.printStackTrace();
		}
	}
	
	/**
	 * When user closes application on garden deesign screen brings user back to where they were in a previous session
	 */
	public void restoreState() {
		System.out.println("restoring state");
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/SavedState.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Model model = (Model) ois.readObject();
			System.out.println("is gardenDesign true? "+model.gardenDesign);
			if(model.gardenDesign) {
				Map<String, PlantSpecies> plantDirectory = this.model.plantDirectory;
				Map<String, Lep> lepDirectory = this.model.lepDirectory;
				this.model = model;
				this.model.plantDirectory = plantDirectory;
				this.model.lepDirectory = lepDirectory;
				this.view.switchViews("GardenDesign");
				setTheStage();
				Garden garden = model.gardenMap;
				System.out.println("outline size: "+garden.outline.size());
				System.out.println("plants size: "+ garden.plants.size());
				garden.placedPlants = new HashMap<>();
				((GardenDesign) view.views.get("GardenDesign")).remakePane();
				garden.plants.forEach(plant->{
					System.out.println("so plants exist");
					double heightWidth = scalePlantSpread(plant.getName());
					String node = ((GardenDesign) view.views.get("GardenDesign")).addImageView(plant.getX(), plant.getY(), plant.getName(),heightWidth,true);
					model.placePlant(plant.getX(), plant.getY(), plant.getName(), node, true);
				});
				((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(garden.getCost(), garden.getLepCount(),garden.getBudget());
			}
			ois.close();
		}catch(ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * when a user wants to edit a previously saved garden
	 * @param event the edit button action
	 * @param index index of saved garden
	 * @param dialog the stage that contains edit button
	 */
	public void editSavedGarden(MouseEvent event, int index, Stage window) {
		this.view.switchViews("GardenDesign");
		this.goingToGardenDesign();
		setTheStage();
		model.gardenMap = model.savedGardens.get(index);
		Garden garden = model.gardenMap;
		model.gardenMap.placedPlants = new HashMap<>();
		System.out.println("polygon; "+ garden.polygonCorners + " "+ garden.polygonCorners.size());
		System.out.println("putline: "+garden.outline + " "+ garden.outline.size());
		((GardenDesign) view.views.get("GardenDesign")).remakePane();
		model.scale = garden.scale;
		model.lengthPerPixel = garden.lengthPerPixel;
		garden.plants.forEach(plant->{
			double heightWidth = scalePlantSpread(plant.getName());
			String node = ((GardenDesign) view.views.get("GardenDesign")).addImageView(plant.getX(), plant.getY(), plant.getName(),heightWidth,true);
			model.placePlant(plant.getX(), plant.getY(), plant.getName(), node, true);
		});
		System.out.println(garden.getBudget());
		((GardenDesign) view.views.get("GardenDesign")).updateBudgetandLep(garden.getCost(), garden.getLepCount(),garden.getBudget());
		window.close();
		model.setToEdit();
		model.setEditGardenIndex(index);
	}
	
	/**
	 * Deletes a saved garden when user clicks delete button
	 * @param event the mouse event from clicked delete
	 * @param index the index of thee garden to be deleted
	 * @param window the stage that shows the garden summary and and the delete button
	 */
	public void deleteSavedGarden(MouseEvent event, int index, Stage window) {
		model.savedGardens.remove(index);
		Gallery gal = (Gallery) view.views.get("Gallery");
		gal.removeGardenFromPane(index);
		window.close();
		new File("src/main/resources/garden.ser").delete();
		new File("src/main/resources/gardenImage"+index+".png").delete();
		try {
			FileOutputStream fos = new FileOutputStream("src/main/resources/garden.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(model.savedGardens);
			oos.close();	
		}
		catch(IOException e) {
			System.out.println("error writing to file after deleting");
			e.printStackTrace();
		}
	}
	

	/**
	 * Everytime the application is started a read back all the saved gardens from previos sessions
	 * @author Arunima Dey
	 */
	@SuppressWarnings("unchecked")
	public void readBack() {
		try {
			System.out.println("reading");
			FileInputStream fis = new FileInputStream("src/main/resources/garden.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			model.savedGardens = (ArrayList<Garden>) ois.readObject();
			Gallery gal = (Gallery) view.views.get("Gallery");
			//model.savedGardens.clear();
			for(int i = 0; i<model.savedGardens.size();i++) {
				Garden garden = model.savedGardens.get(i);
				System.out.println(garden.getTitle());
				try {
					Image im = new Image(getClass().getResourceAsStream("/SavedGardenImages/"+garden.getTitle()+".png"));
					gal.loadScreen(im,i,model.getCostforGallery(garden),model.getLepsforGallery(garden),garden.getTitle());
				}catch(NullPointerException e) {
					e.printStackTrace();
				}
			}
			ois.close();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("error reading in");
			e.printStackTrace();
		}
	}
	
	/**
	 * Refreshes the images in the view screens
	 */
	public void refreshImages() {
		GardenDesign gd = (GardenDesign) view.getView("GardenDesign");
		ComparePlants cp = (ComparePlants) view.getView("ComparePlants");
		
		ArrayList<String> newPlants = new ArrayList<>();
		newPlants.addAll(model.getPlantInfo().keySet());
		
		System.out.println("ADDING PLANTS: " + newPlants);
		
		gd.updateImageList(newPlants);
		cp.setPlantDir(view.getPlantImages());
	}
	
	/**
	 * Creates a popup with given message
	 * @param message
	 * @return the popup
	 */
	// https://stackoverflow.com/questions/18669209/javafx-what-is-the-best-way-to-display-a-simple-message
	public static Popup createPopup(final String message) {
	    final Popup popup = new Popup();
	    popup.setAutoFix(true);
	    popup.setAutoHide(true);
	    popup.setHideOnEscape(true);
	    Label label = new Label(message);
	    label.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent e) {
	            popup.hide();
	        }
	    });
	    label.setStyle(" -fx-background-color: #8C6057; -fx-padding: 10; -fx-border-color: #5C5346; -fx-border-width: 5; -fx-font-size: 16; -fx-text-fill: white");
	    popup.getContent().add(label);
	    return popup;
	}
	
	/**
	 * Displays popup message with given string
	 * @param message the message that goes in the popup
	 * @param stage the stage that owns the popup
	 */
	public static void showPopupMessage(final String message, final Stage stage) {
	    final Popup popup = createPopup(message);
	    popup.setOnShown(new EventHandler<WindowEvent>() {
	        @Override
	        public void handle(WindowEvent e) {
	            popup.setX(stage.getX() + stage.getWidth()/2 - popup.getWidth()/2);
	            popup.setY(stage.getY() + stage.getHeight()/2 - popup.getHeight()/2);
	        }
	    });        
	    popup.show(stage);
	}
	
	/**
	 * Called when user saves garden from summary
	 * @param event button pressed event
	 * @author Arunima Dey
	 */
	//https://stackoverflow.com/questions/44841329/how-to-implement-serializable-for-my-project-to-have-persistence
	public void summarySave(MouseEvent event) {
		new File("src/main/resources/garden.ser").delete();
		Collection<PlacedPlant> values = model.gardenMap.placedPlants.values();
		model.gardenMap.lengthPerPixel = model.lengthPerPixel;
		model.gardenMap.scale = model.scale;
		model.gardenMap.plants = new ArrayList<PlacedPlant>(values);
		Garden garden = model.getGarden();
 		try {
			Gallery gal = (Gallery) view.views.get("Gallery");
			if(model.editing()) {
				int index = model.getEditGardenIndex();
				model.savedGardens.remove(index);
				model.savedGardens.add(index,model.getGarden());
				gal.loadScreen(view.getGardenImag(), index ,garden.getCost(),garden.getLepCount(),garden.getTitle());
				FileOutputStream fos = new FileOutputStream("src/main/resources/garden.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(model.savedGardens);
				oos.close();
				System.out.println("the index:"+index);
				new File("src/main/resources/SavedGardenImages/"+model.gardenMap.getTitle()+".png").delete();
				File f = new File("src/main/resources/SavedGardenImages/"+model.gardenMap.getTitle()+".png");
				try {
					ImageIO.write(view.savedImg,"png", f);
				}
				catch (Exception s){
					s.printStackTrace();
				}
				
			}else {
				int index = model.savedGardens.size()-1;
				TextField titleField = ((Summary) view.views.get("Summary")).gardenTitlePopup();
				titleField.setOnKeyReleased(e->{
					if(e.getCode()==KeyCode.ENTER) {
						String title = titleField.getText();
						if(title!=null && model.isTitleValid(title)) {
							this.setGardenTitle(title);
							(titleField.getScene().getWindow()).hide();
							System.out.println("Title of garden: "+garden.getTitle());
							model.savedGardens.add(garden);
							gal.loadScreen(view.getGardenImag(),index,garden.getCost(),garden.getLepCount(),garden.getTitle());
							try {
								FileOutputStream fos = new FileOutputStream("src/main/resources/garden.ser");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(model.savedGardens);
								oos.close();	
							}catch(IOException e1) {
								System.out.println("error writing to file");
								e1.printStackTrace();
							}
							new File("src/main/resources/SavedGardenImages/"+title+".png").delete();
							File f = new File("src/main/resources/SavedGardenImages/"+title+".png");
							try {
								ImageIO.write(view.savedImg,"png", f);
							}
							catch (Exception s){
								s.printStackTrace();
							}
						}else {
							Popup error = createPopup("Error with title!\nTry to enter a different title");
							error.show(stage);
						}
					}
				});
			}
 		}
 		catch(IOException e) {
 			System.out.println("error writing to file");
 			e.printStackTrace();
 		}
		
		
	}
	

	/** 
	 * Called when user is drawing. 
	 * Updates the canvas of the relevant view and calls updateOutlineSection in model to pass boundary coordinates
	 * @param MouseEvent
	 * @param Boolean describing whether mouse is pressed for the first time
	 * @author Ishika Govil 
	 */
	public void draw(MouseEvent event, boolean isPressed) { // (changeCursor called with false) -- in beta
		if(isPressed) {
			 this.view.getGC().beginPath();
			 restartPlotting();
		}
		this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		this.view.getGC().stroke();
		this.model.getGarden().updateOutline(event.getSceneX(), event.getSceneY());
		this.view.validateSave();
	}
	/**
	 * When user lifts their mouse upon drawing the plot boundary, it adds a (-1, -1) point to the outline 
	 */
	public void drawBreak() {
		this.model.getGarden().updateOutline(-1,-1);
	}
	
	/** Sets the new coordinates of the anchor of the relevant anchor of the polygon after user drags it
	 * @param EventHandler<MouseEvent>
	 * @param Anchor
	 * @param Polygon
	 * @param boolean dragAnchor describing whether anchor is draggable
	 * @param DoubleProperty x describing x position
	 * @param DoubleProperty y describing y position
	 * @param int index of anchor
	 * @author Ishika Govil 
	 */
	public void anchoring(MouseEvent event, Anchor anchor, boolean dragAnchor, DoubleProperty x, DoubleProperty y, Polygon poly, int idx) {
		if(dragAnchor) {
    		anchor.setCenterX(event.getX());           
    		anchor.setCenterY(event.getY());    
    		poly.getPoints().set(idx, x.get());
    		poly.getPoints().set(idx + 1, y.get());
    		restartPlotting();           	
    	}
	}
	
	/**
	 * When user hits clear on the polygon boundary, polygonCorners ArrayList in the Garden is reset
	 */
	public void restartPolygonBoundary() {
		this.model.getGarden().polygonCorners = new ArrayList<Vector2>();
	}
	
	/**
	 * When the user saves the boundary, if they have drawn a polygon, it saves the corners of the polygon
	 * @param Polygon drawn by user
	 * @author Ishika Govil
	 */
	public void enterPolygonBoundary(Polygon poly) {
		for(int i = 0; i < poly.getPoints().size(); i+= 2) {
			DoubleProperty x = new SimpleDoubleProperty(poly.getPoints().get(i));
            DoubleProperty y = new SimpleDoubleProperty(poly.getPoints().get(i + 1));
			this.model.getGarden().setPolygonCorners(x.get(), y.get());
		}
	}
	
	/**
	 * Iterates over the boundary ArrayLists in Garden to draw plot to screen.
	 * Calls iteratePlot() to translate the plot
	 * @author Ishika Govil
	 */
	public void drawPlot() {
		ListIterator<Vector2> itr = model.getGarden().outline.listIterator();
		iteratePlot(itr, model.getGarden().outline, false);
		itr = model.getGarden().polygonCorners.listIterator();
		iteratePlot(itr, model.getGarden().polygonCorners, true);
	}
	
	/**
	 * Iterates over just the freehand portion outline ArrayList in Garden
	 * @author Ishika Govil
	 */
	public void drawFreehandPart() {
		ListIterator<Vector2> itr = model.getGarden().outline.listIterator();
		iteratePlot(itr, model.getGarden().outline, false);
	}
	
	/**
	 * Draws the garden onto any sized Canvas including plot boundaries and conditions
	 * @param canvas the Canvas to draw onto
	 */
	public void drawToCanvas(Canvas canvas) {
		ArrayList<Vector2> extrema = this.model.getGarden().getExtremes();
		ArrayList<Conditions> conds = this.model.getGarden().getSections();
		
		double newScale = View.drawOnCanvas(canvas, this.model.getGarden().getOutline(), this.model.getGarden().getPolygonCorners(), extrema, conds);
		this.model.setScale(newScale);
	}	
	
	/**
	 * Scales and translates all the points onto the screen by calling drawLine in View
	 * @param Iterator itr
	 * @param ArrayList representing which plot boundary list, outline or polygonCorners
	 * @param boolean isPolygon representing if the boundary is a polygon
	 * @param double scale 
	 * @author Ishika Govil
	 */
	public void iteratePlot(ListIterator<Vector2> itr, ArrayList<Vector2> list, boolean isPolygon) {
		while(itr.hasNext()) {
			Vector2 point1 = itr.next();
			Vector2 point2;
			if(itr.hasNext())
				point2 = list.get(itr.nextIndex());	
			else if(isPolygon)
				point2 = list.get(0);
			else 
				return;
			if(point2.getX() != -1 && point1.getX()!= -1)
				this.view.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY(), isPolygon);
		}
	}
	
	
	/** 
	 * Called when user is drawing. 
	 * Updates the canvas of the relevant view and calculates the number of pixels from the starting and ending point of line
	 * @param MouseEvent
	 * @param Boolean describing whether mouse is pressed for the first time
	 * @author Ishika Govil 
	 */
	public void settingDimensionLine(MouseEvent event, boolean isPressed) { // (changeCursor called with false) -- in beta
		if(isPressed)
			view.getGC().beginPath();
		 this.view.getGC().lineTo(event.getSceneX(), event.getSceneY());
		 this.view.getGC().stroke();
		
		//Get pixel information
		double[] arr = {event.getSceneX(),event.getSceneY()};
		this.view.dimLen.add(arr);
		this.view.dimPixel = this.model.calculateLineDistance(view.dimLen.get(view.dimLen.size()-1)[0], view.dimLen.get(0)[0], view.dimLen.get(view.dimLen.size()-1)[1], view.dimLen.get(0)[1]);
	}
	
	/** 
	 * Called after user hits enter after inputting the dimension of their line, in feet.
	 * Sets the length per pixel in model by dividing user inputted value by the number of pixels in the line
	 * @param double representing the user inputted line dimension
	 * @author Ishika Govil 
	 */
	public void settingLength(double length) {
		 this.model.setLengthPerPixel(length/view.dimPixel);
	}
	
	/**
	 * Gets the lep directory
	 * @return the map for info and lep
	 */
	public Map<String, Lep> getLepInfo() {
		return this.model.getLepDirectory();
	}
	
	/**
	 * Gets the plant information
	 * @return the information and the corresponding PlantSpecie
	 */
	public Map<String, PlantSpecies> getPlantInfo() {
		return this.model.getPlantInfo();
	}
	
	/** 
	 * Called when a button is pressed in order to determine the next screen
	 * The next action is determined by the string passed to the function
	 * @param String describing the next action to be shown
	 * @author Ishika Govil 
	 */
	public void switchViews(String next) {
		 //Clears the canvas the user was drawing on. Also clears the ArrayList corresponding to the coordinates of the plot boundary
		 if(next.equals("Clear")) {
			 this.model.getGarden().clearOutline();
			 restartPlotting();
		 }
		 //Clears only the lines drawn after setting dimension. Also clears the ArrayList corresponding to the coordinates of the line
		 else if(next.equals("ClearDim")) {
			 this.view.getGC().drawImage(this.view.img, 0, 0);  
			 this.view.dimLen = new ArrayList<>();
		 } 
		 else if(next.equals("Restart")) {
			 this.model.restart();
			 this.view.restartPlot();
			 setTheStage();
		 }
		 else if(next.equals("ConditionScreen")) {
			 if(this.model.lengthPerPixel!= -1 && this.view.dimPixel != -1) {
				 this.view.switchViews(next);
				 setTheStage();
			 }
		 }
		 else if(next.equals("Summary")) {
			 model.gardenDesign=false;
			 ((Summary) view.views.get("Summary")).addCanvas();
			 ((Summary) view.views.get("Summary")).updateLepandCost(model.gardenMap.getCost(), model.gardenMap.getLepCount());
			 this.view.switchViews(next);
			 setTheStage();
			 //model.gardenMap.setImage(view.savedImg);
			 
		 }
		 else if(next.equals("GardenDesign")) {
			 goingToGardenDesign();
			 this.view.switchViews(next);
			 setTheStage();
		 }
		 else {
			 this.view.switchViews(next);
			 setTheStage();
		 }
	}
	/**
	 * Restarts various properties of PlotDesign if the user goes back to plot design and makes changes
	 * @author Ishika Govil
	 */
	public void restartPlotting() {
		 this.view.views.remove("ConditionScreen");
		 this.view.views.put("ConditionScreen", new ConditionScreen(stage,this,this.view));
		 this.model.getGarden().sections = new ArrayList<Conditions>();
		 //Removing the dimension line
		 this.view.dimLen = new ArrayList<>();
		 this.view.dimPixel = -1;
		 if(((PlotDesign) view.views.get("PlotDesign")).dimLine != null) {
			 this.view.currView.border.getChildren().remove(((PlotDesign) view.views.get("PlotDesign")).dimLine );
			 ((PlotDesign) view.views.get("PlotDesign")).dimLine = null;
		 }
	}
	
	/**
	 * Sets models gardendesign attribute to true
	 */
	public void goingToGardenDesign() {
		model.gardenDesign=true;
	}
	/**
	 * Gets the initial budget set by user to be displayed in garden design screen
	 * @return the budget
	 * @author Arunima Dey
	 */
	public double getBudget() {
		return model.getBudget();
	}
	
	/**
	 * Gets the gardenMap from model
	 * @return the garden
	 */
	public Garden getGarden() {
		return this.model.getGarden();
	}

	/**
	 * Fills the region of a canvas with the user specified conditions
	 * @param canvas
	 * @param e
	 */
	private void fillRegion(Canvas canvas, MouseEvent e) {
		ArrayList<Vector2> extrema = this.model.getGarden().getExtremes();

		double minX = extrema.get(3).getX();
		double maxX = extrema.get(1).getX();
		double minY = extrema.get(0).getY();
		double maxY = extrema.get(2).getY();

		double scale = View.findScale(minX, maxX, minY, maxY, canvas.getWidth(), canvas.getHeight());
		double newX = (e.getX() / scale) + minX;
		double newY = (e.getY() / scale) + minY;

		ConditionScreen screen = (ConditionScreen) this.view.getView("ConditionScreen");
		SoilType soil = screen.soilDropdown.getValue();
		MoistureType moisture = screen.moistureDropdown.getValue();
		LightType light = screen.sunlightDropdown.getValue();
		
		Conditions conditions = new Conditions(soil, moisture, light);
		
		conditions.setX(newX);
		conditions.setY(newY);
		
		this.model.getGarden().addSection(conditions);
		this.drawToCanvas(canvas);
	}
	
	/**
	 * Makes the tooltip information for all plant imageViews
	 * @param plantName name of plant hovering over
	 * @return the String information
	 * @author Arunima Dey
	 */
	public String tooltipInfo(String plantName) {
		String info = "";
		PlantSpecies s = model.plantDirectory.get(plantName);
		info = plantName;
		info += "\nCost: $"+s.getCost();
		info += "\nLeps supported: "+s.getLepsSupported();
		return info;
	}

	/*
	public void isWoody(ComboBox<String> cb) {
		//ArrayList<Integer> countPlants = new ArrayList<Integer>();
		/*int woody = 0;
		int herb = 0;
		boolean isWoody;
		String selected = cb.getSelectionModel().getSelectedItem();
		System.out.println(selected);
		if (selected.equals("Herbaceous vs. woody")) {
			for (PlacedPlant p : model.getGarden().getPlants()) {
				isWoody = p.getSpecies().isWoody();
				if (isWoody) {
					woody = woody + 1;
				}
				else {
					herb = herb + 1;
				}
			}
			countPlants.add(woody);
			countPlants.add(herb);
			
		}
		System.out.println("Woody vs Herb:" + countPlants);
		return countPlants;
		
		Summary s = (Summary) this.view.views.get("Summary");
		//s.isWoody(model.isWoodyData());
		//s.piePopup();
		s.isWoody();
		System.out.println("hello, im in woody");
	}
*/
	/*public ArrayList<PlantSpecies> lepsSupported(ComboBox<String> cb) {
		ArrayList<PlantSpecies> leppy = new ArrayList<PlantSpecies>();
		if (cb.getValue().equals("Top 5 lep-supported plants")) {
			for (PlacedPlant p : model.gardenMap.getPlants()) {
				leppy.add(p.getSpecies());
			}
			
			Collections.sort(leppy, new Comparator<PlantSpecies>() {
				@Override
				public int compare(PlantSpecies x, PlantSpecies y) {
					return x.getLepsSupported() - y.getLepsSupported();
				}
			});
			Collections.reverseOrder();
		}
		return leppy;
	}
	*/
	
	/**
	 * generate an array of plants in the garden and sorting them from highest number of leps
	 * supported to lowest number of leps
	 * @return an arraylist of plants in the garden, sorted
	 */
	public ArrayList<PlantSpecies> lepsCount() {
		ArrayList<PlantSpecies> leppy = new ArrayList<PlantSpecies>();
		for (PlacedPlant p : model.getGarden().getPlants()) {
			leppy.add(p.getSpecies());
		}
		Collections.sort(leppy, new Comparator<PlantSpecies>() {
			@Override
			public int compare(PlantSpecies x, PlantSpecies y) {
				return x.getLepsSupported() - y.getLepsSupported();
			}
		});
		Collections.reverseOrder();
		//leppy.subList(0, 4).clear();;
		return leppy;
	}
	
	/** 
	 * first, get the list of plants in the garden and sort it by the number of leps supported by the plants
	 * second, from the list of plants, generate the list of leps that are being supported
	 * by the plant
	 * @return the list of lep names supported by the plants in the garden
	 */
	public ArrayList<Lep> getLepsName() {
		Map<String, Lep> leppy = getLepInfo();
		HashSet<String> plantToLep = new HashSet<>();
		ArrayList<PlacedPlant> plants = model.getGarden().getPlants();
		ArrayList<Lep> lepsInGarden = new ArrayList<>();
		
		plants.forEach(plant -> {
			plantToLep.add(plant.getSpecies().getGenusName());
		});
		Collections.sort(plants, new Comparator<PlacedPlant>() {
			@Override
			public int compare(PlacedPlant x, PlacedPlant y) {
				return x.getSpecies().getLepsSupported() - y.getSpecies().getLepsSupported();
			}
		});
		Collections.reverseOrder();
		plants.subList(5, plants.size()).clear();
		
		leppy.forEach((lepKey, lepObj) -> {
	    	ArrayList<String> thrivesIn = lepObj.getThrivesInGenus();
	    	System.out.println(thrivesIn);
	    	for (String genusReqs: thrivesIn) {
	    		System.out.println(genusReqs);
	    		if (plantToLep.contains(genusReqs)) {
	    			if (!(lepsInGarden.contains(lepObj))) {
	    				lepsInGarden.add(lepObj);
	    			}
	    		}
	    	}
	    });
		return lepsInGarden;
	}
/*	public void isWoody1(ActionEvent e) {
		ComboBox<String> box = (ComboBox<String>) e.getSource();
		//Object selected = box.getsel
		Summary s = (Summary) this.view.getView("Summary");
		s.isWoody(model.isWoodyData());
		System.out.println("get into the combo box");
	}
*/
	/*
	public EventHandler<ActionEvent> getHandlerForSummary(ComboBox<String> cb) {
		return (e) -> {
			isWoody(cb);
		};
	}*/
	
	/**
	 * get the list of colors that made up a certain condition of the garden
	 * @param ind
	 * @return an arraylist of RBG colors
	 */
	public ArrayList<Integer> conditionColor(int ind) {
		ArrayList<Integer> c = new ArrayList<Integer>();
		c.add((int) model.getGarden().getSections().get(ind).toColor().getRed());
		c.add((int) model.getGarden().getSections().get(ind).toColor().getGreen());
		c.add((int) model.getGarden().getSections().get(ind).toColor().getBlue());
		return c;
	}
	
	/**
	 * get a an arraylist of conditions in the current garden
	 * @return an arraylist of conditions
	 */
	public ArrayList<Conditions> getConditions() {
		return model.getGarden().getSections();
	}
	
	/**
	 * get all the plants in the garden 
	 * @return a hashmap of plants in garden
	 */
	public HashMap<String, PlacedPlant> getPlacedPlants() {
		return model.getGarden().placedPlants;
	}
	
	/**
	 * count the number of woody plants in garden
	 * @return integer, number of plants
	 */
	public int countWoody()  {
		int wood = 0;
		for (PlacedPlant p : model.getGarden().getPlants()) {
			if (p.getSpecies().isWoody()) {
				wood = wood + 1;
			}
		}
		return wood;
	}
	
	/**
	 * count the number of herbaceous plants in garden
	 * @return int, the number of plants
	 */
	public int countHerbaceous() {
		int herb = 0;
		for (PlacedPlant p : model.getGarden().getPlants()) {
			if (! p.getSpecies().isWoody()) {
				herb = herb + 1;
			}
		}
		return herb;
	}
	
	/*public static Popup createWoodyPopup() {
		final Popup p = new Popup();
		p.setAutoFix(true);
		p.setAutoHide(true);
		p.setHideOnEscape(true);
		Label l = new Label("Woody vs Herbaceous in your garden: ");
		l.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				p.hide();
			}
		});
		return p;
	}
	public static void woodyPopup(final Stage stage) {
		final Popup p = createWoodyPopup();
		p.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				p.setX(stage.getX() + stage.getWidth() / 2 - p.getWidth() / 2);
	            p.setY(stage.getY() + stage.getHeight() / 2 - p.getHeight() / 2);
			}
		});
		p.show(stage);
	}*/
	
	
}
