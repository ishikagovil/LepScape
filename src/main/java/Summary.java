import java.io.File;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Summary extends View {
	public ArrayList <Button> b1;
	public Controller ic;
	Pane main;
	Canvas canvas;
	
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		// set up the stage with different area
		super(stage, c, manageView);
		border = new BorderPane();
		
		
        // set up a Horizon Box pane for the bottom of the page
        HBox box = new HBox();
        box.setStyle("-fx-background-color: steelblue");
        box.setSpacing(15);
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setAlignment(Pos.CENTER_RIGHT);
        border.setBottom(box);
                
        // make buttons for Lepedia, Download and Create New Garden
        b1 = new ArrayList <Button>();
        //Button lep = new Button("Lepedia");
        //lep.setPrefSize(100, 30);
        //b1.add(lep);
        b1.add(addNextButton("Lepedia", "Lepedia"));
        Button download = new Button("Download");
        download.setPrefSize(100, 30);
        download.setOnAction(e -> {
        	FileChooser file = new FileChooser();
        	file.setTitle("Download File");
        	File file1 = file.showSaveDialog(stage);
        });
        b1.add(download);
        b1.add(addNextButton("Create New Garden", "Restart"));
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
        buttons.add(addNextButton("Gallery", "Gallery"));
        buttons.add(addNextButton("Back", "GardenDesign"));
        buttons.get(0).setPrefSize(100, 30);
        buttons.get(1).setPrefSize(100, 30);
        Button suggested = new Button("Suggested");
        suggested.setPrefSize(100, 30);
        buttons.add(suggested);
        buttons.add(addNextButton("Learn More","LearnMore"));
        buttons.get(2).setPrefSize(100, 30);
        vb1.getChildren().addAll(buttons);
        vb1.setAlignment(Pos.TOP_RIGHT);
        /*buttons.add(navi.saved);
        buttons.add(navi.settings);
        buttons.add(navi.suggested);
        */
       
       // center pane for the garden design
       StackPane sp1 = new StackPane();
       sp1.setStyle("-fx-border-color: chocolate; -fx-border-width: 5px; -fx-background-color: lightblue");
       border.setCenter(sp1);
       
       // get the total leps supported and cost 
       PlantSpecies p1 = new PlantSpecies();
      /* Image lepPic = new Image(getClass().getResourceAsStream("/butterfly.png"));
       ImageView lepView = new ImageView(lepPic);
       lepView.setPreserveRatio(true);
       lepView.setFitHeight(50);
       Label lepCount = new Label("0");
       lepCount.setTextAlignment(TextAlignment.LEFT);
       lepCount.setFont(Font.font(null, FontWeight.BOLD, 20));
       lepCount.setGraphic(lepView);
       
       Image cost = new Image(getClass().getResourceAsStream("/dollar.png"));
       ImageView costView = new ImageView(cost);
       costView.setPreserveRatio(true);
       costView.setFitHeight(50);
       Label costTotal = new Label("" + ic.getBudget());
       costTotal.setGraphic(costView);
       */
       
       Text title = new Text("Summary");
       title.setFont(Font.font(null, FontWeight.BOLD, 30));
       title.setTextAlignment(TextAlignment.LEFT);
       Text lepCount = new Text("Number of leps supported: " + p1.getLepsSupported());
       Text totalCost = new Text("Total cost: " + p1.getCost());
       totalCost.setTextAlignment(TextAlignment.LEFT);
       
       // load butterfly animation
       ImageView iv1 = new ImageView();
       Image butterfly = new Image(getClass().getResourceAsStream("/butterfly.png"));
      // Image flapping = new Image(getClass().getResourceAsStream("/flapping.png"));
       iv1.setImage(butterfly);
       iv1.setPreserveRatio(true);
       iv1.setFitHeight(30);
       
       Duration duration = Duration.minutes(2);
       TranslateTransition translation = new TranslateTransition(duration, iv1);
       translation.setByX(100);
       translation.setAutoReverse(true);
       sp1.getChildren().add(iv1);
       translation.play();
       
       TilePane tp1 = new TilePane();
       tp1.setPadding(new Insets(10));
       tp1.setStyle("-fx-background-color: lavender");
       tp1.setAlignment(Pos.TOP_LEFT);
       tp1.getChildren().addAll(title, lepCount, totalCost);
       border.setRight(tp1);
       
       //Add garden view
       main = addCanvas();
       border.setCenter(main);
       border.setCenter(main);
    }
/**
 * Makes the canvas so the previously set garden outline can be displayed
 * Canvas then places inside a pane
 * @return the created pane
 */
	public Pane addCanvas() {
		Pane gardenDesign = new Pane();
		gardenDesign.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
		gardenDesign.getChildren().add(canvas);
	
		canvas.widthProperty().bind(gardenDesign.widthProperty());
		canvas.heightProperty().bind(gardenDesign.heightProperty());
	
		canvas.widthProperty().addListener(e -> manageView.redrawImage());
		canvas.heightProperty().addListener(e -> manageView.redrawImage());
		return gardenDesign;
	}
}