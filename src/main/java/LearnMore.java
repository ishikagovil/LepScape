import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LearnMore extends View{


	public LearnMore(Stage stage, Controller c,ManageViews manageView) {
		super(stage, c,manageView);

		border = new BorderPane();
		Label title = new Label("Learn More");
		title.setFont(Font.font(null, 30));
		border.setTop(title);
		border.setAlignment(title, Pos.CENTER);
		border.setBottom(addBottomPane());
		border.setCenter(addCenterPane());
	}
	
	public HBox addBottomPane() {
		HBox box = new HBox();
		box.setPadding(new Insets(20));
		Button done = addNextButton("Done", "GardenDesign");
		box.getChildren().add(done);
		box.setAlignment(Pos.CENTER);
		return box;
	}
	
	// get from https://www.youtube.com/watch?v=C_Y6yrkj9Sg
	public ScrollPane addCenterPane(){
		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		sp.setFitToHeight(true);
		sp.setFitToWidth(true);
		
		TilePane tile = new TilePane(Orientation.HORIZONTAL);
		tile.setPadding(new Insets(10));
		tile.setVgap(4);
		tile.setHgap(4);
		tile.setPrefColumns(1);
		tile.setPrefRows(5);
		tile.setStyle("-fx-background-color: sand");
		/*
		ImageView pics[] = new ImageView[5];
		pics[0] = new ImageView(new Image(getClass().getResourceAsStream("/lep-button.png")));
		pics[1] = new ImageView(new Image(getClass().getResourceAsStream("/freehand-button.png")));
		pics[2] = new ImageView(new Image(getClass().getResourceAsStream("/polygon-button.png")));
		pics[3] = new ImageView(new Image(getClass().getResourceAsStream("/new-button.png")));
		pics[4] = new ImageView(new Image(getClass().getResourceAsStream("/gallery-button.png")));
		tile.getChildren().addAll(pics);
		*/
		tile.setAlignment(Pos.CENTER);
		tile.getChildren().addAll(addLepButton(), addFreehandButton(), addPolygonButton(), addNewButton(), addGalleryButton());
		sp.setContent(tile);
		return sp;
	}
	
	
	public HBox addLepButton() {
		HBox b = new HBox();
		Image l = new Image(getClass().getResourceAsStream("/lep-button.png"));
		ImageView lIV = new ImageView(l);
		Text d1 = new Text("Click on Lepedia button to learn more about different leps that are supported in your garden!");
		d1.setFont(Font.font(30));
		d1.setWrappingWidth(1000);
		b.getChildren().addAll(lIV, d1);
		b.setAlignment(Pos.TOP_LEFT);
		return b;
	}
	
	public HBox addFreehandButton() {
		HBox b1 = new HBox(); 
		Image l1 = new Image(getClass().getResourceAsStream("/freehand-button.png"));
		ImageView l1IV = new ImageView(l1);
		Text d2 = new Text("Click on freehand button to start designing your plot however you want.");
		d2.setFont(Font.font(30));
		d2.setWrappingWidth(screenWidth - 290);
		b1.getChildren().addAll(l1IV, d2);
		return b1;
	}
	
	public HBox addPolygonButton() {
		HBox b = new HBox(); 
		Image l = new Image(getClass().getResourceAsStream("/polygon-button.png"));
		ImageView lIV = new ImageView(l);
		Text d = new Text("Click on polygon button to start designing your plot using polygon tool, you can move the vertices to change the shape of the garden.");
		d.setFont(Font.font(30));
		d.setWrappingWidth(screenWidth - 290);
		b.getChildren().addAll(lIV, d);
		return b;
	}
	
	public HBox addNewButton() {
		HBox b = new HBox(); 
		Image l = new Image(getClass().getResourceAsStream("/new-button.png"));
		ImageView lIV = new ImageView(l);
		Label d = new Label("Choose \"New Garen\" to start designing your own garden.");
		d.setFont(Font.font(30));
		d.isWrapText();
		b.getChildren().addAll(lIV, d);
		return b;
	}
	
	public HBox addGalleryButton() {
		HBox b = new HBox(); 
		Image l = new Image(getClass().getResourceAsStream("/gallery-button.png"));
		ImageView lIV = new ImageView(l);
		Text d = new Text("Choose \"Gallery\" to view your saved gardens.");
		d.setFont(Font.font(30));
		d.setWrappingWidth(screenWidth - 290);
		b.getChildren().addAll(lIV, d);
		return b;
	}
}
