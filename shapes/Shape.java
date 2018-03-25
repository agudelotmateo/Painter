package shapes;

import java.awt.Graphics2D;

import math.Matrix3;

public interface Shape {

	public void draw(Graphics2D g);

	public void transform(Matrix3 transformationMatrix);

}
