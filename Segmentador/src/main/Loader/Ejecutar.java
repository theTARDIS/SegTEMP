package main.Loader;

import main.Preprocesador.*;

public class Ejecutar {
	public static void main(String[] args) {
		
		CargarLibrerias();
		Analizador aux = new Analizador();
		aux.CargarVideo("C:\\Users\\Jorge\\workspace\\prueba\\dis.mp4");
	}
	/*
	 * Detecta la plataforma de ejecución y carga las librerias correspondientes
	 * del Sistema Windows
	 * 
	 * @author German Vives
	 */
	public static void CargarLibrerias() {

		String Arquitectura = System.getProperty("sun.arch.data.model").toString();
		try {
			if (Arquitectura.equals("64")) {
				System.loadLibrary("opencv_java2413_64");
				System.loadLibrary("opencv_ffmpeg2413_64");
				System.out.println("Se ha cargado las librerias para plataforma AMD64");

			} else {
				// Arquitectura X86 - 32BITS
				System.loadLibrary("opencv_java2413");
				System.loadLibrary("opencv_ffmpeg2413");
				System.out.println("Se ha cargado las librerias para plataforma X86");
			}
		} catch (Error ex) {

			System.out.println(ex.getMessage().toString());
		}

	}
}
