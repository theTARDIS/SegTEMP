
package main.webService;

import javax.jws.WebService;  


@WebService(endpointInterface = "main.webService.IServicio")  
public class Servicio implements IServicio {

	@Override
	public String helloWorld(String name) {
		// TODO Auto-generated method stub
		return "Hello World "+ name;
	}

}


  

