package menu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.print.PrintException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import frame.GFrame;
import frame.GPanel;
import main.GConstants.EFileMenuItem;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components
	private File path;
	private File file;
	// associations
	private GPanel panel;
	private static GFrame frame;

	public GFileMenu(String text) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eFileMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			menuItem.setActionCommand(eFileMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);
		}
		
		this.path = null;
		this.file = null;
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void openFile() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(file)));

			Vector<GShapeTool> shapes = (Vector<GShapeTool>) objectInputStream.readObject();
			this.panel.setShapes(shapes);
			objectInputStream.close();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveFile() {
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(this.file)));
			objectOutputStream.writeObject(this.panel.getShapes());
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void writeFile() {

		try {

			if (file != null) {
				ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(file)));

				objectOutputStream1.writeObject(panel.getShapes());
				objectOutputStream1.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean checkSaveOrNot() {
		boolean bCancel = true;
		if (this.panel.isModified()) {
			int reply = JOptionPane.showConfirmDialog(this.panel, "변경내용을 저장 할까요?");
			if (reply == JOptionPane.OK_OPTION) {
				this.save();
				bCancel = false;
			} else if (reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			} else if (reply == JOptionPane.CANCEL_OPTION) {

			} else {
				bCancel = false;
			}
		}
		return bCancel;
	}

	public void nnew() {
		if (!checkSaveOrNot()) {
			this.panel.clearScreen();
			this.file = null;
		}
	}

	private void open() {
	      if (this.panel.isModified()) {
	         if (!checkSaveOrNot()) {
	            JFileChooser chooser = new JFileChooser();
	            int returnVal = chooser.showOpenDialog(this.panel);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	               this.file = chooser.getSelectedFile();
	               this.openFile();
	            }
	         }
	         this.panel.setModified(false);
	      } else {
	         JFileChooser chooser = new JFileChooser();
	         int returnVal = chooser.showOpenDialog(this.panel);
	         if (returnVal == JFileChooser.APPROVE_OPTION) {
	            this.file = chooser.getSelectedFile();
	            this.openFile();
	         }
	      }
	   }

	public void save() {
		if (this.panel.isModified()) {
			if (this.file == null) {
				this.saveAs();
			} else {
				this.saveFile();
			}
		}
	}

	public void saveAs() {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showSaveDialog(this.panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.saveFile();
		}
	}
	
	public void print() {
		PrinterJob printerJob =  PrinterJob.getPrinterJob();
		printerJob.setJobName(CONTENT_AREA_FILLED_CHANGED_PROPERTY);		
		printerJob.setPrintable(new Printable() {
			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				
				Graphics graphics2D = (Graphics2D) graphics;
				graphics2D.translate((int)pageFormat.getImageableX(), (int)pageFormat.getImageableY() );
				((Graphics2D) graphics2D).scale(0.5,0.5);
				
				panel.paint(graphics2D);
				return Printable.PAGE_EXISTS;
			}
		});
		
		boolean returnP = printerJob.printDialog();
		if(returnP) {
			try {
				printerJob.print();
			}  catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}


	public void exitProgram() {
		if (!checkSaveOrNot()) {
			System.exit(0);
		}
	}
	
	public void workspace() {
		frame = new GFrame();
		frame.initialize();
		frame.setVisible(true);
	}

	public class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eMenuItem = EFileMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eWorkspace :
				workspace();
				break;
			case eNew:
				nnew();
				break;
			case eOpen:
				open();
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				print();
				break;
			case eExit:
				exitProgram();
				break;
			default:
				break;
			}
		}



	
	}

}
