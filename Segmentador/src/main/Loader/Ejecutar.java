package main.Loader;

import main.Preprocesador.*;
//import main.webService.*;

import javax.xml.ws.Endpoint;

public class Ejecutar {
	public static void main(String[] args) {
	
		
		//	Endpoint.publish("http://localhost:8080/WS/HelloWorld",new Servicio()); ACTIVAR WEB SERVICE
		CargarLibrerias();
		Analizador aux = new Analizador();
		aux.cargarvideo("e:\\video.mp4");
	}
	/*
	 * Detecta la plataforma de ejecucin y carga las ´
	 * librerias correspondientes
	 * del Sistema Windows
	 * 
	 * @author German Vives
	 */
	
	public static void CargarLibrerias() {

		String arquitectura = 
		System.getProperty("sun.arch.data.model").toString();
		try {
			if (arquitectura.equals("64")) {
				System.loadLibrary("opencv_java2413_64");
				System.loadLibrary("opencv_ffmpeg2413_64");
				System.out.println("Se ha cargado  AMD64");

			} else {
				// Arquitectura X86 - 32BITS
				System.loadLibrary("opencv_java2413");
				System.loadLibrary("opencv_ffmpeg2413");
				System.out.println("Se ha cargado las librerias"
						+ " para plataforma X86");
			}
		} catch (Error ex) {

			System.out.println(ex.getMessage().toString());
		}

	}
}
