import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConditionScreen extends View {
	public Button back;
	public Button next;
	public Slider soilType;
	public Slider moisture;
	public Slider sunlight;
	public TextField budget;
	public Button save;
	
	public ConditionScreen(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
	}
}
