import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

import java.util.*;

public class Gallery extends View{
	public ArrayList<Button> multiview;
	public Button back;
	public Button sort;
	public TextField search;
	
	public Gallery(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		border.setStyle("-fx-background-color: #F3B5D7");
		HBox hb1 = new HBox();
		hb1.setStyle("-fx-background-color: black");
		border.setBottom(hb1);
		
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		
		StackPane sp1 = new StackPane();
		sp1.getChildren().add(scroll);
		border.setCenter(sp1);
	}
}
