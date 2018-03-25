package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ColorChooser extends JPanel implements ChangeListener {

	private JColorChooser cc;

	public ColorChooser(DrawingPanel panel) {
		super(new BorderLayout());
		cc = new JColorChooser(Color.BLACK);
		cc.getSelectionModel().addChangeListener(this);
		cc.setBorder(BorderFactory.createTitledBorder("Choose Pen Color:"));
		add(cc, BorderLayout.PAGE_END);
		addKeyListener(panel);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		DrawingPanel.drawingColor = cc.getColor();
	}

}
