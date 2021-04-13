import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LearnMore extends View{

	public LearnMore(Stage stage, Controller c,ManageViews manageView) {
		super(stage, c,manageView);
		border = new BorderPane();
		Label title = new Label("Learn More");
		title.setFont(new Font("Arial", 24));
		border.setTop(title);
		border.setAlignment(title, Pos.CENTER);
		Button done = addNextButton("Done", "GardenDesign");
		border.setBottom(done);
		border.setAlignment(done, Pos.CENTER);
		
	}
	
	

}
