import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.util.*;
import java.util.Map.Entry;
import javax.swing.event.ChangeListener;
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
import javafx.scene.text.Font;
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
	Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
	ImageView c = new ImageView(compost);
	Image lep = new Image(getClass().getResourceAsStream("/butterfly.png"));
	Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
	
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
		
		HBox blPane = addBudgetLepPane();
		blPane.setAlignment(Pos.CENTER);
		border.setTop(blPane);
		border.setAlignment(blPane, Pos.CENTER);
		BorderPane bd2= new BorderPane();
		bd2.setTop(vb);
		bd2.setAlignment(bd2, Pos.TOP_LEFT);
		bd2.setBottom(comparePane);
		bd2.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		
		border.setLeft(bd2);
		
//		border.getChildren().add(canvas); 
		
//        gc = canvas.getGraphicsContext2D();	
	}
	
	public TilePane addTilePane() {
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: LIGHTSTEELBLUE");
		oblist.forEach((k,v)->{
			v.setOnMousePressed(ic.getHandlerforPressed(k));
			v.setOnMouseDragged(ic.getHandlerforDrag());
			v.setOnMouseReleased(ic.getHandlerforReleased(k,true));
			v.setOnDragDetected(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					v.startFullDrag();
					System.out.println("drag detected");
					
				}
			});
			tile.getChildren().add(v);
		});
		
		return tile;
	}
	
	public HBox  addBudgetLepPane() {
		HBox budgetLepPane = new HBox();
		budgetLepPane.setSpacing(20);
		
		ImageView lepIv= new ImageView(lep);
		lepIv.setPreserveRatio(true);
		lepIv.setFitHeight(50);
		ImageView budget = new ImageView(dollar);
		budget.setPreserveRatio(true);
		budget.setFitHeight(50);
		Label lepCount = new Label("0");
		lepCount.setFont(new Font("Arial", 16));
		Label budgetCount = new Label(""+ic.getBudget());
		budgetCount.setFont(new Font("Arial", 16));
		lepCount.setGraphic(lepIv);
		budgetCount.setGraphic(budget);
		
		budgetLepPane.getChildren().add(lepCount);
		budgetLepPane.getChildren().add(budgetCount);
		
		return budgetLepPane;
		
	}
	
	
	
	public void updateBudgetandLep(int cost, int lepCount) {
		border.getChildren().remove(border.getTop());
		
		HBox budgetLepPane = new HBox();
		budgetLepPane.setSpacing(20);
		ImageView lepIv= new ImageView(lep);
		lepIv.setPreserveRatio(true);
		lepIv.setFitHeight(50);
		ImageView budget = new ImageView(dollar);
		budget.setPreserveRatio(true);
		budget.setFitHeight(50);
		Label leps = new Label(""+lepCount);
		leps.setFont(new Font("Arial", 16));
		Label budgetCount = new Label(""+cost);
		budgetCount.setFont(new Font("Arial", 16));
		leps.setGraphic(lepIv);
		budgetCount.setGraphic(budget);
		budgetLepPane.getChildren().add(leps);
		budgetLepPane.getChildren().add(budgetCount);
		
		budgetLepPane.setAlignment(Pos.CENTER);
		border.setTop(budgetLepPane);
		
	}
	
	public void addImageView(double x, double y, Boolean startingInTile) {
		System.out.println("in the inner addImageView");
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
//		iv2.setTranslateX(iv2.getLayoutX()+x);
//		iv2.setTranslateY(iv2.getLayoutY()+y);
//		setX(x,iv2);
//		setY(y,iv2);
		iv2.setTranslateX(x);
		iv2.setTranslateY(y);
		iv2.setOnMouseDragged(ic.getHandlerforDrag());
		c.setOnMouseDragReleased(ic.getHandlerForDragReleasedOver(false));
//		iv2.setOnMouseReleased(event->{
//			c.setOnMouseDragEntered(event2-> {
//				c.setFitHeight(85);
//				removePlant(iv2);
//			});
//		});
		border.getChildren().add(iv2);
	}
	
	public void makeInfoPane(String name,String info) {
		BorderPane info1 = new BorderPane();
		info1.setPrefWidth(screenWidth/4);
		info1.setMinHeight(screenHeight-300);
		info1.setStyle("-fx-background-color: LIGHTBLUE");
		
		Label title = new Label(name);
		
		Button toggle = new Button();
		Image toggleIM = new Image(getClass().getResourceAsStream("/toggle.png"));
		ImageView toggleIV = new ImageView(toggleIM);
		toggleIV.setPreserveRatio(true);
		toggleIV.setFitHeight(10);
		toggle.setGraphic(toggleIV);
		toggle.setOnAction(event->{
			border.getChildren().remove(border.getRight());
		});
		
		HBox top = new HBox();
//		top.setSpacing(80);
		top.getChildren().add(toggle);
		toggle.setAlignment(Pos.TOP_LEFT);
		top.getChildren().add(title);
		title.setAlignment(Pos.CENTER);
		
		Text tf = new Text();
		tf.setText(info);
		
		info1.setTop(top);
		info1.setCenter(tf);
		info1.setAlignment(tf, Pos.CENTER);
		
		border.setRight(info1);
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
		border.setMinHeight(screenHeight/3);
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
		oblist.put("commonMilkweed", iv1);
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
		oblist.put("pine", iv2);
		
		return oblist;
	}
	
	public void removePlant(Node n) {
		border.getChildren().remove(n);
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	public Node compost() {
		return this.compost();
	}
	public void showCompostBin() {
//		Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
//		ImageView c = new ImageView(compost);
		c.setPreserveRatio(true);
		c.setFitHeight(75);
		c.setTranslateX(screenWidth/6);
		c.setTranslateY((screenHeight-200)/2);
		c.setOnMouseExited(event->{
			c.setFitHeight(75);
		});
		c.setOnMouseEntered(event->{
			c.setFitHeight(85);
			
		});
		border.getChildren().add(c);
	}
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}
