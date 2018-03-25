package math;

public class Point3 {

	private int x, y, w;

	public Point3(int x, int y) {
		this.x = x;
		this.y = y;
		this.w = 1;
	}

	public Point3(int x, int y, int w) {
		this.x = x;
		this.y = y;
		this.w = w;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

}
