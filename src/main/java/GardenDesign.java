import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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

/**
 * This class sets the main screen
 * It allows user to make their garden based on set preferences and conditions
 * @author Arunima Dey, Dea Harjianto
 *
 */
public class GardenDesign extends View{
//	public Controller ic;
	Canvas canvas;
	//Panes
	public VBox vb = new VBox();
//	public BorderPane stack = new BorderPane();
	public TilePane tile = new TilePane();
	public BorderPane comparePane = new BorderPane();
	public StackPane info = new StackPane();
	Map<String,ImageView> oblist;
	Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
	ImageView c = new ImageView(compost);
	Pane main;
	
	public ArrayList<ImageView> addedPlants;
	
	/**
	 * Initializes an instance of GardenDesign
	 * @param stage the Stage 
	 * @param c the controller
	 * @param manageView view manager that stores shared information
	 */
	public GardenDesign(Stage stage, Controller c, ManageViews manageView) {
		super(stage,c,manageView);
//		this.ic=c;
		//oblist = initializeHashMap();
		oblist = manageView.getPlantImages();					// loading in plantImages
		vb = addVBox();
		border = new BorderPane();
		main = addCanvas();
		border.setCenter(main);
		
		ScrollPane scroll = new ScrollPane();					// for holding plant images and TilePane
		tile.setMaxWidth(screenHeight);

		tile = addTilePane();
		
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // vertical scroll bar
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        //scroll.setMaxWidth(screenWidth);
        scroll.setMaxHeight(screenHeight);						// needed to initialize a dimension for scrollpane; leave in
		scroll.setContent(tile);
		border.setBottom(scroll);
		//border.setBottom(tile);
		comparePane = addBorderPane();
		
		BorderPane bd2= new BorderPane();
		bd2.setTop(vb);
		bd2.setAlignment(bd2, Pos.TOP_LEFT);
		bd2.setBottom(comparePane);
		bd2.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		
		border.setLeft(bd2);

		showCompostBin();
	}
	
	/**
	 * Makes the canvas so the previously set garden outline can be displayed
	 * Canvas then places inside a pane
	 * @return the created pane
	 */
	public Pane addCanvas() {
		Pane gardenDesign = new Pane();
		gardenDesign.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color:GREY; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
		gardenDesign.getChildren().add(canvas);
		
		canvas.widthProperty().bind(gardenDesign.widthProperty());
		canvas.heightProperty().bind(gardenDesign.heightProperty());
		
		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		return gardenDesign;
	}
	
	/**
	 * Makes the pane that holds all the ImageViews of the plants
	 * Each of the imageView has handlers for different events
	 * @return the created pane
	 */
	public TilePane addTilePane() {
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: LIGHTSTEELBLUE");
		oblist.forEach((k,v)->{
			v.setOnMousePressed(controller.getHandlerforPressed(k));
			v.setOnMouseDragged(controller.getHandlerforDrag());
			v.setOnMouseReleased(controller.getHandlerforReleased(k,true));
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
	
	/**
	 * Adds the pane that hols the lep count and the budget
	 * Called from previous screen after user has set a budget
	 */
	public void addBudgetLepPane() {
		Image lep = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
		
		HBox blPane = new HBox();
		blPane.setSpacing(20);
		
		ImageView lepIv= new ImageView(lep);
		lepIv.setPreserveRatio(true);
		lepIv.setFitHeight(50);
		ImageView budget = new ImageView(dollar);
		budget.setPreserveRatio(true);
		budget.setFitHeight(50);
		Label lepCount = new Label("0");
		lepCount.setFont(new Font("Arial", 16));
		Label budgetCount = new Label(""+controller.getBudget());
		budgetCount.setFont(new Font("Arial", 16));
		lepCount.setGraphic(lepIv);
		budgetCount.setGraphic(budget);
		
		blPane.getChildren().add(lepCount);
		blPane.getChildren().add(budgetCount);
		
		blPane.setAlignment(Pos.CENTER);
		border.setTop(blPane);
		border.setAlignment(blPane, Pos.CENTER);

		
	}

	/**
	 * Everytime a plant is placed onto or removed the garden the lep count and budget is updated
	 */
	public void updateBudgetandLep(int cost, int lepCount) {
		Image lep = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
		
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
	
	/**
	 * After a drag release a new imageview is created inside the center pane
	 * This new imageView is a copy of the imageView that was dragged and can be dragged, cannot be used to create another imageView
	 */
	public void addImageView(double x, double y, String key, double heightWidth) {
		System.out.println("in the inner addImageView");
//		ImageView iv2 = oblist.get(key);
		System.out.println("key: "+key);
//		Image im = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image im = new Image(getClass().getResourceAsStream("/plantimg/"+key+".png"));
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(heightWidth);
		iv2.setFitWidth(heightWidth);
		
		iv2.setTranslateX(x-main.getLayoutX());
		iv2.setTranslateY(y-main.getLayoutY());

		iv2.setOnMousePressed(controller.getHandlerforPressed(null));
		iv2.setOnMouseDragged(controller.getHandlerforDrag());
		iv2.setOnMouseReleased(controller.getHandlerforReleased(key, false));
		iv2.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				iv2.startFullDrag();
				System.out.println("drag detected");
				
			}
		});
		
		c.setOnMouseDragEntered(controller.getHandlerforMouseEntered(key));
//		c.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
//			@Override
//			public void handle(MouseDragEvent event) {
//				// TODO Auto-generated method stub
//				System.out.println("trying to remove");
//				c.setFitHeight(85);
//				ImageView iv = (ImageView) event.getGestureSource();
//				oblist.forEach((k,v)->{
//					Image im = new Image(getClass().getResourceAsStream("/"+k+".jpg"));
//					Image im2 = iv.getImage();
//					if(im==im2) {
//						System.out.println("EQUAL");
//					}
//				});
////				System.out.println("key: "+key);
//				removePlant((Node)event.getGestureSource());
//				
//			}
//		});
		
		
		main.getChildren().add(iv2);
	}
	
	/**
	 * Makes the pane will display information about a given plant when it is clicked
	 */
	public void makeInfoPane(String name,String info) {
		BorderPane info1 = new BorderPane();
		info = fitInfo(info);
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
	
	/**
	 * Starts a new paragraph after a few characters so the information can bettre fit into the pane
	 * @param info the information that will be changed
	 * @return the changed string
	 */
	public String fitInfo(String info) {
		StringBuilder sb = new StringBuilder(info);
		for(int i = 20; i<info.length(); i+=30) {
			if(info.charAt(i)==' ') {
				sb.insert(i, "\n");
			}
			else {
				for(int j = i; j<info.length(); j++) {
					if(info.charAt(j)==' ') {
						sb.insert(j, "\n");
						i = j;
						break;
					}
				}
			}
			sb.insert(i, "\n");
		}
		return sb.toString();
	}
	
	/**
	 * Adds the navigation buttons to a pane. 
	 * Back takes to the previous screen, next takes you to the next, learn more takes you to learn more page and save saves an image of the garden
	 * @return the new pane 
	 */
	public VBox addVBox() {
		VBox vb = new VBox();
		vb.setStyle("-fx-background-color: LIGHTBLUE");
		vb.setSpacing(10);
		vb.setMinHeight(screenHeight/4);
		vb.setPrefWidth(screenHeight/4);
		vb.setAlignment(Pos.CENTER);;
		Button[] buttons = new Button[] {
			addNextButton("Back","ConditionScreen"), addNextButton("Learn More", "LearnMore"), addNextButton("Lepedia", "Lepedia")
		};
		vb.getChildren().addAll(buttons);
		Button save = new Button("Next");
		save.setPrefSize(buttonWidth, buttonHeight);
		save.setOnAction(e->{
			saveGardenImage();
			setOnMouse(save);
			save.setOnAction(controller.getHandlerforClicked("Summary"));
		});
		vb.getChildren().add(save);
		return vb;
		
	}
	
	/**
	 * Saves an image of the garden when save button is pressed
	 */
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
	
	/**
	 * Makes the compare pane where plants can be placed and compared 
	 * @return the created pane
	 */
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
	
	/**
	 * makes the pane that will display information of the plants in compare pane
	 * @return the created pane
	 */
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
	
	/**
	 * Makes the pane that plants are dragged into for the comparing
	 * @param background the background color that is to be set for the pane
	 * @return the created pane
	 */
	public StackPane addStackPane(String background) {
		StackPane stack = new StackPane();
		stack.setStyle("-fx-border-color:GREY; -fx-border-width:1px; "+background);
		stack.setMinWidth(screenHeight/8);
		return stack;
	}
	
	/**
	 * Makes an observable hashmap for all the images of the pants that go into the tile pane 
	 * @return the created hashmap
	 */
	/*public Map<String,ImageView> initializeHashMap(){
//		oblist = FXCollections.observableHashMap();
		oblist = new HashMap<>();
		manageView.plantImages.forEach((k,v)->{
			try {
				saveImage(v,"src/"+k+".jpg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	}*/
	
	/**
	 * This method saves the Image that comes from a given url into a filea
	 * @param imageUrl the url for the image
	 * @param destinationFile the file where it will get saved
	 * @throws IOException the possible exception that may occur
	 */
	//https://stackoverflow.com/questions/10292792/getting-image-from-url-java
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
	    URL url = new URL(imageUrl);
	    InputStream is = url.openStream();
	    OutputStream os = new FileOutputStream(destinationFile);

	    byte[] b = new byte[2048];
	    int length;

	    while ((length = is.read(b)) != -1) {
	        os.write(b, 0, length);
	    }

	    is.close();
	    os.close();
	}
	
	/**
	 * removed the copy of the plant imageView that is dragged over compost 
	 */
	public void removePlant(Node n) {
		main.getChildren().remove(n);
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	
	/**
	 * Adds a compost to the screen that is used to remove copies of plant imageViews
	 */
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