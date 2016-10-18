package main.webService;

import javax.jws.WebMethod;  
import javax.jws.WebService;  

@WebService 
public interface IServicio {
	 @WebMethod public String helloWorld(String name);  
}
