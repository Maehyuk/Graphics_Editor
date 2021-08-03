package shapeTools;


import java.awt.geom.RoundRectangle2D;

import main.GConstants.EDrawingStyle;

public class GRoundRec extends GShapeTool {

	private static final long serialVersionUID = 1L;

	public GRoundRec() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new RoundRectangle2D.Double();

	}

	@Override
	public GShapeTool newInstance() {
		return new GRoundRec();
	}

	@Override
	public void setInitPoint(int x, int y) {
		RoundRectangle2D roundRec = (RoundRectangle2D) this.shape;
		roundRec.setRoundRect(x, y, 0, 0, 70, 70);
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		RoundRectangle2D roundRec = (RoundRectangle2D) this.shape;
		roundRec.setRoundRect(roundRec.getX(),roundRec.getY(),x-roundRec.getX(),y-roundRec.getY(),70,70);
	}
}
