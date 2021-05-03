import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This class sets the main screen
 * It allows user to make their garden based on set preferences and conditions
 * @author Arunima Dey, Dea Harjianto
 *
 */
public class GardenDesign extends View{
//	public Controller ic;
	final int STANDARD_IMAGEVIEW = 100;
	final int NORMALCOMPOST = 75;
	final int ENTERCOMPOST = 85;
	Canvas canvas;
	Stage stage;
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
	Map<String, String> plants;
	
//	public ArrayList<ImageView> addedPlants;
	
	/**
	 * Initializes an instance of GardenDesign
	 * @param stage the Stage 
	 * @param c the controller
	 * @param manageView view manager that stores shared information
	 */
	public GardenDesign(Stage stage, Controller controller, ManageViews manageView) {
		super(stage,controller,manageView);
		this.stage = stage;
//		this.ic=c;
		//oblist = initializeHashMap();
		oblist = manageView.getPlantImages();					// loading in plantImages
		vb = addGridPane();
		border = new BorderPane();
		main = addCanvas();
		
		border.setCenter(main);
		
		ScrollPane scroll = new ScrollPane();
		tile.setMaxWidth(screenHeight);
		tile.setMaxHeight(200);
		tile = addTilePane();
		
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // vertical scroll bar
//        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        //scroll.setMaxWidth(screenWidth);
        scroll.setMaxHeight(300);						// needed to initialize a dimension for scrollpane; leave in
		scroll.setContent(tile);
		border.setBottom(scroll);
		//border.setBottom(tile);
		//comparePane = addBorderPane();
		
		BorderPane bd2= new BorderPane();
		bd2.setTop(vb);
		bd2.setAlignment(bd2, Pos.TOP_LEFT);
		//bd2.setBottom(comparePane);
		//bd2.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		
		border.setLeft(bd2);

		showCompostBin();
	}
	
	/**
	 * Remakes the main pane when user tries to edit a saved garden
	 */
	public void remakePane() {
		border.getChildren().remove(border.getCenter());
		this.main = addCanvas();
		border.setCenter(main);
		border.getChildren().remove(border.getRight());
		makeInfoPane("Information","");
		main.setOnMouseDragReleased(event->{
			System.out.println("(remakePane) will read when plant enters main");
			controller.inMain = true;
		});
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
		
		canvas.setOnMouseClicked(controller.getHandlerForSectionClick(canvas));
		
		return gardenDesign;
	}
	
	/**
	 * Makes the popup pane for all the deleted p;ants
	 * @param plant all the plants that have been deleted 
	 */
	//https://docs.oracle.com/javafx/2/ui_controls/list-view.htm
	public void compostPopUp(HashSet<String> plant) {
		final Stage deleted = new Stage();
		VBox box = new VBox();
		//BorderPane bp = new BorderPane();
		Label plantName = new Label();
		deleted.setTitle("Deleted");
		deleted.initModality(Modality.APPLICATION_MODAL);
		deleted.initOwner(stage);
		ListView<Label> list = new ListView<Label> ();
		box.getChildren().addAll(list);
		VBox.setVgrow(list, Priority.ALWAYS);
		plantName.setLayoutX(10);
		plantName.setLayoutY(115);
		plantName.setFont(Font.font("Verdana", 20));
		ObservableList<Label> images = FXCollections.observableArrayList();
		plant.forEach(v->{
			System.out.println("adding plant to popUp");
			System.out.println(v);
			Image im1 = new Image(getClass().getResourceAsStream("/plantimg/"+v+".png"));
			ImageView iv1 = new ImageView(im1);
			iv1.setPreserveRatio(true);
			iv1.setFitHeight(STANDARD_IMAGEVIEW);
			Label l = new Label(v);
			l.setGraphic(iv1);
			iv1.setOnMouseClicked(e->{
				System.out.println("the deleted clicked");
			});
			images.add(l);
		});
		list.setItems(images);
		
		list.setCellFactory(lv -> new ListCell<Label>() {
		    @Override
		    public void updateItem(Label item, boolean empty) {
		        super.updateItem(item, empty);
		        if (empty) {
		            setText(null);
		        } else {
		        	System.out.println("where are we");
		        	this.setText(getDisplayText(item));
		        	Image im1 = new Image(getClass().getResourceAsStream("/plantimg/"+getDisplayText(item)+".png"));
					ImageView iv1 = new ImageView(im1);
					iv1.setPreserveRatio(true);
					iv1.setFitHeight(STANDARD_IMAGEVIEW);
		        	this.setGraphic(iv1);
		        	this.setGraphicTextGap(10);
		        }
		    }
		});
		
		list.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	if(event.getClickCount()==2) {
	        		System.out.println("clicked on " + getDisplayText(list.getSelectionModel().getSelectedItem()));
	        		String name = getDisplayText(list.getSelectionModel().getSelectedItem());
	        		String node = addImageView(main.getLayoutX(),main.getLayoutY(),name,controller.scalePlantSpread(name));
	        		controller.removeFromDeleted(name, node,main.getLayoutX(),main.getLayoutY() );
	        	}
	            
	        }
	    });
		
//		System.out.println(list.getSelectionModel().getSelectedItem().toString());
		Label item = list.getSelectionModel().getSelectedItem();
		if(item!=null) {
			String displayText = getDisplayText(item);
			System.out.println("dsiplayText: "+displayText);
		}
		
		Scene del = new Scene(box,400,700);
		deleted.setScene(del);
		deleted.show();
		
	}
	
	/**
	 * Gets the plant name from a label in deleted pane
	 * @param l the label that is clicked
	 * @return the plant name
	 */
	private String getDisplayText(Label l) {
//		return "";
		if(l.getText()!=null) {
			return l.getText();
		}
		return "";	
	}
	
	/**
	 * Makes the pane that holds all the ImageViews of the plants
	 * Each of the imageView has handlers for different events
	 * @return the created pane
	 */
	public TilePane addTilePane() {
		plants = new HashMap<>();
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: LIGHTSTEELBLUE");
		oblist.forEach((k,v)->{
			v.setOnMousePressed(controller.getHandlerforPressed(k,false));
			v.setOnMouseDragged(controller.getHandlerforDrag());
			v.setOnMouseReleased(controller.getHandlerforReleased(k,true));
			v.setOnDragDetected(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					v.startFullDrag();
					//System.out.println("drag detected");
				}
			});
			String uniqueID = UUID.randomUUID().toString();
			v.setId(uniqueID);
			plants.put(uniqueID, k);
			tile.getChildren().add(v);
		});
		main.setOnMouseDragReleased(event->{
			System.out.println("(remakePane) will read when plant enters main");
			controller.inMain = true;
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
	public void updateBudgetandLep(double cost, int lepCount) {
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
	public String addImageView(double x, double y, String key, double heightWidth) {
		System.out.println("in the inner addImageView");
//		ImageView iv2 = oblist.get(key);
		System.out.println("key: "+key);
//		Image im = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image im = new Image(getClass().getResourceAsStream("/plantimg/"+key+".png"));
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
//		iv2.setFitHeight(100);
		iv2.setFitHeight(heightWidth);
		iv2.setFitWidth(heightWidth);
		String uniqueID = UUID.randomUUID().toString();
 		iv2.setId(uniqueID);
// 		iv2.setX(x);
// 		iv2.setY(y);
		iv2.setTranslateX(x-main.getLayoutX());
		iv2.setTranslateY(y-main.getLayoutY());

		iv2.setOnMousePressed(controller.getHandlerforPressed(key,true));
		iv2.setOnMouseDragged(controller.getHandlerforDrag());
		iv2.setOnMouseReleased(controller.getHandlerforReleased(key, false));
		iv2.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				iv2.startFullDrag();
			}
		});
//		c.setOnMouseDragReleased(controller.getHandlerforMouseEntered(key));
		main.getChildren().add(iv2);
		return iv2.getId();
	}
	
	/**
	 * Makes the pane will display information about a given plant when it is clicked
	 */
	public void makeInfoPane(String name, String info) {
		BorderPane info1 = new BorderPane();
		info1.setPrefWidth(screenWidth / 6);
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
		top.getChildren().add(toggle);
		toggle.setAlignment(Pos.TOP_LEFT);
		top.getChildren().add(title);
		title.setAlignment(Pos.CENTER);
		
		Text tf = new Text();
		tf.setText(info);
		tf.setTextAlignment(TextAlignment.LEFT);
		tf.setWrappingWidth(screenWidth / 6.5);;

		info1.setTop(top);
		info1.setCenter(tf);
		info1.setAlignment(tf, Pos.CENTER);
		border.setRight(info1);
		
	}
	
	/**
	 * Adds the navigation buttons to a pane. 
	 * Back takes to the previous screen, next takes you to the next, learn more takes you to learn more page and save saves an image of the garden
	 * @return the new pane 
	 */
	public VBox addGridPane() {
		VBox vb = new VBox();
		vb.setStyle("-fx-background-color: LIGHTBLUE");
		vb.setMinHeight(screenHeight/4);
		vb.setPrefWidth(screenHeight/4);
		vb.setAlignment(Pos.CENTER);;
		ImageView[] buttons = new ImageView[] {
			addNextButton("back","ConditionScreen"), addNextButton("learnmore", "LearnMore"),addNextButton("next","Summary")
		}; 

		vb.getChildren().addAll(buttons); 
		ImageView save = new ImageView(this.manageView.buttonImages.get("save"));
		setOnMouse(save, "save");
		save.setOnMouseClicked(e->{
			saveGardenImage();
			setOnMouse(save, "save");
			save.setOnMouseClicked(controller.getHandlerforClicked("Summary"));
		});
		vb.getChildren().add(save);
		return vb;
		
	}
	
	/**
	 * Saves an image of the garden when save button is pressed
	 */
	public void saveGardenImage() {
		System.out.println("calling from in here");
		this.manageView.setSavedImage(main.snapshot(null, null));
		
		this.manageView.sp = main;
		((Summary) this.manageView.views.get("Summary")).addCanvas();
		
	}
	
	/**
	 * returns the center pane in borderPane
	 * @return the pane
	 */
	public Pane mainPane(){
		return main;
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
		System.out.println("removing plant");
		main.getChildren().remove(n);
	}
	
	public void showPlantInfo(String plantInfo) {} //Shows plant information when clicked
	public void showPlantGallery() {} //Shows plants based on conditions
	
	/**
	 * Adds a compost to the screen that is used to remove copies of plant imageViews
	 */
	public void showCompostBin() {
		c.setPreserveRatio(true);
		c.setFitHeight(NORMALCOMPOST);
		c.setTranslateX(60);
		c.setTranslateY((screenHeight-200)/2 + 100);
		c.setOnMouseExited(event->{
			System.out.println("set on mouse exited");
			c.setFitHeight(NORMALCOMPOST);
		});
		c.setOnMouseEntered(event->{
			System.out.println("set on mouse entered");
			c.setFitHeight(ENTERCOMPOST);
			
		});
		c.setOnMouseClicked(controller.getHandlerForCompostClicked());
//		c.setOnMouseDragReleased(event->{
//			System.out.println("set on mouse released");
//		});
		c.setOnMouseDragReleased(controller.getHandlerforMouseEntered(""));
		border.getChildren().add(c); 
		

	}
	
	/**
	 * updated the images on the screen
	 * @param plantNames the plants that are placed
	 */
	public void updateImageList(ArrayList<String> plantNames) {
		tile.getChildren().clear();
		
		plantNames.forEach((name) -> {
			tile.getChildren().add(oblist.get(name));
		});
	}
	
}