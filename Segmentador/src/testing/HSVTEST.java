package testing;

import static org.junit.Assert.*;

import org.junit.After;  
import org.junit.Before;  
import org.junit.Test; 
import org.opencv.core.Mat;

import main.MotorAlgoritmia.*;

public class HSVTEST {
	public HSV convertidorHSV;
	
	@Before  
    public void antesDelTest() {  
        /** 
         * El metodo precedido por la etiqueta @Before 
         * es para indicar a JUnit que debe ejecutarlo 
         * antes de ejecutar los Tests que figuran en 
         * esta clase. 
         */  
        this.convertidorHSV = new HSV();  
    }  
  
    @After  
    public void despuesDelTest() {  
        /** 
         * La etiqueta @After es la antítesis de @Before. 
         * Simplemente este metodo se ejecutara despues de 
         * ejecutar todos los tests de esta clase. 
         */  
        // en este caso no hago nada, solo esta de ejemplo  
    }  

	@Test
	public void test() {
		 Mat resultado = this.convertidorHSV.convertirBGR2HSV("verde.jpg");  
	     // con esto verificamos que el resultado es el esperado  
	     //assertTrue(resultado == 8);  
	}

}
