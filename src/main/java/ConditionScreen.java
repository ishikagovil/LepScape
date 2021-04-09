import java.util.*;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ConditionScreen extends View {
	public ArrayList<Button> switchScreens; //back, next, save,
	public Slider soilType;
	public Slider moisture;
	public Slider sunlight;
	public TextField budget;

	public ConditionScreen(Stage stage, Controller c) {
		super(stage,c);
		border = new BorderPane();

		border.setStyle("-fx-background-color: #F3B5D7");
        //gc.restore(); //possible to pass the gc from PlotDesign and restore the drawing
	}
}
