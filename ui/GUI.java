package ui;

import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public static final int FRAME_WIDTH = 1100;
	public static final int FRAME_HEIGHT = 650;
	private static final String TITLE = "Cualquier cosa - 2D";

	public GUI() {
		super();
		setTitle(TITLE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		DrawingPanel panel = new DrawingPanel(this);
		add(new Toolbar(this, panel), getContentPane());
		add(panel, getContentPane());
		addKeyListener(panel);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setFocusable(true);
		requestFocusInWindow();
		setVisible(true);
	}

	public static ImageIcon createImageIcon(String path, int width, int height) {
		return new ImageIcon(new ImageIcon(GUI.class.getResource(path)).getImage().getScaledInstance(width, height,
				Image.SCALE_SMOOTH));
	}
}
