import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Gallery extends View{
	public ArrayList<Button> multiview;
	public Button back;
	public Button sort;
	public TextField search;
	
	public Gallery(Stage stage, Controller c) {
		super(stage, c);
		border = new BorderPane();
		border.setStyle("-fx-background-color: #F3B5D7");
	}
}
