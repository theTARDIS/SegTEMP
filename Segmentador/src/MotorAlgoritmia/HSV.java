package MotorAlgoritmia;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HSV {
	
	public void HSV() {
		
	}
	
	//Entrada: String con la direccion de la imagen
	//Salida: Matriz con ColorMode HSV
	public Mat convertirBGR2HSV(String direccion){
		
		File input = new File(direccion);
	    BufferedImage image = new BufferedImage(0,0,0) ;
	    
		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error abriendo la imagen");
			Mat nulo = new Mat();
			return nulo;
		}	

	    byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	    Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
	    mat.put(0, 0, data);

	    Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC1);
	    Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_BGR2HSV);
	    
	    return mat1;
		
	}
	
	
	

}
