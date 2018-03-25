package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import math.Matrix3;
import math.Point3;

public class Triangle implements Shape {

	private Color color;
	private Matrix3 matrix;
	private Point3[] originalPoints, points;
	private int[] xPoints, yPoints;

	public Triangle(int x1, int y1, int x2, int y2, Color color) {
		this.color = color;
		matrix = new Matrix3();
		points = new Point3[3];
		xPoints = new int[3];
		yPoints = new int[3];
		originalPoints = new Point3[] { new Point3(x1, y1), new Point3(x2, y2), new Point3(x1, y2) };
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for (int i = 0; i < originalPoints.length; ++i) {
			points[i] = Matrix3.times(matrix, originalPoints[i]);
			xPoints[i] = points[i].getX();
			yPoints[i] = points[i].getY();
		}
		g.fillPolygon(xPoints, yPoints, 3);
	}

	@Override
	public void transform(Matrix3 transformationMatrix) {
		matrix = Matrix3.times(transformationMatrix, matrix);
	}

}
