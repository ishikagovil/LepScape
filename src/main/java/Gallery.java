import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

		// back button 
		HBox hb1 = new HBox();
		hb1.setAlignment(Pos.BASELINE_LEFT);
		hb1.setPadding(new Insets(20));
		back = new Button("Back");
		back.setPrefSize(100, 30);;
		setOnMouse(back);
		back.setOnAction(controller.getHandlerforClicked("Start"));
		hb1.getChildren().add(back);
		border.setBottom(hb1);
		
		// get the summary pane on the right to display lep + cost
		VBox vb1 = new VBox();
		vb1.setPadding(new Insets(20));
		vb1.setSpacing(15);
		vb1.setStyle("-fx-background-color: lavender");
		Text summary = new Text("Summary");
		summary.setTextAlignment(TextAlignment.LEFT);
		summary.setFont(Font.font(null, FontWeight.BOLD, 30));
		vb1.getChildren().add(summary);
		border.setRight(vb1);
		
		// make scrollable screen with scroll bar
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		AnchorPane ap1 = new AnchorPane();
		ap1.setStyle("-fx-background-color: lightblue; -fx-border-color: chocolate; -fx-border-width: 5px");
		ap1.getChildren().add(scroll);
		AnchorPane.setTopAnchor(scroll, 0d);
		AnchorPane.setRightAnchor(scroll, 0d);
		AnchorPane.setBottomAnchor(scroll, 0d);
		border.setCenter(ap1);
		
		// make title of the page
		HBox hb2 = new HBox();
		hb2.setPadding(new Insets(20));
		Text title = new Text("Gallery");
		title.setTextAlignment(TextAlignment.LEFT);
		title.setFont(Font.font(null, FontWeight.BOLD, 40));
		hb2.getChildren().add(title);
		border.setTop(hb2);
		
	}
	
}
