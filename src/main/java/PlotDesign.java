import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.*;

public class PlotDesign extends View{
	public ArrayList<Button> drawingShapes;
	public ArrayList<Button> editingTools;
	public TextField search;
	public Button back;
	public Button next;
	public Button clear;
	
	public PlotDesign(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
        gc = canvas.getGraphicsContext2D();	
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        
        scene.setOnMousePressed(controller.getHandlerforDrawing(true));
        scene.setOnMouseDragged(controller.getHandlerforDrawing(false));
       
        root.getChildren().add(canvas); 
        stage.show();
	}
	
	public void render(Node n) {
		gc.beginPath();
		gc.lineTo(n.getTranslateX(),n.getTranslateY());
		gc.stroke();
	}	
}