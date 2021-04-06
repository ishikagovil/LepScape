import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.util.*;

public class Start extends View {
	public ArrayList<Button> buttons;
	
	public Start(Stage stage, Scene scene, Group root, Controller c) {
		super(stage, root, c);
		Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);
        
        //Populate button array
		buttons = new ArrayList<Button>();
		for(int i = 0; i< 3; i++) {
			//Initialize buttons
			Button b;
			if(i ==0 ) 
				b = addButton(screenWidth/2-175 + 100*i, screenHeight/2, "Continue", EClicked.LEFTOFF);			
			else if(i == 1)
				b = addButton(screenWidth/2-175 + 100*i, screenHeight/2, "New Garden", EClicked.PLOTDESIGN);		
			else
				b = addButton(screenWidth/2-175 + 100*i, screenHeight/2, "Gallery", EClicked.GALLERY);		
			
			buttons.add(b);
			root.getChildren().add(b);
		}
        gc = canvas.getGraphicsContext2D();	
        stage.show();
	}
}