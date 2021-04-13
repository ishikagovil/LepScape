import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
		VBox sidebar = new VBox();
		sidebar.setPadding(new Insets(12.0));
		sidebar.setStyle("-fx-background-color: #f0ebdd");
		
		HBox budgetRow = new HBox();
		TextField budgetField = new TextField();
		Label budgetLabel = new Label("Budget ($):");
		budgetRow.getChildren().addAll(budgetLabel, budgetField);
		
		Slider moistureSlider = new Slider(0, 10, 1);
		moistureSlider.setOrientation(Orientation.VERTICAL);

		Slider sunlightSlider = new Slider(0, 10, 1);
		sunlightSlider.setOrientation(Orientation.VERTICAL);

		HBox sliders = new HBox();
		sliders.getChildren().addAll(moistureSlider, sunlightSlider);
		
		HBox tools = new HBox();
		Button fillButton = new Button("Fill");
		fillButton.setOnAction(controller.getHandlerforModeSetter(UserMode.SETTING_CONDITIONS));
		Button partitionButton = new Button("Partition");
		partitionButton.setOnAction(controller.getHandlerforModeSetter(UserMode.PARTITIONING));
		tools.getChildren().addAll(fillButton, partitionButton);
		
		VBox.setVgrow(sliders, Priority.ALWAYS);
		sidebar.getChildren().addAll(budgetRow, sliders, tools);
		
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
	
	
}
