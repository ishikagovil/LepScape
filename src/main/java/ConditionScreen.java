import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The screen to select garden conditions and partition the garden into sections
 * @author Jinay Jain
 *
 */
public class ConditionScreen extends View {

	public ComboBox<SoilType> soilDropdown;
	public ComboBox<MoistureType> moistureDropdown;
	public ComboBox<LightType> sunlightDropdown;
	
	private final int boxSpacing = 8;
	private final int boxPadding = 12;
	private final int largeFontSize = 20;
	private final int fontSize = 16;
	private final double wrappingWidth = 200;
	
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
		Label budgetLabel = new Label("Budget ($): ");
		budgetRow.getChildren().addAll(budgetLabel, budgetField);
		budgetField.setOnKeyReleased(e -> {
			controller.updateBudget(budgetField.getText());
		});
		
		HBox buttons = new HBox(boxSpacing);
		Button back = new Button("Back");
		back.setPrefSize(buttonWidth, buttonHeight);
		setOnMouse(back);
		back.setOnAction(controller.getHandlerforClicked("PlotDesign"));
		Button next = new Button("Next");
		next.setPrefSize(buttonWidth, buttonHeight);
		next.setOnAction((e) -> {
			GardenDesign gd = (GardenDesign) manageView.getView("GardenDesign");
			gd.addBudgetLepPane();
			controller.switchViews("GardenDesign");
		});
		buttons.getChildren().addAll(back, next);
		
		Node dropdowns = createConditionDropdowns();
		
		Text title = new Text("Plot Conditions");
		Text info = new Text("Set the conditions of your garden by selecting them from the dropdowns.");
		Text info2 = new Text("Then, select any region of your garden to fill the conditions in!");
		title.setFont(new Font(largeFontSize));
		info.setFont(new Font(fontSize));
		info2.setFont(new Font(fontSize));
		info.setWrappingWidth(wrappingWidth);
		info2.setWrappingWidth(wrappingWidth);
		title.setWrappingWidth(wrappingWidth);
		sidebar.getChildren().addAll(title, info, info2, budgetRow, dropdowns, buttons);
		
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
	 * Creates the dropdown boxes for each condition
	 * @return a Node containing those dropdowns
	 * @author Jinay Jain
	 */
	private Node createConditionDropdowns() {
		VBox box = new VBox(boxSpacing);
		
		Label soilLabel = new Label("Soil Type: ");
		soilDropdown = new ComboBox<>();
		soilDropdown.getItems().addAll(SoilType.values());
		soilDropdown.getItems().remove(0);
		soilDropdown.getSelectionModel().select(1);
		
		Label moistLabel = new Label("Moisture Level: ");
		moistureDropdown = new ComboBox<>();
		moistureDropdown.getItems().addAll(MoistureType.values());
		moistureDropdown.getItems().remove(0);
		moistureDropdown.getSelectionModel().select(1);

		Label sunLabel = new Label("Sunlight Level: ");
		sunlightDropdown = new ComboBox<>();
		sunlightDropdown.getItems().addAll(LightType.values());
		sunlightDropdown.getItems().remove(0);
		sunlightDropdown.getSelectionModel().select(1);

		box.getChildren().addAll(soilLabel, soilDropdown, moistLabel, moistureDropdown, sunLabel, sunlightDropdown);
		
		VBox.setVgrow(box, Priority.ALWAYS);
		
		return box;
	}
}
