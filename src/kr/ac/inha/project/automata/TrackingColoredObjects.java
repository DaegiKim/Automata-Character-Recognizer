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
 * �÷� ��ü�� �����ϴ� Ŭ����
 * 
 * ���α׷� �䱸����
 * 		-javacv: 0.2
 * 		-opencv: 2.4.2, C:/opencv ���� �Ʒ��� ��ġ�Ǿ� ���� ��.
 * 		-������ ������ ī�޶� ��ġ
 * 
 * @author DAEGI KIM
 */

public class TrackingColoredObjects {
	//����� ��� �÷���
	//true: ����� ���
	//false: ������ ���
	private static boolean isDebugMod = true;
	
	//��� �˻���
	private static Searcher searcher;
	
	//�ʼ�, �߼�1, �߼�2, ���� ����Ʈ ����Ʈ
	private static PList pl1 = new PList();
	private static PList pl2 = new PList();
	private static PList pl3 = new PList();
	private static PList pl4 = new PList();
	
	//�Էµ� ���ڿ��� ����ȭ�Ѵ�.
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
	 * �Ķ���ͷ� ���� IplImage���� Red ������ �����ϰ�
	 * ����ȭ �� ����� ��ȯ�Ѵ�.
	 * @param RGB �÷��� ���� ����
	 * @return Red ������ ǥ�õ� ���� ����
	 */
	private static IplImage GetThresholdedImage(IplImage img){
		//�ӽ� ��ü imgHSV ����
		IplImage imgHSV = cvCreateImage(cvGetSize(img), 8, 3);

		//���� �̹����� HSV �÷� �������� ��ȯ�Ͽ� imgHSV�� �Ҵ�
		cvCvtColor(img, imgHSV, CV_BGR2HSV);

		//���� ����� ������ ��ü ����
		//Binary �÷� �̹���
		IplImage imgThreshed = cvCreateImage(cvGetSize(img), 8, 1);

		//imgHSV���� Red ������ �����Ͽ� imgThreshed�� �����Ѵ�
		cvInRangeS(imgHSV, cvScalar(140, 135, 135, 0), cvScalar(179, 255, 255, 0), imgThreshed);

		//�ڿ�����
		cvReleaseImage(imgHSV);

		//��� ��ȯ
		return imgThreshed;
	}
	
	/**
	 * �̹��� ���� ���ڿ��� ����Ѵ�.
	 * @param �̹���
	 * @param �Է��� ���ڿ�
	 * @return ���ڿ��� �Էµ� �̹���
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
        	//3���� ������ ����
        	//video = ���� ����, thresh = red ���� ���� ����, scribble = �� �׸��� ����
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
    	
    	//ī�޶� ĸ�� ��ü ����
    	CvCapture capture = null;
    	capture = cvCreateCameraCapture(0);
    	
    	loadingMsg("data/init5.bmp", 100);
    	
    	//ī�޶� ĸ�� ��ü ���� ���� ó��
    	if(capture.isNull()) {
    		System.err.println("Could not initialize capturing...");
    		return;
    	}
    	
    	loadingMsg("data/init7.bmp", 100);
		
    	//'����' �����͸� ������ �̹��� ��ü.
    	//red ��ü ��ġ�� �����Ѵ�.
    	IplImage imgScribble = null;
    	IplImage threshTemp = null;
    	
    	loadingMsg("data/init9.bmp", 100);
    	loadingMsg("data/init10.bmp", 100);
    	
    	//���� ����
        while (true) {
        	long start = System.currentTimeMillis();
        	
        	//ī�޶�� ���� 1 �������� frame ��ü�� �����´�.
        	frame = null;
        	frame = cvQueryFrame(capture);
        	
        	//���� �������� �� �޾��� ��� ���� ó��
        	if(frame==null)	break;
        	
        	//���� ������ ������ ����
//        	medianBlur(frame, frame, 5);
        	cvFlip(frame, frame, -1);
        	
        	//���� ���� �� imgScribble ��ü�� null�� ���
        	if(imgScribble==null) {
        		imgScribble = cvCreateImage(cvGetSize(frame), frame.depth(), frame.nChannels());
        		cvSet(imgScribble, cvScalar(0,0,0,0));
        	}
        	
        	//���� ������ �Լ��� �Ѱ� Red ������ �����Ѵ�.
        	//����ȭ�� ���� Red = White, �׿� = Black
        	IplImage imgRedThresh = GetThresholdedImage(frame);
        	CvMoments moments = new CvMoments();
        	
        	//��ü�� ���Ʈ ���
        	cvMoments(imgRedThresh, moments, 1);
        	double moment10 = cvGetSpatialMoment(moments, 1, 0);
        	double moment01 = cvGetSpatialMoment(moments, 0, 1);
        	double area = cvGetCentralMoment(moments, 0, 0);
        	
        	lastX = posX;
        	lastY = posY;
        	
        	//����� �߽��� ���ϱ�
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
        	
        	//���� �Էµ� keycode�� ���� �̹����� ���
        	frame = IplImage.createFrom(PutText(frame.getBufferedImage(), "RESULT: "+resultChar+" TAT: "+TAT+"ms keycode: "+prevKeycode));
        	
        	//ESC �Է½� ���α׷� ����
    		if(keycode == 27) {
    			break;
    		}
    		
    		//5 Ű �Է½� ȭ�� Ŭ����
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
    		
    		//1~4 Ű �Է½�
    		else if(prevKeycode >= 49 && prevKeycode <= 52) {
	        	if(lastX>0 && lastY>0 && posX>0 && posY>0 && lastX != posX || lastY != posY)
	    		{
	        		cvWaitKey(1);
	        		//System.out.println("c:("+posX+","+posY+") l:("+lastX+","+lastY+")");
	        		//imgScribble ��ü�� Line�� �׸���.
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
        	//ȭ�鿡 ����� ����Ѵ�.
    		
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

    		//�ڿ�����
    		cvReleaseImage(imgRedThresh);
    		cvReleaseImage(threshTemp);
    		System.gc();
    		
    		long end = System.currentTimeMillis();
    		TAT = end-start;
//    		System.out.println("TAT: "+TAT+"ms");
        }
        
        //�ڿ�����
        cvDestroyAllWindows();
        cvReleaseCapture(capture);
    }
}
