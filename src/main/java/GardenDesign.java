import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GardenDesign extends View{
	public Navigation navi;
	public Button compare;
	public Button save;

	public GardenDesign(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		border.getChildren().add(canvas); 
        gc = canvas.getGraphicsContext2D();	
	}
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	public void showCompostBin() {}
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}
