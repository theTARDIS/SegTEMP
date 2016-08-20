package entities;

import java.io.*;
import javax.xml.bind.annotation.*;

@XmlRootElement(name= "test")
public class Test implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _nombre;
	private int id;
	
	
	public Test(String _nombre, int id) {
		super();
		this._nombre = _nombre;
		this.id = id;
	}
	
	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	@XmlElement
	public String get_nombre() {
		return _nombre;
	}
	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	@XmlElement
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
