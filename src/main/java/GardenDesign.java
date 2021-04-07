import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GardenDesign extends View {
	public Navigation navi;
	public Button compare;
	public Button save;
	
	public GardenDesign(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
	}
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	public void showCompostBin() {}
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}
