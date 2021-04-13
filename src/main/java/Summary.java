import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Summary extends View {
	public Navigation navi;
	public ArrayList <Button> b1;
	
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		// set up the stage with different area
		super(stage, c, manageView);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		border.getChildren().add(canvas);
<<<<<<< HEAD

        // set up a Horizon Box pane for the bottom of the page
        HBox box = new HBox();
        box.setStyle("-fx-background-color: steelblue");
        box.setSpacing(15);
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setAlignment(Pos.CENTER_RIGHT);
        border.setBottom(box);
                
        // make buttons for Lepedia, Download and Create New Garden
        b1 = new ArrayList <Button>();
        Button lep = new Button("Lepedia");
        lep.setPrefSize(100, 30);
        b1.add(lep);
        Button download = new Button("Download");
        download.setPrefSize(100, 30);
        b1.add(download);
        b1.add(addNextButton("Create New Garden", "PlotDesign"));
        box.getChildren().addAll(b1);
        
        // make a vertical box pane for the navigation button
        VBox vb1 = new VBox();
        vb1.setStyle("-fx-background-color: lavender");
        vb1.setSpacing(15);
        vb1.setPadding(new Insets(20));
        border.setLeft(vb1);
        
        // add buttons of navigation on the upper right corner
        ArrayList <Button> buttons = new ArrayList<Button>();
        // need to use Navigation button instead
        Button saved =  new Button("Saved");
        saved.setPrefSize(100, 30);
        Button settings = new Button("Settings");
        settings.setPrefSize(100, 30);
        Button suggested = new Button("Suggested");
        suggested.setPrefSize(100, 30);
        Button learnMore = new Button("Learn More");
        learnMore.setPrefSize(100, 30);
        buttons.add(saved);
        buttons.add(settings);
        buttons.add(suggested);
        buttons.add(learnMore);
        vb1.getChildren().addAll(buttons);
        vb1.setAlignment(Pos.TOP_RIGHT);
        /*buttons.add(navi.saved);
        buttons.add(navi.settings);
        buttons.add(navi.suggested);
        */
        
       StackPane sp1 = new StackPane();
       sp1.setStyle("-fx-border-color: brown; -fx-border-width: 5px; -fx-background-color: lightblue");
       border.setCenter(sp1);
       
       PlantSpecies p1 = new PlantSpecies();
       VBox vb2 = new VBox();
       /*Rectangle data = new Rectangle(100.0, 50.0);
       data.setFill(Color.AQUAMARINE);
       */
       Text title = new Text("Summary");
       title.setFont(Font.font(null, FontWeight.BOLD, 16));
       Text lepCount = new Text("Number of leps supported: " + p1.getLepsSupported());
       Text totalCost = new Text("Total cost: " + p1.getCost());
       vb2.setAlignment(Pos.TOP_LEFT);
       vb2.setStyle("-fx-background-color: lavender");
       vb2.setPadding(new Insets(20));
       vb2.getChildren().addAll(title, lepCount, totalCost);
       border.setRight(vb2);
       
       /*
       // load butterfly animation
       ImageView v1 = new ImageView();
       Image butterfly = new
       //Image butterfly = new Image(getClass().getResourceAsStream(""));
       v1.setImage(butterfly);
       */
=======
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
		
		
>>>>>>> fa288b6e8d06b153e82aac951d253ea2b912e2f1
	}
	public void render() {}
}
