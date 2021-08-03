package transformer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GMover extends GTransformer {

	
	public GMover(GShapeTool selectedShape) {
		super(selectedShape);

	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(x-px, y-py);
		this.selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));

	}

	@Override
	public void copyMove() {
		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(10, 10);
		this.selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape()));
	}
}
