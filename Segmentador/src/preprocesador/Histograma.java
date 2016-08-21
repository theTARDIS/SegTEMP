package preprocesador;

import java.util.LinkedList;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

public class Histograma {
	public void Histograma(){
				
	}
	
	//Entrada: matriz HSV colormode normalizada
	//Salida: matriz con el histograma
	public Mat calcular(Mat mat1){
		// añade matriz a la lista
		java.util.List<Mat> matList = new LinkedList<Mat>();
        matList.add(mat1);
        // Se crean las estructuras necesarias para la generacion del histograma
        Mat histogram = new Mat();
        MatOfFloat ranges=new MatOfFloat(0,256);
        //Imgproc.cal
        Imgproc.calcHist(
                matList, 
                new MatOfInt(0), 
                new Mat(), 
                histogram , 
                new MatOfInt(256), 
                ranges,false);
        System.out.print("histogram\n"+histogram.dump());
        return histogram;
	}

}
