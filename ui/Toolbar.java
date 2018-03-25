package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Toolbar extends JPanel {

	JFrame colorChooser = null;
	JFrame fileChooser = null;

	public Toolbar(GUI parent, DrawingPanel panel) {
		super();
		setLayout(new FlowLayout());

		Dimension d = parent.getSize();
		int buttonWidth = (int) (d.width * 0.05);
		int buttonHeight = (int) (d.height * 0.05);

		JButton colorButton = new JButton("", GUI.createImageIcon("../images/palette.png", buttonWidth, buttonHeight));
		colorButton.setPreferredSize(new Dimension(buttonWidth + 5, buttonHeight + 5));
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (colorChooser == null) {
					colorChooser = new JFrame("Color chooser");
					colorChooser.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					JComponent newContentPane = new ColorChooser(panel);
					newContentPane.setOpaque(true);
					colorChooser.setContentPane(newContentPane);
					colorChooser.pack();
					colorChooser.setLocationRelativeTo(null);
					colorChooser.setResizable(false);
					colorChooser.setVisible(true);
				} else {
					colorChooser.setVisible(true);
				}
			}
		});
		colorButton.addKeyListener(panel);

		JFileChooser fc = new JFileChooser();
		JButton fileButton = new JButton("", GUI.createImageIcon("../images/file.png", buttonWidth, buttonHeight));
		fileButton.setPreferredSize(new Dimension(buttonWidth + 5, buttonHeight + 5));
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(Toolbar.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
					panel.readFile(fc.getSelectedFile());
			}
		});
		fileButton.addKeyListener(panel);

		JButton lineButton = new JButton("", GUI.createImageIcon("../images/line.png", buttonWidth, buttonHeight));
		lineButton.setPreferredSize(new Dimension(buttonWidth + 5, buttonHeight + 5));
		lineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.changeToLine();
			}
		});
		lineButton.addKeyListener(panel);

		JButton triangleButton = new JButton("",
				GUI.createImageIcon("../images/triangle.png", buttonWidth, buttonHeight));
		triangleButton.setPreferredSize(new Dimension(buttonWidth + 5, buttonHeight + 5));
		triangleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.changeToTriangle();
			}
		});
		triangleButton.addKeyListener(panel);

		JButton bezierButton = new JButton("", GUI.createImageIcon("../images/bezier.png", buttonWidth, buttonHeight));
		bezierButton.setPreferredSize(new Dimension(buttonWidth + 5, buttonHeight + 5));
		bezierButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.changeToBezier();
			}
		});
		bezierButton.addKeyListener(panel);

		addKeyListener(panel);

		add(colorButton);
		add(lineButton);
		add(triangleButton);
		add(bezierButton);
		add(fileButton);
	}

}
