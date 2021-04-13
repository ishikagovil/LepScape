import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
<<<<<<< HEAD
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
=======
import javafx.scene.layout.BorderPane;
>>>>>>> a49086ae286d481d0ec616efebc47eba90941dfc
import javafx.stage.Stage;

public class Summary extends View{
	public Navigation navi;
	public Button openLepedia;
	public Button download;
<<<<<<< HEAD
	public Button startNew;
	
	public ArrayList<Button> buttons; // Settings, suggested, saved, learn more
	
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		AnchorPane ap1 = new AnchorPane();
		border = new BorderPane();
		Canvas canvas = new Canvas();
		border.getChildren().add(canvas);
		border.setStyle("-fx-background-color: white");
		HBox hb1 = new HBox();
		hb1.setPadding(new Insets(20, 10, 20, 10));
		
		// make & add Lepedia and Download buttons
		openLepedia = new Button("Lepedia");	
		download = new Button("Download)"); 
		startNew = new Button("Start New Garden");
		hb1.getChildren().addAll(openLepedia, download, startNew);
		
		// add the Lepedia and Download buttons to the bottom right corner of the anchor pane
		ap1.getChildren().add(hb1);
		AnchorPane.setBottomAnchor(hb1, 8.0);
		AnchorPane.setRightAnchor(hb1, 5.0);
		AnchorPane.setTopAnchor(hb1, 10.0);
		border.setBottom(ap1);
		
		// flow pane created to hold the navigation bar
		FlowPane flow1 = new FlowPane();
		flow1.setStyle("-fx-border-color: black; -fx-border-width: 5px' -fx-background-color: lavender");
		border.setLeft(flow1);
		
		// make the button navigation buttons on the upper left corner of the screen
		navi = new Navigation(stage, c);
		
		// add the buttons to the navigation bar
		buttons = new ArrayList<Button>();
		buttons.add(navi.learnMore);
		buttons.add(navi.suggested);
	//	buttons.add(navi.saved);
	//	buttons.add(navi.settings);
		
		flow1.getChildren().addAll(buttons);
		
		VBox vb1 = new VBox();
		vb1.setStyle("-fx-border-color: black; -fx-border-width: 5px; -fx-background-color: aliceblue");
		Text title = new Text("Summary");
		title.setFont(Font.font(20));
		vb1.getChildren().add(title);
		Garden garden = new Garden();
		Text lepCount = new Text("Number of leps supported: " + garden.getNumLeps());
		Text totalCost = new Text("The total cost: " + garden.getCost());
		vb1.getChildren().addAll(lepCount, totalCost);
		border.setRight(vb1);
		
		
=======
	public Summary(Stage stage, Controller c) {
		super(stage, c);
		//remove this line before merging
		border = new BorderPane();

>>>>>>> a49086ae286d481d0ec616efebc47eba90941dfc
	}
	public void render() {}
}
