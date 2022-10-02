package py.una.entidad;

public class Peticion {

	int estado;
	String mensaje;
	String datos;
	int operacion;

	public Peticion(int estado, String mensaje, String datos, int operacion) {
		super();
		this.estado = estado;
		this.mensaje = mensaje;
		this.datos = datos;
		this.operacion = operacion;
	}

	public Peticion() {}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getDatos() {
		return datos;
	}

	public void setDatos(String datos) {
		this.datos = datos;
	}

	public int getOperacion() {
		return operacion;
	}

	public void setOperacion(int operacion) {
		this.operacion = operacion;
	}

	
}
