import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<Button> buttons;
	
	public Start(Stage stage, Controller c) {
		super(stage, c);        
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		border.getChildren().add(canvas);
        border.setStyle("-fx-background-color: #94DF86");
        HBox box = new HBox();
        box.setSpacing(15);
        border.setCenter(box);
 
        //Populate button array
		buttons = new ArrayList<Button>();
		buttons.add(addNextButton( "New Garden", "PlotDesign"));
		buttons.add(addNextButton("My Gallery", "Gallery"));
		box.getChildren().addAll(buttons); 
		box.setAlignment(Pos.CENTER);

	}
}