import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ConditionScreen extends View {
//	public ArrayList<Button> switchScreens; //back, next, save,
//	public Slider soilType;
//	public Slider moisture;
//	public Slider sunlight;
//	public TextField budget;
	
	private Canvas canvas;

	public ConditionScreen(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		
	    // Create a wrapper Pane first
	    border.setCenter(createCanvasPane());
		border.setRight(createSidebar());
		
        //gc.restore(); //possible to pass the gc from PlotDesign and restore the drawing
	}
	
	private Node createSidebar() {
		VBox sidebar = new VBox(8);
		sidebar.setPadding(new Insets(12.0));
		sidebar.setStyle("-fx-background-color: #f0ebdd");
		
		HBox budgetRow = new HBox();
		TextField budgetField = new TextField();
		Label budgetLabel = new Label("Budget ($):");
		budgetRow.getChildren().addAll(budgetLabel, budgetField);
		budgetField.setOnKeyReleased(e -> {
			controller.updateBudget(budgetField.getText());
		});
		
		HBox tools = new HBox(8);
		Button fillButton = new Button("Fill");
		fillButton.setOnAction(controller.getHandlerforModeSetter(UserMode.SETTING_CONDITIONS));
		Button partitionButton = new Button("Partition");
		partitionButton.setOnAction(controller.getHandlerforModeSetter(UserMode.PARTITIONING));
		tools.getChildren().addAll(fillButton, partitionButton);
		
		Button next = new Button("Next");
		next.setOnAction((e) -> {
			GardenDesign gd = (GardenDesign) manageView.getView("GardenDesign");
			gd.addBudgetLepPane();
			controller.switchViews("GardenDesign");
		});
		
		Node sliders = createSliders();
		Node soilButtons = createSoilButtons();
		
		VBox.setVgrow(sliders, Priority.ALWAYS);
		sidebar.getChildren().addAll(budgetRow, sliders, soilButtons, tools, next);
		
		return sidebar;
	}
	
	private Node createCanvasPane() {
	    Pane wrapperPane = new Pane();

		wrapperPane.setStyle("-fx-background-color: #c9deff");
	    // Put canvas in the center of the window
	    canvas = new Canvas();
	    gc = canvas.getGraphicsContext2D();
	    wrapperPane.getChildren().add(canvas);
	    // Bind the width/height property to the wrapper Pane
	    canvas.widthProperty().bind(wrapperPane.widthProperty());
	    canvas.heightProperty().bind(wrapperPane.heightProperty());
		
		canvas.widthProperty().addListener(e -> manageView.redrawImage());
		canvas.heightProperty().addListener(e -> manageView.redrawImage());
		
		canvas.setOnMousePressed(controller.getConditionsClickHandler());
		canvas.setOnMouseDragged(controller.getConditionsDragHandler());
		
		return wrapperPane;
	}
	
	private Node createSliders() {
		HBox sliders = new HBox(16);
		Slider moistureSlider = createConditionSlider();
		Label moistureLabel = new Label("Moisture");
		Slider sunlightSlider = createConditionSlider();
		Label sunlightLabel = new Label("Sunlight");
		
		sunlightSlider.valueProperty().addListener((ov, oldVal, newVal) -> {
			controller.updateConditionSlider((int) moistureSlider.getValue(), (int) sunlightSlider.getValue());
		});
		moistureSlider.valueProperty().addListener((ov, oldVal, newVal) -> {
			controller.updateConditionSlider((int) moistureSlider.getValue(), (int) sunlightSlider.getValue());
		});
		
		sliders.getChildren().addAll(moistureLabel, moistureSlider, sunlightLabel, sunlightSlider);
		
		return sliders;
	}
	
	private Slider createConditionSlider() {
		Slider slider = new Slider(0, 10, 0);
		slider.setOrientation(Orientation.VERTICAL);
		slider.setShowTickLabels(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		
		return slider;
	}
	
	private Node createSoilButtons() {
		HBox buttonBox = new HBox(4);
		for(SoilType type : SoilType.values()) {
			Button soilButton = new Button(type.toString());
			soilButton.setOnAction(controller.getConditionsSoilHandler(type));
			buttonBox.getChildren().add(soilButton);
		}
		
		return buttonBox;
	}
	
	
	public void saveImage() {
		this.manageView.setImage(canvas.snapshot(null, null));
	}
	
}
