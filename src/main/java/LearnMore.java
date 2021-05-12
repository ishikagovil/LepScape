import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class LearnMore extends View{
	
	final int TITLESIZE = 50;
	final int TSIZE = 30;
	final int INS = 20;
	final int ROWS = 10;
	final int VGAP = 50;
	final int SPC = 20;
	final int TOPINS = 50;
	double wrap;


	public LearnMore(Stage stage, Controller c,ManageViews manageView) {
		super(stage, c,manageView);
		
		this.wrap = this.manageView.getScreenWidth() - 290;

		border = new BorderPane();
		border.setTop(makeTitle());
		border.getTop().setStyle("-fx-background-color: #afd5aa");
		border.setCenter(addCenterPane());
	}
	
	public BorderPane makeTitle() {
		BorderPane header = new BorderPane();
		header.setPadding(new Insets(INS));
		
		ImageView done = addNextButton("Back", "GardenDesign");
		header.setLeft(done);
		header.setStyle("-fx-background-color: #afd5aa");
		
		Label title = new Label("Learn More!");
		title.setFont(Font.font("Andale Mono", TITLESIZE));
		title.setPadding(new Insets(INS));
		header.setCenter(title);
		
		return header;
	}
	
	public TilePane addCenterPane(){

		TilePane tile = new TilePane(Orientation.VERTICAL);
		tile.setPadding(new Insets(TOPINS, INS, INS, INS));
		tile.setVgap(VGAP);
		tile.setHgap(INS);
		tile.setPrefRows(ROWS);
		tile.setAlignment(Pos.CENTER);
		
		tile.getChildren().addAll(addSubtitle());
		tile.setAlignment(Pos.BASELINE_CENTER);
		
		tile.setStyle("-fx-background-color: #a69f98");
		
		tile.setAlignment(Pos.BASELINE_CENTER);
		
		return tile;
	}
	
	public VBox addSubtitle() {
		Text by = new Text("Created by undergraduate software engineers Arunima Dey, Ishika Govil, Dea Harjianto, Kimmy Huynh, and Jinay Jain.");
		Text reas = new Text("This program was created to help users like you plan a garden surrounding native plants and lep maximization.");
		
		VBox sub = new VBox();
		sub.getChildren().addAll(by, reas);
		
		return sub;
	}
	
}
