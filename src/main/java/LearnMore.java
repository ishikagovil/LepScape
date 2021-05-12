import javafx.scene.control.Hyperlink;
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
	final int MOVEUP = -60;
	final int MOVEUP2 = - 35;
	final int MOVEUP3 = -10;
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
		by.setFont(new Font("Andale Mono", TSIZE));
		by.setWrappingWidth(this.manageView.getScreenWidth()-200);
		by.setTextAlignment(TextAlignment.CENTER);
		
		Text reas = new Text("This program was created to help users like you plan a garden surrounding native plants and lep maximization.");
		reas.setFont(new Font("Andale Mono", TSIZE));
		reas.setWrappingWidth(this.manageView.getScreenWidth()-200);
		reas.setTextAlignment(TextAlignment.CENTER);
		
		Text lepinfo = new Text("Lepidoptera, coming from the Ancient Greek lepís \"scale\" + pterón \"wing\", is an order of insects including butterflies and moths. Our goal is to attract as many of these insects to your garden using only native plants!");
		lepinfo.setFont(new Font("Andale Mono", TSIZE));
		lepinfo.setWrappingWidth(this.manageView.getScreenWidth()-200);
		lepinfo.setTextAlignment(TextAlignment.CENTER);
		lepinfo.setTranslateY(MOVEUP);
		
		Text nativePlant = new Text("The native plants of this program are native to Delaware and are beneficial to our insects' health! If our insects are not supported, our ecosystem will collapse!");
		nativePlant.setFont(new Font("Andale Mono", TSIZE));
		nativePlant.setWrappingWidth(this.manageView.getScreenWidth()-200);
		nativePlant.setTextAlignment(TextAlignment.CENTER);
		nativePlant.setTranslateY(MOVEUP2);
		
		Text clickNow = new Text("Click the link to find out more about native plants and insect support via Mt. Cuba!");
		clickNow.setFont(new Font("Andale Mono", TSIZE));
		clickNow.setWrappingWidth(this.manageView.getScreenWidth()-200);
		clickNow.setTextAlignment(TextAlignment.CENTER);
		clickNow.setTranslateY(MOVEUP3);
		
		Hyperlink mtcuba = new Hyperlink("https://mtcubacenter.org");
		mtcuba.setOnAction(this.controller.getHandlerForLinkClicked(mtcuba.getText()));
		mtcuba.setFont(new Font("Andale Mono", TSIZE));
		mtcuba.setPadding(new Insets(INS));
		mtcuba.setStyle("-fx-text-fill: #afd5aa");
		
		VBox sub = new VBox();
		sub.getChildren().addAll(by, reas, lepinfo, nativePlant, clickNow, mtcuba);
		sub.setAlignment(Pos.BASELINE_CENTER);
		sub.setPrefWidth(this.manageView.getScreenWidth());
		sub.setSpacing(SPC);
		
		
		return sub;
	}
	
}
