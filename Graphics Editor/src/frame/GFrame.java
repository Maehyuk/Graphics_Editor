package frame;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import main.GConstants.CFrame;
import main.GConstants.EMenu;
import menu.GFileMenu;
import menu.GMenuBar;

public class GFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	private GFileMenu fileMenu;
	private WindowHandler windowHandler;
	public GFrame() {

		windowHandler = new WindowHandler();
		this.addWindowListener(windowHandler);

		fileMenu = new GFileMenu(EMenu.eFile.getText());

		// initialize attributes
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimesion);

		// initialize components
		this.menuBar = new GMenuBar();
		this.setJMenuBar(this.menuBar);

		BorderLayout layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);

		this.toolBar = new GToolBar();
		this.getContentPane().add(this.toolBar, BorderLayout.NORTH);

		this.panel = new GPanel();
		this.getContentPane().add(this.panel, BorderLayout.CENTER);
		


		// set associations
		this.menuBar.setAssociation(this.panel);
		this.toolBar.setAssociation(this.panel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void initialize() {
		this.toolBar.initialize();
		this.panel.initialize();
	}

	public void windowClose() {
		this.menuBar.windowClose();
	}
	

	private class WindowHandler implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			windowClose();
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

	}
}
