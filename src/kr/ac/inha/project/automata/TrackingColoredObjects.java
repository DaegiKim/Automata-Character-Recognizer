package kr.ac.inha.project.automata;

import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import kr.ac.inha.project.automata.lucene.Searcher;
import kr.ac.inha.project.automata.pointlist.PList;
import kr.ac.inha.project.automata.unicode.Consonant;
import kr.ac.inha.project.automata.unicode.Normalization;

/**
 * 컬러 객체를 추적하는 클래스
 * 
 * 프로그램 요구사항
 * 		-javacv: 0.2
 * 		-opencv: 2.4.2, C:/opencv 폴더 아래에 설치되어 있을 것.
 * 		-엑세스 가능한 카메라 장치
 * 
 * @author DAEGI KIM
 */

public class TrackingColoredObjects {
	//디버깅 모드 플래그
	//true: 디버깅 모드
	//false: 릴리즈 모드
	private static boolean isDebugMod = true;
	
	//루씬 검색기
	private static Searcher searcher;
	
	//초성, 중성1, 중성2, 종성 포인트 리스트
	private static PList pl1 = new PList();
	private static PList pl2 = new PList();
	private static PList pl3 = new PList();
	private static PList pl4 = new PList();
	
	//입력된 문자열을 정규화한다.
	private static Normalization Nor;
	
	private static Consonant Con;
//	private static CharFind cf;
	private static IplImage frame;
	
	private static int keycode = -1;
	private static int prevKeycode = -1;
	private static int posX = 0;
	private static int posY = 0;
	private static int lastX = 0;
	private static int lastY = 0;
	private static long TAT = 0l;
	
	private static int cho = 0;
	private static int combineJung = 0;
	private static int jung1 = -1;
	private static int jung2 = -1;
	private static int jong = 0;
	
	private static int length = 0;
	private static int unicode = 0;
	
	private static String resultChar = "";
	private static String chain = "";
	private static String normalized = "";
	
	/**
	 * 파라미터로 받은 IplImage에서 Red 영역을 검출하고
	 * 이진화 된 결과를 반환한다.
	 * @param RGB 컬러의 원본 영상
	 * @return Red 영역이 표시된 이진 영상
	 */
	private static IplImage GetThresholdedImage(IplImage img){
		//임시 객체 imgHSV 생성
		IplImage imgHSV = cvCreateImage(cvGetSize(img), 8, 3);

		//원본 이미지를 HSV 컬러 형식으로 변환하여 imgHSV에 할당
		cvCvtColor(img, imgHSV, CV_BGR2HSV);

		//검출 결과를 저장할 객체 생성
		//Binary 컬러 이미지
		IplImage imgThreshed = cvCreateImage(cvGetSize(img), 8, 1);

		//imgHSV에서 Red 영역을 검출하여 imgThreshed에 저장한다
		cvInRangeS(imgHSV, cvScalar(140, 135, 135, 0), cvScalar(179, 255, 255, 0), imgThreshed);

		//자원해제
		cvReleaseImage(imgHSV);

		//결과 반환
		return imgThreshed;
	}
	
	/**
	 * 이미지 영상에 문자열을 기록한다.
	 * @param 이미지
	 * @param 입력할 문자열
	 * @return 문자열이 입력된 이미지
	 */
	private static BufferedImage PutText(BufferedImage old, String str) {
		int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = img.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.ORANGE);
        g2d.setFont(new Font("San-Serif", Font.BOLD, 35));
        //String s = "Hello, world!";
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(str) - 5;
        int y = fm.getHeight()-20;
//        Composite c = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .8f);
//        g2d.setComposite(c);
        g2d.drawString(str, x, y);
        g2d.dispose();
        return img;
	}
	
	private static void loadingMsg(String path, int sleep) throws IOException{
		File f = null;
		BufferedImage img = null;
		f = new File(path);
		img = ImageIO.read(f);
		frame = IplImage.createFrom(img);
		cvShowImage("video", frame);
		cvWaitKey(sleep);
		f=null; img=null; frame=null;
		System.gc();
	}
	
    public static void main(String[] args) throws Exception {
    	if(isDebugMod){
        	//3개의 윈도우 생성
        	//video = 원본 영상, thresh = red 영역 검출 영상, scribble = 선 그리기 영상
        	cvNamedWindow("video"); cvMoveWindow("video", 650, 0);
        	cvNamedWindow("thresh"); cvMoveWindow("thresh", 0, 0);
        	cvNamedWindow("scribble"); cvMoveWindow("scribble", 0, 384);
    	}else{
    		Toolkit t = Toolkit.getDefaultToolkit();
        	Dimension dim = t.getScreenSize();
    		cvNamedWindow("video"); cvMoveWindow("video", dim.width/2-320, dim.height/2-240);
    		
        	loadingMsg("data/Title.bmp", 1500);
        	loadingMsg("data/Inha.bmp", 1500);
        	
        	loadingMsg("data/OpenCV.bmp", 500);
        	loadingMsg("data/Java.bmp", 500);
        	loadingMsg("data/Lucene.bmp", 500);
    	}
    	
		loadingMsg("data/init1.bmp", 100);
		
    	searcher = new Searcher();
//    	cf = new CharFind();
    	Con = new Consonant();
    	
    	loadingMsg("data/init3.bmp", 100);
    	
    	//카메라 캡쳐 객체 생성
    	CvCapture capture = null;
    	capture = cvCreateCameraCapture(0);
    	
    	loadingMsg("data/init5.bmp", 100);
    	
    	//카메라 캡쳐 객체 생성 에러 처리
    	if(capture.isNull()) {
    		System.err.println("Could not initialize capturing...");
    		return;
    	}
    	
    	loadingMsg("data/init7.bmp", 100);
		
    	//'낙서' 데이터를 저장할 이미지 객체.
    	//red 물체 위치를 추적한다.
    	IplImage imgScribble = null;
    	IplImage threshTemp = null;
    	
    	loadingMsg("data/init9.bmp", 100);
    	loadingMsg("data/init10.bmp", 100);
    	
    	//무한 루프
        while (true) {
        	long start = System.currentTimeMillis();
        	
        	//카메라로 부터 1 프레임을 frame 객체로 가져온다.
        	frame = null;
        	frame = cvQueryFrame(capture);
        	
        	//만약 프레임을 못 받았을 경우 에러 처리
        	if(frame==null)	break;
        	
        	//원본 영상의 노이즈 제거
//        	medianBlur(frame, frame, 5);
        	cvFlip(frame, frame, -1);
        	
        	//최초 실행 시 imgScribble 객체가 null일 경우
        	if(imgScribble==null) {
        		imgScribble = cvCreateImage(cvGetSize(frame), frame.depth(), frame.nChannels());
        		cvSet(imgScribble, cvScalar(0,0,0,0));
        	}
        	
        	//원본 영상을 함수로 넘겨 Red 영역을 검출한다.
        	//이진화를 통해 Red = White, 그외 = Black
        	IplImage imgRedThresh = GetThresholdedImage(frame);
        	CvMoments moments = new CvMoments();
        	
        	//물체의 모멘트 계산
        	cvMoments(imgRedThresh, moments, 1);
        	double moment10 = cvGetSpatialMoment(moments, 1, 0);
        	double moment01 = cvGetSpatialMoment(moments, 0, 1);
        	double area = cvGetCentralMoment(moments, 0, 0);
        	
        	lastX = posX;
        	lastY = posY;
        	
        	//덩어리의 중심점 구하기
        	posX = (int) (moment10/area);
        	posY = (int) (moment01/area);
    	
        	if(keycode == 96) {
        		switch(prevKeycode){
        		case 49: pl1.setFlag(); break;
        		case 50: pl2.setFlag(); break;
        		case 51: pl3.setFlag(); break;
        		case 52: pl4.setFlag(); break;
    			default: break;
        		}
        		prevKeycode = -1;
        	}
        	
        	if(keycode != -1) {
        		prevKeycode = keycode;
        	}
        	
        	keycode = cvWaitKey(1);
        	
        	//현재 입력된 keycode를 원본 이미지에 출력
        	frame = IplImage.createFrom(PutText(frame.getBufferedImage(), "RESULT: "+resultChar+" TAT: "+TAT+"ms keycode: "+prevKeycode));
        	
        	//ESC 입력시 프로그램 종료
    		if(keycode == 27) {
    			break;
    		}
    		
    		//5 키 입력시 화면 클리어
    		else if(keycode == 53) {
    			resultChar = "";
    			posX = 0; posY =0; lastX = 0; lastY = 0;
    			cho = 0; jung1 = -1; jung2 = -1; combineJung = 0; jong = 0; chain = ""; length = 0;
//    			cf = new CharFind();
    			pl1 = new PList(); pl2 = new PList(); pl3 = new PList(); pl4 = new PList();
    			
    			cvReleaseImage(imgScribble);
    			System.gc();
    			imgScribble = cvCreateImage(cvGetSize(frame), frame.depth(), frame.nChannels());
    			cvSet(imgScribble, cvScalar(0,0,0,0));
    		}
    		
    		//1~4 키 입력시
    		else if(prevKeycode >= 49 && prevKeycode <= 52) {
	        	if(lastX>0 && lastY>0 && posX>0 && posY>0 && lastX != posX || lastY != posY)
	    		{
	        		cvWaitKey(1);
	        		//System.out.println("c:("+posX+","+posY+") l:("+lastX+","+lastY+")");
	        		//imgScribble 객체에 Line을 그린다.
	        		cvLine(imgScribble, cvPoint(posX, posY), cvPoint(lastX, lastY), cvScalar(255,255,255,0), 1, 0, 0);
	        		
	        		switch(prevKeycode){
	        		case 49:
	        			cvCircle(imgScribble, cvPoint(posX, posY), 4, cvScalar(0,0,255,0), 1, 8, 0);
	        			pl1.add(posX, posY);
	        			
	        			if(prevKeycode == -1){
	        				pl1.setFlag();
	        			}	        			
	        			
	        			chain = pl1.getChaincode();
	        			length = chain.length();
	        			if(length>0){
	        				Nor = new Normalization(chain);	
	        				normalized = Nor.normalizer();
	        				cho = Con.getInit_Con(normalized);
	        				System.out.println("Nor1: "+normalized);		
	        			}
	        			System.out.println("pl1: "+pl1.getChaincode());
	        			break;
	        		case 50:
	        			cvCircle(imgScribble, cvPoint(posX, posY), 4, cvScalar(0,255,0,0), 1, 8, 0);
	        			pl2.add(posX, posY);
	        			
	        			if(prevKeycode == -1){
	        				pl2.setFlag();
	        			}
	        			
	        			chain = pl2.getChaincode();
	        			length = chain.length();
	        			if(length>0){
	        				Nor = new Normalization(chain);	
	        				normalized = Nor.normalizer();
	        				jung1 = Con.getMid2_Con(normalized);
	        				System.out.println("Nor2: "+normalized);		
	        			}
	        			System.out.println("pl2: "+pl2.getChaincode());
	        			break;
	        		case 51:
	        			cvCircle(imgScribble, cvPoint(posX, posY), 4, cvScalar(255,0,0,0), 1, 8, 0);
	        			pl3.add(posX, posY);
	        			
	        			if(prevKeycode == -1){
	        				pl3.setFlag();
	        			}
	        			
	        			chain = pl3.getChaincode();
	        			length = chain.length();
	        			if(length>0){
	        				Nor = new Normalization(chain);	
	        				normalized = Nor.normalizer();
	        				jung2 = Con.getMid1_Con(normalized);
	        				System.out.println("Nor3: "+normalized);		
	        			}
	        			System.out.println("pl3: "+pl3.getChaincode());
	        			break;
	        		case 52:
	        			cvCircle(imgScribble, cvPoint(posX, posY), 4, cvScalar(0,255,255,0), 1, 8, 0);
	        			pl4.add(posX, posY);
	        			
	        			if(prevKeycode == -1){
	        				pl4.setFlag();
	        			}
	        			
	        			chain = pl4.getChaincode();
	        			length = chain.length();
	        			if(length>0){
	        				Nor = new Normalization(chain);	
	        				normalized = Nor.normalizer();	
	        				jong = Con.getFinal_Con(normalized);
	        				System.out.println("Nor4: "+normalized);		
	        			}
	        			System.out.println("pl4: "+pl4.getChaincode());
	        			break;
	        		default:
	        			break;
	        		}
	        		
//	        		cvWaitKey(1);
	        		combineJung = Con.getMidf_Con(jung1, jung2);
	        		unicode = 44032 + cho * 588 + combineJung * 28 + jong;
	        		
//	        		long ss = System.currentTimeMillis();
//	        		cf = new CharFind();
//	        		resultChar = cf.Finder(unicode);
	        		resultChar = searcher.Searching(String.valueOf(unicode));
//	        		long ee = System.currentTimeMillis();
//	        		System.out.println(ee-ss+"ms");
	        		
	        		System.out.println("RESULT: "+resultChar);
	    		}
        	}
        	
    		cvWaitKey(1);
        	//화면에 결과를 출력한다.
    		
    		threshTemp = cvCreateImage(cvGetSize(frame), 8, 3);
    		cvCvtColor(imgRedThresh, threshTemp, CV_GRAY2RGB);
//    		cvAdd(imgScribble, threshTemp, threshTemp, null);
    		cvAdd(imgScribble, frame, frame, null);
    		
    		
    		if(isDebugMod){
        		cvShowImage("video", frame);
        		cvShowImage("thresh", threshTemp);
        		cvShowImage("scribble", imgScribble);
    		}else{
    			cvShowImage("video", frame);
    		}

    		//자원해제
    		cvReleaseImage(imgRedThresh);
    		cvReleaseImage(threshTemp);
    		System.gc();
    		
    		long end = System.currentTimeMillis();
    		TAT = end-start;
//    		System.out.println("TAT: "+TAT+"ms");
        }
        
        //자원해제
        cvDestroyAllWindows();
        cvReleaseCapture(capture);
    }
}
