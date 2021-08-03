package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import shapeTools.GShapeTool;

public class GResizer extends GTransformer {

	private AffineTransform affineTransform;
	
	public GResizer(GShapeTool selectedShape) {
		super(selectedShape);
		affineTransform = new AffineTransform();

	}

	@Override
	public void transform(Graphics2D graphics2d, int x, int y) {

		Point m = new Point();
		double ratioX = 0;
		double ratioY = 0;
		
		double width = selectedShape.getShape().getBounds().width;
		double height = selectedShape.getShape().getBounds().height;

		m.x = -setX;
		m.y = -setY;
		
		affineTransform.setToTranslation(m.x, m.y); 
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape())); 

		switch (selectedShape.getSelectedAnchor()) {
		
		case x0y0:	
			ratioX = (setX - x + width) / (width);
			ratioY = (setY - y + height) / (height);
			m.x = x;
			m.y = y;
			setX = x;
			setY = y;
			break;
			
		case x0y1:  
			ratioX = (setX - x + width) / (width);
			ratioY = 1;
			m.x = x;
			m.y = setY;
			setX = x;
			break;
			
		case x0y2:
			ratioX = (setX - x + width) / (width);
			ratioY = (y - setY) / (height);
			m.x = x;
			m.y = setY;
			setX = x;
			break;
			
		case x1y0:  
			ratioX = 1;
			ratioY = (setY - y + height) / (height);
			m.x = setX;
			m.y = y;
			setY = y;
			break;
			
		case x1y2:
			ratioX = 1;
			ratioY = (y - setY) / (height);
			m.x = setX;
			m.y = setY;
			break;
			
		case x2y0:
			ratioX = (x - setX) / (width);
			ratioY = (setY - y + height) / (height);
			m.x = setX;
			m.y = y;
			setY = y;
			break;
			
		case x2y1:
			ratioX = (double)(x - setX) / (width);
			ratioY = 1;
			m.x = setX;
			m.y = setY;
			break;
			
		case x2y2:
			ratioX = (x - setX) / (width);
			ratioY = (y - setY) / (height);
			m.x = setX;
			m.y = setY;
			break;
			
		}
		affineTransform.setToScale(ratioX, ratioY);
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape())); 

		affineTransform.setToTranslation(m.x, m.y); 
		selectedShape.setShape(affineTransform.createTransformedShape(selectedShape.getShape())); 
		
	}

	@Override
	public void copyMove() {}
}
