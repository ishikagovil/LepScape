import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import javafx.embed.swing.SwingFXUtils;

import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;


public class Summary extends View {
	final int STANDARD_IMAGEVIEW = 100;
	final int NORMALCOMPOST = 75;
	final int ENTERCOMPOST = 85;
	final int INFO_IV_SIZE = 50;
	final int HBOX_SPACING = 20;
	final int FONTSIZE = 20;
	public Controller ic;
	Pane main;
	Canvas canvas;
	ComboBox<String> cb;

/**
 * set up a stage and border pane to hold other panes
 * @param stage
 * @param c
 * @param manageView
 */
	public Summary(Stage stage, Controller c, ManageViews manageView) {
		// set up the stage with different area
		super(stage, c, manageView);
		border = new BorderPane();
		border.setBottom(addBottomHBox());
		border.setLeft(addNavigationVBox());
		//border.setCenter(addCenterPane());
		
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
	
/**
 * add bottom pane to hold the buttons for create new garden and download
 * @return the bottom pane created 
 */
	public HBox addBottomHBox() {
		HBox box = new HBox();
        box.setStyle("-fx-background-color: steelblue");
        box.setSpacing(15);
        box.setPadding(new Insets(15, 12, 15, 12));
        box.setAlignment(Pos.CENTER_RIGHT);
        box.getChildren().addAll(addBottomButtons());
        return box;
	}
	
/**
 * create an array list of buttons of download and create new garden
 * @return an array list of buttons
 */
	public ArrayList<ImageView> addBottomButtons() {
		// make buttons for Lepedia, Download and Create New Garden
        ArrayList <ImageView> bottomButtons = new ArrayList <>();
        ImageView download = new ImageView(this.manageView.buttonImages.get("download"));
        setOnMouse(download, "download");
        download.setOnMouseClicked(e -> {
        	FileChooser file = new FileChooser();
        	file.setTitle("Download File");
        	File file1 = file.showSaveDialog(stage);
        	file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"), new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        });
        WritableImage wim = manageView.savedImg;
		File f = new File("src/main/resources/gardenImage");
		//BufferedImage b = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wim,null),"png", f);
		}
		catch (Exception s){

		}
        // https://www.youtube.com/watch?v=CuK4urJtoyA
        // https://www.youtube.com/watch?v=Mef0Thtrjsc
        download.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		Document document = new Document();
				try {
					PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("GardenDesign.pdf"));
			        document.open();
			        document.add(new Paragraph("A Hello World PDF document."));
			        WritableImage wi = new WritableImage((int) gc.getCanvas().getWidth(), (int) gc.getCanvas().getHeight());
			        gc.getCanvas().snapshot(null, wi);
			        BufferedImage bi = SwingFXUtils.fromFXImage((Image) wi, null);
			        ByteArrayOutputStream bo = new ByteArrayOutputStream();
			        ImageIO.write(bi, "png", bo);
			        com.itextpdf.text.Image im = com.itextpdf.text.Image.getInstance(bo.toByteArray());
			        document.add(im);
			        document.close();
			        writer.close();
				}
				catch (DocumentException | IOException e) {
					e.printStackTrace();
				}
        	}
        });
        bottomButtons.add(download);
        bottomButtons.add(addNextButton("next", "Restart"));
        return bottomButtons;
	}
	
/**
 * create a vertical box pane to hold the navigation buttons
 * @return the vertical pane
 */
	public VBox addNavigationVBox() {
		VBox sideVBox = new VBox();
        sideVBox.setStyle("-fx-background-color: lavender");
        sideVBox.setSpacing(15);
        sideVBox.setPadding(new Insets(20));
        sideVBox.getChildren().addAll(addNavigationButtons()); 
        sideVBox.setAlignment(Pos.TOP_RIGHT);
        return sideVBox;
	}

/**
 * create an array list of navigation buttons
 * @return an array list of buttons
 */
	public ArrayList<ImageView> addNavigationButtons() {
		ArrayList <ImageView> buttons = new ArrayList<>();
        buttons.add(addNextButton("back", "GardenDesign"));
        buttons.add(addNextButton("lepedia", "Lepedia"));
        buttons.add(addNextButton("learnmore","LearnMore"));
        ImageView saveGarden = new ImageView(this.manageView.buttonImages.get("save"));
        setOnMouse(saveGarden, "save");
        saveGarden.setOnMouseClicked(controller.getHandlerforSummarySave());
        buttons.add(saveGarden);
        buttons.add(addNextButton("gallery","Gallery"));
        buttons.get(4).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
        	this.manageView.setCalledFromStart(false);
        });
        return buttons;
	}
	
	public TextField gardenTitlePopup() {
		final Stage gardenTitle = new Stage();
		gardenTitle.initModality(Modality.APPLICATION_MODAL);
		gardenTitle.initOwner(stage);
		Label text = new Label("Set a title for your garden");
		text.setFont(new Font("Andale Mono", FONTSIZE));
		text.setStyle("-fx-font-size: 16; -fx-text-fill: white");
		Label instruction = new Label("Press enter to set new budget or the X if you are done");
		TextField titleField = new TextField("Enter title");
		titleField.setMaxWidth(STANDARD_IMAGEVIEW);
		BorderPane border = new BorderPane();
		border.setTop(text);
		BorderPane.setAlignment(text,Pos.CENTER);
		border.setCenter(titleField);
		border.setBottom(instruction);
		BorderPane.setAlignment(instruction,Pos.CENTER);
		border.setStyle(" -fx-background-color: #8C6057; -fx-padding: 10; -fx-border-color: #5C5346; -fx-border-width: 5;");
		Scene popUpScene = new Scene(border,450,STANDARD_IMAGEVIEW);
		gardenTitle.setScene(popUpScene);
		gardenTitle.show();
		
		return titleField;
	}

/**
 * create a center pane to hold the garden design 
 * @return the center pane
 */
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
		gardenDesign.setStyle("-fx-background-color: lavender");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
//		gardenDesign.getChildren().add(canvas);
	
		canvas.widthProperty().bind(gardenDesign.widthProperty());
		canvas.heightProperty().bind(gardenDesign.heightProperty());
	
		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		ImageView iv = new ImageView(manageView.savedImg);
		iv.setPreserveRatio(true);
		iv.fitWidthProperty().bind(gardenDesign.widthProperty());
//		iv.fitHeightProperty().bind(gardenDesign.heightProperty());
		gardenDesign.getChildren().add(iv);
		border.setCenter(gardenDesign);
		border.setAlignment(gardenDesign, Pos.CENTER);
	}
	
/**
 * create a tilepane to hold information about the garden with updated cost and leps count
 * @return
 */
	public void updateLepandCost(double cost, int lepCount) {
		VBox rightPane = new VBox();
	    rightPane.setPadding(new Insets(10));
	    rightPane.setStyle("-fx-background-color: lavender");
	    Text title = new Text("Summary");
	    title.setFont(Font.font("Andale Mono", FontWeight.BOLD, 30));
	    
		HBox box1 = new HBox();
	    Image lepIm = new Image(getClass().getResourceAsStream("/butterfly1.png"));
	    ImageView lepIV = new ImageView(lepIm);
		lepIV.setPreserveRatio(true);
		lepIV.setFitHeight(40);
		Label leps = new Label("" + lepCount);
		leps.setFont(new Font("Andale Mono", 16));
		Label budgetCount = new Label("" + cost);
		budgetCount.setFont(new Font("Andale Mono", 16));
		leps.setGraphic(lepIV);
		box1.getChildren().addAll(lepIV, leps);
		
		HBox box = new HBox();
		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
		ImageView costIV = new ImageView(dollar);
		budgetCount.setGraphic(costIV);
		costIV.setPreserveRatio(true);
		costIV.setFitHeight(40);
		box.getChildren().addAll(costIV, budgetCount);
		
		VBox vb = new VBox();
		Label des = new Label("Use the drop down to choose the statistical data you want to view for this garden: ");
		String options[] = {"Top 5 lep-supported plants", "Herbaceous vs. woody"};
		cb = new ComboBox<String>(FXCollections.observableArrayList(options));
		cb.setStyle("-fx-font: \"Andale Mono\"");
		//cb.setOnAction(controller.getHandlerForSummaryPie(cb));
		vb.getChildren().addAll(des, cb);
		vb.setAlignment(Pos.CENTER);
		
		rightPane.getChildren().addAll(title, box1, box, vb);
		border.setRight(rightPane);
	}
	
	public void herbaceousVSWoody() {
		
		
	}
}