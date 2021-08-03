package main;

import java.awt.Dimension;
import java.awt.Point;
import shapeTools.GArc;
import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GRectangle;
import shapeTools.GRoundRec;
import shapeTools.GPolygon;
import shapeTools.GShapeTool;
import shapeTools.GQuadcurve;

public class GConstants {
	public static class CFrame {
		public final static Point point = new Point(200, 100);
		public final static Dimension dimesion = new Dimension(900, 800);
	}
		// 400 , 600
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing
	};
	
	public final static int wAnchor = 10;	
	public final static int hAnchor = 10;
	
	public enum EAction {
		eDraw,
		eMove,
		eResize,
		eRotate,
		eShear
	}
	
	public enum EShapeTool {
		
		eRectangle(new GRectangle(), "Rectangle"), // "eRectangle"
		eRoundRec(new GRoundRec(), "RoundRec"),
		eOval(new GOval(), "Oval"),
		eLine(new GLine(), "Line"),
		ePolygon(new GPolygon(), "Polygon"),
		eArc(new GArc(), "Arc"),
		eQuadcurve(new GQuadcurve(), "Quadcurve");
		//eImg(new GImg(), "Img");
		
		private GShapeTool shapeTool;
		private String text;

		private EShapeTool(GShapeTool shapeTool, String text) {
			this.shapeTool = shapeTool;
			this.text = text;
		}
		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}
		public String getText() {
			return this.text;
		}
	}
	
	
	public enum EMenu {
		eFile("����"),
		eEdit("����"),
		eColor("������"),
		eHelp("����");		
		
		private String text;
		
		private EMenu(String text) {
			this.text = text;
		}
	
		public String getText() {
			return this.text;
		}
	}
	
	public enum EFileMenuItem {
		eWorkspace("�� ȭ�� ����"),
		eNew("���θ����"),
		eOpen("����"),
		eSave("����"),
		eSaveAs("�ٸ��̸�����"),
		ePrint("����Ʈ"),
		eExit("������");
		
		private String text;
		private EFileMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EEditMenuItem {
		eUndo("�������"), 
		eRedo("�����"),
		eCut("�߶󳻱�"),
		eCopy("�����ϱ�"),
		ePaste("�ٿ��ֱ�"),
		eDelete("�����ϱ�"),
		eFront("������ ������"),
		eBack("�ڷ� ������");
		

		private String text;
		EEditMenuItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
	
	public enum EColorMenuItem {
		eLineColor("�� ��"),
		eFillColor("���� ��"),
		eBackColor("��� ��"),
		eLineWidth("�� �β�"),
		eLineChange("����/�Ǽ�"),
		eImage("�̹��� �߰�");
		
		private String text;
		
		EColorMenuItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
	
	public enum EHelpItem {
		eCapture("��ũ����"),
		eSetText("������ ��¥"),
		eInfo("�б� Ȩ������");

		private String text;
		
		EHelpItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
}
