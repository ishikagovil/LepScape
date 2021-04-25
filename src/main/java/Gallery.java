import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

//https://stackoverflow.com/questions/44841329/how-to-implement-serializable-for-my-project-to-have-persistence
public class Gallery extends View{
	public ArrayList<Button> multiview;
	public Button back;
	public Button sort;
	public TextField search;
	Garden g;
	
	public Gallery(Stage stage, Controller c, ManageViews manageView) {
		super(stage, c, manageView);
		border = new BorderPane();

		// back button 
		HBox hb1 = new HBox();
		hb1.setAlignment(Pos.BASELINE_LEFT);
		hb1.setPadding(new Insets(20));
		back = new Button("Back");
		back.setPrefSize(100, 30);;
		setOnMouse(back);
		back.setOnAction(controller.getHandlerforClicked("Start"));
		hb1.getChildren().add(back);
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
		ScrollBar scroll = new ScrollBar();
		scroll.setOrientation(Orientation.VERTICAL);
		AnchorPane ap1 = new AnchorPane();
		ap1.setStyle("-fx-background-color: lightblue; -fx-border-color: chocolate; -fx-border-width: 5px");
		ap1.getChildren().add(scroll);
		AnchorPane.setTopAnchor(scroll, 0d);
		AnchorPane.setRightAnchor(scroll, 0d);
		AnchorPane.setBottomAnchor(scroll, 0d);
		border.setCenter(ap1);
		
		// make title of the page
		HBox hb2 = new HBox();
		hb2.setPadding(new Insets(20));
		Text title = new Text("Gallery");
		title.setTextAlignment(TextAlignment.LEFT);
		title.setFont(Font.font(null, FontWeight.BOLD, 40));
		hb2.getChildren().add(title);
		border.setTop(hb2);
		
	}
	
	public void loadScreen() {
		try {
			Canvas canvas;
			FileInputStream fis = new FileInputStream("garden1.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			g = (Garden) ois.readObject();
			g.getPlants().forEach(k->{
				System.out.println(k.getName());
			});
			ois.close();
//			canvas = g.getCanvas();
//			if(g.getPane()==null) {
//				System.out.println("not set");
//			}
//			StackPane sp = (StackPane) g.getPane();
			System.out.println(g.getCost());
			System.out.println(g.getNumLeps());
			new File("garden1.ser").delete();
			StackPane garden = makeSavedGarden();
//			border.setCenter(garden);
			border.setCenter(garden);
			border.setAlignment(garden, Pos.BOTTOM_LEFT);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("No the error is here");
			e.printStackTrace();
		}
	}
	
	public StackPane makeSavedGarden() {
		StackPane sp = new StackPane();
		sp.setPrefSize(200,200);
		sp.setStyle("-fx-background-color: yellow");
//		g.getPlants().forEach(plant->{
//			String key = plant.getName();
//			Image im = new Image(getClass().getResourceAsStream("/"+key+".jpg"));
//			ImageView iv = new ImageView(im);
//			double x = plant.getX();
//			double y = plant.getY();
//			System.out.println(x-sp.getLayoutX());
//			System.out.println(y-sp.getLayoutY());
//			System.out.println(sp.getLayoutBounds());
//			iv.setTranslateX(x+sp.getLayoutX());
//			iv.setTranslateY(y+sp.getLayoutY());
//			
////			iv.setTranslateX(x-sp.getLayoutX());
////			iv.setTranslateY(y-sp.getLayoutY());
//			sp.getChildren().add(iv);
//		});
//		ImageView iv = new ImageView(image);
//		sp.getChildren().add(iv);
		return sp;
	}
	
}
