import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Summary extends View{
	public Navigation navi;
	public Button openLepedia;
	public Button download;
	
	public Summary(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
	}
}
