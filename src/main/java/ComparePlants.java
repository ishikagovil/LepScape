import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * A screen that serves to pull up two plants and compare their stats. Provides feedback as well.
 * @author Dea Harjianto
 *
 */

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
    ImageView leftImg;
    PlantSpecies rightSpecies;
    ImageView rightImg;
    VBox center;
	//public TilePane tile = new TilePane();
    
    final int INFOSPC = 20;
    final int INS = 20;
    final int CENTER = 100;
    final int PUSHX = 200;
    final int MOVEUP = -50;
		
	/**
	 * Initializes a new instance of ComparePlants.
	 * @param stage
	 * @param controller
	 * @param manageView
	 */
    public ComparePlants(Stage stage, Controller controller, ManageViews manageView) {
		super(stage, controller, manageView);
		
		this.screenWidth = (int)manageView.getScreenWidth();
		this.screenHeight = (int)manageView.getScreenHeight();
		
		this.stage = stage;
		isPlant1 = false;
		
		// load in images, make the header/title/etc.
		plantPics = manageView.getPlantImages();
        
        plantSpecs = controller.getPlantInfo();
        List<Entry<String, PlantSpecies>> sortList = new ArrayList<>(plantSpecs.entrySet());
        sortList.sort(Entry.<String, PlantSpecies>comparingByValue());
        		
        Map<String, PlantSpecies> result = new LinkedHashMap<>();
        for (Entry<String, PlantSpecies> entry : sortList) {
            result.put(entry.getKey(), entry.getValue());
        }
        plantSpecs = result;
		
		border = new BorderPane();
		//comparePlant = new HBox();
		border.setTop(makeTitle());
		
		mainCompare = new Pane();
		
		this.mainCompare.setStyle("-fx-background-color: #A69F98");
		
		this.center = new VBox();
		this.center.setSpacing(INFOSPC);
		this.center.getChildren().add(makeInstructions());
		this.center.getChildren().add(makePlantBoxes());
		this.center.getChildren().add(this.mainCompare);
		
		VBox.setVgrow(this.center.getChildren().get(0), Priority.ALWAYS);
		VBox.setVgrow(this.center.getChildren().get(1), Priority.ALWAYS);
		VBox.setVgrow(this.center.getChildren().get(2), Priority.ALWAYS);
		
		this.center.setAlignment(Pos.CENTER);

        border.setCenter(this.center);
        
		updateCenterBottom();
        
        this.leftPlant = makePlantInfo();
        for (Node x : this.leftPlant.getChildren()) {
        	this.leftPlant.setAlignment(x,  Pos.CENTER);
        }
        border.setLeft(this.leftPlant);
        this.rightPlant = makePlantInfo();
        border.setRight(this.rightPlant);
        for (Node x : this.rightPlant.getChildren()) {
        	this.rightPlant.setAlignment(x,  Pos.CENTER);
        }

	}
	

    
	/**
	 * Makes the instructions blurb and returns the Label.
	 * @return Label
	 */
    public Label makeInstructions() {
		String text = "Choose from the drop down menus below to compare two plants! View their supported butterfly/moth count, cost, and more!";
		Label insT = new Label(text);
		
		insT.setFont(new Font("Andale Mono", 20));
		insT.setMaxWidth(screenWidth / 3);
		insT.setWrapText(true);
		insT.setTextAlignment(TextAlignment.CENTER);
		insT.setPadding(new Insets(INS));
		
		return insT;
	}
	
	/**
	 * Makes the drop down boxes for the plants; makes two instances.
	 * @return VBox
	 */
    public VBox makePlantBoxes() {
		VBox centerSelect = new VBox();
		
		Label plant1 = new Label("Plant 1:");
		plant1.setFont(new Font ("Andale Mono", 20));
		Label plant2 = new Label("Plant 2:");
		plant2.setFont(new Font("Andale Mono", 20));
		
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
		
		centerSelect.setAlignment(Pos.CENTER);
		centerSelect.setSpacing(INFOSPC);

		return centerSelect;
	}
	
	/**
	 * When a drop down box is altered, call this function to make the screen display the information.
	 * @param plant
	 * @param isFirst
	 */
    public void updatePlantInfo(PlantSpecies plant, boolean isFirst) {
		Text title = new Text(plant.getCommonName());
		//title.setMaxWidth(screenWidth / 3);
		title.setFont(new Font("Andale Mono", 20));
		Text sciName = new Text("(" + plant.getGenusName() + " " + plant.getSpeciesName() + ")");
		//sciName.setMaxWidth(screenWidth / 3);
		sciName.setFont(Font.font("Andale Mono", 20));
		
		// create a new ImageView so the original ImageView doesn't get messed up by manipulations
		ImageView host = plantPics.get(plant.getGenusName() + "-" + plant.getSpeciesName());
		if(host == null) return;
		ImageView plantImg = new ImageView();
		plantImg.setImage(host.getImage());
		
		int lepCount = plant.getLepsSupported();
		int cost = plant.getCost();
		double spread = plant.getSpreadRadius();
		String type;
		if (plant.isWoody()) {
			type = "woody";
		} else {
			type = "herbaceous";
		}
	
		Text desc = new Text("Cost: $" + cost + "\n\nSpread Radius: " + spread + "ft\n\nButterflies/Moths Supported: " + lepCount + "\n\nPlant Type: " + type);
		desc.setFont(new Font("Andale Mono", 16));
		
		VBox plantBlock = new VBox();
		plantBlock.setMaxWidth(screenWidth / 3);
		plantImg.setPreserveRatio(true);
		plantImg.setFitWidth(screenWidth / 5);
		plantBlock.setSpacing(INFOSPC);
		
		title.setTextAlignment(TextAlignment.CENTER);
		sciName.setTextAlignment(TextAlignment.CENTER);
		desc.setTextAlignment(TextAlignment.CENTER);
		desc.setTranslateY(MOVEUP);
		
		plantBlock.getChildren().addAll(title, sciName, plantImg, desc);
		
		if (isFirst) {
			this.leftPlant.getChildren().clear();
			this.leftPlant.getChildren().add(plantBlock);
			this.leftSpecies = plant;
		} else {
			this.rightPlant.getChildren().clear();
			this.rightPlant.getChildren().add(plantBlock);
			this.rightSpecies = plant;
		}
		
		plantBlock.setAlignment(Pos.BASELINE_CENTER);
		plantBlock.setTranslateX(PUSHX);
		plantBlock.setPadding(new Insets(INS, INS, INS, CENTER));
		
		updateCenterBottom();
	}
	
	/**
	 * Call this function to update the feedback given depending on what plants are displayed.
	 */
    public void updateCenterBottom() {
		
		this.mainCompare.getChildren().clear();
		
		Label costLabel = new Label("Best Price:");
		costLabel.setFont(new Font("Andale Mono", 20));
		costLabel.setUnderline(true);
		costLabel.setMaxWidth(screenWidth / 3);
		Label lepLabel = new Label("Best Lep Support:");
		lepLabel.setUnderline(true);
		lepLabel.setFont(new Font("Andale Mono", 20));
		lepLabel.setMaxWidth(screenWidth / 3);
		Label advLabel = new Label("Advice:");
		advLabel.setFont(new Font("Andale Mono", 20));
		advLabel.setUnderline(true);
		advLabel.setMaxWidth(screenWidth / 3);
		
		Label costWinner = new Label();
		costWinner.setFont(new Font("Andale Mono", 20));
		costWinner.setMaxWidth(screenWidth / 3);
		Label lepWinner = new Label();
		lepWinner.setFont(new Font("Andale Mono", 20));
		lepWinner.setMaxWidth(screenWidth / 3);
		Label adv = new Label();
		adv.setFont(new Font("Andale Mono", 20));
		adv.setMaxWidth(screenWidth / 3);
		adv.setAlignment(Pos.CENTER);
		adv.setWrapText(true);
		
		if (this.leftSpecies == null || this.rightSpecies == null) {
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
		
		for (Node x : results.getChildren()) {
			x.setStyle("-fx-text-fill: white");
		}
		
		results.setMaxWidth(screenWidth / 3);
		results.setPadding(new Insets(INS));
		results.setSpacing(INFOSPC);
		
		this.mainCompare.getChildren().add(results);
		results.setAlignment(Pos.CENTER);

	}

	
	/**
	 * Makes empty component to display plant information in.
	 * @return BorderPane
	 */
    public BorderPane makePlantInfo() {
		BorderPane plant = new BorderPane();
		
		plant.setPrefWidth(screenWidth / 3);
		//plant.setMinHeight(screenHeight-300);
		
		Label plantInfo = new Label("test");
		plant.getChildren().add(plantInfo);
		
		plant.setStyle("-fx-background-color: #AFD5AA");
		
		return plant;
	}
	
	/**
	 * Makes the title and back button to display.
	 * @return BorderPane
	 */
    public BorderPane makeTitle() {
		BorderPane header = new BorderPane();
		Label title = new Label("Compare Plants!");
		title.setFont(new Font("Andale Mono", 50));
		title.setStyle("-fx-text-fill: white");
		header.setCenter(title);
		ImageView back = addNextButton("Back", "GardenDesign");
		header.setLeft(back);
		header.setPadding(new Insets(INS));
		
		header.setStyle("-fx-background-color: #A69F98");
		
		return header;
	}
	
    /**
     * Sets the new imageview directory after they are loaded in
     * @param Map<String, ImageView> the new imageview directory
     */
    public void setPlantDir(Map<String, ImageView> dir) {
    	this.plantPics = dir;
    }

}
