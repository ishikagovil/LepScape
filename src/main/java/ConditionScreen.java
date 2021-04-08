import java.util.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConditionScreen extends View {
	public ArrayList<Button> switchScreens; //back, next, save,
	public Slider soilType;
	public Slider moisture;
	public Slider sunlight;
	public TextField budget;
	
	public ConditionScreen(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, c);
	}
}
