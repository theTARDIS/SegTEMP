package main.Preprocesador;

import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import java.awt.image.BufferedImage;
import  java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * @author German
 *
 */
public class Analizador {
	/**
	 * Esta Función recibe el vídeo a analizar y genera los cuadros del vídeo
	 * @author: German Vives
	 * @version: 1.0
	 * @param Ruta Recibe la dirección del path del vídeo 
	 */
	
	public BufferedImage Mat2BufferedImage(Mat m){
		// source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
		// Fastest code
		// The output can be assigned either to a BufferedImage or to an Image

		    int type = BufferedImage.TYPE_BYTE_GRAY;
		    if ( m.channels() > 1 ) {
		        type = BufferedImage.TYPE_3BYTE_BGR;
		    }
		    int bufferSize = m.channels()*m.cols()*m.rows();
		    byte [] b = new byte[bufferSize];
		    m.get(0,0,b); // get all the pixels
		    BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		    final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		    System.arraycopy(b, 0, targetPixels, 0, b.length);  
		    return image;

		}
	 
	public void CargarVideo(String Ruta) {
		
		 	Mat frame = new Mat();
		    VideoCapture camera = new VideoCapture(Ruta);
		    camera.open(Ruta);
		    System.out.println(Ruta);
		    JFrame jframe = new JFrame("MyTitle");
		    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    JLabel vidpanel = new JLabel();
		    jframe.setContentPane(vidpanel);
		    jframe.setVisible(true);
		    int contador = 0 ;
		    while (true) {
		    	
		        if (camera.read(frame)) { 
		        	ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
		            vidpanel.setIcon(image);
		            vidpanel.repaint();	
		            
		            
		            Image img = image.getImage();

		            BufferedImage bi = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_3BYTE_BGR);

		            Graphics2D g2 = bi.createGraphics();
		            g2.drawImage(img, 0, 0, null);
		            g2.dispose();
		            try {
		            	File theDir = new File("output");
		            	theDir.mkdir();
						ImageIO.write(bi, "jpg", new File("output/img"+String.valueOf(contador) +".jpg"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            contador++;
		            
		        }
		  
		    }
	}

	
    public void displayImage(Image img2)
{   
    //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
    ImageIcon icon=new ImageIcon(img2);
    JFrame frame=new JFrame();
    frame.setLayout(new FlowLayout());        
    frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);     
    JLabel lbl=new JLabel();
    lbl.setIcon(icon);
    frame.add(lbl);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

}

}
