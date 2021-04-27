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

/**
 * The screen to select garden conditions and partition the garden into sections
 * @author Jinay Jain
 *
 */
public class ConditionScreen extends View {
//	public ArrayList<Button> switchScreens; //back, next, save,
//	public Slider soilType;
//	public Slider moisture;
//	public Slider sunlight;
//	public TextField budget;
	
	private final int boxSpacing = 8;
	private final int boxPadding = 12;
	
	private Canvas canvas;

	/**
	 * Initializes a new instance of ConditionScreen
	 * @param stage Stage to draw on
	 * @param c the associated MVC Controller
	 * @param manageView view manager that stores shared info
	 */
	public ConditionScreen(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		
	    // Create a wrapper Pane first
	    border.setCenter(createCanvasPane());
		border.setRight(createSidebar());
		
	}
	
	/**
	 * Creates the entire Conditions sidebar
	 * @return the created sidebar node
	 */
	private Node createSidebar() {
		VBox sidebar = new VBox(boxSpacing);
		sidebar.setPadding(new Insets(boxPadding));
		sidebar.setStyle("-fx-background-color: #f0ebdd");
		
		HBox budgetRow = new HBox();
		TextField budgetField = new TextField();
		Label budgetLabel = new Label("Budget ($):");
		budgetRow.getChildren().addAll(budgetLabel, budgetField);
		budgetField.setOnKeyReleased(e -> {
			controller.updateBudget(budgetField.getText());
		});
		
		HBox tools = new HBox(boxSpacing);
		Button fillButton = new Button("Fill");
		fillButton.setOnAction(controller.getHandlerforModeSetter(UserMode.SETTING_CONDITIONS));
		tools.getChildren().addAll(fillButton);
		
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
	
	/**
	 * Creates the drawable canvas for conditions
	 * @return the created canvas
	 */
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
		
		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		canvas.setOnMousePressed(controller.getConditionsClickHandler(canvas));
		
		return wrapperPane;
	}
	
	/**
	 * Creates sunlight and moisture sliders
	 * @return an HBox containing the two sliders and their labels
	 */
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
	
	/**
	 * Creates a single condition slider
	 * @return the created condition slider
	 */
	private Slider createConditionSlider() {
		Slider slider = new Slider(0, 10, 0);
		slider.setOrientation(Orientation.VERTICAL);
		slider.setShowTickLabels(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setSnapToTicks(true);
		
		return slider;
	}
	
	/**
	 * Creates the buttons to select soil type
	 * @return a box with soil buttons for all soil types
	 */
	private Node createSoilButtons() {
		HBox buttonBox = new HBox(4);
		for(SoilType type : SoilType.values()) {
			Button soilButton = new Button(type.toString());
			soilButton.setOnAction(controller.getConditionsSoilHandler(type));
			buttonBox.getChildren().add(soilButton);
		}
		
		return buttonBox;
	}
	
	
	/**
	 * Saves the image after partitioning to enable fill tool to update
	 */
	public void saveImage() {
		this.manageView.setImage(canvas.snapshot(null, null));
	}
	
}
