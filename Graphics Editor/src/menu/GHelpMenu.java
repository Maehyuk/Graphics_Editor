package menu;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frame.GFrame;
import frame.GPanel;
import main.GConstants.EHelpItem;

public class GHelpMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private GPanel panel;
	private GFrame frame;

	public GHelpMenu(String name) {
		super(name);
		ActionHandler actionHandler = new ActionHandler();

		for (EHelpItem eHelpItem : EHelpItem.values()) {
			JMenuItem menuItem = new JMenuItem(eHelpItem.getText());
			menuItem.setActionCommand(eHelpItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void info() {
		panel.setInfo();
	}

	private void Text() {
		panel.Text();
	}
	
	private void capture() {
		int count = 0;
		try {
			BufferedImage screencapture = new Robot().createScreenCapture(new Rectangle(200,100,900,800));
			File file = new File("img//ScreenCapture"+ " " + (count++) + " " + ".jpg");
			
			try {
				JOptionPane.showMessageDialog(null, "Ä¸Ã³Çß½À´Ï´Ù", "È­¸é Ä¸ÃÄ",JOptionPane.INFORMATION_MESSAGE);
				ImageIO.write(screencapture, "jpg", file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} catch (HeadlessException e1) {
			e1.printStackTrace();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}


	private class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			EHelpItem eHelpItem = EHelpItem.valueOf(e.getActionCommand());

			switch (eHelpItem) {
			
			case eCapture :
				capture();
				break;
			case eSetText:
				Text();
				break;

			case eInfo:
				info();
				break;
			}
		}




	}

}
