package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;

abstract public class GShapeTool implements Serializable, Cloneable {
	// attributes
	private static final long serialVersionUID = 1L;

	private EDrawingStyle eDrawingStyle;
	protected Shape shape;
	private Ellipse2D[] anchors;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private EAction eAction;
	private AffineTransform affineTransform;
	private Color lineColor, fillColor;
	private int lineWidth;
	private boolean setdotLine;

	public enum EAnchors {
		x0y0(Cursor.NW_RESIZE_CURSOR), x0y1(Cursor.W_RESIZE_CURSOR), x0y2(Cursor.SW_RESIZE_CURSOR),
		x1y0(Cursor.N_RESIZE_CURSOR), x1y2(Cursor.S_RESIZE_CURSOR), x2y0(Cursor.NE_RESIZE_CURSOR),
		x2y1(Cursor.E_RESIZE_CURSOR), x2y2(Cursor.SE_RESIZE_CURSOR), RR(Cursor.HAND_CURSOR);

		private int cursorType;

		EAnchors(int cursorType) {
			this.cursorType = cursorType;
		}

		public int getCursorType() {
			return this.cursorType;
		}
	}

	// working variables

	// constructors
	public GShapeTool(EDrawingStyle eDrawingState) {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.isSelected = false;
		this.eDrawingStyle = eDrawingState;
		this.selectedAnchor = null;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();

	}

	public GShapeTool clone() {
		try {
			return (GShapeTool) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// getters & setters
	public EDrawingStyle getDrawingStyle() {
		return this.eDrawingStyle;
	}

	public EAction getAction() {
		return this.eAction;
	}

	public EAnchors getSelectedAnchor() {
		return selectedAnchor;
	}

	public double getWidth() {
		return this.shape.getBounds2D().getWidth();
	}

	public double getHeight() {
		return this.shape.getBounds2D().getWidth();
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setlineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setfillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setlineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	public void setdotLine(boolean setdotLine) {
		this.setdotLine = setdotLine;
	}

	// methods
	public EAction containes(int x, int y) {
		this.eAction = null;
		if (this.isSelected) {
			for (int i = 0; i < this.anchors.length - 1; i++) {
				if (this.affineTransform.createTransformedShape(this.anchors[i]).contains(x, y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
				}
			}
			if (this.affineTransform.createTransformedShape(this.anchors[EAnchors.RR.ordinal()]).contains(x, y)) {
				this.selectedAnchor = EAnchors.values()[this.anchors.length - 1];
				this.eAction = EAction.eRotate;
			}
		}
		if (this.affineTransform.createTransformedShape(this.shape).contains(x, y)) {
			this.selectedAnchor = null;
			this.eAction = EAction.eMove;
		}
		return this.eAction;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isSelected() {
		return isSelected;
	}

	private void drawAnchors(Graphics2D graphics) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;

		Rectangle rectangle = this.shape.getBounds();
		int x0 = rectangle.x - wAnchor / 2;
		int x1 = rectangle.x - wAnchor / 2 + (rectangle.width) / 2;
		int x2 = rectangle.x - wAnchor / 2 + rectangle.width;
		int y0 = rectangle.y - hAnchor / 2;
		int y1 = rectangle.y - hAnchor / 2 + (rectangle.height) / 2;
		int y2 = rectangle.y - hAnchor / 2 + rectangle.height;

		this.anchors[EAnchors.x0y0.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y1.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y2.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y0.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y2.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y0.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y1.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y2.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0 - 40, wAnchor, hAnchor);

		for (EAnchors eAnchor : EAnchors.values()) {
			Color color = graphics.getColor();
			graphics.setColor(Color.WHITE);
			graphics.setStroke(new BasicStroke(1, 0, BasicStroke.CAP_ROUND, 0));
			graphics.fill(this.affineTransform.createTransformedShape(this.anchors[eAnchor.ordinal()]));
			graphics.setColor(color);
			graphics.draw(this.affineTransform.createTransformedShape(this.anchors[eAnchor.ordinal()]));
		}
	}

	public void draw(Graphics2D graphics2d) {
		Color color = graphics2d.getColor();
		graphics2d.setColor(this.fillColor);
		if (this.setdotLine) {
			float[] dot = new float[] { 10, 5, 5, 5 };
			graphics2d.setStroke(new BasicStroke(lineWidth, 0, BasicStroke.JOIN_MITER, 1.0f, dot, 0));
		} else  if(!this.setdotLine){
			graphics2d.setStroke(new BasicStroke(lineWidth, 0, BasicStroke.CAP_ROUND, 0));
		}
		
		graphics2d.fill(this.affineTransform.createTransformedShape(this.shape));
		graphics2d.setColor(this.lineColor);
		graphics2d.draw(this.affineTransform.createTransformedShape(this.shape));
		graphics2d.setColor(color);

		if (isSelected) {
			this.drawAnchors(graphics2d);
		}
	}

	public void animate(Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	// interface
	public abstract GShapeTool newInstance();

	public abstract void setInitPoint(int x, int y);

	public void setIntermediatePoint(int x, int y) {
	}

	public abstract void setFinalPoint(int x, int y);

	public abstract void movePoint(int x, int y);

}
