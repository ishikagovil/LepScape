import java.util.*;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ConditionScreen extends View {
	public ArrayList<Button> switchScreens; //back, next, save,
	public Slider soilType;
	public Slider moisture;
	public Slider sunlight;
	public TextField budget;

	public ConditionScreen(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		budget = new TextField("budget");
		
		border.setStyle("-fx-background-color: #c9deff");
		
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
		
		VBox.setVgrow(sliders, Priority.ALWAYS);
		sidebar.getChildren().addAll(budgetRow, sliders);
		
		return sidebar;
	}
}
