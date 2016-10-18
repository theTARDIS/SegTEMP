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

public class Analizador {
	/**
	 * Esta Función recibe el video a analizar y
	 * genera los cuadros del video
	 * @author: German Vives
	 * @version: 1.0
	 * @param Ruta
	 *            Recibe la dirección del path del v�deo
	 */

	public BufferedImage Mat2BufferedImage(Mat m) {
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

	public void cargarvideo(String Ruta) {
		Mat frame = new Mat();
		VideoCapture camera = new VideoCapture(Ruta);
		camera.open(Ruta);
		System.out.println(Ruta);
		// JFrame jframe = new JFrame("MyTitle");
		// jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JLabel vidpanel = new JLabel();
		// jframe.setContentPane(vidpanel);
		// jframe.setVisible(true);
		double suma = 0.0;
		ArrayList<Mat> Cola = new ArrayList<Mat>();
		ArrayList<Double> varaci = new ArrayList<Double>();
		while (camera.read(frame)) {

			HSV a = new HSV();
			Mat salida = 
					a.convertirBGR2HSV("", Mat2BufferedImage(frame));
			List<Mat> canales = new ArrayList<Mat>();
			Core.split(salida, canales);
			salida = canales.get(0);
			System.out.println(salida.dump());
			Core.normalize(salida, salida, 0, 255,
					Core.NORM_MINMAX, -1, new Mat());
			Mat h1 = calcular(salida);
			System.out.println(h1.dump());
			System.out.println("--NORMALIZADO--");
			h1 = normalizar(h1);
			System.out.println(h1.dump());
			for (int j = 0; j < h1.rows(); j++) {
				double[] salidax = h1.get(j, 0);
				suma = suma + salidax[0];

			}
			System.out.print(suma);
			Cola.add(h1);
			if (Cola.size() == 2) {
				Mat m1 = Cola.remove(0);
				Mat m2 = Cola.get(0);
				ArrayList<Double> hist1 = Mat_to_List(m1);
				ArrayList<Double> hist2 = Mat_to_List(m2);
				varaci.add(distancia(m1, m2));
			}
			break;
		}
	
		double Media = promedio(varaci);
		double ac = 0.0;
		for (int index=0;index<varaci.size();index++){
			ac = ac + ((varaci.get(index)-Media) * (varaci.get(index)-Media));
		}
		double desvest = Math.sqrt(ac/varaci.size());
		
		ArrayList<Integer> Cortes = new ArrayList<Integer>();
		ArrayList<Integer> NoCortes = new ArrayList<Integer>();
		
		System.out.println(desvest + Media);
		for(int m=0;m<varaci.size();m++){
			if (varaci.get(m) >= desvest + Media)
			{
				
				Cortes.add(m);
			}
			else{
				NoCortes.add(m);
			}
			
		}
		
		System.out.println(varaci);
		System.out.println(Cortes);
		
	}

	public double promedio(ArrayList<Double> histograma) {
		double prom = 0.0;
		int i;
		for (i = 0; i < histograma.size(); i++) {
			prom = prom + histograma.get(i);
		}
		
		return prom/histograma.size();
	}

	public ArrayList<Double> Mat_to_List(Mat mat) {
		ArrayList<Double> Histograma = new ArrayList<Double>();
		for (int j = 0; j < mat.rows(); j++) {
			double[] salidax = mat.get(j, 0);
			Histograma.add(salidax[0]);
		}
		return Histograma;
	}
	public double distancia (Mat H1,Mat H2){
		
		return Imgproc.compareHist(H1, H2, Imgproc.CV_COMP_BHATTACHARYYA );
	}
	public double distancia(ArrayList<Double> histograma1, ArrayList<Double> histograma2) {
		// ArrayList<Double> ej1 = promedio(histograma1);
		// promedio de ambos histogramas
		double promedio1 = promedio(histograma1);
		double promedio2 = promedio(histograma2);
		double score = 0;
		int i;
		for (i = 0; i < histograma2.size(); i++) {
			score += Math.sqrt(histograma1.get(i) * histograma2.get(i));
		}
		score = Math.sqrt(1 - (1 / Math.sqrt(promedio1 * promedio2 * histograma1.size() * histograma2.size() )) * score);
		return score;
	}

	public Mat calcular(Mat mat1) {
		java.util.List<Mat> matList = new LinkedList<Mat>();
		Mat histogram = new Mat();
		MatOfInt histSize = new MatOfInt(256);
		MatOfFloat histRange = new MatOfFloat(0, 255 + 1); // Componentes de 256
															// Valores Capa Hue
		matList.add(mat1);
		Imgproc.calcHist(matList, new MatOfInt(0), new Mat(), histogram, histSize, histRange);
		return histogram;
	}

	/**
	 * Normalización de Histograma Normado entre [0,1]
	 * 
	 * @return Mat
	 * @param img2
	 */
	public Mat normalizar(Mat histograma) {
		Core.normalize(histograma, histograma, 1, 0, Core.NORM_L1, -1, new Mat());
		return histograma;

	}

	public void almacenar_imagen(Mat frame, String nombre) {

		ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
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
	 * Funcion
	 * 
	 * @param img2
	 * @
	 */
	public  void displayImage(Image img2) {

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
