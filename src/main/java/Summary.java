import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.*;

public class Summary extends View {
	public Controller ic;
	Pane main;
	Canvas canvas;
	
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		// set up the stage with different area
		super(stage, c, manageView);
		border = new BorderPane();
		border.setBottom(addBottomHBox());
		border.setLeft(addNavigationVBox());
		border.setCenter(addCenterPane());
		border.setRight(addInfoPane());
		
		/*
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
	       */
		
       //main = addCanvas();
		border.setCenter(main);  
       
       
    }
	
	public HBox addBottomHBox() {
		HBox box = new HBox();
        box.setStyle("-fx-background-color: steelblue");
        box.setSpacing(15);
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(addBottomButtons());
        
        return box;
	}
	
	public ArrayList<Button> addBottomButtons() {
		// make buttons for Lepedia, Download and Create New Garden
        ArrayList <Button> bottomButtons = new ArrayList <Button>();
        Button download = new Button("Download");
        download.setPrefSize(buttonWidth, buttonHeight);
        download.setOnAction(e -> {
        	FileChooser file = new FileChooser();
        	file.setTitle("Download File");
        	File file1 = file.showSaveDialog(stage);
        });
        bottomButtons.add(download);
        bottomButtons.add(addNextButton("Create New Garden", "Restart"));
        bottomButtons.get(1).setPrefSize(120, buttonHeight);
        return bottomButtons;
	}
	
	public VBox addNavigationVBox() {
		VBox sideVBox = new VBox();
        sideVBox.setStyle("-fx-background-color: lavender");
        sideVBox.setSpacing(15);
        sideVBox.setPadding(new Insets(20));
        
        sideVBox.getChildren().addAll(addNavigationButtons());
        sideVBox.setAlignment(Pos.TOP_RIGHT);
        return sideVBox;
	}
	
	public ArrayList<Button> addNavigationButtons() {
		ArrayList <Button> buttons = new ArrayList<Button>();
        buttons.add(addNextButton("Back", "GardenDesign"));
        buttons.add(addNextButton("Lepedia", "Lepedia"));
        buttons.add(addNextButton("Learn More","LearnMore"));
        Button saveGarden = new Button("Save");
        saveGarden.setOnAction(controller.getHandlerforSummarySave());
        buttons.add(saveGarden);
        return buttons;
	}
	
	public StackPane addCenterPane() {
		StackPane centerPane = new StackPane();
		centerPane.setStyle("-fx-border-color: chocolate; -fx-border-width: 5px; -fx-background-color: lightblue");
		
		return centerPane;
	}
	
/**
 * Makes the canvas so the previously set garden outline can be displayed
 * Canvas then places inside a pane
 * @return the created pane
 */
	public void addCanvas() {
		Pane gardenDesign = new Pane();
		gardenDesign.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
//		canvas = new Canvas();
//		canvas.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
//		gc = canvas.getGraphicsContext2D();
//		gardenDesign.getChildren().add(canvas);
//	
//		canvas.widthProperty().bind(gardenDesign.widthProperty());
//		canvas.heightProperty().bind(gardenDesign.heightProperty());
//	
//		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
//		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		ImageView iv = new ImageView(manageView.savedImg);
		iv.setPreserveRatio(true);
		iv.fitWidthProperty().bind(gardenDesign.widthProperty());
		iv.fitHeightProperty().bind(gardenDesign.widthProperty());
		gardenDesign.getChildren().add(iv);
		border.setCenter(gardenDesign);
	}
	
	public TilePane addInfoPane() {
		TilePane rightPane = new TilePane();
	    rightPane.setPadding(new Insets(10));
	    rightPane.setStyle("-fx-background-color: lavender");
	    
	    Text title = new Text("Summary");
	    title.setFont(Font.font(null, FontWeight.BOLD, 30));
	    
	    Image lepCount = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image cost = new Image(getClass().getResourceAsStream("/dollar.png"));
		ImageView lepIV = new ImageView(lepCount);
		lepIV.setPreserveRatio(true);
		lepIV.setFitHeight(50);
		ImageView costIV = new ImageView(cost);
		costIV.setPreserveRatio(true);
		costIV.setFitHeight(50);
		
		rightPane.getChildren().addAll(title, lepIV, costIV);
		return rightPane;
	}


	
}