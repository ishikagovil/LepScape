import java.util.Iterator;

import java.util.Map;

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
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage; 

public class Lepedia extends View {
	
	final int numLepImages = 117;

	public Lepedia(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		Image bgimg = new Image("lepedia-background.jpg");
		BackgroundImage bgImg = new BackgroundImage(bgimg, 
			    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
		border.setBackground(new Background(bgImg));
		
		Label lepTitle = new Label("Lepedia");
		lepTitle.setFont(new Font("Arial", 48));
		border.setTop(lepTitle);
		border.setAlignment(lepTitle, Pos.CENTER);
		Button back = addNextButton("Back", "Summary");
		border.setBottom(back);
		border.setAlignment(back, Pos.CENTER);
		
		ScrollPane sp = new ScrollPane();
		sp.setBackground(new Background(bgImg));
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // horizontal scroll bar
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // vertical scroll bar
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        //scroll.setMaxWidth(screenWidth);
        sp.setMaxHeight(screenHeight);						// needed to initialize a dimension for scrollpane; leave in
		
	    TilePane outerTile = new TilePane(Orientation.VERTICAL);
	    //outerTile.setTileAlignment(Pos.CENTER);
	    
	    outerTile.setPrefRows(numLepImages);
	    outerTile.setPrefColumns(1);
	    
		sp.setContent(outerTile);
	   
	    border.setCenter(sp);
	    Map<String, Lep> info = c.getLepInfo();
	    Map<String, ImageView> lepImages = manageView.getLepImages();
	    Iterator lepIter = info.entrySet().iterator();
	    
	    while (lepIter.hasNext()) {
	    	Map.Entry lepElement = (Map.Entry)lepIter.next();
            Lep lepObj = (Lep)lepElement.getValue();
            outerTile.setPrefWidth(screenWidth);
            outerTile.setTileAlignment(Pos.CENTER);
            outerTile.getChildren().add(getInfoTile(lepImages, lepObj));
	    }
	}
	
	public TilePane getInfoTile(Map<String, ImageView> lepImages, Lep lep) {
		Label genusName = new Label(lep.getGenusName());
		genusName.setFont(new Font("Arial", 20));
		Label speciesName = new Label(lep.getSpeciesName());
		speciesName.setFont(new Font("Arial", 20));
		Label commonName = new Label(lep.getCommonName());
		commonName.setFont(new Font("Arial", 20));
		ImageView lepImg = lepImages.get(lep.getGenusName() + "-" + lep.getSpeciesName());
		
		TilePane lepTile = new TilePane(Orientation.HORIZONTAL);
		//lepTile.setTileAlignment(Pos.CENTER);
		lepTile.setPrefColumns(4);
		//lepTile.setPrefColumns(3);
		lepTile.setPrefRows(1);
		
		lepTile.getChildren().add(lepImg);
		lepTile.getChildren().add(genusName);
		lepTile.getChildren().add(speciesName);
		lepTile.getChildren().add(commonName);

		
		return lepTile;
		}
	
}
