package shapeTools;

import java.awt.geom.Arc2D;

import main.GConstants.EDrawingStyle;

public class GArc extends GShapeTool {

	private static final long serialVersionUID = 1L;

	public GArc() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Arc2D.Float();

	}

	@Override
	public GShapeTool newInstance() {
		return new GArc();

	}

	@Override
	public void setInitPoint(int x, int y) {
		Arc2D arc = (Arc2D) this.shape;
		arc.setArc(x, y, 0, 0, 0, 0, Arc2D.PIE);
		
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		Arc2D arc = (Arc2D) this.shape;
		arc.setArc(arc.getX(), arc.getY(), x-arc.getX(), y-arc.getY(), 90, 270 , Arc2D.PIE);
	}

}
