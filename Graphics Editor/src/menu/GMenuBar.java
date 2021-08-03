package menu;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import frame.GPanel;
import main.GConstants.EMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GColorMenu colorMenu;
	private GHelpMenu helpMenu;

	public GMenuBar() {
		this.fileMenu = new GFileMenu(EMenu.eFile.getText());
		this.add(this.fileMenu);

		this.editMenu = new GEditMenu(EMenu.eEdit.getText());
		this.add(this.editMenu);

		this.colorMenu = new GColorMenu(EMenu.eColor.getText());
		this.add(this.colorMenu);

		this.helpMenu = new GHelpMenu(EMenu.eHelp.getText());
		this.add(this.helpMenu);

	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAssociation(panel);
		this.editMenu.setAssociation(panel);
		this.colorMenu.setAssociation(panel);
		this.helpMenu.setAssociation(panel);
	}

	public void windowClose() {
		this.fileMenu.exitProgram();
	}

}
