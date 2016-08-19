 Dificultad 01
---------
  Bajo Plataforma Windows las direcciones de recursos  ejemplo recurso/imagen.png
  en Java se resuelven  de manera  /c:/folder/recurso/imagen.png
  lo que cae en una condición de error

 **Correción**
	Se debe eliminar el primer caracter de la hilera string siempre y cuando se trabaje 
  	en la plataforma Windows
 
  **Estado **
    Solucionada
	
  
Dificultad 02  
----------
   Durante la instalación del ambiente se presento problemas con el  Path de las librerias DLL y el JAR asociado
   a OpenCV 3.1 
   Por Motivos de documentación introductoria
   
 **Correción**
   se procede a usar versión OpenCV 2.4.13
   
  **Estado**
     Solucionada
