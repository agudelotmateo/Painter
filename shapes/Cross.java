package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import math.Matrix3;
import math.Point3;

public class Cross implements Shape {

	private Color color;
	private Matrix3 matrix;
	private Point3[] originalPoints, points;

	public Cross(int x, int y, Color color) {
		this.color = color;
		matrix = new Matrix3();
		points = new Point3[4];
		originalPoints = new Point3[] { new Point3(x - 2, y), new Point3(x + 2, y), new Point3(x, y - 2),
				new Point3(x, y + 2) };
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for (int i = 0; i < originalPoints.length; ++i)
			points[i] = Matrix3.times(matrix, originalPoints[i]);
		g.drawLine(points[0].getX(), points[0].getY(), points[1].getX(), points[1].getY());
		g.drawLine(points[2].getX(), points[2].getY(), points[3].getX(), points[3].getY());
	}

	@Override
	public void transform(Matrix3 transformationMatrix) {
		matrix = Matrix3.times(transformationMatrix, matrix);
	}

}
