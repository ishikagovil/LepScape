import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;
/**
 * Anchor class used to create draggable circles on the edges of the Polygon in PlotDesign
 * @author Ishika Govil
 */
public class Anchor extends Circle {
	PlotDesign plotView;
	Controller controller;
	boolean dragAnchor;
	Polygon poly;
	DoubleProperty x;
	DoubleProperty y;
	int idx;
        Anchor(Color color, DoubleProperty x, DoubleProperty y, Polygon poly, int idx, boolean dragAnchor, PlotDesign plotView, Controller controller) {
            super(x.get(), y.get(), 10);
            //Initialize values
            this.plotView = plotView;
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
        /**
         * When dragged by user, it calls the controller's getHandlerforAnchor() with the relevant parameters. 
         * Also sets the class's field dragAnchor to the boolean parameter
         * @param boolean dragAnchor representing whether the anchor should be draggable or not
         */
        public void setDragAnchor(boolean dragAnchor) {
        	this.dragAnchor = dragAnchor;      	
        	setOnMouseDragged(controller.getHandlerforAnchor(this, dragAnchor, x, y, poly, idx));              	
        }
    }