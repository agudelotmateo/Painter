package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JPanel;

import math.Matrix3;
import math.Point3;
import shapes.Line;
import shapes.Polygon;
import shapes.Bezier;
import shapes.Cross;
import shapes.Shape;
import shapes.Triangle;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel implements KeyListener {

	private static final double ROTATION_DELTA = Math.PI / 36.0;
	private static final double SCALING_DELTA = 0.05;
	private static final double TRANSLATION_DELTA = 10.0;

	private static final Matrix3 TRANSLATE_X_POSITIVE = Matrix3.translationMatrix(TRANSLATION_DELTA, 0.0);
	private static final Matrix3 TRANSLATE_X_NEGATIVE = Matrix3.translationMatrix(-TRANSLATION_DELTA, 0.0);
	private static final Matrix3 TRANSLATE_Y_NEGATIVE = Matrix3.translationMatrix(0.0, TRANSLATION_DELTA);
	private static final Matrix3 TRANSLATE_Y_POSITIVE = Matrix3.translationMatrix(0.0, -TRANSLATION_DELTA);
	private static final Matrix3 SCALE_UP = Matrix3.scalingMatrix(1.0 + SCALING_DELTA, 1.0 + SCALING_DELTA);
	private static final Matrix3 SCALE_DOWN = Matrix3.scalingMatrix(1.0 - SCALING_DELTA, 1.0 - SCALING_DELTA);
	private static final Matrix3 ROTATE_CCW = Matrix3.rotationMatrix(ROTATION_DELTA);
	private static final Matrix3 ROTATE_CW = Matrix3.rotationMatrix(-ROTATION_DELTA);

	private static enum State {
		LINE_1, LINE_2, TRIANGLE_1, TRIANGLE_2, BEZIER_1, BEZIER_2, BEZIER_3, BEZIER_4
	}

	private static State state = State.BEZIER_1;
	private int x1, y1, x2, y2, x3, y3, x4, y4;
	private ArrayList<Shape> shapes = new ArrayList<>();

	static Color drawingColor = Color.BLACK;

	public DrawingPanel(GUI parent) {
		super();
		Dimension d = parent.getSize();
		setPreferredSize(new Dimension((int) (d.width * 0.8), (int) (d.height * 0.8)));
		setBackground(Color.WHITE);

		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (state == State.BEZIER_1) {
					state = State.BEZIER_2;
					x1 = e.getX();
					y1 = e.getY();
					shapes.add(new Cross(e.getX(), e.getY(), drawingColor));
					parent.repaint();
				} else if (state == State.BEZIER_2) {
					state = State.BEZIER_3;
					x2 = e.getX();
					y2 = e.getY();
					shapes.add(new Cross(e.getX(), e.getY(), drawingColor));
					parent.repaint();
				} else if (state == State.BEZIER_3) {
					state = State.BEZIER_4;
					x3 = e.getX();
					y3 = e.getY();
					shapes.add(new Cross(e.getX(), e.getY(), drawingColor));
					parent.repaint();
				} else if (state == State.BEZIER_4) {
					state = State.BEZIER_1;
					x4 = e.getX();
					y4 = e.getY();
					removeDots();
					shapes.add(new Bezier(x1, y1, x2, y2, x3, y3, x4, y4, drawingColor));
					parent.repaint();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (state == State.LINE_1 || state == State.TRIANGLE_1) {
					x1 = e.getX();
					y1 = e.getY();
					if (state == State.LINE_1)
						state = State.LINE_2;
					else if (state == State.TRIANGLE_1)
						state = State.TRIANGLE_2;
					shapes.add(new Cross(e.getX(), e.getY(), drawingColor));
					parent.repaint();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (state == State.LINE_2 || state == State.TRIANGLE_2) {
					shapes.remove(shapes.size() - 1);
					x2 = e.getX();
					y2 = e.getY();
					if (state == State.LINE_2) {
						state = State.LINE_1;
						shapes.add(new Line(x1, y1, x2, y2, drawingColor));
					} else {
						state = State.TRIANGLE_1;
						shapes.add(new Triangle(x1, y1, x2, y2, drawingColor));
					}
					parent.repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// do nothing
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// do nothing
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		for (Shape s : shapes)
			s.draw(g2D);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_A)
			for (Shape s : shapes)
				s.transform(TRANSLATE_X_NEGATIVE);
		else if (key == KeyEvent.VK_D)
			for (Shape s : shapes)
				s.transform(TRANSLATE_X_POSITIVE);
		else if (key == KeyEvent.VK_S)
			for (Shape s : shapes)
				s.transform(TRANSLATE_Y_NEGATIVE);
		else if (key == KeyEvent.VK_W)
			for (Shape s : shapes)
				s.transform(TRANSLATE_Y_POSITIVE);
		else if (key == KeyEvent.VK_UP)
			for (Shape s : shapes)
				s.transform(SCALE_UP);
		else if (key == KeyEvent.VK_DOWN)
			for (Shape s : shapes)
				s.transform(SCALE_DOWN);
		else if (key == KeyEvent.VK_LEFT)
			for (Shape s : shapes)
				s.transform(ROTATE_CCW);
		else if (key == KeyEvent.VK_RIGHT)
			for (Shape s : shapes)
				s.transform(ROTATE_CW);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	public void readFile(File file) {
		try {
			Scanner in = new Scanner(file);
			int n = in.nextInt();
			Point3[] points = new Point3[n];
			for (int i = 0; i < n; i++)
				points[i] = toJavaPoint(in.nextInt(), in.nextInt());
			int m = in.nextInt();
			Point3[] order = new Point3[m];
			for (int i = 0; i < m; i++)
				order[i] = new Point3(in.nextInt(), in.nextInt());
			shapes.add(new Polygon(points, order, drawingColor));
			repaint();
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
	}

	private Point3 toJavaPoint(int x, int y) {
		return new Point3(x + GUI.FRAME_WIDTH / 2, GUI.FRAME_HEIGHT / 2 - y);
	}

	public void removeDots() {
		ArrayList<Shape> clear = new ArrayList<>(shapes.size());
		for (Shape s : shapes)
			if (!(s instanceof Cross))
				clear.add(s);
		shapes = clear;
	}

	public void changeToLine() {
		removeDots();
		state = State.LINE_1;
	}

	public void changeToTriangle() {
		removeDots();
		state = State.TRIANGLE_1;
	}

	public void changeToBezier() {
		removeDots();
		state = State.BEZIER_1;
	}

}
