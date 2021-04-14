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
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
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
	Canvas canvas;
	//Panes
	public VBox vb = new VBox();
	public BorderPane stack = new BorderPane();
	public TilePane tile = new TilePane();
	public BorderPane comparePane = new BorderPane();
	public StackPane info = new StackPane();
	ObservableMap<String,ImageView> oblist;
	Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
	ImageView c = new ImageView(compost);
	Image lep = new Image(getClass().getResourceAsStream("/butterfly1.png"));
	Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
	HBox blPane;
	Pane main;
	
	public GardenDesign(Stage stage, Controller c, ManageViews manageView) {
		super(stage,c,manageView);
		this.ic=c;
		oblist = initializeHashMap();
		vb = addVBox();
//		Canvas canvas = new Canvas(screenWidth, screenHeight);
		border = new BorderPane();
		main = addCanvas();
		border.setCenter(main);
//		Canvas canvas = new Canvas(main.getWidth(), main.getHeight());
//		canvas.setStyle("-fx-background-color: PINK");
//		main.getChildren().add(canvas);

		tile = addTilePane();
		border.setBottom(tile);
		comparePane = addBorderPane();
		
		BorderPane bd2= new BorderPane();
		bd2.setTop(vb);
		bd2.setAlignment(bd2, Pos.TOP_LEFT);
		bd2.setBottom(comparePane);
		bd2.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		
		border.setLeft(bd2);

		showCompostBin();
		
//        gc = canvas.getGraphicsContext2D();	
//        gc.drawImage(im, 0, 0, 50, 50);
//        gc.drawImage(im, screenHeight, screenHeight, screenWidth, screenHeight);
	}
	
	public Pane addCanvas() {
		Pane gardenDesign = new Pane();
		gardenDesign.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
		gardenDesign.getChildren().add(canvas);
		
		canvas.widthProperty().bind(gardenDesign.widthProperty());
		canvas.heightProperty().bind(gardenDesign.heightProperty());
		
		canvas.widthProperty().addListener(e -> manageView.redrawImage());
		canvas.heightProperty().addListener(e -> manageView.redrawImage());
		
		
		return gardenDesign;
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
			main.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
				@Override
				public void handle(MouseDragEvent event) {
					System.out.println("entered the pane");
					
				}
				
			});
			
			tile.getChildren().add(v);
		});
		
		return tile;
	}
	
	public void  addBudgetLepPane() {
		blPane = new HBox();
		blPane.setSpacing(20);
		
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
		
		blPane.getChildren().add(lepCount);
		blPane.getChildren().add(budgetCount);
		
		blPane.setAlignment(Pos.CENTER);
		border.setTop(blPane);
		border.setAlignment(blPane, Pos.CENTER);

		
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
	
	public void addImageView(double x, double y, boolean startingInTile, String key) {
		System.out.println("in the inner addImageView");
		Image im = new Image(getClass().getResourceAsStream("/"+key+".jpg"));
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(100);
		
		iv2.setTranslateX(x-main.getLayoutX());
		iv2.setTranslateY(y-main.getLayoutY());

		iv2.setOnMousePressed(ic.getHandlerforPressed(null));
		iv2.setOnMouseDragged(ic.getHandlerforDrag());
		iv2.setOnMouseReleased(ic.getHandlerforReleased(key, false));
		iv2.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				iv2.startFullDrag();
				System.out.println("drag detected");
				
			}
		});
		
		c.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {

			@Override
			public void handle(MouseDragEvent event) {
				// TODO Auto-generated method stub
				System.out.println("trying to remove");
				c.setFitHeight(85);
				removePlant(iv2);
				
			}
			
		});
		
		main.getChildren().add(iv2);


	}
	
	public void makeInfoPane(String name,String info) {
		BorderPane info1 = new BorderPane();

		info1.setPrefWidth(screenWidth/6);

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
	
	  
//	public String fitInfo(String info) {
//		StringBuilder sb = new StringBuilder(info);
//		for(int i = 20; i<info.length(); i+=20) {
//			if(info.charAt(i)==' ') {
//				sb.insert(i, "\n");
//			}
//			else {
//				for(int j = i; j<info.length(); j++) {
//					if(info.charAt(j)==' ') {
//						sb.insert(j, "\n");
//						i = j;
//						break;
//					}
//				}
//			}
//			sb.insert(i, "\n");
//		}
	//	
//		return sb.toString();
	//}
	
	public VBox addVBox() {
		VBox vb = new VBox();
		vb.setStyle("-fx-background-color: GAINSBORO");
		vb.setSpacing(10);
		vb.setMinHeight(screenHeight/4);
		vb.setMaxWidth(screenHeight/9);
		vb.setAlignment(Pos.CENTER);;
		Button[] buttons = new Button[] {
			addNextButton("Back","ConditionScreen"), addNextButton("Learn More", "LearnMore"), new Button("Clear"),addNextButton("Next","Summary")
		};
		vb.getChildren().addAll(buttons);
		Button save = new Button("Save");
		save.setOnAction(e->{
			saveGardenImage();
		});
		vb.getChildren().add(save);
		return vb;
		
	}
	
	public void saveGardenImage() {
		main.getChildren().remove(c);
		
		this.manageView.setImage(canvas.snapshot(null, null));
		
		c.setPreserveRatio(true);
		c.setFitHeight(75);

		c.setTranslateX(screenWidth/110);

		c.setTranslateY((screenHeight-200)/2);
		c.setOnMouseExited(event->{
			c.setFitHeight(75);
		});
		c.setOnMouseEntered(event->{
			c.setFitHeight(85);
			
		});

		main.getChildren().add(c);
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
//		ImageView iv = new ImageView(im);
//		iv.setPreserveRatio(true);
//		iv.setFitHeight(100);
//		stack.getChildren().add(iv);
		return stack;
	}
	
	public ObservableMap<String,ImageView> initializeHashMap(){
		oblist = FXCollections.observableHashMap();
		manageView.plantImages.forEach((k,v)->{
			if(getClass().getResourceAsStream("/"+k+".jpg")!=null) {
				System.out.println(k);
				Image im = new Image(getClass().getResourceAsStream("/"+k+".jpg"));
				ImageView iv1 = new ImageView(im);
				iv1.setPreserveRatio(true);
				iv1.setFitHeight(75);
				oblist.put(k, iv1);
				
			}
		});
		return oblist;
	}
	
	public void removePlant(Node n) {
		main.getChildren().remove(n);
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	
	public Node compost() {
		return this.c;
	}
	
	public void showCompostBin() {
		c.setPreserveRatio(true);
		c.setFitHeight(75);

		c.setTranslateX(screenWidth/110);

		c.setTranslateY((screenHeight-200)/2);
		c.setOnMouseExited(event->{
			c.setFitHeight(75);
		});
		c.setOnMouseEntered(event->{
			c.setFitHeight(85);
			
		});

		main.getChildren().add(c);
		

	}
	
	//TODO: will make a list view of all the plants that are put in the compost.
	//Clicking on one will place in the pane and can be placed wherever 
	//the user wants
	public void displayBasket() {} //Shows the plants that are currently in the garden on the rightmost pane
}