import javafx.scene.canvas.Canvas;
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

/**
 * A screen used to give the user help tips for using the app
 *
 */
public class Help extends View{
	
	final int TITLESIZE = 50;
	final int TSIZE = 30;
	final int INS = 20;
	final int ROWS = 10;
	final int VGAP = 50;
	final int SPC = 20;
	final int TOPINS = 50;
	double wrap;


	public Help(Stage stage, Controller c,ManageViews manageView) {
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
		
		ImageView done = addNextButton("Back", "Start");
		header.setLeft(done);
		header.setStyle("-fx-background-color: #afd5aa");
		
		Label title = new Label("Need Help Using Our Program?");
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
		
		tile.getChildren().addAll(addFirst(), addSecond(), addThird(), addFourth());
		tile.setAlignment(Pos.BASELINE_CENTER);
		
		tile.setStyle("-fx-background-color: #F0F2EF)");
		
		tile.setAlignment(Pos.BASELINE_CENTER);
		
		return tile;
	}
	
	public HBox addFirst() {
		HBox b = new HBox();
		Image l = manageView.getButtons().get("Draw");
		Image j = manageView.getButtons().get("Polygon");
		ImageView free = new ImageView(l);
		ImageView poly = new ImageView(j);
		Text d1 = new Text("Start by drawing your garden using our design tool! Choose between freehand drawing or a polygon!");
		
		d1.setTextAlignment(TextAlignment.CENTER);
		d1.setFont(new Font("Andale Mono", TSIZE));

		d1.setWrappingWidth(wrap);
		b.getChildren().addAll(free, d1, poly);
		b.setAlignment(Pos.TOP_LEFT);
		return b;
	}
	
	public HBox addSecond() {
		HBox b = new HBox();
		Image l = manageView.getButtons().get("Plant Compare");
		ImageView comp = new ImageView(l);

		Text d1 = new Text("Next, add plants to your garden! Be careful, each plant has a different size!");
		
		d1.setTextAlignment(TextAlignment.CENTER);
		d1.setFont(new Font("Andale Mono", TSIZE));

		d1.setWrappingWidth(wrap);
		b.getChildren().addAll(comp, d1);
		b.setAlignment(Pos.TOP_LEFT);
		return b;
	}
	
	public HBox addThird() {
		HBox b = new HBox();
		Image l = manageView.getButtons().get("Lepedia");
		ImageView lIV = new ImageView(l);
		Text d1 = new Text("Finally, when you finish your garden, you can view all of the insects attracted to your plot! You can even save your garden as a PDF!");
		
		d1.setTextAlignment(TextAlignment.CENTER);
		d1.setFont(new Font("Andale Mono", TSIZE));

		d1.setWrappingWidth(wrap);
		b.getChildren().addAll(lIV, d1);
		b.setAlignment(Pos.TOP_LEFT);
		return b;
	}
	
	public HBox addFourth() {
		HBox b = new HBox();
		Image l = manageView.getButtons().get("New");
		Image j = manageView.getButtons().get("Gallery");
		ImageView beg = new ImageView(l);
		ImageView gal = new ImageView(j);
		Text d1 = new Text("Get started on your LepScape journey by designing a new garden today!");
		
		d1.setTextAlignment(TextAlignment.CENTER);
		d1.setFont(new Font("Andale Mono", TSIZE));

		d1.setWrappingWidth(wrap);
		b.getChildren().addAll(beg, d1, gal);
		b.setAlignment(Pos.TOP_LEFT);
		return b;
	}
	
}
