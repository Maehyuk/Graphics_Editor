package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
	protected int px, py, setX, setY;
	protected Point2D.Double Center;
	protected double angle;

	public GTransformer(GShapeTool selectedShape) {
		this.selectedShape = selectedShape;
	}

	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.px = x;
		this.py = y;

		setX = selectedShape.getShape().getBounds().x;
		setY = selectedShape.getShape().getBounds().y;

		Center = new Point2D.Double(this.selectedShape.getShape().getBounds().getCenterX(),
				this.selectedShape.getShape().getBounds().getCenterY());
		angle = Math.atan2(Center.y - y, x - Center.x);

	}

	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.transform(graphics2d, x, y);
		this.px = x;
		this.py = y;
	}
	
	

	public void finishTransforming(Graphics2D graphics2d, int x, int y) {
	}

	public void continueTransforming(Graphics2D graphics2d, int x, int y) {
	}
	
	

	abstract public void transform(Graphics2D graphics2d, int x, int y);
	
	abstract public void copyMove();


}
