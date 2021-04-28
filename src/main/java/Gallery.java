import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

//https://stackoverflow.com/questions/44841329/how-to-implement-serializable-for-my-project-to-have-persistence
public class Gallery extends View{
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
		hb1.getChildren().add(addNextButton("Back","Start"));
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
		tile.setPadding(new Insets(5, 5, 5, 5));
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
	public void loadScreen(WritableImage gardenImage, int index, double cost, int lep) {
		System.out.println("in here");
//		try {
//			FileInputStream fis = new FileInputStream("garden1.ser");
//			ObjectInputStream ois = new ObjectInputStream(fis);
//			Garden g = (Garden) ois.readObject();
//			g.getPlants().forEach(k->{
//				System.out.println(k.getName());
//			});
//			ois.close();
////			canvas = g.getCanvas();
////			if(g.getPane()==null) {
////				System.out.println("not set");
////			}
////			StackPane sp = (StackPane) g.getPane();
//			System.out.println(g.getCost());
//			System.out.println(g.getNumLeps());
//			new File("garden1.ser").delete();
//			ArrayList<ImageView> m = new ArrayList<>();
//			for(int i = 0; i<100; i++) {
//				ImageView iv = new ImageView(g.image);
//				iv.setPreserveRatio(true);
//				iv.setFitHeight(150);
//				m.add(iv);
//			}
			ImageView iv = new ImageView(gardenImage);
			iv.setPreserveRatio(true);
			iv.setFitHeight(100);
			iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					final Stage dialog = new Stage();
	                dialog.initModality(Modality.APPLICATION_MODAL);
	                dialog.initOwner(stage);
	                BorderPane bp = new BorderPane();
	                VBox vb = new VBox(20);
	                Button edit = new Button("Edit");
	                edit.setOnAction(c.getHandlerforEditSaved(index,dialog));
	                vb.getChildren().add(edit);
	                bp.setBottom(vb);
	                vb.setAlignment(Pos.CENTER);
	                bp.setAlignment(vb, Pos.CENTER);
	                Label information= new Label("Cost: "+cost+" Leps Supported "+lep);
	                information.setFont(new Font("Arial", 24));
	                information.setAlignment(Pos.CENTER);
	                bp.setTop(information);
	                bp.setAlignment(information, Pos.CENTER);
	                ImageView garden = new ImageView(gardenImage);
	                garden.setPreserveRatio(true);
	                garden.setFitHeight(500);
	                bp.setCenter(garden);
	                Scene dialogScene = new Scene(bp, 700, 600);
	                dialog.setScene(dialogScene);
					dialog.show();
				}
	            
			});
			tile.getChildren().add(iv);
			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			System.out.println("No the error is here");
//			e.printStackTrace();
//		}
	}
	
	
	
//	public void clearTilePane() {
//		tile.getChildren().forEach(children->{
//			tile.getChildren().remove(children);
//		});
//	}
	
	public VBox makeSummary(double cost, int lep) {
		VBox vb1 = new VBox();
		
		return vb1;
	}

	
	
}
