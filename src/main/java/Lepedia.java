import java.util.Iterator;
import java.util.Map;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
		border.setAlignment(lepTitle, Pos.CENTER);
		Button back = addNextButton("Back", "Summary");
		border.setBottom(back);
		
	    TilePane outerTile = new TilePane(Orientation.VERTICAL);
	    outerTile.setTileAlignment(Pos.CENTER_LEFT);
	    outerTile.setPrefRows(5);
	    outerTile.setPrefColumns(1);
	   
	    border.setCenter(outerTile);
	    Map<String, Lep> info = c.getLepInfo();
	    Iterator lepIter = info.entrySet().iterator();
	    
	    while (lepIter.hasNext()) {
	    	Map.Entry lepElement = (Map.Entry)lepIter.next();
            Lep lepObj = (Lep)lepElement.getValue();
            outerTile.getChildren().add(getInfoTile(lepObj));
	    }
	}
	
	public TilePane getInfoTile(Lep lep) {
		Label genusName = new Label(lep.getGenusName());
		Label speciesName = new Label(lep.getSpeciesName());
		Label commonName = new Label(lep.getCommonName());
		Label description = new Label(lep.getDescription());
		
		TilePane lepTile = new TilePane(Orientation.HORIZONTAL);
		lepTile.setTileAlignment(Pos.CENTER_LEFT);
		lepTile.setPrefColumns(4);
		lepTile.setPrefRows(1);
		
		lepTile.getChildren().add(genusName);
		lepTile.getChildren().add(speciesName);
		lepTile.getChildren().add(commonName);
		lepTile.getChildren().add(description);
		
		return lepTile;
		}
	
}
