package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import shapeTools.GShapeTool;
import shapeTools.GShapeTool.EAnchors;
import transformer.GMover;
import transformer.GResizer;
import transformer.GRotater;
import transformer.GTransformer;

public class GPanel extends JPanel {
	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private Vector<GShapeTool> shapes;
	private Vector<Vector<GShapeTool>> undoList;
	private Vector<Vector<GShapeTool>> redoList;
	private GMouseHandler mouseHandler;
	private GActionHandler acitonHandler;
	// associations

	private Color lineColor, fillColor, backColor;
	private int lineWidth;
	private boolean setdotLine;

	private PopupMenu p;

	MenuItem item1 = new MenuItem("앞으로 보내기");
	MenuItem item2 = new MenuItem("뒤로 보내기");
	MenuItem item3 = new MenuItem("확대");
	MenuItem item4 = new MenuItem("축소");
	MenuItem item5 = new MenuItem("전단");

	// working objects
	private GShapeTool shapeTool;
	private GShapeTool selectedShape;
	private GShapeTool copyShape;

	private GTransformer transformer;

	private boolean bModified;

	///////////////////////////////////////////////////////
	// getters and setters
	public Vector<GShapeTool> getShapes() {
		return this.shapes;
	}

	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}

	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}

	public boolean isModified() {
		return this.bModified;
	}

	public boolean setModified(boolean bModified) {
		return this.bModified = bModified;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
		this.setBackground(this.backColor);
	}

	public void setLineWidth() {
		JOptionPane a = new JOptionPane();
		@SuppressWarnings("static-access")
		String value = a.showInputDialog(this, "원하는 선 두께를 입력하세요");

		if (value != null) {
			int num = Integer.parseInt(value);
			this.lineWidth = num;
		}
	}

	public void setdotLine(boolean setdotLine) {
		this.setdotLine = setdotLine;
	}

	public void setInfo() {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
				URI uri = new URI("https://www.mju.ac.kr/sites/mjukr/intro/intro.html");
				desktop.browse(uri);
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (URISyntaxException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	
	public void makePopup() {
		this.p = new PopupMenu();
		p.add(item1);
		p.add(item2);
		p.addSeparator();
		p.add(item3);
		p.add(item4);
		p.addSeparator();
		p.add(item5);
		add(p);
	}

	// constructors
	public GPanel() {
		super();
		this.shapes = new Vector<GShapeTool>();
		
		this.undoList = new Vector<Vector<GShapeTool>>();
		this.redoList = new Vector<Vector<GShapeTool>>();

		lineColor = Color.BLACK;
		fillColor = Color.WHITE;

		this.makePopup();

		this.mouseHandler = new GMouseHandler();
		this.acitonHandler = new GActionHandler();

		item1.addActionListener(this.acitonHandler);
		item2.addActionListener(this.acitonHandler);
		item3.addActionListener(this.acitonHandler);
		item4.addActionListener(this.acitonHandler);
		item5.addActionListener(this.acitonHandler);

		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);
		this.transformer = null;
		this.bModified = false;
		this.setdotLine = false;

	}

	public void initialize() {
		this.setBackground(Color.WHITE);
	}

	public void clearScreen() {
		this.shapes.clear();
		this.repaint();
	}

	// methods
	public void paint(Graphics graphics) {
		super.paint(graphics);

		for (GShapeTool shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}

	private void setSelected(GShapeTool selectedShape) {
		for (GShapeTool shape : this.shapes) {
			shape.setSelected(false);
		}
		if (selectedShape == null) {
			this.repaint();

		} else {
			this.selectedShape = selectedShape;
			this.selectedShape.setSelected(true);
			this.repaint();
		}

	}

	private GShapeTool onShape(int x, int y) {
		for (GShapeTool shape : this.shapes) {
			EAction eAction = shape.containes(x, y);
			if (eAction != null) {
				return shape;
			}
		}
		return null;
	}

	private void initDrawing(int x, int y) {
		this.selectedShape = this.shapeTool.newInstance();
		this.selectedShape.setInitPoint(x, y);

		this.selectedShape.setlineColor(lineColor);
		this.selectedShape.setfillColor(fillColor);
		this.selectedShape.setlineWidth(lineWidth);
		this.selectedShape.setdotLine(setdotLine);

	}

	private void setIntermediatePoint(int x, int y) {
		this.selectedShape.setIntermediatePoint(x, y);
	}

	private void keepDrawing(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setXORMode(getBackground());
		selectedShape.animate(graphics2D, x, y);
	}

	private void finishDrawing(int x, int y) {

		Vector<GShapeTool> undoVector = new Vector<GShapeTool>();
		for (GShapeTool shapeTool : shapes) {
			undoVector.add(shapeTool.clone());
		}

		this.undoList.add(undoVector);
		this.shapes.remove(this.selectedShape);

		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape);

		this.bModified = true;
		repaint();

	}

	private void initTransforming(GShapeTool selectedShape, int x, int y) {

		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch (eAction) {
		case eMove:
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize:
			this.transformer = new GResizer(this.selectedShape);
			break;
		case eRotate:
			this.transformer = new GRotater(this.selectedShape);
			break;
		default:
			break;
		}

		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.initTransforming(graphics2d, x, y);

		Vector<GShapeTool> undoVector = new Vector<GShapeTool>();
		for (GShapeTool shape : this.shapes) {
			undoVector.add(shape.clone());
		}
		this.undoList.add(undoVector);
	}

	private void keepTransforming(int x, int y) {
		repaint();
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.keepTransforming(graphics2d, x, y);
	}

	private void finishTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		this.transformer.finishTransforming(graphics2d, x, y);
		this.setSelected(this.selectedShape);

		this.bModified = true;

	}

	private void Cursor(int x, int y) {
		GShapeTool shape = this.onShape(x, y);
		if (shape == null) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		} else {
			EAnchors eAnchor = shape.getSelectedAnchor();
			if (eAnchor == null) {
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			} else {
				this.setCursor(new Cursor(eAnchor.getCursorType()));
			}
		}
	}

	private class GMouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

		private boolean isDrawing;
		private boolean isTransforming;

		public GMouseHandler() {
			this.isDrawing = false;
			this.isTransforming = false;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
					setSelected(selectedShape);

				} else if (e.getButton() == MouseEvent.BUTTON3) { /////////////////////////////
					p.show(GPanel.this, e.getX(), e.getY());
				}

				else {
					initTransforming(selectedShape, e.getX(), e.getY());
					this.isTransforming = true;
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			} else if (this.isTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;

				}
			} else if (this.isTransforming) {
				finishTransforming(e.getX(), e.getY());
				this.isTransforming = false;

			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			Cursor(e.getX(), e.getY());
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(), e.getY());

				}
			}

		}

		private void mouseLButton1Clicked(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					setSelected(selectedShape);
				}
			} else {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}
		}

		private void mouseLButton2Clicked(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			}
		}

		private void mouseRButton1Clicked(MouseEvent e) {
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.mouseLButton1Clicked(e);
				} else if (e.getClickCount() == 2) {
					this.mouseLButton2Clicked(e);
				}
			} else if (e.getButton() == MouseEvent.BUTTON2) {
				if (e.getClickCount() == 1) {
					this.mouseRButton1Clicked(e);
				}
			} 
		}
	}

	private class GActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == item1) {
				front();
			} else if (e.getSource() == item2) {
				back();
			} else if (e.getSource() == item3) {
				scaleUp();
			} else if (e.getSource() == item4) {
				scaleDown();
			} else if(e.getSource() == item5) {
				reversal();
			}

		}


	}


	private void reversal() {
		double cx = this.getBounds().getCenterX();
		double cy = this.getBounds().getCenterY();

		
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(cx, cy);
		affineTransform.shear(-0.5, 0);
		affineTransform.translate(-cx, -cy);

		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
		repaint();

	}
	
	
	private void scaleUp() {

		double cx = this.getBounds().getCenterX();
		double cy = this.getBounds().getCenterY();

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(cx, cy);
		affineTransform.scale(1.07, 1.07);
		affineTransform.translate(-cx, -cy);

		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));

		repaint();
	}

	private void scaleDown() {

		double cx = this.getBounds().getCenterX();
		double cy = this.getBounds().getCenterY();

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(cx, cy);
		affineTransform.scale(0.93, 0.93);
		affineTransform.translate(-cx, -cy);

		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));

		repaint();
	}

	public void front() {

		Vector<GShapeTool> temp = new Vector<GShapeTool>();

		for (GShapeTool shapeTool : shapes) {
			temp.add(shapeTool);
		}

		this.shapes.removeAllElements();
		temp.add(selectedShape);

		for (GShapeTool shapeTool : temp) {
			shapes.add(shapeTool);
		}

		repaint();
	}

	public void back() {

		GShapeTool backShape = selectedShape;
		this.shapes.remove(selectedShape);

		Vector<GShapeTool> temp = new Vector<GShapeTool>();
		for (GShapeTool shapeTool : shapes) {
			temp.add(shapeTool);
		}

		this.shapes.removeAllElements();
		this.shapes.add(backShape);

		for (GShapeTool shapeTool : temp) {
			shapes.add(1, shapeTool);
		}

		repaint();
	}

	public void redo() {
		if (!redoList.isEmpty()) {

			Vector<GShapeTool> undoVec = new Vector<GShapeTool>();
			for (GShapeTool shapeTool : shapes) {
				undoVec.add(shapeTool.clone());
			}

			this.undoList.add(undoVec);

			repaint();

			Vector<GShapeTool> redoVec = new Vector<GShapeTool>();
			redoVec = this.redoList.lastElement();

			this.shapes.clear();
			this.shapes = redoVec;

			this.redoList.remove(redoList.lastElement());
			this.setSelected(this.selectedShape);

			repaint();

		}
	}

	public void undo() {
		if (!undoList.isEmpty()) {
			Vector<GShapeTool> redoVec = new Vector<GShapeTool>();
			for (GShapeTool shapeTool : shapes) {
				redoVec.add(shapeTool.clone());
			}

			this.redoList.add(redoVec);
			paint(getGraphics());

			Vector<GShapeTool> undoVec = new Vector<GShapeTool>();
			undoVec = this.undoList.lastElement();

			this.shapes.clear();
			this.shapes = undoVec;

			this.undoList.remove(undoList.lastElement());
			this.setSelected(this.selectedShape);

			repaint();
		}
	}

	public void cut() {
		this.copy();
		this.delete();
	}

	public void copy() {
		if (selectedShape != null) {
			this.copyShape = this.selectedShape.clone();
		}
	}

	public void paste() {

		Vector<GShapeTool> redoVec = new Vector<GShapeTool>();
		for (GShapeTool shapeTool : shapes) {
			redoVec.add(shapeTool.clone());
		}

		this.undoList.add(redoVec);

		this.transformer = new GMover(this.copyShape);
		this.transformer.copyMove();

		GShapeTool pasteShape = this.copyShape.clone();

		this.shapes.add(pasteShape);
		this.setSelected(pasteShape);

	}

	public void delete() {
		Vector<GShapeTool> redoVec = new Vector<GShapeTool>();
		for (GShapeTool shapeTool : shapes) {
			redoVec.add(shapeTool.clone());
		}

		this.redoList.add(redoVec);
		this.shapes.remove(this.selectedShape);

		repaint();
	}

	public void Text() {
		Calendar cal = Calendar.getInstance();
		String now = cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월 " + cal.get(Calendar.DATE)
				+ "일";

		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setFont(new Font("맑은 고딕", Font.PLAIN, 50));

		graphics2D.drawString(now, this.getWidth() / 2 - 180, this.getHeight() / 2);

	}
}
