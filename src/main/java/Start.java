import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<Button> buttons;
	
	public Start(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
	}
}