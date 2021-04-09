import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Summary extends View{
	public Navigation navi;
	public Button openLepedia;
	public Button download;
	public Summary(Stage stage, Controller c) {
		super(stage, c);
//		Canvas canvas = new Canvas(screenWidth, screenHeight);
//        root.getChildren().add(canvas);
//        stage.show();
	}
	public void render() {}
}
