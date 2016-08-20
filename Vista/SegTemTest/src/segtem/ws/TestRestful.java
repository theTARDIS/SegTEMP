package segtem.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entities.Test;

@Path("test")
public class TestRestful {
	
	@GET
	@Path("/print")
	@Produces(MediaType.APPLICATION_XML)
	public List<Test>  print(){
		List<Test> resultado = new ArrayList<Test>();
		resultado.add(new Test("Prueba WebService Angular",1));
		return resultado;
	}
	
	

}
