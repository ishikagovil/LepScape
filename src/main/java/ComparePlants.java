import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ComparePlants extends View {
	
	Map<String, ImageView> plantPics;
	Map<String, PlantSpecies> plantSpecs;
	HBox comparePlant;
	boolean isPlant1;
    public TilePane tile = new TilePane();
	//public TilePane tile = new TilePane();
		
	public ComparePlants(Stage stage, Controller controller, ManageViews manageView) {
		super(stage, controller, manageView);
		
		this.stage = stage;
		isPlant1 = false;
		
		// load in images, make the header/title/etc.
		plantPics = manageView.getPlantImages();
        
        plantSpecs = controller.getPlantInfo();
		
		border = new BorderPane();
		//comparePlant = new HBox();
		border.setTop(makeTitle());

        border.setCenter(makePlantBoxes());
        
        border.setLeft(getPlantInfo());
        border.setRight(getPlantInfo());
        //border.setBo

	}
	
	public VBox makePlantBoxes() {
		VBox centerSelect = new VBox();
		
		Label plant1 = new Label("Plant 1:");
		Label plant2 = new Label("Plant 2:");
		
        ComboBox<PlantSpecies> plantList1 = new ComboBox<>();
        plantSpecs.forEach((k, v) -> {
        	plantList1.getItems().add(v);
        });
		//plantList.getItems().remove(0);
		plantList1.getSelectionModel().select(0);
		
		ComboBox<PlantSpecies> plantList2 = new ComboBox<>();
		plantSpecs.forEach((k,v) -> {
			plantList2.getItems().add(v);
		});
		plantList2.getSelectionModel().select(0);
	
		centerSelect.getChildren().add(plant1);
		centerSelect.getChildren().add(plantList1);
		
		centerSelect.getChildren().add(plant2);
		centerSelect.getChildren().add(plantList2);

		return centerSelect;
	}
	
	public BorderPane getPlantInfo() {
		BorderPane plant = new BorderPane();
		
		plant.setPrefWidth(screenWidth / 3);
		plant.setMinHeight(screenHeight-300);
		
		Label plantInfo = new Label("test");
		plant.getChildren().add(plantInfo);
		
		plant.setStyle("-fx-background-color: #AFD5AA");
		
		return plant;
	}
	
	public BorderPane makeTitle() {
		BorderPane header = new BorderPane();
		Label title = new Label("Compare Plants!");
		title.setFont(new Font("Andale Mono", 20));
		header.setCenter(title);
		ImageView back = addNextButton("back", "GardenDesign");
		header.setLeft(back);
		return header;
	}
	

}
