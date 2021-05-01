
/**
 * Represents a vector of x and y points
 * @author Jinay Jain
 *
 */
public class Vector2 implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public final double epsilon = 1e-7;

	private double x, y;

	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj.getClass() != this.getClass()) return false;
		
		Vector2 other = (Vector2) obj;
		boolean xEquals = Math.abs(other.getX() - this.getX()) < epsilon;
		boolean yEquals = Math.abs(other.getY() - this.getY()) < epsilon;
		return xEquals && yEquals;
	}
}
