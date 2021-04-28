import java.util.ArrayList;
import java.util.Iterator;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
import javafx.scene.text.Font;
import javafx.stage.Stage; 

public class Lepedia extends View {
	
	final int numLepImages = 117;
	final int descFontSize = 20;
	final int titleFontSize = 48;

	public Lepedia(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		Image bgimg = new Image("lepedia-background.jpg");
		BackgroundImage bgImg = new BackgroundImage(bgimg, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		border.setBackground(new Background(bgImg));
		
		Label lepTitle = new Label("Lepedia");
		lepTitle.setFont(new Font("Arial", titleFontSize));
		border.setTop(lepTitle);
		border.setAlignment(lepTitle, Pos.CENTER);
		Button back = addNextButton("Back", "Summary");
		border.setBottom(back);
		border.setAlignment(back, Pos.CENTER);
		
		ScrollPane sp = new ScrollPane();
		sp.setBackground(new Background(bgImg));
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // horizontal scroll bar
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // vertical scroll bar
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        //scroll.setMaxWidth(screenWidth);
        sp.setMaxHeight(screenHeight);						// needed to initialize a dimension for scrollpane; leave in
		
	    TilePane outerTile = new TilePane(Orientation.HORIZONTAL);
	    //outerTile.setTileAlignment(Pos.CENTER);
	    
	    outerTile.setPrefRows(numLepImages);
	    outerTile.setPrefColumns(1);
	    
	    Map<String, Lep> info = c.getLepInfo();
	    Map<String, ImageView> lepImages = manageView.getLepImages();
	    ArrayList<PlacedPlant> plants = c.getGarden().getPlants();
	    Iterator lepIter = info.entrySet().iterator();
	    Iterator plantIter = plants.iterator();
	    
	    while (plantIter.hasNext()) {
	    	PlacedPlant plant = (PlacedPlant)plantIter.next();
	    	String plantGenus = plant.getSpecies().getGenusName();
	    	
	    	while (lepIter.hasNext()) {
	    		Map.Entry lepElement = (Map.Entry)lepIter.next();
	            Lep lepObj = (Lep)lepElement.getValue();
	            
	            Iterator genusIter = lepObj.getThrivesInGenus().iterator();
	            while (genusIter.hasNext()) {
	            	String lepGenus = (String)genusIter.next();
	            	if (lepGenus.equals(plantGenus)) {
	                    outerTile.getChildren().add(getInfoTile(lepImages, lepObj));
	            	}
	            }
	    	}
            outerTile.setTileAlignment(Pos.CENTER);
	    }
	    		
	    		
	   /* while (lepIter.hasNext()) {
	    	Map.Entry lepElement = (Map.Entry)lepIter.next();
            Lep lepObj = (Lep)lepElement.getValue();
            //outerTile.setPrefWidth(screenWidth);
            outerTile.getChildren().add(getInfoTile(lepImages, lepObj));
            outerTile.setTileAlignment(Pos.CENTER);
	    } */
		sp.setContent(outerTile);
	    border.setCenter(sp);
	} 
	
	public HBox getInfoTile(Map<String, ImageView> lepImages, Lep lep) {
		String genusName = lep.getGenusName();
		String speciesName = lep.getSpeciesName();
		String commonName = lep.getCommonName();
		ArrayList<String> thrivesIn = lep.getThrivesInGenus();
		ImageView lepImg = lepImages.get(genusName + "-" + speciesName);
		Label description = new Label(genusName + " " + speciesName + ". Also known as the " + commonName + ". Thrives in " + thrivesIn.toString());
		description.setFont(new Font("Arial", descFontSize));
		
		HBox lepTile = new HBox();
	    //lepTile.setPadding(new Insets(15, 12, 15, 12));
	    //lepTile.setSpacing(10);
		//lepTile.setTileAlignment(Pos.CENTER);
		//lepTile.setPrefColumns(2);
		//lepTile.setPrefColumns(3);
		//lepTile.setPrefRows(1);
		
		lepTile.getChildren().addAll(lepImg, description);
		
		return lepTile;
	}
	
	/*public TilePane getInfoTile(Map<String, ImageView> lepImages, Lep lep) {
		String genusName = lep.getGenusName();
		String speciesName = lep.getSpeciesName();
		String commonName = lep.getCommonName();
		ArrayList<String> thrivesIn = lep.getThrivesInGenus();
		ImageView lepImg = lepImages.get(genusName + "-" + speciesName);
		Label description = new Label(genusName + " " + speciesName + ". Also known as the " + commonName + ". Thrives in " + thrivesIn.toString());
		description.setFont(new Font("Arial", 20));
		
		TilePane lepTile = new TilePane(Orientation.HORIZONTAL);
		lepTile.setTileAlignment(Pos.CENTER);
		lepTile.setPrefColumns(2);
		//lepTile.setPrefColumns(3);
		lepTile.setPrefRows(1);
		
		lepTile.getChildren().add(lepImg);
		lepTile.getChildren().add(description);

		
		return lepTile;
		}*/
	
}
