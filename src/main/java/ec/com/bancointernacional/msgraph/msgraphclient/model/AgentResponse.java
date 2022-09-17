package ec.com.bancointernacional.msgraph.msgraphclient.model;

public class AgentResponse extends Response {

	private String nombre;
	private String correo;
	private String nuneroCelular;
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNuneroCelular() {
		return nuneroCelular;
	}
	public void setNuneroCelular(String nuneroCelular) {
		this.nuneroCelular = nuneroCelular;
	}
	
	
	

}
