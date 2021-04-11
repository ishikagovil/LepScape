import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GardenDesign extends View{
	public Controller ic;
	public Navigation navi;
	public Button compare;
	public Button save;
	public VBox vb;
	Image im = new Image(getClass().getResourceAsStream("/commonMilkweed.png"));
	ObservableMap<String,ImageView> oblist;
	BorderPane innerPane = new BorderPane();
	
	public GardenDesign(Stage stage, Controller c) {
		super(stage,c);
		this.ic=c;
		oblist = initializeHashMap();
		
		
		vb = addVBox();
//		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		innerPane.setMinHeight(screenHeight);
		innerPane.setMinWidth(screenWidth);
		innerPane.setStyle("-fx-background-color: BLUE");
		border.setCenter(innerPane);
		TilePane tile = addTilePane();
		innerPane.setBottom(tile);
		BorderPane bp2 = addBorderPane();
		innerPane.setLeft(bp2);
		innerPane.setAlignment(bp2, Pos.BOTTOM_LEFT);
		innerPane.setTop(vb);
		innerPane.setAlignment(vb, Pos.TOP_LEFT);
//		border.getChildren().add(canvas); 
		
//        gc = canvas.getGraphicsContext2D();	
	}
	
	public TilePane addTilePane() {
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: LIGHTSTEELBLUE");
		ImageView iv = new ImageView(im);
		iv.setPreserveRatio(true);
		iv.setFitHeight(100);
		
		iv.setOnMousePressed(event->ic.pressed(event));
		iv.setOnMouseDragged(event->ic.drag(event));
		iv.setOnMouseReleased(event->ic.release(event));
		
		oblist.put("thisOne", iv);
		tile.getChildren().add(iv);
		
		return tile;
	}
	
	public void click(MouseEvent event) {
		System.out.println("clicked");
		
	}
	
	public void drag(MouseEvent event) {
		System.out.println("dragging");
		Node n = (Node)event.getSource();
		n.setTranslateX(n.getTranslateX() + event.getX());
		n.setTranslateY(n.getTranslateY() + event.getY());
	}
	
	public void addImageView(double x, double y) {
		System.out.println("in the inner addImageView");
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
		iv2.setTranslateX(x);
		iv2.setTranslateY(y);
		innerPane.getChildren().add(iv2);
	}
	
	public VBox addVBox() {
		VBox vb = new VBox();
		vb.setStyle("-fx-background-color: GAINSBORO");
		vb.setSpacing(10);
		vb.setMinHeight(screenHeight/4);
		vb.setMaxWidth(screenHeight/9);
		vb.setAlignment(Pos.CENTER);;
		Button[] buttons = new Button[] {
			new Button("Settings"), new Button("Learn More"), new Button("Save"), new Button("Clear"),addNextButton("Next","Summary")
		};
		vb.getChildren().addAll(buttons);
		return vb;
		
	}
	
	public BorderPane addBorderPane() {
		BorderPane border = new BorderPane();
		border.setStyle("-fx-background-color: LIGHTBLUE");
		border.setMaxHeight(screenHeight/3);
		border.setMaxWidth(screenHeight/4);
		StackPane s1 = addStackPane("-fx-background-color: ALICEBLUE");
		StackPane s2 = addStackPane("-fx-background-color: LAVENDER");
		Label l = new Label("Compare");
		s2.toBack();
		s1.toBack();
		border.setTop(l);
		border.setAlignment(l,Pos.TOP_CENTER);
		border.setRight(s1);
		border.setLeft(s2);
		border.setBottom(addTile());
		border.toBack();
		return border;
	}
	
	public TilePane addTile() {
		TilePane tile = new TilePane();
		tile.setPrefColumns(2);
		tile.setVgap(2);
		tile.setHgap(2);
		tile.setPadding(new Insets(0,5,0,5));
		Text t1 = new Text("Plant 1:\n  lep count:100   \n  cost: $6");
		Text t2 = new Text("Plant 2:\n  lep count:30   \n  cost: $8");
		tile.getChildren().add(t1);
		tile.getChildren().add(t2);
		
		return tile;
	}
	
	public StackPane addStackPane(String background) {
		StackPane stack = new StackPane();
		stack.setStyle("-fx-border-color:GREY; -fx-border-width:1px; "+background);
		stack.setMinWidth(screenHeight/8);
		ImageView iv = new ImageView(im);
		iv.setPreserveRatio(true);
		iv.setFitHeight(100);
		stack.getChildren().add(iv);
		return stack;
	}
	
	public ObservableMap<String,ImageView> initializeHashMap(){
		ImageView iv1 = new ImageView(im);
		oblist = FXCollections.observableHashMap();
		iv1.setPreserveRatio(true);
		iv1.setFitHeight(100);
		
		oblist.put("Image0", iv1);
		oblist.put("Image1", iv1);
		oblist.put("Image2", iv1);
		oblist.put("Image3", iv1);
		
		return oblist;
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	public void showCompostBin() {}
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}
