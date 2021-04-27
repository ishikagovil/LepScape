import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.*;

public abstract class View{
	public Map<String, Image> plantImages;
	public int screenWidth = 1270;
	public int screenHeight = 760;
	public double gardenWidth = 0.5*screenWidth;
	public double gardenHeight = 0.5*screenHeight;
	public double gardenTopLeftX = 0.3*screenWidth;
	public double gardenTopLeftY = 0.2*screenHeight;
	public int buttonWidth = 100;
	public int buttonHeight = 30;
	BorderPane border;
	Stage stage;
	Controller controller;
	ManageViews manageView;
	GraphicsContext gc;
	ArrayList<Line> polygonLines;
	ArrayList<Line> freeLines;

	public View(Stage stage, Controller c, ManageViews manageView) { 
		this.manageView = manageView;
		this.stage = stage;
		this.polygonLines = new ArrayList<>();
		this.freeLines = new ArrayList<>();
        this.controller = c;
		this.stage.setTitle("Lepscape");
	}	
	
	public void setX(double x, Node n) {
		n.setTranslateX(x);
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

	public void setY(double y, Node n) {
		n.setTranslateY(y);
	}

	public void changeCursor(boolean hand) { //Changes cursor to either a hand if true is passed, or pointer if false
		//https://blog.idrsolutions.com/2014/05/tutorial-change-default-cursor-javafx/ 
		if(hand)
			stage.getScene().setCursor(Cursor.HAND);
		else
			stage.getScene().setCursor(Cursor.DEFAULT);
	} 
	public Button addNextButton(String text, String next) {
		Button b = new Button(text);
		b.setPrefSize(buttonWidth, buttonHeight);
		setOnMouse(b);
		b.setOnAction(controller.getHandlerforClicked(next));
		return b;
	}	

	public void setOnMouse(Button b) {
		b.setOnMouseEntered(controller.getHandlerforMouseEntered());
		b.setOnMouseExited(controller.getHandlerforMouseExited());

	}
	
	public void drawLine(double x1, double y1, double x2, double y2, boolean isPolygon) {
		Line line = new Line(x1, y1, x2, y2);
		line.setStrokeWidth(2);
		border.getChildren().add(line);
		if(isPolygon) 
			polygonLines.add(line);
		else
			freeLines.add(line);
	}
	public void removeLines() {
			border.getChildren().removeAll(polygonLines);
			polygonLines = new ArrayList<>();
			border.getChildren().removeAll(freeLines);
			freeLines = new ArrayList<>();
	}
	
	//Used only in gardenDesign. In here because need to called by controller
<<<<<<< HEAD
	//public void addImageView(double x, double y, String key, double heightWidth) {}
=======
	//
	public void addImageView(double x, double y, String key) {}
>>>>>>> saveGarden
	public void removePlant(Node n) {}
	public void makeInfoPane(String name, String info) {}
	public void updateBudgetandLep(int cost, int lepCount) {}


}
