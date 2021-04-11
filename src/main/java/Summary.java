import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Summary extends View{
	public Navigation navi;
	public Button openLepedia;
	public Button download;
	public Summary(Stage stage, Controller c) {
		super(stage, c);
		//remove this line before merging
		border = new BorderPane();

	}
	public void render() {}
}
