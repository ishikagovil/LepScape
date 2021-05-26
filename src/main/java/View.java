import javafx.geometry.Bounds;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

/**
 * Abstract class that holds all the views and any methods that are common among them
 */
public abstract class View{
	private static final int lineWidth = 3;
	final int FONTSIZE = 20;
	private static final double fillThreshold = 0.95;
	BorderPane border;
	Stage stage;
	Controller controller;
	ManageViews manageView;
	GraphicsContext gc;
	ArrayList<Line> polygonLines;
	ArrayList<Line> freeLines;
	//Image from: https://custom-cursor.com/en/collection/simpsons/sps-willie-shovel
	ImageCursor shovelCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/shovelCursor.png"), 50,50,false,false));
	//Image from: https://custom-cursor.com/en/collection/animals/blue-and-purple-butterfly
	ImageCursor handCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/lepCursor.png"), 40,40,false,false));

	/**
	 * View class that is the super class for all View screens
	 * @param stage Stage to use from JavaFX
	 * @param c controller instance
	 * @param manageView ManageViews instance
	 */
	public View(Stage stage, Controller c, ManageViews manageView) { 
		this.manageView = manageView;
		this.stage = stage;
		this.polygonLines = new ArrayList<>();
		this.freeLines = new ArrayList<>();
        this.controller = c;
		this.stage.setTitle("Lepscape");
		
	}	
	
	/**
	 * Sets the translateX value of a node n
	 * @param x new X value
	 * @param n node to update
	 */
	public void setX(double x, Node n) {
		n.setTranslateX(x);
	}
	/**
	 * Gets the BorderPane
	 * @return BorderPane
	 */
	public BorderPane getBorderPane() {
		return border;
	}

	/**
	 * Sets the translateY value of a node n
	 * @param y new Y value
	 * @param n node to set
	 */
	public void setY(double y, Node n) {
		n.setTranslateY(y);
	}

	/**
	 * Updates the cursor to the different ImageCursors
	 * @param hand
	 * @param key representing key for the ImageView
	 * @param b representing the ImageView being hovered
	 * @author Ishika Govil
	 */
	public void changeCursor(boolean hand, String key, ImageView b) { //Changes cursor to either a hand if true is passed, or pointer if false
		if(hand) {
			stage.getScene().setCursor(this.handCursor);
			b.setImage(this.manageView.buttonImages.get(key + "_h"));
			if(!key.equals("Back")  && !key.equals("Next")  && !key.equals("New") )
				hoverTooltip(key, b);
		}
		else {
			stage.getScene().setCursor(this.shovelCursor);
			b.setImage(this.manageView.buttonImages.get(key));
		}
	} 
	/**
	 * Adding tooltip to each ImageView that displays the text to the user and gives information about the ImageView
	 * @param text
	 * @param node
	 * @author Ishika Govil
	 */
	public void hoverTooltip(String text, Node node) {
		Tooltip tip = new Tooltip(text);
		tip.setFont(new Font("Andale Mono", FONTSIZE));
		tip.setShowDelay(Duration.seconds(0.5));
		tip.setOnShowing(s->{
			//https://stackoverflow.com/questions/24621133/javafx-how-to-set-tooltip-location-relative-to-the-mouse-cursor
		    //Get button current bounds on computer screen
		    Bounds bounds = node.localToScreen(node.getBoundsInLocal());
		    tip.setX(bounds.getMaxX());
		    tip.setY(bounds.getMinY());

		});
		Tooltip.install(node, tip);
	}
	/**
	 * Adds a button with the correct size and actions
	 * @param key representing key of the Image in ManageView's buttonImages HashMap
	 * @param next representing the action the button performs when clicked
	 * @return Button
	 * @author Ishika Govil
	 */
	public ImageView addNextButton(String key, String next) {
		ImageView b = new ImageView(this.manageView.buttonImages.get(key));
		setOnMouse(b, key);
		b.setOnMouseClicked(controller.getHandlerforClicked(next));
		return b;
	}	

	/**
	 * Sets the MouseEntered and MouseExited handlers for ImageView
	 * @param b
	 * @param key
	 * @author Ishika Govil
	 */
	public void setOnMouse(ImageView b, String key) {
		b.setOnMouseEntered(controller.getHandlerforMouseEntered(key, b));
		b.setOnMouseExited(controller.getHandlerforMouseExited(key, b));
	}
	/**
	 * Adds a line between (x1,y1) and (x2,y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param isPolygon
	 * @author Ishika Govil
	 */
	public void drawLine(double x1, double y1, double x2, double y2, boolean isPolygon) {
		Line line = new Line(x1, y1, x2, y2);
		line.setStrokeWidth(2);
		border.getChildren().add(line);
		if(isPolygon) 
			polygonLines.add(line);
		else
			freeLines.add(line);
	}
	

	/**
	 * Removes all the lines drawn using the drawLine() method
	 */
	public void removeLines() {
			border.getChildren().removeAll(polygonLines);
			polygonLines = new ArrayList<>();
			border.getChildren().removeAll(freeLines);
			freeLines = new ArrayList<>();
	}
	
	/**
	 * Draws the user's plot onto any size canvas
	 * @param canvas canvas to draw on
	 * @param outline outline of the freehand
	 * @param polygon plot polygon corners
	 * @param extrema extrema of the points of the outline
	 * @param conditions an arraylist of conditions to draw
	 * @return the scale that was used to draw the plot
	 */
	public static double drawOnCanvas(Canvas canvas, ArrayList<Vector2> outline, ArrayList<Vector2> polygon, ArrayList<Vector2> extrema, ArrayList<Conditions> conditions) {
		double minX = extrema.get(3).getX();
		double maxX = extrema.get(1).getX();
		double minY = extrema.get(0).getY();
		double maxY = extrema.get(2).getY();
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setLineWidth(lineWidth);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		double scale = findScale(minX, maxX, minY, maxY, canvas.getWidth(), canvas.getHeight());
		
//		System.out.println("minX: " + minX);
//		System.out.println("maxX: " + maxX);
//		System.out.println("minY: " + minY);
//		System.out.println("maxY: " + maxY);
//		System.out.println("scale: " + scale);
//		System.out.println("cw: " + canvas.getWidth());
//		System.out.println("ch: " + canvas.getHeight());
		
		drawOutlines(gc, outline, scale, minX, minY, false);
		drawOutlines(gc, polygon, scale, minX, minY, true);
		
		Iterator<Conditions> condIter = conditions.iterator();
		
		while(condIter.hasNext()) {
			Conditions cond = condIter.next();
			int startX = (int) ((cond.getX() - minX) * scale);
			int startY = (int) ((cond.getY() - minY) * scale);
			floodFill(canvas, cond, startX, startY, (int) canvas.getWidth(), (int) canvas.getHeight());
			gc.save();
		}
		return scale;
	}
	
	public static double findScale(double minX, double maxX, double minY, double maxY, double targetWidth, double targetHeight) {
		double sourceWidth = maxX - minX;
		double sourceHeight = maxY - minY;

		double xScale = targetWidth / sourceWidth;
		double yScale = targetHeight / sourceHeight;

		return Math.min(xScale, yScale);
	}
	
	private static void drawOutlines(GraphicsContext gc, ArrayList<Vector2> points, double scale, double minX, double minY, boolean closed) {
		boolean isNewLine = true;
		
		for(Vector2 point : points) {
			double x = (point.getX() - minX) * scale;
			double y = (point.getY() - minY) * scale;
			
			System.out.println("Point: x: " + x + " y: " + y);

			if(x < 0 && y < 0) {
				isNewLine = true;
			} else if (isNewLine) {
				gc.beginPath();
				gc.moveTo(x, y);
				isNewLine = false;
			} else {
				gc.lineTo(x, y);
				gc.stroke();
			}
			
		}
		if(closed && !points.isEmpty()) {
			Vector2 point = points.get(0);
			double x = (point.getX() - minX) * scale;
			double y = (point.getY() - minY) * scale;
			gc.lineTo(x, y);
			gc.stroke();
		}
		gc.closePath();
	}

	private static void floodFill(Canvas canvas, Conditions conds, int startX, int startY, int width, int height) {
		// Inspired by the flood fill example https://stackoverflow.com/questions/23983465/is-there-a-fill-function-for-arbitrary-shapes-in-javafx
		Stack<Vector2> fillStack = new Stack<>();

		WritableImage snapshot = canvas.snapshot(null, null);
		PixelReader pr = snapshot.getPixelReader();
		PixelWriter imgPW = snapshot.getPixelWriter();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		PixelWriter pw = gc.getPixelWriter();

		
		Color fillColor = conds.toColor();
		fillStack.push(new Vector2(startX, startY));
		
		while(!fillStack.empty()) {
			Vector2 curr = fillStack.pop();
			int x = (int) curr.getX();
			int y = (int) curr.getY();

			if(pr.getColor(x, y).grayscale().getBrightness() < fillThreshold) continue;
			
			pw.setColor(x, y, fillColor);
			imgPW.setColor(x, y, Color.BLACK);

			if(x > 0) {
				fillStack.push(new Vector2(x-1, y));
			}
			
			if(x < width - 1) {
				fillStack.push(new Vector2(x+1, y));
			}

			if(y > 0) {
				fillStack.push(new Vector2(x, y-1));
			}

			if(y < height - 1) {
				fillStack.push(new Vector2(x, y+1));
			}
				
		}
	}
	


}
