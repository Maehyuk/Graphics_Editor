package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import main.GConstants.EShapeTool;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;
	private Vector<JRadioButton> buttons;
	private ButtonGroup buttonGroup;

	// associations
	private GPanel panel;

	public GToolBar() {
		// initialize components
		ActionHandler actionHandler = new ActionHandler();
		buttons = new Vector<JRadioButton>();
		buttonGroup = new ButtonGroup();

		for (EShapeTool eButton : EShapeTool.values()) {
			JRadioButton button = new JRadioButton();

			button.setIcon(new ImageIcon("img/" + eButton + ".png"));
			button.setSelectedIcon(new ImageIcon("img/" + eButton + "A." + "png"));

			button.setActionCommand(eButton.toString());
			button.addActionListener(actionHandler);
			buttons.add(button);
			buttonGroup.add(button);
			this.add(button);
		}
	}

	public void initialize() {
		((JRadioButton) (this.getComponent(EShapeTool.eRectangle.ordinal()))).doClick();
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EShapeTool eShapeTool = EShapeTool.valueOf(event.getActionCommand());
			panel.setSelection(eShapeTool.getShapeTool());
		}
	}

}
