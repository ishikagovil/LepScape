import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<ImageView> buttons;
	Canvas canvas;
	/**
	 * @author Kimmy Huynh
	 */
	
	/**
	 * Creates a simple introductory screen with two buttons, new plot and gallery of saved plots
	 * @param Stage
	 * @param Controller
	 * @param ManageViews
	 */
	public Start(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);     
		canvas = new Canvas(this.manageView.getScreenWidth(), this.manageView.getScreenHeight());
		border = new BorderPane();
		border.getChildren().add(canvas);
        addBackgroundImage();
        border.setCenter(addButtonPane());
	}

	public void addBackgroundImage() {
		Image bgImage = new Image(getClass().getResourceAsStream("/lep-background.jpg"),  this.manageView.getScreenWidth(), this.manageView.getScreenHeight(), false, false);
        gc = canvas.getGraphicsContext2D();	
        gc.getCanvas().setWidth(this.manageView.getScreenWidth());
        gc.getCanvas().setHeight(this.manageView.getScreenHeight());
        gc.drawImage(bgImage, 0, 0);
	}
	
	/** 
	 * make a pane to the title and the buttons on the start screen
	 * @return a Vertical Box pane 
	 */
	public VBox addButtonPane() {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        Text title = new Text("Lepscape");
		title.setFont(Font.font("Segoe Script", FontWeight.EXTRA_BOLD, 100));
        box.getChildren().addAll(title, makeButtons());
        return box;
	}
	
	/**
	 * make buttons to go to gallery screen or start a new garden design
	 * @return Horizontal Box pane
	 */
	public HBox makeButtons() {
		HBox box = new HBox();
		box.setSpacing(15);
		box.setAlignment(Pos.CENTER);
		buttons = new ArrayList<>();
		buttons.add(addNextButton( "new", "PlotDesign"));
		buttons.add(addNextButton("gallery", "Gallery"));
		buttons.get(1).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
	       	this.manageView.setCalledFromStart(true);
	    });
		box.getChildren().addAll(buttons);
		return box;
	}
}
	