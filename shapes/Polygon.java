package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import math.Matrix3;
import math.Point3;

public class Polygon implements Shape {

	private Color color;
	private Matrix3 matrix;
	private Point3[] originalPoints, points, order;

	public Polygon(Point3[] points, Point3[] order, Color color) {
		matrix = new Matrix3();
		this.color = color;
		this.points = new Point3[points.length];
		originalPoints = new Point3[points.length];
		for (int i = 0; i < points.length; ++i)
			originalPoints[i] = points[i];
		this.order = new Point3[order.length];
		for (int i = 0; i < order.length; ++i)
			this.order[i] = order[i];
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for (int i = 0; i < originalPoints.length; ++i)
			points[i] = Matrix3.times(matrix, originalPoints[i]);
		for (Point3 o : order)
			g.drawLine(points[o.getX()].getX(), points[o.getX()].getY(), points[o.getY()].getX(),
					points[o.getY()].getY());
	}

	@Override
	public void transform(Matrix3 transformationMatrix) {
		matrix = Matrix3.times(transformationMatrix, matrix);
	}
}
