import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ComparePlants extends View {
	
	Map<String, ImageView> plantPics;
	Map<String, PlantSpecies> plantSpecs;
	HBox comparePlant;
	boolean isPlant1;
    public TilePane tile = new TilePane();
    int screenWidth;
    int screenHeight;
    BorderPane leftPlant;
    BorderPane rightPlant;
    Pane mainCompare;
    PlantSpecies leftSpecies;
    PlantSpecies rightSpecies;
	//public TilePane tile = new TilePane();
    
    final int INFOSPC = 20;
    final int ins = 20;
    final int centerThis = 60;
		
	public ComparePlants(Stage stage, Controller controller, ManageViews manageView) {
		super(stage, controller, manageView);
		
		this.screenWidth = (int)manageView.getScreenWidth();
		this.screenHeight = (int)manageView.getScreenHeight();
		
		this.stage = stage;
		isPlant1 = false;
		
		// load in images, make the header/title/etc.
		plantPics = manageView.getPlantImages();
        
        plantSpecs = controller.getPlantInfo();
		
		border = new BorderPane();
		//comparePlant = new HBox();
		border.setTop(makeTitle());
		
		mainCompare = new Pane();
		
		VBox center = new VBox();
		center.setSpacing(INFOSPC);
		center.getChildren().add(makeInstructions());
		center.getChildren().add(makePlantBoxes());
		center.getChildren().add(this.mainCompare);

        border.setCenter(center);
        
		//updateCenterBottom();
        
        this.leftPlant = makePlantInfo();
        border.setLeft(this.leftPlant);
        this.rightPlant = makePlantInfo();
        border.setRight(this.rightPlant);

	}
	
	public Label makeInstructions() {
		String ins = "Choose from the drop down menus below to compare two plants! View their supported butterfly/moth count, cost, and more!";
		Label insT = new Label(ins);
		
		insT.setFont(new Font("Andale Mono", 20));
		insT.setMaxWidth(screenWidth / 3);
		insT.setWrapText(true);
		
		return insT;
	}
	
	public VBox makePlantBoxes() {
		VBox centerSelect = new VBox();
		
		Label plant1 = new Label("Plant 1:");
		Label plant2 = new Label("Plant 2:");
		
        ComboBox<PlantSpecies> plantList1 = new ComboBox<>();
        plantSpecs.forEach((k, v) -> {
        	plantList1.getItems().add(v);
        });
        
		plantList1.getSelectionModel().select(0);
		
		plantList1.setOnAction(controller.getHandlerForPlantCompare(plantList1));
		
		ComboBox<PlantSpecies> plantList2 = new ComboBox<>();
		plantSpecs.forEach((k,v) -> {
			plantList2.getItems().add(v);
		});
		plantList2.getSelectionModel().select(0);
		
		plantList2.setOnAction(controller.getHandlerForPlantCompare2(plantList2));
	
		centerSelect.getChildren().add(plant1);
		centerSelect.getChildren().add(plantList1);
		
		centerSelect.getChildren().add(plant2);
		centerSelect.getChildren().add(plantList2);

		return centerSelect;
	}
	
	public void updatePlantInfo(PlantSpecies plant, boolean isFirst) {
		Text title = new Text(plant.getCommonName());
		//title.setMaxWidth(screenWidth / 3);
		title.setFont(new Font("Andale Mono", 20));
		Text sciName = new Text("(" + plant.getGenusName() + " " + plant.getSpeciesName() + ")");
		//sciName.setMaxWidth(screenWidth / 3);
		sciName.setFont(Font.font("Andale Mono", FontPosture.ITALIC, 20));
		ImageView plantImg = plantPics.get(plant.getGenusName() + "-" + plant.getSpeciesName());
		int lepCount = plant.getLepsSupported();
		int cost = plant.getCost();
		double spread = plant.getSpreadRadius();
		String type;
		if (plant.isWoody()) {
			type = "woody";
		} else {
			type = "herbaceous";
		}
	
		Text desc = new Text("Cost: " + cost + "\n\nSpread Radius: " + spread + "\n\nButterflies/Moths Supported: " + lepCount + "\n\nPlant Type: " + type);
		desc.setFont(new Font("Andale Mono", 15));
		
		VBox plantBlock = new VBox();
		plantBlock.setMaxWidth(screenWidth / 3);
		plantImg.setFitWidth(screenWidth / 4);
		plantBlock.setSpacing(INFOSPC);
		plantBlock.getChildren().addAll(title, sciName, plantImg, desc);
		
		plantBlock.setPadding(new Insets(ins, ins, ins, centerThis));
		
		if (isFirst) {
			this.leftPlant.getChildren().clear();
			this.leftPlant.getChildren().add(plantBlock);
			this.leftSpecies = plant;
		} else {
			this.rightPlant.getChildren().clear();
			this.rightPlant.getChildren().add(plantBlock);
			this.rightSpecies = plant;
		}
		
		updateCenterBottom();
	}
	
	public void updateCenterBottom() {
		
		this.mainCompare.getChildren().clear();
		
		Label costLabel = new Label("Best Cost: ");
		costLabel.setFont(new Font("Andale Mono", 20));
		Label lepLabel = new Label("Best Lep Support: ");
		lepLabel.setFont(new Font("Andale Mono", 20));
		Label advLabel = new Label("Advice: ");
		advLabel.setFont(new Font("Andale Mono", 20));
		
		Label costWinner = new Label();
		costWinner.setFont(new Font("Andale Mono", 20));
		Label lepWinner = new Label();
		lepWinner.setFont(new Font("Andale Mono", 20));
		Label adv = new Label();
		adv.setFont(new Font("Andale Mono", 20));
		adv.setMaxWidth(screenWidth / 3);
		adv.setWrapText(true);
		
		if (this.leftPlant.getChildren().isEmpty() || this.rightPlant.getChildren().isEmpty()) {
			costWinner.setText("N/A");
			lepWinner.setText("N/A");
		} else {
			int lLeps = this.leftSpecies.getLepsSupported();
			int rLeps = this.rightSpecies.getLepsSupported();
			
			if (lLeps > rLeps) {
				lepWinner.setText(this.leftSpecies.getCommonName());
			} else if (rLeps > lLeps) {
				lepWinner.setText(this.rightSpecies.getCommonName());
			} else {
				lepWinner.setText("Equal leps supported!");
			}
			
			int lCost = this.leftSpecies.getCost();
			int rCost = this.rightSpecies.getCost();
			int factor;
			
			if (lCost < rCost) {
				costWinner.setText(this.leftSpecies.getCommonName());
				factor = Math.floorDiv(rCost,lCost);
			} else if (rCost < lCost) {
				costWinner.setText(this.rightSpecies.getCommonName());
				factor = Math.floorDiv(lCost,rCost);
			} else {
				costWinner.setText("Equal costs!");
				factor = 0;
			}
			
			if (factor * lLeps > rLeps && lCost < rCost) {
				adv.setText(this.leftSpecies.getCommonName() + " can attract more leps at a cheaper cost! For " + factor + " amount of plants, it will attract " + factor * lLeps + " leps!");
			} else if (factor * rLeps > lLeps && rCost < lCost) {
				adv.setText(this.rightSpecies.getCommonName() + " can attract more leps at a cheaper cost! For " + factor + " amount of plants, it will attract " + factor * rLeps + " leps!");
			}
			
		}
		
		VBox results = new VBox();
		results.getChildren().addAll(costLabel, costWinner, lepLabel, lepWinner);
		if (!adv.getText().isEmpty()) {
			results.getChildren().addAll(advLabel, adv);
		}
		
		results.setSpacing(INFOSPC);
		
		this.mainCompare.getChildren().add(results);
		
	}
	
	public BorderPane makePlantInfo() {
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
		title.setFont(new Font("Andale Mono", 50));
		header.setCenter(title);
		ImageView back = addNextButton("back", "GardenDesign");
		header.setLeft(back);
		return header;
	}
	

}
