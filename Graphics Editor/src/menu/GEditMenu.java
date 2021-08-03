package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EEditMenuItem;

public class GEditMenu extends JMenu {
	// attributes
	private static final long serialVersionUID = 1L;

	private GPanel panel;

	@SuppressWarnings("unlikely-arg-type")
	public GEditMenu(String text) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();

		for (EEditMenuItem eMenuItem : EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getText());
			menuItem.setActionCommand(eMenuItem.name());
			menuItem.addActionListener(actionHandler);
			this.add(menuItem);

			if (eMenuItem.getText().equals("재실행")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('Y', InputEvent.CTRL_MASK));
			} else if (eMenuItem.getText().equals("실행취소")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('Z', InputEvent.CTRL_MASK));
			} else if (eMenuItem.getText().equals("잘라내기")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('X', InputEvent.CTRL_MASK));
			} else if (eMenuItem.getText().equals("복사하기")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('C', InputEvent.CTRL_MASK));
			} else if (eMenuItem.getText().equals("붙여넣기")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('V', InputEvent.CTRL_MASK));
			} else if (eMenuItem.getText().equals("삭제하기")) {
				menuItem.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK));
			}
		}
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem eMenuItem = EEditMenuItem.valueOf(e.getActionCommand());

			switch (eMenuItem) {
			case eRedo:
				panel.redo();
				break;
			case eUndo:
				panel.undo();
				break;
			case eCut:
				panel.cut();
				break;
			case eCopy:
				panel.copy();
				break;
			case ePaste:
				panel.paste();
				break;
			case eDelete:
				panel.delete();
				break;
			case eFront :
				panel.front();
				break;
			case eBack :
				panel.back();
				break;
			default:
				break;
			}
		}


	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}
}
