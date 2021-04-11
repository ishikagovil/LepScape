import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Summary extends View{
	public Navigation navi;
	public Button openLepedia;
	public Button download;
<<<<<<< HEAD
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
=======
	public Summary(Stage stage, Controller c) {
		super(stage, c);
		//remove this line before merging
		border = new BorderPane();
>>>>>>> feat: Add compost bin

	}
	public void render() {}
}
