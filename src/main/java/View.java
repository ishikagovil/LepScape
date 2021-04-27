import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.*;

public abstract class View{
	public Map<String, Image> plantImages;
	
	private static final int lineWidth = 3;
	private static final double fillThreshold = 0.95;
	
	public int screenWidth = 1290;
	public int screenHeight = 800;
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
	ImageCursor flowerCursor;
	ImageCursor handCursor;
	
	public View(Stage stage, Controller c, ManageViews manageView) { 
		this.manageView = manageView;
		this.stage = stage;
		this.polygonLines = new ArrayList<>();
		this.freeLines = new ArrayList<>();
        this.controller = c;
		this.stage.setTitle("Lepscape");
		//Image from: https://custom-cursor.com/en/collection/life-style/hand-painted-poppy-flower
		this.flowerCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/flowerCursor.png"), 30,40,false,false));
		//Image from: https://custom-cursor.com/en/collection/animals/blue-and-purple-butterfly
		this.handCursor = new ImageCursor(new Image(getClass().getResourceAsStream("/lepCursor.png"), 40,40,false,false));

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
			stage.getScene().setCursor(this.handCursor);
		else
			stage.getScene().setCursor(this.flowerCursor);
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
	public static void drawOnCanvas(Canvas canvas, ArrayList<double[]> points, ArrayList<double[]> extrema, ArrayList<Conditions> conditions) {
		double minX = extrema.get(3)[0];
		double maxX = extrema.get(1)[0];
		double minY = extrema.get(0)[1];
		double maxY = extrema.get(2)[1];
		
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
		
		Iterator<double[]> pointIter = points.iterator();

		boolean isNewLine = true;
		
		while(pointIter.hasNext()) {

			double[] point = pointIter.next();
			double x = (point[0] - minX) * scale;
			double y = (point[1] - minY) * scale;
			
//			System.out.println("x: " + x + " y: " + y);

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
		gc.closePath();
		
		
		Iterator<Conditions> condIter = conditions.iterator();
		
		System.out.println("Drawing conditions");
		while(condIter.hasNext()) {
			Conditions cond = condIter.next();
			int startX = (int) ((cond.getX() - minX) * scale);
			int startY = (int) ((cond.getY() - minY) * scale);
			floodFill(canvas, cond, startX, startY, (int) canvas.getWidth(), (int) canvas.getHeight());
			gc.save();
			System.out.println("drawing cond at " + startX + " " + startY);
		}
		
	}
	
	public static double findScale(double minX, double maxX, double minY, double maxY, double targetWidth, double targetHeight) {
		double sourceWidth = maxX - minX;
		double sourceHeight = maxY - minY;

		double xScale = targetWidth / sourceWidth;
		double yScale = targetHeight / sourceHeight;

		return Math.min(xScale, yScale);
	}

	private static void floodFill(Canvas canvas, Conditions conds, int startX, int startY, int width, int height) {
		// Inspired by the flood fill example https://stackoverflow.com/questions/23983465/is-there-a-fill-function-for-arbitrary-shapes-in-javafx
		Stack<Point2D> fillStack = new Stack<>();

		WritableImage snapshot = canvas.snapshot(null, null);
		PixelReader pr = snapshot.getPixelReader();
		PixelWriter imgPW = snapshot.getPixelWriter();

		GraphicsContext gc = canvas.getGraphicsContext2D();
		PixelWriter pw = gc.getPixelWriter();

		
		Color fillColor = conds.toColor();
		
		fillStack.push(new Point2D(startX, startY));
		
		while(!fillStack.empty()) {
			Point2D curr = fillStack.pop();

			int x = (int) curr.getX();
			int y = (int) curr.getY();

			if(pr.getColor(x, y).grayscale().getBrightness() < fillThreshold) continue;
			
			pw.setColor(x, y, fillColor);
			imgPW.setColor(x, y, Color.BLACK);

			if(x > 0) {
				fillStack.push(new Point2D(x-1, y));
			}
			
			if(x < width - 1) {
				fillStack.push(new Point2D(x+1, y));
			}

			if(y > 0) {
				fillStack.push(new Point2D(x, y-1));
			}

			if(y < height - 1) {
				fillStack.push(new Point2D(x, y+1));
			}
				
		}
	}
	
	//Used only in gardenDesign. In here because need to called by controller
	//public void addImageView(double x, double y, String key, double heightWidth) {}
	public void removePlant(Node n) {}
	public void makeInfoPane(String name, String info) {}
	public void updateBudgetandLep(int cost, int lepCount) {}


}
