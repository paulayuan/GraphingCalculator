public class Point {

	Double x;
	Double y;
	
	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}
	
	public String toString() {
		return "\n(" + x + ", " + y + ")";
	}
}
