package co.com.iesonline.sjies.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Operador entity.
 */
public class OperadorDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private ZonedDateTime fechaCreacion;

    private Integer totalSorteos;

    private Integer sorteosActivos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonedDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(ZonedDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getTotalSorteos() {
        return totalSorteos;
    }

    public void setTotalSorteos(Integer totalSorteos) {
        this.totalSorteos = totalSorteos;
    }

    public Integer getSorteosActivos() {
        return sorteosActivos;
    }

    public void setSorteosActivos(Integer sorteosActivos) {
        this.sorteosActivos = sorteosActivos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperadorDTO operadorDTO = (OperadorDTO) o;
        if (operadorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operadorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperadorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", totalSorteos=" + getTotalSorteos() +
            ", sorteosActivos=" + getSorteosActivos() +
            "}";
    }
}
