package shapeTools;

import java.awt.geom.QuadCurve2D;

import main.GConstants.EDrawingStyle;

public class GQuadcurve extends GShapeTool {

	private static final long serialVersionUID = 1L;

	public GQuadcurve() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new QuadCurve2D.Float();
	}

	@Override
	public GShapeTool newInstance() {
		return new GQuadcurve();
	}

	// methods
	@Override
	public void setInitPoint(int x, int y) {

		QuadCurve2D QC = (QuadCurve2D) this.shape;
		double middleX = (QC.getX1() + QC.getX2()) / 2 + 200;
		double middleY = (QC.getY1() + QC.getY2()) / 2 + 200;
		
		QC.setCurve(x,y, middleX, middleY, x - (QC.getX1()+QC.getX2()), y - (QC.getY1()+ QC.getY2()));

		// 다음 곡선 세그먼트(segment)의 시점의 x 좌표입니다.
		// 다음 곡선 세그먼트(segment)의 시점의 y 좌표입니다.

		// 2 다음 곡선 세그먼트(segment)의 제어점의 x 좌표입니다.
		// 2 다음 곡선 세그먼트(segment)의 제어점의 y 좌표입니다.

		// 다음 곡선 세그먼트(segment)의 종점의 x 좌표입니다.
		// 다음 곡선 세그먼트(segment)의 종점의 y 좌표입니다.

	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		QuadCurve2D QC = (QuadCurve2D) this.shape;
		double middleX = (QC.getX1() + QC.getX2()) / 2;
		double middleY = (QC.getY1() + QC.getY2()) / 2 +200;
		
		QC.setCurve(QC.getX1(),QC.getY1(), middleX, middleY, x - QC.getX2(), y - QC.getY2());
	}

}
