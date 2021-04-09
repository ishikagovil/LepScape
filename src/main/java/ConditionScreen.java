import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConditionScreen extends View {
	public Button back;
	public Button next;
	public Slider soilType;
	public Slider moisture;
	public Slider sunlight;
	public TextField budget;
	public Button save;

	public ConditionScreen(Stage stage, Controller c) {
		super(stage,c);
		//Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();

		//border.getChildren().add(gc.getCanvas()); 
		border.setStyle("-fx-background-color: #F3B5D7");
        //gc.restore();
	}
}
