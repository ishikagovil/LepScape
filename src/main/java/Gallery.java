import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;

import java.util.*;

public class Gallery extends View{
	public ArrayList<Button> multiView;
	public Button back;
	public Button sort;
	public TextField search;
	
	public Gallery(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		scroll.setBlockIncrement(100);
		border.setStyle("-fx-background-color: #F3B5D7");
		border.getChildren().add(scroll);
		
		multiView = new ArrayList<Button>();
		
	}
}
