import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import java.util.*;

//https://stackoverflow.com/questions/44841329/how-to-implement-serializable-for-my-project-to-have-persistence
public class Gallery extends View{
	final int GARDEN_IMAGEVIEW_HEIGHT=100;
	final int HBOX_SPACING = 20;
	final int INFO_IV_SIZE = 50;
	Controller c;
	public ArrayList<Button> multiview;
	public Button back;
	public Button sort;
	public TextField search;
	public TilePane tile;
	Stage stage;
	
	public Gallery(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();
		this.stage = stage;
		this.c = c;
		// back button 
		HBox hb1 = new HBox();
		hb1.setAlignment(Pos.BASELINE_LEFT);
		hb1.setPadding(new Insets(20));
		back = new Button("Back");
		back.setPrefSize(100, 30);;
//		back.setOnAction(controller.getHandlerforClicked("Back to Start"));
		hb1.getChildren().add(addNextButton("back","Start"));
		border.setBottom(hb1);
		
		// get the summary pane on the right to display lep + cost
		VBox vb1 = new VBox();
		vb1.setPadding(new Insets(20));
		vb1.setSpacing(15);
		vb1.setStyle("-fx-background-color: lavender");
		Text summary = new Text("Summary");
		summary.setTextAlignment(TextAlignment.LEFT);
		summary.setFont(Font.font(null, FontWeight.BOLD, 30));
		vb1.getChildren().add(summary);
		border.setRight(vb1);
		
		
		//make scrollable screen with scroll bar
//		ScrollBar scroll = new ScrollBar();
//		scroll.setOrientation(Orientation.VERTICAL);
		ScrollPane root = new ScrollPane();
		
		tile = new TilePane();
		tile.setStyle("-fx-background-color: lightblue; -fx-border-color: chocolate; -fx-border-width: 5px");
		tile.setPadding(new Insets(5));
        tile.setVgap(4);
        tile.setHgap(4);
        tile.setPrefHeight(stage.getHeight());
        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);    // Horizontal scroll bar
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);    // Vertical scroll bar
        root.setFitToHeight(true);
        root.setFitToWidth(true);
        root.setContent(tile);  
//		ap1.getChildren().add(scroll);
//		
//		AnchorPane.setTopAnchor(scroll, 0d);
//		AnchorPane.setRightAnchor(scroll, 0d);
//		AnchorPane.setBottomAnchor(scroll, 0d);
		border.setCenter(root);
		
		// make title of the page
		HBox hb2 = new HBox();
		hb2.setPadding(new Insets(20));
		Text title = new Text("Gallery");
		title.setTextAlignment(TextAlignment.LEFT);
		title.setFont(Font.font(null, FontWeight.BOLD, 40));
		hb2.getChildren().add(title);
		border.setTop(hb2);
		
	}

	//https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
	public void loadScreen(WritableImage gardenImage, int index, double cost, double leps) {
		System.out.println("in here");
			ImageView iv = new ImageView(gardenImage);
			iv.setPreserveRatio(true);
			iv.setFitHeight(GARDEN_IMAGEVIEW_HEIGHT);
			iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					final Stage dialog = new Stage();
	                dialog.initModality(Modality.APPLICATION_MODAL);
	                dialog.initOwner(stage);
	                
	                Image lep = new Image(getClass().getResourceAsStream("/butterfly1.png"));
	        		Image dollar = new Image(getClass().getResourceAsStream("/dollar.png"));
	        		
	        		ImageView lepIv= new ImageView(lep);
	        		ImageView budgetIv = new ImageView(dollar);
	        		lepIv.setPreserveRatio(true);
	        		lepIv.setFitHeight(INFO_IV_SIZE);
	        		budgetIv.setPreserveRatio(true);
	        		budgetIv.setFitHeight(INFO_IV_SIZE);
	        		
	        		Label lepLabel = new Label(""+leps);
	        		lepLabel.setFont(new Font("Arial", 16));
	        		Label costLabel = new Label(""+cost);
	        		costLabel.setFont(new Font("Arial", 16));
	        		lepLabel.setGraphic(lepIv);
	        		costLabel.setGraphic(budgetIv);
	        		
	        		HBox information = new HBox(HBOX_SPACING);
	        		information.setAlignment(Pos.CENTER);
	        		information.getChildren().add(lepLabel);
	        		information.getChildren().add(costLabel);
	        		
	                BorderPane bp = new BorderPane();
	                
	                HBox hb = new HBox(HBOX_SPACING);
	                Button edit = new Button("Edit");
	                edit.setOnAction(c.getHandlerforEditSaved(index,dialog));
	                hb.getChildren().add(edit);
	                bp.setBottom(hb);
	                hb.setAlignment(Pos.CENTER);
	                bp.setAlignment(hb, Pos.CENTER);
	                
	                bp.setTop(information);
	                bp.setAlignment(information, Pos.CENTER);
	                ImageView garden = new ImageView(gardenImage);
	                garden.setPreserveRatio(true);
	                garden.setFitHeight(5*GARDEN_IMAGEVIEW_HEIGHT);
	                bp.setCenter(garden);
	                Scene dialogScene = new Scene(bp);
	                dialog.setScene(dialogScene);
					dialog.show();
				}
	            
			});
			tile.getChildren().add(iv);

	}

	
	
}
