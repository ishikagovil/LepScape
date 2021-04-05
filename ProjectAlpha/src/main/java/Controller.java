import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
	public View view;
	public Model model;
	
	public Controller(View view) {this.view = view;}
	
	//Methods that render different Views depending on which buttons are pressed
	public void designNewPlot() {} //renders PlotDesign when initialize button is pressed on Start
	public void conditionsDesignate() {} //renders ConditionScreen
	public void goToGallery() {} //renders Gallery and shows all saved gardens from Model
	public void designGarden() {} //renders GardenDesign
	public void summaryScreen() {} //renders Summary 
	public void openLepedia() {} //renders Lepedia and shows current leps in garden from Model
	
	//Methods used when user is designing new plot and inputting conditions
	public void readyToDraw(String shape) {} //method takes a string to show which shape (or freehand) will be drawn by user
	public void onDrawingPlot() {} //User is dragging mouse to create boundary, this method updates view
	public void onFinishPlot() {} //User releases mouse, boundary is created and updates Garden's outline field
	public void getBoundaries() {} //Gets boundaries of garden and sends to View when rendering the ConditionScreen
	public void readyToSection() {} //User has clicked on sectioning tool, controller updates view and model creates new Condition object
	public void onSectioning() {} //User begins to paint boundary, model calls updateOutlineSection and view is updated 
	public void onFinishSection() {} //User lifts mouse, controller calls displayConditionsOptions
	public void displayConditionsOptions() {} //Renders the view to display condition options
	public void onSetConditions() {} //After conditions are set with the slider, then user hits save and conditions are updated in Model
	
	//Methods used when user is designing their garden
	public void onPlantClicked() {} //Gets plant info from model and passes information to view
	public void onPlantDragged() {} //Handlers for dragging and dropping plant in view
	public void displayValidPlantLocation() {} //Called when user is dropping a plant, if it is not valid, it tells the view and colors plant red. Otherwise plant is green
	public void onPlantRelease() {} //placePlant() is called in model and updated cost/leps are returned. Calls displayNewGarden()
	public void displayNewGarden() {} //Called after a plant is dropped and view is created to show that plant and updates Basket
	public void getCompostInfo() {} //Called when user clicks on the compost bin, which gets info about deleted plants and sends to View
	
	
	//Methods that provide other feedback when buttons are pressed
	public void loadGarden() {} // takes garden information stored in Model and renders GardenDesign 
	public void downloadGarden() {} //gets info from model, puts it in a pdf, downloads it to computer
	public void navigationDisplay() {} //displays navigation pane on the displays that need it (GardenDesign, Summary screens)
	
	//Helper methods 
	public void getRecommendedPlants() {} //when designGarden is called, this method is also called to intialize the optimal garden	
}
