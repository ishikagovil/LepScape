import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.util.*;
import java.util.Map.Entry;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
	//Panes
	public VBox vb = new VBox();
	public BorderPane stack = new BorderPane();
	public TilePane tile = new TilePane();
	public BorderPane comparePane = new BorderPane();
	public StackPane info = new StackPane();
	Image im = new Image(getClass().getResourceAsStream("/commonMilkweed.png"));
	ObservableMap<String,ImageView> oblist;
	
	
	public GardenDesign(Stage stage, Controller c) {
		super(stage,c);
		this.ic=c;
		oblist = initializeHashMap();

		vb = addVBox();
//		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		showCompostBin();
//		innerPane.setMinHeight(screenHeight);
//		innerPane.setMinWidth(screenWidth);
//		stack.setStyle("-fx-background-color: BLUE");
//		border.setCenter(stack);
		tile = addTilePane();
		border.setBottom(tile);
		comparePane = addBorderPane();
		border.setLeft(comparePane);
		border.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		border.setTop(vb);
		border.setAlignment(vb, Pos.TOP_LEFT);
//		border.getChildren().add(canvas); 
		
//        gc = canvas.getGraphicsContext2D();	
	}
	
	public TilePane addTilePane() {
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: LIGHTSTEELBLUE");
		for(int i = 0; i<4; i++) {
			oblist.get("Image"+i).setOnMousePressed(ic.getHandlerforPressed());
			oblist.get("Image"+i).setOnMouseDragged(ic.getHandlerforDrag());
			oblist.get("Image"+i).setOnMouseReleased(ic.getHandlerforReleased());
			tile.getChildren().add(oblist.get("Image"+i));
		}
//		ImageView iv = new ImageView(im);
//		iv.setPreserveRatio(true);
//		iv.setFitHeight(100);
//		
//		iv.setOnMousePressed(ic.getHandlerforPressed());
//		iv.setOnMouseDragged(ic.getHandlerforDrag());
//		iv.setOnMouseReleased(ic.getHandlerforReleased());
////		iv.setOnMouseReleased(event -> tile.setOnDragExited(event2 -> ic.release(event)));
//		
//		oblist.put("thisOne", iv);
//		tile.getChildren().add(iv);
		
		return tile;
	}
	
	public void addImageView(double x, double y) {
		System.out.println("in the inner addImageView");
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
//		iv2.setTranslateX(iv2.getLayoutX()+x);
//		iv2.setTranslateY(iv2.getLayoutY()+y);
		setX(x,iv2);
		setY(y,iv2);
//		iv2.setTranslateX(x);
//		iv2.setTranslateY(y);
		iv2.setOnMouseDragged(ic.getHandlerforDrag());
		border.getChildren().add(iv2);
	}
	
	public StackPane makeInfoPane(String name,String info) {
		StackPane info1 = new StackPane();
		info1.setMinWidth(screenWidth/4);
		Character.toUpperCase(name.charAt(0));
		Label title = new Label(name);
		
		return info1;
	}
	
	public VBox addVBox() {
		VBox vb = new VBox();
		vb.setStyle("-fx-background-color: GAINSBORO");
		vb.setSpacing(10);
		vb.setMinHeight(screenHeight/4);
		vb.setMaxWidth(screenHeight/9);
		vb.setAlignment(Pos.CENTER);;
		Button[] buttons = new Button[] {
			new Button("Settings"), addNextButton("Learn More", "LearnMore"), new Button("Save"), new Button("Clear"),addNextButton("Next","Summary")
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
		oblist = FXCollections.observableHashMap();
		
		ImageView iv1 = new ImageView(im);
		iv1.setPreserveRatio(true);
		iv1.setFitHeight(100);
		oblist.put("Image0", iv1);
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
		oblist.put("Image1", iv2);
		ImageView iv3 = new ImageView(im);
		iv3.setPreserveRatio(true);
		iv3.setFitHeight(100);
		oblist.put("Image2", iv3);
		ImageView iv4 = new ImageView(im);
		iv4.setPreserveRatio(true);
		iv4.setFitHeight(100);
		oblist.put("Image3", iv4);
		
		return oblist;
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	public void showCompostBin() {
		Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
		ImageView c = new ImageView(compost);
		c.setPreserveRatio(true);
		c.setFitHeight(100);
		c.setTranslateX(screenHeight/12.5);
		c.setTranslateY((screenHeight-200)/2);
		c.setOnMouseExited(event->{
			c.setFitHeight(100);
		});
		c.setOnMouseEntered(event->{
			c.setFitHeight(120);
		});
		border.getChildren().add(c);
	}
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}
