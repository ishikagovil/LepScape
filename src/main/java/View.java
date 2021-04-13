import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.util.*;

public abstract class View{
	public Map<String, Image> plantImages;
	public int screenWidth = 1270;
	public int screenHeight = 760;
	BorderPane border;
	Stage stage;
	Controller controller;
	ManageViews manageView;
	GraphicsContext gc;

	public View(Stage stage, Controller c, ManageViews manageView) { 
		this.manageView = manageView;
		this.stage = stage;
        controller = c;
		this.stage.setTitle("Lepscape");
        importImages();
	}	
	

	
	public void setX(double x, Node n) {
		n.setTranslateX(n.getLayoutX() + x);
	}
	public BorderPane getBorderPane() {
		return border;
	}
	public int getScreenWidth() {
		return screenWidth;
	}
	public int getScreenHeight() {
		return screenHeight;
	}
<<<<<<< HEAD
=======
	public void setX(double d, Node n) {
		n.setTranslateX(n.getLayoutX() + d);
	}
>>>>>>> a49086ae286d481d0ec616efebc47eba90941dfc
	public void setY(double y, Node n) {
		n.setTranslateY(n.getLayoutY() + y);
	}
	
	public void importImages() {}
	public void changeCursor(boolean hand) { //Changes cursor to either a hand if true is passed, or pointer if false
		if(hand)
			stage.getScene().setCursor(Cursor.HAND);
		else
			stage.getScene().setCursor(Cursor.DEFAULT);
	} 
	public Button addNextButton(String text, String next) {
		Button b = new Button(text);
		setOnMouse(b);
		b.setOnAction(controller.getHandlerforClicked(next));
		return b;
	}	
<<<<<<< HEAD

	public void setOnMouse(Button b) {
		b.setOnMouseEntered(controller.getHandlerforMouseEntered());
		b.setOnMouseExited(controller.getHandlerforMouseExited());

	}

=======
	
>>>>>>> a49086ae286d481d0ec616efebc47eba90941dfc
	//Used only in gardenDesig. In here because need to called by controller
	public void addImageView(double x, double y) {}
	public void removePlant(Node n) {}
	public void makeInfoPane(String name, String info) {}
	public void updateBudgetandLep(int cost, int lepCount) {}
<<<<<<< HEAD

=======
>>>>>>> a49086ae286d481d0ec616efebc47eba90941dfc
}