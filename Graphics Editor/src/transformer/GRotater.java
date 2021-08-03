package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GRotater extends GTransformer {

	private AffineTransform affineTransform;

	public GRotater(GShapeTool selectedShape) {
		super(selectedShape);
		affineTransform = new AffineTransform();

	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {
		double angle2 = angle - Math.atan2(Center.y - y, x - Center.x);
		affineTransform.setToRotation(angle2, Center.x, Center.y);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape())); 
		angle = Math.atan2(Center.y - y, x - Center.x); 

	}

	@Override
	public void copyMove() {}

}
