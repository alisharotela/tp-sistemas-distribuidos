package py.una.entidad;

public class Nis {
    
    Integer id;
    Long consumo;
    Boolean esActivo;

    public Nis() {
    }

    public Nis(Integer id, Long consumo, Boolean esActivo) {
        this.id = id;
        this.consumo = consumo;
        this.esActivo = esActivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getConsumo() {
        return consumo;
    }

    public void setConsumo(Long consumo) {
        this.consumo = consumo;
    }

    public Boolean getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(Boolean esActivo) {
        this.esActivo = esActivo;
    }
    
}
