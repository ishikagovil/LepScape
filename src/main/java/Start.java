import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<Button> buttons;
	
	/**
	 * @author Ishika Govil 
	 */
	
	/**
	 * Creates a simple introductory screen with two buttons, new plot and gallery of saved plots
	 * @param Stage
	 * @param Controller
	 * @param ManageViews
	 */
	public Start(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);     
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		border.getChildren().add(canvas);
        border.setStyle("-fx-background-color: #94DF86");
        border.setCenter(addButtonPane());
        
        Image bg = new Image(getClass().getResourceAsStream(""));
	}
	
	public HBox addButtonPane() {
		HBox box = new HBox();
        box.setSpacing(15);
        box.getChildren().addAll(makeButtons());
        box.setAlignment(Pos.CENTER);
        return box;
	}
	
	public ArrayList <Button> makeButtons() {
		buttons = new ArrayList<Button>();
		buttons.add(addNextButton( "New Garden", "PlotDesign"));
		buttons.add(addNextButton("My Gallery", "Gallery"));
		return buttons;
	}
}
	