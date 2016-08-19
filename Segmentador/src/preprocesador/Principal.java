package preprocesador;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Principal {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg2413");
		String dir = "E:\\";
	    String file = "E:\\video.mp4";
	    VideoCapture vc = new VideoCapture();
	    vc.open(file);
	    if(vc.open(file)) {
	        System.out.println("Success");
	    } else {
	        System.out.println("Failure");
	        
	    }
		
		
		
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
		  System.loadLibrary("opencv_ffmpeg");
	      Mat mat = Mat.eye( 3, 3, CvType.CV_8UC1 );
	      System.out.println("mat = " + mat.dump() );
	      Analizador aux = new Analizador();
	      aux.CargarVideo("C:\\Users\\Jorge\\workspace\\prueba\\dis.mp4");
	    
	    
	}
	
}
