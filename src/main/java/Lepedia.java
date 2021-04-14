import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Lepedia extends View {

	public Lepedia(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		BorderPane border = new BorderPane();
	    TilePane outerTile = new TilePane(Orientation.VERTICAL);
	    outerTile.setTileAlignment(Pos.CENTER_LEFT);
	    outerTile.setPrefRows(5);
	    outerTile.setPrefColumns(1);
	    
	    
	}
	
}
