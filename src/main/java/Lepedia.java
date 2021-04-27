import java.util.Iterator;
import java.util.Map;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Lepedia extends View {

	public Lepedia(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		Label lepTitle = new Label("Lepedia");
		lepTitle.setFont(new Font("Arial", 24));
		border.setTop(lepTitle);
		//border.setAlignment(lepTitle, Pos.CENTER);
		Button back = addNextButton("Back", "Summary");
		border.setBottom(back);
		//border.setAlignment(back, Pos.CENTER);
		
	    TilePane outerTile = new TilePane(Orientation.VERTICAL);
	    outerTile.setTileAlignment(Pos.CENTER_LEFT);
	    outerTile.setPrefRows(5);
	    outerTile.setPrefColumns(1);
	   
	    border.setCenter(outerTile);
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
		Label speciesName = new Label(lep.getSpeciesName());
		Label commonName = new Label(lep.getCommonName());
		ImageView lepImg = lepImages.get(genusName + "-" + speciesName);
		
		TilePane lepTile = new TilePane(Orientation.HORIZONTAL);
		//lepTile.setTileAlignment(Pos.CENTER);
		//lepTile.setPrefColumns(4);
		lepTile.setPrefColumns(3);
		lepTile.setPrefRows(1);
		
		//lepTile.getChildren().add(lepImg);
		lepTile.getChildren().add(genusName);
		lepTile.getChildren().add(speciesName);
		lepTile.getChildren().add(commonName);
		
		return lepTile;
		}
	
}
