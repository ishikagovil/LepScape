import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
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
		border.setTop(addTitle());
		border.setBottom(addBottomHBox());
		border.setLeft(addNavigationVBox());
		//main = new Pane();
		//border.setCenter(main);
		
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
	
	public HBox addTitle() {
		HBox box = new HBox();
		box.setPadding(new Insets(HBOX_SPACING));
		box.setStyle("-fx-background-color: #a69f98");
		Text title = new Text("Summary Screen");
		title.setFont(Font.font("Andale Mono", FontWeight.BOLD, INFO_IV_SIZE));
		box.getChildren().add(title);
		return box;
	}
	
/**
 * add bottom pane to hold the buttons for create new garden and download
 * @return the bottom pane created 
 */
	public HBox addBottomHBox() {
		HBox box = new HBox();
        box.setStyle("-fx-background-color: #8C6057");
        box.setSpacing(15);
        box.setPadding(new Insets(HBOX_SPACING));
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
        ImageView download = new ImageView(this.manageView.buttonImages.get("Download"));
        setOnMouse(download, "Download");
        // https://www.howtobuildsoftware.com/index.php/how-do/9Zf/itext-javafx-8-save-javafx-scrollpane-content-to-pdf-file
        download.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	@Override
        	public void handle(MouseEvent event) {
        		FileChooser fc = new FileChooser();
        		FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        		fc.getExtensionFilters().add(ext);
        		fc.setTitle("Download File");
        		File pdfFile = fc.showSaveDialog(stage);
        		try {
        			//WritableImage wi = controller.snapshotGarden();
        			//BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);
        			//BufferedImage bi = SwingFXUtils.fromFXImage(manageView.getGardenImag(), null);
        			FileOutputStream fo = new FileOutputStream(new File("GardenDesign.png"));
        			ImageIO.write(manageView.savedImg, "png", fo);
        			//ImageIO.write(bi, "png", fo);
        			fo.flush();
        			fo.close();
        			
        			com.itextpdf.text.Image im = com.itextpdf.text.Image.getInstance("GardenDesign.png");
        			Document doc = new Document(new com.itextpdf.text.Rectangle(im.getScaledWidth(), im.getScaledHeight()));
        			FileOutputStream fos = new FileOutputStream(pdfFile);
        			PdfWriter w = PdfWriter.getInstance(doc, fos);
        			
        			//PdfContentByte cb = w.getDirectContent();
        			//cb.moveTo(100, 200);
        			//cb.circle(FONTSIZE, FONTSIZE, FONTSIZE);
        			
        			//int ind = 0;
        			//for (Conditions c : controller.getConditions()) {
        				//cb.setRGBColorFillF(controller.conditionColor(ind).get(0), controller.conditionColor(ind).get(1), controller.conditionColor(ind).get(2));
        				//ind = ind + 1;
        			//}
        			doc.open();
        			
        			/*List plantList = new List(List.ORDERED);
        			for (PlantSpecies p : controller.lepsCount()) {
        				plantList.add(new ListItem(p.pdfDescription));
        			}
        			*/
        			doc.add(im);
        			//doc.add((Element) cb);
        			//doc.add(plantList);
        			fos.flush();
        			doc.close();
        			fos.close();
        			w.close();
        		}
        		catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        });
        bottomButtons.add(download);
        bottomButtons.add(addNextButton("New", "Restart"));
        return bottomButtons;
	}
	
/**
 * create a vertical box pane to hold the navigation buttons
 * @return the vertical pane
 */
	public VBox addNavigationVBox() {
		VBox sideVBox = new VBox();
        sideVBox.setStyle("-fx-background-color: #AFD5AA");
        sideVBox.setSpacing(15);
        sideVBox.setPadding(new Insets(HBOX_SPACING));
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
		buttons.add(addNextButton("Back", "GardenDesign"));
        buttons.add(addNextButton("Lepedia", "Lepedia"));
        ImageView saveGarden = new ImageView(this.manageView.buttonImages.get("Save"));
        setOnMouse(saveGarden, "Save");
        saveGarden.setOnMouseClicked(controller.getHandlerforSummarySave());
        buttons.add(saveGarden);
        buttons.add(addNextButton("Gallery","Gallery"));
        buttons.get(3).addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
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
	/*public StackPane addCenterPane() {
		StackPane centerPane = new StackPane();
	//	centerPane.setStyle("-fx-border-color: chocolate; -fx-border-width: 5px; -fx-background-color: lightblue");
		return centerPane;
	}*/
	
/**
 * Makes the canvas so the previously set garden outline can be displayed
 * Canvas then places inside a pane
 * @return the created pane
 */
	public void addCanvas() {
		Pane gardenDesign = new Pane();
		gardenDesign.setStyle("-fx-background-color: #F0F2EF");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color: #5C5346; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
		//gardenDesign.getChildren().add(canvas);
	
		canvas.widthProperty().bind(gardenDesign.widthProperty());
		canvas.heightProperty().bind(gardenDesign.heightProperty());
	
		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		ImageView iv = new ImageView(manageView.getGardenImag());
		iv.setPreserveRatio(true);
		///iv.fitWidthProperty().bind(gardenDesign.widthProperty());
		//iv.fitHeightProperty().bind(gardenDesign.heightProperty());
		gardenDesign.getChildren().add(iv);
		border.setCenter(gardenDesign);
		BorderPane.setAlignment(gardenDesign, Pos.CENTER);
	}
	
/**
 * create a tilepane to hold information about the garden with updated cost and leps count
 * @return
 */
	VBox rightPane;
	VBox vb;
	public void updateLepandCost(double cost, int lepCount) {
		rightPane = new VBox();
	    rightPane.setPadding(new Insets(HBOX_SPACING));
	    rightPane.setStyle("-fx-background-color: #AFD5AA");
	    //Text title = new Text("Summary");
	    //title.setFont(Font.font(null, FontWeight.BOLD, 30));
	    
		HBox box1 = new HBox();
	    Image lepIm = new Image(getClass().getResourceAsStream("/butterfly1.png"));
	    ImageView lepIV = new ImageView(lepIm);
		lepIV.setPreserveRatio(true);
		lepIV.setFitHeight(INFO_IV_SIZE);
		Label leps = new Label(""+lepCount);
		leps.setFont(new Font("Andale Mono", FONTSIZE));
		Label budgetCount = new Label(""+cost);
		budgetCount.setFont(new Font("Andale Mono", FONTSIZE));
		leps.setGraphic(lepIV);
		box1.getChildren().addAll(lepIV, leps);
		
		HBox box = new HBox();
		box.setPadding(new Insets(HBOX_SPACING));
		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
		ImageView costIV = new ImageView(dollar);
		budgetCount.setGraphic(costIV);
		costIV.setPreserveRatio(true);
		costIV.setFitHeight(INFO_IV_SIZE);
		box.getChildren().addAll(costIV, budgetCount);
		
		vb = new VBox();
		/*Label des = new Label("Use the drop down view statistics: ");
		String options[] = {"Top 5 lep-supported plants", "Herbaceous vs. woody", "helloooooo testing"};
		cb = new ComboBox<String>(FXCollections.observableArrayList(options));
		cb.setStyle("-fx-font: \"Andale Mono\"");
		vb.getChildren().addAll(des, cb);
		cb.setOnAction(controller.getHandlerForSummary(cb));
		*/
		Label w = new Label("Woody vs Herbaceous plant species in your garden: ");
		PieChart pc = isWoody();
		vb.getChildren().addAll(w, pc);
		
		Label label = new Label("Plant species support the most number of leps: ");
		//StackedBarChart sbc = mostLeps();
		//vb.getChildren().addAll(label);
		
		/*if (cb.getValue().equals(options[0])) {
			CategoryAxis x = new CategoryAxis();
			NumberAxis y = new NumberAxis();
			int i = 0;
			for (PlantSpecies p : controller.lepsSupported(cb)) {
				x.setLabel(p.getSpeciesName());
				y.setLabel("Leps supported by each plant");
				StackedBarChart sb = new StackedBarChart(x, y);
				sb.setTitle("Leps supported by top 5 plants in garden");
				XYChart.Series p1 = new XYChart.Series<>();
				p1.setName(controller.lepsSupported(cb).get(i).getSpeciesName());
				p1.getData().add(new XYChart.Data<>(controller.lepsSupported(cb).get(i).)
			}
		}
		*/
		vb.setAlignment(Pos.CENTER);
		
		rightPane.getChildren().addAll(box1, box, vb);
		border.setRight(rightPane); 
		//border.setRight(rightPane);
	}
	
	public PieChart isWoody() {
	/*	final Popup p = new Popup();
		p.setAutoFix(true);
		p.setAutoHide(true);
		p.setHideOnEscap(true);
		Label l = new Label("Woody vs Herbaceous in your garden: ");
		l.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				p.hide();
			}
		});*/
		//p.getContent().add(l);
		String type[] = {"Woody", "Hebaceous"};
		System.out.println("woody: " + controller.countWoody());
		int wood = controller.countWoody();
		int herb = controller.countHerbaceous();
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList(new PieChart.Data(type[0], wood), new PieChart.Data(type[1], herb));
		PieChart pc = new PieChart(data);
		pc.setLegendSide(Side.BOTTOM);
		pc.setClockwise(true);
		pc.setLabelsVisible(true);
		pc.setLegendVisible(true);
		pc.autosize();
		//pc.setMaxSize(, 250);
		return pc;
		//p.getContent().add(pc);
		//vb.getChildren().add(pc);
	}
	
	public StackedBarChart<String, Number> mostLeps() {
		CategoryAxis x = new CategoryAxis();
		x.setCategories(FXCollections.<String> observableArrayList(Arrays.asList(
				controller.lepsCount().get(0).getSpeciesName(), 
				controller.lepsCount().get(1).getSpeciesName(),
				controller.lepsCount().get(2).getSpeciesName(),
				controller.lepsCount().get(3).getSpeciesName(),
				controller.lepsCount().get(4).getSpeciesName())));
		NumberAxis y = new NumberAxis();
		y.setLabel("Number of leps supported");
		StackedBarChart<String, Number> sbc = new StackedBarChart<>(x, y);
		sbc.setTitle("Number of leps supported by top 5 plants");
		
		XYChart.Series<String, Number> s1 = new XYChart.Series<>();
		s1.setName(controller.lepsCount().get(0).getSpeciesName());
		s1.getData().add(new XYChart.Data<>(controller.getLepsName().get(0).getSpeciesName(), controller.lepsCount().get(0).getLepsSupported()));
		sbc.getData().add(s1);
		return sbc;
		
	}
	/*public void piePopup() {
		final Stage piePopup = new Stage();
		piePopup.setTitle("Woody vs. Herbaceous: ");
		String type[] = {"Woody", "Hebaceous"};
		System.out.println("woody: " + controller.countWoody());
		int wood = controller.countWoody();
		int herb = controller.countHerbaceous();
		ObservableList<PieChart.Data> data = FXCollections.observableArrayList(new PieChart.Data(type[0], wood), new PieChart.Data(type[1], herb));
		PieChart pc = new PieChart(data);
		pc.setStartAngle(180);
		pc.setLegendSide(Side.BOTTOM);
		pc.setClockwise(true);
		
		BorderPane b = new BorderPane();
		Label l = new Label("Woody vs Herbaceous in your garden: ");
		b.setTop(l);
		b.setCenter(pc);
		BorderPane.setAlignment(pc, Pos.CENTER);
		b.setStyle("-fx-background-color: #F0F2EF, -fx-padding: 10; -fx-border-color: #5C5346; -fx-border-width: 5;");
		Scene popupScene = new Scene(border, 500, STANDARD_IMAGEVIEW);
		piePopup.setScene(popupScene);
		piePopup.show();
	}*/
}