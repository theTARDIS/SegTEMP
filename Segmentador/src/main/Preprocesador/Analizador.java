package main.Preprocesador;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.opencv.highgui.VideoCapture;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import main.MotorAlgoritmia.*;
/**
 * Esta clase se encarga del procesamiento de la imagen 
 * para la segmentacion temporal
 * @author: German Vives
 * @version: 1.0
 *
 */
public class Analizador {
	/**
	 * Esta Función recibe la matriz de la imagen.
	 * genera el buffer de datos de la imagen
	 * @author: German Vives
	 * @version: 1.0
	 * @return buffer de datos de imagem listo para ser procesado
	 * @param m es la matriz de la imagen.
	 */
	public final BufferedImage mat2BufferedImage(final Mat m) {
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image =
		new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels =
				((DataBufferByte) image.getRaster()
						.getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
	}
    /**
     * Esta funcion recibe la direccion del video.
	 * y realiza la segmentacion temporal.
	 * @author: German Vives
	 * @version: 1.0
	 * @param ruta es la direccion local del video
	 *
	 **/
	public final void segmentador(final String ruta) {
		Mat frame = new Mat();
		VideoCapture camera = new VideoCapture(ruta);
		camera.open(ruta);
		final int tamano = 255;
		System.out.println(ruta);
		double suma = 0.0;
		ArrayList<Mat> cola = new ArrayList<Mat>();
		ArrayList<Double> varaciones = new ArrayList<Double>();
		while (camera.read(frame)) {

			HSV a = new HSV();
			Mat salida =
			a.convertirBGR2HSV(frame);
			List<Mat> canales = new ArrayList<Mat>();
			Core.split(salida, canales);
			salida = canales.get(0);
			
			Core.normalize(salida, salida, 0, 179,
					Core.NORM_MINMAX, -1);
	
			
			Mat h1 = calcularHistograma(salida);
			//System.out.println(h1.dump());

			h1 = normalizar(h1);
		//	 System.out.println("--NORMALIZADO--");
		//	System.out.println(h1.dump());
			cola.add(h1);
			if (cola.size() == 2) {
			    
				Mat m1 = cola.remove(0);
				Mat m2 = cola.get(0);
				ArrayList<Double> hist1 = mat2List(m1);
				ArrayList<Double> hist2 = mat2List(m2);
				
				//double distHue = distancia(hist1,hist2);
				
				
			//	double distValue = distancia(hist1,hist2);
				
				varaciones.add(distancia(hist1,hist2));
			}
			
		
		}
	
		double media = promedio(varaciones);
		double ac = 0.0;
		for (int index = 0; index < varaciones.size(); index++) {
			ac = ac + ((varaciones.get(index) - media) * (varaciones.get(index) - media));
		}
		double desvest = Math.sqrt(ac / varaciones.size());
		ArrayList<Integer> cortes = new ArrayList<Integer>();
		ArrayList<Integer> noCortes = new ArrayList<Integer>();
		double ejex = desvest + media;
		System.out.println(ejex);
		for (int m = 0; m < varaciones.size(); m++) {
			if (varaciones.get(m) >= desvest + media) {
				cortes.add(m+1);
			}
			else {
				noCortes.add(m+1);
			}
		}
		System.out.println(varaciones);
		System.out.println(cortes);	
	}
	
   /**
	 * Esta función recibe el histograma.
	 * y retorna el valor promedio de dicho histograma.
	 * @author: German Vives.
	 * @version: 1.0
	 * @param histograma
	 * @return valor promedio
	 */
	public double promedio(ArrayList<Double> histograma) {
		double prom = 0.0;
		for (int i = 0; i < histograma.size(); i++) {
			prom = prom + histograma.get(i);
		}
		return prom / histograma.size();
	}
	
	
	
	 /**
     * Esta función recibe el histograma.
     * y retorna el valor medio de dicho histograma.
     * @author: German Vives.
     * @version: 1.0
     * @param histograma
     * @return valor medio
     */
    public double valorMedio(ArrayList<Double> histograma) {
        
        double min = 0.0;
        double max = 0.0;
        for (int i = 0; i < histograma.size(); i++) {
            if (histograma.get(i) >= max){
                max = histograma.get(i) ;
            }
            if (histograma.get(i) <= min){
                min = histograma.get(i) ;
            }
        }
               
        return ((min+max)/2);
    }
	
	
	/**
	 * Esta función recibe la matriz del histograma.
	 * y retorna la conversion del mismo a ArrayList
	 * @author: German Vives
	 * @version: 1.0
	 * @param mat
	 * @return histograma en arrayList
	 */
	public final ArrayList<Double> mat2List(final Mat mat) {
		ArrayList<Double> histograma = new ArrayList<Double>();
		for (int j = 0; j < mat.rows(); j++) {
			double[] salidax = mat.get(j, 0);
			histograma.add(salidax[0]);
		}
		return histograma;
	}
	/**
	 *  Esta función recibe dos matrices de histogramas 
	 * y retorna la distancia de bhattacharyya entre ellos
	 * @author: German Vives
	 * @version: 1.0
	 * @param Matriz histograma1, Matriz histograma2
	 *           metodo 1
	 */
	public double distancia (Mat H1,Mat H2){
		
		return Imgproc.compareHist(H1, H2, Imgproc.CV_COMP_BHATTACHARYYA );
	}
	/**
	 * Esta función recibe dos matrices de histogramas. 
	 * y retorna la distancia de bhattacharyya entre ellos
	 * @author: German Vives
	 * @version: 1.0
	 * @param histograma1
	 * @param histograma2
	 * @return distancia
	 *           metodo 2
	 */
	public final double distancia(final ArrayList<Double> histograma1, final ArrayList<Double> histograma2) {
		double promedio1 = promedio(histograma1);
		double promedio2 = promedio(histograma2);
		double score = 0;
		int i;
		for (i = 0; i < histograma2.size(); i++) {
			score += Math.sqrt(histograma1.get(i) * histograma2.get(i));
		}
		score = Math.sqrt(1 - (1 / (Math.sqrt(promedio1 * promedio2 * histograma1.size() * histograma2.size()))) * score);
		return (score);
	}
    
	/**
	 * Esta función recibe dos matrices de histogramas 
	 * y retorna la distancia de bhattacharyya entre ellos
	 * @author: German Vives
	 * @version: 1.0
	 * @param Matriz histograma1, Matriz histograma2
	 * @return distancia bhattacharyya
	 *           metodo 2
	 */
	public final Mat calcularHistograma(final Mat mat1) {
		java.util.List<Mat> matList = new LinkedList<Mat>();
		Mat histogram = new Mat();
		matList.add(mat1);
		MatOfInt histSize = new MatOfInt(179);
		MatOfFloat histRange = new MatOfFloat(0, 179); // Componentes de 256
		matList.add(mat1);
		Imgproc.calcHist(matList, new MatOfInt(0), new Mat(),histogram ,histSize, histRange);
	   //calcHist(List<Mat> images, MatOfInt channels, Mat mask, Mat hist, MatOfInt histSize, MatOfFloat ranges)
		
		return histogram;
	}

	/**
	 * Esta función recibe el histograma.
	 * y retorna el histograma normalizado entre [0,1]
	 * @author: German Vives
	 * @version: 1.0
	 * @param histograma
	 * @return histograma normalizado
	 */
	public final Mat normalizar(final Mat histograma) {
		Core.normalize(histograma, histograma, 1, 0, Core.NORM_L1, -1, new Mat());
		return histograma;

	}
	/**
	 * Esta función recibe un frame de imagen con un nombre
	 * y almacena una imagen en formato .jpg del mismo
	 * @author: German Vives
	 * @version: 1.0
	 * @param nombre
	 * @param frame
	 *         Pruebas
	 */
	public final void almacenarImagen(final Mat frame, final String nombre) {

		ImageIcon image = new ImageIcon(mat2BufferedImage(frame));
		Image img = image.getImage();
		BufferedImage bi = new BufferedImage(img.getWidth(null),
		img.getHeight(null), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		try {
			File theDir = new File("output");
			theDir.mkdir();
			ImageIO.write(bi, "jpg", new File("output/" + nombre + ".jpg"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Esta función recibe una imagen.
	 * y se encarga de mostrarla.
	 * @author: German Vives
	 * @version: 1.0
	 * @param img2
	 */
	public final void displayImage(final Image img2) {

		ImageIcon icon = new ImageIcon(img2);
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);
		JLabel lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
