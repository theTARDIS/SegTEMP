package main.MotorAlgoritmia;
import org.opencv.core.*;
import java.awt.geom.Point2D;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import java.io.*;
import java.util.*;


import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import java.io.File;
import javax.imageio.ImageIO;

public class calculoDistancia {
	
	public calculoDistancia(){
		
	}
	
	public double distancia(Mat histograma1, Mat histograma2){		
		double resultado = Imgproc.compareHist(histograma1, histograma2, 4);
		return resultado;
	}
	
	public double distancia2(ArrayList<Double> histograma1,ArrayList<Double> histograma2){	
		//ArrayList<Double> ej1 = promedio(histograma1);
		// promedio de ambos histogramas
		double promedio1 = promedio(histograma1);
		double promedio2 = promedio(histograma2);
		double score = 0;
		int i;
		for(i = 0;i<256;i++){
			score += Math.sqrt(histograma1.get(i)*histograma2.get(i));
		}
		score = Math.sqrt(1 - ( 1 / Math.sqrt(promedio1 * promedio2 * 256 * 256)) * score);
		return score;
	}
	
	/*
	public ArrayList<Double> convertir(Mat hist){
		ArrayList<Double> x = new ArrayList<Double>();
		hist.
		return x;
	}
	*/
	public double promedio(ArrayList<Double> histograma){
		double prom = 0.0;
		int i,j;
		for(i=0;i<256;i++){
			prom = prom + histograma.get(i);
		}
		return prom;
	}
	
	public Mat normalizar(Mat histograma){
		Mat normalizado = new Mat();
		Core.normalize(histograma, normalizado, 0, 1, Core.NORM_MINMAX, -1, new Mat());
		return normalizado;
		
	}

}
