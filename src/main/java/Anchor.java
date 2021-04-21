import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

public class Anchor extends Circle {
	Controller controller;
	boolean dragAnchor;
	Polygon poly;
	DoubleProperty x;
	DoubleProperty y;
	int idx;
        Anchor(Color color, DoubleProperty x, DoubleProperty y, Polygon poly, int idx, boolean dragAnchor, Controller controller) {
            super(x.get(), y.get(), 8);
            //Initialize values
            this.controller = controller;
            this.x = x;
            this.y = y;
            this.poly = poly;
            this.dragAnchor = dragAnchor;
            this.idx = idx;         
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            x.bind(centerXProperty());
            y.bind(centerYProperty());
            setOnMouseDragged(controller.getHandlerforAnchor(this, dragAnchor, x, y, poly, idx));         
        }
        public void setDragAnchor(boolean dragAnchor) {
        	this.dragAnchor = dragAnchor;
        	setOnMouseDragged(controller.getHandlerforAnchor(this, dragAnchor, x, y, poly, idx)); 
        }
    }