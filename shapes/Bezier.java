package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

import math.Matrix3;
import math.Point3;

public class Bezier implements Shape {

	private static final int STEPS = 40;

	private Color color;
	private Matrix3 matrix;
	private Point3[] originalPoints, points;

	public Bezier(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Color color) {
		this.color = color;
		matrix = new Matrix3();
		points = new Point3[4];
		originalPoints = new Point3[] { new Point3(x1, y1), new Point3(x2, y2), new Point3(x3, y3),
				new Point3(x4, y4) };
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(color);
		for (int i = 0; i < originalPoints.length; ++i)
			points[i] = Matrix3.times(matrix, originalPoints[i]);
		Point3[] allPoints = curve(points);
		for (int i = 0; i < allPoints.length - 1; ++i)
			g.drawLine(allPoints[i].getX(), allPoints[i].getY(), allPoints[i + 1].getX(), allPoints[i + 1].getY());
	}

	@Override
	public void transform(Matrix3 transformationMatrix) {
		matrix = Matrix3.times(transformationMatrix, matrix);
	}

	private static Point3[] curve(Point3[] controlPoints) {
		Point3[] points = new Point3[STEPS + 1];
		double step = 1.0 / STEPS;
		int n = controlPoints.length;
		double u = 0.0;
		for (int i = 0; i <= STEPS; i++) {
			points[i] = bezier(u, n - 1, controlPoints);
			u += step;
		}
		return points;
	}

	private static Point3 bezier(double u, int n, Point3[] controlPoints) {
		double acumX = 0.0;
		double acumY = 0.0;
		for (int i = 0; i < controlPoints.length; i++) {
			double blend = blending(u, n, i);
			acumX += controlPoints[i].getX() * blend;
			acumY += controlPoints[i].getY() * blend;
		}
		return new Point3((int) acumX, (int) acumY);
	}

	private static double blending(double u, int n, int k) {
		return coeff(n, k) * Math.pow(u, k) * Math.pow(1 - u, n - k);
	}

	private static int coeff(int n, int k) {
		if (k > n - k)
			k = n - k;
		int res = 1;
		for (int i = 1, m = n; i <= k; ++i, --m)
			res = res * m / i;
		return res;
	}

}
