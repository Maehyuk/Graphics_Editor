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
		eFile("파일"),
		eEdit("편집"),
		eColor("디자인"),
		eHelp("도움말");		
		
		private String text;
		
		private EMenu(String text) {
			this.text = text;
		}
	
		public String getText() {
			return this.text;
		}
	}
	
	public enum EFileMenuItem {
		eWorkspace("새 화면 띄우기"),
		eNew("새로만들기"),
		eOpen("열기"),
		eSave("저장"),
		eSaveAs("다른이름으로"),
		ePrint("프린트"),
		eExit("나가기");
		
		private String text;
		private EFileMenuItem(String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EEditMenuItem {
		eUndo("실행취소"), 
		eRedo("재실행"),
		eCut("잘라내기"),
		eCopy("복사하기"),
		ePaste("붙여넣기"),
		eDelete("삭제하기"),
		eFront("앞으로 보내기"),
		eBack("뒤로 보내기");
		

		private String text;
		EEditMenuItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
	
	public enum EColorMenuItem {
		eLineColor("선 색"),
		eFillColor("도형 색"),
		eBackColor("배경 색"),
		eLineWidth("선 두께"),
		eLineChange("점선/실선"),
		eImage("이미지 추가");
		
		private String text;
		
		EColorMenuItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
	
	public enum EHelpItem {
		eCapture("스크린샷"),
		eSetText("오늘의 날짜"),
		eInfo("학교 홈페이지");

		private String text;
		
		EHelpItem(String text) {
			this.text = text;
		}

		public String getText() {
			return this.text;
		}
	}
}
