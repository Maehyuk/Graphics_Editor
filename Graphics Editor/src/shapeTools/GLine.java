package shapeTools;


import java.awt.geom.Line2D;
import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {

	private static final long serialVersionUID = 1L;

	public GLine() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Line2D.Double();
	}
	
	@Override
	public GShapeTool newInstance() {
		return new GLine();
	}
	
	@Override
	public void setInitPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(x, y, x, y);
	}
	
	@Override
	public void setFinalPoint(int x, int y) {}

	@Override
	public void movePoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(((Line2D) this.shape).getX1(), 
				((Line2D) this.shape).getY1(), x, y);		
	}	
}
