import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import java.awt.image.BufferedImage;
import java.util.*;
import javafx.geometry.Pos;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

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
	final int INFO_IV_SIZE = 50;
	final int HBOX_SPACING = 20;
	final int XDISPLACE = 200;
	final int YDISPLACE = 50;
	final int POPUPWIDTH = 100;
	final int POPUPHEIGHT = 50;
	final int MOVEUP = -50;
	private final int INFOSPC = 5;
	private final int SMALLFONT = 12;
	private final int SMALLSPC = 10;
	final double THRESHOLD = 0.00001;
	final double BAR_SPACING = 8;
	Canvas canvas;
	Stage stage;
	//Panes
	public VBox vb = new VBox();
//	public BorderPane stack = new BorderPane();
	public TilePane tile = new TilePane();
	public BorderPane comparePane = new BorderPane();
	public StackPane info = new StackPane();
	BorderPane bd2;
	ScrollPane scroll;
	Map<String,ImageView> oblist;
	ArrayList<ImageView> placed = new ArrayList<>();
	Image compost = new Image(getClass().getResourceAsStream("/compost.png"));
	ImageView c = new ImageView(compost);
	Pane main;
	Map<String, String> plants;
	Label plantLarge = new Label("Can't fit in garden!");
	Label collisionDetected = new Label("Plants overlapping!");
	Label emptyLabel = new Label();
	Label fillMe = new Label();
	
	/**
	 * Initializes an instance of GardenDesign
	 * @param stage the Stage 
	 * @param c the controller
	 * @param manageView view manager that stores shared information
	 */
	public GardenDesign(Stage stage, Controller controller, ManageViews manageView) {
		super(stage,controller,manageView);
		this.stage = stage;
		oblist = manageView.getPlantImages();					// loading in plantImages
		vb = addGridPane();
		border = new BorderPane();
		main = new Pane();
		addCanvas(main);
		
		border.setCenter(main);
		main.setStyle("-fx-background-color: #F0F2EF");
		
		border.setBottom(createBottom());
		
		//border.setBottom(tile);
		//comparePane = addBorderPane();
		
		bd2 = new BorderPane();
		
		bd2.setTop(vb);
		bd2.setStyle("-fx-background-color: #AFD5AA");
		BorderPane.setAlignment(bd2, Pos.TOP_LEFT);
		//bd2.setBottom(comparePane);
		//bd2.setAlignment(comparePane, Pos.BOTTOM_LEFT);
		
		border.setLeft(bd2);
		showCompostBin();
	}
	
	/**
	 * Created the bottom of the screen that hold all the plant images
	 * @return the bottom pane
	 */
	private Node createBottom() {
		VBox bottom = new VBox();
		
		HBox filterBar = new HBox(BAR_SPACING);
		
		filterBar.setStyle("-fx-background-color: #8C6057");
		filterBar.setPadding(new Insets(BAR_SPACING));
		
		TextField search = new TextField();
		search.setPromptText("Search");
		search.setOnKeyReleased((e) -> { controller.updateSearch(search.getText()); });
		
		ComboBox<Comparator<PlantSpecies>> filters = createFilterDropdown();
		
		filters.setOnAction(controller.getHandlerForSort());
		
		filterBar.getChildren().addAll(search, filters);
		
		scroll = new ScrollPane();
		tile.setMaxWidth(this.manageView.getScreenHeight());
		tile.setMaxHeight(2*STANDARD_IMAGEVIEW);
		tile = addTilePane();
		
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // horizontal scroll bar
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);    // vertical scroll bar
//        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        //scroll.setMaxWidth(screenWidth);
        scroll.setMaxHeight(2*STANDARD_IMAGEVIEW);						// needed to initialize a dimension for scrollpane; leave in
		scroll.setContent(tile);
		
		bottom.getChildren().addAll(filterBar, scroll);
		
		return bottom;
	}
	
	/**
	 * Created sort by drop downs for plants
	 * @return thee Combo box
	 */
	private ComboBox<Comparator<PlantSpecies>> createFilterDropdown() {
		ComboBox<Comparator<PlantSpecies>> combo = new ComboBox<>();
		
		List<Comparator<PlantSpecies>> sorts = new ArrayList<>();
		sorts.add(new SortByLeps());
		sorts.add(new SortByName());
		sorts.add(new SortByCost());
		
		combo.getItems().addAll(sorts);
		combo.getSelectionModel().select(0);
		
		return combo;
	}

	/**
	 * Creates the popup for when user exceeds budget
	 */
	public void budgetExceededPopup() {
		final Stage budgetExceeded = new Stage();
		budgetExceeded.initModality(Modality.APPLICATION_MODAL);
		budgetExceeded.initOwner(stage);
		budgetExceeded.setTitle("YOU HAVE EXCEEDED YOUR BUDGET!");
		Label text = new Label("To continue adding to your garden increase your budget");
		text.setStyle("-fx-font-size: 16; -fx-text-fill: white");
		Label instruction = new Label("Press enter to set new budget or the X if you are done");
		instruction.setStyle("-fx-font-size: 16; -fx-text-fill: white");
		TextField budgetField = new TextField("Enter new budget");
		budgetField.setMaxWidth(STANDARD_IMAGEVIEW);
		BorderPane border = new BorderPane();
		border.setTop(text);
		BorderPane.setAlignment(text,Pos.CENTER);
		border.setCenter(budgetField);
		border.setBottom(instruction);
		BorderPane.setAlignment(instruction,Pos.CENTER);
		budgetField.setOnKeyReleased(event->{
			if(event.getCode()==KeyCode.ENTER) {
				try {
					controller.updateBudget(Double.parseDouble(budgetField.getText()));
					budgetExceeded.close();
					controller.updateBudgetandLep();
				}catch (NumberFormatException e) {
					budgetField.clear();
				}
			}
		});
		border.setStyle(" -fx-background-color: #8C6057; -fx-padding: 10; -fx-border-color: #5C5346; -fx-border-width: 5;");
		Scene popUpScene = new Scene(border,450,STANDARD_IMAGEVIEW);
		budgetExceeded.setScene(popUpScene);
		budgetExceeded.show();
	}
	
	/**
	 * Remakes the main pane when user tries to edit a saved garden
	 */
	public void remakePane() {
//		border.getChildren().remove(border.getCenter());
		main.getChildren().clear();
		addCanvas(main);
//		this.main = addCanvas();
//		border.setCenter(main);
		border.getChildren().remove(border.getRight());
	}
	
	/**
	 * Makes the canvas so the previously set garden outline can be displayed
	 * Canvas then places inside a pane
	 * @return the created pane
	 */
	public void addCanvas(Pane main) {
		System.out.println("in addCanvas");
		canvas = new Canvas();
		canvas.setStyle("-fx-border-color:#F0F2EF; -fx-border-width:5px");
		gc = canvas.getGraphicsContext2D();
		main.getChildren().add(canvas);
		
		canvas.widthProperty().bind(main.widthProperty());
		canvas.heightProperty().bind(main.heightProperty());
		
		canvas.widthProperty().addListener(e -> controller.drawToCanvas(canvas));
		canvas.heightProperty().addListener(e -> controller.drawToCanvas(canvas));
		
		canvas.setOnMouseClicked(controller.getHandlerForSectionClick(canvas));
		
		//return gardenDesign;
	}
	
	/**
	 * Makes the popup pane for all the deleted plants
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
		plantName.setFont(Font.font("Andale Mono", FONTSIZE));
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
	        		String name = getDisplayText(list.getSelectionModel().getSelectedItem());
	        		String node = addImageView(main.getLayoutX(),main.getLayoutY(),name,controller.scalePlantSpread(name),false);
	        		controller.removeFromDeleted(name, node,main.getLayoutX(),main.getLayoutY());
	        		deleted.close();
	        	}
	            
	        }
	    });
		
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
		oblist = manageView.getPlantImages();
		plants = new HashMap<>();
		TilePane tile = new TilePane();
		tile.setStyle("-fx-background-color: #A69F98");
		oblist.forEach((k,v)->{
			v.setOnMousePressed(controller.getHandlerforPressed(k,false));
			v.setOnDragDetected((MouseEvent event)->{
				//v.startFullDrag();
				Dragboard db = v.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				ClipboardContent content = new ClipboardContent();
				Image im =  new  Image(getClass().getResourceAsStream("/plantimg/"+k+".png"), 100, 100, true, false);
				content.putString(k);
				System.out.println("the key that is set for content: "+k);
				db.setContent(content);
				db.setDragView(im);
				//event.consume();
					
			});
			hoverTooltip(controller.tooltipInfo(k),v);
			String uniqueID = UUID.randomUUID().toString();
			v.setId(uniqueID);
			plants.put(uniqueID, k);
			tile.getChildren().add(v);
		});
		main.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if(event.getGestureSource()!=main && event.getDragboard().hasString()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				
			}
		});
		main.setOnDragDropped(controller.getHandlerForDragOver());
		return tile;
	}
	
	/**
	 * Remakes the top of the border pane, that holds cost and lep count, when user places plant
	 * @param cost the new cost
	 * @param lepCount the leps supported
	 * @param budget thee total budget
	 */
	public void updateBudgetandLep(double cost, int lepCount, double budget) {
		Image lep = new Image(getClass().getResourceAsStream("/butterfly1.png"));
		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));

		if(border.getTop()!=null) {
			border.getChildren().remove(border.getTop());
		}
		BorderPane top = new BorderPane();
		//top.setPadding(new Insets(HBOX_SPACING));
		HBox budgetLepPane = new HBox();
		budgetLepPane.setPadding(new Insets(HBOX_SPACING));
		top.setStyle("-fx-background-color: #A69F98");
		budgetLepPane.setSpacing(HBOX_SPACING);
		ImageView lepIv= new ImageView(lep);
		ImageView budgetIv = new ImageView(dollar);
		
		hoverTooltip("Insects supported", lepIv);
		hoverTooltip("Cost",budgetIv);
		
		lepIv.setPreserveRatio(true);
		lepIv.setFitHeight(INFO_IV_SIZE);
		budgetIv.setPreserveRatio(true);
		budgetIv.setFitHeight(INFO_IV_SIZE);
		Label leps = new Label(""+lepCount);
		leps.setFont(new Font("Andale Mono", 16));
		Label budgetCount = new Label(""+cost);
		budgetCount.setFont(new Font("Andale Mono", 16));
		leps.setGraphic(lepIv);
		budgetCount.setGraphic(budgetIv);
		budgetLepPane.getChildren().add(leps);
		budgetLepPane.getChildren().add(budgetCount);
		ProgressBar costBar = new ProgressBar((budget-cost)/budget);
		if((budget-cost)/budget<=0.1) {
			costBar.setStyle("-fx-accent: red");
		}
		budgetLepPane.getChildren().add(costBar);
		hoverTooltip("$ "+(int)cost+"/"+(int)budget, costBar);
		budgetLepPane.setAlignment(Pos.CENTER);
		top.setCenter(budgetLepPane);
		
		ImageView next = new ImageView(this.manageView.buttonImages.get("Next"));
		setOnMouse(next, "Next");
		next.setOnMouseClicked(e->{
			saveGardenImage();
			controller.switchViews("Summary");
			
		});
		
		ImageView back = new ImageView(this.manageView.buttonImages.get("Back"));
		setOnMouse(back, "Back");
		back.setOnMouseClicked(e->{
			controller.switchViews("ConditionScreen");
			
		});
		
		top.setRight(next);
		BorderPane.setAlignment(next, Pos.BOTTOM_RIGHT);
		top.setLeft(back);
		BorderPane.setAlignment(back, Pos.BOTTOM_LEFT);
		
		HBox box = new HBox();
		//box.setPadding(new Insets(HBOX_SPACING));
		box.setStyle("-fx-background-color: #a69f98");
//		Text title = new Text("Garden Design");
//		title.setFont(Font.font("Andale Mono", FontWeight.BOLD, INFO_IV_SIZE));
//		box.getChildren().add(title);
		top.setTop(box);
		border.setTop(top);
	}
	
	/**
	 * After a drag release a new imageview is created inside the center pane
	 * This new imageView is a copy of the imageView that was dragged and can be dragged, cannot be used to create another imageView
	 */
	public String addImageView(double x, double y, String key, double heightWidth, boolean initial) {
		System.out.println("in the inner addImageView");
		System.out.println("key: "+key);
		Image im = new Image(getClass().getResourceAsStream("/plantimg/"+key+".png"));
		ImageView iv2 = new ImageView(im);
		iv2.setPreserveRatio(true);
		iv2.setFitHeight(heightWidth);
		iv2.setFitWidth(heightWidth);
		String uniqueID = UUID.randomUUID().toString();
 		iv2.setId(uniqueID);
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
		//c.setOnMouseDragReleased(controller.getHandlerforMouseEntered(key));
		if (validatePlantPlacement(x, y, heightWidth)[0] > THRESHOLD && !initial) {
			// if collided, don't add ImageView to Pane
			System.out.println("collided!");
			controller.showPopupMessage("Cannot place plant there!", stage);
			return null;
		} else {
			// if no collision detected, add ImageView to Pane and add to overall plantlist
			System.out.println("no collide?");
			main.getChildren().add(iv2);
			placed.add(iv2);
			System.out.println("placed plant!");
			return iv2.getId();
		}
	}
	
	/**
	 * Determines whether the location the ImageView is being placed is a valid location.
	 * @param x
	 * @param y
	 * @param heightWidth
	 * @return double[]
	 */
	public double[] validatePlantPlacement(double x, double y, double heightWidth) {
		
		double[] returnThis = new double[5];
		
		boolean isCollide = false;
		returnThis[0] = 0;				// the distance
		returnThis[1] = 0;				// whether it's top left(1), top right(2), bottom right(3), bottom left(4)
		returnThis[2] = 0;
		returnThis[3] = 0;
		returnThis[4] = 0;
		
		//ObservableList<Node> children = main.getChildren();
		ArrayList<ImageView> children = placed;
		
		for (ImageView plantImg : children) {
			// assuming x & y are top left
			
			double compHeight = plantImg.getBoundsInParent().getHeight();		// the height of the ImageView
			double compWidth = plantImg.getBoundsInParent().getWidth();			// the width of the ImageView
			
			// compHeight should be equal to compWidth (because circle duh)
			// => radius is equal to compHeight / 2 or compWidth / 2
			
			// circle collision defined by
			// distance between centers of obj < radii of two circles
			
			double xCoord = plantImg.getBoundsInParent().getCenterX();	
			double yCoord = plantImg.getBoundsInParent().getCenterY();
			
			System.out.println("x: " + xCoord + ", y: " + yCoord);
			
			double xCoord2 = x + heightWidth/2 - XDISPLACE;
			double yCoord2 = y + heightWidth/2 - YDISPLACE;
			
			System.out.println("x2: " + xCoord2 + ", y2: " + yCoord2);
			
			double shoulderLengthApart = (heightWidth/2) + (compHeight/2);
			System.out.println("heightWidth: " + heightWidth/2);
			System.out.println("radius: " + compHeight/2);
			System.out.println("shoulderLengthApart: " + shoulderLengthApart);
			
			double distance = Math.sqrt(Math.pow(xCoord2 - xCoord, 2) + Math.pow(yCoord2 - yCoord, 2));
			System.out.println("distance: " + distance);
			
			// threshold number accounting for if they are comparing the same image
			if (distance < shoulderLengthApart && distance > 0.0001) {
				
				if (xCoord2 < xCoord) {
					if (yCoord2 < yCoord) {
						returnThis[1] = 1;
					} else {
						returnThis[1] = 4;
					}
				} else {
					if (yCoord2 < yCoord) {
						returnThis[1] = 2;
					} else {
						returnThis[1] = 3;
					}
				}
				returnThis[0] = distance;
				returnThis[2] = xCoord;
				returnThis[3] = yCoord;
				returnThis[4] = shoulderLengthApart;
			}
			System.out.println("---");
		}
		
		return returnThis;
	}
	
	/**
	 * Makes the pane will display information about a given plant when it is clicked
	 */
	public void makeInfoPane(String sciName, String name, String info) {
		BorderPane info1 = new BorderPane();
		info1.setPrefWidth(this.manageView.getScreenWidth() / 6);
		info1.setMinHeight(this.manageView.getScreenHeight()-300);
		info1.setStyle("-fx-background-color: #afd5aa");
		
		Label title = new Label(name);
		title.setFont(new Font("Andale Mono", FONTSIZE));
		title.setWrapText(true);
		title.setMaxWidth(this.manageView.getScreenWidth()/6);
		
		Button toggle = new Button();
		Image toggleIM = new Image(getClass().getResourceAsStream("/toggle.png"));
		ImageView toggleIV = new ImageView(toggleIM);
		toggleIV.setPreserveRatio(true);
		toggleIV.setFitHeight(10);
		toggle.setGraphic(toggleIV);
		toggle.setOnAction(event->{
			border.getChildren().remove(border.getRight());
		});

		VBox top = new VBox();
		top.getChildren().add(toggle);
		toggle.setAlignment(Pos.TOP_LEFT);
		title.setAlignment(Pos.CENTER);
		title.setTextAlignment(TextAlignment.CENTER);
		top.getChildren().add(title);
		top.setSpacing(SMALLSPC);
		top.setPadding(new Insets(SMALLSPC));
		
		VBox desc = new VBox();
		ImageView plant = new ImageView();
		plant.setImage(this.manageView.getPlantImages().get(sciName).getImage());
		plant.setPreserveRatio(true);
		plant.setFitWidth(this.manageView.getScreenWidth()/7);
		
		Text tf = new Text();
		tf.setText(info);
		tf.setTextAlignment(TextAlignment.CENTER);
		tf.setWrappingWidth(this.manageView.getScreenWidth() / 6.5);;
		tf.setFont(new Font("Andale Mono", 14));
		tf.setTranslateY(MOVEUP);
		
		desc.getChildren().addAll(plant, tf);
		desc.setSpacing(INFOSPC);
		desc.setPadding(new Insets(SMALLSPC));
		desc.setAlignment(Pos.BASELINE_CENTER);

		info1.setTop(top);
		info1.setCenter(desc);
		info1.setAlignment(desc, Pos.CENTER);
		border.setRight(info1);
	}
	
	/**
	 * Adds the navigation buttons to a pane. 
	 * Back takes to the previous screen, next takes you to the next, learn more takes you to learn more page and save saves an image of the garden
	 * @return the new pane 
	 */
	public VBox addGridPane() {
		VBox vb = new VBox(10);
		vb.setPadding(new Insets(HBOX_SPACING));
		vb.setStyle("-fx-background-color: #afd5aa");
		vb.setMinHeight(this.manageView.getScreenWidth()/6);
		vb.setPrefWidth(this.manageView.getScreenHeight()/7);
		vb.setAlignment(Pos.CENTER);
	
		vb.getChildren().addAll(addNextButton("Learn More", "LearnMore"), addNextButton("Plant Compare", "ComparePlants")); 
		ImageView clear = new ImageView(this.manageView.buttonImages.get("Clear"));
		setOnMouse(clear, "Clear");
		clear.setOnMouseClicked(e->{
			//setOnMouse(clear, "clear");
			Node canvas = main.getChildren().get(0);
			main.getChildren().clear();
			main.getChildren().add(canvas);
			placed.clear();
			controller.clearGarden();
		});
		vb.getChildren().add(clear);
		
		return vb;
	}
	
	/**
	 * Saves an image of the garden when save button is pressed
	 */
	public void saveGardenImage() {
		System.out.println("calling from in here");
		WritableImage wim = controller.snapshotGarden();
		BufferedImage image = SwingFXUtils.fromFXImage(wim,null);
		this.manageView.setSavedImage(image);
		
	}
	
	/**
	 * Makes the compare pane where plants can be placed and compared 
	 * @return the created pane
	 */
/*	public BorderPane addBorderPane() {
		BorderPane border = new BorderPane();
		border.setStyle("-fx-background-color: #afd5aa");
		border.setMinHeight(this.manageView.getScreenHeight()/3);
		border.setMaxWidth(this.manageView.getScreenHeight()/4);
		StackPane s1 = addStackPane("-fx-background-color: #5C5346");
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
*/	
	/**
	 * makes the pane that will display information of the plants in compare pane
	 * @return the created pane
	 */
/*	public TilePane addTile() {
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
*/	
	/**
	 * Makes the pane that plants are dragged into for the comparing
	 * @param background the background color that is to be set for the pane
	 * @return the created pane
	 */
/*	public StackPane addStackPane(String background) {
		StackPane stack = new StackPane();
		stack.setStyle("-fx-border-color:GREY; -fx-border-width:1px; "+background);
		stack.setMinWidth(this.manageView.getScreenHeight()/8);
		return stack;
	}
	*/
	/**
	 * removed the copy of the plant imageView that is dragged over compost 
	 */
	public void removePlant(Node n) {
		System.out.println("removing plant");
		placed.remove(n);
		main.getChildren().remove(n);
	}
	
	/**
	 * Adds a compost to the screen that is used to remove copies of plant imageViews
	 */
	public void showCompostBin() {
		c.setPreserveRatio(true);
		c.setFitHeight(NORMALCOMPOST);
		c.setTranslateX(60);
		c.setTranslateY((this.manageView.getScreenHeight()-200)/2 + 100);
		c.setOnMouseExited(event->{
			System.out.println("set on mouse exited");
			c.setFitHeight(NORMALCOMPOST);
		});
		c.setOnMouseEntered(event->{
			System.out.println("set on mouse entered");
			c.setFitHeight(ENTERCOMPOST);
			
		});
		c.setOnMouseClicked(controller.getHandlerForCompostClicked());
		c.setOnMouseDragReleased(controller.getHandlerforMouseEntered(""));
		bd2.getChildren().add(c);
		//border.getChildren().add(c); 
		

	}
	
	/**
	 * updated the images on the screen
	 * @param plantNames the plants that are placed
	 */
	public void updateImageList(ArrayList<String> plantNames) {
		tile = addTilePane();
		scroll.setContent(tile);
		tile.getChildren().clear();
		
		plantNames.forEach((name) -> {
			tile.getChildren().add(oblist.get(name));
		});
	}
	
}