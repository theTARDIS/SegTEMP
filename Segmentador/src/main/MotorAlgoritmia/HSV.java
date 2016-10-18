package main.MotorAlgoritmia;

import java.awt.image.BufferedImage;
import java.awt.Color.*;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class HSV {
	
	public HSV() {
		
	}
	
	//Entrada: String con la direccion de la imagen
	//Salida: Matriz con ColorMode HSV
	public Mat convertirBGR2HSV(String direccion,BufferedImage imagen){
		
		BufferedImage image = null;
		
		try {
			 
			if (direccion!=""){
				File input = new File(direccion);
			 
			    image = ImageIO.read(input);
				}
				else{
					image = imagen;
				}
			
			
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

	    Mat mat1 = new Mat(image.getHeight(),image.getWidth(),CvType.CV_8UC3);
	    Imgproc.cvtColor(mat, mat1, Imgproc.COLOR_BGR2HSV);
	  //  RGBtoHSB(0,0,0);
	    return mat1;
		
	}
	
	
	

}
