package menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frame.GPanel;
import main.GConstants.EColorMenuItem;

public class GColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private GPanel panel;
	private boolean setdotLine;

	public GColorMenu(String name) {
		super(name);
		ActionHandler actionHandler = new ActionHandler();

		for (EColorMenuItem eColorMenuItem : EColorMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eColorMenuItem.getText());
			menuItem.setActionCommand(eColorMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void lineColor() {
		Color color = JColorChooser.showDialog(null, "선 색", null);
		if (color != null) {
			panel.setLineColor(color);
		}
	}

	private void fillColor() {
		Color color = JColorChooser.showDialog(null, "도형 색", null);
		if (color != null) {
			panel.setFillColor(color);
		}
	}

	private void fillBack() {
		Color color = JColorChooser.showDialog(null, "배경 색", null);
		if (color != null) {
			panel.setBackColor(color);
		}
	}

	private void changeLine() {
		panel.setLineWidth();
	}

	private void dotLine() {

		if (!setdotLine) {
			setdotLine = true;
		} else {
			setdotLine = false;
		}
		panel.setdotLine(setdotLine);
	}

	private void insertImage() {
		Graphics2D graphics2d = (Graphics2D) panel.getGraphics();

		JFileChooser chooser = new JFileChooser();
		File imageFile = null;
		int returnVal = chooser.showOpenDialog(panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				InputStream objectInputStream = new BufferedInputStream(new FileInputStream(file));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Image image = null;
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			graphics2d.drawImage(image, 0, 0, this.panel);

		}

	}

	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			EColorMenuItem eMenuItem = EColorMenuItem.valueOf(e.getActionCommand());

			switch (eMenuItem) {
			case eLineColor:
				lineColor();
				break;
			case eFillColor:
				fillColor();
				break;
			case eBackColor:
				fillBack();
				break;
			case eLineWidth:
				changeLine();
				break;
			case eLineChange:
				dotLine();
				break;
			case eImage:
				insertImage();
				break;

			}
		}

	}
}
