package py.una.entidad;

public class Logger {
    
    String origen;
    String destino;
    // fechaHora es fecha y hora
    String fechaHora;
    int operacion;

    public Logger(String origen, String destino, String fechaHora, int operacion) {
        this.origen = origen;
        this.destino = destino;
        this.fechaHora = fechaHora;
        this.operacion = operacion;
    }
}
