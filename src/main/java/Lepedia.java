import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage; 

/**
 * Creating the Lepedia screen showcasing the directory of leps associated with PlacedPlants of Garden
 * @author Dea Harjianto
 */
public class Lepedia extends View {
	
	final int numLepImages = 117;
	final int descFontSize = 20;
	final int titleFontSize = 48;
	final int ins = 10;
	final int spc = 40;
	int centerThis;
	final int boxWidth = 1000;

	/**
	 * Creates an instance of the Lepedia.
	 * @param stage
	 * @param c
	 * @param manageView
	 */
	public Lepedia(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		border.setStyle("-fx-background-color: #AFD5AA");
		
		centerThis = (int) (manageView.screenWidth / 2 - 350);
		
		border.setTop(makeHeader());
		//border.setAlignment(lepTitle, Pos.CENTER);
		ImageView back = addNextButton("Back", "Summary");
		border.setBottom(back);
		border.setAlignment(back, Pos.CENTER);
	}
	
	/**
	 * Makes the header/title of the page.
	 * @return VBox
	 */
	public VBox makeHeader() {
		VBox header = new VBox();
		
		Label lepTitle = new Label("Lepedia");
		lepTitle.setFont(new Font("Andale Mono", titleFontSize));
		lepTitle.setStyle("-fx-text-fill: #ffffff");
		
		Text description = new Text("Learn more about the butterflies dwelling in your garden!");
		description.setFont(new Font("Andale Mono", descFontSize));
		description.setStyle("-fx-text-fill: #ffffff");
		
		header.getChildren().add(lepTitle);
		header.getChildren().add(description);
		
		header.setAlignment(Pos.CENTER);
		
		header.setPadding(new Insets(ins, ins, ins, ins));
		header.setSpacing(spc);
		
		return header;
	}
	
	/**
	 *  Gathers information from completed Garden and updates it based on the plants available
	 */
	public void updateLepedia() {
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // horizontal scroll bar
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // vertical scroll bar
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setMaxHeight(this.manageView.getScreenHeight());						// needed to initialize a dimension for scrollpane; leave in
		
	    TilePane outerTile = new TilePane(Orientation.HORIZONTAL);
	    //outerTile.setTileAlignment(Pos.CENTER);
	    
	    outerTile.setPrefRows(numLepImages);
	    outerTile.setPrefColumns(1);
	    
	    ArrayList<PlacedPlant> plants = controller.getGarden().getPlants();
	    Map<String, Lep> info = controller.getLepInfo();
	    Map<String, ImageView> lepImages = manageView.getLepImages();
	    HashSet<String> plantToLep = new HashSet<>();
	    
	    plants.forEach(plant -> {
	    	plantToLep.add(plant.getSpecies().getGenusName());
	    });
	    
	    ArrayList<Lep> lepsInGarden = new ArrayList<>();
	    
	    info.forEach((lepKey, lepObj) -> {
	    	ArrayList<String> thrivesIn = lepObj.getThrivesInGenus();
	    	System.out.println(thrivesIn);
	    	for (String genusReqs: thrivesIn) {
	    		System.out.println(genusReqs);
	    		if (plantToLep.contains(genusReqs)) {
	    			if (!(lepsInGarden.contains(lepObj))) {
	    				lepsInGarden.add(lepObj);
	    			}
	    		}
	    	}

	    });
	    
	    for (Lep lepInfo : lepsInGarden) {
	    	outerTile.getChildren().add(getInfoTile(lepImages, lepInfo));
			outerTile.setTileAlignment(Pos.CENTER);
	    }
	    
		sp.setContent(outerTile);
		
	    border.setCenter(sp);
	    
	    sp.setFitToWidth(true);
	    sp.setFitToHeight(true);
	    
	}
	
	/**
	 * Create each row in the Lepedia consisting of ImageViw (if avail) and information of specific Lep
	 * @param lepImages
	 * @param lep
	 * @return HBox
	 */
	public HBox getInfoTile(Map<String, ImageView> lepImages, Lep lep) {
		String genusName = lep.getGenusName();
		String speciesName = lep.getSpeciesName();
		String commonName = lep.getCommonName();
		ArrayList<String> thrivesIn = lep.getThrivesInGenus();
		String feedsOff = thrivesIn.get(0);
		thrivesIn.remove(0);
		for (String gen : thrivesIn) {
			feedsOff += ", " + gen;
		}
		ImageView lepImg = lepImages.get(genusName + "-" + speciesName);
		Label description = new Label("Scientific Name: " + genusName + " " + speciesName + "\nCommon Name: " + commonName + "\nFeeds off genera: " + feedsOff);
		description.setFont(new Font("Andale Mono", descFontSize));
		description.setWrapText(true);
		
		HBox lepTile = new HBox();
		lepTile.setMaxWidth(boxWidth);
		lepTile.getChildren().addAll(lepImg, description);
		lepTile.setPadding(new Insets(ins, ins, ins, centerThis));
		lepTile.setSpacing(spc);
		
		return lepTile;
	}
	
}
